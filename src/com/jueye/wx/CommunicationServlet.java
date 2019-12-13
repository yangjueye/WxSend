package com.jueye.wx;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dao.db.DBManager;

import net.sf.json.JSONArray;

public class CommunicationServlet extends HttpServlet {
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html;charset=utf-8");
		/* 设置响应头允许ajax跨域访问 */
		response.setHeader("Access-Control-Allow-Origin", "*");
		/* 星号表示所有的异域请求都可以接受， */
		response.setHeader("Access-Control-Allow-Methods", "GET,POST");
		String openid = request.getParameter("openid");
		//System.out.print(openid);
		boolean boolopenid = DBManager.showrepeat("wxadressbook", openid, "openid");
		//System.out.print(boolid);
		if (!boolopenid) {
			String pname = DBManager.showvaluleByOpenid(openid, "wxadressbook",
					"wxtext");
			//System.out.print(pname);
			String phone = DBManager.showvaluleByOpenid(openid, "wxadressbook",
					"phone");
			//System.out.print(phone);
			Writer out = response.getWriter();
			List<Communication> list = new ArrayList<Communication>();
			Communication comm1 = new Communication(1, pname,phone,"../../images/1.png");
			list.add(comm1);
			JSONArray jsonArray = JSONArray.fromObject(list);
			//System.out.println(jsonArray);
			out.write(JSONArray.fromObject(list).toString());
			out.flush();
		} else {
			Writer out = response.getWriter();
			out.write("");
			out.flush();
		}
	}
}
