<%@page import="com.alipay.api.request.AlipayTradePagePayRequest"%>
<%@page import="cn.com.cs.pay.alipay.controller.AlipayConfig"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="com.alipay.api.AlipayClient"%>
<%@ page import="com.alipay.api.DefaultAlipayClient"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>付款</title>
</head>
<%
// 	AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl, AlipayConfig.app_id, AlipayConfig.merchant_private_key);
	AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl, AlipayConfig.app_id, AlipayConfig.merchant_private_key, "json", AlipayConfig.charset, AlipayConfig.alipay_public_key, AlipayConfig.sign_type);
	
	AlipayTradePagePayRequest alipayRequest = (AlipayTradePagePayRequest)request.getAttribute("alipayRequest");
	
// 	String result = alipayClient.execute(alipayRequest).getBody();
	String result = alipayClient.pageExecute(alipayRequest).getBody();
	
	out.println(result);
%>
<body>
</body>
</html>