package plh.team1.weatherapp.api;

import com.google.gson.Gson;
import java.io.IOException;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import plh.team1.weatherapp.serialization.WeatherData;

public class Api {

    private static final Gson gson = new Gson();
    private String urlToCall;    

    public Api(String cityName, String countryName) {
        this.setUrl(cityName, countryName);
    }

    public void setUrl(String cityName, String countryName) {
        this.urlToCall=  "https://wttr.in/" + cityName + "+" + countryName + "?format=j1";        
    }

    public WeatherData fetchWeatherData() {
        String jsonResponse = null;
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
