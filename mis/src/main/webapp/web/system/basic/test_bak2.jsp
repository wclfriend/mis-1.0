<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
<%@ include file="/common.jsp"%>
<title>Test</title>
</head>
<body class='default'>
    <input type="button" value="添加种类" class="btn">
    <div id="treeGrid"></div>
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
        $(document).ready(function () {
        	$(".btn").click(function(){
//         		alert("tabletreeGrid");
				var tab = $('#treeGrid').find(".jqx-grid-cell-selected");
				alert(tab[0].value);
        	});
        	var result = null;
        	$.ajax({
    			url: '${ctx}/code/list',
    			type: "POST",
    		    async : false,
    			dataType:"json",
    			success:function(data){
    				result = data;
    			}
    		});
            var source =
             {
                 dataType: "json",
                 dataFields: [
                      { name: "id", type: "string" },
                      { name: "codeName", type: "string" },
                      { name: "code", type: "string" },
                      { name: "tstypes", type: "array" }
                 ],
                 hierarchy:
                 {
                    root: "tstypes"
                 },
                 localData: result,
                 id: "id"
             };

            var dataAdapter = new $.jqx.dataAdapter(source, {
                loadComplete: function () {
					console.log("ss");
                }
            });


            $("#treeGrid").jqxTreeGrid({
                source: dataAdapter,
                altRows: true,
                autoRowHeight: false,
            	pageable: true,
                ready: function () {
                },
                editable: true,
                columns: [{
                     text: '名称', dataField: 'codeName',
                     validation: function (cell, value) {
                    	 if (value.toString().length <= 0 ) {
                             return { message: "不能为空", result: false };
                         } else if (value.toString().length > 30) {
                             return { message: "长度太长", result: false };
                         }
                         return true;
                     }
                 },{
                     text: '编码', dataField: 'code',
                     validation: function (cell, value) {
                    	 if (value.toString().length <= 0 ) {
                             return { message: "不能为空", result: false };
                         } else if (value.toString().length > 30) {
                             return { message: "长度太长", result: false };
                         }
                         return true;
                     }
                 }
                ]
            });
        });
    </script>
</body>
</html>
