package org.redrock.gayligayli.manage;

import org.redrock.gayligayli.util.LoginUtil;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static org.redrock.gayligayli.util.FinalStringUtil.*;

@WebServlet(name = "ManageLoginServlet", urlPatterns = "/manageLogin")
public class ManageLoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding(UTF8);
        response.setCharacterEncoding(UTF8);

        String username = request.getParameter(USERNAME);
        String password = request.getParameter(PASSWORD);
        if (username != null && password != null) {
            if (LoginUtil.isPass("email", username, password)) {
                request.setAttribute("username", username);
                request.setAttribute("password", password);
                HttpSession session = request.getSession();
                request.setAttribute("result", "success");
                session.setAttribute("manage", "mashiroc");
            } else {
                request.setAttribute("result", "error");
            }
        }
        RequestDispatcher view = request.getRequestDispatcher("meirenxingmeitianli.jsp");
        view.forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }


}
