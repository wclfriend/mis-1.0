<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE HTML>
<html>
<%@ include file="/common.jsp"%>
<body>
	<div class="pd-20">
		<table style="width: 600px;" cellpadding="0" cellspacing="1"
			class="table">
			<tr>
				<td align="right"><label class="Validform_label">
						头像: </label></td>
				<td class="value">
					<img src="${ctx }${user.headImage}" alt="头像" width="40" height="40"/>
				</td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label">
						登录用户名: </label></td>
				<td class="value">${user.userName}</td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label"> 真实姓名:
				</label></td>
				<td class="value">${user.realName}</td>
			</tr>
			<tr>
			</tr>
		</table>
	</div>
	<jsp:include page="/footer.jsp"></jsp:include>
	<!--请在下方写此页面业务相关的脚本-->
</body>
</html>