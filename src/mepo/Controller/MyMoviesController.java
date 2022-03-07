package mepo.Controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
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
import mepo.Components.*;
import mepo.Helper.CategoryHelper;
import mepo.Helper.ProductHelper;
import mepo.Implements.CategoryImp;
import mepo.Implements.ProductImp;
import mepo.Navigator;

import java.io.IOException;
import java.util.List;

public class MyMoviesController {

    private User user = null;
    private CategoryImp categoryHelper = new CategoryHelper();
    private ProductImp productHelper = new ProductHelper();


    @FXML
    private JFXComboBox<String> txtCategory;

    @FXML
    private JFXTextField txtSearch;

    @FXML
    private GridPane mainGrid;

    @FXML
    private Label txtSll;

    @FXML
    private JFXButton textCoin;

    @FXML
    void btnActionCoin(ActionEvent event) {

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
    void btnCart(ActionEvent event) throws IOException {
        Navigator.getInstance().goToOrder(user);
    }

    @FXML
    void btnContact(ActionEvent event) throws IOException {
        Navigator.getInstance().goToContact(user);

    }

    @FXML
    private void btnGame(ActionEvent event) throws IOException {
        Navigator.getInstance().goToHome(user);
    }



    @FXML
    private void cbCategory(ActionEvent event) {
        mainGrid.getChildren().clear();
        if (txtCategory.getValue().equals("All")) {
            displayAllMyMovies();
        } else {
            Category cate = categoryHelper.selectCategoryName(txtCategory.getValue());
            List<HistoryProduct> result = productHelper.selectMovieByCateID(cate.getCategoryID(), user.getUsername());
            int column = 0;
            int row = 1;
            try {
                for (HistoryProduct pro : result) {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("/mepo/view/movieBuy.fxml"));

                    Pane pane = fxmlLoader.load();
                    MovieBuyController movieCTRL = fxmlLoader.getController();
                   movieCTRL.setData(pro, this.user);


                    if (column == 4) {
                        column = 0;
                        ++row;
                    }

                    mainGrid.add(pane, column++, row);
                    GridPane.setMargin(pane, new Insets(10));

                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
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
    void txtSearchClick(ActionEvent event) {

    }



    public void initialize(User user) {
        this.user = user;
        textCoin.setText(Helper.formatString(user.getCoin()));
        HomeController.orderList.addListener((ListChangeListener<Product>) c -> {
            if (c.next()) {
                HomeController.setSLL(txtSll);
            }
        });
        ObservableList<Category> rs = categoryHelper.selectAll();
        ObservableList<String> list = FXCollections.observableArrayList();
        list.add("All");
        for (Category c : rs) {
            list.add(c.getCategoryName());
        }
        txtCategory.setItems(list);
        searchByName();
        displayAllMyMovies();
        HomeController.setSLL(txtSll);
    }
    private void searchByName() {
        txtSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            txtCategory.setValue("All");
            mainGrid.getChildren().clear();
            List<HistoryProduct> result = productHelper.searchMovieBuy(newValue, user.getUsername());
            int column = 0;
            int row = 1;
            try {
                for (HistoryProduct product : result) {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("/mepo/view/movieBuy.fxml"));

                    Pane pane = fxmlLoader.load();
                    MovieBuyController movieCTRL = fxmlLoader.getController();
                    movieCTRL.setData(product, this.user);

                    if (column == 4) {
                        column = 0;
                        ++row;
                    }

                    mainGrid.add(pane, column++, row);
                    GridPane.setMargin(pane, new Insets(10));
                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }

        });
    }
    private void displayAllMyMovies() {
        List<HistoryProduct> result = productHelper.selectProductboughtByUser(user.getUsername());
        int column = 0;
        int row = 1;
        try {
            for (HistoryProduct product : result) {

                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/mepo/view/movieBuy.fxml"));
                Pane pane = fxmlLoader.load();
                MovieBuyController movieCTRL = fxmlLoader.getController();
                movieCTRL.setData(product, this.user);

                if (column == 4) {
                    column = 0;
                    ++row;
                }
                mainGrid.add(pane, column++, row);
                GridPane.setMargin(pane, new Insets(10));
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
