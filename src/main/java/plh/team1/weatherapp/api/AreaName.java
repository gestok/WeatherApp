
package plh.team1.weatherapp.api;

import com.google.gson.annotations.SerializedName;


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
