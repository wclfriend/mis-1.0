<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
<%@ include file="/common.jsp"%>
</head>
<body>
<article class="page-container">
	<form action="${ctx }/code/addG" method="post" class="form form-horizontal" id="form-member-add">
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
		<div class="row cl">
			<label class="form-label col-xs-3 col-sm-3"><span class="c-red">*</span>名称：</label>
			<div class="formControls col-xs-6 col-sm-9">
				<input type="text" class="input-text" placeholder="不能为空" name="codeName">
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-3 col-sm-3"><span class="c-red">*</span>代码：</label>
			<div class="formControls col-xs-6 col-sm-9">
				<input type="text" class="input-text" placeholder="不能为空" name="code">
			</div>
		</div>
		<div style="height: 30px;" >
		</div>
		<div class="row cl">
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
			"codeName":{
				required:true,
				minlength:1,
				maxlength:10
			},
			"code":{
				required:true,
				minlength:1,
				maxlength:50
			},
		},
		messages: {   
			"codeName": {       
	            required: "名称不能为空"  
	        },
	        "code":{   
	            required: "代码不能为空"
	        }
	    },
		onkeyup:false,
		focusCleanup:true,
		success:"valid",
		submitHandler:function(form){
			$(form).ajaxSubmit({
				 async : false,
				 success:function(data){
				 	layer.alert('更新成功', {icon: 1},function(){
					 	window.parent.location.reload();
						var index = parent.layer.getFrameIndex(window.name);
					 	parent.layer.close(index);
					});
				 }
			});
			
		}
	});
});
</script> 
<!--/请在上方写此页面业务相关的脚本-->
</body>
</html>