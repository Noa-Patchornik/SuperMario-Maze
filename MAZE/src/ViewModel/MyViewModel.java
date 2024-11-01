package ViewModel;

import Model.IModel;
import algorithms.search.ISearchable;
import algorithms.search.Solution;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.input.KeyEvent;

import java.io.Serializable;
import java.util.Observable;
import java.util.Observer;

public class MyViewModel extends Observable implements Observer {

    private IModel model;
    private int [][] maze;
    private IntegerProperty playerRow = new SimpleIntegerProperty();
    private IntegerProperty playerCol = new SimpleIntegerProperty();


    public MyViewModel(IModel model) {
        this.model = model;
        this.model.assignObserver(this);
        this.maze = null;
    }

    /**
     * the binding between the row position of the player and the view
     * @return
     */
    public IntegerProperty playerRowProperty() {
        return playerRow;
    }

    /**
     * the binding between the col position of the player and the view
     * @return
     */
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

    public int[][] getMaze() {
        return maze;
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

    /**
     * the function get the key event that happened and change the key code to int and send it to the model,
     * update the position of the player and notify the observers of the ViewModel class
     * @param keyEvent
     */
    public void moveCharacter(KeyEvent keyEvent)
    {
        int direction;
        if (keyEvent.getCode().isDigitKey()) {
            // Get the numeric character from the key event text
            String keyText = keyEvent.getText();
            try {
                // Parse the key text to an integer direction
                direction = Integer.parseInt(keyText);
            } catch (NumberFormatException e) {
                return; // exit if parsing fails
            }
        } else {
            return;
        }
        if (direction < 1 || direction > 9) {
            return;
        }
        model.updateCharacterLocation(direction);
        setPlayerRow(model.getRowChar());
        setPlayerCol(model.getColChar());
        setChanged();
        notifyObservers("player moved");
    }

    public Solution solveMaze(ISearchable iSearchable, String selectedSearchingValue)
    {
       return model.solveMaze(iSearchable,selectedSearchingValue);
    }

    public void getSolution()
    {
        model.getSolution();
    }

}
