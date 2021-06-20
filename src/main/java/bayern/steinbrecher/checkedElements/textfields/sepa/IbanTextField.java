package bayern.steinbrecher.checkedElements.textfields.sepa;

import bayern.steinbrecher.checkedElements.report.ReportEntry;
import bayern.steinbrecher.checkedElements.report.ReportType;
import bayern.steinbrecher.checkedElements.textfields.CheckedRegexTextField;
import bayern.steinbrecher.checkedElements.textfields.SpecificRegexTextField;
import bayern.steinbrecher.sepaxmlgenerator.IBAN;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

/**
 * Represents a {@link CheckedRegexTextField} specialized for IBANs.
 *
 * @author Stefan Huber
 * @since 0.1
 */
public final class IbanTextField extends SpecificRegexTextField {

    private final BooleanProperty invalidIban = new SimpleBooleanProperty(this, "invalidIban");

    /**
     * Constructs an {@link IbanTextField} with no initial content.
     */
    public IbanTextField() {
        this("");
    }

    /**
     * Constructs an {@link IbanTextField} with given content.
     *
     * @param text The initial content.
     */
    public IbanTextField(String text) {
        super(IBAN.MAX_CHAR_IBAN, text, IBAN.IBAN_REGEX, true);
        invalidIban.bind(Bindings.createBooleanBinding(
                () -> new IBAN(textProperty().get()).isValid(), textProperty()));
        addReport(new ReportEntry("invalidIban", ReportType.ERROR, invalidIban));
        getStyleClass().add("iban-textfield");
    }
}
