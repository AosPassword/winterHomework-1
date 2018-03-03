package org.redrock.gayligayli.Dao;

/**
 * 和user有关的数据库操作
 *
 * @author MashiroC
 * @time 2018.2.11 14:13
 */

import org.redrock.gayligayli.util.SecretUtil;

import java.io.ObjectInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import static org.redrock.gayligayli.util.FinalStringUtil.*;

public class UserDao extends Dao {

    /**
     * 初始头像的位置
     */
    private static final String initPhotoUrl = "http://p3qhkqnrm.bkt.clouddn.com/image/png/initAvatar.png";


    /**
     * 获得某个用户的信息
     *
     * @param username     用户名
     * @param usernameType 用户名的类型
     * @return 用户的信息Map
     */
    public static Map<String, String> getUserInfo(String usernameType, String username) {

        String sql = "SELECT nickname,big_vip,coin,b_coin,experience,level FROM user WHERE " + usernameType + " = ? LIMIT 1";
        Map<String, Object> map = jdbcTemplate.queryForMap(sql, username);
        Map<String, String> info = new HashMap<>();
        info.put(NICKNAME, (String) map.get(NICKNAME));
        info.put(BIG_VIP, (String) map.get(BIG_VIP_DATA));
        info.put(COIN, (String) map.get(COIN));
        info.put(B_COIN, (String) map.get(B_COIN_DATA));
        info.put(EXPERIENCE, (String) map.get(EXPERIENCE));
        info.put(LEVEL, (String) map.get(LEVEL));

        return info;

    }

    /**
     * 获得某个用户的id
     *
     * @param username     用户名
     * @param usernameType 用户名的类型
     * @return 用户的id
     */
    public static int getUserid(String usernameType, String username) {

        String sql = "SELECT id FROM user WHERE " + usernameType + " = ?";
        Integer userId = jdbcTemplate.queryForObject(sql,new Object[]{username},Integer.class);
        if(userId!=null){
            return userId;
        } else {
            return -1;
        }
//        System.out.println(sql);
//        Connection connection = null;
//        PreparedStatement preparedStatement = null;
//        ResultSet resultSet = null;
//        System.out.println(usernameType + "  " + username);
//        try {
//            connection = JdbcUtil.getConnection();
//            preparedStatement = connection.prepareStatement(sql);
//            preparedStatement.setString(1, username);
//            resultSet = preparedStatement.executeQuery();
//
//            if (resultSet.next()) {
//                id = resultSet.getInt(ID);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//            System.out.println("getUserId抛错误！");
//        } finally {
//            JdbcUtil.close(connection, preparedStatement, resultSet);
//        }
    }

    /**
     * 获得用户的昵称
     *
     * @param id 用户id
     * @return 用户的昵称
     */
    public static String getUserNickname(int id) {

        String nickname;
        String sql = "SELECT nickname FROM user WHERE id = ?";
        nickname = jdbcTemplate.queryForObject(sql,new Object[]{id},String.class);
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
//                nickname = resultSet.getString(NICKNAME);
//            }
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//            System.out.println("getUserNickname抛错误！");
//        } finally {
//            JdbcUtil.close(connection, preparedStatement, resultSet);
//        }

        return nickname;

    }

    /**
     * 获得用户的密码
     *
     * @param username     用户名
     * @param usernameType 用户名的类型
     * @return 用户的已加密的密码
     */
    public static String getUserPass(String username, String usernameType) {

        String password;
        String sql = "SELECT password FROM user WHERE " + usernameType + " = ?";
        password=jdbcTemplate.queryForObject(sql,new Object[]{username},String.class);

//        Connection connection = null;
//        PreparedStatement preparedStatement = null;
//        ResultSet resultSet = null;
//
//        try {
//
//            connection = JdbcUtil.getConnection();
//            preparedStatement = connection.prepareStatement(sql);
//            preparedStatement.setString(1, username);
//            resultSet = preparedStatement.executeQuery();
//
//            if (resultSet.next()) {
//                password = resultSet.getString(PASSWORD);
//            }
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//            System.out.println("getUserPass抛错误！");
//        } finally {
//            JdbcUtil.close(connection, preparedStatement, resultSet);
//        }

        return password;

    }

