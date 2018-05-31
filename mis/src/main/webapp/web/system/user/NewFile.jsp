<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript">
function() {
	var D = Date.parse(new Date());
	D = parseInt(D) / 1000;
	if ((D >= z && D < y) || (D >= w && D < t) || (D >= r && D < p) || (D >= n && D < l) || (D >= j && D < x) || (D >= u && D < s) || (D >= q && D < o) || (D >= m && D < k) || (D >= i && D < h)) {
		$(".coupon_dialog_jf").find(".content").html("本次礼券已全部领完，欢迎下次再来").end().show();
		$(".coupon_dialog_jf").find(".content").css("line-height", "100px")
	} else {
		$(".coupon_dialog_jf").find(".content").html(b).end().show()
	}
}
</script>
</head>
<body>

</body>
</html>