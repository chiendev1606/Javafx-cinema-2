
package mepo.Implements;

import javafx.collections.ObservableList;
import mepo.Components.Order;
import mepo.Components.User;


public interface OrderImp {

    Order insert(Order od);

//    boolean update(Order od);

    boolean delete(Order od);

    ObservableList<Order> selectAll();

    Order selectOrderByOrderID(int id);
    
    ObservableList<Order> selectAllByUsername(User username);
}
