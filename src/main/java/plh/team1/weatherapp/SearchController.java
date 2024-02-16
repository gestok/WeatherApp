package plh.team1.weatherapp;

import java.io.IOException;
import javafx.fxml.FXML;

public class SearchController {

    @FXML
    private void switchToOverview() throws IOException {
        App.setRoot("Overview");
    }
}