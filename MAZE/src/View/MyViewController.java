package View;

import ViewModel.MyViewModel;
import algorithms.search.ISearchable;
import algorithms.search.Solution;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

public class MyViewController implements Initializable,Observer {
    public MediaView mediaView;
    private MediaPlayer mediaPlayer;
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
    public ComboBox<String> myComboBox; //for the generate maze
    @FXML
    public ComboBox<String> myComboBox1;
    String selectedValueSearchable;//the selected value of the generate maze
    String selectedValueSearching; //the selected solving strategy
    ISearchable searchable;
    private Solution solution;

    /**
     * Initialize the application, creating the binding to the text boxes in the app and to the comboBox
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lbl_player_row.textProperty().bind(update_player_position_row);
        lbl_player_column.textProperty().bind(update_player_position_col);

        myComboBox.getItems().addAll("simpleMaze", "emptyMaze", "MyMaze");
        myComboBox.setOnAction(event -> {
            this.selectedValueSearchable = myComboBox.getSelectionModel().getSelectedItem();
        });
        myComboBox1.getItems().addAll("Best First Search", "Depth First Search", "Breadth First Search");
        myComboBox1.setOnAction(event -> {
            this.selectedValueSearching = myComboBox1.getSelectionModel().getSelectedItem();
        });
    }

    /**
     * initialize the Start position of the player in the maze
     * @param row
     * @param col
     */
    public void setInitialPlayerPosition(int row, int col) {
        viewModel.setPlayerRow(row);
        viewModel.setPlayerCol(col);
        setPlayerPosition(row, col);
    }

    /**
     * initialize the view model and bind it to the view controller
     * @param viewModel
     */
    public void setViewModel(MyViewModel viewModel) {
        this.viewModel = viewModel;
        // Bind the view's properties to the ViewModel's properties
        lbl_player_row.textProperty().bind(viewModel.playerRowProperty().asString());
        lbl_player_column.textProperty().bind(viewModel.playerColProperty().asString());
    }

    /**
     * generate the maze when click on the generate button in the app and update the view model
     */
    public void generateMaze()
    {
        int rows = Integer.valueOf(textField_mazeRows.getText());
        int cols = Integer.valueOf(textField_mazeColumns.getText());
        this.searchable = viewModel.generateMaze(rows,cols,selectedValueSearchable);
        viewModel.update(viewModel,"Generate Maze");

    }

    /**
     * solve the maze when click on the solve maze button in the app and update the view model
     */
    public void solveMaze()
    {
        this.solution = viewModel.solveMaze(this.searchable,this.selectedValueSearching);

        this.viewModel.update(viewModel,"maze solved");
    }

    /**
     * the listener of the key pressed event to know where to move the player
     * @param keyEvent
     */
    public void keyPressed(KeyEvent keyEvent) {
        if(!(this.searchable.getGoalState().getR() == this.viewModel.getPlayerRow() &&
        this.searchable.getGoalState().getC() == this.viewModel.getPlayerCol())){
            viewModel.moveCharacter(keyEvent);
        }
        keyEvent.consume();
    }

    /**
     * the listener of the mouse clicked on to get the focus on the maze to move
     * @param mouseEvent
     */
    public void mouseClicked(MouseEvent mouseEvent) {
        mazeDisplayer.requestFocus();
    }

    /**
     * the update function that gets notify when something is changed in the app and act as it should.
     * generate maze, move player or solve the maze
     * @param o     the observable object.
     * @param arg   an argument passed to the {@code notifyObservers}
     */
    @Override
    public void update (Observable o, Object arg){
        String change = (String) arg;
        switch (change) {
            case "Generate Maze" -> mazeGenerated();
            case "player moved" -> playerMoved();
            case "maze solved" -> mazeSolved();
            case "Goal State" -> solvingTheMaze();
            default -> System.out.println("Not implemented change: " + change);
        }
    }

    /**
     * the function notify to the client that he succeeded to solve the maze
     */
    private void solvingTheMaze() {
        playWinAnimation();
    }

    /**
     * solving the maze
     */
    private void mazeSolved() {
        mazeDisplayer.drawSolution(this.searchable,this.solution);
    }

    /**
     * moving the image of the player based on the new position
     */
    private void playerMoved() {
        setPlayerPosition(viewModel.getPlayerRow(), viewModel.getPlayerCol());
    }

