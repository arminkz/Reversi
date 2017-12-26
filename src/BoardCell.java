import javax.swing.*;
import java.awt.*;

public class BoardCell extends JLabel {

    int value;
    // 0 : Empty
    // 1 : Player 1
    // 2 : Player 2

    @Override
    public void paint(Graphics g) {
        g.drawRect(0,0,this.getWidth(),this.getHeight());
        g.setColor(Color.WHITE);
        g.fillOval(10,10,this.getWidth()-20,this.getHeight()-20);
        g.setColor(new Color(100,100,100));
        g.drawOval(10,10,this.getWidth()-20,this.getHeight()-20);
        super.paint(g);
    }
    
}
