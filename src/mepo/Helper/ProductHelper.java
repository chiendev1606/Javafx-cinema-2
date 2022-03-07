
package mepo.Helper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import mepo.Components.HistoryProduct;
import mepo.Components.Product;
import mepo.Implements.ProductImp;


public class ProductHelper implements ProductImp {

    @Override
    public ObservableList<Product> selectAll() {
        ObservableList<Product> Product = FXCollections.observableArrayList();
        try (
                Connection conn = DbHelper.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("select * from product");) {
            while (rs.next()) {
                Product u = new Product();

                u.setProductID(rs.getInt("productID"));
                u.setCategoryID(rs.getInt("categoryID"));
                u.setProductName(rs.getString("productname"));
                u.setPrice(rs.getString("price"));
                u.setImage(rs.getBytes("image"));
                u.setDescription(rs.getString("description"));
                u.setVideo(rs.getString("video"));
                u.setUrl(rs.getString("url"));

                Product.add(u);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
        return Product;
    }

    public ObservableList<Product> selectAllExcept(String userName) {
        String sql = "select * from product where productID not in (" +
                "" + "select a.productID  from product a, `order` b, orderdetail c, client d where d.username = b.username and b.orderID = c.orderID and c.productID = a.productID and d.username = ? " +
                ")";
        ObservableList<Product> Product = FXCollections.observableArrayList();
        try (

                Connection conn = DbHelper.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);) {
            stmt.setString(1, userName);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Product u = new Product();

                u.setProductID(rs.getInt("productID"));
                u.setCategoryID(rs.getInt("categoryID"));
                u.setProductName(rs.getString("productname"));
                u.setPrice(rs.getString("price"));
                u.setImage(rs.getBytes("image"));
                u.setDescription(rs.getString("description"));
                u.setVideo(rs.getString("video"));
                u.setUrl(rs.getString("url"));

                Product.add(u);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
        return Product;
    }

    @Override
    public Product insert(Product product) {
        String sql = "INSERT INTO product (CategoryID, productName, price, description, video, url, image) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";
        ResultSet key = null;
        try (
                Connection conn = DbHelper.getConnection();
                PreparedStatement stmt
                        = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {
            stmt.setInt(1, product.getCategoryID());
            stmt.setString(2, product.getProductName());
            stmt.setDouble(3, Double.parseDouble(product.getPrice()));
            stmt.setString(4, product.getDescription());
            stmt.setString(5, product.getVideo());
            stmt.setString(6, product.getUrl());
            stmt.setBytes(7, product.getImage());

            int rowInserted = stmt.executeUpdate();
            if (rowInserted == 1) {
                key = stmt.getGeneratedKeys();
                key.next();
                int newKey = key.getInt(1);
                product.setProductID(newKey);
                System.out.println("Insert Successfully");
                return product;
            } else {
                System.out.println("Insert Fail");
                return null;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }


    @Override
    public boolean update(Product updateproduct) {
        String sql = "UPDATE product SET "
                + "categoryID = ?, "
                + "productname = ?, "
                + "price = ?, "
                + "image = ?, "
                + "description = ? ,"
                + "video = ? ,"
                + "url = ?"
                + "WHERE productID = ?";
        try (
                Connection conn = DbHelper.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);) {
            stmt.setInt(1, updateproduct.getCategoryID());
            stmt.setString(2, updateproduct.getProductName());
            stmt.setString(3, updateproduct.getPrice());
            stmt.setBytes(4, updateproduct.getImage());
            stmt.setString(5, updateproduct.getDescription());
            stmt.setString(6, updateproduct.getVideo());
            stmt.setString(7, updateproduct.getUrl());
            stmt.setInt(8, updateproduct.getProductID());

            int rowUpdated = stmt.executeUpdate();
            if (rowUpdated == 1) {
                System.out.println("Update Successfully");
                return true;
            } else {
                System.out.println("Update False");
                return false;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public boolean delete(Product deleteProduct) {
        String sql = "DELETE FROM product WHERE productID = ?";
        try (
                Connection conn = DbHelper.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);) {
            stmt.setInt(1, deleteProduct.getProductID());
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
    public String selectCategoryNameByID(int id) {
        String sql = "SELECT * FROM category WHERE categoryID = " + id;
        String str = "";
        try (
                Connection conn = DbHelper.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql);) {
            while (rs.next()) {
                str = rs.getString("categoryname");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
        return str;
    }

    @Override
    public ObservableList<Product> selectAllProductByCateID(int id, String username) {
        String sql = " SELECT * FROM product WHERE categoryID = ? and productID not in (" + "select a.productID  from product a, `order` b, orderdetail c, client d where d.username = b.username and b.orderID = c.orderID and c.productID = a.productID and d.username = ? )";
        ObservableList<Product> Product = FXCollections.observableArrayList();
        try (
                Connection conn = DbHelper.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);) {
            stmt.setInt(1, id);
            stmt.setString(2, username);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Product u = new Product();

                u.setProductID(rs.getInt("productID"));
                u.setCategoryID(rs.getInt("categoryID"));
                u.setProductName(rs.getString("productname"));
                u.setPrice(rs.getString("price"));
                u.setImage(rs.getBytes("image"));
                u.setDescription(rs.getString("description"));
                u.setVideo(rs.getString("video"));
                u.setUrl(rs.getString("url"));

                Product.add(u);
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
        return Product;
    }

    @Override
    public ObservableList<HistoryProduct> selectMovieByCateID(int id, String username) {
        String sql = "select  a.productID, a.url, a.categoryID, a.video, a.description, a.image,a.price, a.productname, b.orderdate from product a, `order` b, orderdetail c, client d where d.username = b.username and b.orderID = c.orderID and c.productID = a.productID and d.username= ? and a.categoryID = ?;";
        ObservableList<HistoryProduct> listProduct = FXCollections.observableArrayList();
        try (
                Connection conn = DbHelper.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);) {
            stmt.setString(1, username);
            stmt.setInt(2, id);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                HistoryProduct u = new HistoryProduct();

                u.setProductID(rs.getInt("productID"));
                u.setCategoryID(rs.getInt("categoryID"));
                u.setProductname(rs.getString("productname"));
                u.setPrice(rs.getString("price"));
                u.setImage(rs.getBytes("image"));
                u.setDescription(rs.getString("description"));
                u.setVideo(rs.getString("video"));
                u.setDate(rs.getString("orderdate"));
                u.setUrl(rs.getString("url"));


                listProduct.add(u);
            }


        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
        return listProduct;
    }

    @Override
    public ObservableList<Product> searchProduct(String s, String username) {
        String sql = "SELECT * FROM product WHERE productname LIKE ? and productID not in (select a.productID  from product a, `order` b, orderdetail c, client d where d.username = b.username and b.orderID = c.orderID and c.productID = a.productID and d.username= ?);";
        ObservableList<Product> Product = FXCollections.observableArrayList();
        try (
                Connection conn = DbHelper.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + s + "%");
            stmt.setString(2, username);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Product u = new Product();

                u.setProductID(rs.getInt("productID"));
                u.setCategoryID(rs.getInt("categoryID"));
                u.setProductName(rs.getString("productname"));
                u.setPrice(rs.getString("price"));
                u.setImage(rs.getBytes("image"));
                u.setDescription(rs.getString("description"));
                u.setVideo(rs.getString("video"));
                u.setUrl(rs.getString("url"));


                Product.add(u);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;

        }
        return Product;
    }

    @Override
    public ObservableList<Product> searchProductByPrice(double price, String username) {
        String sql = "SELECT * FROM product WHERE price <= ? and productID not in (select a.productID  from product a, `order` b, orderdetail c, client d where d.username = b.username and b.orderID = c.orderID and c.productID = a.productID and d.username= ?);";
        ObservableList<Product> Product = FXCollections.observableArrayList();
        try (
                Connection conn = DbHelper.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDouble(1, price);
            stmt.setString(2, username);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Product u = new Product();

                u.setProductID(rs.getInt("productID"));
                u.setCategoryID(rs.getInt("categoryID"));
                u.setProductName(rs.getString("productname"));
                u.setPrice(rs.getString("price"));
                u.setImage(rs.getBytes("image"));
                u.setDescription(rs.getString("description"));
                u.setVideo(rs.getString("video"));
                u.setUrl(rs.getString("url"));


                Product.add(u);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;

        }
        return Product;
    }

    @Override
    public ObservableList<HistoryProduct> searchMovieBuy(String s, String username) {
        String sql = "select  a.productID, a.url, a.categoryID, a.video, a.description, a.image,a.price, a.productname, b.orderdate  from product a, `order` b, orderdetail c, client d where d.username = b.username and b.orderID = c.orderID and c.productID = a.productID and d.username= ? and productname LIKE ?;";
        ObservableList<HistoryProduct> Product = FXCollections.observableArrayList();
        try (
                Connection conn = DbHelper.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, "%" + s + "%");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                HistoryProduct u = new HistoryProduct();

                u.setProductID(rs.getInt("productID"));
                u.setCategoryID(rs.getInt("categoryID"));
                u.setProductname(rs.getString("productname"));
                u.setPrice(rs.getString("price"));
                u.setImage(rs.getBytes("image"));
                u.setDescription(rs.getString("description"));
                u.setVideo(rs.getString("video"));
                u.setDate(rs.getString("orderdate"));
                u.setUrl(rs.getString("url"));


                Product.add(u);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
        return Product;
    }

    public static double totalCoin = 0;
    @Override
    public ObservableList<HistoryProduct> selectProductboughtByUser(String username) {
        String sql = "select a.productID, a.url, b.orderdate, a.categoryID, a.image, a.price, a.productname, a.description, a.video, a.price from product a, `order` b, orderdetail c, client d where d.username = b.username and b.orderID = c.orderID and c.productID = a.productID and d.username = ? ";
        ObservableList<HistoryProduct> list = FXCollections.observableArrayList();
        try (
                Connection conn = DbHelper.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                HistoryProduct historyProduct = new HistoryProduct();
                historyProduct.setProductID(rs.getInt("productID"));
                historyProduct.setCategoryID(rs.getInt("categoryID"));
                historyProduct.setProductname(rs.getString("productname"));
                historyProduct.setPrice(rs.getString("price"));
                historyProduct.setImage(rs.getBytes("image"));
                historyProduct.setDescription(rs.getString("description"));
                historyProduct.setVideo(rs.getString("video"));
                historyProduct.setDate(rs.getString("orderdate"));
                historyProduct.setUrl(rs.getString("url"));
                double coin = rs.getDouble("price");
                historyProduct.setCoin(coin);
                totalCoin += coin;
                list.add(historyProduct);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
        return list;
    }


    @Override
    public Product findProductById(int id) {
        String sql = "SELECT * FROM product WHERE productID = ?";
        Product product = null;
        try (
                Connection conn = DbHelper.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                product = new Product();
                product.setProductID(rs.getInt("productID"));
                product.setCategoryID(rs.getInt("categoryID"));
                product.setProductName(rs.getString("productname"));
                product.setPrice(rs.getString("price"));
                product.setImage(rs.getBytes("image"));
                product.setDescription(rs.getString("description"));
                product.setVideo(rs.getString("video"));
                product.setUrl(rs.getString("url"));

            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;

        }
        return product;
    }

    @Override
    public int findMaxPrice() {

        try {
            Connection conn = DbHelper.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select max(price) as max from product group by price order by price desc limit 1");
            if (rs.next()) {
                return rs.getInt("max");
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return -1;
        }
        return -1;
    }

}
