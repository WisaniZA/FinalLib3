package com.lib.listener;

import java.sql.SQLException;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import org.apache.tomcat.dbcp.dbcp.BasicDataSource;

@WebListener
public class MyServletListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext sc = sce.getServletContext();
        String url = sc.getInitParameter("url");
        String username = sc.getInitParameter("username");
        String password = sc.getInitParameter("password");
        String database = sc.getInitParameter("database");
        String driver = sc.getInitParameter("driver");
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(driver);
        dataSource.setUrl(url+database);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setMinIdle(5);
        dataSource.setMaxIdle(10);
//        dataSource.setMaxActive(25);
        sc.setAttribute("dbman", dataSource);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ServletContext sc = sce.getServletContext();
        BasicDataSource dataSource = (BasicDataSource) sc.getAttribute("dbman");
        if (dataSource != null) {
            try {
                dataSource.close();
            } catch (SQLException ex) {
                System.out.println("Error closing Database Pool: "+ex.getMessage()); 
            }
        }
    }
}
