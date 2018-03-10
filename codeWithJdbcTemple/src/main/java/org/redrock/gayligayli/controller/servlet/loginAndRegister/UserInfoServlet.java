package org.redrock.gayligayli.controller.servlet.loginAndRegister;

import net.sf.json.JSONObject;
import org.redrock.gayligayli.Dao.UserDao;
import org.redrock.gayligayli.service.loginAndRegister.util.LoginUtil;
import org.redrock.gayligayli.util.JsonUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

import static org.redrock.gayligayli.util.FinalStringUtil.*;

@WebServlet(name = "UserInfoServlet")
public class UserInfoServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = (String) request.getAttribute(ID);
        JSONObject jsonObject = new JSONObject();
        if(LoginUtil.isNumeric(id)){
            jsonObject.put(RESULT,SUCCESS);
            Map<String,String> map = UserDao.getUserInfo(ID,id);
            jsonObject.put(DATA,map);
        } else {
            jsonObject.put(RESULT,PARAMETER_ERROR);
        }
        JsonUtil.writeResponse(response,jsonObject.toString());
    }
}
