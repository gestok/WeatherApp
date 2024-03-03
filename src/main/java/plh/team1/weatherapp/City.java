package plh.team1.weatherapp;

public class City {

    private String name;
    private String admin;
    private String country;
    private double latitude;
    private double longitude;
    private int population;
    private boolean isFavourite;

    public City(String name, String admin, String country, double latitude, double longitude, int population, boolean isFavourite) {
        this.name = name;
        this.admin = admin;
        this.country = country;
        this.latitude = latitude;
        this.longitude = longitude;
        this.population = population;
        this.isFavourite = isFavourite;
    }

    public String getName() {
        return this.name;
    }

    public String getAdmin() {
        return this.admin;
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

    public boolean getIsFavourite() {
        return this.isFavourite;
    }

    public void setIsFavourite(boolean isFavourite) {
        this.isFavourite = isFavourite;
    }

    @Override
    public String toString() {
        return this.name + ", " + this.admin + ", " + this.country;
    }
}
