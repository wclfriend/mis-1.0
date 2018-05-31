<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE HTML>
<html>
<%@ include file="/common.jsp" %>
<body>
<div class="cl pd-20" style=" background-color:#5bacb6">
	<img class="avatar size-XL l" src="${user.image }">
	<dl style="margin-left:80px; color:#fff">
		<dt>
			<span class="f-18">${user.userName }</span>
		</dt>
	</dl>
</div>
<div class="pd-20">
<table style="width: 600px;" cellpadding="0" cellspacing="1" class="table">
				<tr>
					<td align="right">
						<label class="Validform_label">
							客户号:
						</label>
					</td>
					<td class="value">
						${user.userId}
					</td>
					<td align="right">
						<label class="Validform_label">
							OpenId:
						</label>
					</td>
					<td class="value">
						${user.openId}
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							性别:
						</label>
					</td>
					<td class="value">
						${user.sex}
					</td>
					<td align="right">
						<label class="Validform_label">
							客户状态:
						</label>
					</td>
					<td class="value">
						${user.userStatus }
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							生日:
						</label>
					</td>
					<td class="value">
						${user.birthday}
					</td>
					<td align="right">
						<label class="Validform_label">
							年龄:
						</label>
					</td>
					<td class="value">
						${user.age}
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							手机号:
						</label>
					</td>
					<td class="value">
						${user.mobilePhoneNumber }
					</td>
					<td align="right">
						<label class="Validform_label">
							昵称:
						</label>
					</td>
					<td class="value">
						${user.nickname}
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							农历生日:
						</label>
					</td>
					<td class="value">
						${user.traditionalBirthday}
					</td>
					<td align="right">
						<label class="Validform_label">
							时辰:
						</label>
					</td>
					<td class="value">
						${user.season}
					</td>
				</tr>
				
				<tr>
					<td align="right">
						<label class="Validform_label">
							出生地址:
						</label>
					</td>
					<td class="value" colspan="3">
						${user.bornAddress}
					</td>
				</tr>
			</table>
</div>
<jsp:include page="/footer.jsp"></jsp:include>

<!--请在下方写此页面业务相关的脚本-->
</body>
</html>