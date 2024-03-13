package plh.team1.weatherapp;

// Java
import java.io.IOException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Collectors;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

// JavaFX
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.layout.VBox;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.AnchorPane;

// OkHttp3
import plh.team1.weatherapp.model.CityModel;
import plh.team1.weatherapp.model.WeatherDataModel;

public class StatsController {

    // Variables
    private SharedState state;
    private Utilities utilities = new Utilities();
    private ArrayList<CityModel> allCities = new ArrayList<>();
    private Double cityListHeight = 100.0;
    @FXML
    private VBox root;
    @FXML
    private AnchorPane searchWrapper;
    @FXML
    private TextField searchBar;
    @FXML
    private ListView<CityModel> cityListView;
    @FXML
    private ObservableList<CityModel> filteredCities = FXCollections.observableArrayList();
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
    private Label timesSearched;
    @FXML
    private VBox tablesWrapper;
    @FXML
    private Button searchButton;
    @FXML
    private Button addFavButton;
    @FXML
    private Button favouriteIconButton;
    //tableview
    @FXML
    private TableView<WeatherDataModel> weatherTableView;
    @FXML
    private TableColumn<WeatherDataModel, String> temperatureColumn;
    @FXML
    private TableColumn<WeatherDataModel, String> humidityColumn;
    @FXML
    private TableColumn<WeatherDataModel, String> windSpeedColumn;
    @FXML
    private TableColumn<WeatherDataModel, String> uvIndexColumn;
    @FXML
    private TableColumn<WeatherDataModel, String> weatherDescColumn;
    @FXML
    private TableColumn<WeatherDataModel, String> dateColumn;

    private ObservableList<WeatherDataModel> weatherDataObservableList = FXCollections.observableArrayList();

    @FXML
    private Button deleteButton;

    // Constructor
    public StatsController() {
        this.state = SharedState.getInstance();
    }

    @FXML
    private void initialize() {
        if (this.state.getCityModel() != null) {
            this.populateSearchWindow(this.state.getCityModel());
            this.updateCityDetails(this.state.getCityModel());
            this.populateTableview(this.state.getCityModel());
        }
        this.populateCityListView();
        this.detectRootClick();
        this.detectSearchBarClick();
        this.applySearchBarChangeListener();
        this.onCityListViewClicked();
        this.initializeTableView();

    }

