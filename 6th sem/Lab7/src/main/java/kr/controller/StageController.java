package kr.controller;

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

    public void showNextStage(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession();
        int stage = (Integer) session.getAttribute("currentStage");

        if (stage == 0) {
            session.setAttribute("currentStage", setNextStage(stage));
            return;
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
