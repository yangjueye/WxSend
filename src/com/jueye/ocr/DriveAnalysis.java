package com.jueye.ocr;

import java.util.HashMap;

import org.json.JSONObject;

import com.baidu.aip.bodyanalysis.AipBodyAnalysis;

public class DriveAnalysis {

	/**
	 * @param args
	 * 驾驶行为分析 请求参数详情

参数名称	是否必选	类型	说明
image	是	mixed	本地图片路径或者图片二进制数据
type	否	String	smoke,cellphone,
not_buckling_up,
both_hands_leaving_wheel,
not_facing_front	识别的属性行为类别，英文逗号分隔，默认所有属性都识别；
smoke //吸烟，
cellphone //打手机 ，
not_buckling_up // 未系安全带，
both_hands_leaving_wheel // 双手离开方向盘，
not_facing_front // 视角未看前方
驾驶行为分析 返回数据参数详情

字段	是否必选	类型	说明
person_num	是	uint64	识别出的驾驶员检测框数目，0或者1，0代表未监测到驾驶员
person_info	是	object[]	驾驶员的属性行为信息；若未检测到驾驶员，则该项为[]
+location	否	object	检测出驾驶员的位置
++left	否	int	检测区域在原图的左起开始位置
++top	否	int	检测区域在原图的上起开始位置
++width	否	int	检测区域宽度
++height	否	int	检测区域高度
+attributes	否	object	驾驶员属性行为内容
++smoke	否	object	吸烟
+++score	否	float	对应概率分数
+++threshold	否	float	建议阈值，仅作为参考，实际应用中根据测试情况选取合适的score阈值即可
++cellphone	否	object	使用手机
+++score	否	float	对应概率分数
+++threshold	否	float	建议阈值，仅作为参考，实际应用中根据测试情况选取合适的score阈值即可
++not_buckling_up	否	object	未系安全带
+++score	否	float	对应概率分数
+++threshold	否	float	建议阈值，仅作为参考，实际应用中根据测试情况选取合适的score阈值即可
++both_hands_leaving_wheel	否	object	双手离开方向盘
+++score	否	float	对应概率分数
+++threshold	否	float	建议阈值，仅作为参考，实际应用中根据测试情况选取合适的score阈值即可
++not_facing_front	否	object	视角未朝前方
+++score	否	float	对应概率分数
+++threshold	否	float	建议阈值，仅作为参考，实际应用中根据测试情况选取合适的score阈值即可
	 */
	public static final String APP_ID = "16305352";
    public static final String API_KEY = "ipF1SlbFzaVS8yYjtVI22LNa";
    public static final String SECRET_KEY = "Lh2uHQtutWnyVAhefmYgpGv5CcuVfdga";
    
    public static JSONObject driveanalysis(String path) {
	// TODO Auto-generated method stub
	 AipBodyAnalysis client = new AipBodyAnalysis(APP_ID, API_KEY, SECRET_KEY);

        // 可选：设置网络连接参数
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);
	 // 传入可选参数调用接口
    HashMap<String, String> options = new HashMap<String, String>();
 //   options.put("type", "smoke");//识别的属性行为类别，英文逗号分隔，默认所有属性都识别； smoke //吸烟，cellphone //打手机 ，not_buckling_up // 未系安全带，  both_hands_leaving_wheel // 双手离开方向盘， not_facing_front // 视角未看前方


    // 参数为本地路径
   // String path = "E:/Program Files/myeclipse10/WorkSpace/WxTravel/WebRoot/ocrimages/driver.png";
    JSONObject res = client.driverBehavior(path, options); 
   // System.out.println(res.toString(2));
    return res;
}

}
