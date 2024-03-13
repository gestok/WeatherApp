package plh.team1.weatherapp.api;

// OKHttp3
import com.google.gson.Gson;
import java.io.IOException;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Callback;
import okhttp3.Response;
import plh.team1.weatherapp.serialization.WeatherData;

public final class Api {

    // Variables
    private String urlToCall;
    private final OkHttpClient client = new OkHttpClient();

    // Constructor
    public Api() {
    }

    // Constructor with a query parameter
    public Api(String name) {
        this.setQuery(name);
    }

    /**
     * Method that sets the location query of the API.
     *
     * @param name A string location name to fetch data for.
     */
    public void setQuery(String name) {
        this.urlToCall = "https://wttr.in/" + name + "?format=j1";
    }

    /**
     * Method that fetches JSON data from the specified URL using OkHttp.
     *
     * @param callback
     */
    public void fetchData(Callback callback) {
        // Build the request
        Request request = new Request.Builder().url(this.urlToCall).build();
        // Asynchronous call
        this.client.newCall(request).enqueue(callback);
    }

    /**
     *
     *
     * Bellow code is used for testing
     *
     *
     */
    public WeatherData fetchWeatherData() {
        String jsonResponse = null;
        Gson gson = new Gson();
        try {
            jsonResponse = getJSONString();
            return gson.fromJson(jsonResponse, WeatherData.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public WeatherData fetchWeatherData(String cityName, String countryName) {
        setUrl(cityName, countryName);
        return fetchWeatherData();
    }

    public void setUrl(String cityName, String countryName) {
        this.urlToCall = "https://wttr.in/" + cityName + "+" + countryName + "?format=j1";
    }

    private String getJSONString() throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(urlToCall).build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful() && response.body() != null) {
                return response.body().string();
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }
        return null;
    }
}
