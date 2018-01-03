package player.ai;

import game.BoardHelper;

import java.awt.*;
import java.util.ArrayList;

public class Minimax {

    //returns max score move
    public static Point solve(int[][] board, int player,int depth){
        int bestScore = Integer.MIN_VALUE;
        Point bestMove = null;
        for(Point move : BoardHelper.getAllPossibleMoves(board,player)){
            //create new node
            int[][] newNode = BoardHelper.getNewBoardAfterMove(board,move,player);
            //recursive call
            int childScore = MM(newNode,player,depth-1,false);
            if(childScore > bestScore) {
                bestScore = childScore;
                bestMove = move;
            }
        }
        return bestMove;
    }

    //returns minimax value for a given node
    private static int MM(int[][] node,int player,int depth,boolean max){
        //if terminal reached or depth limit reached evaluate
        if(depth == 0 || BoardHelper.isGameFinished(node)){
            return Evaluator.eval(node,player);
        }
        int oplayer = (player==1) ? 2 : 1;
        //if no moves available then forfeit turn
        if((max && BoardHelper.hasAnyMoves(node,player)) || (!max && BoardHelper.hasAnyMoves(node,oplayer))){
            return MM(node,player,depth-1,!max);
        }
        int score;
        if(max){
            //maximizing
            score = Integer.MIN_VALUE;
            for(Point move : BoardHelper.getAllPossibleMoves(node,player)){ //my turn
                //create new node
                int[][] newNode = BoardHelper.getNewBoardAfterMove(node,move,player);
                //recursive call
                int childScore = MM(newNode,player,depth-1,false);
                if(childScore > score) score = childScore;
            }
        }else{
            //minimizing
            score = Integer.MAX_VALUE;
            for(Point move : BoardHelper.getAllPossibleMoves(node,oplayer)){ //opponent turn
                //create new node
                int[][] newNode = BoardHelper.getNewBoardAfterMove(node,move,oplayer);
                //recursive call
                int childScore = MM(newNode,player,depth-1,false);
                if(childScore < score) score = childScore;
            }
        }
        return score;
    }

}
