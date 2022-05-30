package kr.tables;

import java.io.Serializable;

public class Stock implements Serializable {
    private int uniqueID;
    private String name;

    public Stock() {
    }

    public Stock(int uniqueID, String stockName) {
        this.uniqueID = uniqueID;
        this.name = stockName;
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
