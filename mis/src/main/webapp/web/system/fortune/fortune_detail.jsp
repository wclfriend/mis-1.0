<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE HTML>
<html>
<%@ include file="/common.jsp" %>
<body>
<div class="cl pd-20" style=" background-color:#5bacb6">
	<img class="avatar size-XL l" src="${ctx }/static/h-ui/images/ucnter/avatar-default.jpg">
	<dl style="margin-left:80px; color:#fff">
		<dt>
			<span class="f-18">${f.userName }</span>
		</dt>
	</dl>
</div>
<div class="pd-20">
	<table style="width: 600px;" cellpadding="0" cellspacing="1" class="table">
		<tr>
			<td align="right">
				<label class="Validform_label">
					手机号:
				</label>
			</td>
			<td class="value">
				${f.mobilePhoneNumber}
			</td>
		</tr>
		<tr>
			<td align="right">
				<label class="Validform_label">
					备注:
				</label>
			</td>
			<td class="value">
				${f.remark}
			</td>
		</tr>
		<tr>
			<td align="right">
				<label class="Validform_label">
					创建时间:
				</label>
			</td>
			<td class="value">
				${f.createTime}
			</td>
		</tr>
	</table>
</div>
<jsp:include page="/footer.jsp"></jsp:include>

<!--请在下方写此页面业务相关的脚本-->
</body>
</html>