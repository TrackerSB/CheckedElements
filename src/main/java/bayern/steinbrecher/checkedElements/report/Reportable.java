package bayern.steinbrecher.checkedElements.report;

import bayern.steinbrecher.checkedElements.CheckedControl;
import javafx.beans.binding.BooleanExpression;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.value.ObservableBooleanValue;
import javafx.collections.ObservableList;
import javafx.scene.control.Skin;

/**
 * @author Stefan Huber
 * @since 0.1
 */
public interface Reportable {

    /**
     * Returns the property holding whether the current input is valid. It is valid iff there are no reports triggered
     * which are classified as {@link ReportType#ERROR}.
     *
     * @return The property holding whether the current input is valid.
     * @see #addReport(ReportEntry)
     */
    ReadOnlyBooleanProperty validProperty();

    default boolean isValid(){
        return validProperty()
                .get();
    }

    /**
     * Add a further constraint to the list of validity criteria without adding a report. NOTE Use this method in
     * subclasses of implementing classes or {@link Skin}s of {@link CheckedControl}s only! Since multiple inheritance
     * is not possible in Java this class has to be an interface and interfaces are not allowed to have protected
     * members.
     *
     * @param constraint The constraint to add.
     * @return {@code true} only if the {@link ObservableBooleanValue} was added.
     * @since 0.12
     * @see #addReport(ReportEntry)
     */
    //FIXME How to make it protected and final?
    @Deprecated
    /* protected final */ boolean addValidityConstraint(ObservableBooleanValue constraint);

    ObservableList<ReportEntry> getReports();

    /**
     * Adds a report to the set of reports. If the {@link ReportType} is {@link ReportType#ERROR} the negation of the
     * {@link BooleanExpression} is added to the set of conditions for validity of the input of this control.  Be careful about cyclic dependencies between the
     * {@link BooleanExpression}s of the reports. NOTE Use this method in subclasses of implementing classes only! Since
     * multiple inheritance is not possible in Java this class has to be an interface and interfaces are not allowed to
     * have protected members.
     *
     * @param report The report to add.
     * @return {@code true} only if the {@link ReportEntry} was added.
     * @throws IllegalArgumentException Only if the given {@link ReportEntry} has a message which was already added.
     * @see #addValidityConstraint(ObservableBooleanValue)
     */
    //FIXME How to make it protected and final?
    @Deprecated
    /* protected final */ boolean addReport(ReportEntry report);

    /**
     * Adds all reports of the given {@link Reportable} to this {@link Reportable}. NOTE Use this method in subclasses
     * of implementing classes (or their {@link Skin}s only! Since multiple inheritance is not possible in Java this
     * class has to be an interface and interfaces are not allowed to have protected members.
     *
     * @param reportable The {@link Reportable} whose messages have to be added to this one.
     */
    //FIXME How to make it protected and final?
    @Deprecated
    /* protected final */ default void addReports(Reportable reportable) {
        reportable.getReports()
                .forEach(this::addReport);
    }
}
