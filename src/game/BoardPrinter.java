package game;

import javax.swing.*;
import java.awt.*;

public class BoardPrinter extends JFrame implements GameEngine {


    //DEBUG CODE HERE
    public static void main(String[] args) {
        int[][] exampleBoard = new int[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                exampleBoard[i][j] = 0;
            }
        }
        exampleBoard[3][3] = 2;
        exampleBoard[3][4] = 1;
        exampleBoard[4][3] = 1;
        exampleBoard[4][4] = 2;
        BoardPrinter bp = new BoardPrinter(exampleBoard);
        bp.cells[1][1].highlight = 10;
        bp.cells[1][1].text = "+3";
        bp.repaint();
    }


    BoardCell[][] cells;
    int[][] board;

    public BoardPrinter(int[][] board){

        this.board = board;

        JPanel reversiBoard = new JPanel();
        reversiBoard.setLayout(new GridLayout(8,8));
        reversiBoard.setPreferredSize(new Dimension(500,500));
        reversiBoard.setBackground(new Color(41,100, 59));

        //init board
        //resetBoard();

        cells = new BoardCell[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                cells[i][j] = new BoardCell(this,reversiBoard,i,j);
                reversiBoard.add(cells[i][j]);
            }
        }

        this.add(reversiBoard);
        this.setTitle("BoardPrinter");
        //this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);

        reversiBoard.repaint();
        //this.setSize(500,500);

    }


    @Override
    public int getBoardValue(int i, int j) {
        return board[i][j];
    }

    @Override
    public void setBoardValue(int i, int j, int value) {
        System.err.println("Printer cant edit !");
    }

    @Override
    public void handleClick(int i, int j) {
        //do nothing
    }
}
