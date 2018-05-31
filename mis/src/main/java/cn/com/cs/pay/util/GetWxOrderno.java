package cn.com.cs.pay.util;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import cn.com.cs.pay.util.http.HttpClientConnectionManager;

public class GetWxOrderno {
	public static CloseableHttpClient httpclient;
	
	public static CloseableHttpClient getHttpClient() {
		if (httpclient == null) {
			httpclient = HttpClients.createDefault();
//			httpclient = (CloseableHttpClient) HttpClientConnectionManager.getSSLInstance(httpclient);
		}
		return httpclient;
	}

	/**
	 * description:获取预支付id
	 * 
	 * @param url
	 * @param xmlParam
	 * @return
	 */
	public static String getPayNo(String url, String xmlParam) {
		CloseableHttpClient httpclient = getHttpClient();
		HttpPost httpost = HttpClientConnectionManager.getPostMethod(url);
		String prepay_id = "";
		try {
			httpost.setEntity(new StringEntity(xmlParam, "UTF-8"));
			HttpResponse response = httpclient.execute(httpost);
			String jsonStr = EntityUtils.toString(response.getEntity(), "UTF-8");
			System.out.println("response:\n" + jsonStr);
			Element root = DocumentHelper.parseText(jsonStr).getRootElement();
			String return_code = root.selectSingleNode("return_code").getStringValue();
			if ("SUCCESS".equals(return_code)) {
				String result_code = root.selectSingleNode("result_code").getStringValue();
				if ("SUCCESS".equals(result_code)) {
					prepay_id = root.selectSingleNode("prepay_id").getStringValue();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return prepay_id;
	}
	
	public static void main(String[] args) {
		GetWxOrderno.getPayNo("http://www.suanxiansen.cn/jeewx/api/pay/wechat/payCallBack", "");
	}

	/**
	 * description:获取扫码支付连接
	 * 
	 * @param url
	 * @param xmlParam
	 * @return
	 * @author ex_yangxiaoyi
	 * @see
	 */
	public static String getCodeUrl(String url, String xmlParam) {
		HttpPost httpost = HttpClientConnectionManager.getPostMethod(url);
		String code_url = "";
		try {
			httpost.setEntity(new StringEntity(xmlParam, "UTF-8"));
			HttpResponse response = httpclient.execute(httpost);
			String jsonStr = EntityUtils.toString(response.getEntity(), "UTF-8");
			System.out.println("response:\n" + jsonStr);
			Element root = DocumentHelper.parseText(jsonStr).getRootElement();
			String return_code = root.selectSingleNode("return_code").getStringValue();
			if ("SUCCESS".equals(return_code)) {
				String result_code = root.selectSingleNode("result_code").getStringValue();
				if ("SUCCESS".equals(result_code)) {
					code_url = root.selectSingleNode("code_url").getStringValue();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return code_url;
	}

	public static InputStream String2Inputstream(String str) {
		return new ByteArrayInputStream(str.getBytes());
	}

}