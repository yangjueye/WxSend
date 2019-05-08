package com.dao.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * 数据库连接层MYSQL
 * @author Administrator
 *
 */
public class DBConnection {
    
    
    /**
     * 连接数据库
     * @return
     */
	
	    String driver = "com.mysql.jdbc.Driver";
	    String url= "jdbc:mysql://localhost:3306/wxsend";
	    String user = "root";
	    String password = "8023";
	    
	    public Connection conn;

	    public DBConnection() {

	        try {
	            Class.forName(driver);// 加载驱动程序
	            conn = (Connection) DriverManager.getConnection(url, user, password);// 连续数据库
	            
	            if(!conn.isClosed())
	                System.out.println(""); 
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	    
	    public void close() {
	        try {
	            this.conn.close();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	
    
}

