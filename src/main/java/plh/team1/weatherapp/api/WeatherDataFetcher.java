package plh.team1.weatherapp.api;

import plh.team1.weatherapp.model.CurrentCondition;
import com.google.gson.Gson;
import java.io.IOException;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class WeatherDataFetcher {

    private static final Gson gson = new Gson();
    private final String urlToCall;
    private final WeatherData weatherData;


    public WeatherDataFetcher(String cityName) {
        urlToCall = "https://wttr.in/" + cityName + "?format=j1";
        this.weatherData = fetchWeatherData();

    }
    
    private WeatherData fetchWeatherData(){
        String jsonResponse = null;
        try {
            jsonResponse = getJSONString();
            return gson.fromJson(jsonResponse, WeatherData.class);
        } catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }
    

    public String getJSONString() throws IOException {
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


    public WeatherData getWeatherData() {        
        return weatherData;

    }

    public CurrentCondition getCurrentCondition() {
        if (weatherData != null) {
            return weatherData.getCurrentCondition();
        } else {
            return null;
        }
    }
    
    public NearestArea getNearestArea() {
        if (weatherData != null) {
            return weatherData.getNearestArea();
        } else {
            return null;
        }
    }

  public AreaName getAreaName() {
        if (weatherData != null && weatherData.getNearestArea() != null) {
            return weatherData.getNearestArea().getAreaName();
        } else {
            return null;
        }
    }
  
  public Country getCountry() {
        if (weatherData != null && weatherData.getNearestArea() != null) {
            return weatherData.getNearestArea().getCountry();
        } else {
            return null;
        }
    }

}
