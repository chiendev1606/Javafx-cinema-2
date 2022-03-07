/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mepo.Controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import mepo.Components.MyAlert;
import mepo.Helper.AdminHelper;
import mepo.Components.Category;
import mepo.Helper.CategoryHelper;
import mepo.Components.User;
import mepo.Implements.UserImp;
import mepo.Implements.CategoryImp;
import mepo.Navigator;


public class CategoryController {

    private CategoryImp categoryHelper = new CategoryHelper();
    private User user = null;
    private int index = -1;
    private final ObservableList<Category> dataList = FXCollections.observableArrayList();

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
    private TableView<Category> tvCategory;
    @FXML
    private TableColumn<Category, String> tcCategory;

    @FXML
    private TableColumn<Category, String> tcDescription;

    @FXML
    private TableColumn<Category, Integer> TcQoP;
    @FXML
    private Button btnCreate;
    @FXML
    private Button btnDelete;
    @FXML
    private Button btnEdit;
    @FXML
    private Label txtUsername;
    @FXML
    private JFXTextField txtValue;

    @FXML
    private Label txtAlertCategoryName;

    @FXML
    private Label txtAlertDescription;

    @FXML
    private JFXTextField txtDescription;

    @FXML
    private JFXTextField txtSearch;

    private MyAlert alert = new MyAlert();


    public void initialize(User username) {
        checkValid();
        this.user = username;
        txtUsername.setText("User: " + this.user.getUsername());
        search();
    }

    void search() {
        ObservableList<Category> result = categoryHelper.selectAll();
        tvCategory.setItems(result);

        tcCategory.setCellValueFactory((c) -> {
            return c.getValue().getCategoryNameProperty();
        });
        tcDescription.setCellValueFactory((c) -> {
            return c.getValue().getDescriptionProperty();
        });
        TcQoP.setCellValueFactory((c) -> {
            int id = c.getValue().getCategoryIDProperty().get();
            id = categoryHelper.countQoP(id);
            ObjectProperty<Integer> op = new SimpleObjectProperty<>(id);
            return op;
        });

        FilteredList<Category> fillteredData = new FilteredList<>(result, b -> true);

        txtSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            fillteredData.setPredicate((category) -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lower = newValue.toLowerCase();
                if (category.getCategoryName().toLowerCase().indexOf(lower) != -1) {
                    return true;
                } else if (category.getDescriptionName().toLowerCase().indexOf(lower) != -1) {
                    return true;
                } else {
                    return false;
                }
            });
        });
        SortedList<Category> sortedData = new SortedList<>(fillteredData);
        sortedData.comparatorProperty().bind(tvCategory.comparatorProperty());
        tvCategory.setItems(sortedData);
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
    void btnActionTuCoins(ActionEvent actionEvent) throws IOException {
        Navigator.getInstance().goToDialogHistoryAdmin(user);
    }

    @FXML
    void btnActionCard(ActionEvent actionEvent) throws IOException {
        Navigator.getInstance().goToCardAdmin(user);
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
        Category insertCat = extractCategoryFromFields();
        if (validateInfo(insertCat, true)) {
            insertCat = categoryHelper.insert(insertCat);
            Navigator.getInstance().goToCategory(user);
            this.alert.alertSuccess(" Added category successfully !!");
        } else {
            this.alert.alertError("Added category failed !!");
        }

    }

    @FXML
    private void btnDeleteClick(ActionEvent event) {
        Category cat = tvCategory.getSelectionModel().getSelectedItem();
        if (cat == null) {
            this.alert.alertError("PLEASE SELECT A CATEGORY");
        } else {

            String text = "Are you sure you want to delete the Category and its Products?";
            if (this.alert.alertComfirm(text.toUpperCase()) == ButtonType.YES) {
                Category cate = cat;
                boolean result = categoryHelper.deleteAllProductByCateID(cate.getCategoryID());
                result = categoryHelper.delete(cate);
                setBlank();
                search();
                if (result) {
                    tvCategory.getItems().remove(cate);
                    this.alert.alertSuccess("Delete product successfully !!!");
                } else {
                    this.alert.alertError("There are a few problems associated with this category!!! Needs to be resolved before deleting");
                }
            }
        }
    }

    @FXML
    private void btnEditClick(ActionEvent event) throws IOException {
        Category cate = tvCategory.getSelectionModel().getSelectedItem();
        Category updateCategory = extractCategoryFromFields();
        updateCategory.setCategoryID(cate.getCategoryID());

        if (validateInfo(updateCategory, false)) {
            boolean result = categoryHelper.update(updateCategory);
            Navigator.getInstance().goToCategory(user);
            if (result) {
                this.alert.alertSuccess("UPDATE CATEGORY SUCCESSFULLY !!");
            } else {
                this.alert.alertError("UPDATE CATEGORY FAIL !!!");
            }
        } else {
            this.alert.alertError("UPDATE CATEGORY FAIL !!!");
        }
    }

    boolean validateInfo(Category insertCat, boolean flag) {
        HashMap<String, String> err = new HashMap<String, String>();

        if (insertCat.getDescriptionName().length() < 1) {
            err.put("description", "Description cannot be left blank");
        }
        Category categoryExist = categoryHelper.selectCategoryName(insertCat.getCategoryName().toLowerCase());

        if (insertCat.getCategoryName().length() < 1) {
            err.put("name", "Category title cannot be left blank");
        } else if (categoryExist != null && flag) {
                err.put("name", "Category already exist ");
        }
        if (err.size() == 0) {
            return true;
        } else {
            txtAlertCategoryName.setText(err.get("name"));
            txtAlertDescription.setText(err.get("description"));
            return false;
        }
    }

    @FXML
    void btnResetClick(ActionEvent event) {
        txtValue.setText("");
        txtDescription.setText("");
    }


    private Category extractCategoryFromFields() {
        Category cate = new Category();
        cate.setCategoryName(txtValue.getText());
        cate.setDescriptionName(txtDescription.getText());
        return cate;
    }

    @FXML
    void getSelected(MouseEvent even) {
        index = tvCategory.getSelectionModel().getSelectedIndex();
        if (index <= -1) {
            return;
        }
        txtValue.setText(tcCategory.getCellData(index).toString());
        txtDescription.setText(tcDescription.getCellData(index).toString());
    }

    private void checkValid() {
        txtValue.textProperty().addListener((observable, oldValue, newValue) -> {
            resetTextFields(txtAlertCategoryName);
            if (newValue.length() < 1) {
                txtAlertCategoryName.setText("Category title cannot be left blank");
            }
        });
        txtDescription.textProperty().addListener((observable, oldValue, newValue) -> {
            resetTextFields(txtAlertDescription);
            if (newValue.length() < 1) {
                txtAlertDescription.setText("Description cannot be left blank");
            }
        });
    }

    void resetTextFields(Label l) {
        l.setText("");
    }

    void setBlank() {
        txtValue.setText("");
        txtDescription.setText("");
        txtAlertDescription.setText("");
        txtAlertCategoryName.setText("");

    }
}
