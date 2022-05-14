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

    private JdbcInterface jdbcController;

    public void setJdbcController(JdbcInterface jdbcController) {
        this.jdbcController = jdbcController;
    }

    @FXML
    protected void onUpdateButtonClick() {
        try {
            infoTable.setText(jdbcController.getInfoTable());
            productsTable.setText(jdbcController.getProductsTable());
            stocksTable.setText(jdbcController.getStocksTable());
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
            jdbcController.addProduct(product, stock, price);
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
            jdbcController.changePrice(product, stock, changePercentage);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void onDeleteButtonClick() {
        System.out.println("change");
        String product = productName.getText();
        String stock = stockName.getText();
        String price = productPrice.getText();
        try {
            jdbcController.removeProduct(product, stock, price);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
