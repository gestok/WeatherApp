/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package plh.team1.weatherapp.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import plh.team1.weatherapp.serialization.WeatherData;
import java.util.Date;

/*
weatherData model class for JPA
 */
@Entity
@Table(name = "WEATHERDATA")
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
    private Date date;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    

    public WeatherDataModel() {
    }

    public WeatherDataModel(WeatherData weatherData) {
        this.temperature = weatherData.getCurrentCondition().getTempC();
        this.windspeed = weatherData.getCurrentCondition().getWindspeed();
        this.uvIndex = weatherData.getCurrentCondition().getUvIndex();
        this.weatherDesc = weatherData.getCurrentCondition().getWeatherDesc();
        this.date = new Date();

    }

    public void setDate() {
        this.date = new Date();
    }

    public Date getDate() {
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "weatherDataModel{" + ", temperature=" + temperature + ", windspeed=" + windspeed + ", uvIndex=" + uvIndex + ", weatherDesc=" + weatherDesc + '}';
    }



}