    /**
     * Helper method that initializes weatherTableView and prepares it for
     * editing
     */
    private void initializeTableView() {

        this.weatherTableView.setPlaceholder(new Label("No saved data!"));
        this.weatherTableView.setEditable(true);
        this.temperatureColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        this.humidityColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        this.windSpeedColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        this.uvIndexColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        this.weatherDescColumn.setCellFactory(TextFieldTableCell.forTableColumn());
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

    /**
     * Method that triggers when "Search" button is clicked.
     */
    private void search(String term) {
        if (term.isEmpty()) {
            this.setCityListVisibility(false);
        } else {
            try {
                // Filter the list
                ObservableList<CityModel> filtered = this.allCities.stream()
                        .filter(city -> city.getCityName().toLowerCase().contains(term.toLowerCase())
                        || city.getCountryName().toLowerCase().contains(term.toLowerCase()))
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
            this.state.setCityModel(newValue);
            this.populateTableview(newValue);
            this.populateSearchWindow(newValue);
        });
    }

    /**
     * Method that populates Search window with the details of a given city
     * object.
     *
     * @param populateCityDetails
     */
    private void populateSearchWindow(CityModel city) {
        if (city == null) {
            return;
        }
        this.searchBar.setText(city.toString());
        this.addFavButton.setText(city.getFavourite() ? "Remove from favourites" : "Add to favourites");
        this.addFavButton
                .setStyle(city.getFavourite() ? "-fx-background-color: #c2160a" : "-fx-background-color: #499bca");
        this.updateCityDetails(city);
        this.setCityListVisibility(false);
        this.setCityDetailsVisibility(true);
    }

    /**
     * Method that loads cities JSON and populates a list variable with the
     * names of the cities.
     */
    private void populateCityListView() {
        //gets all the cities that are associated with some weatherdata
        this.allCities = (ArrayList) state.getRepo().getCities();

        // Sort city list
        Collections.sort(this.allCities, Comparator.comparing(CityModel::getCityName).thenComparing(CityModel::getAdmin_name));

        // Populate ListView
        this.filteredCities.setAll(this.allCities.stream().collect(Collectors.toList()));
        this.cityListView.setItems(this.filteredCities);
    }

    /**
     * Method to add to favourites with a click
     */
    @FXML
    private void onAddToFavouritesClick() {
        if (this.state == null || this.state.getCityModel() == null) {
            return;
        }
        state.getRepo().setFavourite(state.getCityModel().getId(), !state.getCityModel().getFavourite());
        this.addFavButton
                .setText(this.state.getCityModel().getFavourite() ? "Remove from favourites" : "Add to favourites");
        this.addFavButton.setStyle(this.state.getCityModel().getFavourite() ? "-fx-background-color: #c2160a"
                : "-fx-background-color: #499bca");
    }

    /**
     * Method that updates the city details in the info box.
     *
     * @param city A city object.
     */
    private void updateCityDetails(CityModel city) {
        this.cityName.setText(city.getCityName());
        this.cityCountry.setText(city.getCountryName());
        this.cityLng.setText(this.utilities.formatToDecimals(Double.parseDouble(city.getLongitude()), 4));
        this.cityLat.setText(this.utilities.formatToDecimals(Double.parseDouble(city.getLatitude()), 4));
        this.cityPopulation.setText(city.getPopulation());
        this.timesSearched.setText((Integer.toString(city.getTimesSearched())));

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

    /**
     * Clears weather TableView
     */
    private void clearTableView() {
        this.weatherDataObservableList.clear();
    }

    /**
     * Populates weather TableView
     */
    private void populateTableview(CityModel city) {
        this.clearTableView();
        var repo = this.state.getRepo();
        ArrayList<WeatherDataModel> data = (ArrayList) repo.findByCity(city.getId());
        for (WeatherDataModel wd : data) {
            weatherDataObservableList.add(wd);
        }
        weatherTableView.setItems(weatherDataObservableList);
        temperatureColumn.setCellValueFactory(cell -> {
            String temperature = cell.getValue().getTemperature();
            return Bindings.createStringBinding(() -> temperature);
        });
        dateColumn.setCellValueFactory(cell -> {
            String date = cell.getValue().getDateTime().toString();
            return Bindings.createStringBinding(() -> date);
        });
        humidityColumn.setCellValueFactory(cell -> {
            String humidity = cell.getValue().getHumidity();
            return Bindings.createStringBinding(() -> humidity);
        });
        windSpeedColumn.setCellValueFactory(cell -> {
            String windSpeed = cell.getValue().getWindspeed();
            return Bindings.createStringBinding(() -> windSpeed);
        });
        uvIndexColumn.setCellValueFactory(cell -> {
            String uvIndex = cell.getValue().getUvIndex();
            return Bindings.createStringBinding(() -> uvIndex);
        });
        weatherDescColumn.setCellValueFactory(cell -> {
            String weatherDesc = cell.getValue().getWeatherDesc();
            return Bindings.createStringBinding(() -> weatherDesc);
        });

    }

    /**
     * method that deletes all data from the city sitting on the search tab
     *
     * @param actionEvent
     */
    public void Delete(ActionEvent actionEvent) {
        CityModel cityToBeRemoved = this.state.getCityModel();
        if (cityToBeRemoved == null) {
            return;
        }
        Alert alert = confirmationDialog("Are you sure you want to delete " + cityToBeRemoved.getCityName()
                + ", " + cityToBeRemoved.getCountryName()
                + " from your database?", "");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            this.state.getRepo().deleteCityData(cityToBeRemoved.getId());
            this.clearTableView();
            this.clearSearch();
            this.setCityDetailsVisibility(false);
            this.populateCityListView();

        } else {
            return;
        }

    }

    public void changeTemperature(TableColumn.CellEditEvent editedCell) {
        
        if (!isConfirmedAfterAlert()) {
            weatherTableView.refresh();
            return;
        }

        WeatherDataModel dataSelected = weatherTableView.getSelectionModel().getSelectedItem();
        try {
            int valueInserted = Integer.parseInt(editedCell.getNewValue().toString());
            dataSelected.setTemperature(Integer.toString(valueInserted));
            this.state.getRepo().updateTemperature(dataSelected.getWeatherDataId(), Integer.toString(valueInserted));

        } catch (NumberFormatException e) {
            this.populateTableview(this.state.getCityModel());
            // Handle the case where the input is not a valid integer
            Alert alert = confirmationDialog("Invalid Input", "Please enter an integer valued Temperature.");
            alert.showAndWait();
        }

    }

    public void changeHumidity(TableColumn.CellEditEvent editedCell) {
        
        if (!isConfirmedAfterAlert()) {
            weatherTableView.refresh();
            return;
        }

        WeatherDataModel dataSelected = weatherTableView.getSelectionModel().getSelectedItem();
        try {
            int valueInserted = Integer.parseInt(editedCell.getNewValue().toString());
            dataSelected.setHumidity(Integer.toString(valueInserted));
            this.state.getRepo().updateHumidity(dataSelected.getWeatherDataId(), Integer.toString(valueInserted));

        } catch (NumberFormatException e) {
            this.populateTableview(this.state.getCityModel());
            Alert alert = confirmationDialog("Invalid Input", "Please enter an integer valued Humidity.");
            alert.showAndWait();
        }

    }

    public void changeWindSpeed(TableColumn.CellEditEvent editedCell) {
        
        if (!isConfirmedAfterAlert()) {
            weatherTableView.refresh();
            return;
        }

        WeatherDataModel dataSelected = weatherTableView.getSelectionModel().getSelectedItem();
        try {
            int valueInserted = Integer.parseInt(editedCell.getNewValue().toString());
            dataSelected.setWindspeed(Integer.toString(valueInserted));
            state.getRepo().updateWindSpeed(dataSelected.getWeatherDataId(), Integer.toString(valueInserted));

        } catch (NumberFormatException e) {
            this.populateTableview(this.state.getCityModel());
            Alert alert = confirmationDialog("Invalid Input", "Please enter an integer valued Windspeed.");
            alert.showAndWait();
        }

    }

    public void changeUvIndex(TableColumn.CellEditEvent editedCell) {
        
        if (!isConfirmedAfterAlert()) {
            weatherTableView.refresh();
            return;
        }

        WeatherDataModel dataSelected = weatherTableView.getSelectionModel().getSelectedItem();
        try {
            int valueInserted = Integer.parseInt(editedCell.getNewValue().toString());
            dataSelected.setWindspeed(Integer.toString(valueInserted));
            this.state.getRepo().updateWindSpeed(dataSelected.getWeatherDataId(), Integer.toString(valueInserted));

        } catch (NumberFormatException e) {
            this.populateTableview(this.state.getCityModel());
            Alert alert = confirmationDialog("Invalid Input", "Please enter an integer valued UvIndex.");
            alert.showAndWait();
        }

    }

    public void changeWeatherDesc(TableColumn.CellEditEvent edittedCell) {
        
        if (!isConfirmedAfterAlert()) {
            weatherTableView.refresh();
            return;
        }
        
        WeatherDataModel dataSelected = weatherTableView.getSelectionModel().getSelectedItem();
        String valueInserted = edittedCell.getNewValue().toString();
        dataSelected.setWeatherDesc(valueInserted);
        this.state.getRepo().updateWeatherDesc(dataSelected.getWeatherDataId(), valueInserted);

    }

    @FXML
    private void onSaveToPdf(ActionEvent event) {

        List<CityModel> citiesToPrint;
        citiesToPrint = state.getRepo().getCities();
        if (citiesToPrint.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No Cities in DB. Cannot export");
            alert.showAndWait();
            return;
        }
        ExportPdfStats.exportPdfStats(citiesToPrint);
    }

    /**
     * Alert helper method
     *
     * @param confirmation
     * @param additionalText
     * @return
     */
    private Alert confirmationDialog(String confirmation, String additionalText) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm");
        alert.setHeaderText(confirmation);
        alert.setContentText(additionalText);
        return alert;
    }
    
    /**
     * Confirmation helper method
     *
     * @param void
     * @return
     */
     private boolean isConfirmedAfterAlert() {
        Alert alert = confirmationDialog("Are you sure you want to modify this record?",
                "This action modifies the database!");

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            return true;
        } else if (result.get() == ButtonType.CANCEL) {
            return false;
        } else {
            return false;
        }
    }

}
