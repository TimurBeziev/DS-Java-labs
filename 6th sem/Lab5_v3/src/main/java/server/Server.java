package server;

import java.rmi.AlreadyBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;

public class Server {

    public static void main(String[] args) throws SQLException, RemoteException, AlreadyBoundException {
        String productsRepoPath = "/Users/timurbeziev/Uni/Java/6th sem/Lab45/src/main/resources/xmlFiles/products.xml";
        String stocksRepoPath = "/Users/timurbeziev/Uni/Java/6th sem/Lab45/src/main/resources/xmlFiles/stocks.xml";
        String infosRepoPath = "/Users/timurbeziev/Uni/Java/6th sem/Lab45/src/main/resources/xmlFiles/infos.xml";
        XmlController server = new XmlController(productsRepoPath, stocksRepoPath, infosRepoPath);
        Registry registry = LocateRegistry.createRegistry(2732);
        Remote stub = UnicastRemoteObject.exportObject(server, 0);
        registry.bind("server", stub);
    }
}
