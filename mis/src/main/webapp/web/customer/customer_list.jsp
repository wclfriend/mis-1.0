<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
<%@ include file="/common.jsp" %>
<title>用户管理</title>
</head>
<body>
<nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span> 客户信息 <span class="c-gray en">&gt;</span> 客户管理 <a class="btn btn-success radius r" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a></nav>
<div class="page-container">
	<form class="form" method="post" action="" target="_self">
		<div class="row cl text-l">
			<div class="col-xs-6 col-sm-2">
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;姓名： 
				<input type="text" name="userName" class="input-text" style="width:120px">
			</div>
			<div class="col-xs-6 col-sm-2">
				手机号： 
				<input type="text" name="mobilePhone"  class="input-text" style="width:140px">
			</div>
			<div class="col-xs-6 col-sm-2">
				客户号： 
				<input type="text" name="userId"  class="input-text" style="width:120px">
			</div>
			<div class="col-xs-6 col-sm-2">
				性别： 
				<span class="select-box inline">
					<select name="sex" class="select" style="width:108px"></select>
				</span>
			</div>
			<div class="col-xs-6 col-sm-2">
				客户状态： 
				<span class="select-box inline">
					<select name="userStatus" class="select" style="width:108px"></select>
				</span>
			</div>
		</div>
		
		<div align="center" style="padding-top: 10px;">
			<button type="button" class="btn btn-success search">查询</button>
			<button type="reset" class="btn btn-success">重置</button>
		</div>
	</form>
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
	function loadSelect(){
		var list = $("select"); 
		for(var i=0;i<list.length;i++){
			var name = list[i].name;
			if(name){
				selectLoad("select[name="+name+"]",name);
			}
		}
	}

	function selectLoad(selector,name){
		var html='<option value="">请选择...</option><br/>';
		$.ajax({
			url: '${ctx}/code/init',
			type: "POST",
		    async : false,
		    contentType : "application/x-www-form-urlencoded; charset=utf-8",
			data:{
				"typeName":name,
				'${_csrf.parameterName}' : '${_csrf.token}'
			},
			dataType:"json",
			success:function(data){
				for(var key in data){
					html = html+'<option value="'+key+'">'+data[key]+'</option>';
				}
				$(selector).html(html);
			}
		});
	}
	loadSelect();
	$(".table-sort").on("dblclick", "tbody tr", function() {
		layer_show("客户信息",'${ctx}/customer/detail?userId=' + datatable.fnGetData($(this)).userId,null,null);
	});
	
	var datatable = null;
	function init() {
		datatable = $('.table-sort').dataTable({
		    "bPaginate": true, //翻页功能
		    "bLengthChange": true, //改变每页显示数据数量  
		    "bFilter": true, //过滤功能
		    "bSort": true, //排序功能
		    "bInfo": true,//页脚信息
		    'sPaginationType':"full_numbers",
//	 	    "bAutoWidth": true,//自动宽度
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
// 			  {"orderable":false,"aTargets":[0,8,9]}// 制定列不参与排序
			],
			ajax: {
				url: "${ctx}/customer/list?" + $(".form").serialize()
			},
			columns: [{
				data: "userId",
				title: "客户号",
			},{
				data: "openId",
				title: "openId",
			},{
				data: "userName",
				title: "姓名",
			},{
				data: "sexName",
				title: "性别",
			},{
				data: "userStatusName",
				title: "客户状态",
			},{
				data: "birthday",
				title: "生日",
			},{
				data: "age",
				title: "年龄",
			},{
				data: "mobilePhoneNumber",
				title: "手机号",
			},{
				data: "createTime",
				title: "创建时间",
			},{
				data: "traditionalBirthday",
				title: "农历生日",
				render: function(data, type, full, meta ) {
					var str = "";
					if (data != null) {
						str = data;
					}
					return str;
			    }
			},{
				data: "seasonName",
				title: "时辰",
			}, {
				data:null,
				title: "操作",
				render: function(data,type,row,meta){
					var btnStr = ''
					return btnStr;
				}
			}]
		});
	}
	init();
	
	$(".search").click(function(){
		datatable.fnDestroy();
		init();
	});
});
</script> 
</body>
</html>