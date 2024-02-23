package plh.team1.weatherapp.api;

import plh.team1.weatherapp.model.CurrentCondition;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class WeatherData {

    @SerializedName("current_condition")
    private List<CurrentCondition> currentCondition;
    @SerializedName("nearest_area")
    private List<NearestArea> nearestArea;

    public CurrentCondition getCurrentCondition() {
        return currentCondition.get(0);
    }

    public NearestArea getNearestArea() {
        return nearestArea.get(0);
    }

}

class NearestArea {

    @SerializedName("areaName")
    private List<AreaName> areaName;
    private List<Country> country;

    public AreaName getAreaName() {
        return areaName.get(0);
    }

    public Country getCountry() {
        return country.get(0);
    }

    public void setAreaName(List<AreaName> areaName) {
        this.areaName = areaName;
    }

}

class Country {

    private String country;

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

}

class AreaName {

    @SerializedName("value")
    private String cityName;

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

}
