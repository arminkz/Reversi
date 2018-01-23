package player.ai;

import game.BoardHelper;

import static player.ai.StaticEvaluator.*;

public class DynamicEvaluator implements Evaluator {

    //Evaluation Function Changes during Early-Game / Mid-Game / Late-Game
    enum GamePhase {
        EARLY_GAME,
        MID_GAME,
        LATE_GAME
    }

    private GamePhase getGamePhase(int[][] board){
        int sc = BoardHelper.getTotalStoneCount(board);
        if(sc<20) return GamePhase.EARLY_GAME;
        else if(sc<=58) return GamePhase.MID_GAME;
        else return GamePhase.LATE_GAME;
    }

    public int eval(int[][] board , int player){

        //terminal
        if(BoardHelper.isGameFinished(board)){
            return 1000*evalDiscDiff(board, player);
        }

        //semi-terminal
        switch (getGamePhase(board)){
            case EARLY_GAME:
                return 1000*evalCorner(board,player) + 50*evalMobility(board,player);
            case MID_GAME:
                return 1000*evalCorner(board,player) + 20*evalMobility(board,player) + 10*evalDiscDiff(board, player) + 100*evalParity(board);
            case LATE_GAME:
            default:
                return 1000*evalCorner(board,player) + 100*evalMobility(board,player) + 500*evalDiscDiff(board, player) + 500*evalParity(board);
        }
    }

}
