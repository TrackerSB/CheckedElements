package bayern.steinbrecher.checkedElements.textfields;

import javafx.beans.property.StringProperty;

/**
 * Represents a {@link CheckedTextField} which also checks whether the current text is matching a given regex. In
 * contrast to {@link SpecificRegexTextField} the regex can be changed.
 *
 * @author Stefan Huber
 * @since 0.1
 */
public class CheckedRegexTextField extends SpecificRegexTextField {

    /**
     * Constructs a new {@link CheckedRegexTextField} without initial content, maximum column count of
     * {@link Integer#MAX_VALUE} and an all accepting regex.
     */
    public CheckedRegexTextField() {
        this("");
    }

    /**
     * Creates a new {@link CheckedRegexTextField} with no initial content, a maximum column count of
     * {@link Integer#MAX_VALUE} and validated by {@code regex}.
     *
     * @param regex The regex for validating the input.
     */
    public CheckedRegexTextField(String regex) {
        this(Integer.MAX_VALUE, regex);
    }

    /**
     * Constructs a new {@link CheckedRegexTextField} with an max input length of {@code maxColumnCount}, no initial
     * content and regex {@code regex}.
     *
     * @param maxColumnCount The initial max input length.
     * @param regex The regex for validating the input.
     */
    public CheckedRegexTextField(int maxColumnCount, String regex) {
        this(maxColumnCount, "", regex);
    }

    /**
     * Constructs a new {@link CheckedRegexTextField} with an max input length of {@code maxColumnCount}, {@code text}
     * as initial content and regex {@code regex}.
     *
     * @param maxColumnCount The initial max input length.
     * @param text The initial content.
     * @param regex The regex for validating the input.
     */
    public CheckedRegexTextField(int maxColumnCount, String text, String regex) {
        this(maxColumnCount, text, regex, false);
    }

    /**
     * Constructs a new {@link CheckedRegexTextField} with an max input length of {@code maxColumnCount}, {@code text}
     * as initial content and regex {@code regex}.
     *
     * @param maxColumnCount The initial max input length.
     * @param text The initial content.
     * @param regex The regex for validating the input.
     * @param eliminateSpaces Indicates whether spaces have to be removed before checking using the given regex.
     */
    public CheckedRegexTextField(int maxColumnCount, String text, String regex, boolean eliminateSpaces) {
        super(maxColumnCount, text, regex, eliminateSpaces);
        getStyleClass().add("checked-regex-textfield");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StringProperty regexProperty() {
        return super.regexPropertyModifiable();
    }

    /**
     * Sets the regex used for validation.
     *
     * @param regex The new regex to be used for validation.
     */
    public void setRegex(String regex) {
        regexProperty().set(regex);
    }
}
