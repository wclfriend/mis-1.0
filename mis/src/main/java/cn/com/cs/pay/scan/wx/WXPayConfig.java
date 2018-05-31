package cn.com.cs.pay.scan.wx;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public class WXPayConfig {
	
	private String appId;
	
	private String mch_id;
	
	String key;
	InputStream certStream;
	
	private byte[] certData;
    private static WXPayConfig INSTANCE;

    private WXPayConfig(Map<String, String> map) {
        try {
			String certPath = this.getClass().getResource("/").getPath() + "/config/apiclient_cert.p12";
			File file = new File(certPath);
			InputStream certStream = new FileInputStream(file);
			this.certData = new byte[(int) file.length()];
			certStream.read(this.certData);
			certStream.close();
			
			setAppID(map.get("appId"));
			setMchID(map.get("partner"));
			setKey(map.get("partnerkey"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    public static WXPayConfig getInstance(Map<String, String> map) {
        if (INSTANCE == null) {
            synchronized (WXPayConfig.class) {
                if (INSTANCE == null) {
                    INSTANCE = new WXPayConfig(map);
                }
            }
        }
        return INSTANCE;
    }
    /**
     * 获取 App ID
     *
     * @return App ID
     */
    String getAppID() {
    	return appId;
    }
    void setAppID(String appId) {
    	this.appId = appId;
    }


    /**
     * 获取 Mch ID
     *
     * @return Mch ID
     */
    String getMchID() {
    	return mch_id;
    }
    void setMchID(String mch_id) {
    	this.mch_id = mch_id;
    }


    /**
     * 获取 API 密钥
     *
     * @return API密钥
     */
    String getKey() {
    	return key;
    }
    void setKey(String key) {
    	this.key = key;
    }


    /**
     * 获取商户证书内容
     *
     * @return 商户证书内容
     */
    InputStream getCertStream() {
    	return certStream;
    }
    
    void setCertStream(InputStream certStream) {
    	this.certStream = certStream;
    }

    /**
     * HTTP(S) 连接超时时间，单位毫秒
     *
     * @return
     */
    public int getHttpConnectTimeoutMs() {
        return 6*1000;
    }

    /**
     * HTTP(S) 读数据超时时间，单位毫秒
     *
     * @return
     */
    public int getHttpReadTimeoutMs() {
        return 8*1000;
    }

    WXPayDomain domain;
    /**
     * 获取WXPayDomain, 用于多域名容灾自动切换
     * @return
     */
    
    WXPayDomain getWXPayDomain() {
        return WXPayDomainSimpleImpl.instance();
    }

    public String getPrimaryDomain() {
        return "api.mch.weixin.qq.com";
    }

    public String getAlternateDomain() {
        return "api2.mch.weixin.qq.com";
    }
    

    /**
     * 是否自动上报。
     * 若要关闭自动上报，子类中实现该函数返回 false 即可。
     *
     * @return
     */
    public boolean shouldAutoReport() {
        return true;
    }

    /**
     * 进行健康上报的线程的数量
     *
     * @return
     */
    public int getReportWorkerNum() {
        return 6;
    }


    /**
     * 健康上报缓存消息的最大数量。会有线程去独立上报
     * 粗略计算：加入一条消息200B，10000消息占用空间 2000 KB，约为2MB，可以接受
     *
     * @return
     */
    public int getReportQueueMaxSize() {
        return 10000;
    }

    /**
     * 批量上报，一次最多上报多个数据
     * @return
     */
    public int getReportBatchSize() {
        return 10;
    }

}
