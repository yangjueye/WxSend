package com.jueye.wx;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dao.db.DBManager;

public class ShowVoiceServlet extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public ShowVoiceServlet() {
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
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);

	}

	/**
	 * The doPost method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to
	 * post.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		/* 设置响应头允许ajax跨域访问 */
		response.setHeader("Access-Control-Allow-Origin", "*");
		/* 星号表示所有的异域请求都可以接受， */
		response.setHeader("Access-Control-Allow-Methods", "GET,POST");
		String usercode = request.getParameter("usercode");
		String type = request.getParameter("type");

		if (type.equals("1")) {
			if (DBManager.showvaluleByParams(
					DBManager.showvaluleByCode(usercode)[0],
					DBManager.showvaluleByCode(usercode)[1],
					DBManager.showvaluleByCode(usercode)[2], "wxcontents",
					"wxtext") == null || DBManager.showvaluleByParams(
							DBManager.showvaluleByCode(usercode)[0],
							DBManager.showvaluleByCode(usercode)[1],
							DBManager.showvaluleByCode(usercode)[2], "wxcontents",
							"wxtext").isEmpty()) {
				Writer out = response.getWriter();
				out.write("获取失败！请检查爱心传音验证码是否正确或失效！有问题请联系客服！");
				out.flush();
			} else {
				String status = DBManager.showvaluleByParams(
						DBManager.showvaluleByCode(usercode)[0],
						DBManager.showvaluleByCode(usercode)[1],
						DBManager.showvaluleByCode(usercode)[2], "wxcontents",
						"wxtext");
				Writer out = response.getWriter();
				out.write(status);
				out.flush();
			}
		} 
		if (type.equals("2")) {

			if (DBManager.showvaluleByCode(usercode)[0] == null) {
				Writer out = response.getWriter();
				out.write("获取失败！请检查爱心传音验证码是否正确或失效！有问题请联系客服！");
				out.flush();
			} else {
				String openid = DBManager.showvaluleByCode(usercode)[0];
				String nickname = DBManager.showvaluleByOpenid(openid,
						"wxuser", "nickname");
				if (!nickname.isEmpty()) {
					Writer out = response.getWriter();
					out.write(nickname);
					out.flush();
				}
			}
		}
	}

	/**
	 * Initialization of the servlet. <br>
	 * 
	 * @throws ServletException
	 *             if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
