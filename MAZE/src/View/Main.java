package View;

import Model.IModel;
import Model.MyModel;
import ViewModel.MyViewModel;
import javafx.application.Application;
import javafx.scene.media.AudioClip;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.net.URL;
import java.util.Objects;

public class Main extends Application {
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MyView.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root, 1000, 800);

        // Set Mario style theme - CSS file
        scene.getStylesheets().add(Objects.requireNonNull(View.Main.class.getResource("/View/MainStyle.css")).toExternalForm());

        // Set background music - Make sure to use the correct path
//        try {
//            String musicFile = "/music/Ground_Theme.mp3";
//            URL musicUrl = Main.class.getResource(musicFile);
//            if (musicUrl == null) {
//                System.err.println("Could not find music file: " + musicFile);
//            } else {
//                AudioClip backgroundMusic = new AudioClip(musicUrl.toExternalForm());
//                backgroundMusic.setCycleCount(AudioClip.INDEFINITE);
//                backgroundMusic.play();
//            }
//        } catch (Exception e) {
//            System.err.println("Error playing music: " + e.getMessage());
//        }

        primaryStage.setTitle("JavaFX FXML Example");
        primaryStage.setScene(scene);
        primaryStage.show();

        IModel model = new MyModel();
        MyViewModel viewModel = new MyViewModel(model);
        MyViewController view = fxmlLoader.getController();
        view.setViewModel(viewModel);
    }
}
