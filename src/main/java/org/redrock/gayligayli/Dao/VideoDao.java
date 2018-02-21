package org.redrock.gayligayli.Dao;

/**
 * 和video有关的数据库操作类
 *
 * @author MashiroC
 * @time 2018.2.11 14-28
 */

import org.redrock.gayligayli.service.videoInfo.been.Barrage;
import org.redrock.gayligayli.service.videoInfo.been.Comment;
import org.redrock.gayligayli.service.videoInfo.been.Video;
import org.redrock.gayligayli.service.videoInfo.util.VideoInfoUtil;

import java.sql.*;
import java.util.*;

import static org.redrock.gayligayli.util.FinalStringUtil.*;

public class VideoDao {

    /** 装样子的直播【2333333 */

    /**
     * 直播的名字
     */
    private static final String[] LIVE_NAME = {};
    /**
     * 直播的播主名字
     */
    private static final String[] LIVE_AUTHOR_NAME = {};
    /**
     * 直播现在的人数
     */
    private static final String[] LIVE_PEOPLE_NUMBER = {};
    /**
     * 直播的类型
     */
    private static final String[] LIVE_TYPE = {};
    /**
     * 直播的封面图片
     */
    private static final String[] LIVE_PHOTO_URL_ONE = {};
    /**
     * 鼠标移上去的图片
     */
    private static final String[] LIVE_PHOTO_URL_TWO = {};

    /**
     * 得到某视频弹幕的数量
     *
     * @param id 视频id
     * @return 弹幕的数量
     */
    public static int getBarrageNum(int id) {

        String sql = "SELECT videoId,count(videoId) AS temp FROM barrage WHERE videoId = ?";
        int count = -1;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {

            connection = JdbcUtil.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                count = resultSet.getInt(TEMP);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("getBarrageNum抛错误！");
        } finally {
            JdbcUtil.close(connection, preparedStatement, resultSet);
        }

        return count;

    }

    /**
     * 得到一个随机的视频
     *
     * @param sql 得到视频的sql语句
     * @return 得到的视频
     */
    private static Video getRandomVideo(String sql) {

        Video video = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {

            connection = JdbcUtil.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                video = new Video(resultSet);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("getRandomVideo抛错误");
        } finally {
            JdbcUtil.close(connection, preparedStatement, resultSet);
        }

        return video;

    }

    /**
     * 得到任意类型的随机视频
     *
     * @return 视频
     */
    public static Video getAllTypeRandomVideo() {
        String sql = "SELECT * FROM videoInfo WHERE type = ? AND success = 'y' ORDER BY rand() LIMIT 1";
        return getRandomVideo(sql);
    }

    /**
     * 得到某一类型的视频
     *
     * @param type 类型
     * @return 视频
     */
    public static Video getOneTypeRandomVideo(String type) {
        String sql = "SELECT * FROM videoInfo WHERE type = ? AND success = 'y' ORDER BY rand() LIMIT 1";
        return getRandomVideo(sql);
    }

