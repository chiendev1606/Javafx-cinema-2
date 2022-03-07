package mepo.Components;


import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class MyAlert {
    private Alert alert;

    public MyAlert(){
    }

    public void alertError(String text){
        this.alert = new Alert(Alert.AlertType.ERROR, text);
        alert.setHeaderText("Sorry, something went wrong !!!");
        DialogPane dialog = alert.getDialogPane();
        dialog.getStylesheets().add(getClass().getResource("/css/alertError.css").toExternalForm());
        Image image = new Image(getClass().getResource("/img/icon/warning.png").toExternalForm());
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(50);
        imageView.setFitWidth(50);
        alert.setGraphic(imageView);
        alert.showAndWait();
    }
    public void alertSuccess(String text){
        this.alert = new Alert(Alert.AlertType.INFORMATION, text);
        alert.setHeaderText(("Congratulations !!!").toUpperCase());
        DialogPane dialog = alert.getDialogPane();
        dialog.getStylesheets().add(getClass().getResource("/css/alertSuccess.css").toExternalForm());
        Image image = new Image(getClass().getResource("/img/icon/emoji.png").toExternalForm());
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(50);
        imageView.setFitWidth(50);
        alert.setGraphic(imageView);
        alert.showAndWait();
    }
    public ButtonType alertComfirm(String text){
        this.alert = new Alert(Alert.AlertType.CONFIRMATION, text, ButtonType.YES, ButtonType.NO);
        alert.setHeaderText("Something need to confirm !!");
        DialogPane dialog = alert.getDialogPane();
        dialog.getStylesheets().add(getClass().getResource("/css/alertError.css").toExternalForm());
        Image image = new Image(getClass().getResource("/img/icon/question-mark.png").toExternalForm());
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(50);
        imageView.setFitWidth(50);
        alert.setGraphic(imageView);
       alert.showAndWait();
       return this.alert.getResult();

    }



}

