package com.spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;


public class Application {
	private  static ApplicationContext applicationContext;
	public static String fileConfigPath = "";
	public static void main(String[] args) {
		try{
			if(args != null && args.length>0){
				fileConfigPath = args[0];
				String filePah = fileConfigPath+"spring.xml";
				applicationContext = new FileSystemXmlApplicationContext("file:"+filePah);
			}else{
				System.out.println("请指定配置文件的路径");
				System.exit(1);
			}	
		}catch(Exception e){
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	public static ApplicationContext getApplicationContext(){
		return applicationContext;
	}

}
