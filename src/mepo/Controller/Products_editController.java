
package mepo.Controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.util.HashMap;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import mepo.Components.*;
import mepo.Helper.AdminHelper;
import mepo.Helper.CategoryHelper;
import mepo.Helper.ProductHelper;
import mepo.Implements.CategoryImp;
import mepo.Implements.ProductImp;
import mepo.Implements.UserImp;
import mepo.Navigator;


public class Products_editController {

    private UserImp adminHelper = new AdminHelper();
    private CategoryImp categoryHelper = new CategoryHelper();
    private ProductImp productHelper = new ProductHelper();
    private Product productCurrent = null;
    public static StringProperty textImg = new SimpleStringProperty();
    private MyAlert alert = new MyAlert();
    private User user = null;
    private byte[] imageChoose = null;

    @FXML
    private AnchorPane anchorpane;

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
    private Button btnEdit;

    @FXML
    private Button btnReset;

    @FXML
    private Button btnBack;

    @FXML
    private FontAwesomeIconView back;

    @FXML
    private ComboBox<String> btnSelectCategory;

    @FXML
    private Label description;

    @FXML
    private Button btnImage;

    @FXML
    private Label txtAlertName;

    @FXML
    private Label txtAlertPrice;

    @FXML
    private Text txtAlertUrl;

    @FXML
    private Label txtAlertCategory;

    @FXML
    private Label txtLB;

    @FXML
    private Label txtUsername;

    @FXML
    private JFXTextField txtProductname;

    @FXML
    private JFXTextField txtPrice;

    @FXML
    private JFXTextArea txtDescription;

    @FXML
    private GridPane gridImg;

    @FXML
    private Label txtLB1;

    @FXML
    private Label txtAlertVideo;

    @FXML
    private Label txtAlertDescription;

    @FXML
    private ImageView imageMovie;

    @FXML
    private JFXTextField videoTrailer;

    @FXML
    private Label textAlertImage;
    @FXML
    private JFXTextField fullMovie;


    public void initialize(User username, Product product) throws SQLException {
        checkDisplayError();
        this.productCurrent = product;
        this.user = username;
        txtUsername.setText("User: " + this.user.getUsername());
        ObservableList<Category> result = categoryHelper.selectAll();
        ObservableList<String> listCategories = FXCollections.observableArrayList();
        for (Category category : result) {
            listCategories.add(category.getCategoryName());
        }
        btnSelectCategory.setItems(listCategories);


        if (this.productCurrent != null) {
            txtProductname.setText(productCurrent.getProductName());
            txtPrice.setText(productCurrent.getPrice());
            txtDescription.setText(productCurrent.getDescription());
            videoTrailer.setText(productCurrent.getVideo());
            fullMovie.setText(productCurrent.getUrl());
            btnSelectCategory.setValue(productHelper.selectCategoryNameByID(productCurrent.getCategoryID()));
            imageMovie.setImage(Helper.convertByteToImage(productCurrent.getImage(), imageMovie));
            imageChoose = productCurrent.getImage();

        }
    }

