package cn.com.cs.wx.message.model;

public class ResMusicMsg extends ResBaseMsg {

	/**
	 * 通过上传多媒体文件，得到的id
	 */
	private String MediaId;

	/**
	 * 音乐标题
	 */
	private String Title;

	/**
	 * 音乐描述
	 */
	private String Description;
	
	
	/**
	 * 音乐链接
	 */
	private String MusicURL;
	
	
	/**
	 * 高质量音乐链接，WIFI环境优先使用该链接播放音乐
	 */
	private String HQMusicUrl;
	
	/**
	 * 缩略图的媒体id，通过上传多媒体文件，得到的id
	 */
	private String ThumbMediaId;
	

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

	public String getMusicURL() {
		return MusicURL;
	}

	public void setMusicURL(String musicURL) {
		this.MusicURL = musicURL;
	}

	public String gethQMusicUrl() {
		return HQMusicUrl;
	}

	public void sethQMusicUrl(String hQMusicUrl) {
		this.HQMusicUrl = hQMusicUrl;
	}

	public String getThumbMediaId() {
		return ThumbMediaId;
	}

	public void setThumbMediaId(String thumbMediaId) {
		this.ThumbMediaId = thumbMediaId;
	}

}
