module re.working.reworking {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;

    opens re.working.reworking to javafx.fxml;
    exports re.working.reworking;
}