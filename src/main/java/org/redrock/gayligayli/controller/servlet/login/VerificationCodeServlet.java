package org.redrock.gayligayli.controller.servlet.login;

import com.aliyuncs.exceptions.ClientException;
import net.sf.json.JSONObject;
import org.redrock.gayligayli.util.JsonUtil;
import org.redrock.gayligayli.util.SecretUtil;
import org.redrock.gayligayli.service.loginAndRegister.util.SendUtil;
import org.redrock.gayligayli.util.TimeUtil;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

import static org.redrock.gayligayli.util.FinalStringUtil.*;

public class VerificationCodeServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        doGet(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding(UTF8);
        response.setCharacterEncoding(UTF8);
        /*
        {telephone:"",
         timestamp:"",
         signture:"HMacHs256(telephone.timestamp)"}
         */
        String jsonStr = JsonUtil.getJsonStr(request.getInputStream());
        JSONObject requestJson = JSONObject.fromObject(jsonStr);

        String telephone = requestJson.getString(TELEPHONE);
        String timestamp = requestJson.getString(TIMESTAMP);
        String signatrue = requestJson.getString(SIGNATURE);
        JSONObject jsonObject = new JSONObject();
        if (SecretUtil.isSecret(telephone + SIGNATURE_SEPARATOR + timestamp, signatrue)) {
            if (TimeUtil.isNotOverTime(timestamp, REQUEST_OVERTIME_SECOND)) {
                String code = null;
                try {
                    code = SendUtil.sendSms(telephone);
                } catch (ClientException e) {
                    e.printStackTrace();
                }
                if (code != null) {
                    jsonObject.put(RESULT, SUCCESS);
                    jsonObject.put(VERIFICATION, SecretUtil.encoderHs256(code));
                } else {
                    jsonObject.put(RESULT, SEND_ERROR);
                }
            } else {
                jsonObject.put(RESULT,REQUEST_OVERTIME);
            }
        } else {
            jsonObject.put(RESULT, SIGNATURE_ERROR);
        }

        JsonUtil.writeResponse(response,jsonObject.toString());
    }
}
