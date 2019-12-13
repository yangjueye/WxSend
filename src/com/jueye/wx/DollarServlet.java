package com.jueye.wx;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import com.dao.db.DBManager;

public class DollarServlet extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public DollarServlet() {
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
//        String nickName = request.getParameter("nickName");
//        nickName = new String(nickName.getBytes("iso8859-1"),"utf-8");
        String openid=request.getParameter("openid");
        //System.out.print("前台获取openid="+openid);
        String uid=DBManager.showuid(openid);
        int dollar=DBManager.showdollarbyopenid(openid);
        int stars=DBManager.showstarssignsnum("wxstar", uid, "stars");
        int signs=DBManager.showstarssignsnum("wxsign", uid, "signs");
        String time=DBManager.showstarssignstime("wxsign", uid);
        String privatetext=DBManager.ShowStringByUid("wxprivate", uid, "text");
       
         //返回值给微信小程序
        Writer out = response.getWriter(); 
        out.write(dollar+"?"+stars+"?"+uid+"?"+signs+"?"+time+"?"+privatetext);
        out.flush(); 
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
