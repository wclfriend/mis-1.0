package cn.com.cs.wx.order.pojo;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "wx_orderRefund", schema = "")
@DynamicUpdate(true)
@DynamicInsert(true)
@SuppressWarnings("serial")
@XmlRootElement
@JsonAutoDetect
//@JsonInclude(Include.NON_EMPTY)
//@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "id" })
public class WechatOrderRefund implements java.io.Serializable {
	/** 主键ID */
	private java.lang.String id;
	/** 订单号 */
	private java.lang.String orderSerialNumber;
	/** 交易流水号 */
	private java.lang.String transactionSerialNumber;
	/**退款状态*/
	private String refundStatus;
	/**退款单号*/
	private String refundNumber;
	/**退款流水号*/
	private String transactionRefundNumber;
	/** 订单金额 */
	private BigDecimal orderAmount;
	/** 退款金额 */
	private BigDecimal refundAmount;
	/** 创建时间 */
	private java.util.Date createTime;
	/** 更新时间 */
	private java.util.Date modifyTime;
	/** 备注 */
	private java.lang.String remark;
	/**退款时间*/
	private Date refundTime;
	
	WechatOrderForm orderForm;
	
	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String 主键ID
	 */

	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
	@Column(name = "ID", nullable = false, length = 32)
	public java.lang.String getId() {
		return this.id;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String
	 *             主键ID
	 */
	public void setId(java.lang.String id) {
		this.id = id;
	}

	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String 订单号
	 */
	@Column(name = "ORDER_SERIAL_NUMBER", nullable = true, length = 32)
	public java.lang.String getOrderSerialNumber() {
		return this.orderSerialNumber;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String
	 *             订单号
	 */
	public void setOrderSerialNumber(java.lang.String orderSerialNumber) {
		this.orderSerialNumber = orderSerialNumber;
	}


	/**
	 * 方法: 取得BigDecimal
	 * 
	 * @return: BigDecimal 金额
	 */
	@Column(name = "ORDER_AMOUNT", nullable = true, precision = 10, scale = 0)
	public BigDecimal getOrderAmount() {
		return this.orderAmount;
	}

	/**
	 * 方法: 设置BigDecimal
	 * 
	 * @param: BigDecimal
	 *             金额
	 */
	public void setOrderAmount(BigDecimal orderAmount) {
		this.orderAmount = orderAmount;
	}


	/**
	 * 方法: 取得java.util.Date
	 * 
	 * @return: java.util.Date 创建时间
	 */
	@Column(name = "CREATE_TIME", nullable = true)
	@JsonFormat(pattern ="yyyy-MM-dd HH:mm:ss")
	public java.util.Date getCreateTime() {
		return this.createTime;
	}

	/**
	 * 方法: 设置java.util.Date
	 * 
	 * @param: java.util.Date
	 *             创建时间
	 */
	public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * 方法: 取得java.util.Date
	 * 
	 * @return: java.util.Date 更新时间
	 */
	@Column(name = "MODIFY_TIME", nullable = true)
	@JsonFormat(pattern ="yyyy-MM-dd HH:mm:ss")
	public java.util.Date getModifyTime() {
		return this.modifyTime;
	}

	/**
	 * 方法: 设置java.util.Date
	 * 
	 * @param: java.util.Date
	 *             更新时间
	 */
	public void setModifyTime(java.util.Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String 备注
	 */
	@Column(name = "REMARK", nullable = true, length = 256)
	public java.lang.String getRemark() {
		return this.remark;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String
	 *             备注
	 */
	public void setRemark(java.lang.String remark) {
		this.remark = remark;
	}
	
	public java.lang.String getRefundNumber() {
		return refundNumber;
	}

	public void setRefundNumber(java.lang.String refundNumber) {
		this.refundNumber = refundNumber;
	}

	public java.lang.String getTransactionSerialNumber() {
		return transactionSerialNumber;
	}

	public void setTransactionSerialNumber(java.lang.String transactionSerialNumber) {
		this.transactionSerialNumber = transactionSerialNumber;
	}
	public String getTransactionRefundNumber() {
		return transactionRefundNumber;
	}

	public void setTransactionRefundNumber(String transactionRefundNumber) {
		this.transactionRefundNumber = transactionRefundNumber;
	}

	public BigDecimal getRefundAmount() {
		return refundAmount;
	}

	public void setRefundAmount(BigDecimal refundAmount) {
		this.refundAmount = refundAmount;
	}

	public String getRefundStatus() {
		return refundStatus;
	}

	public void setRefundStatus(String refundStatus) {
		this.refundStatus = refundStatus;
	}

	public Date getRefundTime() {
		return refundTime;
	}

	public void setRefundTime(Date refundTime) {
		this.refundTime = refundTime;
	}
	@ManyToOne(fetch = FetchType.LAZY, cascade={CascadeType.ALL}, targetEntity=WechatOrderForm.class)
	@JoinColumn(name="wechatOrderFormId")
	public WechatOrderForm getOrderForm() {
		return orderForm;
	}

	public void setOrderForm(WechatOrderForm orderForm) {
		this.orderForm = orderForm;
	}
	
}
