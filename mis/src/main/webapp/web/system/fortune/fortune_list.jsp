<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
<%@ include file="/common.jsp" %>
<title>用户管理</title>
</head>
<body>
<nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span> 系统管理 <span class="c-gray en">&gt;</span> 测算师信息 <a class="btn btn-success radius r" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a></nav>
<div class="page-container">
	<form>
	</form>
	<div class="cl pd-5 bg-1 bk-gray mt-20">
		<span class="l">
			<a href="javascript:;" onclick="fortune_add()" class="btn btn-primary radius">
				<i class="Hui-iconfont">&#xe600;</i> 添加测算师信息
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
<script type="text/javascript" src="${ctx }/lib/laypage/1.2/laypage.js"></script>
<script type="text/javascript">
$(function(){
	var datatable = $('.table-sort').dataTable({
	    "bPaginate": true, //翻页功能  
	    "bLengthChange": true, //改变每页显示数据数量  
	    "bFilter": true, //过滤功能
	    "bSort": true, //排序功能
	    "bInfo": true,//页脚信息
	    'sPaginationType':"full_numbers",
// 	    "bAutoWidth": true,//自动宽度
	    "oLanguage": {
		    "sLengthMenu": "每页显示 _MENU_条",
		    "sZeroRecords": "没有找到符合条件的数据",
		    "sProcessing": "&lt;img src=’./loading.gif’ /&gt;", 
		    "sInfo": "当前第 _START_ - _END_ 条　共计 _TOTAL_ 条", 
		    "sInfoEmpty": "木有记录",
		    "sInfoFiltered": "(从 _MAX_ 条记录中过滤)", 
		    "sSearch": "搜索：", 
	    	"oPaginate": {
			    "sFirst": "首页",
			    "sPrevious": "前一页",  
			    "sNext": "后一页",  
			    "sLast": "尾页"  
			    }
	    },
		"aaSorting": [[ 1, "desc" ]],//默认第几个排序
		"bStateSave": true,//状态保存
		"aoColumnDefs": [
		  {"orderable":false,"aTargets":[0]}// 制定列不参与排序
		],
		ajax: {
			url: "${ctx}/f/list",
			type: 'post',
		    async : false,
			data:{
				'${_csrf.parameterName}' : '${_csrf.token}'
			},
		},
		columns: [{
			data: "userName",
			title: "姓名",
		},{
			data: "mobilePhoneNumber",
			title: "手机号",
		},{
			data: "describes",
			title: "描述",
		},{
			data: "createTime",
			title: "创建时间",
		},{
			data: "remark",
			title: "备注",
		}, {
			data:null,
			title: "操作",
			render: function(data,type,row,meta){
				var str = '<a style="text-decoration:none" onclick="javascript:del(\''+data.id+'\');" title="删除"><i class="Hui-iconfont">&#xe6e2;</i></a>&nbsp;&nbsp';
				return str;
			}
		}]
	});
	$(".table-sort").on("dblclick", "tbody tr", function() {
		layer_show("测算师信息",'${ctx}/f/detail?id=' + datatable.fnGetData($(this)).id,null,null);
	});
});
/*管理员-角色-删除*/
function del(id){
	layer.confirm('删除须谨慎，确认要删除吗？',function(index){
		$.ajax({
			type: 'POST',
			url: '${ctx}/f/del',
			dataType: 'json',
			data: {
				'id' : id,
				'${_csrf.parameterName}' : '${_csrf.token}'
			},
			success: function(data){
				if (data.success) {
					layer.msg(data.msg,{icon:1,time:1000},function(){
						window.location.reload();
					});
				} else {
					layer.msg(data.msg,{icon:2,time:3000});
				}
			},
			error:function(data) {
				console.log(data.msg);
			},
		});		
	});
}

function fortune_add(title,url,w,h){
	layer_show("添加测算师","${ctx}/web/system/fortune/fortune_add.jsp",null,null);
}
</script> 
</body>
</html>