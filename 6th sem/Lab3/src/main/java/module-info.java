module com.beziev.lab1.lab12 {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires java.sql;
    requires org.apache.commons.lang3;

    opens com.beziev.lab3.lab3 to javafx.fxml;
    exports com.beziev.lab3.lab3;
}