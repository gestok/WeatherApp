package plh.team1.weatherapp.model;

import com.google.gson.annotations.SerializedName;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="WEATHERDATA")
public class CurrentCondition {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @OneToOne
    private City city;
    
    @Column(name = "humidity", nullable = false, length = 150)
    protected String humidity;
    
    @Column(name = "temp", nullable = false, length = 150)
    @SerializedName("temp_C")
    protected String tempC;
    
    @Column(name = "feels_like", nullable = false, length = 150)
    @SerializedName("FeelsLikeC")
    protected String feelsLikeC; 
    
    @Column(name = "uv_index", nullable = false, length = 150)
    protected String uvIndex;
    
    @Column(name = "wind_speed", nullable = false, length = 150)
    @SerializedName("windspeedKmph")
    protected String windspeed;
    
    protected String cloudcover;
    protected String localObsDateTime;
    protected String observation_time;
    protected String precipMM;
    protected String pressure;
    protected String visibility;
    protected String weatherCode;
    protected String winddir16Point;
    protected String winddirDegree;



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

    public String getTemp() {
        return tempC;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFeelsLikeC() {
        return feelsLikeC;
    }

    public void setFeelsLikeC(String feelsLikeC) {
        this.feelsLikeC = feelsLikeC;
    }

    public String getTempC() {
        return tempC;
    }

    public void setTempC(String tempC) {
        this.tempC = tempC;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }
    
    
    
    
}
