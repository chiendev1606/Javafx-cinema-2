package mepo.Components;

import com.jfoenix.controls.JFXTextField;
import javafx.scene.control.Label;

import java.awt.*;
import java.io.*;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.scene.image.Image;
import org.mindrot.jbcrypt.BCrypt;


public class Validate {
    public static boolean isValidEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        Pattern p = Pattern.compile(ePattern);
        Matcher m = p.matcher(email);
        return m.matches();
    }
    public static boolean isYoutubeUrl(String url) {
        String ePattern = "^((?:https?:)?\\/\\/)?((?:www|m)\\.)?((?:youtube\\.com|youtu.be))(\\/(?:[\\w\\-]+\\?v=|embed\\/|v\\/)?)([\\w\\-]+)(\\S+)?$";
        Pattern p = Pattern.compile(ePattern);
        Matcher m = p.matcher(url);
        return m.matches();
    }
    public static boolean isMovieUrl(String url) {
        String ePattern = "^https?:\\/\\/(www\\.)?[-a-zA-Z0-9@:%._\\+~#=]{2,256}\\.[a-z]{2,4}\\b([-a-zA-Z0-9@:%_\\+.~#?&//=]*)$";
        Pattern p = Pattern.compile(ePattern);
        Matcher m = p.matcher(url);
        return m.matches();
    }
    public static void validateCodeEmail(JFXTextField jfxTextField, Label label) {
        jfxTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            label.setText("");
            if (newValue.isEmpty()) {
                label.setText("Code can be not blank");
            } else if (newValue.length() < 6) {
                label.setText("Code must be 6 digits");
            } else if (!isNumeric(newValue)) {
                label.setText("Code must be number");
            }
        });
    }

    public static boolean isNumeric(String str) {
        try {
            Float.parseFloat(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    public static boolean checkPwHash(String hashString, String pw ){
        return BCrypt.checkpw(pw, hashString);
    }




}
