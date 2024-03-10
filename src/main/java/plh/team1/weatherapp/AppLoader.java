package plh.team1.weatherapp;

// This class is a workaround for Maven Shade uberjar packaging
public class AppLoader {
    public static void main(String[] args) {
        App.main(args);
    }
}
