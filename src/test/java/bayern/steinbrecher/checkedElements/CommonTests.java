package bayern.steinbrecher.checkedElements;

import bayern.steinbrecher.utility.InitUtility;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

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
}
