package plh.team1.weatherapp.utils;

// Java
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;
import java.util.Set;

// JavaFX
import javafx.scene.image.Image;

/**
 * This class includes many utilities methods that can be used throughout the
 * app without the need of rewriting them in every class.
 */
public class Utilities {

    // Constructor
    public Utilities() {
    }

    /**
     * Km/Hour to Beaufort conversion
     *
     * @param kmPerHour
     * @return int
     */
    public int kmPerHourToBeaufort(int kmPerHour) {
        if (kmPerHour < 1) {
            return 0;
        } else if (kmPerHour <= 5) {
            return 1;
        } else if (kmPerHour <= 11) {
            return 2;
        } else if (kmPerHour <= 19) {
            return 3;
        } else if (kmPerHour <= 28) {
            return 4;
        } else if (kmPerHour <= 38) {
            return 5;
        } else if (kmPerHour <= 49) {
            return 6;
        } else if (kmPerHour <= 61) {
            return 7;
        } else if (kmPerHour <= 74) {
            return 8;
        } else if (kmPerHour <= 88) {
            return 9;
        } else if (kmPerHour <= 102) {
            return 10;
        } else if (kmPerHour <= 117) {
            return 11;
        } else {
            return 12;
        }
    }

    /**
     * Returns the wind rank from 0-5.
     *
     * @param beaufort
     * @return int
     */
    public int windRank(int beaufort) {
        if (beaufort < 1) {
            return 0;
        } else if (beaufort <= 2) {
            return 1;
        } else if (beaufort <= 4) {
            return 2;
        } else if (beaufort <= 6) {
            return 3;
        } else if (beaufort <= 8) {
            return 4;
        } else {
            return 5;
        }
    }

    /**
     * Returns the sea wind rank from 0-5.
     *
     * @param beaufort
     * @return int
     */
    public int seaWindRank(int beaufort) {
        if (beaufort == 0) {
            return 0;
        } else if (beaufort <= 2) {
            return 1;
        } else if (beaufort <= 4) {
            return 2;
        } else if (beaufort <= 6) {
            return 3;
        } else if (beaufort <= 8) {
            return 4;
        } else {
            return 5;
        }
    }

    /**
     * Returns the humidity rank from 1-4.
     *
     * @param humidity
     * @return int
     */
    public int humidityRank(int humidity) {
        if (humidity <= 24) {
            return 1;
        } else if (humidity <= 49) {
            return 2;
        } else if (humidity <= 74) {
            return 3;
        } else {
            return 4;
        }
    }

    /**
     * Returns the UV Index rank as a metric.
     *
     * @param uvIndex
     * @return String
     */
    public String uvIndexRank(int uvIndex) {
        if (uvIndex < 3) {
            return "Low";
        } else if (uvIndex < 6) {
            return "Medium";
        } else if (uvIndex < 8) {
            return "High";
        } else if (uvIndex < 11) {
            return "Very High";
        } else {
            return "Extreme";
        }
    }

    /**
     * Method that gets a double and an int and returns a string of the double
     * with the specified amount of int decimals.
     *
     * @param value
     * @param decimals
     * @return String
     */
    public String formatToDecimals(double value, int decimals) {
        DecimalFormat decimalFormat = new DecimalFormat("0." + "0".repeat(decimals));
        return decimalFormat.format(value);
    }

    /**
     * Method that gets an int and returns a string of the number notated.
     *
     * @param value
     * @return String
     */
    public String toLocaleNotation(int value) {
        NumberFormat defaultFormat = NumberFormat.getNumberInstance();
        return defaultFormat.format(value);
    }

    /**
     * Method that returns the image node of the weather condition based on the
     * weather description value from wttr.in API response
     *
     * @param weatherDescValue
     * @return Image
     */
    public Image getWeatherIcon(String weatherDescValue) {
        Set<String> groupSunny = Set.of("Clear", "Sunny");
        Set<String> groupCloudy = Set.of("Partly cloudy", "Freezing fog", "Cloudy", "Overcast", "Mist", "Fog");
        Set<String> groupRain = Set.of("Freezing drizzle", "Patchy freezing drizzle possible",
                "Moderate or heavy rain shower", "Light rain shower", "Ice pellets", "Patchy rain possible",
                "Patchy light drizzle", "Light drizzle", "Light rain", "Moderate rain at times", "Moderate rain",
                "Heavy rain at times", "Heavy rain", "Light freezing rain", "Patchy light rain",
                "Moderate or heavy freezing rain");
        Set<String> groupSnow = Set.of("Patchy sleet possible", "Light sleet", "Light sleet showers",
                "Moderate or heavy sleet showers", "Moderate or heavy sleet", "Heavy freezing drizzle",
                "Patchy snow possible", "Blizzard", "Blowing snow", "Heavy snow", "Patchy heavy snow", "Moderate snow",
                "Patchy moderate snow", "Light snow", "Patchy light snow", "Light snow showers",
                "Moderate or heavy snow showers");
        Set<String> groupThunderRain = Set.of("Patchy light rain with thunder", "Moderate or heavy rain with thunder",
                "Torrential rain shower", "Thundery outbreaks possible");
        Set<String> groupThunderSnow = Set.of("Patchy light snow with thunder", "Moderate or heavy snow with thunder");

        if (groupSunny.contains(weatherDescValue)) {
            return new Image(getClass().getResourceAsStream("/gfx/states/sunny.png"));
        } else if (groupCloudy.contains(weatherDescValue)) {
            return new Image(getClass().getResourceAsStream("/gfx/states/cloudy.png"));
        } else if (groupRain.contains(weatherDescValue)) {
            return new Image(getClass().getResourceAsStream("/gfx/states/rainy.png"));
        } else if (groupSnow.contains(weatherDescValue)) {
            return new Image(getClass().getResourceAsStream("/gfx/states/snowy.png"));
        } else if (groupThunderRain.contains(weatherDescValue)) {
            return new Image(getClass().getResourceAsStream("/gfx/states/rainy-thunder.png"));
        } else if (groupThunderSnow.contains(weatherDescValue)) {
            return new Image(getClass().getResourceAsStream("/gfx/states/snowy-thunder.png"));
        } else {
            // Fallback to sunny, in order to not break application's runtime.
            return new Image(getClass().getResourceAsStream("/gfx/states/sunny.png"));
        }
    }

    /**
     * Method that gets a date string as parameter in the format of "yyyy-MM-dd"
     * and returns a date string in the format of "Day d Month".
     *
     * @param dateString
     * @return String
     */
    public String formatDate(String dateString) {
        LocalDate date = LocalDate.parse(dateString, DateTimeFormatter.ISO_LOCAL_DATE);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE d MMMM", Locale.ENGLISH);

        return date.format(formatter);
    }
}
