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

                if(canPlay(board,turn,i,j)){
                    cells[i][j].highlight = 1;
                }else{
                    cells[i][j].highlight = 0;
                }
            }
        }

        score1.setText("Score 1 : " + p1score);
        score2.setText("Score 2 : " + p2score);
    }

    public void handleClick(int i,int j){
        if(turn==playerTurn && canPlay(board,turn,i,j)){
            System.out.println("User Played in : "+ i + " , " + j);
            //unhighlight all points
            //cells[i][j].highlight = 0;

            //place piece
            board[i][j] = turn;
            //reverse pieces
            ArrayList<Point> rev = getReversePoints(board,turn,i,j);
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
            ArrayList<Point> rev = getReversePoints(board,turn,i,j);
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

    public static ArrayList<Point> getAllPossibleMoves(int[][] board,int player){
        ArrayList<Point> result = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if(canPlay(board,player,i,j)){
                    result.add(new Point(i,j));
                }
            }
        }
        return result;
    }

    public static ArrayList<Point> getReversePoints(int[][] board,int player,int i,int j){

        ArrayList<Point> allReversePoints = new ArrayList<>();

        int mi , mj , c;
        int oplayer = ((player == 1) ? 2 : 1);

        //move up
        ArrayList<Point> mupts = new ArrayList<>();
        mi = i - 1;
        mj = j;
        while(mi>0 && board[mi][mj] == oplayer){
            mupts.add(new Point(mi,mj));
            mi--;
        }
        if(mi>=0 && board[mi][mj] == player && mupts.size()>0){
            allReversePoints.addAll(mupts);
        }


        //move down
        ArrayList<Point> mdpts = new ArrayList<>();
        mi = i + 1;
        mj = j;
        while(mi<7 && board[mi][mj] == oplayer){
            mdpts.add(new Point(mi,mj));
            mi++;
        }
        if(mi<=7 && board[mi][mj] == player && mdpts.size()>0){
            allReversePoints.addAll(mdpts);
        }

        //move left
        ArrayList<Point> mlpts = new ArrayList<>();
        mi = i;
        mj = j - 1;
        while(mj>0 && board[mi][mj] == oplayer){
            mlpts.add(new Point(mi,mj));
            mj--;
        }
        if(mj>=0 && board[mi][mj] == player && mlpts.size()>0){
            allReversePoints.addAll(mlpts);
        }

        //move right
        ArrayList<Point> mrpts = new ArrayList<>();
        mi = i;
        mj = j + 1;
        while(mj<7 && board[mi][mj] == oplayer){
            mrpts.add(new Point(mi,mj));
            mj++;
        }
        if(mj<=7 && board[mi][mj] == player && mrpts.size()>0){
            allReversePoints.addAll(mrpts);
        }

        //move up left
        ArrayList<Point> mulpts = new ArrayList<>();
        mi = i - 1;
        mj = j - 1;
        while(mi>0 && mj>0 && board[mi][mj] == oplayer){
            mulpts.add(new Point(mi,mj));
            mi--;
            mj--;
        }
        if(mi>=0 && mj>=0 && board[mi][mj] == player && mulpts.size()>0){
            allReversePoints.addAll(mulpts);
        }

        //move up right
        ArrayList<Point> murpts = new ArrayList<>();
        mi = i - 1;
        mj = j + 1;
        while(mi>0 && mj<7 && board[mi][mj] == oplayer){
            murpts.add(new Point(mi,mj));
            mi--;
            mj++;
        }
        if(mi>=0 && mj<=7 && board[mi][mj] == player && murpts.size()>0){
            allReversePoints.addAll(murpts);
        }

        //move down left
        ArrayList<Point> mdlpts = new ArrayList<>();
        mi = i + 1;
        mj = j - 1;
        while(mi<7 && mj>0 && board[mi][mj] == oplayer){
            mdlpts.add(new Point(mi,mj));
            mi++;
            mj--;
        }
        if(mi<=7 && mj>=0 && board[mi][mj] == player && mdlpts.size()>0){
            allReversePoints.addAll(mdlpts);
        }

        //move down right
        ArrayList<Point> mdrpts = new ArrayList<>();
        mi = i + 1;
        mj = j + 1;
        while(mi<7 && mj<7 && board[mi][mj] == oplayer){
            mdrpts.add(new Point(mi,mj));
            mi++;
            mj++;
        }
        if(mi<=7 && mj<=7 && board[mi][mj] == player && mdrpts.size()>0){
            allReversePoints.addAll(mdrpts);
        }

        return allReversePoints;
    }

    public static boolean canPlay(int[][] board,int player,int i,int j){

        if(board[i][j] != 0) return false;

        int mi , mj , c;
        int oplayer = ((player == 1) ? 2 : 1);

        //move up
        mi = i - 1;
        mj = j;
        c = 0;
        while(mi>0 && board[mi][mj] == oplayer){
            mi--;
            c++;
        }
        if(mi>=0 && board[mi][mj] == player && c>0) return true;


        //move down
        mi = i + 1;
        mj = j;
        c = 0;
        while(mi<7 && board[mi][mj] == oplayer){
            mi++;
            c++;
        }
        if(mi<=7 && board[mi][mj] == player && c>0) return true;

        //move left
        mi = i;
        mj = j - 1;
        c = 0;
        while(mj>0 && board[mi][mj] == oplayer){
            mj--;
            c++;
        }
        if(mj>=0 && board[mi][mj] == player && c>0) return true;

        //move right
        mi = i;
        mj = j + 1;
        c = 0;
        while(mj<7 && board[mi][mj] == oplayer){
            mj++;
            c++;
        }
        if(mj<=7 && board[mi][mj] == player && c>0) return true;

        //move up left
        mi = i - 1;
        mj = j - 1;
        c = 0;
        while(mi>0 && mj>0 && board[mi][mj] == oplayer){
            mi--;
            mj--;
            c++;
        }
        if(mi>=0 && mj>=0 && board[mi][mj] == player && c>0) return true;

        //move up right
        mi = i - 1;
        mj = j + 1;
        c = 0;
        while(mi>0 && mj<7 && board[mi][mj] == oplayer){
            mi--;
            mj++;
            c++;
        }
        if(mi>=0 && mj<=7 && board[mi][mj] == player && c>0) return true;

        //move down left
        mi = i + 1;
        mj = j - 1;
        c = 0;
        while(mi<7 && mj>0 && board[mi][mj] == oplayer){
            mi++;
            mj--;
            c++;
        }
        if(mi<=7 && mj>=0 && board[mi][mj] == player && c>0) return true;

        //move down right
        mi = i + 1;
        mj = j + 1;
        c = 0;
        while(mi<7 && mj<7 && board[mi][mj] == oplayer){
            mi++;
            mj++;
            c++;
        }
        if(mi<=7 && mj<=7 && board[mi][mj] == player && c>0) return true;

        //when all hopes fade away
        return false;
    }

}
