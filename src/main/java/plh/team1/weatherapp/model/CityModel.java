package plh.team1.weatherapp.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.HashSet;
import java.util.Set;
import plh.team1.weatherapp.serialization.WeatherData;

/**
 * Entity model class used for JPA that acts like a parser of the needed
 * serialized data.
 */
@Entity
@Table(name = "CITY")
public class CityModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "cityname")
    private String cityName;

    @Column(name = "countryName")
    private String countryName;

    @Column(name = "latitude")
    private String latitude;

    @Column(name = "longitude")
    private String longitude;

    @Column(name = "population")
    private String population;

    private String admin_name;

    @Column(name = "favourite")
    private Boolean favourite;

    @Column(name = "times_searched")
    private int timesSearched;

    @OneToMany
    @JoinColumn(name = "citymodel_id")
    private Set<WeatherDataModel> weatherDatas = new HashSet<>();

    public CityModel() {
    }

    public CityModel(WeatherData weatherData) {
        this.cityName = weatherData.getNearestArea().getAreaName().getCityName();
        this.countryName = weatherData.getNearestArea().getCountry().getName();
        this.latitude = weatherData.getNearestArea().getLatitude();
        this.longitude = weatherData.getNearestArea().getLongitude();
        this.population = weatherData.getNearestArea().getPopulation();
        this.admin_name = weatherData.getNearestArea().getRegion().getAdminName();
        this.favourite = false;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getPopulation() {
        return population;
    }

    public void setPopulation(String population) {
        this.population = population;
    }

    public Set<WeatherDataModel> getWeatherDatas() {
        return weatherDatas;
    }

    public void setWeatherDatas(Set<WeatherDataModel> weatherDatas) {
        this.weatherDatas = weatherDatas;
    }

    public Boolean getFavourite() {
        return favourite;
    }

    public void setFavourite(Boolean favourite) {
        this.favourite = favourite;
    }

    public int getTimesSearched() {
        return timesSearched;
    }

    public void setTimesSearched(int timesSearched) {
        this.timesSearched = timesSearched;
    }

    public String getAdmin_name() {
        return admin_name;
    }

    public void setAdmin_name(String admin_name) {
        this.admin_name = admin_name;
    }

    public void incrementTimesSearched() {
        this.timesSearched++;
    }

    @Override
    public String toString() {
        return cityName + ", " + countryName + ", " + admin_name;
    }

}
