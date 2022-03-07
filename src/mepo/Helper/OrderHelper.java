
package mepo.Helper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import mepo.Components.Order;
import mepo.Components.User;
import mepo.Implements.OrderImp;


public class OrderHelper implements OrderImp {

    @Override
    public ObservableList<Order> selectAll() {
        ObservableList<Order> list = FXCollections.observableArrayList();
        try (
                Connection conn = DbHelper.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM `order`");) {
            while (rs.next()) {
                Order order = new Order();
                order.setOrderId(rs.getInt("orderID"));
                order.setUsername(rs.getString("username"));
                order.setOrderDate(rs.getString("orderdate"));
                order.setAccountAddress(rs.getString("accountaddress"));
                list.add(order);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return list;
    }

    @Override
    public ObservableList<Order> selectAllByUsername(User username) {
        ObservableList<Order> list = FXCollections.observableArrayList();
        try (
                Connection conn = DbHelper.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM order WHERE username = \"" + username.getUsername() + "\"");) {
            while (rs.next()) {
                Order order = new Order();
                order.setOrderId(rs.getInt("orderID"));
                order.setUsername(rs.getString("username"));
                order.setOrderDate(rs.getString("orderdate"));
                order.setAccountAddress(rs.getString("accountaddress"));
                list.add(order);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return list;
    }

    @Override
    public Order insert(Order order) {
        String sql = "INSERT INTO `order` (username, orderdate, accountaddress) "
                + "VALUES(?, ?, ?)";
        ResultSet key = null;
        try (
                Connection conn = DbHelper.getConnection();
                PreparedStatement stmt
                        = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {
            stmt.setString(1, order.getUsername());
            stmt.setString(2, order.getOrderDate());
            stmt.setString(3, order.getAccountAddress());
            int rowInserted = stmt.executeUpdate();
            if (rowInserted == 1) {
                key = stmt.getGeneratedKeys();
                key.next();
                int newKey = key.getInt(1);
                order.setOrderId(newKey);
                System.out.println("Insert Successfully");
                return order;
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
    public boolean delete(Order order) {
        String sql = "DELETE FROM order WHERE orderID = ?";
        try (
                Connection conn = DbHelper.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);) {
            stmt.setInt(1, order.getOrderID());
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
    public Order selectOrderByOrderID(int id) {
        Order order = new Order();
        try (
                Connection conn = DbHelper.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM order WHERE orderID = " + id);) {
            while (rs.next()) {
                order.setOrderId(rs.getInt("orderID"));
                order.setUsername(rs.getString("username"));
                order.setOrderDate(rs.getString("orderdate"));
                order.setAccountAddress(rs.getString("accountaddress"));
            }
        } catch (Exception e) {
        }
        return order;
    }

}
