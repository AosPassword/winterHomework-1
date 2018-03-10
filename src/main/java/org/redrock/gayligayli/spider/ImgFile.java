package org.redrock.gayligayli.spider;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.Match;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ImgFile {
    private static final String filePath = "D:" + File.separator + "img" + File.separator;
    private String urlStr;
    private String avId;
    private String authority;
    private String ends;
    private String path;

    public ImgFile(String url, String avId) {
        Matcher matcher = Pattern.compile("http://(\\w+).hdslb.com/bfs/archive/(\\w+).(\\D+)").matcher(url);
        this.urlStr = url;
        this.avId = avId;
        if (matcher.find()) {
            this.authority = matcher.group(1)+".hdsli.com";
//            this.urlStr="https://"+matcher.group(1)+".hdslb.com/bfs/archive/"+matcher.group(2)+"."+matcher.group(3)+"@200w_125h.webp";
            this.path="/bfs/archive/"+matcher.group(2)+"."+matcher.group(3)+"@200w_125h.webp";
            this.ends="."+matcher.group(3);
        }
    }

    public void down() {
        try {
            URL url = new URL(urlStr);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestMethod("GET");
            connection.setRequestProperty("authority", authority);
            connection.setRequestProperty("path",path);
            connection.setRequestProperty("scheme","https");
            connection.setRequestProperty("accept","image/webp,image/apng,image/*,*/*;q=0.8");
            connection.setRequestProperty("accept-encoding","gzip, deflate, br");
            connection.setRequestProperty("accept-language","zh-TW,zh-CN;q=0.9,zh;q=0.8");
            connection.setRequestProperty("referer","https://www.bilibili.com/");
            connection.setRequestProperty("user-agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/64.0.3282.186 Safari/537.36");
            connection.setUseCaches(false);
            connection.connect();
            BufferedInputStream bis = new BufferedInputStream(connection.getInputStream());
            FileOutputStream fileOut =new FileOutputStream(filePath + avId +ends);
            BufferedOutputStream bos = new BufferedOutputStream(fileOut);

            byte[] buf = new byte[4096];
            int length = bis.read(buf);
            while(length!=-1){
                bos.write(buf,0,length);
                length=bis.read(buf);
            }
            bos.close();
            bis.close();
            connection.disconnect();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        String str = "http://i2.hdslb.com/bfs/archive/5b673b1d38d069e6180e1187d9de2ff7cf644993.jpg";
        Matcher matcher = Pattern.compile("http://(\\w+).hdslb.com/bfs/archive/(\\w+).(\\D+)").matcher(str);
        if (matcher.find()){
            System.out.println(matcher.group(1));
            System.out.println(matcher.group(2));
            System.out.println(matcher.group(3));
        }
    }
}
