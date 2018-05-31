<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
<%@ include file="/common.jsp" %>
<title>角色列表</title>
<style type="text/css">
td,th{
	font-size: 14px;
}
</style>
</head>
<body>
<nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span> 系统管理 <span class="c-gray en">&gt;</span> 角色信息 <a class="btn btn-success radius r" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a></nav>
<div class="page-container">
	<div class="cl pd-5 bg-1 bk-gray mt-20">
		<span class="l">
			<a href="javascript:;" onclick="add()" class="btn btn-primary radius">
				<i class="Hui-iconfont">&#xe600;</i>添加角色
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
<script type="text/javascript" src="${ctx }/lib/datatables/1.10.0/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="${ctx }/lib/laypage/1.2/datatables.optimize.js"></script>
<script type="text/javascript" src="${ctx }/lib/laypage/1.2/laypage.js"></script>
<script type="text/javascript" src="${ctx }/lib/laypage/1.2/layer.ext.js"></script>

<script type="text/javascript">
function role(roleId){
	layer_show("授权","${ctx}/web/system/role/role_menu.jsp?roleId=" + roleId,300,400);
}
function init() {
	datatable = $('.table-sort').dataTable({
		ajax: {
			url: "${ctx}/role/list",
			type: 'POST',
		    async : false,
			data:{
				'${_csrf.parameterName}' : '${_csrf.token}'
			}
		},
		columns: [{
			data: "roleCode",
			title: "角色编码",
		},{
			data: "roleName",
			title: "角色名称",
		}, {
			data:null,
			title: "操作",
			render: function(data,type,row,meta){
				var str = '<a style="text-decoration:none" onclick="javascript:role(\''+data.id+'\');" title="权限控制"><i class="Hui-iconfont">&#xe61d;</i></a>&nbsp;&nbsp';
// 				   str += '<a style="text-decoration:none" onclick="javascript:edit(\''+data.id+'\');" title="编辑"><i class="Hui-iconfont">&#xe6df;</i></a>&nbsp;&nbsp';
				   str += '<a style="text-decoration:none" onclick="javascript:del(\''+data.id+'\');" title="删除"><i class="Hui-iconfont">&#xe6e2;</i></a>&nbsp;&nbsp';
				return str;
			}
		}]
	});
}
init();
function add() {
	layer_show("添加角色","${ctx}/web/system/role/role_edit.jsp",400,300);
};
function del(id){
	layer.confirm('角色删除须谨慎，确认要删除吗？',function(index){
		$.ajax({
			type: 'GET',
			url: '${ctx}/role/del/' + id,
			dataType: 'json',
			data:{
				'${_csrf.parameterName}' : '${_csrf.token}'
			},
			success: function(data) {
				if (data.success) {
					layer.msg('已删除!',{icon:1,time:1000},function() {
					 	window.location.reload();
					});
				} else {
					layer.msg(data.msg,{icon:2,time:2000});
				}
			},
			error:function(data) {
				console.log(data.msg);
			},
		});	
	});
};
function edit(id) {
	layer_show("编辑信息","${ctx}/role/edit/"+ id,400,300);
}
</script> 
</body>
</html>















