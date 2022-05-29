package kr.servlet;

import kr.controller.StageController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class MainServlet extends HttpServlet {

    private final static String index = "/WEB-INF/view/index.jsp";

    private StageController stageController;

    @Override
    public void init() throws ServletException {
        stageController = new StageController();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        System.out.println("doGet Method");
        stageController.setSessionAttributes(req, resp);
        req.getRequestDispatcher(index).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        System.out.println("doPost Method");
        if (req.getParameter("nextStage") != null) {
            stageController.showNextStage(req, resp);
        } else if (req.getParameter("prevStage") != null) {
            stageController.showPrevStage(req, resp);
        }
        req.getRequestDispatcher(index).forward(req, resp);
    }
}
