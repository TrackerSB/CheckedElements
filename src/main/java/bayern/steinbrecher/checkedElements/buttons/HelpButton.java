package bayern.steinbrecher.checkedElements.buttons;

import bayern.steinbrecher.javaUtility.DialogCreationException;
import bayern.steinbrecher.javaUtility.DialogGenerator;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Represents a button which shows a help message on click. This button uses
 * {@link javafx.scene.control.ButtonBase#setOnAction(javafx.event.EventHandler)}. NOTE: Because this method is final it
 * cannot be overridden. That means: Don't call it on this button otherwise it may not work as expected.
 *
 * @author Stefan Huber
 * @since 0.1
 */
public class HelpButton extends Button {

    public static final String CSS_CLASS_HELP_BUTTON = "help-button";
    private static final DialogGenerator DIALOG_GENERATOR = new DialogGenerator();
    private static final Logger LOGGER = Logger.getLogger(HelpButton.class.getName());
    private static final ResourceBundle RESOURCE_BUNDLE
            = ResourceBundle.getBundle("bayern.steinbrecher.checkedElements.CheckedElements");
    private final StringProperty helpMessage = new SimpleStringProperty(this, "helpMessage", "");

    public HelpButton() {
        this("");
    }

    public HelpButton(String helpMessage) {
        this("?", helpMessage);
    }

    public HelpButton(String text, String helpMessage) {
        this(text, helpMessage, null);
    }

    public HelpButton(String text, String helpMessage, Node graphic) {
        super(text, graphic);
        setOnAction(aevt -> showHelpMessage());
        setHelpMessage(helpMessage);
        setFocusTraversable(false);
        getStyleClass().add(CSS_CLASS_HELP_BUTTON);
    }

    private void showHelpMessage() {
        String help = RESOURCE_BUNDLE.getString("help");
        String findHelp = RESOURCE_BUNDLE.getString("findHelp");
        try {
            Alert alert = DIALOG_GENERATOR.createMessageAlert(getHelpMessage(), findHelp, help, help);
            Platform.runLater(alert::show);
        } catch (DialogCreationException ex) {
            LOGGER.log(Level.WARNING, "Could not show help message dialog", ex);
        }
    }

    public StringProperty helpMessageProperty() {
        return helpMessage;
    }

    public void setHelpMessage(String helpMessage) {
        helpMessageProperty()
                .set(helpMessage);
    }

    public String getHelpMessage() {
        return helpMessageProperty()
                .get();
    }
}
