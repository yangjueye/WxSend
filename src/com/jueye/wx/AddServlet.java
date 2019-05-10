package com.jueye.wx;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import send.SMS;

import com.dao.db.DBManager;
import com.miaodiyun.httpApiDemo.IndustrySMS;

public class AddServlet extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public AddServlet() {
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
        String userphone = request.getParameter("userphone");
        String usercontens = request.getParameter("usercontens");
        usercontens = new String(usercontens.getBytes("iso8859-1"),"utf-8");
        System.out.print(usercontens);
        String openid = request.getParameter("openid");
        System.out.print(userphone+usercontens);
       int charnum=getContentWordCount(usercontens);
        System.out.print("字数="+charnum+"取余="+charnum/60);
        if(userphone==null || userphone.equals("")||usercontens.equals("")||userphone.equals("undefined")||usercontens.equals("undefined")){
        	Writer out = response.getWriter(); 
            String status="内容有误或空！";
            out.write(status);
            out.flush(); 
        }else{
        	
        	String uid=DBManager.showuid(openid);
        int i =DBManager.addcontents(openid, usercontens, userphone,uid);
        //System.out.print(DBManager.addcontents(nickname, usercontens, userphone));
        if(i==1){
        	int dollar=Integer.parseInt(DBManager.showdollarbyopenid(openid));
        	System.out.print(1+charnum/60);
        	if(dollar>1+charnum/60 || dollar==1+charnum/60){
        	DBManager.updateDollarByopenid(openid,dollar-1-charnum/60);
        	boolean sendstatus=SMS.execute(userphone, usercontens);
        	if(sendstatus){
        	 //返回值给微信小程序
            Writer out = response.getWriter(); 
            String status="恭喜传音成功！";
            out.write(status);
            out.flush(); 
            if(DBManager.showuid("wxstar", uid).equals("sucess")){
        		int num=Integer.parseInt(DBManager.showstarssignsnum("wxstar", uid, "stars"))+1;
        		System.out.print(num);
        		DBManager.updatestarsignByuid("wxstar", "stars", uid,num);
        	}
        	else {
        		DBManager.addstarsigns("wxstar","stars",uid, 1);
        	}
        	}
        	if(!sendstatus){
        		  Writer out = response.getWriter(); 
                  String status="传音失败！";
                  out.write(status);
                  out.flush(); 
        	}
        	}
        	if(dollar>0 && dollar<1+charnum/60){
           	 //返回值给微信小程序
               Writer out = response.getWriter(); 
               String status="字符超出余量！";
               out.write(status);
               out.flush(); 
           	}
        	
        	 
        }
        }
	}
    public static int getContentWordCount(String content){
	content = content.replaceAll("\\s", "");//可以替换大部分空白字符，不限于空格
	int hzCount = 0;//汉字数量
	int szCount = 0;//数字数量
	int zmCount = 0;//字母数量
	int fhCount = 0;//标点符号数量
	for(int i = 0;i < content.length();i++){
		char c = content.charAt(i);
		if(c >= '0' && c <= '9'){
			szCount++;
		}else if((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')){
			zmCount++;
		}else if(Character.toString(c).matches("[\\u4E00-\\u9FA5]+")){
			hzCount++;
		}else{
			fhCount++;
		}
	}
	return hzCount+szCount+zmCount+fhCount;
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
