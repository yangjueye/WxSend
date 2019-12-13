package com.jueye.ocr;

import java.util.HashMap;

import org.json.JSONObject;

import com.baidu.aip.bodyanalysis.AipBodyAnalysis;

public class HumanAnalysis {

	/**
	 * @param args
	 */
	 public static final String APP_ID = "16305352";
	    public static final String API_KEY = "ipF1SlbFzaVS8yYjtVI22LNa";
	    public static final String SECRET_KEY = "Lh2uHQtutWnyVAhefmYgpGv5CcuVfdga";

	    public static JSONObject humananalysis(String path){
	        // 初始化一个AipBodyAnalysis
	        AipBodyAnalysis client = new AipBodyAnalysis(APP_ID, API_KEY, SECRET_KEY);

	        // 可选：设置网络连接参数
	        client.setConnectionTimeoutInMillis(2000);
	        client.setSocketTimeoutInMillis(60000);

	        // 可选：设置代理服务器地址, http和socket二选一，或者均不设置
//	        client.setHttpProxy("proxy_host", proxy_port);  // 设置http代理
//	        client.setSocketProxy("proxy_host", proxy_port);  // 设置socket代理

	        // 调用接口
	      //  String path = "E:/Program Files/myeclipse10/WorkSpace/WxTravel/WebRoot/ocrimages/humananalysis.png";
	        //JSONObject res = client.bodyAnalysis(path, new HashMap<String, String>());//人体关键点
	        JSONObject res =client.bodyAttr(path, new HashMap<String, String>());
	      //  System.out.println(res.toString(2));
	        return res;
	    }
	    public static void main(String[] args) {
	    	
	    }
}
