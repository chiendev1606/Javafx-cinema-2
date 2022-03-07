package mepo.Implements;

import mepo.Components.Card;

import java.util.List;

public interface CardImp {
    List<Card> selectAll();
    boolean updateUsed(Card card);
    void insert(Card card);
    List<Integer> selectCodeAll();
}
