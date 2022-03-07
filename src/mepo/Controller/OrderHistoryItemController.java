/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mepo.Controller;

import com.jfoenix.controls.JFXButton;

import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import mepo.Components.*;
import mepo.Helper.CategoryHelper;
import mepo.Helper.OrderHelper;
import mepo.Helper.OrderDetailHelper;
import mepo.Implements.CategoryImp;
import mepo.Navigator;


public class OrderHistoryItemController implements Initializable {


    private CategoryImp categoryHelper = new CategoryHelper();
    private HistoryProduct historyProduct = null;

    @FXML
    private Pane item;

    @FXML
    private Label txtPrice;

    @FXML
    private Label txtDate;

    @FXML
    private Label txtID;

    @FXML
    private ImageView txtImg;

    @FXML
    private Label txtCategory;

    @FXML
    private Label txtName;


    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    void btnView(MouseEvent event) throws IOException {
        Navigator.getInstance().gotoWatchMovie(historyProduct, HomeController.user);
    }

    public void setData(HistoryProduct product) {

        this.historyProduct = product;
        txtID.setText(String.valueOf(product.getProductID()));
        txtDate.setText(product.getDate());
        txtName.setText(product.getProductname());
        txtCategory.setText(categoryHelper.selectNameById(product.getCategoryID()));
        txtPrice.setText(Helper.formatString(Double.parseDouble(product.getPrice())));
        txtImg.setFitHeight(140);
        txtImg.setFitWidth(80);
        txtImg.setImage(Helper.convertByteToImage(product.getImage(), txtImg));

    }
}
