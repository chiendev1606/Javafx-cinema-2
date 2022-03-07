
package mepo;

import java.io.IOException;
import java.sql.SQLException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import mepo.Components.HistoryProduct;
import mepo.Controller.*;
import mepo.Components.Product;
import mepo.Components.User;

import javax.mail.MessagingException;


public class Navigator {

    public static final String LOGIN_ADMIN = "/mepo/view/login_admin.fxml";
    public static final String LOGIN = "/mepo/view/login.fxml";
    public static final String REGISTRATION = "/mepo/view/registration.fxml";
    public static final String HOME_ADMIN = "/mepo/view/home_admin.fxml";
    public static final String PRODUCTS = "/mepo/view/products.fxml";
    public static final String PRODUCT_EDIT = "/mepo/view/product_edit.fxml";
    public static final String PRODUCT_VIEW = "/mepo/view/product_view.fxml";
    public static final String CATEGORY = "/mepo/view/category.fxml";
    public static final String ADMIN = "/mepo/view/admin.fxml";
    public static final String ADMIN_EDIT = "/mepo/view/admin_edit.fxml";
    public static final String ADMIN_CREATE = "/mepo/view/admin_create.fxml";
    public static final String CUSTOMER = "/mepo/view/customer.fxml";
    public static final String CUSTOMER_EDIT = "/mepo/view/customer_edit.fxml";
    public static final String CUSTOMER_CREATE = "/mepo/view/customer_create.fxml";
    public static final String HOME = "/mepo/view/home.fxml";
    public static final String VIEW_2 = "/mepo/view/home_view2.fxml";
    public static final String FEEDBACK_ADMIN = "/mepo/view/feedbackAdmin.fxml";
    public static final String ORDER_CUSTOMER = "/mepo/view/order.fxml";
    public static final String ORDER_ADMIN = "/mepo/view/orderAdmin.fxml";
    public static final String NEWS = "/mepo/view/news.fxml";
    public static final String CONTACT = "/mepo/view/contact.fxml";
    public static final String PROFILE_CLIENT = "/mepo/view/profileInfo.fxml";
    public static final String PROFILE_CHANGE = "/mepo/view/profileChangePw.fxml";
    public static final String ORDER_HISTORY = "/mepo/view/orderHistory.fxml";
    public static final String EMAIL_VERIFY = "/mepo/view/emailVerification.fxml";
    public static final String FORGET_PW = "/mepo/view/forgetPw.fxml";
    public static final String TOPUP_COIN = "/mepo/view/topUpCoins.fxml";
    public static final String MYMOVIES = "/mepo/view/myMovies.fxml";
    public static final String WATCH_MOVIE = "/mepo/view/Home_movieBuy.fxml";
    public static final String CARD_HISTORY = "/mepo/view/CardHistory.fxml";
    public static final String DIALOG_HISTORY = "/mepo/view/DialogHistoryAdmin.fxml";
    public static final String CARD_ADMIN = "/mepo/view/CardAdmin.fxml";

    private FXMLLoader loader;
    private Stage stage;

    public static Navigator nav = null;

    public Navigator() {

    }

