package algorithms.search;

import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.Position;

import java.util.ArrayList;
import java.util.List;

public class SearchableMaze implements ISearchable {

    public Maze getOrigionMaze() {
        return origionMaze;
    }

    private Maze origionMaze;
    private MazeState StartState;
    private MazeState EndState;
    private int[][] graph;

    private int[][] isVisited;

    public SearchableMaze(Maze m){
        this.origionMaze=m;
        StartState = new MazeState(m.getStartPosition().getRowIndex(),m.getStartPosition().getColumnIndex());
        EndState= new MazeState(m.getGoalPosition().getRowIndex(),m.getGoalPosition().getColumnIndex());
        isVisited=new int[m.getRows()][m.getCols()];
    }

    public int [][] adaptMyMaze(){
        int [][] graph = new int [origionMaze.getRows()][origionMaze.getCols()];
        List<Integer> neigh;
        for(int i=0;i< origionMaze.getRows();i++) {
            for (int j = 0; j < origionMaze.getCols(); j++) {
                if (origionMaze.getMaze()[i][j] == 0 || origionMaze.getMaze()[i][j] == 2 || origionMaze.getMaze()[i][j] == 3) {
                    graph[i][j] = 1;
                    neigh = GETALL0(origionMaze.getMaze(), i, j);
                    for (int k = 0; k < neigh.size(); k++) {
                        int tmp = neigh.get(k);
                        switch (tmp) {
                            case 1:
                                graph[i - 1][j] = 1;
                                break;
                            case 2:
                                graph[i][j + 1] = 1;
                                break;
                            case 3:
                                graph[i + 1][j] = 1;
                                break;
                            case 4:
                                graph[i][j - 1] = 1;
                                break;
                        }
                    }
                }
            }
        }
        this.graph=graph;
        return graph;
    }

