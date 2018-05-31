<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
<jsp:include page="/common.jsp"></jsp:include>
<title>订单管理</title>
</head>
<body>
<jsp:include page="/menu.jsp"></jsp:include>
<jsp:include page="/header.jsp"></jsp:include>

<section class="Hui-article-box">
	<nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span> 图片管理 <span class="c-gray en">&gt;</span> 图片列表 <a class="btn btn-success radius r" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a></nav>
	<div class="Hui-article">
		<article class="cl pd-20">
			<div class="text-c"> 日期范围：
				<input type="text" onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'logmax\')||\'%y-%M-%d\'}'})" id="logmin" class="input-text Wdate" style="width:120px;">
				-
				<input type="text" onfocus="WdatePicker({minDate:'#F{$dp.$D(\'logmin\')}',maxDate:'%y-%M-%d'})" id="logmax" class="input-text Wdate" style="width:120px;">
				<input type="text" name="" id="" placeholder=" 图片名称" style="width:250px" class="input-text">
				<button name="" id="" class="btn btn-success" type="submit"><i class="Hui-iconfont">&#xe665;</i> 搜图片</button>
			</div>
			<div class="cl pd-5 bg-1 bk-gray mt-20"> <span class="l"><a href="javascript:;" onclick="datadel()" class="btn btn-danger radius"><i class="Hui-iconfont">&#xe6e2;</i> 批量删除</a> <a class="btn btn-primary radius" onclick="picture_add('添加图片','picture-add.html')" href="javascript:;"><i class="Hui-iconfont">&#xe600;</i> 添加图片</a></span> <span class="r">共有数据：<strong>54</strong> 条</span> </div>
			<div class="mt-20">
				<table class="table table-border table-bordered table-bg table-hover table-sort">
					<thead>
						<tr class="text-c">
<!-- 						<th width=""><input type="checkbox" value="" name=""></th> -->
						<th width="">编号</th>
						<th width="">订单号</th>
						<th width="">退款单号</th>
						<th>订单状态</th>
						<th>订单类型</th>
						<th>服务方式</th>
						<th>服务内容</th>
						<th>金额</th>
						<th>姓名</th>
						<th>手机号</th>
						<th>是否录音</th>
<!-- 						<th>服务地址</th> -->
						<th>创建时间</th>
<!-- 						<th>服务日期</th> -->
<!-- 						<th>服务时间</th> -->
						<th>是否已延期</th>
						<th>测算师</th>
<!-- 						<th>延期后的服务日期</th> -->
<!-- 						<th>延期后的服务时间</th> -->
						<th width="">操作</th>
						</tr>
					</thead>
					<tbody id="listData">
					</tbody>
				</table>
			</div>
		</article>
		<div id="Pagination" class="pagination footer"></div>
	</div>
</section>
<input type="hidden" name="_csrf" id="_csrf" value="${_csrf.token}"/>
<link rel="stylesheet" type="text/css" href="${ctx }/resources/pagination.css" />
<jsp:include page="/footer.jsp"/>
<script type="text/javascript" src="${ctx }/resources/jquery.pagination.js"></script>

<script type="text/javascript">
$('.table-sort').dataTable({
	"aaSorting": [[ 1, "desc" ]],//默认第几个排序
	"bStateSave": true,//状态保存
	"aoColumnDefs": [
	  //{"bVisible": false, "aTargets": [ 3 ]} //控制列的隐藏显示
	  {"orderable":false,"aTargets":[0,8]}// 制定列不参与排序
	]
});
</script>
</body>
</html>