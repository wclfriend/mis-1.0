package cn.com.cs.wx.user.pojo;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import cn.com.cs.common.enums.CommonEnum;
import cn.com.cs.common.enums.OrderStatus;
import cn.com.cs.common.enums.Season;
import cn.com.cs.common.enums.Sex;
import cn.com.cs.common.enums.UserStatus;


/**   
 * @Title: Entity
 * @Description: 客户及亲属基本信息
 * @author zhangdaihao
 * @date 2017-02-15 16:16:22
 * @version V1.0   
 *
 */
@Entity
@Table(name = "wx_userInfo", schema = "")
@DynamicUpdate(true)
@DynamicInsert(true)
@SuppressWarnings("serial")
@XmlRootElement
@JsonAutoDetect
//@JsonInclude(Include.NON_EMPTY)
@JsonIgnoreProperties(value = { "hibernateLazyInitializer"})
public class WechatUserInfo implements java.io.Serializable {
	/**主键*/
	private java.lang.String id;
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
	/**创建时间*/
	private java.util.Date createTime;
	/**更新时间*/
	private java.util.Date modifyTime;
	/**是否有效*/
	private java.lang.String active;
	/**备注*/
	private java.lang.String remark;
	/**昵称*/
	private byte[] nickname;
	
	private List<PartyRoleInUser> partys;
				 
	private Date traditionalBirthday;
	
