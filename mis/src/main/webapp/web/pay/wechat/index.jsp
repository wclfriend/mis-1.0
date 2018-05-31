<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script src="${ctx}/webpage/resources/lib/jweixin-1.0.0.js"></script>
<script src="${ctx}/plug-in/shop/js/jquery-1.9.1.min.js"></script>
</head>
<body>
<div style="padding-top: 100px;">
	ID:<input type="text" id="id">
</div>
<div style="padding-top: 100px;">
	<input type="button" onclick="ss()" name="but" value="支付" style="height: 60px;width: 100px;">
</div>
</body>
<script type="text/javascript">
function ss() {
	$.ajax({
		type : "GET",
		async : false,
		url : "${ctx }/pay/wechat/011",
		dataType : "json",
		data : {},
		success : function(data) {
			if (data.success) {
				var map = data.obj;
				WeixinJSBridge.invoke('getBrandWCPayRequest', {
				    "appId": map.appId,
				    "timeStamp": map.timeStamp,
				    "nonceStr": map.nonceStr,
				    "package": map.package,
				    "signType": map.signType,
				    "paySign": map.paySign
				}, function(res) {
					if (res.err_msg == 'get_brand_wcpay_request:ok') {
						alert("success");
					} else {
						alert("failed");
					}
				});
			} else {
				alert(data.msg);
			}
		},
		error:function(XMLHttpRequest, textStatus, errorThrown) {
		}
	});
}

</script>
</html>