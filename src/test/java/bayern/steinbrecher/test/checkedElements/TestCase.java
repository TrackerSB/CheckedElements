package bayern.steinbrecher.test.checkedElements;

import bayern.steinbrecher.checkedElements.buttons.HelpButton;
import bayern.steinbrecher.checkedElements.textfields.NameField;
import org.junit.jupiter.params.provider.Arguments;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

public final class TestCase<T> {
    private static final Logger LOGGER = Logger.getLogger(TestCase.class.getName());
    public static final Collection<TestCase<?>> TEST_CASES = List.of(
            new TestCase<>(HelpButton.class),
            new TestCase<>(NameField.class)
    );

    private final Class<T> typeOfTestElement;

    private TestCase(Class<T> typeOfTestElement) {
        this.typeOfTestElement = typeOfTestElement;
    }

    public static Stream<Arguments> provideTestClasses(){
        return TEST_CASES.stream()
                .map(testCase -> testCase.typeOfTestElement)
                .map(Arguments::of);
    }

    public static Stream<Arguments> provideTestElements(){
        return TEST_CASES.stream()
                .map(testCase -> testCase.typeOfTestElement)
                .map(c -> {
                    try {
                        return c.getDeclaredConstructor();
                    } catch (NoSuchMethodException ex) {
                        LOGGER.log(Level.SEVERE, "Could not find appropriate constructor", ex);
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .map(c -> {
                    try {
                        return c.newInstance();
                    } catch (InstantiationException | IllegalAccessException | InvocationTargetException ex) {
                        LOGGER.log(Level.SEVERE, "Could not create instance of object to test", ex);
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .map(Arguments::of);
    }
}
