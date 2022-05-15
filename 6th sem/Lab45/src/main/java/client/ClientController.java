package client;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import shared.JdbcInterface;

import java.rmi.RemoteException;

public class ClientController {
    @FXML
    private TextField stockName;
    @FXML
    private TextField productName;
    @FXML
    private TextField productPrice;

    @FXML
    private TextField priceChangePercentage;

    @FXML
    private TextArea infoTable;

    @FXML
    private TextArea productsTable;

    @FXML
    private TextArea stocksTable;

    private JdbcInterface serverController;

    public void setServerController(JdbcInterface serverController) {
        this.serverController = serverController;
    }

    @FXML
    protected void onUpdateButtonClick() {
        try {
            infoTable.setText(serverController.getInfoTable());
            productsTable.setText(serverController.getProductsTable());
            stocksTable.setText(serverController.getStocksTable());
            System.out.println("update");
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void onAddProductButtonClick() {
        String product = productName.getText();
        String stock = stockName.getText();
        String price = productPrice.getText();
        System.out.println("onAddProductButtonClick");
        try {
            serverController.addProduct(product, stock, price);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void onChangePriceButtonClick() {
        System.out.println("change");
        String product = productName.getText();
        String stock = stockName.getText();
        String changePercentage = priceChangePercentage.getText();
        try {
            serverController.changePrice(product, stock, changePercentage);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void onDeleteButtonClick() {
        System.out.println("delete");
        String product = productName.getText();
        String stock = stockName.getText();
        String price = productPrice.getText();
        try {
            serverController.removeProduct(product, stock, price);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
