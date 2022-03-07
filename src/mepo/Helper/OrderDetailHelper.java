
package mepo.Helper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import mepo.Components.OrderDetail;
import mepo.Components.Product;
import mepo.Implements.OrderDetailImp;


public class OrderDetailHelper implements OrderDetailImp {

    @Override
    public ObservableList<OrderDetail> selectAll() {
        ObservableList<OrderDetail> od = FXCollections.observableArrayList();
        try (
                Connection conn = DbHelper.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM orderdetail");) {
            while (rs.next()) {
                OrderDetail o = new OrderDetail();
                o.setOrderDetailID(rs.getInt("orderdetailID"));
                o.setProductID(rs.getInt("productID"));
                o.setProductID(rs.getInt("orderID"));
//                o.setQuantity(rs.getInt("quantity"));
                od.add(o);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return od;
    }

    @Override
    public OrderDetail insert(OrderDetail od) {
        String sql = "INSERT INTO orderdetail (productID, orderID) "
                + "VALUES(?, ?)";
        ResultSet key = null;
        try (
                Connection conn = DbHelper.getConnection();
                PreparedStatement stmt
                        = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {
            stmt.setInt(1, od.getProductID());
            stmt.setInt(2, od.getOrderID());
//            stmt.setInt(3, od.getQuantity());
            int rowInserted = stmt.executeUpdate();
            if (rowInserted == 1) {
                key = stmt.getGeneratedKeys();
                key.next();
                int newKey = key.getInt(1);
                od.setOrderDetailID(newKey);
                System.out.println("Insert Succesfully");
                return od;
            } else {
                System.out.println("Insert Fail");
                return null;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public boolean update(OrderDetail od) {
        String sql = "UPDATE orderdetail SET "
                + "productID = ?, "
                + "orderID = ?, "
                + "WHERE orderdetailID = ?";
        try (
                Connection conn = DbHelper.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);) {
            stmt.setInt(1, od.getProductID());
            stmt.setInt(2, od.getOrderID());
            stmt.setInt(4, od.getOrderDetailID());

            int rowUpdated = stmt.executeUpdate();
            if (rowUpdated == 1) {
                System.out.println("Update Succesfully");
                return true;
            } else {
                System.out.println("Update Fail");
                return false;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public boolean delete(OrderDetail od) {
        String sql = "DELETE FROM orderdetail WHERE orderdetailID = ?";
        try (
                Connection conn = DbHelper.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);) {
            stmt.setInt(1, od.getOrderDetailID());
            int rowD = stmt.executeUpdate();
            if (rowD == 1) {
                System.out.println("Delete Successfully");
                return true;
            } else {
                System.out.println("Delete Fail");
                return false;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public boolean deleteAllOrderDetailByOrderID(int id) {
        String sql = "DELETE FROM orderdetail WHERE orderID = ?";
        try (
                Connection conn = DbHelper.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);) {
            stmt.setInt(1, id);
            int rowD = stmt.executeUpdate();
            if (rowD > 0) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public ObservableList<OrderDetail> selectOrderDetailByOrderID(int id) {
        ObservableList<OrderDetail> OrderDetails = FXCollections.observableArrayList();
        try (
                Connection conn = DbHelper.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM orderdetail WHERE orderID = " + id);) {
            while (rs.next()) {
                OrderDetail o = new OrderDetail();

                o.setOrderDetailID(rs.getInt("orderdetailID"));
                o.setProductID(rs.getInt("productID"));
                o.setOrderID(rs.getInt("orderID"));
                OrderDetails.add(o);
            }
        } catch (Exception e) {
        }
        return OrderDetails;
    }

    @Override
    public Product selectProductsByOrderProductsID(int id) {
        Product p = new Product();
        try (
                Connection conn = DbHelper.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM product WHERE productID = " + id);) {
            while (rs.next()) {
                p.setProductID(rs.getInt("productID"));
                p.setCategoryID(rs.getInt("categoryID"));
                p.setProductName(rs.getString("productname"));
                p.setPrice(rs.getString("price"));
                p.setImage(rs.getBytes("image"));
                p.setDescription(rs.getString("description"));

            }
        } catch (Exception e) {
        }
        return p;
    }


}
