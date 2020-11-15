package bayern.steinbrecher.test.checkedElements.textfields;

import bayern.steinbrecher.checkedElements.textfields.NameField;
import bayern.steinbrecher.test.utility.InitUtility;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;
import java.util.stream.Collectors;

public class NameFieldTest {
    @BeforeAll
    public static void init() throws InterruptedException {
        InitUtility.initJavaFX();
    }

    @Test
    public void checkCreation() {
        final NameField nameField = new NameField();
        final Set<String> styleSheetNames = nameField.getStylesheets()
                .stream()
                .map(path -> path.split("/"))
                .filter(pathElements -> pathElements.length > 0)
                .map(pathElements -> pathElements[pathElements.length - 1])
                .collect(Collectors.toSet());
        Assertions.assertTrue(styleSheetNames.contains("checkedTextField.css"));
        Assertions.assertTrue(styleSheetNames.contains("nameField.css"));
        // TODO Check whether paths are external
        // TODO Verify (visual) correctness of the button
    }
}
