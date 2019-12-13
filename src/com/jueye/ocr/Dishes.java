package com.jueye.ocr;

import java.util.HashMap;

import org.json.JSONObject;

import com.baidu.aip.imageclassify.AipImageClassify;

public class Dishes {

	/**
	 * @param args
	 * 菜品识别 请求参数详情

参数名称	是否必选	类型	默认值	说明
image	是	mixed		本地图片路径或者图片二进制数据
top_num	否	String		返回预测得分top结果数，默认为5
filter_threshold	否	String		默认0.95，可以通过该参数调节识别效果，降低非菜识别率.
baike_num	否	String	0	返回百科信息的结果数，默认不返回
菜品识别 返回数据参数详情

字段	是否必选	类型	说明
log_id	是	uint64	唯一的log id，用于问题定位
result_num	否	unit32	返回结果数目，及result数组中的元素个数
result	否	array()	菜品识别结果数组
+name	否	string	菜名，示例：鱼香肉丝
+calorie	否	float	卡路里，每100g的卡路里含量
+probability	否	float	识别结果中每一行的置信度值，0-1
+baike_info	object	否	对应识别结果的百科词条名称
++baike_url	string	否	对应识别结果百度百科页面链接
++image_url	string	否	对应识别结果百科图片链接
++description	string	否	对应识别结果百科内容描述
	 */
	public static final String APP_ID = "16310393";
    public static final String API_KEY = "xMBEx5iP1dzAOOY7vk2wUC0b";
    public static final String SECRET_KEY = "gAqpp7UCi7MkGKij0EoZozyFvXf7UXNA";
    public static JSONObject dishes(String path) {
	// TODO Auto-generated method stub
    	AipImageClassify client = new AipImageClassify(APP_ID, API_KEY, SECRET_KEY);

        // 可选：设置网络连接参数
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);
	 // 传入可选参数调用接口
    HashMap<String, String> options = new HashMap<String, String>();
    options.put("top_num", "5");
    options.put("filter_threshold", "0.7");
    options.put("baike_num", "5");

    // 参数为本地路径
   // String path = "E:/Program Files/myeclipse10/WorkSpace/WxTravel/WebRoot/ocrimages/dishes.jpg";
    JSONObject res = client.dishDetect(path, options); 
  //  System.out.println(res.toString(2));
    return res;
    }
}
