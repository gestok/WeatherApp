package plh.team1.weatherapp;

// Java
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Collectors;
import java.util.Comparator;

// Gson
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

// JavaFX
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.VBox;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebView;

// OkHttp3
import okhttp3.*;

public class SearchController {

    // Variables
    private SharedState state;
    private Utilities utilities = new Utilities();
    private ArrayList<City> allCities = new ArrayList<>();
    private Double cityListHeight = 200.0;
    @FXML
    private VBox root;
    @FXML
    private AnchorPane searchWrapper;
    @FXML
    private TextField searchBar;
    @FXML
    private ListView<City> cityListView;
    @FXML
    private ObservableList<City> filteredCities = FXCollections.observableArrayList();
    @FXML
    private VBox cityInfoWrapper;
    @FXML
    private VBox cityInfo;
    @FXML
    private Label cityName;
    @FXML
    private Label cityCountry;
    @FXML
    private Label cityLng;
    @FXML
    private Label cityLat;
    @FXML
    private Label cityPopulation;
    @FXML
    private VBox mapViewWrapper;
    @FXML
    private WebView mapView;
    @FXML
    private Button searchButton;
    @FXML
    private Button addFavButton;
    @FXML
    private Button favouriteIconButton;

    // Constructor
    public SearchController() {
        this.state = SharedState.getInstance();
    }

    @FXML
    private void initialize() {
        if (this.state.getCity() != null) {
            this.populateSearchWindow(this.state.getCity());
        }
        this.populateCityListView();
        this.detectRootClick();
        this.detectSearchBarClick();
        this.applySearchBarChangeListener();
        this.onCityListViewClicked();
    }

    /**
     * Methods that switches FXML.
     *
     * @throws IOException
     */
    @FXML
    private void switchToOverview() throws IOException {
        App.setRoot("Overview");
    }

