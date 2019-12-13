package com.jueye.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.CharArrayWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Scanner;

public class ModifyFileDemo {
	// 按行读取文本内容
	public static String TXTRead(String filename) {
		StringBuilder sb = new StringBuilder();
		String str;
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(filename));
			String data = br.readLine();// 一次读入一行，直到读入null为文件结束
			while ((str = br.readLine()) != null) // 判断最后一行不存在，为空结束循环
			{
				// System.out.println(str);//原样输出读到的内容
				sb.append(str);
				// System.out.print(str);
				sb.append("\r\n");
			}
			br.close();
			System.out.println(sb.toString());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	// 替换文本中特定字符
	public static String TXTReplaceChar(String srcStr, String replaceStr,
			String filename) throws IOException {
		// 读
		File file = new File(filename);
		FileReader in = new FileReader(file);
		BufferedReader bufIn = new BufferedReader(in);
		// 内存流, 作为临时流
		CharArrayWriter tempStream = new CharArrayWriter();
		// 替换
		String line = null;
		while ((line = bufIn.readLine()) != null) {
			// 替换每行中, 符合条件的字符串
			line = line.replaceAll(srcStr, replaceStr);
			// 将该行写入内存
			tempStream.write(line);
			// 添加换行符
			tempStream.append(System.getProperty("line.separator"));
		}
		// 关闭 输入流
		bufIn.close();
		// 将内存中的流 写入 文件
		FileWriter out = new FileWriter(file);
		tempStream.writeTo(out);
		out.close();
		return null;
	}

	// 删除文本中空白行
	public static String DelBlackLine(String filename, String mfilename) {
		String line = null;
		int i = 0;
		File file = new File(filename);// 用命令行参数直接写入待处理文件
		File file1 = new File(mfilename);
		// 判断输入的是否是TXT文档，不是则提示退出
		if (filename.endsWith("txt") & mfilename.endsWith("txt")) {
			// 判断输入的文档是否存在，不存在则提示退出
			if (!file.exists()) {
				System.out.println("文件不存在！");
				System.exit(0);
			}
			// 输入的是TXT文档则继续往下执行
			try {
				Runtime.getRuntime().exec("notepad " + filename);// 打开待处理文件
				// 读出文档数据流方式
				InputStreamReader Stream = new InputStreamReader(
						new FileInputStream(file), "UTF-8");// 读入数据流方式设为‘UTF-8’，避免乱码
				// 构造一个字符流的缓存器，存放在控制台输入的字节转换后成的字符
				BufferedReader reader = new BufferedReader(Stream);
				// 写入数据流方式
				OutputStreamWriter outStream = new OutputStreamWriter(
						new FileOutputStream(file1), "UTF-8");
				BufferedWriter writer = new BufferedWriter(outStream);
				// 以行读出文档内容至结束
				while ((line = reader.readLine()) != null) {
					if (line.equals(""))// 判断是否是空行
					{
						continue;// 是空行则不进行操作
					} else {
						i++;
						writer.write("[" + i + "]");// 可在文档中标出行号
						writer.write(line + "\r\n");// 将非空白行写入新文档
					}
				}
				// 关闭数据流
				writer.close();
				reader.close();
				System.out.println("修改完成！");
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				// 打开修改后的文档
				Runtime.getRuntime().exec("notepad " + mfilename);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("输入错误！（非.txt）");
			System.exit(0);// 退出程序
		}

		return null;

	}

	// 刪除一行括号内的内容
	public static String DelBracketsStringsFirst(String str) {
		int head = str.indexOf('('); // 标记第一个使用左括号的位置
		if (head == -1); // 如果str中不存在括号，什么也不做，直接跑到函数底端返回初值str
		else {
			int next = head + 1; // 从head+1起检查每个字符
			int count = 1; // 记录括号情况
			do {
				if (str.charAt(next) == '(')
					count++;
				else if (str.charAt(next) == ')')
					count--;
				next++; // 更新即将读取的下一个字符的位置
				if (count == 0) // 已经找到匹配的括号
				{
					String temp = str.substring(head, next); // 将两括号之间的内容及括号提取到temp中
					str = str.replace(temp, ""); // 用空内容替换，复制给str
					head = str.indexOf('('); // 找寻下一个左括号
					next = head + 1; // 标记下一个左括号后的字符位置
					count = 1; // count的值还原成1
				}
			} while (head != -1); // 如果在该段落中找不到左括号了，就终止循环
		}
		return str; // 返回更新后的str
	}

	public static String DelBracketsStrings(String filename, String mfilename)
			throws FileNotFoundException {
		Scanner in = new Scanner(new File(filename));
		// 读取该地址下的input.txt文件
		PrintWriter out = new PrintWriter(new File(mfilename));
		// 将处理后的文件output.txt创建到该地址下
		while (in.hasNext()) {
			String str = in.nextLine();
			// 按行读取，遇到换行符停止。将读取到的内容赋值到str中
			str = str.replace('（', '(');
			str = str.replace('）', ')');
			// 考虑到某些文献中输入法混用，统一将中文输入法下的括号--‘（’、‘）’替换成英文输入法下的括号--‘(’、‘)’，便于之后的处理
			out.write(DelBracketsStringsFirst(str) + "\r\n");
			// 写出文本并换行
		}
		out.close(); // 关闭写入的文本

		return null;
	}

	// 测试实例
	public static void main(String[] args) throws Exception {
		String srcStr = ",";
		String replaceStr = "";
		String filename = "src/1.txt";
		String mfilename = "src/2.txt";
		// TXTReplaceChar(srcStr, replaceStr, filename);
		//TXTRead(filename);
		// DelBlackLine(filename,mfilename);
		DelBracketsStrings(filename,mfilename);
	}

}