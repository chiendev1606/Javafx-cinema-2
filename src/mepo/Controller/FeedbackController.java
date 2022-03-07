
package mepo.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import mepo.Components.Feedback;


public class FeedbackController{
    Feedback fb = null;
    
    @FXML
    private Label txtUserName;
    @FXML
    private Text txtContent;
    @FXML
    private Label txtDate;

    /**
     * Initializes the controller class.
     */
    public void setData(Feedback f) {
        this.fb = f;
        txtUserName.setText(fb.getUsername());
        txtContent.setText(fb.getContent());
        txtDate.setText(fb.getDate());
    }    
    
}
