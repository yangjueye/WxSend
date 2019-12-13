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

import com.jueye.ocr.AuthService;
import com.mysql.jdbc.Statement;

/**
 * MYSQL数据库底层封装
 * 
 * @author Administrator
 * 
 */
public class DBManager {
	public static void main(String[] args) {
		// add(uname, uemail, upwd);
		// update("李诗诗","lishishi@com","666");
		// show();
		// del("wxuser","1");
		// System.out.print(showdollar("QnMe0aeP1cWSC6oLJePKf1ezu-s"));
		// addcontents("1","2","3");
		// updateDollarByNickname("杨爵爷",34);
		// addstarsigns("wxstar","stars","456456456",5);
		// addstarsigns("wxsign","signs","456456456",5);
		// DBManager.updatestarsignByuid("wxstar", "stars", "7268260",5);
		// String token =AuthService.getAuth();
		// DBManager.addtoken("baidutoken", token);
		// System.out.print(DBManager.showvaluleByOpenid("oQQsi0SJIEJhYKS2vWiluYA9RPgA",
		// "wxadressbook", "wxtext"));
		// System.out.print(DBManager.show("oQQsi0SJIEJhYKS2vWiluYA9RPgA",
		// "wxadressbook"));
		// SimpleDateFormat df = new
		// SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
		// String t=df.format(new Date());// new Date()为获取当前系统时间
		// DBManager.addcodebytable("wxcode", "oQQsi0SJIEJhYKS2vWiluYA9RPgA",
		// "154869",t, "15555555555");
		// System.out.print(DBManager.showvaluleByParams(DBManager.showvaluleByCode("653726")[0],
		// DBManager.showvaluleByCode("653726")[1],
		// DBManager.showvaluleByCode("653726")[2], "wxcontents", "wxtext"));
		// System.out.print(DBManager.showvaluleByCode("653726")[1]);
		// DBManager.del("wxuser", "sex", "1");
	}

