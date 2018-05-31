package cn.com.cs.aop;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeHandler {
	public void printTime() {
		System.out.println("current time = " + new SimpleDateFormat().format(new Date()));
	}
}