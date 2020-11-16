package bayern.steinbrecher.checkedElements;

import bayern.steinbrecher.checkedElements.textfields.CheckedPasswordField;
import bayern.steinbrecher.checkedElements.textfields.CheckedTextField;
import bayern.steinbrecher.checkedElements.textfields.NameField;
import bayern.steinbrecher.checkedElements.textfields.sepa.BicTextField;
import bayern.steinbrecher.checkedElements.textfields.sepa.CreditorIdTextField;
import bayern.steinbrecher.checkedElements.textfields.sepa.IbanTextField;
import bayern.steinbrecher.checkedElements.textfields.sepa.SepaNameField;
import bayern.steinbrecher.utility.InitUtility;
import javafx.scene.Parent;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;

// TODO Verify (visual) correctness of elements
public class CommonTests {
    @BeforeAll
    public static void init() throws InterruptedException {
        InitUtility.initJavaFX();
    }

    @ParameterizedTest
    @MethodSource("bayern.steinbrecher.checkedElements.TestCase#provideTestClasses")
    void checkCreation(Class<?> objectClass) {
        assertDoesNotThrow(() -> objectClass.getDeclaredConstructor().newInstance(),
                "Could not create instance of " + objectClass.getCanonicalName());
    }

    @ParameterizedTest
    @MethodSource("bayern.steinbrecher.checkedElements.TestCase#provideTestElements")
    void checkStylesheets(Parent node) {
        final Set<String> availableStylesheets = node.getStylesheets()
                .stream()
                .map(path -> path.split("/"))
                .filter(pathElements -> pathElements.length > 0)
                .map(pathElements -> pathElements[pathElements.length - 1])
                .collect(Collectors.toSet());

        List<String> expectedStylesheets = new ArrayList<>();

        // bayern.steinbrecher.checkedElements.textfields
        if (node instanceof CheckedPasswordField) {
            expectedStylesheets.add("checkedPasswordField.css");
        }
        if (node instanceof CheckedTextField) {
            expectedStylesheets.add("checkedTextField.css");
        }
        if (node instanceof NameField) {
            expectedStylesheets.add("nameField.css");
        }

        // bayern.steinbrecher.checkedElements.textfields.sepa
        if(node instanceof BicTextField){
            expectedStylesheets.add("bicTextField.css");
        }
        if(node instanceof CreditorIdTextField){
            expectedStylesheets.add("creditorIdTextField.css");
        }
        if(node instanceof IbanTextField){
            expectedStylesheets.add("ibanTextField.css");
        }
        if(node instanceof SepaNameField){
            expectedStylesheets.add("sepaNameField.css");
        }

        assertAll("Check included stylesheets of " + node.getClass().getCanonicalName(),
                expectedStylesheets.stream()
                        .map(stylesheet -> () -> assertTrue(availableStylesheets.contains(stylesheet),
                                stylesheet + " is missing"))
        );
        // TODO Check whether paths are external
    }
}
