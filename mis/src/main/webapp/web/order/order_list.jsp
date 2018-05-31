<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
<%@ include file="/common.jsp" %>
<title>订单列表</title>
<style type="text/css">
td,th{
	font-size: 14px;
}
</style>
</head>
<body>
<nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i>首页<span class="c-gray en">&gt;</span> 用户中心 <span class="c-gray en">&gt;</span> 用户管理 <a class="btn btn-success radius r" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a></nav>
<div class="page-container">
	<form class="form" method="post" action="" target="_self">
			<div class="row cl text-l">
				<div class="col-xs-6 col-sm-3">
					订单类型： 
					<span class="select-box inline">
						<select name="orderType" class="select" style="width:108px"></select>
					</span>
				</div>
				<div class="col-xs-6 col-sm-3">
					服务方式： 
					<span class="select-box inline">
						<select name="serviceType" class="select" style="width:108px"></select>
					</span>
				</div>
				<div class="col-xs-6 col-sm-3">
					服务内容： 
					<span class="select-box inline">
						<select name="serviceContent" class="select" style="width:108px"></select>
					</span>
				</div>
				<div class="col-xs-6 col-sm-3">
					订单状态： 
					<span class="select-box inline">
						<select name="orderStatus" class="select" style="width:108px"></select>
					</span>
				</div>
			</div>
			<div class="row cl text-l">
				<div class="col-xs-6 col-sm-3">
					开始时间：
					<input type="text" onClick="WdatePicker()" name="startDate" class="input-text Wdate" style="width:120px;">
				</div>
				<div class="col-xs-6 col-sm-3">
					终止时间：
					<input type="text" onClick="WdatePicker()" name="endDate" class="input-text Wdate" style="width:120px;">
				</div>
				<div class="col-xs-6 col-sm-3">
					 客户姓名： 
					<input type="text" class="input-text" placeholder="客户姓名" name="userName" style="width:120px">
				</div>
				<div class="col-xs-6 col-sm-3">
					 手机号码： 
					<input type="text" class="input-text" placeholder="手机号" name="mobilePhoneNumber" style="width:120px">
				</div>
			</div>
			<div class="row cl text-l">
				<div class="col-xs-6 col-sm-3">
					 &nbsp;&nbsp;&nbsp;客户ID： 
					<input type="text" class="input-text" placeholder="客户ID" name="userId" style="width:120px">
				</div>
				<div class="col-xs-6 col-sm-3">
					 &nbsp;&nbsp;&nbsp;订单号： 
					<input type="text" class="input-text" placeholder="订单号" name="orderSerialNumber" style="width:120px">
				</div>
				<div class="col-xs-6 col-sm-3">
					 支付流水： 
					<input type="text" class="input-text" placeholder="支付流水号" name="transactionSerialNumber" style="width:120px">
				</div>
			</div>
			<div align="center" style="padding-top: 10px;">
				<button type="button" class="btn btn-success select search">查询</button>
				<button type="reset" class="btn btn-success select">重置</button>
			</div>
			<input type="hidden" name="${_csrf.parameterName}" id="_csrf" value="${_csrf.token}"/>
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
<script type="text/javascript" src="${ctx }/lib/laypage/1.2/datatables.optimize.js"></script>
<script type="text/javascript" src="${ctx }/lib/laypage/1.2/laypage.js"></script>
<script type="text/javascript" src="${ctx }/lib/laypage/1.2/layer.ext.js"></script>
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
		layer_show("订单明细",'${ctx}/order/detail?orderNo=' + datatable.fnGetData($(this)).orderSerialNumber,null,null);
	});
	
	$(".table-sort").on("click", "tbody tr .recored", function() {
		var orderNo = datatable.fnGetData($(this).parent().parent()).orderSerialNumber;
		layer_show("在线录音",'${ctx}/web/order/order_recorder.jsp?orderNo=' + orderNo,null,null);
	});
	
	function addFile(id) {
		add('在线录音', 'wechatOrderFormController?addFile&id=' + id, "wechatOrderFormList");
	}
	function delay(id) {
		add('服务延期', 'wechatOrderFormController?delay&id=' + id, "wechatOrderFormList");
	}
	function stop() {
		$("#voice").html("");
	}
	var datatable = null;
	function init() {
		datatable = $('.table-sort').dataTable({
			"bSort" : true,
			ajax: {
				url: "${ctx}/order/list?" + $(".form").serialize()
			},
			columns: [{
				data: "orderSerialNumber",
				title: "订单号",
			},{
				data: "orderStatus",
				title: "订单状态",
			},{
				data: "refundNumber",
				title: "退款单号",
			},{
				data: "orderType",
				title: "订单类型",
			},{
				data: "orderAmount",
				title: "金额",
			}, {
				data:null,
				title: "操作",
				render: function(data,type,row,meta){
					var str = '';
					if (data.orderType == '0') {
						if (data.orderStatus == '1') {
							var str = '<a style="text-decoration:none" class="play" title="播放"><i class="Hui-iconfont">&#xe6e6;</i></a>&nbsp;&nbsp';
							   str += '<a style="text-decoration:none" class="resend" title="停止"><i class="Hui-iconfont">&#xe6e4;</i></a>&nbsp;&nbsp';
						} else if (data.orderStatus == '0') {
						   str += '<a style="text-decoration:none" class="recored" title="停止"><i class="Hui-iconfont">&#xe66f;</i></a>&nbsp;&nbsp';
						}
					} else {
						if (data.orderStatus == '2') {
						   str += '<a style="text-decoration:none" class="close" title="关闭"><i class="Hui-iconfont">&#xe706;</i></a>&nbsp;&nbsp';
						} 
						if (data.delayFlag == '0') {
						   str += '<a style="text-decoration:none" class="time" title="延期"><i class="Hui-iconfont">&#xe606;</i></a>&nbsp;&nbsp';
						}
					}
					return str;
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