module client {
    requires javafx.fxml;
    requires javafx.controls;
    requires java.rmi;
    requires java.xml;
    requires java.sql;
    requires jaxb.api;
//    requires javafx.controls;
//    requires javafx.fxml;
//    requires javafx.web;
//
//    requires org.controlsfx.controls;
//    requires com.dlsc.formsfx;
//    requires validatorfx;
//    requires org.kordamp.ikonli.javafx;
//    requires org.kordamp.bootstrapfx.core;
//    requires eu.hansolo.tilesfx;
//    requires java.sql;
//    requires org.apache.commons.lang3;
//    requires java.rmi;

    opens client to javafx.fxml;
    exports client;
    exports shared;
}