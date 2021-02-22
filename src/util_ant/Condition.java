package util_ant;

public class Condition extends Node implements Cloneable {

    public Condition clone() throws CloneNotSupportedException {
        Condition c = (Condition) super.clone();
        return c;
    }

    public Condition(String text) {
        super(text);
    }
}