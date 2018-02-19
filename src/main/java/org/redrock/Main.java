package org.redrock;

import net.sf.json.JSONObject;

import java.io.*;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

import static org.redrock.gayligayli.util.FinalStringUtil.*;

public class Main {
    public static void main(String[] args) throws IOException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(USERNAME_TYPE,"telephone");
        jsonObject.put(USERNAME,"17347898530");
        jsonObject.put(PASSWORD,"zxc981201");
        jsonObject.put(TIMESTAMP,"1519047332");
        jsonObject.put(SIGNATURE,"de095794f2a00a74c10c5ebd74172ae64970747cee65057b6a85f2426d006241");
        System.out.println(jsonObject.toString());
        String result = sendPost("http://localhost:8080/login",jsonObject.toString());
        System.out.println(result);
    }

    public static String sendPost(String strUrl, String info) throws IOException {
        URL url = new URL(strUrl);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("ContentType", "text/xml;charset=utf-8");
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