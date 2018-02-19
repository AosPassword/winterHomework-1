package org.redrock.gayligayli.Dao;

import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JdbcUtil {
    //TODO:数据库连接
    private static BasicDataSource basicDataSource;

    public static BasicDataSource getBasicDataSource(){
        return basicDataSource;
    }

    public static void initDatabase(BasicDataSource dataSource){
        basicDataSource=dataSource;
    }

    public static Connection getConnection() throws SQLException {
       return basicDataSource.getConnection();
    }

    public static void close(Connection connection, PreparedStatement pstmt){
        try {
            if(pstmt!=null){
                pstmt.close();
                if(connection!=null){
                    connection.close();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("关闭连接出了问题！");
        }
    }

    public static void close(Connection connection, PreparedStatement pstmt, ResultSet resultSet){
        try {
            if(resultSet!=null){
                resultSet.close();
                if(pstmt!=null){
                    pstmt.close();
                    if(connection!=null){
                        connection.close();
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("关闭连接出了问题！");
        }
    }

}
