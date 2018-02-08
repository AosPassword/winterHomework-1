package org.redrock.gayligayli.Dao;

import org.redrock.gayligayli.util.DaoUtil;
import org.redrock.gayligayli.video.been.Video;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class VideoDao {
    private static final String AUTHOR_ID = "authorId";
    private static final String NAME = "name";
    private static final String TYPE = "type";
    private static final String DESCRIPTION = "description";
    private static final String TIME = "time";
    private static final String LENGTH = "length";
    private static final String VIEWS = "views";
    private static final String COIN = "coin";
    private static final String COLLECTION = "collection";
    private static final String PHOTO_URL = "photoUrl";
    private static final String VIDEO_URL = "videoUrl";
    private static final String SUCCESS = "success";

    private static final String CARTOON = "cartoon";
    private static final String ANIME = "anime";
    private static final String CREATED_BY_NATIVE = "createdByNative";
    private static final String MUSIC = "music";
    private static final String DANCE = "dance";
    private static final String GAME = "game";
    private static final String SCIENCE = "science";
    private static final String LIFE = "life";
    private static final String AUTOTUNE_REMIX = "autotuneRemix";
    private static final String FASHION = "fashion";
    private static final String ADVERTISEMENT = "advertisement";
    private static final String ENTERTAINMENT = "entertainment";
    private static final String MOVIES = "movies";
    private static final String SCREENING_HALL = "screeningHall";


    public static Video getRandromVideo() {
        String sql = "SELECT * FROM video ORDER BY rand() LIMIT 1";
        Video video = null;
        try {
            Connection connection = JdbcUtil.getConnection();
            PreparedStatement pstmt = connection.prepareStatement(sql);
            ResultSet resultSet = pstmt.executeQuery();
            if (resultSet.next()) {
                video = DaoUtil.initVedio(resultSet);
            }
            resultSet.close();
            pstmt.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return video;
    }

    //select class,count(class) from students group by class order by class desc;
    public static Map<String, Integer> getTypesNum() {
        Map<String, Integer> info = null;
        String sql = "SELECT type,count(type) FROM video GROUP BY type ORDER BY type DESC";
        try {
            Connection connection = JdbcUtil.getConnection();
            PreparedStatement pstmt = connection.prepareStatement(sql);
            ResultSet resultSet = pstmt.executeQuery();
            if (resultSet.next()) {
                info = new HashMap<>();
                info.put(CARTOON, resultSet.getInt(CARTOON));
                info.put(ANIME, resultSet.getInt(ANIME));
                info.put(CREATED_BY_NATIVE, resultSet.getInt(CREATED_BY_NATIVE));
                info.put(MUSIC, resultSet.getInt(MUSIC));
                info.put(DANCE, resultSet.getInt(DANCE));
                info.put(GAME, resultSet.getInt(GAME));
                info.put(SCIENCE, resultSet.getInt(SCIENCE));
                info.put(LIFE, resultSet.getInt(LIFE));
                info.put(AUTOTUNE_REMIX, resultSet.getInt(AUTOTUNE_REMIX));
                info.put(FASHION, resultSet.getInt(FASHION));
                info.put(ADVERTISEMENT, resultSet.getInt(ADVERTISEMENT));
                info.put(ENTERTAINMENT, resultSet.getInt(ENTERTAINMENT));
                info.put(MOVIES, resultSet.getInt(MOVIES));
                info.put(SCREENING_HALL, resultSet.getInt(SCREENING_HALL));
            }
            resultSet.close();
            pstmt.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return info;
    }

    public static ArrayList<Video> getCarouselVideo(long time) {
        String sql = "SELECT video.*,user.nickname,SUM(video.views+video.coin*70) AS temp " +
                "FROM video,user WHERE time > ? AND video.authorId=user.id " +
                "GROUP BY video.id ORDER BY temp DESC LIMIT 9";
        ArrayList<Video> carouselInfo = null;
        try {
            Connection connection = JdbcUtil.getConnection();
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setLong(1, time - 604800L);//一周内的加权排名最高的
            ResultSet resultSet = pstmt.executeQuery();
            carouselInfo = new ArrayList<>();
            while (resultSet.next()) {
                Video video = DaoUtil.initVedio(resultSet);
                carouselInfo.add(video);
            }
            resultSet.close();
            pstmt.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return carouselInfo;
    }
}
