package org.redrock.gayligayli.spider;

import lombok.Data;
import org.apache.commons.dbcp2.BasicDataSource;
import org.redrock.gayligayli.Dao.Dao;
import org.springframework.jdbc.core.JdbcTemplate;

@Data
public class Database {
    private static final String DBDRIVER = "com.mysql.jdbc.Driver";
    private static final String DBURL = "jdbc:mysql://67.209.179.6:3306/gayligayli?useUnicode=true&characterEncoding=utf-8&useSSL=false";
    private static final String DBUSER = "root";
    private static final String DBPASS = "zxc981201";
    private static final int initConnectionSize = 3;
    private static final int maxAction = 400;
    private static final int mininle = 30;
    private static final String CONNECT_TIME_OUT = "sun.net.client.defaultConnectTimeout";
    private static final String CONNEVT_TIME_OUT_SECOND = "10000";// （单位：毫秒）
    private static final String READ_TIME_OUT = "sun.net.client.defaultReadTimeout";
    private static final String READ_TIME_OUT_SECOND = "10000";// （单位：毫秒）
    private static final int MAX_WAIT_MILL = 3;
    private static final int CHECKOUT_TIMEOUT = 2000;//毫秒
    private static final int TEST_PERIOD = 120;

    private BasicDataSource basicDataSource;
    private JdbcTemplate jdbcTemplate;

    public Database() {
        basicDataSource = new BasicDataSource();
        basicDataSource.setDriverClassName(DBDRIVER);
        basicDataSource.setUrl(DBURL);
        basicDataSource.setUsername(DBUSER);
        basicDataSource.setPassword(DBPASS);
        basicDataSource.setInitialSize(initConnectionSize);
        basicDataSource.setMaxTotal(maxAction);
        basicDataSource.setMinIdle(mininle);
        basicDataSource.setMaxWaitMillis(MAX_WAIT_MILL);
        basicDataSource.setLifo(true);
        jdbcTemplate = new JdbcTemplate();
        jdbcTemplate.setDataSource(basicDataSource);
        System.setProperty(CONNECT_TIME_OUT, CONNEVT_TIME_OUT_SECOND);
        System.setProperty(READ_TIME_OUT, READ_TIME_OUT_SECOND);

    }
}
