package com.jueye.ocr;

import java.util.HashMap;

import org.json.JSONObject;

import com.baidu.aip.imageclassify.AipImageClassify;

public class Plant {

	/**
	 * @param args
	 * 植物识别 请求参数详情

参数名称	是否必选	类型	默认值	说明
image	是	mixed		本地图片路径或者图片二进制数据
baike_num	否	String	0	返回百科信息的结果数，默认不返回
植物识别 返回数据参数详情

参数	类型	是否必须	说明
log_id	uint64	是	唯一的log id，用于问题定位
result	arrry(object)	是	植物识别结果数组
+name	string	是	植物名称，示例：吉娃莲
+score	uint32	是	置信度，示例：0.5321
+baike_info	object	否	对应识别结果的百科词条名称
++baike_url	string	否	对应识别结果百度百科页面链接
++image_url	string	否	对应识别结果百科图片链接
++description	string	否	对应识别结果百科内容描述
	 */
	public static final String APP_ID = "16310393";
    public static final String API_KEY = "xMBEx5iP1dzAOOY7vk2wUC0b";
    public static final String SECRET_KEY = "gAqpp7UCi7MkGKij0EoZozyFvXf7UXNA";
    public static JSONObject plant(String path) {
	// TODO Auto-generated method stub
    	AipImageClassify client = new AipImageClassify(APP_ID, API_KEY, SECRET_KEY);

        // 可选：设置网络连接参数
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);
	 // 传入可选参数调用接口
    HashMap<String, String> options = new HashMap<String, String>();
    options.put("baike_num", "5");

    // 参数为本地路径
 //   String path = "E:/Program Files/myeclipse10/WorkSpace/WxTravel/WebRoot/ocrimages/plant.jpg";
    JSONObject res = client.plantDetect(path, options); 
 //   System.out.println(res.toString(2));
    return res;
    }

}
