package org.redrock.gayligayli.Dao;

/**
 * 和user有关的数据库操作
 *
 * @author MashiroC
 * @time 2018.2.11 14:13
 */

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import static org.redrock.gayligayli.util.FinalStringUtil.*;

public class UserDao {

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
    public static Map<String, String> getUserInfo(String username, String usernameType) {

        Map<String, String> info = null;
        String sql = "SELECT nickname,bigVip,coin,Bcoin,experience,level FROM user WHERE " + usernameType + " = ?";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {

            connection = JdbcUtil.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                info = new HashMap<>();
                info.put(NICKNAME, resultSet.getString(NICKNAME));
                info.put(BIG_VIP, resultSet.getString(BIG_VIP));
                info.put(COIN, resultSet.getString(COIN));
                info.put(B_COIN, resultSet.getString(B_COIN));
                info.put(EXPERIENCE, resultSet.getString(EXPERIENCE));
                info.put(LEVEL, resultSet.getString(LEVEL));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("getUserInfo抛错误！");
        } finally {
            JdbcUtil.close(connection, preparedStatement, resultSet);
        }

        return info;

    }

    /**
     * 获得某个用户的id
     *
     * @param username     用户名
     * @param usernameType 用户名的类型
     * @return 用户的id
     */
    public static int getUserid(String username, String usernameType) {

        int id = -1;
        String sql = "SELECT id FROM user WHERE ? = ?";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {

            connection = JdbcUtil.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, usernameType);
            preparedStatement.setString(2,username);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                id = resultSet.getInt(ID);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("getUserId抛错误！");
        } finally {
            JdbcUtil.close(connection, preparedStatement, resultSet);
        }

        return id;

    }

    /**
     * 获得用户的昵称
     *
     * @param id 用户id
     * @return 用户的昵称
     */
    public static String getUserNickname(int id) {

        String nickname = null;
        String sql = "SELECT nickname FROM user WHERE id = ?";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {

            connection = JdbcUtil.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                nickname = resultSet.getString(NICKNAME);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("getUserNickname抛错误！");
        } finally {
            JdbcUtil.close(connection,preparedStatement,resultSet);
        }

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

        String password = null;
        String sql = "SELECT password FROM user WHERE " + usernameType + " = ?";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {

            connection = JdbcUtil.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                password = resultSet.getString(PASSWORD);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("getUserPass抛错误！");
        } finally {
            JdbcUtil.close(connection,preparedStatement,resultSet);
        }

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

        String sql = "INSERT INTO user(nickname,?,password,photoUrl,bigVip,coin,Bcoin,experience,level) VALUE(?,?,?,?,?,?,?,?,?)";
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {

            connection = JdbcUtil.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, usernameType);
            preparedStatement.setString(2, nickname);
            preparedStatement.setString(3, username);
            preparedStatement.setString(4, password);
            preparedStatement.setString(5, initPhotoUrl);
            preparedStatement.setString(6, N_STR);
            preparedStatement.setInt(7, 0);
            preparedStatement.setInt(8, 0);
            preparedStatement.setInt(9, 0);
            preparedStatement.executeQuery();

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("insertNewUser抛错误！");
        } finally {
            JdbcUtil.close(connection,preparedStatement);
        }

    }

}
