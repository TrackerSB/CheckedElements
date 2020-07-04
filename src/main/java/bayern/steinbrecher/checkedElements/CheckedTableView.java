package bayern.steinbrecher.checkedElements;

import bayern.steinbrecher.checkedElements.report.ReportEntry;
import bayern.steinbrecher.checkedElements.report.Reportable;
import bayern.steinbrecher.checkedElements.report.ReportableBase;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;

/**
 * Represents a {@link TableView} which is extended by the capability of reporting.
 *
 * @author Stefan Huber
 * @param <S> See {@link TableView}.
 * @since 0.1
 */
public class CheckedTableView<S> extends TableView<S> implements Reportable {

    private final ReportableBase<CheckedTableView<S>> reportableBase = new ReportableBase<>(this);

    @Override
    public ReadOnlyBooleanProperty validProperty() {
        return reportableBase.validProperty();
    }

    @Override
    public boolean isValid() {
        return reportableBase.isValid();
    }

    @Override
    public ObservableList<ReportEntry> getReports() {
        return reportableBase.getReports();
    }

    @Override
    public boolean addReport(ReportEntry report) {
        return reportableBase.addReport(report);
    }
}
