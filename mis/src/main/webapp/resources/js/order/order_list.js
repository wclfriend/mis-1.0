$(function(){
	var ctx = null;
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
				"typeName":name
			},
			dataType:"json",
			success:function(data){
				for(var key in data){
					html = html+'<option value="'+key+'">'+data[key]+'</option>' +'<br/>';
				}
				$(selector).html(html);
			}
		});
	}

	function addFile(id) {
		add('在线录音', 'wechatOrderFormController?addFile&id=' + id, "wechatOrderFormList");
	}
	function delay(id) {
		add('服务延期', 'wechatOrderFormController?delay&id=' + id, "wechatOrderFormList");
	}
	function stop() {
		$("#voice").html("");
	}
	
	
	$(".table-sort").on("dblclick", "tbody tr", function() {
		layer_show("订单明细",'${ctx}/order/detail?orderNo=' + datatable.fnGetData($(this)).orderSerialNumber,null,null);
	});
	
	$(".table-sort").on("click", "tbody tr .recored", function() {
		var orderNo = datatable.fnGetData($(this).parent().parent()).orderSerialNumber;
		layer_show("在线录音",'${ctx}/web/order/order_recorder.jsp?orderNo=' + orderNo,null,null);
	});
	
	var datatable = $('.table-sort').dataTable({
	    "bPaginate": true, //翻页功能  
	    "bLengthChange": true, //改变每页显示数据数量  
	    "bFilter": true, //过滤功能
	    "bSort": true, //排序功能
	    "bInfo": true,//页脚信息
	    'sPaginationType':"full_numbers",
	    "bServerSide" : true,//指定从服务器端获取数据  
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
		  {"orderable":false,"aTargets":[0,8,9]}// 制定列不参与排序
		],
		ajax: {
			url: "${ctx}/order/list?" + $(".form").serialize()
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
});




















