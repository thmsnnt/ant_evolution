import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Window extends JFrame implements KeyListener {
    public Window(){
        super();
        build();
        addKeyListener(this);
    }

    public void build(){
        setTitle("Ant simulation");
        setSize(1100,700);
        setLocationRelativeTo(null);
        setResizable(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setLayout(new GridLayout(1,2));
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {
        Menu menu = (Menu) getContentPane().getComponent(1);
        if(e.getKeyCode() == 37)
            menu.getSimulation().slowDownSimulationSpeed();
        else if(e.getKeyCode() == 39)
            menu.getSimulation().accelerateSimulationSpeed();
        else if(e.getKeyCode() == 10 && menu.getSimulation().getSimulationPausePlay())
            menu.getSimulation().pause();
        else if(e.getKeyCode() == 10 && !menu.getSimulation().getSimulationPausePlay())
            menu.getSimulation().play();
        else if(e.getKeyCode() == 78)
            menu.getSimulation().nextSimulation();
    }
}
