package org.redrock.gayligayli.loginAndRegister.servlet;

import com.mysql.jdbc.log.LogUtils;
import net.sf.json.JSONObject;
import org.redrock.gayligayli.loginAndRegister.util.LoginUtil;
import org.redrock.gayligayli.loginAndRegister.been.Token;
import org.redrock.gayligayli.util.JsonUtil;
import org.redrock.gayligayli.util.SecretUtil;
import org.redrock.gayligayli.util.TimeUtil;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

public class LoginServlet extends HttpServlet {
    private final static int OVERTIME = 2592000;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        doPost(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");
        String jsonStr = JsonUtil.getJsonStr(request.getInputStream());
        JSONObject requrestJson = JSONObject.fromObject(jsonStr);
        String usernameType = requrestJson.getString("usernameType");
        String username = requrestJson.getString("username");
        String password = requrestJson.getString("password");
        String timestamp = requrestJson.getString("timestamp");
        String signature = requrestJson.getString("signature");
        JSONObject jsonObject = new JSONObject();
        StringBuilder data = new StringBuilder();
        data.append(usernameType).append(".").append(username).append(".").append(password).append(".").append(timestamp);
        if (SecretUtil.isSecret(data.toString(), signature)) {
            if (!TimeUtil.isNotOverTime(timestamp, 600)) {
                if (usernameType != null && username != null && password != null) {
                    if (!LoginUtil.hasSomeCharacter(username)) {
                        if (LoginUtil.isPass(usernameType, username, password)) {
                            Token token = new Token();
                            token.setSub("user");
                            token.setTime(OVERTIME);
                            token.setData(usernameType, username);
                            jsonObject.put("result", "Success");
                            jsonObject.put("token", token.getToken());
                        } else {
                            jsonObject.put("result", "PasswordError");
                        }
                    } else {
                        jsonObject.put("result", "PasswordError");
                    }
                } else {
                    jsonObject.put("result", "RequestError");
                }
            } else {
                jsonObject.put("result", "OverTime");
            }
        } else {
            jsonObject.put("result", "SignatureError");
        }
        BufferedWriter writer = new BufferedWriter(
                new PrintWriter(response.getWriter())
        );
        writer.write(jsonObject.toString());
        writer.flush();
        writer.close();
    }
}
