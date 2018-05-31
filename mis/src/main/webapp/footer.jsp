<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<script type="text/javascript" src="${ctx }/lib/jquery/1.9.1/jquery.min.js"></script> 
<script type="text/javascript" src="${ctx }/lib/layer/2.4/layer.js"></script>
<script type="text/javascript" src="${ctx }/static/h-ui/js/H-ui.min.js"></script>
<script type="text/javascript" src="${ctx }/static/h-ui.admin/js/H-ui.admin.js"></script>

<script type="text/javascript">
$(document).ready(function(){
	
}).keydown(function (e) {
	if (e.which === 27) {
		var index = parent.layer.getFrameIndex(window.name);
		if (index) {
	 		parent.layer.close(index);
		} else {
			layer.closeAll();			
		}
	}
});
</script>