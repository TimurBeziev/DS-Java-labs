package kr.tables;

import java.io.Serializable;

public class Item implements Serializable {

    private int uniqueID;
    private Product product;
    private Stock  stock;
    private double price;

    public Item() {

    }

    public Item(int uniqueID, Product product, Stock stock, double price) {
        this.uniqueID = uniqueID;
        this.product = product;
        this.stock = stock;
        this.price = price;
    }

    public Product getProduct() {
        return product;
    }

    public Stock getStock() {
        return stock;
    }

    public double getPrice() {
        return price;
    }

    public int getUniqueID() {
        return uniqueID;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setUniqueID(int uniqueID) {
        this.uniqueID = uniqueID;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public void setStock(Stock stock) {
        this.stock = stock;
    }

}
