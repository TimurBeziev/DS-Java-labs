package kr.model;

public class Container {

    private int id;
    private String type;
    private String payer;
    private double price;

    public Container(int id, String type, String payer, double price) {
        this.id = id;
        this.type = type;
        this.payer = payer;
        this.price = price;
    }

    public int getid() {
        return id;
    }
    public String gettype() {
        return type;
    }
    public String getpayer() {
        return payer;
    }
    public double getprice() {
        return price;
    }
}
