
package mepo.Helper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import mepo.Components.Feedback;
import mepo.Implements.FeedbackImp;


public class FeedbackHelper implements FeedbackImp {

    @Override
    public ObservableList<Feedback> selectAll() {
        ObservableList<Feedback> feedbacks = FXCollections.observableArrayList();
        try (
                Connection conn = DbHelper.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("select * from feedback");) {
            while (rs.next()) {
                Feedback f = new Feedback();

                f.setFeedbackID(rs.getInt("feedbackID"));
                f.setProductID(rs.getInt("productID"));
                f.setUsername(rs.getString("username"));
                f.setContent(rs.getString("content"));
                f.setDate(rs.getString("date"));
                f.setSelect(rs.getBoolean("select"));
                feedbacks.add(f);
            }
        } catch (Exception e) {
        }
        return feedbacks;
    }

    @Override
    public Feedback insert(Feedback f) {
        String sql = "INSERT INTO feedback (productID, username, content, date, `select`) "
                + "VALUES (?, ?, ?, ?, ?)";
        ResultSet key = null;
        try (
                Connection conn = DbHelper.getConnection();
                PreparedStatement stmt
                        = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {

            stmt.setInt(1, f.getProductID());
            stmt.setString(2, f.getUsername());
            stmt.setString(3, f.getContent());
            stmt.setString(4, f.getDate());
            stmt.setBoolean(5, f.getSelect());
            int rowInserted = stmt.executeUpdate();
            if (rowInserted == 1) {
                key = stmt.getGeneratedKeys();
                key.next();
                int newKey = key.getInt(1);
                f.setFeedbackID(newKey);
                System.out.println("Insert Successfully");
                return f;
            } else {
                System.out.println("Insert Failse");
                return null;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public boolean update(Feedback feedback) {
        String sql = "UPDATE feedback SET "
                + "`select` = ? "
                + "WHERE feedbackID = ?";
        try (
                Connection conn = DbHelper.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);) {
            stmt.setBoolean(1, feedback.getSelect());
            stmt.setInt(2, feedback.getFeedbackID());
            int rowUpdated = stmt.executeUpdate();
            if (rowUpdated == 1) {
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
    public boolean delete(Feedback feedback) {
        String sql = "DELETE FROM feedback WHERE feedbackID = ?";
        try (
                Connection conn = DbHelper.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);) {
            stmt.setInt(1, feedback.getFeedbackID());
            int rowD = stmt.executeUpdate();
            if (rowD == 1) {
                System.out.println("Delete Successfully");
                return true;
            } else {
                System.out.println("Delete Failse");
                return false;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public String selectProductNameByID(int id) {
        String sql = "SELECT * FROM product WHERE productID = " + id;
        String str = "";
        try (
                Connection conn = DbHelper.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql);) {
            while (rs.next()) {
                str = rs.getString("productname");
            }
        } catch (Exception e) {
            System.out.println("here");
            return null;
        }
        return str;
    }

    @Override
    public ObservableList<Feedback> selectFeedbackByProductID(int id) {
        ObservableList<Feedback> feedbacks = FXCollections.observableArrayList();
        try (
                Connection conn = DbHelper.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM feedback WHERE productID = " + id);) {
            while (rs.next()) {
                Feedback feedback = new Feedback();

                feedback.setFeedbackID(rs.getInt("feedbackID"));
                feedback.setProductID(rs.getInt("productID"));
                feedback.setUsername(rs.getString("username"));
                feedback.setContent(rs.getString("content"));
                feedback.setDate(rs.getString("date"));
                feedback.setSelect(rs.getBoolean("select"));
                feedbacks.add(feedback);
            }
        } catch (Exception e) {
        }
        return feedbacks;
    }

    @Override
    public int countFeedback() {
        String sql = "SELECT COUNT(*) AS result FROM feedback";
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
