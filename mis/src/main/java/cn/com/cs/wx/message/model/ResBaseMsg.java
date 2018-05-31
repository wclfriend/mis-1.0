package cn.com.cs.wx.message.model;

public class ResBaseMsg {

	/**
	 * 接收方帐号（一个OpenID）
	 */
	private String ToUserName;

	/**
	 * 发送方帐号（一个OpenID）
	 */
	private String FromUserName;

	/**
	 * 消息创建时间 （整型）
	 */
	private long CreateTime;

	/**
	 * 消息类型：text/image/voice/video/location/link/event
	 */
	private String MsgType;

	// 位0x0001被标志时，星标刚收到的消息
	private int FuncFlag;
	
	public String getToUserName() {
		return ToUserName;
	}

	public void setToUserName(String toUserName) {
		this.ToUserName = toUserName;
	}

	public String getFromUserName() {
		return FromUserName;
	}

	public void setFromUserName(String fromUserName) {
		this.FromUserName = fromUserName;
	}

	public long getCreateTime() {
		return CreateTime;
	}

	public void setCreateTime(long createTime) {
		this.CreateTime = createTime;
	}

	public void setCreateTime(int createTime) {
		this.CreateTime = createTime;
	}

	public String getMsgType() {
		return MsgType;
	}

	public void setMsgType(String msgType) {
		this.MsgType = msgType;
	}

	public int getFuncFlag() {
		return FuncFlag;
	}

	public void setFuncFlag(int funcFlag) {
		this.FuncFlag = funcFlag;
	}
	
}
