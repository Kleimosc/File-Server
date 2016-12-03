package com.zaytsev.dto;

import java.io.File;

public class FileDto {

	private String name;
	
	private String url;
	
	public FileDto(File file, String url) {
		this.name = file.getName();
		if (file.isDirectory()) {
			this.url = url + file.getName() + "\\";
		} else {
			this.url = url + file.getName();
		}
		
	}

	public String getName() {
		return name;
	}
	
	public String getUrl() {
		return url;
	}

}
