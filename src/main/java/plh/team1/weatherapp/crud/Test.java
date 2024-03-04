package plh.team1.weatherapp.crud;

import plh.team1.weatherapp.model.CityModel;
import plh.team1.weatherapp.serialization.WeatherData;
import plh.team1.weatherapp.api.Api;

public class Test {

    public static void main(String[] args) {
        
        Api api = new Api("Serres", "Greece");
        WeatherData wr12 = api.fetchWeatherData();        
        WeatherData wr1 = api.fetchWeatherData("Xanthi", "Greece");
        
        
        Repo repo = new Repo();
        repo.addData(wr12);
        repo.addData(wr1);
        
        
        
        
    }

}
