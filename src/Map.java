import java.util.ArrayList;

/**
 * Pour la création de la map, voici les informations :
 * 0 : vide
 * 1 : fourmilière
 * 2 : nourriture
 */

public class Map {
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

    public Map(int largeur, int hauteur, int nbFood){
        this(largeur,hauteur);
        grid = new Cell[this.hauteur][this.largeur];
        anthills = new ArrayList<>();
        foods = new ArrayList<>();
        initializeGrid(nbFood,1);
    }

    public Map(Cell[][] grid){
        this.grid = grid;
    }

    public Map(int largeur, int hauteur, String mapTxt){
        this(largeur,hauteur);
        grid = new Cell[this.hauteur][this.largeur];
        int nbChar = 0;
        anthills = new ArrayList<>();
        foods = new ArrayList<>();
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
                    Food f = new Food(posLargeur,posHauteur);
                    grid[posHauteur][posLargeur] = f;
                    foods.add(f);
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
        for(int food=0;food<nbFood;food++){
            do{
                xRand = (int) Math.floor(Math.random()*this.largeur);
                yRand = (int) Math.floor(Math.random()*this.hauteur);
            }
            while(grid[yRand][xRand] != null);
            Food f = new Food(xRand,yRand);
            foods.add(f);
            grid[yRand][xRand] = f;
        }
        for(int anthill=0;anthill<nbAnthill;anthill++){
            do{
                xRand = (int) Math.floor(Math.random()*this.largeur);
                yRand = (int) Math.floor(Math.random()*this.hauteur);
            }
            while(grid[yRand][xRand] != null);
            Anthill a = new Anthill(xRand,yRand);
            anthills.add(a);
            grid[yRand][xRand] = a;
        }
    }

    public void displayMap(){
        for (int i = 0; i<this.hauteur; i++){
            for (int j = 0; j<this.largeur; j++){
                if(grid[i][j] instanceof Anthill)
                    System.out.print('A');
                else if(grid[i][j] instanceof Food)
                    System.out.print('F');
                else
                    System.out.print('#');
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
}
