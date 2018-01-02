package game;

import player.RandomPlayer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class GamePanel extends JPanel {

    int[][] board;
    BoardCell[][] cells;
    JLabel score1;
    JLabel score2;

    //player turn
    //black plays first
    int turn = 2;
    int aiTurn = 1;
    int playerTurn = 2;

    String name1 = "Random Player (AI)";
    String name2 = "User";

    GameAI ai = new RandomPlayer();

    Timer playerHandlerTimer;

    public GamePanel(){
        this.setBackground(Color.WHITE);
        this.setLayout(new BorderLayout());

        JPanel reversiBoard = new JPanel();
        reversiBoard.setLayout(new GridLayout(8,8));
        reversiBoard.setPreferredSize(new Dimension(500,500));
        reversiBoard.setBackground(new Color(41,100, 59));

        board = new int[8][8];
        cells = new BoardCell[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                cells[i][j] = new BoardCell(this,reversiBoard,i,j);
                reversiBoard.add(cells[i][j]);
            }
        }


        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar,BoxLayout.Y_AXIS));
        sidebar.setPreferredSize(new Dimension(150,0));

        score1 = new JLabel("Score 1");
        score2 = new JLabel("Score 2");

        sidebar.add(score1);
        sidebar.add(score2);


        this.add(sidebar,BorderLayout.WEST);
        this.add(reversiBoard);


        //initial board state
        setBoardValue(3,3,2);
        setBoardValue(3,4,1);
        setBoardValue(4,3,1);
        setBoardValue(4,4,2);

        //
        updateBoardInfo();

        //AI Handler Timer
        playerHandlerTimer = new Timer(500,(ActionEvent e) -> {
            handleAI(ai);
            playerHandlerTimer.stop();
        });
    }

    public int getBoardValue(int i,int j){
        return board[i][j];
    }

    public void setBoardValue(int i,int j,int value){
        board[i][j] = value;
    }

    //update highlights on possible moves and scores
    public void updateBoardInfo(){

        int p1score = 0;
        int p2score = 0;

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if(board[i][j] == 1) p1score++;
                if(board[i][j] == 2) p2score++;

                if(BoardHelper.canPlay(board,turn,i,j)){
                    cells[i][j].highlight = 1;
                }else{
                    cells[i][j].highlight = 0;
                }
            }
        }

        score1.setText(name1 + " : " + p1score);
        score2.setText(name2 + " : " + p2score);
    }

    public void handleClick(int i,int j){
        if(turn==playerTurn && BoardHelper.canPlay(board,turn,i,j)){
            System.out.println("User Played in : "+ i + " , " + j);
            //unhighlight all points
            //cells[i][j].highlight = 0;

            //place piece
            board[i][j] = turn;
            //reverse pieces
            ArrayList<Point> rev = BoardHelper.getReversePoints(board,turn,i,j);
            for(Point pt : rev){
                board[pt.x][pt.y] = turn;
            }

            //advance turn
            turn = (turn == 1) ? 2 : 1;
            //
            updateBoardInfo();

            repaint();

            //trigger next player
            playerHandlerTimer.start();
        }
    }

    public void handleAI(GameAI ai){
        if(turn == aiTurn) {
            Point aiPlayPoint = ai.play(board, aiTurn);
            int i = aiPlayPoint.x;
            int j = aiPlayPoint.y;
            System.out.println("AI Played in : "+ i + " , " + j);

            //place piece
            board[i][j] = turn;
            //reverse pieces
            ArrayList<Point> rev = BoardHelper.getReversePoints(board,turn,i,j);
            for(Point pt : rev){
                board[pt.x][pt.y] = turn;
            }

            //advance turn
            turn = (turn == 1) ? 2 : 1;
            //
            updateBoardInfo();

            repaint();
        }
    }

    private void repaintBoard(){
        //repait board
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                cells[i][j].repaint();
            }
        }
    }



}
