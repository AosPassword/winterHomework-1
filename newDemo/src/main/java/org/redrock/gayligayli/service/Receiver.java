package org.redrock.gayligayli.service;

import net.sf.json.JSONObject;
import org.redrock.gayligayli.service.loginAndRegister.been.Token;
import org.redrock.gayligayli.service.loginAndRegister.util.LoginUtil;
import org.redrock.gayligayli.util.SecretUtil;
import org.redrock.gayligayli.util.TimeUtil;

import java.util.Date;
import java.util.Set;

import static org.redrock.gayligayli.util.FinalStringUtil.*;

public class Receiver {
    private JSONObject requestJson;


    public Receiver(String jsonStr) {
        this.requestJson = JSONObject.fromObject(jsonStr);
    }

    private int isSignatureTrue() {
        int flag = -1;
        String signature;
        if (requestJson != null) {
            if ((signature = requestJson.getString(SIGNATURE)) != null) {
                Set<String> set = requestJson.keySet();
                StringBuilder sb = new StringBuilder();
                for (String str : set) {
                    sb.append(requestJson.getString(str)).append(SIGNATURE_SEPARATOR);
                }
                sb.delete(sb.length() - 1, sb.length());
                if (SecretUtil.isSecret(sb.toString(), signature)) {
                    if (TimeUtil.isNotOverTime(requestJson.getString(TIMESTAMP), new Date().getTime())) {
                        flag = 1;
                    }
                } else {
                    flag = 0;
                }
            }
        }
        return flag;
    }

    private String getErrorString(int flag) {
        JSONObject jsonObject = new JSONObject();
        if (flag == -1) {
            jsonObject.put(RESULT, SIGNATURE_ERROR);
        } else {
            jsonObject.put(RESULT, REQUEST_OVERTIME);
        }
        return jsonObject.toString();
    }


    public String login() {
        String result;
        int flag;
        if ((flag = isSignatureTrue()) == 1) {
            String usernameType = requestJson.getString(USERNAME_TYPE);
            String username = requestJson.getString(USERNAME);
            String password = requestJson.getString(PASSWORD);

            JSONObject jsonObject = new JSONObject();

            if (usernameType != null && username != null && password != null) {
                if (!LoginUtil.hasSomeCharacter(username) && LoginUtil.isPass(usernameType, username, password)) {
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
            result = jsonObject.toString();
        } else {
            result = getErrorString(flag);
        }
        return result;
    }

    public String RefreshToken() {
    }

    public String Register() {
    }

    public String VerificationCode() {
    }
}
