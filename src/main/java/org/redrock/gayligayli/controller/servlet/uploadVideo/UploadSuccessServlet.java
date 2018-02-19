package org.redrock.gayligayli.controller.servlet.uploadVideo;

import org.redrock.gayligayli.service.Command;
import org.redrock.gayligayli.service.Receiver;
import org.redrock.gayligayli.service.videoUpload.command.UploadSuccessCommand;
import org.redrock.gayligayli.util.JsonUtil;

import javax.print.attribute.standard.MediaSize;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.redrock.gayligayli.util.FinalStringUtil.UTF8;

@WebServlet(name = "uploadSuccess", urlPatterns = "/uploadSuccess")
public class UploadSuccessServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        request.setCharacterEncoding(UTF8);
        response.setCharacterEncoding(UTF8);
        String requestStr = JsonUtil.getJsonStr(request.getInputStream());

        Receiver receiver = new Receiver(requestStr);
        Command command = new UploadSuccessCommand(receiver);
        command.exectue();

        JsonUtil.writeResponse(response, command.getResponseJson());

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
