/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mepo.Controller;

import com.jfoenix.controls.JFXButton;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.control.Button;
import javafx.scene.control.Label;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import mepo.Components.Helper;
import mepo.Components.Product;
import mepo.Helper.ProductHelper;
import mepo.Implements.ProductImp;
import mepo.Components.User;
import mepo.Navigator;


public class Product_viewController {

    private Product productCurrent = null;
    private User user = null;
    private ProductImp productHelper = new ProductHelper();

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
    private Button btnBack;

    @FXML
    private Label txtProductName;

    @FXML
    private Label tvtCategoryName;

    @FXML
    private Label txtPrice;

    @FXML
    private Label txtUsername;

    @FXML
    private Text txtDescription;

    @FXML
    private ImageView img1;

    @FXML
    private WebView videoTrailer;

    @FXML
    private WebView fullMovie;
    private WebEngine webEngineTrailer;
    private WebEngine webEngineFullMovie;


    public void initialize(User user, Product product) {

        this.productCurrent = product;
        this.user = user;

        txtUsername.setText("User: " + this.user.getUsername());
        txtProductName.setText(productCurrent.getProductName());
        txtPrice.setText(Helper.formatString(Double.parseDouble(productCurrent.getPrice())) + " coins");
        txtDescription.setText(productCurrent.getDescription());

        this.webEngineFullMovie = fullMovie.getEngine();
        this.webEngineFullMovie.load(productCurrent.getUrl());

        this.webEngineTrailer = videoTrailer.getEngine();
        this.webEngineTrailer.load(productCurrent.getVideo() + "?rel=0&autoplay=1");

        tvtCategoryName.setText(productHelper.selectCategoryNameByID(productCurrent.getCategoryID()));
        img1.setImage(Helper.convertByteToImage(productCurrent.getImage(), img1));
        img1.setStyle("-fx-effect:dropShadow(three-pass-box,rgba(45,49,56,1),10,0,0,0);");
    }

    @FXML
    void logoClicked(MouseEvent event) throws IOException {
        this.webEngineTrailer.load(null);
        this.webEngineFullMovie.load(null);
        Navigator.getInstance().goToHomeAdmin(user);
    }

    @FXML
    private void btnHomeClick(ActionEvent event) throws IOException {
        this.webEngineTrailer.load(null);
        this.webEngineFullMovie.load(null);
        Navigator.getInstance().goToHomeAdmin(user);
    }

    @FXML
    void btnActionTuCoins(ActionEvent actionEvent) throws IOException {
        this.webEngineTrailer.load(null);
        this.webEngineFullMovie.load(null);
        Navigator.getInstance().goToDialogHistoryAdmin(user);
    }

    @FXML
    void btnActionCard(ActionEvent actionEvent) throws IOException {
        this.webEngineTrailer.load(null);
        this.webEngineFullMovie.load(null);
        Navigator.getInstance().goToCardAdmin(user);
    }

    @FXML
    private void btnAdminClick(ActionEvent event) throws IOException {
        this.webEngineTrailer.load(null);
        this.webEngineFullMovie.load(null);
        Navigator.getInstance().goToAdmin(user);
    }

    @FXML
    private void btnCustomerClick(ActionEvent event) throws IOException {
        this.webEngineTrailer.load(null);
        this.webEngineFullMovie.load(null);
        Navigator.getInstance().goToCustomer(user);
    }

    @FXML
    private void btnCategoryClick(ActionEvent event) throws IOException {
        this.webEngineTrailer.load(null);
        this.webEngineFullMovie.load(null);
        Navigator.getInstance().goToCategory(user);
    }

    @FXML
    private void btnProductClick(ActionEvent event) throws IOException {
        this.webEngineTrailer.load(null);
        this.webEngineFullMovie.load(null);
        Navigator.getInstance().goToProducts(user);
    }

    @FXML
    private void btnOrderClick(ActionEvent event) throws IOException {
        this.webEngineTrailer.load(null);
        this.webEngineFullMovie.load(null);
        Navigator.getInstance().goToOrderAdmin(user);
    }

    @FXML
    private void btnFeedbackClick(ActionEvent event) throws IOException {
        this.webEngineTrailer.load(null);
        this.webEngineFullMovie.load(null);
        Navigator.getInstance().goToFeedbackAdmin(user);
    }

    @FXML
    private void btnLogoutClick(ActionEvent event) throws IOException {
        this.webEngineTrailer.load(null);
        this.webEngineFullMovie.load(null);
        Navigator.getInstance().goToLoginAdmin();
    }

    @FXML
    private void btnBackClick(ActionEvent event) throws IOException {
        this.webEngineTrailer.load(null);
        this.webEngineFullMovie.load(null);
        Navigator.getInstance().goToProducts(user);
    }


}
