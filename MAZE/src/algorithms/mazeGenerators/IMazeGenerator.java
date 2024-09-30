package algorithms.mazeGenerators;

public interface IMazeGenerator {

    Maze generate(int rows, int cols);
    long measureAlgorithmTimeMillis(int r, int c);
    int[] ValidArgs(int r, int c);
    String getName();
}
