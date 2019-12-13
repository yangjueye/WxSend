package com.jueye.wx;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.json.JSONObject;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.jueye.ocr.Animal;
import com.jueye.ocr.AuthService;
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



public class chooseocrimg extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public chooseocrimg() {
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
	public String dates(){
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String dateString = formatter.format(currentTime);
        return dateString;
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
	public void doPost(HttpServletRequest request, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.setContentType("application/json;charset=utf-8");//指定返回的格式为JSON格式
		request.setCharacterEncoding("UTF-8");
	  //允许跨域访问
        //设置各种过滤器
        request.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        /*String path = request.getRealPath("/ocrchooseimgs") + "/";  
	        File dir = new File(path);  
	        if (!dir.exists()) {  
	            dir.mkdir();  
	        }  
	     
	  
	   
	        //获得磁盘文件条目工厂  
	        DiskFileItemFactory factory = new DiskFileItemFactory();  
	  
	        //如果没以下两行设置的话,上传大的文件会占用很多内存，  
	        //设置暂时存放的存储室,这个存储室可以和最终存储文件的目录不同  
	        *//** 
	         * 原理: 它是先存到暂时存储室，然后再真正写到对应目录的硬盘上， 
	         * 按理来说当上传一个文件时，其实是上传了两份，第一个是以 .tem 格式的 
	         * 然后再将其真正写到对应目录的硬盘上 
	         *//*  
	        factory.setRepository(dir);  
	        //设置缓存的大小，当上传文件的容量超过该缓存时，直接放到暂时存储室  
	        factory.setSizeThreshold(1024*1024);  
	        //高水平的API文件上传处理  
	        ServletFileUpload upload = new ServletFileUpload(factory);  
	        try {  
	            List<FileItem> list = upload.parseRequest(request);  
	            FileItem picture = null;  
	            for (FileItem item : list) {  
	                //获取表单的属性名字  
	                String name = item.getFieldName();  
	                //如果获取的表单信息是普通的 文本 信息  
	                if (item.isFormField()) {  
	                    //获取用户具体输入的字符串  
	                    String value = item.getString();  
	                    request.setAttribute(name, value);  
	                 
	                } else {  
	                    picture = item;  
	                }  
	            }  
	  
	            //自定义上传图片的名字为userId.jpg  
	            String fileName = (String) request.getAttribute("filename") ;  
	            System.out.print("fileName:"+fileName);
	            String destPath = path + fileName;  
	            System.out.print("destPath:"+destPath);
	          String ocrtype = (String) request.getAttribute("ocrtype") ;  
	          String ip= (String) request.getAttribute("ip") ;  
	            String paths = path.replaceAll("\\\\", "/")+fileName;
	            System.out.print(paths);
	            //真正写到磁盘上  
	            File file = new File(destPath);  
	            OutputStream outs = new FileOutputStream(file);  
	            InputStream in = picture.getInputStream();  
	            int length = 0;  
	            byte[] buf = new byte[1024];  
	            // in.read(buf) 每次读到的数据存放在buf 数组中  
	            while ((length = in.read(buf)) != -1) {  
	                //在buf数组中取出数据写到（输出流）磁盘上  
	                outs.write(buf, 0, length);  
	            }  
	            in.close();  
	            outs.close();  
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
	            
	        } catch (FileUploadException e1) {  
	            
	        } catch (Exception e) {  
	          
	        }  */
		  String ip="";
	        String ocrtype="";
		 String savePath= request.getRealPath("/ocrchooseimgs") + "/";  
		
		File file = new File(savePath);
		//判断上传文件的保存目录是否存在
		if (!file.exists() && !file.isDirectory()) {
			System.out.println(savePath+"目录不存在，需要创建");
			//创建目录
			file.mkdir();
		}
		//消息提示
		String message = "";
		try{
			//使用Apache文件上传组件处理文件上传步骤：
			//1、创建一个DiskFileItemFactory工厂
			DiskFileItemFactory factory = new DiskFileItemFactory();
			//2、创建一个文件上传解析器
			ServletFileUpload upload = new ServletFileUpload(factory);
			//解决上传文件名的中文乱码
			upload.setHeaderEncoding("UTF-8"); 
			//3、判断提交上来的数据是否是上传表单的数据
			if(!ServletFileUpload.isMultipartContent(request)){
				//按照传统方式获取数据
				System.out.println("没有文件上传");
			
			}
           //4、使用ServletFileUpload解析器解析上传数据，解析结果返回的是一个List<FileItem>集合，每一个FileItem对应一个Form表单的输入项
           List<FileItem> list = upload.parseRequest(request);
           for(FileItem item : list){
               //如果fileitem中封装的是普通输入项的数据
               if(item.isFormField()){
                   String name = item.getFieldName();
                   //解决普通输入项的数据的中文乱码问题
		          String value = item.getString("UTF-8");
		          //value = new String(value.getBytes("iso8859-1"),"UTF-8");
		          if(name.equals("ip")){
                  	ip+=value;
                  }
                  if(name.equals("ocrtype")){
                  	ocrtype+=value;
                  }
		          System.out.println(name + "=" + value);
		      }else{//如果fileitem中封装的是上传文件
                   //得到上传的文件名称，
                   String filename = item.getName();
                  
                   System.out.println(filename);
                   if(filename==null || filename.trim().equals("")){
                       continue;
                   }
		          //注意：不同的浏览器提交的文件名是不一样的，有些浏览器提交上来的文件名是带有路径的，如：  c:\a\b\1.txt，而有些只是单纯的文件名，如：1.txt
		          //处理获取到的上传文件的文件名的路径部分，只保留文件名部分
		          filename = filename.substring(filename.lastIndexOf("\\")+1);
		          //获取item中的上传文件的输入流
		          InputStream in = item.getInputStream();
                   //创建一个文件输出流
                   FileOutputStream fout = new FileOutputStream(savePath + "\\" + filename);
                   //创建一个缓冲区
                   byte buffer[] = new byte[1024];
                   //判断输入流中的数据是否已经读完的标识
		          int len = 0;
		          //循环将输入流读入到缓冲区当中，(len=in.read(buffer))>0就表示in里面还有数据
		          while((len=in.read(buffer))>0){
		              //使用FileOutputStream输出流将缓冲区的数据写入到指定的目录(savePath + "\\" + filename)当中
		              fout.write(buffer, 0, len);
		          }
                   //关闭输入流
                   in.close();
                   //关闭输出流
                   fout.close();
		          //删除处理文件上传时生成的临时文件
		          item.delete();
		          message = "文件上传成功！";
		          String paths= savePath.replaceAll("\\\\", "/")+filename;
	               //人体分析
	               String accessToken=AuthService.getAuth();
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
       }catch (Exception e) {
		  message= "文件上传失败！";
           e.printStackTrace();
           
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
