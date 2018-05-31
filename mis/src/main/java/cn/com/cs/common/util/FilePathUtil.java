package cn.com.cs.common.util;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
@SuppressWarnings({"rawtypes"})
public class FilePathUtil {
	
	/**
	 * 获取web项目的Web Content 目录
	 * @return
	 */
	public static String webContentPath(){
		try {
			String path = FilePathUtil.class.getResource("").getFile();
			path = URLDecoder.decode(path, "UTF-8");
			if (path.lastIndexOf("/WEB-INF") > -1) {
				path = path.substring(0, path.lastIndexOf("/WEB-INF"));
				String temp = "";
				if (StringUtils.isNotBlank(path) && path.length() >= 5)
					temp = path.substring(0, 5);
				if ("file:".equalsIgnoreCase(temp)) {
					if (path.lastIndexOf("/") > -1) {
						path = path.substring(5);
					}
				}
			}
			return path;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static String getWebInfClassesPath(){
		try {
			String path = FilePathUtil.class.getResource("").getFile();
			path = URLDecoder.decode(path, "UTF-8");
			if (path.lastIndexOf("/WEB-INF") > -1) {
				path = path.substring(0, path.lastIndexOf("/WEB-INF"));
				String temp = "";
				if (StringUtils.isNotBlank(path) && path.length() >= 5)
					temp = path.substring(0, 5);
				if ("file:".equalsIgnoreCase(temp)) {
					if (path.lastIndexOf("/") > -1) {
						path = path.substring(5);
					}
				}
				return path + "/WEB-INF/classes";
			}
			return null;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	/**
	 * 获取类的绝对路径
	 * @param clazz
	 * @return
	 */
	public static String getClassAbsolutePath(Class clazz) {
		return clazz.getResource("").getPath();
	}
	
	
	/**
	 * 获取类的绝对路径
	 * @param clazz
	 * @return
	 */
	public static String getClassBuildPath() {
		return FilePathUtil.class.getClassLoader().getResource("").getPath();
	}
	/**
	 * 获取工程路径
	 * @return
	 */
	public static String getProjectPath() {
		return System.getProperty("user.dir");
	}
	
	/**
	 * 根据类获取文件
	 * @param clazz 类
	 * @param file 文件夹及文件名
	 * @return 文件的URL地址
	 */
	public static URL getResourceFilePath(Class clazz, String file) {
		return clazz.getResource(file);
	}
	
	
	/***
	 * 根据类获取文件
	 * @param clazz 类
	 * @param fileDirectory 文件夹
	 * @param fileName 文件名
	 * @return 文件的URL地址
	 */
	public static URL getResourceFilePath(Class clazz, String fileDirectory, String fileName) {
		return clazz.getResource(fileDirectory + fileName);
	}
	
	/**
	 * 获取项目物理路径
	 * @param clzss 参照物class
	 * @param dir 路径
	 * @param tagDir 标签路径
	 * @return
	 */
	public static String getProjectLocalPath(Class clzss, final String dir, String tagDir) {
		try {
			String path = clzss.getResource("").getFile();
			path = URLDecoder.decode(path, "UTF-8");
			if (path.lastIndexOf("/WEB-INF") > -1) {
				path = path.substring(0, path.lastIndexOf("/WEB-INF"));
				if (path.lastIndexOf("/") > -1) {
					path = path.substring(0, path.lastIndexOf("/"));
				}
				if (path.lastIndexOf("/") > -1) {
					path = path.substring(0, path.lastIndexOf("/"));
				}
				String temp = "";
				if (StringUtils.isNotBlank(path) && path.length() >= 5)
					temp = path.substring(0, 5);
				if ("file:".equalsIgnoreCase(temp)) {
					if (path.lastIndexOf("/") > -1) {
						path = path.substring(5);
					}
				}
			}
			
			Properties props=System.getProperties(); //获得系统属性集  
			String osName = props.getProperty("os.name"); //操作系统名称  
			if (osName.startsWith("Windows")) {
				path = props.getProperty("user.dir").replace("\\", "/") + tagDir;//"/src/main/webapp";
			} else {
				path += dir;
			}
			return path;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}
}
