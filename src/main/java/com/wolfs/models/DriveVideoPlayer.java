package com.wolfs.models;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import javafx.scene.web.WebView;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DriveVideoPlayer {
    private static String APPLICATION_NAME = "Google Drive Video Player";
    private static JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    private static String TOKENS_DIRECTORY_PATH = "tokens";
    private static List<String> SCOPES = Collections.singletonList(DriveScopes.DRIVE_READONLY);
    private static String CREDENTIALS_FILE_PATH = "/credentials.json";

    private Drive driveService;
    private List<String> videoIds;
    private int currentVideoIndex = 0;

    public DriveVideoPlayer() throws IOException, GeneralSecurityException {
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        driveService = new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    private Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {
        InputStream in = DriveVideoPlayer.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
        if (in == null) {
            throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
        }
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
                .setAccessType("online")
                .build();
        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
        return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
    }

    /**
     * Lists all video files in a specific folder on Google Drive.
     *
     * @param folderId The ID of the folder to list videos from.
     * @return A list of video file IDs.
     */
    public List<String> listVideosInFolder(String folderId) throws IOException {
        String query = "'" + folderId + "' in parents and mimeType contains 'video/'";
        FileList result = driveService.files().list()
                .setQ(query)
                .setFields("files(id, name)")
                .execute();

        videoIds = new ArrayList<>();
        for (File file : result.getFiles()) {
            videoIds.add(file.getId());
        }
        return videoIds;
    }

    /**
     * Loads the current video into the WebView.
     *
     * @param webView The WebView component to display the video.
     */
    public void loadCurrentVideo(WebView webView) {
        if (videoIds == null || videoIds.isEmpty()) {
            System.out.println("No videos to play.");
            return;
        }

        String videoId = videoIds.get(currentVideoIndex);
        String videoUrl = "https://drive.google.com/file/d/" + videoId + "/view";
        webView.getEngine().load(videoUrl);
        System.out.println("Loading video: " + videoUrl);
        System.out.println("WebView: " + webView);

    }

    /**
     * Loads the next video in the list.
     *
     * @param webView The WebView component to display the video.
     */
    public void loadNextVideo(WebView webView) {
        if (currentVideoIndex < videoIds.size() - 1) {
            currentVideoIndex++;
            loadCurrentVideo(webView);
        } else {
            System.out.println("No more videos to play.");
        }
    }

    /**
     * Loads the previous video in the list.
     *
     * @param webView The WebView component to display the video.
     */
    public void loadPreviousVideo(WebView webView) {
        if (currentVideoIndex > 0) {
            currentVideoIndex--;
            loadCurrentVideo(webView);
        } else {
            System.out.println("No previous videos.");
        }
    }
}