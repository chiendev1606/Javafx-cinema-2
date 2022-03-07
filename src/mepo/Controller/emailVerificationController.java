package mepo.Controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import mepo.Components.*;
import mepo.Helper.ClientHelper;
import mepo.Implements.UserImp;
import mepo.Navigator;

import java.io.IOException;
import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;


public class emailVerificationController {


    @FXML
    private JFXButton btnregister;

    @FXML
    private JFXTextField codeEmail;

    @FXML
    private Label textAlertCode;

    @FXML
    private Button bnResend;

    @FXML
    private JFXButton btnlogin;
    @FXML
    private JFXButton btnChangeInfo;

    private MyAlert alert = new MyAlert();

    @FXML
    void btnloginclick(ActionEvent event) throws IOException {
        Navigator.getInstance().goToLogin();
    }

    public static int count;
    private String code;
    private User userVerify;
    private UserImp userHelper = new ClientHelper();
    private Mail mail = new Mail();

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public User getUserVerify() {
        return userVerify;
    }

    public void setUserVerify(User userVerify) {
        this.userVerify = userVerify;
    }

    @FXML
    void btnResend(ActionEvent event) throws MessagingException, UnsupportedEncodingException {
        if (count < 2) {
            this.setCode(mail.doEmailVerification(userVerify.getContact()));
            MyNotify.MyNotifyAlert(" VERIFICATION CODE IS SENT!! PLEASE CHECK YOUR EMAIL!!");
            count++;
        } else {
            bnResend.setDisable(true);
            alert.alertError("There was a problem verifying your account.Check that verification code is invalid");
        }
    }

    @FXML
    void btnregisterclick(ActionEvent event) throws IOException {
        if (this.getCode().equals(codeEmail.getText().trim())) {
            userVerify.setCoin((double) 100);
            userHelper.insert(this.getUserVerify());
            this.setUserVerify(null);
            this.setCode("");
            alert.alertSuccess("successful registration, please login now !!");
            MyNotify.MyNotifyAlert(" WELCOME TO MEPO CINEMA !! YOU ARE GIVEN 100 COINS !!!");
            Navigator.getInstance().goToLogin();
        } else {
            codeEmail.setText("");
            MyNotify.MyNotifyAlertError("VERIFICATION CODE IS INVALID !!! PLEASE TRY AGAIN !!!");
        }

    }

    @FXML
    void btnAcTionChangeInfo(ActionEvent event) throws IOException {
        Navigator.getInstance().goToRegistration(this.getUserVerify());
    }

    public void initialize(User user) throws MessagingException, UnsupportedEncodingException {
        this.setUserVerify(user);
        this.setCode(mail.doEmailVerification(user.getContact()));
        MyNotify.MyNotifyAlert(" VERIFICATION CODE IS SENT!! PLEASE CHECK YOUR EMAIL!!");
        count = 0;
        Validate.validateCodeEmail(codeEmail, textAlertCode);

    }

}
