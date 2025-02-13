package com.wolfs.controllers;

import com.wolfs.models.*;
import com.wolfs.services.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javafx.util.StringConverter;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class PostController {
    public ScrollPane postsContainer;
    @FXML private ComboBox<Forum> forumComboBox;
    @FXML private VBox postsVBox;
    @FXML private VBox commentsSection;
    @FXML private Button backButton;
    @FXML private TextArea forumDescription;
    @FXML private Label forumPrivacyLabel;
    @FXML private Label forumDateLabel;
    @FXML private Label memberCountLabel;
    @FXML private ListView<String> membersListView;
    @FXML private VBox membersSection;
    @FXML private ComboBox<String> postTypeComboBox;
    @FXML private VBox announcementFields;
    @FXML private VBox surveyFields;
    @FXML private TextField announcementTitleField;
    @FXML private TextArea announcementContentField;
    @FXML private TextField announcementTagsField;
    @FXML private TextField surveyQuestionField;
    @FXML private TextField surveyTagsField;
    @FXML private TextArea newCommentField;
    @FXML private VBox commentsVBox;
    
    // Error labels
    @FXML private Label titleErrorLabel;
    @FXML private Label contentErrorLabel;
    @FXML private Label tagsErrorLabel;
    @FXML private Label questionErrorLabel;
    @FXML private Label surveyTagsErrorLabel;
    @FXML private Label commentErrorLabel;
    @FXML private Label choiceErrorLabel;

    @FXML private TabPane leftTabPane;
    @FXML private Label postTitleLabel;
    @FXML private Text postContentText;
    @FXML private Label postTagsLabel;
    @FXML private VBox surveyChoicesBox;
    @FXML private Button viewVotersButton;
    @FXML private VBox postDetailsBox;

    @FXML private TextField choiceField;
    @FXML private Button addChoiceButton;
    @FXML private ListView<String> choicesListView;

    private ForumService forumService = new ForumService();
    private PostService postService = new PostService();
    private ChoixService choixService = new ChoixService();
    
    private Post currentPost;



    @FXML
    private void initialize() {
        // Initialize post type combo box
        postTypeComboBox.getItems().addAll("Announcement", "Survey");
        postTypeComboBox.setOnAction(e -> handlePostTypeChange());
        
        // Load forums into combo box
        loadForums();
        
        // Setup forum combo box listener
        forumComboBox.setOnAction(e -> updateForumInfo());
        
        // Initialize validation listeners
        setupValidationListeners();
        
        // Load initial posts
        refreshPosts();

        leftTabPane.setStyle("-fx-tab-max-height: 0;"); // Hide tab headers
        
        // Setup choice validation
        choiceField.textProperty().addListener((obs, old, newValue) -> 
            validateChoice(newValue));
    }

    private void handlePostTypeChange() {
        String selectedType = postTypeComboBox.getValue();
        announcementFields.setVisible("Announcement".equals(selectedType));
        surveyFields.setVisible("Survey".equals(selectedType));
        
        // Clear fields when switching types
        if ("Announcement".equals(selectedType)) {
            clearSurveyFields();
        } else {
            clearAnnouncementFields();
        }
    }

    private void loadForums() {
        List<Forum> forums = forumService.rechercher();
        forumComboBox.getItems().clear();
        forumComboBox.getItems().addAll(forums);
        
        // Setup display format for forum items
        forumComboBox.setConverter(new StringConverter<Forum>() {
            @Override
            public String toString(Forum forum) {
                return forum != null ? forum.getName() : "";
            }

            @Override
            public Forum fromString(String string) {
                return null; // Not needed for ComboBox
            }
        });
    }

    private void updateForumInfo() {
        Forum selectedForum = forumComboBox.getValue();
        if (selectedForum != null) {
            forumDescription.setText(selectedForum.getDescription());
            forumPrivacyLabel.setText(selectedForum.isPrivate() ? "Private" : "Public");
            forumDateLabel.setText("Created: " + selectedForum.getDateCreation().toString());
            
            if (selectedForum.isPrivate()) {
                memberCountLabel.setText("Members: " + selectedForum.getNbrMembers());
                membersSection.setVisible(true);
                
                // Load members list
                membersListView.getItems().clear();
                if (selectedForum.getListMembers() != null && !selectedForum.getListMembers().isEmpty()) {
                    String[] members = selectedForum.getListMembers().split(",");
                    for (String member : members) {
                        membersListView.getItems().add(member);
                    }
                }
            } else {
                memberCountLabel.setText("");
                membersSection.setVisible(false);
            }
            handleBackButton();
            refreshPosts();
        }
    }

    private void setupValidationListeners() {
        // Announcement validation
        announcementTitleField.textProperty().addListener((obs, old, newValue) -> 
            validateAnnouncementTitle(newValue));
        
        announcementContentField.textProperty().addListener((obs, old, newValue) -> 
            validateAnnouncementContent(newValue));
        
        announcementTagsField.textProperty().addListener((obs, old, newValue) -> 
            validateTags(newValue, tagsErrorLabel));
        
        // Survey validation
        surveyQuestionField.textProperty().addListener((obs, old, newValue) -> 
            validateSurveyQuestion(newValue));
        
        surveyTagsField.textProperty().addListener((obs, old, newValue) -> 
            validateTags(newValue, surveyTagsErrorLabel));
        
        // Comment validation
        newCommentField.textProperty().addListener((obs, old, newValue) -> 
            validateComment(newValue));
    }

    private void refreshPosts() {
        postsVBox.getChildren().clear();
        Forum selectedForum = forumComboBox.getValue();

        if (selectedForum != null) {
            List<Post> posts = postService.rechercher().stream()
                    .filter(p -> p.getForumId() == selectedForum.getForumId())
                    .filter(p -> (p instanceof Announcement) || (p instanceof Survey))
                    .toList();

            for (Post post : posts) {
                VBox postBox = createPostBox(post);
                postsVBox.getChildren().add(postBox);
            }
        }
    }


    private VBox createPostBox(Post post) {
        VBox postBox = new VBox(10);
        postBox.getStyleClass().add("post-box");

        if (post instanceof Announcement announcement) {
            Label title = new Label(announcement.getAnnouncementTitle());
            title.getStyleClass().add("post-title");

            Text content = new Text(announcement.getAnnouncementContent());
            content.getStyleClass().add("post-content");

            Label tags = new Label("Tags: " + announcement.getAnnouncementTags());
            tags.getStyleClass().add("post-tags");

            postBox.getChildren().addAll(title, content, tags);

        }
        else if (post instanceof Survey survey) {
            Label question = new Label(survey.getSurveyQuestion());
            question.getStyleClass().add("post-title");

            VBox choicesBox = new VBox(5);
            List<Choix> choices = choixService.rechercher().stream()
                .filter(c -> c.getPostId() == survey.getPostId())
                .toList();

            for (Choix choice : choices) {
                HBox choiceBox = new HBox(10);
                Label choiceLabel = new Label(choice.getChoix());
                Label voteCount = new Label(choice.getChoiceVotesCount() + " votes");
                choiceBox.getChildren().addAll(choiceLabel, voteCount);
                choicesBox.getChildren().add(choiceBox);
            }

            Label tags = new Label("Tags: " + survey.getSurveyTags());
            tags.getStyleClass().add("post-tags");

            postBox.getChildren().addAll(question, choicesBox, tags);
        }

        // Add common controls
        HBox controls = new HBox(10);
        Button viewComments = new Button("View Comments");
        Button editPost = new Button("Edit");

        viewComments.setOnAction(e -> showComments(post));
        editPost.setOnAction(e -> editPost(post));

        controls.getChildren().addAll(viewComments, editPost);
        postBox.getChildren().add(controls);

        return postBox;
    }

    private void clearSurveyFields() {
        surveyQuestionField.clear();
        surveyTagsField.clear();
        choicesListView.getItems().clear();
        choiceField.clear();
        addChoiceButton.setDisable(true);
    }

    private void clearAnnouncementFields() {
        announcementTitleField.clear();
        announcementContentField.clear();
        announcementTagsField.clear();
    }

    private void validateAnnouncementTitle(String title) {
        if (title.isEmpty()) {
            showError(announcementTitleField, titleErrorLabel, "Le titre ne peut pas être vide");
        } else if (title.length() > 50) {
            showError(announcementTitleField, titleErrorLabel, "Le titre ne doit pas dépasser 50 caractères");
        } else {
            hideError(announcementTitleField, titleErrorLabel);
        }
    }

    private void validateAnnouncementContent(String content) {
        if (content.isEmpty()) {
            showError(announcementContentField, contentErrorLabel, "Le contenu ne peut pas être vide");
        } else if (content.length() > 500) {
            showError(announcementContentField, contentErrorLabel, "Le contenu ne doit pas dépasser 500 caractères");
        } else {
            hideError(announcementContentField, contentErrorLabel);
        }
    }

    private void validateSurveyQuestion(String question) {
        if (question.isEmpty()) {
            showError(surveyQuestionField, questionErrorLabel, "La question ne peut pas être vide");
        } else if (question.length() > 200) {
            showError(surveyQuestionField, questionErrorLabel, "La question ne doit pas dépasser 200 caractères");
        } else {
            hideError(surveyQuestionField, questionErrorLabel);
        }
    }

    private void validateTags(String tags, Label errorLabel) {
        if (tags.length() > 100) {
            showError(tags.equals(surveyTagsField.getText()) ? surveyTagsField : announcementTagsField,
                    errorLabel, "Les tags ne doivent pas dépasser 100 caractères");
        } else if (!tags.isEmpty() && !tags.matches("^[a-zA-Z0-9,\\s]+$")) {
            showError(tags.equals(surveyTagsField.getText()) ? surveyTagsField : announcementTagsField,
                    errorLabel, "Les tags ne doivent contenir que des lettres, chiffres et virgules");
        } else {
            hideError(tags.equals(surveyTagsField.getText()) ? surveyTagsField : announcementTagsField,
                    errorLabel);
        }
    }

    private void validateComment(String comment) {
        if (comment.isEmpty()) {
            showError(newCommentField, commentErrorLabel, "Le commentaire ne peut pas être vide");
        } else if (comment.length() > 200) {
            showError(newCommentField, commentErrorLabel, "Le commentaire ne doit pas dépasser 200 caractères");
        } else {
            hideError(newCommentField, commentErrorLabel);
        }
    }

    private void validateChoice(String choice) {
        if (choice.isEmpty()) {
            addChoiceButton.setDisable(true);
            showError(choiceField, choiceErrorLabel, "Le choix ne peut pas être vide");
        } else if (choice.length() > 50) {
            addChoiceButton.setDisable(true);
            showError(choiceField, choiceErrorLabel, "Le choix ne doit pas dépasser 50 caractères");
        } else if (choicesListView.getItems().contains(choice)) {
            addChoiceButton.setDisable(true);
            showError(choiceField, choiceErrorLabel, "Ce choix existe déjà");
        } else {
            hideError(choiceField, choiceErrorLabel);
            addChoiceButton.setDisable(false);
        }
    }

    private void showError(TextInputControl field, Label errorLabel, String message) {
        if (field != null) {
            field.getStyleClass().remove("valid-field");
            field.getStyleClass().add("text-field-error");
        }
        if (errorLabel != null) {
            errorLabel.setText(message);
            errorLabel.setVisible(true);
        }
    }

    private void hideError(TextInputControl field, Label errorLabel) {
        if (field != null) {
            field.getStyleClass().remove("text-field-error");
            field.getStyleClass().add("valid-field");
        }
        if (errorLabel != null) {
            errorLabel.setVisible(false);
        }
    }

    private void showComments(Post post) {
        currentPost = post;
        
        // Switch to comments tab
        leftTabPane.getSelectionModel().select(1);
        
        // Update post details
        if (post instanceof Announcement announcement) {
            postTitleLabel.setText(announcement.getAnnouncementTitle());
            postContentText.setText(announcement.getAnnouncementContent());
            postTagsLabel.setText("Tags: " + announcement.getAnnouncementTags());
            surveyChoicesBox.setVisible(false);
            viewVotersButton.setVisible(false);
        } else if (post instanceof Survey survey) {
            postTitleLabel.setText(survey.getSurveyQuestion());
            postContentText.setText("");
            postTagsLabel.setText("Tags: " + survey.getSurveyTags());
            
            // Show survey choices
            surveyChoicesBox.getChildren().clear();
            surveyChoicesBox.setVisible(true);
            viewVotersButton.setVisible(true);
            
            List<Choix> choices = choixService.rechercher().stream()
                .filter(c -> c.getPostId() == survey.getPostId())
                .toList();
                
            for (Choix choice : choices) {
                HBox choiceBox = new HBox(10);
                Label choiceLabel = new Label(choice.getChoix());
                Label voteCount = new Label(choice.getChoiceVotesCount() + " votes");
                choiceBox.getChildren().addAll(choiceLabel, voteCount);
                surveyChoicesBox.getChildren().add(choiceBox);
            }
        }
        
        // Load comments
        loadComments();
    }

    private void loadComments() {
        // Clear previous comments
        commentsVBox.getChildren().clear();

        // Retrieve the comments for the current post
        List<Comment> comments = postService.rechercher().stream()
                .filter(p -> p instanceof Comment)
                .map(p -> (Comment) p)
                .filter(c -> c.getParentId() == currentPost.getPostId())
                .toList();

        for (Comment comment : comments) {
            VBox commentBox = new VBox(5);
            commentBox.getStyleClass().add("comment-box");

            Label content = new Label(comment.getCommentContent());
            Label date = new Label(comment.getDateCreation().toString());
            date.getStyleClass().add("comment-date");

            commentBox.getChildren().addAll(content, date);
            commentsVBox.getChildren().add(commentBox);
        }
    }


    @FXML
    private void handleViewVoters() {
        if (currentPost instanceof Survey survey) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Survey Voters");
            alert.setHeaderText("Users who voted in this survey");
            alert.setContentText(survey.getSurveyUserList());
            alert.showAndWait();
        }
    }

    @FXML
    private void handleBackButton() {
        leftTabPane.getSelectionModel().select(0);
        currentPost = null;
        newCommentField.clear();
    }

    private void editPost(Post post) {
        if (post instanceof Announcement announcement) {
            // Switch to Add Post tab
            leftTabPane.getSelectionModel().select(1);
            postTypeComboBox.setValue("Announcement");
            
            // Fill announcement fields
            announcementTitleField.setText(announcement.getAnnouncementTitle());
            announcementContentField.setText(announcement.getAnnouncementContent());
            announcementTagsField.setText(announcement.getAnnouncementTags());
            
        } else if (post instanceof Survey survey) {
            // Switch to Add Post tab
            leftTabPane.getSelectionModel().select(1);
            postTypeComboBox.setValue("Survey");
            
            // Fill survey fields
            surveyQuestionField.setText(survey.getSurveyQuestion());
            surveyTagsField.setText(survey.getSurveyTags());
            
            // Load choices
            choicesListView.getItems().clear();
            List<Choix> choices = choixService.rechercher().stream()
                .filter(c -> c.getPostId() == survey.getPostId())
                .toList();
                
            for (Choix choice : choices) {
                choicesListView.getItems().add(choice.getChoix());
            }
        }
        currentPost = post;
    }

    @FXML
    private void handleAddChoice() {
        String choice = choiceField.getText().trim();
        if (!choice.isEmpty() && !choicesListView.getItems().contains(choice)) {
            choicesListView.getItems().add(choice);
            choiceField.clear();
            addChoiceButton.setDisable(true);
        }
    }

    @FXML
    private void handleAddComment() {
        if (currentPost == null) return;
        
        String content = newCommentField.getText().trim();
        if (content.isEmpty()) {
            showError(newCommentField, commentErrorLabel, "Le commentaire ne peut pas être vide");
            return;
        }
        
        Comment comment = new Comment(
            0, // postId will be set by database
            currentPost.getForumId(),
            1, // TODO: Get current user ID
            0, // initial votes
            LocalDateTime.now(),
            LocalDateTime.now(),
            "", // no file
            "active",
            0, // initial signal count
            content,
            currentPost.getPostId()
        );
        
        postService.ajouter(comment);
        newCommentField.clear();
        showComments(currentPost);
    }

    @FXML
    public void handleSubmitPost(ActionEvent actionEvent) {
        String selectedType = postTypeComboBox.getValue();
        Forum selectedForum = forumComboBox.getValue();

        // Ensure a forum is selected
        if (selectedForum == null) {
            // Optionally, display an error message to select a forum
            return;
        }

        if ("Announcement".equals(selectedType)) {
            // Retrieve announcement fields
            String title = announcementTitleField.getText().trim();
            String content = announcementContentField.getText().trim();
            String tags = announcementTagsField.getText().trim();

            // Basic validation check (rely on your listeners for detailed feedback)
            if (title.isEmpty() || content.isEmpty()) {
                return;
            }

            // Edit existing announcement or add a new one
            if (currentPost != null && currentPost instanceof Announcement announcement) {
                // Update existing announcement
                announcement.setAnnouncementTitle(title);
                announcement.setAnnouncementContent(content);
                announcement.setAnnouncementTags(tags);
                // Assuming postService has an update (modifier) method
                postService.modifier(announcement);
            } else {
                // Create a new announcement
                Announcement newAnnouncement = new Announcement(
                        0, // id will be set by the database
                        selectedForum.getForumId(),
                        1, // TODO: Replace with the current user ID
                        0, // initial vote count
                        LocalDateTime.now(),
                        LocalDateTime.now(),
                        "", // no file associated
                        "active",
                        0, // initial signal count
                        title,
                        content,
                        tags
                );
                postService.ajouter(newAnnouncement);
            }
        } else if ("Survey".equals(selectedType)) {
            // Retrieve survey fields
            String question = surveyQuestionField.getText().trim();
            String tags = surveyTagsField.getText().trim();

            if (question.isEmpty()) {
                return;
            }

            // Get choices from ListView instead of TextField list
            List<String> choices = new ArrayList<>(choicesListView.getItems());
            
            if (choices.size() < 2) {
                showError(choiceField, choiceErrorLabel, "Une enquête doit avoir au moins deux choix");
                return;
            }

            if (currentPost != null && currentPost instanceof Survey survey) {
                // Update existing survey
                survey.setSurveyQuestion(question);
                survey.setSurveyTags(tags);
                postService.modifier(survey);

                // For editing choices: This simple implementation does not update choices.
                // You might want to add logic here to update or replace existing choices.
            } else {
                // Create a new survey
                Survey newSurvey = new Survey(
                        0, // id will be generated by the database
                        selectedForum.getForumId(),
                        1, // TODO: Replace with the current user ID
                        0, // initial vote count
                        LocalDateTime.now(),
                        LocalDateTime.now(),
                        "", // no file associated
                        "active",
                        0, // initial signal count
                        question,
                        tags,""
                );
                postService.ajouter(newSurvey);

                // After adding the survey, add each choice.
                // This assumes that newSurvey.getPostId() returns the generated ID.
                for (String choiceText : choices) {
                    Choix choice = new Choix(
                            0,
                            newSurvey.getPostId(),
                            choiceText,
                            0 ,0
                    );
                    choixService.ajouter(choice);
                }
            }
        }

        // Refresh posts display and clear the input fields
        refreshPosts();
        clearAnnouncementFields();
        clearSurveyFields();
        currentPost = null;
    }

} 