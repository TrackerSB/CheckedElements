package bayern.steinbrecher.checkedElements.report;

import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.collections.ObservableList;

/**
 * @author Stefan Huber
 * @since 0.1
 */
public interface Reportable {

    /**
     * Returns the property holding whether the current is valid. It is valid if and and only if there are no reports
     * triggered which are classified as {@link ReportType#ERROR}.
     *
     * @return The property holding whether the current is valid.
     */
    ReadOnlyBooleanProperty validProperty();

    /**
     * Checks whether the current input is valid.
     *
     * @return {@code true} only if the current input is valid.
     */
    boolean isValid();

    /**
     * Returns all associated reports.
     *
     * @return All associated reports. The list is unmodifiable.
     */
    ObservableList<ReportEntry> getReports();

    /**
     * Adds a further report to the list of reports.NOTE Use this method in subclasses of implementing classes only!
     * Since multiple inheritance is not possible in Java this class has to be an interface and interfaces are not
     * allowed to have protected members.
     *
     * @param report The report to add.
     * @return {@code true} only if the {@link ReportEntry} was added.
     * @throws IllegalArgumentException Only if the given {@link ReportEntry} has a message which was already added.
     */
    //FIXME How to make it protected and final?
    @Deprecated
    /* protected final */ boolean addReport(ReportEntry report);

    /**
     * Adds all reports of the given {@link Reportable} to this {@link Reportable}. NOTE Use this method in subclasses
     * of implementing classes only! Since multiple inheritance is not possible in Java this class has to be an
     * interface and interfaces are not allowed to have protected members.
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
