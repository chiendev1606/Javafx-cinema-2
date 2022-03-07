/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mepo.Controller;

import com.jfoenix.controls.JFXButton;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.io.IOException;
import java.util.HashMap;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import mepo.Components.MyNotify;
import mepo.Helper.AdminHelper;
import mepo.Components.User;
import mepo.Implements.UserImp;
import mepo.Navigator;

/**
 * FXML Controller class
 *
 * @author Dell
 */
public class Admin_createController {

    private UserImp adminHelper = new AdminHelper();
    private User user = null;

    @FXML
    private Button btnReset;
    @FXML
    private Button btnEdit;
    @FXML
    private Label txtUser;
    @FXML
    private TextField txtUsername;
    @FXML
    private Button btnBack;
    @FXML
    private FontAwesomeIconView back;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private PasswordField txtCfPassword;
    @FXML
    private TextField txtFullname;
    @FXML
    private TextField txtContact;
    @FXML
    private JFXButton btnHome;
    @FXML
    private JFXButton btnAdmin;
    @FXML
    private JFXButton btnCustomer;
    @FXML
    private JFXButton btnCategory;
    @FXML
    private JFXButton btnProduct;
    @FXML
    private JFXButton btnOrder;
    @FXML
    private JFXButton btnFeedback;
    @FXML
    private JFXButton btnLogout;
    @FXML
    private Label txtAlertName;

    @FXML
    private Label txtAlertPassword;

    @FXML
    private Label txtAlertCfPassword;

    @FXML
    private Label txtAlertFullName;

    @FXML
    private Label txtAlertContact;

    /**
     * Initializes the controller class.
     */
    public void initialize(User user) {
        this.user = user;
        txtUser.setText("User: " + this.user.getUsername());
        checkValid();
    }

    @FXML
    private void btnResetClick(ActionEvent event) {
        txtPassword.setText("");
        txtUsername.setText("");
        txtCfPassword.setText("");
        txtFullname.setText("");
        txtContact.setText("");
    }

    private User extractTeacherFromFields() {
        User user = new User();
        user.setUsername(txtUsername.getText());
        user.setPassword(txtPassword.getText());
        user.setFullname(txtFullname.getText());
        user.setContact(txtContact.getText());

        return user;
    }

    private boolean validateInfo(User insertAdmin) {
        HashMap<String, String> Errors = new HashMap<String, String>();
        User adminExist = adminHelper.selectUsername(insertAdmin.getUsername());
        if (insertAdmin.getUsername().isEmpty()) {
            Errors.put("username", "Username cannot be left blank");
        }
        if (insertAdmin.getPassword().isEmpty()) {
            Errors.put("password", "Password cannot be left blank");
        }
        if (txtCfPassword.getText().isEmpty()) {
            Errors.put("cfpassword", "Confirm Password cannot be left blank");
        }
        if (insertAdmin.getFullName().isEmpty()) {
            Errors.put("fullname", "Full Name cannot be left blank");
        }
        if (insertAdmin.getContact().isEmpty()) {
            Errors.put("contact", "Contact cannot be left blank");
        }
        if (!txtPassword.getText().toString().equals(txtCfPassword.getText().toString())) {
            Errors.putIfAbsent("cfpassword", "Confirm password is not correct");
        }
        if (insertAdmin.getUsername().length() < 6) {
            Errors.putIfAbsent("username", "Username must have at least 6 characters");
        }
        if (insertAdmin.getPassword().length() < 6) {
            Errors.putIfAbsent("password", "Password must have at least 6 characters");
        }
        if (txtCfPassword.getText().length() < 6) {
            Errors.putIfAbsent("cfpassword", "Confirm Password must have at least 6 characters");
        }
        if (adminExist.getUsername() != null) {
            Errors.putIfAbsent("username", "Username already exists");
        }

        if (Errors.size() == 0) {
            return true;
        } else {
            txtAlertName.setText(Errors.get("username"));
            txtAlertPassword.setText(Errors.get("password"));
            txtAlertCfPassword.setText(Errors.get("cfpassword"));
            txtAlertFullName.setText(Errors.get("fullname"));
            txtAlertContact.setText(Errors.get("contact"));
            return false;
        }
    }

