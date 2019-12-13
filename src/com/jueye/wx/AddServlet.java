package com.jueye.wx;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import send.SMS;

import com.dao.db.DBManager;
import com.jueye.util.BadWordUtil2;
import com.jueye.util.RandomVerificationCodes;

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
		String userphone = request.getParameter("userphone");
		String usercontens = request.getParameter("usercontens");
		//usercontens = new String(usercontens.getBytes("iso8859-1"), "utf-8");
		// String txtpath =
		// "E:\\Program Files\\myeclipse10\\WorkSpace\\WxTravel\\WebRoot\\txt\\dictionary.txt";
		String txtpath = request.getRealPath("/txt/dictionary.txt");
		// System.out.print("敏感词检测："
		// + BadWordUtil2.BadWordNum(usercontens, txtpath));
		// if (BadWordUtil2.BadWordNum(usercontens,txtpath) != 0) {
		// Writer out = response.getWriter();
		// String status = "存在敏感词汇！";
		// out.write(status);
		// out.flush();
		// } else {
		String openid = request.getParameter("openid");
		// System.out.print(userphone + usercontens);
		int charnum = getContentWordCount(usercontens);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
		String t = df.format(new Date());// new Date()为获取当前系统时间
		// System.out.print("字数=" + charnum + "取余=" + charnum / 60);
		if (userphone == null || userphone.equals("") || usercontens.equals("")
				|| userphone.equals("undefined")
				|| usercontens.equals("undefined")) {
			Writer out = response.getWriter();
			String status = "内容有误或空！";
			out.write(status);
			out.flush();
		} else {
			String uid = DBManager.showuid(openid);
			if (BadWordUtil2.BadWordNum(usercontens, txtpath) == 0) {
				boolean booladdcontents = DBManager.addcontents(openid,
						usercontens, userphone, t, uid);
				// 插入发送内容判断是否成功
				if (booladdcontents) {
					int dollar = DBManager.showdollarbyopenid(openid);
					// System.out.print(1 + charnum / 60);
					if (dollar > 1 + charnum / 60 || dollar == 1 + charnum / 60) {
						// 生成验证码
						String usercode = "";
						usercode += RandomVerificationCodes
								.VerificationCodes(6);
						System.out.print("验证码：" + usercode);
						// 插入之前判断是否数据库重复
						while (!DBManager
								.showrepeat("wxcode", usercode, "code")) {
							usercode = "";
							usercode += RandomVerificationCodes
									.VerificationCodes(6);
							System.out.print("验证码去重后：" + usercode);
							continue;
						}
						if (!usercode.equals("") && usercode.length() < 7) {
							boolean boolupdatedollar = DBManager
									.updateDollarByopenid(openid, dollar - 1
											- charnum / 60);
							boolean booladdcode = DBManager.addcodebytable(
									"wxcode", openid, usercode, t, userphone);
							boolean sendstatus = SMS.execute(userphone,
									usercode);
							boolean boolupdatestars = DBManager.updatestarsignByuid(
									"wxstar", "stars", uid, DBManager
											.showstarssignsnum("wxstar", uid,
													"stars") + 10);
							if(boolupdatedollar && booladdcode && sendstatus && boolupdatestars){
							Writer out = response.getWriter();
							String status = "恭喜传音成功！";
							out.write(status);
							out.flush();
							}
						}
					}
					// 判断余额不足
					else if (dollar > 0 && dollar < 1 + charnum / 60) {
						// 返回值给微信小程序
						Writer out = response.getWriter();
						String status = "字符超出余量！";
						out.write(status);
						out.flush();
					}

				}
			} 
			//存在敏感词汇
			else if (BadWordUtil2.BadWordNum(usercontens, txtpath) != 0) {
				Writer out = response.getWriter();
				String status = "存在敏感词汇！";
				out.write(status);
				out.flush();
			}
		}

	}

	public static int getContentWordCount(String content) {
		content = content.replaceAll("\\s", "");// 可以替换大部分空白字符，不限于空格
		int hzCount = 0;// 汉字数量
		int szCount = 0;// 数字数量
		int zmCount = 0;// 字母数量
		int fhCount = 0;// 标点符号数量
		for (int i = 0; i < content.length(); i++) {
			char c = content.charAt(i);
			if (c >= '0' && c <= '9') {
				szCount++;
			} else if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')) {
				zmCount++;
			} else if (Character.toString(c).matches("[\\u4E00-\\u9FA5]+")) {
				hzCount++;
			} else {
				fhCount++;
			}
		}
		return hzCount + szCount + zmCount + fhCount;
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
