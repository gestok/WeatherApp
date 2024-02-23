package plh.team1.weatherapp.api;

import plh.team1.weatherapp.model.CurrentCondition;

public class Test {

    public static void main(String[] args) {
        WeatherDataFetcher fetch = new WeatherDataFetcher("Thessaloniki");
        CurrentCondition cur = fetch.getCurrentCondition();
        System.out.println(cur.getTemp());

    }
}
