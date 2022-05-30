package kr.repositories;

import kr.controller.JDBCController;
import kr.tables.Product;
import kr.tables.Stock;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class StocksRepository implements Serializable {
    private List<Stock> stocks;
    private JDBCController db;

    public StocksRepository() {
        stocks = new ArrayList<>();
    }

    public StocksRepository(JDBCController jdbcController) {
        this.db = jdbcController;
    }

    public boolean contains(String name) {
        return !Objects.isNull(getStockByName(name));
    }

    public Stock getStockByName(String name) {
        for (Stock stock : stocks) {
            if (stock.getName().equals(name)) {
                return stock;
            }
        }
        return null;
    }

    public List<Stock> getStocks() throws SQLException {
        stocks = db.getStocksTable();
        return stocks;
    }

    public void setStocks(List<Stock> stocks) {
        this.stocks = stocks;
    }

    public JDBCController getDb() {
        return db;
    }

    public void setDb(JDBCController db) {
        this.db = db;
    }
}
