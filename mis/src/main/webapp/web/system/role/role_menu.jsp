<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
<%@ include file="/common.jsp"%>
<title>角色菜单</title>
<link rel="stylesheet" href="${ctx }/resources/lib/css/ztree/zTreeStyle.css" type="text/css">
<link rel="stylesheet" type="text/css" href="${ctx}/lib/zTree/v3/css/metroStyle/metroStyle.css" />
</head>
<body>
<div class="content_wrap">
	<div class="zTreeDemoBackground left">
		<ul id="treeDemo" class="ztree"></ul>
	</div>
	<div class="mt-20 mr-20 r">
			<button class="btn btn-success save">保存</button>
	</div>
</div>
<script type="text/javascript" src="${ctx }/resources/lib/js/ztree/jquery-1.4.4.min.js"></script>
<script type="text/javascript" src="${ctx }/resources/lib/js/ztree/jquery.ztree.core.js"></script>
<script type="text/javascript" src="${ctx }/resources/lib/js/ztree/jquery.ztree.excheck.js"></script>
<script type="text/javascript" src="${ctx }/resources/lib/js/ztree/jquery.ztree.exedit.js"></script>
<script type="text/javascript" src="${ctx }/lib/layer/2.4/layer.js"></script>
<script type="text/javascript">
var roleId = "${param.roleId}";
$(function(){
	var zTree = null;
	var setting = {
			async: {
				enable: true,
				url: "${ctx}/menu/list",
				type: "GET",
			    async : false,
				data:{
					'${_csrf.parameterName}' : '${_csrf.token}'
				},
			},
			check: {
				enable: true
			},
			data: {
				simpleData: {
	                enable: true,
	                pIdKey: "parentId",
	                idKey: "id"
	            },
                key: {
                    name:"menuName",
                    url: ""
                }
			},
			callback: {
				onAsyncSuccess: function(event, treeId, msg) {
					$.fn.zTree.getZTreeObj(treeId).expandAll(true);
					$.ajax({
						url:  "${ctx}/menu/getMenu",
						type: "POST",
						data:{
							"roleId":roleId,
							'${_csrf.parameterName}' : '${_csrf.token}'
						},
						dataType:"JSON",
						success: function(data) {
							var s = jQuery.parseJSON(data);
							var res = s.obj;
							for(var i = 0; i < res.length; i ++) {
								var node = $.fn.zTree.getZTreeObj(treeId).getNodeByParam("id", res[i], null);
								if(node){
									$.fn.zTree.getZTreeObj(treeId).checkNode(node, true);
								}
							}
						}
					});
				},
				onClick: function(event, treeId, treeNode){
					$.fn.zTree.getZTreeObj(treeId).checkNode(treeNode, true);
				},
				
			}
		};
		
		function setCheck() {
			zTree = $.fn.zTree.getZTreeObj("treeDemo"),
			py = $("#py").attr("checked")? "p":"",
			sy = $("#sy").attr("checked")? "s":"",
			pn = $("#pn").attr("checked")? "p":"",
			sn = $("#sn").attr("checked")? "s":"",
			type = { "Y": "ps", "N":"ps"};
			zTree.setting.check.chkboxType = { "Y" : "ps", "N" : "ps"};
		}
		
		$(document).ready(function(){
			$.fn.zTree.init($("#treeDemo"), setting);
			setCheck();
			$("#py").bind("change", setCheck);
			$("#sy").bind("change", setCheck);
			$("#pn").bind("change", setCheck);
			$("#sn").bind("change", setCheck);
		});
	$(".save").click(function(){
		var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
		var nodes = treeObj.getCheckedNodes(true);
		var menuId = "";
        for(var i = 0; i < nodes.length; i++) {
        	menuId += nodes[i].id + "#";
        }
        $.ajax({
			url:  "${ctx}/role/update",
			type: "POST",
		    async : false,
			data:{
				"roleId":roleId, 
				"menuId" : menuId,
				'${_csrf.parameterName}' : '${_csrf.token}'
			},
			dataType:"JSON",
			success: function(data){
// 				layer.alert('更新成功!',{icon:1,time:1000},function(){
					var index = parent.layer.getFrameIndex(window.name);
				 	parent.layer.close(index);
// 				});
			}
		});
	});
});
</script> 
</body>
</html>