package kr.controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class StageController {

    private final String name;
    private final String gender;
    private final String bestSubject;

    public StageController() {
        name = "";
        gender = "";
        bestSubject = "";
    }

    public void setSessionAttributes(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession();
        session.setAttribute("name", name);
        session.setAttribute("gender", gender);
        session.setAttribute("bestSubject", bestSubject);
        session.setAttribute("currentStage", 0);
    }

    public void showNextStage(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
        HttpSession session = req.getSession();
        int stage = (Integer) session.getAttribute("currentStage");
        System.out.println("stage is" +stage);

        if (stage == 0) {
            session.setAttribute("currentStage", setNextStage(stage));
            return;
        }
        if (stage == 3) {
            String gender = (String) session.getAttribute("gender");
            System.out.println("gender is = " + gender);
            if (gender.equals("")) {
                throw new ServletException("YOU SHOULD ENTER GENDER!");
            }
        }
        String selectedInput = (String) req.getParameter("input");
        if (selectedInput == null) {
            System.out.println("input is null");
            return;
        }
        switch (stage) {
            case 1:
                System.out.println("set name");
                session.setAttribute("name", selectedInput);
                break;
            case 2:
                System.out.println("set gender");
                session.setAttribute("gender", selectedInput);
                break;
            case 3:
                System.out.println("set subject");
                session.setAttribute("bestSubject", selectedInput);
                break;
        }
        session.setAttribute("currentStage", setNextStage(stage));
    }

    public void showPrevStage(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession();
        int stage = (Integer) session.getAttribute("currentStage");
        if (stage == 4) {
            session.setAttribute("currentStage", setPrevStage(stage));
            return;
        }
        String selectedInput = (String) req.getParameter("input");
        if (selectedInput == null) {
            System.out.println("input is null");
            return;
        }
        switch (stage) {
            case 1:
                session.setAttribute("name", selectedInput);
                break;
            case 2:
                session.setAttribute("gender", selectedInput);
                break;
            case 3:
                session.setAttribute("bestSubject", selectedInput);
                break;
        }
        session.setAttribute("currentStage", setPrevStage(stage));
    }

    public int setNextStage(int currentStage) {
        if (currentStage == 4) {
            System.out.println("stage 4");
            return currentStage;
        }
        currentStage++;
        return currentStage;
    }

    public int setPrevStage(int currentStage) {
        if (currentStage == 1) {
            return currentStage;
        }
        currentStage--;
        return currentStage;
    }
}
