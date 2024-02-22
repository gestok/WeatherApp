package plh.team1.weatherapp;

public class City {

    private String name;
    private String country;
    private double latitude;
    private double longitude;
    private int population;

    public City(String name, String country, double latitude, double longitude, int population) {
        this.name = name;
        this.country = country;
        this.latitude = latitude;
        this.longitude = longitude;
        this.population = population;
    }

    public String getName() {
        return this.name;
    }

    public String getCountry() {
        return this.country;
    }

    public double getLatitude() {
        return this.latitude;
    }

    public double getLongitude() {
        return this.longitude;
    }

    public int getPopulation() {
        return this.population;
    }

    @Override
    public String toString() {
        return this.name + ", " + this.country;
    }
}
