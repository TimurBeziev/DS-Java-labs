package server.tables;

public class Item {
    private int uniqueID;
    private Product product;
    private Stock stock;
    private double price;

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

    @Override
    public String toString() {
        return product + "\t" + stock + "\t price = " + price + "\n";
    }
}
