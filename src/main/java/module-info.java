module bayern.steinbrecher.CheckedElements {
    exports bayern.steinbrecher.checkedElements;
    exports bayern.steinbrecher.checkedElements.buttons;
    exports bayern.steinbrecher.checkedElements.report;
    exports bayern.steinbrecher.checkedElements.textfields.sepa;
    exports bayern.steinbrecher.checkedElements.spinner;
    exports bayern.steinbrecher.checkedElements.textfields;

    requires bayern.steinbrecher.Utility;
    requires java.desktop; // NOTE Only required for testing
    requires java.logging;
    requires java.xml;
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.swing; // NOTE Only required for testing

    requires static error.prone.annotation;

    // FIXME Open resources to whom?
    opens bayern.steinbrecher.checkedElements.report;
    opens bayern.steinbrecher.checkedElements.textfields;
    opens bayern.steinbrecher.checkedElements.textfields.sepa;
}