public class World {
    Map map;
    Ant[] ants;

    World(int largeur, int hauteur, int nbAnt, int nbFood){
        map = new Map(largeur, hauteur, nbFood);
        ants = new Ant[nbAnt];
        for (int i=0;i<nbAnt;i++){
            ants[i] = new Ant(map.getHanthillX(), map.getHanthillY());
        }
    }

    public void placeAnts(){
        int largeur = map.getLargeur();
        int hauteur = map.getHauteur();
        Map map_ant = new Map(largeur, hauteur);
        for(Ant ant : ants){
            map_ant.placeObject(ant.getPositionLargeur(), ant.getPositionHauteur(), ant);
        }
        map_ant.displayMap();
    }

    public void moveAnt(Ant ant, char direction){
        if(direction == 'T'){
            if(ant.getPositionHauteur() > 0)
                ant.goToTop();
            else
                ant.setPositionHauteur(map.getHauteur()-1);
        }
        else if(direction == 'B'){
            if(ant.getPositionHauteur() < map.getHauteur()-1)
                ant.goToBottom();
            else
                ant.setPositionHauteur(0);
        }
        else if(direction == 'L'){
            if(ant.getPositionLargeur() > 0)
                ant.goToLeft();
            else
                ant.setPositionLargeur(map.getLargeur()-1);
        }
        else if(direction == 'R'){
            if(ant.getPositionLargeur() < map.getHauteur()-1)
                ant.goToRight();
            else
                ant.setPositionLargeur(0);
        }
    }

    public Map getMap() { return map; }

    public Ant[] getAnts() { return ants; }

    public Ant getAnt(int id) { return ants[id]; }
}
