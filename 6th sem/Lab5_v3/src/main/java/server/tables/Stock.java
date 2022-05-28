package server.tables;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "stock")
public class Stock {
    private int uniqueID;
    private String name;

    public Stock(int uniqueID, String stockName) {
        this.uniqueID = uniqueID;
        this.name = stockName;
    }

    @XmlAttribute(name = "name")
    public String getName() {
        return name;
    }
    @XmlAttribute(name = "id")
    public int getUniqueID() {
        return uniqueID;
    }

    @Override
    public String toString() {
        return "stock name = " + name;
    }
}
