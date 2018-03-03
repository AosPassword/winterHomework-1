package org.redrock.gayligayli.controller.servlet.loginAndRegister;


import net.sf.json.JSONObject;
import org.redrock.gayligayli.service.Command;
import org.redrock.gayligayli.service.Receiver;
import org.redrock.gayligayli.service.loginAndRegister.been.Token;
import org.redrock.gayligayli.service.loginAndRegister.commond.RefreshTokenCommand;
import org.redrock.gayligayli.util.JsonUtil;
import org.redrock.gayligayli.util.SecretUtil;
import org.redrock.gayligayli.util.TimeUtil;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

import static org.redrock.gayligayli.util.FinalStringUtil.*;

@WebServlet(name = "refreshToken", urlPatterns = "/refreshToken")
public class RefreshTokenServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        doGet(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding(UTF8);
        response.setCharacterEncoding(UTF8);
        response.setHeader(HEADER_ONE,HEADER_TWO);
        String tokenStr = request.getHeader(JWT);
        String jsonStr = JsonUtil.getJsonStr(request.getInputStream());

        Receiver receiver = new Receiver(jsonStr);
        Command command = new RefreshTokenCommand(receiver, tokenStr);
        command.exectue();

        JsonUtil.writeResponse(response, command.getResponseJson());
    }
}
