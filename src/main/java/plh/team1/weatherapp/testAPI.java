package plh.team1.weatherapp;

public class testAPI {
    
    public static void main(String[] args) {
        
        Api myApiCall = new Api("Crete");
        System.out.println(myApiCall.getResponseString());
        
    }
    
}
