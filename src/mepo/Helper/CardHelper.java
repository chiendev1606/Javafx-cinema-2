package mepo.Helper;

import mepo.Components.Card;
import mepo.Implements.CardImp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CardHelper implements CardImp {
    @Override
    public List<Card> selectAll() {
        List<Card> list = new ArrayList<>();
        try (
                Connection conn = DbHelper.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("select * from card")) {
            while (rs.next()) {
                Card u = new Card();
               u.setUsed(rs.getBoolean("used"));
               u.setValue(rs.getInt("value"));
               u.setCode(rs.getInt("cardCode"));
                list.add(u);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("ok");
            return null;
        }
        return list;
    }

    @Override
    public boolean updateUsed(Card card) {
        String sql = "UPDATE card SET used = ? WHERE cardCode = ?";
        try (
                Connection conn = DbHelper.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);) {
            stmt.setBoolean(1, card.isUsed());
            stmt.setInt(2, card.getCode());
            int rowUpdated = stmt.executeUpdate();
            if (rowUpdated == 1) {
                System.out.println("Update Successfully");
                return true;
            } else {
                System.out.println("Update Failse");
                return false;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public void insert(Card card) {
        String sql = "INSERT INTO card (cardCode, value, used ) "
                + "VALUES (?, ?, ?)";
        ResultSet key = null;
        try (
                Connection conn = DbHelper.getConnection();
                PreparedStatement stmt
                        = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {
            stmt.setInt(1, card.getCode());
            stmt.setInt(2, card.getValue());
            stmt.setBoolean(3, card.isUsed());
            int rowInserted = stmt.executeUpdate();
            if (rowInserted == 1) {
                System.out.println("Insert Card Successfully");
            } else {
                System.out.println("Insert card ");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    @Override
    public List<Integer> selectCodeAll() {
        List<Integer> list = new ArrayList<>();
        try (
                Connection conn = DbHelper.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("select cardCode from card");) {
            while (rs.next()) {

                list.add(rs.getInt("cardCode"));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
        return list;
    }


}
