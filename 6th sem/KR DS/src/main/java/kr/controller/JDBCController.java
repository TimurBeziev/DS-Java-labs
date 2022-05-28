package kr.controller;

import kr.model.Container;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JDBCController {
    private Connection connection;

    public JDBCController(String url, String user, String password) throws SQLException {
        connection = DriverManager.getConnection(url, user, password);
        initTable();
    }

    private void initTable() throws SQLException {
        System.out.println("init table");
        Statement stmt = connection.createStatement();
        String sql =
                "CREATE TABLE IF NOT EXISTS customers (" +
                        "Id SERIAL PRIMARY KEY, " +
                        "Type varchar(50) NOT NULL," +
                        "Payer varchar(50) NOT NULL," +
                        "Price REAL NOT NULL" +
                        ");";
        stmt.execute(sql);
        stmt.close();
    }

    public void addCustomer(String type, String payer, double productPrice) throws SQLException {
//        Statement stmt = connection.createStatement();
//        stmt.execute(String.format("INSERT INTO customers (Type, Payer, Price) VALUES (%s,%s,%f);",
//                type, payer, productPrice));
    }

    public List<Container> getCustomers() throws SQLException {
        List<Container> result = new ArrayList<>();
        System.out.println("get customers");
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM customers;");
        while (rs.next()) {
            int id = rs.getInt(1);
            String type = rs.getString(2);
            String payer = rs.getString(3);
            double price = rs.getDouble(4);
            result.add(new Container(id, type, payer, price));
        }
        stmt.close();
        return result;
    }

    public double getMaxPayment() throws SQLException {
        double result = 0;
        Statement stmt = connection.createStatement();
        String sql = String.format(
                "SELECT MAX(Price) FROM customers\n" +
                        "WHERE customers.Type = ('%s');", "nalog");
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()) {
            result = rs.getDouble(1);
        }
        stmt.close();
        return result;
    }

    public int getNalogCount() throws SQLException {
        int result = 0;
        Statement stmt = connection.createStatement();
        String sql = String.format(
                "SELECT * FROM customers\n" +
                        "WHERE customers.Type = ('%s');", "nalog");
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()) {
            result++;
        }
        stmt.close();
        return result;
    }
}
