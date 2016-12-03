package com.zaytsev.servlets;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zaytsev.dto.FileDto;


@WebServlet(name = "IndexServlet")
public class IndexServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	private Properties prop;
	
    public IndexServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (prop == null) {
			getProp();
		}
		String url;
		try {
			url = URLDecoder.decode(((HttpServletRequest)request).getRequestURI().toString().substring(7), "UTF-8");
		} catch(Exception e) {
			url = "";
		}
		
		Path path = Paths.get(prop.getProperty("dir")).resolve(url); 
		System.out.println(path);
		File downloadFile = new File(path.toString());
		if (downloadFile.isDirectory()) {
			File[] files = downloadFile.listFiles();
			List<FileDto> fileList = new ArrayList<>();
			for (File file : files) {
				fileList.add(new FileDto(file, url));
			}
			request.setAttribute("files", fileList);
			request.getRequestDispatcher("/WEB-INF/pages/index.jsp").forward(request, response);
		} else {
			FileInputStream inputStream = new FileInputStream(downloadFile);
	        // get MIME type of the file
	        String mimeType = Files.probeContentType(path);
	        if (mimeType == null) {
	            // set to binary type if MIME mapping not found
	            mimeType = "application/octet-stream";
	        }
	        // set content attributes for the response
	        response.setContentType(mimeType);
	        response.setContentLength((int) downloadFile.length());
	        // set headers for the response
	        String headerKey = "Content-Disposition";
	        String headerValue = String.format("attachment; filename*0*=utf-8''\"%s\"",
	        URLEncoder.encode(downloadFile.getName(), "UTF-8"));
	        response.setHeader(headerKey, headerValue);
	        // get output stream of the response
	        OutputStream outStream = null;
			try {
				outStream = response.getOutputStream();
			} catch (IOException e) {
				e.printStackTrace();
			}
	        byte[] buffer = new byte[4096];
	        int bytesRead = -1;
	        // write bytes read from the input stream into the output stream
	        try {
				while ((bytesRead = inputStream.read(buffer)) != -1) {
				    outStream.write(buffer, 0, bytesRead);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
	        inputStream.close();
	        outStream.close();
		}
	}
	
	private void getProp() throws IOException{
		Properties properties = new Properties();
	    InputStream is = getClass().getClassLoader().getResourceAsStream("application.properties");
	    try {
			properties.load(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.prop = properties;
		is.close();
		
	}

}
