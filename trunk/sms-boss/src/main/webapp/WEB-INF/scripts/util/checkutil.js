function getNowFormatDate() {
	var day = new Date();
	var Year = 0;
	var Month = 0;
	var Day = 0;
	var Hour = 0; 
	var Min = 0;
	var Sec = 0;
	var CurrentDate = "";
	//初始化时间 
	//Year= day.getYear();//有火狐下2008年显示108的bug 
	Year = day.getFullYear();//ie火狐下都可以 
	Month = day.getMonth() + 1;
	Day = day.getDate();
	Hour = day.getHours();
	Min = day.getMinutes();  
	Sec = day.getSeconds(); 
	CurrentDate += Year;
	if (Month >= 10) {
		CurrentDate += Month;
	} else {
		CurrentDate += "0" + Month;
	}
	if (Day >= 10) {
		CurrentDate += Day;
	} else {
		CurrentDate += "0" + Day;
	}
	if (Hour >= 10) {
		CurrentDate += Hour;
	} else {
		CurrentDate += "0" + Hour;
	}
	if (Min >= 10) {
		CurrentDate += Min;
	} else {
		CurrentDate += "0" + Min;
	}
	if (Sec >= 10) {
		CurrentDate += Sec;
	} else {
		CurrentDate += "0" + Sec;
	}
	return CurrentDate;
}
function checkformdate(startDt, endDt, option) {
	var start = $('#' + startDt).datebox('getValue').replace(/[^0-9]/g,'');
	var end = $('#' + endDt).datebox('getValue').replace(/[^0-9]/g,'');
	var now = getNowFormatDate();

	if (start != "" && start != null && start > now) {
		$.messager.alert("提示", option +"的开始时间不能大于当前时间!");
		return false;
	}
	if (start != "" && start != null && end != "" && end != null) {
		if (start > end) {
			$.messager.alert("提示", option +"的开始时间不能大于结束时间!");
			return false
		}
	}
	return true;
}