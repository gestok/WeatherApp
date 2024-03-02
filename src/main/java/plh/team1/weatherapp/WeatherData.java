package plh.team1.weatherapp;

// Java
import java.io.Serializable;
import java.util.List;

// Gson
import com.google.gson.annotations.SerializedName;

// Jakarta
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "WEATHERDATA")
public class WeatherData implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private int id;
    // Τα annotations συνδέουν το όνομα πεδίου στο json με όνομα πεδίου στην
    // κλάση. Μπορούν να παραλειφθούν αν τα δύο αυτά ταυτίζονται
    @SerializedName("current_condition")
    private List<CurrentCondition> currentCondition;
    private List<Weather> weather;

    @Column(name = "CITYNAME")
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

    public WeatherData.CurrentCondition getCurrentCondition() {
        return this.currentCondition.get(0);
    }

    public List<WeatherData.Weather> getWeather() {
        return this.weather;
    }

    public static class CurrentCondition implements Serializable {

        @Column(name = "FEELSLIKEC")
        @SerializedName("FeelsLikeC")
        private int feelsLikeC;

        @Column(name = "HUMIDITY")
        @SerializedName("humidity")
        private int humidity;

        @Column(name = "TEMP_C")
        @SerializedName("temp_C")
        private int tempC;

        @Column(name = "UVINDEX")
        @SerializedName("uvIndex")
        private int uvIndex;

        @Column(name = "VISIBILITY")
        @SerializedName("visibility")
        private int visibility;

        @SerializedName("weatherDesc")
        private List<WeatherDescription> weatherDesc;

        @Column(name = "WINDSPEEDKMPH")
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

        public int getUvIndex() {
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

        @Column(name = "WEATHERDESC")
        @SerializedName("value")
        private String value;

        public String getValue() {
            return this.value;
        }
    }

    public static class Weather implements Serializable {

        @Column(name = "DATE")
        @SerializedName("date")
        private String date;

        @Column(name = "MAXTEMPC")
        @SerializedName("maxtempC")
        private int maxtempC;

        @Column(name = "MINTEMPC")
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
