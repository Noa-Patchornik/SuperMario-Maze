package ViewModel;

import Model.IModel;
import algorithms.search.ISearchable;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.input.KeyEvent;

import java.util.Observable;
import java.util.Observer;

public class MyViewModel extends Observable implements Observer {

    private IModel model;
    private int [][] maze;
    private int rowChar;
    private int colChar;
    private IntegerProperty playerRow = new SimpleIntegerProperty();
    private IntegerProperty playerCol = new SimpleIntegerProperty();

    public IntegerProperty playerRowProperty() {
        return playerRow;
    }

    public IntegerProperty playerColProperty() {
        return playerCol;
    }

    public int getPlayerRow() {
        return playerRow.get();
    }

    public void setPlayerRow(int playerRow) {
        this.playerRow.set(playerRow);
    }

    public void setPlayerCol(int playerCol) {
        this.playerCol.set(playerCol);
    }

    public int getPlayerCol() {
        return playerCol.get();
    }



    public void setPlayerPosition(int row, int col) {
        this.playerRow.set(row);
        this.playerCol.set(col);
    }

    public MyViewModel(IModel model) {
        this.model = model;
        this.model.assignObserver(this);
        this.maze = null;
    }


    public int[][] getMaze() {
        return maze;
    }


    public int getRowChar() {
        return rowChar;
    }

    public int getColChar() {
        return colChar;
    }

    @Override
    public void update(Observable o, Object arg) {
        setChanged();
        notifyObservers(arg);
    }


    public ISearchable generateMaze(int row, int col, String selectedSearchable)
    {
        return this.model.generateRandomMaze(row,col,selectedSearchable);
    }

    public void moveCharacter(KeyEvent keyEvent)
    {
        int direction = -1;

        switch (keyEvent.getCode()){
            case UP:
                direction = 1;
                break;
            case DOWN:
                direction = 2;
                break;
            case LEFT:
                direction = 3;
                break;
            case RIGHT:
                direction = 4;
                break;
        }

        model.updateCharacterLocation(direction);
        setPlayerRow(model.getRowChar());
        setPlayerCol(model.getColChar());
        setChanged();
        notifyObservers("player moved");
    }

    public void solveMaze(int [][] maze)
    {
        model.solveMaze(maze);
    }

    public void getSolution()
    {
        model.getSolution();
    }

}
