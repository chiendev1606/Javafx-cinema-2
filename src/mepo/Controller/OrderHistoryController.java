
package mepo.Controller;

import com.jfoenix.controls.JFXButton;

import java.io.IOException;
import java.util.Comparator;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import mepo.Components.*;
import mepo.Helper.OrderHelper;
import mepo.Helper.ProductHelper;
import mepo.Implements.ProductImp;
import mepo.Navigator;


public class OrderHistoryController {

    mepo.Implements.OrderImp OrderImp = new OrderHelper();
    ProductImp productHelper = new ProductHelper();
    private User user = null;

    @FXML
    private MenuButton mbtnUser;
    @FXML
    private MenuItem mi1;
    @FXML
    private MenuItem mi2;
    @FXML
    private JFXButton btnProfile;
    @FXML
    private GridPane gridOrderHistory;
    @FXML
    private Label txtSll;
    @FXML
    private Label txtMMM;
    @FXML
    private JFXButton textCoin;

    public void initialize(User userOrderHistory) {
        user = userOrderHistory;
        ObservableList<HistoryProduct> result = productHelper.selectProductboughtByUser(user.getUsername());
        addOrder(result);
        textCoin.setText(Helper.formatString(user.getCoin()));
        HomeController.setSLL(txtSll);
    }


    void addOrder(ObservableList<HistoryProduct> list) {
        gridOrderHistory.getChildren().clear();
        int column = 1;
        int row = 0;
        try {
            for (HistoryProduct product : list) {
                ++row;
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/mepo/view/orderHistoryItem.fxml"));
                Pane pane = fxmlLoader.load();
                OrderHistoryItemController controller = fxmlLoader.getController();
                controller.setData(product);
                gridOrderHistory.add(pane, column, row);
                GridPane.setMargin(pane, new Insets(5));
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
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
    private void mbtnUserClick(ActionEvent event) {
    }

    @FXML
    void btnCardHistory(ActionEvent event) throws IOException {
        Navigator.getInstance().goToCardHistory(user);
    }

    @FXML
    private void btnProfileClick(ActionEvent event) throws IOException {
        Navigator.getInstance().goToProfileClient(user);
    }

    @FXML
    private void btnChangePassword(ActionEvent event) throws IOException {
        Navigator.getInstance().goToProfileChange(user);
    }

    @FXML
    private void btnHistory(ActionEvent event) throws IOException {
        Navigator.getInstance().goToOrderHistory(user);
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
    void btnActionMyMovies(ActionEvent actionEvent) throws IOException {
        Navigator.getInstance().goToMyMovies(user);
    }
    @FXML
    void btnActionCoin(ActionEvent actionEvent) {
    }
}
