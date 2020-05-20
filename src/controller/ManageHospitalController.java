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

public class ManageHospitalController {
    public AnchorPane root;
    public ListView<HospitalTM> lstHospitals;
    public Label lblId;
    public JFXButton btnSave;
    public JFXButton btnDelete;
    public JFXButton btnAddNew;
    public JFXTextField txtHosName;
    public JFXTextField txtCity;
    public JFXComboBox<String> cmbDistricts;
    public JFXTextField txtCapacity;
    public JFXTextField txtDirector;
    public JFXTextField txtDirectorContact;
    public JFXTextField txtHosContact;
    public JFXTextField txtHosEmail;
    public JFXTextField txtSearch;
    public ImageView imgHome;

    ArrayList<HospitalTM> hospitalsList = new ArrayList<>();

    //Initialize Method
    public void initialize() {

        btnSave.setDisable(true);
        btnDelete.setDisable(true);

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

        lstHospitals.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<HospitalTM>() {
            @Override
            public void changed(ObservableValue<? extends HospitalTM> observable, HospitalTM oldValue, HospitalTM newValue) {

                btnDelete.setDisable(false);

            }
        });

        loadAllHospitals();

        txtSearch.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                ObservableList<HospitalTM> hospitals = lstHospitals.getItems();
                hospitals.clear();

