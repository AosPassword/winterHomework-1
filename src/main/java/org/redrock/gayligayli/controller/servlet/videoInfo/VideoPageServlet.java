package org.redrock.gayligayli.controller.servlet.videoInfo;

import org.redrock.gayligayli.service.Command;
import org.redrock.gayligayli.service.Receiver;
import org.redrock.gayligayli.service.videoInfo.command.VideoPageCommand;
import org.redrock.gayligayli.util.JsonUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.redrock.gayligayli.util.FinalStringUtil.*;

@WebServlet(name = "videoPage", urlPatterns = "/videoPage")
public class VideoPageServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Receiver receiver = (Receiver) request.getAttribute(RECEIVE);
        Command command = new VideoPageCommand(receiver);
        if (receiver.isSignatureTrue()) {
            command.exectue();
        } else {
            receiver.errorString();
        }
        JsonUtil.writeResponse(response, command.getResponseJson());
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
