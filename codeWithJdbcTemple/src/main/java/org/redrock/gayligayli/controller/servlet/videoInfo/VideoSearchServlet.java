package org.redrock.gayligayli.controller.servlet.videoInfo;

import org.redrock.gayligayli.service.Command;
import org.redrock.gayligayli.service.Receiver;
import org.redrock.gayligayli.service.videoInfo.command.VideoSearchCommand;
import org.redrock.gayligayli.util.JsonUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.redrock.gayligayli.util.FinalStringUtil.UTF8;

@WebServlet(name = "VideoSearchServlet",urlPatterns = "/search")
public class VideoSearchServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding(UTF8);
        request.setCharacterEncoding(UTF8);
        String requestJson= JsonUtil.getJsonStr(request.getInputStream());
        Receiver receiver = new Receiver(requestJson);
        Command command = new VideoSearchCommand(receiver);
        command.exectue();

        JsonUtil.writeResponse(response,command.getResponseJson());
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }
}
