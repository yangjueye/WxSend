package com.jueye.ocr;

import java.util.HashMap;

import org.json.JSONObject;

import com.baidu.aip.bodyanalysis.AipBodyAnalysis;

public class HumanNumber {

	/**
	 * @param args
	 */
	 public static final String APP_ID = "16305352";
	  public static final String API_KEY = "ipF1SlbFzaVS8yYjtVI22LNa";
	    public static final String SECRET_KEY = "Lh2uHQtutWnyVAhefmYgpGv5CcuVfdga";

	    public static JSONObject humannumber(String path) {
	        // 初始化一个AipBodyAnalysis
	        AipBodyAnalysis client = new AipBodyAnalysis(APP_ID, API_KEY, SECRET_KEY);

	        // 可选：设置网络连接参数
	        client.setConnectionTimeoutInMillis(2000);
	        client.setSocketTimeoutInMillis(60000);

	        // 可选：设置代理服务器地址, http和socket二选一，或者均不设置
//	        client.setHttpProxy("proxy_host", proxy_port);  // 设置http代理
//	        client.setSocketProxy("proxy_host", proxy_port);  // 设置socket代理

	        // 调用接口
	        HashMap<String, String> options = new HashMap<String, String>();
	     //   options.put("area", "0,0,100,100,200,200");//特定框选区域坐标，逗号分隔，如‘x1,y1,x2,y2,x3,y3...xn,yn'，默认尾点和首点相连做闭合，此参数为空或无此参数默认识别整个图片的人数
	     //   options.put("show", "true");
	     
	    //    String path = "E:/Program Files/myeclipse10/WorkSpace/WxTravel/WebRoot/ocrimages/humannum.jpg";
	        //JSONObject res = client.bodyAnalysis(path, new HashMap<String, String>());//人体关键点
	        JSONObject res =client.bodyNum(path, options);
	        return res;
	       // System.out.println(res.toString(2));
}
}