import util_tree.DecisionTree;

import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws IOException {
        /*World w = new World(10, 5, 5, 5);
        w.getMap().displayMap();
        System.out.println();
        w.placeAnts();
        System.out.println();
        w.getAnt(0).getIntelligence().displayTree();*/

        String path = System.getProperty("user.dir") + "\\save\\map.txt";
        World w2 = new World(path);
        w2.getMap().displayMap();
    }
}