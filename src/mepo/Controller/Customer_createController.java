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
import mepo.Helper.ClientHelper;
import mepo.Components.User;
import mepo.Implements.UserImp;
import mepo.Navigator;

public class Customer_createController {

    private UserImp UserImp = new ClientHelper();
    private User user = null;
    private User userk = null;

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

    public void initialize(User username1) {
        this.user = username1;
        txtUser.setText("User: " + this.user.getUsername());
        checkValid();
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
    private void btnResetClick(ActionEvent event) {
        txtPassword.setText("");
        txtUsername.setText("");
        txtCfPassword.setText("");
        txtFullname.setText("");
        txtContact.setText("");
    }

    @FXML
    private void btnEditClick(ActionEvent event) {
        User insertUser = extractTeacherFromFields();

        if (checkVa(insertUser)) {
            insertUser = UserImp.insert(insertUser);
            System.out.println("Insert successfully");
            alert("Succesfully", "Insert succesfully");
        } else {
            System.out.println("Insert fail");
        }
    }

    private User extractTeacherFromFields() {
        User u = new User();
        u.setUsername(txtUsername.getText());
        u.setPassword(txtPassword.getText());
        u.setFullname(txtFullname.getText());
        u.setContact(txtContact.getText());

        return u;
    }

    boolean checkVa(User insertClient) {
        HashMap<String, String> err = new HashMap<String, String>();
        User checkUserName = UserImp.selectUsername(insertClient.getUsername());
        err.clear();
        if (insertClient.getUsername().isEmpty()) {
            err.put("username", "Username cannot be left blank");
        }
        if (insertClient.getPassword().isEmpty()) {
            err.put("password", "Password cannot be left blank");
        }
        if (txtCfPassword.getText().isEmpty()) {
            err.put("cfpassword", "Confirm Password cannot be left blank");
        }
        if (insertClient.getFullName().isEmpty()) {
            err.put("fullname", "Full Name cannot be left blank");
        }
        if (insertClient.getContact().isEmpty()) {
            err.put("contact", "Contact cannot be left blank");
        }
        if (txtPassword.getText().toString().equals(txtCfPassword.getText().toString()) == false) {
            err.putIfAbsent("cfpassword", "Confirm password is not correct");
        }
        if (insertClient.getUsername().length() < 6) {
            err.putIfAbsent("username", "Username must have at least 6 characters");
        }
        if (insertClient.getPassword().length() < 6) {
            err.putIfAbsent("password", "Password must have at least 6 characters");
        }
        if (txtCfPassword.getText().length() < 6) {
            err.putIfAbsent("cfpassword", "Confirm Password must have at least 6 characters");
        }
        if (checkUserName.getUsername() != null) {
            err.putIfAbsent("username", "Username already exists");
        }

        if (err.size() == 0) {
            return true;
        } else {
            txtAlertName.setText(err.get("username"));
            txtAlertPassword.setText(err.get("password"));
            txtAlertCfPassword.setText(err.get("cfpassword"));
            txtAlertFullName.setText(err.get("fullname"));
            txtAlertContact.setText(err.get("contact"));
            return false;
        }
    }

    @FXML
    private void btnBackClick(ActionEvent event) throws IOException {
        Navigator.getInstance().goToCustomer(user);
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
