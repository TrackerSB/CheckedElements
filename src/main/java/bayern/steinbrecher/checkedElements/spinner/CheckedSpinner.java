package bayern.steinbrecher.checkedElements.spinner;

import bayern.steinbrecher.checkedElements.CheckableControl;
import bayern.steinbrecher.checkedElements.CheckableControlBase;
import bayern.steinbrecher.checkedElements.report.ReportEntry;
import bayern.steinbrecher.checkedElements.report.ReportType;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.value.ObservableBooleanValue;
import javafx.collections.ObservableList;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;

import java.util.Optional;
import java.util.function.Function;

/**
 * Extends the class {@link Spinner} with a valid and checked property.
 *
 * @param <T> The type of the values to spin.
 * @author Stefan Huber
 * @since 0.1
 */
public class CheckedSpinner<T> extends Spinner<T> implements CheckableControl {

    private final CheckableControlBase<CheckedSpinner<T>> ccBase = new CheckableControlBase<>(this);

    /**
     * Constructs a new {@link CheckedSpinner}.
     *
     * @param factory The factory generating values.
     * @param parser  The function to parse the content of the {@link Spinner}.
     */
    public CheckedSpinner(SpinnerValueFactory<T> factory, Function<String, Optional<T>> parser) {
        super(factory);
        initProperties(factory, parser);
        getStyleClass().add("checked-spinner");
    }

    private void initProperties(SpinnerValueFactory<T> factory, Function<String, Optional<T>> parser) {
        ccBase.addReport(new ReportEntry("noNumber", ReportType.ERROR, Bindings.createBooleanBinding(() -> {
            Optional<T> parsed = parser.apply(getEditor().textProperty().get());
            parsed.ifPresent(factory::setValue);
            return parsed.isEmpty();
        }, getEditor().textProperty())));
        ccBase.addReport(new ReportEntry("inputMissing", ReportType.ERROR, valueProperty().isNull()));
    }

    @Override
    public ObservableList<ReportEntry> getReports() {
        return ccBase.getReports();
    }

    @Override
    public boolean addReport(ReportEntry report) {
        return ccBase.addReport(report);
    }

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
        return ccBase.isValid();
    }

    @Override
    public boolean addValidityConstraint(ObservableBooleanValue constraint) {
        return ccBase.addValidityConstraint(constraint);
    }
}
