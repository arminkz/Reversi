import javax.swing.*;

public class GameWindow extends JFrame {

    public GameWindow(){
        GamePanel gp = new GamePanel();
        //this.setLayout(null);
        this.add(gp);
        this.setTitle("Reversi v0.1");
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setVisible(true);
        //this.setSize(500,500);
        this.pack();
    }

    public static void main(String[] args) {
        new GameWindow();
    }

}
