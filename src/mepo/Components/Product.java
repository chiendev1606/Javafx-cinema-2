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


public class Product {
    private ObjectProperty<Integer> productID;
    private ObjectProperty<Integer> categoryID;
    private StringProperty productname;
    private StringProperty price;
    private StringProperty description;
    private StringProperty video;
    private StringProperty url;
    private byte[] image = null;


    public Product() {
        productID = new SimpleObjectProperty<>(null);
        categoryID = new SimpleObjectProperty<>(null);
        productname = new SimpleStringProperty();
        price = new SimpleStringProperty();
        description = new SimpleStringProperty();
        video = new SimpleStringProperty();
        url = new SimpleStringProperty();
    }

    @Override
    public String toString() {
        return getProductID() + " " + getProductName() + " " + getPrice();
    }

    public int getProductID() {
        return productID.get();
    }

    public int getCategoryID() {
        return categoryID.get();
    }

    public String getProductName() {
        return productname.get();
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getPrice() {
        return price.get();
    }

    public String getDescription() {
        return description.get();
    }

    public String getVideo() {
        return video.get();
    }

    public void setProductID(int id) {
        this.productID.set(id);
    }

    public void setCategoryID(int id) {
        this.categoryID.set(id);
    }

    public void setProductName(String name) {
        this.productname.set(name);
    }

    public void setPrice(String name) {
        this.price.set(name);
    }

    public void setDescription(String name) {
        this.description.set(name);
    }

    public void setVideo(String video) {
        this.video.set(video);
    }


    public ObjectProperty<Integer> getProductIDProperty() {
        return this.productID;
    }

    public ObjectProperty<Integer> getCategoryIDProperty() {
        return this.categoryID;
    }

    public StringProperty getProductNameProperty() {
        return this.productname;
    }

    public StringProperty getPriceProperty() {
        return this.price;
    }

    public StringProperty descriptionProperty() {
        return description;
    }

    public StringProperty getVideoProperty() {
        return this.video;
    }

    public String getUrl() {
        return url.get();
    }

    public StringProperty urlProperty() {
        return url;
    }


    public void setUrl(String url) {
        this.url.set(url);
    }
}


