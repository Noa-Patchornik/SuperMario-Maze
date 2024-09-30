package algorithms.mazeGenerators;
import java.util.*;

public class MyMazeGenerator extends  AMazeGenerator {

//    //uses DFS for generating the maze.
//    //took an inspiration from wikipedia (iterative implementation (with stack))
//    public Maze generateFunc(int rows, int cols) {
//        int[] arr = ValidArgs(rows, cols);
//        rows = arr[0];
//        cols = arr[1];
//        Maze ANSWER = null;
//        while (ANSWER == null) {
//            Maze m = new Maze(rows, cols, 1);
//            List<Position> trail = new ArrayList<>();
//            //setting the start and end positions of the maze
//            m.setStartPosition();
//            Position initstate = m.getStartPosition();
//            m.setGoalPosition();
//            Position end = m.getGoalPosition();
//            //mark the start position as visited
//            initstate.setVisited(true);
//            Stack<Position> myStack = new Stack<>();
//            Random myrandom = new Random();
//            //push the start position to the stack
//            myStack.push(initstate);
//            List<Position> neighbors;
//            Position chosen = null;
//            //until there are more positions to go to and the end position isn't found
//            while (!myStack.isEmpty()) {
//                Position currState = myStack.pop();
//                //check if the position is already in the trail, and if not add it to the trail
//                if (!trail.contains(currState))
//                    trail.add(currState);
//                //mark the current position as visited
//                currState.setVisited(true);
//                //get the neighbors of this position, ones that hasn't been visited yet
//                neighbors = currState.GETNEIGHBORS(m);
//                //if there is any neighbor
//                if (!neighbors.isEmpty()) {
//                    //choose one randomly from the list
//                    int a = myrandom.nextInt(neighbors.size());
//                    chosen = neighbors.get(a);
//                    //push it to the stack
//                    myStack.push(chosen);
//                    //if the chosen position is the end position, put 0 in this position, mark as visited and return this maze
//                    if (chosen.getRowIndex() == end.getRowIndex() && chosen.getColumnIndex() == end.getColumnIndex()) {
//                        m.setMaze(chosen.getRowIndex(), chosen.getColumnIndex(), 0);
//                        m.setMaze(currState.getRowIndex(), currState.getColumnIndex(), 0);
//                        chosen.setVisited(true);
//                        ANSWER = m;
//                        break;
//                    }
//                    //if it's not the end position, put 0 and mark as visited
//                    m.setMaze(chosen.getRowIndex(), chosen.getColumnIndex(), 0);
//                    m.setMaze(currState.getRowIndex(), currState.getColumnIndex(), 0);
//                    chosen.setVisited(true);
//                }
//                //if there is no neighbor that hasn't visited
//                else {
//                    // Check if there are at least 3 positions to backtrack
//                    if (trail.size() > 3) {
//                        Position backtrackState = trail.get(trail.size() - 2);
//                        // Check if the backtracked position has any valid neighbors left
//                        if (!backtrackState.GETNEIGHBORS(m).isEmpty()) {
//                            myStack.push(backtrackState);
//                            trail.remove(trail.size() - 1);  // Remove current position to avoid loops
//                        } else {
//                            // If no valid neighbors, pop another position further back
//                            if (trail.size() > 4) {
//                                myStack.push(trail.get(trail.size() - 4));  // Go back further
//                                trail.remove(trail.size() - 1);
//                            } else {
//                                ANSWER = null;  // If we can't backtrack any further, reset and retry
//                            }
//                        }
//                    }
//                    else if (trail.size() <= 1) {
//                        ANSWER = null;  // If there aren't enough steps to backtrack, retry
//                    }
//                }
//
//            }
//            //when the stack is empty, and the chosen position is not the end one, return null, else return the maze
//            if (chosen != null) {
//                if (!(chosen.getRowIndex() == end.getRowIndex() && chosen.getColumnIndex() == end.getColumnIndex())) {
//                    ANSWER = null;
//                } else {
//                    ANSWER = m;
//                    break;
//                }
//            }
//        }
//        return ANSWER;
//    }

    @Override
    public Maze generate(int rows, int cols) {
        return generateFunc(rows, cols);
    }

    @Override
    public String getName() {
        return "My Maze";
    }
}