package org.redrock.gayligayli.controller.servlet.spider;

import net.sf.json.JSONArray;
import org.redrock.gayligayli.Dao.VideoDao;
import org.redrock.gayligayli.util.JsonUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.redrock.gayligayli.spider.Main.sendGet;
import static org.redrock.gayligayli.util.FinalStringUtil.*;

@WebServlet(name = "SpiderServlet", urlPatterns = "/spider")
public class SpiderServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        spider();
        JsonUtil.writeResponse(response, "ok");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    private static void spider() {


        String[] apiUrl = new String[14];
        //动画
        apiUrl[0] = "https://api.bilibili.com/x/web-interface/dynamic/region?callback=jQuery17205512269327318235_1519798039235&jsonp=jsonp&ps=10&rid=1&_=1519798138760";
        //番剧
        apiUrl[1] = "https://api.bilibili.com/x/web-interface/dynamic/region?callback=jQuery172042186704389298035_1519800751929&jsonp=jsonp&ps=10&rid=13&_=1519801711982";
        //国创
        apiUrl[2] = "https://api.bilibili.com/x/web-interface/dynamic/region?callback=jQuery172042186704389298035_1519800751928&jsonp=jsonp&ps=10&rid=168&_=1519801678042";
        //音乐
        apiUrl[3] = "https://api.bilibili.com/x/web-interface/dynamic/region?callback=jQuery172042186704389298035_1519800751936&jsonp=jsonp&ps=10&rid=3&_=1519801768932";
        //舞蹈
        apiUrl[4] = "https://api.bilibili.com/x/web-interface/dynamic/region?callback=jQuery172042186704389298035_1519800751937&jsonp=jsonp&ps=10&rid=129&_=1519801806951";
        //游戏
        apiUrl[5] = "https://api.bilibili.com/x/web-interface/dynamic/region?callback=jQuery172042186704389298035_1519800751942&jsonp=jsonp&ps=10&rid=4&_=1519801849208";
        //科技
        apiUrl[6] = "https://api.bilibili.com/x/web-interface/dynamic/region?callback=jQuery172042186704389298035_1519800751947&jsonp=jsonp&ps=10&rid=36&_=1519801893812";
        //生活
        apiUrl[7] = "https://api.bilibili.com/x/web-interface/dynamic/region?callback=jQuery172042186704389298035_1519800751956&jsonp=jsonp&ps=10&rid=160&_=1519801936283";
        //鬼畜
        apiUrl[8] = "https://api.bilibili.com/x/web-interface/dynamic/region?callback=jQuery172042186704389298035_1519800751961&jsonp=jsonp&ps=10&rid=119&_=1519801973276";
        //时尚
        apiUrl[9] = "https://api.bilibili.com/x/web-interface/dynamic/region?callback=jQuery172042186704389298035_1519800751966&jsonp=jsonp&ps=10&rid=155&_=1519802001516";
        //广告
        apiUrl[10] = "https://api.bilibili.com/x/web-interface/dynamic/region?callback=jQuery172042186704389298035_1519800751975&jsonp=jsonp&ps=10&rid=165&_=1519802034151";
        //娱乐
        apiUrl[11] = "https://api.bilibili.com/x/web-interface/dynamic/region?callback=jQuery172042186704389298035_1519800751982&jsonp=jsonp&ps=10&rid=5&_=1519802086594";
        //影视
        apiUrl[12] = "https://api.bilibili.com/x/web-interface/dynamic/region?callback=jQuery172042186704389298035_1519800751983&jsonp=jsonp&ps=10&rid=23&_=1519802120425";
        //放映室
        apiUrl[13] = "https://api.bilibili.com/x/web-interface/dynamic/region?callback=jQuery172042186704389298035_1519800751990&jsonp=jsonp&ps=10&rid=177&_=1519802169562";
        for (int z = 0; z < 14; z++) {
            try {
                String testResult = sendGet(apiUrl[z]);
                String result = null;
                Pattern pattern = Pattern.compile("\"archives\":(.*?)}}");
                Matcher matcher = pattern.matcher(testResult);
                while (matcher.find()) {
                    result = matcher.group(1);
                }
                JSONArray jsonArray = JSONArray.fromObject(result);
                for (int i = 0; i < jsonArray.size(); i++) {
                    Pattern p = Pattern.compile("\"aid\":(.*?),\"videos\":(.*?),\"tid\":(.*?),\"tname\":(.*?),\"copyright\":(.*?),\"pic\":\"(.*?)\",\"title\":\"(.*?)\",\"pubdate\":(.*?),\"ctime\":(.*?),\"desc\":\"(.*?)\",");
                    Matcher m = p.matcher(jsonArray.get(i).toString());
                    Matcher m2 = Pattern.compile("\"view\":(.*?),\"danmaku\":(.*?),\"reply\":(.*?),\"favorite\":(.*?),\"coin\":(.*?),").matcher(jsonArray.get(i).toString());
                    String name = null;
                    String avId = null;
                    String photoUrl = null;
                    String time = null;
                    String desc = null;
                    int coin = 0;
                    int view = 0;
                    while (m.find()) {
                        name = m.group(7);
                        avId = m.group(1);
                        photoUrl = m.group(6);
                        time = m.group(9);
                        desc = m.group(10);
                    }
                    while (m2.find()) {
                        coin = Integer.parseInt(m2.group(5));
                        view = Integer.parseInt(m2.group(1));
                    }
                    System.out.println("名字:" + name + " av号:" + avId + " 封面地址:" + photoUrl + " 上传时间:" + time + " 硬币:" + coin + " 浏览:" + view);
//                 sout
                    System.out.println(z);
                    VideoDao.addVideoBiliBili(name, partition[z], avId, desc, time, view, coin, photoUrl);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
