package com.beziev.lab3.lab3;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.sql.SQLException;

public class HelloController {
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

    private JDBCController jdbcController;

    public void setJdbcController(JDBCController jdbcController1) {
        jdbcController = jdbcController1;
    }

    @FXML
    protected void onUpdateButtonClick() throws SQLException {
        infoTable.setText(jdbcController.getInfoTable());
        productsTable.setText(jdbcController.getProductsTable());
        stocksTable.setText(jdbcController.getStocksTable());
        System.out.println("update");
    }

    @FXML
    protected void onAddProductButtonClick() throws SQLException {
        String product = productName.getText();
        String stock = stockName.getText();
        String price = productPrice.getText();
        System.out.println("onAddProductButtonClick");
        jdbcController.addProduct(product, stock, price);
    }

    @FXML
    protected void onChangePriceButtonClick() throws SQLException {
        System.out.println("change");
        String product = productName.getText();
        String stock = stockName.getText();
        String changePercentage = priceChangePercentage.getText();
        jdbcController.changePrice(product, stock, changePercentage);
    }

    @FXML
    protected void onDeleteButtonClick() throws SQLException {
        System.out.println("change");
        String product = productName.getText();
        String stock = stockName.getText();
        String price = productPrice.getText();
        jdbcController.removeProduct(product, stock, price);
    }
}