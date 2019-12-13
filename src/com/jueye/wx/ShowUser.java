package com.jueye.wx;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dao.db.DBManager;
import com.jueye.util.RandomVerificationCodes;

public class ShowUser extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public ShowUser() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		/* 设置响应头允许ajax跨域访问 */
		response.setHeader("Access-Control-Allow-Origin", "*");
		/* 星号表示所有的异域请求都可以接受， */
		response.setHeader("Access-Control-Allow-Methods", "GET,POST");
		String code = request.getParameter("code");
		String headurl = request.getParameter("headurl");
		String nickname = request.getParameter("nickname");
		nickname = new String(nickname.getBytes("iso8859-1"), "utf-8");
		String sex = request.getParameter("sex");
		String country = request.getParameter("country");
		String province = request.getParameter("province");
		String city = request.getParameter("city");
		String openid = null;
		try {
			openid = new GetOpenid().get(code);
			//System.out.print(openid);
			int dollar = 20;
			boolean boolopenid = DBManager.showrepeat("wxuser", openid,
					"openid");
			boolean boolname = DBManager.showrepeat("wxuser", nickname,
					"nickname");
			boolean boolsex = DBManager.showrepeat("wxuser", sex, "sex");
			boolean boolcity = DBManager.showrepeat("wxuser", city, "city");
			if (boolopenid) {
				// 随机生成用户UID
				String userId = "";
				userId += RandomVerificationCodes.VerificationCodes(7);
				System.out.println("用户UID：" + userId);
				while (!DBManager.showrepeat("wxuser", userId, "uid")) {
					userId = "";
					userId += RandomVerificationCodes.VerificationCodes(7);
					System.out.println("用户UID去重后：" + userId);
					continue;
				}
				boolean booladduser = DBManager.adduser(openid, nickname, sex, city, dollar, userId);
				boolean booladdstars = DBManager.addstarsigns("wxstar", "stars", userId, 0);
				boolean booladdprivate = DBManager.addprivate("wxprivate", "text", userId,"[遇见你，是爵爷莫大的荣幸！]");				
			}
			Writer out = response.getWriter();
			out.write(openid);
			out.flush();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
