//package com.jueye.wx;
//
//import java.io.IOException;
//import java.io.PrintWriter;
//import java.io.Writer;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import send.SMS;
//
//import com.dao.db.DBManager;
//import com.jueye.util.BadWordUtil2;
//
//public class AddVoiceServlet extends HttpServlet {
//
//	/**
//	 * Constructor of the object.
//	 */
//	public AddVoiceServlet() {
//		super();
//	}
//
//	/**
//	 * Destruction of the servlet. <br>
//	 */
//	public void destroy() {
//		super.destroy(); // Just puts "destroy" string in log
//		// Put your code here
//	}
//
//	/**
//	 * The doGet method of the servlet. <br>
//	 * 
//	 * This method is called when a form has its tag value method equals to get.
//	 * 
//	 * @param request
//	 *            the request send by the client to the server
//	 * @param response
//	 *            the response send by the server to the client
//	 * @throws ServletException
//	 *             if an error occurred
//	 * @throws IOException
//	 *             if an error occurred
//	 */
//	public void doGet(HttpServletRequest request, HttpServletResponse response)
//			throws ServletException, IOException {
//		this.doPost(request, response);
//	}
//
//	/**
//	 * The doPost method of the servlet. <br>
//	 * 
//	 * This method is called when a form has its tag value method equals to
//	 * post.
//	 * 
//	 * @param request
//	 *            the request send by the client to the server
//	 * @param response
//	 *            the response send by the server to the client
//	 * @throws ServletException
//	 *             if an error occurred
//	 * @throws IOException
//	 *             if an error occurred
//	 */
//	public void doPost(HttpServletRequest request, HttpServletResponse response)
//			throws ServletException, IOException {
//
//		response.setContentType("text/html;charset=utf-8");
//		/* 设置响应头允许ajax跨域访问 */
//		response.setHeader("Access-Control-Allow-Origin", "*");
//		/* 星号表示所有的异域请求都可以接受， */
//		response.setHeader("Access-Control-Allow-Methods", "GET,POST");
//		String userphone = request.getParameter("userphone");
//		String usercontens = request.getParameter("usercontens");
//		usercontens = new String(usercontens.getBytes("iso8859-1"), "utf-8");
//		String txtpath = request.getRealPath("/txt/dictionary.txt");
//		String openid = request.getParameter("openid");
//		String voicename = request.getParameter("voicename");
//		int charnum = AddServlet.getContentWordCount(usercontens);
//		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
//        String t=df.format(new Date());// new Date()为获取当前系统时间
//		System.out.print(userphone + usercontens);
//		if (userphone == null || userphone.equals("") || usercontens.equals("")
//				|| userphone.equals("undefined")
//				|| usercontens.equals("undefined")) {
//			Writer out = response.getWriter();
//			String status = "内容有误或空！";
//			out.write(status);
//			out.flush();
//		} else {
//
//			int i = 0;
//			String uid = DBManager.showuid(openid);
//			if (BadWordUtil2.BadWordNum(usercontens, txtpath) == 0) {
//				i += DBManager.addcontents(openid, usercontens, userphone, t, uid);
//			}
//			// System.out.print(DBManager.addcontents(nickname, usercontens,
//			// userphone));
//			if (i == 1) {
//
//				int dollar = Integer.parseInt(DBManager
//						.showdollarbyopenid(openid));
//				// System.out.print(dollar-1);
//
//				if (dollar > 3 + charnum / 60 || dollar == 3 + charnum / 60) {
//					DBManager.updateDollarByopenid(openid, dollar - 3 - charnum
//							/ 60);
//
//					boolean sendstatus = SMS.execute(userphone, usercontens,
//							voicename);
//					if (sendstatus) {
//						// 返回值给微信小程序
//						Writer out = response.getWriter();
//						String status = "恭喜传音成功！";
//						out.write(status);
//						out.flush();
//						if (DBManager.showuid("wxstar", uid).equals("sucess")) {
//							int num = Integer.parseInt(DBManager
//									.showstarssignsnum("wxstar", uid, "stars")) + 3;
//							//System.out.print(num);
//							DBManager.updatestarsignByuid("wxstar", "stars",
//									uid, num);
//						} else {
//							DBManager.addstarsigns("wxstar", "stars", uid, 3);
//						}
//					}
//					if (!sendstatus) {
//						Writer out = response.getWriter();
//						String status = "传音失败！";
//						out.write(status);
//						out.flush();
//					}
//				}
//				if (dollar > 0 && dollar < 3 + charnum / 60) {
//					// 返回值给微信小程序
//					Writer out = response.getWriter();
//					String status = "字符超出余量！";
//					out.write(status);
//					out.flush();
//				}
//
//			} else if (BadWordUtil2.BadWordNum(usercontens, txtpath) != 0) {
//				Writer out = response.getWriter();
//				String status = "存在敏感词汇！";
//				out.write(status);
//				out.flush();
//			}
//		}
//
//	}
//
//	/**
//	 * Initialization of the servlet. <br>
//	 * 
//	 * @throws ServletException
//	 *             if an error occurs
//	 */
//	public void init() throws ServletException {
//		// Put your code here
//	}
//
//}
