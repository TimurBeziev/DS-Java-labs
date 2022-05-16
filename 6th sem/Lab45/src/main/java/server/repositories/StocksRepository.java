package server.repositories;

import server.tables.Product;
import server.tables.Stock;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class StocksRepository {
    private List<Stock> stocks;
    private int maxUniqueID;

    public StocksRepository() {
        stocks = new ArrayList<>();
        this.maxUniqueID = 1;
    }

    public void addStock(String name) {
        if (this.contains(name)) {
            return;
        }
        stocks.add(new Stock(maxUniqueID, name));
        maxUniqueID++;
    }
    public void addStock(int uniqueID, String name) {
        stocks.add(new Stock(uniqueID, name));
    }
    public List<Stock> getStocks(){
        return stocks;
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

    @Override
    public String toString() {
        return "stocks=\n" + stocks + "\nmaxUniqueID=" + maxUniqueID;
    }
}
