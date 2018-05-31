<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
<link rel="stylesheet" href="${ctx }/resources/lib/css/treegrid/jqx.base.css" type="text/css" />
<script type="text/javascript" src="${ctx }/resources/lib/js/treegrid/jquery-1.11.1.min.js"></script>

<script type="text/javascript" src="${ctx }/lib/layer/2.4/layer.js"></script>
<script type="text/javascript" src="${ctx }/static/h-ui/js/H-ui.min.js"></script>

<script type="text/javascript" src="${ctx }/resources/lib/js/treegrid/jqxcore.js"></script>
<script type="text/javascript" src="${ctx }/resources/lib/js/treegrid/jqxbuttons.js"></script>
<script type="text/javascript" src="${ctx }/resources/lib/js/treegrid/jqxscrollbar.js"></script>
<script type="text/javascript" src="${ctx }/resources/lib/js/treegrid/jqxdatatable.js"></script>
<script type="text/javascript" src="${ctx }/resources/lib/js/treegrid/jqxtreegrid.js"></script>
<script type="text/javascript" src="${ctx }/resources/lib/js/treegrid/jqxlistbox.js"></script>
<script type="text/javascript" src="${ctx }/resources/lib/js/treegrid/jqxdropdownbutton.js"></script>
<script type="text/javascript" src="${ctx }/resources/lib/js/treegrid/jqxdata.js"></script>
<script type="text/javascript" src="${ctx }/resources/lib/js/treegrid/jqxtooltip.js"></script>
<script type="text/javascript" src="${ctx }/resources/lib/js/treegrid/jqxinput.js"></script>
<script type="text/javascript" src="${ctx }/resources/lib/js/treegrid/demos.js"></script>
<%@ include file="/common.jsp" %>
<title>系统菜单</title>
</head>
<body>
	<a class="btn btn-success radius r" style="line-height:1.6em;margin-top:3px" 
	href="javascript:location.replace(location.href);" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a>
	<div id='content' style="padding-left: 20px;">
       <div id='treeGrid'>
       </div>
    </div>

<script type="text/javascript" src="${ctx }/lib/laypage/1.2/laypage.js"></script>
<script type="text/javascript" src="${ctx }/lib/laypage/1.2/layer.ext.js"></script>

<%-- <script type="text/javascript" src="${ctx }/lib/jquery/1.9.1/jquery.min.js"></script>  --%>
<%-- <script type="text/javascript" src="${ctx }/lib/layer/2.4/layer.js"></script> --%>
<%-- <script type="text/javascript" src="${ctx }/static/h-ui/js/H-ui.min.js"></script> --%>
<script type="text/javascript" src="${ctx }/static/h-ui.admin/js/H-ui.admin.js"></script>

