package plh.team1.weatherapp.api;



import plh.team1.weatherapp.api.Api;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import java.util.List;
import plh.team1.weatherapp.model.CurrentCondition;

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

    
    public static WeatherData fetchApi(String city){
        Gson gson = new Gson();
        Api api = new Api(city);
        String responseString = api.getResponseString();
        return gson.fromJson(responseString, WeatherData.class);
               
        
    }    

 
    

}
