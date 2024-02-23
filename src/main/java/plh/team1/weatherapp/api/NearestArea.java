package plh.team1.weatherapp.api;

import com.google.gson.annotations.SerializedName;
import java.util.List;



class NearestArea {
    @SerializedName("areaName")
    private List<AreaName> areaName;
    private List<Country> country;

    public AreaName getAreaName() {
        return areaName.get(0);
    }
    
    public Country getCountry(){
        return country.get(0);
    }

    public void setAreaName(List<AreaName> areaName) {
        this.areaName = areaName;
    }
    
}
