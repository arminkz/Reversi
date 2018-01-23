package player.ai;

import game.GamePlayer;

import java.awt.*;

public class AIPlayerStatic extends GamePlayer {

    private int searchDepth;
    private Evaluator evaluator;

    public AIPlayerStatic(int mark, int depth) {
        super(mark);
        searchDepth = depth;
        evaluator = new StaticEvaluator();
    }

    @Override
    public boolean isUserPlayer() {
        return false;
    }

    @Override
    public String playerName() {
        return "Static AI (Depth " + searchDepth + ")";
    }

    @Override
    public Point play(int[][] board) {
        return Minimax.solve(board,myMark,searchDepth,evaluator);
    }
}
