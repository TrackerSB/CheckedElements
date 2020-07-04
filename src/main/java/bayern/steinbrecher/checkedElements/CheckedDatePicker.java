package bayern.steinbrecher.checkedElements;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.FormatStyle;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import bayern.steinbrecher.checkedElements.report.ReportEntry;
import bayern.steinbrecher.checkedElements.report.ReportType;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.binding.ObjectBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyBooleanWrapper;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.DatePicker;

/**
 * Represents a DatePicker which sets a css class attribute when it is empty or an invalid date is inserted.
 *
 * @author Stefan Huber
 * @since 0.1
 */
@SuppressWarnings("PMD.DataClass")
public class CheckedDatePicker extends DatePicker implements CheckableControl {

    private static final Logger LOGGER = Logger.getLogger(CheckedDatePicker.class.getName());
    private final CheckableControlBase<CheckedDatePicker> ccBase = new CheckableControlBase<>(this);
    private final ReadOnlyBooleanWrapper empty = new ReadOnlyBooleanWrapper(this, "empty");
    private final BooleanProperty forceFuture = new SimpleBooleanProperty(this, "forceFuture", false);
    private final ReadOnlyBooleanWrapper invalidPastDate = new ReadOnlyBooleanWrapper(this, "invalidPastDate");

    /**
     * Constructs {@link CheckedDatePicker} without initial date and {@code forceFuture} set to {@code false}.
     */
    public CheckedDatePicker() {
        this(false);
    }

    /**
     * Constructs a {@link CheckedDatePicker} with no initial date inserted.
     *
     * @param forceFuture {@code true} indicates a date can only be valid if it is not in the past and not today.
     */
    public CheckedDatePicker(boolean forceFuture) {
        this(null, forceFuture);
    }

    /**
     * Constructs a {@link CheckedDatePicker} with {@code locale} as initial date.
     *
     * @param locale The initial date.
     * @param forceFuture {@code true} indicates a date can only be valid if it is not in the past and not today.
     */
    public CheckedDatePicker(LocalDate locale, boolean forceFuture) {
        super(locale);
        this.forceFuture.set(forceFuture);
        initProperties();

        getStyleClass().add("checked-date-picker");
        getStylesheets().add(CheckedDatePicker.class.getResource("checkedDatePicker.css").toExternalForm());
    }

    private void initProperties() {
        empty.bind(getEditor().textProperty().isEmpty());

        ObjectBinding<LocalDate> executionDateBinding = Bindings.createObjectBinding(() -> {
            String dateToParse = getEditor().textProperty().get();
            LocalDate newDate = null;

            String[] dateParts = dateToParse.split("\\.");
            //CHECKSTYLE.OFF: MagicNumber - Every date consists of 3 elements (year, month, day)
            if (dateParts.length == 3) { //NOPMD - A date consists of exactly three point separated parts.
                //CHECKSTYLE.ON: MagicNumber
                dateParts[0] = (dateParts[0].length() < 2 ? "0" : "") + dateParts[0];
                dateParts[1] = (dateParts[1].length() < 2 ? "0" : "") + dateParts[1];
                dateToParse = Arrays.stream(dateParts).collect(Collectors.joining("."));
            }
            if (!dateToParse.isEmpty()) {
                try {
                    newDate = LocalDate.parse(dateToParse, DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT));
                } catch (DateTimeParseException ex) {
                    try {
                        newDate = LocalDate.parse(dateToParse, DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM));
                    } catch (DateTimeParseException ex2) {
                        LOGGER.log(Level.WARNING,
                                dateToParse + " can neither be parsed by FormatStyle.SHORT nor FormatStyle.MEDIUM.",
                                ex2);
                    }
                }
            }
            return newDate;
        }, getEditor().textProperty());

        BooleanBinding executionDateInFuture = Bindings.createBooleanBinding(() -> {
            LocalDate executionDate = executionDateBinding.get();
            return executionDate != null && executionDate.isAfter(LocalDate.now());
        }, executionDateBinding);
        invalidPastDate.bind(this.forceFuture.and(executionDateInFuture.not()));

        ccBase.addReport(new ReportEntry("pastExecutionDate", ReportType.ERROR, invalidPastDateProperty()));
        ccBase.addReport(new ReportEntry("inputMissing", ReportType.ERROR, emptyProperty()));
        ccBase.addReport(new ReportEntry("noDate", ReportType.ERROR, executionDateBinding.isNull()));
    }

    @Override
    public ObservableList<ReportEntry> getReports() {
        return ccBase.getReports();
    }

    @Override
    public boolean addReport(ReportEntry report) {
        return ccBase.addReport(report);
    }

    @Override
    public BooleanProperty checkedProperty() {
        return ccBase.checkedProperty();
    }

    @Override
    public boolean isChecked() {
        return ccBase.isChecked();
    }

    @Override
    public void setChecked(boolean checked) {
        ccBase.setChecked(checked);
    }

    @Override
    public ReadOnlyBooleanProperty validProperty() {
        return ccBase.validProperty();
    }

    @Override
    public boolean isValid() {
        return validProperty().get();
    }

    /**
     * Represents a boolean value indicating whether the textfield is empty or not.
     *
     * @return The property represents a boolean value indicating whether the textfield is empty or not.
     */
    public ReadOnlyBooleanProperty emptyProperty() {
        return empty.getReadOnlyProperty();
    }

    /**
     * Checks whether the textfield is empty.
     *
     * @return {@code true} only if the textfield is empty.
     */
    public boolean isEmpty() {
        return empty.get();
    }

    /**
     * Returns the property indicating whether the date picker checks whether the inserted date si not in the past and
     * not today.
     *
     * @return The property indicating whether the date picker checks whether the inserted date si not in the past and
     * not today.
     */
    public BooleanProperty forceFutureProperty() {
        return forceFuture;
    }

    /**
     * Indicates whether the date picker checks whether the inserted date si not in the past and not today.
     *
     * @return {@code true} only if the inserted date has to be in the future to be valid.
     */
    public boolean isForceFuture() {
        return forceFuture.get();
    }

    /**
     * Sets {@code forceFuture} which indicates whether the inserted date has to be in the future to be valid.
     *
     * @param forceFuture {@code true} only if the inserted date has to be in the future to be valid.
     */
    public void setForceFuture(boolean forceFuture) {
        this.forceFuture.set(forceFuture);
    }

    /**
     * Returns the property which saves {@code false} if {@code forceFuture} saves {@code true} but the date is not in
     * the future. It saves {@code false} otherwise.
     *
     * @return The property which saves {@code false} if {@code forceFuture} saves {@code true} but the date is not in
     * the future. It saves {@code false} otherwise.
     */
    public ReadOnlyBooleanProperty invalidPastDateProperty() {
        return invalidPastDate.getReadOnlyProperty();
    }

    /**
     * Returns {@code false} if {@code forceFuture} saves {@code true} but the date is not in the future. It returns
     * {@code false} otherwise.
     *
     * @return {@code false} only if {@code forceFuture} saves {@code true} but the date is not in the future.
     */
    public boolean isInvalidPastDate() {
        return invalidPastDate.get();
    }
}
