module bayern.steinbrecher.CheckedElements {
    exports bayern.steinbrecher.checkedElements;
    exports bayern.steinbrecher.checkedElements.buttons;
    exports bayern.steinbrecher.checkedElements.report;
    exports bayern.steinbrecher.checkedElements.textfields.sepa;
    exports bayern.steinbrecher.checkedElements.spinner;
    exports bayern.steinbrecher.checkedElements.textfields;

    requires bayern.steinbrecher.SepaXMLGenerator;
    requires bayern.steinbrecher.Utility;
    requires java.logging;
    requires java.xml;
    requires javafx.controls;
    requires javafx.fxml;

    // FIXME Open resources to whom?
    opens bayern.steinbrecher.checkedElements.report;
    opens bayern.steinbrecher.checkedElements.textfields;
    opens bayern.steinbrecher.checkedElements.textfields.sepa;
}