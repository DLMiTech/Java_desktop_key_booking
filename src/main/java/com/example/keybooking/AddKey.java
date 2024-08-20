
package com.example.keybooking;

import com.example.keybooking.models.KeyRepo;
import com.example.keybooking.models.Keys;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;


import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;


public class AddKey implements Initializable {

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
    private ComboBox<String> blockSelect;

    @FXML
    private TextField noKeys;

    @FXML
    private TextField roomName;

    @FXML
    private Button saveBtn;

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

    @FXML
    void OnSaveClick(ActionEvent event) {

        if (blockSelect.getSelectionModel().isEmpty() || roomName.getText().isEmpty() || noKeys.getText().isEmpty()) {
            alert("Input Warning", Alert.AlertType.WARNING, "All inputs are required to add a key");
        } else if (!noKeys.getText().matches("-?\\d+")) {
            alert("Input Warning", Alert.AlertType.WARNING, "Key number input is not a valid integer.");
        } else {
            String myBlock = blockSelect.getSelectionModel().getSelectedItem();
            String myRoom = roomName.getText();
            int myNoKeys = Integer.parseInt(noKeys.getText());

            Keys keys = new Keys();
            keys.setBlock(myBlock);
            keys.setRoom(myRoom);
            keys.setNumKeys(myNoKeys);

            int result = KeyRepo.insert(keys);
            if (result == 1) {
                alert("Success", Alert.AlertType.INFORMATION, "Key Added Successfully");
                blockSelect.getSelectionModel().clearSelection();
                roomName.clear();
                noKeys.clear();
            }else {
                alert("Error", Alert.AlertType.ERROR, "Error occurred adding key.");
            }


        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        images(ktuLogoImg, keyLogoImg);


        ObservableList<String> list = FXCollections.observableArrayList("CCB Block", "AB Block", "FBMS Block", "FOE Block", "FBEN Block", "FAST Block");
        blockSelect.setItems(list);
    }

    static void images(ImageView ktuLogoImg, ImageView keyLogoImg) {
        File ktuLogoFile = new File("images/ktu_logo.png");
        File keyLogoFile = new File("images/keys.png");
        Image ktuLogoImage = new Image(ktuLogoFile.toURI().toString());
        Image keyLogoImage = new Image(keyLogoFile.toURI().toString());
        ktuLogoImg.setImage(ktuLogoImage);
        keyLogoImg.setImage(keyLogoImage);
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
}


