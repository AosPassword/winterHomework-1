package org.redrock.gayligayli.controller.servlet.userAction;

import org.redrock.gayligayli.service.Command;
import org.redrock.gayligayli.service.Receiver;
import org.redrock.gayligayli.service.loginAndRegister.been.Token;
import org.redrock.gayligayli.service.userAction.command.ReplacePasswordCommand;
import org.redrock.gayligayli.util.JsonUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.redrock.gayligayli.util.FinalStringUtil.JWT;
import static org.redrock.gayligayli.util.FinalStringUtil.RECEIVE;
import static org.redrock.gayligayli.util.FinalStringUtil.UTF8;

@WebServlet(name = "ReplacePasswordServlet" ,urlPatterns = "/replacePassword")
public class ReplacePasswordServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Receiver receiver = (Receiver) request.getAttribute(RECEIVE);
        Token token = (Token) request.getAttribute(JWT);
        receiver.setToken(token);
        Command command = new ReplacePasswordCommand(receiver);
        command.exectue();
        JsonUtil.writeResponse(response, command.getResponseJson());

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
