/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mepo.Controller;

import com.jfoenix.controls.JFXTextArea;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import mepo.Components.Feedback;
import mepo.Components.MyNotify;
import mepo.Helper.FeedbackHelper;
import mepo.Components.User;

/**
 * FXML Controller class
 *
 * @author Dell
 */
public class FeedbackCustomerController {

    mepo.Implements.FeedbackImp FeedbackImp = new FeedbackHelper();
    User user = null;

    @FXML
    private MenuButton mbtnUser;
    @FXML
    private MenuItem mi1;
    @FXML
    private MenuItem mi2;
    private JFXTextArea txtFeedback;

    /**
     * Initializes the controller class.
     */
    public void initialize(User username) {
        this.user = username;
        System.out.println(user.getUsername());
    }

    @FXML
    private void btnLogo(ActionEvent event) {
    }

    @FXML
    private void btnGame(ActionEvent event) {
    }

    @FXML
    private void btnFeedBack(ActionEvent event) {
    }

    @FXML
    private void btnContact(ActionEvent event) {
    }

    @FXML
    private void btnCart(ActionEvent event) {
    }

    @FXML
    private void mbtnUserClick(ActionEvent event) {
    }

    private void btnSubmit(ActionEvent event) {
        Feedback f = extractTeacherFromFields();

        f = FeedbackImp.insert(f);
        MyNotify.MyNotifyAlert("Thank you for your feedback!!");
    }

    private Feedback extractTeacherFromFields() {
        String date = java.time.LocalDate.now().toString();
        Feedback f = new Feedback();
        f.setUsername(user.getUsername());
        f.setContent(txtFeedback.getText());
        f.setDate(date);
        return f;
    }



}
