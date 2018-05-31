package cn.com.cs.wx.message.model;

public class ReqEventMsg extends ReqBaseMsg {

	/**
	 * 事件类型，subscribe(订阅)、unsubscribe(取消订阅)、scan(扫描带参数二维码事件,用户已关注时)、LOCATION(
	 * 上报地理位置事件)、CLICK(自定义菜单事件)
	 */
	private String Event;

	/**
	 * 【扫描带参数二维码事件，用户未关注时】事件KEY值，qrscene_为前缀，后面为二维码的参数值
	 * 【扫描带参数二维码事件，用户已关注时】事件KEY值，是一个32位无符号整数，即创建二维码时的二维码scene_id
	 * 【自定义菜单事件】事件KEY值，与自定义菜单接口中KEY值对应
	 */
	private String EventKey;

	/**
	 * 二维码的ticket，可用来换取二维码图片
	 */
	private String Ticket;

	/**
	 * 地理位置纬度
	 */
	private String Latitude;

	/**
	 * 地理位置经度
	 */
	private String Longitude;

	/**
	 * 地理位置精度
	 */
	private String Precision;

	public String getEvent() {
		return Event;
	}

	public void setEvent(String event) {
		this.Event = event;
	}

	public String getEventKey() {
		return EventKey;
	}

	public void setEventKey(String eventKey) {
		this.EventKey = eventKey;
	}

	public String getTicket() {
		return Ticket;
	}

	public void setTicket(String ticket) {
		this.Ticket = ticket;
	}

	public String getLatitude() {
		return Latitude;
	}

	public void setLatitude(String latitude) {
		this.Latitude = latitude;
	}

	public String getLongitude() {
		return Longitude;
	}

	public void setLongitude(String longitude) {
		this.Longitude = longitude;
	}

	public String getPrecision() {
		return Precision;
	}

	public void setPrecision(String precision) {
		this.Precision = precision;
	}

}
