package com.jueye.ocr;

import java.util.HashMap;

import org.json.JSONObject;

import com.baidu.aip.bodyanalysis.AipBodyAnalysis;

public class PortraitSegmentation {

	/**
	 * @param args
	 */
	 public static final String APP_ID = "16305352";
	    public static final String API_KEY = "ipF1SlbFzaVS8yYjtVI22LNa";
	    public static final String SECRET_KEY = "Lh2uHQtutWnyVAhefmYgpGv5CcuVfdga";
	public static String portraitsegmentation(String path ) {
		// TODO Auto-generated method stub
		 AipBodyAnalysis client = new AipBodyAnalysis(APP_ID, API_KEY, SECRET_KEY);

	        // 可选：设置网络连接参数
	        client.setConnectionTimeoutInMillis(2000);
	        client.setSocketTimeoutInMillis(60000);
		 // 传入可选参数调用接口
	    HashMap<String, String> options = new HashMap<String, String>();
	 //   options.put("type", "labelmap");


	    // 参数为本地路径
	  //  String path = "E:/Program Files/myeclipse10/WorkSpace/WxTravel/WebRoot/ocrimages/123.png";
	    JSONObject res = client.bodySeg(path, options);
	    String getimage = res.get("foreground").toString();
	   Base64Test.GenerateImage(getimage,path);
	   return path;
	 //   System.out.println(res.toString());
	}

}
