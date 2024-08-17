package com.example.keybooking;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Login implements Initializable {

    @FXML
    private ImageView keyLogoImg;

    @FXML
    private ImageView ktuLogoImg;

    @FXML
    private Button loginBtn;

    @FXML
    private PasswordField passwordInput;

    @FXML
    private TextField usernameInput;

    @FXML
    void OnLoginClick(ActionEvent event) {

        String username = usernameInput.getText();
        String password = passwordInput.getText();

        if (username.isEmpty() || password.isEmpty()) {
            alert("Auth", Alert.AlertType.WARNING, "Enter username and password to login");
        } else if (username.equals("admin") && password.equals("12345")) {
            loadScene("home.fxml", 700, 500, loginBtn, "KEYs.KTU Home");
        }else {
            alert("Auth", Alert.AlertType.WARNING, "Wrong username or password");
        }


    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        AddKey.images(ktuLogoImg, keyLogoImg);
    }

    public void alert(String title, Alert.AlertType alertType, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
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
}
