package server.tables;

public class Item {
    Product product;
    Stock stock;
    double price;

    public Item(Product product, Stock stock, double price) {
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

    public void setPrice(double price) {
        this.price = price;
    }
    @Override
    public String toString() {
        return "Item{" +
                product +
                stock +
                ", price=" + price +
                "}\n";
    }
}
