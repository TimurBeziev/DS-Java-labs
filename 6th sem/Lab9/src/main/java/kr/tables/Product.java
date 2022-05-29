package kr.tables;

import java.io.Serializable;

public class Product implements Serializable {

    private int uniqueID;
    private String name;

    public Product() {
    }

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

    public void setUniqueID(int uniqueID) {
        this.uniqueID = uniqueID;
    }

    public void setName(String name) {
        this.name = name;
    }
}
