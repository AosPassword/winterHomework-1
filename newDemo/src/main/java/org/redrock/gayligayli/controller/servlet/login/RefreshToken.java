package org.redrock.gayligayli.controller.servlet.login;


import net.sf.json.JSONObject;
import org.redrock.gayligayli.service.loginAndRegister.been.Token;
import org.redrock.gayligayli.util.JsonUtil;
import org.redrock.gayligayli.util.SecretUtil;
import org.redrock.gayligayli.util.TimeUtil;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

import static org.redrock.gayligayli.util.FinalStringUtil.*;

public class RefreshToken extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        doGet(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding(UTF8);
        response.setCharacterEncoding(UTF8);
        String tokenStr = request.getHeader(JWT);
        String jsonStr = JsonUtil.getJsonStr(request.getInputStream());
        JSONObject requestJson = JSONObject.fromObject(jsonStr);
        String timestamp = requestJson.getString(TIMESTAMP);
        String signature = requestJson.getString(SIGNATURE);
        JSONObject jsonObject = new JSONObject();
        Token originToken = new Token(tokenStr);
        if (SecretUtil.isSecret(timestamp, signature)) {
            if (TimeUtil.isNotOverTime(timestamp, REQUEST_OVERTIME_SECOND)) {
                if (originToken.isToken()) {
                    if (originToken.isNotTokenOverTime()) {
                        Token newToken = new Token();
                        newToken.setSub(AUTHOR);
                        newToken.setData(NICKNAME, originToken.getNickname());
                        newToken.setTime(TOKEN_OVERTIME_SECOND);
                        jsonObject.put(RESULT, SUCCESS);
                        jsonObject.put(JWT, newToken.getToken());
                    } else {
                        jsonObject.put(RESULT, TOKEN_OVERTIME);
                    }
                } else {
                    jsonObject.put(RESULT, TOKEN_ERROR);
                }
            } else {
                jsonObject.put(RESULT, REQUEST_OVERTIME);
            }
        } else {
            jsonObject.put(RESULT, SIGNATURE_ERROR);
        }
        JsonUtil.writeResponse(response, jsonObject.toString());
    }
}
