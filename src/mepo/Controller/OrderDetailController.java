/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mepo.Controller;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import mepo.Components.Helper;
import mepo.Components.Product;
import mepo.Components.User;
import mepo.Navigator;

import java.io.IOException;


public class OrderDetailController extends OrderController {

    private int currentValue;
    private User user = null;
    private Product productOrderDetail = null;
    private BooleanProperty selected = new SimpleBooleanProperty();

    @FXML
    public CheckBox cbSelect;
    @FXML
    private ImageView img;
    @FXML
    private Label txtPrice;

    @FXML
    private Label txtProductName;

    public void setData(Product product, boolean checked) {
        this.productOrderDetail = product;
        img.setImage(Helper.convertByteToImage(product.getImage(), img));
        txtProductName.setText(product.getProductName());
        cbSelect.setSelected(checked);
        cbSelect.setDisable(true);
        txtPrice.setText(Helper.formatString(Double.parseDouble(product.getPrice())));

    }
    @FXML
    void btnView(ActionEvent event) throws IOException {
        Navigator.getInstance().goToView2(HomeController.user, productOrderDetail);
    }

    @FXML
    void selectedItem(MouseEvent event) {
        if (cbSelect.isSelected()) {
            cbSelect.setSelected(false);
        } else if (!cbSelect.isSelected()) {
            cbSelect.setSelected(true);
        }
        if (!checkItemExistInListChecked(productOrderDetail)) {
            OrderController.listChecked.add(productOrderDetail);
        } else {
            OrderController.listChecked.removeAll(productOrderDetail);
        }

    }

    public void initialize(User user) {

    }

    @FXML
    private void btnDeleteClick(ActionEvent event) {
        try {
            for (Product p : HomeController.orderList) {
                if (p.getProductID() == productOrderDetail.getProductID()) {
                    HomeController.orderList.removeAll(p);
                    OrderController.listChecked.remove(p);
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}