    public static Navigator getInstance() {
        if (nav == null) {
            nav = new Navigator();
        }
        return nav;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    Stage getStage() {
        return this.stage;
    }

    private void goTo(String fxml) throws IOException {
        this.loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(fxml));
        stage.getIcons().add(new Image("/img/icon/cinema-reel.png"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/mepo/view/Style.css").toExternalForm());
        this.stage.setScene(scene);
    }

    public void goToLogin() throws IOException {
        this.goTo(LOGIN);
    }

    public void goToLoginAdmin() throws IOException {
        this.goTo(LOGIN);
    }
    public void goToCardHistory(User user) throws IOException {
        this.goTo(CARD_HISTORY);
        CardHistoryController ctrl = loader.getController();
        ctrl.initialize(user);
    }
    public void goToDialogHistoryAdmin(User user) throws IOException {
        this.goTo(DIALOG_HISTORY);
        DialogHistoryController ctrl = loader.getController();
        ctrl.initialize(user);
    }
    public void goToCardAdmin(User user) throws IOException {
        this.goTo(CARD_ADMIN);
        CardAdminContrller ctrl = loader.getController();
        ctrl.initialize(user);
    }
    public void goToMyMovies(User user) throws IOException {
        this.goTo(MYMOVIES);
        MyMoviesController ctrl = loader.getController();
        ctrl.initialize(user);
    }
    public void gotoForgetPw(String email) throws IOException {
        this.goTo(FORGET_PW);
        ForgetPWController ctrl = loader.getController();
        ctrl.initialize(email);
    }

    public void goToRegistration(User user) throws IOException {
        this.goTo(REGISTRATION);
        RegistrationController ctrl = loader.getController();
        ctrl.initialize(user);
    }
    public void gotoTopUpCoins(User user) throws IOException {
        this.goTo(TOPUP_COIN);
        TopUpCoinController ctrl = loader.getController();
        ctrl.initialize(user);
    }
    public void gotoWatchMovie(HistoryProduct product, User user) throws IOException {
        this.goTo(WATCH_MOVIE);
        Home_moviebuyController ctrl = loader.getController();
        ctrl.initialize(product, user);
    }

    public void goToHomeAdmin(User username) throws IOException {
        this.goTo(HOME_ADMIN);
        HomeAdminController ctrl = loader.getController();
        ctrl.initialize(username);
    }

    public void goToEmailVerify(User user) throws IOException, MessagingException {
        this.goTo(EMAIL_VERIFY);
        emailVerificationController ctrl = loader.getController();
        ctrl.initialize(user);

    }

    public void goToProducts(User username) throws IOException {
        this.goTo(PRODUCTS);
        ProductsController ctrl = loader.getController();
        ctrl.initialize(username);
    }

    public void goToProductEdit(User username, Product pro) throws IOException, SQLException {
        this.goTo(PRODUCT_EDIT);
        Products_editController ctrl = loader.getController();
        ctrl.initialize(username, pro);
    }

    public void goToProductView(User username, Product pro) throws IOException {
        this.goTo(PRODUCT_VIEW);
        Product_viewController ctrl = loader.getController();
        ctrl.initialize(username, pro);
    }

    public void goToCategory(User username) throws IOException {
        this.goTo(CATEGORY);
        CategoryController ctrl = loader.getController();
        ctrl.initialize(username);
    }

    public void goToAdmin(User username) throws IOException {
        this.goTo(ADMIN);
        AdminController ctrl = loader.getController();
        ctrl.initialize(username);
    }

    public void goToAdminEdit(User username1, User username2) throws IOException {
        this.goTo(ADMIN_EDIT);
        Admin_editController ctrl = loader.getController();
        ctrl.initialize(username1, username2);
    }

    public void goToAdminCreate(User username1) throws IOException {
        this.goTo(ADMIN_CREATE);
        Admin_createController ctrl = loader.getController();
        ctrl.initialize(username1);
    }

    public void goToCustomer(User username) throws IOException {
        this.goTo(CUSTOMER);
        CustomerController ctrl = loader.getController();
        ctrl.initialize(username);
    }

    public void goToCustomerCreate(User username1) throws IOException {
        this.goTo(CUSTOMER_CREATE);
        Customer_createController ctrl = loader.getController();
        ctrl.initialize(username1);
    }

    public void goToCustomerEdit(User username1, User username2) throws IOException {
        this.goTo(CUSTOMER_EDIT);
        Customer_editController ctrl = loader.getController();
        ctrl.initialize(username1, username2);
    }

    public void goToHome(User username) throws IOException {
        this.goTo(HOME);
        HomeController ctrl = loader.getController();
        ctrl.initialize(username);
    }

    public void goToView2(User username, Product pro) throws IOException {
        this.goTo(VIEW_2);
        Home_view2Controller ctrl = loader.getController();
        ctrl.initialize(username, pro);
    }

    public void goToFeedbackAdmin(User username) throws IOException {
        this.goTo(FEEDBACK_ADMIN);
        FeedbackAdminController ctrl = loader.getController();
        ctrl.initialize(username);
    }

    public void goToOrder(User username) throws IOException {
        this.goTo(ORDER_CUSTOMER);
        OrderController ctrl = loader.getController();
        ctrl.initialize(username);
    }

    public void goToOrderAdmin(User username) throws IOException {
        this.goTo(ORDER_ADMIN);
        OrderAdminController ctrl = loader.getController();
        ctrl.initialize(username);
    }

    public void goToNews(User username) throws IOException {
        this.goTo(NEWS);
        NewsController ctrl = loader.getController();
        ctrl.initialize(username);
    }

    public void goToContact(User username) throws IOException {
        this.goTo(CONTACT);
        ContactController ctrl = loader.getController();
        ctrl.initialize(username);
    }

    public void goToProfileClient(User username) throws IOException {
        this.goTo(PROFILE_CLIENT);
        ProfileInfoController ctrl = loader.getController();
        ctrl.initialize(username);
    }

    public void goToProfileChange(User username) throws IOException {
        this.goTo(PROFILE_CHANGE);
        profileChangePwController ctrl = loader.getController();
        ctrl.initialize(username);
    }

    public void goToOrderHistory(User username) throws IOException {
        this.goTo(ORDER_HISTORY);
        OrderHistoryController ctrl = loader.getController();
        ctrl.initialize(username);
    }
}
