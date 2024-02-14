package plh.team1.weatherapp;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class WeatherData {

    @SerializedName("current_condition")
    private List<CurrentCondition> currentCondition;

    public List<CurrentCondition> getCurrentCondition() {
        return currentCondition;
    }

    public static class CurrentCondition {
        @SerializedName("humidity")
        private int humidity;

        @SerializedName("temp_C")
        private int tempC;

        @SerializedName("uvIndex")
        private int uvIndex;

        @SerializedName("weatherDesc")
        private List<WeatherDescription> weatherDesc;

        @SerializedName("windspeedKmph")
        private int windspeedKmph;

        public int getHumidity() {
            return humidity;
        }

        public int getTempC() {
            return tempC;
        }

        public int getUvIndex() {
            return uvIndex;
        }

        public int getWindspeedKmph() {
            return windspeedKmph;
        }
        
        public String getWeatherDescValue() {
            if (weatherDesc != null && !weatherDesc.isEmpty()) {
                return weatherDesc.get(0).getValue();
            }
            return null;
        }
    }

    public static class WeatherDescription {
        @SerializedName("value")
        private String value;

        public String getValue() {
            return value;
        }
    }
}
