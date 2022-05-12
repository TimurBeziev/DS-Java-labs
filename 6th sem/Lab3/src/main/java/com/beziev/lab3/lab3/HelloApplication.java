package com.beziev.lab3.lab3;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class HelloApplication extends Application {
    Connection conn;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1000, 600);
        stage.setTitle("Hello!");
        stage.setScene(scene);

        String url = "jdbc:postgresql://localhost:5432/postgres";
        String username = "postgres";
        String password = "123456";

        try {
            System.out.println("Connected to server");
            JDBCController jdbcController = new JDBCController(url, username, password);
            HelloController helloController = fxmlLoader.getController();
            helloController.setJdbcController(jdbcController);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}