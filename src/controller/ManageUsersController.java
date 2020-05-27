package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class ManageUsersController {
    public AnchorPane root;
    public ImageView imgHome;
    public JFXButton btnAddNew;
    public JFXTextField txtName;
    public JFXTextField txtContact;
    public JFXTextField txtMail;
    public JFXTextField txtUserName;
    public JFXPasswordField txtPassword;
    public JFXComboBox cmbUserRole;
    public JFXComboBox cmbUserLocation;
    public JFXButton btnSave;
    public TableView tblUser;
    public TableColumn clmUsername;
    public TableColumn clmName;
    public TableColumn clmRole;
    public TableColumn clmRemove;
    public JFXTextField txtSearch;

    public void initialize(){



    }

    public void btnAddNew_OnAction(ActionEvent actionEvent) {
    }

    public void btnSave_OnAction(ActionEvent actionEvent) {
    }

    public void imgHome_OnAction(MouseEvent mouseEvent) throws IOException {

        URL resource = this.getClass().getResource("/view/Dashboard.fxml");
        Parent root = FXMLLoader.load(resource);
        Scene scene = new Scene(root);
        Stage primaryStage = (Stage) (this.root.getScene().getWindow());
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();

    }
}
