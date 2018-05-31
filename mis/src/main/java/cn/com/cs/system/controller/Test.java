package cn.com.cs.system.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import net.sf.json.JSONArray;

@JsonAutoDetect
@JsonIgnoreProperties(value={"date"})
public class Test {

	public static void main(String[] args) {
		for (int i = 0; i < 20; i++) {
			System.out.println(UUID.randomUUID().toString().replaceAll("-", ""));;
		}
	}

	@JsonFormat(pattern ="yyyy-MM-dd HH:mm:ss")
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@JsonIgnore
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String name;

	public String url;

	public Date date;

}

