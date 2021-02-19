package bayern.steinbrecher.checkedElements;

import bayern.steinbrecher.checkedElements.report.Reportable;
import bayern.steinbrecher.checkedElements.report.ReportableBase;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyBooleanWrapper;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.css.PseudoClass;
import javafx.scene.Node;

/**
 * This class represents an implementation of {@link CheckableControl} which can be used for delegation.
 *
 * @param <C> The type of the control delegating to this class.
 * @author Stefan Huber
 * @since 0.1
 */
public class CheckableControlBase<C extends Node & Reportable> extends ReportableBase<C> implements CheckableControl {
    private static final PseudoClass CHECKED_PSEUDO_CLASS = PseudoClass.getPseudoClass("checked");
    private final ReadOnlyBooleanWrapper valid = new ReadOnlyBooleanWrapper();
    private final BooleanProperty checked = new SimpleBooleanProperty(this, "checked", true) {
        @Override
        protected void invalidated() {
            control.pseudoClassStateChanged(CHECKED_PSEUDO_CLASS, get());
        }
    };
    private final C control;

    /**
     * Creates a {@link CheckableControlBase} which can be used for delegating calls when implementing
     * {@link CheckableControl}.
     *
     * @param control The control to add pseudo classes to.
     */
    public CheckableControlBase(C control) {
        super(control);
        this.control = control;
        control.getStyleClass().add("checked-control-base");
        valid.bind(super.validProperty().or(checkedProperty().not()));
    }

    @Override
    public ReadOnlyBooleanProperty validProperty() {
        return valid.getReadOnlyProperty(); // FIXME Have all related validity methods to be overridden as well?
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
