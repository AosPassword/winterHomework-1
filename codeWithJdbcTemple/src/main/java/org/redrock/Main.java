package org.redrock;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.redrock.gayligayli.Dao.VideoDao;
import org.redrock.gayligayli.service.videoInfo.been.Video;
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
//        jsonObject.put(USERNAME_TYPE, "telephone");
//        jsonObject.put(USERNAME, "17347898530");
//        jsonObject.put(PASSWORD, "zxc981201");
//        jsonObject.put(NICKNAME,"mashiroc");
//        jsonObject.put(TELEPHONE,"17347898530");
//        jsonObject.put(NAME,"test.mp4");
//        jsonObject.put(TYPE,"testType");
//        jsonObject.put(DESCRIPTION,"testDescription");
//        jsonObject.put(LENGTH,600);
//        jsonObject.put(AV_ID,97597741);
        jsonObject.put(ID,726);
//        jsonObject.put(CONTENT,"test3");
//        jsonObject.put(DEVICE,"windows");
//        jsonObject.put("pid",1);
//        jsonObject.put(APPEAR_TIME,10);
//        jsonObject.put(COLOR,"red");
//        jsonObject.put(FONTSIZE,5);
//        jsonObject.put(POSITION,0);
//        jsonObject.put(SEND_COIN,1);
        jsonObject.put(TIMESTAMP, String.valueOf(time));//"telephone.17347898530.zxc981201."+
        StringBuilder sb = new StringBuilder();
        Iterator it = jsonObject.keys();
        while (it.hasNext()){
            String key =(String) it.next();
            sb.append(jsonObject.getString(key)).append(".");
        }
        sb.delete(sb.length()-1,sb.length());
        jsonObject.put(SIGNATURE, SecretUtil.encoderHs256(sb.toString()));
//
//        jsonObject.put(TIMESTAMP,time);
//        jsonObject.put(SIGNATURE, SecretUtil.encoderHs256("email.mashiroc@outlook.com.Testttttt.password!."+String.valueOf(time)));
//        System.out.println(jsonObject.toString());
        long time1 = new Date().getTime();
        String result = sendPost("http://localhost:8080/videoPage",jsonObject.toString());
        System.out.println(result);
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
        con.setRequestProperty(JWT, "eyd0eXAnOiAnSldUJywnYWxnJzogJ0hTMjU2J30=.eydzdWInOidhdXRob3InLCduYmYnOicyNTkyMDAwJywnQmNvaW4nOicwJywnbGV2ZWwnOicxJywnaXNzJzonU2hpaW5hJywnbmlja25hbWUnOidtYXNoaXJvYycsJ2V4cCc6JzE1MjI3MzQ1OTEnLCdleHBlcmllbmNlJzonMCcsJ2lhdCc6JzE1MjAxNDI1OTEnLCdiaWdWaXAnOiduJywnY29pbic6JzAnfQ==.a759445f98541a1aeaa0d1aead1df57aec0a786c2d3542b0a4724b2ffd2b09b9");
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