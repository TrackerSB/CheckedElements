package bayern.steinbrecher.checkedElements.report;

import bayern.steinbrecher.utility.BindingUtility;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyBooleanWrapper;
import javafx.beans.property.ReadOnlyListWrapper;
import javafx.beans.value.ObservableBooleanValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.css.PseudoClass;
import javafx.scene.control.Control;

/**
 * Contains a basic implementation of {@link Reportable} which may be used for delegation.
 *
 * @author Stefan Huber
 * @param <C> The type of the control delegating to this class.
 * @since 0.1
 */
public class ReportableBase<C extends Control & Reportable> implements Reportable {

    private final ReadOnlyBooleanWrapper valid = new ReadOnlyBooleanWrapper(this, "valid");
    private final ObservableList<ObservableBooleanValue> validConditions = FXCollections.observableArrayList();

    private static final PseudoClass INVALID_PSEUDO_CLASS = PseudoClass.getPseudoClass("invalid");
    private final ReadOnlyBooleanWrapper invalid = new ReadOnlyBooleanWrapper(this, "invalid") {
        @Override
        protected void invalidated() {
            control.pseudoClassStateChanged(INVALID_PSEUDO_CLASS, get());
        }
    };
    private final C control;
    private final ReadOnlyListWrapper<ReportEntry> reports
            = new ReadOnlyListWrapper<>(this, "reports", FXCollections.observableArrayList());

    /**
     * Creates a {@link ReportableBase} which can be used for delegating calls when implementing {@link Reportable}.
     *
     * @param control The control to add pseudo classes and the {@link ReportBubble} to.
     */
    public ReportableBase(C control) {
        this.control = control;
        validConditions.addListener((ListChangeListener.Change<? extends ObservableBooleanValue> c) -> {
            valid.bind(createValidBinding());
        });
        invalid.bind(valid.not());
        validConditions.add(BindingUtility.TRUE_BINDING); //Trigger init of property valid
    }

    /**
     * Creates the binding for determining whether the input of the control is valid. It may be overridden in order to
     * extend the binding by further restrictions of its validity.
     *
     * @return The binding for determining whether the input of the control is valid.
     */
    protected BooleanBinding createValidBinding() {
        return BindingUtility.reduceAnd(validConditions.stream());
    }

    @Override
    public ObservableList<ReportEntry> getReports() {
        return FXCollections.unmodifiableObservableList(reports);
    }

    @Override
    public boolean addReport(ReportEntry report) {
        boolean isDuplicate = reports.stream()
                .anyMatch(entry -> entry.getMessage().equalsIgnoreCase(report.getMessage()));
        if (isDuplicate) {
            throw new IllegalArgumentException(
                    "A report for \"" + report.getMessage() + "\" is already registered.");
        } else {
            boolean gotAdded = reports.add(report);
            if (gotAdded && report.getType() == ReportType.ERROR) { //FIXME A change of the type breaks the validation.
                gotAdded &= validConditions.add(report.reportTriggeredProperty().not());
            }
            return gotAdded;
        }
    }

    @Override
    public ReadOnlyBooleanProperty validProperty() {
        return valid.getReadOnlyProperty();
    }

    @Override
    public boolean isValid() {
        return validProperty().get();
    }

    /**
     * Returns the property holding the inverse value of {@link #validProperty()}. This method may be used for
     * convenience.
     *
     * @return The property holding the inverse value of {@link #validProperty()}.
     */
    public ReadOnlyBooleanProperty invalidProperty() {
        return invalid.getReadOnlyProperty();
    }

    /**
     * Returns the opposite value of {@link #isValid()}. This method may be used for convenience.
     *
     * @return The opposite value of {@link #isValid()}.
     */
    public boolean isInvalid() {
        return invalidProperty().getValue();
    }
}
