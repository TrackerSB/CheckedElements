package bayern.steinbrecher.checkedElements.spinner;

import bayern.steinbrecher.checkedElements.CheckableControl;
import bayern.steinbrecher.checkedElements.CheckableControlBase;
import bayern.steinbrecher.checkedElements.report.ReportEntry;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.Control;
import javafx.scene.control.Skin;
import javafx.scene.paint.Color;

import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Represents a contribution spinner. It is a {@link CheckedDoubleSpinner} which has a further field for associating a
 * color to the contribution.
 *
 * @author Stefan Huber
 * @since 0.1
 */
public class ContributionField extends Control implements CheckableControl {

    private static final Logger LOGGER = Logger.getLogger(ContributionField.class.getName());
    private final CheckableControlBase<ContributionField> ccBase = new CheckableControlBase<>(this);
    private final double minValue;
    private final double maxValue;
    private final double initialValue;
    private final double amountToStepBy;
    private final boolean includeMinValue;
    private final ObjectProperty<Double> contributionProperty = new SimpleObjectProperty<>(null);
    private final ObjectProperty<Color> colorProperty = new SimpleObjectProperty<>(Color.TRANSPARENT);

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
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.initialValue = initialValue;
        this.amountToStepBy = amountToStepBy;
        this.includeMinValue = includeMinValue;

        skinProperty()
                .addListener((obs, oldSkin, newSkin) -> {
                    contributionProperty.unbind();
                    colorProperty.unbind();
                    if (newSkin != null) {
                        if (newSkin instanceof ContributionFieldSkin) {
                            ContributionFieldSkin contributionFieldSkin = (ContributionFieldSkin) newSkin;
                            contributionProperty.bind(contributionFieldSkin.contributionProperty());
                            colorProperty.bind(contributionFieldSkin.colorProperty());
                        } else {
                            LOGGER.log(Level.WARNING, String.format(
                                    "'%s' is an unsuitable skin since it does not inherit from '%s'",
                                    newSkin.getClass().getName(), ContributionFieldSkin.class.getName()));
                        }
                    }
                });
    }

    /**
     * @since 0.11
     */
    @Override
    protected Skin<?> createDefaultSkin() {
        return new ContributionFieldSkin(this, minValue, maxValue, initialValue, amountToStepBy, includeMinValue);
    }

    // Inherited {@link Reportable} properties

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
        return contributionProperty;
    }

    public Optional<Double> getContribution() {
        return Optional.ofNullable(contributionProperty().getValue());
    }

    public void setContribution(double contribution) {
        contributionProperty().set(contribution);
    }

    public ObjectProperty<Color> colorProperty() {
        return colorProperty;
    }

    public Color getColor() {
        return colorProperty().get();
    }

    public void setColor(Color color) {
        colorProperty().set(color);
    }
}
