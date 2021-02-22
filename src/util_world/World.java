package util_world;
import util_ant.Action;
import util_ant.Ant;
import util_ant.DecisionTree;
import util_ant.Node;

import java.io.*;
import java.util.ArrayList;

public class World implements Cloneable{
    Map map;
    ArrayList<Ant> ants;

    public World(int largeur, int hauteur, int nbAnt, int nbFood, int nbAnthill) throws IOException {
        map = new Map(largeur, hauteur, nbFood, nbAnthill);
        ants = new ArrayList<>();
        ArrayList<Anthill> anthills = map.getAnthills();
        for (int i=0;i<nbAnt;i++){
            Ant a;
            int randAnthill = (int) (Math.random() * anthills.size());
            a = new Ant(anthills.get(randAnthill).getPositionLargeur(),anthills.get(randAnthill).getPositionHauteur());
            ants.add(a);
        }
    }

    public World(Map map, int nbAnt) throws IOException{
        this.map = map;
        ants = new ArrayList<>();
        for (int i=0;i<nbAnt;i++){
            ArrayList<Anthill> anthills = map.getAnthills();
            Ant a = new Ant();
            if(anthills.size() == 1){
                a = new Ant(anthills.get(0).getPositionLargeur(),anthills.get(0).getPositionHauteur());
            }
            ants.add(a);
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
        String mapTxt = "";
        String antsParameters = "";
        String foodsParameters = "";
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
                antsParameters = line;
                nbMap++;
            }
            else if(nbMap == 3){
                foodsParameters = line;
                nbMap++;
            }
            else if(nbMap == 5){
                mapTxt += line;
                mapTxt += '\n';
            }
        }
        readerWithBuffer.close();
        System.out.println(foodsParameters);
        map = new Map(largeur, hauteur, mapTxt, foodsParameters);

