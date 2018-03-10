package org.redrock;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.redrock.gayligayli.Dao.VideoDao;
import org.redrock.gayligayli.service.videoInfo.been.Video;
import org.redrock.gayligayli.util.FinalStringUtil;
import org.redrock.gayligayli.util.SecretUtil;

import javax.xml.bind.Element;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.redrock.gayligayli.spider.Main.sendGet;
import static org.redrock.gayligayli.util.FinalStringUtil.*;

public class Main {
    public static void main(String[] args) throws IOException {
//        JSONObject jsonObject = new JSONObject();
//        jsonObject.put(USERNAME_TYPE, "email");
//        jsonObject.put(USERNAME,"mashiroc@outlook.com");
//        jsonObject.put(NICKNAME,"Testttttt");
//        jsonObject.put(PASSWORD,"password!");
        long time = (long) Math.ceil(new Date().getTime() / 1000);
        JSONObject jsonObject = new JSONObject();
//        jsonObject.put(USERNAME_TYPE,"a= -1 UNION SELECT \"6b86b273ff34fce19d6b804eff5a3f5747ada4eaa22f1d49c01e52ddb7875b4b\" #");
//        jsonObject.put(USERNAME_TYPE, "telephone = '17695089761' AND (SELECT SUBSTRING(user(),0,1))='a' -- AND 'a'");
//        jsonObject.put(USERNAME_TYPE,"email");
//        jsonObject.put(USERNAME, "843961670@qq.com");
//        jsonObject.put(PASSWORD, "zxc981201");
//        jsonObject.put(NICKNAME,"");
//        jsonObject.put(TELEPHONE,"17347898530");
//        jsonObject.put(NAME,"测试.webm");
//        jsonObject.put(TYPE,"music");
//        jsonObject.put(DESCRIPTION,"testDescription");
//        jsonObject.put(LENGTH,600);
//        jsonObject.put(AV_ID,92964541);
//        jsonObject.put(ID,748);
//        jsonObject.element(DATA,Base64.getEncoder().encodeToString("动画".getBytes()));
//        jsonObject.element(PAGE,1);
//        jsonObject.put(CONTENT,"test4");
//        jsonObject.put(DEVICE,"windows");
//        jsonObject.put(COMMENT_PID,7);
//        jsonObject.put(APPEAR_TIME,10);
//        jsonObject.put(COLOR,"red");
//        jsonObject.put(FONTSIZE,5);
//        jsonObject.put(POSITION,0);
        jsonObject.put(VIDEO_ID,748);
//        jsonObject.put(ACTION,CANCEL);
        jsonObject.put(SEND_COIN,1);
//        jsonObject.put(DATA,"吃鸡");
//        jsonObject.put(PAGE,1);
        jsonObject.put(TIMESTAMP, String.valueOf(time));//"telephone.17347898530.zxc981201."+
        StringBuilder sb = new StringBuilder();
        Iterator it = jsonObject.keys();
        while (it.hasNext()){
            String key =(String) it.next();
            sb.append(jsonObject.getString(key)).append(".");
        }
        sb.delete(sb.length()-1,sb.length());
        System.out.println(sb.toString());
        System.out.println(Base64.getEncoder().encodeToString(sb.toString().getBytes()));
        jsonObject.put(SIGNATURE,SecretUtil.encoderHs256(Base64.getEncoder().encodeToString(sb.toString().getBytes())));
//
//        jsonObject.put(TIMESTAMP,time);
//        jsonObject.put(SIGNATURE, SecretUtil.encoderHs256("email.mashiroc@outlook.com.Testttttt.password!."+String.valueOf(time)));
//        System.out.println(jsonObject.toString());
        long time1 = new Date().getTime();
//        System.out.println(jsonObject.toString());
        System.out.println(jsonObject.toString());
//        String test = "{\"usernameType\":\"\",\"username\":\"a\",\"password\":\"1\",\"timestamp\":\"1520251308\",\"signature\":\"5bc09a48544244328d00198495a89d235e730ba424e88c2042bb0df7b345ab2e\"}";
//        String data = "{\"name\":\"薛之谦 刚刚好 国语 流行 NBN07738 MVMKV.Com MV下载精灵.webm\",\"type\":\"music\",\"description\":\"detail\",\"length\":33549382,\"timestamp\":\"1520173013\",\"signature\":\"98a1579d85735bfce77fef0289a07ec8cd2c030577e90f62b59a660d6b22ddc6\"}\n";
        String result = sendPost("http://www.mashiroc.cn/gayligayliapi/sendCoin",jsonObject.toString());
//        String result=sendGet("http://localhost:8080/videoPage"+"?json="+jsonObject.toString());
        System.out.println(result);
//        JSONObject resultJson = JSONObject.fromObject(result);
//        System.out.println(resultJson.getJSONArray(TOP_INFO).size());
//        System.out.println(resultJson.getJSONObject(GAME).getString(INFO));
//        System.out.println(resultJson.getJSONObject(GAME).getString(RANK));
//        System.out.println(resultJson.getJSONObject(GAME).getJSONArray(RANK).size());
//        long time = (long) Math.ceil(new Date().getTime()/1000);
//        System.out.println(jsonObject);//refreshToken
//        String result = sendPost("http://localhost:8080/uploadToken", jsonObject.toString());
//        System.out.println(result);

//        System.out.println(sendGet("http://localhost:8080/homePage"));
        System.out.println(new Date().getTime()-time1);
//        System.out.println(sendPost("http://localhost:8080/","null"));
//        sendPost("http://localhost:8080/spider","null");
    }

