package plh.team1.weatherapp;

// Java
import plh.team1.weatherapp.api.Api;
import plh.team1.weatherapp.serialization.WeatherData;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
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
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import plh.team1.weatherapp.model.CityModel;
import plh.team1.weatherapp.model.WeatherDataModel;

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
     * @param weatherData
     * @param index
     */
    private void populateStats(CityModel city, WeatherDataModel wd, int index) {
        this.tempValue.setText(wd.getTemperature());
        this.weatherDesc.setText(wd.getWeatherDesc());
        this.cityCountry.setText(city.getCountryName());
        this.highLowFeels.setText(String.format("%s° / %s° Feels like %s°", wd.getHighTemp(), wd.getLowTemp(), wd.getFeelIsLike()));
        this.currentDate.setText(this.utilities.formatDate(wd.getDate()));
        this.weatherImage.setImage(this.utilities.getWeatherIcon(wd.getWeatherDesc()));
        this.uvindex_v.setText(wd.getUvIndex());
        this.humidity_v.setText(wd.getHumidity()+ "%");
        this.wind_v.setText(wd.getWindspeed() + " km/h");
        this.visibility_v.setText(wd.getVisibility() + " km");
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
                        WeatherData weatherData = gson.fromJson(responseData, WeatherData.class);
                        WeatherDataModel weatherDataModel = new WeatherDataModel(weatherData);
                        CityModel cityModel = new CityModel(weatherData);

                        cityCountyBottom.setText(cityModel.getCityName() + ", " + cityModel.getCountryName());

                        state.setData(weatherData);
                        populateStats(cityModel, weatherDataModel, 0);
                        updateMap(Double.parseDouble(cityModel.getLatitude()), Double.parseDouble(cityModel.getLongitude()));
                        mapViewWrapper.setVisible(true);
                        mapView.setVisible(true);
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
                    });
                }
            }
        });
    }

    @FXML
    private void onSaveButtonClick(ActionEvent event) {
        // To be implemented
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirm");
        alert.setHeaderText("Do you want to save the current search?");
        alert.setContentText("Tip: Saving stuff reduces your disk's lifespan");
        alert.showAndWait();
    }

    /**
     * Method to call when a city is selected. The method populates a web view
     * of a map with the help of OpenLayers library.
     *
     * @param latitude
     * @param longitude
     */
    private void updateMap(double latitude, double longitude) {
        int zoom = 12;
        String htmlContent = "<!DOCTYPE html>"
                + "<html>"
                + "<head>"
                + "<link rel=\"stylesheet\" href=\"https://openlayers.org/en/v4.6.5/css/ol.css\" type=\"text/css\">"
                + "<style>"
                + "  body {"
                + "    margin: 0;"
                + "    background: #ebfbfe;"
                + "  }"
                + "  .map {"
                + "    height: 100vh;"
                + "    width: 100%;"
                + "  }"
                + "  canvas {"
                + "    border-radius: 14px;"
                + "  }"
                + "</style>"
                + "<script src=\"https://openlayers.org/en/v4.6.5/build/ol.js\" type=\"text/javascript\"></script>"
                + "</head>"
                + "<body>"
                + "<div id=\"map\" class=\"map\"></div>"
                + "<script type=\"text/javascript\">"
                + "  var map = new ol.Map({"
                + "    target: 'map',"
                + "    layers: ["
                + "      new ol.layer.Tile({"
                + "        source: new ol.source.OSM()"
                + "      })"
                + "    ],"
                + "    view: new ol.View({"
                + "      center: ol.proj.fromLonLat([" + longitude + ", " + latitude + "]),"
                + "      zoom: " + zoom
                + "    }),"
                + "    controls: ol.control.defaults({"
                + "      attributionOptions: ({"
                + "        collapsible: false"
                + "      }),"
                + "      zoom: false," // Disable default zoom controls
                + "    }),"
                + "    interactions: ol.interaction.defaults({"
                + "      mouseWheelZoom: false,"
                + "      dragPan: false,"
                + "      doubleClickZoom: false"
                + "    })"
                + "  });"
                // Marker
                + "  var marker = new ol.Feature({"
                + "    geometry: new ol.geom.Point("
                + "      ol.proj.fromLonLat([" + longitude + ", " + latitude + "])"
                + "    )"
                + "  });"
                + "  var vectorSource = new ol.source.Vector({"
                + "    features: [marker]"
                + "  });"
                + "  var markerVectorLayer = new ol.layer.Vector({"
                + "    source: vectorSource,"
                + "  });"
                // Disable right-click context menu
                + "  document.getElementById('map').addEventListener('contextmenu', function(evt) {"
                + "    evt.preventDefault();"
                + "  });"
                + "  map.addLayer(markerVectorLayer);"
                + "</script>"
                + "</body>"
                + "</html>";

        this.mapView.getEngine().loadContent(htmlContent);
    }

}
