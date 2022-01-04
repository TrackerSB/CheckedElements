package bayern.steinbrecher.checkedElements.spinner;

import java.util.Optional;
import java.util.function.Function;

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
     * @since 0.13
     */
    public CheckedDoubleSpinner(boolean includeMin) {
        super(new CheckedDoubleSpinnerValueFactory(Double.MIN_VALUE, Double.MAX_VALUE, 0d, 1d, includeMin),
                PARSE_FUNCTION);
    }
}
