package util_world;

public class Cell implements Cloneable {
    private int positionLargeur;
    private int positionHauteur;

    public Cell(int positionLargeur, int positionHauteur){
        this.positionLargeur = positionLargeur;
        this.positionHauteur = positionHauteur;
    }

    public int getPositionLargeur() {
        return positionLargeur;
    }

    public int getPositionHauteur() {
        return positionHauteur;
    }

    public String toString(){
        return "#";
    }
}
