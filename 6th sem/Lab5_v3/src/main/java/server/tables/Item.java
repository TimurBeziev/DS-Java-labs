package server.tables;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Item")
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

    @XmlElement(name = "product")
    public Product getProduct() {
        return product;
    }
    @XmlElement(name = "stock")
    public Stock getStock() {
        return stock;
    }
    @XmlAttribute(name = "price")
    public double getPrice() {
        return price;
    }

    @XmlAttribute(name = "id")
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
