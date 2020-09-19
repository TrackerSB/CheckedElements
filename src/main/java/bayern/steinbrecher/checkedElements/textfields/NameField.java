package bayern.steinbrecher.checkedElements.textfields;

/**
 * Represents a {@link CheckedTextField} which is meant to be used for inserting names. Currently the main purpose is to
 * apply further specific CSS rules.
 *
 * @author Stefan Huber
 * @since 0.1
 */
public class NameField extends CheckedTextField {

    /**
     * Constructs a new {@link NameField} with an max input length of {@link Integer#MAX_VALUE} and no initial content.
     */
    public NameField() {
        this(Integer.MAX_VALUE);
    }

    /**
     * Constructs a new {@link NameField} with an max input length of {@code maxColumnCount} and no initial content.
     *
     * @param maxColumnCount The initial max input length.
     */
    public NameField(int maxColumnCount) {
        this(maxColumnCount, "");
    }

    /**
     * Constructs a new {@link NameField} with an max input length of {@code maxColumnCount} and {@code text} as initial
     * content.
     *
     * @param maxColumnCount The initial max input length.
     * @param text The initial content.
     */
    public NameField(int maxColumnCount, String text) {
        super(maxColumnCount, text);

        getStyleClass().add("name-field");
        getStylesheets().add(NameField.class.getResource("nameField.css").getPath());
    }
}
