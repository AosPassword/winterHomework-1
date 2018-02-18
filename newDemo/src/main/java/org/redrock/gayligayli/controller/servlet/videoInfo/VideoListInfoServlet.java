package org.redrock.gayligayli.controller.servlet.videoInfo;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;

public class VideoListInfoServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request , HttpServletResponse response){

    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
//TODO 我也不知道这是干啥的servlet
    }
}
