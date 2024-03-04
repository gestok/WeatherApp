package plh.team1.weatherapp.model;

import java.util.Objects;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.HashSet;
import java.util.Set;
import plh.team1.weatherapp.serialization.WeatherData;


/*Entity model class used for JPA that acts like a
parser of the needed serialized data
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

    @OneToMany(targetEntity = WeatherDataModel.class)
    private Set<WeatherDataModel> weatherDatas = new HashSet<>();

    public CityModel(WeatherData weatherData) {
        this.cityName = weatherData.getNearestArea().getAreaName().getCityName();
        this.countryName = weatherData.getNearestArea().getCountry().getName();
        this.latitude = weatherData.getNearestArea().getLatitude();
        this.longitude = weatherData.getNearestArea().getLongitude();
        this.population = weatherData.getNearestArea().getPopulation();
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

    
    // delete later
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CityModel)) {
            return false;
        }
        CityModel city = (CityModel) o;
        return Objects.equals(getCityName(), city.getCityName())
                && Objects.equals(getCountryName(), city.getCountryName());
    }
    
    @Override
    public int hashCode(){
        return Objects.hash(getCityName(), getCountryName());
    }

    @Override
    public String toString() {
        return "CityModel{" + ", id=" + id + ", cityName=" + cityName + ", countryName=" + countryName + '}';
    }

}
