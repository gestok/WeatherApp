package plh.team1.weatherapp;

// Java
import plh.team1.weatherapp.api.Api;
import plh.team1.weatherapp.serialization.WeatherData;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javafx.application.Platform;
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
import plh.team1.weatherapp.crud.Repo;
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
    private Label cityCountryBottom;
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
    private final Tooltip saveButtonTooltip = new CustomTooltip("Saves data");
    @FXML
    private VBox mapViewWrapper;
    @FXML
    private WebView mapView;
    @FXML
    private Label cityCountyBottom;
    //threading for db
    private ExecutorService executor;

    // Constructor
    public OverviewController() {
        this.state = SharedState.getInstance();
        this.executor = Executors.newFixedThreadPool(4);
        this.initializeTooltips();
        this.state.setRepo(); //instantiate repo
    }

    @FXML
    private void initialize() {
        if (this.state.getData() != null) {
            this.populateStats();
        }
        this.initializeTooltips();

    }

    /**
     * Method that switches FXML.
     *
     * @throws IOException
     */
    @FXML
    private void switchToStats() throws IOException {
        createJson("cities3.json");
        App.setRoot("Stats");
    }

    /**
     * Method that initializes the tool-tips on elements.
     */
    //not working, tried removing css 
    private void initializeTooltips() {
        Tooltip.install(this.menuItemOverview, this.overviewTooltip);
        Tooltip.install(this.menuItemSearch, this.searchTooltip);
        Tooltip.install(this.SaveButton, this.saveButtonTooltip);
    }

    /**
     * adds city searched to db exports city info to JSON file cities.json
     */
    private void addToDb() {
        try {
            var cityToBeAdded = this.state.getRepo().addCity(state.getCityModel());
            state.setCityModel(cityToBeAdded);
        } catch (Exception e) {
            e.printStackTrace();
        };
    }

    /**
     * populates UI
     */
    private void populateStats() {
        var wd = state.getData();

        if (wd == null) {
            return;
        }
        var wdm = new WeatherDataModel(wd);
        var city = new CityModel(wd);
        var index = state.getIndex();

        this.tempValue.setText(wdm.getTemperature());
        this.weatherDesc.setText(wdm.getWeatherDesc());
        this.cityCountry.setText(city.getCityName() + ", " + city.getCountryName());
        String highTemp = state.getData().getWeather(index).getMaxTempC();
        String lowTemp = state.getData().getWeather(index).getMinTempC();
        this.highLowFeels.setText(String.format("%s° / %s° Feels like %s°", highTemp, lowTemp, wdm.getFeelIsLike()));
        this.currentDate.setText(this.utilities.formatDate(wdm.getDate()));
        this.weatherImage.setImage(this.utilities.getWeatherIcon(wdm.getWeatherDesc()));
        this.uvindex_v.setText(wdm.getUvIndex());
        this.humidity_v.setText(wdm.getHumidity() + "%");
        this.wind_v.setText(wdm.getWindspeed() + " km/h");
        this.visibility_v.setText(wdm.getVisibility() + " km");
        this.cityCountryBottom.setText((city.getCityName() + ", " + city.getCountryName()));

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
            infoDialog("Enter a city name");
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
                if (response.isSuccessful() && response.body() != null) {
                    String responseData = response.body().string();
                    Platform.runLater(() -> {
                        Gson gson = new Gson();
                        WeatherData weatherData = gson.fromJson(responseData, WeatherData.class);
                        state.setData(weatherData);
                        addToDb();
                        populateStats();
                        var city = state.getCityModel();
                        updateMap(Double.parseDouble(city.getLatitude()), Double.parseDouble(city.getLongitude()));
                        mapViewWrapper.setVisible(true);
                        mapView.setVisible(true);
                    });
                } else if (response.code() == 404) {
                    Platform.runLater(() -> infoDialog("City Not Found"));
                }
            }

        });
    }

    @FXML
    private void onSaveButtonClick() {

        if (state.getData() == null) {
            infoDialog("You have to search for a city first");
            return;
        }
        Alert alert = confirmationDialog("Do you want to save the current search?", "Tip: Saving stuff reduces your disk's lifespan");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            CompletableFuture.runAsync(new Runnable() {
                @Override
                public void run() {
                    try  {
                        var repo = state.getRepo();
                        var wd = state.getData();
                        var city = new CityModel(wd);
                        var wdm = new WeatherDataModel(wd);

                        repo.addWeatherData(wdm);
                        city = repo.addCity(city);
                        repo.addWeatherDataToCity(city.getId(), wdm);
                    } catch (Exception NullPointerException) {
                        
                    }
                }
            });
        }
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

    private void infoDialog(String errorMessage) {
        Alert errorAlert = new Alert(AlertType.INFORMATION);
        errorAlert.setTitle("Did you search for a city?");
        errorAlert.setHeaderText(null);
        errorAlert.setContentText(errorMessage);
        errorAlert.showAndWait();
    }

    private Alert confirmationDialog(String confirmation, String additionalText) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirm");
        alert.setHeaderText(confirmation);
        alert.setContentText(additionalText);
        return alert;
    }
    
     /**
     * Creates cities.json file that contains all the cities that are associated
     * with weatherData.
     *
     * @param fileName
     */
    private void createJson(String fileName) {
        Gson gson = new Gson();
        var repo = state.getRepo();
        List<CityModel> cities = repo.getCities();
        repo.close();
        StringBuilder jsonContent = new StringBuilder("[");
        for (int i = 0; i < cities.size(); i++) {
            JsonElement element = gson.fromJson(cities.get(i).toJSON(), JsonElement.class);
            jsonContent.append(gson.toJson(element));
            if (i < cities.size() - 1) {
                jsonContent.append(",\n ");
            }
        }
        jsonContent.append("]");
        //Write JSON content to file
        try {
            Path outputPath = Path.of("src/main/resources/data/"+fileName);
            Files.createDirectories(outputPath.getParent());
            Files.write(outputPath, jsonContent.toString().getBytes());
            System.out.println("JSON objects written to file successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
