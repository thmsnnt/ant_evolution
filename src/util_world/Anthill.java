package util_world;

public class Anthill extends Cell implements Cloneable {
    public Anthill(int positionLargeur, int positionHauteur){
        super(positionLargeur,positionHauteur);
    }

    public String toString(){
        return "A";
    }
}
