package mepo.Helper;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import mepo.Components.Dialog;
import mepo.Implements.dialogImp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DialogHelper implements dialogImp {

    @Override
    public ObservableList<Dialog> selectAll() {
        ObservableList<Dialog> list = FXCollections.observableArrayList();
        try (
                Connection conn = DbHelper.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("select b.id, a.cardCode, b.username, b.date, a.value from card a, dialog b where a.cardCode = b.cardCode");) {
            while (rs.next()) {
                Dialog u = new Dialog();

                u.setId(rs.getInt("id"));
                u.setCardCode(rs.getInt("cardCode"));
                u.setDateTime(rs.getString("date"));
                u.setUserName(rs.getString("username"));
                u.setValue(rs.getInt("value"));

                list.add(u);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
        return list;
    }


    @Override
    public ObservableList<Dialog> selectDiaglogByUserName(String userName) {
        String sql = "select b.id, a.cardCode, b.username, b.date, a.value from card a, dialog b where a.cardCode = b.cardCode and b.username = ?";
        ObservableList<Dialog> list = FXCollections.observableArrayList();
        try (
                Connection conn = DbHelper.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);) {
            stmt.setString(1, userName);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Dialog u = new Dialog();

                u.setId(rs.getInt("id"));
                u.setCardCode(rs.getInt("cardCode"));
                u.setDateTime(rs.getString("date"));
                u.setUserName(rs.getString("username"));
                u.setValue(rs.getInt("value"));
                list.add(u);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
        return list;
    }

    @Override
    public Dialog insertDialog(Dialog dialog) {
        dialog.InsertDateTime();
        String sql = "INSERT INTO dialog (cardCode, username, date) "
                + "VALUES (?, ?, ?)";
        ResultSet key = null;
        try (
                Connection conn = DbHelper.getConnection();
                PreparedStatement stmt
                        = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {
            stmt.setInt(1, dialog.getCardCode());
            stmt.setString(2, dialog.getUserName());
            stmt.setString(3, dialog.getDateTime());

            int rowInserted = stmt.executeUpdate();
            if (rowInserted == 1) {
                key = stmt.getGeneratedKeys();
                key.next();
                int newKey = key.getInt(1);
                dialog.setId(newKey);
                System.out.println("Insert Successfully");
                return dialog;
            } else {
                System.out.println("Insert Failse");
                return null;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }


}
