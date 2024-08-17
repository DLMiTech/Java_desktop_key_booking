package com.example.keybooking;

import com.example.keybooking.models.BookingRepo;
import com.example.keybooking.models.KeyRepo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class Home implements Initializable {

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
    private Label bookingCount;
    @FXML
    private Label keyCount;
    @FXML
    private Label usernameLab;

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

        int totalKeys = KeyRepo.getTotalKeysCount();
        keyCount.setText(String.valueOf(totalKeys));

        int totalBookedKeys = BookingRepo.getTotalBookedKeysCount();
        bookingCount.setText(String.valueOf(totalBookedKeys));
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
