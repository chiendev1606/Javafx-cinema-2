package mepo.Controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import mepo.Components.Dialog;
import mepo.Components.User;
import mepo.Helper.DialogHelper;
import mepo.Implements.dialogImp;
import mepo.Navigator;

import java.io.IOException;

public class DialogHistoryController {
    private User user = null;
    private dialogImp dialogHelper = new DialogHelper();
    @FXML
    private TableView<Dialog> Topup;

    @FXML
    private TableColumn<Dialog, Integer> id;

    @FXML
    private TableColumn<Dialog, String> username;

    @FXML
    private TableColumn<Dialog, Integer> code;

    @FXML
    private TableColumn<Dialog, Integer> value;

    @FXML
    private TableColumn<Dialog, String> date;


    @FXML
    private JFXTextField txtSearch;

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
    void btnAdminClick(ActionEvent event) throws IOException {
        Navigator.getInstance().goToAdmin(user);
    }

    @FXML
    void btnCategoryClick(ActionEvent event) throws IOException {
        Navigator.getInstance().goToCategory(user);
    }

    @FXML
    void btnCustomerClick(ActionEvent event) throws IOException {
        Navigator.getInstance().goToCustomer(user);
    }

    @FXML
    void btnFeedbackClick(ActionEvent event) throws IOException {
        Navigator.getInstance().goToFeedbackAdmin(user);
    }

    @FXML
    void btnHomeClick(ActionEvent event) throws IOException {
        Navigator.getInstance().goToHomeAdmin(user);
    }

    @FXML
    void btnLogoutClick(ActionEvent event) throws IOException {
        Navigator.getInstance().goToLogin();
    }

    @FXML
    void btnOrderClick(ActionEvent event) throws IOException {
        Navigator.getInstance().goToOrderAdmin(user);
    }

    @FXML
    void btnProductClick(ActionEvent event) throws IOException {
        Navigator.getInstance().goToProducts(user);
    }

    void filterDialog() {
        ObservableList<Dialog> result = dialogHelper.selectAll();
        Topup.setItems(result);
        Topup.setFixedCellSize(25);
        id.setCellValueFactory((c) -> {
            return c.getValue().idProperty();
        });
        username.setCellValueFactory((c) -> {
            return c.getValue().userNameProperty();
        });
        value.setCellValueFactory((c) -> {
            return c.getValue().valueProperty();
        });
        code.setCellValueFactory((c) -> {
            return c.getValue().cardCodeProperty();
        });
        date.setCellValueFactory((c) -> {
            return c.getValue().dateTimeProperty();
        });




        ObservableList<Dialog> list = dialogHelper.selectAll();
        FilteredList<Dialog> fillteredData = new FilteredList<>(list, bl -> true);
        txtSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            fillteredData.setPredicate((dialog) -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lower = newValue.toLowerCase();
                if (dialog.getUserName().toLowerCase().contains(lower)) {
                    return true;
                } else if (dialog.getDateTime().toLowerCase().contains(lower)) {
                    return true;
                }  else {
                    return false;
                }

            });
        });
        SortedList<Dialog> sortedData = new SortedList<>(fillteredData);
        sortedData.comparatorProperty().bind(Topup.comparatorProperty());
        Topup.setItems(sortedData);
    }

    @FXML
    void btnActionTuCoins(ActionEvent actionEvent) throws IOException {
        Navigator.getInstance().goToDialogHistoryAdmin(user);
    }
    @FXML
    void btnActionCard(ActionEvent actionEvent) throws IOException {
        Navigator.getInstance().goToCardAdmin(user);
    }

    public void initialize(User user) {
        this.user = user;
        filterDialog();

    }
}
