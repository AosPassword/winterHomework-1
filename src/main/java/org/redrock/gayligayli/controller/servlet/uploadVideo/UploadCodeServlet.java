package org.redrock.gayligayli.controller.servlet.uploadVideo;

import com.qiniu.util.Auth;
import com.sun.org.apache.regexp.internal.RE;
import net.sf.json.JSONObject;
import org.redrock.gayligayli.Dao.UserDao;
import org.redrock.gayligayli.Dao.VideoDao;
import org.redrock.gayligayli.service.video.been.Video;
import org.redrock.gayligayli.service.video.util.VideoInfoUtil;
import org.redrock.gayligayli.util.JsonUtil;
import org.redrock.gayligayli.util.SecretUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.redrock.gayligayli.util.FinalStringUtil.*;

public class UploadCodeServlet extends HttpServlet {
    private static final String accessKey = "czxMRl6_nz0WYgjkVEl7t6pAWKLQGgAGhGAeF16x";
    private static final String secretKey = "RJ8XGZAibjHodVI97-HzkO89Aj9Cxd2lSGSBOwSg";
    private static final String bucket = "gayligayli";

    private static final String UP_TOKEN = "upToken";
    private static final String DO_NOT_FIND_USER = "doNotFindUser";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding(UTF8);
        response.setCharacterEncoding(UTF8);

        String requestStr = JsonUtil.getJsonStr(request.getInputStream());
        JSONObject requestJson = JSONObject.fromObject(requestStr);
        String name = requestJson.getString(NAME);
        String nickname = requestJson.getString(NICKNAME);
        String type = requestJson.getString(TYPE);
        String description = requestJson.getString(DESCRIPTION);
        String time = requestJson.getString(TIME);
        String length = requestJson.getString(LENGTH);
        String signature = requestJson.getString(SIGNATURE);
        String names[] = name.split(".");
        int authorId = -1;

        StringBuilder sb = new StringBuilder();
        sb.append(name).append(SIGNATURE_SEPARATOR)
                .append(nickname).append(SIGNATURE_SEPARATOR)
                .append(type).append(SIGNATURE_SEPARATOR)
                .append(description).append(SIGNATURE_SEPARATOR)
                .append(time).append(SIGNATURE_SEPARATOR)
                .append(length);

        JSONObject jsonObject = new JSONObject();
        if (SecretUtil.isSecret(sb.toString(), signature)) {
            if ((authorId = UserDao.getUserid(nickname, NICKNAME)) != -1) {
                    int avId = VideoDao.addVideo(name, authorId, type, description, time, length);
                    String upToken;
                    Auth auth = Auth.create(accessKey, secretKey);
                    upToken = auth.uploadToken(bucket);
                    jsonObject.put(RESULT, SUCCESS);
                    jsonObject.put(AV_ID, avId);
                    jsonObject.put(UP_TOKEN, upToken);
            } else {
                jsonObject.put(RESULT, DO_NOT_FIND_USER);
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
