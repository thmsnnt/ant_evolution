package util_world;

import java.util.ArrayList;

/**
 * Pour la création de la map, voici les informations :
 * 0 : vide
 * 1 : fourmilière
 * 2 : nourriture
 */

public class Map implements Cloneable{
    private Cell[][] grid;
    private ArrayList<Anthill> anthills;
    private ArrayList<Food> foods;
    private int largeur;
    private int hauteur;

    public Map(int largeur, int hauteur){
        this.largeur = largeur;
        this.hauteur = hauteur;
        grid = new Cell[this.hauteur][this.largeur];
        anthills = new ArrayList<>();
        foods = new ArrayList<>();
    }

    public Map(int largeur, int hauteur, int nbFood, int nbAnthill){
        this(largeur,hauteur);
        grid = new Cell[this.hauteur][this.largeur];
        anthills = new ArrayList<>();
        foods = new ArrayList<>();
        initializeGrid(nbFood,nbAnthill);
    }

    public Map(Cell[][] grid){
        this.grid = grid;
    }

    public Map(int largeur, int hauteur, String mapTxt, String foodTxt){
        this(largeur,hauteur);
        grid = new Cell[this.hauteur][this.largeur];
        int nbChar = 0;
        int indFood = 1;
        anthills = new ArrayList<>();
        foods = new ArrayList<>();
        String[] foodsQuantity = foodTxt.split(" ");
        for(int i=0;i<mapTxt.length();i++){
            char c = mapTxt.charAt(i);
            if(c != ' ' && c != '\n'){
                int posLargeur = nbChar%largeur;
                int posHauteur = (int) nbChar/largeur;
                nbChar++;
                if(c == 'A'){
                    Anthill a = new Anthill(posLargeur,posHauteur);
                    grid[posHauteur][posLargeur] = a;
                    anthills.add(a);
                }
                else if(c == 'F'){
                    for(int j=1;j<foodsQuantity.length;j++){
                        int positionLargeur = Integer.parseInt(foodsQuantity[j].split(",")[0].split("\\(")[1]);
                        int positionHauteur = Integer.parseInt(foodsQuantity[j].split(",")[1]);
                        int quantity = Integer.parseInt(foodsQuantity[j].split(",")[2].split("\\)")[0]);
                        if(positionLargeur == posLargeur && positionHauteur == posHauteur){
                            Food f = new Food(posLargeur,posHauteur,quantity);
                            grid[posHauteur][posLargeur] = f;
                            foods.add(f);
                        }
                    }
                }
                else{
                    grid[posHauteur][posLargeur] = new Cell(posLargeur,posHauteur);
                }
            }
        }
    }

    private void initializeGrid(int nbFood, int nbAnthill){
        int xRand = 0;
        int yRand = 0;
        for(int i=0;i<hauteur;i++){
            for(int j=0;j<largeur;j++){
                grid[i][j] = new Cell(largeur,hauteur);
            }
        }
        for(int food=0;food<nbFood;food++){
            do{
                xRand = (int) Math.floor(Math.random()*this.largeur);
                yRand = (int) Math.floor(Math.random()*this.hauteur);
            }
            while(!(grid[yRand][xRand] instanceof Cell));
            Food f = new Food(xRand,yRand);
            foods.add(f);
            grid[yRand][xRand] = f;
        }
        for(int anthill=0;anthill<nbAnthill;anthill++){
            do{
                xRand = (int) Math.floor(Math.random()*this.largeur);
                yRand = (int) Math.floor(Math.random()*this.hauteur);
            }
            while(!(grid[yRand][xRand] instanceof Cell));
            Anthill a = new Anthill(xRand,yRand);
            anthills.add(a);
            grid[yRand][xRand] = a;
        }
    }

    public void displayMap(){
        for (int i = 0; i<this.hauteur; i++){
            for (int j = 0; j<this.largeur; j++){
                System.out.print(grid[i][j]);
                System.out.print(" ");
            }
            System.out.println();
        }
    }

    public void placeObject(int largeur, int hauteur, Cell o){
        grid[hauteur][largeur] = o;
    }

    public Cell[][] getGrid(){ return grid; }

    public int getLargeur() { return largeur; }

    public int getHauteur() { return hauteur; }

    public ArrayList<Food> getFoods(){ return foods; }

    public ArrayList<Anthill> getAnthills(){ return anthills; }

    public void setAnthills(ArrayList<Anthill> anthills){ this.anthills = anthills; }

    public Map clone() throws CloneNotSupportedException {
        Map m = (Map) super.clone();
        m.grid = (Cell[][]) this.grid.clone();
        return m;
    }
}
