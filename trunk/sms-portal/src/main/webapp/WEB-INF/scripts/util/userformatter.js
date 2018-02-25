function statusFormatter(value){
	switch(value){
		case '100':
			return '正常';
		case '200':
			return '关闭';
		case '300':
			return '锁定';
		case '400':
			return '超过有效期';
	}
}
function formatAmt(value, rowData) {
	if(null != value){
	    sign = (value == (value = Math.abs(value)));
	    value = Math.floor(value*100+0.50000000001);
	    cents = value%100;
	    value = Math.floor(value/100).toString();
	    if(cents<10)
	    cents = "0" + cents;
	    for (var i = 0; i < Math.floor((value.length-(1+i))/3); i++)
	    	value = value.substring(0,value.length-(4*i+3))+','+
	    	value.substring(value.length-(4*i+3));
	    return (((sign)?'':'-') + value + '.' + cents);
	}else{
		return '';
	}
}


function fmtwithhms(date){
	if(date !=null){
		var y = date.getFullYear();  
	    var m = date.getMonth()+1;  
	    var d = date.getDate();  
	    var h = date.getHours(); 
	    var min = date.getMinutes();  
	    var sec = date.getSeconds(); 
	    return  y+'-'+(m<10?('0'+m):m)+'-'+(d<10?('0'+d):d)+' '+(h<10?('0'+h):h)+':'+(min<10?('0'+min):min)+':'+(sec<10?('0'+sec):sec);
	}else{
		return '';
	}
}	
function fmtwithnothms(date){
	if(date !=null){
		var y = date.getFullYear();  
	    var m = date.getMonth()+1;  
	    var d = date.getDate();  
	    return  y+'-'+(m<10?('0'+m):m)+'-'+(d<10?('0'+d):d);
	}else{
		return '';
	}
}

function mobileFormatter(value){
	if(value != null){
		return plusXing(value,3,4)	
	}
	return '';
}

function idNoFormatter(value){
	if(value != null){
		return plusXing(value,6,4)	
	}
	return '';
}

function cardNoFormatter(value){
	if(value != null){
		return plusXing(value,6,4)	
	}
	return '';
}

function plusXing (str,frontLen,endLen) { 
    var len = str.length-frontLen-endLen;
    var xing = '';
    for (var i=0;i<len;i++) {
        xing+='*';
    }
    return str.substr(0,frontLen)+xing+str.substr(str.length-endLen);
}

