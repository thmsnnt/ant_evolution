package util_ant;

public class Action extends Node implements Cloneable{

    public Action clone() throws CloneNotSupportedException {
        Action a = (Action) super.clone();
        return a;
    }

    public Action(String text){
        super(text);
    }
}