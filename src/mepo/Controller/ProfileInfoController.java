/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mepo.Controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import java.io.IOException;
import java.util.HashMap;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import mepo.Components.Helper;
import mepo.Components.MyAlert;
import mepo.Components.MyNotify;
import mepo.Helper.ClientHelper;
import mepo.Components.User;
import mepo.Implements.UserImp;
import mepo.Navigator;


public class ProfileInfoController {

    private UserImp clientHelper = new ClientHelper();
    private User user = null;
    private MyAlert alert = new MyAlert();


    @FXML
    private JFXTextField txtUserName;
    @FXML
    private Label txtAlertName;
    @FXML
    private JFXTextField txtFullName;
    @FXML
    private Label txtAlertFullName;
    @FXML
    private JFXTextField txtContact;

    @FXML
    private Label txtAlertContact;
    @FXML
    private JFXButton btnBack;
    @FXML
    private JFXButton btnSubmit;
    @FXML
    private JFXButton btnReset;
    @FXML
    private Label txtSll;
    @FXML
    private Label txtMMM;

    @FXML
    private JFXButton textCoin;

    @FXML
    void btnActionCoin(ActionEvent event) {

    }

    public void initialize(User user) {
        txtMMM.setText(user.getUsername());
        this.user = user;
        textCoin.setText(Helper.formatString(user.getCoin()));
        HomeController.setSLL(txtSll);
        checkValid();

        User us = clientHelper.selectUsername(this.user.getUsername());
        txtUserName.setText(us.getUsername());
        txtFullName.setText(us.getFullName());
        txtContact.setText(us.getContact());
        txtContact.setDisable(true);
        txtUserName.setDisable(true);
    }


    @FXML
    void btnTransactions(ActionEvent event) throws IOException {
        Navigator.getInstance().goToOrderHistory(user);
    }

    @FXML
    void btnActiontuCoin(ActionEvent event) throws IOException {
        Navigator.getInstance().gotoTopUpCoins(user);
    }

    @FXML
    void miAccountClick(ActionEvent event) throws IOException {
        Navigator.getInstance().goToOrderHistory(user);
    }

    @FXML
    void miLogout(ActionEvent event) throws IOException {
        Navigator.getInstance().goToLogin();
    }

    @FXML
    private void btnLogo(ActionEvent event) throws IOException {
        Navigator.getInstance().goToHome(user);
    }

    @FXML
    private void btnGame(ActionEvent event) throws IOException {
        Navigator.getInstance().goToHome(user);
    }

    @FXML
    private void btnFeedBack(ActionEvent event) throws IOException {
        Navigator.getInstance().goToNews(user);
    }

    @FXML
    private void btnContact(ActionEvent event) throws IOException {
        Navigator.getInstance().goToContact(user);
    }

    @FXML
    private void btnCart(ActionEvent event) throws IOException {
        Navigator.getInstance().goToOrder(user);
    }

    @FXML
    private void btnProfileClick(ActionEvent event) throws IOException {
        Navigator.getInstance().goToProfileClient(user);
    }

    @FXML
    private void btnChangePassword(ActionEvent event) throws IOException {
        Navigator.getInstance().goToProfileChange(user);
    }

    @FXML
    private void btnSubmitclick(ActionEvent event) {
        try {
            User updateUser = extractTeacherFromFields();
            if (validateInfo(updateUser)) {
                if(alert.alertComfirm("DO YOU WANT TO CHANGE INFO ???") == ButtonType.YES){
                boolean result = clientHelper.update(updateUser);
                if (result) {
                    MyNotify.MyNotifyAlert("CHANGE INFO SUCCESSFULLY !!");
                } else {
                    MyNotify.MyNotifyAlertError("Edit fail !!! please try again");
                }}
            } else {
                MyNotify.MyNotifyAlertError("PLEASE CHECK YOUR INFO AGAIN !!!");

            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            MyNotify.MyNotifyAlertError("Edit fail !!! please try again");
        }
    }

    boolean validateInfo(User editClient) {
        HashMap<String, String> err = new HashMap<String, String>();
        User checkUserName = clientHelper.selectUsername(editClient.getUsername());
        err.clear();

        if (editClient.getFullName().isEmpty()) {
            err.put("fullname", "Full Name cannot be left blank");
        }
        if (editClient.getContact().isEmpty()) {
            err.put("profile", "Email cannot be left blank");
        }

        if (err.size() == 0) {
            return true;
        } else {
            txtAlertFullName.setText(err.get("fullname"));
            txtAlertContact.setText(err.get("profile"));
            return false;
        }
    }

    private void checkValid() {
        txtFullName.textProperty().addListener((observable, oldValue, newValue) -> {
            resetTextFields(txtAlertFullName);
            if (newValue.isEmpty()) {
                txtAlertFullName.setText("Full name cannot be left blank");
            }
        });
        txtContact.textProperty().addListener((observable, oldValue, newValue) -> {
            resetTextFields(txtAlertContact);
            if (newValue.isEmpty()) {
                txtAlertContact.setText("Email cannot be left blank");
            }
        });
    }

    private void resetTextFields(Label label) {
        label.setText("");
    }

    private User extractTeacherFromFields() {
        User newUser = new User();
        newUser.setUsername(user.getUsername());
        newUser.setPassword(user.getPassword());
        newUser.setFullname(txtFullName.getText());
        newUser.setContact(txtContact.getText());
        newUser.setCoin(user.getCoin());
        return newUser;
    }

    @FXML
    private void btnResetclick(ActionEvent event) {
        txtFullName.setText("");
        txtContact.setText("");
    }

    @FXML
    void btnActionMyMovies(ActionEvent actionEvent) throws IOException {
        Navigator.getInstance().goToMyMovies(user);
    }
}
