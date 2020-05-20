package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import db.DBConnection;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import util.CampTM;
import util.HospitalTM;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

public class ManageQuarantineCentersController {
    public AnchorPane root;
    public ListView<CampTM> lstCamp;
    public Label lblId;
    public JFXButton btnSave;
    public JFXButton btnDelete;
    public JFXButton btnAddNew;
    public JFXTextField txtCampName;
    public JFXTextField txtCity;
    public JFXComboBox<String> cmbDistricts;
    public JFXTextField txtCapacity;
    public JFXTextField txtHeadName;
    public JFXTextField txtHeadContact;
    public JFXTextField txtCampContact;
    public JFXTextField txtCampEmail;
    public JFXTextField txtSearch;
    public ImageView imgHome;

    ArrayList<CampTM> campsList = new ArrayList<>();

    public void initialize() {

        loadAllCamps();

        String districtsText = " Colombo\n" +
                " Gampaha\n" +
                " Kalutara\n" +
                " Kandy\n" +
                " Matale\n" +
                " Nuwara Eliya\n" +
                " Galle\n" +
                " Matara\n" +
                " Hambantota\n" +
                " Jaffna\n" +
                " Mannar\n" +
                " Vauniya\n" +
                " Mullativue\n" +
                " Ampara\n" +
                " Trincomalee\n" +
                " Batticaloa\n" +
                " Kilinochchi\n" +
                " Kurunegala\n" +
                " Puttalam\n" +
                " Anuradhapura\n" +
                " Polonnaruwa\n" +
                " Badulla\n" +
                " Moneragala\n" +
                " Ratnapura\n" +
                " Kegalle";
        String[] districts = districtsText.split("\n");
        ObservableList<String> olDistricts = FXCollections.observableArrayList(Arrays.asList(districts));
        cmbDistricts.setItems(olDistricts);

        lstCamp.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<CampTM>() {
            @Override
            public void changed(ObservableValue<? extends CampTM> observable, CampTM oldValue, CampTM newValue) {

                btnDelete.setDisable(false);

            }
        });

        btnSave.setDisable(true);
        btnDelete.setDisable(true);

        txtSearch.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                ObservableList<CampTM> camps = lstCamp.getItems();
                camps.clear();

