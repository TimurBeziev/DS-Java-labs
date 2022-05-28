package server.tables;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "product")
public class Product {
    private int uniqueID;
    private String name;
    public int num;

    public Product(int uniqueID, String productName) {
        this.uniqueID = uniqueID;
        this.name = productName;
        this.num = 5;
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
        return  "product name = " + name;
    }
}
