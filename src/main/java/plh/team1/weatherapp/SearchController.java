package plh.team1.weatherapp;

// Java
import plh.team1.weatherapp.model.City;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Collectors;
import java.util.Comparator;

// Gson
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

// JavaFX
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.VBox;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebView;

public class SearchController {

    @FXML
    private VBox root;
    private ArrayList<City> allCities = new ArrayList<>();
    @FXML
    private AnchorPane searchWrapper;
    @FXML
    private TextField searchBar;
    @FXML
    private ListView<City> cityListView;
    private Double cityListHeight = 200.0;
    @FXML
    private ObservableList<City> filteredCities = FXCollections.observableArrayList();
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

    public void initialize() {
        this.populateCityListView();
        this.detectRootClick();
        this.detectSearchBarClick();
        this.applySearchBarChangeListener();
        this.onCityListViewClicked();
    }

    /**
     * Function that switches FXML.
     *
     * @throws IOException
     */
    @FXML
    private void switchToOverview() throws IOException {
        App.setRoot("Overview");
    }

    /**
     * Function that searches throughout cities JSON and filters the list
     * results based on string parameter term.
     *
     * @param term A string to query through the list of cities.
     */
    private void search(String term) {
        if (term.isEmpty()) {
            this.hideCityList();
        } else {
            try {
                // Filter the list
                ObservableList<City> filtered = this.allCities.stream()
                        .filter(city -> city.getName().toLowerCase().contains(term.toLowerCase())
                        || city.getCountry().toLowerCase().contains(term.toLowerCase()))
                        .collect(Collectors.toCollection(FXCollections::observableArrayList));

                this.filteredCities.setAll(filtered);
                this.showCityList();
            } catch (IndexOutOfBoundsException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    /**
     * Clears the current search query.
     */
    @FXML
    private void clearSearch() {
        this.searchBar.clear();
    }

    /**
     * Detects if there was a click in root and hides the overlay list of cities
     * while also setting focus to root.
     */
    private void detectRootClick() {
        this.root.setOnMouseClicked(event -> {
            if (!this.cityListView.isFocused()) {
                this.hideCityList();
            }
            if (this.searchBar.isFocused()) {
                this.root.requestFocus();
            }
        });
    }

    /**
     * Detects if there was a click in search input, and if there is a value,
     * shows the the overlay list of cities.
     */
    private void detectSearchBarClick() {
        this.searchBar.setOnMouseClicked(event -> {
            if (!this.searchBar.getText().isEmpty()) {
                this.showCityList();
            }
        });
    }

    /**
     * Applies change listener on search bar input.
     */
    private void applySearchBarChangeListener() {
        this.searchBar.textProperty().addListener((observable, oldValue, newValue) -> {
            // Call search with new input
            search(newValue);
        });
    }

    /**
     * Applies click listener on list of cities.
     */
    private void onCityListViewClicked() {
        this.cityListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                try {
                    this.searchBar.setText(newValue.toString());
                    this.updateMap(newValue.getLatitude(), newValue.getLongitude());
                    this.updateCityDetails(newValue);
                    this.hideCityList();
                    this.showCityDetails();
                } catch (IndexOutOfBoundsException e) {
                    System.err.println("Error accessing selected item: " + e.getMessage());
                }
            }
        });
    }

    /**
     * Loads cities JSON file and populates a list variable with the names of
     * the cities.
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
            JsonObject jsonObject = jsonElement.getAsJsonObject();

            // Add "City, Country" records
            for (String cityKey : jsonObject.keySet()) {
                try {
                    JsonObject cityInfo = jsonObject.getAsJsonObject(cityKey);
                    String name = cityInfo.get("city_ascii").getAsString();
                    String country = cityInfo.get("country").getAsString();
                    double latitude = cityInfo.has("lat") ? cityInfo.get("lat").getAsDouble() : 0.0;
                    double longitude = cityInfo.has("lng") ? cityInfo.get("lng").getAsDouble() : 0.0;
                    int population = cityInfo.has("population") && !cityInfo.get("population").getAsString().isEmpty() ? cityInfo.get("population").getAsInt() : 0;

                    this.allCities.add(new City(name, country, latitude, longitude, population));
                } catch (NumberFormatException e) {
                    System.err.println("Error parsing city data for " + cityKey + ": " + e.getMessage());
                }
            }

            // Sort city list
            Collections.sort(this.allCities, Comparator.comparing(City::getName).thenComparing(City::getCountry));

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
                + "  .map {"
                + "    height: 526px;"
                + "    width: 100%;"
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

    /**
     * Method that updates the city details in the info box.
     *
     * @param city A city object.
     */
    private void updateCityDetails(City city) {
        this.cityName.setText(city.getName());
        this.cityCountry.setText(city.getCountry());
        this.cityLng.setText(String.valueOf(city.getLongitude()));
        this.cityLat.setText(String.valueOf(city.getLatitude()));
        this.cityPopulation.setText(String.valueOf(city.getPopulation()));
    }

    /**
     * Method that hides the list of cities.
     */
    private void hideCityList() {
        AnchorPane.setTopAnchor(this.cityListView, 30.0);
        this.cityListView.setPrefHeight(0.0);
        this.cityListView.setVisible(false);
    }

    /**
     * Method that shows the list of cities.
     */
    private void showCityList() {
        AnchorPane.setTopAnchor(this.cityListView, 50.0);
        this.cityListView.setVisible(true);
        this.cityListView.setPrefHeight(this.cityListHeight);
    }

    /**
     * Method that hides all additional details for a city.
     */
    private void hideCityDetails() {
        this.mapViewWrapper.setVisible(false);
        this.mapView.setVisible(false);
        this.cityInfo.setVisible(false);
    }

    /**
     * Method that shows all additional details for a city.
     */
    private void showCityDetails() {
        this.mapViewWrapper.setVisible(true);
        this.mapView.setVisible(true);
        this.cityInfo.setVisible(true);
    }

}
