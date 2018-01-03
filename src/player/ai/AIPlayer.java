package player.ai;

import game.GamePlayer;

import java.awt.*;

public class AIPlayer extends GamePlayer {

    public AIPlayer(int mark) {
        super(mark);
    }

    @Override
    public boolean isUserPlayer() {
        return false;
    }

    @Override
    public String playerName() {
        return "Minimax AI (Depth 3)";
    }

    @Override
    public Point play(int[][] board) {
        return Minimax.solve(board,myMark,3);
    }
}