    @FXML
    private void btnEditClick(ActionEvent event) throws IOException {
        User insertUser = extractTeacherFromFields();

        if (validateInfo(insertUser)) {
            insertUser = adminHelper.insert(insertUser);
            String message = " account admin created successfully";
            MyNotify.MyNotifyAlert(message);
            Navigator.getInstance().goToAdminCreate(user);
        } else {
            MyNotify.MyNotifyAlertError("created fail !! please try again !!");
        }
    }
    @FXML
    void btnActionTuCoins(ActionEvent actionEvent) throws IOException {
        Navigator.getInstance().goToDialogHistoryAdmin(user);
    }
    @FXML
    void btnActionCard(ActionEvent actionEvent) throws IOException {
        Navigator.getInstance().goToCardAdmin(user);
    }

    @FXML
    private void btnBackClick(ActionEvent event) throws IOException {
        Navigator.getInstance().goToAdmin(user);
    }

    @FXML
    void logoClicked(MouseEvent event) throws IOException {
        Navigator.getInstance().goToHomeAdmin(user);
    }

    @FXML
    private void btnHomeClick(ActionEvent event) throws IOException {
        Navigator.getInstance().goToHomeAdmin(user);
    }

    @FXML
    private void btnAdminClick(ActionEvent event) throws IOException {
        Navigator.getInstance().goToAdmin(user);
    }

    @FXML
    private void btnCustomerClick(ActionEvent event) throws IOException {
        Navigator.getInstance().goToCustomer(user);
    }

    @FXML
    private void btnCategoryClick(ActionEvent event) throws IOException {
        Navigator.getInstance().goToCategory(user);
    }

    @FXML
    private void btnProductClick(ActionEvent event) throws IOException {
        Navigator.getInstance().goToProducts(user);
    }

    @FXML
    private void btnOrderClick(ActionEvent event) throws IOException {
        Navigator.getInstance().goToOrderAdmin(user);
    }

    @FXML
    private void btnFeedbackClick(ActionEvent event) throws IOException {
        Navigator.getInstance().goToFeedbackAdmin(user);
    }

    @FXML
    private void btnLogoutClick(ActionEvent event) throws IOException {
        Navigator.getInstance().goToLoginAdmin();
    }

    private void alert(String title, String text) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(text);
        alert.showAndWait();
    }

    private void checkValid() {
        txtUsername.textProperty().addListener((observable, oldValue, newValue) -> {
            resetTextFields(txtAlertName);
            if (newValue.isEmpty()) {
                txtAlertName.setText("Username cannot be left blank");
            } else if (newValue.length() < 6) {
                txtAlertName.setText("Username must have at least 6 characters");
            }
        });
        txtPassword.textProperty().addListener((observable, oldValue, newValue) -> {
            resetTextFields(txtAlertPassword);
            if (newValue.isEmpty()) {
                txtAlertPassword.setText("Password cannot be left blank");
            } else if (newValue.length() < 6) {
                txtAlertPassword.setText("Password must have at least 6 characters");
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
        txtFullname.textProperty().addListener((observable, oldValue, newValue) -> {
            resetTextFields(txtAlertFullName);
            if (newValue.isEmpty()) {
                txtAlertFullName.setText("Full name cannot be left blank");
            }
        });
        txtContact.textProperty().addListener((observable, oldValue, newValue) -> {
            resetTextFields(txtAlertContact);
            if (newValue.isEmpty()) {
                txtAlertContact.setText("Contact cannot be left blank");
            }
        });
    }

    void resetTextFields(Label l) {
        l.setText("");
    }

}