<script type="text/javascript">
$(document).ready(function () {
	var newRowID = null;
    var source =
    {
        dataType: "json",
        dataFields: [
            { name: "id", type: "string" },
            { name: "code", type: "string" },
            { name: "tstypes", type: "children" },
            { name: "codeName", type: "string" }
        ],
        hierarchy:
        {
        	root: 'tstypes'
        },
        id: 'id',
        url: '${ctx}/code/list',
        addRow: function (rowID, rowData, position, parentID, commit) {
       		commit(true);
            newRowID = rowID;
        },
        updateRow: function (rowID, rowData, commit) {
           	commit(true);
            updateMenu(rowID,rowData);
        },
        deleteRow: function (rowID, commit) {
            delMenu(rowID);
            commit(true);
        }
    };
    var dataAdapter = new $.jqx.dataAdapter(source, {
        loadComplete: function () {
        	
        }
    });
    // create Tree Grid
    $("#treeGrid").jqxTreeGrid(
    {
        width: 850,
        source: dataAdapter, 
        sortable: true,
//         pageable: true,
        editable: true,
        showToolbar: true,
        altRows: false,
        ready: function () {
            $("#treeGrid").jqxTreeGrid('focus');
        },
        pagerButtonsCount: 80,
        toolbarHeight: 35,
        renderToolbar: function(toolBar)
        {
            var toTheme = function (className) {
                if (theme == "") return className;
                return className + " " + className + "-" + theme;
            }

            // appends buttons to the status bar.
            var container = $("<div style='overflow: hidden; position: relative; height: 100%; width: 100%;'></div>");
            var buttonTemplate = "<div style='float: left; padding: 3px; margin: 2px;'><div style='margin: 4px; width: 16px; height: 16px;'></div></div>";
            var addButton = $(buttonTemplate);
            var deleteButton = $(buttonTemplate);
            var refreshButton = $(buttonTemplate);
            var addGroupButton =  $(buttonTemplate);
            container.append(addGroupButton);
            container.append(addButton);
            container.append(deleteButton);
            container.append(refreshButton);
            

            toolBar.append(container);
            addGroupButton.jqxButton({cursor: "pointer", enableDefault: false, disabled: false, height: 25, width: 25 });
            addGroupButton.find('div:first').addClass(toTheme('jqx-icon-group-plus'));
            addGroupButton.jqxTooltip({ position: 'bottom', content: "添加分类"});

            toolBar.append(container);
            addButton.jqxButton({cursor: "pointer", enableDefault: false, disabled: true, height: 25, width: 25 });
            addButton.find('div:first').addClass(toTheme('jqx-icon-plus'));
            addButton.jqxTooltip({ position: 'bottom', content: "添加属性"});


            deleteButton.jqxButton({ cursor: "pointer", disabled: true, enableDefault: false,  height: 25, width: 25 });
            deleteButton.find('div:first').addClass(toTheme('jqx-icon-delete'));
            deleteButton.jqxTooltip({ position: 'bottom', content: "删除"});
			            
            refreshButton.jqxButton({ cursor: "pointer", disabled: true, enableDefault: false,  height: 25, width: 25 });
            refreshButton.find('div:first').addClass(toTheme('jqx-icon-refresh'));
            refreshButton.jqxTooltip({ position: 'bottom', content: "刷新"});

            var updateButtons = function (action) {
                switch (action) {
                    case "Select":
                    	var selection = $("#treeGrid").jqxTreeGrid('getSelection');
                    	if (selection[0].parent == null) {
                    		addButton.jqxButton({ disabled: false });	
                    	} else {
                    		addButton.jqxButton({ disabled: true });
                    	}
                        deleteButton.jqxButton({ disabled: false });
                        break;
                    case "Unselect":
                        addButton.jqxButton({ disabled: true });
                        deleteButton.jqxButton({ disabled: true });
                        break;
                    case "Edit":
                        addButton.jqxButton({ disabled: true });
                        deleteButton.jqxButton({ disabled: true });
                        break;
                    case "End Edit":
                        addButton.jqxButton({ disabled: false });
                        deleteButton.jqxButton({ disabled: false });
                        break;
                }
            }

            var rowKey = null;
            $("#treeGrid").on('rowSelect', function (event) {
                var args = event.args;
                rowKey = args.key;
                updateButtons('Select');
            });
            $("#treeGrid").on('rowUnselect', function (event) {
                updateButtons('Unselect');
            });
            $("#treeGrid").on('rowEndEdit', function (event) {
                updateButtons('End Edit');
            });
            $("#treeGrid").on('rowBeginEdit', function (event) {
                updateButtons('Edit');
            });
            addButton.click(function (event) {
                if (!addButton.jqxButton('disabled')) {
                    $("#treeGrid").jqxTreeGrid('expandRow', rowKey);
                    // add new empty row.
                    $("#treeGrid").jqxTreeGrid('addRow', null, {}, 'first', rowKey);
                    // select the first row and clear the selection.
                    $("#treeGrid").jqxTreeGrid('clearSelection');
                    $("#treeGrid").jqxTreeGrid('selectRow', newRowID);
                    // edit the new row.
                    $("#treeGrid").jqxTreeGrid('beginRowEdit', newRowID);
                    updateButtons('add');
                }
            });

            deleteButton.click(function () {
                if (!deleteButton.jqxButton('disabled')) {
                    var selection = $("#treeGrid").jqxTreeGrid('getSelection');
                    if (selection.length > 1) {
                        var keys = new Array();
                        for (var i = 0; i < selection.length; i++) {
                            keys.push($("#treeGrid").jqxTreeGrid('getKey', selection[i]));
                        }
                        $("#treeGrid").jqxTreeGrid('deleteRow', keys);
                    } else {
                        $("#treeGrid").jqxTreeGrid('deleteRow', rowKey);
                    }
                    updateButtons('delete');
                }
            });
            refreshButton.click(function(){
            	window.location.reload();
            });
            addGroupButton.click(function(){
            	layer_show("添加分类","${ctx}/web/system/basic/basic_addGroup.jsp",400,300);
            });
        },
        columns: [
          { text: '名称', dataField: "codeName", align: 'center', cellsAlign: 'left', width: '50%' },
          { text: '代码', dataField: "code", align: 'center', width: '50%' }
        ]
    });
});

function updateMenu(rowID,rowData) {
	var code = rowData.code;
	var codeName = rowData.codeName;
		
	var parentId = null;
	if (rowData.parent != null) {
		parentId = rowData.parent.id;
	}
	console.log(parentId);
	
	$.ajax({
		type : "POST",
		async : false,
		url : "${ctx}/code/update",
		dataType : 'JSON',
		data : {
			'id' : rowID,
			'code' : code,
			'codeName' : codeName,
			'parentId' : parentId,
			'${_csrf.parameterName}' : '${_csrf.token}'
		},
		success: function(data) {
			if (data.success) {
				layer.msg('操作成功',{time:2000});
			} else {
				layer.msg('失败，存在重复数据',{icon:2,time:2000});
			}
		},
	    error: function(XMLHttpRequest, textStatus, errorThrown) {
		},
		complete: function(XMLHttpRequest, textStatus) {
     	}
	});
}

function delMenu(rowID) {
	var id = "";
	if (rowID instanceof Array) {
		for(var i = 0;i < rowID.length; i ++) {
			id += rowID[i] + ",";
		}
	} else {
		id = rowID;
	}
	layer.confirm('确认要删除吗？',function(index){
		$.ajax({
			type: 'POST',
			url: '${ctx}/code/del',
			dataType: 'json',
			data: {
				'id' : id,
				'${_csrf.parameterName}' : '${_csrf.token}'
			},
			success: function(data){
				layer.msg('删除成功',{time:2000});
			},
			error:function(data) {
				console.log(data.msg);
			},
		});
	});
 }
</script>
</body>
</html>






