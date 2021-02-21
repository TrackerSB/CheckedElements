package bayern.steinbrecher.checkedElements;

import bayern.steinbrecher.checkedElements.report.ReportEntry;
import bayern.steinbrecher.checkedElements.report.Reportable;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.value.ObservableBooleanValue;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;

/**
 * Represents a {@link TableView} which is extended by the capability of reporting.
 *
 * @param <S> See {@link TableView}.
 * @author Stefan Huber
 * @since 0.1
 */
public class CheckedTableView<S> extends TableView<S> implements Reportable {

    private final CheckableControlBase<CheckedTableView<S>> ccBase = new CheckableControlBase<>(this);

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

    @Override
    public ObservableList<ReportEntry> getReports() {
        return ccBase.getReports();
    }

    @Override
    public boolean addReport(ReportEntry report) {
        return ccBase.addReport(report);
    }
}
