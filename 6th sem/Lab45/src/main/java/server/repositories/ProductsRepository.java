package server.repositories;

import server.tables.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ProductsRepository {
    private List<Product> products;
    private int maxUniqueID;

    public ProductsRepository() {
        this.maxUniqueID = 1;
        products = new ArrayList<>();
    }

    public void addProduct(String name) {
        if (this.contains(name)) {
            return;
        }
        products.add(new Product(maxUniqueID, name));
        maxUniqueID++;
    }
    public void addProduct(int uniqueID, String name) {
        products.add(new Product(uniqueID, name));
    }

    public List<Product> getProducts(){
        return products;
    }

    public boolean contains(String name) {
        return !Objects.isNull(getProductByName(name));
    }

    public Product getProductByName(String name) {
        for (Product product : products) {
            if (product.getName().equals(name)) {
                return product;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "products=\n" + products + "\nmaxUniqueID=" + maxUniqueID;
    }
}
