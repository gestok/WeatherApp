package plh.team1.weatherapp;

import com.google.gson.annotations.SerializedName;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.util.List;


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
    
    @Column(name = "CITYNAME")
    private String cityName;
    
    public void setCityName(String cityName){
        this.cityName = cityName;
    }
    
    public String getCityName() {
        return cityName;
    }
    
    
    public int getId() {
            return id;
        }

    public WeatherData.CurrentCondition getCurrentCondition() {
        return currentCondition.get(0);
    }
    
  
    
    public static class CurrentCondition implements Serializable {
        
        
        @Column(name = "HUMIDITY")
        @SerializedName("humidity")
        private int humidity;
        
        @Column(name = "TEMP_C")
        @SerializedName("temp_C")
        private int tempC;
        
        @Column(name = "UVINDEX")
        @SerializedName("uvIndex")
        private int uvIndex;
        
        @SerializedName("weatherDesc")
        private List<WeatherDescription> weatherDesc;
        
        @Column(name = "WINDSPEEDKMPH")
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

    public static class WeatherDescription implements Serializable {
        @Column(name = "WEATHERDESC")
        @SerializedName("value")
        private String value;

        public String getValue() {
            return value;
        }
    }
}