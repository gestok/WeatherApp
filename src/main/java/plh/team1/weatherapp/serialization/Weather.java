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

    public String getDate() {
        return this.date;
    }

    public String getMinTempC() {
        return this.mintempC;
    }

    public String getMaxTempC() {
        return this.maxtempC;
    }
}
