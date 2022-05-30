package kr.controller;

import kr.repositories.ProductsRepository;
import kr.repositories.StocksRepository;
import kr.tables.Item;
import kr.tables.Product;
import kr.tables.Stock;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

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

    public void addProduct(String product, String stock, String price) throws RemoteException {
        if (product.isBlank() || stock.isBlank() || price.isBlank()) {
            System.out.println("can't add product");
//            throw new RemoteException("can't add product");
            return;
        }

        double productPrice = Double.parseDouble(price);
        Statement stmt = null;
        try {
            stmt = connection.createStatement();

            if (getProductID(product) == -1) {
                stmt.execute(String.format("INSERT INTO products (name) VALUES ('%s');", product));
            }

            if (getStockID(stock) == -1) {
                stmt.execute(String.format("INSERT INTO stocks (name) VALUES ('%s');", stock));
            }

            stmt.execute(String.format("INSERT INTO info (Product_id, Stock_id, Price) VALUES (%d,%d,%f);",
                    getProductID(product), getStockID(stock), productPrice));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeProduct(String product, String stock, String price) throws RemoteException {
        Statement stmt = null;
        try {
            stmt = connection.createStatement();

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
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void changePrice(String product, String stock, String pricePercentage) throws SQLException {
        if (pricePercentage.isBlank()) {
            return;
        }
        Statement stmt = null;
        try {
            stmt = connection.createStatement();
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Item> getInfoTable() throws SQLException {
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM info;");
        List<Item> items = new ArrayList<>();

        while (rs.next()) {
            int id = rs.getInt(1);

            int productId = rs.getInt(2);
            Product product = getProductById(productId);

            int stockId = rs.getInt(3);
            Stock stock = getStockById(stockId);

            double price = rs.getDouble(4);

            items.add(new Item(id, product, stock, price));
        }

        stmt.close();
        return items;
    }

    public List<Product> getProductsTable() throws SQLException {
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM products;");
        List<Product> products = new ArrayList<>();
        while (rs.next()) {
            products.add(new Product(rs.getInt(1), rs.getString(2)));
        }
        stmt.close();
        return products;
    }

    public List<Stock> getStocksTable() throws SQLException {
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM stocks;");
        List<Stock> stocks = new ArrayList<>();
        while (rs.next()) {
            stocks.add(new Stock(rs.getInt(1), rs.getString(2)));
        }
        stmt.close();
        return stocks;
    }

    public Product getProductById(int id) throws SQLException {
        for (Product product : getProductsTable()) {
            if (product.getUniqueID() == id) {
                return product;
            }
        }
        return null;
    }

    public Stock getStockById(int id) throws SQLException {
        for (Stock stock : getStocksTable()) {
            if (stock.getUniqueID() == id) {
                return stock;
            }
        }
        return null;
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

    private int getStockID(String stockName) throws SQLException {
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(String.format("SELECT Id FROM stocks WHERE name = ('%s');", stockName));
        int stockID = -1;
        while (rs.next()) {
            stockID = rs.getInt(1);
        }
        return stockID;
    }
}
