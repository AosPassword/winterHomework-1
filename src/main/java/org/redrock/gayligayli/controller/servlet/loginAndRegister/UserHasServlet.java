package org.redrock.gayligayli.controller.servlet.loginAndRegister;

import net.sf.json.JSONObject;
import org.redrock.gayligayli.service.loginAndRegister.util.LoginUtil;
import org.redrock.gayligayli.util.JsonUtil;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.redrock.gayligayli.util.FinalStringUtil.*;

@WebServlet(name = "isUserHas", urlPatterns = "/isUserHas")
public class UserHasServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding(UTF8);
        response.setCharacterEncoding(UTF8);
        String usernameType = request.getParameter(USERNAME_TYPE);
        String username = request.getParameter(USERNAME);
        JSONObject jsonObject = new JSONObject();
        if (LoginUtil.hasUser(usernameType, username)) {
            jsonObject.put(RESULT, USER_EXIST);
        } else {
            jsonObject.put(RESULT, CAN_REGISTER);
        }
        JsonUtil.writeResponse(response, jsonObject.toString());
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        doGet(request, response);
    }

}
