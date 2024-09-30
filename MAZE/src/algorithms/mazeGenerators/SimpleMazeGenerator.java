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
