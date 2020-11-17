package bayern.steinbrecher.checkedElements;

import bayern.steinbrecher.utility.InitUtility;
import javafx.collections.FXCollections;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CheckedComboBoxTest {
    @BeforeAll
    static void init() throws InterruptedException {
        InitUtility.initJavaFX();
    }

    @Test
    void verifyProperties(){
        final CheckedComboBox<?> emptyComboBox = new CheckedComboBox<>();
        assertTrue(emptyComboBox.isNothingSelected());
        assertTrue(emptyComboBox.isChecked());
        assertFalse(emptyComboBox.isValid());

        final CheckedComboBox<?> nonEmptyComboBox
                = new CheckedComboBox<>(FXCollections.observableList(List.of(1, 2, 3)));
        assertTrue(nonEmptyComboBox.isNothingSelected());
        assertTrue(nonEmptyComboBox.isChecked());
        assertFalse(nonEmptyComboBox.isValid());

        nonEmptyComboBox.getSelectionModel()
                .selectFirst();
        assertFalse(nonEmptyComboBox.isNothingSelected());
        assertTrue(nonEmptyComboBox.isChecked());
        assertTrue(nonEmptyComboBox.isValid());
    }
}
