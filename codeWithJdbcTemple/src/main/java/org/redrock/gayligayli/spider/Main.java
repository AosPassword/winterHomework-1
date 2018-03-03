package org.redrock.gayligayli.spider;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.Match;
import net.sf.json.JSONArray;
import org.redrock.gayligayli.Dao.VideoDao;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.redrock.gayligayli.util.FinalStringUtil.*;
import static org.redrock.gayligayli.util.FinalStringUtil.MOVIES;
import static org.redrock.gayligayli.util.FinalStringUtil.SCREENING_HALL;

public class Main {
    public static void main(String[] args) {

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
