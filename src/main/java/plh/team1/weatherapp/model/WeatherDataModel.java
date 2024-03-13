package plh.team1.weatherapp.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import plh.team1.weatherapp.serialization.WeatherData;
import java.util.Date;

@Entity
@Table(name = "WEATHERDATA")
@NamedQuery(name = "find weatherdata by id", query = "Select s from WeatherDataModel s where s.id = :id")
public class WeatherDataModel {

    @Column(name = "temperature", nullable = false, length = 150)
    private String temperature;
    @Column(name = "windspeed", nullable = false, length = 150)
    private String windspeed;
    @Column(name = "uvIndex", nullable = false, length = 150)
    private String uvIndex;
    @Column(name = "weatherDesc", nullable = false, length = 150)
    private String weatherDesc;
    @Column(name = "date", nullable = false, length = 150)
    private String date;
    @Column(name = "highTemp", nullable = false, length = 150)
    private String highTemp;
    @Column(name = "lowTemp", nullable = false, length = 150)
    private String lowTemp;
    @Column(name = "feelIsLike")
    private String feelIsLike;
    @Column(name = "humidity")
    private String humidity;
    @Column(name = "visibility")
    private String visibility;
    @Column(name = "dateTime")
    private Date dateTime;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long weatherDataId;
    @ManyToOne
    private CityModel cityModel;

    public WeatherDataModel() {
    }

    public WeatherDataModel(WeatherData weatherData) {
        this.temperature = weatherData.getCurrentCondition().getTempC();
        this.windspeed = weatherData.getCurrentCondition().getWindspeed();
        this.uvIndex = weatherData.getCurrentCondition().getUvIndex();
        this.weatherDesc = weatherData.getCurrentCondition().getWeatherDesc();
        this.date = weatherData.getWeather(0).getDate();
        this.highTemp = weatherData.getWeather(0).getMaxTempC(); //when the model class is created the index for accessing dates is set to 0 
        this.lowTemp = weatherData.getWeather(0).getMinTempC();
        this.feelIsLike = weatherData.getCurrentCondition().getFeelIsLike();
        this.humidity = weatherData.getCurrentCondition().getHumidity();
        this.visibility = weatherData.getCurrentCondition().getVisibility();
        this.dateTime = new Date(); //date time in order to query for last saved results
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getWindspeed() {
        return windspeed;
    }

    public void setWindspeed(String windspeed) {
        this.windspeed = windspeed;
    }

    public String getUvIndex() {
        return uvIndex;
    }

    public void setUvIndex(String uvIndex) {
        this.uvIndex = uvIndex;
    }

    public String getWeatherDesc() {
        return weatherDesc;
    }

    public void setWeatherDesc(String weatherDesc) {
        this.weatherDesc = weatherDesc;
    }

    public long getWeatherDataId() {
        return weatherDataId;
    }

    public void setWeatherDataId(long weatherDataId) {
        this.weatherDataId = weatherDataId;
    }

    public CityModel getCityModel() {
        return cityModel;
    }

    public void setCityModel(CityModel cityModel) {
        this.cityModel = cityModel;
    }

    public String getHighTemp() {
        return highTemp;
    }

    public void setHighTemp(String highTemp) {
        this.highTemp = highTemp;
    }

    public String getLowTemp() {
        return lowTemp;
    }

    public void setLowTemp(String lowTemp) {
        this.lowTemp = lowTemp;
    }

    public String getFeelIsLike() {
        return feelIsLike;
    }

    public void setFeelIsLike(String feelIsLike) {
        this.feelIsLike = feelIsLike;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getVisibility() {
        return visibility;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }

    @Override
    public String toString() {
        return "WeatherDataModel{" + "temperature=" + temperature + ", windspeed=" + windspeed + ", uvIndex=" + uvIndex + ", weatherDesc=" + weatherDesc + ", date=" + date + ", highTemp=" + highTemp + ", lowTemp=" + lowTemp + ", feelIsLike=" + feelIsLike + ", weatherDataId=" + weatherDataId + ", cityModel=" + cityModel + '}';
    }

}
