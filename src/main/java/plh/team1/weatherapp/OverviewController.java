package plh.team1.weatherapp;

// Java
import com.google.gson.Gson;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;

// JavaFX
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

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
    @FXML
    private Button menuItemStats;
    @FXML
    private Label tempUnit;
    @FXML
    private TextField searchBar;
    @FXML
    private Button searchButton;
    @FXML
    private Button SaveButton;

    // Constructor
    public OverviewController() {
        this.state = SharedState.getInstance();
    }

    private void initialize() {
        this.state = SharedState.getInstance();
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

    @FXML
    private void switchToStats() throws IOException {
        App.setRoot("Stats");
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
        this.cityCountry.setText(data.getCityName());
        int highTemp = data.getWeather().get(index).getMaxTempC();
        int lowTemp = data.getWeather().get(index).getMinTempC();
        int feelsLike = data.getCurrentCondition().getFeelsLikeC();
        this.highLowFeels.setText(String.format("%d° / %d° Feels like %d°", highTemp, lowTemp, feelsLike));
        String date = data.getWeather().get(index).getDate();
        this.currentDate.setText(this.utilities.formatDate(date));

        this.weatherImage.setImage(this.utilities.getWeatherIcon(weatherDesc));

        this.uvindex_v.setText(data.getCurrentCondition().getUvIndex());
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

    @FXML
    private void onSearchButtonClick(ActionEvent event) throws IOException {

        System.out.println("1");

        Api weatherApi = new Api();
        System.out.println("2");
        String cityToSearch = searchBar.getText().trim();
        if (cityToSearch.isEmpty()) {
            System.out.println("Empty");
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("City Name is blank!");
            alert.setContentText("Please enter a city name!");
            alert.showAndWait();
            return;
        }
        weatherApi.setQuery(cityToSearch);
        System.out.println(cityToSearch);

        weatherApi.fetchData(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                Platform.runLater(() -> {
                    System.out.println("Failure!");
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful() && response.body() != null) {
                    String responseData = response.body().string();
                    // Process the response as needed
                    Platform.runLater(() -> {
                        Gson gson = new Gson();
                        System.out.println("3");
                        WeatherData myData = gson.fromJson(responseData, WeatherData.class);
                        myData.setCityName(cityToSearch);
                        state.setData(myData);
                        populateStats(myData, 0);
                        System.out.println("4");
                    });
                } else {
                    int statusCode = response.code();
                    if (statusCode == 404) {
                        Platform.runLater(() -> {
                            Alert alert = new Alert(AlertType.ERROR);
                            alert.setTitle("Error 404");
                            alert.setHeaderText("City "+cityToSearch+" not found!");
                            alert.setContentText("Please enter a valid city name!");
                            alert.showAndWait();

                        });
                    }

                }

            }
        });
    }

    @FXML
    private void onSaveButtonClick(ActionEvent event) {
        System.out.println("Save Button Pressed");
    }

}
