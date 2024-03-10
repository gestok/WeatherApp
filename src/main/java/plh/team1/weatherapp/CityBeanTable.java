package plh.team1.weatherapp;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class CityBeanTable {

    private SimpleIntegerProperty id = new SimpleIntegerProperty();
    private SimpleStringProperty thisName = new SimpleStringProperty();
    private SimpleIntegerProperty timesSearched = new SimpleIntegerProperty();
    
    public CityBeanTable(int id, String thisName, int timesSearched) {
        this.id.set(id);
        this.thisName.set(thisName);
        this.timesSearched.set(timesSearched);
    }

    public void setId(SimpleIntegerProperty id) {
        this.id = id;
    }

    public void setThisName(SimpleStringProperty thisName) {
        this.thisName = thisName;
    }

    public void setTimesSearched(SimpleIntegerProperty timesSearched) {
        this.timesSearched = timesSearched;
    }
    
    public int getId() {
        return id.get();
    }

    public String getThisName() {
        return thisName.get();
    }

    public int getTimesSearched() {
        return timesSearched.get();
    }
     
    public static CityBeanTable convertToCityBeanTable(City city) {
        int myid = city.getId();
        String myname = city.getThisName();
        int myTimesSearched = city.getTimesSearched();
        // Other properties...

        // Create a new CityBeanTable object using constructor-based instantiation
        return new CityBeanTable(myid, myname, myTimesSearched);
    }
}
