package player.ai;

import game.BoardHelper;

public class Evaluator {


    public static int eval(int[][] board , int player){

        int oplayer = (player==1) ? 2 : 1;

        //Player Stone Count
        int mySC = BoardHelper.getStoneCount(board,player);
        int opSC = BoardHelper.getStoneCount(board,oplayer);

        //
        return mySC - opSC;
    }


}
