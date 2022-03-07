package mepo.Helper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import mepo.Components.User;
import mepo.Implements.UserImp;


public class AdminHelper implements UserImp {

    @Override
    public ObservableList<User> selectAll() {
        ObservableList<User> Users = FXCollections.observableArrayList();
        try (
                Connection conn = DbHelper.getConnection();
        ) {
            assert conn != null;
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery("select * from admin");) {
                while (rs.next()) {
                    User u = new User();
                    u.setUsername(rs.getString("username"));
                    u.setPassword(rs.getString("password_hash"));
                    u.setFullname(rs.getString("fullname"));
                    u.setContact(rs.getString("contact"));
                    Users.add(u);
                }
            }
        } catch (Exception e) {
            return null;
        }
        return Users;
    }

    @Override
    public User insert(User newUser) {
        String sql = "INSERT INTO admin (username, password_hash, fullname, contact) "
                + "VALUES (?, ?, ?, ?)";
        ResultSet key = null;
        try (
                Connection conn = DbHelper.getConnection();
        ) {
            assert conn != null;
            try (PreparedStatement stmt
                    = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {
                stmt.setString(1, newUser.getUsername());
                stmt.setString(2, newUser.doHashing(newUser.getPassword()));
                stmt.setString(3, newUser.getFullName());
                stmt.setString(4, newUser.getContact());
                stmt.executeUpdate();
                return newUser;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public boolean update(User updateUser) {
        String sql = "UPDATE admin SET "
                + "password_hash = ?, "
                + "fullname = ?, "
                + "contact = ? "
                + "WHERE username = ?";
        try (
                Connection conn = DbHelper.getConnection();
        ) {
            assert conn != null;
            try (PreparedStatement stmt = conn.prepareStatement(sql);) {
                stmt.setString(1, updateUser.doHashing(updateUser.getPassword()));
                stmt.setString(2, updateUser.getFullName());
                stmt.setString(3, updateUser.getContact());
                stmt.setString(4, updateUser.getUsername());
                int rowUpdated = stmt.executeUpdate();
                if (rowUpdated == 1) {
                    System.out.println("Update Succesfully");
                    return true;
                } else {
                    System.out.println("Update Fail");
                    return false;
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public boolean delete(User deleteUser) {
        String sql = "DELETE FROM admin WHERE username = ?";
        try (
                Connection conn = DbHelper.getConnection();
        ) {
            assert conn != null;
            try (PreparedStatement stmt = conn.prepareStatement(sql);) {
                stmt.setString(1, deleteUser.getUsername());
                int rowD = stmt.executeUpdate();
                if (rowD == 1) {
                    System.out.println("Delete Successfully");
                    return true;
                } else {
                    System.out.println("Delete Fail");
                    return false;
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public User selectUsername(String selectUser) {
        String sql = "SELECT * FROM admin WHERE username = ?";
        User user = new User();
        try (
                Connection conn = DbHelper.getConnection();
        ) {
            assert conn != null;
            try (PreparedStatement stmt = conn.prepareStatement(sql);){
                stmt.setString(1, selectUser);
                 ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    user.setUsername(rs.getString("username"));
                    user.setPassword(rs.getString("password_hash"));
                    user.setFullname(rs.getString("fullname"));
                    user.setContact(rs.getString("contact"));
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
        return user;
    }

    @Override
    public User selectUserByEmail(String Email) {
        return null;
    }

    @Override
    public int countClient() {
        String sql = "SELECT COUNT(*) AS result FROM client";
        int count = 0;
        try (
                Connection conn = DbHelper.getConnection();
        ) {
            assert conn != null;
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(sql);) {
                while (rs.next()) {
                    count = rs.getInt("result");
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return count;
        }
        return count;
    }
}
