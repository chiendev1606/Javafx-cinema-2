
package mepo.Implements;

import javafx.collections.ObservableList;
import mepo.Components.Feedback;


public interface FeedbackImp {

    ObservableList<Feedback> selectAll();

    Feedback insert(Feedback newfeedback);

    boolean update(Feedback newfeedback);

    boolean delete(Feedback newfeedback);

    String selectProductNameByID(int id);

    ObservableList<Feedback> selectFeedbackByProductID(int id);

    int countFeedback();
}
