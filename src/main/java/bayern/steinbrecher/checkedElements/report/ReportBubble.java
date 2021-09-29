package bayern.steinbrecher.checkedElements.report;

import javafx.beans.Observable;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.scene.Node;
import javafx.scene.control.Tooltip;
import javafx.util.Pair;

import java.util.Comparator;
import java.util.Map;
import java.util.stream.Collectors;

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
    private final FilteredList<ReportEntry> triggeredReportsUnmodifiable;
    private final ReadOnlyStringWrapper reportsMessage = new ReadOnlyStringWrapper();
    private final Tooltip bubble = new Tooltip();

    public ReportBubble(C reportable) {
        bubble.textProperty()
                .bind(reportsMessage);

        // Ensure the filtered list is updated on report triggered property changes by using an extractor
        ObservableList<ReportEntry> observableReports
                = FXCollections.observableArrayList(re -> new Observable[]{re.reportTriggeredProperty()});
        triggeredReportsUnmodifiable = observableReports.filtered(ReportEntry::isReportTriggered);
        triggeredReportsUnmodifiable.addListener((ListChangeListener<? super ReportEntry>) change ->
                reportsMessage.set(
                        change.getList()
                                .stream()
                                .map(ReportEntry::getMessageKey)
                                .map(ReportEntry::getMessage)
                                .collect(Collectors.joining("\n"))
                ));
        observableReports.addAll(reportable.getReports());
        reportsMessage.isEmpty()
                .addListener((obs, wasEmpty, isEmpty) -> {
                    if (isEmpty) {
                        Tooltip.uninstall(reportable, bubble);
                    } else {
                        ReportType bubbleType = triggeredReportsUnmodifiable
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
                    }
                });
    }
}
