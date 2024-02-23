/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package plh.team1.weatherapp.api;

/**
 *
 * @author geork
 */
public class Test {
    
    
    
    
    
    
    public static void main(String[] args) {
        WeatherData weatherdata = WeatherData.fetchApi("Serres");
        NearestArea nearestArea = weatherdata.getNearestArea();
        AreaName city = nearestArea.getAreaName();
        
        System.out.println(city.getCityName());
    }
}
