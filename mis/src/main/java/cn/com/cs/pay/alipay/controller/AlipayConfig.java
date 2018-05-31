package cn.com.cs.pay.alipay.controller;

import java.io.FileWriter;
import java.io.IOException;

public class AlipayConfig {

	//↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓

		// 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
		public static String app_id = "2017082108302714";
		
		// 商户私钥，您的PKCS8格式RSA2私钥
	    public static String merchant_private_key = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCuk9DwXokWockvHK8Ayqz1gkHwk5OZE2Iun2XGQ/Q3m5lFROp7pnkLjhxOvjWx4Ymp/EyZnwT2zMl0FUlXJ9xnsedPH7rTql4fW4Wt4yDUL0/cDeIZglY6CYtp6b98Oil2fjUEyqPEYHf6encKE5In8q+kw8b8dS6PVMdn162z73bn//or0JcaaS8BDiDmgbFgT7XaX+C6at2Gz9alC+/09GE0JchnyS8POswckwjxa44ajR2qU9XB+1kPHw77oZuib1oWwpfj5o31r4pbnu8A+gk56ZCzR3RLw6AaikQFSfLzqJHPblYWus3oEc9VEDrC7zmP1MqKkBLVaMHmhpo5AgMBAAECggEBAJgwLDkfcIrWRzhW3QjnppfwEePUAxuzFihmtKZ+nuvLLXaBeZiJq4QzQuIyV0TWxNf3Jh/VzNIJ/KTB2cms6itLn8YOdyQGpT7Y3lSUaA4RoKDJSY3LXbgCkhKmv1NgsLynkTi4HsQXBQDKufOC1CsXGZ+dPF7hGNHCqoMQNlFgv0l/1hPZePVfCTTl17ynXBHy9P5c5eTjbv0lZQJrQEWJMwvOE61eC9VMfVaLC7/3MZaoL6TJAPwZlzfymUyzcAoZUBftged5HagHnsp9VJJhPkebvQf9Z38b+CK1YAHIc3/DcbqJdbnyM/5qyNwY8yE3ntfqX7Org+kefKqzn/ECgYEA9wp2iyPcXlHq1Gf6RrMh6ghvZhJEBAsPjU1aAHy9lVtmOaRAlTXbbMq9IsW+c19gr5bLma2xKeRCIWN39E38myQeR7eZdKsJxPQsodElo1VRwhtlW4tpGckQkX70KrJfRtHPdxOUA7b1GciGt73u//6pg2STYEX7ppWJZRi2zn8CgYEAtOiZeiCHbFFyQxCYVt1dH25SGBsMxPbdeUQ2bosG6sWtFnVq2202LpTk0Rj+MS+YC35/FMSM2cNUJoLMExmO3PrzMnL1eWAjE2IF7wmjy9L9tznuIdFON0C+Y1ZuWb6QByoLNipodATddhr/CrnyZi91umal8v8TiZruOx7cK0cCgYEAnShO2Z1qp6Y9Wfr7wNZckOHk3G5qTpgqwJ2g0AaLJOUMV0civsmQWdlwERrzWbonbAzmYVGaDyhi2ZLJ0OV7+cY0bTNjsFDO2jjr29WNuCai6HU9OcuqhPjgTW8SLcdjzU3USdBurR5LNqfW7DUsQjbj5+6nwutCeGtRCLt8MEkCgYBOQehSekvawYjKFh2wReYIuLJulxT5B0lbMfTR8PY3DPgJJRXqgd3UXLh283bSVCJTpo7Wrwe3E9YNKruY749rbGtTwYMK1FmYAulKNENU4pq6bibjHiXVpP42/qaRz+uCDd/3uHSi64/ZjWgSfcmsBo/I6KqU6V7MQecYUAsx9QKBgHaFknFEoMFMhNiW6ry0/AUl8Ov2iTwFMpH3rGRm2ZeHWKXVuiM8rTX7alBd9SYsO305RCArG9lXRNjXbqZRyyuSx60qK23OacojcUNMT888kBXygAhNgEd4R0V+Kg4WctJaeBXizbPewn7+Jc6E1a9ig2EV5qCsfSFGeGLNlMLt";
	    
		// 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
	    public static String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAhd3+6R3hKfMFcTZxe8XtaMwVG553+qcXb19W/h/TpkQd11YGd6JFl0vENMqxeYYlG16O9QcgFSItnw00PQm2M9Rr1a6GKGkmtoy4oyHOiuGALAFD1/IYDqQlugtcezxRm8htDdubbm28lrjYS6UEAxMUJIzYXMMvpcv8EECncAobE1BgfIMqrVsN/4yQ3ww4+sTeIYcWUArkKg+FIVqDzFVSco7EKmWG5wf+yh3b25gVdX7ceX1kRaRCUOUdLJlA2mSF6mkw7IlNJUN2AnebM1Sr0NL9kODVTP0BaBxjlIptHdmlrAtufaM3w1/4chXrp7A1hlxhhjmpl9Uu6lqpcwIDAQAB";
	    
		// 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
		public static String notify_url = "http://www.keqiaokeji.com/mis/pay/alipay/nofity";
		
		// 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
		public static String return_url = "http://www.keqiaokeji.com/mis/pay/alipay/return";
		
		// 签名方式
		public static String sign_type = "RSA2";
		
		// 字符编码格式
		public static String charset = "UTF-8";
		
		// 支付宝网关
		public static String gatewayUrl = "https://openapi.alipay.com/gateway.do";
//		public static String gatewayUrl = "http://openapi.alipaydev.com/gateway.do";
		
		public static String productCode= "FAST_INSTANT_TRADE_PAY";
		
		
		// 支付宝网关
		public static String log_path = "/Users/billy/eclipse/jw/work/mis/logs";


	//↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

	    /** 
	     * 写日志，方便测试（看网站需求，也可以改成把记录存入数据库）
	     * @param sWord 要写入日志里的文本内容
	     */
	    public static void logResult(String sWord) {
	        FileWriter writer = null;
	        try {
	            writer = new FileWriter(log_path + "alipay_log_" + System.currentTimeMillis()+".txt");
	            writer.write(sWord);
	        } catch (Exception e) {
	            e.printStackTrace();
	        } finally {
	            if (writer != null) {
	                try {
	                    writer.close();
	                } catch (IOException e) {
	                    e.printStackTrace();
	                }
	            }
	        }
	    }
}
