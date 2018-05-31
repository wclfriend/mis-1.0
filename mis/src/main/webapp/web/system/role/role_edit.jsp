<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
<%@ include file="/common.jsp"%>
<title>角色信息</title>
</head>
<body>
<article class="page-container">
	<form action="${ctx }/role/modify" method="post" class="form form-horizontal" id="form-member-add">
		<input type="hidden" id="id" name="id">
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>角色编码：</label>
			<div class="formControls col-xs-6 col-sm-9">
				<input type="text" class="input-text" value="${role.roleCode}" placeholder="只能输入英文" name="roleCode">
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>角色名称：</label>
			<div class="formControls col-xs-6 col-sm-9">
				<input type="text" class="input-text" value="${role.roleName }" placeholder="只能输入中文" name="roleName">
			</div>
		</div>
		<div class="row cl" style="padding-top: 30px;">
			<div class="col-xs-8 col-sm-9 col-xs-offset-4 col-sm-offset-3">
				<input class="btn btn-primary radius" type="submit" value="&nbsp;&nbsp;提交&nbsp;&nbsp;">
			</div>
		</div>
	</form>
</article>

<!--_footer 作为公共模版分离出去-->
<jsp:include page="/footer.jsp"></jsp:include>
<!--/_footer 作为公共模版分离出去-->

<!--请在下方写此页面业务相关的脚本--> 
<script type="text/javascript" src="${ctx }/lib/My97DatePicker/4.8/WdatePicker.js"></script>
<script type="text/javascript" src="${ctx }/lib/jquery.validation/1.14.0/jquery.validate.js"></script> 
<script type="text/javascript" src="${ctx }/lib/jquery.validation/1.14.0/validate-methods.js"></script> 
<script type="text/javascript" src="${ctx }/lib/jquery.validation/1.14.0/messages_zh.js"></script>
<script type="text/javascript">
$(function(){
	$('.skin-minimal input').iCheck({
		checkboxClass: 'icheckbox-blue',
		radioClass: 'iradio-blue',
		increaseArea: '20%'
	});
	
	$("#form-member-add").validate({
		rules:{
			"roleName":{
				required:true,
				minlength:2,
				maxlength:20,
				isChinese:true
			},
			"roleCode":{
				required:true,
				minlength:2,
				maxlength:20,
				isEnglish:true
			}
		},
		messages: {   
			"roleName": {
	            required: "角色名称不能为空",
	            isChinese:"只能输入中文"
	        },
	        "roleCode":{   
	            required: "角色编码不能为空",
	            isEnglish: "只能输入英文"
	        }
	    },
		onkeyup:false,
		focusCleanup:true,
		success:"valid",
		submitHandler:function(form){
			$(form).ajaxSubmit({
				 async : false
			});
			layer.msg('添加成功!',{icon:1,time:1000},function() {
			 	window.parent.location.reload();
			});
		}
	});
});
</script> 
<!--/请在上方写此页面业务相关的脚本-->
</body>
</html>