
package mepo.Controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import mepo.Components.MyAlert;
import mepo.Components.Validate;
import mepo.Helper.AdminHelper;
import mepo.Helper.ClientHelper;
import mepo.Components.User;
import mepo.Implements.UserImp;
import mepo.Navigator;



public class LoginController implements Initializable {

    private UserImp clientHelper = new ClientHelper();
    private UserImp adminHelper = new AdminHelper();
    private MyAlert alert =new MyAlert();

    @FXML
    private JFXTextField txtusername;
    @FXML
    private JFXPasswordField txtpassword;
    @FXML
    private JFXButton btnlogin;
    @FXML
    private JFXButton btnregister;
    @FXML
    private Label txt_alert_username;
    @FXML
    private Label txt_alert_password;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        checkValid();
        OrderController.listChecked.clear();
    }

    @FXML
    private void btnloginclick(ActionEvent event) throws IOException {
        User userLogin = extractFromFields();
        User clientExist = clientHelper.selectUsername(userLogin.getUsername());
        User adminExist = adminHelper.selectUsername(userLogin.getUsername());
        if(adminExist == null || clientExist == null)return;
        if (validateInfo(userLogin)) {
            if (userLogin.getUsername().equals(clientExist.getUsername()) && Validate.checkPwHash(clientExist.getPassword(), userLogin.getPassword()  )) {
                userLogin.setContact(clientExist.getContact());
                userLogin.setCoin(clientExist.getCoin());
                userLogin.setFullname(clientExist.getFullName());
                Navigator.getInstance().goToHome(userLogin);
            } else if(userLogin.getUsername().equals(adminExist.getUsername()) && Validate.checkPwHash(adminExist.getPassword(), userLogin.getPassword())) {
                userLogin.setContact(adminExist.getContact());
                userLogin.setFullname(adminExist.getFullName());
                Navigator.getInstance().goToHomeAdmin(userLogin);
            } else {
                this.alert.alertError("There was a problem logging in. Check your username and password or create an account.");
            }
        }else {
            this.alert.alertError("There was a problem logging in. Check your username and password or create an account.");
        }

    }

    boolean validateInfo(User user) {
        HashMap<String, String> Errors = new HashMap<String, String>();
        if (user.getUsername().length() < 6) {
            Errors.putIfAbsent("username", "Username must have at least 6 characters");
        }
        if (user.getPassword().length() < 6) {
            Errors.putIfAbsent("password", "Password must have at least 6 characters");
        }

        if (Errors.size() == 0) {
            return true;
        } else {
            txt_alert_username.setText(Errors.get("username"));
            txt_alert_password.setText(Errors.get("password"));
            return false;
        }
    }

    @FXML
    private void btnregisterclick(ActionEvent event) throws IOException {
        Navigator.getInstance().goToRegistration(null);
    }
    @FXML
    void btnForgottenPw(ActionEvent event) throws IOException {
        Navigator.getInstance().gotoForgetPw("");
    }
    private User extractFromFields() {
        User user = new User();
        user.setUsername(txtusername.getText().trim());
        user.setPassword(txtpassword.getText().trim());
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
    }

    void resetTextFields(Label l) {
        l.setText("");
    }



}
