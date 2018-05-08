$(function() {
	$(".selectpicker").on('change', function() {
		$("#run").hide();
	})

	$("#checkFile").on('click',
			function() {
				$.ajax({
					url : "checkDefectiveAsinFile.spring",
					type : "post",
					data : {
						sourceCountry : $("#sourceCountry").val(),
						targetCountry : $("#targetCountry").val()
					},
					dataType : "json",
					success : function(data) {
						console_log("-------------------------------------------------------------------------------------------------------------","white");
						var flag = true;
						$.each(data, function(k, v) {
							if (v == "false") {
								console_log("No Find [" + k + "]", "red");
								flag = false;
							} else {
//								
								console_log("Find [<a href='downloadFile.spring?fileName="+k+"&sourceCountry="+$("#sourceCountry").val()+"&targetCountry="+$("#targetCountry").val()+"'>" + k + "</a>] , last update date : "
										+ v, "green");
							}
						})
						if (flag) {
							$("#run").show();
						} else {
							$("#run").hide();
						}
					}
				})
			})

	$("#fileUpload").fileinput({
		uploadUrl : 'uploadFile.spring',
		allowedFileExtensions : [ 'txt' ],
		maxFileCount : 1,
		dropZoneTitle : 'Upload Mapping File',
		textEncoding : 'UTF-8',
		showPreview : true,
		msgFilesTooMany : "选择上传的文件数量({n}) 超过允许的最大数值{m}！",
		uploadExtraData : function() {
			return {
				"sourceCountry" : $("#sourceCountry").val(),
				"targetCountry" : $("#targetCountry").val()
			};
		}
	}).on("fileuploaded", function(event, data) {
		if (data.response) {
			if (data.response[0] == "success") {
				console_log("Upload	 Mapping File OK!", "green");
			} else {
				console_log("Upload	 Mapping File ERROR!", "red");
			}
		}
	})

	function console_log(content, color) {
		var html = '<label style="color:' + color + '" class="form-label">'
				+ content + '</label><br>';
		$("#console_log").append(html);
	}
	
	$("#clearConsole").on('click',function(){
		$("#console_log").html("");
	})
	
	function addRunTime(id){
		console.log(id);
		var num=$("#"+id).html();
		num=parseInt(num)+1;
		$("#"+id).html(num);
	}
	
	$("#run").on('click',function(){
		var num=Math.floor(Math.random()*999+1);
		console_log("Running <span id='runtime"+num+"'>0</span> s","yellow");
		var addTime = setInterval(function(){addRunTime("runtime"+num)},1000); 
		$.ajax({
			url : "mapDefectiveAsin.spring",
			type : "post",
			data : {
				sourceCountry : $("#sourceCountry").val(),
				targetCountry : $("#targetCountry").val()
			},
			dataType : "json",
			success : function(data) {
				if(data[0]=="success"){
					window.clearInterval(addTime);
					console_log("run success,please click [Check File] button downLoad file","green");
				}
			}
		})
	})
})