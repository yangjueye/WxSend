package com.jueye.wx;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dao.db.DBManager;
import com.jueye.util.BadWordUtil2;

public class AddPeopleServlet extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public AddPeopleServlet() {
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
		String openid = request.getParameter("openid");
		String type = request.getParameter("type");
		if (type.equals("add")) {
			String phone = request.getParameter("phone");
			String pname = request.getParameter("pname");
			//System.out.print("转码前："+pname);
			//pname = new String(pname.getBytes("iso8859-1"), "utf-8");
			//System.out.print("转码后："+pname);
			if (phone == null || phone.equals("") || pname.equals("")
					|| phone.equals("undefined") || pname.equals("undefined")) {
				Writer out = response.getWriter();
				String status = "内容有误或空！";
				out.write(status);
				out.flush();
			}
			if (!openid.equals("") && !pname.isEmpty() && !phone.equals("")) {
				String uid = DBManager.showuid(openid);
				boolean booladd = DBManager.AddPeoples(openid, pname, phone,
						uid);
				if (booladd) {
					Writer out = response.getWriter();
					String status = "添加成功！";
					out.write(status);
					out.flush();
				}
			}
		} else if (type.equals("delete")) {
			boolean booldel=DBManager.del("wxadressbook", "openid",openid);
			if (booldel) {
				Writer out = response.getWriter();
				String status = "删除成功！";
				out.write(status);
				out.flush();
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
