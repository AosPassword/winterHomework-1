package org.redrock.gayligayli.controller.listener;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.apache.commons.dbcp2.BasicDataSource;
import org.redrock.gayligayli.Dao.Dao;
import org.redrock.gayligayli.Dao.JdbcUtil;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.beans.PropertyVetoException;

@WebListener
public class DatabaseListener implements ServletContextListener {
    private static final String DBDRIVER = "com.mysql.jdbc.Driver";
    private static final String DBURL = "jdbc:mysql://localhost:3306/gayligayli?useUnicode=true&characterEncoding=utf-8&useSSL=false";
    private static final String DBUSER = "root";
    private static final String DBPASS = "zxc981201";
    private static final int initConnectionSize = 3;
    private static final int maxAction = 400;
    private static final int mininle = 30;
    private static final String CONNECT_TIME_OUT = "sun.net.client.defaultConnectTimeout";
    private static final String CONNEVT_TIME_OUT_SECOND = "10000";// （单位：毫秒）
    private static final String READ_TIME_OUT = "sun.net.client.defaultReadTimeout";
    private static final String READ_TIME_OUT_SECOND = "10000";// （单位：毫秒）
    private static final int MAX_WAIT_MILL=3;
    private static final int CHECKOUT_TIMEOUT=2000;//毫秒
    private static final int TEST_PERIOD=120;


    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {

        BasicDataSource basicDataSource = new BasicDataSource();
        basicDataSource.setDriverClassName(DBDRIVER);
        basicDataSource.setUrl(DBURL);
        basicDataSource.setUsername(DBUSER);
        basicDataSource.setPassword(DBPASS);
        basicDataSource.setInitialSize(initConnectionSize);
        basicDataSource.setMaxTotal(maxAction);
        basicDataSource.setMinIdle(mininle);
        basicDataSource.setMaxWaitMillis(MAX_WAIT_MILL);
        basicDataSource.setLifo(true);
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        jdbcTemplate.setDataSource(basicDataSource);
        Dao.setJdbcTemplate(jdbcTemplate);
        System.setProperty(CONNECT_TIME_OUT, CONNEVT_TIME_OUT_SECOND);
        System.setProperty(READ_TIME_OUT, READ_TIME_OUT_SECOND);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }

}
