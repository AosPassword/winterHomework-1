package org.redrock.gayligayli.controller.servlet.login;

import org.redrock.gayligayli.service.Command;
import org.redrock.gayligayli.service.Receiver;
import org.redrock.gayligayli.service.loginAndRegister.commond.LoginCommand;
import org.redrock.gayligayli.util.JsonUtil;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

import static org.redrock.gayligayli.util.FinalStringUtil.*;

public class LoginServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        doPost(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setCharacterEncoding(UTF8);
        request.setCharacterEncoding(UTF8);
        String jsonStr = JsonUtil.getJsonStr(request.getInputStream());

        Receiver receiver = new Receiver(jsonStr);
        Command command = new LoginCommand(receiver);
        command.exectue();

        JsonUtil.writeResponse(response, command.getResponseJson());
    }
}
