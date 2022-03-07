/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mepo.Controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Optional;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import mepo.Components.MyAlert;
import mepo.Components.Product;
import mepo.Helper.ProductHelper;
import mepo.Components.User;
import mepo.Navigator;


public class ProductsController {

    private mepo.Implements.ProductImp ProductImp = new ProductHelper();
    User user = null;
    private int index = -1;

    @FXML
    private TableView<Product> tvProduct;
    @FXML
    private TableColumn<Product, Integer> tcProductID;
    @FXML
    private TableColumn<Product, String> tcProductname;
    @FXML
    private TableColumn<Product, String> tcDescription;
    @FXML
    private TableColumn<Product, String> tcPrice;
    @FXML
    private TableColumn<Product, String> tcCategoryID;
    @FXML
    private TableColumn<Product, String> tcVideo;
    @FXML
    private TableColumn<Product, String> tcUrl;
    @FXML
    private Button btnCreate;
    @FXML
    private Button btnDelete;
    @FXML
    private Button btnEdit;
    @FXML
    private Label txtUser;
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
    private JFXTextField txtSearch;
    @FXML
    private Button btnView;


    ObservableList<Product> listM;
    ObservableList<Product> dataList;
    private MyAlert alert = new MyAlert();

    /**
     * Initializes the controller class.
     */
    public void initialize(User username) {
        user = username;
        txtUser.setText("User: " + this.user.getUsername());
        search();
    }

    @FXML
    void searchClick(MouseEvent event) {

    }

    void search() {
        ObservableList<Product> result = ProductImp.selectAll();
        tvProduct.setItems(result);
        tvProduct.setFixedCellSize(25);
        tcProductID.setCellValueFactory((c) -> {
            return c.getValue().getProductIDProperty();
        });
        tcProductname.setCellValueFactory((c) -> {
            return c.getValue().getProductNameProperty();
        });
        tcDescription.setCellValueFactory((c) -> {
            return c.getValue().descriptionProperty();
        });
        tcPrice.setCellValueFactory((c) -> {
            return c.getValue().getPriceProperty();
        });
        tcVideo.setCellValueFactory((c) -> {
            return c.getValue().getVideoProperty();
        });
        tcUrl.setCellValueFactory(c -> {
            return c.getValue().urlProperty();
        });
        tcCategoryID.setCellValueFactory((c) -> {
            int id = c.getValue().getCategoryIDProperty().get();
            String str = ProductImp.selectCategoryNameByID(id);
            StringProperty sp = new SimpleStringProperty(str);
            return sp;
        });


        ObservableList<Product> r = ProductImp.selectAll();
        FilteredList<Product> filteredData = new FilteredList<>(r, b -> true);
        txtSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate((product) -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lower = newValue.toLowerCase();
                if (product.getProductName().toLowerCase().indexOf(lower) != -1) {
                    return true;
                } else if (product.getDescription().toLowerCase().indexOf(lower) != -1) {
                    return true;
                } else if (product.getPrice().toLowerCase().indexOf(lower) != -1) {
                    return true;
                } else if (ProductImp.selectCategoryNameByID(product.getCategoryID()).toLowerCase().indexOf(lower) != -1) {
                    return true;
                } else {
                    return false;
                }

            });
        });
        SortedList<Product> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tvProduct.comparatorProperty());
        tvProduct.setItems(sortedData);
    }

    @FXML
    private void btnCreateClick(ActionEvent event) throws IOException, SQLException {
        Navigator.getInstance().goToProductEdit(user, null);
    }

    @FXML
    void btnViewClick(ActionEvent event) throws IOException {
        Product product = tvProduct.getSelectionModel().getSelectedItem();
        if (product == null) {
            this.alert.alertError("PLEASE CHOOSE A PRODUCT !!!");
        } else {
            Navigator.getInstance().goToProductView(user, product);
        }
    }

    @FXML
    private void btnDeleteClick(ActionEvent event) throws IOException {
        try {
            Product u = tvProduct.getSelectionModel().getSelectedItem();
            if (u == null) {
                this.alert.alertError("PLEASE CHOOSE A PRODUCT !!!");
            } else {
                if (this.alert.alertComfirm(("Are you sure you want to delete this Product?").toUpperCase()) == ButtonType.YES) {

                    boolean result = ProductImp.delete(u);
                    search();
                    if (result) {
                        tvProduct.getItems().remove(u);
                        System.out.println("A Product deleted");
                    } else {
                        this.alert.alertError("There are a few problems associated with this product!!! Needs to be resolved before deleting !!");
                    }
                }

            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    private void btnEditClick(ActionEvent event) throws IOException, SQLException {
        Product editProduct = tvProduct.getSelectionModel().getSelectedItem();
        if (editProduct == null) {
            this.alert.alertError("PLEASE CHOOSE A PRODUCT !!!");
        } else {
            Navigator.getInstance().goToProductEdit(user, editProduct);
        }
    }

    @FXML
    void getSelected(MouseEvent even) throws IOException {
        Product editProduct = tvProduct.getSelectionModel().getSelectedItem();
        if (editProduct == null) {
            return;
        }
        index = tvProduct.getSelectionModel().getSelectedIndex();
        if (index <= -1) {
            return;
        }

    }

    @FXML
    void btnActionTuCoins(ActionEvent actionEvent) throws IOException {
        Navigator.getInstance().goToDialogHistoryAdmin(user);
    }

    @FXML
    void btnActionCard(ActionEvent actionEvent) throws IOException {
        Navigator.getInstance().goToCardAdmin(user);
    }

    @FXML
    void logoClicked(MouseEvent event) throws IOException {
        Navigator.getInstance().goToHomeAdmin(user);
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


}
