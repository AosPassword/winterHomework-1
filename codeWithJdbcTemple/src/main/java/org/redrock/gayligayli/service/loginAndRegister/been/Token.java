package org.redrock.gayligayli.service.loginAndRegister.been;

import java.io.UnsupportedEncodingException;
import java.util.*;

import net.sf.json.JSONObject;
import org.redrock.gayligayli.Dao.UserDao;
import org.redrock.gayligayli.util.SecretUtil;

import static org.redrock.gayligayli.util.FinalStringUtil.*;

public class Token {
    /*{
     'typ': 'JWT',
     'alg': 'HS256'
    }*/
/*sub: 该JWT所面向的用户
iss: 该JWT的签发者
iat(issued at): 在什么时候签发的token
exp(expires): token什么时候过期
nbf(not before)：token在此时间之前不能被接收处理
jti：JWT ID为web token提供唯一标识
*/
    private String header;
    private Map<String, String> playloadMap;
    private String playloadStr;
    private String signature;

    public Token() {
        playloadMap = new HashMap<>();
        this.header = "eyd0eXAnOiAnSldUJywnYWxnJzogJ0hTMjU2J30=";
        this.playloadMap.put("iss", "Shiina");
    }

    public Token(String token) {
        String[] tokens = token.split("\\.");
        if (tokens.length == 3) {
            this.playloadMap= new HashMap<>();
            this.header = tokens[0];
            this.playloadStr = new String(Base64.getDecoder().decode(tokens[1].getBytes()));
            System.out.println(this.playloadStr);
            JSONObject jsonObject = JSONObject.fromObject(this.playloadStr);
            Set<String> set = jsonObject.keySet();
            for (String key : set) {
                playloadMap.put(key, jsonObject.getString(key));
            }
            this.signature = tokens[2];
        }
    }

    public Map<String, String> getPlayloadMap() {
        return this.playloadMap;
    }

    public String getNickname() {
        return this.playloadMap.get(NICKNAME);
    }



    /*{
 'typ': 'JWT',
 'alg': 'HS256'
}*/

    public void setData(String usernameType, String username) {
        Map<String, String> userInfo = UserDao.getUserInfo(usernameType, username);
        for (Map.Entry<String, String> entry : userInfo.entrySet()) {
            this.playloadMap.put(entry.getKey(), entry.getValue());
        }
    }

    public void setSub(String sub) {
        playloadMap.put("sub", sub);
    }

    //time是过期的秒数
    public void setTime(long time) {
        long nowTime = (long) Math.ceil(new Date().getTime() / 1000);
        playloadMap.put("nbf", String.valueOf(time));
        playloadMap.put("iat", String.valueOf(nowTime));
        playloadMap.put("exp", String.valueOf(nowTime + time));
    }

    public String getToken() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        for (Map.Entry<String, String> entry : playloadMap.entrySet()) {
            sb.append("'").append(entry.getKey()).append("':'")
                    .append(entry.getValue()).append("',");
        }
        sb.delete(sb.length() - 1, sb.length());
        sb.append("}");
        this.playloadStr = sb.toString();
        String base64Playload = null;
        try {
            base64Playload = Base64.getEncoder().encodeToString(sb.toString().getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String signature = this.header + "." + base64Playload;
        this.signature = signature;

        String hs256Signature = SecretUtil.encoderHs256(signature);
        return this.header + "." + base64Playload + "." + hs256Signature;
    }


    public boolean isToken() {
        if (this.header != null && this.playloadStr != null && this.signature != null) {
            String userSignature = SecretUtil.encoderHs256(header + "." + Base64.getEncoder().encodeToString(playloadStr.getBytes()));
            System.out.println(userSignature);
            System.out.println("one");
            System.out.println(signature);
            System.out.println("two");
            System.out.println(playloadMap.size());
            if (this.signature.equals(userSignature) && playloadMap.size() == 11) {
                return true;
            }
        }
        return false;
    }

    public boolean isNotTokenOverTime() {
        long tokenExp = Long.parseLong(playloadMap.get("exp"));
        long tokenNbf = Long.parseLong(playloadMap.get("nbf"));
        long nowTime = (long) Math.ceil(new Date().getTime() / 1000);
        return tokenExp >= nowTime && nowTime >= tokenNbf;
    }


    public static void main(String[] args) {
        HashMap playloadMap = new HashMap();
        long time = 1000000000L;
        long nowTime = (long) Math.ceil(new Date().getTime() / 1000);
        playloadMap.put("nbf", String.valueOf(time));
        playloadMap.put("iat", String.valueOf(nowTime));
        playloadMap.put("exp", String.valueOf(nowTime + time));
        System.out.println(playloadMap);
    }
}
