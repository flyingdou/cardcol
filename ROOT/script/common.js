String.prototype.isPositive = function(){
	var parten = /^[0-9]*[1-9][0-9]*$/;
	return parten.test(this);
}

String.prototype.isNumber = function(){
	if (this == '') return true;
	var parten = /^(0|[1-9]\d*)$|^(0|[1-9]\d*)\.(\d+)$/;
	return parten.test(this);
}
String.prototype.isTell = function() {
	if (this == '') return true;
	var parten = /^\d{3,4}-\d{6,8}(-\d{3,4})?$/;
	return parten.test(this);
}
String.prototype.isMobile = function() {
	if (this == '') return true;
	var regu =/^[1][1-9][0-9]{9}$/;
	return regu.test(this);
}
String.prototype.isPost = function() {
	if (this == '') return true;
	var pattern =/^[0-9]{6}$/;
	return pattern.test(this);
}
String.prototype.isEmail = function() {
	if (this == '') return true;
	var emailReg = /^[a-zA-Z0-9_\-]{1,}@[a-zA-Z0-9_\-]{1,}\.[a-zA-Z0-9_\-.]{1,}$/;
	return emailReg.test(this);
}
function isNumber(clsName){
	var $objs = $('.' + clsName), o, v;
	for (var i = 0 ; i < $objs.length ; i++) {
		o = $($objs[i]);
		v = o.val();
		if (v != '' && !v.isNumber()) {
			alert('必须输入数值！');
			o.select();
			o.focus();
			return false;
		}
	}
	return true;
}

function loadMask() {
	$("<div class=\"load-mask\"></div>").css({display:"block",width:"100%",height:$(document.body).outerHeight()}).appendTo("body"); 
	$("<div class=\"load-mask-msg\"></div>").html("正在处理，请稍候。。。").appendTo("body").css({display:"block",left:($(document.body).outerWidth(true) - 190) / 2,top:($(window).height() - 45) / 2});
}

function removeMask(){
	$('.load-mask-msg').remove();
	$('.load-mask').remove();
}

var hexStr ="0123456789ABCDEF"; 
function getColor(strhex){
	if (strhex && strhex.length == 7) {
		var r = hexStr.indexOf(strhex.charAt(1))*16 + hexStr.indexOf(strhex.charAt(2));
		var g = hexStr.indexOf(strhex.charAt(3))*16 + hexStr.indexOf(strhex.charAt(4));
		var b = hexStr.indexOf(strhex.charAt(5))*16 + hexStr.indexOf(strhex.charAt(6));
		return "#" + toHex(255 - r) + ""  + toHex(255 - g) + "" + toHex(255 - b);
	}
	return "#ffffff";
}

function toHex(n){   
	var hexch = ["0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F"]; 
	var h, l;   
	n = Math.round(n);
	l = n % 16;   
	h = Math.floor((n / 16)) % 16;   
	return (hexch[h] + hexch[l]); 
} 
$(function(){
	$.ajax({type: 'post', url: 'index!queryHeadInfo.asp', 
		success: function(msg) {
			var json = $.parseJSON(msg);
			$('.msgb').html(json.msgNum);
			$('.ainsp').html(json.shopNum);
			$('.city').html(json.city);
		}
	});
});

function countShop(){
	$.ajax({type: 'post', url: 'index!queryHeadInfo.asp', 
	success: function(msg) {
		var json = $.parseJSON(msg);
		$('.msgb').html(json.msgNum);
		$('.ainsp').html(json.shopNum);
		$('.city').html(json.city);
	}
});

}

function selectAll(name){
	var theEvent = window.event || arguments.callee.caller.arguments[0];
	var obj = obj = theEvent.srcElement ? theEvent.srcElement : theEvent.target;
	var val = obj.checked;
	var frm = obj.form;
	var iLen = frm.elements.length;
	for (var i = 0 ; i < iLen ;i++){
		var element = frm.elements[i];
		var tagName = element.name;
		if(tagName == name) element.checked = val;
	}
}
String.prototype.toYMD = function() {
	var ds = this.split('-');
	return ds[0] + '-' + ds[1].lrepeat(2, "0") + '-' + ds[2].lrepeat(2, "0");
};
Date.prototype.format = function(format) {
	var o = {
		"M+" : this.getMonth() + 1,
		"d+" : this.getDate(),
		"h+" : this.getHours(),
		"m+" : this.getMinutes(),
		"s+" : this.getSeconds(),
		"q+" : Math.floor((this.getMonth() + 3) / 3),
		"S" : this.getMilliseconds()
	}
	format = format || "yyyy-MM-dd hh:mm:ss";
	if (/(y+)/.test(format)) {
		format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
	}

	for ( var k in o) {
		if (new RegExp("(" + k + ")").test(format)) {
			format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ("00" + o[k]).substr(("" + o[k]).length));
		}
	}
	return format;
};
