/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mepo.Controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import java.io.IOException;
import java.util.HashMap;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import mepo.Components.MyAlert;
import mepo.Helper.AdminHelper;
import mepo.Helper.ClientHelper;
import mepo.Components.User;
import mepo.Implements.UserImp;
import mepo.Navigator;

import javax.mail.MessagingException;

import static mepo.Components.Validate.isValidEmailAddress;

public class RegistrationController {

    private UserImp clientHelper = new ClientHelper();
    private UserImp adminHelper = new AdminHelper();
    private MyAlert alert = new MyAlert();

    @FXML
    private JFXTextField txtusername;
    @FXML
    private JFXPasswordField txtpassword;
    @FXML
    private JFXButton btnregister;
    @FXML
    private JFXPasswordField txtconfirmpassword;
    @FXML
    private JFXTextField txtfullname;
    @FXML
    private JFXTextField txtcontact;
    @FXML
    private JFXButton btnlogin;

    @FXML
    private Label txt_alert_username;
    @FXML
    private Label txt_alert_password;
    @FXML
    private Label txt_alert_confirm_password;
    @FXML
    private Label txt_alert_fullname;
    @FXML
    private Label txt_alert_contact;


    public void initialize(User user) {
        if(user != null){
            txtusername.setText(user.getUsername());
            txtpassword.setText(user.getPassword());
            txtconfirmpassword.setText(user.getPassword());
            txtfullname.setText(user.getPassword());
            txtcontact.setText(user.getContact());
        }
        checkValid();
    }

    @FXML
    private void btnregisterclick(ActionEvent event) throws IOException, MessagingException {
        User insertUser = extractTeacherFromFields();
        if (validateInfo(insertUser)) {
            Navigator.getInstance().goToEmailVerify(insertUser);

        } else {
            this.alert.alertError("There was a problem creating your account. Check that some info is invalid.");
        }
    }

    void clear() {
        txtusername.setText("");
        txtpassword.setText("");
        txtconfirmpassword.setText("");
        txtfullname.setText("");
        txtcontact.setText("");
    }

    boolean validateInfo(User insertClient) {
        HashMap<String, String> Errors = new HashMap<String, String>();
        User userExist = clientHelper.selectUsername(insertClient.getUsername());
        User adminExist = adminHelper.selectUsername(insertClient.getUsername());
        User emailExist = clientHelper.selectUserByEmail(insertClient.getContact());

        if (insertClient.getUsername().isEmpty()) {
            Errors.put("username", "Username cannot be blank");
        } else  if (insertClient.getUsername().length() < 6) {
            Errors.put("username", "Username must have at least 6 characters");
        } else if (userExist.getUsername() != null || adminExist.getUsername() != null) {
            Errors.put("username", "Username already exists");
        }

        if (txtconfirmpassword.getText().isEmpty()) {
            Errors.put("cfpassword", "Confirm Password cannot be blank");
        } else if (txtconfirmpassword.getText().length() < 6) {
            Errors.putIfAbsent("cfpassword", "Confirm Password must have at least 6 characters");
        }

        if (insertClient.getPassword().isEmpty()) {
            Errors.put("password", "Password cannot be blank");
        } else if (insertClient.getPassword().length() < 6) {
            Errors.put("password", "Password must have at least 6 characters");
        }else  if (!txtpassword.getText().toString().equals(txtconfirmpassword.getText().toString())) {
            Errors.put("cfpassword", "Confirm password is not correct");
        }

        if (insertClient.getFullName().isEmpty()) {
            Errors.put("fullname", "Full Name cannot be blank");
        }

        if (insertClient.getContact().isEmpty()) {
            Errors.put("email", "email cannot be blank");
        } else if (!isValidEmailAddress(insertClient.getContact())) {
            Errors.put("email", "email is not valid");
        } else if (emailExist != null) {
            Errors.put("email", "email already exists ");
        }

        if (Errors.size() == 0) {
            return true;
        } else {
            txt_alert_username.setText(Errors.get("username"));
            txt_alert_password.setText(Errors.get("password"));
            txt_alert_confirm_password.setText(Errors.get("cfpassword"));
            txt_alert_fullname.setText(Errors.get("fullname"));
            txt_alert_contact.setText(Errors.get("email"));

            return false;
        }
    }

    @FXML
    private void btnloginclick(ActionEvent event) throws IOException {
        Navigator.getInstance().goToLogin();
    }



    private User extractTeacherFromFields() {
        User user = new User();
        user.setUsername(txtusername.getText());
        user.setPassword(txtpassword.getText());
        user.setFullname(txtfullname.getText());
        user.setContact(txtcontact.getText());

        return user;
    }

    private void checkValid() {
        txtusername.textProperty().addListener((observable, oldValue, newValue) -> {
            resetTextFields(txt_alert_username);
            if (newValue.length() < 6) {
                txt_alert_username.setText("Username must have at least 6 characters");
            }
        });
        txtpassword.textProperty().addListener((observable, oldValue, newValue) -> {
            resetTextFields(txt_alert_password);
            if (newValue.length() < 6) {
                txt_alert_password.setText("Password must have at least 6 characters");
            }
        });
        txtconfirmpassword.textProperty().addListener((observable, oldValue, newValue) -> {
            resetTextFields(txt_alert_confirm_password);
            if (newValue.length() < 6) {
                txt_alert_confirm_password.setText("Confirm password must have at least 6 characters");
            }
        });
        txtcontact.textProperty().addListener((observable, oldValue, newValue) -> {
            resetTextFields(txt_alert_contact);
            if (!isValidEmailAddress(newValue)) {
                txt_alert_contact.setText("Email is invalid");
            }
        });
        txtfullname.textProperty().addListener((observable, oldValue, newValue) -> {
            resetTextFields(txt_alert_fullname);
            if (newValue.isEmpty()) {
                txt_alert_fullname.setText("Full name cannot be left blank ");
            }
        });
    }

    void resetTextFields(Label l) {
        l.setText("");
    }

}
