package plh.team1.weatherapp.serialization;

// Gson
import com.google.gson.annotations.SerializedName;

public class Region {

    @SerializedName("value")
    private String adminName;

    public String getAdminName() {
        return adminName;
    }
}
