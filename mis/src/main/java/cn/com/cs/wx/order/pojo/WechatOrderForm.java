package cn.com.cs.wx.order.pojo;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Version;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import cn.com.cs.common.pojo.IdEntity;
import cn.com.cs.wx.user.pojo.WechatUserInfo;

@Entity
@Table(name = "wx_orderForm")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "id"})
public class WechatOrderForm extends IdEntity implements java.io.Serializable {
	
	@Id
	private String id;
	
	@Version
	private int version;
	private static final long serialVersionUID = 1L;
	/** 订单号 */
	private String orderSerialNumber;
	
	/** 交易流水号 */
	private String transactionSerialNumber;
	
	/**产品代码*/
	private String productCode;
	
	/**产品名称*/
	private String productName;
	
	/**支付时间*/
	private Date payTime;
	
	/**退款单号*/
	private String refundNumber;
	
	/** 订单状态 */
	private String orderStatus;
	
	/** 订单类型 */
	private String orderType;
	
	/** 金额 */
	private BigDecimal orderAmount;
	
	/** 文件 */
	private String voiceFilePath;

	/**微信客户信息*/
	private WechatUserInfo wechatUserInfo;

	public String getOrderSerialNumber() {
		return orderSerialNumber;
	}

	public void setOrderSerialNumber(String orderSerialNumber) {
		this.orderSerialNumber = orderSerialNumber;
	}

	public String getTransactionSerialNumber() {
		return transactionSerialNumber;
	}

	public void setTransactionSerialNumber(String transactionSerialNumber) {
		this.transactionSerialNumber = transactionSerialNumber;
	}

	public Date getPayTime() {
		return payTime;
	}

	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}

	public String getRefundNumber() {
		return refundNumber;
	}

	public void setRefundNumber(String refundNumber) {
		this.refundNumber = refundNumber;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public BigDecimal getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(BigDecimal orderAmount) {
		this.orderAmount = orderAmount;
	}

	public String getVoiceFilePath() {
		return voiceFilePath;
	}

	public void setVoiceFilePath(String voiceFilePath) {
		this.voiceFilePath = voiceFilePath;
	}

	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="wechatuserInfoId")
	public WechatUserInfo getWechatUserInfo() {
		return wechatUserInfo;
	}

	public void setWechatUserInfo(WechatUserInfo wechatUserInfo) {
		this.wechatUserInfo = wechatUserInfo;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
	@Column(name = "ID", nullable = false, length = 32)
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}
}
