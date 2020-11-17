package bayern.steinbrecher.checkedElements.buttons;

import bayern.steinbrecher.utility.InitUtility;
import javafx.event.ActionEvent;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class HelpButtonTest {
    @BeforeAll
    static void init() throws InterruptedException {
        InitUtility.initJavaFX();
    }

    @Test
    void verify(){
        final HelpButton defaultButton = new HelpButton();
        assertEquals(defaultButton.getText(), "?");
        assertEquals(defaultButton.getHelpMessage(), "");

        final String label = "Click me";
        final String helpMessageA = "Here could have been help for users";
        final HelpButton customizedButton = new HelpButton(label, helpMessageA);
        assertEquals(customizedButton.getText(), label);
        assertEquals(customizedButton.getHelpMessage(), helpMessageA);

        final String helpMessageB = "This could have been another place for help";
        customizedButton.setHelpMessage(helpMessageB);
        assertEquals(customizedButton.getHelpMessage(), helpMessageB);

        customizedButton.getOnAction()
                .handle(new ActionEvent());
    }
}
