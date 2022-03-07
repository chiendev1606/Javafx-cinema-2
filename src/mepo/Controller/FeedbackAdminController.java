/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mepo.Controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import java.io.IOException;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import mepo.Components.Feedback;
import mepo.Components.MyAlert;
import mepo.Components.MyNotify;
import mepo.Helper.FeedbackHelper;
import mepo.Components.User;
import mepo.Navigator;



public class FeedbackAdminController {

    private User user = null;
    private mepo.Implements.FeedbackImp FeedbackImp = new FeedbackHelper();
    private int index = -1;
    private CheckBox showAllCheckBox;

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
    private Label txtUser;
    @FXML
    private TableView<Feedback> tvFeedback;
    @FXML
    private TableColumn<Feedback, Integer> tcID;
    @FXML
    private TableColumn<Feedback, String> tcUsername;
    @FXML
    private TableColumn<Feedback, String> tcProductName;
    @FXML
    private TableColumn<Feedback, String> tcContent;
    @FXML
    private TableColumn<Feedback, String> tcDate;
    @FXML
    private TableColumn<Feedback, Boolean> tcCheck;
    @FXML
    private Label txtUsername;
    @FXML
    private Label txtProductName;
    @FXML
    private Label txtDate;
    @FXML
    private Text txtContent;
    @FXML
    private JFXTextField txtSearch;

    ObservableList<Feedback> data;

    /**
     * Initializes the controller class.
     */
    public void initialize(User username) {
        this.user = username;
        txtUser.setText("User: " + this.user.getUsername());
        search();

    }

    @FXML
    void btnActionTuCoins(ActionEvent actionEvent) throws IOException {
        Navigator.getInstance().goToDialogHistoryAdmin(user);
    }

    @FXML
    void btnActionCard(ActionEvent actionEvent) throws IOException {
        Navigator.getInstance().goToCardAdmin(user);
    }

    private void setupTableView() {
        tvFeedback.setEditable(true);
    }

    void search() {
        ObservableList<Feedback> result = FeedbackImp.selectAll();
        tvFeedback.setItems(result);
        tvFeedback.setEditable(true);
        tcID.setCellValueFactory((c) -> {
            return c.getValue().getFeedbackIDProperty();
        });
        tcUsername.setCellValueFactory((c) -> {
            return c.getValue().getUsernameProperty();
        });
        tcProductName.setCellValueFactory((c) -> {
            int id = c.getValue().getProductIDProperty().get();
            String str = FeedbackImp.selectProductNameByID(id);
            StringProperty sp = new SimpleStringProperty(str);
            return sp;
        });
        tcContent.setCellValueFactory((c) -> {
            return c.getValue().getContentProperty();
        });
        tcDate.setCellValueFactory((c) -> {
            return c.getValue().getDateProperty();
        });

//        Checked
        checked(result);
        tcCheck.setCellFactory(CheckBoxTableCell.forTableColumn(tcCheck));
        tcCheck.setCellValueFactory(cell -> {
            cell.getValue().getSelectProperty();
            BooleanProperty property = cell.getValue().getSelectProperty();
            Feedback feedback = cell.getValue();
            if (property.get()) {
                feedback.setSelect(Boolean.TRUE);
                FeedbackImp.update(feedback);
            } else {
                feedback.setSelect(Boolean.FALSE);
                FeedbackImp.update(feedback);
            }
            return property;
        });

//        search
        ObservableList<Feedback> r = FeedbackImp.selectAll();
        FilteredList<Feedback> fillteredData = new FilteredList<>(r, b -> true);
        txtSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            fillteredData.setPredicate((pro) -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lower = newValue.toLowerCase();
                if (pro.getUsername().toLowerCase().indexOf(lower) != -1) {
                    return true;
                } else if (pro.getDate().toLowerCase().indexOf(lower) != -1) {
                    return true;
                } else if (pro.getContent().toLowerCase().indexOf(lower) != -1) {
                    return true;
                } else if (FeedbackImp.selectProductNameByID(pro.getProductID()).toLowerCase().indexOf(lower) != -1) {
                    return true;
                } else {
                    return false;
                }

            });
        });
        SortedList<Feedback> sortedData = new SortedList<>(fillteredData);
        sortedData.comparatorProperty().bind(tvFeedback.comparatorProperty());
        tvFeedback.setItems(sortedData);
    }

    void checked(ObservableList<Feedback> rs) {
        for (Feedback feedback : rs) {
            if (feedback.getSelect()) {
                feedback.getBox().setSelected(true);
            } else {
                feedback.getBox().setSelected(false);
            }
        }
    }


    @FXML
    void selectFeedback(MouseEvent event) {
        index = tvFeedback.getSelectionModel().getSelectedIndex();
        if (index <= -1) {
            return;
        }
        txtUsername.setText("Username: " + tcUsername.getCellData(index));
        txtProductName.setText("ProductName: " + tcProductName.getCellData(index));
        txtDate.setText("Date: " + tcDate.getCellData(index));
        txtContent.setText("Content: " + tcContent.getCellData(index));
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

    private MyAlert alert = new MyAlert();

    @FXML
    private void btnDeleteClick(ActionEvent event) {
        Feedback f = tvFeedback.getSelectionModel().getSelectedItem();
        if (f == null) {
            MyNotify.MyNotifyAlertError("Please select a feedback");
        } else {
            String text = "Are you sure you want to delete this Feedback?";
            if (alert.alertComfirm(text) == ButtonType.YES) {
                boolean result = FeedbackImp.delete(f);
                search();
                if (result) {
                    tvFeedback.getItems().remove(f);
                    MyNotify.MyNotifyAlert("A Feedback deleted");
                } else {
                    MyNotify.MyNotifyAlertError("No Feedback is deleted");
                }
            }
        }
    }


    @FXML
    private void searchClick(MouseEvent event) {
    }


}
