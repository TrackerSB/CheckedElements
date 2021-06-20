package bayern.steinbrecher.checkedElements.textfields.sepa;

import bayern.steinbrecher.checkedElements.report.ReportEntry;
import bayern.steinbrecher.checkedElements.report.ReportType;
import bayern.steinbrecher.sepaxmlgenerator.CreditorId;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

/**
 * Represents a {@link javafx.scene.control.TextField} for inserting a creditor id.
 *
 * @author Stefan Huber
 */
public final class CreditorIdTextField extends CheckedSepaTextField {

    private final BooleanProperty invalidCreditorId = new SimpleBooleanProperty(this, "invalidCreditorId");

    /**
     * Creates a new empty {@link CreditorIdTextField}.
     */
    public CreditorIdTextField() {
        this("");
    }

    /**
     * Creates a new {@link CreditorIdTextField} with initial text {@code text}.
     *
     * @param text The initial content of this field.
     */
    public CreditorIdTextField(String text) {
        super(Integer.MAX_VALUE, text);
        invalidCreditorId.bind(Bindings.createBooleanBinding(
                () -> new CreditorId(textProperty().get()).isValid(), textProperty()));
        addReport(new ReportEntry("invalidCreditorId", ReportType.ERROR, invalidCreditorId));
        getStyleClass().add("creditorid-textfield");
    }
}
