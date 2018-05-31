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
					 &nbsp;&nbsp;&nbsp;订单号： 
					<input type="text" class="input-text" placeholder="订单号" name="orderSerialNumber" style="width:120px">
				</div>
				<div class="col-xs-6 col-sm-3">
					 &nbsp;&nbsp;&nbsp;支付流水号： 
					<input type="text" class="input-text" placeholder="支付流水号" name="transactionSerialNumber" style="width:120px">
				</div>
				<div class="col-xs-6 col-sm-3">
					 &nbsp;&nbsp;&nbsp;退款单号： 
					<input type="text" class="input-text" placeholder="退款单号" name="refundNumber" style="width:120px">
				</div>
				<div class="col-xs-6 col-sm-3">
					 &nbsp;&nbsp;&nbsp;退款流水号： 
					<input type="text" class="input-text" placeholder="退款流水号" name="transactionRefundNumber" style="width:120px">
				</div>
			</div>
			<div class="row cl text-l">
				<div class="col-xs-6 col-sm-3">
					 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;金额： 
					<input type="text" class="input-text" placeholder="金额" name="refundAmount" style="width:120px">
				</div>
				<div class="col-xs-6 col-sm-3">
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;退款状态： 
					<span class="select-box inline">
						<select name="refundStatus" class="select" style="width:108px"></select>
					</span>
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
	
	var datatable = null;
	function init() {
		datatable = $('.table-sort').dataTable({
// 			"bSort" : true,
// // 			bServerSide : false,
			ajax: {
				url: "${ctx}/refund/list?" + $(".form").serialize()
			},
			columns: [{
				data: "orderSerialNumber",
				title: "订单号",
			},{
				data: "orderStatusName",
				title: "订单状态",
			},{
				data: "refundNumber",
				title: "退款单号",
			},{
				data: "orderTypeName",
				title: "订单类型",
			},{
				data: "serviceTypeName",
				title: "服务方式",
			},{
				data: "serviceContentName",
				title: "服务内容",
			},{
				data: "orderAmount",
				title: "金额",
			},{
				data: "wechatUserInfo.userName",
				title: "姓名",
			},{
				data: "wechatUserInfo.mobilePhoneNumber",
				title: "手机号",
			},{
				data: "analyseResultName",
				title: "是否录音",
			},{
				data: "createTime",
				title: "创建时间",
			},{
				data: "delayFlagName",
				title: "是否已延期",
			},{
				data: "fortuneTellerInfo.userName",
				title: "测算师",
			}, {
				data:null,
				title: "操作",
				render: function(data,type,row,meta){
					var str = '';
					if (data.orderType == '0') {
						if (data.orderStatus == '1') {
							str += '<a class="play" title="播放">播放</a>&nbsp;&nbsp';
							str += '<a class="resend" title="停止">停止</a>&nbsp;&nbsp'
						} else if (data.orderStatus == '0') {
							str += '<a class="recored" title="去录音">去录音</a>&nbsp;&nbsp';
						}
					} else {
						if (data.orderStatus == '2') {
							str += '<a class="play" title="关闭">关闭</a>&nbsp;&nbsp';
						} 
						if (data.delayFlag == '0') {
							str += '<a class="recored" title="延期">延期</a>';
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