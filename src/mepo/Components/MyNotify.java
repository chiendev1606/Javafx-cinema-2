package mepo.Components;


import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;




public class MyNotify {
    public static   void MyNotifyAlert(String content){
        Image image = new Image("/img/icon/check-mark.png");
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(40);
        imageView.setFitWidth(40);
        Notifications notify = Notifications.create().title("").graphic(imageView).text(content).hideAfter(Duration.seconds(2)).position(Pos.CENTER).darkStyle();
        notify.show();
    }
    public static void MyNotifyAlertError(String content){
        Image image = new Image("/img/icon/error.png");
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(40);
        imageView.setFitWidth(40);
        Notifications notify = Notifications.create().title("").graphic(imageView).text(content).hideAfter(Duration.seconds(2)).position(Pos.CENTER).darkStyle();
        notify.show();
    }

}
