package org.redrock.gayligayli.loginAndRegister.servlet;

import com.aliyuncs.exceptions.ClientException;
import net.sf.json.JSONObject;
import org.redrock.gayligayli.loginAndRegister.util.LoginUtil;
import org.redrock.gayligayli.util.JsonUtil;
import org.redrock.gayligayli.util.SecretUtil;
import org.redrock.gayligayli.loginAndRegister.util.SendUtil;
import org.redrock.gayligayli.util.TimeUtil;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.sql.Time;

public class VerificationCodeServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        doGet(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        /*
        {telephone:"",
         timestamp:"",
         signture:"HMacHs256(telephone.timestamp)"}
         */
        String jsonStr = JsonUtil.getJsonStr(request.getInputStream());
        JSONObject requestJson = JSONObject.fromObject(jsonStr);
        String telephone = requestJson.getString("telephone");
        String timestamp = requestJson.getString("timestamp");
        String signatrue = requestJson.getString("signature");
        JSONObject jsonObject = new JSONObject();
        if (SecretUtil.isSecret(telephone + "." + timestamp, signatrue)) {
            if (TimeUtil.isNotOverTime(timestamp, 1800)) {
                String code = null;
                try {
                    code = SendUtil.sendSms(telephone);
                } catch (ClientException e) {
                    e.printStackTrace();
                }
                if (code != null) {
                    jsonObject.put("result", "Success");
                    jsonObject.put("verification", SecretUtil.encoderHs256(code));
                } else {
                    jsonObject.put("result", "SendError");
                }
            } else {
                jsonObject.put("result","OverTime");
            }
        } else {
            jsonObject.put("result", "SignatureError");
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
}
