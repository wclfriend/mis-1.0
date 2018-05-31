<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
<%@ include file="/common.jsp" %>
<title>系统用户</title>
<style type="text/css">
td,th{
	font-size: 14px;
}
</style>
</head>
<body>
<nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span> 系统管理 <span class="c-gray en">&gt;</span> 用户信息 <a class="btn btn-success radius r" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a></nav>
<div class="page-container">
	<form class="form" method="post" action="" target="_self">
			<div class="row cl text-l">
			</div>
	</form>
	<div class="cl pd-5 bg-1 bk-gray mt-20">
		<span class="l">
			<a href="javascript:;" onclick="user_add()" class="btn btn-primary radius">
				<i class="Hui-iconfont">&#xe600;</i>添加用户
			</a>
		</span>
	</div>
	<div class="mt-20">
	<table class="table table-border table-bordered table-hover table-bg table-sort">
	</table>
	</div>
</div>
<jsp:include page="/footer.jsp"></jsp:include>

<!--请在下方写此页面业务相关的脚本-->
<script type="text/javascript" src="${ctx }/lib/My97DatePicker/4.8/WdatePicker.js"></script> 
<script type="text/javascript" src="${ctx }/lib/datatables/1.10.0/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="${ctx }/lib/laypage/1.2/datatables.optimize.js"></script>
<script type="text/javascript" src="${ctx }/lib/laypage/1.2/laypage.js"></script>
<script type="text/javascript" src="${ctx }/lib/laypage/1.2/layer.ext.js"></script>
<script type="text/javascript">
function user_add(title,url,w,h){
	layer_show("添加客户","${ctx}/web/system/user/user_edit.jsp",null,null);
}
function edit(id) {
	layer_show('编辑', '${ctx}/user/edit?id=' + id,null,null);
}
function reset(id) {
	layer_show('修改密码', '${ctx}/web/system/user/change_pwd.jsp?id=' + id,700,500);
}
function del(id) {
	layer.confirm('确认要删除吗？',function(index){
		$.ajax({
			type: 'GET',
			url: '${ctx}/user/del/' + id,
			dataType: 'json',
			data:{
				'${_csrf.parameterName}' : '${_csrf.token}'
			},
			success: function(data){
				if (data.success) {
					layer.msg('已删除!',{icon:1,time:1000},function() {
					 	window.location.reload();
					});
				} else {
					layer.alert(data.msg);
				}
			},
			error:function(data) {
				console.log(data.msg);
			},
		});
	});
}
function lock(id) {
	$.ajax({
		type: 'GET',
		url: '${ctx}/user/lock/' + id,
		dataType: 'json',
		data:{
			'${_csrf.parameterName}' : '${_csrf.token}'
		},
		success: function(data){
			if (data.success) {
				layer.msg(data.msg,{icon:1,time:1000},function() {
				 	window.location.reload();
				});
			} else {
				layer.msg(data.msg,{icon:2,time:1000});
			}
		},
		error:function(data) {
			console.log(data.msg);
		},
	});
}
$(function(){
	$(".table-sort").on("dblclick", "tbody tr", function() {
		layer_show("系统用户信息",'${ctx}/user/detail/' + datatable.fnGetData($(this)).userName, 650,350);
	});
	
	var datatable = null;
	function init() {
		datatable = $('.table-sort').dataTable({
			ajax: {
				url: "${ctx}/user/list?" + $(".form").serialize()
			},
			columns: [{
				data: "userName",
				title: "用户名",
			},{
				data: "realName",
				title: "真实姓名",
			},{
				data: "lockFlag",
				title: "账号状态",
				render: function(data,type,row,meta){
					var res = null;
					if (row.lockFlag) {
						res = "<span class='label label-success radius'>正常</span>";
					} else {
						res = "<span class='label label-defaunt radius'>已停用</span>";
					}
					return res;
				}
			}, {
				data:null,
				title: "操作",
				render: function(data,type,row,meta){
					var str = "";
				   	if (!data.admin) {
						str  = '<a style="text-decoration:none" onclick="javascript:edit(\''+data.id+'\');" title="编辑"><i class="Hui-iconfont">&#xe6df;</i></a>&nbsp;&nbsp';
					   	str += '<a style="text-decoration:none" onclick="javascript:del(\''+data.id+'\');" title="删除"><i class="Hui-iconfont">&#xe6e2;</i></a>&nbsp;&nbsp';
						if (data.lockFlag) {
							   str += '<a style="text-decoration:none" onclick="javascript:lock(\''+data.id+'\');" title="停用"><i class="Hui-iconfont">&#xe631;</i></a>&nbsp;&nbsp';
						} else {
						   str += '<a style="text-decoration:none" onclick="javascript:lock(\''+data.id+'\');" title="启用"><i class="Hui-iconfont">&#xe6e1;</i></a>&nbsp;&nbsp';
						}
						str += '<a style="text-decoration:none" onclick="javascript:reset(\''+data.id+'\');" title="修改密码"><i class="Hui-iconfont">&#xe63f;</i></a>&nbsp;&nbsp';
				   	}
					return str;
				}
			}]
		});
	}
	init();
			
	$(".btn-success").click(function(){
		datatable.fnDestroy();
		init();
	});
});
</script> 
</body>
</html>