import util_tree.DecisionTree;

import java.io.IOException;

public class Ant {
    private int positionLargeur;
    private int positionHauteur;
    private boolean asFood = false;
    private Anthill anthill;
    private DecisionTree intelligence = new DecisionTree();

    public Ant() throws IOException {
        this.positionLargeur = 0;
        this.positionHauteur = 0;
    }

    Ant(int positionLargeur, int positionHauteur, Anthill anthill) throws IOException {
        this.positionLargeur = positionLargeur;
        this.positionHauteur = positionHauteur;
        this.anthill = anthill;
    }

    Ant(int positionLargeur, int positionHauteur, Anthill anthill, DecisionTree intelligence) throws IOException {
        this(positionLargeur, positionHauteur, anthill);
        this.intelligence = intelligence;
    }

    public void move(char direction){
        switch (direction){
            case 'T': positionHauteur--; break;
            case 'B': positionHauteur++; break;
            case 'R': positionLargeur++; break;
            case 'L': positionLargeur--; break;
            default: break;
        }
    }

    public int getPositionLargeur(){ return positionLargeur; }

    public void setPositionLargeur(int positionLargeur){ this.positionLargeur = positionLargeur; }

    public int getPositionHauteur(){ return positionHauteur; }

    public void setPositionHauteur(int positionHauteur){ this.positionHauteur = positionHauteur; }

    public boolean getAsFood(){ return asFood; }

    public void setAsFood(boolean asFood){ this.asFood = asFood; }

    public DecisionTree getIntelligence(){ return this.intelligence; }

    public void setIntelligence(DecisionTree intelligence){ this.intelligence = intelligence; }
}
