import util_ant.Ant;
import util_world.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Graphics extends JPanel {
    private Simulation simulation;
    private ArrayList<Ant> antsToShow;

    public Graphics(Simulation simulation){
        super();
        this.simulation = simulation;
    }

    protected void paintComponent(java.awt.Graphics g) {
        Cell[][] grid = simulation.getWorld().getMap().getGrid();

        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();

        int largeur = simulation.getWorld().getMap().getLargeur();
        int hauteur = simulation.getWorld().getMap().getHauteur();
        int size = Math.min(getWidth() - 4, getHeight() - 4) / hauteur;

        String pathCell = System.getProperty("user.dir") + "\\images\\Cell_2.jpg";
        String pathAnthill = System.getProperty("user.dir") + "\\images\\Anthill.png";
        String pathFood1 = System.getProperty("user.dir") + "\\images\\Food_1.png";
        String pathFood5 = System.getProperty("user.dir") + "\\images\\Food_5+.png";
        String pathFood10 = System.getProperty("user.dir") + "\\images\\Food_10+.png";
        String pathAntTop = System.getProperty("user.dir") + "\\images\\Ant_top.png";
        String pathAntBottom = System.getProperty("user.dir") + "\\images\\Ant_bottom.png";
        String pathAntLeft = System.getProperty("user.dir") + "\\images\\Ant_left.png";
        String pathAntRight = System.getProperty("user.dir") + "\\images\\Ant_right.png";
        String pathAntTopFood = System.getProperty("user.dir") + "\\images\\Ant_top_food.png";
        String pathAntBottomFood = System.getProperty("user.dir") + "\\images\\Ant_bottom_food.png";
        String pathAntLeftFood = System.getProperty("user.dir") + "\\images\\Ant_left_food.png";
        String pathAntRightFood = System.getProperty("user.dir") + "\\images\\Ant_right_food.png";

        Image cellImg = null;
        Image anthillImg = null;
        Image foodImg1 = null;
        Image foodImg5 = null;
        Image foodImg10 = null;
        Image antTopImg = null;
        Image antBottomImg = null;
        Image antLeftImg = null;
        Image antRightImg = null;
        Image antTopImgFood = null;
        Image antBottomImgFood = null;
        Image antLeftImgFood = null;
        Image antRightImgFood = null;

        try {
            cellImg = ImageIO.read(new File(pathCell));
            anthillImg = ImageIO.read(new File(pathAnthill));
            foodImg1 = ImageIO.read(new File(pathFood1));
            foodImg5 = ImageIO.read(new File(pathFood5));
            foodImg10 = ImageIO.read(new File(pathFood10));
            antTopImg = ImageIO.read(new File(pathAntTop));
            antBottomImg = ImageIO.read(new File(pathAntBottom));
            antLeftImg = ImageIO.read(new File(pathAntLeft));
            antRightImg = ImageIO.read(new File(pathAntRight));
            antTopImgFood = ImageIO.read(new File(pathAntTopFood));
            antBottomImgFood = ImageIO.read(new File(pathAntBottomFood));
            antLeftImgFood = ImageIO.read(new File(pathAntLeftFood));
            antRightImgFood = ImageIO.read(new File(pathAntRightFood));
        } catch (IOException e) {
            e.printStackTrace();
        }

        int positionLargeur = 0;
        int positionHauteur = 0;
        int passage = 0;

        int y = (getHeight() - (size * hauteur)) / 2;
        for (int horz = 0; horz < hauteur; horz++) {
            int x = 8; // (getWidth() - (size * largeur)) / 2;
            for (int vert = 0; vert < largeur; vert++) {
                positionLargeur = passage%largeur;
                positionHauteur = (int) passage/largeur;
                g.drawImage(cellImg, x, y, size, size, this);
                if(grid[positionHauteur][positionLargeur] instanceof Anthill)
                    g.drawImage(anthillImg, x, y, size, size, this);
                else if(grid[positionHauteur][positionLargeur] instanceof Food){
                    if(((Food) grid[positionHauteur][positionLargeur]).getQuantity() >=10){
                        g.drawImage(foodImg10, x, y, size, size, this);
                    }
                    else if(((Food) grid[positionHauteur][positionLargeur]).getQuantity() >=2){
                        g.drawImage(foodImg5, x, y, size, size, this);
                    }
                    else{
                        g.drawImage(foodImg1, x, y, size, size, this);
                    }
                }
                for(Ant ant : simulation.getWorld().getAnts()){
                    if(ant.getPositionLargeur() == positionLargeur && ant.getPositionHauteur() == positionHauteur && antsToShow.contains(ant))
                        if(!ant.getAsFood()) {
                            if (ant.getDirection() == 'T')
                                g.drawImage(antTopImg, x, y, size, size, this);
                            else if (ant.getDirection() == 'B')
                                g.drawImage(antBottomImg, x, y, size, size, this);
                            else if (ant.getDirection() == 'L')
                                g.drawImage(antLeftImg, x, y, size, size, this);
                            else if (ant.getDirection() == 'R')
                                g.drawImage(antRightImg, x, y, size, size, this);
                        }
                        else {
                            if (ant.getDirection() == 'T')
                                g.drawImage(antTopImgFood, x, y, size, size, this);
                            else if (ant.getDirection() == 'B')
                                g.drawImage(antBottomImgFood, x, y, size, size, this);
                            else if (ant.getDirection() == 'L')
                                g.drawImage(antLeftImgFood, x, y, size, size, this);
                            else if (ant.getDirection() == 'R')
                                g.drawImage(antRightImgFood, x, y, size, size, this);
                        }
                }
                x += size;
                passage++;
            }
            y += size;
        }
        g2d.dispose();
    }

    public void setSimulation(Simulation simulation) { this.simulation = simulation; }

    public void setAntsToShow(ArrayList<Ant> antsToShow) { this.antsToShow = antsToShow; }
}
