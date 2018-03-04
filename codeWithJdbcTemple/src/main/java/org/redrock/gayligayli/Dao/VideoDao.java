package org.redrock.gayligayli.Dao;

/**
 * 和video有关的数据库操作类
 *
 * @author MashiroC
 * @time 2018.2.11 14-28
 */

import org.omg.CORBA.INTERNAL;
import org.omg.CORBA.OBJECT_NOT_EXIST;
import org.omg.CORBA.ObjectHelper;
import org.redrock.gayligayli.service.videoInfo.been.Barrage;
import org.redrock.gayligayli.service.videoInfo.been.Comment;
import org.redrock.gayligayli.service.videoInfo.been.Video;
import org.redrock.gayligayli.service.videoInfo.util.VideoInfoUtil;

import java.sql.*;
import java.util.*;

import static org.redrock.gayligayli.util.FinalStringUtil.*;

public class VideoDao extends Dao {


    //装样子的直播

    /**
     * 直播的名字
     */
    private static final String[] LIVE_NAME = {"里脊姐姐教大家敲代码啦！", "在线膜cmjj", "cc学姐dota课堂开课啦", "为何yrx这么强啊",
            "卓玛扎西带大家一起吃胖！", "近距离直播肖畅dalao写前端！", "国家队我tm吹爆", "真正的补考现场",
            "我现在只想看Ditf第8集", "没有国家队看我要死了"};
    /**
     * 直播的播主名字
     */
    private static final String[] LIVE_AUTHOR_NAME = {"里里里里脊", "菜鸡如我", "我！cc！举盾！", "oyrx",
            "小扎西", "肖畅dalao", "我永远喜欢02", "只有我",
            "莓人性！", "莓真是太可爱啦"};
    /**
     * 直播现在的人数
     */
    private static final int[] LIVE_PEOPLE_NUMBER = {999999, 999999, 999999, 999999,
            999999, 999999, 999999, 999999,
            999999, 999999,};
    /**
     * 直播的类型
     */
    private static final String[] LIVE_TYPE = {"代码·Android", "tql·ocmjj", "比赛·TI8", "tql·oyrx", "" +
            "美食·堕落街", "代码·前端", "霸权·国家队", "限定·补考",
            "霸权·国家队", "霸权·国家队"};
    /**
     * 直播的封面图片
     */
    private static final String[] LIVE_PHOTO_URL_ONE = {"url1", "url2", "url3", "url4", "url5", "url6", "url7", "url8", "url9", "url10", "url11", "url12"};
    /**
     * 鼠标移上去的图片
     */
    private static final String[] LIVE_PHOTO_URL_TWO = {"url1", "url2", "url3", "url4", "url5", "url6", "url7", "url8", "url9", "url10", "url11", "url12"};

    /**
     * 得到某视频弹幕的数量
     *
     * @param id 视频id
     * @return 弹幕的数量
     */
    public static int getBarrageNum(int id) {

        String sql = "SELECT count(video_id) AS temp FROM barrage WHERE video_id = ?";
        Integer barrageNum = jdbcTemplate.queryForObject(sql, new Object[]{id}, Integer.class);
        if (barrageNum != null) {
            return barrageNum;
        } else {
            return 0;
        }
//        Connection connection = null;
//        PreparedStatement preparedStatement = null;
//        ResultSet resultSet = null;
//
//        try {
//
//            connection = JdbcUtil.getConnection();
//            preparedStatement = connection.prepareStatement(sql);
//            preparedStatement.setInt(1, id);
//            resultSet = preparedStatement.executeQuery();
//
//            if (resultSet.next()) {
//                count = resultSet.getInt(TEMP);
//            }
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//            System.out.println("getBarrageNum抛错误！");
//        } finally {
//            JdbcUtil.close(connection, preparedStatement, resultSet);
//        }

    }


    private static Video getRandomVideo(String sql) {

        Video video = null;
        Map<String, Object> map = jdbcTemplate.queryForMap(sql);
        video = new Video(map);
//        Connection connection = null;
//        PreparedStatement preparedStatement = null;
//        ResultSet resultSet = null;
//
//        try {
//
//            connection = JdbcUtil.getConnection();
//            preparedStatement = connection.prepareStatement(sql);
//            resultSet = preparedStatement.executeQuery();
//
//            if (resultSet.next()) {
//                video = new Video(resultSet);
//            }
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//            System.out.println("getRandomVideo抛错误");
//        } finally {
//            JdbcUtil.close(connection, preparedStatement, resultSet);
//        }

        return video;

    }

