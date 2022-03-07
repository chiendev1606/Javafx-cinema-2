package mepo.Controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import mepo.Components.*;
import mepo.Helper.CardHelper;
import mepo.Helper.ClientHelper;
import mepo.Helper.DialogHelper;
import mepo.Implements.CardImp;
import mepo.Implements.UserImp;
import mepo.Implements.dialogImp;
import mepo.Navigator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TopUpCoinController {
    private User userTu;
    private UserImp userHelper = new ClientHelper();
    private dialogImp dialogHelper = new DialogHelper();
    private CardImp cardHelper = new CardHelper();
    private MyAlert myAlert = new MyAlert();
    private  List<Card> list = cardHelper.selectAll();
    private List<Card> listUnused = new ArrayList<>();
   private Card cardUsed = null;


    @FXML
    private Label txtSll;

    @FXML
    private JFXButton textCoin;

    @FXML
    private JFXTextField txtCode;

    @FXML
    private Label textError;

    public Card getCardUsed() {
        return cardUsed;
    }

    public void setCardUsed(Card cardUsed) {
        this.cardUsed = cardUsed;
    }

    @FXML
    void btnActionCoin(ActionEvent event) {

    }

    public User getUserTu() {
        return userTu;
    }

    public void setUserTu(User userTu) {
        this.userTu = userTu;
    }

    @FXML
    void btnActionMyMovies(ActionEvent event) throws IOException {
        Navigator.getInstance().goToMyMovies(userTu);

    }

    @FXML
    void btnActiontuCoin(ActionEvent event) throws IOException {
        Navigator.getInstance().gotoTopUpCoins(this.getUserTu());
    }

    @FXML
    void btnCart(ActionEvent event) throws IOException {
        Navigator.getInstance().goToOrder(this.getUserTu());
    }

    @FXML
    void btnContact(ActionEvent event) throws IOException {
        Navigator.getInstance().goToContact(this.getUserTu());
    }

    @FXML
    void btnGame(ActionEvent event) throws IOException {
        Navigator.getInstance().goToHome(this.getUserTu());
    }

    @FXML
    void btnTopUp(ActionEvent event) throws IOException {
        if (checkValid()) {
            if (this.myAlert.alertComfirm("DO YOU WANT TO TOP-UP " + cardUsed.getValue() + " COINS ? ") == ButtonType.YES) {
                cardUsed.setUsed(true);
                cardHelper.updateUsed(cardUsed);
                list.removeIf(card -> card.getCode() == cardUsed.getCode());
                this.listUnused = creataListUnused();
                Helper.writeFile(this.listUnused);
                double totalCoin = userTu.getCoin() + cardUsed.getValue();
                userTu.setCoin(totalCoin);
                userHelper.update(userTu);
                Dialog dialog = new Dialog();
                dialog.setUserName(userTu.getUsername());
                dialog.setCardCode(cardUsed.getCode());
                dialogHelper.insertDialog(dialog);
                textCoin.setText(Helper.formatString(totalCoin));
                MyNotify.MyNotifyAlert("YOUR COINS IS " + Helper.formatString(totalCoin) + "!!!");
                cardUsed = null;
            }
        } else {
            this.myAlert.alertError("CODE IS INCORRECT OR ALREADY TAKEN !!! PLEASE TRY AGAIN !!!");
        }

    }

    @FXML
    void btnTransactions(ActionEvent event) throws IOException {
        Navigator.getInstance().goToOrder(userTu);
    }

    @FXML
    void miAccountClick(ActionEvent event) throws IOException {
        Navigator.getInstance().goToProfileClient(this.getUserTu());
    }

    @FXML
    void miLogout(ActionEvent event) throws IOException {
        Navigator.getInstance().goToLogin();
    }

    private boolean checkValid() {
        boolean flag = true;
        String codeCheck = txtCode.getText().trim();
        if (codeCheck.isEmpty()) {
            textError.setText("Code can not be blank !");
            flag = false;
        } else if (!Validate.isNumeric(codeCheck)) {
            textError.setText("Code must be digits!");
            flag = false;
        } else if (codeCheck.length() != 6) {
            textError.setText("Code must have 6 digits");
            flag = false;
        } else {
            for (Card cardCurrent :this.list) {
                if ((cardCurrent.getCode() == Integer.parseInt(txtCode.getText().trim())) && !cardCurrent.isUsed()) {
                    this.cardUsed = cardCurrent;
                    break;
                }
            }
            if(this.cardUsed == null){
                flag = false;
            }

        }
        return flag;
    }
    public List<Card> creataListUnused (){
        List<Card> list = new ArrayList<Card>();
        for (Card card: this.list) {
            if(!card.isUsed()){
                list.add(card);
            }
        }
        return list;
    }
    public void initialize(User user) {
        this.setUserTu(user);
        textCoin.setText(Helper.formatString(this.getUserTu().getCoin()));
        setSLL();
        txtCode.textProperty().addListener((observable, oldValue, newValue) -> {
            if (String.valueOf(newValue).length() != 6) {
                textError.setText("Code must have 6 digits");
            } else if (!Validate.isNumeric(newValue)) {
                textError.setText("Code must be digits");
            }
        });


    }

    void setSLL() {
        ObservableList<Product> result = HomeController.orderList;
        ObservableList<Product> rs = FXCollections.observableArrayList();
        int count = 0, sll = 0;
        for (Product f : result) {
            count = 0;
            sll++;
            for (Product ff : rs) {
                if (ff.getProductID() == f.getProductID()) {
                    count++;
                    sll -= count;
                }
            }
            if (count > 0) {
                continue;
            }
            rs.add(f);
        }
        String sss = String.valueOf(sll);
        txtSll.setText(sss);
    }
}
