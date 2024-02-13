module plh.team1.weatherapp {
    requires javafx.controls;
    requires javafx.fxml;

    opens plh.team1.weatherapp to javafx.fxml;
    exports plh.team1.weatherapp;
}
