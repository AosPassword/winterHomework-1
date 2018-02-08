package org.redrock.gayligayli.video.servlet;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.redrock.gayligayli.Dao.VideoDao;
import org.redrock.gayligayli.video.been.Video;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class HomePageServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        JSONObject jsonObject = new JSONObject();

        JSONArray typeNumJson = new JSONArray();
        typeNumJson.element(VideoDao.getTypesNum());
        jsonObject.put("typeNum",typeNumJson);

        JSONArray carouselJson = new JSONArray();
        ArrayList<Video> carouselList = VideoDao.getCarouselVideo((long)Math.ceil(new Date().getTime()/1000));
        for (Video video:carouselList) {
            carouselJson.element(video.toString());
        }
        jsonObject.put("carouse",carouselJson);



    }
}
