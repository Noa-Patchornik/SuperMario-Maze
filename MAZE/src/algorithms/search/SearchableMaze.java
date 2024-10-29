package algorithms.search;

import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.Position;

import java.util.ArrayList;
import java.util.List;

public class SearchableMaze implements ISearchable {

    private Maze origionMaze; //the origin maze
    private MazeState StartState; //start position in the maze
    private MazeState EndState; // end position in the maze
    public int[][] isVisited; //matrix that represent if the cell is visited

    public SearchableMaze(Maze m){
        this.origionMaze=m;
        StartState = new MazeState(m.getStartPosition().getRowIndex(),m.getStartPosition().getColumnIndex());
        EndState= new MazeState(m.getGoalPosition().getRowIndex(),m.getGoalPosition().getColumnIndex());
        isVisited=new int[m.getRows()][m.getCols()];
    }

    @Override
    public MazeState getInitState() {
        return this.StartState;
    }

    @Override
    public MazeState getGoalState() {
        return this.EndState;
    }

    @Override
    public void setGoal(MazeState n) {
        if(n!=null){
            this.EndState = n;
        }
    }

    /**
     * The function return all the possible moves from a current state up, down, right, left and diagonal
     * @param curr - the state the maze is at
     * @return the list of the possible moves from the state
     */
    public List<AState> getAllPossibleStates(AState curr){
        List<AState> ans;
        MazeState c = (MazeState) curr;
        ans = isOkNotDiagonal(c); // get all the state not diagonal: up,down, right, left
        List<AState> ansDiagonal = isOkDiagonal(c); // get all the diagonal states
        //add the diagonal states to the list of no diagonal
        for(int i=0; i<ansDiagonal.size(); i++){
            ans.add(ansDiagonal.get(i));
        }
        //return the complete list of moves include diagonal
        return ans;
    }

    /**
     * The function return the diagonal possible moves from the state
     * @param c - the current state in the maze
     * @return - the list of the diagonal moves
     */
    private List<AState> isOkDiagonal(MazeState c) {
        List<AState> diagonalstate = new ArrayList<>();
        int row= c.getR();
        int col = c.getC();
        MazeState tmp;
        /** up left */
        //checks if the move is not out of the bounds of the maze, if the cell is 0 (not wall) and if the cell isn't visited
        if(isLegalMove(row-1,col-1) && getCellMaze(row-1,col-1) && this.isVisited[row-1][col-1]==0){
            //checking if the cells next to the current are 0 too so there would be a way in diagonal (up or left)
            if(getCellMaze(row-1,col) || getCellMaze(row,col-1)){
                //checks if the cells next to current wasn't visited already
                if(this.isVisited[row-1][col]==0 || this.isVisited[row][col-1]==0){
                    //making a new state, set the cost of diagonal as 15 and add to the list
                    tmp = new MazeState(row-1,col-1);
                    tmp.setCost(15);
                    diagonalstate.add(tmp);
                    //remark the cell as visited
                    this.isVisited[row - 1][col-1] = 1;
                }
            }
        }
        /** down right */
        //checks if the move is not out of the bounds of the maze, if the cell is 0 (not wall) and if the cell isn't visited
        if(isLegalMove(row+1,col+1) && getCellMaze(row+1,col+1) && this.isVisited[row+1][col+1]==0){
            //checking if the cells next to the current are 0 too so there would be a way in diagonal
            if(getCellMaze(row+1,col) || getCellMaze(row,col+1)){
                //checks if the cells next to current wasn't visited already
                if(this.isVisited[row+1][col]==0 || this.isVisited[row][col+1]==0){
                    //making new state, set the cost of diagonal as 15 and add to the list
                    tmp = new MazeState(row+1,col+1);
                    tmp.setCost(15);
                    diagonalstate.add(tmp);
                    //remark the cell as visited
                    this.isVisited[row + 1][col+1] = 1;
                }
            }
        }
        /** up right */
        //checks if the move is not out of the bounds of the maze, if the cell is 0 (not wall) and if the cell isn't visited
        if(isLegalMove(row-1,col+1) && getCellMaze(row-1,col+1) && this.isVisited[row-1][col+1]==0){
            //checking if the cells next to the current are 0 too so there would be a way in diagonal
            if(getCellMaze(row-1,col) || getCellMaze(row,col+1)){
                //checks if the cells next to current wasn't visited already
                if(this.isVisited[row-1][col]==0 || this.isVisited[row][col+1]==0){
                    //making a new state, set the cost of diagonal as 15 and add to the list
                    tmp = new MazeState(row-1,col+1);
                    tmp.setCost(15);
                    diagonalstate.add(tmp);
                    //remark the cell as visited
                    this.isVisited[row - 1][col+1] = 1;
                }
            }
        }
        /** down left */
        //checks if the move is not out of the bounds of the maze, if the cell is 0 (not wall) and if the cell isn't visited
        if(isLegalMove(row+1,col-1) && getCellMaze(row+1,col-1) && this.isVisited[row+1][col-1]==0){
            //checking if the cells next to the current are 0 too so there would be a way in diagonal
            if(getCellMaze(row+1,col) || getCellMaze(row,col-1)){
                //checks if the cells next to current wasn't visited already
                if(this.isVisited[row+1][col]==0 || this.isVisited[row][col-1]==0){
                    //making a new state, set the cost of diagonal as 15 and add to the list
                    tmp = new MazeState(row+1,col-1);
                    tmp.setCost(15);
                    diagonalstate.add(tmp);
                    //remark the cell as visited
                    this.isVisited[row + 1][col-1] = 1;
                }
            }
        }
        //the list of the diagonal states
        return diagonalstate;
    }

