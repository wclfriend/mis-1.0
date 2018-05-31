<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
<%@ include file="/common.jsp"%>

<title>Test</title>
</head>
<body class='default'>
    <div id="treeGrid">
    </div>
<jsp:include page="/footer.jsp"></jsp:include>
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
<link rel="stylesheet" href="${ctx }/resources/lib/css/treegrid/jqx.base.css" type="text/css" />

<script type="text/javascript">
$(function(){
	
});
</script>
   <script type="text/javascript">
   		var jsonStr = [
   		    {
   		        "id": "A1", 
   		        "createTime": "2017-05-12 16:22", 
   		        "modifyTime": "2017-04-12 08:30", 
   		        "remark": null, 
   		        "menuName": "订单管理", 
   		        "url": "", 
   		        "text": null, 
   		        "sortOrder": 1, 
   		        "parentId": null, 
   		        "children": [
   		            {
   		                "id": "A2", 
   		                "createTime": "2017-05-12 16:25", 
   		                "modifyTime": "2017-04-12 08:30", 
   		                "remark": null, 
   		                "menuName": "订单列表", 
   		                "url": "/web/order/order_list.jsp", 
   		                "text": null, 
   		                "sortOrder": 1, 
   		                "parentId": "A1"
   		            }, 
   		            {
   		                "id": "A13", 
   		                "createTime": "2017-05-1216:25", 
   		                "modifyTime": "2017-04-2402:10", 
   		                "remark": null, 
   		                "menuName": "退款订单", 
   		                "url": "/web/refund/refund_list.jsp", 
   		                "text": null, 
   		                "sortOrder": 1, 
   		                "parentId": "A1"
   		            }
   		        ]
   		    }
   		];
        $(document).ready(function () {
            $("#treeGrid").jqxTreeGrid(
            {
                width: 850,
                pageable: true,
                altRows: true,
                virtualModeCreateRecords: function (expandedRecord, done) {
                    var source =
                    {
                        dataType: "json",
                        dataFields: [
                            { name: "id", type: "string" },
                            { name: "menuName", type: "string" },
                            { name: "parentId", type: "string" },
                            { name: "url", type: "string" },
                            { name: "sortOrder", type: "string" }
                        ],
                        hierarchy:
                        {
                        	root: "children"
//                             keyDataField: { name: 'id' },
//                             parentDataField: { name: 'parentId' }
                        },
						localData: jsonStr,
                        id : "id"
                    }
                    var dataAdapter = new $.jqx.dataAdapter(source, {
                        loadComplete: function () {
                            done(dataAdapter.records);
                        }
                    });
                    dataAdapter.dataBind();
                },
                virtualModeRecordCreating: function (record) {
                    if (record.level == 2) {
                        record.leaf = true;
                    }
                },
                columns: [
                    { text: 'No.1', dataField: "id", align: 'center', width: 300 },
                    { text: 'No.2', dataField: "menuName", cellsAlign: 'center', align: 'center', width: 300 },
                    {
                        text: 'Duration', aggregates: ['sum'], dataField: "url", cellsAlign: 'center', align: 'center', cellsRenderer: function (row, column, value) {
                            return value;
                        }
                    }
                ]
            });
        });
    </script>

</body>
</html>
