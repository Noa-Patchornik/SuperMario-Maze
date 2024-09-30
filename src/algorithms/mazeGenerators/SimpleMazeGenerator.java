package algorithms.mazeGenerators;

import algorithms.search.AState;
import algorithms.search.DepthFirstSearch;
import algorithms.search.SearchableMaze;

import java.util.*;

public class SimpleMazeGenerator extends AMazeGenerator {

    @Override
    public String getName() {
        return "Simple Maze";
    }

//    public Maze generateM(int rows, int cols) {
//        int [] arr = ValidArgs(rows, cols);
//        rows=arr[0];
//        cols=arr[1];
//        Maze m =null;
//        while(m==null) {
//            m = new Maze(rows, cols,1);
//            Random myrandom = new Random();
//            //set start and end position to the maze
//            m.setStartPosition();
//            m.setGoalPosition();
//            m.getStartPosition().setSuc(null);
//            Position tmp = m.getStartPosition();
//            //put 0 in the start position
//            m.setMaze(tmp.getRowIndex(), tmp.getColumnIndex(), 0);
//            List<Position> neighbors;
//            boolean flag;
//            int k;
//            //as long as we didn't get to the end position
//            while (!(tmp.getRowIndex() == m.getGoalPosition().getRowIndex() && tmp.getColumnIndex() == m.getGoalPosition().getColumnIndex())) {
//                flag=false;
//                //get the neighbors of the current position
//                neighbors = tmp.GETNEIGHBORS(m);
//                //go through all the neighbors
//                for (int i = 0; i < neighbors.size(); i++) {
//                    //if the cell is 1, set the successor of that neighbor as the current position, change the flag to true
//                    if (m.getMaze()[neighbors.get(i).getRowIndex()][neighbors.get(i).getColumnIndex()] != 0) {
//                        neighbors.get(i).setSuc(tmp);
//                        flag = true;
//                        break;
//                    }
//                }
//                //if the flag is false, start over
//                if (!flag) {
//                    m = null;
//                    break;
//                }
//                //go through all the neighbors, if the neighbor is the end position, set the successor as the current position
//                //and return the maze
//                for (int i = 0; i < neighbors.size(); i++) {
//                    if (neighbors.get(i).getColumnIndex() == m.getGoalPosition().getColumnIndex() && neighbors.get(i).getRowIndex() == m.getGoalPosition().getRowIndex()) {
//                        neighbors.get(i).setSuc(tmp);
//                        return m;
//                    }
//                    //choose randomly a neighbor
//                    k = myrandom.nextInt(neighbors.size());
//                    int k2=k;
//                    while(k2==k) {
//                        //if the chosen neighbor is already 0, choose another neighbor
//                        if (m.getMaze()[neighbors.get(k).getRowIndex()][neighbors.get(k).getColumnIndex()] == 0) {
//                            k = myrandom.nextInt(neighbors.size());
//                            //if the neighbor is not the same as before, check if the cell is 0, if the cell is 0 change the number k
//                            //if the cell is 1 break the loop
//                            if (k != k2){
//                                if(m.getMaze()[neighbors.get(k).getRowIndex()][neighbors.get(k).getColumnIndex()] == 0)
//                                    k=k2;
//                                else{
//                                    break;
//                                }
//                            }
//                        }
//                        //if the cell is 1 break the loop
//                        else if (m.getMaze()[neighbors.get(k).getRowIndex()][neighbors.get(k).getColumnIndex()] == 1){
//                            break;
//                        }
//                    }
//                    //set the cell as 0
//                    m.setMaze(neighbors.get(k).getRowIndex(), neighbors.get(k).getColumnIndex(), 0);
//                    //if the position is the end set the successor, get the neighbor from the list and break the loop of the neighbors
//                    if(!(tmp.getRowIndex() == m.getStartPosition().getRowIndex() && tmp.getColumnIndex()==m.getStartPosition().getColumnIndex())) //&&
//                        neighbors.get(k).setSuc(tmp);
//                    tmp = neighbors.get(k);
//                    break;
//                }
//            }
//        }
//        return m;
//    }

    @Override
    public Maze generate(int rows, int cols) {
        Maze m = generateFunc(rows, cols);
        Random myrandom = new Random();
        //randomly add 0 to the maze after there is a path from the start to the end
        for(int i=0; i<m.rows; i++){
            for(int j=0; j<m.cols; j++){
                m.getMaze()[i][j] = myrandom.nextInt(2);
            }
        }
        return m;
    }
}
