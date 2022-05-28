package client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import shared.JdbcInterface;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;


public class Client extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(Client.class.getResource("client-app-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1000, 600);
        stage.setTitle("Hello!");
        stage.setScene(scene);

        Registry registry = LocateRegistry.getRegistry(2732);
        ClientController clientController = fxmlLoader.getController();
        JdbcInterface jdbcInterface = (JdbcInterface) registry.lookup("server");
        clientController.setServerController(jdbcInterface);

        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
