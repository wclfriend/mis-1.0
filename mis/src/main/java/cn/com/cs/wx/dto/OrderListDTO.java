package cn.com.cs.wx.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import cn.com.cs.wx.user.pojo.FortuneTellerInfo;
import cn.com.cs.wx.user.pojo.PartyRoleInUser;
import cn.com.cs.wx.user.pojo.WechatUserInfo;

public class OrderListDTO {
	/** 主键ID */
	private java.lang.String id;
	/** 订单号 */
	private java.lang.String orderSerialNumber;
	/**退款单号*/
	private String refundNumber;
	/** 订单状态 */
	private java.lang.String orderStatus;
	/** 订单类型 */
	private java.lang.String orderType;
	/** 服务方式 */
	private java.lang.String serviceType;
	/** 服务内容 */
	private java.lang.String serviceContent;
	/** 金额 */
	private BigDecimal orderAmount;
	/** 测算结果
	 * 0 ： 未上传 
	 * 1 ： 已上传
	 * */
	private String analyseResult;
	/** 测算结果音频文件 */
	private String voiceFilePath;
	/** 服务地址 */
	private java.lang.String serviceAddress;
	/** 创建时间 */
	private java.util.Date createTime;
	/** 更新时间 */
	private java.util.Date modifyTime;
	/** 是否有效 */
	private java.lang.String active;
	/** 备注 */
	private java.lang.String remark;
	/** 服务日期 */
	private Date serviceDate;
	/** 服务时间 */
	private java.lang.String serviceTime;
	/** 是否已延期 */
	private java.lang.String delayflag;
	/** 延期后的测算时间 */
	private Date modifiedServiceDate;
	/** 延期后的测算时间 */
	private java.lang.String modifiedServiceTime;
	/**测算师*/
	private FortuneTellerInfo fortuneTellerInfo;
	/**微信客户信息*/
	private WechatUserInfo wechatUserInfo;
	
	/**主键*/
	private java.lang.String f_id;
	/**客户姓名*/
	private String f_userName;
	/**手机号*/
	private java.lang.String f_mobilePhoneNumber;
	
	/**主键*/
	private java.lang.String u_id;
	/**客户ID*/
	private java.lang.String userId;
	/**微信ID*/
	private java.lang.String openId;
	/**微信头像*/
	private java.lang.String image;
	/**出生地址*/
	private java.lang.String bornAddress;
	/**客户姓名*/
	private String userName;
	/**性别*/
	private String sex;
	/**客户状态*/
	private String userStatus;
	/**生日*/
	private java.util.Date birthday;
	/**年龄*/
	private java.lang.String age;
	/**手机号*/
	private java.lang.String mobilePhoneNumber;
	/**省*/
	private java.lang.String provinceName;
	/**市*/
	private java.lang.String cityName;
	/**区*/
	private java.lang.String countyName;
	/**家庭详细地址*/
	private java.lang.String homeAddress;
	/**微信地址*/
	private java.lang.String wechatAddress;
	/**昵称*/
	private byte[] nickname;
	
	private Date traditionalBirthday;
	
	private String season;
	
	public java.lang.String getId() {
		return id;
	}

	public void setId(java.lang.String id) {
		this.id = id;
	}

	public java.lang.String getOrderSerialNumber() {
		return orderSerialNumber;
	}

	public void setOrderSerialNumber(java.lang.String orderSerialNumber) {
		this.orderSerialNumber = orderSerialNumber;
	}

	public String getRefundNumber() {
		return refundNumber;
	}

	public void setRefundNumber(String refundNumber) {
		this.refundNumber = refundNumber;
	}

	public java.lang.String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(java.lang.String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public java.lang.String getOrderType() {
		return orderType;
	}

	public void setOrderType(java.lang.String orderType) {
		this.orderType = orderType;
	}

	public java.lang.String getServiceType() {
		return serviceType;
	}

	public void setServiceType(java.lang.String serviceType) {
		this.serviceType = serviceType;
	}

	public java.lang.String getServiceContent() {
		return serviceContent;
	}

	public void setServiceContent(java.lang.String serviceContent) {
		this.serviceContent = serviceContent;
	}

	public BigDecimal getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(BigDecimal orderAmount) {
		this.orderAmount = orderAmount;
	}

	public String getAnalyseResult() {
		return analyseResult;
	}

	public void setAnalyseResult(String analyseResult) {
		this.analyseResult = analyseResult;
	}

	public String getVoiceFilePath() {
		return voiceFilePath;
	}

	public void setVoiceFilePath(String voiceFilePath) {
		this.voiceFilePath = voiceFilePath;
	}

	public java.lang.String getServiceAddress() {
		return serviceAddress;
	}

	public void setServiceAddress(java.lang.String serviceAddress) {
		this.serviceAddress = serviceAddress;
	}

	public java.util.Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
	}

