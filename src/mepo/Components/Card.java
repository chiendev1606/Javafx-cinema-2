package mepo.Components;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.io.Serializable;

public class Card implements Serializable {
    private ObjectProperty<Integer> code;
    private ObjectProperty<Integer> value;
    private BooleanProperty used;

    public Card(){
        this.code = new SimpleObjectProperty<Integer>(null);
        this.value = new SimpleObjectProperty<Integer>(null);
        this.used = new SimpleBooleanProperty();
    }
    public int getCode() {
        return code.get();
    }

    public ObjectProperty<Integer> codeProperty() {
        return code;
    }

    public void setCode(Integer code) {
        this.code.set(code);
    }

    public int getValue() {
        return value.get();
    }

    public ObjectProperty<Integer> valueProperty() {
        return value;
    }

    public void setValue(Integer value) {
        this.value.set(value);
    }

    public boolean isUsed() {
        return used.get();
    }

    public BooleanProperty usedProperty() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used.set(used);
    }

    @Override
    public String toString() {
        return "CODE: " + this.getCode() + "     PRICE: " + this.getValue();
    }
}
