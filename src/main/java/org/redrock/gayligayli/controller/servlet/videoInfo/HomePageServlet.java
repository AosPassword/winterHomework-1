package org.redrock.gayligayli.controller.servlet.videoInfo;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.redrock.gayligayli.Dao.VideoDao;
import org.redrock.gayligayli.util.JsonUtil;
import org.redrock.gayligayli.service.video.been.Video;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.Set;

import static org.redrock.gayligayli.util.FinalStringUtil.*;

public class HomePageServlet extends HttpServlet {
    private static final String[] partition =
            {CARTOON, ANIME, CREATED_BY_NATIVE, MUSIC, DANCE,
                    GAME, SCIENCE, LIFE, AUTOTUNE_REMIX, FASHION,
                    ADVERTISEMENT, ENTERTAINMENT, MOVIES, SCREENING_HALL};

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        request.setCharacterEncoding(UTF8);
        response.setCharacterEncoding(UTF8);

        JSONObject jsonObject = new JSONObject();

        //类型数量
        JSONObject typeNumJson = JSONObject.fromObject(VideoDao.getTypesNumMap());
        jsonObject.put(TYPE_NUM, typeNumJson);

        //轮播那一条
        JSONArray carouselJson = new JSONArray();
        JSONArray topInfoJson = new JSONArray();
        Set<Video> carouselList = VideoDao.getCarouselVideoSet((long) Math.ceil(new Date().getTime() / 1000));
        {
            int i = 0;
            for (Video video : carouselList) {
                if (i >= 5) {
                    carouselJson.element(video.toCaroselString());
                } else {
                    topInfoJson.element(video.toTopString());
                }
                i++;
            }
            jsonObject.put(CAROUSEL, carouselJson);
            jsonObject.put(TOP_INFO, topInfoJson);
        }

        //推广那一条
        JSONArray spreadJson = new JSONArray();
        Set<Video> spreadList = VideoDao.getSpreadVideoSet();
        for (Video video : spreadList) {
            spreadJson.element(video.toSpreadStirng());
        }
        jsonObject.put(SPREAD, spreadJson);

        //直播
        JSONArray liveVideoJson = new JSONArray();
        Set<Video> liveVideoList = VideoDao.getLiveVideoListSet();
        for (Video video : liveVideoList) {
            liveVideoJson.element(video.toLiveString());
        }
        jsonObject.put(LIVE, liveVideoJson);

        //各个分区
        for (String str : partition) {
            JSONObject partitionJson = new JSONObject();

            JSONArray partitionInfoJson = new JSONArray();
            Set<Video> partitionInfoSet = VideoDao.getPartitionInfoSet(str);
            for (Video video : partitionInfoSet) {
                partitionInfoJson.element(video.toBriefString());
            }
            partitionJson.element(INFO, partitionInfoJson);

            JSONArray partitionRankJson = new JSONArray();
            Set<Video> partitionRankSet = VideoDao.getPartitionRankSet(str, (long) Math.ceil(new Date().getTime() / 1000));
            {
                int i = 0;
                for (Video video : partitionRankSet) {
                    JSONObject json = new JSONObject();
                    json.element(RANK, ++i);
                    json.element(DATA, video.toRankString());
                    partitionRankJson.element(json.toString());
                }
            }
            partitionJson.element(RANK, partitionRankJson);

            jsonObject.put(str, partitionJson);
        }

        JsonUtil.writeResponse(response,jsonObject.toString());
    }
}
