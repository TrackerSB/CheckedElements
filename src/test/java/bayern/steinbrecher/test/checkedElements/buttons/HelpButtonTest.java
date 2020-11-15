package bayern.steinbrecher.test.checkedElements.buttons;

import bayern.steinbrecher.checkedElements.buttons.HelpButton;
import bayern.steinbrecher.test.utility.InitUtility;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class HelpButtonTest {
    @BeforeAll
    public static void init() throws InterruptedException {
        InitUtility.initJavaFX();
    }

    @Test
    public void checkCreation() {
        new HelpButton();
        // TODO Verify (visual) correctness of the button
    }
}
