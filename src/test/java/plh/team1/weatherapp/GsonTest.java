package plh.team1.weatherapp;

import com.google.gson.Gson;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
//import plh.team1.weatherapp.WeatherData;

public class GsonTest {

    public static void main(String[] args) throws IOException {
        String filetoread = "./src/test/testfiles/sampleweatherdata.json";
        FileReader myjson = null;
        
        try {
            myjson = new FileReader(filetoread);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        
        Gson gson = new Gson();
        
        WeatherData myData = gson.fromJson(myjson, WeatherData.class);
        
        // Since getCurrentCondition() returns a list, get the first element
        // A bit ugly, maybe refactor to abstract current condition inside WeatherData class
        WeatherData.CurrentCondition currentCondition = myData.getCurrentCondition().get(0);
        
        // Print field values in stdout
        System.out.println("Humidity: " + currentCondition.getHumidity());
        System.out.println("Temperature: " + currentCondition.getTempC());
        System.out.println("UV Index: " + currentCondition.getUvIndex());
        System.out.println("Weather Description: " + currentCondition.getWeatherDescValue());
        System.out.println("Windspeed Kmph: " + currentCondition.getWindspeedKmph());
        
    }
    
   
    
}
