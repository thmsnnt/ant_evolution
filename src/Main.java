import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException, CloneNotSupportedException {
        /*World w = new World(10, 5, 5, 5);
        w.getMap().displayMap();
        System.out.println();
        w.moveAnt(w.getAnt(0),'B');
        w.moveAnt(w.getAnt(1),'T');
        w.moveAnt(w.getAnt(2),'R');
        w.placeAnts();
        Date date = new Date();
        String path = System.getProperty("user.dir") + "\\save\\" + date.getTime() + "_world.txt";
        w.saveWorld(path);*/
        /*
        String path = System.getProperty("user.dir") + "\\save\\1613170454672_world.txt";
        World w2 = new World(path);
        Ant a = w2.getAnt(0);

        w2.getMap().displayMap();
        System.out.println(a);
        w2.comeHomeAnt(a);
        */

        //String path = System.getProperty("user.dir") + "\\save\\1613661403474_world.txt";
        //Simulation simulation = new Simulation(path,1000);
        Simulation simulation = new Simulation(25,25,100,50,5,200);
        //Simulation simulation = new Simulation(10,10,1,10,3,1000);

        Window window = new Window();
        JPanel graphic = new Graphics(simulation);
        JPanel menu = new Menu(simulation);
        window.add(graphic);
        window.add(menu);

        simulation.launchSimulation(window);
        /*
        for(int i=0;i<20;i++){
            w2.getMap().displayMap();
            System.out.println(a);
            w2.moveAntDecisionTree(a);
            System.out.println();
        }
        */
        /*
        w2.getAnt(0).getIntelligence().displayTree();
        System.out.println(w2.getAnt(0));
        w2.moveAntDecisionTree(w2.getAnt(0));
        System.out.println();
        */

        //String path = System.getProperty("user.dir") + "\\save\\map2.txt";
        //World w2 = new World(path);
        //w2.getMap().displayMap();
        //String path_out = System.getProperty("user.dir") + "\\save\\map2.txt";
        //w2.saveWorld(path_out);
    }
}