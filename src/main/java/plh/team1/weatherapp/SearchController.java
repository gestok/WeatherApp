package plh.team1.weatherapp;

// Java
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

// Gson
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

// JavaFX
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.VBox;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

public class SearchController {

    @FXML
    private VBox root;
    // Original list of cities
    @FXML
    private ArrayList<String> allCities = new ArrayList<>();
    @FXML
    private TextField searchBar;
    @FXML
    private ListView<String> cityListView;
    // Observable list to hold filtered cities
    @FXML
    private ObservableList<String> filteredCities = FXCollections.observableArrayList();

    @FXML
    private void switchToOverview() throws IOException {
        App.setRoot("Overview");
    }

    /**
     * Function that searches throughout cities JSON and filters the list
     * results based on string parameter term.
     *
     * @param term
     */
    private void search(String term) {
        if (term.isEmpty()) {
            // Hide list of cities
            cityListView.setVisible(false);
        } else {
            // Clear all cities from list
            cityListView.getItems().clear();

            // Filter the list
            filteredCities.setAll(this.allCities.stream()
                    .filter(city -> city.toLowerCase().contains(term.toLowerCase()))
                    .collect(Collectors.toList()));

            // Show list of cities
            cityListView.setVisible(true);
        }
    }
    
    /**
     * Clears the current search query.
     */
    @FXML
    private void clearSearch() {
        this.searchBar.clear();
    }

    public void initialize() {
        this.populateCityListView();
        this.detectRootClick();
        this.detectSearchBarClick();
        this.applySearchBarChangeListener();
        this.onCityListViewClicked();
    }

    /**
     * Detects if there was a click in root and hides the overlay list of cities
     * while also setting focus to root.
     */
    private void detectRootClick() {
        this.root.setOnMouseClicked(event -> {
            if (!this.cityListView.isFocused()) {
                this.cityListView.setVisible(false);
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
                this.cityListView.setVisible(true);
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
                this.searchBar.setText(newValue);
                cityListView.setVisible(false);
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
                JsonObject cityInfo = jsonObject.getAsJsonObject(cityKey);
                String cityName = cityInfo.get("city_ascii").getAsString();
                String countryName = cityInfo.get("country").getAsString();
                this.allCities.add(cityName + ", " + countryName);
            }

            // Sort city list
            Collections.sort(this.allCities);

            // Populate ListView
            this.filteredCities = FXCollections.observableArrayList(this.allCities);
            this.cityListView.setItems(this.filteredCities);
        } catch (IOException e) {
            System.err.println("Error reading cities.json: " + e.getMessage());
        }
    }

}
