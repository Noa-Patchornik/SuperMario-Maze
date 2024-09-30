package algorithms.mazeGenerators;

public class EmptyMazeGenerator extends AMazeGenerator {

    @Override
    public Maze generate(int rows, int cols) {
        int [] arr = ValidArgs(rows, cols);
        rows=arr[0];
        cols=arr[1];
        Maze m =  new Maze(rows, cols);
        for(int i=0;i<rows;i++){
            for(int j=0;j<cols;j++){
                m.setMaze(i,j,0);
            }
        }
        m.setStartPosition();
        m.setGoalPosition();
        return m;
    }

    @Override
    public String getName() {
        return "Empty Maze";
    }
}
