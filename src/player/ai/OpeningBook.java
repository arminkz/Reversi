package player.ai;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class OpeningBook {

    private Random rnd = new Random();

    String[] openings = {
            "C4e3F6e6F5g6E7c5",
            "C4e3F6e6F5g6",
            "C4e3F6e6F5c5F4g6F7g5",
            "C4e3F6e6F5c5F4g6F7d3",
            "C4e3F6e6F5c5F4g6F7",
            "C4e3F6e6F5c5F4g5G4f3C6d3D6b3C3b4E2b6",
            "C4e3F6e6F5c5F4g5G4f3C6d3D6",
            "C4e3F6e6F5c5D6",
            "C4e3F6e6F5c5D3",
            "C4e3F6e6F5c5C3g5",
            "C4e3F6e6F5c5C3c6D6",
            "C4e3F6e6F5c5C3c6D3d2E2b3C1c2B4a3A5b5A6a4A2",
            "C4e3F6e6F5c5C3c6",
            "C4e3F6e6F5c5C3b4D6c6B5a6B6c7",
            "C4e3F6e6F5c5C3b4",
            "C4e3F6e6F5c5C3",
            "C4e3F6e6F5",
            "C4e3F6b4",
            "C4e3F5e6F4c5D6c6F7g5G6",
            "C4e3F5e6F4c5D6c6F7f3",
            "C4e3F5e6F4",
            "C4e3F5e6D3",
            "C4e3F5b4F3f4E2e6G5f6D6c6",
            "C4e3F5b4F3",
            "C4e3F5b4",
            "C4e3F4c5E6",
            "C4e3F4c5D6f3E6c6",
            "C4e3F4c5D6f3E6c3D3e2D2",
            "C4e3F4c5D6f3E6c3D3e2B6f5G5f6",
            "C4e3F4c5D6f3E6c3D3e2B6f5G5",
            "C4e3F4c5D6f3E6c3D3e2B6f5B4f6G5d7",
            "C4e3F4c5D6f3E6c3D3e2B6f5",
            "C4e3F4c5D6f3E6c3D3e2B5f5B4f6C2e7D2c7",
            "C4e3F4c5D6f3E6c3D3e2B5f5B3",
            "C4e3F4c5D6f3E6c3D3e2B5f5",
            "C4e3F4c5D6f3E6c3D3e2B5",
            "C4e3F4c5D6f3E6c3D3e2",
            "C4e3F4c5D6f3E2",
            "C4e3F4c5D6f3D3c3",
            "C4e3F4c5D6f3D3",
            "C4e3F4c5D6f3C6",
            "C4e3F4c5D6e6",
            "C4e3",
            "C4c5",
            "C4c3F5c5",
            "C4c3E6c5",
            "C4c3D3c5F6f5",
            "C4c3D3c5F6e3C6f5F4g5",
            "C4c3D3c5F6e2C6",
            "C4c3D3c5F6",
            "C4c3D3c5D6f4F5e6F6",
            "C4c3D3c5D6f4F5e6C6d7",
            "C4c3D3c5D6f4F5d2G4d7",
            "C4c3D3c5D6f4F5d2B5",
            "C4c3D3c5D6f4F5d2",
            "C4c3D3c5D6f4F5",
            "C4c3D3c5D6f4B4e3B3",
            "C4c3D3c5D6f4B4c6B5b3B6e3C2a4A5a6D2",
            "C4c3D3c5D6f4B4b6B5c6F5",
            "C4c3D3c5D6f4B4b6B5c6B3",
            "C4c3D3c5D6f4B4",
            "C4c3D3c5D6e3",
            "C4c3D3c5D6",
            "C4c3D3c5B6e3",
            "C4c3D3c5B6c6B5",
            "C4c3D3c5B6",
            "C4c3D3c5B5",
            "C4c3D3c5B4e3",
            "C4c3D3c5B4d2E2",
            "C4c3D3c5B4d2D6",
            "C4c3D3c5B4d2C2f4D6c6F5e6F7",
            "C4c3D3c5B4",
            "C4c3D3c5B3f4B5b4C6d6F5",
            "C4c3D3c5B3f3",
            "C4c3D3c5B3",
            "C4c3D3c5B2",
            "C4c3"
        };

        public ArrayList<ArrayList<Point>> openingList;

        public void initOpening(){
            openingList = new ArrayList<>();
            for(String seq : openings){
                String oseq = seq.toLowerCase();
                ArrayList<Point> moveSequence = new ArrayList<>();
                for (int i = 0; i < oseq.length(); i+=2) {
                    char c1 = oseq.charAt(i);
                    char c2 = oseq.charAt(i+1);
                    int col = c1 - 'a';
                    int row = c2 - '1';
                    System.out.print(row + " , " + col + "  ");
                    moveSequence.add(new Point(row,col));
                }
                System.out.println("");
                openingList.add(moveSequence);
            }
        }

        public Point getMoveFromOpeningBook(ArrayList<Point> history){
            ArrayList<ArrayList<Point>> expiredSequences = new ArrayList<>();
            ArrayList<Point> availableMoves = new ArrayList<>();
            System.out.println("Checking with " + openingList.size() + " Opening Sequences ..." );
            for(ArrayList<Point> sequence : openingList){
                if(sequence.size() <= history.size()){
                    //sequence is no more used and considered expired
                    expiredSequences.add(sequence);
                    continue;
                }
                int i;
                boolean isMatch = true;
                for (i = 0; i < history.size(); i++) {
                    if(!sequence.get(i).equals(history.get(i))){
                        //mismatch happened ! expire the sequence
                        isMatch = false;
                        expiredSequences.add(sequence);
                        break;
                    }
                }
                if(isMatch){
                    availableMoves.add(sequence.get(i));
                }
            }
            //dispose expired sequences
            openingList.removeAll(expiredSequences);

            if(availableMoves.size()>0) {
                //choose randomly between available moves
                return availableMoves.get(rnd.nextInt(availableMoves.size()));
            }else return null;
        }

}
