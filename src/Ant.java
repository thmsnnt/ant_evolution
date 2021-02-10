public class Ant {
    private int positionLargeur;
    private int positionHauteur;
    private boolean asFood = false;

    Ant(int positionLargeur, int positionHauteur){
        this.positionLargeur = positionLargeur;
        this.positionHauteur = positionHauteur;
    }

    public void goToTop(){
        positionHauteur--;
    }

    public void goToBottom(){
        positionHauteur++;
    }

    public void goToRight(){
        positionLargeur++;
    }

    public void goToLeft(){
        positionLargeur--;
    }

    public int getPositionLargeur(){ return positionLargeur; }

    public void setPositionLargeur(int positionLargeur){ this.positionLargeur = positionLargeur; }

    public int getPositionHauteur(){ return positionHauteur; }

    public void setPositionHauteur(int positionHauteur){ this.positionHauteur = positionHauteur; }
}
