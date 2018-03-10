package org.redrock.gayligayli.controller.servlet.loginAndRegister;

import org.redrock.gayligayli.service.Command;
import org.redrock.gayligayli.service.Receiver;
import org.redrock.gayligayli.service.loginAndRegister.commond.VerificationCodeCommand;
import org.redrock.gayligayli.util.JsonUtil;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

import static org.redrock.gayligayli.util.FinalStringUtil.*;

@WebServlet(name = "verificationCode", urlPatterns = "/verificationCode")
public class VerificationCodeServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        doPost(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        Receiver receiver = (Receiver) request.getAttribute(RECEIVE);
        Command command = new VerificationCodeCommand(receiver);
        command.exectue();

        JsonUtil.writeResponse(response, command.getResponseJson());

    }
}

