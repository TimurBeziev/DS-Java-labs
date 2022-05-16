package server.repositories;

import server.tables.Item;
import server.tables.Product;
import server.tables.Stock;

import java.util.ArrayList;
import java.util.List;

public class InfoRepository {
    private List<Item> items;
    private int maxUniqueID;

    public InfoRepository() {
        maxUniqueID = 1;
        items = new ArrayList<>();
    }

    public void add(Product product, Stock stock, double price) {
        items.add(new Item(maxUniqueID, product, stock, price));
        maxUniqueID++;
    }

    public void add(int uniqueID, Product product, Stock stock, double price) {
        items.add(new Item(uniqueID, product, stock, price));
    }

    public List<Item> getInfo() {
        return items;
    }

    public void clearRepository() {
        items.clear();
    }

    public void removeItem(Product product) {
        items.removeIf(item -> item.getProduct().equals(product));
    }

    public void removeItem(Product product, Stock stock) {
        items.removeIf(item -> item.getProduct().equals(product) && item.getStock().equals(stock));
    }

    public void removeItem(Product product, Stock stock, double price) {
        items.removeIf(
                item -> item.getProduct().equals(product)
                        && item.getStock().equals(stock)
                        && Double.compare(item.getPrice(), price) == 0
        );
    }

    public void changePrice(double pricePercentage) {
        for (Item item : items) {
            item.setPrice(item.getPrice() * pricePercentage);
        }
    }

    public void changePrice(Product product, double pricePercentage) {
        for (Item item : items) {
            if (item.getProduct().equals(product)) {
                item.setPrice(item.getPrice() * pricePercentage);
            }
        }
    }

    public void changePrice(Product product, Stock stock, double pricePercentage) {
        for (Item item : items) {
            if (item.getProduct().equals(product) && item.getStock().equals(stock)) {
                item.setPrice(item.getPrice() * pricePercentage);
            }
        }
    }

    @Override
    public String toString() {
        return "items=\n" + items + "\nmaxUniqueID=" + maxUniqueID;
    }
}