    /**
     * 得到任意类型的随机视频
     *
     * @return 视频
     */
    public static Video getAllTypeRandomVideo() {
        String sql = "SELECT * FROM video WHERE success = 'y' ORDER BY rand() LIMIT 1";
        return getRandomVideo(sql);
    }

    /**
     * 得到某一类型的视频
     *
     * @param type 类型
     * @return 视频
     */
    public static Video getOneTypeRandomVideo(String type) {
        String sql = "SELECT * FROM video WHERE type = '" + type + "' AND success = 'y' ORDER BY rand() LIMIT 1";
        return getRandomVideo(sql);
    }

    /**
     * 得到所有类型下视频的数量
     *
     * @return key为类型，value为视频数量的Map
     */
    public static Map<String, Integer> getTypesNumMap() {

        String sql = "SELECT type,count(type) AS temp FROM video WHERE success = 'y' GROUP BY type ORDER BY type DESC";

        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
        Map<String, Integer> info = new HashMap<>();

        for (Map<String, Object> aList : list) {
            info.put((String) aList.get(TYPE),((Long)aList.get(TEMP)).intValue());
        }

        return info;

    }

    /**
     * 获得轮播视频的信息
     *
     * @param nowTime 现在的时间
     * @return 轮播视频信息的set
     */
    public static Set<Video> getCarouselVideoSet(long nowTime) {

        String sql = "SELECT video.*,user.*,SUM(video.views+video.coin*70) AS temp " +
                "FROM video,user WHERE time > ? AND video.author_id=user.id AND success = 'y' " +
                "GROUP BY video.id ORDER BY temp DESC LIMIT 13";
        Set<Video> carouselInfo = new TreeSet<>();
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql,nowTime);
            for (Map<String, Object> aList : list) {
                carouselInfo.add(new Video(aList));
            }

