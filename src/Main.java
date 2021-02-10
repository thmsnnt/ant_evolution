import util_tree.DecisionTree;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        World w = new World(10, 5, 5, 5);
        w.getMap().displayMap();
        System.out.println();
        w.placeAnts();
        w.moveAnt(w.getAnt(0),'L');
        w.moveAnt(w.getAnt(0),'L');
        System.out.println();
        w.placeAnts();
        DecisionTree dt = new DecisionTree();
        dt.displayTree();
    }
}
