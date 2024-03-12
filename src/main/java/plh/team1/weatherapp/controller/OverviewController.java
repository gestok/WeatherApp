package plh.team1.weatherapp.controller;

// Java
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;

// JavaFX
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import plh.team1.weatherapp.api.Api;
import plh.team1.weatherapp.App;
import plh.team1.weatherapp.persistence.SharedState;

// Project Classes
import plh.team1.weatherapp.info.WeatherDataInfo;
import plh.team1.weatherapp.utils.Utilities;
import plh.team1.weatherapp.utils.CustomTooltip;
import plh.team1.weatherapp.model.WeatherData;
import plh.team1.weatherapp.model.City;
import plh.team1.weatherapp.utils.WebViewHelper;

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
    private Button saveButton;
    @FXML
    private VBox mapViewWrapper;
    @FXML
    private WebView mapView;
    @FXML
    private Label cityCountyBottom;

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
    private void populateStats(WeatherDataInfo data, int index) {
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

        Api weatherApi = new Api();

        String cityToSearch = searchBar.getText().trim();

        if (cityToSearch.isEmpty()) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("City Name is blank!");
            alert.setContentText("Please enter a city name!");
            alert.showAndWait();
            saveButton.setDisable(true);
            return;
        }
        weatherApi.setQuery(cityToSearch);

        weatherApi.fetchData(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                Platform.runLater(() -> System.out.println("Failure!"));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Platform.runLater(() -> handleResponse(response));
            }

            private void handleResponse(Response response) {
                if (response.isSuccessful() && response.body() != null) {
                    try {
                        String responseData = response.body().string();
                        Gson gson = new Gson();
                        JsonObject jsonObject = gson.fromJson(responseData, JsonObject.class);
                        JsonObject nearest = jsonObject.getAsJsonArray("nearest_area").get(0).getAsJsonObject();
                        Double lat = nearest.get("latitude").getAsDouble();
                        Double lon = nearest.get("longitude").getAsDouble();
//                        String pop = nearest.get("population").getAsString();
                        String region = nearest.getAsJsonArray("region").get(0).getAsJsonObject().get("value").getAsString();
                        String country = nearest.getAsJsonArray("country").get(0).getAsJsonObject().get("value").getAsString();
                        
                        cityCountyBottom.setText(cityToSearch + ", " + region + ", " + country);
                        
                        JsonObject currentCondition = jsonObject.getAsJsonArray("current_condition").get(0).getAsJsonObject();
                        JsonObject weatherDescription = currentCondition.getAsJsonArray("weatherDesc").get(0).getAsJsonObject();
                        
                        City myCity = new City();
                        myCity.setThisName(cityToSearch);
                        state.addCity(myCity);
                        
                        WeatherData myWeatherData = new WeatherData();
                        myWeatherData.setHumidity(currentCondition.get("humidity").getAsInt());
                        myWeatherData.setTempC(currentCondition.get("temp_C").getAsInt());
                        myWeatherData.setWindSpeed(currentCondition.get("windspeedKmph").getAsInt());
                        myWeatherData.setWeatherDesc(weatherDescription.get("value").getAsString());
                        myWeatherData.setUvindex(currentCondition.get("uvIndex").getAsInt());
                        myWeatherData.setWdDate(currentCondition.get("localObsDateTime").getAsString());
                        
                        
                        state.setWdData(myWeatherData);
                                               
                        WeatherDataInfo myData = gson.fromJson(responseData, WeatherDataInfo.class);                        
                                           
                        myData.setCityName(cityToSearch);
                        state.setData(myData);
                        populateStats(myData, 0);
                        mapView.getEngine().loadContent(WebViewHelper.formatMapHtml(lat, lon));;
                        mapViewWrapper.setVisible(true);
                        mapView.setVisible(true);
                        saveButton.setDisable(false);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    handleErrorResponse(response.code());
                }
            }

            private void handleErrorResponse(int statusCode) {
                if (statusCode == 404) {
                    Platform.runLater(() -> {
                        Alert alert = new Alert(AlertType.ERROR);
                        alert.setTitle("Error 404");
                        alert.setHeaderText("City " + cityToSearch + " not found!");
                        alert.setContentText("Please enter a valid city name!");
                        alert.showAndWait();
                        saveButton.setDisable(true);
                    });
                }
            }
        });
    }

    @FXML
    private void onSaveButtonClick(ActionEvent event) {

        WeatherData wd = state.getWdData();
        int cityId = state.getCityId();
        String wdDate = wd.getWdDate();

        if (state.isWdEntryDuplicate() == false) {
            wd.setCityId(state.getCityId());
            state.addWeatherData(wd);
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText("Entry successfully added!");
            alert.showAndWait();
            
        } else {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("This entry already exists in the DB for this city!");
            alert.setContentText("Entry: " + wdDate);
            alert.showAndWait();
        }

    }
 

}
