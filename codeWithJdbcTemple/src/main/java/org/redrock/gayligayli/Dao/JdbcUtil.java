package org.redrock.gayligayli.Dao;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JdbcUtil {
    private static JdbcTemplate jdbcTemplate = null;

    public static void initJdbcTemplate(JdbcTemplate template){
        jdbcTemplate=template;
    }

    public static JdbcTemplate getJdbcTemplate(){
        return jdbcTemplate;
    }

}
