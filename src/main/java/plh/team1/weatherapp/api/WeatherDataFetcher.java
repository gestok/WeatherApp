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
    private String responseString;
    private final WeatherData weatherData;
    private CurrentCondition currentCondition;
    private AreaName areaName;
    private Country country;
    private NearestArea nearestArea;

    public WeatherDataFetcher(String cityName) {
        urlToCall = "https://wttr.in/" + cityName + "?format=j1";
        this.weatherData = gson.fromJson(getResponseString(), WeatherData.class);

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

    public String getResponseString() {
        if (responseString == null) {
            try {
                responseString = getJSONString();
            } catch (IOException e) {
                // Handle the exception if needed
                e.printStackTrace();
            }
        }
        return responseString;
    }

    public WeatherData getWeatherData() {

        return weatherData;

    }

    public CurrentCondition getCurrentCondition() {
        this.currentCondition = weatherData.getCurrentCondition();
        return currentCondition;
    }

    public NearestArea getNearestArea() {
        this.nearestArea = weatherData.getNearestArea();
        return nearestArea;
    }

    public AreaName getAreaName() {
        this.areaName = weatherData.getNearestArea().getAreaName();
        return areaName;
    }

    public Country getCountry() {
        this.country = getNearestArea().getCountry();
        return country;
    }

}
