package cn.com.cs.wx.message.model;

public class ResImageMsg extends ResBaseMsg {

	/**
	 * 通过上传多媒体文件，得到的id。
	 */
	private String MediaId;

	public String getMediaId() {
		return MediaId;
	}

	public void setMediaId(String mediaId) {
		this.MediaId = mediaId;
	}

}
