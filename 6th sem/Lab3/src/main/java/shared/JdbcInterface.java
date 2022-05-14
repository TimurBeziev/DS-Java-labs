package shared;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface JdbcInterface extends Remote {

    void addProduct(String product, String stock, String price) throws RemoteException;

    void removeProduct(String product, String stock, String price) throws RemoteException;

    void changePrice(String product, String stock, String pricePercentage) throws RemoteException;

    String getInfoTable() throws RemoteException;

    String getProductsTable() throws RemoteException;

    String getStocksTable() throws RemoteException;

}
