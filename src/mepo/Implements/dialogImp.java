package mepo.Implements;

import javafx.collections.ObservableList;
import mepo.Components.Dialog;

import java.util.List;

public interface dialogImp {
    ObservableList<Dialog> selectAll();
    ObservableList<Dialog> selectDiaglogByUserName(String userName);
    Dialog insertDialog(Dialog dialog);
}
