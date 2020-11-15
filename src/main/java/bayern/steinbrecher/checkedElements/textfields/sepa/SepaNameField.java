package bayern.steinbrecher.checkedElements.textfields.sepa;

/**
 * @author Stefan Huber
 * @since 0.1
 */
public class SepaNameField extends CheckedSepaTextField {

    /**
     * Constructs a new {@link SepaNameField} with an max input length of {@link Integer#MAX_VALUE} and no initial
     * content.
     */
    public SepaNameField() {
        this(Integer.MAX_VALUE);
    }

    /**
     * Constructs a new {@link SepaNameField} with an max input length of {@code maxColumnCount} and no initial content.
     *
     * @param maxColumnCount The initial max input length.
     */
    public SepaNameField(int maxColumnCount) {
        this(maxColumnCount, "");
    }

    /**
     * Constructs a new {@link SepaNameField} with an max input length of {@code maxColumnCount} and {@code text} as
     * initial content.
     *
     * @param maxColumnCount The initial max input length.
     * @param text The initial content.
     */
    public SepaNameField(int maxColumnCount, String text) {
        super(maxColumnCount, text);
        getStyleClass().add("sepa-name-field");
        getStylesheets().add(SepaNameField.class.getResource("sepaNameField.css").toExternalForm());
    }
}
