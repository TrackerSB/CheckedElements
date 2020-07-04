package bayern.steinbrecher.checkedElements;

import bayern.steinbrecher.checkedElements.report.Reportable;
import bayern.steinbrecher.checkedElements.report.ReportableBase;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.css.PseudoClass;
import javafx.scene.control.Control;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class represents an implementation of {@link CheckableControl} which can be used for delegation.
 *
 * @author Stefan Huber
 * @since 0.1
 * @param <C> The type of the control delegating to this class.
 */
public class CheckableControlBase<C extends Control & Reportable> extends ReportableBase<C> implements CheckableControl {

    private static final Logger LOGGER = Logger.getLogger(CheckableControlBase.class.getName());
    private static final PseudoClass CHECKED_PSEUDO_CLASS = PseudoClass.getPseudoClass("checked");

    private final BooleanProperty checked = new SimpleBooleanProperty(this, "checked", true) {
        @Override
        protected void invalidated() {
            control.pseudoClassStateChanged(CHECKED_PSEUDO_CLASS, get());
        }
    };
    private final C control;

    /**
     * Creates a {@link CheckableControlBase} which can be used for delgating calls when implementing
     * {@link CheckableControl}.
     *
     * @param control The control to add pseudo classes to.
     */
    public CheckableControlBase(C control) {
        super(control);
        this.control = control;
        control.getStyleClass().add("checked-control-base");
    }

    @Override
    protected BooleanBinding createValidBinding() {
        BooleanBinding validBinding;
        if (checkedProperty() == null) {
            LOGGER.log(Level.WARNING, "checkedProperty() returns null and could not be connected, yet.");
            validBinding = super.createValidBinding();
        } else {
            validBinding = super.createValidBinding()
                    .or(checkedProperty().not());
        }
        return validBinding;
    }

    @Override
    public BooleanProperty checkedProperty() {
        return checked;
    }

    @Override
    public boolean isChecked() {
        return checkedProperty().get();
    }

    @Override
    public void setChecked(boolean checked) {
        checkedProperty().set(checked);
    }
}
