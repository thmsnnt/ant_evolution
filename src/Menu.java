import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.Date;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class Menu extends JPanel {
    private Simulation simulation;

    class MenuTop extends JPanel implements ActionListener {
        private JButton playButton;
        private JButton pauseButton;
        private JButton slowButton;
        private JButton speedButton;
        private JButton skipSimulationButton;
        private JButton saveSimulationButton;
        private JButton selectionAntsDisplayValidateButton;
        private JTextField numAntTextArea;
        private JButton selectionAntsDisplaySelectAll;
        private JButton selectionAntsDisplaySelectOne;
        private JButton selectionAntsDisplaySelectTwo;
        private JButton selectionAntsDisplaySelectThree;
        private JButton selectionAntsDisplaySelectFive;
        private JButton selectionAntsDisplaySelectTen;
        private JButton selectionAntsDisplaySelectTwenty;

        private MenuTop(){
            super();
            setLayout(new GridLayout(2,1));

            JPanel header = new JPanel(new GridLayout(2,1));

            JPanel titlePanel = new JPanel(new GridBagLayout());
            JLabel title = new JLabel("Ant simulation");
            title.setFont(new Font("Arial", Font.BOLD, 30));
            titlePanel.add(title);

            int sizeIcon = 35;
            int sizeButton = (int) (sizeIcon*1.3);
            JPanel buttonPanel = new JPanel();

            ImageIcon iconPause = new ImageIcon(System.getProperty("user.dir") + "\\images\\icon\\pause.png");
            iconPause = new ImageIcon(iconPause.getImage().getScaledInstance(sizeIcon,sizeIcon, java.awt.Image.SCALE_SMOOTH));

            ImageIcon iconPlay = new ImageIcon(System.getProperty("user.dir") + "\\images\\icon\\play.png");
            iconPlay = new ImageIcon(iconPlay.getImage().getScaledInstance(sizeIcon,sizeIcon, java.awt.Image.SCALE_SMOOTH));

            ImageIcon iconSpeed = new ImageIcon(System.getProperty("user.dir") + "\\images\\icon\\speed.png");
            iconSpeed = new ImageIcon(iconSpeed.getImage().getScaledInstance(sizeIcon,sizeIcon, java.awt.Image.SCALE_SMOOTH));

            ImageIcon iconSlow = new ImageIcon(System.getProperty("user.dir") + "\\images\\icon\\slow.png");
            iconSlow = new ImageIcon(iconSlow.getImage().getScaledInstance(sizeIcon,sizeIcon, java.awt.Image.SCALE_SMOOTH));

            ImageIcon iconSkipSimulation = new ImageIcon(System.getProperty("user.dir") + "\\images\\icon\\skip_simulation.png");
            iconSkipSimulation = new ImageIcon(iconSkipSimulation.getImage().getScaledInstance(sizeIcon,sizeIcon, java.awt.Image.SCALE_SMOOTH));

            ImageIcon iconSaveSimulation = new ImageIcon(System.getProperty("user.dir") + "\\images\\icon\\save_simulation.png");
            iconSaveSimulation = new ImageIcon(iconSaveSimulation.getImage().getScaledInstance(sizeIcon,sizeIcon, java.awt.Image.SCALE_SMOOTH));

            this.playButton = new JButton(iconPlay);
            this.pauseButton = new JButton(iconPause);
            this.speedButton = new JButton(iconSpeed);
            this.slowButton = new JButton(iconSlow);
            this.skipSimulationButton = new JButton("Next simulation",iconSkipSimulation);
            this.saveSimulationButton = new JButton(iconSaveSimulation);

            this.playButton.setPreferredSize(new Dimension(sizeButton,sizeButton));
            this.pauseButton.setPreferredSize(new Dimension(sizeButton,sizeButton));
            this.speedButton.setPreferredSize(new Dimension(sizeButton,sizeButton));
            this.slowButton.setPreferredSize(new Dimension(sizeButton,sizeButton));
            this.saveSimulationButton.setPreferredSize(new Dimension(sizeButton,sizeButton));

            this.slowButton.addActionListener(this);
            this.playButton.addActionListener(this);
            this.pauseButton.addActionListener(this);
            this.speedButton.addActionListener(this);
            this.skipSimulationButton.addActionListener(this);
            this.saveSimulationButton.addActionListener(this);

            buttonPanel.add(this.saveSimulationButton);
            buttonPanel.add(this.slowButton);
            buttonPanel.add(this.playButton);
            buttonPanel.add(this.pauseButton);
            buttonPanel.add(this.speedButton);
            buttonPanel.add(this.skipSimulationButton);

            header.add(titlePanel);
            header.add(buttonPanel);

            JPanel informationsPanel = new JPanel(new GridLayout(4,1));

            JLabel simulationLabel = new JLabel();
            simulationLabel.setFont(new Font("Arial", Font.PLAIN, 16));
            informationsPanel.add(simulationLabel);

            JLabel percentageIterationLabel = new JLabel();
            percentageIterationLabel.setFont(new Font("Arial", Font.PLAIN, 16));
            informationsPanel.add(percentageIterationLabel);

            JPanel selectionAntsDisplayPanel = new JPanel(new GridLayout(1,10));
            this.numAntTextArea = new JTextField();
            this.numAntTextArea.setFont(new Font("Arial", Font.PLAIN, 24));
            this.numAntTextArea.setHorizontalAlignment(0);
            selectionAntsDisplayPanel.add(numAntTextArea);

            this.selectionAntsDisplayValidateButton = new JButton("Ok");
            this.selectionAntsDisplayValidateButton.addActionListener(this);
            selectionAntsDisplayPanel.add(this.selectionAntsDisplayValidateButton);

            selectionAntsDisplayPanel.add(new JLabel());

            this.selectionAntsDisplaySelectAll = new JButton("All");
            this.selectionAntsDisplaySelectAll.addActionListener(this);
            selectionAntsDisplayPanel.add(this.selectionAntsDisplaySelectAll);

            this.selectionAntsDisplaySelectOne = new JButton("1");
            this.selectionAntsDisplaySelectOne.addActionListener(this);
            selectionAntsDisplayPanel.add(this.selectionAntsDisplaySelectOne);

            this.selectionAntsDisplaySelectTwo = new JButton("2");
            this.selectionAntsDisplaySelectTwo.addActionListener(this);
            selectionAntsDisplayPanel.add(this.selectionAntsDisplaySelectTwo);

            this.selectionAntsDisplaySelectThree = new JButton("3");
            this.selectionAntsDisplaySelectThree.addActionListener(this);
            selectionAntsDisplayPanel.add(this.selectionAntsDisplaySelectThree);

            this.selectionAntsDisplaySelectFive = new JButton("5");
            this.selectionAntsDisplaySelectFive.addActionListener(this);
            selectionAntsDisplayPanel.add(this.selectionAntsDisplaySelectFive);

            this.selectionAntsDisplaySelectTen = new JButton("10");
            this.selectionAntsDisplaySelectTen.addActionListener(this);
            selectionAntsDisplayPanel.add(this.selectionAntsDisplaySelectTen);

            this.selectionAntsDisplaySelectTwenty = new JButton("20");
            this.selectionAntsDisplaySelectTwenty.addActionListener(this);
            selectionAntsDisplayPanel.add(this.selectionAntsDisplaySelectTwenty);

            informationsPanel.add(selectionAntsDisplayPanel);

            add(header);
            add(informationsPanel);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource() == slowButton){
                simulation.slowDownSimulationSpeed();
            }
            else if(e.getSource() == speedButton){
                simulation.accelerateSimulationSpeed();
            }
            else if(e.getSource() == pauseButton){
                simulation.pause();
            }
            else if(e.getSource() == playButton){
                simulation.play();
            }
            else if(e.getSource() == skipSimulationButton){
                simulation.nextSimulation();
            }
            else if(e.getSource() == saveSimulationButton){
                Date date = new Date();
                String path = System.getProperty("user.dir") + "\\save\\" + date.getTime() + "_world.txt";
                try {
                    simulation.getWorld().saveWorld(path);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
            else if(e.getSource() == selectionAntsDisplayValidateButton){
                int antToShow = -1;
                if(numAntTextArea.getText().matches("^\\p{Digit}+$")){
                    antToShow = Integer.parseInt(numAntTextArea.getText());
                    simulation.setAntToShow(antToShow);
                    numAntTextArea.setText("");
                }
            }
            else if(e.getSource() == selectionAntsDisplaySelectAll){
                simulation.setAntToShow("All");
            }
            else if(e.getSource() == selectionAntsDisplaySelectOne){
                simulation.setAntToShow("One");
            }
            else if(e.getSource() == selectionAntsDisplaySelectTwo){
                simulation.setAntToShow("Two");
            }
            else if(e.getSource() == selectionAntsDisplaySelectThree){
                simulation.setAntToShow("Three");
            }
            else if(e.getSource() == selectionAntsDisplaySelectFive){
                simulation.setAntToShow("Five");
            }
            else if(e.getSource() == selectionAntsDisplaySelectTen){
                simulation.setAntToShow("Ten");
            }
            else if(e.getSource() == selectionAntsDisplaySelectTwenty){
                simulation.setAntToShow("Twenty");
            }
        }
    }

    class MenuBottom extends JPanel{
        private MenuBottom(){
            super();
            setLayout(new GridLayout(1,3));

            JPanel leftPanel = new JPanel(new GridLayout(17,1));
            JPanel centerPanel = new JPanel(new GridLayout(17,1));
            JPanel rightPanel = new JPanel(new GridLayout(17,1));

            //rightPanel.setBackground(Color.RED);
            //centerPanel.setBackground(Color.BLUE);

            JPanel bestAntPanel = new JPanel(new GridBagLayout());
            bestAntPanel.setBackground(new Color(220,220,220));
            JLabel bestAntLabel = new JLabel("Meilleure fourmi");
            bestAntLabel.setFont(new Font("Arial", Font.BOLD, 14));
            bestAntPanel.add(bestAntLabel);
            leftPanel.add(bestAntPanel);
            leftPanel.add(new JLabel());
            for(int i=2;i<17;i++)
                leftPanel.add(new JLabel());

            JPanel chooseAntsPanel = new JPanel(new GridBagLayout());
            chooseAntsPanel.setBackground(new Color(238,208,135));
            JLabel chooseAntsLabel = new JLabel("Fourmi(s) choisie(s)");
            chooseAntsLabel.setFont(new Font("Arial", Font.BOLD, 14));
            chooseAntsPanel.add(chooseAntsLabel);
            centerPanel.add(chooseAntsPanel);
            centerPanel.add(new JLabel());
            for(int i=2;i<17;i++)
                centerPanel.add(new JLabel());

            JPanel currentSimulationPanel = new JPanel(new GridBagLayout());
            currentSimulationPanel.setBackground(new Color(150,210,150));
            JLabel currentSimulationLabel = new JLabel("Simulation actuelle");
            currentSimulationLabel.setFont(new Font("Arial", Font.BOLD, 14));
            currentSimulationPanel.add(currentSimulationLabel);
            rightPanel.add(currentSimulationPanel);
            JLabel currentBestScoreLabel = new JLabel();
            JLabel currentMeanScoreLabel = new JLabel();
            JLabel currentWorstScoreLabel = new JLabel();
            rightPanel.add(new JLabel());
            rightPanel.add(currentBestScoreLabel);
            rightPanel.add(currentMeanScoreLabel);
            rightPanel.add(currentWorstScoreLabel);

            rightPanel.add(new JLabel());
            rightPanel.add(new JLabel());
            JPanel previousSimulationPanel = new JPanel(new GridBagLayout());
            previousSimulationPanel.setBackground(new Color(210,190,150));
            JLabel previousSimulationLabel = new JLabel("Simulation précédente");
            previousSimulationLabel.setFont(new Font("Arial", Font.BOLD, 14));
            previousSimulationPanel.add(previousSimulationLabel);
            rightPanel.add(previousSimulationPanel);
            JLabel previousBestScoreLabel = new JLabel();
            JLabel previousMeanScoreLabel = new JLabel();
            JLabel previousWorstScoreLabel = new JLabel();
            rightPanel.add(new JLabel());
            rightPanel.add(previousBestScoreLabel);
            rightPanel.add(previousMeanScoreLabel);
            rightPanel.add(previousWorstScoreLabel);

            add(leftPanel);
            add(centerPanel);
            add(rightPanel);
        }
    }

    public Menu(Simulation simulation){
        super();
        this.simulation = simulation;
        setLayout(new GridLayout(2,1));

        MenuTop menuTop = new MenuTop();
        MenuBottom menuBottom = new MenuBottom();
        add(menuTop);
        add(menuBottom);
    }

    protected void paintComponent(java.awt.Graphics g) {
        JPanel menuTop = (JPanel) getComponent(0);
        JPanel header = (JPanel) menuTop.getComponent(0);
        JPanel informationPanel = (JPanel) menuTop.getComponent(1);
        JPanel selectionAntsDisplayPanel = (JPanel) informationPanel.getComponent(2);
        JTextField numAntTextArea = (JTextField) selectionAntsDisplayPanel.getComponent(0);

        JLabel simulationLabel = (JLabel) informationPanel.getComponent(0);
        simulationLabel.setText("Simulation : " + simulation.getActualSimulation() + "/" + simulation.getNbSimulation());

        int percentageIteration = (int) (simulation.getPercentageIteration()*100);
        JLabel percentageIterationLabel = (JLabel) informationPanel.getComponent(1);
        percentageIterationLabel.setText("Progression : " + percentageIteration + "%");

        JPanel menuBottom = (JPanel) getComponent(1);

        JPanel leftPanel = (JPanel) menuBottom.getComponent(0);
        String[] intelligence = simulation.getBestAnt().getIntelligence().toString().split("\n");
        for(int i=0;i<15;i++){
            JLabel lineLabel = (JLabel) leftPanel.getComponent(i+2);
            if(i < intelligence.length && simulation.getBestAnt().getScore() != 0){
                String[] line = intelligence[i].split("\\.");
                if(Integer.parseInt(line[0].replaceAll("\\s+","")) == 0){
                    if(line[1].split("_")[0].equals("act"))
                        lineLabel.setText("  > " + line[1].split("_")[1]);
                    else
                        lineLabel.setText("  " + line[1].split("_")[1] + " ?");
                }

                else if(Integer.parseInt(line[0].replaceAll("\\s+","")) == 1){
                    if(line[1].split("_")[0].equals("act"))
                        lineLabel.setText("       > " + line[1].split("_")[1]);
                    else
                        lineLabel.setText("       " + line[1].split("_")[1] + " ?");
                }
                else if(Integer.parseInt(line[0].replaceAll("\\s+","")) == 2){
                    if(line[1].split("_")[0].equals("act"))
                        lineLabel.setText("           > " + line[1].split("_")[1]);
                    else
                        lineLabel.setText("           " + line[1].split("_")[1] + " ?");
                }

                else if(Integer.parseInt(line[0].replaceAll("\\s+","")) == 3){
                    if(line[1].split("_")[0].equals("act"))
                        lineLabel.setText("               > " + line[1].split("_")[1]);
                    else
                        lineLabel.setText("               " + line[1].split("_")[1] + " ?");
                }

            }
            else
                lineLabel.setText("");
        }

        JPanel centerPanel = (JPanel) menuBottom.getComponent(1);
        if(simulation.getAntsToShowList() != null){
            int listSize = simulation.getAntsToShowList().size();
            if(listSize == 1){
                String[] intelligenceAnt = simulation.getAntsToShowList().get(0).getIntelligence().toString().split("\n");
                for(int i=0;i<15;i++){
                    JLabel lineLabel = (JLabel) centerPanel.getComponent(i+2);
                    if(i < intelligenceAnt.length){
                        String[] line = intelligenceAnt[i].split("\\.");
                        if(Integer.parseInt(line[0].replaceAll("\\s+","")) == 0){
                            if(line[1].split("_")[0].equals("act"))
                                lineLabel.setText("  > " + line[1].split("_")[1]);
                            else
                                lineLabel.setText("  " + line[1].split("_")[1] + " ?");
                        }

                        else if(Integer.parseInt(line[0].replaceAll("\\s+","")) == 1){
                            if(line[1].split("_")[0].equals("act"))
                                lineLabel.setText("       > " + line[1].split("_")[1]);
                            else
                                lineLabel.setText("       " + line[1].split("_")[1] + " ?");
                        }
                        else if(Integer.parseInt(line[0].replaceAll("\\s+","")) == 2){
                            if(line[1].split("_")[0].equals("act"))
                                lineLabel.setText("           > " + line[1].split("_")[1]);
                            else
                                lineLabel.setText("           " + line[1].split("_")[1] + " ?");
                        }

                        else if(Integer.parseInt(line[0].replaceAll("\\s+","")) == 3){
                            if(line[1].split("_")[0].equals("act"))
                                lineLabel.setText("               > " + line[1].split("_")[1]);
                            else
                                lineLabel.setText("               " + line[1].split("_")[1] + " ?");
                        }

                    }
                    else
                        lineLabel.setText("");
                }
            }
            else if(listSize >= 2 && listSize <= 10){
                for(int i=0;i<15;i++){
                    JLabel lineLabel = (JLabel) centerPanel.getComponent(i+2);
                    if(i<listSize){
                        lineLabel.setText("Score fourmi n°" + (i+1) + " : " + simulation.getAntsToShowList().get(i).getScore());
                    }
                    else
                        lineLabel.setText("");
                }
            }
            else if(simulation.getAntsToShowList().size() >= 20){
                for(int i=0;i<15;i++) {
                    JLabel lineLabel = (JLabel) centerPanel.getComponent(i + 2);
                    lineLabel.setText("");
                }
            }
        }


        JPanel rightPanel = (JPanel) menuBottom.getComponent(2);
        JLabel currentBestScoreLabel = (JLabel) rightPanel.getComponent(2);
        JLabel currentMeanScoreLabel = (JLabel) rightPanel.getComponent(3);
        JLabel currentWorstScoreLabel = (JLabel) rightPanel.getComponent(4);
        currentBestScoreLabel.setText("Meilleur score : " + simulation.getBestAnt().getScore());
        currentMeanScoreLabel.setText("Score moyen : " + simulation.getMeanScore());
        currentWorstScoreLabel.setText("Pire score : " + simulation.getWorstScore());

        JLabel previousBestScoreLabel = (JLabel) rightPanel.getComponent(9);
        JLabel previousMeanScoreLabel = (JLabel) rightPanel.getComponent(10);
        JLabel previousWorstScoreLabel = (JLabel) rightPanel.getComponent(11);
        if(simulation.getBestAntPrevious() != null){
            previousBestScoreLabel.setText("Meilleur score : " + simulation.getBestAntPrevious().getScore());
            previousMeanScoreLabel.setText("Score moyen : " + simulation.getMeanScorePrevious());
            previousWorstScoreLabel.setText("Pire score : " + simulation.getWorstScorePrevious());
        }
        else{
            previousBestScoreLabel.setText("Meilleur score : ");
            previousMeanScoreLabel.setText("Score moyen : ");
            previousWorstScoreLabel.setText("Pire score : ");
        }
    }

    public Simulation getSimulation() { return this.simulation; }
}
