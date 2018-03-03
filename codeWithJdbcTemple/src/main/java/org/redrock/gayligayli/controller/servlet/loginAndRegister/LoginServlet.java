package org.redrock.gayligayli.controller.servlet.loginAndRegister;

import org.redrock.gayligayli.service.Command;
import org.redrock.gayligayli.service.Receiver;
import org.redrock.gayligayli.service.loginAndRegister.commond.LoginCommand;
import org.redrock.gayligayli.util.JsonUtil;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

import static org.redrock.gayligayli.util.FinalStringUtil.*;

@WebServlet(name = "login", urlPatterns = "/login")
public class LoginServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        doPost(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setCharacterEncoding(UTF8);
        request.setCharacterEncoding(UTF8);
        response.setHeader(HEADER_ONE,HEADER_TWO);
        String jsonStr = JsonUtil.getJsonStr(request.getInputStream());

        Receiver receiver = new Receiver(jsonStr);
        Command command = new LoginCommand(receiver);
        command.exectue();

        JsonUtil.writeResponse(response, command.getResponseJson());
    }
}
