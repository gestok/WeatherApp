package plh.team1.weatherapp.serialization;


import com.google.gson.annotations.SerializedName;

public class AreaName {

    @SerializedName("value")
    private String cityName;

    public String getCityName() {
        return cityName;
    }


}

