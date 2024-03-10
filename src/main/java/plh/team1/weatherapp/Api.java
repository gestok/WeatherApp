package plh.team1.weatherapp;

// OKHttp3
import okhttp3.CacheControl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Callback;

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
}
