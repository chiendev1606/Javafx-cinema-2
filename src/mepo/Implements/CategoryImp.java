
package mepo.Implements;

import javafx.collections.ObservableList;
import mepo.Components.Category;


public interface CategoryImp {
//    ObservableList<category> selectAll();

    Category insert(Category newcategory);

    boolean update(Category newcategory);

    boolean delete(Category newcategory);

    ObservableList<Category> selectAll();

    Category selectCategoryName(String selectCate);

    int countQoP(int id);

    String selectNameById(int id);

    boolean deleteAllProductByCateID(int id);
}
