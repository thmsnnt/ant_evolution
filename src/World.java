import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class World {
    Map map;
    Ant[] ants;

    public World(int largeur, int hauteur, int nbAnt, int nbFood) throws IOException {
        map = new Map(largeur, hauteur, nbFood);
        ants = new Ant[nbAnt];
        for (int i=0;i<nbAnt;i++){
            ArrayList<Anthill> anthills = map.getAnthills();
            Ant a = new Ant();
            if(anthills.size() == 1){
                a = new Ant(anthills.get(0).getPositionLargeur(),anthills.get(0).getPositionHauteur(), anthills.get(0));
            }
            ants[i] = a;
        }
    }

    public World(Map map, int nbAnt) throws IOException{
        this.map = map;
        ants = new Ant[nbAnt];
        for (int i=0;i<nbAnt;i++){
            ArrayList<Anthill> anthills = map.getAnthills();
            Ant a = new Ant();
            if(anthills.size() == 1){
                a = new Ant(anthills.get(0).getPositionLargeur(),anthills.get(0).getPositionHauteur(), anthills.get(0));
            }
            ants[i] = a;
        }
    }

    public World(String path) throws IOException {
        BufferedReader readerWithBuffer = null;
        String line;
        try
        {
            readerWithBuffer = new BufferedReader(new FileReader(path));
        }
        catch(FileNotFoundException exc)
        {
            System.out.println("File not found");
        }
        int nbMap = 1;
        String map1Txt = "";
        String map2Txt = "";
        int largeur = 0;
        int hauteur = 0;
        int nbAnts = 0;
        while ((line = readerWithBuffer.readLine()) != null){
            if(line.equals("")){
                nbMap++;
            }
            else if(nbMap == 1){
                String[] values = line.split("x");
                largeur = Integer.parseInt(values[0]);
                hauteur = Integer.parseInt(values[1]);
                nbMap++;
            }
            else if(nbMap == 2){
                nbAnts = Integer.parseInt(line);
                nbMap++;
            }
            else if(nbMap == 4){
                map1Txt += line;
                map1Txt += '\n';
            }
            else{
                map2Txt += line;
                map2Txt += '\n';
            }
        }
        readerWithBuffer.close();
        map = new Map(largeur, hauteur, map1Txt);
    }

    public void placeAnts(){
        int largeur = map.getLargeur();
        int hauteur = map.getHauteur();
        Ant[][] antsMap = new Ant[largeur][hauteur];
        for(Ant ant : ants){
            antsMap[ant.getPositionLargeur()][ant.getPositionHauteur()] = ant;
        }
        for(int i=0;i<hauteur;i++){
            for(int j=0;j<largeur;j++){
                if(antsMap[j][i] instanceof Ant)
                    System.out.print('A');
                else
                    System.out.print('#');
                System.out.print(' ');
            }
            System.out.println();
        }
    }

    public void moveAnt(Ant ant, char direction){
        if(direction == 'T'){
            if(ant.getPositionHauteur() > 0)
                ant.move('T');
            else
                ant.setPositionHauteur(map.getHauteur()-1);
        }
        else if(direction == 'B'){
            if(ant.getPositionHauteur() < map.getHauteur()-1)
                ant.move('B');
            else
                ant.setPositionHauteur(0);
        }
        else if(direction == 'L'){
            if(ant.getPositionLargeur() > 0)
                ant.move('L');
            else
                ant.setPositionLargeur(map.getLargeur()-1);
        }
        else if(direction == 'R'){
            if(ant.getPositionLargeur() < map.getHauteur()-1)
                ant.move('R');
            else
                ant.setPositionLargeur(0);
        }
    }

    public void takeFood(Ant ant, int largeur, int hauteur){
        if(!ant.getAsFood()){
            ant.setAsFood(true);
            map.getGrid()[largeur][hauteur] = new Cell(largeur,hauteur);
        }
    }

    public void saveWorld(){

    }

    public Map getMap() { return map; }

    public Ant[] getAnts() { return ants; }

    public Ant getAnt(int id) { return ants[id]; }
}
