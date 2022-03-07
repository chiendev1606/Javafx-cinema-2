
package mepo.Helper;

import java.sql.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import mepo.Components.User;
import mepo.Implements.UserImp;


public class ClientHelper implements UserImp {

    @Override
    public ObservableList<User> selectAll() {
        ObservableList<User> Users = FXCollections.observableArrayList();
        try (
                Connection conn = DbHelper.getConnection();
        ) {
            assert conn != null;
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery("select * from client");) {
                while (rs.next()) {
                    User u = new User();
                    u.setUsername(rs.getString("username"));
                    u.setPassword(rs.getString("password_hash"));
                    u.setFullname(rs.getString("fullname"));
                    u.setContact(rs.getString("email"));
                    u.setCoin(rs.getDouble("coin"));


                    Users.add(u);
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return Users;
    }

    @Override
    public User insert(User newUser) {
        String sql = "INSERT INTO client (username, password_hash, fullname, email, coin) "
                + "VALUES (?, ?, ?, ?, ?)";
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
                stmt.setDouble(5, newUser.getCoin());
                stmt.executeUpdate();
                System.out.println("success");
                return newUser;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public boolean update(User updateUser) {
        String sql = "UPDATE client SET "
                + "password_hash = ?, "
                + "fullname = ?, "
                + "email = ? ,"
                + "coin = ?"
                + "WHERE username = ?";
        try (
                Connection conn = DbHelper.getConnection();
        ) {
            assert conn != null;
            try (PreparedStatement stmt = conn.prepareStatement(sql);) {
                stmt.setString(1, updateUser.doHashing(updateUser.getPassword()));
                stmt.setString(2, updateUser.getFullName());
                stmt.setString(3, updateUser.getContact());
                stmt.setDouble(4, updateUser.getCoin());
                stmt.setString(5, updateUser.getUsername());
                int rowUpdated = stmt.executeUpdate();
                if (rowUpdated == 1) {
                    System.out.println("Update Successfully");
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
        String sql = "DELETE FROM client WHERE username = ?";
        try (
                Connection conn = DbHelper.getConnection();
        ) {
            assert conn != null;
            try (PreparedStatement stmt = conn.prepareStatement(sql);) {
                stmt.setString(1, deleteUser.getUsername());

                int rowD = stmt.executeUpdate();
                if (rowD == 1) {
                    return true;
                } else {
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
        String sql = "SELECT * FROM client WHERE username = ?";
        User u = new User();
        try (
                Connection conn = DbHelper.getConnection();
        ) {
            assert conn != null;
            try (PreparedStatement stmt = conn.prepareStatement(sql);) {
                stmt.setString(1, selectUser);
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    u.setUsername(rs.getString("username"));
                    u.setPassword(rs.getString("password_hash"));
                    u.setFullname(rs.getString("fullname"));
                    u.setContact(rs.getString("email"));
                    u.setCoin(rs.getDouble("coin"));
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
        return u;
    }

    @Override
    public User selectUserByEmail(String Email) {
        String sql = "SELECT * FROM client WHERE email = ?";
        User user = null;
        try (Connection conn = DbHelper.getConnection();
        ) {
            assert conn != null;
            try (PreparedStatement stmt
                         = conn.prepareStatement(sql);) {
                stmt.setString(1, Email);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    user = new User();
                    user.setUsername(rs.getString("username"));
                    user.setPassword(rs.getString("password_hash"));
                    user.setFullname(rs.getString("fullname"));
                    user.setContact(rs.getString("email"));
                    user.setCoin(rs.getDouble("coin"));
                } else {
                    System.out.println("no data");
                }


            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return user;


    }


    @Override
    public int countClient() {
        String sql = "SELECT COUNT(*) AS result FROM client";
        int c = 0;
        try (
                Connection conn = DbHelper.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql);) {
            while (rs.next()) {
                c = rs.getInt("result");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return c;
        }
        return c;
    }
}