    /**
     * 新用户的注册
     *
     * @param nickname     用户昵称
     * @param password     密码
     * @param username     用户名
     * @param usernameType 用户名类型（邮箱 手机号）
     */
    public static void insertNewUser(String nickname, String password, String username, String usernameType) {

        String sql = "INSERT INTO user(nickname," + usernameType + ",password,photo_url,big_vip,coin,b_coin,experience,level,description) VALUES(?,?,?,?,?,?,?,?,?,?)";
//        Connection connection = null;
//        PreparedStatement preparedStatement = null;

//        try {

        jdbcTemplate.update(sql,password,initPhotoUrl,0,N_STR,0,0,0,1,INIT_DESCRIPTION);

//            connection = JdbcUtil.getConnection();
//            preparedStatement = connection.prepareStatement(sql);
//            preparedStatement.setString(1, nickname);
//            preparedStatement.setString(2, username);
//            preparedStatement.setString(3, SecretUtil.encoderHs256(password));
//            preparedStatement.setString(4, initPhotoUrl);
//            preparedStatement.setString(5, N_STR);
//            preparedStatement.setInt(6, 0);
//            preparedStatement.setInt(7, 0);
//            preparedStatement.setInt(8, 0);
//            preparedStatement.setInt(9, 1);
//            preparedStatement.setString(10, INIT_DESCRIPTION);
//            preparedStatement.execute();
//        } catch (SQLException e) {
//            e.printStackTrace();
//            System.out.println("insertNewUser抛错误！");
//        } finally {
//            JdbcUtil.close(connection, preparedStatement);
//        }

    }

    /**
     * 视频上传成功回调时调用的函数
     *
     * @param avId 视频的av号
     */
    public static void uploadSuccess(int avId) {

        String sql = "UPDATE user SET success = 'y' WHERE av_id = ?";
        jdbcTemplate.update(sql,avId);
//        Connection connection = null;
//        PreparedStatement preparedStatement = null;
//
//        try {
//
//            connection = JdbcUtil.getConnection();
//            preparedStatement = connection.prepareStatement(sql);
//            preparedStatement.setString(1, String.valueOf(avId));
//            preparedStatement.execute();
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//            System.out.println("uploadSuccess抛错误！");
//        } finally {
//            JdbcUtil.close(connection, preparedStatement);
//        }

    }

    public static int getCoin(int userId) {

        String sql = "SELECT coin FROM user WHERE id = ?";
        Integer coin = jdbcTemplate.queryForObject(sql,new Object[]{userId},Integer.class);
        if(coin!=null){
            return coin;
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
//            preparedStatement.setInt(1, userId);
//            resultSet = preparedStatement.executeQuery();
//            if (resultSet.next()) {
//                coin = resultSet.getInt(COIN);
//            }
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//            System.out.println("getCoin报错误");
//        } finally {
//            JdbcUtil.close(connection, preparedStatement);
//        }


    }

    public static void reduceCoin(int sendCoin, int userId) {

        String sql = "UPDATE user SET coin = coin - ? WHERE id = ?";
        jdbcTemplate.update(sql,sendCoin,userId);
//        Connection connection = null;
//        PreparedStatement preparedStatement = null;
//
//        try {
//
//            connection = JdbcUtil.getConnection();
//            preparedStatement = connection.prepareStatement(sql);
//            preparedStatement.setInt(1, sendCoin);
//            preparedStatement.setInt(2, userId);
//            preparedStatement.execute();
//
//        } catch (SQLException e) {
//            System.out.println("reduceCoin抛错误！");
//            e.printStackTrace();
//        } finally {
//            JdbcUtil.close(connection, preparedStatement);
//        }

    }

    public static void addCollection(int userId, int videoId) {
        String sql = "INSERT INTO collection(user_id,video_id) VALUES(?,?)";
        jdbcTemplate.update(sql,userId,videoId);
//        Connection connection = null;
//        PreparedStatement preparedStatement = null;
//        try {
//            connection = JdbcUtil.getConnection();
//            preparedStatement = connection.prepareStatement(sql);
//            preparedStatement.setInt(1, userId);
//            preparedStatement.setInt(2, videoId);
//            preparedStatement.execute();
//        } catch (SQLException e) {
//            System.out.println("addCollection抛错误！");
//            e.printStackTrace();
//        } finally {
//            JdbcUtil.close(connection, preparedStatement);
//        }
    }

    public static Map<String, Object> getAuthor(int videoId) {
        String sql = "SELECT id,nickname,description,level,photo_url from user where id = (SELECT author_id FROM video where id = ?)";
        return jdbcTemplate.queryForMap(sql,videoId);
    }
}
