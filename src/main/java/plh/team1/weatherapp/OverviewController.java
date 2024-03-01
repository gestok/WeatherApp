package plh.team1.weatherapp;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;

public class OverviewController {

    // Variables
    private SharedState state;
    @FXML
    private Label uvindex_v;
    @FXML
    private Label humidity_v;
    @FXML
    private Label wind_v;
    @FXML
    private Label visibility_v;

    // Constructor
    public OverviewController() {
        this.state = SharedState.getInstance();
    }

    @FXML
    private void initialize() {
        if (this.state.getData() != null) {
            this.populateStats(this.state.getData());
        }
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

    private void populateStats(WeatherData data) {
        this.uvindex_v.setText(this.state.getUvIndexRank());
        this.humidity_v.setText(String.valueOf(data.getCurrentCondition().getHumidity()) + "%");
        this.wind_v.setText(String.valueOf(data.getCurrentCondition().getWindspeedKmph()) + " km/h");
        this.visibility_v.setText(String.valueOf(data.getCurrentCondition().getVisibility()));
    }
}