            while (carouselInfo.size() < 13) {
                carouselInfo.add(getAllTypeRandomVideo());
            }
        return carouselInfo;

    }

    /**
     * 拿推广视频信息
     *
     * @return 推广视频信息的set
     */
    public static Set<Video> getSpreadVideoSet() {
        Set<Video> spreadVideo = new HashSet<>();
        while (spreadVideo.size() < 5) {
            spreadVideo.add(getAllTypeRandomVideo());
        }
        return spreadVideo;
    }

    public static Set<Video> getLiveVideoListSet() {
        Set<Video> liveSet = new HashSet<>();
        for (int i = 0; i < 10; i++) {
            Video video = new Video();
            video.setName(LIVE_NAME[i]);
            video.setDescription(LIVE_AUTHOR_NAME[i]);
            video.setViews(LIVE_PEOPLE_NUMBER[i]);
            video.setType(LIVE_TYPE[i]);
            video.setPhotoUrl(LIVE_PHOTO_URL_ONE[i]);
            video.setVideoUrl(LIVE_PHOTO_URL_TWO[i]);
            liveSet.add(video);
        }
        return liveSet;
    }

    public static Set<Video> getPartitionInfoSet(String type) {
        Set<Video> partitionSet = new HashSet<>();
        while (partitionSet.size() < 10) {
            partitionSet.add(getOneTypeRandomVideo(type));
        }
        return partitionSet;
    }


    public static Set<Video> getPartitionRankSet(String type, long nowTime) {
        Set<Video> partitionSet = new TreeSet<>();
        String sql = "SELECT video.*,user.*,SUM(video.views+video.coin*70) AS temp " +
                "FROM video,user WHERE time > ? AND type = ? AND video.author_id=user.id AND success = 'y' " +
                "GROUP BY video.id ORDER BY temp DESC LIMIT 7";
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql,nowTime,type);
            for (Map<String, Object> aList : list) {
                partitionSet.add(new Video(aList));
            }
            while (partitionSet.size() < 13) {
                partitionSet.add(getAllTypeRandomVideo());
            }
        return partitionSet;
    }

    public static Video getVideoInfo(int id) {
        String sql = "SELECT video.* FROM video WHERE id = ? AND success = 'y'";
        Map<String, Object> map = jdbcTemplate.queryForMap(sql,id);

        return new Video(map);
    }

    public static ArrayList<Barrage> getBarrageList(int id) {
        String sql = "SELECT * FROM barrage WHERE video_id = ? ORDER BY appear_time";
        ArrayList<Barrage> barragesList = new ArrayList<>();
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql,id);
        for (Map<String, Object> aList : list) {
            barragesList.add(new Barrage(aList));
        }
        return barragesList;
    }

    public static ArrayList<Comment> getCommentList(int id) {
        String sql = "SELECT * FROM comment WHERE video_id = ? AND pid=0 GROUP BY id ORDER BY num";
        ArrayList<Comment> commentsList = new ArrayList<>();
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, id);
        for (Map<String, Object> aList : list) {
            commentsList.add(new Comment(aList));
        }
        for (Comment comment : commentsList) {
            ArrayList<Comment> childCommentList = comment.getChildComment();
            ArrayList<Comment> newCommentList = getDfsCommentList(comment.getId());
            childCommentList.addAll(newCommentList);
        }
        return commentsList;
    }

    private static ArrayList<Comment> getDfsCommentList(int pid) {
        String sql = "SELECT * FROM comment WHERE pid = ? GROUP BY id ORDER BY time";
        ArrayList<Comment> commentList = new ArrayList<>();
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, pid);
        for (Map<String, Object> aList : list) {
            commentList.add(new Comment(aList));
        }
        for (Comment comment : commentList) {
            ArrayList<Comment> childCommentList = comment.getChildComment();
            ArrayList<Comment> newCommentList = getDfsCommentList(comment.getId());
            childCommentList.addAll(newCommentList);
        }
        return commentList;
    }

    public static void addView(int id) {
        String sql = "UPDATE video SET views=views+1 WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    public static int getCollectionNum(int id) {
        String sql = "SELECT count(id) AS temp FROM video WHERE id = ?";
        Integer collectionNum = jdbcTemplate.queryForObject(sql, new Object[]{id}, Integer.class);
        if (collectionNum != null) {
            return collectionNum;
        } else {
            return -1;
        }
//        Connection connection = null;
//        PreparedStatement preparedStatement = null;
//        ResultSet resultSet = null;
//        try {
//            connection = JdbcUtil.getConnection();
//            preparedStatement = connection.prepareStatement(sql);
//            preparedStatement.setInt(1, id);
//            resultSet = preparedStatement.executeQuery();
//            if (resultSet.next()) {
//                collectionNum = resultSet.getInt(TEMP);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } finally {
//            JdbcUtil.close(connection, preparedStatement, resultSet);
//        }
    }

    public static void addVideoBiliBili(String name, String type, String avId, String desc, String time, int views, int coin, String photoUrl) {
        String sql = "INSERT INTO video(name,author_id,type,av_id,description,time,length,views,coin,photo_url,video_url,child_type,success) " +
                "VALUE(?,?,?,?,?,?,?,?,?,?,?,?,?)";
        if (!VideoInfoUtil.isVideoExist(avId))
            jdbcTemplate.update(sql, name, 1, type, avId, desc, time, 60, views, coin, photoUrl, "test", 0, "y");
    }


    public static int addVideo(String name, int authorId, String type, String description, String time, String length) {
        int avId = VideoInfoUtil.getAvid();
        String names[] = name.split("\\.");
        if (names.length == 2) {
            String photoUrl = VideoInfoUtil.getPhotoUrl(avId, names[1]);
            String videoUrl = VideoInfoUtil.getVideoUrl(avId, names[1]);

            String sql = "INSERT INTO video(name,author_id,type,av_id,description,time,length,views,coin,photo_url,video_url,child_type,success) " +
                    "VALUE(?,?,?,?,?,?,?,?,?,?,?,?,?)";
            jdbcTemplate.update(sql, name, authorId, type, avId, description, time, length, 1, 0, photoUrl, videoUrl, 0, N_STR);
//            Connection connection = null;
//            PreparedStatement preparedStatement = null;
//            try {
//                connection = JdbcUtil.getConnection();
//                preparedStatement = connection.prepareStatement(sql);
//                preparedStatement.setString(1, name);
//                preparedStatement.setInt(2, authorId);
//                preparedStatement.setString(3, type);
//                preparedStatement.setInt(4, avId);
//                preparedStatement.setString(5, description);
//                preparedStatement.setString(6, time);
//                preparedStatement.setString(7, length);
//                preparedStatement.setInt(8, 1);
//                preparedStatement.setInt(9, 0);
//                preparedStatement.setString(10, photoUrl);
//                preparedStatement.setString(11, videoUrl);
//                preparedStatement.setString(12, "IDK");
//                preparedStatement.setString(13, "n");
//                preparedStatement.execute();
//            } catch (SQLException e) {
//                e.printStackTrace();
//                System.out.println("添加视频抛错！");
//            } finally {
//                JdbcUtil.close(connection, preparedStatement);
//            }
            return avId;
        }
        return -1;
    }

    public static int getVideoId(String usernameType, String username) {
        int id;

        String sql = "SELECT id FROM video WHERE " + usernameType + " = ?";
        List<Map<String,Object>> list = jdbcTemplate.queryForList(sql, username);
        if (list.size()!=0) {
            return (int)list.get(0).get(ID);
        } else {
            return -1;
        }
//        Connection connection = null;
//        PreparedStatement preparedStatement = null;
//        ResultSet resultSet = null;
//        try {
//            connection = JdbcUtil.getConnection();
//            preparedStatement = connection.prepareStatement(sql);
//            preparedStatement.setString(1, username);
//            resultSet = preparedStatement.executeQuery();
//            if (resultSet.next()) {
//                id = resultSet.getInt(ID);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//            System.out.println("getVideoId抛错！");
//        } finally {
//            JdbcUtil.close(connection, preparedStatement);
//        }
    }

    public static void addCoin(int sendCoin, int id) {
        String sql = "UPDATE video SET coin = coin + ? WHERE id = ?";
        jdbcTemplate.update(sql, sendCoin, id);
//        Connection connection = null;
//        PreparedStatement preparedStatement = null;
//        try {
//            connection = JdbcUtil.getConnection();
//            preparedStatement = connection.prepareStatement(sql);
//            preparedStatement.setInt(1, sendCoin);
//            preparedStatement.setInt(2, id);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } finally {
//            JdbcUtil.close(connection, preparedStatement);
//        }
    }

    public static void addBarrage(int videoId, int authorId, String content, String appearTime, String sendTime, String color, int fontsize, int position) {
        String sql = "INSERT barrage(video_id,author_id,content,appear_time,send_time,color,fontsize,position) VALUE(?,?,?,?,?,?,?,?)";
        jdbcTemplate.update(sql, videoId, authorId, content, appearTime, sendTime, color, fontsize, position);
//        Connection connection = null;
//        PreparedStatement preparedStatement = null;
//        try {
//            connection = JdbcUtil.getConnection();
//            preparedStatement = connection.prepareStatement(sql);
//            preparedStatement.setInt(1, videoId);
//            preparedStatement.setInt(2, authorId);
//            preparedStatement.setString(3, content);
//            preparedStatement.setString(4, appearTime);
//            preparedStatement.setString(5, sendTime);
//            preparedStatement.setString(6, color);
//            preparedStatement.setInt(7, fontsize);
//            preparedStatement.setInt(8, position);
//            preparedStatement.execute();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } finally {
//            JdbcUtil.close(connection, preparedStatement);
//        }
    }

    public static boolean pidExist(int pid) {
        if (pid == 0) {
            return true;
        }
        String sql = "SELECT * FROM comment WHERE id = ?";
        List list = jdbcTemplate.queryForList(sql, pid);
        return list.size() != 0;
    }

    public static void addComment(int videoId, int pid, int authorId, String content, String time, String device) {
        int num;
        if (pid == 0) {
            num = getCommentNum(VIDEO_ID_DATA, videoId)+1;
        } else {
            num = getCommentNum(ID, pid);
        }
        String sql = "INSERT comment(video_id,pid,author_id,content,time,device,num) VALUE(?,?,?,?,?,?,?)";
        jdbcTemplate.update(sql, videoId, pid, authorId, content, time, device, num);
//        Connection connection = null;
//        PreparedStatement preparedStatement = null;
//        try {
//            connection = JdbcUtil.getConnection();
//            preparedStatement = connection.prepareStatement(sql);
//            preparedStatement.setInt(1, videoId);
//            preparedStatement.setInt(2, pid);
//            preparedStatement.setInt(3, authorId);
//            preparedStatement.setString(4, content);
//            preparedStatement.setString(5, time);
//            preparedStatement.setString(6, device);
//            preparedStatement.setInt(7, num);
//            preparedStatement.execute();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } finally {
//            JdbcUtil.close(connection, preparedStatement);
//        }
    }


    public static int getCommentNum(String flag, int temp) {
        String sql = "SELECT num FROM comment WHERE " + flag + " = ? ORDER BY num DESC LIMIT 1";
        List<Map<String,Object>> list = jdbcTemplate.queryForList(sql, temp);
        if(list.size()==0){
            return 1;
        }else {
            return (int) list.get(0).get(NUM);
        }
//        Connection connection = null;
//        PreparedStatement preparedStatement = null;
//        ResultSet resultSet = null;
//        try {
//            connection = JdbcUtil.getConnection();
//            preparedStatement = connection.prepareStatement(sql);
//            preparedStatement.setInt(1, temp);
//            resultSet = preparedStatement.executeQuery();
//            if (resultSet.next()) {
//                num = resultSet.getInt(NUM);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } finally {
//            JdbcUtil.close(connection, preparedStatement, resultSet);
//        }
    }
}