                for (CampTM campTM : campsList) {
                    if (campTM.getCampName().contains(newValue)){
                        camps.add(campTM);
                        lstCamp.refresh();
                    }
                }

            }
        });

    }

    public void btnAddNew_OnAction(ActionEvent actionEvent) {

        txtCampName.clear();
        txtCity.clear();
        cmbDistricts.setValue(null);
        txtCapacity.clear();
        txtHeadName.clear();
        txtHeadContact.clear();
        txtCampContact.clear();
        txtCampEmail.clear();
        btnSave.setDisable(false);
        btnDelete.setDisable(false);
        txtCampName.requestFocus();

        int maxId = 0;
        try {
            Statement stm = DBConnection.getInstance().getConnection().createStatement();
            ResultSet rst = stm.executeQuery("SELECT camp_id FROM Camp ORDER BY camp_id DESC LIMIT 1");
            if (rst.next()) {
                maxId = Integer.parseInt(rst.getString(1).replace("C", ""));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        maxId = maxId + 1;
        String id = "";
        if (maxId < 10) {
            id = "C00" + maxId;
        } else if (maxId < 100) {
            id = "C0" + maxId;
        } else {
            id = "C" + maxId;
        }
        lblId.setText(id);

    }

    public void btnDelete_OnAction(ActionEvent actionEvent) {

        Optional<ButtonType> buttonType = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure whether you want to delete this Camp.?", ButtonType.YES, ButtonType.NO).showAndWait();

        if (buttonType.get() == ButtonType.YES) {

            CampTM selectedCamp = lstCamp.getSelectionModel().getSelectedItem();

            try {
                PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement("DELETE FROM Camp WHERE camp_id=?");
                pstm.setObject(1, selectedCamp.getCampID());
                int affectedRows = pstm.executeUpdate();
                if (affectedRows == 0){
                    new Alert(Alert.AlertType.ERROR, "Failed to delete", ButtonType.OK).show();
                }else {
                    lstCamp.getItems().remove(selectedCamp);
                    lstCamp.getSelectionModel().clearSelection();
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

        loadAllCamps();
        lstCamp.refresh();

        lblId.setText(null);
        txtCampName.clear();
        txtCity.clear();
        cmbDistricts.setValue(null);
        txtCapacity.clear();
        txtHeadName.clear();
        txtHeadContact.clear();
        txtCampContact.clear();
        txtCampEmail.clear();
        btnAddNew.requestFocus();
        btnSave.setDisable(true);
        btnDelete.setDisable(true);

    }

    public void btnSave_OnAction(ActionEvent actionEvent) {

        if (txtCampName.getText().trim().length() == 0 || txtCity.getText().trim().length() == 0 || cmbDistricts.getValue().trim().length() == 0 || txtCapacity.getText().trim().length() == 0 || txtHeadName.getText().trim().length() == 0 || txtHeadContact.getText().trim().length() == 0 || txtCampContact.getText().trim().length() == 0 || txtCampEmail.getText().trim().length() == 0) {
            new Alert(Alert.AlertType.ERROR, "Text fields can't be empty. Click Add new button to continue", ButtonType.OK).show();
            btnSave.setDisable(true);
            btnDelete.setDisable(true);
            return;
        }

        if (!txtCampEmail.getText().matches("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])")){
            new Alert(Alert.AlertType.ERROR,"Email is not valid.!").show();
            return;
        }

        if (!txtHeadContact.getText().matches("^[0-9]\\d.-[0-9]\\d.....$") || !txtCampContact.getText().matches("^[0-9]\\d.-[0-9]\\d.....$")){
            new Alert(Alert.AlertType.ERROR,"One or more phone numbers are not valid.!").show();
            return;
        }

        if (!txtCampName.getText().matches("^[a-zA-Z]+(([',. -][a-zA-Z ])?[a-zA-Z]*)*$") || !txtCity.getText().matches("^[a-zA-Z]+(([',. -][a-zA-Z ])?[a-zA-Z]*)*$") || !txtHeadName.getText().matches("^[a-zA-Z]+(([',. -][a-zA-Z ])?[a-zA-Z]*)*$")){
            new Alert(Alert.AlertType.ERROR,"Camp name, City or Head name must not be contain any numbers.!").show();
            return;
        }

        ObservableList<CampTM> camps = lstCamp.getItems();

        try {
            PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement("INSERT INTO Camp VALUES (?,?,?,?,?,?,?,?,?)");
            pstm.setObject(1,lblId.getText());
            pstm.setObject(2,txtCampName.getText());
            pstm.setObject(3,txtCity.getText());
            pstm.setObject(4,cmbDistricts.getValue());
            pstm.setObject(5,txtCapacity.getText());
            pstm.setObject(6,txtHeadName.getText());
            pstm.setObject(7,txtHeadContact.getText());
            pstm.setObject(8,txtCampContact.getText());
            pstm.setObject(9,txtCampEmail.getText());

            int affectedRows = pstm.executeUpdate();
            if (affectedRows == 0){
                new Alert(Alert.AlertType.ERROR, "Failed to save the Camp details", ButtonType.OK).show();
            } else {
                new Alert(Alert.AlertType.INFORMATION, "Success", ButtonType.OK).show();
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        loadAllCamps();
        lstCamp.refresh();

        lblId.setText(null);
        txtCampName.clear();
        txtCity.clear();
        cmbDistricts.setValue(null);
        txtCapacity.clear();
        txtHeadName.clear();
        txtHeadContact.clear();
        txtCampContact.clear();
        txtCampEmail.clear();
        btnSave.setDisable(true);
        btnDelete.setDisable(true);

    }

    public void loadAllCamps() {

        ObservableList<CampTM> camps = lstCamp.getItems();

        try {
            Statement stm = DBConnection.getInstance().getConnection().createStatement();
            ResultSet rst = stm.executeQuery("SELECT * FROM Camp");
            camps.clear();

            while (rst.next()) {

                String campId = rst.getString(1);
                String campName = rst.getString(2);
                String city = rst.getString(3);
                String district = rst.getString(4);
                int capacity = rst.getInt(5);
                String campHead = rst.getString(6);
                String headContact = rst.getString(7);
                String campContact = rst.getString(8);
                String campEmail = rst.getString(9);

                camps.add(new CampTM(campId, campName, city, district, capacity, campHead, headContact, campContact, campEmail));
                campsList.add(new CampTM(campId, campName, city, district, capacity, campHead, headContact, campContact, campEmail));

            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    public void imgHome_OnAction(MouseEvent mouseEvent) throws IOException {

        URL resource = this.getClass().getResource("/view/Dashboard.fxml");
        Parent root = FXMLLoader.load(resource);
        Scene scene = new Scene(root);
        Stage primaryStage = (Stage) (this.root.getScene().getWindow());
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();

    }

    // TODO: 5/18/2020 "Validation and Search"
}
