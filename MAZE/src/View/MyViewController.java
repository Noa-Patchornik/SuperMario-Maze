package View;

import Model.IModel;
import Model.MovementDirection;
import Model.MyModel;
import ViewModel.MyViewModel;
import algorithms.mazeGenerators.AMazeGenerator;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.Position;
import algorithms.search.ISearchable;
import algorithms.search.SearchableMaze;
import algorithms.search.Solution;
import com.sun.webkit.BackForwardList;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

public class MyViewController implements IView, Initializable, Observer {
    public MyModel generator;
    public MazeDisplayer mazeDisplayer;
    @FXML
    public TextField txtFldRow;
    @FXML
    public TextField txtFldCol;
    String selectedValueSearchable;
    String selectedValueSearching;
    String str;
    public ISearchable searchable;
    public MyViewModel myViewModel= new MyViewModel(new MyModel());
    public Maze maze;
    public Label rowlbl;
    public Label collbl;
    StringProperty updatePlayerRow = new SimpleStringProperty();
    StringProperty updatePlayerCol = new SimpleStringProperty();
    //yaki
    @FXML
    public ComboBox<String> myComboBox;
    @FXML
    public Button solveMazeButton;
    @FXML
    public Button generateMazeButton;
    @FXML
    public ComboBox myComboBox2;
    @FXML
    public Label bindingRow;
    @FXML
    public Label bindingCol;
    @FXML
    public ComboBox myComboBox3;
    private BackForwardList Paths;

    //set the view model as an observer for the view controller
    public void setMyViewModel(MyViewModel myViewModel) {
        this.myViewModel = myViewModel;
        this.myViewModel.addObserver(this);
    }

    //initialize the application and bind the position of the player
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        rowlbl.textProperty().bind(updatePlayerRow);
        collbl.textProperty().bind(updatePlayerCol);
        // Add items to the ComboBox
        myComboBox.getItems().addAll("simpleMaze", "emptyMaze", "MyMaze");
        myComboBox.setOnAction(event -> {
            this.selectedValueSearchable = myComboBox.getSelectionModel().getSelectedItem();
            System.out.println("Selected Value: " + selectedValueSearchable);
        });

//        // Create an instance of MazeDisplayer and set it as the content of the canvas

        myComboBox2.getItems().addAll("BestFirstSearch", "BreadthFirstSearch", "DepthFirstSearch");
        myComboBox2.setOnAction(event -> {
            selectedValueSearching = (String) myComboBox2.getSelectionModel().getSelectedItem();
        });
        rowlbl.textProperty().bind(updatePlayerRow);
        collbl.textProperty().bind(updatePlayerCol);
    }

    //generate the maze with the size from the client
    public void generateMaze(ActionEvent actionEvent) {
        int rows = Integer.parseInt(txtFldRow.getText());
        int cols = Integer.parseInt(txtFldCol.getText());
        this.searchable = myViewModel.generateMaze(rows, cols,this.selectedValueSearchable);
        mazeDisplayer.setMaze(this.searchable);
        mazeDisplayer.drawMaze(this.searchable);
    }

    //by the change of the observable do something
    public void update(Observable o, Object arg) {
        String change = (String) arg;
        switch (change){
            case "maze generated" -> mazeGenerated();
            case "player moved" -> playerMoved();
            case "maze solved" -> mazeSolved();
            default -> System.out.println("Not implemented change: " + change);
        }
    }

    //if the maze was solved save the solution and draw it
    private void mazeSolved() {
        mazeDisplayer.setSolution(myViewModel.getSolution());
    }

    //if the player moved set the new location of the player
    private void playerMoved() {
        setPlayerPosition(myViewModel.getPlayerRow(), myViewModel.getPlayerCol());
    }

    //if the observable changed the maze, draw the new maze
    private void mazeGenerated() {
        mazeDisplayer.setMaze(this.searchable);
        mazeDisplayer.drawMaze(this.searchable);
    }

    public void solveMaze(ActionEvent actionEvent) {
        myViewModel.solveMaze();
    }

    //the handler function to move the player when an arrow is pressed
    public void keyPressed(KeyEvent keyEvent) {
        myViewModel.movePlayer(keyEvent);
        keyEvent.consume();
    }

    //change the focus of the mouse to the maze to be able to move the character
    public void clickOnMazeformoving(MouseEvent mouseEvent) {
        mazeDisplayer.requestFocus();
    }


    public String getUpdatePlayerRow() {
        return updatePlayerRow.get();
    }

    public void setUpdatePlayerRow(int updatePlayerRow) {
        this.updatePlayerRow.set(updatePlayerRow + "");
    }

    public String getUpdatePlayerCol() {
        return updatePlayerCol.get();
    }

    public void setUpdatePlayerCol(int updatePlayerCol) {
        this.updatePlayerCol.set(updatePlayerCol + "");
    }


    public void setPlayerPosition(int row, int col){
        mazeDisplayer.setPlayerPosition(row, col);
        setUpdatePlayerRow(row);
        setUpdatePlayerCol(col);
    }




    public void Help(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("About MazeApp");
        alert.setHeaderText(null);
        alert.setContentText("לפניך אפליקציה ליצירת מבוכים ופתרונם מבוססת אלגוריתמים שןנים. יש שימוש באסטרטגיית Strategy כדי לתמוך בבחירה בזמן ריצה של אלגוריתמים שונים.");
        alert.showAndWait();
    }



    @FXML
    public void openExistingMaze(ActionEvent actionEvent) {
        // Open FileChooser to select a file within the hardcoded folder
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select File");

        // Set the initial directory to the hardcoded path
        File initialDirectory = new File(System.getProperty("java.io.tmpdir"));
        if (initialDirectory.exists() && initialDirectory.isDirectory()) {
            fileChooser.setInitialDirectory(initialDirectory);
        } else {
            // Handle the case where the hardcoded path is invalid
            System.out.println("The specified directory does not exist.");
            return;
        }
        // Correctly obtain the Stage from the ActionEvent
        Stage stage = (Stage) ((MenuItem) actionEvent.getSource()).getParentPopup().getOwnerWindow();

        File file = fileChooser.showOpenDialog(stage);

        if (file != null) {
            handleFile(file);
        }
    }

    private void handleFile(File file) {
    }

    public void setViewModel(MyViewModel viewModel) {
    }
}
