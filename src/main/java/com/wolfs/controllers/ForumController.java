package com.wolfs.controllers;

import com.wolfs.models.Forum;
import com.wolfs.services.ForumService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.control.TableCell;
import javafx.util.Callback;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.IntegerStringConverter;
import javafx.util.converter.BooleanStringConverter;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.Label;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Control;
import javafx.scene.text.Text;
import javafx.beans.property.SimpleStringProperty;

import java.time.LocalDateTime;

public class ForumController {

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

    @FXML
    private ListView<String> membersListView;
    @FXML
    private TextField memberEmailField;
    @FXML
    private Button addMemberButton;

    @FXML
    private TableView<Forum> forumTableView;
    @FXML
    private TableColumn<Forum, Integer> forumIdColumn;
    @FXML
    private TableColumn<Forum, String> nameColumn;
    @FXML
    private TableColumn<Forum, String> createdByColumn;
    @FXML
    private TableColumn<Forum, Integer> postCountColumn;
    @FXML
    private TableColumn<Forum, String> membersColumn;
    @FXML
    private TableColumn<Forum, String> descriptionColumn;
    @FXML
    private TableColumn<Forum, LocalDateTime> dateColumn;
    @FXML
    private TableColumn<Forum, Boolean> privateColumn;

    private ObservableList<Forum> forumList = FXCollections.observableArrayList();

    @FXML
    private Label nameErrorLabel;
    @FXML
    private Label emailErrorLabel;
    @FXML
    private Label descriptionErrorLabel;
    @FXML
    private Label memberEmailErrorLabel;

    private boolean isNameValid = false;
    private boolean isEmailValid = false;
    private boolean isDescriptionValid = false;
    private boolean isMemberEmailValid = false;

    private ForumService forumService = new ForumService();

    @FXML
    private void handlePrivateCheckbox() {
        boolean isPrivate = isPrivateCheckBox.isSelected();
        membersListView.setDisable(!isPrivate);
        memberEmailField.setDisable(!isPrivate);
        
        if (!isPrivate) {
            membersListView.getItems().clear();
            updateMemberCount();
        }
    }

    @FXML
    private void addMember() {
        String email = memberEmailField.getText().trim();
        if (!email.isEmpty() && !membersListView.getItems().contains(email)) {
            if (forumService.getUserByEmail(email) != -1) {
            membersListView.getItems().add(email);
                memberEmailField.clear();
                hideError(memberEmailField, memberEmailErrorLabel);
                isMemberEmailValid = true;
                
                updateMemberCount();
            }
        }
    }

    @FXML
    private void initialize() {
        forumTableView.setEditable(true);

        forumIdColumn.setCellValueFactory(new PropertyValueFactory<>("forumId"));

        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        nameColumn.setOnEditCommit(this::onNameEdit);

        createdByColumn.setCellValueFactory(cellData -> {
            int userId = cellData.getValue().getCreatedBy();
            String email = forumService.getEmailById(userId);
            return new SimpleStringProperty(email);
        });
        createdByColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        createdByColumn.setOnEditCommit(event -> {
            Forum forum = event.getRowValue();
            if (validateTableEdit(forum, "createdBy", event.getNewValue())) {
                int userId = forumService.getUserByEmail(event.getNewValue());
                forum.setCreatedBy(userId);
                updateForum(forum);
            }
        });

        postCountColumn.setCellValueFactory(new PropertyValueFactory<>("postCount"));
        postCountColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        postCountColumn.setOnEditCommit(this::onPostCountEdit);

        membersColumn.setCellValueFactory(cellData -> {
            Forum forum = cellData.getValue();
            if (!forum.isPrivate()) {
                return new SimpleStringProperty("Public Forum");
            }
            String members = forum.getListMembers();
            if (members == null || members.isEmpty()) {
                return new SimpleStringProperty("No members");
            }
            return new SimpleStringProperty(members.replace(",", "\n"));
        });
        membersColumn.setCellFactory(tc -> {
            TableCell<Forum, String> cell = new TableCell<>() {
                private final Text text = new Text();
                {
                    setGraphic(text);
                    setPrefHeight(Control.USE_COMPUTED_SIZE);
                    text.wrappingWidthProperty().bind(widthProperty());
                }
                
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    text.setText(empty ? null : item);
                }
            };
            return cell;
        });
        membersColumn.setEditable(false);

        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        descriptionColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        descriptionColumn.setOnEditCommit(this::onDescriptionEdit);

        dateColumn.setCellValueFactory(new PropertyValueFactory<>("dateCreation"));

