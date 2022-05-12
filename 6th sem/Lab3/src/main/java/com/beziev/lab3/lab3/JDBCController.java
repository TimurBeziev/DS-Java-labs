package com.beziev.lab3.lab3;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.commons.lang3.StringUtils;


public class JDBCController {

    private Connection connection;

    public JDBCController(String url, String user, String password) throws SQLException {
        connection = DriverManager.getConnection(url, user, password);
        initTable();
    }

    private void initTable() throws SQLException {
        Statement stmt = connection.createStatement();
        String sql =
                "CREATE TABLE IF NOT EXISTS products (" +
                        "Id SERIAL PRIMARY KEY, " +
                        "name varchar(50) NOT NULL UNIQUE" +
                        ");\n" +
                        "CREATE TABLE IF NOT EXISTS stocks (" +
                        "Id SERIAL PRIMARY KEY," +
                        "name varchar(50) NOT NULL UNIQUE" +
                        ");\n" +
                        "CREATE TABLE IF NOT EXISTS info (" +
                        "Id SERIAL PRIMARY KEY,\n" +
                        "Product_id INTEGER NOT NULL REFERENCES products (Id),\n" +
                        "Stock_id INTEGER NOT NULL REFERENCES stocks (Id),\n" +
                        "Price REAL NOT NULL" +
                        ");";
        stmt.execute(sql);
        stmt.close();
    }

    public void addProduct(String product, String stock, String price) throws SQLException {
        if (product.isBlank() || stock.isBlank() || price.isBlank()) {
            System.out.println("can't add product");
            return;
        }

        double productPrice = Double.parseDouble(price);
        Statement stmt = connection.createStatement();

        if (getProductID(product) == -1) {
            stmt.execute(String.format("INSERT INTO products (name) VALUES ('%s');", product));
        }

        if (getStockID(stock) == -1) {
            stmt.execute(String.format("INSERT INTO stocks (name) VALUES ('%s');", stock));
        }

        stmt.execute(String.format("INSERT INTO info (Product_id, Stock_id, Price) VALUES (%d,%d,%f);",
                getProductID(product), getStockID(stock), productPrice));
    }

    public void removeProduct(String product, String stock, String price) throws SQLException {
        Statement stmt = connection.createStatement();
        String sql = null;

        if (product.isBlank() && stock.isBlank() && price.isBlank()) {
            sql = String.format(
                    "DELETE FROM info;\n");
        } else if (stock.isBlank() && price.isBlank()) {
            sql = String.format(
                    "DELETE FROM info\n" +
                            "WHERE info.Product_id = ('%d');",
                    getProductID(product));
        } else if (price.isBlank()) {
            sql = String.format(
                    "DELETE FROM info\n" +
                            "WHERE info.Product_id = ('%d')\n" +
                            "AND info.Stock_id = ('%d');",
                    getProductID(product), getStockID(stock));
        } else if (!product.isBlank() && !stock.isBlank() && !price.isBlank()) {
            sql = String.format(
                    "DELETE FROM info\n" +
                            "WHERE info.Product_id = ('%d')\n" +
                            "AND info.Stock_id = ('%d')\n" +
                            "AND info.Price = ('%f');",
                    getProductID(product), getStockID(stock), Float.parseFloat(price));
        }
        stmt.execute(sql);
        stmt.close();
    }

    public void changePrice(String product, String stock, String pricePercentage) throws SQLException {
        if (pricePercentage.isBlank()) {
            return;
        }
        Statement stmt = connection.createStatement();
        String sql = null;
        Double productPricePercentage = Double.parseDouble(pricePercentage) / 100;

        if (product.isBlank() && stock.isBlank()) {
            sql = String.format(
                    "UPDATE info SET price = price * %f;\n",
                    productPricePercentage);
        } else if (stock.isBlank()) {
            sql = String.format(
                    "UPDATE info SET price = price * %f\n" +
                            "WHERE info.Product_id = ('%d');",
                    productPricePercentage, getProductID(product));
        } else {
            sql = String.format(
                    "UPDATE info SET price = price * %f\n" +
                            "WHERE info.Product_id = ('%d')\n" +
                            "AND info.Stock_id = ('%d');",
                    productPricePercentage, getProductID(product), getStockID(stock));
        }
        stmt.execute(sql);
        stmt.close();
    }

    public String getInfoTable() throws SQLException {
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM info;");

        StringBuilder result = new StringBuilder("ID \t Product_id \t Stock_id \t Price \n");


        while (rs.next()) {
            String productName = getProductNameByID(rs.getInt(2));
            String stockName = getStockNameByID(rs.getInt(3));
            result.append(String.format("%d \t\t %s \t\t\t\t %s \t\t %.2f \n",
                    rs.getInt(1),
                    productName,
                    stockName,
                    rs.getFloat(4)));
        }

        stmt.close();
        return result.toString();
    }

    public String getProductsTable() throws SQLException {
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM products;");
        StringBuilder result = new StringBuilder("ID \t Name\n");
        while (rs.next()) {
            result.append(String.format("%d \t\t %s \t\t",
                    rs.getInt(1),
                    rs.getString(2))).append("\n");
        }

        stmt.close();
        return result.toString();
    }

    public String getStocksTable() throws SQLException {
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM stocks;");
        StringBuilder result = new StringBuilder("ID \t Name\n");
        while (rs.next()) {
            result.append(String.format("%d \t\t %s \t\t",
                    rs.getInt(1),
                    rs.getString(2))).append("\n");
        }
        stmt.close();
        return result.toString();
    }

    private int getProductID(String productName) throws SQLException {
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(String.format("SELECT Id FROM products WHERE name = ('%s');", productName));
        int productID = -1;
        while (rs.next()) {
            productID = rs.getInt(1);
        }
        return productID;
    }

    private String getProductNameByID(int id) throws SQLException {
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(String.format("SELECT name FROM products WHERE Id = ('%d');", id));
        String productName = "";
        while (rs.next()) {
            productName = rs.getString(1);
        }
        return productName;
    }

    private int getStockID(String stockName) throws SQLException {
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(String.format("SELECT Id FROM stocks WHERE name = ('%s');", stockName));
        int stockID = -1;
        while (rs.next()) {
            stockID = rs.getInt(1);
        }
        return stockID;
    }

    private String getStockNameByID(int id) throws SQLException {
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(String.format("SELECT name FROM stocks WHERE Id = ('%d');", id));
        String stockName = "";
        while (rs.next()) {
            stockName = rs.getString(1);
        }
        return stockName;
    }
}
