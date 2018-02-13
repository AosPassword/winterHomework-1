package org.redrock.gayligayli.controller.servlet.videoInfo;

import net.sf.json.JSONObject;
import org.redrock.gayligayli.Dao.VideoDao;
import org.redrock.gayligayli.service.video.util.VideoInfoUtil;
import org.redrock.gayligayli.util.JsonUtil;
import org.redrock.gayligayli.util.SecretUtil;
import org.redrock.gayligayli.util.TimeUtil;
import org.redrock.gayligayli.service.video.been.Barrage;
import org.redrock.gayligayli.service.video.been.Comment;
import org.redrock.gayligayli.service.video.been.Video;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

import static org.redrock.gayligayli.util.FinalStringUtil.*;

public class VideoPageServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding(UTF8);
        response.setCharacterEncoding(UTF8);

        JSONObject requestJson = JSONObject.fromObject(JsonUtil.getJsonStr(request.getInputStream()));
        int id = requestJson.getInt(ID);
        String timestamp = requestJson.getString(TIMESTAMP);
        String signature = requestJson.getString(SIGNATURE);

        Video video;
        ArrayList<Barrage> barrageLIst;
        ArrayList<Comment> commentList;
        JSONObject jsonObject = new JSONObject();
        if (SecretUtil.isSecret(id + SIGNATURE_SEPARATOR + timestamp, signature)) {
            if (TimeUtil.isNotOverTime(timestamp, REQUEST_OVERTIME_SECOND)) {
                if (VideoInfoUtil.isVideoExist(id)) {
                    VideoDao.addView(id);
                    video = VideoDao.getVideoInfo(id);
                    barrageLIst = VideoDao.getBarrageList(id);
                    commentList = VideoDao.getCommentList(id);
                    jsonObject.put(RESULT, SUCCESS);
                    jsonObject.put(VIDEO, video.toString());
                    jsonObject.put(BARRAGE, JsonUtil.getArrayString(barrageLIst));
                    jsonObject.put(COMMENT, JsonUtil.getArrayString(commentList));
                } else {
                    jsonObject.put(RESULT,DO_NOT_FIND_VIDEO);
                }
            } else {
                jsonObject.put(RESULT, REQUEST_OVERTIME);
            }
        } else {
            jsonObject.put(RESULT, SIGNATURE_ERROR);
        }
        JsonUtil.writeResponse(response, jsonObject.toString());
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