                for (HospitalTM hospitalTM : hospitalsList) {
                    if (hospitalTM.getHosName().contains(newValue)){
                        hospitals.add(hospitalTM);
                        lstHospitals.refresh();
                    }
                }

            }
        });

    }

    //Add New Button
    public void btnAddNew_OnAction(ActionEvent actionEvent) {

        txtHosName.clear();
        txtCity.clear();
        txtCapacity.clear();
        txtDirector.clear();
        txtDirectorContact.clear();
        txtHosContact.clear();
        txtHosEmail.clear();
        cmbDistricts.setValue(null);
        txtHosName.requestFocus();
        btnSave.setDisable(false);
        btnDelete.setDisable(false);

        int maxId = 0;
        try {
            Statement stm = DBConnection.getInstance().getConnection().createStatement();
            ResultSet rst = stm.executeQuery("SELECT hos_id FROM Hospital ORDER BY hos_id DESC LIMIT 1");
            if (rst.next()) {
                maxId = Integer.parseInt(rst.getString(1).replace("H", ""));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        maxId = maxId + 1;
        String id = "";
        if (maxId < 10) {
            id = "H00" + maxId;
        } else if (maxId < 100) {
            id = "H0" + maxId;
        } else {
            id = "H" + maxId;
        }
        lblId.setText(id);


    }

    //Save Button
    public void btnSave_OnAction(ActionEvent actionEvent) {

        String id = lblId.getText();
        String hosName = txtHosName.getText();
        String hosCity = txtCity.getText();
        String district = cmbDistricts.getValue();
        String capacity = txtCapacity.getText();
        String director = txtDirector.getText();
        String directorContact = txtDirectorContact.getText();
        String hosContact = txtHosContact.getText();
        String email = txtHosEmail.getText();

        if (hosName.trim().length() == 0 || hosCity.trim().length() == 0 || district.trim().length() == 0 || capacity.trim().length() == 0 || director.trim().length() == 0 || directorContact.trim().length() == 0 || hosContact.trim().length() == 0 || email.trim().length() == 0) {
            new Alert(Alert.AlertType.ERROR, "Text fields can't be empty. Click Add new button to continue", ButtonType.OK).show();
            btnSave.setDisable(true);
            btnDelete.setDisable(true);
            return;
        }

        if (!email.matches("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])")){
            new Alert(Alert.AlertType.ERROR,"Email is not valid.!").show();
            return;
        }

        if (!directorContact.matches("^[0-9]\\d.-[0-9]\\d.....$") || !hosContact.matches("^[0-9]\\d.-[0-9]\\d.....$")){
            new Alert(Alert.AlertType.ERROR,"One or more phone numbers are not valid.!").show();
            return;
        }

        if (hosName.matches("^[a-zA-Z]+(([',. -][a-zA-Z ])?[a-zA-Z]*)*$") || hosCity.matches("^[a-zA-Z]+(([',. -][a-zA-Z ])?[a-zA-Z]*)*$") || director.matches("^[a-zA-Z]+(([',. -][a-zA-Z ])?[a-zA-Z]*)*$")){
            new Alert(Alert.AlertType.ERROR,"Hospital name, City or Director name must not be contain any numbers.!").show();
            return;
        }

        try {
            PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement("INSERT INTO Hospital VALUES (?,?,?,?,?,?,?,?,?)");
            pstm.setObject(1, id);
            pstm.setObject(2, hosName);
            pstm.setObject(3, hosCity);
            pstm.setObject(4, district);
            pstm.setObject(5, capacity);
            pstm.setObject(6, director);
            pstm.setObject(7, directorContact);
            pstm.setObject(8, hosContact);
            pstm.setObject(9, email);
            int affectedRows = pstm.executeUpdate();
            if (affectedRows == 0) {
                new Alert(Alert.AlertType.ERROR, "Failed to save the hospital", ButtonType.OK).show();
            } else {
                new Alert(Alert.AlertType.INFORMATION, "Success", ButtonType.OK).show();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        loadAllHospitals();
        lstHospitals.refresh();

        lblId.setText(null);
        txtHosName.clear();
        txtCity.clear();
        txtCapacity.clear();
        txtDirector.clear();
        txtDirectorContact.clear();
        txtHosContact.clear();
        txtHosEmail.clear();
        cmbDistricts.setValue(null);
        btnSave.setDisable(true);
        btnDelete.setDisable(true);

    }

    //Delete Button
    public void btnDelete_OnAction(ActionEvent actionEvent) {


        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure whether you want to delete this Hospital?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> buttonType = alert.showAndWait();

        if (buttonType.get() == ButtonType.YES) {
            HospitalTM selectedHospital = lstHospitals.getSelectionModel().getSelectedItem();

            try {
                PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement("DELETE FROM Hospital WHERE hos_id=?");
                pstm.setObject(1, selectedHospital.getHosID());
                if (pstm.executeUpdate() == 0) {
                    new Alert(Alert.AlertType.ERROR, "Failed to delete.", ButtonType.OK).show();
                } else {
                    lstHospitals.getItems().remove(selectedHospital);
                    lstHospitals.getSelectionModel().clearSelection();
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }

        }

        loadAllHospitals();
        lstHospitals.refresh();

        lblId.setText(null);
        txtHosName.clear();
        txtCity.clear();
        txtCapacity.clear();
        txtDirector.clear();
        txtDirectorContact.clear();
        txtHosContact.clear();
        txtHosEmail.clear();
        cmbDistricts.setValue(null);
        btnAddNew.requestFocus();
        btnSave.setDisable(true);
        btnDelete.setDisable(true);

        }

        public void loadAllHospitals () {

            try {

                Statement stm = DBConnection.getInstance().getConnection().createStatement();
                ResultSet rst = stm.executeQuery("SELECT * FROM Hospital");
                ObservableList<HospitalTM> hospitals = lstHospitals.getItems();
                hospitals.clear();
                while (rst.next()) {
                    String id = rst.getString(1);
                    String hosName = rst.getString(2);
                    String hosCity = rst.getString(3);
                    String hodDistrict = rst.getString(4);
                    int hosCapacity = rst.getInt(5);
                    String hosDirectorName = rst.getString(6);
                    String hosDirectorContact = rst.getString(7);
                    String hosContact = rst.getString(8);
                    String hosEmail = rst.getString(9);
                    hospitals.add(new HospitalTM(id, hosName, hosCity, hodDistrict, hosCapacity, hosDirectorName, hosDirectorContact, hosContact, hosEmail));
                    hospitalsList.add(new HospitalTM(id, hosName, hosCity, hodDistrict, hosCapacity, hosDirectorName, hosDirectorContact, hosContact, hosEmail));
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }

        }

        public void imgHome_OnAction (MouseEvent mouseEvent) throws IOException {

            URL resource = this.getClass().getResource("/view/Dashboard.fxml");
            Parent root = FXMLLoader.load(resource);
            Scene scene = new Scene(root);
            Stage primaryStage = (Stage) (this.root.getScene().getWindow());
            primaryStage.setScene(scene);
            primaryStage.centerOnScreen();

        }
    }
// TODO: 5/18/2020 "Validation and Search"