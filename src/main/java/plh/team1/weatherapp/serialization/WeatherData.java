package plh.team1.weatherapp.serialization;

// Java
import java.util.List;

// Gson
import com.google.gson.annotations.SerializedName;

public class WeatherData {

    @SerializedName("current_condition")
    private List<CurrentCondition> currentCondition;
    @SerializedName("nearest_area")
    private List<NearestArea> nearestArea;
    private List<Weather> weather;

    public CurrentCondition getCurrentCondition() {
        return currentCondition.get(0);
    }

    public NearestArea getNearestArea() {
        return nearestArea.get(0);
    }

    public Weather getWeather(int index) throws ArrayIndexOutOfBoundsException {
        return weather.get(index);
    }
}
