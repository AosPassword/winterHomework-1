package org.redrock.gayligayli.controller.servlet.videoInfo;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.redrock.gayligayli.service.Command;
import org.redrock.gayligayli.service.Receiver;
import org.redrock.gayligayli.service.videoInfo.command.HomePageCommand;
import org.redrock.gayligayli.util.JsonUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.redrock.gayligayli.util.FinalStringUtil.*;

public class HomePageServlet extends HttpServlet {
    private static final String[] partition =
            {CARTOON, ANIME, CREATED_BY_NATIVE, MUSIC, DANCE,
                    GAME, SCIENCE, LIFE, AUTOTUNE_REMIX, FASHION,
                    ADVERTISEMENT, ENTERTAINMENT, MOVIES, SCREENING_HALL};

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        request.setCharacterEncoding(UTF8);
        response.setCharacterEncoding(UTF8);
        JSONArray jsonArray = JSONArray.fromObject(partition);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(CONTENT,jsonArray);

        Receiver receiver = new Receiver(jsonObject.toString());
        Command command = new HomePageCommand(receiver);
        command.exectue();


        JsonUtil.writeResponse(response,command.getResponseJson());
    }
}
