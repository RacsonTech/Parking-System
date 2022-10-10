module com.smartparking.gui {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    opens com.smartparking.gui to javafx.fxml;
    exports com.smartparking.gui;
}