package plh.team1.weatherapp;

import plh.team1.weatherapp.crud.Repo;
import plh.team1.weatherapp.model.CityModel;
import plh.team1.weatherapp.model.WeatherDataModel;
import plh.team1.weatherapp.serialization.WeatherData;

/**
 * A model state class that retains application data between scenes. The model
 * decouples the controllers from each other and makes the data (state)
 * management more centralized and easier to handle.
 */
public class SharedState {

    private static SharedState instance;
    private WeatherData data;
    private int index;
    private CityModel cityModel;
    private Repo repo;
    private WeatherDataModel weatherDataModel;

    private SharedState() {
    }

    /**
     * Returns a Singleton instance that shares the same data to the callers.
     *
     * @return SharedState The same instance of shared state.
     */
    public static synchronized SharedState getInstance() {
        if (instance == null) {
            instance = new SharedState();
        }
        return instance;
    }

    /**
     * Stores the weather data response for reuse.
     *
     * @param data
     */
    public synchronized void setData(WeatherData data) {
        this.data = data;
        this.cityModel = new CityModel(data);
        this.weatherDataModel = new WeatherDataModel(data);
    }

    /**
     * WeatherData
     */
    public synchronized WeatherData getData() {
        return this.data;
    }

    /**
     * Index
     *
     * @param index
     */
    public synchronized void setIndex(int index) {
        this.index = index;
    }

    public synchronized int getIndex() {
        return this.index;
    }

    /**
     * CityModel
     */
    public synchronized CityModel getCityModel() {
        return cityModel;
    }

    public synchronized void setCityModel(CityModel cityModel) {
        this.cityModel = cityModel;
    }

    /**
     * WeatherDataModel
     *
     * @return
     */
    public synchronized WeatherDataModel getWeatherDataModel() {
        return weatherDataModel;
    }

    public synchronized void setWeatherDataModel(WeatherDataModel weatherDataModel) {
        this.weatherDataModel = weatherDataModel;
    }

    /**
     * Database
     */
    public synchronized Repo getRepo() {
        return repo;
    }

    public synchronized void setRepo() {
        this.repo = new Repo();

    }

}
