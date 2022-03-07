package mepo.Controller;

import com.jfoenix.controls.JFXTextArea;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import mepo.Components.*;
import mepo.Helper.CategoryHelper;
import mepo.Helper.FeedbackHelper;
import mepo.Implements.CategoryImp;
import mepo.Implements.FeedbackImp;
import mepo.Navigator;


import java.io.IOException;
import java.util.HashMap;
import java.util.List;


public class Home_moviebuyController {

    @FXML
    private WebView webView;

    @FXML
    private JFXTextArea txtFeedback;

    @FXML
    private GridPane gridFeedback;
    @FXML
    private Label nameMovie;

    @FXML
    private Label category;

    @FXML
    private ImageView movieImg;

    @FXML
    private Text desc;

    @FXML
    private Label txtAlertFeedback;


    @FXML
    void btnSubmit(ActionEvent event) {
        try {
            Feedback feedback = extractTeacherFromFields();
            if (checkVa(feedback)) {
                feedback = feedbackHelper.insert(feedback);
                txtFeedback.setText("");
                String message = "Feedback has been sent !!";
                MyNotify.MyNotifyAlert(message);
            } else {
                MyNotify.MyNotifyAlertError("Something went wrong !!! please try agains !!!");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    @FXML
    void back(ActionEvent event) throws IOException {
        webView.getEngine().load(null);
        Navigator.getInstance().goToMyMovies(user);
    }


    private HistoryProduct product = null;
    private User user = null;
    private FeedbackImp feedbackHelper = new FeedbackHelper();
    private CategoryImp categoryHelper = new CategoryHelper();

    public void initialize(HistoryProduct product, User user) {
        this.user = user;
        this.product = product;
        category.setText(categoryHelper.selectNameById(product.getCategoryID()));
        nameMovie.setText(product.getProductname());
        WebEngine webEngine = webView.getEngine();
        webEngine.load(product.getUrl());
        desc.setText(product.getDescription());
        movieImg.setImage(Helper.convertByteToImage(product.getImage(), movieImg));
        addFeedback();

    }

    private Feedback extractTeacherFromFields() {
        String date = java.time.LocalDate.now().toString();
        Feedback feedback = new Feedback();
        feedback.setProductID(product.getProductID());
        feedback.setUsername(user.getUsername());
        feedback.setContent(txtFeedback.getText());
        feedback.setDate(date);
        feedback.setSelect(true);
        return feedback;
    }

    private void checkValid() {
        txtFeedback.textProperty().addListener((observable, oldValue, newValue) -> {
            txtAlertFeedback.setText("");
            if (newValue.length() < 7 && newValue.length() > 0) {
                txtAlertFeedback.setText("Please write longer than 10 characters");
            } else if (newValue.length() > 255) {
                txtAlertFeedback.setText("Maximum of 255 characters");
            }

        });
    }

    boolean checkVa(Feedback feedback) {
        HashMap<String, String> errr = new HashMap<String, String>();
        if (feedback.getContent().length() < 7) {
            errr.put("content", "Please write longer than 10 characters");
        } else if (feedback.getContent().length() > 255) {
            errr.put("content", "Maximum of 255 characters");
        }
        if (errr.size() == 0) {
            return true;
        } else {
            txtAlertFeedback.setText(errr.get("content"));
            return false;
        }
    }

    void addFeedback() {
        List<Feedback> result = feedbackHelper.selectFeedbackByProductID(product.getProductID());
        int column = 0;
        int row = 1;
        try {
            for (Feedback f : result) {
                if (f.getSelect()) {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("/mepo/view/feedback.fxml"));
                    if (column == 2) {
                        column = 0;
                        ++row;
                    }
                    Pane pane = fxmlLoader.load();
                    FeedbackController fbCTRL = fxmlLoader.getController();
                    fbCTRL.setData(f);
                    gridFeedback.add(pane, column++, row);
                    GridPane.setMargin(pane, new Insets(5,20,10,5));
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
