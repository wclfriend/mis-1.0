package cn.com.cs.wx.user.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

/**   
 * @Title: Entity
 * @Description: 唯一ID
 * @author zhangdaihao
 * @date 2017-02-15 14:53:51
 * @version V1.0   
 *
 */
@Entity
@Table(name = "wx_uniqueId", schema = "")
@DynamicUpdate(true)
@DynamicInsert(true)
@SuppressWarnings("serial")
public class WechatUniqueId implements java.io.Serializable {
	/**主键*/
	private java.lang.String id;
	/**客户唯一ID*/
	private java.lang.String uniqueId;
	/**ID类型*/
	private java.lang.String idType;
	/**是否已使用*/
	private java.lang.String active;
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  主键
	 */
	
	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
	@Column(name ="ID",nullable=true,length=32)
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
	 *@return: java.lang.String  客户唯一ID
	 */
	@Column(name ="UNIQUE_ID",nullable=true,length=32)
	public java.lang.String getUniqueId(){
		return this.uniqueId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  客户唯一ID
	 */
	public void setUniqueId(java.lang.String uniqueId){
		this.uniqueId = uniqueId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  ID类型
	 */
	@Column(name ="ID_TYPE",nullable=true,length=16)
	public java.lang.String getIdType(){
		return this.idType;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  ID类型
	 */
	public void setIdType(java.lang.String idType){
		this.idType = idType;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  是否已使用
	 */
	public java.lang.String isActive(){
		return this.active;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  是否已使用
	 */
	public void setActive(java.lang.String active){
		this.active = active;
	}
}
