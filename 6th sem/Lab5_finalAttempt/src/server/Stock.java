package server;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "stock")
public class Stock {
    private int uniqueID;
    private String name;

    public Stock() {

    }
    public Stock(int uniqueID, String stockName) {
        this.uniqueID = uniqueID;
        this.name = stockName;
    }

    public void setName(String name) {
        this.name = name;
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