    /**
     * draw the new maze that was generated and initialize the start position of the player
     */
    private void mazeGenerated() {
        // Stop any playing video first
        stopWinAnimation();
        this.mazeDisplayer.drawMaze(this.searchable);
        int startRow = this.searchable.getInitState().getR();
        int startCol = this.searchable.getInitState().getC();
        setInitialPlayerPosition(startRow, startCol);
        setPlayerPosition(startRow, startCol);
        viewModel.setPlayerRow(startRow);
        viewModel.setPlayerCol(startCol);
    }

    /**
     * set new position to the player
     * @param row
     * @param col
     */
    public void setPlayerPosition(int row, int col){
        mazeDisplayer.setPlayerPosition(row, col);
        setUpdatePlayerRow(row);
        setUpdatePlayerCol(col);
    }

    /**
     * update the col of the player that is bind to the view, so that the correct col would appear on the screen
     * @param col
     */
    private void setUpdatePlayerCol(int col) {
        this.update_player_position_col.set(col+ "");
    }

    /**
     * update the row of the player that is bind to the view, so that the correct row would appear on the screen
     * @param row
     */
    private void setUpdatePlayerRow(int row) {
        this.update_player_position_row.set(row + "");
    }

    /**
     * menu function that present to the client an alert with the data of the application
     * @param actionEvent
     */
    @FXML
    public void AboutTheApp(ActionEvent actionEvent) {
        showCustomAlert("לפניך אפליקציה ליצירת מבוכים ופתרונם מבוססת אלגוריתמים שונים. יש שימוש באסטרטגיית Strategy כדי לתמוך בבחירה בזמן ריצה של אלגוריתמים שונים.");
    }

    /**
     * the function shows alert to the client with explanation of how the app is working and how to use it
     * @param actionEvent
     */
    public void Help(ActionEvent actionEvent) {
        showCustomAlert("לפנייך אפליקציה שיוצרת ופותרת מבוכים, המטרה הינה להגיע עם הדמות של מריו לדמות הנסיכה ולפתור את המבוך. ליצירת מבוך חדש לחץ על כתפתור הGENERATE, לפתירה של מבוך קיים לחץ על כפתור הSOLVE. בכדי לזוז השתמש בחצים של המקלדת");
    }

    /**
     * custom alert function to make sure that the message fits into it
     * @param message
     */
    public void showCustomAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information ");
        alert.setHeaderText("Information about the application and the way to use it");
        // Use a Label for the message text with wrapping enabled
        Label label = new Label(message);
        label.setWrapText(true);
        // Set the label as content and adjust the dialog pane
        alert.getDialogPane().setContent(label);
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE); // Expand height based on content
        alert.getDialogPane().setPrefWidth(500); // Set a preferred width (adjust as needed)
        alert.showAndWait();
    }

    /**
     * the function generate new maze and draw it to the client on the screen
     * @param actionEvent
     */
    public void generateNewMaze(ActionEvent actionEvent) {
        // Stop any playing video first
        stopWinAnimation();
        this.selectedValueSearchable = "MyMaze";
        this.selectedValueSearching = "Depth First Search";
        generateMaze();
    }

    /**
     * the function that play the GIF of mario after the client solve the maze
     */
    public void playWinAnimation() {
        try {
            // Get the absolute path of the project directory
            String projectDir = System.getProperty("user.dir");
            String videoPath = projectDir + "/MAZE/resources/images/GIF.mp4";
            File videoFile = new File(videoPath);
            if (!videoFile.exists()) {
                // Try alternative path
                videoPath = projectDir + "/MAZE/images/GIF.mp4";
                videoFile = new File(videoPath);
            }
            Media media = new Media(videoFile.toURI().toString());
            mediaPlayer = new MediaPlayer(media);
            mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
            mediaView.setMediaPlayer(mediaPlayer);
            mediaPlayer.play();
        } catch (Exception e) {
            System.err.println("Error playing video: " + e.getMessage());
        }
    }

    /**
     * the function stop all the animation so the client could generate new maze
     */
    public void stopWinAnimation() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.dispose(); // This releases resources
            mediaPlayer = null;    // Clear the reference
        }
        if (mediaView != null) {
            mediaView.setMediaPlayer(null); // Clear the media view
        }
    }

}


