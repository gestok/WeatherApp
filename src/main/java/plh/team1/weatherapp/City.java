package plh.team1.weatherapp;

// Jakarta
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "CITIES")
public class City {

    @Id
    private int id;
    private String name;
    private String country;
    private double latitude;
    private double longitude;
    private int population;
    private boolean isFavourite;

    public City(int id, String name, String country, double latitude, double longitude, int population, boolean isFavourite) {
        this.id = id;
        this.name = name;
        this.country = country;
        this.latitude = latitude;
        this.longitude = longitude;
        this.population = population;
        this.isFavourite = isFavourite;
    }

    public int getId() {
        return this.id;
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

    public boolean getIsFavourite() {
        return this.isFavourite;
    }

    public void setIsFavourite(boolean isFavourite) {
        this.isFavourite = isFavourite;
    }

    @Override
    public String toString() {
        return this.name + ", " + this.country;
    }
}
