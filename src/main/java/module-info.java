module plh.team1.weatherapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires okhttp3;
    requires gson;
    requires java.desktop;
    requires jfreechart;
    requires jcommon;
    requires jakarta.persistence;

    opens plh.team1.weatherapp to javafx.fxml, gson;
    exports plh.team1.weatherapp;
}
