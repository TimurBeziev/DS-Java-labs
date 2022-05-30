package kr.repositories;

import kr.controller.JDBCController;
import kr.tables.Item;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class InfosRepository implements Serializable {
    private List<Item> items;
    private JDBCController db;

    public InfosRepository() {
        items = new ArrayList<>();

    }

    public InfosRepository(JDBCController jdbcController) {
        this.db = jdbcController;
    }

    public List<Item> getItems() throws SQLException {
        items = db.getInfoTable();
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public JDBCController getDb() {
        return db;
    }

    public void setDb(JDBCController db) {
        this.db = db;
    }
}