	public java.util.Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(java.util.Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public java.lang.String getActive() {
		return active;
	}

	public void setActive(java.lang.String active) {
		this.active = active;
	}

	public java.lang.String getRemark() {
		return remark;
	}

	public void setRemark(java.lang.String remark) {
		this.remark = remark;
	}

	public Date getServiceDate() {
		return serviceDate;
	}

	public void setServiceDate(Date serviceDate) {
		this.serviceDate = serviceDate;
	}

	public java.lang.String getServiceTime() {
		return serviceTime;
	}

	public void setServiceTime(java.lang.String serviceTime) {
		this.serviceTime = serviceTime;
	}

	public java.lang.String getDelayflag() {
		return delayflag;
	}

	public void setDelayflag(java.lang.String delayflag) {
		this.delayflag = delayflag;
	}

	public Date getModifiedServiceDate() {
		return modifiedServiceDate;
	}

	public void setModifiedServiceDate(Date modifiedServiceDate) {
		this.modifiedServiceDate = modifiedServiceDate;
	}

	public java.lang.String getModifiedServiceTime() {
		return modifiedServiceTime;
	}

	public void setModifiedServiceTime(java.lang.String modifiedServiceTime) {
		this.modifiedServiceTime = modifiedServiceTime;
	}

	public FortuneTellerInfo getFortuneTellerInfo() {
		return fortuneTellerInfo;
	}

	public void setFortuneTellerInfo(FortuneTellerInfo fortuneTellerInfo) {
		this.fortuneTellerInfo = fortuneTellerInfo;
	}

	public WechatUserInfo getWechatUserInfo() {
		return wechatUserInfo;
	}

	public void setWechatUserInfo(WechatUserInfo wechatUserInfo) {
		this.wechatUserInfo = wechatUserInfo;
	}

	public java.lang.String getF_id() {
		return f_id;
	}

	public void setF_id(java.lang.String f_id) {
		this.f_id = f_id;
	}

	public String getF_userName() {
		return f_userName;
	}

	public void setF_userName(String f_userName) {
		this.f_userName = f_userName;
	}

	public java.lang.String getF_mobilePhoneNumber() {
		return f_mobilePhoneNumber;
	}

	public void setF_mobilePhoneNumber(java.lang.String f_mobilePhoneNumber) {
		this.f_mobilePhoneNumber = f_mobilePhoneNumber;
	}

	public java.lang.String getU_id() {
		return u_id;
	}

	public void setU_id(java.lang.String u_id) {
		this.u_id = u_id;
	}

	public java.lang.String getUserId() {
		return userId;
	}

	public void setUserId(java.lang.String userId) {
		this.userId = userId;
	}

	public java.lang.String getOpenId() {
		return openId;
	}

	public void setOpenId(java.lang.String openId) {
		this.openId = openId;
	}

	public java.lang.String getImage() {
		return image;
	}

	public void setImage(java.lang.String image) {
		this.image = image;
	}

	public java.lang.String getBornAddress() {
		return bornAddress;
	}

	public void setBornAddress(java.lang.String bornAddress) {
		this.bornAddress = bornAddress;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(String userStatus) {
		this.userStatus = userStatus;
	}

	public java.util.Date getBirthday() {
		return birthday;
	}

	public void setBirthday(java.util.Date birthday) {
		this.birthday = birthday;
	}

	public java.lang.String getAge() {
		return age;
	}

	public void setAge(java.lang.String age) {
		this.age = age;
	}

	public java.lang.String getMobilePhoneNumber() {
		return mobilePhoneNumber;
	}

	public void setMobilePhoneNumber(java.lang.String mobilePhoneNumber) {
		this.mobilePhoneNumber = mobilePhoneNumber;
	}

	public java.lang.String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(java.lang.String provinceName) {
		this.provinceName = provinceName;
	}

	public java.lang.String getCityName() {
		return cityName;
	}

	public void setCityName(java.lang.String cityName) {
		this.cityName = cityName;
	}

	public java.lang.String getCountyName() {
		return countyName;
	}

	public void setCountyName(java.lang.String countyName) {
		this.countyName = countyName;
	}

	public java.lang.String getHomeAddress() {
		return homeAddress;
	}

	public void setHomeAddress(java.lang.String homeAddress) {
		this.homeAddress = homeAddress;
	}

	public java.lang.String getWechatAddress() {
		return wechatAddress;
	}

	public void setWechatAddress(java.lang.String wechatAddress) {
		this.wechatAddress = wechatAddress;
	}

	public byte[] getNickname() {
		return nickname;
	}

	public void setNickname(byte[] nickname) {
		this.nickname = nickname;
	}

	public Date getTraditionalBirthday() {
		return traditionalBirthday;
	}

	public void setTraditionalBirthday(Date traditionalBirthday) {
		this.traditionalBirthday = traditionalBirthday;
	}

	public String getSeason() {
		return season;
	}

	public void setSeason(String season) {
		this.season = season;
	}

}
