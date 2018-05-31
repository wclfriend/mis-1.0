<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
<%@ include file="/common.jsp" %>
<title>用户管理</title>
<style type="text/css">
* {margin: 0;padding: 0;}
.btnWrap {float: left;margin-left: 30px;text-align: center;cursor: pointer;}
.btnWrap .txt {font-size: 14px;margin-top: 10px;}
.imgWrap {width: 40px;display: block;}
</style>
</head>
<body>
	<form action="${ctx }/wechatOrderFormController?succ" id="frm" method="post">
		<input id="id" name="id" type="hidden" value="${param.orderNo}">
		<audio id="audio" controls autoplay></audio>
		<div class="btnWrap" id="startBtn">
			<img class="imgWrap" src="${ctx }/resources/img/audio.png" />
			<div class="txt">录制语音</div>
			<div id="time"></div>
		</div>
		<div class="btnWrap" id="stopBtn" onclick="stopRecord()">
			<img class="imgWrap" src="${ctx }/resources/img/end.png" />
			<div class="txt">录制结束</div>
		</div>
		<div class="btnWrap" id="playBtn" onclick="playRecord()">
			<img class="imgWrap" src="${ctx }/resources/img/try.png" />
			<div class="txt">试听</div>
		</div>
		<div class="btnWrap" id="obtainBtn" onclick="obtainRecord()">
			<img class="imgWrap" src="${ctx }/resources/img/new.png" />
			<div class="txt">上传录音</div>
		</div>
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
	</form>
		<jsp:include page="/footer.jsp"></jsp:include>
		<script type="text/javascript" src="${ctx }/resources/lib/recorder.js"></script>
		<script>
		/* 音频对象*/
		var recorder, timerId, count = 0, locker = false;
		var audio = document.querySelector('#audio');
		var startBtn = document.querySelector('#startBtn');
		var stopBtn = document.querySelector('#stopBtn');
		var playBtn = document.querySelector('#playBtn');
		var obtainBtn = document.querySelector('#obtainBtn');
		var timer = document.querySelector('#time');

		startBtn.onclick = function() {
			if (locker) {
				return;
			}
			locker = true;
			audio.pause();
			HZRecorder.get(function(rec) {
				recorder = rec;
				recorder.start();
				timerId = setInterval(function() {
					count++;
					var val = formatSeconds(count);
					timer.innerHTML = val;
				}, 1000);
			});
		}

		stopBtn.onclick = function() {
			recorder.stop();
			clearTimerId(timerId);
		}

		playBtn.onclick = function() {
			recorder.play(audio);
			clearTimerId(timerId);
		}

		obtainBtn.onclick = function() {
			if (recorder == undefined || recorder == null) {
				alert("请先录制语音");
				return;
			}
			var record = recorder.getBlob();
			clearTimerId(timerId);

			var fd = new FormData();
			fd.append('audioData', record);
			var xhr = new XMLHttpRequest();
			url = "${ctx}/order/uploadFile?id=" + $("#id").val();
			xhr.open('POST', url);
			xhr.send(fd);
// 			alert("上传成功");
		}

		function clearTimerId(timerId) {
			clearInterval(timerId);
			count = 0;
			locker = false;
		}

		function formatSeconds(value) {
			var theTime = parseInt(value);// 秒
			var theTime1 = 0;// 分
			var theTime2 = 0;// 小时
			var result = parseInt(theTime % 60);

			theTime1 = parseInt(theTime / 60);
			theTime1 = theTime1 < 10 ? '0' + theTime1 : theTime1;

			theTime2 = parseInt(theTime / 3600);
			theTime2 = theTime2 < 10 ? '0' + theTime2 : theTime2;

			result = result < 10 ? '0' + result : result;

			result = theTime2 + ":" + theTime1 + ":" + result;
			return result;
		}
		</script>
</body>
</html>