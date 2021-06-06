package bayern.steinbrecher.checkedElements.report;

import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyBooleanWrapper;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ObservableValue;

import java.util.ResourceBundle;

/**
 * Represents an immutable entry of a report which can be used with {@link ReportBubble}.
 *
 * @author Stefan Huber
 * @since 0.1
 */
public final class ReportEntry {

    private static final ResourceBundle fallbackResources
            = ResourceBundle.getBundle("bayern.steinbrecher.checkedElements.CheckedElements");
    public static ResourceBundle resources;
    private final ReadOnlyStringWrapper messageKey = new ReadOnlyStringWrapper(this, "message");
    private final ReadOnlyObjectWrapper<ReportType> type = new ReadOnlyObjectWrapper<>(this, "type");
    private final ReadOnlyBooleanWrapper reportTriggered = new ReadOnlyBooleanWrapper(this, "reportTriggered");
    private final ReadOnlyObjectWrapper<ReportConditionResult> reportResult
            = new ReadOnlyObjectWrapper<>(this, "reportResult");

    public ReportEntry(String messageKey, ReportType type, ObservableValue<? extends Boolean> reportTrigger) {
        this.messageKey.set(messageKey);
        this.type.set(type);
        this.reportTriggered.bind(reportTrigger);
    }

    /**
     * @since 0.12
     */
    public ReadOnlyStringProperty messageKeyProperty() {
        return messageKey.getReadOnlyProperty();
    }

    /**
     * @since 0.12
     */
    public String getMessageKey() {
        return messageKeyProperty()
                .get();
    }

    /**
     * @since 0.12
     */
    public static String getMessage(String messageKey) {
        if (resources != null && resources.containsKey(messageKey)) {
            return resources.getString(messageKey);
        }
        if (fallbackResources.containsKey(messageKey)) {
            return fallbackResources.getString(messageKey);
        }
        return messageKey;
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

    public ReadOnlyObjectProperty<ReportConditionResult> reportResultProperty() {
        return reportResult.getReadOnlyProperty();
    }

    public ReportConditionResult getReportResult() {
        return reportResultProperty()
                .get();
    }
}
