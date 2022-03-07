
package mepo.Components;

import javafx.beans.property.*;
import org.mindrot.jbcrypt.BCrypt;

public class User {

    private StringProperty username;
    private StringProperty password;
    private StringProperty fullname;
    private StringProperty contact;
    private DoubleProperty coin;

    @Override
    public String toString() {
        return getUsername() + " " + getFullName() + " " + getCoin();
    }

    public User() {
        username = new SimpleStringProperty();
        password = new SimpleStringProperty();
        fullname = new SimpleStringProperty();
        contact = new SimpleStringProperty();
        coin = new SimpleDoubleProperty();
    }

    public User(String username, String password, String fullname, String contact, Double coin) {
        this.username = new SimpleStringProperty(username);
        this.password = new SimpleStringProperty(password);
        this.fullname = new SimpleStringProperty(fullname);
        this.contact = new SimpleStringProperty(contact);
        this.coin = new SimpleDoubleProperty(coin);
    }

    public String getUsername() {
        return username.get();
    }

    public String getPassword() {
        return password.get();
    }

    public String getFullName() {
        return fullname.get();
    }

    public String getContact() {
        return contact.get();
    }

    public double getCoin() {
        return coin.get();
    }

    public void setUsername(String username) {
        this.username.set(username);
    }

    public void setPassword(String password) {
        this.password.set(password);
    }


    public void setFullname(String fullname) {
        this.fullname.set(fullname);
    }

    public void setContact(String contact) {
        this.contact.set(contact);
    }

    public void setCoin(Double coin) {
        this.coin.set(coin);
    }

    public StringProperty getUsernameProperty() {
        return this.username;
    }

    public StringProperty getPasswordProperty() {
        return this.password;
    }


    public StringProperty getFullnameProperty() {
        return this.fullname;
    }

    public StringProperty getContactProperty() {
        return this.contact;
    }

    public DoubleProperty getCoinProperty() {
        return this.coin;
    }

    public String doHashing(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt(12));
    }
}
