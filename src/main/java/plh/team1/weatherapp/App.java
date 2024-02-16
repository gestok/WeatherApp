package plh.team1.weatherapp;

// JavaFX Imports
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.text.Font;

// IO Imports
import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage primaryStage) throws IOException {
        // Add application icon
        primaryStage.getIcons().add(new Image("/icons/icon.png"));

        // Load fonts
        Font.loadFont(getClass().getResourceAsStream("/fonts/Roboto-Regular.ttf"), 12);
        Font.loadFont(getClass().getResourceAsStream("/fonts/Roboto-Medium.ttf"), 12);

        // Set a scene from primary FXML
        scene = new Scene(loadFXML("primary"), 1024, 720);
        primaryStage.setScene(scene);

        // Link stylesheet to application
        try {
            scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
        } catch (Exception e) {
            System.err.println("Error loading styles: " + e.getMessage());
        }

        // Set application properties
        primaryStage.setTitle("WeatherApp");
        primaryStage.setResizable(false);

        // Show scene
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

}
