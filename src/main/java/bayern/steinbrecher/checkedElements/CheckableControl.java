package bayern.steinbrecher.checkedElements;

import javafx.beans.property.BooleanProperty;

/**
 * @author Stefan Huber
 * @since 0.1
 */
public interface CheckableControl extends CheckedControl {

    @Override
    BooleanProperty checkedProperty();

    default void setChecked(boolean checked) {
        checkedProperty()
                .set(checked);
    }
}
