package bayern.steinbrecher.checkedElements.report;

import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyBooleanWrapper;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ObservableValue;

/**
 * Represents an immutable entry of a report which can be used with {@link ReportBubble}.
 *
 * @author Stefan Huber
 * @since 0.1
 */
public final class ReportEntry {

    private final ReadOnlyStringWrapper message = new ReadOnlyStringWrapper(this, "message");
    private final ReadOnlyObjectWrapper<ReportType> type = new ReadOnlyObjectWrapper<>(this, "type");
    private final ReadOnlyBooleanWrapper reportTriggered = new ReadOnlyBooleanWrapper(this, "reportTriggered");
    private final ReadOnlyObjectWrapper<ReportConditionResult> reportResult
            = new ReadOnlyObjectWrapper<>(this, "reportResult");

    public ReportEntry(String message, ReportType type, ObservableValue<? extends Boolean> reportTrigger) {
        this.message.set(message);
        this.type.set(type);
        this.reportTriggered.bind(reportTrigger);
    }

    public ReadOnlyStringProperty messageProperty() {
        return message.getReadOnlyProperty();
    }

    public String getMessage(){
        return messageProperty()
                .get();
    }

    public ReadOnlyObjectProperty<ReportType> typeProperty() {
        return type.getReadOnlyProperty();
    }

    public ReportType getType() {
        return typeProperty()
                .get();
    }

    public ReadOnlyBooleanProperty reportTriggeredProperty() {
        return reportTriggered.getReadOnlyProperty();
    }

    public boolean isReportTriggered() {
        return reportTriggeredProperty()
                .get();
    }

    public ReadOnlyObjectProperty<ReportConditionResult> reportResultProperty(){
        return reportResult.getReadOnlyProperty();
    }

    public ReportConditionResult getReportResult(){
        return reportResultProperty()
                .get();
    }
}
