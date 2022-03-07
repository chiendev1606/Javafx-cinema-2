/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mepo.Controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import java.io.IOException;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;
import mepo.Components.Helper;
import mepo.Components.HistoryProduct;
import mepo.Components.Product;
import mepo.Helper.ClientHelper;
import mepo.Components.User;
import mepo.Helper.ProductHelper;
import mepo.Implements.ProductImp;
import mepo.Implements.UserImp;
import mepo.Navigator;

public class CustomerController {

    private UserImp clientHelper = new ClientHelper();
    private ProductImp productHelper = new ProductHelper();
    private ObservableList<User> listUser = FXCollections.observableArrayList();
    private ObservableList<HistoryProduct> listMovie = FXCollections.observableArrayList();
    private User user = null;

    @FXML
    private JFXButton btnHome;
    @FXML
    private JFXButton btnAdmin;
    @FXML
    private JFXButton btnCustomer;
    @FXML
    private JFXButton btnCategory;
    @FXML
    private JFXButton btnProduct;
    @FXML
    private JFXButton btnOrder;
    @FXML
    private JFXButton btnFeedback;
    @FXML
    private JFXButton btnLogout;
    @FXML
    private TableView<User> tvAdmin;
    @FXML
    private TableColumn<User, String> tcUsername;
    @FXML
    private TableColumn<User, String> tcFullName;
    @FXML
    private TableColumn<User, String> tcContact;
    @FXML
    private TableColumn<User, Double> tcCoins;
    @FXML
    private Label txtUser;

    @FXML
    private JFXTextField txtSearch;

    @FXML
    private TableView<HistoryProduct> tbGallery;

    @FXML
    private TableColumn<HistoryProduct, Integer> tcMovieId;

    @FXML
    private TableColumn<HistoryProduct, String> tcMovieName;

    @FXML
    private TableColumn<HistoryProduct, String> tcDateMovie;

    @FXML
    private TableColumn<HistoryProduct, Double> tcMovieCoins;
    @FXML
    private Label lbTotalCoins;
    private Callback<TableColumn.CellDataFeatures<HistoryProduct, Integer>, ObservableValue<Integer>> cellDataFeaturesObservableValueCallback;


    public void initialize(User user) {
        this.listUser = clientHelper.selectAll();
        this.user = user;
        txtUser.setText("User: " + this.user.getUsername());
        createTableCustomer();
        if (!listUser.isEmpty()) {
            createTableOrder(listUser.get(0).getUsername());
        }

    }

    private void createTableCustomer() {
        tvAdmin.setItems(this.listUser);

        tcUsername.setCellValueFactory((c) -> {
            return c.getValue().getUsernameProperty();
        });
        tcFullName.setCellValueFactory((c) -> {
            return c.getValue().getFullnameProperty();
        });
        tcContact.setCellValueFactory((c) -> {
            return c.getValue().getContactProperty();
        });
        tcCoins.setCellValueFactory(c -> {
            return c.getValue().getCoinProperty().asObject();
        });

        FilteredList<User> filteredData = new FilteredList<>(this.listUser, b -> true);

        txtSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate((c) -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lower = newValue.toLowerCase();
                if (c.getUsername().toLowerCase().indexOf(lower) != -1) {
                    return true;
                } else if (c.getFullName().toLowerCase().indexOf(lower) != -1) {
                    return true;
                } else {
                    return false;
                }
            });
        });
        SortedList<User> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tvAdmin.comparatorProperty());
        tvAdmin.setItems(sortedData);
    }

    @FXML
    void logoClicked(MouseEvent event) throws IOException {
        Navigator.getInstance().goToHomeAdmin(user);
    }

    @FXML
    void btnOrder(MouseEvent event) {
        User user = tvAdmin.getSelectionModel().getSelectedItem();
        if (user != null) {
            createTableOrder(user.getUsername());
        }
    }

    private void createTableOrder(String productName) {
        ProductHelper.totalCoin = 0;
        this.listMovie.clear();
        this.listMovie = productHelper.selectProductboughtByUser(productName);
        lbTotalCoins.setText("Total coins bought : " + Helper.formatString(ProductHelper.totalCoin));
        tbGallery.setItems(listMovie);
        tcMovieId.setCellValueFactory(cellDataFeaturesObservableValueCallback);
        tcMovieName.setCellValueFactory(c -> {
            return c.getValue().productnameProperty();
        });
        tcDateMovie.setCellValueFactory(c -> {
            return c.getValue().dateProperty();
        });
        tcMovieCoins.setCellValueFactory(c -> {
            return c.getValue().coinProperty().asObject();
        });
        tcMovieId.setCellValueFactory(c -> {
            return c.getValue().productIDProperty();
        });

    }

    @FXML
    private void btnHomeClick(ActionEvent event) throws IOException {
        Navigator.getInstance().goToHomeAdmin(user);
    }

    @FXML
    private void btnAdminClick(ActionEvent event) throws IOException {
        Navigator.getInstance().goToAdmin(user);
    }

    @FXML
    private void btnCustomerClick(ActionEvent event) throws IOException {
        Navigator.getInstance().goToCustomer(user);
    }

    @FXML
    private void btnCategoryClick(ActionEvent event) throws IOException {
        Navigator.getInstance().goToCategory(user);
    }

    @FXML
    private void btnProductClick(ActionEvent event) throws IOException {
        Navigator.getInstance().goToProducts(user);
    }

    @FXML
    private void btnOrderClick(ActionEvent event) throws IOException {
        Navigator.getInstance().goToOrderAdmin(user);
    }

    @FXML
    private void btnFeedbackClick(ActionEvent event) throws IOException {
        Navigator.getInstance().goToFeedbackAdmin(user);
    }

    @FXML
    private void btnLogoutClick(ActionEvent event) throws IOException {
        Navigator.getInstance().goToLoginAdmin();
    }

    @FXML
    private void btnCreateClick(ActionEvent event) throws IOException {
        Navigator.getInstance().goToCustomerCreate(user);
    }


    @FXML
    void btnActionTuCoins(ActionEvent actionEvent) throws IOException {
        Navigator.getInstance().goToDialogHistoryAdmin(user);
    }

    @FXML
    void btnActionCard(ActionEvent actionEvent) throws IOException {
        Navigator.getInstance().goToCardAdmin(user);
    }
}
