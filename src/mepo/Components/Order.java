/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mepo.Components;

import java.util.Comparator;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


public class Order {

    public static void sort(Comparator<Order> comparator) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    private ObjectProperty<Integer> orderID;
    private StringProperty username;
    private StringProperty orderDate;
    private StringProperty accountAddress;
    
    public Order(){
        orderID = new SimpleObjectProperty<>(null); 
        username = new SimpleStringProperty();
        orderDate = new SimpleStringProperty();
        accountAddress = new SimpleStringProperty();
    }
    
    public int getOrderID(){
        return this.orderID.get();
    }
    public String getUsername(){
        return this.username.get();
    }
    public String getOrderDate(){
        return this.orderDate.get();
    }
    public String getAccountAddress(){
        return this.accountAddress.get();
    }
    public void setOrderId(int id){
        this.orderID.set(id);
    }
    public void setUsername(String username){
        this.username.set(username);
    }
    public void setOrderDate(String date){
        this.orderDate.set(date);
    }
    public void setAccountAddress(String add){
        this.accountAddress.set(add);
    }

    
    public ObjectProperty<Integer> getOrderIDProperty(){
        return this.orderID;
    }
    public StringProperty getUsernameProperty(){
        return this.username;
    }
    public StringProperty getOrderDateProperty(){
        return this.orderDate;
    }
    public StringProperty getAccountAddressProperty(){
        return this.accountAddress;
    }

        
}
