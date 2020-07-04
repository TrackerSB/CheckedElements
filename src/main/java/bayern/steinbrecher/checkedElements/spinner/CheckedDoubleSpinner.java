package bayern.steinbrecher.checkedElements.spinner;

import java.util.Optional;
import java.util.function.Function;
import javafx.beans.NamedArg;

/**
 * Represents a spinner for double values which sets a css attribute when the inserted value is not valid.
 *
 * @author Stefan Huber
 * @since 0.1
 */
public class CheckedDoubleSpinner extends CheckedSpinner<Double> {

    private static final Function<String, Optional<Double>> PARSE_FUNCTION = value -> {
        try {
            return Optional.of(Double.parseDouble(value.replace(',', '.')));
        } catch (NumberFormatException ex) {
            return Optional.empty();
        }
    };

    /**
     * Constructs a new {@link CheckedDoubleSpinner}.
     *
     * @param initialValue The value of the Spinner when first instantiated, must be within the bounds of the min and
     * max arguments, or else the min value will be used.
     * @param amountToStepBy The amount to increment or decrement by, per step.
     */
    public CheckedDoubleSpinner(@NamedArg("initialValue") double initialValue,
            @NamedArg("amountToStepBy") double amountToStepBy) {
        this(Double.MIN_VALUE, initialValue, amountToStepBy);
    }

    /**
     * Constructs a new {@link CheckedDoubleSpinner}.
     *
     * @param min The minimum allowed value.
     * @param initialValue The value of the Spinner when first instantiated, must be within the bounds of the min and
     * max arguments, or else the min value will be used.
     * @param amountToStepBy The amount to increment or decrement by, per step.
     */
    public CheckedDoubleSpinner(@NamedArg("min") double min,
            @NamedArg("initialValue") double initialValue,
            @NamedArg("amountToStepBy") double amountToStepBy) {
        this(min, Double.MAX_VALUE, initialValue, amountToStepBy);
    }

    /**
     * Constructs a new {@link CheckedDoubleSpinner}.
     *
     * @param min The minimum allowed value.
     * @param max The maximum allowed value.
     * @param initialValue The value of the Spinner when first instantiated, must be within the bounds of the min and
     * max arguments, or else the min value will be used.
     * @param amountToStepBy The amount to increment or decrement by, per step.
     */
    public CheckedDoubleSpinner(@NamedArg("min") double min,
            @NamedArg("max") double max,
            @NamedArg("initialValue") double initialValue,
            @NamedArg("amountToStepBy") double amountToStepBy) {
        this(min, max, initialValue, amountToStepBy, true);
    }

    /**
     * Constructs a new {@link CheckedDoubleSpinner}.
     *
     * @param min The minimum allowed value.
     * @param max The maximum allowed value.
     * @param initialValue The value of the Spinner when first instantiated, must be within the bounds of the min and
     * max arguments, or else the min value will be used.
     * @param amountToStepBy The amount to increment or decrement by, per step.
     * @param includeMin {@code true} only if the minimum value has to be included in the range of allowed values.
     */
    public CheckedDoubleSpinner(@NamedArg("min") double min,
            @NamedArg("max") double max,
            @NamedArg("initialValue") double initialValue,
            @NamedArg("amountToStepBy") double amountToStepBy,
            @NamedArg("includeMin") boolean includeMin) {
        super(new CheckedDoubleSpinnerValueFactory(min, max, initialValue, amountToStepBy, includeMin), PARSE_FUNCTION);
    }
}
