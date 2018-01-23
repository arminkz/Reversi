package game;

import java.awt.*;
import java.util.ArrayList;

public class BoardHelper {

    public static boolean isGameFinished(int[][] board){
       return !(hasAnyMoves(board,1) || hasAnyMoves(board,2));
    }


    public static int[][] getStartBoard(){
        int[][] b = new int[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                b[i][j] = 0;
            }
        }
        b[3][3] = 2;
        b[3][4] = 1;
        b[4][3] = 1;
        b[4][4] = 2;
        return b;
    }

    public static Point getMove(int[][] before , int[][] after){
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if(before[i][j]==0 && after[i][j]!=0){
                    return new Point(i,j);
                }
            }
        }
        return null;
    }

    public static int getWinner(int[][] board){
        if(!isGameFinished(board))
            //game not finished
            return -1;
        else{
            //count stones
            int p1s = getPlayerStoneCount(board,1);
            int p2s = getPlayerStoneCount(board,2);

            if(p1s == p2s){
                //tie
                return 0;
            }else if(p1s > p2s){
                //p1 wins
                return 1;
            }else{
                //p2 wins
                return 2;
            }
        }
    }

    public static int getTotalStoneCount(int[][] board){
        int c = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if(board[i][j] != 0) c++;
            }
        }
        return c;
    }

    public static int getPlayerStoneCount(int[][] board, int player){
        int score = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if(board[i][j] == player) score++;
            }
        }
        return score;
    }


    public static boolean hasAnyMoves(int[][] board, int player){
        return getAllPossibleMoves(board,player).size() > 0;
    }

    public static ArrayList<Point> getAllPossibleMoves(int[][] board, int player){
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

    public static int[][] getNewBoardAfterMove(int[][] board, Point move , int player){
        //get clone of old board
        int[][] newboard = new int[8][8];
        for (int k = 0; k < 8; k++) {
            for (int l = 0; l < 8; l++) {
                newboard[k][l] = board[k][l];
            }
        }

        //place piece
        newboard[move.x][move.y] = player;
        //reverse pieces
        ArrayList<Point> rev = BoardHelper.getReversePoints(newboard,player,move.x,move.y);
        for(Point pt : rev){
            newboard[pt.x][pt.y] = player;
        }

        return newboard;
    }

    public static ArrayList<Point> getStableDisks(int[][] board,int player,int i,int j){

        ArrayList<Point> stableDiscs = new ArrayList<>();

        int mi , mj;
        int oplayer = ((player == 1) ? 2 : 1);

        //move up
        ArrayList<Point> mupts = new ArrayList<>();
        mi = i - 1;
        mj = j;
        while(mi>0 && board[mi][mj] == player){
            mupts.add(new Point(mi,mj));
            mi--;
        }
        for(Point sd : mupts){
            boolean redundant = false;
            for(Point stableDisc : stableDiscs){
                if(sd.equals(stableDisc)){
                    redundant = true;
                    break;
                }
            }
            if(!redundant) stableDiscs.add(sd);
        }

        //move down
        ArrayList<Point> mdpts = new ArrayList<>();
        mi = i + 1;
        mj = j;
        while(mi<7 && board[mi][mj] == oplayer){
            mdpts.add(new Point(mi,mj));
            mi++;
        }
        for(Point sd : mdpts){
            boolean redundant = false;
            for(Point stableDisc : stableDiscs){
                if(sd.equals(stableDisc)){
                    redundant = true;
                    break;
                }
            }
            if(!redundant) stableDiscs.add(sd);
        }

        //move left
        ArrayList<Point> mlpts = new ArrayList<>();
        mi = i;
        mj = j - 1;
        while(mj>0 && board[mi][mj] == oplayer){
            mlpts.add(new Point(mi,mj));
            mj--;
        }
        for(Point sd : mlpts){
            boolean redundant = false;
            for(Point stableDisc : stableDiscs){
                if(sd.equals(stableDisc)){
                    redundant = true;
                    break;
                }
            }
            if(!redundant) stableDiscs.add(sd);
        }

        //move right
        ArrayList<Point> mrpts = new ArrayList<>();
        mi = i;
        mj = j + 1;
        while(mj<7 && board[mi][mj] == oplayer){
            mrpts.add(new Point(mi,mj));
            mj++;
        }
        for(Point sd : mrpts){
            boolean redundant = false;
            for(Point stableDisc : stableDiscs){
                if(sd.equals(stableDisc)){
                    redundant = true;
                    break;
                }
            }
            if(!redundant) stableDiscs.add(sd);
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
        for(Point sd : mulpts){
            boolean redundant = false;
            for(Point stableDisc : stableDiscs){
                if(sd.equals(stableDisc)){
                    redundant = true;
                    break;
                }
            }
            if(!redundant) stableDiscs.add(sd);
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
        for(Point sd : murpts){
            boolean redundant = false;
            for(Point stableDisc : stableDiscs){
                if(sd.equals(stableDisc)){
                    redundant = true;
                    break;
                }
            }
            if(!redundant) stableDiscs.add(sd);
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
        for(Point sd : mdlpts){
            boolean redundant = false;
            for(Point stableDisc : stableDiscs){
                if(sd.equals(stableDisc)){
                    redundant = true;
                    break;
                }
            }
            if(!redundant) stableDiscs.add(sd);
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
        for(Point sd : mdrpts){
            boolean redundant = false;
            for(Point stableDisc : stableDiscs){
                if(sd.equals(stableDisc)){
                    redundant = true;
                    break;
                }
            }
            if(!redundant) stableDiscs.add(sd);
        }

        return stableDiscs;
    }


    public static ArrayList<Point> getFrontierSquares(int[][] board,int player){

        ArrayList<Point> frontiers = new ArrayList<>();

        int oplayer = ((player == 1) ? 2 : 1);

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if(board[i][j]==oplayer){

                    ArrayList<Point> possiblefrontiers = new ArrayList<>();

                    //check 8 directions

                    //up
                    if(i>0 && board[i-1][j]==0) possiblefrontiers.add(new Point(i-1,j));
                    //down
                    if(i<7 && board[i+1][j]==0) possiblefrontiers.add(new Point(i+1,j));
                    //right
                    if(j<7 && board[i][j+1]==0) possiblefrontiers.add(new Point(i,j+1));
                    //left
                    if(j>0 && board[i][j-1]==0) possiblefrontiers.add(new Point(i,j-1));
                    //up-left
                    if(i>0 && j>0 && board[i-1][j-1]==0) possiblefrontiers.add(new Point(i-1,j-1));
                    //up-right
                    if(i>0 && j<7 && board[i-1][j+1]==0) possiblefrontiers.add(new Point(i-1,j+1));
                    //down-left
                    if(i<7 && j>0 && board[i+1][j-1]==0) possiblefrontiers.add(new Point(i+1,j-1));
                    //down-right
                    if(i<7 && j<7 && board[i+1][j+1]==0) possiblefrontiers.add(new Point(i+1,j+1));

                    //remove duplicates
                    for(Point pf : possiblefrontiers){
                        boolean redundant = false;
                        for(Point f : frontiers){
                            if(f.equals(pf)){
                                redundant = true;
                                break;
                            }
                        }
                        if(!redundant) frontiers.add(pf);
                    }
                }
            }
        }

        return frontiers;
    }

}
