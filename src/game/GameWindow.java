package game;

import javax.swing.*;
import java.awt.*;

public class GameWindow extends JFrame {

    public GameWindow(){
        GamePanel gp = new GamePanel();
        this.add(gp);
        this.setTitle("Reversi v0.1");
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);
        //this.setSize(500,500);

    }

    public static void main(String[] args) {
        new GameWindow();
    }

}
