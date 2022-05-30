package kr.repositories;

import kr.controller.JDBCController;
import kr.tables.Product;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ProductsRepository implements Serializable {
    private List<Product> products;
    private JDBCController db;

    public ProductsRepository() {
        products = new ArrayList<>();
    }

    public ProductsRepository(JDBCController jdbcController) {
        this.db = jdbcController;
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

    public List<Product> getProducts() throws SQLException {
        products = db.getProductsTable();
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public JDBCController getDb() {
        return db;
    }

    public void setDb(JDBCController db) {
        this.db = db;
    }
}
