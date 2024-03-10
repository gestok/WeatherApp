package plh.team1.weatherapp;


// Java
import java.io.IOException;
import java.util.List;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import java.util.Date;

// JavaFX
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

public class StatsController {
    
    // DB - Persistence
    private EntityManager em;

    // Observables
    private ObservableList<CityBeanTable> citiesObservable;
    private ObservableList<WeatherDataBeanTable> weatherDataObservable;

    // View assets
    @FXML
    private Button menuItemOverview;
    @FXML
    private Button menuItemSearch;
    @FXML
    private Button menuItemStats;
    @FXML
    private TableView<CityBeanTable> tblCities;
    @FXML
    private TableColumn clmnId;
    @FXML
    private TableColumn clmnName;
    @FXML
    private TableColumn clmnTimesSearched;
    @FXML
    private TableView<WeatherDataBeanTable> tblWeatherData;
    @FXML
    private TableColumn clmnWeatherId;
    @FXML
    private TableColumn clmnDate;
    @FXML
    private TableColumn clmnTemperature;
    @FXML
    private TableColumn clmnHumidity;
    @FXML
    private TableColumn clmnWindspeed;
    @FXML
    private TableColumn clmnWeatherDescription;
    @FXML
    private TableColumn clmnUvIndex;

    // Constructor
    public StatsController() {
    }
    
    @FXML
    private void initialize() {
        
        clmnId.setCellValueFactory(new PropertyValueFactory<CityBeanTable, Integer>("id"));
        clmnName.setCellValueFactory(new PropertyValueFactory<CityBeanTable, String>("thisName"));
        clmnTimesSearched.setCellValueFactory(new PropertyValueFactory<CityBeanTable, Integer>("timesSearched"));
        
        clmnWeatherId.setCellValueFactory(new PropertyValueFactory<WeatherDataBeanTable, Integer>("id"));
        clmnDate.setCellValueFactory(new PropertyValueFactory<WeatherDataBeanTable, Date>("wdDate"));
        clmnTemperature.setCellValueFactory(new PropertyValueFactory<WeatherDataBeanTable, Integer>("tempC"));
        clmnHumidity.setCellValueFactory(new PropertyValueFactory<WeatherDataBeanTable, Integer>("humidity"));
        clmnWindspeed.setCellValueFactory(new PropertyValueFactory<WeatherDataBeanTable, Integer>("windSpeed"));
        clmnWeatherDescription.setCellValueFactory(new PropertyValueFactory<WeatherDataBeanTable, String>("weatherDesc"));
        clmnUvIndex.setCellValueFactory(new PropertyValueFactory<WeatherDataBeanTable, Integer>("uvIndex"));
                
        citiesObservable = FXCollections.observableArrayList();
        weatherDataObservable = FXCollections.observableArrayList();
        tblCities.setItems(citiesObservable);
        tblWeatherData.setItems(weatherDataObservable);
        
        try {
            populateDB();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        
        tblCities.setRowFactory(tv -> {
            TableRow<CityBeanTable> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    CityBeanTable rowData = row.getItem();
                    System.out.println(rowData.getThisName());
                }
            });
            return row;
        });
        
        tblCities.setRowFactory(tv -> {
            TableRow<CityBeanTable> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 1 && (!row.isEmpty())) {
                    CityBeanTable rowData = row.getItem();
                    Integer idToSearch = rowData.getId();
                    searchWDItems(idToSearch);
                }
            });
            return row;
        });
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
    private void switchToOverview(ActionEvent event) throws IOException {
        App.setRoot("Overview");
    }
    
    private void populateDB() {
        em = SharedState.getInstance().getEmf().createEntityManager();
        TypedQuery<City> query = em.createNamedQuery(
                "City.findAll", City.class);
        List<City> allCities = query.getResultList();
        if (allCities.isEmpty()) {
            System.out.println("MOTHERFATHER DB IS EMPTY");
        }
        citiesObservable.clear();
        for (City city : allCities) {
            CityBeanTable cityBeanTable = CityBeanTable.convertToCityBeanTable(city);
            citiesObservable.add(cityBeanTable);
        }
        tblCities.setItems(citiesObservable);
        em.close();
    }
    
    private void searchWDItems(Integer cityId) {
        System.out.println("City Id is " + cityId);
        em = SharedState.getInstance().getEmf().createEntityManager();
        TypedQuery<WeatherData> query = em.createNamedQuery(
                "WeatherData.findByCityId", WeatherData.class);
        query.setParameter("cityId", cityId);
        List<WeatherData> allWeatherData = query.getResultList();
        if (allWeatherData.isEmpty()) {
            System.out.println("MOTHERFATHER WD IS EMPTY");
        } else {
            int entries = allWeatherData.size();
            System.out.println("City has " + entries + " entries");
        }
        weatherDataObservable.clear();
        for (WeatherData data : allWeatherData) {
            WeatherDataBeanTable weatherDataBeanTable = WeatherDataBeanTable.convertToWeatherDataBeanTable(data);
            weatherDataObservable.add(weatherDataBeanTable);
        }
        tblWeatherData.setItems(weatherDataObservable);
        em.close();
    }
    
    @FXML
    private void onDeleteButtonClick(ActionEvent event) {
        // Handle delete button click
        CityBeanTable selectedCity = tblCities.getSelectionModel().getSelectedItem();

        if (selectedCity != null) {
            // Get the selected item and remove it from the data source
            int cityId = selectedCity.getId();
            
            ObservableList<CityBeanTable> data = tblCities.getItems();
            data.remove(selectedCity);

            // Perform additional logic for deleting from the database or any other actions
            SharedState.deleteCity(cityId);

            // Clear the selection after deletion
            tblCities.getSelectionModel().clearSelection();
        }
    }

    private void deleteCityFromDatabase(CityBeanTable city) {
        // Perform your logic for deleting the city from the database
        // This might involve calling a service or DAO class
        // ...
    }

    }
