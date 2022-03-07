/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mepo.Components;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


public class Category {

    private ObjectProperty<Integer> categoryID;
    private StringProperty categoryname;
    private StringProperty description;

    public Category() {
        categoryID = new SimpleObjectProperty<Integer>();
        categoryname = new SimpleStringProperty();
        description = new SimpleStringProperty();
    }

    public int getCategoryID() {
        return categoryID.get();
    }

    public String getCategoryName() {
        return categoryname.get();
    }

    public String getDescriptionName() {
        return description.get();
    }

    public void setCategoryID(int id) {
        this.categoryID.set(id);
    }

    public void setCategoryName(String name) {
        this.categoryname.set(name);
    }

    public void setDescriptionName(String des) {
        this.description.set(des);
    }

    public ObjectProperty<Integer> getCategoryIDProperty() {
        return this.categoryID;
    }

    public StringProperty getCategoryNameProperty() {
        return this.categoryname;
    }

    public StringProperty getDescriptionProperty() {
        return this.description;
    }
}
