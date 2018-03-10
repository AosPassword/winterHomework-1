package org.redrock.gayligayli.spider;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import com.sun.corba.se.impl.orb.DataCollectorBase;
import com.sun.org.apache.xerces.internal.impl.xpath.regex.Match;
import net.sf.json.JSONArray;
import org.redrock.gayligayli.Dao.VideoDao;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.xml.crypto.Data;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.redrock.gayligayli.util.FinalStringUtil.*;
import static org.redrock.gayligayli.util.FinalStringUtil.MOVIES;
import static org.redrock.gayligayli.util.FinalStringUtil.SCREENING_HALL;

public class Main {
//    public static void main(String[] args) {
//        Database database = new Database();
//        JdbcTemplate jdbcTemplate = database.getJdbcTemplate();
//        String sql = "SELECT av_id,photo_url FROM video WHERE author_id = 1";
//        List<Map<String,Object>> list  = jdbcTemplate.queryForList(sql);
//        int length = list.size();
//        for(int i=0;i<list.size();i++){
//            String url = (String) list.get(i).get(PHOTO_URL_DATA);
//            int avId=(int)list.get(i).get(AV_ID_DATA);
//            ImgFile imgFile = new ImgFile(url,String.valueOf(avId));
//            imgFile.down();
//            System.out.println(length--);
//        }
//    }
public static void main(String[] args) {
    String path = "D:\\img\\";
    File file = new File(path);
    File[] imgs =  file.listFiles();
    int length = imgs.length;
    Configuration cfg = new Configuration(Zone.zone0());
    UploadManager uploadManager = new UploadManager(cfg);
    for (File img : imgs) {
        String key = "image/video/photo/"+img.getName();
        Auth auth = Auth.create(ACCESS_KEY,SECRET_KEY);
        String upToken = auth.uploadToken(BUCKET,key);
        try {
            Response response = uploadManager.put(img.toString(),key,upToken);
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(),DefaultPutRet.class);
            System.out.println(putRet.key);
            System.out.println(putRet.hash);
        } catch (QiniuException ex) {
            Response r = ex.response;
            System.err.println(r.toString());
            try {
                System.err.println(r.bodyString());
            } catch (QiniuException ex2) {
                //ignore
            }
        }
        System.out.println(length--);
    }
}

    public static String sendGet(String urlStr) throws IOException {
        URL url = new URL(urlStr);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("ContentType", "text/html;charset=utf-8");
        connection.setDoInput(true);
        connection.setDoOutput(true);
        connection.connect();
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(
                        connection.getInputStream(), "UTF-8"
                )
        );
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        return sb.toString();
    }
}
