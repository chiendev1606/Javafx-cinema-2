/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mepo.Controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import mepo.Components.Helper;
import mepo.Components.MyNotify;
import mepo.Components.Product;
import mepo.Helper.ProductHelper;
import mepo.Navigator;
import mepo.Implements.ProductImp;

/**
 * @author admin
 */
public class MovieController {

    private ProductImp productHelper = new ProductHelper();
    Product product = null;

    @FXML
    private ImageView img;


    @FXML
    private Label price;


    public void setData(Product product) {
        this.product = product;
        img.setImage(Helper.convertByteToImage(this.product.getImage(), img));
        String str = productHelper.selectCategoryNameByID(product.getCategoryID());
        price.setText(Helper.formatString(Double.parseDouble(product.getPrice())));

    }

    @FXML
    void GameClicked(MouseEvent event) throws IOException {
        Navigator.getInstance().goToView2(HomeController.user, product);
    }

    boolean CheckItemExist(Product product) {
        for (Product product1 : HomeController.orderList) {
            if (product.getProductID() == product1.getProductID()) {
                return true;
            }
        }
        return false;
    }

    @FXML
    void btnAddToCart(ActionEvent event) throws IOException {
        if (CheckItemExist(product)) {
            MyNotify.MyNotifyAlertError(product.getProductName() + " is added in your cart!!!");
        } else {
            HomeController.orderList.add(product);
            MyNotify.MyNotifyAlert("Add " + product.getProductName() + " to cart successfully !!!");
        }


    }
}
