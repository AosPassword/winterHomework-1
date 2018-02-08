package org.redrock.gayligayli.Dao;


import javax.xml.transform.Result;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class UserDao {
    private static final String initPhotoUrl = "http://p3qhkqnrm.bkt.clouddn.com/image/png/initAvatar.png";

    public static Map<String, String> getUserInfo(String username, String usernameType) {
        Map<String, String> info = null;
        String sql = "SELECT nickname,bigVip,coin,Bcoin,experience,level FROM user WHERE " + usernameType + " = ?";
        try {
            Connection connection = JdbcUtil.getConnection();
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, username);
            ResultSet resultSet = pstmt.executeQuery();
            if (resultSet.next()) {
                info = new HashMap<>();
                info.put("nickname", resultSet.getString("nickname"));
                info.put("bigVip", resultSet.getString("bigVip"));
                info.put("coin", resultSet.getString("coin"));
                info.put("Bcoin", resultSet.getString("Bcoin"));
                info.put("experience", resultSet.getString("experience"));
                info.put("level", resultSet.getString("level"));
            }
            resultSet.close();
            pstmt.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return info;

    }

    public static int getUserid(String username, String flag) {
        int id = -1;
        String sql = "SELECT id FROM user WHERE nickname WHERE " + flag + " = ?";
        try {
            Connection connection = JdbcUtil.getConnection();
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, username);
            ResultSet resultSet = pstmt.executeQuery();
            if (resultSet.next()) {
                id = resultSet.getInt("id");
            }
            resultSet.close();
            pstmt.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    public static String getUserNickname(int id) {
        String nickname = null;
        String sql="SELECT nickname FROM user WHERE id = ?";
        try {
            Connection connection = JdbcUtil.getConnection();
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1,id);
            ResultSet resultSet = pstmt.executeQuery();
            if(resultSet.next()){
                nickname=resultSet.getString("nickname");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return nickname;
    }

    public static String getUserPass(String username, String flag) {
        String password = "";
        String sql = "SELECT password FROM user WHERE " + flag + " = ?";
        try {
            Connection connection = JdbcUtil.getConnection();
            PreparedStatement pstmt = connection.prepareStatement(sql);
            ResultSet resultSet = pstmt.executeQuery();
            if (resultSet.next()) {
                password = resultSet.getString("password");
            }
            resultSet.close();
            pstmt.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return password;
    }

    public static void insertNewUser(String nickname, String password, String username, String flag) {
        String sql = "INSERT INTO user(nickname,?,password,photoUrl,bigVip,coin,Bcoin,experience,level) VALUE(?,?,?,?,?,?,?,?,?)";
        try {
            Connection connection = JdbcUtil.getConnection();
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, flag);
            pstmt.setString(2, username);
            pstmt.setString(3, username);
            pstmt.setString(4, password);
            pstmt.setString(5, initPhotoUrl);
            pstmt.setString(6, "N");
            pstmt.setInt(7, 0);
            pstmt.setInt(8, 0);
            pstmt.setInt(9, 0);
            pstmt.executeQuery();
            pstmt.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /*playloadMap.put("sub", UserDao.);
        playloadMap.put("coin", String.valueOf(coin));
        playloadMap.put("experience", String.valueOf(experience));
        playloadMap.put("levle", String.valueOf(level));
        playloadMap.put("bCoin", String.valueOf(coin));*/
}
