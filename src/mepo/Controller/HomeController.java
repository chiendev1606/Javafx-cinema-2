
package mepo.Controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXSlider;
import com.jfoenix.controls.JFXTextField;

import java.io.IOException;
import java.util.List;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import mepo.Components.Category;
import mepo.Components.Helper;
import mepo.Components.Product;
import mepo.Helper.CategoryHelper;
import mepo.Helper.ProductHelper;
import mepo.Components.User;
import mepo.Navigator;
import mepo.Implements.ProductImp;
import mepo.Implements.CategoryImp;


public class HomeController {

    public static ObservableList<Product> orderList = FXCollections.observableArrayList();

    public static User user;
    private List<Product> Product;
    private ProductImp productHelper = new ProductHelper();
    private CategoryImp categoryHelper = new CategoryHelper();

    @FXML
    private JFXTextField txtSearch;
    @FXML
    private GridPane mainGrid;

    @FXML
    private JFXComboBox<String> txtCategory;


    @FXML
    private Label txtSll;

    @FXML
    private JFXButton textCoin;


    @FXML
    private JFXSlider sliderPrice;

    @FXML
    private Label sliderLabel;

    @FXML
    void btnTransactions(ActionEvent event) throws IOException {
        Navigator.getInstance().goToOrderHistory(user);
    }

    @FXML
    void btnActiontuCoin(ActionEvent event) throws IOException {
        Navigator.getInstance().gotoTopUpCoins(user);
    }


    public void initialize(User user) {
        this.user = user;
        int maxPrice = productHelper.findMaxPrice();
        setSLL(txtSll);
        sliderPrice.setMin(0);
        sliderPrice.setMax(maxPrice);
        sliderPrice.setValue(maxPrice);
        sliderLabel.setText("MAX: " + maxPrice);
        HomeController.orderList.addListener((ListChangeListener<mepo.Components.Product>) c -> {
            if (c.next()) {
                setSLL(txtSll);
            }
        });

        ObservableList<Category> rs = categoryHelper.selectAll();
        ObservableList<String> list = FXCollections.observableArrayList();
        list.add("All");
        for (Category c : rs) {
            list.add(c.getCategoryName());
        }
        txtCategory.setItems(list);
        textCoin.setText(Helper.formatString(user.getCoin()));

        getProductNotBought();
        searchByName();
        searchByPrice();

    }

    public static void setSLL(Label label) {
        label.setText(Helper.formatString(HomeController.orderList.size()));
    }

    public User getUser() {
        return this.user;
    }

    public List<Product> getLists() {
        return this.orderList;
    }

    public void addProductListToCart(Product p) {
        orderList.add(p);
    }

    private void searchByName() {
        txtSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            txtCategory.setValue("All");
            List<Product> result = productHelper.searchProduct(newValue, user.getUsername());
            displayMovie(result);
        });
    }

    private void searchByPrice() {
        sliderPrice.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                sliderLabel.setText("MAX: " + Helper.formatString(sliderPrice.getValue()));
                txtCategory.setValue("All");
                txtSearch.setText("");
                List<Product> result = productHelper.searchProductByPrice(sliderPrice.getValue(), user.getUsername());
                displayMovie(result);
            }
        });
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
    private void cbCategory(ActionEvent event) {
        mainGrid.getChildren().clear();
        if (txtCategory.getValue().equals("All")) {
            getProductNotBought();
        } else {
            Category cate = categoryHelper.selectCategoryName(txtCategory.getValue());
            List<Product> result = productHelper.selectAllProductByCateID(cate.getCategoryID(), user.getUsername());
            displayMovie(result);
        }
    }

    private void getProductNotBought() {
        List<Product> result = productHelper.selectAllExcept(user.getUsername());
        displayMovie(result);
    }

    private void displayMovie(List<Product> result) {
        mainGrid.getChildren().clear();
        int column = 0;
        int row = 1;
        try {
            for (Product pro : result) {

                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/mepo/view/movie.fxml"));
                Pane pane = fxmlLoader.load();
                MovieController movieCTRL = fxmlLoader.getController();
                movieCTRL.setData(pro);

                if (column == 4) {
                    column = 0;
                    ++row;
                }
                mainGrid.add(pane, column++, row);
                GridPane.setMargin(pane, new Insets(10, 10, 15, 10));
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    void btnActionMyMovies(ActionEvent actionEvent) throws IOException {
        Navigator.getInstance().goToMyMovies(user);
    }
}