        privateColumn.setCellValueFactory(new PropertyValueFactory<>("private"));
        privateColumn.setCellFactory(TextFieldTableCell.forTableColumn(new BooleanStringConverter()));
        privateColumn.setOnEditCommit(event -> {
            Forum forum = event.getRowValue();
            forum.setPrivate(event.getNewValue());
            if (!event.getNewValue()) {
                forum.setListMembers("");
                forum.setNbrMembers(0);
            }
            updateForum(forum);
        });

        addButtonsToTable();

        refreshTable();

        addForumButton.setOnAction(event -> addForum());
        isPrivateCheckBox.setOnAction(event -> handlePrivateCheckbox());
        addMemberButton.setOnAction(event -> addMember());

        nameField.textProperty().addListener((observable, oldValue, newValue) -> validateName(newValue));
        createdByField.textProperty().addListener((observable, oldValue, newValue) -> validateEmail(newValue));
        descriptionField.textProperty().addListener((observable, oldValue, newValue) -> validateDescription(newValue));
        memberEmailField.textProperty().addListener((observable, oldValue, newValue) -> validateMemberEmail(newValue));

        nameErrorLabel.setVisible(false);
        emailErrorLabel.setVisible(false);
        descriptionErrorLabel.setVisible(false);
        memberEmailErrorLabel.setVisible(false);
    }

    private void addButtonsToTable() {
        TableColumn<Forum, Void> actionsCol = new TableColumn<>("Actions");

        Callback<TableColumn<Forum, Void>, TableCell<Forum, Void>> cellFactory = new Callback<>() {
            @Override
            public TableCell<Forum, Void> call(final TableColumn<Forum, Void> param) {
                return new TableCell<>() {
                    private final Button deleteButton = new Button("Delete");
                    private final HBox buttons = new HBox(5, deleteButton);

                    {
                        deleteButton.setOnAction(event -> {
                            Forum forum = getTableView().getItems().get(getIndex());
                            handleDelete(forum);
                        });

                        buttons.setAlignment(javafx.geometry.Pos.CENTER);
                    }

                    @Override
                    protected void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(buttons);
                        }
                    }
                };
            }
        };

        actionsCol.setCellFactory(cellFactory);
        forumTableView.getColumns().add(actionsCol);
    }

    private void handleDelete(Forum forum) {
        forumService.supprimer(forum);
        refreshTable();
    }

    private void refreshTable() {
        forumList.clear();
        forumList.addAll(forumService.rechercher());
        forumTableView.setItems(forumList);
    }

    private void onNameEdit(CellEditEvent<Forum, String> event) {
        Forum forum = event.getRowValue();
        if (validateTableEdit(forum, "name", event.getNewValue())) {
            forum.setName(event.getNewValue());
            updateForum(forum);
        }
    }

    private void onPostCountEdit(CellEditEvent<Forum, Integer> event) {
        Forum forum = event.getRowValue();
        forum.setPostCount(event.getNewValue());
        updateForum(forum);
    }

    private void onDescriptionEdit(CellEditEvent<Forum, String> event) {
        Forum forum = event.getRowValue();
        if (validateTableEdit(forum, "description", event.getNewValue())) {
            forum.setDescription(event.getNewValue());
            updateForum(forum);
        }
    }

    private void updateForum(Forum forum) {
        forumService.modifier(forum);
        refreshTable();
    }

    private void validateName(String name) {
        if (name.isEmpty()) {
            showError(nameField, nameErrorLabel, "Le nom ne peut pas être vide");
            isNameValid = false;
        } else if (name.length() > 20) {
            showError(nameField, nameErrorLabel, "Le nom ne doit pas dépasser 20 caractères");
            isNameValid = false;
        } else if (!name.matches("[a-zA-Z ]+")) {
            showError(nameField, nameErrorLabel, "Le nom ne doit contenir que des lettres et des espaces");
            isNameValid = false;
        } else {
            hideError(nameField, nameErrorLabel);
            isNameValid = true;
        }
    }

    private void validateEmail(String email) {
        if (email.isEmpty()) {
            showError(createdByField, emailErrorLabel, "L'email ne peut pas être vide");
            isEmailValid = false;
        } else if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            showError(createdByField, emailErrorLabel, "Format d'email invalide");
            isEmailValid = false;
        } else if (forumService.getUserByEmail(email) == -1) {
            showError(createdByField, emailErrorLabel, "Cet utilisateur n'existe pas");
            isEmailValid = false;
        } else {
            hideError(createdByField, emailErrorLabel);
            isEmailValid = true;
        }
    }

    private void validateDescription(String description) {
        if (description.isEmpty()) {
            showError(descriptionField, descriptionErrorLabel, "La description ne peut pas être vide");
            isDescriptionValid = false;
        } else if (description.length() > 100) {
            showError(descriptionField, descriptionErrorLabel, "La description ne doit pas dépasser 100 caractères");
            isDescriptionValid = false;
        } else {
            hideError(descriptionField, descriptionErrorLabel);
            isDescriptionValid = true;
        }
    }

    private void validateMemberEmail(String email) {
        addMemberButton.setDisable(true);

        if (email.isEmpty()) {
            showError(memberEmailField, memberEmailErrorLabel, "L'email ne peut pas être vide");
            isMemberEmailValid = false;
        } else if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            showError(memberEmailField, memberEmailErrorLabel, "Format d'email invalide");
            isMemberEmailValid = false;
        } else if (forumService.getUserByEmail(email) == -1) {
            showError(memberEmailField, memberEmailErrorLabel, "Cet utilisateur n'existe pas");
            isMemberEmailValid = false;
        } else {
            hideError(memberEmailField, memberEmailErrorLabel);
            isMemberEmailValid = true;
            addMemberButton.setDisable(false);
        }
    }

    private void showError(TextField field, Label errorLabel, String message) {
        field.getStyleClass().clear();
        field.getStyleClass().addAll("text-field", "text-field-error");
        errorLabel.setText(message);
        errorLabel.setVisible(true);
    }

    private void hideError(TextField field, Label errorLabel) {
        field.getStyleClass().clear();
        field.getStyleClass().addAll("text-field", "valid-field");
        errorLabel.setVisible(false);
    }

    @FXML
    private void addForum() {
        validateName(nameField.getText());
        validateEmail(createdByField.getText());
        validateDescription(descriptionField.getText());

        if (!isNameValid || !isEmailValid || !isDescriptionValid) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Erreur de validation");
            alert.setHeaderText("Veuillez corriger les erreurs suivantes:");
            
            StringBuilder content = new StringBuilder();
            if (!isNameValid) content.append("- Erreur dans le nom du forum\n");
            if (!isEmailValid) content.append("- Erreur dans l'email du créateur\n");
            if (!isDescriptionValid) content.append("- Erreur dans la description\n");
            
            alert.setContentText(content.toString());
            alert.showAndWait();
            return;
        }

        String name = nameField.getText();
        String createdBy = createdByField.getText();
        String description = descriptionField.getText();
        boolean isPrivate = isPrivateCheckBox.isSelected();

        String listMembers = isPrivate ? String.join(",", membersListView.getItems()) : "";

        Forum forum = new Forum();
        forum.setName(name);
        forum.setDescription(description);
        forum.setDateCreation(LocalDateTime.now());
        forum.setPrivate(isPrivate);
        forum.setListMembers(listMembers);
        forum.setCreatedBy(forumService.getUserByEmail(createdBy));

        forum.setNbrMembers(membersListView.getItems().size());

        forumService.ajouter(forum);
        refreshTable();
    }

    private boolean validateTableEdit(Forum forum, String field, String newValue) {
        StringBuilder errors = new StringBuilder();
        boolean isValid = true;
        
        switch(field) {
            case "name":
                if (newValue.isEmpty()) {
                    errors.append("- Le nom ne peut pas être vide\n");
                    isValid = false;
                }
                if (newValue.length() > 20) {
                    errors.append("- Le nom ne doit pas dépasser 20 caractères\n");
                    isValid = false;
                }
                if (!newValue.matches("[a-zA-Z ]+")) {
                    errors.append("- Le nom ne doit contenir que des lettres et des espaces\n");
                    isValid = false;
                }
                break;
            
            case "createdBy":
                if (newValue.isEmpty()) {
                    errors.append("- L'email ne peut pas être vide\n");
                    isValid = false;
                } else if (!newValue.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
                    errors.append("- Format d'email invalide\n");
                    isValid = false;
                } else if (forumService.getUserByEmail(newValue) == -1) {
                    errors.append("- Cet utilisateur n'existe pas\n");
                    isValid = false;
                }
                break;
            
            case "description":
                if (newValue.isEmpty()) {
                    errors.append("- La description ne peut pas être vide\n");
                    isValid = false;
                }
                if (newValue.length() > 100) {
                    errors.append("- La description ne doit pas dépasser 100 caractères\n");
                    isValid = false;
                }
                break;
        }
        
        if (!isValid) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Erreur de modification");
            alert.setHeaderText("Veuillez corriger les erreurs suivantes:");
            alert.setContentText(errors.toString());
            alert.showAndWait();
            refreshTable();
            return false;
        }
        
        return true;
    }

    private void updateMemberCount() {
        if (isPrivateCheckBox.isSelected()) {
            int memberCount = membersListView.getItems().size();
            Forum selectedForum = forumTableView.getSelectionModel().getSelectedItem();
            if (selectedForum != null) {
                selectedForum.setNbrMembers(memberCount);
                selectedForum.setListMembers(String.join(",", membersListView.getItems()));
                updateForum(selectedForum);
            }
        }
    }
}