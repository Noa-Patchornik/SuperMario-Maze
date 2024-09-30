package algorithms.mazeGenerators;
import java.util.*;

public class MyMazeGenerator extends  AMazeGenerator {

    @Override
    public Maze generate(int rows, int cols) {
        return generateFunc(rows, cols);
    }

    @Override
    public String getName() {
        return "My Maze";
    }
}