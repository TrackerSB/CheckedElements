package bayern.steinbrecher.checkedElements.spinner;

import javafx.beans.NamedArg;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.control.SpinnerValueFactory;

/**
 * Represents a {@link SpinnerValueFactory.DoubleSpinnerValueFactory} which has the option to exclude the minimum value
 * itself from the range of valid numbers.
 *
 * @author Stefan Huber
 * @since 0.1
 */
public class CheckedDoubleSpinnerValueFactory extends SpinnerValueFactory.DoubleSpinnerValueFactory {

    private final BooleanProperty includeMin = new SimpleBooleanProperty(this, "includeMin");

    /**
     * Constructs a new {@code CheckedDoubleSpinnerValueFactory} that sets the initial value to be equal to the min
     * value, and a default {@code amountToStepBy} of one and including the minimum value in the range of valid numbers.
     *
     * @param min The minimum allowed double value for the Spinner.
     * @param max The maximum allowed double value for the Spinner.
     */
    public CheckedDoubleSpinnerValueFactory(@NamedArg("min") double min,
                                            @NamedArg("max") double max) {
        this(min, max, min);
    }

    /**
     * Constructs a new {@code CheckedDoubleSpinnerValueFactory} with a default {@code amountToStepBy} of one and
     * including the minimum value in the range of valid numbers.
     *
     * @param min          The minimum allowed double value for the Spinner.
     * @param max          The maximum allowed double value for the Spinner.
     * @param initialValue The value of the Spinner when first instantiated, must be within the bounds of the min and
     *                     max arguments, or else the min value will be used.
     */
    public CheckedDoubleSpinnerValueFactory(@NamedArg("min") double min,
                                            @NamedArg("max") double max,
                                            @NamedArg("initialValue") double initialValue) {
        this(min, max, initialValue, 1);
    }

    /**
     * Constructs a new {@code CheckedDoubleSpinnerValueFactory} including the minimum value in the range of valid
     * numbers.
     *
     * @param min            The minimum allowed double value for the Spinner.
     * @param max            The maximum allowed double value for the Spinner.
     * @param initialValue   The value of the Spinner when first instantiated, must be within the bounds of the min and
     *                       max arguments, or else the min value will be used.
     * @param amountToStepBy The amount to increment or decrement by, per step.
     */
    public CheckedDoubleSpinnerValueFactory(@NamedArg("min") double min,
                                            @NamedArg("max") double max,
                                            @NamedArg("initialValue") double initialValue,
                                            @NamedArg("amountToStepBy") double amountToStepBy) {
        this(min, max, initialValue, amountToStepBy, true);
    }

    /**
     * Constructs a new {@code CheckedDoubleSpinnerValueFactory}.
     *
     * @param min            The minimum allowed double value for the Spinner.
     * @param max            The maximum allowed double value for the Spinner.
     * @param initialValue   The value of the Spinner when first instantiated, must be within the bounds of the min and
     *                       max arguments, or else the min value will be used.
     * @param amountToStepBy The amount to increment or decrement by, per step.
     * @param includeMin     {@code true} only if the minimum value has to be included within the range of allowed
     *                                   values.
     */
    public CheckedDoubleSpinnerValueFactory(@NamedArg("min") double min,
                                            @NamedArg("max") double max,
                                            @NamedArg("initialValue") double initialValue,
                                            @NamedArg("amountToStepBy") double amountToStepBy,
                                            @NamedArg("includeMin") boolean includeMin) {
        super(min, max, initialValue, amountToStepBy);

        valueProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                if (isIncludeMin()) {
                    if (newVal < getMin()) {
                        setValue(getMin());
                    }
                } else {
                    if (newVal <= getMin()) {
                        /* NOTE Here the boxed version of double is used otherwise it may happend that oldVal is
                        unboxed but
                         * is null.
                         */
                        Double oneStepOverMin = getMin() + getAmountToStepBy();
                        setValue(oldVal < oneStepOverMin ? oldVal : oneStepOverMin);
                    }
                }
            }
        });
        this.includeMin.addListener((obs, oldVal, newVal) -> {
            if (oldVal && getValue() <= getMin()) {
                setValue(getMin() + getAmountToStepBy());
            }
        });

        setMin(min);
        setMax(max);
        setAmountToStepBy(amountToStepBy);
        setIncludeMin(includeMin);
        valueProperty().set(initialValue);
    }

    /**
     * Returns the property holding whether the minimum value is included or not.
     *
     * @return The property holding whether the minimum value is included or not.
     */
    public final BooleanProperty includeMinProperty() {
        return includeMin;
    }

    /**
     * Checks whether the minimum value is included.
     *
     * @return {@code true} if and only if the minimum value is include.
     */
    public final boolean isIncludeMin() {
        return includeMin.get();
    }

    /**
     * Sets whether the minimum value has to be included.
     *
     * @param includeMin {@code true} only if the minimum value has to be included.
     */
    public final void setIncludeMin(boolean includeMin) {
        this.includeMin.set(includeMin);
    }

    @Override
    public void increment(int steps) {
        if (getValue() != null) {
            super.increment(steps);
        }
    }

    @Override
    public void decrement(int steps) {
        if (getValue() != null) {
            super.decrement(steps);
        }
    }
}
