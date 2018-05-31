<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE HTML>
<html>
<%@ include file="/common.jsp" %>
<link href="${ctx }/lib/webuploader/0.1.5/webuploader.css" rel="stylesheet" type="text/css" />
<body>
<div class="page-container">
	<form action="${ctx }/user/update" method="POST" id="form_add" class="form form-horizontal">
		<input type="hidden" name="id" id="id" value="${systemUser.id }">
		<input type="hidden" name="_csrf" value="${_csrf.token}">
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>登录用户名：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text" value="${systemUser.userName }" placeholder="" id="userName" name="userName" >
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>真实姓名：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text" value="${systemUser.realName }" placeholder="" id="realName" name="realName">
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>角色：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<span class="select-box">
				</span>
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3">头像：</label>
			<div class="formControls col-xs-8 col-sm-9"> <span class="btn-upload form-group">
				<input class="input-text upload-url" type="text" name="uploadfile" id="uploadfile" readonly nullmsg="请上传头像" style="width:200px">
				<a href="javascript:void();" class="btn btn-primary radius upload-btn"><i class="Hui-iconfont">&#xe642;</i>浏览文件</a>
					<input type="file" multiple name="headIamge" class="input-file" id="headIamge" accept=".jpg,.png">
				</span>
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3">预览：</label>
			<div class="formControls col-xs-8 col-sm-9" id="localImag">
				<img id="preview" src=""/>
			</div>
		</div>
		
		<div class="row cl" style="padding-top: 20px;">
			<div class="col-xs-8 col-sm-9 col-xs-offset-4 col-sm-offset-3">
				<input class="btn btn-primary radius" type="submit" value="&nbsp;&nbsp;提交&nbsp;&nbsp;">
			</div>
		</div>
	</form>
</div>
<jsp:include page="/footer.jsp"></jsp:include>

<script type="text/javascript" src="${ctx }/lib/My97DatePicker/4.8/WdatePicker.js"></script>
<script type="text/javascript" src="${ctx }/lib/jquery.validation/1.14.0/jquery.validate.js"></script> 
<script type="text/javascript" src="${ctx }/lib/jquery.validation/1.14.0/validate-methods.js"></script> 
<script type="text/javascript" src="${ctx }/lib/jquery.validation/1.14.0/messages_zh.js"></script>
<script type="text/javascript" src="${ctx }/lib/webuploader/0.1.5/webuploader.min.js"></script> 
<script type="text/javascript" src="${ctx }/lib/ueditor/1.4.3/ueditor.config.js"></script>
<script type="text/javascript" src="${ctx }/lib/ueditor/1.4.3/ueditor.all.min.js"> </script>
<script type="text/javascript" src="${ctx }/lib/ueditor/1.4.3/lang/zh-cn/zh-cn.js"></script>
<script type="text/javascript">
var roleCodes = '${roleCodes}'.split("#");
function contains(obj) {
	for(var i = 0; i < roleCodes.length; i ++) {
		if (obj == roleCodes[i]) {
			return true;
		}
	}
	return false;
} 
	function selectLoad(){
		var html='';
		$.ajax({
			url: '${ctx}/role/show',
			type: "POST",
		    async : false,
			data:{
				'${_csrf.parameterName}' : '${_csrf.token}'
			},
			dataType:"json",
			success:function(data){
				for(var key in data){
					var res = contains(key);
					if (res) {
						html = html + '<input type="checkbox" name="roleCode" id="'+key+'" value="'+key+'" checked="checked">&nbsp;<label for="'+key+'">' + data[key] + '</label>';
					} else {
						html = html + '<input type="checkbox" name="roleCode" id="'+key+'" value="'+key+'" >&nbsp;<label for="'+key+'">' + data[key] + '</label>';
					}
					html += "&nbsp;&nbsp;&nbsp;";
				}
				$(".select-box").html(html);
			}
		});
	}
	selectLoad();
	
	$(document).ready(function(){
		$("#headIamge").change(function(){
			var docObj = document.getElementById("headIamge"); 
		    var imgObjPreview = document.getElementById("preview");
		    if (docObj.files && docObj.files[0]) {
		        //火狐下，直接设img属性
		        imgObjPreview.style.display = 'block';
		        imgObjPreview.style.width = '180px';
		        imgObjPreview.style.height = '180px';
		        //imgObjPreview.src = docObj.files[0].getAsDataURL();
		         
		        //火狐7以上版本不能用上面的getAsDataURL()方式获取，需要一下方式
		        imgObjPreview.src = window.URL.createObjectURL(docObj.files[0]);
		    } else {
		        //IE下，使用滤镜
		        docObj.select();
		        var imgSrc = document.selection.createRange().text;
		        var localImagId = document.getElementById("localImag");
		        //必须设置初始大小
		        localImagId.style.width = "180px";
		        localImagId.style.height = "180px";
		        //图片异常的捕捉，防止用户修改后缀来伪造图片
		        try {
		            localImagId.style.filter = "progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale)";
		            localImagId.filters.item("DXImageTransform.Microsoft.AlphaImageLoader").src = imgSrc;
		        } catch(e) {
		            alert("您上传的图片格式不正确，请重新选择!");
		            return false;
		        }
		        imgObjPreview.style.display = 'none';
		        document.selection.empty();
		    }
		    return true;
		});
	    
// 		$(".headImage").click(function(){
// 			  layui.upload({
// 			  url: '${ctx}/user/upload',
// 			  title: '请上传头像吧亲',
// 			  ext: 'JPG|PNG',
// 			  type: 'file',
// 			  data: {
// 				  '${_csrf.parameterName}' : '${_csrf.token}'
// 			  },
// 			  success: function(res, input){
// 				  alert("上传成功");
// 			  }
// 			});
// 		});
		 
		$('.skin-minimal input').iCheck({
			checkboxClass: 'icheckbox-blue',
			radioClass: 'iradio-blue',
			increaseArea: '20%'
		});
		
		$("#form_add").validate({
			rules:{
				"userName":{
					required:true,
					minlength:2,
					maxlength:16
				},
				"realName":{
					required:true,
					minlength:2,
					maxlength:16
				}
            },
			onkeyup:false,
			focusCleanup:true,
			submitHandler:function(form){
				$.ajax({
					url: '${ctx}/user/exist',
					type: "GET",
				    async : true,
					data:{
						'userName' : $("#userName").val(),
						'id' : $("#id").val(),
						'${_csrf.parameterName}' : '${_csrf.token}'
					},
					dataType:"json",
					success:function(data) {
						if (data.success) {
							$(form).ajaxSubmit({
								 async : false
							});
							layer.alert("操作成功", {icon: 1},function(){
							 	window.parent.location.reload();
								var index = parent.layer.getFrameIndex(window.name);
							 	parent.layer.close(index);
							});
						} else {
							layer.msg(data.msg, {icon: 2}, 1000);
						}
					}
				});
			}
		});
	});
</script>
</body>
</html>