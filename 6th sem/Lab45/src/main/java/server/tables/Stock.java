package server.tables;

public class Stock {
    private int uniqueID;
    private String name;

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

    @Override
    public String toString() {
        return "stock name = " + name;
    }
}
