package org.redrock.gayligayli.loginAndRegister.servlet;

import net.sf.json.JSON;
import net.sf.json.JSONObject;
import org.redrock.gayligayli.Dao.UserDao;
import org.redrock.gayligayli.loginAndRegister.util.LoginUtil;
import org.redrock.gayligayli.util.JsonUtil;
import org.redrock.gayligayli.util.SecretUtil;
import org.redrock.gayligayli.util.TimeUtil;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.sql.Time;

public class UserHasServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String usernameType = request.getParameter("usernameType");
        String username = request.getParameter("username");
        JSONObject jsonObject = new JSONObject();
        if (LoginUtil.hasUser(usernameType, username)) {
            jsonObject.put("result", "yes");
        } else {
            jsonObject.put("result", "no");
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

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        doGet(request, response);
    }
}
