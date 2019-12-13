package com.jueye.ocr;


import java.net.URLEncoder;

import com.baidu.aip.util.Base64Util;

public class FruitVegetable {

	/**
	 * @param args
	 */
	 /**
	    * 重要提示代码中所需工具类
	    * FileUtil,Base64Util,HttpUtil,GsonUtils请从
	    * https://ai.baidu.com/file/658A35ABAB2D404FBF903F64D47C1F72
	    * https://ai.baidu.com/file/C8D81F3301E24D2892968F09AE1AD6E2
	    * https://ai.baidu.com/file/544D677F5D4E4F17B4122FBD60DB82B3
	    * https://ai.baidu.com/file/470B3ACCA3FE43788B5A963BF0B625F3
	    * 下载
	    */
	    public static String fruitvegetable(String filePath, String accessToken) {
	        // 请求url
	        String url = "https://aip.baidubce.com/rest/2.0/image-classify/v1/classify/ingredient";
	        try {
	            // 本地文件路径
	           
	            byte[] imgData = FileUtil.readFileByBytes(filePath);
	            String imgStr = Base64Util.encode(imgData);
	            String imgParam = URLEncoder.encode(imgStr, "UTF-8");

	            String param = "image=" + imgParam;

	            // 注意这里仅为了简化编码每一次请求都去获取access_token，线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。
	           

	            String result = HttpUtil.post(url, accessToken, param);
	            System.out.println(result);
	            return result;
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return null;
	    }

	    public static void main(String[] args) {
	    	FruitVegetable.fruitvegetable("E:/Program Files/myeclipse10/WorkSpace/WxTravel/WebRoot/ocrimages/fruitvegetable.png","24.1bbf2f02ea0b2b93c11b45120eb90487.2592000.1561035532.282335-16310393");
	    }

}
