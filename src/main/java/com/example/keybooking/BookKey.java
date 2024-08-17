
package com.example.keybooking;

import com.example.keybooking.models.Booking;
import com.example.keybooking.models.BookingRepo;
import com.example.keybooking.models.KeyRepo;
import com.example.keybooking.models.Keys;
import javafx.beans.property.SimpleStringProperty;
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
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;


public class BookKey implements Initializable {

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
    private ComboBox<Keys> keysCombo;

    @FXML
    private TableView<Booking> bookingTable;
    @FXML
    private TableColumn<Booking, String> keyCol;
    @FXML
    private TableColumn<Booking, String> indexCol;
    @FXML
    private TableColumn<Booking, String> dateCol;
    @FXML
    private TableColumn<Booking, Void> actionCol;

    @FXML
    private TextField indexInput;
    @FXML
    private Button bookingBtn;

    @FXML
    void OnBookingClick(ActionEvent event) {
        if (keysCombo.getSelectionModel().isEmpty() || indexInput.getText().isEmpty()){
            alert("Input Warning", Alert.AlertType.WARNING, "All inputs are required to book a key");
        }else {

            Keys selectedKey = keysCombo.getSelectionModel().getSelectedItem();

            String myIndex = indexInput.getText();
            String myKeyName = selectedKey.getBlock() + " - " + selectedKey.getRoom();
            int myKeyId = selectedKey.getId();

            Booking booking = new Booking();
            booking.setKeyId(myKeyId);
            booking.setKey(myKeyName);
            booking.setStudentIndex(myIndex);

            int result = BookingRepo.insert(booking);

            if (result == 1) {
                alert("Success", Alert.AlertType.INFORMATION, "Key Added Successfully");
                bookingTable.getItems().add(booking);
                keysCombo.getSelectionModel().clearSelection();
                indexInput.clear();
            } else if (result == 2) {
                alert("Success", Alert.AlertType.WARNING, "Key is already booked");
            } else {
                alert("Error", Alert.AlertType.ERROR, "Error occurred adding key.");
            }
        }
    }


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

        keyComboSelect();


        indexCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStudentIndex()));
        keyCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getKey()));
        dateCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getBookingDate()));


        actionCol.setCellFactory(param -> new TableCell<Booking, Void>() {
            private final Button returnButton = new Button("Return Key");

            {
                returnButton.setStyle("-fx-background-color: green; -fx-text-fill: white;");
            }

            {
                returnButton.setOnAction(event -> {
//                    Booking booking = getTableView().getItems().get(getIndex());

                    Optional<ButtonType> result = confirm("Confirm Key Returns", "Are you sure you want to Return key?");
                    if (result.isPresent() && result.get() == ButtonType.OK) {
                        Booking booking = getTableView().getItems().get(getIndex());

                        boolean returnResult = BookingRepo.returnKey(booking.getId());
                        if (returnResult) {
                            alert("KTU KEYs", Alert.AlertType.INFORMATION, "Key returned successfully");
                            // Optionally, remove the key from the table
                            getTableView().getItems().remove(booking);
                        } else {
                            alert("KTU KEYs", Alert.AlertType.ERROR, "Error occurred while returning key");
                        }
                    } else {
                        // Cancel the save action
                        System.out.println("Return Key Canceled");
                    }
                });

                HBox hBox = new HBox(20, returnButton);
                setGraphic(hBox);
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(new HBox(10, returnButton));
                }
            }
        });



        // Load data into the table
        List<Booking> bookings = BookingRepo.getAllBooking();
        bookingTable.getItems().addAll(bookings);
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


    public void keyComboSelect(){
        List<Keys> keysList = KeyRepo.getAllKeys();
        // Add the keys to the ComboBox
        keysCombo.getItems().addAll(keysList);

        keysCombo.setCellFactory(param -> new ListCell<Keys>() {
            @Override
            protected void updateItem(Keys item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getBlock() + " - " + item.getRoom());
                }
            }
        });

        keysCombo.setConverter(new StringConverter<Keys>() {
            @Override
            public String toString(Keys keys) {
                return keys == null ? null : keys.getBlock() + " - " + keys.getRoom();
            }

            @Override
            public Keys fromString(String string) {
                return null;
            }
        });
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

