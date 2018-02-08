package org.redrock.gayligayli.loginAndRegister.servlet;

import com.mysql.jdbc.BufferRow;
import net.sf.json.JSON;
import net.sf.json.JSONObject;
import org.redrock.gayligayli.loginAndRegister.been.Token;
import org.redrock.gayligayli.util.JsonUtil;
import org.redrock.gayligayli.util.SecretUtil;
import org.redrock.gayligayli.util.TimeUtil;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.sql.Time;
import java.util.Map;

public class RefreshToken extends HttpServlet {
    private final static int OVERTIME = 2592000;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        doGet(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String tokenStr = request.getHeader("jwt");
        String jsonStr = JsonUtil.getJsonStr(request.getInputStream());
        JSONObject requestJson = JSONObject.fromObject(jsonStr);
        String timestamp = requestJson.getString("timestamp");
        String signature = requestJson.getString("signature");
        JSONObject jsonObject = new JSONObject();
        Token originToken = new Token(tokenStr);
        if(SecretUtil.isSecret(timestamp,signature)){
            if(TimeUtil.isNotOverTime(timestamp,600)){
                if(originToken.isToken()){
                    if(originToken.isNotTokenOverTime()){
                        Token newToken = new Token();
                        newToken.setSub("user");
                        newToken.setData("nickname",originToken.getNickname());
                        newToken.setTime(OVERTIME);
                        jsonObject.put("result","Success");
                        jsonObject.put("token", newToken.getToken());
                    } else {
                     jsonObject.put("result","TokenOverTime");
                    }
                } else {
                    jsonObject.put("result","TokenError");
                }
            } else {
                jsonObject.put("result","OverTime");
            }
        } else {
            jsonObject.put("result","SignatureError");
        }
        BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(
                        response.getOutputStream()
                )
        );
        writer.write(jsonObject.toString());
        writer.flush();
        writer.close();
    }
}
