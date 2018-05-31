package cn.com.cs.wx.message.model;
/**
 * 主动向客户推送消息
 * @author admin
 *
 */
public class ReqInitiativeMes {
	/**openId*/
	public String touser;
	
	/**消息类型 text*/
	public String msgtype;
	
	/**推送内容*/
	public ReqTextMsg text;
	
	/**推送图文消息*/
	public ResNewsMsg news;

	public String getTouser() {
		return touser;
	}

	public void setTouser(String touser) {
		this.touser = touser;
	}

	public String getMsgtype() {
		return msgtype;
	}

	public void setMsgtype(String msgtype) {
		this.msgtype = msgtype;
	}

	public ReqTextMsg getText() {
		return text;
	}

	public void setText(ReqTextMsg text) {
		this.text = text;
	}

	public ResNewsMsg getNews() {
		return news;
	}

	public void setNews(ResNewsMsg news) {
		this.news = news;
	}
	
}
