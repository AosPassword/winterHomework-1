package org.redrock.gayligayli.util;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class JsonUtil {
    public static String getJsonStr(ServletInputStream input) throws IOException {
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(
                        input,"UTF-8"
                )
        );
        StringBuilder sb = new StringBuilder();
        String line = null;
        while((line=reader.readLine())!=null){
            sb.append(line);
        }
        return sb.toString();
    }
}
