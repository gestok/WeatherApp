module plh.team1.weatherapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires okhttp3;
    requires gson;
    requires java.desktop;
    requires jfreechart;
    requires jcommon;

    opens plh.team1.weatherapp to javafx.fxml;
    exports plh.team1.weatherapp;
}
