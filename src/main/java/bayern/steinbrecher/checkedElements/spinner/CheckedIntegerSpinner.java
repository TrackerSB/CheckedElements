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
     * Constructs a new {@link CheckedIntegerSpinner} with maximum value {@link Integer#MAX_VALUE} and minimum value
     * {@link Integer#MIN_VALUE}.
     *
     * @param initialValue The value of the Spinner when first instantiated, must be within the bounds of the min and
     * max arguments, or else the min value will be used.
     * @param amountToStepBy The amount to increment or decrement by, per step.
     */
    public CheckedIntegerSpinner(@NamedArg("initialValue") int initialValue,
            @NamedArg("amountToStepBy") int amountToStepBy) {
        this(Integer.MIN_VALUE, initialValue, amountToStepBy);
    }

    /**
     * Constructs a new {@link CheckedIntegerSpinner} with maximum value {@link Integer#MAX_VALUE}.
     *
     * @param min The minimum allowed value.
     * @param initialValue The value of the Spinner when first instantiated, must be within the bounds of the min and
     * max arguments, or else the min value will be used.
     * @param amountToStepBy The amount to increment or decrement by, per step.
     */
    public CheckedIntegerSpinner(@NamedArg("min") int min,
            @NamedArg("initialValue") int initialValue,
            @NamedArg("amountToStepBy") int amountToStepBy) {
        this(min, Integer.MAX_VALUE, initialValue, amountToStepBy);
    }

    /**
     * Constructs a new {@link CheckedIntegerSpinner}.
     *
     * @param min The minimum allowed value.
     * @param max The maximum allowed value.
     * @param initialValue The value of the Spinner when first instantiated, must be within the bounds of the min and
     * max arguments, or else the min value will be used.
     * @param amountToStepBy The amount to increment or decrement by, per step.
     */
    public CheckedIntegerSpinner(@NamedArg("min") int min,
            @NamedArg("max") int max,
            @NamedArg("initialValue") int initialValue,
            @NamedArg("amountToStepBy") int amountToStepBy) {
        super(new SpinnerValueFactory.IntegerSpinnerValueFactory(min, max, initialValue, amountToStepBy),
                PARSE_FUNCTION);
    }
}
