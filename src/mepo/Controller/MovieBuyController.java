package mepo.Controller;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import mepo.Components.Helper;
import mepo.Components.HistoryProduct;
import mepo.Components.Product;
import mepo.Components.User;
import mepo.Helper.ProductHelper;
import mepo.Implements.ProductImp;
import mepo.Navigator;

import java.io.IOException;
import java.util.Objects;

public class MovieBuyController {
    private HistoryProduct product = null;
    private User user = null;
    @FXML
    private ImageView img;

    @FXML
    void GameClicked(MouseEvent event) throws IOException {
     Navigator.getInstance().gotoWatchMovie(product, user);
    }

    public void setData(HistoryProduct historyProduct, User user) {
        this.user = user;
        this.product = historyProduct;
        img.setImage(Helper.convertByteToImage(this.product.getImage(), img));
    }


}