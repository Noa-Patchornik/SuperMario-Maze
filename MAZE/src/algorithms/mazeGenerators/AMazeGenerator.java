package algorithms.mazeGenerators;

import algorithms.search.SearchableMaze;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class AMazeGenerator implements IMazeGenerator {

    private static final int WALL = 1;
    private static final int PATH = 0;

    @Override
    //measure the time of generating one maze
    public long measureAlgorithmTimeMillis(int r, int c) {
        long a = System.currentTimeMillis();
        Maze m = generate(r, c);
        long b=0;
        boolean problem=true;
        while (problem) {
            if (m != null) {
                b = System.currentTimeMillis();
                problem=false;
            } else {
                m = generate(r, c);
            }
        }
        return b - a;
    }

    @Override
    //checks if the size of the requested maze is valid
    public int[] ValidArgs(int r, int c){
        //if args not valid (negative or small numbers) make a default maze size(10);
        int [] arr = new int[2];
        if(r<2){
            arr[0]=10;
        }
        else{
            arr[0]=r;
        }
        if(c<2){
            arr[1]=10;
        }
        else{
            arr[1]=c;
        }
        return arr;
    }

    //generate the maze, using Prim algorithm
    public Maze generateFunc(int rows, int cols) {
        int[] arr = ValidArgs(rows, cols);
        rows = arr[0];
        cols = arr[1];

        Maze maze = new Maze(rows, cols, 1);  // Start with all walls (1)
        // Set of walls that can be removed to form the maze
        List<Position> wallList = new ArrayList<>();
        Random random = new Random();

        //Choose a random starting point
        maze.setStartPosition();
        Position start = maze.getStartPosition();

        // Set a random end position on the border, making sure it is different from the start
        maze.setGoalPosition();
        Position end = maze.getGoalPosition();

        //Mark the start position as a path
        maze.setMaze(start.getRowIndex(), start.getColumnIndex(), 0);

        //Add the walls around the start position to the wall list
        addNeighboringWalls(maze, wallList, start);

        //Continue until there are no more walls in the list
        while (!wallList.isEmpty()) {
            // Choose a random wall from the list
            int randIndex = random.nextInt(wallList.size());
            Position wall = wallList.get(randIndex);
            wallList.remove(randIndex);

            // Check if the wall can be turned into a path
            if (canBeOpened(maze, wall)) {
                maze.setMaze(wall.getRowIndex(), wall.getColumnIndex(), 0); // Open the wall
                addNeighboringWalls(maze, wallList, wall); // Add new walls adjacent to the opened path

                // Stop if we've reached the goal position
                if (wall.equals(end)) {
                    maze.setMaze(end.getRowIndex(), end.getColumnIndex(), 0); // Ensure the end is a path
                    break; // Stop the algorithm once the goal is reached
                }
            }
        }
        return maze;
    }

    // Add walls around the given cell (position) to the wall list
    public void addNeighboringWalls(Maze maze, List<Position> wallList, Position pos) {
        int row = pos.getRowIndex();
        int col = pos.getColumnIndex();
        // Add valid walls to the list (must be within bounds and still a wall)
        if (isValidWall(maze, row - 1, col))
            wallList.add(new Position(row - 1, col));  // Top
        if (isValidWall(maze, row + 1, col))
            wallList.add(new Position(row + 1, col));  // Bottom
        if (isValidWall(maze, row, col - 1))
            wallList.add(new Position(row, col - 1));  // Left
        if (isValidWall(maze, row, col + 1))
            wallList.add(new Position(row, col + 1));  // Right
    }

    //Check if a wall can be opened (i.e., separates a visited and unvisited cell)
    public boolean canBeOpened(Maze maze, Position wall) {
        int row = wall.getRowIndex();
        int col = wall.getColumnIndex();
        int visitedCount = 0;
        //Check how many neighboring cells are paths (visited cells)
        if (isPath(maze, row - 1, col))
            visitedCount++;  // Top
        if (isPath(maze, row + 1, col))
            visitedCount++;  // Bottom
        if (isPath(maze, row, col - 1))
            visitedCount++;  // Left
        if (isPath(maze, row, col + 1))
            visitedCount++;  // Right

        //Open the wall if it separates a path from an unvisited cell
        return visitedCount == 1;  // Only 1 neighbor should be a path
    }

    // Check if a position is a valid wall to add to the wall list
    public boolean isValidWall(Maze maze, int row, int col) {
        //checks if the cell is in the boundaries of the maze and the cell represent wall by 1
        return (maze.ValidRCell(row) && maze.ValidCCell(col) && maze.getMaze()[row][col] == WALL);
    }

    // Check if a position is a path (i.e., part of the maze)
    public boolean isPath(Maze maze, int row, int col) {
        //checks if the cell is in the boundaries of the maze and the cell represent path by 0
        return (maze.ValidRCell(row) && maze.ValidCCell(col) && maze.getMaze()[row][col] == PATH);
    }
}
