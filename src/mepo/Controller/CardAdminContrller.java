package mepo.Controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import mepo.Components.Card;
import mepo.Components.User;
import mepo.Helper.CardHelper;
import mepo.Implements.CardImp;
import mepo.Navigator;

import java.io.IOException;

public class CardAdminContrller {

    @FXML
    private TableView<Card> cardTable;

    @FXML
    private TableColumn<Card, Integer> code;

    @FXML
    private TableColumn<Card, Integer> value;

    @FXML
    private TableColumn<Card, String> used;

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
    private StringProperty status = new SimpleStringProperty();


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

    private User user = null;
    private CardImp cardHelper = new CardHelper();
    public void initialize(User username) {
        this.user = username;
        txtUser.setText("User: " + this.user.getUsername());
        filterCard();

    }
    void filterCard() {
        ObservableList<Card> result = FXCollections.observableArrayList(cardHelper.selectAll());
        cardTable.setItems(result);
        cardTable.setFixedCellSize(25);
        code.setCellValueFactory((c) -> {
            return c.getValue().codeProperty();
        });
        value.setCellValueFactory((c) -> {
            return c.getValue().valueProperty();
        });
        used.setCellValueFactory((c) -> {
            if (c.getValue().isUsed()) {
                status.set("Used");
            } else {
                status.set("Unused");
            }
            return status;
        });


        ObservableList<Card> list = FXCollections.observableArrayList(cardHelper.selectAll());
        FilteredList<Card> fillteredData = new FilteredList<>(list, bl -> true);
        txtSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            fillteredData.setPredicate((card) -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
           
                if(String.valueOf(card.getCode()).indexOf(newValue) != -1){
                    return true;
                } else if(String.valueOf(card.getValue()).indexOf(newValue) != -1){
                    return true;
                } else {
                    return false;
                }

            });
        });
        SortedList<Card> sortedData = new SortedList<>(fillteredData);
        sortedData.comparatorProperty().bind(cardTable.comparatorProperty());
        cardTable.setItems(sortedData);
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
