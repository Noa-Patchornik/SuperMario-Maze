package Model;

import algorithms.search.ISearchable;

import java.util.Observer;

public interface IModel {
    public ISearchable generateRandomMaze(int row, int col, String selectedSearchable);
    public int[][] getMaze();
    public void updateCharacterLocation(int direction);
    public int getRowChar();
    public int getColChar();
    public void assignObserver(Observer o);
    public void solveMaze(int [][] maze);
    public void getSolution();
}
