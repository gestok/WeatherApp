package plh.team1.weatherapp.serialization;

// Java
import java.util.List;

public class NearestArea {

    private List<AreaName> areaName;
    private List<Country> country;
    private String latitude;
    private String longitude;
    private String population;
    private List<Region> region;

    public String getLongitude() {
        return longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getPopulation() {
        return population;
    }

    public void setPopulation(String population) {
        this.population = population;
    }

    public AreaName getAreaName() {
        return areaName.get(0);
    }

    public Country getCountry() {
        return country.get(0);
    }

    public void setAreaName(AreaName areaName) {
        this.areaName.set(0, areaName);
    }

    public Region getRegion() {
        return region.get(0);
    }

}
