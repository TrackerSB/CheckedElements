package bayern.steinbrecher.checkedElements.report;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Locale;

public enum ReportConditionResult {
    FULFILLED("success.png"),
    SKIPPED("next.png"),
    UNFULFILLED("error.png"),
    UNKNOWN("info.png");

    private static final int ICON_SIZE = 15;
    private final ImageView graphic;

    ReportConditionResult(String graphicResourcePath) {
        if (graphicResourcePath == null) {
            this.graphic = null;
        } else {
            Image graphicImage = new Image(graphicResourcePath, ICON_SIZE, ICON_SIZE, true, true);
            this.graphic = new ImageView(graphicImage);
            this.graphic.setSmooth(true);
        }
    }

    /**
     * Returns the graphical symbol used for representing the type.
     *
     * @return The graphical symbol used for representing the type.
     */
    public ImageView getGraphic() {
        return graphic;
    }

    /**
     * Returns the CSS class name associated with this type of entry.
     *
     * @return The CSS class name associated with this type of entry.
     */
    public String getCSSClass() {
        return "report-type-" + name().toLowerCase(Locale.ROOT);
    }
}
