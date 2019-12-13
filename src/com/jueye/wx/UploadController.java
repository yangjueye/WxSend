package com.jueye.wx;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Writer;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.List;

import javax.mail.Multipart;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.json.JSONArray;
import org.json.JSONObject;


import com.baidu.aip.ocr.AipOcr;
import com.google.gson.JsonParser;
import com.jueye.ocr.Animal;
import com.jueye.ocr.CarType;
import com.jueye.ocr.Dishes;
import com.jueye.ocr.DriveAnalysis;
import com.jueye.ocr.FruitVegetable;
import com.jueye.ocr.GeneralObject;
import com.jueye.ocr.GoodsRatio;
import com.jueye.ocr.HumanAnalysis;
import com.jueye.ocr.HumanNumber;
import com.jueye.ocr.LandMark;
import com.jueye.ocr.Plant;
import com.jueye.ocr.PortraitSegmentation;
import com.jueye.ocr.RedWine;

public class UploadController extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public UploadController() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doGet(request, response);
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.setContentType("application/json;charset=utf-8");//指定返回的格式为JSON格式
	    req.setCharacterEncoding("UTF-8");
	  //允许跨域访问
        resp.setHeader("Access-Control-Allow-Origin","*");
        //设置各种过滤器
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
    //    resp.setContentType("UTF-8");
        String ip="";
        String ocrtype="";
        //输出请求的类型
        System.out.println(req.getContentType());
        //这一段是获取参数并且输出参数名和值，用的是常规方法
        Enumeration<String> parameterNames = req.getParameterNames();
      //  resp.getWriter().println("------这是parameter部分------");
        while (parameterNames.hasMoreElements()) {
            String name = parameterNames.nextElement();
            //resp.getWriter().println(name + "\t" + req.getParameter(name));
        }
        //处理form-data
        boolean isMultipart = ServletFileUpload.isMultipartContent(req);
        //首先要new一个专门生产FileItem的工厂，fileitem是通过调包解析请求数据后，数据存储的对象
        DiskFileItemFactory factory = new DiskFileItemFactory();
        //设置临时文件大小（有时候可能传的文件太大，需要使用临时文件）
        factory.setSizeThreshold(1024 * 1024);
        //设置临时文件存放的位置
        factory.setRepository(new File("testcore"));
        //工厂相关的参数设置完成后，以工厂为模版，new一个upload实例
        ServletFileUpload upload = new ServletFileUpload(factory);
        if (isMultipart) {
            try {
                //用upload实例解析request，并将解析的结果放在List<FileItem>里面
                //每一个FileItem实际就是form-data中的键值对
                List<FileItem> items = upload.parseRequest(req);
               // resp.getWriter().println("------这是form-data部分------");
                //遍历items
                for (FileItem item : items) {
                    //判断当前item是不是表单域（也就是表单域中的文字信息）
                    if (item.isFormField()) {
                        //获取这个键值对的键和值，并输出
                        String name = item.getFieldName();
                        String value = item.getString();
                        if(name.equals("openid")){
                        	System.out.print("openid="+value);
                        }
                        if(name.equals("ip")){
                        	ip+=value;
                        }
                        if(name.equals("ocrtype")){
                        	ocrtype+=value;
                        }
                       // resp.getWriter().println(name + "\t" + value);
                    } else {
                        //如果不是表单域，则说明这是个文件，获取文件的相关信息并输出
                        String name = item.getName();
                        String field = item.getFieldName();
                        long size = item.getSize();
                        String type = item.getContentType();
                        String url = name;
                       // resp.getWriter().println(field + "\t" + type + "\t" + size + "b\t" + url);
                      //  resp.getWriter().println(url);
//                        Writer out = resp.getWriter(); 
//                        out.write(url);
//                        out.flush(); 
                        //既然已经拿到文件了（文件在内存里），那么就把它写到硬盘里吧
                    //    File files = new File(getServletConfig().getServletContext().getRealPath("testcore"), name);
                        String path = req.getRealPath("/ocrimages") + "/";
                        File dir = new File(path);
                        if (!dir.exists()) {
                            dir.mkdir();
                        }
                        File file = new File(path, name);//"C:/Program Files/Apache Software Foundation/Tomcat 7.0/webapps/ROOT/say"
                        if (!file.getParentFile().exists()) {
                            if (file.getParentFile().mkdir())
                                //写入硬盘
                                item.write(file);
                            
                            
                        } else
                        
                            //写入硬盘
                            item.write(file);
                        //打印文件在硬盘里的绝对路径
                        String paths = path.replaceAll("\\\\", "/")+name;
//                        System.out.println(paths);
                     //   String number=OcrCar.CarColor(file.getAbsolutePath());
                  //     System.out.println(ip+"/WxSend/ocrimages/"+name);
         
                   //    String color=OcrCar.CarColor(ip+"/WxSend/ocrimages/"+name);
                        //人体分析
                        String accessToken="24.4bf52adddf1d5dec472d6761d153083a.2592000.1561098014.282335-16310393";
                        if(ocrtype.equals("humananalysis")){
                          JSONObject json = HumanAnalysis.humananalysis(paths);
                          PrintWriter writer = resp.getWriter();
                       
                          writer.write(json.toString(2));
                            } 
                        //人像分割
                        if(ocrtype.equals("portraitsegmentation")){
                        	String imageurl = PortraitSegmentation.portraitsegmentation(paths);
                        	 Writer out = resp.getWriter(); 
                             out.write(imageurl);
                             out.flush();
                              } 
                        //人流量统计
                        if(ocrtype.equals("humannumber")){
                        	  JSONObject json = HumanNumber.humannumber(paths);
                              PrintWriter writer = resp.getWriter();
                              writer.write(json.toString(2));
                              } 
                        //车型识别
                        if(ocrtype.equals("cartype")){
                        	  JSONObject json = CarType.cartype(paths);
                              PrintWriter writer = resp.getWriter();
                              writer.write(json.toString(2));
                              } 
                        //车牌号识别
                        if(ocrtype.equals("carnum")){
                        String number=OcrCar.CarNumber(paths);
                        String color = OcrCar.CarColor(paths);
                        Writer out = resp.getWriter(); 
                        out.write("车牌颜色："+color+"车牌号码："+number);
                        out.flush();
                        } 
                        //驾驶行为分析
                        if(ocrtype.equals("driveanalysis")){
                        	  JSONObject json = DriveAnalysis.driveanalysis(paths);
                              PrintWriter writer = resp.getWriter();
                              writer.write(json.toString(2));
                              } 
                        //通用物体和场景识别
                        if(ocrtype.equals("generalobject")){
                        	  JSONObject json = GeneralObject.generalobject(paths);
                              PrintWriter writer = resp.getWriter();
                              writer.write(json.toString(2));
                              } 

                        //地标识别
                        if(ocrtype.equals("landmark")){
                        	  JSONObject json = LandMark.landmark(paths);
                              PrintWriter writer = resp.getWriter();
                              writer.write(json.toString(2));
                              } 
                      //植物识别
                        if(ocrtype.equals("plant")){
                        	  JSONObject json = Plant.plant(paths);
                              PrintWriter writer = resp.getWriter();
                              writer.write(json.toString(2));
                              } 
                      //动物识别
                        if(ocrtype.equals("animal")){
                        	  JSONObject json = Animal.animal(paths);
                              PrintWriter writer = resp.getWriter();
                              writer.write(json.toString(2));
                              } 
                      //果蔬识别
                        if(ocrtype.equals("fruitvegetable")){
                        	  String json = FruitVegetable.fruitvegetable(paths, accessToken);
                              PrintWriter writer = resp.getWriter();
                              writer.write(json);
                              } 
                      //菜品识别
                        if(ocrtype.equals("dishes")){
                        	  JSONObject json = Dishes.dishes(paths);
                              PrintWriter writer = resp.getWriter();
                              writer.write(json.toString(2));
                              } 
                      //红酒识别
                        if(ocrtype.equals("redwine")){
                        	  String json = RedWine.redwine(paths, accessToken);
                              PrintWriter writer = resp.getWriter();
                              writer.write(json);
                              } 
                      //货币识别
                        if(ocrtype.equals("goodsratio")){
                        	  String json = GoodsRatio.goodsratio(paths, accessToken);
                              PrintWriter writer = resp.getWriter();
                              writer.write(json);
                              } 
               
                    }
                    
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
