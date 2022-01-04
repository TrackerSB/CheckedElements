package bayern.steinbrecher.checkedElements.spinner;

import java.util.Optional;
import java.util.function.Function;

import javafx.beans.NamedArg;
import javafx.scene.control.SpinnerValueFactory;

/**
 * Represents a spinner for double values which sets a css attribute when the inserted value is not valid.
 *
 * @author Stefan Huber
 * @since 0.1
 */
public class CheckedIntegerSpinner extends CheckedSpinner<Integer> {

    private static final Function<String, Optional<Integer>> PARSE_FUNCTION = value -> {
        try {
            return Optional.of(Integer.parseInt(value));
        } catch (NumberFormatException ex) {
            return Optional.empty();
        }
    };

    /**
     * @since 0.13
     */
    public CheckedIntegerSpinner() {
        super(new SpinnerValueFactory.IntegerSpinnerValueFactory(Integer.MIN_VALUE, Integer.MAX_VALUE, 0, 1),
                PARSE_FUNCTION);
    }
}
