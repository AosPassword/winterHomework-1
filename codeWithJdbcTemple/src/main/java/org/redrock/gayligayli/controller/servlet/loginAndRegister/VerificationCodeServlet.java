package org.redrock.gayligayli.controller.servlet.loginAndRegister;

import com.aliyuncs.exceptions.ClientException;
import net.sf.json.JSONObject;
import org.redrock.gayligayli.service.Command;
import org.redrock.gayligayli.service.Receiver;
import org.redrock.gayligayli.service.loginAndRegister.commond.VerificationCodeCommand;
import org.redrock.gayligayli.util.JsonUtil;
import org.redrock.gayligayli.util.SecretUtil;
import org.redrock.gayligayli.service.loginAndRegister.util.SendUtil;
import org.redrock.gayligayli.util.TimeUtil;

import javax.resource.spi.ConnectionManager;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

import static org.redrock.gayligayli.util.FinalStringUtil.*;

@WebServlet(name = "verificationCode", urlPatterns = "/verificationCode")
public class VerificationCodeServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        doPost(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding(UTF8);
        response.setCharacterEncoding(UTF8);
        response.setHeader(HEADER_ONE,HEADER_TWO);

        String jsonStr = JsonUtil.getJsonStr(request.getInputStream());

        Receiver receiver = new Receiver(jsonStr);
        Command command = new VerificationCodeCommand(receiver);
        command.exectue();

        JsonUtil.writeResponse(response, command.getResponseJson());

    }
}

