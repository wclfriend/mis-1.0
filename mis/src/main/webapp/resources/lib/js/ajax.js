var Ajax = {
	method : function(murl, mdata, method, success) {
		$.ajax({
			type : method,
			url : murl,
			dataType : "jsonp",
			data : mdata,
			timeout : 20000,
			error : function(data) {
				console.log(data);
			},
			success : function(data) {
				success ? success(data) : function() {
				};
			}
		});
	}
}
