package player.ai;

import game.BoardHelper;

public class StaticEvaluator implements Evaluator {

    public int eval(int[][] board , int player){
        int mob = evalMobility(board,player);
        int sc = evalStoneCount(board,player);
        return 2 * mob + sc ;
    }

    public int evalStoneCount(int[][] board , int player){
        int oplayer = (player==1) ? 2 : 1;

        int mySC = BoardHelper.getStoneCount(board,player);
        int opSC = BoardHelper.getStoneCount(board,oplayer);

        return mySC - opSC;
    }

    public int evalMobility(int[][] board , int player){
        int oplayer = (player==1) ? 2 : 1;

        int myMoveCount = BoardHelper.getAllPossibleMoves(board,player).size();
        int opMoveCount = BoardHelper.getAllPossibleMoves(board,oplayer).size();

        return myMoveCount - opMoveCount;
    }


}
