package bayern.steinbrecher.checkedElements.textfields.sepa;

import bayern.steinbrecher.checkedElements.report.ReportEntry;
import bayern.steinbrecher.checkedElements.report.ReportType;
import bayern.steinbrecher.checkedElements.textfields.SpecificRegexTextField;
import bayern.steinbrecher.javaUtility.SepaUtility;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

/**
 * Represents a {@link bayern.steinbrecher.checkedElements.textfields.CheckedTextField} which contains a BIC. Currently
 * it is completely the same but it adds an additional CSS style class.
 *
 * @author Stefan Huber
 * @since 0.1
 */
public class BicTextField extends SpecificRegexTextField {

    private final BooleanProperty invalidBic = new SimpleBooleanProperty(this, "invalidBic");

    /**
     * Constructs a new {@link BicTextField} with an max input length of {@link Integer#MAX_VALUE} and no initial
     * content.
     */
    public BicTextField() {
        this(Integer.MAX_VALUE);
    }

    /**
     * Constructs a new {@link BicTextField} with an max input length of {@code maxColumnCount} and no initial content.
     *
     * @param maxColumnCount The initial max input length.
     */
    public BicTextField(int maxColumnCount) {
        this(maxColumnCount, "");
    }

    /**
     * Constructs a new {@link BicTextField} with an max input length of {@code maxColumnCount} and {@code text} as
     * initial content.
     *
     * @param maxColumnCount The initial max input length.
     * @param text The initial content.
     */
    public BicTextField(int maxColumnCount, String text) {
        super(maxColumnCount, text, SepaUtility.BIC_REGEX, false);
        getStyleClass().add("bic-textfield");
        invalidBic.bind(Bindings.createBooleanBinding(
                () -> !SepaUtility.isValidBic(textProperty().get()), textProperty()));
        getStylesheets().add(BicTextField.class.getResource("bicTextField.css").toExternalForm());
        initProperties();
    }

    private void initProperties() {
        addReport(new ReportEntry("invalidBic", ReportType.ERROR, invalidBic));
    }
}
