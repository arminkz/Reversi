package player.ai;

import game.BoardHelper;
import game.GamePlayer;

import java.awt.*;
import java.util.ArrayList;

public class AIPlayerRealtimeKiller extends GamePlayer {

    private int searchDepth;
    private Evaluator evaluator;
    private boolean isFirstPlayer;

    private ArrayList<Point> moveHistory;
    private int[][] lastboard;
    private boolean firstRun = true;

    OpeningBook OB;
    private boolean isOpeningActive = true;

    public AIPlayerRealtimeKiller(int mark, int depth, boolean firstplayer) {
        super(mark);

        //init OpeningBook
        OB = new OpeningBook();
        OB.initOpening();

        moveHistory = new ArrayList<>();

        searchDepth = depth;
        isFirstPlayer = firstplayer;

        if(mark==1) {
            evaluator = new RealtimeEvaluator(new int[][] {
                    {8, 85, -40, 10, 210, 520},
                    {8, 85, -40, 10, 210, 520},
                    {33, -50, -15, 4, 416, 2153},
                    {46, -50, -1, 3, 612, 4141},
                    {51, -50, 62, 3, 595, 3184},
                    {33, -5,  66, 2, 384, 2777},
                    {44, 50, 163, 0, 443, 2568},
                    {13, 50, 66, 0, 121, 986},
                    {4, 50, 31, 0, 27, 192},
                    {8, 500, 77, 0, 36, 299}},
                    new int[] {0, 55, 56, 57, 58, 59, 60, 61, 62, 63});
        }else{
            evaluator = new RealtimeEvaluator(new int[][] {
                    {8, 85, -40, 10, 210, 520},
                    {8, 85, -40, 10, 210, 520},
                    {33, -50, -15, 4, 416, 2153},
                    {46, -50, -1, 3, 612, 4141},
                    {51, -50, 62, 3, 595, 3184},
                    {33, -5,  66, 2, 384, 2777},
                    {44, 50, 163, 0, 443, 2568},
                    {13, 50, 66, 0, 121, 986},
                    {4, 50, 31, 0, 27, 192},
                    {8, 500, 77, 0, 36, 299}},
                    new int[] {0, 55, 56, 57, 58, 59, 60, 61, 62, 63});
        }
    }

    @Override
    public boolean isUserPlayer() {
        return false;
    }

    @Override
    public String playerName() {
        return "Realtime Killer (Depth " + searchDepth + ")";
    }

    @Override
    public Point play(int[][] board) {

        //Add Opponents Move to History (null means opponent was not able to play)
        //Opening loses effect when Move Sequence is out of 1-1 sync
        if(firstRun){
            if(!isFirstPlayer){
                Point opMove = BoardHelper.getMove(BoardHelper.getStartBoard(),board);
                if(opMove != null) moveHistory.add(opMove);
                else isOpeningActive = false;
            }
            firstRun = false;
        }else{
            Point opMove = BoardHelper.getMove(lastboard,board);
            if(opMove != null) moveHistory.add(opMove);
            else isOpeningActive = false;
        }

        Point myMove = playUtil(board);
        lastboard = BoardHelper.getNewBoardAfterMove(board,myMove,myMark);
        moveHistory.add(myMove);

        //print history till now
        /*for(Point m : moveHistory){
            System.out.print("(" + m.x + "," + m.y + ") -> ");
        }*/

        return myMove;
    }

    public Point playUtil(int[][] board) {
        ArrayList<Point> moves = BoardHelper.getAllPossibleMoves(board,myMark);
        int opMark = ((myMark == 1) ? 2 : 1);

        Point bestToPlay = null;
        int bestValue = Integer.MIN_VALUE;

        //Corner Detection
        ArrayList<Point> corners = new ArrayList<>();
        corners.add(new Point(0,0));
        corners.add(new Point(0,7));
        corners.add(new Point(7,0));
        corners.add(new Point(7,7));
        for(Point move : moves){
            for(Point corner : corners){
                if(corner.equals(move)){
                    int mval = evaluator.eval(BoardHelper.getNewBoardAfterMove(board,move,myMark),myMark);
                    if(mval > bestValue) {
                        //update best corner
                        bestToPlay = move;
                        bestValue = mval;
                    }
                }
            }
        }
        if(bestToPlay != null){
            System.out.println("\033[1;30;34m KILLER MOVE : CORNER \033[0m\n");
            return bestToPlay;
        }

        bestToPlay = null;
        bestValue = Integer.MIN_VALUE;

        //Blocking Move Detection
        for(Point move : moves){
            int[][] resBoard = BoardHelper.getNewBoardAfterMove(board,move,myMark);
            if(BoardHelper.getAllPossibleMoves(resBoard,opMark).size() == 0){ //if opponent has no moves
                int mval = evaluator.eval(resBoard,myMark);
                if(mval > bestValue) {
                    //update best corner
                    bestToPlay = move;
                    bestValue = mval;
                }
            }
        }
        if(bestToPlay != null){
            System.out.println("\033[1;30;34m KILLER MOVE : BLOCKING MOVE \033[0m\n");
            return bestToPlay;
        }

        //Opening Moves (if fails then stop opening)
        if(isOpeningActive) {
            Point opmove = OB.getMoveFromOpeningBook(moveHistory);
            if (opmove != null) {
                System.out.println("\033[1;30;34m OPENING MOVE \033[0m\n");
                return opmove;
            }
            isOpeningActive = false;
            System.out.println("\033[1;30;44m OPENING DEACTIVATED \033[0m\n");
        }

        //if no killer moves availiable do a minimax search
        return Minimax.solve(board,myMark,searchDepth,evaluator);
    }
}
