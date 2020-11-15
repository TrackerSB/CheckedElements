package bayern.steinbrecher.checkedElements.textfields.sepa;

import bayern.steinbrecher.checkedElements.report.ReportEntry;
import bayern.steinbrecher.checkedElements.report.ReportType;
import bayern.steinbrecher.checkedElements.textfields.CheckedRegexTextField;
import bayern.steinbrecher.checkedElements.textfields.SpecificRegexTextField;
import bayern.steinbrecher.javaUtility.SepaUtility;
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
        super(SepaUtility.MAX_CHAR_IBAN, text, SepaUtility.IBAN_REGEX, true);
        invalidIban.bind(Bindings.createBooleanBinding(
                () -> !SepaUtility.isValidIban(textProperty().get()), textProperty()));
        addReport(new ReportEntry("invalidIban", ReportType.ERROR, invalidIban));
        getStyleClass().add("iban-textfield");
        getStylesheets().add(IbanTextField.class.getResource("ibanTextField.css").toExternalForm());
    }
}
