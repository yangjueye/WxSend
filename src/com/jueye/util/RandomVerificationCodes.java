package com.jueye.util;

import java.util.Random;

public class RandomVerificationCodes {
public static String VerificationCodes(int num){
	String sources = "0123456789";
	Random rand = new Random();
	StringBuffer flag = new StringBuffer();
	for (int j = 0; j < num; j++) 
	{
		flag.append(sources.charAt(rand.nextInt(10)) + "");
	}
	String usercode=flag.toString();
	return (String) usercode;
}

public static void main(String[] args) {
	System.out.print("随机生成的验证码："+RandomVerificationCodes.VerificationCodes(6));
}
}
