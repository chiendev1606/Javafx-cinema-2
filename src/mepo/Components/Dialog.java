package mepo.Components;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Dialog {
    private ObjectProperty<Integer> id;
    private ObjectProperty<Integer> cardCode;
    private StringProperty userName;
    private StringProperty dateTime;
    private ObjectProperty<Integer> value;


    public Dialog() {
        this.id = new SimpleObjectProperty<>(null);
        this.cardCode = new SimpleObjectProperty<>(null);
        this.userName = new SimpleStringProperty();
        this.dateTime = new SimpleStringProperty();
        this.value = new SimpleObjectProperty<>(null);
    }

    public int getId() {
        return id.get();
    }

    public Integer getValue() {
        return value.get();
    }

    public ObjectProperty<Integer> valueProperty() {
        return value;
    }

    public void setValue(Integer value) {
        this.value.set(value);
    }

    public ObjectProperty<Integer> idProperty() {
        return id;
    }

    public void setId(Integer id) {
        this.id.set(id);
    }

    public int getCardCode() {
        return cardCode.get();
    }

    public ObjectProperty<Integer> cardCodeProperty() {
        return cardCode;
    }

    public void setCardCode(Integer cardCode) {
        this.cardCode.set(cardCode);
    }

    public String getUserName() {
        return userName.get();
    }

    public StringProperty userNameProperty() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName.set(userName);
    }

    public String getDateTime() {
        return dateTime.get();
    }

    public StringProperty dateTimeProperty() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime.set(dateTime);
    }

    public void InsertDateTime(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        String dateTimeNow = now.format(formatter);
        this.setDateTime(dateTimeNow);
    }
}
