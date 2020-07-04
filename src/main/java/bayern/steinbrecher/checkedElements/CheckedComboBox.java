package bayern.steinbrecher.checkedElements;

import bayern.steinbrecher.checkedElements.report.ReportEntry;
import bayern.steinbrecher.checkedElements.report.ReportType;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;

/**
 * Extended version of {@link ComboBox}. Also contains {@link BooleanProperty} indicating whether nothing is selected.
 *
 * @author Stefan Huber
 * @param <T> The type of the element of the ComboBox.
 * @since 0.1
 */
public class CheckedComboBox<T> extends ComboBox<T> implements CheckedControl {

    private final CheckableControlBase<CheckedComboBox<T>> ccBase = new CheckableControlBase<>(this);
    private final BooleanProperty nothingSelected = new SimpleBooleanProperty(this, "nothingSelected");

    /**
     * Creates a {@link CheckedComboBox} which contains no element.
     */
    public CheckedComboBox() {
        this(FXCollections.observableArrayList());
    }

    /**
     * Creates a {@link CheckedComboBox} which show the given elements.
     *
     * @param items The elements to show.
     */
    public CheckedComboBox(ObservableList<T> items) {
        super(items);
        initProperties();
    }

    private void initProperties() {
        nothingSelected.bind(Bindings.createBooleanBinding(() -> getSelectionModel().isEmpty(),
                selectionModelProperty(), getSelectionModel().selectedItemProperty()));
        addReport(new ReportEntry("nothingSelected", ReportType.ERROR, nothingSelected));
    }

    /**
     * Represents a boolean value indicating whether the textfield is empty or not.
     *
     * @return The property represents a boolean value indicating whether the textfield is empty or not.
     */
    public ReadOnlyBooleanProperty nothingSelectedProperty() {
        return nothingSelected;
    }

    /**
     * Checks whether the textfield is empty.
     *
     * @return {@code true} only if the textfield is empty.
     */
    public boolean isNothingSelected() {
        return nothingSelected.get();
    }

    @Override
    public ReadOnlyBooleanProperty checkedProperty() {
        return ccBase.checkedProperty();
    }

    @Override
    public boolean isChecked() {
        return ccBase.isChecked();
    }

    @Override
    public ReadOnlyBooleanProperty validProperty() {
        return ccBase.validProperty();
    }

    @Override
    public boolean isValid() {
        return ccBase.isValid();
    }

    @Override
    public ObservableList<ReportEntry> getReports() {
        return ccBase.getReports();
    }

    @Override
    public boolean addReport(ReportEntry report) {
        return ccBase.addReport(report);
    }
}