        String[] values = antsParameters.split(" ");
        nbAnts = Integer.parseInt(values[0]);
        ants = new ArrayList<>();
        for(int i=0;i<nbAnts;i++){
            String position = values[i+1];
            int positionLargeur = Integer.parseInt(position.split(",")[0].split("\\(")[1]);
            int positionHauteur = Integer.parseInt(position.split(",")[1]);
            int score = Integer.parseInt(position.split(",")[2].split("\\)")[0]);
            Ant a = new Ant(positionLargeur,positionHauteur,score);
            ants.add(a);
        }
        nbMap++;
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
        else if(direction == 'A'){
            ArrayList<Character> directions = new ArrayList<>();
            directions.add('T');
            directions.add('B');
            directions.add('R');
            directions.add('L');
            int rand = (int) Math.floor(Math.random()*4);
            moveAnt(ant, directions.get(rand));
        }
    }

    public void comeHomeAnt(Ant ant){
        int distanceAntAnthillMin = 999999999;
        int antHillSelected = 0;
        int deltaHauteurSelected = 0;
        int deltaLargeurSelected = 0;
        int deltaHauteur = 0;
        int deltaLargeur = 0;
        char hauteurDirectionSelected = 'N';
        char largeurDirectionSelected = 'N';
        for(int i=0;i<map.getAnthills().size();i++){
            int anthillPositionLargeur = map.getAnthills().get(i).getPositionLargeur();
            int anthillPositionHauteur = map.getAnthills().get(i).getPositionHauteur();
            int antPositionLargeur = ant.getPositionLargeur();
            int antPositionHauteur = ant.getPositionHauteur();

            int deltaHauteur1 = 0;
            int deltaHauteur2 = 0;
            int deltaLargeur1 = 0;
            int deltaLargeur2 = 0;
            char hauteurDirection = 'N';
            char largeurDirection = 'N';

            if(antPositionHauteur < anthillPositionHauteur){
                deltaHauteur1 = anthillPositionHauteur-antPositionHauteur;
                deltaHauteur2 = antPositionHauteur-anthillPositionHauteur+map.getHauteur();
                if(deltaHauteur1 < deltaHauteur2) hauteurDirection = 'B';
                else hauteurDirection = 'T';
            }
            else if(antPositionHauteur > anthillPositionHauteur){
                deltaHauteur1 = antPositionHauteur-anthillPositionHauteur;
                deltaHauteur2 = anthillPositionHauteur-antPositionHauteur+map.getHauteur();
                if(deltaHauteur1 < deltaHauteur2) hauteurDirection = 'T';
                else hauteurDirection = 'B';
            }
            else{
                hauteurDirection = 'N';
            }

            if(antPositionLargeur < anthillPositionLargeur){
                deltaLargeur1 = anthillPositionLargeur-antPositionLargeur;
                deltaLargeur2 = antPositionLargeur-anthillPositionLargeur+map.getLargeur();
                if(deltaLargeur1 < deltaLargeur2) largeurDirection = 'R';
                else largeurDirection = 'L';
            }
            else if(antPositionLargeur > anthillPositionLargeur){
                deltaLargeur1 = antPositionLargeur-anthillPositionLargeur;
                deltaLargeur2 = anthillPositionLargeur-antPositionLargeur+map.getLargeur();
                if(deltaLargeur1 < deltaLargeur2) largeurDirection = 'L';
                else largeurDirection = 'R';
            }
            else{
                largeurDirection = 'N';
            }

            deltaHauteur = Math.min(deltaHauteur1,deltaHauteur2);
            deltaLargeur = Math.min(deltaLargeur1,deltaLargeur2);
            int deltaTotal = deltaHauteur + deltaLargeur;
            if(distanceAntAnthillMin > deltaTotal){
                distanceAntAnthillMin = deltaTotal;
                antHillSelected = i;
                deltaHauteurSelected = deltaHauteur;
                deltaLargeurSelected = deltaLargeur;
                hauteurDirectionSelected = hauteurDirection;
                largeurDirectionSelected = largeurDirection;
            }
        }
        if(hauteurDirectionSelected != 'N'){
            ant.setIsComingHome(true);
            //System.out.println("Déplacement vers " + hauteurDirectionSelected);
            moveAnt(ant, hauteurDirectionSelected);
        }
        else if(hauteurDirectionSelected == 'N' && largeurDirectionSelected != 'N'){
            ant.setIsComingHome(true);
            //System.out.println("Déplacement vers " + largeurDirectionSelected);
            moveAnt(ant, largeurDirectionSelected);
        }
        else{
            ant.setIsComingHome(false);
        }
    }

    public void takeFood(Ant ant){
        int antPositionLargeur = ant.getPositionLargeur();
        int antPositionHauteur = ant.getPositionHauteur();
        if(map.getGrid()[antPositionHauteur][antPositionLargeur] instanceof Food && !ant.getAsFood()){
            ant.setAsFood(true);
            ant.augmenterScore(1);
            ((Food) map.getGrid()[antPositionHauteur][antPositionLargeur]).takeFood();
            if(((Food) map.getGrid()[antPositionHauteur][antPositionLargeur]).getQuantity() == 0){
                map.getFoods().remove((Food) map.getGrid()[antPositionHauteur][antPositionLargeur]);
                map.getGrid()[antPositionHauteur][antPositionLargeur] = new Cell(antPositionLargeur,antPositionHauteur);
            }
        }
    }

    public void dropFood(Ant ant){
        int antPositionLargeur = ant.getPositionLargeur();
        int antPositionHauteur = ant.getPositionHauteur();
        if(ant.getAsFood()){
            ant.setAsFood(false);
            if(map.getGrid()[antPositionHauteur][antPositionLargeur] instanceof Food){
                ant.augmenterScore(1);
                ((Food) map.getGrid()[antPositionHauteur][antPositionLargeur]).dropFood();
            }
            else if(map.getGrid()[antPositionHauteur][antPositionLargeur] instanceof Anthill) {
                ant.augmenterScore(25);
            }
            else{
                ant.augmenterScore(1);
                map.getGrid()[antPositionHauteur][antPositionLargeur] = new Food(antPositionLargeur,antPositionHauteur,1);
                map.getFoods().add((Food) map.getGrid()[antPositionHauteur][antPositionLargeur]);
            }
        }
    }

    public void moveAntDecisionTree(Ant ant) throws IOException {
        if(!ant.getIsComingHome()){
            DecisionTree dt = ant.getIntelligence();
            moveAntDecisionTreePrivate(ant, dt);
        }
        else
            comeHomeAnt(ant);
    }

    private void moveAntDecisionTreePrivate(Ant ant, DecisionTree dt) throws IOException {
        String nodeValue[] = dt.getValeurNoeud().split("_");
        String nodeType = nodeValue[0];
        int positionLargeur = ant.getPositionLargeur();
        int positionHauteur = ant.getPositionHauteur();
        if(nodeType.equals("cond")){
            String condition = nodeValue[1];
            if(condition.equals("nourriture")){
                if(map.getGrid()[positionHauteur][positionLargeur] instanceof Food)
                    moveAntDecisionTreePrivate(ant, dt.getTreeLeft());
                else
                    moveAntDecisionTreePrivate(ant, dt.getTreeRight());
            }
            else if(condition.equals("fourmiliere")){
                if(map.getGrid()[positionHauteur][positionLargeur] instanceof Anthill)
                    moveAntDecisionTreePrivate(ant, dt.getTreeLeft());
                else
                    moveAntDecisionTreePrivate(ant, dt.getTreeRight());
            }
            else if(condition.equals("possedeNourriture")){
                if(ant.getAsFood())
                    moveAntDecisionTreePrivate(ant, dt.getTreeLeft());
                else
                    moveAntDecisionTreePrivate(ant, dt.getTreeRight());
            }
        }
        else if (nodeType.equals("act")){
            String action = nodeValue[1];
            if(action.equals("allerGauche")){
                moveAnt(ant,'L');
            }
            else if(action.equals("allerDroite")){
                moveAnt(ant,'R');
            }
            else if(action.equals("allerHaut")){
                moveAnt(ant,'T');
            }
            else if(action.equals("allerBas")){
                moveAnt(ant,'B');
            }
            else if(action.equals("allerAleat")){
                moveAnt(ant,'A');
            }
            else if(action.equals("ramasser")){
                takeFood(ant);
            }
            else if(action.equals("deposer")){
                dropFood(ant);
            }
            else if(action.equals("rentrer")){
                comeHomeAnt(ant);
            }
            else if(action.equals("aleat")){
                DecisionTree dtTemp = new DecisionTree();
                int randAct = (int) (Math.random() * (dtTemp.getActTab().length-1));
                DecisionTree dtTemp2 = new DecisionTree(dtTemp.getActTab()[randAct]);
                moveAntDecisionTreePrivate(ant,dtTemp2);
            }
        }
    }

    public void saveWorld(String path) throws IOException {
        File file = new File(path);
        if (!file.exists()) {
            file.createNewFile();
        }
        FileWriter fw = new FileWriter(file.getAbsoluteFile());
        BufferedWriter bw = new BufferedWriter(fw);
        String str = map.getLargeur() + "x" + map.getHauteur() + "\n";
        str += ants.size();
        for(Ant ant : ants){
            str += " (" + ant.getPositionLargeur() + "," + ant.getPositionHauteur() + "," + ant.getScore() + ")";
        }
        str += "\n";
        str += map.getFoods().size();
        for(Food food: map.getFoods()){
            str += " (" + food.getPositionLargeur() + "," + food.getPositionHauteur() + "," + food.getQuantity() + ")";
        }
        str += "\n";
        for(int i=0;i<map.getHauteur();i++){
            str += "\n";
            for(int j=0;j<map.getLargeur();j++){
                str += map.getGrid()[i][j] + " ";
            }
        }
        bw.write(str);
        bw.close();
    }

    public Map getMap() { return map; }

    public ArrayList<Ant> getAnts() { return ants; }

    public void setAnts(ArrayList<Ant> ants){ this.ants = ants; }

    public void setMap(Map map) { this.map = map; }

    public Ant getAnt(int id) { return ants.get(id); }

    public World clone() throws CloneNotSupportedException {
        World w = (World) super.clone();
        w.map = (Map) this.map.clone();
        w.ants = (ArrayList<Ant>) this.ants.clone();
        return w;
    }
}
