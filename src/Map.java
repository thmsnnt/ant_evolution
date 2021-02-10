/**
 * Pour la création de la map, voici les informations :
 * 0 : vide
 * 1 : fourmilière
 * 2 : nourriture
 */

public class Map {
    private Object[][] map;
    private int largeur;
    private int hauteur;
    private int hanthillX;
    private int hanthillY;

    public Map(int largeur, int hauteur){
        this.largeur = largeur;
        this.hauteur = hauteur;
        map = new Object[this.hauteur][this.largeur];
    }

    public Map(int largeur, int hauteur, int nbFood){
        this.largeur = largeur;
        this.hauteur = hauteur;
        map = new Object[this.hauteur][this.largeur];
        initializeMap(nbFood,1);
    }

    private void emptyFullMap(){
        for (int i = 0; i<this.hauteur; i++){
            for (int j = 0; j<this.largeur; j++){
                map[i][j] = null;
            }
        }
    }

    private void initializeMap(int nbFood, int nbAnthill){
        int xAleat = 0;
        int yAleat = 0;
        for(int food=0;food<nbFood;food++){
            do{
                xAleat = (int) Math.floor(Math.random()*this.largeur);
                yAleat = (int) Math.floor(Math.random()*this.hauteur);
            }
            while(map[yAleat][xAleat] != null);
            map[yAleat][xAleat] = new Food();
        }
        for(int anthill=0;anthill<nbAnthill;anthill++){
            do{
                xAleat = (int) Math.floor(Math.random()*this.largeur);
                yAleat = (int) Math.floor(Math.random()*this.hauteur);
            }
            while(map[yAleat][xAleat] != null);
            hanthillX = xAleat;
            hanthillY = yAleat;
            map[yAleat][xAleat] = new Anthill();
        }
    }

    public void displayMap(){
        for (int i = 0; i<this.hauteur; i++){
            for (int j = 0; j<this.largeur; j++){
                if(map[i][j] instanceof Anthill)
                    System.out.print(1);
                else if(map[i][j] instanceof Food)
                    System.out.print(2);
                else if(map[i][j] instanceof Ant)
                    System.out.print(3);
                else
                    System.out.print(0);
                System.out.print(" ");
            }
            System.out.println();
        }
    }

    public void placeObject(int largeur, int hauteur, Object o){
        map[hauteur][largeur] = o;
    }

    public void takeFood(int largeur, int hauteur){
        map[largeur][hauteur] = 0;
    }

    public int getLargeur() { return largeur; }

    public int getHauteur() { return hauteur; }

    public int getHanthillX() { return hanthillX; }

    public int getHanthillY() { return hanthillY; }
}
