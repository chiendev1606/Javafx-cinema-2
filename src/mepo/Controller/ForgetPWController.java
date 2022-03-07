package mepo.Controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import mepo.Components.*;
import mepo.Helper.ClientHelper;
import mepo.Implements.UserImp;
import mepo.Navigator;


import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;

public class ForgetPWController {

    @FXML
    private JFXButton btnSearch;

    @FXML
    private Label txt_alert;

    @FXML
    private JFXTextField btnEmail;

    @FXML
    private Text textFw;

    @FXML
    private JFXTextField btnCode;

    @FXML
    private Button btnReset;

    @FXML
    private JFXButton btnregister;

    @FXML
    private Text textResendCode;

    @FXML
    private Button bnResend;

    @FXML
    private JFXButton btnChangeInfo;

    @FXML
    void btnAcTionChangeInfo(ActionEvent event) throws IOException {
        Navigator.getInstance().gotoForgetPw(this.getUserFW().getContact());
    }

    private static HashMap<String, String> error = new HashMap<String, String>();
    private UserImp userHelper = new ClientHelper();
    private User userFW;
    private static String code = "";
    private Mail mail = new Mail();
    private MyAlert alert = new MyAlert();


    public User getUserFW() {
        return userFW;
    }

    public void setUserFW(User userFW) {
        this.userFW = userFW;
    }

    @FXML
    void btnActionResetPw(ActionEvent event) throws IOException , javax.mail.MessagingException {
        if (code.equals(btnCode.getText().trim())) {
            String newPassWord = mail.resetPw(userFW.getContact());
            userFW.setPassword(newPassWord);
            userHelper.update(getUserFW());
            alert.alertSuccess("New password has been sent to your email, please sign in now !!");
            Navigator.getInstance().goToLogin();
        } else {
            alert.alertError("please check the verification code in your email again !!");
        }
    }

    @FXML
    void btnResend(ActionEvent event) {

    }

    @FXML
    void btnActionSearch(ActionEvent event) throws  UnsupportedEncodingException, javax.mail.MessagingException {
        if (!checkValid()) {
            txt_alert.setText(error.get("email"));
            MyNotify.MyNotifyAlertError( error.get("email"));
        } else {
            btnSearch.setVisible(false);
            btnReset.setVisible(true);
            btnEmail.setVisible(false);
            btnCode.setVisible(true);
            btnChangeInfo.setVisible(true);
            textResendCode.setVisible(true);
            bnResend.setVisible(true);
            code = mail.doEmailVerification(this.getUserFW().getContact());
            MyNotify.MyNotifyAlert("Your verification code have been sent to email !!! please check your email !!!");
            txt_alert.setText("");
            textFw.setText("Please Enter the verification code sent to your email !!");

        }

    }

    @FXML
    void btnregisterclick(ActionEvent event) throws IOException {
        Navigator.getInstance().goToRegistration(null);

    }

    public void initialize(String email) {
        if (!email.equals("")) {
            btnEmail.setText(email);
        }
        btnReset.setVisible(false);
        btnCode.setVisible(false);
        textResendCode.setVisible(false);
        bnResend.setVisible(false);
        btnChangeInfo.setVisible(false);

        btnEmail.textProperty().addListener((observable, oldValue, newValue) -> {
            txt_alert.setText("");
            if (newValue.isEmpty()) {
                txt_alert.setText("Email cannot be blank");

            } else if (!Validate.isValidEmailAddress(newValue.trim())) {
                txt_alert.setText("Email is not valid");
            }
        });
        Validate.validateCodeEmail(btnCode, txt_alert);
    }

    private boolean checkValid() {
        boolean flag = true;
        error.clear();
        if (btnEmail.isVisible()) {
            if (btnEmail.getText().isEmpty()) {
                flag = false;
                error.put("email", "email cannot be blank");
            } else if (!Validate.isValidEmailAddress(btnEmail.getText().trim())) {
                flag = false;
                error.put("email", "email is not valid");
            } else {
                User userEmailExist = userHelper.selectUserByEmail(btnEmail.getText().trim());
                if (userEmailExist == null) {
                    error.put("email", "Email does not exists ! please check again !!");
                    flag = false;
                } else {
                    this.setUserFW(userEmailExist);
                }
            }
        }
        return flag;
    }
}
