package controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.Window;
import util.UserTM;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Random;

public class ManageUsersController {
    public PasswordField txtPasswordHide;
    public TextField txtPasswordShow;
    public ImageView imgPasswordShowHide;
    public ComboBox<String> cmbUserRole;
    public ComboBox<String> cmbUserLocation;
    public TableView<UserTM> tblUsers;
    public Pane pne1;

    public void initialize() {

        cmbUserLocation.setVisible(false);
        pne1.setLayoutY(311);

        txtPasswordHide.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                txtPasswordShow.setText(newValue);
            }
        });
        txtPasswordShow.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                txtPasswordHide.setText(newValue);
            }
        });

        tblUsers.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("username"));
        tblUsers.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("name"));
        tblUsers.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("delete"));
        tblUsers.getColumns().get(3).setStyle("-fx-alignment: center");

        // Let's add some dummy data to the table
        ObservableList<UserTM> olUsers = tblUsers.getItems();
        for (int i = 0; i < 10; i++) {
            Button btnDelete = new Button("Delete");
            UserTM newUser = new UserTM(generateRandomString(), "ijse-" + (i + 1), btnDelete);
            btnDelete.setOnAction(event -> olUsers.remove(newUser));
            olUsers.add(newUser);
        }

        cmbUserRole.getItems().add("Admin");
        cmbUserRole.getItems().add("PSTF");
        cmbUserRole.getItems().add("Hospital IT");
        cmbUserRole.getItems().add("Quarantine Center IT");

        cmbUserRole.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if (newValue.longValue() == 2 || newValue.longValue() == 3) {
                    cmbUserLocation.setVisible(true);
                    pne1.setLayoutY(349);
                    Window window = pne1.getScene().getWindow();
                    window.setHeight(680);
                    cmbUserLocation.setPromptText(newValue.longValue() == 2?"Select Hospital":"Select Quarantine Center");
                } else {
                    cmbUserLocation.setVisible(false);
                    pne1.setLayoutY(311);
                    Window window = pne1.getScene().getWindow();
                    window.setHeight(640);
                }
            }
        });
    }

    public void imgPasswordShowHide_OnMouseClicked(MouseEvent mouseEvent) {
        TextField txt = null;
        if (txtPasswordShow.getUserData() == null) {
            txt = txtPasswordShow;
            txt.setUserData("front");
            imgPasswordShowHide.setUserData(imgPasswordShowHide.getImage());
            imgPasswordShowHide.setImage(new Image(this.getClass().getResource("/assert/password-hide.png").toString()));
        } else {
            txt = txtPasswordHide;
            imgPasswordShowHide.setImage((Image) imgPasswordShowHide.getUserData());
            txtPasswordShow.setUserData(null);
        }
        txt.toFront();
        imgPasswordShowHide.toFront();
        txt.requestFocus();
        txt.deselect();
        txt.positionCaret(txt.getLength());
    }

    /**
     * Generates a random string with 10 characters
     *
     * @return Random String
     */
    private String generateRandomString() {
        /*
         * Creates an byte array of 10 elements
         * Each element is stored a random number between 65 to 122 */
        byte[] randomBytes = new byte[10];
        Random rnd = new Random();
        for (int i = 0; i < randomBytes.length; i++) {
            randomBytes[i] = (byte) (rnd.nextInt(122 - 65) + 65);
        }
        return new String(randomBytes, StandardCharsets.US_ASCII);
    }

    public void btnHome_OnAction(ActionEvent event) throws IOException {
        Scene mainScene = new Scene(FXMLLoader.load(this.getClass().getResource("/view/Dashboard.fxml")));
        Stage primaryStage = (Stage) (txtPasswordHide.getScene().getWindow());
        primaryStage.setScene(mainScene);
        primaryStage.centerOnScreen();
        primaryStage.sizeToScene();
    }
}
