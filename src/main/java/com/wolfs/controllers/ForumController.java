package com.wolfs.controllers;

import com.wolfs.models.Forum;
import com.wolfs.services.ForumService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.time.LocalDateTime;
import java.util.List;

public class ForumController {

    // Fields for Adding a Forum
    @FXML
    private TextField nameField;
    @FXML
    private TextField createdByField;
    @FXML
    private TextField descriptionField;
    @FXML
    private CheckBox isPrivateCheckBox;
    @FXML
    private Button addForumButton;

    // Fields for Modifying a Forum
    @FXML
    private TextField forumIdField;
    @FXML
    private TextField newNameField;
    @FXML
    private TextField newDescriptionField;
    @FXML
    private CheckBox modifyIsPrivateCheckBox;
    @FXML
    private Button modifyForumButton;

    // Fields for Deleting a Forum
    @FXML
    private TextField deleteForumIdField;
    @FXML
    private Button deleteForumButton;

    // Fields for Displaying Forums
    @FXML
    private ListView<String> forumListView;
    @FXML
    private Button refreshForumsButton;

    private ForumService forumService = new ForumService();

    @FXML
    private ListView<String> membersListView;
    @FXML
    private TextField memberEmailField;
    @FXML
    private Button addMemberButton;

    @FXML
    private void handlePrivateCheckbox() {
        boolean isPrivate = isPrivateCheckBox.isSelected();
        membersListView.setDisable(!isPrivate);
        memberEmailField.setDisable(!isPrivate);
        addMemberButton.setDisable(!isPrivate);
    }

    // Add member to the list and update the forum object
    @FXML
    private void addMember() {
        String email = memberEmailField.getText().trim();
        if (!email.isEmpty() && !membersListView.getItems().contains(email)) {
            membersListView.getItems().add(email);
            memberEmailField.clear();

            // Update the forum's number of members in the current forum object
            Forum forum = new Forum();
            forum.setNbrMembers(membersListView.getItems().size());
        }
    }

    // Create a new forum and add it
    private void addForum() {
        String name = nameField.getText();
        String createdBy = createdByField.getText();
        String description = descriptionField.getText();
        boolean isPrivate = isPrivateCheckBox.isSelected();

        // Convert members list to comma-separated string if private
        String listMembers = isPrivate ? String.join(",", membersListView.getItems()) : "";

        // Create the Forum object
        Forum forum = new Forum();
        forum.setName(name);
        forum.setDescription(description);
        forum.setDateCreation(LocalDateTime.now());
        forum.setPrivate(isPrivate);
        forum.setListMembers(listMembers);

        // Set the number of members
        forum.setNbrMembers(membersListView.getItems().size());

        forumService.ajouter(forum);
    }

    // Initialize buttons actions
    @FXML
    private void initialize() {
        addForumButton.setOnAction(event -> addForum());
        modifyForumButton.setOnAction(event -> modifyForum());
        deleteForumButton.setOnAction(event -> deleteForum());
        refreshForumsButton.setOnAction(event -> showForums());
    }

    // Modify the forum with new details
    private void modifyForum() {
        String forumIdText = forumIdField.getText();
        String newName = newNameField.getText();
        String newDescription = newDescriptionField.getText();
        boolean isPrivate = modifyIsPrivateCheckBox.isSelected();

        int forumId;
        try {
            forumId = Integer.parseInt(forumIdText);
        } catch (NumberFormatException e) {
            System.err.println("Forum ID must be an integer.");
            return;
        }

        // Create the Forum object for modification
        Forum forum = new Forum();
        forum.setForumId(forumId);
        forum.setName(newName);
        forum.setDescription(newDescription);
        // For simplicity, updating the creation date to now.
        forum.setDateCreation(LocalDateTime.now());
        forum.setPrivate(isPrivate);

        forumService.modifier(forum);
    }

    // Delete a forum by ID
    private void deleteForum() {
        String forumIdText = deleteForumIdField.getText();

        int forumId;
        try {
            forumId = Integer.parseInt(forumIdText);
        } catch (NumberFormatException e) {
            System.err.println("Forum ID must be an integer.");
            return;
        }

        // Create the Forum object for deletion
        Forum forum = new Forum();
        forum.setForumId(forumId);
        forumService.supprimer(forum);
    }

    // Show forums in the ListView
    private void showForums() {
        List<Forum> forums = forumService.rechercher();
        forumListView.getItems().clear();
        for (Forum forum : forums) {
            String forumInfo = "Forum ID: " + forum.getForumId() +
                    ", Name: " + forum.getName() +
                    ", Created By: " + forum.getCreatedBy() +
                    ", Post Count: " + forum.getPostCount() +
                    ", Members: " + forum.getNbrMembers() +
                    ", Description: " + forum.getDescription() +
                    ", Date: " + forum.getDateCreation() +
                    ", Private: " + forum.isPrivate();
            forumListView.getItems().add(forumInfo);
        }
    }
}
