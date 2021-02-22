import util_ant.Ant;
import util_ant.DecisionTree;
import util_world.World;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class Simulation {
    private World world;
    private World initialWorld;
    private Window window;

    private int nbIteration = 500;
    private int actualIteration = 0;

    private int nbSimulation = 100;
    private int actualSimulation = 1;

    private ArrayList<Ant> antsToShowList;

    private Ant bestAnt;
    private int meanScore;
    private int worstScore;

    private Ant bestAntPrevious;
    private int meanScorePrevious;
    private int worstScorePrevious;

    private int simuationSpeed = 100;
    private int previousSimulationSpeed;
    private boolean simulationPausePlay = true;
    String pathSaveBeginSimulation = "saveTemp_world.txt";

    public Simulation() throws IOException {
        this.world = new World(pathSaveBeginSimulation);
        this.bestAnt = this.world.getAnt(0);
    }

    public Simulation(int largeur, int hauteur, int nbAnt, int nbFood, int nbAnthill) throws IOException, CloneNotSupportedException {
        this.world = new World(largeur, hauteur, nbAnt, nbFood, nbAnthill);
        this.initialWorld = this.world.clone();
        this.bestAnt = this.world.getAnt(0);
    }

    public Simulation(String path) throws IOException, CloneNotSupportedException {
        this.world = new World(path);
        this.initialWorld = this.world.clone();
        this.bestAnt = this.world.getAnt(0);
    }

    public Simulation(int largeur, int hauteur, int nbAnt, int nbFood, int nbAnthill, int nbIteration) throws IOException, CloneNotSupportedException {
        this(largeur,hauteur,nbAnt,nbFood,nbAnthill);
        this.initialWorld = this.world.clone();
        this.nbIteration = nbIteration;
    }

    public Simulation(String path, int nbIteration) throws IOException, CloneNotSupportedException {
        this(path);
        this.nbIteration = nbIteration;
    }

    public void launchSimulation(Window window) throws InterruptedException, IOException, CloneNotSupportedException {
        this.window = window;
        ArrayList<Ant> ants = world.getAnts();
        Graphics g = (Graphics) this.window.getContentPane().getComponent(0);
        for(int i=0;i<ants.size();i++){
            ants.get(i).setPerfectIntelligence();
        }
        g.setAntsToShow(ants);
        world.saveWorld(this.pathSaveBeginSimulation);

        while(this.actualSimulation < this.nbSimulation) {
            window.revalidate();
            window.repaint();
            System.out.println(this.actualSimulation + " : " + this.simuationSpeed);
            while (this.actualIteration < this.nbIteration) {
                Thread.sleep(this.simuationSpeed);
                if (this.simulationPausePlay) {
                    ArrayList<Integer> scores = new ArrayList<>();
                    for (int j = 0; j < ants.size(); j++) {
                        world.moveAntDecisionTree(ants.get(j));
                        scores.add(ants.get(j).getScore());
                        if (ants.get(j).getScore() > bestAnt.getScore()) {
                            this.bestAnt = ants.get(j);
                        }
                    }
                    if (simuationSpeed != 0) {
                        window.revalidate();
                        window.repaint();
                    }
                    this.worstScore = Collections.min(scores);
                    this.meanScore = meanScore(scores);
                    this.actualIteration++;
                }
            }
            if(this.simuationSpeed == 0)
                this.simuationSpeed = this.previousSimulationSpeed;
            window.revalidate();
            window.repaint();
            this.actualSimulation++;
            this.actualIteration = 0;

            this.bestAntPrevious = this.bestAnt.clone();
            this.meanScorePrevious = this.meanScore;
            this.worstScorePrevious = worstScore;

            modificationsPostSimulation();
            ants = world.getAnts();
            g.setAntsToShow(ants);
            this.bestAnt = this.world.getAnt(0);
            this.meanScore = 0;
            this.worstScore = 0;
        }
    }

    public void modificationsPostSimulation() throws IOException, CloneNotSupportedException {
        ArrayList<Ant> ants = (ArrayList<Ant>) world.getAnts().clone();
        ArrayList<Ant> newAnts = new ArrayList<>();
        int antsSize = ants.size();
        int antsNewSize = (int) (antsSize * 0.2);
        int antsKeepSize = (int) (antsSize * 0.5);
        //int antsSize = (int) (antsSize * 0.1);
        Collections.sort(ants);
        // Remplacer aléatoirement une condition
        // Remplacer aléatoirement une action
        // Echanger deux sous arbres aléatoirement
        // Remplacer aléatoirement un sous arbre par un autre donné
        // replaceCondition, replaceAction, exchangeChilds, replaceChild
        for(int i=0;i<antsKeepSize;i++){
            int randAnthill = (int) (Math.random() * world.getMap().getAnthills().size());
            Ant a = new Ant(world.getMap().getAnthills().get(randAnthill).getPositionLargeur(), world.getMap().getAnthills().get(randAnthill).getPositionHauteur(),ants.get(i).getIntelligence());
            newAnts.add(a);
        }
        for(int i=0;i<antsNewSize;i++){
            int randAnthill = (int) (Math.random() * world.getMap().getAnthills().size());
            Ant a = new Ant(world.getMap().getAnthills().get(randAnthill).getPositionLargeur(), world.getMap().getAnthills().get(randAnthill).getPositionHauteur());
            newAnts.add(a);
        }
        for(int i=0;i<(antsSize-antsKeepSize-antsNewSize);i++){
            int randAnt = (int) (Math.random() * antsSize);
            ants.get(randAnt).getIntelligence().exchangeChilds();
            ants.get(randAnt).getIntelligence().replaceAction();
            ants.get(randAnt).getIntelligence().replaceCondition();
            int randAnthill = (int) (Math.random() * world.getMap().getAnthills().size());
            Ant a = new Ant(world.getMap().getAnthills().get(randAnthill).getPositionLargeur(),world.getMap().getAnthills().get(randAnthill).getPositionHauteur(),ants.get(randAnt).getIntelligence());
            newAnts.add(a);
        }
        world = initialWorld.clone();
        System.out.println(world.getMap());
        world.setAnts(newAnts);
    }

    public int meanScore(ArrayList<Integer> scores) {
        int total = 0;
        for(int i=0;i<scores.size();i++){
            total += scores.get(i);
        }
        return total/scores.size();
    }

    public World getWorld() { return this.world; }

    public Ant getBestAnt() { return this.bestAnt; }

    public Ant getBestAntPrevious() { return this.bestAntPrevious; }

    public void accelerateSimulationSpeed(){
        if(this.simuationSpeed > 10)
            this.simuationSpeed -= 10;
    }

    public void slowDownSimulationSpeed(){
        this.simuationSpeed += 10;
    }

    public void pause(){ this.simulationPausePlay = false; }

    public void play(){ this.simulationPausePlay = true; }

    public int getNbSimulation(){ return this.nbSimulation; }

    public int getActualSimulation(){ return this.actualSimulation; }

    public int getMeanScore(){ return this.meanScore; }

    public int getWorstScore(){ return this.worstScore; }

    public int getMeanScorePrevious() { return this.meanScorePrevious; }

    public int getWorstScorePrevious() { return this.worstScorePrevious; }

    public void nextSimulation(){
        if(this.simuationSpeed > 0)
            this.previousSimulationSpeed = this.simuationSpeed;
        this.simuationSpeed = 0;
    }

    public void setAntToShow(int antToShow){
        if(antToShow != -1 && antToShow < world.getAnts().size()){
            Graphics g = (Graphics) this.window.getContentPane().getComponent(0);
            Ant a = world.getAnt(antToShow);
            this.antsToShowList = new ArrayList<>();
            this.antsToShowList.add(a);
            g.setAntsToShow(this.antsToShowList);
        }
    }

    public void setAntToShow(String antToShow){
        this.antsToShowList = new ArrayList<>();
        ArrayList<Ant> all = (ArrayList<Ant>) world.getAnts().clone();
        Graphics g = (Graphics) this.window.getContentPane().getComponent(0);
        if(antToShow.equals("All"))
            this.antsToShowList = all;
        else if(antToShow.equals("One"))
            this.antsToShowList.add(bestAnt);
        else if(antToShow.equals("Two")){
            Collections.sort(all);
            for(int i=0;i<Math.min(2,all.size());i++)
                this.antsToShowList.add(all.get(i));
            g.setAntsToShow(this.antsToShowList);
        }
        else if(antToShow.equals("Three")){
            all = world.getAnts();
            Collections.sort(all);
            for(int i=0;i<Math.min(3,all.size());i++)
                this.antsToShowList.add(all.get(i));
            g.setAntsToShow(this.antsToShowList);
        }

        else if(antToShow.equals("Five")){
            all = world.getAnts();
            Collections.sort(all);
            for(int i=0;i<Math.min(5,all.size());i++)
                this.antsToShowList.add(all.get(i));
            g.setAntsToShow(this.antsToShowList);
        }
        else if(antToShow.equals("Ten")){
            all = world.getAnts();
            Collections.sort(all);
            for(int i=0;i<Math.min(10,all.size());i++)
                this.antsToShowList.add(all.get(i));
            g.setAntsToShow(this.antsToShowList);
        }
        else if(antToShow.equals("Twenty")){
            all = world.getAnts();
            Collections.sort(all);
            for(int i=0;i<Math.min(20,all.size());i++)
                this.antsToShowList.add(all.get(i));
            g.setAntsToShow(this.antsToShowList);
        }

        g.setAntsToShow(this.antsToShowList);
    }

    public ArrayList<Ant> getAntsToShowList() { return this.antsToShowList; }

    public float getPercentageIteration() { return (float)this.actualIteration/(float)this.nbIteration; }

    public boolean getSimulationPausePlay() { return this.simulationPausePlay; }
}
