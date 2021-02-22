package util_world;

public class Food extends Cell implements Cloneable {
    private int quantity = 25;

    public Food(int positionLargeur, int positionHauteur){
        super(positionLargeur,positionHauteur);
    }

    public Food(int positionLargeur, int positionHauteur, int quantity){
        this(positionLargeur, positionHauteur);
        this.quantity = quantity;
    }

    public void takeFood(){
        quantity--;
    }

    public void dropFood(){
        quantity++;
    }

    public int getQuantity(){
        return quantity;
    }

    public String toString(){
        return "F";
    }
}
