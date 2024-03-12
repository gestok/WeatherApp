package plh.team1.weatherapp.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Entity
@Table(name = "WEATHERDATA")
@NamedQueries({
    @NamedQuery(name = "WeatherData.findAll", query = "SELECT w FROM WeatherData w"),
    /*
    @NamedQuery(name = "WeatherData.findById", query = "SELECT w FROM WeatherData w WHERE w.id = :id"),
    @NamedQuery(name = "WeatherData.findByHumidity", query = "SELECT w FROM WeatherData w WHERE w.humidity = :humidity"),
    @NamedQuery(name = "WeatherData.findByTempC", query = "SELECT w FROM WeatherData w WHERE w.tempC = :tempC"),
    @NamedQuery(name = "WeatherData.findByUvIndex", query = "SELECT w FROM WeatherData w WHERE w.uvIndex = :uvindex"),
    @NamedQuery(name = "WeatherData.findByWindSpeed", query = "SELECT w FROM WeatherData w WHERE w.windSpeed = :windspeedkmph"),
    @NamedQuery(name = "WeatherData.findByWeatherDesc", query = "SELECT w FROM WeatherData w WHERE w.weatherDesc = :weatherdesc"),
    */
    @NamedQuery(name = "WeatherData.findDuplicate", query = "SELECT COUNT(w) FROM WeatherData w WHERE w.cityId = :cityId AND w.wdDate = :wdDate"),
    @NamedQuery(name = "WeatherData.findByWddate", query = "SELECT w FROM WeatherData w WHERE w.wdDate = :wdDate"),
    @NamedQuery(name = "WeatherData.findByCityId", query = "SELECT w FROM WeatherData w WHERE w.cityId = :cityId")})
public class WeatherData implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "HUMIDITY")
    private Integer humidity;
    @Column(name = "TEMP_C")
    private Integer tempC;
    @Column(name = "UVINDEX")
    private Integer uvIndex;
    @Column(name = "WINDSPEEDKMPH")
    private Integer windSpeed;
    @Column(name = "WEATHERDESC")
    private String weatherDesc;
    @Basic(optional = false)
    @Column(name = "WD_DATE")
//    @Temporal(TemporalType.DATE)
    private String wdDate;
    @Basic(optional = false)
    @Column(name = "CITY_ID")
    private int cityId;

    public WeatherData() {
    }

    public WeatherData(Integer id) {
        this.id = id;
    }

    public WeatherData(Integer id, String wdDate, int cityId) {
        this.id = id;
        this.wdDate = wdDate;
        this.cityId = cityId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getHumidity() {
        return humidity;
    }

    public void setHumidity(Integer humidity) {
        this.humidity = humidity;
    }

    public Integer getTempC() {
        return tempC;
    }

    public void setTempC(Integer tempC) {
        this.tempC = tempC;
    }

    public Integer getUvindex() {
        return uvIndex;
    }

    public void setUvindex(Integer uvindex) {
        this.uvIndex = uvindex;
    }

    public Integer getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(Integer windSpeed) {
        this.windSpeed = windSpeed;
    }

    public String getWeatherDesc() {
        return weatherDesc;
    }

    public void setWeatherDesc(String weatherdesc) {
        this.weatherDesc = weatherdesc;
    }

    public String getWdDate() {
        return wdDate;
    }

    public void setWdDate(String wdDate) {
        this.wdDate = wdDate;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof WeatherData)) {
            return false;
        }
        WeatherData other = (WeatherData) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.uptobottom.WeatherData[ id=" + id + " ]";
    }
    
}