    //return true if the cell of the maze is not wall. meaning 0 or the start point or the end point, else return false
    public boolean getCellMaze(int row, int col) {
        if(origionMaze.getCellMaze(row,col)==0 || origionMaze.getCellMaze(row,col)==2 ||origionMaze.getCellMaze(row,col)==3)
            return true;
        return false;
    }

    /**
     * The function return all the NOT diagonal moves that is legal from the current one, up,down,right,left
     * @param curr - the current state in the maze
     * @return - the list of all the not diagonal possible moves
     */
    public List<AState> isOkNotDiagonal(AState curr){
        MazeState c = (MazeState) curr;
        List<AState> ans=new ArrayList<>();
        MazeState tmp;
        int row = c.getR();
        int col = c.getC();
        /** up */
        //check if the move is in the bounds of the maze and check if the cell wasn't already visited
        if(isLegalMove(row-1,col) && this.isVisited[row-1][col]==0) {
            //check if the cell from the original maze is 0/2/3, so it is possible move
            if (getCellMaze(row - 1, col)) {
                //add the state to the list of possible moves, set the cell as visited
                tmp = new MazeState(row - 1, col);
                tmp.setCost(10);
                ans.add(tmp);
                //remark as visited
                this.isVisited[row - 1][col] = 1;
            }
        }
        /** down */
        //check if the move is in the bounds of the maze, check if the cell wasn't already visited
        if(isLegalMove(row+1,col) && this.isVisited[row+1][col]==0) {
            //check if the cell from the original maze is 0/2/3, so it is possible move
            if (getCellMaze(row + 1, col)) {
                //add the state to the list of possible moves, set the cell as visited
                tmp = new MazeState(row + 1, col);
                tmp.setCost(10);
                ans.add(tmp);
                this.isVisited[row + 1][col] = 1;
            }
        }
        /** left */
        //check if the move is in the bounds of the maze, check if the cell wasn't already visited
        if(isLegalMove(row,col-1) && this.isVisited[row][col-1]==0) {
            //check if the cell from the original maze is 0/2/3, so it is possible move
            if (getCellMaze(row, col - 1)) {
                //add the state to the list of possible moves, set the cell as visited
                tmp = new MazeState(row, col - 1);
                tmp.setCost(10);
                ans.add(tmp);
                this.isVisited[row][col - 1] = 1;
            }
        }
        /** right */
        //check if the move is in the bounds of the maze, check if the cell wasn't already visited
        if(isLegalMove(row,col+1) && this.isVisited[row][col+1]==0) {
            //check if the cell from the original maze is 0/2/3, so it is possible move
            if (getCellMaze(row, col + 1)) {
                //add the state to the list of possible moves, set the cell as visited
                tmp = new MazeState(row, col + 1);
                tmp.setCost(10);
                ans.add(tmp);
                this.isVisited[row][col + 1] = 1;
            }
        }
        //return the list of possible moves that not visited, and can be visited from the current state
        return ans;
    }

    /**
     * The function gets row and col and checks if it is in the maze bounds
     * @param row - the row of the state
     * @param col - the col of the state
     * @return - true if it is in the bounds, false else
     */
    public boolean isLegalMove(int row, int col){
        if(row <0 || col< 0 || row >= origionMaze.getRows() || col >= origionMaze.getCols() ){
            return false;
        }
        return true;
    }

    /**
     * The function reset the isVisited Matrix to 0 so the maze can be solved many times
     */
    public void isVisitedReset() {
        for(int i=0; i<isVisited.length; i++){
            for(int j=0; j<isVisited[0].length; j++){
                isVisited[i][j]=0;
            }
        }
    }

    //the function set a state to visited
    public void setIsVisited(AState state){
        if(state!=null && isLegalMove(((MazeState)state).getR(),((MazeState)state).getC())){
            this.isVisited[((MazeState)state).getR()][((MazeState)state).getC()]=1;
        }
    }

    // the function checks if the cell is not visited return true, else return false
    public boolean getIsCellVisited(AState state){
        if(state!=null){
            if(this.isVisited[((MazeState)state).getR()][((MazeState)state).getC()]==0){
                return true;
            }
        }
        return false;
    }

    public Maze getOrigionMaze() {
        return this.origionMaze;
    }
}
