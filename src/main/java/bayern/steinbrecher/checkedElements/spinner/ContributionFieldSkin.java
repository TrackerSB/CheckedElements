package bayern.steinbrecher.checkedElements.spinner;

import javafx.beans.property.ObjectProperty;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.SkinBase;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

/**
 * @author Stefan Huber
 * @since 0.11
 */
public class ContributionFieldSkin extends SkinBase<ContributionField> {
    private final CheckedDoubleSpinner contributionSpinner;
    private final ColorPicker colorPicker = new ColorPicker(Color.TRANSPARENT);

    protected ContributionFieldSkin(ContributionField control, double minValue, double maxValue, double initialValue,
                                    double amountToStepBy, boolean includeMinValue) {
        super(control);

        contributionSpinner = new CheckedDoubleSpinner(
                minValue, maxValue, initialValue, amountToStepBy, includeMinValue);

        HBox row = new HBox(contributionSpinner, colorPicker);
        row.prefHeightProperty().bind(getSkinnable().prefHeightProperty());
        row.prefWidthProperty().bind(getSkinnable().prefWidthProperty());
        getChildren().add(row);
        getSkinnable()
                .getStyleClass()
                .add("contribution-field");

        control.addReports(contributionSpinner);
    }

    public ObjectProperty<Double> contributionProperty() {
        return contributionSpinner.getValueFactory().valueProperty();
    }

    public ObjectProperty<Color> colorProperty() {
        return colorPicker.valueProperty();
    }
}
