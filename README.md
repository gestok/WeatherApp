# Weather Application

## Overview

This GitHub project is a JavaFX-based weather application enabling users to check current weather conditions, search for weather data on specific cities, and analyze statistical information. The project is structured into three main tabs: **Search**, **Cities**, and **Stats**.

### Search Tab

The *Search* tab provides a real-time snapshot of the current weather for a selected city, based on free-form text input. Users can access details such as temperature, humidity, wind speed, visibility, and UV index. Additionally, users have the ability to save weather data for a specific city.

### Cities Tab

In the *Cities* tab, users can perform searches against a static JSON file containing popular Greek cities, along with their locations on a map.

### Stats Tab

The *Stats* tab offers statistical weather data, providing insights into the most popular searches for city weather data. A list of previously searched cities is displayed, allowing users to select a city and view detailed information. Users also have the option to generate a statistical report (PDF) or delete a city.

## Usage Instructions

To run the application in standalone mode, download the uberjar and follow these steps:

### Standalone
Open a terminal and run:
```bash
java -jar WeatherApp.jar
```

To run the application in you IDE, follow these steps:

1. Clone the repository to your local machine.
2. Open the project in your preferred Java IDE (Netbeans recommended)
3. Build and Run the application. Dependencies are resolved automatically.

## Dependencies

- All dependencies are declared in ```pom.xml``` and managed automatically by Maven Dependency System.
- Standalone app is built using Maven Shade.
- Project was built on JDK 21.


