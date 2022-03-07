
package mepo.Controller;

import com.jfoenix.controls.JFXButton;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import mepo.Components.Helper;
import mepo.Components.Order;
import mepo.Components.Product;
import mepo.Helper.OrderHelper;
import mepo.Components.OrderDetail;
import mepo.Helper.OrderDetailHelper;
import mepo.Implements.OrderDetailImp;
import mepo.Implements.OrderImp;


public class OrderItemAdminController {

//    public static BooleanProperty RMV = new SimpleBooleanProperty();

    private Order order = null;
    private OrderDetail orderDetail = null;
    private OrderDetailImp orderDetailHelper = new OrderDetailHelper();
    private OrderImp orderHelper = new OrderHelper();
    private Product product = null;
    private int currentValue;

    @FXML
    private ImageView img;
    @FXML
    private Text name;
    @FXML
    private JFXButton bDelete;

    @FXML
    private Label txtPrice;

    @FXML
    private Label txtPri;

    public void setData(OrderDetail orderDetail) {
        this.orderDetail = orderDetail;
        this.order = orderHelper.selectOrderByOrderID(this.orderDetail.getOrderID());
        this.product = orderDetailHelper.selectProductsByOrderProductsID(orderDetail.getProductID());
        bDelete.setText("");
//        RMV.set(false);

        txtPrice.setText(Helper.formatString(Double.parseDouble(product.getPrice())));
        name.setText(product.getProductName());
        img.setImage(Helper.convertByteToImage(product.getImage(), img));


    }

}
