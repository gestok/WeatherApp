package plh.team1.weatherapp;

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
}
