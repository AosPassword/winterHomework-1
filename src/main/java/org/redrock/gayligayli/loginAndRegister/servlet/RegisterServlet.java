package org.redrock.gayligayli.loginAndRegister.servlet;

import net.sf.json.JSONObject;
import org.redrock.gayligayli.Dao.UserDao;
import org.redrock.gayligayli.loginAndRegister.been.Token;
import org.redrock.gayligayli.loginAndRegister.util.LoginUtil;
import org.redrock.gayligayli.util.JsonUtil;
import org.redrock.gayligayli.util.SecretUtil;
import org.redrock.gayligayli.util.TimeUtil;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class RegisterServlet extends HttpServlet {
    private final static int OVERTIME = 2592000;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        doPost(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String jsonStr = JsonUtil.getJsonStr(request.getInputStream());
        JSONObject requestJson = JSONObject.fromObject(jsonStr);
        String usernameType = requestJson.getString("usernameType");
        String username = requestJson.getString("username");
        String password = requestJson.getString("password");
        String nickname = requestJson.getString("nicename");
        String timestamp = requestJson.getString("timestamp");
        String signature = requestJson.getString("signature");
        JSONObject jsonObject = new JSONObject();
        StringBuilder sb = new StringBuilder();
        sb.append(usernameType).append(".").append(username).append(".").append(password).append(".").append(nickname).append(".").append(timestamp);
        if(SecretUtil.isSecret(sb.toString(),signature)){
            if(TimeUtil.isNotOverTime(timestamp,600)){
                if(!LoginUtil.hasUser(usernameType,username)){
                    UserDao.insertNewUser(nickname,password,username,usernameType);
                    Token token = new Token();
                    token.setSub("user");
                    token.setTime(OVERTIME);
                    token.setData(usernameType,username);
                    jsonObject.put("result","Success");
                    jsonObject.put("jwt",token.getToken());
                } else {
                    jsonObject.put("result","UserExist");
                }
            } else {
                jsonObject.put("result","OverTime");
            }
        } else {
            jsonObject.put("result","SignatureError");
        }
        BufferedWriter writer = new BufferedWriter(
                new PrintWriter(
                        response.getWriter()
                )
        );
        writer.write(jsonObject.toString());
        writer.flush();
        writer.close();
    }
}
