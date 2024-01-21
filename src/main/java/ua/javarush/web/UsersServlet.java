package ua.javarush.web;

import lombok.extern.log4j.Log4j2;
import ua.javarush.models.User;
import ua.javarush.repository.UserRepositoryImpl;
import ua.javarush.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;

@Log4j2
@WebServlet("/users")
public class UsersServlet extends HttpServlet {
    private final UserService userService = new UserService(new UserRepositoryImpl());

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String password = req.getParameter("password");
        String email = req.getParameter("email");
        LocalDate dateOfBirth = LocalDate.parse(req.getParameter("birthDate"));

        User user = new User(name, password, dateOfBirth, email);

        userService.createUser(user);
        resp.setStatus(201);
        req.setAttribute("users", userService.getAllUsers());
        req.getRequestDispatcher("usersPage.jsp").forward(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("users", userService.getAllUsers());
        req.getRequestDispatcher("usersPage.jsp").forward(req, resp);
    }
}
