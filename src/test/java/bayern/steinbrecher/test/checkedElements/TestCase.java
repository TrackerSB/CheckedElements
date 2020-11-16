package bayern.steinbrecher.test.checkedElements;

import bayern.steinbrecher.checkedElements.CheckedComboBox;
import bayern.steinbrecher.checkedElements.CheckedDatePicker;
import bayern.steinbrecher.checkedElements.CheckedTableView;
import bayern.steinbrecher.checkedElements.buttons.HelpButton;
import bayern.steinbrecher.checkedElements.report.ConditionReportTable;
import bayern.steinbrecher.checkedElements.spinner.CheckedDoubleSpinner;
import bayern.steinbrecher.checkedElements.spinner.CheckedIntegerSpinner;
import bayern.steinbrecher.checkedElements.spinner.CheckedSpinner;
import bayern.steinbrecher.checkedElements.spinner.ContributionField;
import bayern.steinbrecher.checkedElements.textfields.CharsetTextField;
import bayern.steinbrecher.checkedElements.textfields.CheckedPasswordField;
import bayern.steinbrecher.checkedElements.textfields.CheckedRegexTextField;
import bayern.steinbrecher.checkedElements.textfields.CheckedTextField;
import bayern.steinbrecher.checkedElements.textfields.NameField;
import bayern.steinbrecher.checkedElements.textfields.SpecificRegexTextField;
import bayern.steinbrecher.checkedElements.textfields.sepa.BicTextField;
import bayern.steinbrecher.checkedElements.textfields.sepa.CheckedSepaTextField;
import bayern.steinbrecher.checkedElements.textfields.sepa.CreditorIdTextField;
import bayern.steinbrecher.checkedElements.textfields.sepa.IbanTextField;
import bayern.steinbrecher.checkedElements.textfields.sepa.MessageIdTextField;
import bayern.steinbrecher.checkedElements.textfields.sepa.SepaNameField;
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
            // bayern.steinbrecher.checkedElements
            new TestCase<>(CheckedComboBox.class),
            new TestCase<>(CheckedDatePicker.class),
            new TestCase<>(CheckedTableView.class),
            // bayern.steinbrecher.checkedElements.buttons
            new TestCase<>(HelpButton.class),
            // bayern.steinbrecher.checkedElements.report
            new TestCase<>(ConditionReportTable.class),
            // bayern.steinbrecher.checkedElements.spinner
            new TestCase<>(CheckedDoubleSpinner.class),
            new TestCase<>(CheckedIntegerSpinner.class),
            new TestCase<>(CheckedSpinner.class),
            new TestCase<>(ContributionField.class),
            // bayern.steinbrecher.checkedElements.textfields
            new TestCase<>(CharsetTextField.class),
            new TestCase<>(CheckedPasswordField.class),
            new TestCase<>(CheckedRegexTextField.class),
            new TestCase<>(CheckedTextField.class),
            new TestCase<>(NameField.class),
            new TestCase<>(SpecificRegexTextField.class),
            // bayern.steinbrecher.checkedElements.textfields
            new TestCase<>(BicTextField.class),
            new TestCase<>(CheckedSepaTextField.class),
            new TestCase<>(CreditorIdTextField.class),
            new TestCase<>(IbanTextField.class),
            new TestCase<>(MessageIdTextField.class),
            new TestCase<>(SepaNameField.class)
    );

    private final Class<T> typeOfTestElement;

    private TestCase(Class<T> typeOfTestElement) {
        this.typeOfTestElement = typeOfTestElement;
    }

    public static Stream<Arguments> provideTestClasses() {
        return TEST_CASES.stream()
                .map(testCase -> testCase.typeOfTestElement)
                .map(Arguments::of);
    }

    public static Stream<Arguments> provideTestElements() {
        return TEST_CASES.stream()
                .map(testCase -> testCase.typeOfTestElement)
                .map(c -> {
                    try {
                        return c.getDeclaredConstructor();
                    } catch (NoSuchMethodException ex) {
                        LOGGER.log(Level.WARNING, "Could not find appropriate constructor for "
                                + c.getCanonicalName() + ". Test results may be incomplete.", ex);
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .map(c -> {
                    try {
                        return c.newInstance();
                    } catch (InstantiationException | IllegalAccessException | InvocationTargetException ex) {
                        LOGGER.log(Level.WARNING, "Could not create instance for "
                                + c.getDeclaringClass().getCanonicalName() + ". Test results may be incomplete.", ex);
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .map(Arguments::of);
    }
}
