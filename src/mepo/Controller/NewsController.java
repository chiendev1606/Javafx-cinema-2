/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mepo.Controller;

import java.io.IOException;

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
import mepo.Components.Product;
import mepo.Components.User;
import mepo.Navigator;

public class NewsController {

    private User user;

    @FXML
    private MenuButton mbtnUser;
    @FXML
    private MenuItem mi1;
    @FXML
    private MenuItem mi2;
    @FXML
    private Label txtSll;

    public void initialize(User username) {
        this.user = username;

        setSLL();
        ObservableList<Product> result = HomeController.orderList;
        result.addListener(new ListChangeListener<Product>() {
            @Override
            public void onChanged(javafx.collections.ListChangeListener.Change<? extends Product> c) {
                if (c.next()) {
                    setSLL();
                }
            }
        });


    }

    void setSLL() {
        ObservableList<Product> result = HomeController.orderList;
        ObservableList<Product> rs = FXCollections.observableArrayList();
        int count = 0, sll = 0;
        for (Product f : result) {
            count = 0;
            sll++;
            for (Product ff : rs) {
                if (ff.getProductID() == f.getProductID()) {
                    count++;
                    sll -= count;
                }
            }
            if (count > 0) {
                continue;
            }
            rs.add(f);
        }
        String sss = String.valueOf(sll);
        txtSll.setText(sss);
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
    private void btnFeedBack(ActionEvent event) throws IOException {
        Navigator.getInstance().goToNews(user);
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
    private void mbtnUserClick(ActionEvent event) throws IOException {
    }

}
