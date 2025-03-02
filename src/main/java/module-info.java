module com.example.demoa4 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    requires org.xerial.sqlitejdbc;
    requires java.desktop;

    opens com.example.demoa4 to javafx.fxml;
    exports com.example.demoa4;
}