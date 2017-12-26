import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GamePanel extends JPanel {

    int[][] board;
    BoardCell[][] cells;

    //player turn
    int turn = 1;

    public GamePanel(){
        this.setPreferredSize(new Dimension(500,500));
        this.setBackground(new Color(41,100, 59));

        this.setLayout(new GridLayout(8,8));

        board = new int[8][8];
        cells = new BoardCell[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                cells[i][j] = new BoardCell(this,i,j);
                this.add(cells[i][j]);
            }
        }

        //initial board state
        setBoardValue(3,3,1);
        setBoardValue(3,4,2);
        setBoardValue(4,3,2);
        setBoardValue(4,4,1);

        getPossibleMoves();
    }

    public int getBoardValue(int i,int j){
        return board[i][j];
    }

    public void setBoardValue(int i,int j,int value){
        board[i][j] = value;
    }

    public ArrayList<Point> getPossibleMoves(){
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if(canPlay(turn,i,j)){
                    cells[i][j].highlight = 1;
                    cells[i][j].repaint();
                }
            }
        }
        return null;
    }

    public boolean canPlay(int player,int i,int j){
        int mi , mj , c;
        int oplayer = ((player == 1) ? 2 : 1);

        //move up
        mi = i - 1;
        mj = j;
        c = 0;
        while(mi>0 && getBoardValue(mi,mj) == oplayer){
            mi--;
            c++;
        }
        if(mi>=0 && getBoardValue(mi,mj) == player && c>0) return true;


        //move down
        mi = i + 1;
        mj = j;
        c = 0;
        while(mi<7 && getBoardValue(mi,mj) == oplayer){
            mi++;
            c++;
        }
        if(mi<=7 && getBoardValue(mi,mj) == player && c>0) return true;

        //move left
        mi = i;
        mj = j - 1;
        c = 0;
        while(mj>0 && getBoardValue(mi,mj) == oplayer){
            mj--;
            c++;
        }
        if(mj>=0 && getBoardValue(mi,mj) == player && c>0) return true;

        //move right
        mi = i;
        mj = j + 1;
        c = 0;
        while(mj<7 && getBoardValue(mi,mj) == oplayer){
            mj++;
            c++;
        }
        if(mj<=7 && getBoardValue(mi,mj) == player && c>0) return true;

        //when all hopes fade away
        return false;
    }

}
