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
import plh.team1.weatherapp.api.Api;
import plh.team1.weatherapp.info.CityInfo;
import plh.team1.weatherapp.persistence.SharedState;
import plh.team1.weatherapp.utils.Utilities;
import plh.team1.weatherapp.utils.WebViewHelper;

public class SearchController {

    // Variables
    private SharedState state;
    private Utilities utilities = new Utilities();
    private ArrayList<CityInfo> allCities = new ArrayList<>();
    private Double cityListHeight = 200.0;
    @FXML
    private VBox root;
    @FXML
    private AnchorPane searchWrapper;
    @FXML
    private TextField searchBar;
    @FXML
    private ListView<CityInfo> cityListView;
    @FXML
    private ObservableList<CityInfo> filteredCities = FXCollections.observableArrayList();
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
        this.populateCityInfoListView();
        this.detectRootClick();
        this.detectSearchBarClick();
        this.applySearchBarChangeListener();
        this.onCityInfoListViewClicked();
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

    /**
     * Method that triggers when "Search" button is clicked.
     */
    @FXML
    private void onSearchButtonClick() throws IOException {
        switchToOverview();
    }

    /**
     * Method that searches throughout cities JSON and filters the list results
     * based on string parameter term.
     *
     * @param term A string to query through the list of cities.
     */
    private void search(String term) {
        if (term.isEmpty()) {
            this.setCityInfoListVisibility(false);
        } else {
            try {
                // Filter the list
                ObservableList<CityInfo> filtered = this.allCities.stream()
                        .filter(city -> city.getName().toLowerCase().contains(term.toLowerCase())
                        || city.getAdmin().toLowerCase().contains(term.toLowerCase()))
                        .collect(Collectors.toCollection(FXCollections::observableArrayList));

                if (!filtered.isEmpty()) {
                    this.filteredCities.setAll(filtered);
                    this.setCityInfoListVisibility(true);
                } else {
                    this.setCityInfoListVisibility(false);
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
                this.setCityInfoListVisibility(false);
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
                this.setCityInfoListVisibility(true);
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
    private void onCityInfoListViewClicked() {
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
     * @param populateCityInfoDetails
     */
    private void populateSearchWindow(CityInfo city) {
        if (city == null) {
            return;
        }
        this.searchBar.setText(city.toString());
        this.addFavButton.setText(city.getIsFavourite() ? "Remove from favourites" : "Add to favourites");
        this.addFavButton
                .setStyle(city.getIsFavourite() ? "-fx-background-color: #c2160a" : "-fx-background-color: #499bca");
        mapView.getEngine()
                .loadContent(WebViewHelper
                        .formatMapHtml(city.getLatitude(), city.getLongitude()));
        this.updateCityInfoDetails(city);
        this.setCityInfoListVisibility(false);
        this.setCityInfoDetailsVisibility(true);
    }

    /**
     * Method that loads cities JSON file and populates a list variable with the
     * names of the cities.
     */
    private void populateCityInfoListView() {
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

            // Add "CityInfo, Country" records
            for (JsonElement elem : jsonArray) {
                try {
                    JsonObject cityInfoLocal = elem.getAsJsonObject();
                    String name = cityInfoLocal.get("city").getAsString();
                    String admin = cityInfoLocal.get("admin_name").getAsString();
                    String country = cityInfoLocal.get("country").getAsString();
                    double latitude = 0.0;
                    if (cityInfoLocal.has("lat")) {
                        latitude = cityInfoLocal.get("lat").getAsDouble();
                    }
                    double longitude = 0.0;
                    if (cityInfoLocal.has("lng")) {
                        longitude = cityInfoLocal.get("lng").getAsDouble();
                    }
                    int population = 0;
                    if (cityInfoLocal.has("population") && !cityInfoLocal.get("population").getAsString().isEmpty()) {
                        population = cityInfoLocal.get("population").getAsInt();
                    }

                    this.allCities.add(new CityInfo(name, admin, country, latitude, longitude, population, false));
                } catch (NumberFormatException e) {
                    System.err.println("Error parsing city data for " + elem + ": " + e.getMessage());
                }
            }

            // Sort city list
            Collections.sort(this.allCities, Comparator.comparing(CityInfo::getName).thenComparing(CityInfo::getAdmin));

            // Populate ListView
            this.filteredCities.setAll(this.allCities.stream().collect(Collectors.toList()));
            this.cityListView.setItems(this.filteredCities);
        } catch (IOException e) {
            System.err.println("Error reading cities.json: " + e.getMessage());
        }
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
    private void updateCityInfoDetails(CityInfo city) {
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
    private void setCityInfoListVisibility(boolean state) {
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
    private void setCityInfoDetailsVisibility(boolean state) {
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