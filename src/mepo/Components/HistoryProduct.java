package mepo.Components;

import javafx.beans.property.*;

import java.sql.Blob;

public class HistoryProduct {
    private ObjectProperty<Integer> productID;
    private ObjectProperty<Integer> categoryID;
    private StringProperty productname;
    private StringProperty price;
    private byte[] image = null;
    private StringProperty description;
    private StringProperty video;
    private StringProperty date;
    private StringProperty url;
    private DoubleProperty coin;

    public HistoryProduct() {
        productID = new SimpleObjectProperty<>(null);
        categoryID = new SimpleObjectProperty<>(null);
        productname = new SimpleStringProperty();
        price = new SimpleStringProperty();
        description = new SimpleStringProperty();
        video = new SimpleStringProperty();
        date = new SimpleStringProperty();
        url = new SimpleStringProperty();
        coin = new SimpleDoubleProperty();

    }

    public int getProductID() {
        return productID.get();
    }

    public ObjectProperty<Integer> productIDProperty() {
        return productID;
    }

    public double getCoin() {
        return coin.get();
    }

    public DoubleProperty coinProperty() {
        return coin;
    }

    public void setCoin(double coin) {
        this.coin.set(coin);
    }

    public void setProductID(Integer productID) {
        this.productID.set(productID);
    }

    public int getCategoryID() {
        return categoryID.get();
    }

    public ObjectProperty<Integer> categoryIDProperty() {
        return categoryID;
    }

    public void setCategoryID(Integer categoryID) {
        this.categoryID.set(categoryID);
    }

    public String getProductname() {
        return productname.get();
    }

    public StringProperty productnameProperty() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname.set(productname);
    }

    public String getPrice() {
        return price.get();
    }

    public StringProperty priceProperty() {
        return price;
    }

    public void setPrice(String price) {
        this.price.set(price);
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getDescription() {
        return description.get();
    }

    public StringProperty descriptionProperty() {
        return description;
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    public String getVideo() {
        return video.get();
    }

    public StringProperty videoProperty() {
        return video;
    }

    public void setVideo(String video) {
        this.video.set(video);
    }

    public String getDate() {
        return date.get();
    }

    public StringProperty dateProperty() {
        return date;
    }

    public void setDate(String date) {
        this.date.set(date);
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
