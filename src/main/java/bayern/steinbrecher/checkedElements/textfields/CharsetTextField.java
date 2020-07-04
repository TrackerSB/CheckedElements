package bayern.steinbrecher.checkedElements.textfields;

import java.nio.charset.Charset;

import bayern.steinbrecher.checkedElements.report.ReportEntry;
import bayern.steinbrecher.checkedElements.report.ReportType;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

/**
 * Represents a {@link CheckedTextField} for entering a {@link Charset}. It also checks whether the current system
 * supports the entered {@link Charset}.
 *
 * @author Stefan Huber
 * @since 0.1
 */
public final class CharsetTextField extends CheckedTextField {

    private final BooleanProperty invalidCharset = new SimpleBooleanProperty(this, "invalidCharset");

    /**
     * Creates a new {@link CharsetTextField} with a maximum column count of {@link Integer#MAX_VALUE} and no initial
     * text.
     */
    public CharsetTextField() {
        this(Integer.MAX_VALUE);
    }

    /**
     * Creates a new {@link CharsetTextField} with a maximum column count of {@code maxColumnCount} and no initial text.
     *
     * @param maxColumnCount The maximum column count.
     */
    public CharsetTextField(int maxColumnCount) {
        this(maxColumnCount, "");
    }

    /**
     * Creates a new {@link CharsetTextField} with a maximum column count of {@code maxColumnCount} and the initial text
     * {@code text}.
     *
     * @param maxColumnCount The maximum column count.
     * @param text The initial text.
     */
    public CharsetTextField(int maxColumnCount, String text) {
        super(maxColumnCount, text);
        getStyleClass().add("charset-textfield");
        invalidCharset.bind(Bindings.createBooleanBinding(
                () -> !textProperty().get().isEmpty() && !Charset.isSupported(textProperty().get()), textProperty()));
        addReport(new ReportEntry("invalidCharset", ReportType.ERROR, invalidCharset));
    }
}
