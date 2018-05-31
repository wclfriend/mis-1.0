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
			<span class="f-18">${order.fortuneTellerInfo.userName }</span>
		</dt>
	</dl>
</div>
<div class="pd-20">
<table style="width: 600px;" cellpadding="0" cellspacing="1" class="table">
				<tr>
					<td align="right">
						<label class="Validform_label">
							订单号:
						</label>
					</td>
					<td class="value">
						${order.orderSerialNumber}
					</td>
					<td align="right">
						<label class="Validform_label">
							退款单号:
						</label>
					</td>
					<td class="value">
						${order.refundNumber}
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							订单状态:
						</label>
					</td>
					<td class="value">
						${order.orderStatusName}
<%-- 						<t:dictSelect field="orderStatus" typeGroupCode="orderStatus" hasLabel="false" defaultVal="${order.orderStatus}"></t:dictSelect> --%>
					</td>
					<td align="right">
						<label class="Validform_label">
							订单类型:
						</label>
					</td>
					<td class="value">
						${order.orderTypeName}
<%-- 						<t:dictSelect field="orderType" typeGroupCode="orderType" hasLabel="false" defaultVal="${order.orderType}"></t:dictSelect> --%>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							服务方式:
						</label>
					</td>
					<td class="value">
						${order.serviceTypeName}
					</td>
					<td align="right">
						<label class="Validform_label">
							服务内容:
						</label>
					</td>
					<td class="value">
						${order.serviceContentName }
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							金额:
						</label>
					</td>
					<td class="value">
						${order.orderAmount}元
					</td>
					<td align="right">
						<label class="Validform_label">
							客户姓名:
						</label>
					</td>
					<td class="value">
						${order.wechatUserInfo.userName}
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							性别:
						</label>
					</td>
					<td class="value">
						${order.wechatUserInfo.sexName }
					</td>
					<td align="right">
						<label class="Validform_label">
							出生日期:
						</label>
					</td>
					<td class="value">
						${order.wechatUserInfo.birthday}
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							测算师:
						</label>
					</td>
					<td class="value">
						${order.fortuneTellerInfo.userName}
					</td>
					<td align="right">
						<label class="Validform_label">
							客户手机号:
						</label>
					</td>
					<td class="value">
						${order.wechatUserInfo.mobilePhoneNumber}
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							服务日期:
						</label>
					</td>
					<td class="value">
						${order.serviceDate}
					</td>
					<td align="right">
						<label class="Validform_label">
							在线录音:
						</label>
					</td>
					<td class="value">
						${order.analyseResultName }
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							服务时间:
						</label>
					</td>
					<td class="value">
						${order.serviceTimeName }
					</td>
					<td align="right">
						<label class="Validform_label">
							是否已延期:
						</label>
					</td>
					<td class="value">
						${order.delayFlagName}
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							延期后服务日期:
						</label>
					</td>
					<td class="value">
						<fmt:formatDate value="${order.modifiedServiceDate}" pattern="yyyy-MM-dd"/>
					</td>
					<td align="right">
						<label class="Validform_label">
							延期后的测算时间:
						</label>
					</td>
					<td class="value">
						${order.modifiedServiceTimeName}
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							创建日期:
						</label>
					</td>
					<td class="value">
						<fmt:formatDate value="${order.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							服务地址:
						</label>
					</td>
					<td class="value">
						${order.serviceAddress}
					</td>
				</tr>
				<tr>
				<td align="right">
						<label class="Validform_label">
							出生地址:
						</label>
					</td>
					<td class="value">
						${order.wechatUserInfo.bornAddress}
					</td>
				</tr>
			</table>
</div>
<jsp:include page="/footer.jsp"></jsp:include>

<!--请在下方写此页面业务相关的脚本-->
</body>
</html>