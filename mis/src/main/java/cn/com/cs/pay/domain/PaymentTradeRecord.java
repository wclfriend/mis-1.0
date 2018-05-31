package cn.com.cs.pay.domain;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.persistence.Version;

import org.hibernate.annotations.GenericGenerator;

import cn.com.cs.common.pojo.IdEntity;

@Entity
@Table(name = "pay_tradeRecord")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn( name = "payType" ,discriminatorType = DiscriminatorType.STRING)
public class PaymentTradeRecord extends IdEntity {
	
	@Id
	private String id;
	
	@Version
	private int version;

	private static final long serialVersionUID = 1L;
	
	/**交易流水号*/
	private String tradeNo;
	
	/**商户订单号 */
	private String ouTradeNo;
	
	/**支付次数*/
	private int count;

	/**交易金额 */
	private BigDecimal totalAmount;

	/**实收金额*/
	private BigDecimal receiptAmount;

	/**买家付款的金额*/
	private BigDecimal buyerPayAmount;

	/**使用积分抵扣金额*/
	private BigDecimal pointAmount;

	/**交易中可给用户开具发票的金额 */
	private BigDecimal invoiceAmount;

	/** 交易支付时间 yyyy-MM-dd HH:mm:ss*/
	private Date payTime;
	
	/**省*/
	private String province;
	
	/**市*/
	private String city;

	/**银行代码*/
	private String bankCode;

	/**银行账号*/
	private String bankAccountNo;

	/**支付方式*/
	private String payType;
	
	/**资金渠道*/
	private String fundChannel;

	/**支付工具类型所使用的金额 */
	private BigDecimal amount;

	/**渠道实际付款金额 */
	private BigDecimal realAmount;

	/**支付宝卡余额 */
	private BigDecimal cardBalance;

	/**发生支付交易的商户门店名称 */
	private String storeName;

	/**买家在支付宝的用户id */
	private String buyerUserId;

	public String getTradeNo() {
		return tradeNo;
	}

	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}

	public String getOuTradeNo() {
		return ouTradeNo;
	}

	public void setOuTradeNo(String ouTradeNo) {
		this.ouTradeNo = ouTradeNo;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public BigDecimal getReceiptAmount() {
		return receiptAmount;
	}

	public void setReceiptAmount(BigDecimal receiptAmount) {
		this.receiptAmount = receiptAmount;
	}

	public BigDecimal getBuyerPayAmount() {
		return buyerPayAmount;
	}

	public void setBuyerPayAmount(BigDecimal buyerPayAmount) {
		this.buyerPayAmount = buyerPayAmount;
	}

	public BigDecimal getPointAmount() {
		return pointAmount;
	}

	public void setPointAmount(BigDecimal pointAmount) {
		this.pointAmount = pointAmount;
	}

	public BigDecimal getInvoiceAmount() {
		return invoiceAmount;
	}

	public void setInvoiceAmount(BigDecimal invoiceAmount) {
		this.invoiceAmount = invoiceAmount;
	}

	public Date getPayTime() {
		return payTime;
	}

	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public String getBankAccountNo() {
		return bankAccountNo;
	}

	public void setBankAccountNo(String bankAccountNo) {
		this.bankAccountNo = bankAccountNo;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getFundChannel() {
		return fundChannel;
	}

	public void setFundChannel(String fundChannel) {
		this.fundChannel = fundChannel;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getRealAmount() {
		return realAmount;
	}

	public void setRealAmount(BigDecimal realAmount) {
		this.realAmount = realAmount;
	}

	public BigDecimal getCardBalance() {
		return cardBalance;
	}

	public void setCardBalance(BigDecimal cardBalance) {
		this.cardBalance = cardBalance;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public String getBuyerUserId() {
		return buyerUserId;
	}

	public void setBuyerUserId(String buyerUserId) {
		this.buyerUserId = buyerUserId;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
	@Column(name = "ID", nullable = false, length = 32)
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	@Version
	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}
}
