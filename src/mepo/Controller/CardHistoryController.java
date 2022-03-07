package mepo.Controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import mepo.Components.*;
import mepo.Helper.DialogHelper;
import mepo.Implements.dialogImp;
import mepo.Navigator;

import java.io.IOException;

public class CardHistoryController {
    private User user = null;
    private dialogImp dialogHelper = new DialogHelper();
    @FXML
    private Label txtSll;

    @FXML
    private GridPane gridCardHistory;

    @FXML
    private JFXButton textCoin;

    @FXML
    void btnActionCoin(ActionEvent event) {

    }

    @FXML
    void btnActionMyMovies(ActionEvent event) throws IOException {
        Navigator.getInstance().goToMyMovies(user);
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
    void btnGame(ActionEvent event) throws IOException {
        Navigator.getInstance().goToHome(user);
    }
    @FXML
    void btnCardHistory(ActionEvent event) throws IOException {
        Navigator.getInstance().goToCardHistory(user);
    }
    @FXML
    void btnHistory(ActionEvent event) throws IOException {
        Navigator.getInstance().goToOrderHistory(user);
    }

    @FXML
    void btnTransactions(ActionEvent event) throws IOException {
        Navigator.getInstance().goToOrderHistory(user);
    }

    @FXML
    void miAccountClick(ActionEvent event) throws IOException {
        Navigator.getInstance().goToProfileClient(user);
    }

    @FXML
    void miLogout(ActionEvent event) throws IOException {
        Navigator.getInstance().goToLogin();
    }

    public void initialize(User user) {
        this.user = user;
        HomeController.setSLL(txtSll);
        textCoin.setText(Helper.formatString(user.getCoin()));
        addDialog(dialogHelper.selectDiaglogByUserName(user.getUsername()));
    }


    private void addDialog(ObservableList<Dialog> list) {
        gridCardHistory.getChildren().clear();
        int column = 1;
        int row = 0;
        try {
            for (Dialog dialog : list) {
                ++row;
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/mepo/view/CardHistoryDetail.fxml"));
                Pane pane = fxmlLoader.load();
                CardHistoryDetailController fbCTRL = fxmlLoader.getController();
                fbCTRL.setData(dialog);
                gridCardHistory.add(pane, column, row);
                GridPane.setMargin(pane, new Insets(5));
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
