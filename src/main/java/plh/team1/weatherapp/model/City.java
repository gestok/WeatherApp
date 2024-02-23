package plh.team1.weatherapp.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

@Entity
public class City {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String country;

    @OneToMany(targetEntity = CurrentCondition.class)
    private Set<CurrentCondition> currentConditions = new HashSet<>();

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

    public City(String name, String country) {
        this.name = name;
        this.country = country;

    }

    public City() {
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

    public void setName(String name) {
        this.name = name;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<CurrentCondition> getCurrentConditions() {
        return currentConditions;
    }

    public void setCurrentConditions(Set<CurrentCondition> condition) {
        this.currentConditions = condition;
    }

    @Override
    public String toString() {
        return this.name + ", " + this.country;
    }
}
