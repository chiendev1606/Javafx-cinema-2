/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mepo.Controller;

import java.io.IOException;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import mepo.Components.Helper;
import mepo.Components.Product;
import mepo.Components.User;
import mepo.Navigator;

public class ContactController {

    User user = null;

    @FXML
    private Label txtSll;
    @FXML
    private JFXButton textCoin;

    @FXML
    void btnActionCoin(ActionEvent event) {

    }

    public void initialize(User user) {
        this.user = user;
        textCoin.setText(Helper.formatString(user.getCoin()));
        HomeController.setSLL(txtSll);
        HomeController.orderList.addListener(new ListChangeListener<Product>() {
            @Override
            public void onChanged(javafx.collections.ListChangeListener.Change<? extends Product> c) {
                if (c.next()) {
                    HomeController.setSLL(txtSll);
                }
            }
        });


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
        Navigator.getInstance().goToProfileClient(user);
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
    void btnActionMyMovies(ActionEvent actionEvent) throws IOException {
        Navigator.getInstance().goToMyMovies(user);
    }
}
