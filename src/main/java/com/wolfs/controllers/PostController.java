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
import javafx.stage.FileChooser;
import java.io.File;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import java.util.HashSet;
import java.util.Set;
import java.util.Arrays;

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

    @FXML private TextField announcementFileField;
    @FXML private TextField surveyFileField;
    @FXML private ComboBox<String> announcementStatusComboBox;
    @FXML private ComboBox<String> surveyStatusComboBox;
    @FXML private TextField surveyUserField;
    @FXML private Button addSurveyUserButton;
    @FXML private ListView<String> surveyUsersListView;
    @FXML private Label surveyUserErrorLabel;
    @FXML private Label announcementFileErrorLabel;
    @FXML private Label surveyFileErrorLabel;

    @FXML private TextField editChoiceField;
    @FXML private Button editAddChoiceButton;
    @FXML private ListView<String> editChoicesListView;

    @FXML private TextField editSurveyQuestionField;
    @FXML private TextField editSurveyTagsField;
    @FXML private TextField editSurveyFileField;
    @FXML private ComboBox<String> editSurveyStatusComboBox;
    @FXML private TextField editSurveyUserField;
    @FXML private Button editAddSurveyUserButton;
    @FXML private ListView<String> editSurveyUsersListView;
    @FXML private Label editSurveyUserErrorLabel;
    @FXML private Label editChoiceErrorLabel;

    @FXML private TextField editAnnouncementTitleField;
    @FXML private TextArea editAnnouncementContentField;
    @FXML private TextField editAnnouncementTagsField;
    @FXML private TextField editAnnouncementFileField;
    @FXML private ComboBox<String> editAnnouncementStatusComboBox;
    @FXML private VBox editAnnouncementFields;
    @FXML private VBox editSurveyFields;

    @FXML private Label editTitleErrorLabel;
    @FXML private Label editContentErrorLabel;
    @FXML private Label editSurveyQuestionErrorLabel;

    @FXML private TextField announcementUserField;
    @FXML private TextField surveyAuthorField;
    @FXML private TextField editAnnouncementUserField;
    @FXML private TextField editSurveyAuthorField;
    @FXML private Label announcementUserErrorLabel;
    @FXML private Label surveyAuthorErrorLabel;
    @FXML private Label editAnnouncementUserErrorLabel;
    @FXML private Label editSurveyAuthorErrorLabel;

    @FXML private Button addPostButton;

    @FXML private Label editTagsErrorLabel;
    @FXML private Label editSurveyTagsErrorLabel;

    private ForumService forumService = new ForumService();
    private PostService postService = new PostService();
    private ChoixService choixService = new ChoixService();

    private Post currentPost;

    @FXML
    private void initialize() {
        postTypeComboBox.getItems().addAll("Announcement", "Survey");
        postTypeComboBox.setOnAction(e -> handlePostTypeChange());

        loadForums();

        forumComboBox.setOnAction(e -> updateForumInfo());

        setupValidationListeners();

        refreshPosts();

        leftTabPane.setStyle("-fx-tab-max-height: 0;");

        choiceField.textProperty().addListener((obs, old, newValue) ->
                validateChoice(newValue));

        announcementStatusComboBox.setValue("active");
        surveyStatusComboBox.setValue("active");

        surveyUserField.textProperty().addListener((obs, old, newValue) ->
                validateSurveyUser(newValue));

        editChoiceField.textProperty().addListener((obs, old, newValue) ->
                validateEditChoice(newValue));
        editSurveyUserField.textProperty().addListener((obs, old, newValue) ->
                validateEditSurveyUser(newValue));
        editAnnouncementTitleField.textProperty().addListener((obs, old, newValue) ->
                validateEditTitle(newValue));
        editAnnouncementContentField.textProperty().addListener((obs, old, newValue) ->
                validateEditContent(newValue));
        editSurveyQuestionField.textProperty().addListener((obs, old, newValue) ->
                validateEditQuestion(newValue));

        editChoicesListView.setOnContextMenuRequested(e -> {
            if (editChoicesListView.getSelectionModel().getSelectedItem() != null) {
                ContextMenu menu = new ContextMenu();
                MenuItem removeItem = new MenuItem("Remove");
                removeItem.setOnAction(event ->
                        editChoicesListView.getItems().remove(
                                editChoicesListView.getSelectionModel().getSelectedItem()
                        )
                );
                menu.getItems().add(removeItem);
                menu.show(editChoicesListView, e.getScreenX(), e.getScreenY());
            }
        });

        editSurveyUsersListView.setOnContextMenuRequested(e -> {
            if (editSurveyUsersListView.getSelectionModel().getSelectedItem() != null) {
                ContextMenu menu = new ContextMenu();
                MenuItem removeItem = new MenuItem("Remove");
                removeItem.setOnAction(event ->
                        editSurveyUsersListView.getItems().remove(
                                editSurveyUsersListView.getSelectionModel().getSelectedItem()
                        )
                );
                menu.getItems().add(removeItem);
                menu.show(editSurveyUsersListView, e.getScreenX(), e.getScreenY());
            }
        });

        announcementUserField.textProperty().addListener((obs, old, newValue) ->
                validateUserEmail(announcementUserField, announcementUserErrorLabel, newValue));
        surveyAuthorField.textProperty().addListener((obs, old, newValue) ->
                validateUserEmail(surveyAuthorField, surveyAuthorErrorLabel, newValue));
        editAnnouncementUserField.textProperty().addListener((obs, old, newValue) ->
                validateUserEmail(editAnnouncementUserField, editAnnouncementUserErrorLabel, newValue));
        editSurveyAuthorField.textProperty().addListener((obs, old, newValue) ->
                validateUserEmail(editSurveyAuthorField, editSurveyAuthorErrorLabel, newValue));

        addPostButton.setDisable(true);

        forumComboBox.valueProperty().addListener((obs, oldVal, newVal) -> {
            addPostButton.setDisable(newVal == null);
        });

        announcementTagsField.textProperty().addListener((obs, old, newValue) ->
                validateTags(newValue, tagsErrorLabel));

        surveyTagsField.textProperty().addListener((obs, old, newValue) ->
                validateTags(newValue, surveyTagsErrorLabel));

        editAnnouncementTagsField.textProperty().addListener((obs, old, newValue) ->
                validateEditTags(newValue, editTagsErrorLabel));

        editSurveyTagsField.textProperty().addListener((obs, old, newValue) ->
                validateEditTags(newValue, editSurveyTagsErrorLabel));

        List<TextInputControl> fields = Arrays.asList(
                announcementTitleField, announcementContentField, announcementTagsField,
                surveyQuestionField, surveyTagsField, choiceField, surveyUserField,
                editAnnouncementTitleField, editAnnouncementContentField, editAnnouncementTagsField,
                editSurveyQuestionField, editSurveyTagsField, editChoiceField, editSurveyUserField
        );

        for (TextInputControl field : fields) {
            field.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
                if (!isNowFocused && !field.getStyleClass().contains("valid-field")
                        && !field.getStyleClass().contains("text-field-error")) {
                    field.getStyleClass().add("text-field-error");
                }
            });
        }
    }

    private void handlePostTypeChange() {
        String selectedType = postTypeComboBox.getValue();
        announcementFields.setVisible("Announcement".equals(selectedType));
        surveyFields.setVisible("Survey".equals(selectedType));

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

        forumComboBox.setConverter(new StringConverter<Forum>() {
            @Override
            public String toString(Forum forum) {
                return forum != null ? forum.getName() : "";
            }

            @Override
            public Forum fromString(String string) {
                return null;
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

        announcementTitleField.textProperty().addListener((obs, old, newValue) ->
                validateAnnouncementTitle(newValue));

        announcementContentField.textProperty().addListener((obs, old, newValue) ->
                validateAnnouncementContent(newValue));

        announcementTagsField.textProperty().addListener((obs, old, newValue) ->
                validateTags(newValue, tagsErrorLabel));

        surveyQuestionField.textProperty().addListener((obs, old, newValue) ->
                validateSurveyQuestion(newValue));

        surveyTagsField.textProperty().addListener((obs, old, newValue) ->
                validateTags(newValue, surveyTagsErrorLabel));

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
                VBox postBox = displayPost(post);
                postsVBox.getChildren().add(postBox);
            }
        }
    }

    private VBox displayPost(Post post) {
        VBox postBox = new VBox(10);
        postBox.getStyleClass().add("post-box");

        HBox votingBox = new HBox(5);
        Button upVoteBtn = new Button("↑");
        Button downVoteBtn = new Button("↓");
        Label voteCount = new Label(String.valueOf(post.getVotes()));

        upVoteBtn.setOnAction(e -> {
            post.setVotes(post.getVotes() + 1);
            postService.modifier(post);
            refreshPosts();
        });

        downVoteBtn.setOnAction(e -> {
            post.setVotes(post.getVotes() - 1);
            postService.modifier(post);
            refreshPosts();
        });

        votingBox.getChildren().addAll(upVoteBtn, voteCount, downVoteBtn);
        postBox.getChildren().add(votingBox);

        votingBox.getStyleClass().add("vote-box");
        upVoteBtn.getStyleClass().add("vote-button");
        downVoteBtn.getStyleClass().add("vote-button");
        voteCount.getStyleClass().add("vote-count");

        if (post.getCheminFichier() != null && !post.getCheminFichier().isEmpty()) {
            try {
                ImageView imageView = new ImageView(new Image(new File(post.getCheminFichier()).toURI().toString()));
                imageView.setFitWidth(200);
                imageView.setPreserveRatio(true);
                postBox.getChildren().add(imageView);
            } catch (Exception e) {
                System.out.println("Error loading image: " + e.getMessage());
            }
        }

        if (post instanceof Announcement announcement) {
            Label title = new Label(announcement.getAnnouncementTitle());
            title.getStyleClass().add("post-title");

            Text content = new Text(announcement.getAnnouncementContent());
            content.getStyleClass().add("post-content");

            Label tags = new Label("Tags: " + announcement.getAnnouncementTags());
            tags.getStyleClass().add("post-tags");

            Label dateLabel = new Label(formatDateTime(post.getDateCreation()));

            postBox.getChildren().addAll(title, content, tags, dateLabel);

        }
        else if (post instanceof Survey survey) {
            Label question = new Label(survey.getSurveyQuestion());
            question.getStyleClass().add("post-title");

            VBox choicesBox = new VBox(5);
            List<Choix> choices = choixService.rechercher().stream()
                    .filter(c -> c.getPostId() == survey.getPostId())
                    .toList();

            int totalVotes = choices.stream()
                    .mapToInt(Choix::getChoiceVotesCount)
                    .sum();

            for (Choix choice : choices) {
                HBox choiceBox = new HBox(10);
                Button voteBtn = new Button(choice.getChoix());
                voteBtn.setOnAction(e -> handleChoiceVote(choice));

                Label voteInfo = new Label(String.format("%d votes (%.1f%%)",
                        choice.getChoiceVotesCount(),
                        totalVotes > 0 ? (choice.getChoiceVotesCount() * 100.0f) / totalVotes : 0));

                choiceBox.getChildren().addAll(voteBtn, voteInfo);
                choicesBox.getChildren().add(choiceBox);
            }

            Label tags = new Label("Tags: " + survey.getSurveyTags());
            tags.getStyleClass().add("post-tags");

            Label dateLabel = new Label(formatDateTime(post.getDateCreation()));

            postBox.getChildren().addAll(question, choicesBox, tags, dateLabel);
        }

        HBox controls = new HBox(10);
        Button viewComments = new Button("View Comments");
        Button editPost = new Button("Edit");

        viewComments.setOnAction(e -> showComments(post));
        editPost.setOnAction(e -> editPost(post));

        HBox buttonsBox = new HBox(5);
        Button deleteBtn = new Button("Delete");
        deleteBtn.getStyleClass().add("delete-button");
        deleteBtn.setOnAction(e -> {
            currentPost = post;
            handleDeletePost();
        });

        buttonsBox.getChildren().addAll(deleteBtn, editPost);

        controls.getChildren().addAll(viewComments, buttonsBox);
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
        TextField field = tags.equals(surveyTagsField.getText()) ? surveyTagsField : announcementTagsField;

        if (tags.length() > 100) {
            showError(field, errorLabel, "Les tags ne doivent pas dépasser 100 caractères");
            return;
        }

        if (!tags.isEmpty()) {

            if (!tags.matches("^[a-zA-Z0-9,\\s]+$")) {
                showError(field, errorLabel, "Les tags ne doivent contenir que des lettres, chiffres et virgules");
                return;
            }

            String[] tagArray = tags.split(",");
            Set<String> uniqueTags = new HashSet<>();

            for (String tag : tagArray) {
                String trimmedTag = tag.trim();
                if (!trimmedTag.isEmpty()) {
                    if (!uniqueTags.add(trimmedTag)) {
                        showError(field, errorLabel, "Tag '" + trimmedTag + "' est en double");
                        return;
                    }
                }
            }

            for (String tag : tagArray) {
                String trimmedTag = tag.trim();
                if (trimmedTag.length() > 20) {
                    showError(field, errorLabel, "Chaque tag ne doit pas dépasser 20 caractères");
                    return;
                }
            }
        }

        hideError(field, errorLabel);
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
            field.getStyleClass().removeAll("valid-field", "text-field-error");
            field.getStyleClass().add("text-field-error");
        }
        if (errorLabel != null) {
            errorLabel.setText(message);
            errorLabel.setVisible(true);
        }
    }

    private void hideError(TextInputControl field, Label errorLabel) {
        if (field != null) {
            field.getStyleClass().removeAll("valid-field", "text-field-error");
            field.getStyleClass().add("valid-field");
        }
        if (errorLabel != null) {
            errorLabel.setVisible(false);
        }
    }

    private void showComments(Post post) {
        currentPost = post;

        leftTabPane.getSelectionModel().select(1);

        showPostDetails(post);

        loadComments();
    }

    private void loadComments() {

        commentsVBox.getChildren().clear();

        List<Comment> comments = postService.rechercher().stream()
                .filter(p -> p instanceof Comment)
                .map(p -> (Comment) p)
                .filter(c -> c.getParentId() == currentPost.getPostId())
                .toList();

        for (Comment comment : comments) {
            displayComment(comment);
        }
    }

    private void displayComment(Comment comment) {
        HBox commentBox = new HBox(10);
        commentBox.getStyleClass().add("comment-box");

        VBox contentBox = new VBox(5);
        Label contentLabel = new Label(comment.getCommentContent());
        Label dateLabel = new Label(comment.getDateCreation().toString());
        dateLabel.getStyleClass().add("comment-date");
        contentBox.getChildren().addAll(contentLabel, dateLabel);

        HBox buttonsBox = new HBox(5);
        Button editBtn = new Button("Editer");
        Button deleteBtn = new Button("Supprimer");

        editBtn.getStyleClass().add("edit-button");
        deleteBtn.getStyleClass().add("delete-button");

        editBtn.setOnAction(e -> handleEditComment(comment));
        deleteBtn.setOnAction(e -> handleDeleteComment(comment));

        buttonsBox.getChildren().addAll(editBtn, deleteBtn);
        commentBox.getChildren().addAll(contentBox, buttonsBox);

        commentsVBox.getChildren().add(commentBox);
    }

    private void handleEditComment(Comment comment) {
        TextInputDialog dialog = new TextInputDialog(comment.getCommentContent());
        dialog.setTitle("Edit Comment");
        dialog.setHeaderText(null);
        dialog.setContentText("Enter new comment:");

        dialog.showAndWait().ifPresent(newContent -> {
            comment.setCommentContent(newContent);
            postService.modifier(comment);
            loadComments();
        });
    }

    private void handleDeleteComment(Comment comment) {
        Alert confirm = new Alert(AlertType.CONFIRMATION);
        confirm.setTitle("Delete Comment");
        confirm.setHeaderText("Are you sure you want to delete this comment?");
        confirm.setContentText("This action cannot be undone.");

        if (confirm.showAndWait().get() == ButtonType.OK) {
            postService.supprimer(comment);
            loadComments();
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
        currentPost = post;

        if (post instanceof Announcement announcement) {
            editAnnouncementFields.setVisible(true);
            editSurveyFields.setVisible(false);

            editAnnouncementTitleField.setText(announcement.getAnnouncementTitle());
            editAnnouncementContentField.setText(announcement.getAnnouncementContent());
            editAnnouncementTagsField.setText(announcement.getAnnouncementTags());
            editAnnouncementFileField.setText(announcement.getCheminFichier());
            editAnnouncementStatusComboBox.setValue(announcement.getStatus());
            editAnnouncementUserField.setText(forumService.getEmailById(post.getIdUser()));

        } else if (post instanceof Survey survey) {
            editAnnouncementFields.setVisible(false);
            editSurveyFields.setVisible(true);

            editSurveyQuestionField.setText(survey.getSurveyQuestion());
            editSurveyTagsField.setText(survey.getSurveyTags());
            editSurveyFileField.setText(survey.getCheminFichier());
            editSurveyStatusComboBox.setValue(survey.getStatus());
            editSurveyAuthorField.setText(forumService.getEmailById(post.getIdUser()));

            editChoicesListView.getItems().clear();
            List<Choix> choices = choixService.rechercher().stream()
                    .filter(c -> c.getPostId() == survey.getPostId())
                    .toList();

            for (Choix choice : choices) {
                editChoicesListView.getItems().add(choice.getChoix());
            }

            editSurveyUsersListView.getItems().clear();
            if (survey.getSurveyUserList() != null && !survey.getSurveyUserList().isEmpty()) {
                editSurveyUsersListView.getItems().addAll(
                        survey.getSurveyUserList().split(",")
                );
            }
        }
    }

    private void handleEditButton(Post post) {
        currentPost = post;

        TabPane rightTabPane = (TabPane) postTypeComboBox.getParent().getParent().getParent();
        rightTabPane.getSelectionModel().select(2);
        editPost(post);
    }

    @FXML
    private void handleAddChoice() {
        String choice = choiceField.getText().trim();
        if (!choice.isEmpty() && !choicesListView.getItems().contains(choice)) {
            addRemoveButton(choicesListView, choice);
            choiceField.clear();
            addChoiceButton.setDisable(true);
        }
    }

    private void addRemoveButton(ListView<String> listView, String itemToRemove) {
        listView.setCellFactory(lv -> new ListCell<String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setGraphic(null);
                } else {
                    HBox cell = new HBox(10);
                    Label label = new Label(item);
                    Button deleteBtn = new Button("Supprimer");
                    deleteBtn.getStyleClass().add("delete-button");
                    deleteBtn.setOnAction(e -> listView.getItems().remove(item));
                    cell.getChildren().addAll(label, deleteBtn);
                    setGraphic(cell);
                }
            }
        });

        listView.getItems().add(itemToRemove);

        listView.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.DELETE) {
                String selectedItem = listView.getSelectionModel().getSelectedItem();
                if (selectedItem != null) {
                    listView.getItems().remove(selectedItem);
                }
            }
        });
    }

    @FXML
    private void handleAddSurveyUser() {
        String email = surveyUserField.getText().trim();
        if (!email.isEmpty() && !surveyUsersListView.getItems().contains(email)) {
            addRemoveButton(surveyUsersListView, email);
            surveyUserField.clear();
            addSurveyUserButton.setDisable(true);
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
                0,
                currentPost.getForumId(),
                1,
                0,
                LocalDateTime.now(),
                LocalDateTime.now(),
                "",
                "active",
                0,
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

        if (selectedType == null) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Post Type Required");
            alert.setContentText("Please select a post type");
            alert.showAndWait();
            return;
        }

        if (selectedForum == null) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Forum Required");
            alert.setContentText("Please select a forum first");
            alert.showAndWait();
            return;
        }

        if ("Announcement".equals(selectedType)) {

            String title = announcementTitleField.getText().trim();
            String content = announcementContentField.getText().trim();
            String tags = announcementTagsField.getText().trim();

            StringBuilder errors = new StringBuilder();
            if (title.isEmpty()) errors.append("- Title cannot be empty\n");
            if (title.length() > 50) errors.append("- Title must not exceed 50 characters\n");
            if (content.isEmpty()) errors.append("- Content cannot be empty\n");
            if (content.length() > 500) errors.append("- Content must not exceed 500 characters\n");

            if (!errors.isEmpty()) {
                showAlert("Validation Error", errors.toString(), AlertType.ERROR);
                return;
            }

            String userEmail = announcementUserField.getText().trim();

            if (userEmail.isEmpty()) {
                showAlert("Validation Error", "User email cannot be empty", AlertType.ERROR);
                return;
            }
            int userId = forumService.getUserByEmail(userEmail);
            if (userId == -1) {
                showAlert("Validation Error", "User not found", AlertType.ERROR);
                return;
            }

            try {
                Announcement newAnnouncement = new Announcement(
                        0,
                        selectedForum.getForumId(),
                        userId,
                        0,
                        LocalDateTime.now(),
                        LocalDateTime.now(),
                        announcementFileField.getText(),
                        announcementStatusComboBox.getValue(),
                        0,
                        title,
                        content,
                        tags
                );
                postService.ajouter(newAnnouncement);
                showAlert("Success", "Announcement posted successfully", AlertType.INFORMATION);
                refreshPosts();
                clearFields();
            } catch (Exception e) {
                showAlert("Error", "Failed to post announcement: " + e.getMessage(), AlertType.ERROR);
            }

        } else if ("Survey".equals(selectedType)) {

            String question = surveyQuestionField.getText().trim();
            String tags = surveyTagsField.getText().trim();
            List<String> choices = new ArrayList<>(choicesListView.getItems());
            List<String> users = new ArrayList<>(surveyUsersListView.getItems());

            StringBuilder errors = new StringBuilder();
            if (question.isEmpty()) errors.append("- Question cannot be empty\n");
            if (question.length() > 200) errors.append("- Question must not exceed 200 characters\n");
            if (choices.size() < 2) errors.append("- Survey must have at least two choices\n");

            if (!errors.isEmpty()) {
                showAlert("Validation Error", errors.toString(), AlertType.ERROR);
                return;
            }

            String userEmail = surveyAuthorField.getText().trim();

            if (userEmail.isEmpty()) {
                showAlert("Validation Error", "User email cannot be empty", AlertType.ERROR);
                return;
            }
            int userId = forumService.getUserByEmail(userEmail);
            if (userId == -1) {
                showAlert("Validation Error", "User not found", AlertType.ERROR);
                return;
            }

            try {
                Survey newSurvey = new Survey(
                        0,
                        selectedForum.getForumId(),
                        userId,
                        0,
                        LocalDateTime.now(),
                        LocalDateTime.now(),
                        surveyFileField.getText(),
                        surveyStatusComboBox.getValue(),
                        0,
                        question,
                        tags,
                        String.join(",", users)
                );
                postService.ajouter(newSurvey);

                for (String choiceText : choices) {
                    Choix choice = new Choix(0, newSurvey.getPostId(), choiceText, 0, 0);
                    choixService.ajouter(choice);
                }

                showAlert("Success", "Survey posted successfully", AlertType.INFORMATION);
                refreshPosts();
                clearFields();
            } catch (Exception e) {
                showAlert("Error", "Failed to post survey: " + e.getMessage(), AlertType.ERROR);
            }
        }
    }

    private void clearFields() {

        announcementTitleField.clear();
        announcementContentField.clear();
        announcementTagsField.clear();
        announcementFileField.clear();
        announcementStatusComboBox.setValue("active");

        surveyQuestionField.clear();
        surveyTagsField.clear();
        surveyFileField.clear();
        surveyStatusComboBox.setValue("active");
        surveyUserField.clear();
        surveyUsersListView.getItems().clear();
        choicesListView.getItems().clear();
        choiceField.clear();
        addChoiceButton.setDisable(true);

        titleErrorLabel.setVisible(false);
        contentErrorLabel.setVisible(false);
        tagsErrorLabel.setVisible(false);
        questionErrorLabel.setVisible(false);
        surveyTagsErrorLabel.setVisible(false);
        commentErrorLabel.setVisible(false);
        choiceErrorLabel.setVisible(false);
        surveyUserErrorLabel.setVisible(false);
        announcementFileErrorLabel.setVisible(false);
        surveyFileErrorLabel.setVisible(false);

        postTypeComboBox.getSelectionModel().clearSelection();
        announcementFields.setVisible(false);
        surveyFields.setVisible(false);

        forumComboBox.getSelectionModel().clearSelection();
        forumDescription.clear();
        forumPrivacyLabel.setText("");
        forumDateLabel.setText("");
        memberCountLabel.setText("");
        membersListView.getItems().clear();
        membersSection.setVisible(false);

        newCommentField.clear();
        commentsVBox.getChildren().clear();

        postTitleLabel.setText("");
        postContentText.setText("");
        postTagsLabel.setText("");
        surveyChoicesBox.getChildren().clear();
        surveyChoicesBox.setVisible(false);
        viewVotersButton.setVisible(false);

        clearEditFields();

        announcementUserField.clear();
        surveyAuthorField.clear();
        editAnnouncementUserField.clear();
        editSurveyAuthorField.clear();

        announcementUserErrorLabel.setVisible(false);
        surveyAuthorErrorLabel.setVisible(false);
        editAnnouncementUserErrorLabel.setVisible(false);
        editSurveyAuthorErrorLabel.setVisible(false);
    }

    @FXML
    private void handleBrowseFile(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select File");
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {

            Button button = (Button) event.getSource();

            HBox hbox = (HBox) button.getParent();

            TextField fileField = (TextField) hbox.getChildren().get(0);
            fileField.setText(file.getAbsolutePath());
        }
    }

    private void validateSurveyUser(String email) {
        if (email.isEmpty()) {
            addSurveyUserButton.setDisable(true);
            showError(surveyUserField, surveyUserErrorLabel, "L'email ne peut pas être vide");
        } else if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            addSurveyUserButton.setDisable(true);
            showError(surveyUserField, surveyUserErrorLabel, "Format d'email invalide");
        } else if (forumService.getUserByEmail(email) == -1) {
            addSurveyUserButton.setDisable(true);
            showError(surveyUserField, surveyUserErrorLabel, "Cet utilisateur n'existe pas");
        } else if (surveyUsersListView.getItems().contains(email)) {
            addSurveyUserButton.setDisable(true);
            showError(surveyUserField, surveyUserErrorLabel, "Cet utilisateur est déjà dans la liste");
        } else {
            hideError(surveyUserField, surveyUserErrorLabel);
            addSurveyUserButton.setDisable(false);
        }
    }

    @FXML
    private void handleDeletePost() {
        if (currentPost == null) return;

        Alert confirm = new Alert(AlertType.CONFIRMATION);
        confirm.setTitle("Delete Post");
        confirm.setHeaderText("Are you sure you want to delete this post?");
        confirm.setContentText("This action cannot be undone.");

        if (confirm.showAndWait().get() == ButtonType.OK) {
            postService.supprimer(currentPost);
            refreshPosts();
        }
    }

    private String formatDateTime(LocalDateTime dateTime) {
        return dateTime.toString().replace("T", " ");
    }

    @FXML
    private void handleAddEditChoice() {
        String choice = editChoiceField.getText().trim();
        if (!choice.isEmpty() && !editChoicesListView.getItems().contains(choice)) {
            addRemoveButton(editChoicesListView, choice);
            editChoiceField.clear();
            editAddChoiceButton.setDisable(true);
        }
    }

    @FXML
    private void handleAddEditSurveyUser() {
        String email = editSurveyUserField.getText().trim();
        if (!email.isEmpty() && !editSurveyUsersListView.getItems().contains(email)) {
            addRemoveButton(editSurveyUsersListView, email);
            editSurveyUserField.clear();
            editAddSurveyUserButton.setDisable(true);
        }
    }

    @FXML
    private void handleUpdatePost(ActionEvent actionEvent) {
        if (currentPost == null) return;

        if (currentPost instanceof Announcement announcement) {
            String title = editAnnouncementTitleField.getText().trim();
            String content = editAnnouncementContentField.getText().trim();
            String tags = editAnnouncementTagsField.getText().trim();
            String filePath = editAnnouncementFileField.getText().trim();
            String status = editAnnouncementStatusComboBox.getValue();
            String userEmail = editAnnouncementUserField.getText().trim();

            StringBuilder errors = new StringBuilder();
            if (title.isEmpty()) errors.append("- Title cannot be empty\n");
            if (title.length() > 50) errors.append("- Title must not exceed 50 characters\n");
            if (content.isEmpty()) errors.append("- Content cannot be empty\n");
            if (content.length() > 500) errors.append("- Content must not exceed 500 characters\n");
            if (userEmail.isEmpty()) errors.append("- User email cannot be empty\n");
            if (!userEmail.matches("^[A-Za-z0-9+_.-]+@(.+)$")) errors.append("- Invalid email format\n");

            int userId = forumService.getUserByEmail(userEmail);
            if (userId == -1) errors.append("- User not found\n");

            if (!errors.isEmpty()) {
                showAlert("Validation Error", errors.toString(), AlertType.ERROR);
                return;
            }

            try {
                announcement.setAnnouncementTitle(title);
                announcement.setAnnouncementContent(content);
                announcement.setAnnouncementTags(tags);
                announcement.setCheminFichier(filePath);
                announcement.setStatus(status);
                announcement.setDateModification(LocalDateTime.now());
                announcement.setIdUser(userId);

                postService.modifier(announcement);
                showAlert("Success", "Announcement updated successfully", AlertType.INFORMATION);
                refreshPosts();
                clearEditFields();
                leftTabPane.getSelectionModel().select(0);
            } catch (Exception e) {
                showAlert("Error", "Failed to update announcement: " + e.getMessage(), AlertType.ERROR);
            }

        } else if (currentPost instanceof Survey survey) {
            String question = editSurveyQuestionField.getText().trim();
            String tags = editSurveyTagsField.getText().trim();
            String filePath = editSurveyFileField.getText().trim();
            String status = editSurveyStatusComboBox.getValue();
            String userEmail = editSurveyAuthorField.getText().trim();
            List<String> choices = new ArrayList<>(editChoicesListView.getItems());
            List<String> users = new ArrayList<>(editSurveyUsersListView.getItems());

            StringBuilder errors = new StringBuilder();
            if (question.isEmpty()) errors.append("- Question cannot be empty\n");
            if (question.length() > 200) errors.append("- Question must not exceed 200 characters\n");
            if (choices.size() < 2) errors.append("- Survey must have at least two choices\n");
            if (userEmail.isEmpty()) errors.append("- User email cannot be empty\n");
            if (!userEmail.matches("^[A-Za-z0-9+_.-]+@(.+)$")) errors.append("- Invalid email format\n");

            int userId = forumService.getUserByEmail(userEmail);
            if (userId == -1) errors.append("- User not found\n");

            if (!errors.isEmpty()) {
                showAlert("Validation Error", errors.toString(), AlertType.ERROR);
                return;
            }

            try {
                survey.setSurveyQuestion(question);
                survey.setSurveyTags(tags);
                survey.setCheminFichier(filePath);
                survey.setStatus(status);
                survey.setDateModification(LocalDateTime.now());
                survey.setIdUser(userId);
                survey.setSurveyUserList(String.join(",", users));

                List<Choix> existingChoices = choixService.rechercher().stream()
                        .filter(c -> c.getPostId() == survey.getPostId())
                        .toList();

                for (Choix existingChoice : existingChoices) {
                    if (!choices.contains(existingChoice.getChoix())) {
                        choixService.supprimer(existingChoice);
                    }
                }

                for (String choiceText : choices) {
                    if (existingChoices.stream().noneMatch(c -> c.getChoix().equals(choiceText))) {
                        Choix newChoice = new Choix(0, survey.getPostId(), choiceText, 0, 0);
                        choixService.ajouter(newChoice);
                    }
                }

                postService.modifier(survey);
                showAlert("Success", "Survey updated successfully", AlertType.INFORMATION);
                refreshPosts();
                clearEditFields();
                leftTabPane.getSelectionModel().select(0);
            } catch (Exception e) {
                showAlert("Error", "Failed to update survey: " + e.getMessage(), AlertType.ERROR);
            }
        }
    }
    private void clearEditFields() {
        editAnnouncementTitleField.clear();
        editAnnouncementContentField.clear();
        editAnnouncementTagsField.clear();
        editAnnouncementFileField.clear();
        editAnnouncementStatusComboBox.setValue("active");

        editSurveyQuestionField.clear();
        editSurveyTagsField.clear();
        editChoiceField.clear();
        editChoicesListView.getItems().clear();
        editSurveyFileField.clear();
        editSurveyStatusComboBox.setValue("active");
        editSurveyUserField.clear();
        editSurveyUsersListView.getItems().clear();

        editAnnouncementFields.setVisible(false);
        editSurveyFields.setVisible(false);
    }

    @FXML
    private void handleBrowseAnnouncementFile(ActionEvent actionEvent) {
        handleBrowseFile(actionEvent);
    }

    @FXML
    private void handleBrowseSurveyFile(ActionEvent actionEvent) {
        handleBrowseFile(actionEvent);
    }

    private void validateEditChoice(String choice) {
        if (choice.isEmpty()) {
            editChoiceField.getStyleClass().remove("valid-field");
            editChoiceField.getStyleClass().add("text-field-error");
            editAddChoiceButton.setDisable(true);
            showError(editChoiceField, editChoiceErrorLabel, "Le choix ne peut pas être vide");
        } else if (choice.length() > 50) {
            editChoiceField.getStyleClass().remove("valid-field");
            editChoiceField.getStyleClass().add("text-field-error");
            editAddChoiceButton.setDisable(true);
            showError(editChoiceField, editChoiceErrorLabel, "Le choix ne doit pas dépasser 50 caractères");
        } else if (editChoicesListView.getItems().contains(choice)) {
            editChoiceField.getStyleClass().remove("valid-field");
            editChoiceField.getStyleClass().add("text-field-error");
            editAddChoiceButton.setDisable(true);
            showError(editChoiceField, editChoiceErrorLabel, "Ce choix existe déjà");
        } else {
            editChoiceField.getStyleClass().remove("text-field-error");
            editChoiceField.getStyleClass().add("valid-field");
            hideError(editChoiceField, editChoiceErrorLabel);
            editAddChoiceButton.setDisable(false);
        }
    }

    private void validateEditSurveyUser(String email) {
        if (email.isEmpty()) {
            editAddSurveyUserButton.setDisable(true);
            showError(editSurveyUserField, editSurveyUserErrorLabel, "L'email ne peut pas être vide");
        } else if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            editAddSurveyUserButton.setDisable(true);
            showError(editSurveyUserField, editSurveyUserErrorLabel, "Format d'email invalide");
        } else if (forumService.getUserByEmail(email) == -1) {
            editAddSurveyUserButton.setDisable(true);
            showError(editSurveyUserField, editSurveyUserErrorLabel, "Cet utilisateur n'existe pas");
        } else if (editSurveyUsersListView.getItems().contains(email)) {
            editAddSurveyUserButton.setDisable(true);
            showError(editSurveyUserField, editSurveyUserErrorLabel, "Cet utilisateur est déjà dans la liste");
        } else {
            hideError(editSurveyUserField, editSurveyUserErrorLabel);
            editAddSurveyUserButton.setDisable(false);
        }
    }

    private void validateEditTitle(String title) {
        if (title.isEmpty()) {
            showError(editAnnouncementTitleField, editTitleErrorLabel, "Le titre ne peut pas être vide");
        } else if (title.length() > 50) {
            showError(editAnnouncementTitleField, editTitleErrorLabel, "Le titre ne doit pas dépasser 50 caractères");
        } else {
            hideError(editAnnouncementTitleField, editTitleErrorLabel);
        }
    }

    private void validateEditContent(String content) {
        if (content.isEmpty()) {
            showError(editAnnouncementContentField, editContentErrorLabel, "Le contenu ne peut pas être vide");
        } else if (content.length() > 500) {
            showError(editAnnouncementContentField, editContentErrorLabel, "Le contenu ne doit pas dépasser 500 caractères");
        } else {
            hideError(editAnnouncementContentField, editContentErrorLabel);
        }
    }

    private void validateEditQuestion(String question) {
        if (question.isEmpty()) {
            showError(editSurveyQuestionField, editSurveyQuestionErrorLabel, "La question ne peut pas être vide");
        } else if (question.length() > 200) {
            showError(editSurveyQuestionField, editSurveyQuestionErrorLabel, "La question ne doit pas dépasser 200 caractères");
        } else {
            hideError(editSurveyQuestionField, editSurveyQuestionErrorLabel);
        }
    }

    private void handleVote(Post post, boolean isUpvote) {
        if (isUpvote) {
            post.setVotes(post.getVotes() + 1);
        } else {
            post.setVotes(post.getVotes() - 1);
        }
        postService.modifier(post);
        refreshPosts();

        if (leftTabPane.getSelectionModel().getSelectedIndex() == 1) {
            showPostDetails(post);
        }
    }

    private void showPostDetails(Post post) {
        postDetailsBox.getChildren().clear();

        Label titleLabel = new Label();
        titleLabel.getStyleClass().add("post-title");

        Text contentText = new Text();
        contentText.getStyleClass().add("post-content");

        Label tagsLabel = new Label();
        tagsLabel.getStyleClass().add("post-tags");

        HBox votingBox = new HBox(5);
        Button upVoteBtn = new Button("↑");
        Button downVoteBtn = new Button("↓");
        Label voteCount = new Label(String.valueOf(post.getVotes()));

        upVoteBtn.setOnAction(e -> handleVote(post, true));
        downVoteBtn.setOnAction(e -> handleVote(post, false));

        votingBox.getChildren().addAll(upVoteBtn, voteCount, downVoteBtn);
        postDetailsBox.getChildren().add(votingBox);

        votingBox.getStyleClass().add("vote-box");
        upVoteBtn.getStyleClass().add("vote-button");
        downVoteBtn.getStyleClass().add("vote-button");
        voteCount.getStyleClass().add("vote-count");

        if (post instanceof Announcement announcement) {
            titleLabel.setText(announcement.getAnnouncementTitle());
            contentText.setText(announcement.getAnnouncementContent());
            tagsLabel.setText("Tags: " + announcement.getAnnouncementTags());

        } else if (post instanceof Survey survey) {
            titleLabel.setText(survey.getSurveyQuestion());
            tagsLabel.setText("Tags: " + survey.getSurveyTags());

            VBox choicesBox = new VBox(5);
            choicesBox.getStyleClass().add("survey-choices");

            List<Choix> choices = choixService.rechercher().stream()
                    .filter(c -> c.getPostId() == survey.getPostId())
                    .toList();

            int totalVotes = choices.stream()
                    .mapToInt(Choix::getChoiceVotesCount)
                    .sum();

            for (Choix choice : choices) {
                HBox choiceBox = new HBox(10);
                Button voteBtn = new Button(choice.getChoix());
                voteBtn.setOnAction(e -> handleChoiceVote(choice));

                Label voteInfo = new Label(String.format("%d votes (%.1f%%)",
                        choice.getChoiceVotesCount(),
                        totalVotes > 0 ? (choice.getChoiceVotesCount() * 100.0f) / totalVotes : 0));

                choiceBox.getChildren().addAll(voteBtn, voteInfo);
                choicesBox.getChildren().add(choiceBox);
            }

            postDetailsBox.getChildren().add(choicesBox);

            Button viewVotersBtn = new Button("View Voters");
            viewVotersBtn.setOnAction(e -> handleViewVoters());
            postDetailsBox.getChildren().add(viewVotersBtn);
        }

        postDetailsBox.getChildren().add(0, titleLabel);
        postDetailsBox.getChildren().add(1, contentText);
        postDetailsBox.getChildren().add(2, tagsLabel);

        if (post.getCheminFichier() != null && !post.getCheminFichier().isEmpty()) {
            try {
                ImageView imageView = new ImageView(new Image(new File(post.getCheminFichier()).toURI().toString()));
                imageView.setFitWidth(200);
                imageView.setPreserveRatio(true);
                postDetailsBox.getChildren().add(3, imageView);
            } catch (Exception e) {
                System.out.println("Error loading image: " + e.getMessage());
            }
        }

        HBox buttonsBox = new HBox(5);
        Button editBtn = new Button("Editer");
        Button deleteBtn = new Button("Supprimer");

        editBtn.getStyleClass().add("edit-button");
        deleteBtn.getStyleClass().add("delete-button");

        editBtn.setOnAction(e -> handleEditButton(post));
        deleteBtn.setOnAction(e -> handleDeletePost(post));

        buttonsBox.getChildren().addAll(editBtn, deleteBtn);
        postDetailsBox.getChildren().add(buttonsBox);
    }

    private void showAlert(String title, String content, AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void validateUserEmail(TextField field, Label errorLabel, String email) {
        if (email.isEmpty()) {
            field.getStyleClass().remove("valid-field");
            field.getStyleClass().add("text-field-error");
            showError(field, errorLabel, "L'email ne peut pas être vide");
        } else if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            field.getStyleClass().remove("valid-field");
            field.getStyleClass().add("text-field-error");
            showError(field, errorLabel, "Format d'email invalide");
        } else if (forumService.getUserByEmail(email) == -1) {
            field.getStyleClass().remove("valid-field");
            field.getStyleClass().add("text-field-error");
            showError(field, errorLabel, "Cet utilisateur n'existe pas");
        } else {
            field.getStyleClass().remove("text-field-error");
            field.getStyleClass().add("valid-field");
            hideError(field, errorLabel);
        }
    }

    @FXML
    private void handleAddPostButton() {
        TabPane rightTabPane = (TabPane) postTypeComboBox.getParent().getParent().getParent();
        rightTabPane.getSelectionModel().select(1);
    }

    private void handleDeletePost(Post post) {
        if (currentPost == null) return;

        Alert confirm = new Alert(AlertType.CONFIRMATION);
        confirm.setTitle("Delete Post");
        confirm.setHeaderText("Are you sure you want to delete this post?");
        confirm.setContentText("This action cannot be undone.");

        if (confirm.showAndWait().get() == ButtonType.OK) {
            postService.supprimer(currentPost);
            refreshPosts();
        }
    }

    private void handleChoiceVote(Choix choice) {
        choice.setChoiceVotesCount(choice.getChoiceVotesCount() + 1);
        choixService.modifier(choice);
        refreshPosts();

        if (leftTabPane.getSelectionModel().getSelectedIndex() == 1) {
            showPostDetails(currentPost);
        }
    }

    private void validateEditTags(String tags, Label errorLabel) {
        TextField field = tags.equals(editSurveyTagsField.getText()) ? editSurveyTagsField : editAnnouncementTagsField;

        if (tags.length() > 100) {
            showError(field, errorLabel, "Les tags ne doivent pas dépasser 100 caractères");
            return;
        }

        if (!tags.isEmpty()) {
            if (!tags.matches("^[a-zA-Z0-9,\\s]+$")) {
                showError(field, errorLabel, "Les tags ne doivent contenir que des lettres, chiffres et virgules");
                return;
            }

            String[] tagArray = tags.split(",");
            Set<String> uniqueTags = new HashSet<>();

            for (String tag : tagArray) {
                String trimmedTag = tag.trim();
                if (!trimmedTag.isEmpty()) {
                    if (!uniqueTags.add(trimmedTag)) {
                        showError(field, errorLabel, "Tag '" + trimmedTag + "' est en double");
                        return;
                    }
                }
            }

            for (String tag : tagArray) {
                String trimmedTag = tag.trim();
                if (trimmedTag.length() > 20) {
                    showError(field, errorLabel, "Chaque tag ne doit pas dépasser 20 caractères");
                    return;
                }
            }
        }

        hideError(field, errorLabel);
    }
}