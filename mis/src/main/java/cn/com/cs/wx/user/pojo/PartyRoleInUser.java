package cn.com.cs.wx.user.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**   
 * @Title: Entity
 * @Description: 客户及亲属基本信息
 * @author zhangdaihao
 * @date 2017-02-12 21:21:23
 * @version V1.0   
 *
 */
@Entity
@Table(name = "wx_PartyRoleInUser", schema = "")
@DynamicUpdate(true)
@DynamicInsert(true)
@SuppressWarnings("serial")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@XmlRootElement
@JsonAutoDetect
//@JsonInclude(Include.NON_EMPTY)
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "id" })
public class PartyRoleInUser implements java.io.Serializable {
	/**主键*/
	private java.lang.String id;
	/**姓名*/
	private java.lang.String userName;
	/**角色关系*/
	private java.lang.String roleKind;
	/**出生地址*/
	private java.lang.String bornAddress;
	/**性别*/
	private String sex;
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
	/**婚姻状态*/
	private java.lang.String maritalStatus;
	/**创建时间*/
	private java.util.Date createTime;
	/**更新时间*/
	private java.util.Date modifyTime;
	/**是否有效*/
	private java.lang.String active;
	/**备注*/
	private java.lang.String remark;

	/**微信客户信息*/
	private WechatUserInfo wechatUserInfo;
	
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
	 *@return: java.lang.String  姓名
	 */
	@Column(name ="USER_NAME",nullable=true,length=32)
	public java.lang.String getUserName(){
		return this.userName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  姓名
	 */
	public void setUserName(java.lang.String userName){
		this.userName = userName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  角色关系
	 */
	@Column(name ="ROLE_KIND",nullable=true,length=16)
	public java.lang.String getRoleKind(){
		return this.roleKind;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  角色关系
	 */
	public void setRoleKind(java.lang.String roleKind){
		this.roleKind = roleKind;
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
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.String  性别
	 */
	@Column(name ="SEX",nullable=true,precision=10,scale=0)
	public String getSex(){
		return this.sex;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.String  性别
	 */
	public void setSex(String sex){
		this.sex = sex;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  生日
	 */
	@Column(name ="BIRTHDAY",nullable=true)
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
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.String  年龄
	 */
	@Column(name ="AGE",nullable=true,precision=10,scale=0)
	public java.lang.String getAge(){
		return this.age;
	}

	/**
	 *方法: 设置java.lang.Integer
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
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.String  婚姻状态
	 */
	@Column(name ="MARITAL_STATUS",nullable=true,precision=10,scale=0)
	public java.lang.String getMaritalStatus(){
		return this.maritalStatus;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.String  婚姻状态
	 */
	public void setMaritalStatus(java.lang.String maritalStatus){
		this.maritalStatus = maritalStatus;
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
	public java.lang.String getActive(){
		return this.active;
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

	@ManyToOne(fetch=FetchType.LAZY, targetEntity=WechatUserInfo.class)
	@JoinColumn(name = "userSerialNo")
	@JsonIgnore
	public WechatUserInfo getWechatUserInfo() {
		return wechatUserInfo;
	}

	public void setWechatUserInfo(WechatUserInfo wechatUserInfo) {
		this.wechatUserInfo = wechatUserInfo;
	}
	
}
