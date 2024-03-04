package plh.team1.weatherapp.serialization;

import java.util.ArrayList;
import com.google.gson.annotations.SerializedName;

public class CurrentCondition {

    private String humidity;
    @SerializedName("temp_C")
    private String tempC;
    @SerializedName("FeelsLikeC")
    private String feelsLikeC;
    private String uvIndex;
    @SerializedName("windspeedKmph")
    private String windspeed;
    private String cloudcover;
    private String localObsDateTime;
    private String observation_time;
    private String precipMM;
    private String pressure;
    private String visibility;
    private String weatherCode;
    private String winddir16Point;
    private String winddirDegree;
    private ArrayList<WeatherDesc> weatherDesc;

    public String getWeatherDesc() {
        return weatherDesc.get(0).getWeatherDesc();
    }

    public String getFeelIsLike() {
        return feelsLikeC;
    }

    public String getCloudcover() {
        return cloudcover;
    }

    public String getHumidity() {
        return humidity;
    }

    public String getLocalObsDateTime() {
        return localObsDateTime;
    }

    public String getObservation_time() {
        return observation_time;
    }

    public String getPrecipMM() {
        return precipMM;
    }

    public String getPressure() {
        return pressure;
    }

    public String getUvIndex() {
        return uvIndex;
    }

    public String getVisibility() {
        return visibility;
    }

    public String getWeatherCode() {
        return weatherCode;
    }

    public String getWinddir16Point() {
        return winddir16Point;
    }

    public String getWinddirDegree() {
        return winddirDegree;
    }

    public String getWindspeed() {
        return windspeed;
    }

    public String get_feelIsLike() {
        return feelsLikeC;
    }

    public String getFeelsLikeC() {
        return feelsLikeC;
    }


    public String getTempC() {
        return tempC;
    }

    public void setTempC(String tempC) {
        this.tempC = tempC;
    }

    private static class WeatherDesc {

        @SerializedName("value")
        private String weatherDesc;

        public String getWeatherDesc() {
            return weatherDesc;
        }

    }
}
