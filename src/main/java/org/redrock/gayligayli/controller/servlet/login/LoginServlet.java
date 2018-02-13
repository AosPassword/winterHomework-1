package org.redrock.gayligayli.controller.servlet.login;

import net.sf.json.JSONObject;
import org.redrock.gayligayli.service.loginAndRegister.util.LoginUtil;
import org.redrock.gayligayli.service.loginAndRegister.been.Token;
import org.redrock.gayligayli.util.JsonUtil;
import org.redrock.gayligayli.util.SecretUtil;
import org.redrock.gayligayli.util.TimeUtil;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

import static org.redrock.gayligayli.util.FinalStringUtil.*;

public class LoginServlet extends HttpServlet {


    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        doPost(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setCharacterEncoding(UTF8);
        request.setCharacterEncoding(UTF8);
        String jsonStr = JsonUtil.getJsonStr(request.getInputStream());
        JSONObject requrestJson = JSONObject.fromObject(jsonStr);
        String usernameType = requrestJson.getString(USERNAME_TYPE);
        String username = requrestJson.getString(USERNAME);
        String password = requrestJson.getString(PASSWORD);
        String timestamp = requrestJson.getString(TIMESTAMP);
        String signature = requrestJson.getString(SIGNATURE);
        JSONObject jsonObject = new JSONObject();
        StringBuilder data = new StringBuilder();
        data.append(usernameType).append(SIGNATURE_SEPARATOR)
                .append(username).append(SIGNATURE_SEPARATOR)
                .append(password).append(SIGNATURE_SEPARATOR)
                .append(timestamp);
        if (SecretUtil.isSecret(data.toString(), signature)) {
            if (!TimeUtil.isNotOverTime(timestamp, REQUEST_OVERTIME_SECOND)) {
                if (usernameType != null && username != null && password != null) {
                    if (!LoginUtil.hasSomeCharacter(username) && LoginUtil.isPass(usernameType,username,password)) {
                            Token token = new Token();
                            token.setSub(AUTHOR);
                            token.setTime(TOKEN_OVERTIME_SECOND);
                            token.setData(usernameType, username);

                            jsonObject.put(RESULT, SUCCESS);
                            jsonObject.put(JWT, token.getToken());
                    } else {
                        jsonObject.put(RESULT, PASSWORD_ERROR);
                    }
                } else {
                    jsonObject.put(RESULT, REQUEST_ERROR);
                }
            } else {
                jsonObject.put(RESULT, REQUEST_OVERTIME);
            }
        } else {
            jsonObject.put(RESULT, SIGNATURE_ERROR);
        }
        JsonUtil.writeResponse(response,jsonObject.toString());
    }
}
