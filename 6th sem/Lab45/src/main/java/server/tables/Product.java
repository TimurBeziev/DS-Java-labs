package server.tables;

public class Product {
    private int uniqueID;
    private String name;

    public Product(int uniqueID, String productName) {
        this.uniqueID = uniqueID;
        this.name = productName;
    }

    public String getName() {
        return name;
    }

    public int getUniqueID() {
        return uniqueID;
    }

    @Override
    public String toString() {
        return  "product name = " + name;
    }
}
