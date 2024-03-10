package plh.team1.weatherapp;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import java.util.Date;

public class WeatherDataBeanTable {
    
    
    private SimpleIntegerProperty id = new SimpleIntegerProperty();
    private SimpleIntegerProperty humidity = new SimpleIntegerProperty();
    private SimpleIntegerProperty tempC = new SimpleIntegerProperty();
    private SimpleIntegerProperty uvIndex = new SimpleIntegerProperty();
    private SimpleIntegerProperty windSpeed = new SimpleIntegerProperty();
    private SimpleStringProperty weatherDesc = new SimpleStringProperty();
    private ObjectProperty<Date> wdDate = new SimpleObjectProperty<>(this, "wdDate");

    public WeatherDataBeanTable(int id, int humidity, int tempC, int uvIndex, int windSpeed, String weatherDesc, Date wdDate) {
        this.id.set(id);
        this.humidity.set(humidity);
        this.tempC.set(tempC);
        this.uvIndex.set(uvIndex);
        this.windSpeed.set(windSpeed);
        this.weatherDesc.set(weatherDesc);
        this.wdDate.set(wdDate);
    }    

    public int getId() {
        return id.get();
    }

    public void setId(SimpleIntegerProperty id) {
        this.id = id;
    }

    public int getHumidity() {
        return humidity.get();
    }

    public void setHumidity(SimpleIntegerProperty humidity) {
        this.humidity = humidity;
    }

    public int getTempC() {
        return tempC.get();
    }

    public void setTempC(SimpleIntegerProperty tempC) {
        this.tempC = tempC;
    }

    public int getWindSpeed() {
        return windSpeed.get();
    }

    public void setWindSpeed(SimpleIntegerProperty windSpeed) {
        this.windSpeed = windSpeed;
    }

    public String getWeatherDesc() {
        return weatherDesc.get();
    }

    public void setWeatherDesc(SimpleStringProperty weatherDesc) {
        this.weatherDesc = weatherDesc;
    }

    public Date getWdDate() {
        return wdDate.get();
    }

    public void setWdDate(ObjectProperty<Date> date) {
        this.wdDate = date;
    }
    
    
    public int getUvIndex() {
        return uvIndex.get();
    }

    public void setUvIndex(SimpleIntegerProperty uvIndex) {
        this.uvIndex = uvIndex;
    }
        
         
    public static WeatherDataBeanTable convertToWeatherDataBeanTable(WeatherData data) {
        int myid = data.getId();
        int myHumidity = data.getHumidity();
        int myTempC = data.getTempC();
        int myUVIndex = data.getUvindex();
        int myWindSpeed = data.getWindSpeed();
        String myWeatherDesc = data.getWeatherDesc();
        Date myDate = data.getWdDate();
       

        // Create a new CityBeanTable object using constructor-based instantiation
        return new WeatherDataBeanTable(myid, myHumidity, myTempC, myUVIndex, myWindSpeed, myWeatherDesc, myDate);
    }
}
