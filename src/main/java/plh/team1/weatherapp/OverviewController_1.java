package plh.team1.weatherapp;

// Java
import java.io.IOException;

// JavaFX
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;

public class OverviewController {

    // Variables
    private SharedState state;
    private Utilities utilities = new Utilities();
    @FXML
    private Button menuItemOverview;
    private CustomTooltip overviewTooltip = new CustomTooltip("Report");
    @FXML
    private Button menuItemSearch;
    private CustomTooltip searchTooltip = new CustomTooltip("Search");
    @FXML
    private Label uvindex_v;
    @FXML
    private Label humidity_v;
    @FXML
    private Label wind_v;
    @FXML
    private Label visibility_v;
    @FXML
    private Label tempValue;
    @FXML
    private Label weatherDesc;
    @FXML
    private Label cityCountry;
    @FXML
    private Label highLowFeels;
    @FXML
    private Label currentDate;
    @FXML
    private ImageView weatherImage;
    @FXML
    private Button previous;
    @FXML
    private Button next;

    // Constructor
    public OverviewController() {
        this.state = SharedState.getInstance();
    }

    @FXML
    private void initialize() {
        if (this.state.getCity() != null && this.state.getData() != null) {
            this.populateStats(this.state.getData(), 0);
        }
        this.initializeTooltips();
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

    /**
     * Method that initializes the tool-tips on elements.
     */
    private void initializeTooltips() {
        Tooltip.install(this.menuItemOverview, this.overviewTooltip);
        Tooltip.install(this.menuItemSearch, this.searchTooltip);
    }

    /**
     * Method that populates the fields with data based on the day index
     * provided (0 is today).
     *
     * @param data
     * @param index
     */
    private void populateStats(WeatherData data, int index) {
        this.tempValue.setText(String.valueOf(data.getCurrentCondition().getTempC()));
        String weatherDesc = String.valueOf(data.getCurrentCondition().getWeatherDescValue());
        this.weatherDesc.setText(weatherDesc);
        this.cityCountry.setText(this.state.getCity().toString());
        int highTemp = data.getWeather().get(index).getMaxTempC();
        int lowTemp = data.getWeather().get(index).getMinTempC();
        int feelsLike = data.getCurrentCondition().getFeelsLikeC();
        this.highLowFeels.setText(String.format("%d° / %d° Feels like %d°", highTemp, lowTemp, feelsLike));
        String date = data.getWeather().get(index).getDate();
        this.currentDate.setText(this.utilities.formatDate(date));

        this.weatherImage.setImage(this.utilities.getWeatherIcon(weatherDesc));

        this.uvindex_v.setText(this.state.getUvIndexRank());
        this.humidity_v.setText(String.valueOf(data.getCurrentCondition().getHumidity()) + "%");
        this.wind_v.setText(String.valueOf(data.getCurrentCondition().getWindspeedKmph()) + " km/h");
        this.visibility_v.setText(String.valueOf(data.getCurrentCondition().getVisibility()) + " km");
    }

    @FXML
    private void nextDay() {
        this.getDayData(0);
    }

    @FXML
    private void prevDay() {
        this.getDayData(0);
    }

    private void getDayData(int index) {
        return;
    }
}
