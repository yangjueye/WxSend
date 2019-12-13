package com.jueye.wx;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.json.JSONArray;
import org.json.JSONObject;


import com.baidu.aip.ocr.AipOcr;
import com.google.gson.JsonParser;

public class OcrCar {
	 //设置APPID/AK/SK
    public static final String APP_ID = "16244827";
    public static final String API_KEY = "qEZgBj2UQQRpuj82EliPEbTC";
    public static final String SECRET_KEY = "jjubG8b542Wo36KeZlT7RIZagAwtaXQK";

    public static String CarColor(String imagePath) {
        // 初始化一个AipOcr
        AipOcr client = new AipOcr(APP_ID, API_KEY, SECRET_KEY);

        // 可选：设置网络连接参数
//        client.setConnectionTimeoutInMillis(2000);
//        client.setSocketTimeoutInMillis(60000);

        // 可选：设置代理服务器地址, http和socket二选一，或者均不设置
        //client.setHttpProxy("proxy_host", proxy_port);  // 设置http代理
        //client.setSocketProxy("proxy_host", proxy_port);  // 设置socket代理

        // 可选：设置log4j日志输出格式，若不设置，则使用默认配置
        // 也可以直接通过jvm启动参数设置此环境变量
        //System.setProperty("aip.log4j.conf", "path/to/your/log4j.properties");      
        HashMap<String, String> options = new HashMap<String, String>();
        options.put("multi_detect", "true");              
        // 参数为本地路径
        String image = imagePath;
        String color="";
        JSONObject res = client.plateLicense(image, options); 
        
       // JSONObject res = client.basicGeneralUrl(image, options); 
//        String jsonerror =  res.getString("error_msg");
//        System.out.print(jsonerror);
        
         	
          if(res.has("words_result")){
        	  JSONArray jsonArray=(JSONArray) res.getJSONArray("words_result");
              JSONObject jsonObject = jsonArray.getJSONObject(0);            
              color += (String) jsonObject.get("color");
        	  
         }
          else{
        	 
        	  color +="无法识别车牌颜色！";
          }
        /*{"words_result":[
        {"vertexes_location":[{"y":216,"x":215},{"y":217,"x":338},{"y":255,"x":338},{"y":254,"x":216}],
         "color":"blue",
         "probability":[1,0.9999991655349731,0.999997615814209,0.9999961853027344,0.9999855756759644,0.9999802112579346,0.9999747276306152],
         "number":"京KBT355"
        	 }
        ],
         "log_id":3562858472111175118}*/
        
         return color;
    }
    public static String CarNumber(String imagePath) {
        // 初始化一个AipOcr
        AipOcr client = new AipOcr(APP_ID, API_KEY, SECRET_KEY);

        // 可选：设置网络连接参数
//        client.setConnectionTimeoutInMillis(2000);
//        client.setSocketTimeoutInMillis(60000);
        String number="";
        // 可选：设置代理服务器地址, http和socket二选一，或者均不设置
        //client.setHttpProxy("proxy_host", proxy_port);  // 设置http代理
        //client.setSocketProxy("proxy_host", proxy_port);  // 设置socket代理

        // 可选：设置log4j日志输出格式，若不设置，则使用默认配置
        // 也可以直接通过jvm启动参数设置此环境变量
        //System.setProperty("aip.log4j.conf", "path/to/your/log4j.properties");      
        HashMap<String, String> options = new HashMap<String, String>();
        options.put("multi_detect", "true");              
        // 参数为本地路径
        String image = imagePath;
        //JSONObject res = client.basicGeneralUrl(image, options); 
        JSONObject res = client.plateLicense(image, options); 
        
    
       System.out.print(res);
       
        	
       if(res.has("words_result")){
    	   JSONArray jsonArray=(JSONArray) res.getJSONArray("words_result");
       	
           JSONObject jsonObject = jsonArray.getJSONObject(0);            
           number  += (String) jsonObject.get("number");
    	  
        }else{
        	
        	 number +="无法识别车牌照号码！";
        }
        /*{"words_result":[
        {"vertexes_location":[{"y":216,"x":215},{"y":217,"x":338},{"y":255,"x":338},{"y":254,"x":216}],
         "color":"blue",
         "probability":[1,0.9999991655349731,0.999997615814209,0.9999961853027344,0.9999855756759644,0.9999802112579346,0.9999747276306152],
         "number":"京KBT355"
        	 }
        ],
         "log_id":3562858472111175118}*/
        
         return number;
    }
    public static void main(String[] args) {
        // 初始化一个AipOcr
       /* AipOcr client = new AipOcr(APP_ID, API_KEY, SECRET_KEY);

        // 可选：设置网络连接参数
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);
        HashMap<String, String> options = new HashMap<String, String>();
        options.put("multi_detect", "true");        
       */
        // 调用接口
//        String path = "E:/Program Files/myeclipse10/WorkSpace/WxSend/WebRoot/ocrimages/wxe800cf885f543230.o6zAJs_xsoC-FZiKeh8RVJEc5as0.n0IfM19Opywsc2031438e41e9f582fb2976b4084709e.png";
//        JSONObject res = client.basicGeneral(path, new HashMap<String, String>());
//        JSONObject res = client.basicGeneralUrl("http://aip.bdstatic.com/portal/dist/1557456913771/ai_images/technology/ocr-plate/plate/demo-card-1.jpg", options); 
        System.out.println(OcrCar.CarNumber("E:/Program Files/myeclipse10/WorkSpace/WxSend/WebRoot/ocrimages/wxe800cf885f543230.o6zAJs_xsoC-FZiKeh8RVJEc5as0.n0IfM19Opywsc2031438e41e9f582fb2976b4084709e.png"));
        
    }
}
