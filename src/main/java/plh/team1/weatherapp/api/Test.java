package plh.team1.weatherapp.api;

public class Test {

    public static void main(String[] args) {
        WeatherDataFetcher fetch = new WeatherDataFetcher("Thessaloniki");
        NearestArea city = fetch.getNearestArea();
        System.out.println(city.getAreaName().getCityName());

    }
}