	private String season;


	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  主键
	 */
	
	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
	@Column(name ="ID",nullable=false,length=32)
	public java.lang.String getId(){
		return this.id;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  主键
	 */
	public void setId(java.lang.String id){
		this.id = id;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  客户ID
	 */
	@Column(name ="USER_ID",nullable=true,length=32)
	public java.lang.String getUserId(){
		return this.userId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  客户ID
	 */
	public void setUserId(java.lang.String userId){
		this.userId = userId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  微信ID
	 */
	@Column(name ="OPEN_ID",nullable=true,length=16)
	public java.lang.String getOpenId(){
		return this.openId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  微信ID
	 */
	public void setOpenId(java.lang.String openId){
		this.openId = openId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  微信头像
	 */
	@Column(name ="IMAGE",nullable=true,length=128)
	public java.lang.String getImage(){
		return this.image;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  微信头像
	 */
	public void setImage(java.lang.String image){
		this.image = image;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  出生地址
	 */
	@Column(name ="BORN_ADDRESS",nullable=true,length=256)
	public java.lang.String getBornAddress(){
		return this.bornAddress;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  出生地址
	 */
	public void setBornAddress(java.lang.String bornAddress){
		this.bornAddress = bornAddress;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  性别
	 */
	@Column(name ="SEX",nullable=true,length=16)
	public String getSex(){
		return this.sex;
	}

	@Transient
	public String getSexName(){
		if (StringUtils.isBlank(getSex())) {
			return "";
		}
		CommonEnum enum_temp = Sex.getEnumByValue(Sex.class, getSex());
		
		return enum_temp.getName();
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  性别
	 */
	public void setSex(String sex){
		this.sex = sex;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  客户状态
	 */
	@Column(name ="USER_STATUS",nullable=true,length=32)
	public String getUserStatus(){
		return this.userStatus;
	}
	
	@Transient
	public String getUserStatusName(){
		if (StringUtils.isBlank(getUserStatus())) {
			return "";
		}
		CommonEnum enum_temp = UserStatus.getEnumByValue(UserStatus.class, getUserStatus());
		
		return enum_temp.getName();
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  客户状态
	 */
	public void setUserStatus(String userStatus){
		this.userStatus = userStatus;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  生日
	 */
	@Column(name ="BIRTHDAY",nullable=true)
//	@JsonSerialize(using=JsonDateSerializer.class)
	@JsonFormat(pattern ="yyyy-MM-dd")
	public java.util.Date getBirthday(){
		return this.birthday;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  生日
	 */
	public void setBirthday(java.util.Date birthday){
		this.birthday = birthday;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  年龄
	 */
	@Column(name ="AGE",nullable=true,length=16)
	public java.lang.String getAge(){
		return this.age;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  年龄
	 */
	public void setAge(java.lang.String age){
		this.age = age;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  手机号
	 */
	@Column(name ="MOBILE_PHONE_NUMBER",nullable=true,length=32)
	public java.lang.String getMobilePhoneNumber(){
		return this.mobilePhoneNumber;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  手机号
	 */
	public void setMobilePhoneNumber(java.lang.String mobilePhoneNumber){
		this.mobilePhoneNumber = mobilePhoneNumber;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  省
	 */
	@Column(name ="PROVINCE_NAME",nullable=true,length=32)
	public java.lang.String getProvinceName(){
		return this.provinceName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  省
	 */
	public void setProvinceName(java.lang.String provinceName){
		this.provinceName = provinceName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  市
	 */
	@Column(name ="CITY_NAME",nullable=true,length=32)
	public java.lang.String getCityName(){
		return this.cityName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  市
	 */
	public void setCityName(java.lang.String cityName){
		this.cityName = cityName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  区
	 */
	@Column(name ="COUNTY_NAME",nullable=true,length=32)
	public java.lang.String getCountyName(){
		return this.countyName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  区
	 */
	public void setCountyName(java.lang.String countyName){
		this.countyName = countyName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  家庭详细地址
	 */
	@Column(name ="HOME_ADDRESS",nullable=true,length=256)
	public java.lang.String getHomeAddress(){
		return this.homeAddress;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  家庭详细地址
	 */
	public void setHomeAddress(java.lang.String homeAddress){
		this.homeAddress = homeAddress;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  微信地址
	 */
	@Column(name ="WECHAT_ADDRESS",nullable=true,length=128)
	public java.lang.String getWechatAddress(){
		return this.wechatAddress;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  微信地址
	 */
	public void setWechatAddress(java.lang.String wechatAddress){
		this.wechatAddress = wechatAddress;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  创建时间
	 */
	@Column(name ="CREATE_TIME",nullable=true)
	@JsonFormat(pattern ="yyyy-MM-dd HH:mm:ss")
	public java.util.Date getCreateTime(){
		return this.createTime;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  创建时间
	 */
	public void setCreateTime(java.util.Date createTime){
		this.createTime = createTime;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  更新时间
	 */
	@Column(name ="MODIFY_TIME",nullable=true)
	@JsonFormat(pattern ="yyyy-MM-dd HH:mm:ss")
	public java.util.Date getModifyTime(){
		return this.modifyTime;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  更新时间
	 */
	public void setModifyTime(java.util.Date modifyTime){
		this.modifyTime = modifyTime;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  是否有效
	 */
	public java.lang.String getActive() {
		return active;
	}
	
	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  是否有效
	 */
	public void setActive(java.lang.String active){
		this.active = active;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  备注
	 */
	@Column(name ="REMARK",nullable=true,length=256)
	public java.lang.String getRemark(){
		return this.remark;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  备注
	 */
	public void setRemark(java.lang.String remark){
		this.remark = remark;
	}
	/**
	 *方法: 取得byte[]
	 *@return: byte[]  昵称
	 */
	@Column(name ="NICKNAME",nullable=true,length=65535)
	public byte[] getNickname(){
		return this.nickname;
	}

	/**
	 *方法: 设置byte[]
	 *@param: byte[]  昵称
	 */
	public void setNickname(byte[] nickname){
		this.nickname = nickname;
	}

	@OneToMany(fetch=FetchType.LAZY, cascade = { CascadeType.ALL }, mappedBy="wechatUserInfo")
	@JsonIgnore
	public List<PartyRoleInUser> getPartys() {
		return partys;
	}

	public void setPartys(List<PartyRoleInUser> partys) {
		this.partys = partys;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@JsonFormat(pattern ="yyyy-MM-dd") 
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
	@Transient
	public String getSeasonName() {
		if (StringUtils.isBlank(getSeason()))
			return null;
		Season _temp = (Season) OrderStatus.getEnumByValue(Season.class, getSeason());
		
		return _temp.getName();
	}
	
	
}
