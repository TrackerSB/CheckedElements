package bayern.steinbrecher.checkedElements.textfields.sepa;

import bayern.steinbrecher.checkedElements.report.ReportEntry;
import bayern.steinbrecher.checkedElements.report.ReportType;
import bayern.steinbrecher.checkedElements.textfields.SpecificRegexTextField;
import bayern.steinbrecher.sepaxmlgenerator.MessageId;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

/**
 * Represents a {@link bayern.steinbrecher.checkedElements.textfields.CheckedRegexTextField} for entering a message id.
 *
 * @author Stefan Huber
 * @since 0.1
 */
public final class MessageIdTextField extends SpecificRegexTextField {

    private final BooleanProperty invalidMessageId = new SimpleBooleanProperty(this, "invalidMessageId");

    /**
     * Constructs an {@link MessageIdTextField} with no initial content.
     */
    public MessageIdTextField() {
        this("");
    }

    /**
     * Constructs an {@link MessageIdTextField} with given content.
     *
     * @param text The initial content.
     */
    public MessageIdTextField(String text) {
        super(MessageId.MAX_CHAR_MESSAGE_ID, text, MessageId.MESSAGE_ID_REGEX);
        invalidMessageId.bind(Bindings.createBooleanBinding(
                () -> !new MessageId(textProperty().get()).isValid(), textProperty()));
        addReport(new ReportEntry("invalidMessageId", ReportType.ERROR, invalidMessageId));
        getStyleClass().add("messageIdTextField");
    }
}
