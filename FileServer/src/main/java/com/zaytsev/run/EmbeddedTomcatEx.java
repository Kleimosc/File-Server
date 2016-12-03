package com.zaytsev.run;

import java.io.File;

import javax.servlet.ServletException;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;
import org.apache.tomcat.util.scan.StandardJarScanner;


public class EmbeddedTomcatEx {

	public static void main(String[] args) throws LifecycleException,
    InterruptedException, ServletException {
		
		String webappDirLocation = "webapp/";
	    Tomcat tomcat = new Tomcat();
	    System.out.println(new File(webappDirLocation).getAbsolutePath());
	    tomcat.setBaseDir(".");
	    tomcat.setPort(8080);
	    Context ctx = tomcat.addWebapp(tomcat.getHost(), "", new    
	    File(webappDirLocation).getAbsolutePath());
	    ((StandardJarScanner) ctx.getJarScanner()).setScanAllDirectories(true);

	    tomcat.start();
	    
	    tomcat.getServer().await();
	}
		
}


