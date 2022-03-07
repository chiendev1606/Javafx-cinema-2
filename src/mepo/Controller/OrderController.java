
package mepo.Controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import java.io.IOException;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import mepo.Components.*;
import mepo.Helper.ClientHelper;
import mepo.Helper.OrderHelper;
import mepo.Helper.OrderDetailHelper;
import mepo.Implements.*;
import mepo.Navigator;


public class OrderController {

    private OrderDetailImp orderDetailHelper = new OrderDetailHelper();
    private OrderImp orderHelper = new OrderHelper();
    private UserImp clientHelper = new ClientHelper();

    public static int uuu = 0;
    User user = null;
    public static ObservableList<Product> listChecked = FXCollections.observableArrayList();
    private static double total;
    private ObjectProperty<Integer> isSelectAll = new SimpleObjectProperty<>();

    @FXML
    private Label txtPrice;
    @FXML
    private CheckBox btnSelectAll;
    @FXML
    public Label txtTotalPayment;
    @FXML
    private GridPane gridCart;
    @FXML
    private JFXTextField txtAccountAddress;
    @FXML
    private JFXButton textCoin;


    @FXML
    void btnActionCoin(ActionEvent event) {

    }


    public void initialize(User user) {
        txtAccountAddress.setVisible(false);
        this.user = user;
        textCoin.setText(Helper.formatString(user.getCoin()));
        getOrder(false);
        txtAccountAddress.setText(user.getContact());
        listChecked.addListener(new ListChangeListener<Product>() {
            @Override
            public void onChanged(javafx.collections.ListChangeListener.Change<? extends Product> c) {
                if (c.next()) {
                    isSelectAll.set(HomeController.orderList.size() - listChecked.size());
                    float sum = 0;
                    for (Product productChecked : listChecked) {
                        sum += Float.parseFloat(productChecked.getPrice());
                     }
                        total = sum;
                        txtPrice.setText(Helper.formatString(sum));
                    }
                }

            });

        HomeController.orderList.addListener(new ListChangeListener<Product>() {
            @Override
            public void onChanged(Change<? extends Product> c) {
                if(c.next()){
                    getOrder(false);
                }
            }
        });

        btnSelectAll.selectedProperty().addListener(((observable, oldValue, newValue) -> {
            if(newValue){
              listChecked.clear();
              listChecked.addAll(HomeController.orderList);
                getOrder(true);
            }
            if(!newValue){
                listChecked.clear();
                getOrder(false);
            }
        }));

}
    public boolean checkItemExistInListChecked(Product product) {
        for (Product product1 : OrderController.listChecked) {
            if (product1.getProductID() == product.getProductID()) {
                return true;
            }
        }
        return false;
    }

    private void getOrder(boolean checked) {
        try {
            gridCart.getChildren().clear();
            int column = 1, row = 0;
            for (Product product : HomeController.orderList) {
                ++row;
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/mepo/view/orderDetail.fxml"));
                Pane pane = fxmlLoader.load();
                OrderDetailController orderDetailController = fxmlLoader.getController();
                orderDetailController.setData(product, checked);
                gridCart.add(pane, column, row);
                GridPane.setMargin(pane, new Insets(5));
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
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
    private void btnSelectAllClick(ActionEvent event) {
    }

    @FXML
    private void btnDelete(ActionEvent event) {
        HomeController.orderList.removeAll(listChecked);
        listChecked.clear();
    }

    private MyAlert alert = new MyAlert();

    @FXML
    private void btnPurchase(ActionEvent event) throws IOException {
        if (!listChecked.isEmpty()) {
            if (total <= user.getCoin()) {
                    Order order = extractTeacherFromFields();
                    if(order == null){
                        MyNotify.MyNotifyAlertError("PLEASE ENTER YOUR EMAIL !!!! ");
                        return;
                    }
                if (this.alert.alertComfirm("DO YOU REALLY WANT TO BUY ?") == ButtonType.YES) {
                    order = orderHelper.insert(order);
                    for (Product product : listChecked) {
                        OrderDetail orderDetail = new OrderDetail();
                        orderDetail.setProductID(product.getProductID());
                        orderDetail.setOrderID(order.getOrderID());
                        orderDetailHelper.insert(orderDetail);
                    }
                    double totalCoin = user.getCoin() - total;
                    textCoin.setText(Helper.formatString(totalCoin));
                    user.setCoin(totalCoin);
                    clientHelper.update(user);
                    total = 0;
                    HomeController.orderList.removeAll(listChecked);
                    listChecked.clear();
                    Navigator.getInstance().goToOrder(user);
                    MyNotify.MyNotifyAlert("PAYMENT SUCCESSFUL!!!, THANK YOU FOR SUPPORTING US!!!");
                }
            } else {
                this.alert.alertError("Your coins are not enough to pay, please add more coins!");
            }
        } else {
            this.alert.alertError("Please choose at least one movie");
        }
    }

    private Order extractTeacherFromFields() {
        String date = java.time.LocalDate.now().toString();
        Order order = new Order();
        if(txtAccountAddress.getText().isEmpty()){
            return null;
        }
        order.setUsername(user.getUsername());
        order.setAccountAddress(txtAccountAddress.getText());
        order.setOrderDate(date);

        return order;
    }

    @FXML
    void btnActionMyMovies(ActionEvent actionEvent) throws IOException {
        Navigator.getInstance().goToMyMovies(user);
    }
}
