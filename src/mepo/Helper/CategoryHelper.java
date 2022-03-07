
package mepo.Helper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import mepo.Components.Category;
import mepo.Implements.CategoryImp;

public class CategoryHelper implements CategoryImp {

    @Override
    public ObservableList<Category> selectAll() {
        ObservableList<Category> cate = FXCollections.observableArrayList();
        try (
                Connection conn = DbHelper.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM category");) {
            while (rs.next()) {
                Category c = new Category();
                c.setCategoryID(rs.getInt("categoryID"));
                c.setCategoryName(rs.getString("categoryname"));
                c.setDescriptionName(rs.getString("description"));
                cate.add(c);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return cate;
    }

    @Override
    public Category insert(Category newcategory) {
        String sql = "INSERT INTO category (categoryname, description) "
                + "VALUES(?, ?)";
        ResultSet key = null;
        try (
                Connection conn = DbHelper.getConnection();
                PreparedStatement stmt
                        = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {
            stmt.setString(1, newcategory.getCategoryName());
            stmt.setString(2, newcategory.getDescriptionName());
            int rowInserted = stmt.executeUpdate();
            if (rowInserted == 1) {
                key = stmt.getGeneratedKeys();
                key.next();
                int newKey = key.getInt(1);
                newcategory.setCategoryID(newKey);
                System.out.println("Insert Successfully");
                return newcategory;
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
    public boolean update(Category updateCategory) {
        String sql = "UPDATE category SET "
                + "categoryname = ?, "
                + "description = ? "
                + "WHERE categoryID = ?";
        try (
                Connection conn = DbHelper.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);) {
            stmt.setString(1, updateCategory.getCategoryName());
            stmt.setString(2, updateCategory.getDescriptionName());
            stmt.setInt(3, updateCategory.getCategoryID());

            int rowUpdated = stmt.executeUpdate();
            if (rowUpdated == 1) {
                System.out.println("Update Successfully");
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
    public boolean delete(Category deleteCategory) {
        String sql = "DELETE FROM category WHERE categoryID = ?";
        try (
                Connection conn = DbHelper.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);) {
            stmt.setInt(1, deleteCategory.getCategoryID());
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
    public Category selectCategoryName(String name) {
        String sql = "SELECT * FROM category WHERE categoryname = ?";
        Category category = null;
        try (
                Connection conn = DbHelper.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);) {
            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                 category = new Category();
                category.setCategoryID(rs.getInt("categoryID"));
                category.setCategoryName(rs.getString("categoryname"));
                category.setDescriptionName(rs.getString("description"));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
        return category;
    }

    @Override
    public String selectNameById(int id) {
        String sql = "SELECT categoryname FROM category WHERE categoryID = ?";
        String result = "";
        try (
                Connection conn = DbHelper.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                result = rs.getString("categoryname");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());

        }
        return result;
    }

    @Override
    public int countQoP(int id) {
        String sql = "SELECT COUNT(*) AS result FROM product WHERE categoryID = " + id;
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


    @Override
    public boolean deleteAllProductByCateID(int id) {
        String sql = "DELETE FROM product WHERE categoryID = ?";
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
}
