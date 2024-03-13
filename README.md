# Weather EAPplication

## Overview

This GitHub project is a JavaFX-based weather application enabling users to check current weather conditions, search for weather data on specific cities, and analyze statistical information. The project is structured into three main tabs: **Search**, **Cities**, and **Stats**.

### Search Tab

The *Search* tab provides a real-time snapshot of the current weather for a selected city, based on free-form text input. Users can access details such as temperature, humidity, wind speed, visibility, and UV index. Additionally, users have the ability to save weather data for a specific city.


### Cities/Stats Tab

The *Cities/Stats* tab offers statistical weather data, providing insights into the most popular searches. A list of previously searched cities is displayed, allowing users to select a city and view detailed information. Users also have the option to generate a statistical report (PDF), delete a city, or modify a city's records

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
- A standalone app can be built using Maven Shade.
- Project was built on JDK 21.