	// 增加紧急联系人
	public static boolean AddPeoples(String openid, String wxtext,
			String phone, String uid) {
		String sql = "insert into wxadressbook (openid,wxtext,phone,time,uid) values (?,?,?,?,?)";
		DBConnection db = new DBConnection();
		try {
			PreparedStatement preStmt = (PreparedStatement) db.conn
					.prepareStatement(sql);
			preStmt.setString(1, openid);
			preStmt.setString(2, wxtext);
			preStmt.setString(3, phone);
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
			String t = df.format(new Date());// new Date()为获取当前系统时间
			preStmt.setString(4, t);
			preStmt.setString(5, uid);
			preStmt.executeUpdate();
			preStmt.close();
			db.close();// 关闭连接
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}

	// 增加用户操作
	public static boolean adduser(String openid, String nickname, String sex,
			String city, int dollar, String uid) {
		String sql = "insert into wxuser (openid,nickname,sex,city,dollar,uid) values (?,?,?,?,?,?)";
		DBConnection db = new DBConnection();
		try {
			PreparedStatement preStmt = (PreparedStatement) db.conn
					.prepareStatement(sql);
			preStmt.setString(1, openid);
			preStmt.setString(2, nickname);
			preStmt.setString(3, sex);
			preStmt.setString(4, city);
			preStmt.setInt(5, dollar);
			preStmt.setString(6, uid);
			preStmt.executeUpdate();
			// Statement statement = (Statement) db.conn.createStatement();
			// statement.executeUpdate(sql);
			preStmt.close();
			db.close();// 关闭连接
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	// 增加幸运星和签到
	public static boolean addstarsigns(String table, String starssigns,
			String uid, int num) {
		String sql = "insert into " + table + "(uid," + starssigns
				+ ",time) values (?,?,?)";
		DBConnection db = new DBConnection();
		try {
			PreparedStatement preStmt = (PreparedStatement) db.conn
					.prepareStatement(sql);
			preStmt.setString(1, uid);
			preStmt.setInt(2, num);
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
			String t = df.format(new Date());// new Date()为获取当前系统时间
			preStmt.setString(3, t);
			preStmt.executeUpdate();
			preStmt.close();
			db.close();// 关闭连接
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	// 增加私信
	public static boolean addprivate(String table, String text, String uid,
			String contents) {
		String sql = "insert into " + table + "(uid," + text
				+ ",time) values (?,?,?)";
		DBConnection db = new DBConnection();
		try {
			PreparedStatement preStmt = (PreparedStatement) db.conn
					.prepareStatement(sql);
			preStmt.setString(1, uid);
			preStmt.setString(2, contents);
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
			String t = df.format(new Date());// new Date()为获取当前系统时间
			preStmt.setString(3, t);
			preStmt.executeUpdate();
			// Statement statement = (Statement) db.conn.createStatement();
			// statement.executeUpdate(sql);
			preStmt.close();
			db.close();// 关闭连接
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	// 更新幸运星和签到数量通过uid
	public static boolean updatestarsignByuid(String table, String starssigns,
			String uid, int num) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
		String tt = df.format(new Date());// new Date()为获取当前系统时间
		String sql = "update " + table + " set " + starssigns + "='" + num
				+ "',time='" + tt + "' where uid='" + uid + "'";
		DBConnection db = new DBConnection();
		try {
			PreparedStatement preStmt = (PreparedStatement) db.conn
					.prepareStatement(sql);
			preStmt.executeUpdate();
			preStmt.close();
			db.close();// 关闭连接
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	// 增加用户发送短信记录操作
	public static boolean addcontents(String openid, String wxtext,
			String phone, String t, String uid) {
		String sql = "insert into wxcontents (openid,wxtext,phone,time,uid) values (?,?,?,?,?)";
		DBConnection db = new DBConnection();
		try {
			PreparedStatement preStmt = (PreparedStatement) db.conn
					.prepareStatement(sql);
			preStmt.setString(1, openid);
			preStmt.setString(2, wxtext);
			preStmt.setString(3, phone);
			// SimpleDateFormat df = new
			// SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
			// String t=df.format(new Date());// new Date()为获取当前系统时间
			preStmt.setString(4, t);
			preStmt.setString(5, uid);
			preStmt.executeUpdate();
			preStmt.close();
			db.close();// 关闭连接
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	// 根据表名插入验证码
	public static boolean addcodebytable(String table, String openid,
			String code, String t, String phone) {
		String sql = "insert into " + table
				+ " (openid,code,time,phone) values (?,?,?,?)";
		DBConnection db = new DBConnection();
		try {
			PreparedStatement preStmt = (PreparedStatement) db.conn
					.prepareStatement(sql);
			preStmt.setString(1, openid);
			preStmt.setString(2, code);
			preStmt.setString(3, t);
			preStmt.setString(4, phone);
			preStmt.executeUpdate();
			preStmt.close();
			db.close();// 关闭连接
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}

	// 根据字段和表名查找该表中是否存在重复数据值
	public static boolean showrepeat(String table, String values,
			String tablekeys) {
		String sql = "select " + tablekeys + " from " + table + "";
		DBConnection db = new DBConnection();
		try {
			Statement stmt = (Statement) db.conn.createStatement();
			ResultSet rs = (ResultSet) stmt.executeQuery(sql);
			while (rs.next()) {
				if (values.equals(rs.getString(tablekeys))) {
					return false;
				}
			}
			rs.close();
			db.close();// 关闭连接
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return true;
	}

	// 根据openid返回uid
	public static String showuid(String openid) {
		String sql = "select uid from wxuser where openid='" + openid + "'";
		String uid = "";
		DBConnection db = new DBConnection();
		try {
			Statement stmt = (Statement) db.conn.createStatement();
			ResultSet rs = (ResultSet) stmt.executeQuery(sql);
			while (rs.next()) {
				uid += rs.getString("uid");
			}
			rs.close();
			db.close();// 关闭连接
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return uid;
	}

	// 根据openid、表名返回特定字段值
	public static String showvaluleByOpenid(String openid, String table,
			String showvalue) {
		String sql = "select " + showvalue + " from " + table
				+ " where openid='" + openid + "'";
		// System.out.print(sql);
		String value = "";
		DBConnection db = new DBConnection();
		try {
			Statement stmt = (Statement) db.conn.createStatement();
			ResultSet rs = (ResultSet) stmt.executeQuery(sql);
			while (rs.next()) {

				value += rs.getString(showvalue);
			}
			rs.close();
			db.close();// 关闭连接
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return value;
	}

	// 根据多个参数、表名返回特定字段值
	public static String showvaluleByParams(String openid, String time,
			String phone, String table, String showvalue) {
		String sql = "select " + showvalue + " from " + table
				+ " where openid='" + openid + "' and time='" + time
				+ "' and phone='" + phone + "'";
		String value = "";
		DBConnection db = new DBConnection();
		try {
			Statement stmt = (Statement) db.conn.createStatement();
			ResultSet rs = (ResultSet) stmt.executeQuery(sql);
			while (rs.next()) {

				value += rs.getString(showvalue);
			}
			rs.close();
			db.close();// 关闭连接
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return value;
	}

	// 根据验证码返回多个参数
	public static String[] showvaluleByCode(String usercode) {
		String sql = "select openid,time,phone from wxcode where code="
				+ usercode + "";
		String[] value = new String[3];
		DBConnection db = new DBConnection();
		try {
			Statement stmt = (Statement) db.conn.createStatement();
			ResultSet rs = (ResultSet) stmt.executeQuery(sql);
			while (rs.next()) {
				value[0] = rs.getString("openid");
				value[1] = rs.getString("time");
				value[2] = rs.getString("phone");
			}
			rs.close();
			db.close();// 关闭连接
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return value;
	}

	// 根据uid返回用户剩余短信
	public static String showdollar(String uid) {
		String sql = "select dollar from wxuser where uid='" + uid + "'";
		String num = "";
		DBConnection db = new DBConnection();
		try {
			Statement stmt = (Statement) db.conn.createStatement();
			ResultSet rs = (ResultSet) stmt.executeQuery(sql);
			while (rs.next()) {

				num += rs.getInt("dollar");
			}
			rs.close();
			db.close();// 关闭连接
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return num;
	}
	// 根据uid返回String类型数据
		public static String ShowStringByUid(String table,String uid,String tablekey) {
			String sql = "select "+tablekey+" from "+table+" where uid='" + uid + "'";
			String values = "";
			DBConnection db = new DBConnection();
			try {
				Statement stmt = (Statement) db.conn.createStatement();
				ResultSet rs = (ResultSet) stmt.executeQuery(sql);
				while (rs.next()) {

					values += rs.getString(tablekey);
				}
				rs.close();
				db.close();// 关闭连接
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return values;
		}

	// 根据uid返回幸运星签到数量
	public static int showstarssignsnum(String table, String uid,
			String starssigns) {
		String sql = "select " + starssigns + " from " + table + " where uid='"
				+ uid + "'";
		int num = 0;
		DBConnection db = new DBConnection();
		try {
			Statement stmt = (Statement) db.conn.createStatement();
			ResultSet rs = (ResultSet) stmt.executeQuery(sql);
			while (rs.next()) {

				num += rs.getInt(starssigns);
			}
			rs.close();
			db.close();// 关闭连接
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return num;
	}

	// 根据uid返回金币表、签到表的时间
	public static String showstarssignstime(String table, String uid) {
		String sql = "select time from " + table + " where uid='" + uid + "'";
		String time = "";
		DBConnection db = new DBConnection();
		try {
			Statement stmt = (Statement) db.conn.createStatement();
			ResultSet rs = (ResultSet) stmt.executeQuery(sql);
			while (rs.next()) {

				time += rs.getString("time");
			}
			rs.close();
			db.close();// 关闭连接
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return time;
	}

	// 根据openid返回用户剩余短信
	public static int showdollarbyopenid(String openid) {
		String sql = "select dollar from wxuser where openid='" + openid + "'";
		int num = 0;
		DBConnection db = new DBConnection();
		try {
			Statement stmt = (Statement) db.conn.createStatement();
			ResultSet rs = (ResultSet) stmt.executeQuery(sql);
			while (rs.next()) {

				num += rs.getInt("dollar");
			}
			rs.close();
			db.close();// 关闭连接
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return num;
	}

	// 通过openid更新余额
	public static boolean updateDollarByopenid(String openid, int dollar) {
		String sql = "update wxuser set dollar='" + dollar + "' where openid='"
				+ openid + "'";
		DBConnection db = new DBConnection();
		try {
			PreparedStatement preStmt = (PreparedStatement) db.conn
					.prepareStatement(sql);
			preStmt.executeUpdate();
			preStmt.close();
			db.close();// 关闭连接
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	// 根据表中特定键值删除数据
	public static boolean del(String table, String tablekey, String byvalue) {
		String sql = "delete from " + table + " where " + tablekey + "='"
				+ byvalue + "'";
		DBConnection db = new DBConnection();
		try {
			PreparedStatement preStmt = (PreparedStatement) db.conn
					.prepareStatement(sql);
			preStmt.executeUpdate();
			preStmt.close();
			db.close();// 关闭连接
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}

	}

	// 返回新闻
	public static String shownews() {
		String sql = "select text from wxnews";
		String text = "";
		DBConnection db = new DBConnection();
		try {
			Statement stmt = (Statement) db.conn.createStatement();
			ResultSet rs = (ResultSet) stmt.executeQuery(sql);
			while (rs.next()) {

				text += rs.getString("text");
			}
			rs.close();
			db.close();// 关闭连接
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return text;
	}

	// 返回广告墙
	public static String showgg() {
		String sql = "select gg from wxnews";
		String gg = "";
		DBConnection db = new DBConnection();
		try {
			Statement stmt = (Statement) db.conn.createStatement();
			ResultSet rs = (ResultSet) stmt.executeQuery(sql);
			while (rs.next()) {

				gg += rs.getString("gg");
			}
			rs.close();
			db.close();// 关闭连接
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return gg;
	}
}
