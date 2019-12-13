package com.jueye.ocr;

import java.util.HashMap;

import org.json.JSONObject;

import com.baidu.aip.imageclassify.AipImageClassify;

public class LandMark {

	/**
	 * @param args
	 * 地标识别 请求参数详情

参数名称	是否必选	类型	说明
image	是	mixed	本地图片路径或者图片二进制数据
地标识别 返回数据参数详情

参数	类型	是否必须	说明	示例
log_id	number	是	唯一的log id，用于问题定位	507499361
result	object	是	裁剪结果	-
+landmark	string	是	地标名称，无法识别则返回空字符串	狮身人面像
	 */
	public static final String APP_ID = "16310393";
    public static final String API_KEY = "xMBEx5iP1dzAOOY7vk2wUC0b";
    public static final String SECRET_KEY = "gAqpp7UCi7MkGKij0EoZozyFvXf7UXNA";
    public static JSONObject landmark(String path){
	// TODO Auto-generated method stub
    	AipImageClassify client = new AipImageClassify(APP_ID, API_KEY, SECRET_KEY);

        // 可选：设置网络连接参数
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);
	 // 传入可选参数调用接口
    HashMap<String, String> options = new HashMap<String, String>();
    // 参数为本地路径
  //  String path = "E:/Program Files/myeclipse10/WorkSpace/WxTravel/WebRoot/ocrimages/landmark.jpg";
    JSONObject res = client.landmark(path, options); 
   // System.out.println(res.toString(2));
    return res;
    }

}
