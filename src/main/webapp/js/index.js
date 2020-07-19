window.onload = function() {
	var test = window.location.href;
	var lenght;
	var newDate;
	var toDate;
	if(test.indexOf("=") != -1){
		var managementDate = test.split("=")[1];
		
		var managementYear = managementDate.split("-")[0];
		var managementMonth = managementDate.split("-")[1];
		newDate = managementYear + "年" + managementMonth + "月";
		toDate = managementYear + "-" + managementMonth;
		
	}else{
		
		var aDate = new Date();
		var month = aDate.getMonth() + 1;
		var year = aDate.getFullYear();
		newDate = year + "年" + month + "月";
		toDate;
		if (month < 10) {
			toDate = year + "-0" + month;
		} else {
			toDate = year + "-" + month;
		}
	}
	console.log("newDate:"+newDate+" "+"toDate:" + toDate);
	
	timeShow(newDate, toDate);

	$("#power").click(function() {
		var power = $.ajax({
			type : "get",
			url : "UserPower",
			async : false
		}).responseText;
		var alenght = length;
		console.log(alenght);

		console.log(power);
		if (alenght != 0 && power == "0") {
			$("#changeForm").attr('action', 'javascript:void(0)');
			$("#changeForm").submit();
			alert("由于你不是管理员，所以无法修改课室信息");
		}
	})

	$("form input[type='month']").change(function() {

		$(this).next().click(function() {
			$("form input[type='month']").val($(this).prev().val());
			toDate = $(this).prev().val();
			newDate = toDate.replace("-", "年") + "月";
			$("#table .day").remove();
			timeShow(newDate, toDate);
		})
	})

	// 封装根据时间来显示的数据
	function timeShow(newDate, toDate) {

		$(".main > h3").text("培训事业四部课室预定表" + newDate);
		$("form input[type='month']").val(toDate);

		// 根据年月，用ajax来获取数据库的数据

		var txt = $.ajax({
			type : "get",
			url : "management",
			data : "date=" + toDate,
			async : false
		}).responseText;
		
		// 获取的txt是一个集合带有[]，用replace来将[]去掉
		txt = txt.replace("[", "");
		txt = txt.replace("]", "");
		var alist = txt.split(",");
		//获取toDate里面的年份，之后判断2月份的天数
		var yearflag = toDate.split("-")[0];
		var flag = toDate.split("-")[1];
		
		var trL;

		// 根据月份生成天数
		if ([ "01", "03", "05", "07", "08", "10", "12" ].indexOf(flag) != -1) {
			trL = 32;
		} else if ([ "04", "06", "09", "11" ].indexOf(flag) != -1) {
			trL = 31;
		} else if ([ "02" ].indexOf(flag) != -1) {
			if(yearflag % 4 == 0){
				trL = 30;
			}else{
				trL = 29;
			}
			
		}

		for (var i = 1; i < trL; i++) {
			$("#table ").append("<tr class='day'><td>" + i + "日</td></tr>");
			for (var x = 0; x < 6; x++) {
				$("#table tr").eq(i+1).append(
						"<td><span></span><span></span></td>");
			}
		}

		// 将dayList[i].innerHTML中的数据push进dayListp中
		var dayList = $(".day > td:first-child");
		
		var dayListp = [];
		for (var i = 0; i < dayList.length; i++) {
			if (i < 9) {
				dayListp.push("0" + dayList[i].innerHTML);
			} else {
				dayListp.push(dayList[i].innerHTML);
			}
		}
		console.log(dayListp)
		var classroom = [ "第1课室", "第2课室", "第3课室", "第4课室", "第5课室", "第6课室" ];
		for (var i = 0; i < alist.length; i++) {
			flag = alist[i];
			list = flag.split("=");
			var r = list[0][list[0].length - 2] + list[0][list[0].length - 1]
					+ "日";
			var mor = list[1];
			var projectName = list[2];
			var aclassroom = list[3];
			if (dayListp.indexOf(r) != -1) {
				var eqX = classroom.indexOf(aclassroom) + 1;
				if (mor == "上午") {
					$(".day > td:first-child").eq(dayListp.indexOf(r)).parent()
							.children("td").eq(eqX).children("span").eq(0)
							.append(projectName);
				} else {
					$(".day > td:first-child").eq(dayListp.indexOf(r)).parent()
							.children("td").eq(eqX).children("span").eq(1)
							.append(projectName);
				}
			}
		}
		$("#table td span")
				.click(
						function() {

							var aDay = $(this).parent().parent().children(
									"td:first-child").html();
							var txt;

							$("#myModal1").modal({
								show : true
							// 相当于data-show
							});
							$("#myModal1 #day").val(aDay);
							var adate = $("#myModal1 #date").val();
							var aday = $("#myModal1 #day").val();
							var classroom = $(this).parent().index();
							$("#myModal1 #classroom").val(classroom);
							aday = aday.length > 2 ? aday : "0" + aday;
							aday = aday.replace("日", "");
							var aNewDate = adate + "-" + aday;
							var mor;
							if ($(this).index() == 0) {
								$("#myModal1 #mor").val("上午");
								mor = 1;
							} else if ($(this).index() == 1) {
								$("#myModal1 #mor").val("下午");
								mor = 0;
							}
							;
							txt = $.ajax({
								type : "get",
								url : "modalInfor",
								data : "date=" + aNewDate + "&mor=" + mor
										+ "&classroom=" + classroom,
								async : false
							}).responseText;
							console.log(txt)
							var list = txt.split(",");
							if (list != null && list != "") {

								for (var i = 0; i < list.length; i++) {
									aa = list[i].split("=");
									if (i == 0 || i == 6) {
										$(" .modal-body > .message > .body ")
												.append(
														"<span  class='col-md-2'>"
																+ aa[1]
																+ "</span>")
									}else if(i == 3){
										$(" .modal-body > .message > .body ")
										.append(
												"<span  class='col-md-4'>"
														+ aa[1]
														+ "</span>")
									}else {
										$(" .modal-body > .message > .body ")
												.append(
														"<span  class='col-md-1'>"
																+ aa[1]
																+ "</span>")
									}

								}

								$(".modal-body > .message > .title").css(
										"display", "block");
							}
							length = $(" .modal-body > .message > .body ")
									.children().length;

						});

		$("#myModal1").focusout(function() {
			$(" .modal-body > .message > .body span").remove();
			$(".modal-body > .message > .title").css("display", "none");
		});

	}

	// 批量导入
	var zzexcel;

	$("#sendfile").change(function() {
		var f = this.files[0];
		$("#submitBatch").click(function(){
			var flag;
			var reader = new FileReader();
			reader.readAsBinaryString(f);
			reader.onload = function(e) {
				var data = e.target.result;
				zzexcel = XLSX.read(data, {
					type : 'binary'
				});
				var excelCsv = XLSX.utils.sheet_to_csv(zzexcel.Sheets[zzexcel.SheetNames[0]]);
				var list = excelCsv.split("\n")
				var newList = "";
				for (var i = 1; i < list.length; i++) {
					if(i == 1){
						newList = newList + list[i];
					}else{
						newList = newList + "@" + list[i];
					}
				}
				flag = $.ajax({
					type : "post",
					url : "management",
					data : "batch=" + newList,
					async : false
				}).responseText;
				if(flag != null){
					var seccess = "数据导入成功";
					alert(seccess);
					window.location.href="index.html";
					console.log("seccess");
				}
			}
		})
							
	})	
	
}