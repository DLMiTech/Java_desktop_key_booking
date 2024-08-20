
package com.example.keybooking;

import com.example.keybooking.models.KeyRepo;
import com.example.keybooking.models.Keys;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;


public class ManageKey implements Initializable {

    @FXML
    private Button addKeyBtn;

    @FXML
    private Button bookKeyBtn;

    @FXML
    private Button homeBtn;

    @FXML
    private ImageView keyLogoImg;

    @FXML
    private ImageView ktuLogoImg;

    @FXML
    private Button logoutBtn;

    @FXML
    private Button manageKeyBtn;

    @FXML
    private TableView<Keys> ketTable;

    @FXML
    private TableColumn<Keys, Void> actionCol;

    @FXML
    private TableColumn<Keys, String> blockCol;

    @FXML
    private TableColumn<Keys, Integer> numCol;

    @FXML
    private TableColumn<Keys, String> roomCol;

    @FXML
    void OnAddKeyClick(ActionEvent event) {
        loadScene("add_key.fxml", 700, 500, addKeyBtn, "KEYs.KTU Add Key");
    }

    @FXML
    void OnBookClick(ActionEvent event) {
        loadScene("book_key.fxml", 700, 500, bookKeyBtn, "KEYs.KTU Book Key");
    }

    @FXML
    void OnHomeClick(ActionEvent event) {
        loadScene("home.fxml", 700, 500, homeBtn, "KEYs.KTU Home");
    }

    @FXML
    void OnManageKeyClick(ActionEvent event) {
        loadScene("manage_key.fxml", 700, 500, manageKeyBtn, "KEYs.KTU Manage Key");
    }

    @FXML
    void OnLogoutClick(ActionEvent event) {
        loadScene("login.fxml", 700, 500, logoutBtn, "KEYs.KTU Login");
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        AddKey.images(ktuLogoImg, keyLogoImg);

        blockCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getBlock()));
        roomCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getRoom()));
        numCol.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getNumKeys()).asObject());


        actionCol.setCellFactory(param -> new TableCell<Keys, Void>() {
            private final Button deleteButton = new Button("Delete");
            private final Button updateButton = new Button("Edit");
            {
                deleteButton.setStyle("-fx-background-color: red; -fx-text-fill: white;");
                updateButton.setStyle("-fx-background-color: green; -fx-text-fill: white;");
            }

            {
                deleteButton.setOnAction(event -> {
                    // Example usage of the confirm method
                    Optional<ButtonType> result = confirm("Confirm Delete", "Are you sure you want to delete?");
                    if (result.isPresent() && result.get() == ButtonType.OK) {
                        Keys keys = getTableView().getItems().get(getIndex());

                        boolean deleteResult = KeyRepo.deleteKey(keys.getId());
                        if (deleteResult) {
                            getTableView().getItems().remove(keys);
                            alert("KTU KEYs", Alert.AlertType.INFORMATION, "User deleted successfully");
                        }else {
                            alert("KTU KEYs", Alert.AlertType.ERROR, "Error occurred while deleting user");
                        }

                    } else {
                        // Cancel the save action
                        System.out.println("User canceled delete.");
                    }
                });

                updateButton.setOnAction(event -> {
                    Keys keys = getTableView().getItems().get(getIndex());
                    showEditDialog(keys);
                });

                HBox hBox = new HBox(20, updateButton, deleteButton);
                setGraphic(hBox);
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(new HBox(10,updateButton, deleteButton));
                }
            }
        });


        // Load data into the table
        List<Keys> keys = KeyRepo.getAllKeys();
        ketTable.getItems().addAll(keys);
    }

    public void loadScene(String fxmlFileName, int width, int height, Node node, String title){
        try {
            URL resourceUrl = getClass().getResource(fxmlFileName);
            if (resourceUrl == null) {
                System.err.println("FXML file not found: " + fxmlFileName);
                return; // Exit the method if FXML file is not found
            }

            FXMLLoader loader = new FXMLLoader(resourceUrl);
            Parent root = loader.load();
            Stage stage = (Stage) node.getScene().getWindow(); // Get the current stage
            stage.setScene(new Scene(root, width, height));
            stage.setTitle(title);
            stage.show();
            stage.setResizable(false);
            stage.centerOnScreen();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void alert(String title, Alert.AlertType alertType, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public Optional<ButtonType> confirm(String header, String content) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("CRUD APP");
        alert.setHeaderText(header);
        alert.setContentText(content);

        return alert.showAndWait();
    }


    private void showEditDialog(Keys keys) {
        Dialog<Keys> dialog = new Dialog<>();
        dialog.setTitle("Edit Key");
        dialog.setHeaderText("Edit key details");


        TextField blockField = new TextField(keys.getBlock());
        TextField roomField = new TextField(keys.getRoom());
        TextField keyNo = new TextField(String.valueOf(keys.getNumKeys()));



        VBox blockBox = new VBox(10, new Label("Block:"), blockField);
        VBox roomBox = new VBox(10, new Label("Room:"), roomField);
        VBox keyNoBox = new VBox(10, new Label("No. of keys::"), keyNo);


        VBox vbox = new VBox(10, blockBox, roomBox, keyNoBox);
        dialog.getDialogPane().setContent(vbox);


        // Add buttons to the dialog
        ButtonType updateButtonType = new ButtonType("Update", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelButtonType = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().addAll(updateButtonType, cancelButtonType);

        // Convert the result to a RegisterModel if the user clicks "Update"
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == updateButtonType) {
                keys.setBlock(blockField.getText());
                keys.setRoom(roomField.getText());
                keys.setNumKeys(Integer.parseInt(keyNo.getText()));
                // Ensure that the ID remains unchanged
                return keys;
            }
            return null;
        });

        Optional<Keys> result = dialog.showAndWait();

        // Update the user in the table if the user clicked "Update"
        result.ifPresent(updatedKey -> {
            boolean updateResult = KeyRepo.updateKey(updatedKey);
            if (updateResult) {
                int index = ketTable.getItems().indexOf(keys);
                if (index != -1) {
                    ketTable.getItems().set(index, updatedKey);
                    alert("KTU KEYs", Alert.AlertType.INFORMATION, "Key updated successfully");
                }
            } else {
                alert("KTU KEYs", Alert.AlertType.ERROR, "Error occurred while updating key");
            }
        });
    }
}


