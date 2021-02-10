package util_tree;
import java.io.Serializable;

public class Node implements Serializable, Cloneable {
    protected String text;

    public Node(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public Node clone() throws CloneNotSupportedException {
        Node n = (Node) super.clone();
        return n;
    }

    public String toString() {
        return text;
    }
}
