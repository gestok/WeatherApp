package plh.team1.weatherapp;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.input.MouseEvent;

public class PrimaryController implements Initializable {

    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }

    @FXML
    private BarChart<String, Number> barChart;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        CategoryAxis xAxis = (CategoryAxis) barChart.getXAxis();
        NumberAxis yAxis = (NumberAxis) barChart.getYAxis();
        yAxis.setTickLabelsVisible(false); // Hide tick labels
        yAxis.setTickMarkVisible(false); // Hide tick marks
        yAxis.setMinorTickVisible(false); // Hide minor ticks

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.getData().add(new XYChart.Data<>("Humidity", 40 + Math.random() * 60));
        series.getData().add(new XYChart.Data<>("Visibility", 40 + Math.random() * 60));
        series.getData().add(new XYChart.Data<>("UV Index", 40 + Math.random() * 60));
        series.getData().add(new XYChart.Data<>("Wind Speed", 40 + Math.random() * 60));
        series.getData().add(new XYChart.Data<>("Cloud Cover", 40 + Math.random() * 60));

        barChart.getData().add(series);
        barChart.setStyle("-fx-background-color: transparent;");

        // Set the color of the bars programatically
        for (XYChart.Data<String, Number> data : series.getData()) {
            Double val = Double.parseDouble(data.getYValue().toString());
            if (val > 90) {
                data.getNode().setStyle("-fx-bar-fill: #71b1d6;");
            } else if (val > 60) {
                data.getNode().setStyle("-fx-bar-fill: #499bca;");
            } else if (val > 30) {
                data.getNode().setStyle("-fx-bar-fill: #3281ae;");
            } else {
                data.getNode().setStyle("-fx-bar-fill: #276487;");
            }
        }
    }
}
