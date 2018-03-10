package org.redrock.gayligayli.controller.servlet.uploadVideo;

import com.sun.org.apache.regexp.internal.RE;
import org.redrock.gayligayli.service.Command;
import org.redrock.gayligayli.service.Receiver;
import org.redrock.gayligayli.service.loginAndRegister.been.Token;
import org.redrock.gayligayli.service.videoUpload.command.UploadSuccessCommand;
import org.redrock.gayligayli.util.JsonUtil;

import javax.print.attribute.standard.MediaSize;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.redrock.gayligayli.util.FinalStringUtil.*;

@WebServlet(name = "uploadSuccess", urlPatterns = "/uploadSuccess")
public class UploadSuccessServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        Receiver receiver = (Receiver) request.getAttribute(RECEIVE);
        Token token = (Token) request.getAttribute(JWT);
        receiver.setToken(token);
        Command command = new UploadSuccessCommand(receiver);
        command.exectue();

        JsonUtil.writeResponse(response, command.getResponseJson());

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
