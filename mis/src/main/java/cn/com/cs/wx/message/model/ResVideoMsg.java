package cn.com.cs.wx.message.model;

public class ResVideoMsg extends ResBaseMsg {

	/**
	 * 通过上传多媒体文件，得到的id
	 */
	private String MediaId;

	/**
	 * 视频消息的标题
	 */
	private String Title;

	/**
	 * 视频消息的描述
	 */
	private String Description;

	public String getMediaId() {
		return MediaId;
	}

	public void setMediaId(String mediaId) {
		this.MediaId = mediaId;
	}

	public String getTitle() {
		return Title;
	}

	public void setTitle(String title) {
		this.Title = title;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		this.Description = description;
	}

}
