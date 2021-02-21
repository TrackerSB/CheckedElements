package bayern.steinbrecher.checkedElements;

import bayern.steinbrecher.checkedElements.report.ReportEntry;
import bayern.steinbrecher.checkedElements.report.Reportable;
import javafx.beans.property.ReadOnlyBooleanProperty;

/**
 * @author Stefan Huber
 * @since 0.1
 */
public interface CheckedControl extends Reportable {

    /**
     * Return the {@link ReadOnlyBooleanProperty} representing whether the current input is valid or not. It is valid
     * if it is checked and there are no reports of errors triggered.
     *
     * @return The {@link ReadOnlyBooleanProperty} representing whether the current input is valid.
     * @see #checkedProperty()
     */
    @Override
    ReadOnlyBooleanProperty validProperty();

    /**
     * For details see {@link Reportable#addReport(ReportEntry)}. Additionally reports are only shown if this control
     * {@link #isChecked()}.
     */
    @Override
    boolean addReport(ReportEntry report);

    ReadOnlyBooleanProperty checkedProperty();

    default boolean isChecked() {
        return checkedProperty()
                .get();
    }
}
