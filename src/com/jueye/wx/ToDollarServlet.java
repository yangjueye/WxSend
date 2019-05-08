package com.jueye.wx;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dao.db.DBManager;

public class ToDollarServlet extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public ToDollarServlet() {
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
       
		String openid=request.getParameter("openid");
        String uid=DBManager.showuid(openid);
        String issign=DBManager.showuid("wxsign", uid);
        if(issign.equals("sucess")){
        int num=Integer.parseInt(DBManager.showstarssignsnum("wxsign", uid, "signs"));
        if(num<7){
        	 Writer out = response.getWriter(); 		      
		      out.write("距离"+(7-num)+"天！");
		      out.flush(); 
        }else if(num>7 ||num==7){
        	int dollar=num/7;
            int udollar=Integer.parseInt(DBManager.showdollar(uid));
            DBManager.updateDollarByopenid(openid, udollar+dollar);
            int unum=num-dollar*7;
            DBManager.updatestarsignByuid("wxsign", "signs", uid, unum);
        	Writer out = response.getWriter(); 		      
		      out.write("增加传音"+dollar+"次！");
		      out.flush(); 
        }
        }
        else{
        	Writer out = response.getWriter(); 		      
		      out.write("请先签到打卡！");
		      out.flush(); 
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
