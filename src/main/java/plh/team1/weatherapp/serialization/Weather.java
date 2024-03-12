/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package plh.team1.weatherapp.serialization;

import com.google.gson.annotations.SerializedName;


public class Weather {

    @SerializedName("date")
    private String date;

    @SerializedName("maxtempC")
    private String maxtempC;

    @SerializedName("mintempC")
    private String mintempC;
    
    private String sunHour;
    
    @SerializedName("totalSnow_cm")
    private String totalSnow;
    
    private String uvIndex;

    public String getDate() {
        return this.date;
    }    
    
    public String getMinTempC() {
        return this.mintempC;
    }

    public String getMaxTempC() {
        return this.maxtempC;
    }

    public String getSunHour() {
        return sunHour;
    }

    public String getTotalSnow() {
        return totalSnow;
    }

    public String getUvIndex() {
        return uvIndex;
    }
    
    
}
