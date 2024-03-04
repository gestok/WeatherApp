package plh.team1.weatherapp.serialization;

import plh.team1.weatherapp.api.Api;
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
