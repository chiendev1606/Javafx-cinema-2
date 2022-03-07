
package mepo.Controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;

import java.io.IOException;
import java.util.HashMap;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import mepo.Components.*;
import mepo.Helper.ClientHelper;
import mepo.Implements.UserImp;
import mepo.Navigator;


public class profileChangePwController {

    private UserImp clientHelper = new ClientHelper();
    private User user = null;
    private MyAlert alert = new MyAlert();


    @FXML
    private JFXPasswordField txtPassword;
    @FXML
    private Label txtAlertPassword;
    @FXML
    private JFXPasswordField txtNewPassword;
    @FXML
    private Label txtAlertNewPassword;
    @FXML
    private JFXPasswordField txtCfPassword;
    @FXML
    private Label txtAlertCfPassword;
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

    @FXML
    void btnTransactions(ActionEvent event) throws IOException {
        Navigator.getInstance().goToOrderHistory(user);
    }

    @FXML
    void btnActiontuCoin(ActionEvent event) throws IOException {
        Navigator.getInstance().gotoTopUpCoins(user);
    }

    public void initialize(User user) {
        txtMMM.setText(user.getUsername());
        this.user = user;
        textCoin.setText(Helper.formatString(user.getCoin()));

        checkValid();
        HomeController.setSLL(txtSll);
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
    private void btnBackclick(ActionEvent event) {
    }

    @FXML
    private void btnSubmitclick(ActionEvent event) {
        try {
            User updateUser = extractTeacherFromFields();
            if (validateInfo(updateUser)) {
                if (alert.alertComfirm("DO YOU WANT TO CHANGE PASSWORD ?") == ButtonType.YES) {
                    user.setPassword(updateUser.getPassword());
                    boolean result = clientHelper.update(updateUser);
                    if (result) {
                        MyNotify.MyNotifyAlert("Change password successfully");
                    } else {
                        MyNotify.MyNotifyAlert("SYSTEM ERROR !!! PLEASE TRY AGAIN !!!");
                    }
                }
            } else {
                MyNotify.MyNotifyAlertError("PLEASE COMPLETE ALL THE FIELDS !!!");
            }

        } catch (Exception e) {
            MyNotify.MyNotifyAlertError("PLEASE COMPLETE ALL THE FIELDS !!!");
            System.out.println(e.getMessage());
        }
    }

    private User extractTeacherFromFields() {
        User u = new User();
        u.setUsername(user.getUsername());
        u.setPassword(txtNewPassword.getText());
        u.setFullname(user.getUsername());
        u.setContact(user.getContact());
        u.setCoin(user.getCoin());
        return u;
    }

    boolean validateInfo(User editClient) {
        HashMap<String, String> err = new HashMap<String, String>();
        User userExist = clientHelper.selectUsername(user.getUsername());

        if (editClient.getPassword().isEmpty()) {
            err.put("password", "Password cannot be not blank");
        }
        if (txtNewPassword.getText().isEmpty()) {
            err.put("newpassword", "New Password cannot be not blank");
        }
        if (txtCfPassword.getText().isEmpty()) {
            err.put("cfpassword", "Confirm Password cannot be not blank");
        }
        if (!txtNewPassword.getText().equals(txtCfPassword.getText())) {
            err.putIfAbsent("cfpassword", "Confirm password is not correct");
        }
        if (editClient.getPassword().length() < 6) {
            err.putIfAbsent("password", "Password must have at least 6 characters");
        }
        if (txtNewPassword.getText().length() < 6) {
            err.putIfAbsent("newpassword", "New Password must have at least 6 characters");
        }
        if (txtCfPassword.getText().length() < 6) {
            err.putIfAbsent("cfpassword", "Confirm Password must have at least 6 characters");
        }
        if (!Validate.checkPwHash(userExist.getPassword(),txtPassword.getText())) {
            err.putIfAbsent("password", "Password is not correct");
        }

        if (err.size() == 0) {
            return true;
        } else {
            txtAlertPassword.setText(err.get("password"));
            txtAlertNewPassword.setText(err.get("newpassword"));
            txtAlertCfPassword.setText(err.get("cfpassword"));
            return false;
        }
    }

    private void checkValid() {
        txtPassword.textProperty().addListener((observable, oldValue, newValue) -> {
            resetTextFields(txtAlertPassword);
            if (newValue.isEmpty()) {
                txtAlertPassword.setText("Password cannot be left blank");
            } else if (newValue.length() < 6) {
                txtAlertPassword.setText("Password must have at least 6 characters");
            }
        });
        txtNewPassword.textProperty().addListener((observable, oldValue, newValue) -> {
            resetTextFields(txtAlertNewPassword);
            if (newValue.isEmpty()) {
                txtAlertNewPassword.setText("New Password cannot be left blank");
            } else if (newValue.length() < 6) {
                txtAlertNewPassword.setText("New Password must have at least 6 characters");
            }
        });
        txtCfPassword.textProperty().addListener((observable, oldValue, newValue) -> {
            resetTextFields(txtAlertCfPassword);
            if (newValue.isEmpty()) {
                txtAlertCfPassword.setText("Confirm Password cannot be left blank");
            } else if (newValue.length() < 6) {
                txtAlertCfPassword.setText("Confirm Password must have at least 6 characters");
            }
        });
    }

    void resetTextFields(Label l) {
        l.setText("");
    }


    @FXML
    private void btnResetclick(ActionEvent event) {
        txtCfPassword.setText("");
        txtNewPassword.setText("");
        txtPassword.setText("");
    }



    @FXML
    void btnActionMyMovies(ActionEvent actionEvent) {
    }
}
