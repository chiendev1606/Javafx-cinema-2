
package mepo.Controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import static javafx.scene.media.MediaPlayer.Status.PLAYING;

import javafx.scene.media.MediaView;
import javafx.scene.text.Text;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.util.Duration;
import mepo.Components.*;
import mepo.Implements.FeedbackImp;
import mepo.Helper.FeedbackHelper;
import mepo.Helper.ProductHelper;
import mepo.Implements.ProductImp;
import mepo.Navigator;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

public class Home_view2Controller {

    Product pro = null;
    ProductImp productHelper = new ProductHelper();
    User user = null;
    FeedbackImp FeedbackImp = new FeedbackHelper();


    @FXML
    private MediaView movie;

    @FXML
    private Label txtPrice;

    @FXML
    private Text txtDescription;

    @FXML
    private ImageView imgMovie;

    @FXML
    private JFXButton buynow;

    @FXML
    private GridPane gridFeedback;

    @FXML
    private Label txtProductName;

    @FXML
    private Label txtCategory;

    @FXML
    private Label txtSll;

    @FXML
    void btnActionMyMovies(ActionEvent event) throws IOException {
        webviewTrailer.getEngine().load(null);
        Navigator.getInstance().goToMyMovies(user);
    }

    @FXML
    void btnActiontuCoin(ActionEvent event) throws IOException {
        webviewTrailer.getEngine().load(null);

        Navigator.getInstance().gotoTopUpCoins(user);
    }


    @FXML
    void btnTransactions(ActionEvent event) throws IOException {
        webviewTrailer.getEngine().load(null);

        Navigator.getInstance().goToOrderHistory(user);
    }

    @FXML
    private WebView webviewTrailer;
    private File file;


    public void initialize(User user, Product product) {

        this.user = user;
        this.pro = product;
        addFeedback();
        WebEngine webEngine = webviewTrailer.getEngine();


        ObservableList<Product> result = HomeController.orderList;


        String str = productHelper.selectCategoryNameByID(product.getCategoryID());


        webEngine.load(product.getVideo() + "?rel=0&autoplay=1");

        txtProductName.setText(product.getProductName());
        txtCategory.setText(str);
        txtPrice.setText(Helper.formatString(Double.parseDouble(product.getPrice())));
        txtDescription.setText(product.getDescription());
        imgMovie.setImage(Helper.convertByteToImage(product.getImage(),imgMovie));

    }


    @FXML
    void miAccountClick(ActionEvent event) throws IOException {
        webviewTrailer.getEngine().load(null);

        Navigator.getInstance().goToProfileClient(user);
    }

    @FXML
    void miLogout(ActionEvent event) throws IOException {
        webviewTrailer.getEngine().load(null);
        Navigator.getInstance().goToLogin();
    }

    void addFeedback() {
        List<Feedback> result = FeedbackImp.selectFeedbackByProductID(pro.getProductID());
        int column = 1;
        int row = 0;
        try {
            for (Feedback f : result) {
                if (f.getSelect()) {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("/mepo/view/feedback.fxml"));
                    ++row;
                    Pane pane = fxmlLoader.load();
                    FeedbackController fbCTRL = fxmlLoader.getController();
                    fbCTRL.setData(f);
                    gridFeedback.add(pane, column, row);
                    GridPane.setMargin(pane, new Insets(5));
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }


    void resetTextFields(Label l) {
        l.setText("");
    }


    @FXML
    private void btnLogo(ActionEvent event) throws IOException {
        webviewTrailer.getEngine().load(null);
        Navigator.getInstance().goToHome(user);

    }

    @FXML
    private void btnGame(ActionEvent event) throws IOException {
        webviewTrailer.getEngine().load(null);
        Navigator.getInstance().goToHome(user);
    }

    @FXML
    private void btnFeedBack(ActionEvent event) throws IOException {
        webviewTrailer.getEngine().load(null);
        Navigator.getInstance().goToNews(user);
    }

    @FXML
    private void btnContact(ActionEvent event) throws IOException {
        webviewTrailer.getEngine().load(null);
        Navigator.getInstance().goToContact(user);
    }

    @FXML
    private void btnCart(ActionEvent event) throws IOException {
        webviewTrailer.getEngine().load(null);
        Navigator.getInstance().goToOrder(user);
    }


    @FXML
    private void btnAddToCart(ActionEvent event) throws IOException {
        if(CheckItemExist(pro)){
            MyNotify.MyNotifyAlert(pro.getProductName()+ " is added in your cart!!!");
        } else {
            HomeController.orderList.add(pro);
            MyNotify.MyNotifyAlert(pro.getProductName()+ " is added in your cart!!!");
        }
    }
    boolean CheckItemExist(Product product){
        for (Product product1 : HomeController.orderList){
            if(product.getProductID() == product1.getProductID()){
                return true;
            }
        }
        return false;
    }
    @FXML
    private void btnBuyNow(ActionEvent event) throws IOException {
        webviewTrailer.getEngine().load(null);
        if(!CheckItemExist(pro)){
            HomeController.orderList.add(pro);
        }
        Navigator.getInstance().goToOrder(user);
    }


}