    private List<Integer> GETALL0(int[][] maze, int i, int j) {
        List<Integer> all0 = new ArrayList<>();
        if(i-1>=0) {
            if(maze[i-1][j]==0 || maze[i-1][j]==2 || maze[i-1][j]==3)
                all0.add(1);
        }
        if(i+1< maze.length) {
            if(maze[i+1][j]==0 || maze[i+1][j]==2 || maze[i+1][j]==3)
                all0.add(3);
        }
        if(j+1<maze[0].length) {
            if (maze[i][j+1] == 0 || maze[i][j+1] == 2 || maze[i][j+1] == 3)
                all0.add(2);
        }
        if(j-1>-0) {
            if (maze[i][j - 1] == 0 || maze[i][j - 1] == 2 || maze[i][j - 1] == 3)
                all0.add(4);
        }
        return all0;
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
    public List<Position> getAllSuccessors(Position s) {
        List <Position> listposition = s.getNeigh(origionMaze.getRows(), origionMaze.getCols(),origionMaze);
        listposition = s.getvalidneigh(listposition,s.getRowIndex(),s.getColumnIndex(),origionMaze);
        return listposition;
    }

    @Override
    public void setSuccessor(AState n) {
        if(n!=null){
            n.setSuccessor((MazeState) n);
        }
    }

    @Override
    public void setGoal(MazeState n) {
        if(n!=null){
            this.EndState = n;
        }
    }

    /**
     * The function get all the possible moves from a current state up, down, right, left and diagonal
     * @param curr - the state the maze is at
     * @return the list of the possible moves from the state
     */
    public List<AState> getAllPossibleStates(AState curr){
        List<AState> ans;
        MazeState c = (MazeState) curr;
        ans = isOkNotDiagonal(c); // get all the state not diagonal: up,down, right, left/
        List<AState> ansDiagonal = isOkDiagonal(c); // get all the diagonal states
        //add the diagonal states to the list of no diagonal
        for(int i=0; i<ansDiagonal.size(); i++){
            ans.add(ansDiagonal.get(i));
        }
        //return the complete list of moves include diagonal
        return ans;
    }

    /**
     * The function that get the diagonal possible moves from the state
     * @param c - the current state in the maze
     * @return - the list of the diagonal moves
     */
    private List<AState> isOkDiagonal(MazeState c) {
        List<AState> diagonalstate = new ArrayList<>();
        int row= c.getR();
        int col = c.getC();
        MazeState tmp;
        //checks if the move is not out of the bounds of the maze, if we didn't visit there and if there is no wall there
        if(isLegalMove(row-1,col-1) && getCellMaze(row-1,col-1) && this.isVisited[row-1][col-1]==0){
            //checking if the cells next to the current are 0 too so there would be a way in diagonal
            if(getCellMaze(row-1,col) || getCellMaze(row,col-1)){
                //checks if the cells next to current wasn't visited already
                if(this.isVisited[row-1][col]==0 || this.isVisited[row][col-1]==0){
                    //making a new state, set the cost of diagonal as 15 and add to the list
                    tmp = new MazeState(row-1,col-1);
                    tmp.setCost(15);
                    diagonalstate.add(tmp);
                    this.isVisited[row - 1][col-1] = 1;
                }
            }
        }
        //checks if the move is not out of the bounds of the maze, if we didn't visit there and if there is no wall there
        if(isLegalMove(row+1,col+1) && getCellMaze(row+1,col+1) && this.isVisited[row+1][col+1]==0){
            //checking if the cells next to the current are 0 too so there would be a way in diagonal
            if(getCellMaze(row+1,col) || getCellMaze(row,col+1)){
                //checks if the cells next to current wasn't visited already
                if(this.isVisited[row+1][col]==0 || this.isVisited[row][col+1]==0){
                    //making a new state, set the cost of diagonal as 15 and add to the list
                    tmp = new MazeState(row+1,col+1);
                    tmp.setCost(15);
                    diagonalstate.add(tmp);
                    this.isVisited[row + 1][col+1] = 1;
                }
            }
        }
        //checks if the move is not out of the bounds of the maze, if we didn't visit there and if there is no wall there
        if(isLegalMove(row-1,col+1) && getCellMaze(row-1,col+1) && this.isVisited[row-1][col+1]==0){            //checking if the cells next to the current are 0 too so there would be a way in diagonal
            //checking if the cells next to the current are 0 too so there would be a way in diagonal
            if(getCellMaze(row-1,col) || getCellMaze(row,col+1)){
                //checks if the cells next to current wasn't visited already
                if(this.isVisited[row-1][col]==0 || this.isVisited[row][col+1]==0){
                    //making a new state, set the cost of diagonal as 15 and add to the list
                    tmp = new MazeState(row-1,col+1);
                    tmp.setCost(15);
                    diagonalstate.add(tmp);
                    this.isVisited[row - 1][col+1] = 1;
                }
            }
        }
        //checks if the move is not out of the bounds of the maze, if we didn't visit there and if there is no wall there
        if(isLegalMove(row+1,col-1) && getCellMaze(row+1,col-1) && this.isVisited[row+1][col-1]==0){
            //checking if the cells next to the current are 0 too so there would be a way in diagonal
            if(getCellMaze(row+1,col) || getCellMaze(row,col-1)){
                //checks if the cells next to current wasn't visited already
                if(this.isVisited[row+1][col]==0 || this.isVisited[row][col-1]==0){
                    //making a new state, set the cost of diagonal as 15 and add to the list
                    tmp = new MazeState(row+1,col-1);
                    tmp.setCost(15);
                    diagonalstate.add(tmp);
                    this.isVisited[row + 1][col-1] = 1;
                }
            }
        }
        //the list of the diagonal states
        return diagonalstate;
    }

    public boolean getCellMaze(int row, int col) {
        if(origionMaze.getCellMaze(row,col)==0 || origionMaze.getCellMaze(row,col)==2 ||origionMaze.getCellMaze(row,col)==3)
            return true;
        return false;
    }

    /**
     * The function gets all the NOT diagonal moves that can be from the current one, up,down,right,left
     * @param curr - the current state in the maze
     * @return - the list of all the not diagonal possible moves
     */
    public List<AState> isOkNotDiagonal(AState curr){
        MazeState c = (MazeState) curr;
        List<AState> ans=new ArrayList<>();
        MazeState tmp;
        int row = c.getR();
        int col = c.getC();
        //check if the move is in the bounds of the maze and check if the cell wasn't already visited
        if(isLegalMove(row-1,col) && this.isVisited[row-1][col]==0)
            //check if the cell from the original maze is 0/2/3, so it is possible move
            if(getCellMaze(row-1,col)) {
                //add the state to the list of possible moves, set the cell as visited
                tmp = new MazeState(row-1,col);
                tmp.setCost(10);
                ans.add(tmp);
                this.isVisited[row - 1][col] = 1;
            }

        //check if the move is in the bounds of the maze, check if the cell wasn't already visited
        if(isLegalMove(row+1,col) && this.isVisited[row+1][col]==0)
            //check if the cell from the original maze is 0/2/3, so it is possible move
            if(getCellMaze(row+1,col)) {
                //add the state to the list of possible moves, set the cell as visited
                tmp = new MazeState(row+1,col);
                tmp.setCost(10);
                ans.add(tmp);
                this.isVisited[row+ 1][col] =1;
            }
        //check if the move is in the bounds of the maze, check if the cell wasn't already visited
        if(isLegalMove(row,col-1) && this.isVisited[row][col-1]==0)
            //check if the cell from the original maze is 0/2/3, so it is possible move
            if(getCellMaze(row,col-1)) {
                //add the state to the list of possible moves, set the cell as visited
                tmp = new MazeState(row,col-1);
                tmp.setCost(10);
                ans.add(tmp);
                this.isVisited[row][col - 1] = 1;
            }
        //check if the move is in the bounds of the maze, check if the cell wasn't already visited
        if(isLegalMove(row,col+1) && this.isVisited[row][col+1]==0)
            //check if the cell from the original maze is 0/2/3, so it is possible move
            if(getCellMaze(row,col+1)){
                //add the state to the list of possible moves, set the cell as visited
                tmp = new MazeState(row,col+1);
                tmp.setCost(10);
                ans.add(tmp);
                this.isVisited[row][col + 1] =1;
            }
        //return the list of possible move that not visited, and can be visited from the current state
        return ans;
    }

    /**
     * The function gets row and col and checks if the numbers are in the maze bounds
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


    public void setIsVisited(AState state){
        if(state!=null && isLegalMove(((MazeState)state).getR(),((MazeState)state).getC())){
            this.isVisited[((MazeState)state).getR()][((MazeState)state).getC()]=1;
        }
    }

    public boolean getIsCellVisited(AState state){
        if(state!=null){
            if(this.isVisited[((MazeState)state).getR()][((MazeState)state).getC()]==0){
                return true;
            }
        }
        return false;
    }
}
