import javax.swing.*;
import java.awt.*;

public class BoardCell extends JLabel {

    int i;
    int j;

    GamePanel parent;

    int highlight = 0;

    public BoardCell(GamePanel parent,int i,int j){
        this.parent = parent;
        this.i = i;
        this.j = j;
    }

    @Override
    public void paint(Graphics g) {

        //draw highlight
        if(highlight == 1) {
            g.setColor(new Color(138, 177, 62));
            g.fillRect(0,0,this.getWidth(),this.getHeight());
            g.setColor(parent.getBackground());
            g.fillRect(4,4,this.getWidth()-8,this.getHeight()-8);
        }else if(highlight == 2){
            g.setColor(new Color(177, 158, 70));
            g.fillRect(0,0,this.getWidth(),this.getHeight());
            g.setColor(parent.getBackground());
            g.fillRect(4,4,this.getWidth()-8,this.getHeight()-8);
        }

        //draw border
        g.setColor(Color.BLACK);
        g.drawRect(0,0,this.getWidth(),this.getHeight());

        //draw piece
        int value = parent.getBoardValue(i,j);
        if(value == 1){
            g.setColor(Color.WHITE);
            g.fillOval(10,10,this.getWidth()-20,this.getHeight()-20);
        }
        else if(value == 2) {
            g.setColor(Color.BLACK);
            g.fillOval(10,10,this.getWidth()-20,this.getHeight()-20);
        }

        //g.setColor(new Color(100,100,100));
        //g.drawOval(10,10,this.getWidth()-20,this.getHeight()-20);
        super.paint(g);
    }
    
}
