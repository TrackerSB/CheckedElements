package bayern.steinbrecher.checkedElements.spinner;

import bayern.steinbrecher.utility.InitUtility;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CheckedIntegerSpinnerTest {
    @BeforeAll
    static void init() throws InterruptedException {
        InitUtility.initJavaFX();
    }

    @Test
    void verify() {
        final CheckedIntegerSpinner intSpinner = new CheckedIntegerSpinner(-3, 10, 0, 1);
        assertTrue(intSpinner.isChecked());
        assertTrue(intSpinner.isValid());

        assertEquals(0, intSpinner.getValue());
        intSpinner.increment();
        assertEquals(1, intSpinner.getValue());
        intSpinner.increment(3);
        assertEquals(4, intSpinner.getValue());
        intSpinner.increment(100);
        assertEquals(10, intSpinner.getValue());
        intSpinner.decrement(100);
        assertEquals(-3, intSpinner.getValue());
    }
}
