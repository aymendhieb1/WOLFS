package com.wolfs.models;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.PlaylistItem;
import com.google.api.services.youtube.model.PlaylistItemListResponse;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.scene.web.WebEngine;
import javafx.util.Duration;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class YouTubeVideoPlayer {

    public static class VideoItem {
        final String id;
        final String title;

        VideoItem(String id, String title) {
            this.id = id;
            this.title = title;
        }
    }

    private static final String APPLICATION_NAME = "YouTube Video Player";
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    private static final String API_KEY = "AIzaSyDeCQi_RXKiEQDjhWP3hJQUfkecUhyywSA";
    private static final String PLAY_LIST = "PLfSfQ-u0IZ4T919z1Kaeat4e2N75GmsQR";
    private static final int DUR = 1;
    private int currentVideoIndex = 0;

    private final YouTube youtubeService;
    private List<VideoItem> videoItems;
    private final ComboBox<String> videoSelector;
    private final WebView webView;
    private final BorderPane rootPane;
    private final Stage primaryStage;
    private VBox fullscreenContainer;
    private Scene originalScene;
    private Pane originalParent;
    private Timeline  refreshTimeline;

    public YouTubeVideoPlayer(WebView webView, ComboBox<String> videoSelector, BorderPane rootPane, Button fullscreenButton, Stage primaryStage) throws IOException, GeneralSecurityException {
        this.webView = webView;
        this.videoSelector = videoSelector;
        this.rootPane = rootPane;
        this.primaryStage = primaryStage;

        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        youtubeService = new YouTube.Builder(HTTP_TRANSPORT, JSON_FACTORY, null)
                .setApplicationName(APPLICATION_NAME)
                .build();

        setupVideoSelector();
        enableFullscreenSupport();

        if (fullscreenButton != null) {
            fullscreenButton.setOnAction(event -> toggleFullscreen());
        }

        rootPane.widthProperty().addListener((obs, oldWidth, newWidth) -> {
            double newHeight = newWidth.doubleValue() * 9 / 16;
            rootPane.setPrefHeight(newHeight);
        });

        videoItems=fetchVideosFromPlaylist(PLAY_LIST);

        loadCurrentVideo();
        updateVideoSelector();
        setupRefreshTimer();
    }

    private void setupRefreshTimer() {
        refreshTimeline = new Timeline(new KeyFrame(Duration.minutes(DUR), event -> {
            try {
                List<VideoItem> newVideos = fetchVideosFromPlaylist(PLAY_LIST);

                if (!areListsEqual(videoItems, newVideos)) {
                    videoItems = newVideos;
                    updateVideoSelector();
                }

            } catch (IOException e) {
                System.out.println("Refresh timeline failed : " + e.getMessage());
            }
        }));
        refreshTimeline.setCycleCount(Timeline.INDEFINITE);
        refreshTimeline.play();
    }

    public static boolean areListsEqual(List<VideoItem> oldList, List<VideoItem> newList) {

        if (oldList.size() != newList.size()) {
            return false;
        }

        for (int i = 0; i < oldList.size(); i++) {
            if (!oldList.get(i).id.equals(newList.get(i).id)) {
                return false;
            }
        }

        return true;
    }

    public List<VideoItem> fetchVideosFromPlaylist(String playlistId) throws IOException {
        YouTube.PlaylistItems.List request = youtubeService.playlistItems()
                .list(Collections.singletonList("snippet"));

        request.setKey(API_KEY);
        request.setPlaylistId(playlistId);
        request.setFields("items(snippet/resourceId/videoId,snippet/title)");

        List<VideoItem> newVideoItems = new ArrayList<>();
        String nextPageToken = null;

        do {
            if (nextPageToken != null) {
                request.setPageToken(nextPageToken);
            }

            PlaylistItemListResponse response = request.execute();
            for (PlaylistItem item : response.getItems()) {
                String videoId = item.getSnippet().getResourceId().getVideoId();
                String title = item.getSnippet().getTitle();
                newVideoItems.add(new VideoItem(videoId, title));
            }
            nextPageToken = response.getNextPageToken();
        } while (nextPageToken != null);

        return newVideoItems;
    }

    public void loadCurrentVideo() {
        if (videoItems == null || videoItems.isEmpty()) {
            return;
        }

        VideoItem current = videoItems.get(currentVideoIndex);
        String videoUrl = "https://www.youtube.com/embed/" + current.id +
                "?autoplay=1" +
                "&color=white" +
                "&iv_load_policy=3" +
                "&loop=1" +
                "&playlist=" + current.id +
                "&modestbranding=1" +
                "&rel=0" +
                "&fs=0" +
                "&hl=fr" +
                "&cc_lang_pref=fr" +
                "&cc_load_policy=1"

                ;
        webView.getEngine().load(videoUrl);
        webView.getEngine().locationProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.contains("error") || newValue.contains("unavailable")) {
                loadNextVideo();
            }
        });
    }

    private void setupVideoSelector() {
        videoSelector.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            for (int i = 0; i < videoItems.size(); i++) {
                if (videoItems.get(i).title.equals(newValue)) {
                    currentVideoIndex = i;
                    loadCurrentVideo();
                    break;
                }
            }
        });
    }

    private void enableFullscreenSupport() {
        webView.getEngine().setOnStatusChanged(event -> {
            if (event.getSource() instanceof WebEngine) {
                String status = event.getData();
                if (status.contains("fullscreen")) {
                    toggleFullscreen();
                }
            }
        });

        primaryStage.addEventHandler(KeyEvent.KEY_RELEASED, (KeyEvent event) -> {
            if (KeyCode.ESCAPE == event.getCode() && primaryStage.isFullScreen()) {
                toggleFullscreen();
            }
        });

        primaryStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
    }

    private void toggleFullscreen() {
        if (primaryStage.isFullScreen()) {
            primaryStage.setFullScreen(false);

            if (originalScene != null) {
                primaryStage.setScene(originalScene);
            }

            if (originalParent != null) {
                originalParent.getChildren().add(rootPane);
            }

            if (fullscreenContainer != null) {
                fullscreenContainer.getChildren().clear();
            }

            originalScene.setFill(null);
        } else {
            originalScene = primaryStage.getScene();
            originalParent = (Pane) rootPane.getParent();

            if (originalScene == null || originalParent == null) {
                return;
            }

            fullscreenContainer = new VBox(rootPane);
            fullscreenContainer.setStyle("-fx-background-color: black;");
            Scene fullscreenScene = new Scene(fullscreenContainer, Color.BLACK);
            primaryStage.setScene(fullscreenScene);
            primaryStage.setFullScreen(true);
        }
    }

    private void updateVideoSelector() {
        videoSelector.getItems().clear();
        for (VideoItem item : videoItems) {
            videoSelector.getItems().add(item.title);
        }
        if (!videoItems.isEmpty()) {
            videoSelector.getSelectionModel().select(currentVideoIndex);
        }
    }

    public void loadNextVideo() {
        if (videoItems.isEmpty()) return;

        currentVideoIndex = (currentVideoIndex + 1) % videoItems.size();
        loadCurrentVideo();
        videoSelector.getSelectionModel().select(currentVideoIndex);
    }

    public void loadPreviousVideo() {
        if (videoItems.isEmpty()) return;

        currentVideoIndex = (currentVideoIndex - 1 + videoItems.size()) % videoItems.size();
        loadCurrentVideo();
        videoSelector.getSelectionModel().select(currentVideoIndex);
    }

    public void clearResources() {

        if (refreshTimeline != null) {
            refreshTimeline.stop();
            refreshTimeline = null;
        }

        webView.getEngine().load(null);
        webView.getEngine().getLoadWorker().cancel();

        if (videoItems != null) {
            videoItems.clear();
            videoItems = null;
        }

        videoSelector.getItems().clear();

        currentVideoIndex = 0;

        originalScene = null;
        originalParent = null;
        fullscreenContainer = null;

        videoSelector.getItems().clear();
    }

}