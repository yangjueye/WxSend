package com.jueye.ocr;

import java.util.*;

import com.alibaba.fastjson.JSONObject;




/**
* 人脸融合
*/
public class FaceMerge {

    /**
    * 重要提示代码中所需工具类
    * FileUtil,Base64Util,HttpUtil,GsonUtils请从
    * https://ai.baidu.com/file/658A35ABAB2D404FBF903F64D47C1F72
    * https://ai.baidu.com/file/C8D81F3301E24D2892968F09AE1AD6E2
    * https://ai.baidu.com/file/544D677F5D4E4F17B4122FBD60DB82B3
    * https://ai.baidu.com/file/470B3ACCA3FE43788B5A963BF0B625F3
    * 下载
    */
    public static String faceMerge() {
        // 请求url
        String url = "https://aip.baidubce.com/rest/2.0/face/v1/merge";
        try {
            Map<String, Object> map = new HashMap<String, Object>();
            
            Map<String, Object> image_templateMap = new HashMap<String, Object>();
            image_templateMap.put("image", Base64Test.GetImageStrFromUrl("https://jueyevip.top/wo.jpg"));
            image_templateMap.put("image_type", "BASE64");
            image_templateMap.put("quality_control", "NONE");
            map.put("image_template", image_templateMap);
            
            Map<String, Object> image_targetMap = new HashMap<String, Object>();
            image_targetMap.put("image", Base64Test.GetImageStrFromUrl("https://jueyevip.top/ljj.jpg"));
            image_targetMap.put("image_type", "BASE64");
            image_targetMap.put("quality_control", "NONE");
            map.put("image_target", image_targetMap);

            String param = GsonUtils.toJson(map);

            // 注意这里仅为了简化编码每一次请求都去获取access_token，线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。
            String accessToken =AllAccessToken.getAuth();

            String result = HttpUtil.post(url, accessToken, "application/json", param);
            //alibaba fastjson支持String和json格式数据互转
    		JSONObject json;
    		json = JSONObject.parseObject(result).getJSONObject("result");
    		System.out.println(json.get("merge_image"));
    		 Base64Test.GenerateImage(json.get("merge_image").toString(),"D:/123.jpg");
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        FaceMerge.faceMerge();
       
    }
}