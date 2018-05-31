package cn.com.cs.pay.dto.wechat;

/**
 * 微信支付回调结果
 * 
 */
public class WechatPaymentResultDTO {
	/**返回状态码 */
	private String return_code;
	/**返回信息 */
	private String return_msg;
	//------------------------------------------------------------------------------------------------------------------------
	//当returnCode为SUCCESS时，返回如下信息
	/**公众号id*/
	private String appId;
	/**商户号 */
	private String mchId;
	/**随机字符串 */
	private String nonceStr;
	/**签名类型(MD5)*/
	private String sign;
	/**业务结果 */
	private String result_code;
	/**错误代码*/
	private String err_code;
	/**错误代码描述*/
	private String err_code_des; 
	//------------------------------------------------------------------------------------------------------------------------
	//以下字段在return_code 、result_code、trade_state都为SUCCESS时有返回
	/**trade_state 交易状态 判断支付结果当唯一标示*/
	/**
	 * SUCCESS—支付成功
	 * REFUND—转入退款
	 * NOTPAY—未支付
	 * CLOSED—已关闭
	 * REVOKED—已撤销（刷卡支付）
	 * USERPAYING--用户支付中
	 * PAYERROR--支付失败(其他原因，如银行返回失败)
	 * 支付状态机请见下单API页面 
	 * */
	private String trade_state;
	/**openid*/
	private String openid;
	/**是否关注公众账号 */
	private String isSubscribe;
	/***/
	private String tradeType;
	/**付款银行 */
	private String bankType;
	/**标价金额*/
	private String totalFee;
	/**标价币种*/
	private String feeType;
	/**现金支付金额 */
	private String cashFee;
	/**微信订单号（交易流水号）*/
	private String transactionId;
	/**商户订单号 */
	private String outTradeNo;
	/**业务结果*/
	private String resultCode;
	/**支付完成时间 */
	private String timeEnd;
	/**交易状态描述 */
	private String trade_state_desc;
	
	public String getReturn_code() {
		return return_code;
	}
	public void setReturn_code(String return_code) {
		this.return_code = return_code;
	}
	public String getReturn_msg() {
		return return_msg;
	}
	public void setReturn_msg(String return_msg) {
		this.return_msg = return_msg;
	}
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getMchId() {
		return mchId;
	}
	public void setMchId(String mchId) {
		this.mchId = mchId;
	}
	public String getNonceStr() {
		return nonceStr;
	}
	public void setNonceStr(String nonceStr) {
		this.nonceStr = nonceStr;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public String getResult_code() {
		return result_code;
	}
	public void setResult_code(String result_code) {
		this.result_code = result_code;
	}
	public String getErr_code() {
		return err_code;
	}
	public void setErr_code(String err_code) {
		this.err_code = err_code;
	}
	public String getErr_code_des() {
		return err_code_des;
	}
	public void setErr_code_des(String err_code_des) {
		this.err_code_des = err_code_des;
	}
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	public String getIsSubscribe() {
		return isSubscribe;
	}
	public void setIsSubscribe(String isSubscribe) {
		this.isSubscribe = isSubscribe;
	}
	public String getTradeType() {
		return tradeType;
	}
	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}
	public String getTrade_state() {
		return trade_state;
	}
	public void setTrade_state(String trade_state) {
		this.trade_state = trade_state;
	}
	public String getBankType() {
		return bankType;
	}
	public void setBankType(String bankType) {
		this.bankType = bankType;
	}
	public String getTotalFee() {
		return totalFee;
	}
	public void setTotalFee(String totalFee) {
		this.totalFee = totalFee;
	}
	public String getFeeType() {
		return feeType;
	}
	public void setFeeType(String feeType) {
		this.feeType = feeType;
	}
	public String getCashFee() {
		return cashFee;
	}
	public void setCashFee(String cashFee) {
		this.cashFee = cashFee;
	}
	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	public String getOutTradeNo() {
		return outTradeNo;
	}
	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
	}
	public String getResultCode() {
		return resultCode;
	}
	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}
	public String getTimeEnd() {
		return timeEnd;
	}
	public void setTimeEnd(String timeEnd) {
		this.timeEnd = timeEnd;
	}
	public String getTrade_state_desc() {
		return trade_state_desc;
	}
	public void setTrade_state_desc(String trade_state_desc) {
		this.trade_state_desc = trade_state_desc;
	}

	
}