    @FXML
    private void switchToStats() throws IOException {
        App.setRoot("Stats");
    }

//    /**
//     * Method that triggers when "Search" button is clicked.
//     */
//    @FXML
//    private void onSearchButtonClick() throws IOException {
//        Api weatherApi = new Api();
//        weatherApi.setQuery(this.state.getCity().getName());
//        this.setButtonsState(true);
//
//        weatherApi.fetchData(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                e.printStackTrace();
//                setButtonsState(false);
//                Platform.runLater(() -> {
//                    System.out.println("Failure!");
//                });
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                if (response.isSuccessful() && response.body() != null) {
//                    String responseData = response.body().string();
//                    // Process the response as needed
//                    Platform.runLater(() -> {
//                        Gson gson = new Gson();
//                        WeatherData myData = gson.fromJson(responseData, WeatherData.class);
//                        state.setData(myData);
//                    });
//                } else {
//                    // Retry with country
//                    weatherApi.setQuery(state.getCity().getCountry());
//                    weatherApi.fetchData(this);
//                }
//                setButtonsState(false);
//                Platform.runLater(() -> {
//                    try {
//                        // Update UI to reflect the completion, e.g., hide loading indicator
//                        switchToOverview();
//                    } catch (IOException error) {
//                        System.err.println(error);
//                    }
//                });
//            }
//        });
//
//    }

    /**
     * Method that searches throughout cities JSON and filters the list results
     * based on string parameter term.
     *
     * @param term A string to query through the list of cities.
     */
    private void search(String term) {
        if (term.isEmpty()) {
            this.setCityListVisibility(false);
        } else {
            try {
                // Filter the list
                ObservableList<City> filtered = this.allCities.stream()
                        .filter(city -> city.getName().toLowerCase().contains(term.toLowerCase())
                        || city.getAdmin().toLowerCase().contains(term.toLowerCase()))
                        .collect(Collectors.toCollection(FXCollections::observableArrayList));

                if (!filtered.isEmpty()) {
                    this.filteredCities.setAll(filtered);
                    this.setCityListVisibility(true);
                } else {
                    this.setCityListVisibility(false);
                }
            } catch (IndexOutOfBoundsException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    /**
     * Method that clears the current search query.
     */
    @FXML
    private void clearSearch() {
        this.searchBar.clear();
    }

    /**
     * Method that detects if there was a click in root and hides the overlay
     * list of cities while also setting focus to root.
     */
    private void detectRootClick() {
        this.root.setOnMouseClicked(event -> {
            if (!this.cityListView.isFocused()) {
                this.setCityListVisibility(false);
            }
            if (this.searchBar.isFocused()) {
                this.root.requestFocus();
            }
        });
    }

    /**
     * Method that detects if there was a click in search input, and if there is
     * a value, shows the the overlay list of cities.
     */
    private void detectSearchBarClick() {
        this.searchBar.setOnMouseClicked(event -> {
            if (!this.searchBar.getText().isEmpty()) {
                this.setCityListVisibility(true);
            }
        });
    }

    /**
     * Method that applies change listener on search bar input.
     */
    private void applySearchBarChangeListener() {
        this.searchBar.textProperty().addListener((observable, oldValue, newValue) -> {
            // Call search with new input
            search(newValue);
        });
    }

    /**
     * Method that applies click listener on list of cities.
     */
    private void onCityListViewClicked() {
        this.cityListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null) {
                return;
            }
            this.state.setCity(newValue);
            this.populateSearchWindow(newValue);
        });
    }

    /**
     * Method that populates Search window with the details of a given city
     * object.
     *
     * @param populateCityDetails
     */
    private void populateSearchWindow(City city) {
        if (city == null) {
            return;
        }
        this.searchBar.setText(city.toString());
        this.addFavButton.setText(city.getIsFavourite() ? "Remove from favourites" : "Add to favourites");
        this.addFavButton
                .setStyle(city.getIsFavourite() ? "-fx-background-color: #c2160a" : "-fx-background-color: #499bca");
        this.updateMap(city.getLatitude(), city.getLongitude());
        this.updateCityDetails(city);
        this.setCityListVisibility(false);
        this.setCityDetailsVisibility(true);
    }

    /**
     * Method that loads cities JSON file and populates a list variable with the
     * names of the cities.
     */
    private void populateCityListView() {
        JsonParser parser = new JsonParser();

        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("data/cities.json");
        if (inputStream == null) {
            return;
        }

        InputStreamReader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
        try (reader) {
            // Parse JSON
            JsonElement jsonElement = parser.parse(reader);
            JsonArray jsonArray = jsonElement.getAsJsonArray();

            // Add "City, Country" records
            for (JsonElement elem : jsonArray) {
                try {
                    JsonObject cityInfo = elem.getAsJsonObject();
                    String name = cityInfo.get("city").getAsString();
                    String admin = cityInfo.get("admin_name").getAsString();
                    String country = cityInfo.get("country").getAsString();
                    double latitude = 0.0;
                    if (cityInfo.has("lat")) {
                        latitude = cityInfo.get("lat").getAsDouble();
                    }
                    double longitude = 0.0;
                    if (cityInfo.has("lng")) {
                        longitude = cityInfo.get("lng").getAsDouble();
                    }
                    int population = 0;
                    if (cityInfo.has("population") && !cityInfo.get("population").getAsString().isEmpty()) {
                        population = cityInfo.get("population").getAsInt();
                    }

                    this.allCities.add(new City(name, admin, country, latitude, longitude, population, false));
                } catch (NumberFormatException e) {
                    System.err.println("Error parsing city data for " + elem + ": " + e.getMessage());
                }
            }

            // Sort city list
            Collections.sort(this.allCities, Comparator.comparing(City::getName).thenComparing(City::getAdmin));

            // Populate ListView
            this.filteredCities.setAll(this.allCities.stream().collect(Collectors.toList()));
            this.cityListView.setItems(this.filteredCities);
        } catch (IOException e) {
            System.err.println("Error reading cities.json: " + e.getMessage());
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

    @FXML
    private void onAddToFavouritesClick() {
        if (this.state == null || this.state.getCity() == null) {
            return;
        }
        this.state.getCity().setIsFavourite(!this.state.getCity().getIsFavourite());
        this.addFavButton
                .setText(this.state.getCity().getIsFavourite() ? "Remove from favourites" : "Add to favourites");
        this.addFavButton.setStyle(this.state.getCity().getIsFavourite() ? "-fx-background-color: #c2160a"
                : "-fx-background-color: #499bca");
    }

    /**
     * Method that updates the city details in the info box.
     *
     * @param city A city object.
     */
    private void updateCityDetails(City city) {
        this.cityName.setText(city.getName());
        this.cityCountry.setText(city.getCountry());
        this.cityLng.setText(this.utilities.formatToDecimals(city.getLongitude(), 4));
        this.cityLat.setText(this.utilities.formatToDecimals(city.getLatitude(), 4));
        this.cityPopulation.setText(this.utilities.toLocaleNotation(city.getPopulation()));
    }

    /**
     * Method that conditionally renders the list of cities.
     *
     * @param state Boolean
     */
    private void setCityListVisibility(boolean state) {
        AnchorPane.setTopAnchor(this.cityListView, state ? 50.0 : 30.0);
        this.cityListView.setPadding(new Insets(state ? 10 : 0));
        this.cityListView.setVisible(state);
        this.cityListView.setPrefHeight(state ? this.cityListHeight : 0.0);
    }

    /**
     * Method that conditionally renders all additional details for a city.
     *
     * @param state Boolean
     */
    private void setCityDetailsVisibility(boolean state) {
        this.mapViewWrapper.setVisible(state);
        this.mapView.setVisible(state);
        this.cityInfo.setVisible(state);
        this.cityInfoWrapper.setVisible(state);
    }

    /**
     * Method that conditionally disables - enables buttons.
     *
     * @param disabled
     */
    private void setButtonsState(boolean disabled) {
        this.searchButton.setDisable(disabled);
        this.addFavButton.setDisable(disabled);
        this.favouriteIconButton.setDisable(disabled);
    }
}
