package plh.team1.weatherapp;

/**
 * A model state class that retains application data between scenes. The model
 * decouples the controllers from each other and makes the data (state)
 * management more centralized and easier to handle.
 */
public class SharedState {

    private static SharedState instance;
    private City city;
    private WeatherData data;
    private Utilities utilities = new Utilities();
    private int currentIndex = 0;

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
     * Stores the selected city data.
     *
     * @param city
     */
    public synchronized void setCity(City city) {
        this.city = city;
    }

    /**
     * Returns data about selected city.
     *
     * @return City
     */
    public City getCity() {
        return this.city;
    }

    /**
     * Stores the weather data response for reuse.
     *
     * @param data
     */
    public synchronized void setData(WeatherData data) {
        this.data = data;
    }

    /**
     * Returns the weather data response.
     *
     * @return WeatherData
     */
    public WeatherData getData() {
        return this.data;
    }

    
}
