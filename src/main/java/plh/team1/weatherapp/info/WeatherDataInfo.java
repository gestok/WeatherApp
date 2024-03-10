package plh.team1.weatherapp.info;

// Java
import java.io.Serializable;
import java.util.List;

// Gson
import com.google.gson.annotations.SerializedName;


public class WeatherDataInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;
 
    @SerializedName("current_condition")
    private List<CurrentCondition> currentCondition;
    private List<Weather> weather;


    private String cityName;

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCityName() {
        return this.cityName;
    }

    public int getId() {
        return this.id;
    }

    public WeatherDataInfo.CurrentCondition getCurrentCondition() {
        return this.currentCondition.get(0);
    }

    public List<WeatherDataInfo.Weather> getWeather() {
        return this.weather;
    }

    public static class CurrentCondition implements Serializable {

       
        @SerializedName("FeelsLikeC")
        private int feelsLikeC;

    
        @SerializedName("humidity")
        private int humidity;

        
        @SerializedName("temp_C")
        private int tempC;

       
        @SerializedName("uvIndex")
        private String uvIndex;

        
        @SerializedName("visibility")
        private int visibility;

        @SerializedName("weatherDesc")
        private List<WeatherDescription> weatherDesc;

       
        @SerializedName("windspeedKmph")
        private int windspeedKmph;

        public int getFeelsLikeC() {
            return this.feelsLikeC;
        }

        public int getHumidity() {
            return this.humidity;
        }

        public int getTempC() {
            return this.tempC;
        }

        public String getUvIndex() {
            return this.uvIndex;
        }

        public int getVisibility() {
            return this.visibility;
        }

        public int getWindspeedKmph() {
            return this.windspeedKmph;
        }

        public String getWeatherDescValue() {
            if (this.weatherDesc != null && !this.weatherDesc.isEmpty()) {
                return this.weatherDesc.get(0).getValue();
            }
            return null;
        }
    }

    public static class WeatherDescription implements Serializable {

        @SerializedName("value")
        private String value;

        public String getValue() {
            return this.value;
        }
    }

    public static class Weather implements Serializable {

        @SerializedName("date")
        private String date;

        
        @SerializedName("maxtempC")
        private int maxtempC;

       
        @SerializedName("mintempC")
        private int mintempC;

        public String getDate() {
            return this.date;
        }

        public int getMinTempC() {
            return this.mintempC;
        }

        public int getMaxTempC() {
            return this.maxtempC;
        }
    }
}
