/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mepo.Controller;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Img_productsController {

    @FXML
    private ImageView img;
    @FXML

    /**
     * Initializes the controller class.
     */
    public void initialize(URL url, ResourceBundle rb) {
    }

    public void setData(String s) {
        String link = "/img/" + s;
        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream(link)));
        img.setImage(image);
    }

}
