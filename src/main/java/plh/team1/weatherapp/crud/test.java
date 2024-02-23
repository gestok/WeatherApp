/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package plh.team1.weatherapp.crud;

import plh.team1.weatherapp.model.City;
import plh.team1.weatherapp.model.CurrentCondition;
import plh.team1.weatherapp.api.WeatherData;

/**
 *
 * @author geork
 */
public class test {

    public static void main(String[] args) {
        WeatherData response = WeatherData.fetchApi("Thessaloniki");
        CurrentCondition cur = response.getCurrentCondition();

        WeatherRepo weatherRepo = new WeatherRepo();
        CityRepo cityRepo = new CityRepo();
        
        
        weatherRepo.add(cur);       
        cityRepo.addCity(new City("Serres","Greece"));
        cityRepo.addCity(new City("Kilkis", "Greece"));
        cityRepo.addCity(new City("Ptolemaida","Greece"));
        cityRepo.addCity(new City("Serres", "Greece"));
        
        
        
        
        
        
        
        
        

    }

}
