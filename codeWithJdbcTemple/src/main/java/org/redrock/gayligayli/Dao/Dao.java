package org.redrock.gayligayli.Dao;

import org.springframework.jdbc.core.JdbcTemplate;

public class Dao {
    protected static JdbcTemplate jdbcTemplate = null;

    public static void setJdbcTemplate(JdbcTemplate template) {
        jdbcTemplate = template;
    }

    public static JdbcTemplate getJdbcTemplate(){
        return jdbcTemplate;
    }
}
