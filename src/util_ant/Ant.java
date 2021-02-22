package util_ant;

import util_world.Map;
import util_world.World;

import java.io.IOException;
import java.util.ArrayList;

public class Ant implements Cloneable, Comparable<Ant>{
    private int positionLargeur;
    private int positionHauteur;
    private boolean asFood = false;
    private boolean isComingHome = false;
    private int score = 0;
    private char direction = 'T';
    private DecisionTree intelligence = new DecisionTree();

    public Ant() throws IOException {
        this.positionLargeur = 0;
        this.positionHauteur = 0;
    }

    public Ant(int positionLargeur, int positionHauteur) throws IOException {
        this.positionLargeur = positionLargeur;
        this.positionHauteur = positionHauteur;
    }

    public Ant(int positionLargeur, int positionHauteur, int score) throws IOException {
        this(positionLargeur,positionHauteur);
        this.score = score;
    }

    public Ant(int positionLargeur, int positionHauteur, DecisionTree intelligence) throws IOException {
        this(positionLargeur, positionHauteur);
        this.intelligence = intelligence;
    }

    public void move(char direction){
        this.direction = direction;
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

    public boolean getIsComingHome(){ return isComingHome; }

    public void setIsComingHome(boolean isComingHome){ this.isComingHome = isComingHome; }

    public DecisionTree getIntelligence(){ return this.intelligence; }

    public void setIntelligence(DecisionTree intelligence){
        this.intelligence = intelligence;
        this.intelligence.simplifyTree();
        this.intelligence.simplifyTree();
        this.intelligence.numberingLeave();
        this.intelligence.numberingNode();
    }

    public void setPerfectIntelligence(){
        Node a1 = new Action("act_allerAleat");
        Node a2 = new Action("act_ramasser");
        Node a3 = new Action("act_rentrer");
        Node a4 = new Action("act_deposer");

        Node c1 = new Condition("cond_nourriture");
        Node c2 = new Condition("cond_fourmiliere");
        Node c3 = new Condition("cond_possedeNourriture");

        DecisionTree dta1 = new DecisionTree(a4);
        DecisionTree dta2 = new DecisionTree(a3);
        DecisionTree dta3 = new DecisionTree(a2);
        DecisionTree dta4 = new DecisionTree(a1);

        DecisionTree dtc1 = new DecisionTree(c2, dta1, dta2);
        DecisionTree dtc2 = new DecisionTree(c1, dta3, dta4);
        intelligence = new DecisionTree(c3, dtc1, dtc2);
    }


    public int getScore() { return score; }

    public void augmenterScore(int addition) { score += addition; }

    public char getDirection() { return direction; }


    public String toString(){
        return "(" + positionLargeur + "," + positionHauteur + ") " + score + "pts | asFood:" + asFood + " | isComingHome:" + isComingHome;
        //return score + "pts";
    }

    @Override
    public int compareTo(Ant a) {
        return (a.getScore() - this.score);
    }

    public Ant clone() throws CloneNotSupportedException {
        Ant a = (Ant) super.clone();
        a.intelligence = (DecisionTree) this.intelligence.clone();
        return a;
    }
}