package bayern.steinbrecher.checkedElements.spinner;

import bayern.steinbrecher.utility.InitUtility;
import javafx.scene.control.TextField;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class CheckedSpinnerTest {
    @BeforeAll
    static void init() throws InterruptedException {
        InitUtility.initJavaFX();
    }

    @Test
    void verify(){
        final CheckedSpinner<?> checkedSpinner = new CheckedIntegerSpinner(-3, 10, 0, 1);

        final TextField editor = checkedSpinner.getEditor();
        editor.setText("9");
        editor.commitValue();
        assertEquals(9, checkedSpinner.getValue());
        editor.setText("Not a number");
        assertFalse(checkedSpinner.isValid());
        editor.commitValue();
        assertFalse(checkedSpinner.isValid()); // TODO Check whether noNumber report is triggered
        assertEquals(9, checkedSpinner.getValue());
        editor.setText("");
        assertFalse(checkedSpinner.isValid());
        editor.commitValue();
        assertFalse(checkedSpinner.isValid()); // TODO Check whether missingInput report is triggered
    }
}
