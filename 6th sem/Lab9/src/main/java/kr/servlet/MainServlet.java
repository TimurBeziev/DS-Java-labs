package kr.servlet;

import kr.controller.JDBCController;
import kr.repositories.InfosRepository;
import kr.repositories.ProductsRepository;
import kr.repositories.StocksRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class MainServlet extends HttpServlet {

    private final static String index = "/WEB-INF/view/index.jsp";
    private final static String jokes = "/WEB-INF/view/jokes.jsp";
    private final static String url = "jdbc:postgresql://localhost:5432/postgres";
    private final static String user = "postgres";
    private final static String password = "123456";

    private JDBCController db;
    private ProductsRepository productsRepository;
    private StocksRepository stocksRepository;
    private InfosRepository infosRepository;

    @Override
    public void init() throws ServletException {
        try {
            System.out.println("successfully connect");
            db = new JDBCController(url, user, password);
            productsRepository = new ProductsRepository(db);
            stocksRepository = new StocksRepository(db);
            infosRepository = new InfosRepository(db);
            updateRepositories();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            updateRepositories();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        req.getSession().setAttribute("productsRepository", productsRepository);
        req.getSession().setAttribute("stocksRepository", stocksRepository);
        req.getSession().setAttribute("infosRepository", infosRepository);

        req.getRequestDispatcher(index).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String productName = req.getParameter("productName");
        String stockName = req.getParameter("stockName");
        String price = req.getParameter("price");

        if (req.getParameter("addProduct") != null) {
            db.addProduct(productName, stockName, price);
        } else if (req.getParameter("deleteProduct") != null) {
            db.removeProduct(productName, stockName, price);
        } else if (req.getParameter("readJoke") != null) {
            req.getRequestDispatcher(jokes).forward(req, resp);
            return;
        } else if (req.getParameter("mainPage") != null) {
            req.getRequestDispatcher(index).forward(req, resp);
            return;
        }
        try {
            updateRepositories();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        req.getRequestDispatcher(index).forward(req, resp);
    }

    public void updateRepositories() throws SQLException {
        productsRepository.getProducts();
        stocksRepository.getStocks();
        infosRepository.getItems();
    }
}
