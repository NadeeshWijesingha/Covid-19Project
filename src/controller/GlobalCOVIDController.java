package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import db.DBConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;

public class GlobalCOVIDController {


    public AnchorPane root;
    public ImageView imgHome;
    public Label lblDate;
    public Label lblConfirmCases;
    public Label lblRecovered;
    public Label lblDeaths;
    public JFXTextField txtConfirmCase;
    public JFXTextField txtRecovered;
    public JFXTextField txtDeaths;
    public JFXButton btnUpdate;
    public JFXDatePicker txtDate;

    public void initialize(){

        txtDate.setValue(null);
        txtDate.requestFocus();
        txtConfirmCase.clear();
        txtRecovered.clear();
        txtDeaths.clear();

            loadDetails();

    }

    public void btnUpdate_OnAction(ActionEvent actionEvent) {

        LocalDate date = txtDate.getValue();
        String confirmCase = txtConfirmCase.getText();
        String recovered = txtRecovered.getText();
        String deaths = txtDeaths.getText();

        if (!confirmCase.matches("^[0-9]\\d*$") || !recovered.matches("^[0-9]\\d*$") || !deaths.matches("^[0-9]\\d*$")){
            new Alert(Alert.AlertType.ERROR, "All text fields should contain absolute numbers only").show();
            txtConfirmCase.clear();
            txtRecovered.clear();
            txtDeaths.clear();
            return;
        }

        try {
            PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement("INSERT INTO Global_info VALUES (?,?,?,?)");
            pstm.setObject(1,date);
            pstm.setObject(2,confirmCase);
            pstm.setObject(3,recovered);
            pstm.setObject(4,deaths);
            int affectedRows = pstm.executeUpdate();
            if (affectedRows == 0) {
                new Alert(Alert.AlertType.ERROR, "Failed to add the customer", ButtonType.OK).show();
            }

            lblDate.setText(String.valueOf(date));
            lblConfirmCases.setText(confirmCase);
            lblRecovered.setText(recovered);
            lblDeaths.setText(deaths);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        txtConfirmCase.clear();
        txtRecovered.clear();
        txtDeaths.clear();
        txtDate.setValue(null);

    }

    public void loadDetails(){

        try {
            Statement stm = DBConnection.getInstance().getConnection().createStatement();
            ResultSet rst = stm.executeQuery("SELECT * FROM Global_info");

            while (rst.next()) {

                Date date = rst.getDate(1);
                int confirmedCases = rst.getInt(2);
                int recovered = rst.getInt(3);
                int deaths = rst.getInt(4);

                lblDate.setText(String.valueOf(date));
                lblConfirmCases.setText(String.valueOf(confirmedCases));
                lblRecovered.setText(String.valueOf(recovered));
                lblDate.setText(String.valueOf(deaths));


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
}
