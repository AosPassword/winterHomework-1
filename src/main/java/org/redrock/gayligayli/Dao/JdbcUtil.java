package org.redrock.gayligayli.Dao;

import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class JdbcUtil {
    private static BasicDataSource basicDataSource;

    public static BasicDataSource getBasicDataSource(){
        return basicDataSource;
    }

    public static Connection getConnection() throws SQLException {
       return basicDataSource.getConnection();
    }
}