    public static String sendPost(String strUrl, String info) throws IOException {
        URL url = new URL(strUrl);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestProperty(JWT,"eyJ0eXA6IkpXVCIsImFsZyI6IkhTMjU2In0=.eyJzdWIiOiJhdXRob3IiLCJuYmYiOiIyNTkyMDAwIiwiQmNvaW4iOiI5OTkiLCJsZXZlbCI6IjkiLCJpc3MiOiJTaGlpbmEiLCJuaWNrbmFtZSI6IuadpeiHqmJpbGliaWxpIiwiZXhwIjoiMTUyMzEwMzQ5MiIsImV4cGVyaWVuY2UiOiI5OTkiLCJpYXQiOiIxNTIwNTExNDkyIiwiYmlnVmlwIjoieSIsImNvaW4iOiI5OTkifQ==.9209e4f55154bbbbdb4ce1ee18eca58a38fafe8de34cd46fbc1867a1fd7189a7");
                con.setRequestMethod("POST");
        con.setRequestProperty("ContentType", "text/html;charset=utf-8");
        con.setDoInput(true);
        con.setDoOutput(true);
        con.connect();
        BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(
                        con.getOutputStream(), "UTF-8"
                )
        );
        writer.write(info);
        writer.flush();
        writer.close();
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(
                        con.getInputStream(), "UTF-8"
                )
        );
        StringBuffer sb = new StringBuffer();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        reader.close();
        return sb.toString();
    }

}


//public class Main {
//    static int max;
//
//    public static void main(String[] args) {
//        max = 1;
//        Node root = new Node();
//
//        Scanner scan = new Scanner(System.in);
//        int n = scan.nextInt();
//        int a;
//        for (int i = 0; i < n - 1; i++) {
//            a = scan.nextInt();
//            root.add(a);
//        }
//        if(root.isTree())
//            System.out.println("Yes");
//        else
//            System.out.println("No");
//        return;
//    }
//}
//
//class Node {
//    static boolean flag;
//    int num;
//    List<Node> child = new ArrayList<>();
//
//    public void newNode() {
//        this.child.add(new Node());
//    }
//
//    public List<Node> getChild() {
//        return child;
//    }
//
//    public Node() {
//        this.num = Main.max;
//        Main.max++;
//    }
//
//    public void add(int a) {
//        if (a == 1 && num == 1) {
//            this.child.add(new Node());
//        } else if (this.child.size() != 0) {
//            if (this.child.get(this.child.size() - 1).num >= a) {
//                if (this.child.get(0).num <= a) {
//                    for (int i = 0; i < this.child.size(); i++) {
//                        if (this.child.get(i).num == a) {
//                            this.child.get(i).newNode();
//                            flag = true;
//                        }
//                    }
//                }
//            }
//        }
//        for (int i = 0; i < this.child.size(); i++) {
//            if (flag == true) {
//                break;
//            }
//            this.child.get(i).add(a);
//        }
//    }
//
//    public boolean isTree() {
//        int count = 0;
//        if (this.child.size() >= 3) {
//            for (int i = 0; i < this.child.size(); i++) {
//                if (this.child.get(i).getChild().size() == 0) {
//                    count++;
//                } else {
//                    if(!this.child.get(i).isTree())
//                        return false;
//                }
//            }
//        }
//        if (count < 3) {
//            return false;
//        }
//        return true;
//    }
//}