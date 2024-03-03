package plh.team1.weatherapp;

// Java
import java.io.IOException;

// JavaFX
import javafx.fxml.FXML;

public class StatsController {

    // Constructor
    public StatsController() {
    }

    @FXML
    private void initialize() {
    }

    /**
     * Method that switches FXML.
     *
     * @throws IOException
     */
    @FXML
    private void switchToSearch() throws IOException {
        App.setRoot("Search");
    }

}