    /**
     * 得到所有类型下视频的数量
     *
     * @return key为类型，value为视频数量的Map
     */
    public static Map<String, Integer> getTypesNumMap() {

        Map<String, Integer> info = new HashMap<>();
        String sql = "SELECT type,count(type) AS temp FROM video WHERE success = 'y' GROUP BY type ORDER BY type DESC";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {

            connection = JdbcUtil.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                info.put(resultSet.getString(TYPE), resultSet.getInt(TEMP));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("getTypesNumMap抛错误！");
        } finally {
            JdbcUtil.close(connection, preparedStatement, resultSet);
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
                "FROM video,user WHERE time > ? AND video.authorId=user.id AND success = 'y' " +
                "GROUP BY video.id ORDER BY temp DESC LIMIT 13";
        Set<Video> carouselInfo = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {

            connection = JdbcUtil.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, nowTime - ONE_WEEK);//一周内的加权排名最高的
            resultSet = preparedStatement.executeQuery();
            carouselInfo = new HashSet<>();

            while (resultSet.next()) {
                Video video = new Video(resultSet);
                carouselInfo.add(video);
            }
            while (carouselInfo.size() < 13) {
                carouselInfo.add(getAllTypeRandomVideo());
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("getCarouseVideoSet抛错误！");
        } finally {
            JdbcUtil.close(connection, preparedStatement, resultSet);
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
            video.setChildType(LIVE_AUTHOR_NAME[i]);
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
                "FROM video,user WHERE time > ? AND type = ? AND video.authorId=user.id AND success = 'y' " +
                "GROUP BY video.id ORDER BY temp DESC LIMIT 7";
        try {
            Connection connection = JdbcUtil.getConnection();
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setLong(1, nowTime - ONE_WEEK);
            pstmt.setString(2, type);
            ResultSet resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
                Video video = new Video(resultSet);
                partitionSet.add(video);
            }
            while (partitionSet.size() < 7) {
                partitionSet.add(getAllTypeRandomVideo());
            }
            resultSet.close();
            pstmt.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("getPaetitionRankSet泡错");
        }
        return partitionSet;
    }

    public static Video getVideoInfo(int id) {
        String sql = "SELECT video.*,user.* FROM video,user WHERE video.id = ? AND video.authorId = user.id AND success = 'y'";
        Video video = null;
        try {
            Connection connection = JdbcUtil.getConnection();
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, id);
            ResultSet resultSet = pstmt.executeQuery();
            if (resultSet.next()) {
                video = new Video(resultSet);
            }
            resultSet.close();
            pstmt.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return video;
    }

    public static ArrayList<Barrage> getBarrageList(int id) {
        String sql = "SELECT * FROM barrage WHERE videoId = ? ORDER BY time";
        ArrayList<Barrage> barragesList = new ArrayList<>();
        try {
            Connection connection = JdbcUtil.getConnection();
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, id);
            ResultSet resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
                Barrage barrage = new Barrage(resultSet);
                barragesList.add(barrage);
            }
            resultSet.close();
            pstmt.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return barragesList;
    }

    public static ArrayList<Comment> getCommentList(int id) {
        String sql = "SELECT comment.* , user.* FROM comment,user WHERE comment.VideoId = ? AND comment.pid=0 AND comment.authorId=user.id GROUP BY comment.num ORDER BY comment.num";
        ArrayList<Comment> commentsList = new ArrayList<>();
        try {
            Connection connection = JdbcUtil.getConnection();
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, id);
            ResultSet resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
                commentsList.add(new Comment(resultSet));
            }
            resultSet.close();
            pstmt.close();
            connection.close();
            for (Comment comment : commentsList) {
                ArrayList<Comment> childCommentList = comment.getChildComment();
                ArrayList<Comment> newCommentList = getDfsCommentList(comment.getId());
                childCommentList.addAll(newCommentList);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return commentsList;
    }

    private static ArrayList<Comment> getDfsCommentList(int pid) throws SQLException {
        String sql = "SELECT comment.*,user.* FROM comment,user WHERE comment.pid = ? AND comment.authorId=user.id GROUP BY comment.id ORDER BY time";
        ArrayList<Comment> commentList = new ArrayList<>();
        Connection connection = JdbcUtil.getConnection();
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setInt(1, pid);
        ResultSet resultSet = pstmt.executeQuery();
        while (resultSet.next()) {
            commentList.add(new Comment(resultSet));
        }
        resultSet.close();
        pstmt.close();
        connection.close();
        for (Comment comment : commentList) {
            ArrayList<Comment> childCommentList = comment.getChildComment();
            ArrayList<Comment> newCommentList = getDfsCommentList(comment.getId());
            childCommentList.addAll(newCommentList);
        }
        return commentList;
    }

    public static void addView(int id) {
        String sql = "UPDATE video SET views=views+1 WHERE id = ?";
        try {
            Connection connection = JdbcUtil.getConnection();
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("增加视频浏览数出错了！");
        }
    }

    //TODO:获得收藏数
    public static int getCollectionNum(int id) {
        return -1;
    }


