package View;

import ViewModel.MyViewModel;
import algorithms.search.ISearchable;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

public class MyViewController implements Initializable,Observer {
    private MyViewModel viewModel;
    @FXML
    public TextField textField_mazeRows;
    @FXML
    public TextField textField_mazeColumns;
    @FXML
    public MazeDisplayer mazeDisplayer;
    @FXML
    public Label lbl_player_row;
    @FXML
    public Label lbl_player_column;
    StringProperty update_player_position_row = new SimpleStringProperty();
    StringProperty update_player_position_col = new SimpleStringProperty();
    private int [][] maze;
    @FXML
    public ComboBox<String> myComboBox;
    String selectedValueSearchable;
    ISearchable searchable;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lbl_player_row.textProperty().bind(update_player_position_row);
        lbl_player_column.textProperty().bind(update_player_position_col);
        myComboBox.getItems().addAll("simpleMaze", "emptyMaze", "MyMaze");
        myComboBox.setOnAction(event -> {
            this.selectedValueSearchable = myComboBox.getSelectionModel().getSelectedItem();
        });

    }

    public void setInitialPlayerPosition(int row, int col) {
        viewModel.setPlayerRow(row);
        viewModel.setPlayerCol(col);
        setPlayerPosition(row, col);
    }

    public void setViewModel(MyViewModel viewModel) {
        this.viewModel = viewModel;
        // Bind the view's properties to the ViewModel's properties
        lbl_player_row.textProperty().bind(viewModel.playerRowProperty().asString());
        lbl_player_column.textProperty().bind(viewModel.playerColProperty().asString());
    }


    public String get_update_player_position_row() {
        return update_player_position_row.get();
    }

    public void set_update_player_position_row(String update_player_position_row) {
        this.update_player_position_row.set(update_player_position_row);
    }

    public String get_update_player_position_col() {
        return update_player_position_col.get();
    }

    public void set_update_player_position_col(String update_player_position_col) {
        this.update_player_position_col.set(update_player_position_col);
    }



    public void generateMaze()
    {
        int rows = Integer.valueOf(textField_mazeRows.getText());
        int cols = Integer.valueOf(textField_mazeColumns.getText());
        this.searchable = viewModel.generateMaze(rows,cols,selectedValueSearchable);
        viewModel.update(viewModel,"Generate Maze");

    }

    public void solveMaze()
    {
        viewModel.solveMaze(this.maze);
    }


    public void showAlert(String message)
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(message);;
        alert.show();
    }


    public void keyPressed(KeyEvent keyEvent) {

        viewModel.moveCharacter(keyEvent);
        keyEvent.consume();
//        viewModel.update(viewModel,"player moved");


    }

    public void mouseClicked(MouseEvent mouseEvent) {
        mazeDisplayer.requestFocus();
    }



    @Override
    public void update (Observable o, Object arg){
        String change = (String) arg;
        switch (change) {
            case "Generate Maze" -> mazeGenerated();
            case "player moved" -> playerMoved();
            case "maze solved" -> mazeSolved();
            default -> System.out.println("Not implemented change: " + change);
        }
    }

    private void mazeSolved() {
    }

    private void playerMoved() {
        setPlayerPosition(viewModel.getPlayerRow(), viewModel.getPlayerCol());
    }

    private void mazeGenerated() {
        this.mazeDisplayer.drawMaze(this.searchable);
        int startRow = this.searchable.getInitState().getR();
        int startCol = this.searchable.getInitState().getC();
        setInitialPlayerPosition(startRow, startCol);
        setPlayerPosition(startRow, startCol);
        viewModel.setPlayerRow(startRow);
        viewModel.setPlayerCol(startCol);
    }

    public void setPlayerPosition(int row, int col){
        mazeDisplayer.setPlayerPosition(row, col);
        setUpdatePlayerRow(row);
        setUpdatePlayerCol(col);
    }

    private void setUpdatePlayerCol(int col) {
        this.update_player_position_col.set(col+ "");
    }

    private void setUpdatePlayerRow(int row) {
        this.update_player_position_row.set(row + "");
    }
}


