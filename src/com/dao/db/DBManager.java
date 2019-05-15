package com.dao.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;

import com.mysql.jdbc.Statement;

/**
 * MYSQL数据库底层封装
 * @author Administrator
 *
 */
public class DBManager {
	    public static void main(String[] args){
	        //add(uname, uemail, upwd);
	        //update("李诗诗","lishishi@com","666");
	        //show();
	       // del("wxuser","1");
	    	//System.out.print(showdollar("QnMe0aeP1cWSC6oLJePKf1ezu-s"));
	    	//addcontents("1","2","3");
	    	//updateDollarByNickname("杨爵爷",34);
	    	//addstarsigns("wxstar","stars","456456456",5);
	    	//addstarsigns("wxsign","signs","456456456",5);
	    	//DBManager.updatestarsignByuid("wxstar", "stars", "7268260",5);
	    	DBManager.updateDollarByopenid("oQnMe0aeP1cWSC6oLJePKf1ezu-s",77);
	    }
	    //增加用户操作
	    public static int adduser(String openid,String nickname,String sex,String city,int dollar,String uid) {
	        int i=0;
	        String sql="insert into wxuser (openid,nickname,sex,city,dollar,uid) values (?,?,?,?,?,?)";
	        DBConnection db = new DBConnection();
	        try {        
	            PreparedStatement preStmt = (PreparedStatement) db.conn.prepareStatement(sql);
	            preStmt.setString(1, openid);
	            preStmt.setString(2, nickname);
	            preStmt.setString(3, sex);
	            preStmt.setString(4, city);
	            preStmt.setInt(5, dollar);
	            preStmt.setString(6, uid);
	            preStmt.executeUpdate();        
	            //Statement statement = (Statement) db.conn.createStatement();
	            //statement.executeUpdate(sql);
	            preStmt.close();
	            db.close();//关闭连接 
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return i;//返回影响的行数，1为执行成功
	    }
	  //增加幸运星和签到
	    public static int addstarsigns(String table,String starssigns,String uid,int num) {
	        int i=0;
	        String sql="insert into "+table+"(uid,"+starssigns+",time) values (?,?,?)";
	        DBConnection db = new DBConnection();
	        try {        
	            PreparedStatement preStmt = (PreparedStatement) db.conn.prepareStatement(sql);
	            preStmt.setString(1, uid);
	            preStmt.setInt(2, num);
	            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
	            String t=df.format(new Date());// new Date()为获取当前系统时间
	            preStmt.setString(3, t);
	            preStmt.executeUpdate();        
	            //Statement statement = (Statement) db.conn.createStatement();
	            //statement.executeUpdate(sql);
	            preStmt.close();
	            db.close();//关闭连接 
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return i;//返回影响的行数，1为执行成功
	    }
	    //增加私信
	    public static String addprivate(String table,String text,String uid,String contents) {
	        String status="";
	        String sql="insert into "+table+"(uid,"+text+",time) values (?,?,?)";
	        DBConnection db = new DBConnection();
	        try {        
	            PreparedStatement preStmt = (PreparedStatement) db.conn.prepareStatement(sql);
	            preStmt.setString(1, uid);
	            preStmt.setString(2, contents);
	            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
	            String t=df.format(new Date());// new Date()为获取当前系统时间
	            preStmt.setString(3, t);
	            preStmt.executeUpdate();        
	            //Statement statement = (Statement) db.conn.createStatement();
	            //statement.executeUpdate(sql);
	            preStmt.close();
	            db.close();//关闭连接 
	            status+="success";
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return status;//返回影响的行数，1为执行成功
	    }
	    
	    //更新幸运星和签到数量通过uid
	    public static int updatestarsignByuid(String table,String starssigns,String uid,int num) {
	        int i =0;
	        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
            String tt=df.format(new Date());// new Date()为获取当前系统时间
	        String sql="update "+table+" set "+starssigns+"='"+num+"',time='"+tt+"' where uid='"+uid+"'";
	        DBConnection db = new DBConnection();
	        
	        try {
	            PreparedStatement preStmt = (PreparedStatement) db.conn.prepareStatement(sql);          
	            preStmt.executeUpdate();	            
	            preStmt.close();
	            db.close();//关闭连接 
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return i;//返回影响的行数，1为执行成功
	    }
	    //插入用户发送短信记录操作
	    public static int addcontents(String openid,String wxtext,String phone,String uid) {
	        int i=0;
	        String sql="insert into wxcontents (openid,wxtext,phone,time,uid) values (?,?,?,?,?)";
	        DBConnection db = new DBConnection();
	        try {        
	            PreparedStatement preStmt = (PreparedStatement) db.conn.prepareStatement(sql);
	            preStmt.setString(1, openid);
	            preStmt.setString(2, wxtext);
	            preStmt.setString(3, phone);
	            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
	            String t=df.format(new Date());// new Date()为获取当前系统时间
	            preStmt.setString(4, t);
	            preStmt.setString(5, uid);
	            preStmt.executeUpdate();
	            //Statement statement = (Statement) db.conn.createStatement();
	            //statement.executeUpdate(sql);
	            i+=1;
	            preStmt.close();
	            db.close();//关闭连接 
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return i;//返回影响的行数，1为执行成功
	    }
	  //根据uid和表名查找是否重复
	    public static String showuid(String table,String uid){
	         String sql ="select uid from "+table+"";
	         String status="";
	         DBConnection db = new DBConnection();
	         try {
	            Statement stmt = (Statement) db.conn.createStatement();
	            ResultSet rs = (ResultSet) stmt.executeQuery(sql);
	            while(rs.next()){
	                  if(uid.equals(rs.getString("uid"))){
	                	  status="sucess";
	                  }
	               
	            }
	            rs.close();
	            db.close();//关闭连接 
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
			return status; 
	    }
	    //根据openid查找是否重复
	    public static String show(String openid){
	         String sql ="select openid from wxuser";
	       
	         String status="";
	      
	         DBConnection db = new DBConnection();
	         try {
	            Statement stmt = (Statement) db.conn.createStatement();
	            ResultSet rs = (ResultSet) stmt.executeQuery(sql);
	            while(rs.next()){
	                  if(openid.equals(rs.getString("openid"))){
	                	  status="sucess";
	                  }
	               
	            }
	            rs.close();
	            db.close();//关闭连接 
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	         
			return status; 
	    }
	    //根据用户昵称查找是否重复
	    public static String showNickname(String nickname){
	         String sql ="select * from wxuser";
	         String status="";
	         DBConnection db = new DBConnection();
	         try {
	            Statement stmt = (Statement) db.conn.createStatement();
	            ResultSet rs = (ResultSet) stmt.executeQuery(sql);
	            while(rs.next()){
	                  if(nickname.equals(rs.getString("nickname"))){
	                	  status="sucess";
	                  }
	               
	            }
	            rs.close();
	            db.close();//关闭连接 
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
			return status; 
	    }
	  //根据openid返回uid
	    public static String showuid(String openid){
	         String sql ="select uid from wxuser where openid='"+openid+"'";
	         String uid="";
	      
	         
	         DBConnection db = new DBConnection();
	         try {
	            Statement stmt = (Statement) db.conn.createStatement();
	            ResultSet rs = (ResultSet) stmt.executeQuery(sql);
	            while(rs.next()){
	                  
	                	  uid+=rs.getString("uid");              
	            }
	            rs.close();
	            db.close();//关闭连接 
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	         
			return uid; 
	    }
	    //根据uid返回用户剩余短信
	    public static String showdollar(String uid){
	         String sql ="select dollar from wxuser where uid='"+uid+"'";
	         String num="";
	         DBConnection db = new DBConnection();
	         try {
	            Statement stmt = (Statement) db.conn.createStatement();
	            ResultSet rs = (ResultSet) stmt.executeQuery(sql);
	            while(rs.next()){
	                  
	                	  num+=rs.getInt("dollar");              
	            }
	            rs.close();
	            db.close();//关闭连接 
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
			return num; 
	    }
	  //根据uid返回幸运星签到数量
	    public static String showstarssignsnum(String table,String uid,String starssigns){
	         String sql ="select "+starssigns+" from "+table+" where uid='"+uid+"'";
	         String num="";
	         DBConnection db = new DBConnection();
	         try {
	            Statement stmt = (Statement) db.conn.createStatement();
	            ResultSet rs = (ResultSet) stmt.executeQuery(sql);
	            while(rs.next()){
	                  
	            	num+=rs.getString(starssigns);              
	            }
	            rs.close();
	            db.close();//关闭连接 
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
			return num; 
	    }
	  //根据uid返回幸运星签到时间
	    public static String showstarssignstime(String table,String uid){
	         String sql ="select time from "+table+" where uid='"+uid+"'";
	         String time="";
	         DBConnection db = new DBConnection();
	         try {
	            Statement stmt = (Statement) db.conn.createStatement();
	            ResultSet rs = (ResultSet) stmt.executeQuery(sql);
	            while(rs.next()){
	                  
	                	  time+=rs.getString("time");              
	            }
	            rs.close();
	            db.close();//关闭连接 
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
			return time; 
	    }
	    //根据openid返回用户剩余短信
	    public static String showdollarbyopenid(String openid){
	         String sql ="select dollar from wxuser where openid='"+openid+"'";
	         String num="";
	         DBConnection db = new DBConnection();
	         try {
	            Statement stmt = (Statement) db.conn.createStatement();
	            ResultSet rs = (ResultSet) stmt.executeQuery(sql);
	            while(rs.next()){
	                  
	                	  num+=rs.getInt("dollar");	               
	            }
	            rs.close();
	            db.close();//关闭连接 
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
			return num; 
	    }
	    //根据用户昵称返回openid
	    public static String showopenidbynickName(String nickName){
	         String sql ="select openid from wxuser where nickname='"+nickName+"'";
	         String openid="";
	         DBConnection db = new DBConnection();
	         try {
	            Statement stmt = (Statement) db.conn.createStatement();
	            ResultSet rs = (ResultSet) stmt.executeQuery(sql);
	            while(rs.next()){
	                  
	            	openid+=rs.getString("openid");	               
	            }
	            rs.close();
	            db.close();//关闭连接 
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
			return openid; 
	    }
	  //根据用户昵称返回uid
	    public static String showuidbynickName(String nickName){
	         String sql ="select uid from wxuser where nickname='"+nickName+"'";
	         String uid="";
	         DBConnection db = new DBConnection();
	         try {
	            Statement stmt = (Statement) db.conn.createStatement();
	            ResultSet rs = (ResultSet) stmt.executeQuery(sql);
	            while(rs.next()){
	                  
	            	uid+=rs.getString("uid");	               
	            }
	            rs.close();
	            db.close();//关闭连接 
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
			return uid; 
	    }
	   
	  //更新短信数量通过openid
	    public static int updateDollarByopenid(String openid,int dollar) {
	        int i =0;
	        String sql="update wxuser set dollar='"+dollar+"' where openid='"+openid+"'";
	        DBConnection db = new DBConnection();
	        
	        try {
	            PreparedStatement preStmt = (PreparedStatement) db.conn.prepareStatement(sql);
	          
	          
	            preStmt.executeUpdate();
	            
	            preStmt.close();
	            db.close();//关闭连接 
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return i;//返回影响的行数，1为执行成功
	    }
	    //删除操作
	    public static int del(String table,String openid) {
	        int i=0;
	        String sql="delete from "+table+" where openid=?";
	        DBConnection db = new DBConnection();
	        try {    
	            PreparedStatement preStmt = (PreparedStatement) db.conn.prepareStatement(sql);
	            preStmt.setString(1,openid);
	            preStmt.executeUpdate();
	            preStmt.close();
	            db.close();//关闭连接 
	        } catch (SQLException e){
	            e.printStackTrace();
	        }
	        return i;//返回影响的行数，1为执行成功
	    }
	    //返回新闻
	    public static String shownews(){
	         String sql ="select text from wxnews";
	         String text="";
	         DBConnection db = new DBConnection();
	         try {
	            Statement stmt = (Statement) db.conn.createStatement();
	            ResultSet rs = (ResultSet) stmt.executeQuery(sql);
	            while(rs.next()){
	                  
	            	text+=rs.getString("text");	               
	            }
	            rs.close();
	            db.close();//关闭连接 
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
			return text; 
	    }
	    //返回广告墙
	    public static String showgg(){
	         String sql ="select gg from wxnews";
	         String gg="";
	         DBConnection db = new DBConnection();
	         try {
	            Statement stmt = (Statement) db.conn.createStatement();
	            ResultSet rs = (ResultSet) stmt.executeQuery(sql);
	            while(rs.next()){
	                  
	            	gg+=rs.getString("gg");	               
	            }
	            rs.close();
	            db.close();//关闭连接 
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
			return gg; 
	    }
}

