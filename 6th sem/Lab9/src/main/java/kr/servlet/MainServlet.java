package kr.servlet;

import kr.controller.JDBCController;
import kr.controller.StageController;
import kr.repositories.ProductsRepository;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;


public class MainServlet extends HttpServlet {

    private final static String index = "/WEB-INF/view/index.jsp";
    private final static String url = "jdbc:postgresql://localhost:5432/postgres";
    private final static String user = "postgres";
    private final static String password = "123456";

    private JDBCController db;
    private ProductsRepository productsRepository;

    @Override
    public void init() throws ServletException {
        try {
            System.out.println("successfully connect");
            db = new JDBCController(url, user, password);
            productsRepository = new ProductsRepository(db);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        System.out.println("doGet Method");
        System.out.println(productsRepository);
        req.getSession().setAttribute("productsRepository", productsRepository);
//        stageController.setSessionAttributes(req, resp);
        req.getRequestDispatcher(index).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        System.out.println("doPost Method");
//        if (req.getParameter("nextStage") != null) {
////            stageController.showNextStage(req, resp);
//        } else if (req.getParameter("prevStage") != null) {
////            stageController.showPrevStage(req, resp);
//        }
        req.getRequestDispatcher(index).forward(req, resp);
    }
}
