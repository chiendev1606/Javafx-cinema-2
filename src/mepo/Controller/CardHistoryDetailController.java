package mepo.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import mepo.Components.Dialog;

public class CardHistoryDetailController {

    @FXML
    private Pane item;

    @FXML
    private Label coins;

    @FXML
    private Label datetime;

    @FXML
    private Label id;

    @FXML
    private Label code;

    public void setData(Dialog dialog){
        id.setText(dialog.getId() + "");
        coins.setText(dialog.getValue() + "");
        datetime.setText(dialog.getDateTime());
        code.setText(dialog.getCardCode() + "");
    }

}