$.extend($.fn.dataTable.defaults, {
	dom: 'ltrip',
	ordering:false,
	language: {
	    "sProcessing":   "正在获取数据，请稍后...",
	    "sLengthMenu":   "每页显示 _MENU_ 条记录",
	    "sZeroRecords":  "<strong>未查询到数据</strong>",
	    "sInfo":         "显示第 _START_ 至 _END_ 项结果，共 _TOTAL_ 项",
	    "sInfoEmpty":    "显示第 0 至 0 项结果，共 0 项",
	    "sInfoFiltered": "",
	    "sSearch":       "搜索:",
	    "sUrl":          "",
	    "sEmptyTable":     "表中数据为空",
	    "sLoadingRecords": "载入中...",
	    "oPaginate": {
	        "sFirst" : "首页",
	        "sPrevious" : "上一页",
	        "sNext" : "下一页",
	        "sLast" : "末页"
	    },
	    "oAria": {
	        "sSortAscending":  ": 以升序排列此列",
	        "sSortDescending": ": 以降序排列此列"
	    }
	},
	processing: true, //当datatable获取数据时候是否显示正在处理提示信息。
	serverSide: true, //服务器处理分页
	responsive: {
		details: false
	},
	/*initComplete: function(settings) {
		var _$this = this;

		*//**
		 * 重写搜索事件
		 *//*
		$('#doSearch').bind('click', function(e) {
			_$this.api().ajax.reload();
		});
		$('#search').bind('keyup', function(e) {
			if(e.keyCode == 13 || (e.keyCode == 8 && (this.value.length == 0))) {
				_$this.api().ajax.reload();
			}
		});
	},*/
	/*drawCallback: drawCallbackDefault*/
});

/**
 * DT绘制完成默认回调函数
 * 单独写出来是方便二次定制
 * 
 * 默认回调函数功能：
 * 1.DT第一列checkbox初始化成icheck
 * 2.iCheck全选、取消多选、多选与单选双向关联
 * 3.选中的tr加上selected class
 * 
 * @param {Object} settings
 */
function drawCallbackDefault(settings, _$this) {
	_$this = (isExitsVariable('_$this') && _$this) ? _$this : this;
	selector = _$this.selector;
	$(selector + ' input').iCheck({
		checkboxClass: 'icheckbox_minimal',
		increaseArea: '20%'
	});

	/**
	 * DT thead iCheck 点击事件
	 */
	$(selector).on('ifChecked ifUnchecked','input[name=all]', function(e) {
		$(this).closest('table').find('input[name=single]').each(function() {
			if(e.type == 'ifChecked') {
				$(this).iCheck('check');
				$(this).closest('tr').addClass('selected');
			} else {
				$(this).iCheck('uncheck');
				$(this).closest('tr').removeClass('selected');
			}
		});
	});

	/**
	 * DT tbody iCheck点击事件
	 */
	$(selector).on('ifChecked ifUnchecked','input[name=single]', function(e) {
		if(e.type == 'ifChecked') {
			$(this).iCheck('check');
			$(this).closest('tr').addClass('selected');
			//全选单选框的状态处理
			var selected = _$this.api().rows('.selected').data().length; //被选中的行数
			var recordsDisplay = _$this.api().page.info().recordsDisplay; //搜索条件过滤后的总行数
			var iDisplayStart = _$this.api().page.info().start; // 起始行数
			if(selected === _$this.api().page.len() || selected === recordsDisplay || selected === (recordsDisplay - iDisplayStart)) {
				$(selector + ' input[name=all]').iCheck('check');
			}
		} else {
			$(this).iCheck('uncheck');
			$(this).closest('tr').removeClass('selected');
			$(selector + ' input[name=all]').attr('checked', false);
			$(selector + ' input[name=all]').iCheck('update');
		}
	});
}