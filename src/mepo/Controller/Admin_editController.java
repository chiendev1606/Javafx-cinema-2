
package mepo.Controller;

import com.jfoenix.controls.JFXButton;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;

import java.io.IOException;
import java.util.HashMap;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import mepo.Components.MyAlert;
import mepo.Components.MyNotify;
import mepo.Components.Validate;
import mepo.Helper.AdminHelper;
import mepo.Components.User;
import mepo.Implements.UserImp;
import mepo.Navigator;


public class Admin_editController {

    private UserImp adminHelper = new AdminHelper();
    private User adminCurrent = null;
    private User adminEdit = null;
    private MyAlert alert = new MyAlert();
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
    private PasswordField txtNewPassword;
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
    private Label txtAlertNewPassword;

    @FXML
    private Label txtAlertCfPassword;

    @FXML
    private Label txtAlertFullName;

    @FXML
    private Label txtAlertContact;

    public void initialize(User adminCurrent, User adminEdit) {
        this.adminCurrent = adminCurrent;
        this.adminEdit = adminHelper.selectUsername(adminEdit.getUsername());
        txtUser.setText("User: " + this.adminCurrent.getUsername());

        checkValid();

        txtUsername.setText(this.adminEdit.getUsername());
        txtFullname.setText(this.adminEdit.getFullName());
        txtContact.setText(this.adminEdit.getContact());
        txtUsername.setDisable(true);
    }

    @FXML
    void btnActionTuCoins(ActionEvent actionEvent) throws IOException {
        Navigator.getInstance().goToDialogHistoryAdmin(adminCurrent);
    }

    @FXML
    void btnActionCard(ActionEvent actionEvent) throws IOException {
        Navigator.getInstance().goToCardAdmin(adminCurrent);
    }

    @FXML
    private void btnResetClick(ActionEvent event) {
        txtPassword.setText("");
        txtNewPassword.setText("");
        txtCfPassword.setText("");
        txtFullname.setText("");
        txtContact.setText("");
    }

    @FXML
    private void btnEditClick(ActionEvent event) throws IOException {
//        try {
        User updateUser = extractTeacherFromFields();
        if (checkVa(updateUser)) {
            boolean result = adminHelper.update(updateUser);
            if (alert.alertComfirm("DO YOUR WANT TO CHANGE PASSWORD ?") == ButtonType.YES) {
                if (result) {
                    MyNotify.MyNotifyAlert("Edit successfully");
                } else {
                    MyNotify.MyNotifyAlertError("System error, edit failed !!! please come back later !!");
                }
            }
        } else {
            MyNotify.MyNotifyAlertError("Edit fail !!! please check all your info !!!");
        }

    }

    private User extractTeacherFromFields() {
        User u = new User();
        u.setUsername(this.adminEdit.getUsername());
        u.setPassword(txtNewPassword.getText());
        u.setFullname(txtFullname.getText());
        u.setContact(txtContact.getText());

        return u;

    }

    private boolean checkVa(User editAdmin) {
        try {
            HashMap<String, String> err = new HashMap<String, String>();
            if (txtPassword.getText().isEmpty()) {
                err.put("password", "Password cannot be left blank");
            }
            if (txtNewPassword.getText().isEmpty()) {
                err.put("newpassword", "New Password cannot be left blank");
            }
            if (txtCfPassword.getText().isEmpty()) {
                err.put("cfpassword", "Confirm Password cannot be left blank");
            }
            if (editAdmin.getFullName().isEmpty()) {
                err.put("fullname", "Full Name cannot be left blank");
            }
            if (editAdmin.getContact().isEmpty()) {
                err.put("contact", "Contact cannot be left blank");
            }
            if (!txtNewPassword.getText().equals(txtCfPassword.getText())) {
                err.putIfAbsent("cfpassword", "Confirm password is not correct");
            }
            if (txtPassword.getText().length() < 6) {
                err.putIfAbsent("password", "Password must have at least 6 characters");
            }
            if (txtNewPassword.getText().length() < 6) {
                err.putIfAbsent("newpassword", "New Password must have at least 6 characters");
            }
            if (txtCfPassword.getText().length() < 6) {
                err.putIfAbsent("cfpassword", "Confirm Password must have at least 6 characters");
            }
            if (!Validate.checkPwHash(this.adminEdit.getPassword(), txtPassword.getText().trim())) {
                err.putIfAbsent("password", "Password is not correct");
            }

            if (err.size() == 0) {
                return true;
            } else {
                txtAlertPassword.setText(err.get("password"));
                txtAlertNewPassword.setText(err.get("newpassword"));
                txtAlertCfPassword.setText(err.get("cfpassword"));
                txtAlertFullName.setText(err.get("fullname"));
                txtAlertContact.setText(err.get("contact"));
                return false;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    @FXML
    private void btnBackClick(ActionEvent event) throws IOException {
        Navigator.getInstance().goToAdmin(adminCurrent);
    }

    @FXML
    void logoClicked(MouseEvent event) throws IOException {
        Navigator.getInstance().goToHomeAdmin(adminCurrent);
    }

    @FXML
    private void btnHomeClick(ActionEvent event) throws IOException {
        Navigator.getInstance().goToHomeAdmin(adminCurrent);
    }

    @FXML
    private void btnAdminClick(ActionEvent event) throws IOException {
        Navigator.getInstance().goToAdmin(adminCurrent);
    }

    @FXML
    private void btnCustomerClick(ActionEvent event) throws IOException {
        Navigator.getInstance().goToCustomer(adminCurrent);
    }

    @FXML
    private void btnCategoryClick(ActionEvent event) throws IOException {
        Navigator.getInstance().goToCategory(adminCurrent);
    }

    @FXML
    private void btnProductClick(ActionEvent event) throws IOException {
        Navigator.getInstance().goToProducts(adminCurrent);
    }

    @FXML
    private void btnOrderClick(ActionEvent event) throws IOException {
        Navigator.getInstance().goToOrderAdmin(adminCurrent);
    }

    @FXML
    private void btnFeedbackClick(ActionEvent event) throws IOException {
        Navigator.getInstance().goToFeedbackAdmin(adminCurrent);
    }

    @FXML
    private void btnLogoutClick(ActionEvent event) throws IOException {
        Navigator.getInstance().goToLoginAdmin();
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
