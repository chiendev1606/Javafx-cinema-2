package mepo;


import java.io.IOException;
import java.sql.*;

import javafx.application.Application;
import javafx.stage.Stage;


public class main extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException, SQLException {
        Navigator.getInstance().setStage(primaryStage);
        Navigator.getInstance().getStage().setTitle("Mepo Cinema");
        Navigator.getInstance().goToLogin();
        Navigator.getInstance().getStage().show();
    }

    public static void main(String[] args) throws IOException {
//        Helper.createCardCodeAutomatic();
        launch(args);

    }
}













