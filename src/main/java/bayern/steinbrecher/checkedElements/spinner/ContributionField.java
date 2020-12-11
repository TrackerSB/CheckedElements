package bayern.steinbrecher.checkedElements.spinner;

import bayern.steinbrecher.checkedElements.CheckableControl;
import bayern.steinbrecher.checkedElements.CheckableControlBase;
import bayern.steinbrecher.checkedElements.report.ReportEntry;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Control;
import javafx.scene.control.Skin;
import javafx.scene.paint.Color;

import java.util.Optional;

/**
 * Represents a contribution spinner. It is a {@link CheckedDoubleSpinner} which has a further field for associating a
 * color to the contribution.
 *
 * @author Stefan Huber
 * @since 0.1
 */
public class ContributionField extends Control implements CheckableControl {

    private final CheckableControlBase<ContributionField> ccBase = new CheckableControlBase<>(this);
    private final CheckedDoubleSpinner contributionSpinner;
    private final ColorPicker colorPicker = new ColorPicker(Color.TRANSPARENT);

    /**
     * Represents a combination of a spinner for entering a contribution and an associated color. The minimum value is
     * 0, the maximum value is 5000, the initial value is 10 and the amountToStepBy is 1. The default color associated
     * is {@link Color#TRANSPARENT}.
     *
     * @see CheckedDoubleSpinner#CheckedDoubleSpinner(double, double, double, double)
     */
    public ContributionField(double minValue, double maxValue, double initialValue, double amountToStepBy,
                             boolean includeMinValue) {
        super();
        contributionSpinner = new CheckedDoubleSpinner(
                minValue, maxValue, initialValue, amountToStepBy, includeMinValue);
        getStyleClass()
                .add("contribution-field");
    }

    @FXML
    private void initialize() {
        ccBase.addReports(contributionSpinner);
    }

    /**
     * @since 0.11
     */
    @Override
    protected Skin<?> createDefaultSkin() {
        return new ContributionFieldSkin(this);
    }

    // Inherited {@link Reportble} properties

    @Override
    public ObservableList<ReportEntry> getReports() {
        return ccBase.getReports();
    }

    @Override
    public boolean addReport(ReportEntry report) {
        return ccBase.addReport(report);
    }

    // Inherited {@link Checkable} properties

    @Override
    public BooleanProperty checkedProperty() {
        return ccBase.checkedProperty();
    }

    @Override
    public boolean isChecked() {
        return ccBase.isChecked();
    }

    @Override
    public void setChecked(boolean checked) {
        ccBase.setChecked(checked);
    }

    @Override
    public ReadOnlyBooleanProperty validProperty() {
        return ccBase.validProperty();
    }

    @Override
    public boolean isValid() {
        return validProperty().get();
    }

    // Additional properties
    public ObjectProperty<Double> contributionProperty() {
        return contributionSpinner.getValueFactory().valueProperty();
    }

    public Optional<Double> getContribution() {
        return Optional.ofNullable(contributionProperty().getValue());
    }

    public void setContribution(double contribution) {
        contributionProperty().set(contribution);
    }

    public ObjectProperty<Color> colorProperty() {
        return colorPicker.valueProperty();
    }

    public Color getColor() {
        return colorProperty().get();
    }

    public void setColor(Color color) {
        colorProperty().set(color);
    }
}
