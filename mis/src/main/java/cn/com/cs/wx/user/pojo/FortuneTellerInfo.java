package cn.com.cs.wx.user.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**   
 * @Title: Entity
 * @Description: 测算师基本信息
 * @author zhangdaihao
 * @date 2017-02-12 21:21:57
 * @version V1.0   
 *
 */
@Entity
@Table(name = "wx_FortuneTellerInfo", schema = "")
@DynamicUpdate(true)
@DynamicInsert(true)
@SuppressWarnings("serial")
@XmlRootElement
//@JsonInclude(Include.NON_EMPTY)
@JsonAutoDetect
@JsonIgnoreProperties(value = { "hibernateLazyInitializer"})
public class FortuneTellerInfo implements java.io.Serializable {
	/**主键*/
	private java.lang.String id;
	/**客户姓名*/
	private String userName;
	/**描述*/
	private java.lang.String describes;
	/**手机号*/
	private java.lang.String mobilePhoneNumber;
	/**是否有效*/
	private java.lang.String active;
	/**创建时间*/
	private java.util.Date createTime;
	/**更新时间*/
	private java.util.Date modifyTime;
	/**备注*/
	private java.lang.String remark;
	
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
	 *@return: java.lang.String  描述
	 */
	@Column(name ="DESCRIBES",nullable=true,length=32)
	public java.lang.String getDescribes(){
		return this.describes;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  描述
	 */
	public void setDescribes(java.lang.String describes){
		this.describes = describes;
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

	@Column(name ="user_name",nullable=true,length=32)
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	
}
