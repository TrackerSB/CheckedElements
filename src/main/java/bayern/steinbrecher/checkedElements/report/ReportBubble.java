package bayern.steinbrecher.checkedElements.report;

import javafx.beans.property.ListProperty;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.scene.Node;
import javafx.scene.control.Tooltip;
import javafx.util.Pair;

import java.util.Comparator;
import java.util.Map;
import java.util.StringJoiner;

/**
 * Represents a bubble like popup showing attached and triggered reports of a control.It is meant for being used for
 * inline validations.
 *
 * @param <C> The type of the reporting {@link Node} where to attach a {@link ReportBubble} to.
 * @author Stefan Huber
 * @since 0.1
 */
public final class ReportBubble<C extends Node & Reportable> {

    /**
     * The map has the following structure: ReportType --> (background color, font color). The colors represent CSS
     * values.
     */
    private static final Map<ReportType, Pair<String, String>> COLOR_SCHEMES = Map.of(
            ReportType.ERROR, new Pair<>("#ff4d4d", "white"),
            ReportType.WARNING, new Pair<>("#ffe680", "black"),
            ReportType.INFO, new Pair<>("#80bfff", "black"),
            ReportType.UNDEFINED, new Pair<>("#d9d9d9", "black")
    );
    private final ListProperty<ReportEntry> triggeredReports
            = new SimpleListProperty<>(FXCollections.observableArrayList());
    private final ReadOnlyStringWrapper reportsMessage = new ReadOnlyStringWrapper();
    private final Tooltip bubble = new Tooltip();

    public ReportBubble(C reportable) {
        bubble.textProperty()
                .bind(reportsMessage);

        reportable.getReports()
                .addListener((ListChangeListener.Change<? extends ReportEntry> change) -> {
                    while (change.next()) {
                        triggeredReports.removeAll(change.getRemoved());
                        change.getAddedSubList()
                                .stream()
                                .filter(ReportEntry::isReportTriggered)
                                .forEach(triggeredReports::add);
                    }
                });
        triggeredReports.addListener((obs, oldVal, newVal) -> {
            StringJoiner reportBuilder = new StringJoiner("\n");
            newVal.forEach(reportEntry -> reportBuilder.add(reportEntry.getMessage()));
            reportsMessage.set(reportBuilder.toString());
        });
        triggeredReports.emptyProperty()
                .not()
                .and(reportable.validProperty().not())
                .addListener((obs, oldVal, newVal) -> {
                    if (newVal) {
                        ReportType bubbleType = triggeredReports
                                .stream()
                                .map(ReportEntry::getType)
                                .distinct()
                                .max(Comparator.naturalOrder())
                                .orElse(ReportType.UNDEFINED);
                        Pair<String, String> scheme = COLOR_SCHEMES.get(bubbleType);
                        assert scheme != null : "There is no scheme defined for ReportType " + bubbleType;

                        bubble.setStyle("-fx-background-color: " + scheme.getKey() + ";"
                                + "-fx-text-fill: " + scheme.getValue());

                        Tooltip.install(reportable, bubble);
                    } else {
                        Tooltip.uninstall(reportable, bubble);
                    }
                });
    }
}
