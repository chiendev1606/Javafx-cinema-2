/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mepo.Implements;

import javafx.collections.ObservableList;
import mepo.Components.HistoryProduct;
import mepo.Components.Product;

import java.io.File;


public interface ProductImp {
    ObservableList<Product> selectAll();

    ObservableList<Product> selectAllExcept(String userName);

    Product insert(Product product);

    boolean update(Product newproduct);

    boolean delete(Product newproduct);

    String selectCategoryNameByID(int id);

    ObservableList<Product> selectAllProductByCateID(int id, String username);

    ObservableList<HistoryProduct> selectMovieByCateID(int id, String username);

    ObservableList<Product> searchProduct(String s, String username);

    ObservableList<Product> searchProductByPrice(double price, String username);

    ObservableList<HistoryProduct> searchMovieBuy(String s, String username);

    ObservableList<HistoryProduct> selectProductboughtByUser(String username);

    Product findProductById(int id);

    int findMaxPrice();
}
