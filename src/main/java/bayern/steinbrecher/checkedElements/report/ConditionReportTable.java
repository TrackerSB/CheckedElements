package bayern.steinbrecher.checkedElements.report;

import bayern.steinbrecher.checkedElements.CheckedTableView;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Control;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.util.List;

/**
 * Represents a dialog showing a list of conditions and whether they are fulfilled.
 *
 * @author Stefan Huber
 * @since 0.1
 */
public class ConditionReportTable extends Control {

    private static final int ICON_SIZE = 15;
    private final ObservableValue<ObservableList<ReportEntry>> conditionItems
            = new SimpleObjectProperty<>(this, "conditionItems", FXCollections.observableArrayList());

    public ConditionReportTable() {
        super();

        CheckedTableView<ReportEntry> conditionTable = new CheckedTableView<>();
        VBox.setVgrow(conditionTable, Priority.ALWAYS);
        HBox.setHgrow(conditionTable, Priority.ALWAYS);
        conditionTable.itemsProperty()
                .bind(conditionItems);
        TableColumn<ReportEntry, String> conditionNameColumn
                = new TableColumn<>(ReportEntry.getMessage("condition"));
        conditionNameColumn.setCellValueFactory(item -> item.getValue().messageKeyProperty());
        TableColumn<ReportEntry, ReportConditionResult> conditionValueColumn
                = new TableColumn<>(ReportEntry.getMessage("result"));
        conditionValueColumn.setCellValueFactory(item -> item.getValue().reportResultProperty());
        conditionValueColumn.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(ReportConditionResult result, boolean empty) {
                super.updateItem(result, empty);

                if (!empty) {
                    String resultResourceKey = switch (result) {
                        case FULFILLED -> "fulfilled";
                        case UNFULFILLED -> "unfulfilled";
                        case SKIPPED -> "skipped";
                        case UNKNOWN -> "unknown";
                    };
                    setText(ReportEntry.getMessage(resultResourceKey));
                    setGraphic(result.getGraphic());
                }
            }
        });
        conditionTable.getColumns()
                .setAll(List.of(conditionNameColumn, conditionValueColumn));

        getChildren()
                .add(conditionTable);
    }

    public void addReportEntry(ReportEntry entry) {
        conditionItems.getValue()
                .add(entry);
    }
}