    public static int addVideo(String name, int authorId, String type, String description, String time, String length) {
        int avId = VideoInfoUtil.getAvid();
        String names[] = name.split(SIGNATURE_SEPARATOR);
        if (names.length == 2) {
            String photoUrl = VideoInfoUtil.getPhotoUrl(avId, names[1]);
            String videoUrl = VideoInfoUtil.getVideoUrl(avId, names[1]);

            String sql = "INSERT INTO video(name,author_id,type,av_id,description,time,length,views,coin,photo_url,video_url,child_type,success) " +
                    "VALUE(?,?,?,?,?,?,?,?,?,?,?,?,?)";
            Connection connection = null;
            PreparedStatement preparedStatement = null;
            try {
                connection = JdbcUtil.getConnection();
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, name);
                preparedStatement.setInt(2, authorId);
                preparedStatement.setString(3, type);
                preparedStatement.setInt(4, avId);
                preparedStatement.setString(5, description);
                preparedStatement.setString(6, time);
                preparedStatement.setString(7, length);
                preparedStatement.setInt(8, 1);
                preparedStatement.setInt(9, 0);
                preparedStatement.setString(10, photoUrl);
                preparedStatement.setString(11, videoUrl);
                preparedStatement.setString(12, "IDK");
                preparedStatement.setString(13, "n");
                preparedStatement.execute();
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("添加视频抛错！");
            } finally {
                JdbcUtil.close(connection, preparedStatement);
            }
            return avId;
        }
        return -1;
    }

    public static int getVideoId(String usernameType, String username) {
        int id = -1;
        String sql = "SELECT id FROM video WHERE " + usernameType + " = ?";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = JdbcUtil.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                id = resultSet.getInt(ID);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("getVideoId抛错！");
        } finally {
            JdbcUtil.close(connection, preparedStatement);
        }
        return id;
    }

    public static void addCoin(int sendCoin, int id) {
        String sql = "UPDATE video SET coin = coin + ? WHERE id = ?";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = JdbcUtil.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, sendCoin);
            preparedStatement.setInt(2, id);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtil.close(connection, preparedStatement);
        }
    }

    public static void addBarrage(int videoId, int authorId, String content, String appearTime, String sendTime, String color, int fontsize, int position) {
        String sql = "INSERT barrage(video_id,author_id,content,appear_time,send_time,color,fontsize,position) VALUE(?,?,?,?,?,?,?,?)";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = JdbcUtil.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, videoId);
            preparedStatement.setInt(2, authorId);
            preparedStatement.setString(3, content);
            preparedStatement.setString(4, appearTime);
            preparedStatement.setString(5, sendTime);
            preparedStatement.setString(6, color);
            preparedStatement.setInt(7, fontsize);
            preparedStatement.setInt(8, position);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtil.close(connection, preparedStatement);
        }
    }

    public static boolean pidExist(int pid) {
        if (pid == 0) {
            return true;
        }
        boolean flag = false;
        String sql = "SELECT * FROM comment WHERE id = ?";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = JdbcUtil.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, pid);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                flag = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtil.close(connection, preparedStatement, resultSet);
        }
        return flag;
    }

    public static void addComment(int videoId, int pid, int authorId, String content, String time, String device) {
        int num = 0;
        if (pid == 0) {
            num = getCommentNum(VIDEO_ID,videoId);
        } else {
            num = getCommentNum(ID,pid);
        }
        String sql = "INSERT comment(video_id,pid,author_id,content,time,device,num) VALUE(?,?,?,?,?,?,?)";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = JdbcUtil.getConnection();
            preparedStatement=connection.prepareStatement(sql);
            preparedStatement.setInt(1,videoId);
            preparedStatement.setInt(2,pid);
            preparedStatement.setInt(3,authorId);
            preparedStatement.setString(4,content);
            preparedStatement.setString(5,time);
            preparedStatement.setString(6,device);
            preparedStatement.setInt(7,num);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtil.close(connection,preparedStatement);
        }
    }


    public static int getCommentNum(String flag, int temp) {
        String sql = "SELECT * FROM comment WHERE " + flag + " = ? ORDER BY num DESC LIMIT 1";
        int num = -1;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = JdbcUtil.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, temp);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                num = resultSet.getInt(NUM);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtil.close(connection, preparedStatement, resultSet);
        }
        return num;
    }
}
