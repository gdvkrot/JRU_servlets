package ua.javarush.project.web;

import ua.javarush.project.AcceptUfoChallengeService;
import ua.javarush.project.answers.UFOAnswer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/ufo")
public class UFOChallengeServlet extends HttpServlet {

    private final AcceptUfoChallengeService acceptUfoChallengeService = new AcceptUfoChallengeService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UFOAnswer answer = acceptUfoChallengeService.call(Boolean.parseBoolean(req.getParameter("answer")));

        resp.setStatus(200);
        req.setAttribute("answer", answer.getMessage());
        req.getRequestDispatcher(answer.getPage()).forward(req, resp);
    }
}
