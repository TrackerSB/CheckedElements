package bayern.steinbrecher.checkedElements.report;

import java.util.Locale;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Represents classifications of report entries.
 *
 * @author Stefan Huber
 * @since 0.1
 */
public enum ReportType {
    //NOTE These enums have to be ordered based on their severity starting from the lowest.
    /**
     * Marks not yet classified report entries.
     */
    UNDEFINED(null),
    /**
     * Marks a report entry as additional information.
     */
    INFO("info.png"),
    /**
     * Marks a report entry as warning.
     */
    WARNING("warning.png"),
    /**
     * Marks a report entry as errors.
     */
    ERROR("error.png");

    private static final int ICON_SIZE = 15;
    private final ImageView graphic;

    ReportType(String graphicResourcePath) {
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
