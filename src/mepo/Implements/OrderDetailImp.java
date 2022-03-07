
package mepo.Implements;

import javafx.collections.ObservableList;
import mepo.Components.OrderDetail;
import mepo.Components.Product;


public interface OrderDetailImp {

    OrderDetail insert(OrderDetail od);

    boolean update(OrderDetail od);

    boolean delete(OrderDetail od);

    ObservableList<OrderDetail> selectAll();

    boolean deleteAllOrderDetailByOrderID(int id);

    ObservableList<OrderDetail> selectOrderDetailByOrderID(int id);

    Product selectProductsByOrderProductsID(int id);
}
