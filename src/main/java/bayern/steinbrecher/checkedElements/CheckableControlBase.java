package bayern.steinbrecher.checkedElements;

import bayern.steinbrecher.checkedElements.report.ReportEntry;
import bayern.steinbrecher.checkedElements.report.ReportType;
import bayern.steinbrecher.checkedElements.report.Reportable;
import bayern.steinbrecher.javaUtility.BindingUtility;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyBooleanWrapper;
import javafx.beans.property.ReadOnlyListWrapper;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableBooleanValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.css.PseudoClass;
import javafx.scene.Node;

/**
 * This class represents an implementation of {@link CheckableControl} which can be used for delegation.
 *
 * @param <C> The type of the control delegating to this class.
 * @author Stefan Huber
 * @since 0.1
 */
public class CheckableControlBase<C extends Node & Reportable> implements CheckableControl {
    private static final PseudoClass CHECKED_PSEUDO_CLASS = PseudoClass.getPseudoClass("checked");
    private static final PseudoClass INVALID_PSEUDO_CLASS = PseudoClass.getPseudoClass("invalid");

    private final ReadOnlyBooleanWrapper valid = new ReadOnlyBooleanWrapper(true) {
        @Override
        protected void invalidated() {
            super.invalidated();
            control.pseudoClassStateChanged(INVALID_PSEUDO_CLASS, !get());
        }
    };
    private final ObservableList<ObservableBooleanValue> validityConstraints = FXCollections.observableArrayList();
    private final ReadOnlyBooleanWrapper validityConstraintsFulfilled = new ReadOnlyBooleanWrapper();

    // NOTE 2021-06-06: The extractor is required to ensure updating tooltips listing triggered reports
    private final ReadOnlyListWrapper<ReportEntry> reports
            = new ReadOnlyListWrapper<>(this, "reports",
            FXCollections.observableArrayList(re -> new Observable[]{re.reportTriggeredProperty()}));

    private final BooleanProperty checked = new SimpleBooleanProperty(true) {
        @Override
        protected void invalidated() {
            super.invalidated();
            control.pseudoClassStateChanged(CHECKED_PSEUDO_CLASS, get());
        }
    };

    private final C control;

    /**
     * Creates a {@link CheckableControlBase} which can be used for delegating calls when implementing
     * {@link CheckableControl}.
     *
     * @param control The control to add pseudo classes to.
     */
    public CheckableControlBase(C control) {
        this.control = control;
        control.getStyleClass().add("checked-control-base");

        validityConstraints.addListener((InvalidationListener) obs -> {
            validityConstraintsFulfilled.bind(BindingUtility.reduceAnd(validityConstraints.stream()));
        });
        valid.bind(validityConstraintsFulfilled.or(checkedProperty().not()));
    }

    @Override
    public ObservableList<ReportEntry> getReports() {
        return FXCollections.unmodifiableObservableList(reports);
    }

    @Override
    public boolean addReport(ReportEntry report) {
        boolean isDuplicate = reports.stream()
                .anyMatch(entry -> entry.getMessageKey().equalsIgnoreCase(report.getMessageKey()));
        if (isDuplicate) {
            throw new IllegalArgumentException(
                    "A report for \"" + report.getMessageKey() + "\" is already registered.");
        } else {
            boolean gotAdded = reports.add(report);
            if (gotAdded && report.getType() == ReportType.ERROR) { //FIXME A change of the type breaks the validation.
                gotAdded &= validityConstraints.add(report.reportTriggeredProperty().not());
            }
            return gotAdded;
        }
    }

    @Override
    public ReadOnlyBooleanProperty validProperty() {
        return valid.getReadOnlyProperty();
    }

    @Override
    public boolean addValidityConstraint(ObservableBooleanValue constraint) {
        return validityConstraints.add(constraint);
    }

    @Override
    public BooleanProperty checkedProperty() {
        return checked;
    }
}
