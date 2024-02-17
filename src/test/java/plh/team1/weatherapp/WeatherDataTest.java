package plh.team1.weatherapp;

import com.google.gson.Gson;
import java.io.FileNotFoundException;
import java.io.FileReader;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class WeatherDataTest {
    
    // Test method to verify that deserialization works.
    @Test
    public void testSerialization() {
        String filetoread = "./src/test/testfiles/sampleweatherdata.json";
        
        FileReader myjson = null;
        
        try {
            myjson = new FileReader(filetoread);
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
        }
        
        Gson gson = new Gson();
        
        WeatherData myData = gson.fromJson(myjson, WeatherData.class);
        
        // Since getCurrentCondition() returns a list, get the first element
        // A bit ugly, maybe refactor to abstract current condition inside WeatherData class
        WeatherData.CurrentCondition currentCondition = myData.getCurrentCondition();
        
        assertEquals("Asserting Humidity", 55, currentCondition.getHumidity());
        assertEquals("Asserting Temperature", 16, currentCondition.getTempC());
        assertEquals("Asserting UV Index", 3, currentCondition.getUvIndex());
        assertEquals("Weather Description", "Partly cloudy", currentCondition.getWeatherDescValue());
        assertEquals("Asserting Windspeed", 22, currentCondition.getWindspeedKmph());
    }
}
