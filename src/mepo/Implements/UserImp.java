
package mepo.Implements;

import javafx.collections.ObservableList;
import mepo.Components.User;


public interface UserImp {
    ObservableList<User> selectAll();

    User insert(User newUser);

    boolean update(User updateUser);

    boolean delete(User deleteUser);

    User selectUsername(String selectUser);

    User selectUserByEmail(String Email);

    int countClient();


}
