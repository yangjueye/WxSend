package com.jueye.test;

import java.util.concurrent.CountDownLatch;

public class MyThread implements Runnable {
    
    private String para1;
    private String para2;
    private String para3;
    private CountDownLatch countDownLatch;  //多线程结束后，执行后面的代码（计算时间、数量）
    
    public MyThread(String para1, String para2, String para3, CountDownLatch countDownLatch) {
        this.para1 = para1;
        this.para2 = para2;
        this.para3 = para3;
        this.countDownLatch = countDownLatch;
    }

    public void run() {
        
        try{            
            postRequest.postRequestTest(para1, para2, para3);          
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            countDownLatch.countDown();
        }
    }
    

}