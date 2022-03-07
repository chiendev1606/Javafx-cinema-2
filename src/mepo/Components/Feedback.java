/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mepo.Components;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.CheckBox;

public class Feedback {

    private ObjectProperty<Integer> feedbackID;
    private ObjectProperty<Integer> productID;
    private StringProperty username;
    private StringProperty content;
    private StringProperty date;
    private BooleanProperty select;
    private CheckBox box;

    public Feedback() {
        feedbackID = new SimpleObjectProperty<>(null);
        productID = new SimpleObjectProperty<>(null);
        username = new SimpleStringProperty();
        content = new SimpleStringProperty();
        date = new SimpleStringProperty();
        select = new SimpleBooleanProperty();
        box = new CheckBox();
    }

    public int getFeedbackID() {
        return feedbackID.get();
    }

    public int getProductID() {
        return productID.get();
    }

    public String getUsername() {
        return username.get();
    }

    public String getContent() {
        return content.get();
    }

    public String getDate() {
        return date.get();
    }

    public Boolean getSelect() {
        return select.get();
    }
    public CheckBox getBox(){
        return this.box;
    }

    public void setFeedbackID(int id) {
        this.feedbackID.set(id);
    }

    public void setProductID(int id) {
        this.productID.set(id);
    }

    public void setUsername(String name) {
        this.username.set(name);
    }

    public void setContent(String content) {
        this.content.set(content);
    }

    public void setDate(String date) {
        this.date.set(date);
    }

    public void setSelect(Boolean select) {
        this.select.set(select);
    }
    
    public void setBox(CheckBox b){
        this.box = b;
    }

    public ObjectProperty<Integer> getFeedbackIDProperty() {
        return this.feedbackID;
    }

    public ObjectProperty<Integer> getProductIDProperty() {
        return this.productID;
    }

    public StringProperty getUsernameProperty() {
        return this.username;
    }

    public StringProperty getContentProperty() {
        return this.content;
    }

    public StringProperty getDateProperty() {
        return this.date;
    }

    public BooleanProperty getSelectProperty() {
        return this.select;
    }

}
