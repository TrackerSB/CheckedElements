package bayern.steinbrecher.checkedElements;

import javafx.beans.property.BooleanProperty;

/**
 * Represents which represents controls like buttons, checkboxes, input fields, etc which have addional properties
 * describing whether the current input is valid or if it is checked whether it is valid. In contrast to
 * {@link CheckedControl} the checked property can be changed.
 *
 * @see CheckedControl
 *
 * @author Stefan Huber
 * @since 0.1
 */
public interface CheckableControl extends CheckedControl {

    /**
     * {@inheritDoc}
     */
    @Override
    BooleanProperty checkedProperty();

    /**
     * Sets whether to check the input or not.
     *
     * @param checked {@code true} only if the input has to be checked.
     */
    void setChecked(boolean checked);
}
