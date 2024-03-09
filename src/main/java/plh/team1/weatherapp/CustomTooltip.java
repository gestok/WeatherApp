package plh.team1.weatherapp;

// JavaFX
import javafx.scene.control.Tooltip;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.util.Duration;

/**
 * A custom Tooltip class that used to create a customized version from JavaFX's
 * tooltip and attach it to a GUI element.
 */
public class CustomTooltip extends Tooltip {

    public CustomTooltip(String text) {
        super(text);
        initialize();
    }

    private void initialize() {
        // Appearance
        setStyle("-fx-text-fill: #276487;"
                + "-fx-font-family: \"Roboto Regular\";"
                + "-fx-background-color: #ebfbfe;"
                + "-fx-border-color: #dddddd;"
                + "-fx-border-width: 0.8;"
                + "-fx-border-style: solid;"
                + "-fx-border-radius: 8;"
                + "-fx-background-radius: 8;"
                + "-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.1), 8, 0, 0, 2.8);");

        // Behavior
        setShowDelay(Duration.millis(300));
        setHideDelay(Duration.millis(100));
        setAutoHide(true);
    }

}
