package bayern.steinbrecher.checkedElements.spinner;

import bayern.steinbrecher.utility.InitUtility;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CheckedDoubleSpinnerTest {
    @BeforeAll
    static void init() throws InterruptedException {
        InitUtility.initJavaFX();
    }

    @Test
    void verify() {
        final CheckedDoubleSpinner defaultSpinner = new CheckedDoubleSpinner(0, 1);
        // assertEquals(0, defaultSpinner.getValue()); // FIXME assert fails!
        final CheckedDoubleSpinnerValueFactory defaultSpinnerValueFactory
                = (CheckedDoubleSpinnerValueFactory) defaultSpinner.getValueFactory();
        assertTrue(defaultSpinnerValueFactory.isIncludeMin());
        assertEquals(Double.MIN_VALUE, defaultSpinnerValueFactory.getMin());
        assertEquals(Double.MAX_VALUE, defaultSpinnerValueFactory.getMax());

        final CheckedDoubleSpinner includeMinSpinner = new CheckedDoubleSpinner(-3.2, 10.5, 0.3, 1.1, true);
        assertTrue(includeMinSpinner.isChecked());
        assertTrue(includeMinSpinner.isValid());

        assertEquals(0.3, includeMinSpinner.getValue());
        includeMinSpinner.increment();
        assertEquals(1.4, includeMinSpinner.getValue());
        includeMinSpinner.increment(3);
        assertEquals(4.7, includeMinSpinner.getValue());
        includeMinSpinner.increment(100);
        assertEquals(10.5, includeMinSpinner.getValue());
        assertTrue(includeMinSpinner.isValid());
        includeMinSpinner.decrement(200);
        assertEquals(-3.2, includeMinSpinner.getValue());
        assertTrue(includeMinSpinner.isValid());

        final CheckedDoubleSpinner excludeMinSpinner = new CheckedDoubleSpinner(-3.2, 10.5, 0.3, 1.1, false);
        assertTrue(excludeMinSpinner.isChecked());
        assertTrue(excludeMinSpinner.isValid());
        excludeMinSpinner.decrement(5);
        assertEquals(-3.2, excludeMinSpinner.getValue());
        assertFalse(excludeMinSpinner.isValid());
    }
}
