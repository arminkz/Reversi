import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {

    BoardCell[][] cells;

    public GamePanel(){

        this.setPreferredSize(new Dimension(500,500));
        this.setBackground(new Color(41,100, 59));

        this.setLayout(new GridLayout(8,8));

        cells = new BoardCell[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                cells[i][j] = new BoardCell();
                this.add(cells[i][j]);
            }
        }



    }

}
