package kr.servlet;

import kr.controller.JDBCController;
import kr.model.Container;
import org.w3c.dom.ls.LSOutput;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainServlet extends HttpServlet {

    private JDBCController db;

    private final static String index = "/WEB-INF/view/index.jsp";
    private final static String url = "jdbc:postgresql://localhost:5432/postgres";
    private final static String user = "postgres";
    private final static String password = "123456";

    private List<Container> containers;

    @Override
    public void init() throws ServletException {
        containers = new ArrayList<>();

        try {
            System.out.println("successfully connect");
            db = new JDBCController(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        System.out.println("hello");
        double max_payment = 0;
        int nalog_count = 0;
        try {
            containers = db.getCustomers();
            max_payment = db.getMaxPayment();
            nalog_count = db.getNalogCount();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        req.setAttribute("max_payment", max_payment);
        req.setAttribute("nalog_count", nalog_count);
        req.setAttribute("users", containers);
        req.getRequestDispatcher(index).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String loc = req.getParameter("locale");
        Locale locale = new Locale(loc);
        req.getSession().setAttribute("locale", locale);

        double max_payment = 0;
        int nalog_count = 0;
        try {
            containers = db.getCustomers();
            max_payment = db.getMaxPayment();
            nalog_count = db.getNalogCount();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        req.setAttribute("max_payment", max_payment);
        req.setAttribute("nalog_count", nalog_count);
        req.setAttribute("users", containers);
        req.getRequestDispatcher(index).forward(req, resp);
    }

    private boolean requestIsValid(final HttpServletRequest req) {

        final String name = req.getParameter("name");
        final String age = req.getParameter("age");

        return name != null && name.length() > 0 &&
                age != null && age.length() > 0 &&
                age.matches("[+]?\\d+");
    }
}
