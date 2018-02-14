package org.redrock.gayligayli.controller.servlet.login;

import net.sf.json.JSONObject;
import org.redrock.gayligayli.Dao.UserDao;
import org.redrock.gayligayli.service.loginAndRegister.been.Token;
import org.redrock.gayligayli.service.loginAndRegister.util.LoginUtil;
import org.redrock.gayligayli.util.JsonUtil;
import org.redrock.gayligayli.util.SecretUtil;
import org.redrock.gayligayli.util.TimeUtil;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.redrock.gayligayli.util.FinalStringUtil.*;

public class RegisterServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        doPost(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding(UTF8);
        response.setCharacterEncoding(UTF8);
        String jsonStr = JsonUtil.getJsonStr(request.getInputStream());
        JSONObject requestJson = JSONObject.fromObject(jsonStr);
        String usernameType = requestJson.getString(USERNAME_TYPE);
        String username = requestJson.getString(USERNAME);
        String password = requestJson.getString(PASSWORD);
        String nickname = requestJson.getString(NICKNAME);
        String timestamp = requestJson.getString(TIMESTAMP);
        String signature = requestJson.getString(SIGNATURE);
        JSONObject jsonObject = new JSONObject();
        StringBuilder sb = new StringBuilder();

        sb.append(usernameType).append(SIGNATURE_SEPARATOR)
                .append(username).append(SIGNATURE_SEPARATOR)
                .append(password).append(SIGNATURE_SEPARATOR)
                .append(nickname).append(SIGNATURE_SEPARATOR)
                .append(timestamp);
        if(SecretUtil.isSecret(sb.toString(),signature)){
            if(TimeUtil.isNotOverTime(timestamp,REQUEST_OVERTIME_SECOND)){
                if(!LoginUtil.hasUser(usernameType,username)){
                    UserDao.insertNewUser(nickname,password,username,usernameType);
                    Token token = new Token();
                    token.setSub(AUTHOR);
                    token.setTime(TOKEN_OVERTIME_SECOND);
                    token.setData(usernameType,username);
                    jsonObject.put(RESULT,SUCCESS);
                    jsonObject.put(JWT,token.getToken());
                } else {
                    jsonObject.put(RESULT,USER_EXIST);
                }
            } else {
                jsonObject.put(RESULT,REQUEST_OVERTIME);
            }
        } else {
            jsonObject.put(RESULT,SIGNATURE_ERROR);
        }
        JsonUtil.writeResponse(response,jsonObject.toString());
    }
}
