package player;

import game.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class RandomPlayer extends GameAI {

    Random rnd = new Random();

    @Override
    public Point play(int[][] board, int myMark) {

        ArrayList<Point> myPossibleMoves = GamePanel.getAllPossibleMoves(board,myMark);

        if(myPossibleMoves.size() > 0){
            return myPossibleMoves.get(rnd.nextInt(myPossibleMoves.size()));
        }else{
            return null;
        }

    }

}