    @FXML
    void logoClicked(MouseEvent event) throws IOException {
        Navigator.getInstance().goToHomeAdmin(user);
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

    @FXML
    private void btnResetClick(ActionEvent event) {
        txtProductname.setText(null);
        txtPrice.setText(null);
        txtDescription.setText(null);
        btnSelectCategory.setValue(null);
        videoTrailer.setText("");
        fullMovie.setText("");
        imageMovie.setImage(null);
        this.imageChoose = null;
    }

    @FXML
    private void btnEditClick(ActionEvent event) throws IOException, SQLException {

        if (this.productCurrent == null) {
            Product newProduct = extractProductFromFields();
            if (newProduct == null) {
                MyNotify.MyNotifyAlertError("PLEASE COMPLETE ALL FIELDS !!! ");
                return;
            }
            if (validateInfo(newProduct, true)) {
                newProduct = productHelper.insert(newProduct);
                Navigator.getInstance().goToProductEdit(user, productCurrent);
                MyNotify.MyNotifyAlert("Insert Successfully");
            } else {
                alert.alertError("INSERT FAIL !! PLEASE TRY AGAIN !! ");

            }

        } else {
            Product updateProduct = extractProductFromFields();
            if (updateProduct == null) {
                MyNotify.MyNotifyAlertError("PLEASE COMPLETE ALL FIELDS !!! ");

                return;
            }
            if (validateInfo(updateProduct, false)) {

                updateProduct.setProductID(this.productCurrent.getProductID());
                boolean result = productHelper.update(updateProduct);
                if (result) {
                    MyNotify.MyNotifyAlert("Edited successfully");
                } else {
                    alert.alertError("EDIT FAIL ! PLEASE TRY AGAIN !");
                }
            } else {
                System.out.println("fail");
            }
        }
    }

    private Product extractProductFromFields() {
        try {
            Category category = categoryHelper.selectCategoryName(btnSelectCategory.getValue());
            Product product = new Product();

            product.setCategoryID(category.getCategoryID());
            product.setProductName(txtProductname.getText());
            product.setPrice(txtPrice.getText());
            product.setUrl(fullMovie.getText());
            product.setVideo(videoTrailer.getText());
            product.setImage(this.imageChoose);
            if (!txtPrice.getText().isEmpty()) {
                product.setPrice(txtPrice.getText());
            }
            product.setDescription(txtDescription.getText());
            return product;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    boolean validateInfo(Product product, boolean flag) {

        HashMap<String, String> Errors = new HashMap<String, String>();
        if (product.getProductName().trim().isEmpty()) {
            Errors.put("product", "Product Name cannot be left blank");
        }
        if (product.getDescription().trim().isEmpty()) {
            Errors.put("description", "Description cannot be left blank");
        }
        if (product.getVideo().trim().isEmpty()) {
            Errors.put("video", "video trailer cannot be left blank");
        } else if (!Validate.isYoutubeUrl(product.getVideo().trim())) {
            Errors.put("video", "video trailer is invalid");
        }
        if (product.getUrl().trim().isEmpty()) {
            Errors.put("URL", "Movie URL cannot be left blank");
        } else if (!Validate.isMovieUrl(product.getUrl().trim())) {
            Errors.put("URL", "Movie URL is invalid");
        }
        if (product.getPrice().trim().isEmpty()) {
            Errors.put("price", "Price cannot be left blank");
        } else if (!Validate.isNumeric(product.getPrice().trim())) {
            Errors.put("price", "Price is not a number");
        } else if (Double.parseDouble(product.getPrice().trim()) < 0) {
            Errors.put("price", "Price must be greater than 0");
        }

        if (flag) {
            if (this.imageChoose == null) {
                Errors.put("img", "Image can not be blank");
            }
        }


        if (Errors.size() == 0) {
            return true;
        } else {
            txtAlertName.setText(Errors.get("product"));
            txtAlertDescription.setText(Errors.get("description"));
            txtAlertPrice.setText(Errors.get("price"));
            textAlertImage.setText(Errors.get("image"));
            txtAlertVideo.setText(Errors.get("video"));
            txtAlertUrl.setText(Errors.get("URL"));
            return false;
        }

    }

    @FXML
    private void btnBackClick(ActionEvent event) throws IOException {
        Navigator.getInstance().goToProducts(user);
    }


    @FXML
    private void btnImageClick(ActionEvent event) throws IOException, InterruptedException {
        FileChooser filechooser = new FileChooser();
        filechooser.setTitle("Choose your image");
        Stage stage = (Stage) anchorpane.getScene().getWindow();
        filechooser.getExtensionFilters().addAll(new ExtensionFilter("Img File", "*.png", "*.jpeg", "*.jpg"));
        File file = filechooser.showOpenDialog(stage);
        if (file != null) {
            textAlertImage.setText("");
            this.imageChoose = Helper.convertImageToBytes(file.getAbsolutePath());
            imageMovie.setImage(Helper.convertByteToImage(this.imageChoose, imageMovie));
        }
    }

    private void checkDisplayError() {
        txtProductname.textProperty().addListener((observable, oldValue, newValue) -> {
            resetTextFields(txtAlertName);
            if (newValue.isEmpty()) {
                txtAlertName.setText("Product Name cannot be left blank");
            }
        });
        txtDescription.textProperty().addListener((observable, oldValue, newValue) -> {
            resetTextFields(txtAlertDescription);
            if (newValue.isEmpty()) {
                txtAlertDescription.setText("Description cannot be left blank");
            }
        });
        videoTrailer.textProperty().addListener((observable, oldValue, newValue) -> {
            resetTextFields(txtAlertVideo);
            if (newValue.isEmpty()) {
                txtAlertVideo.setText("Video Trailer cannot be left blank");
            } else if (!Validate.isYoutubeUrl(newValue)) {
                txtAlertVideo.setText("Video Trailer is invalid");
            }
        });
        fullMovie.textProperty().addListener((observable, oldValue, newValue) -> {
            txtAlertUrl.setText("");
            if (newValue.isEmpty()) {
                txtAlertUrl.setText("Url cannot be left blank");
            } else if (!Validate.isMovieUrl(newValue)) {
                txtAlertUrl.setText("Url is invalid");
            }
        });
        txtPrice.textProperty().addListener((observable, oldValue, newValue) -> {
            resetTextFields(txtAlertPrice);
            if (newValue.isEmpty()) {
                txtAlertPrice.setText("Price cannot be left blank");
            } else if (!Validate.isNumeric(newValue)) {
                txtAlertPrice.setText("Price is not a number");
            } else if (Double.parseDouble(newValue) < 0) {
                txtAlertPrice.setText("Price must be greater than 0");
            }
        });
    }

    private void resetTextFields(Label label) {
        label.setText("");
    }

}
