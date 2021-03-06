package org.redrock.gayligayli.controller.servlet.uploadVideo;


import org.redrock.gayligayli.service.Command;
import org.redrock.gayligayli.service.Receiver;
import org.redrock.gayligayli.service.loginAndRegister.been.Token;
import org.redrock.gayligayli.service.videoUpload.command.uploadCodeCommand;
import org.redrock.gayligayli.util.JsonUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.redrock.gayligayli.util.FinalStringUtil.*;

@WebServlet(name = "uploadToken", urlPatterns = "/uploadToken")
public class UploadCodeServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        Receiver receiver = (Receiver) request.getAttribute(RECEIVE);
        Token token = (Token) request.getAttribute(JWT);
        receiver.setToken(token);
        Command command = new uploadCodeCommand(receiver);
        command.exectue();

        JsonUtil.writeResponse(response, command.getResponseJson());

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

}
