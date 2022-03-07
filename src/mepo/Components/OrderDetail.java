/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mepo.Components;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;


public class OrderDetail {

    private ObjectProperty<Integer> orderDetailID;
    private ObjectProperty<Integer> productID;
    private ObjectProperty<Integer> orderID;

    public OrderDetail() {
        orderDetailID = new SimpleObjectProperty();
        productID = new SimpleObjectProperty();
        orderID = new SimpleObjectProperty();
    }

    public int getOrderDetailID() {
        return orderDetailID.get();
    }

    public int getProductID() {
        return productID.get();
    }

    public int getOrderID() {
        return orderID.get();
    }

    public void setOrderDetailID(int id) {
        this.orderDetailID.set(id);
    }

    public void setProductID(int id) {
        this.productID.set(id);
    }

    public void setOrderID(int id) {
        this.orderID.set(id);
    }

    public ObjectProperty<Integer> getOrderDetailIDProperty() {
        return this.orderDetailID;
    }

    public ObjectProperty<Integer> getProductIDProperty() {
        return this.productID;
    }

    public ObjectProperty<Integer> getOrderIDProperty() {
        return this.orderID;
    }


}
