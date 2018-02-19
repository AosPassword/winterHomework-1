package org.redrock.gayligayli.controller.listener;

import org.apache.commons.dbcp2.BasicDataSource;
import org.redrock.gayligayli.Dao.JdbcUtil;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class DatabaseListener implements ServletContextListener {
    private static final String DBDRIVER = "com.mysql.jdbc.Driver";
    private static final String DBURL = "jdbc:mysql://localhost:3306/gayligayli?useUnicode=true&characterEncoding=utf-8&useSSL=false";
    private static final String DBUSER = "root";
    private static final String DBPASS = "zxc981201";
    private static final int initConnectionSize = 40;
    private static final int maxAction = 130;
    private static final int mininle = 30;

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

        JdbcUtil.initDatabase(basicDataSource);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }

}
