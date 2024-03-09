package plh.team1.weatherapp.crud;

import java.util.Date;
import plh.team1.weatherapp.model.WeatherDataModel;
import plh.team1.weatherapp.api.Api;
import plh.team1.weatherapp.model.CityModel;
import plh.team1.weatherapp.serialization.WeatherData;
import plh.team1.weatherapp.model.WeatherDataModel;

public class Test {
    
    
    
    public static void main(String[] args) {
        
        //api call
        Api api = new Api("Thessaloniki");        
        //
        WeatherData wr = api.fetchWeatherData();
        WeatherData wr2 = api.fetchWeatherData();
        WeatherData wr3 = api.fetchWeatherData("Patra", "greece");
        WeatherData wr4 = api.fetchWeatherData("Kavala", "greece");
        Repo repo = new Repo();
        
        
        CityModel citymodel = new CityModel(wr);
        CityModel citymodel2 = new CityModel(wr2);
        CityModel citymodel3 = new CityModel(wr3);
        CityModel citymodel4 = new CityModel(wr4);
        
        WeatherDataModel wdm = new WeatherDataModel(wr);
        WeatherDataModel wdm2 = new WeatherDataModel(wr2);
        WeatherDataModel wdm3 = new WeatherDataModel(wr3);
        WeatherDataModel wrm4 = new WeatherDataModel(wr4);
        
        repo.addWeatherData(wdm3);
        repo.addWeatherData(wdm);
        repo.addWeatherData(wdm2);
        repo.addWeatherData(wrm4);
       
        //this is the way of adding a new city to the repo in order to ensure IDs
        citymodel = repo.addCity(citymodel);
        citymodel2 = repo.addCity(citymodel2);
        citymodel3 = repo.addCity(citymodel3);
        citymodel4 = repo.addCity(citymodel4);
        repo.addWeatherDataToCity(citymodel.getId(), wdm);
        repo.addWeatherDataToCity(citymodel2.getId(), wdm2);
        repo.addWeatherDataToCity(citymodel3.getId(), wdm3);
        repo.addWeatherDataToCity(citymodel4.getId(), wrm4);
        repo.setFavourite(citymodel2.getId(), true);
        repo.setFavourite(citymodel3.getId(), true);
        
        for (WeatherDataModel wd:repo.findByCity(citymodel.getId())){
            System.out.println(wd);
        }
        repo.findByCity(citymodel.getId());
        wrm4 = repo.updateTemperature(wrm4.getWeatherDataId(), "100");
        wrm4 = repo.updateUvIndex(wrm4.getWeatherDataId(), "23");
        wrm4 = repo.updateWeatherDesc(wrm4.getWeatherDataId(), "very nice weather yes");
        wrm4 = repo.updateWindSpeed(wrm4.getWeatherDataId(), "132");
        
        



        
        
                
        
        
    }
    
}
