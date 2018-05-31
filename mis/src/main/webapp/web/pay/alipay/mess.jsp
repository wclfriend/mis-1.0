<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
<%@ include file="/common.jsp"%>
<title>Test</title>
</head>
<body>
<form action="/">
${mess }
<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
</form>
<jsp:include page="/footer.jsp"></jsp:include>
<script type="text/javascript">
$(function(){
	
});
</script> 


</body>
</html>