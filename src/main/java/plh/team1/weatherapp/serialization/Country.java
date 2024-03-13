package plh.team1.weatherapp.serialization;

// Gson
import com.google.gson.annotations.SerializedName;

public class Country {

    @SerializedName("value")
    private String name;

    public String getName() {
        return name;
    }
}
