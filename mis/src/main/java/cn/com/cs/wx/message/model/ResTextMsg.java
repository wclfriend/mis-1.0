package cn.com.cs.wx.message.model;

public class ResTextMsg extends ResBaseMsg {

	/**
	 * 回复的消息内容（换行：在content中能够换行，微信客户端就支持换行显示）
	 */
	private String Content;

	public String getContent() {
		return Content;
	}

	public void setContent(String content) {
		this.Content = content;
	}

}
