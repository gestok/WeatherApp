package plh.team1.weatherapp;

// JavaFX Imports
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.scene.image.Image;
import javafx.scene.text.Font;
import javafx.event.EventHandler;

// IO Imports
import java.io.IOException;

// Project Clasees
import plh.team1.weatherapp.persistence.SharedState;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        // Add application icon
        stage.getIcons().add(new Image("/icons/icon.png"));

        // Load fonts
        Font.loadFont(getClass().getResourceAsStream("/fonts/Roboto-Regular.ttf"), 12);
        Font.loadFont(getClass().getResourceAsStream("/fonts/Roboto-Medium.ttf"), 12);
        Font.loadFont(getClass().getResourceAsStream("/fonts/Roboto-Bold.ttf"), 12);

        // Set a scene from primary FXML
        scene = new Scene(loadFXML("Overview"), 1024, 720);
        stage.setScene(scene);

        // Link stylesheet to application
        try {
            scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
        } catch (Exception e) {
            System.err.println("Error loading styles: " + e.getMessage());
        }

        // Set application properties
        stage.setTitle("WeatherApp");
        stage.setResizable(false);

        // Show scene
        stage.show();

        // Create Persistence Unit
        SharedState.createDB();
        
        // Create JVM hook for cleanup on close
        stage.setOnCloseRequest((WindowEvent event) -> {
            // Perform cleanup tasks before closing
            SharedState.closeDB();
        });
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

}
