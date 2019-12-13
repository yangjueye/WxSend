package com.jueye.ocr;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class Base64Test {
	 public static void main(String[] args) {
	       // String strImg = GetImageStr();
	      //  System.out.println(strImg);
//	        Base64Test.GenerateImage("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",
//	        		"D:/123.jpg");
		// System.out.print(Base64Test.GetImageStrFromUrl("https://jueyevip.top/ljj.jpg"));
	    }
	 /**
      * 将一张网络图片转化成Base64字符串
     * @param imgURL
     * @return
     */
    public static String GetImageStrFromUrl(String imgURL) {  
     	ByteArrayOutputStream data = new ByteArrayOutputStream();
         try {
             // 创建URL
             URL url = new URL(imgURL);
             byte[] by = new byte[1024];
             // 创建链接
             HttpURLConnection conn = (HttpURLConnection) url.openConnection();
             conn.setRequestMethod("GET");
             conn.setConnectTimeout(5000);
             InputStream is = conn.getInputStream();
             // 将内容读取内存中
             int len = -1;
             while ((len = is.read(by)) != -1) {
                 data.write(by, 0, len);
             }
             // 关闭流
             is.close();
         } catch (IOException e) {
             e.printStackTrace();
         }
         // 对字节数组Base64编码
         BASE64Encoder encoder = new BASE64Encoder();
 		return encoder.encode(data.toByteArray());
     }
	    // 本地图片转化成base64字符串
	    public static String GetImageStr(String imgFilePath) {
	        // 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
	        String imgFile = imgFilePath;
	        InputStream in = null;
	        byte[] data = null;
	        // 读取图片字节数组
	        try {
	            in = new FileInputStream(imgFile);
	            data = new byte[in.available()];
	            in.read(data);
	            in.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        // 对字节数组Base64编码
	        BASE64Encoder encoder = new BASE64Encoder();
	        // 返回Base64编码过的字节数组字符串
	        return encoder.encode(data);
	    }

	    // base64字符串转化成图片
	    public static boolean GenerateImage(String imgStr, String imgFilePath) {
	        // 对字节数组字符串进行Base64解码并生成图片
	        if (imgStr == null)
	            return false;
	        BASE64Decoder decoder = new BASE64Decoder();
	        try {
	            // Base64解码
	            byte[] b = decoder.decodeBuffer(imgStr);
	            for (int i = 0; i < b.length; ++i) {
	                if (b[i] < 0) {// 调整异常数据
	                    b[i] += 256;
	                }
	            }
	            // 生成新的图片
	           
	            OutputStream out = new FileOutputStream(imgFilePath);
	            out.write(b);
	            out.flush();
	            out.close();
	            return true;
	        } catch (Exception e) {
	            return false;
	        }
	    }

}
