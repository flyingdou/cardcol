/**
 * author:hw
 * edition:1.1.1
 */
var wxUtils = {
	sign : function(signUrl){
		$.ajax({
			url: signUrl,
			Type:"post",
			data:{"url":location.href.split('#')[0]},
			dataType:"json",
			sync:false,
			success:function(sign){
				wx.config({
				    debug: false, 
				    appId: sign.appid, 
				    timestamp: sign.timestamp, 
				    nonceStr: sign.nonceStr, 
				    signature: sign.signature,
				    jsApiList: [        
					   "checkJsApi",
				       "getLocation",    
				       "scanQRCode",     
					   "onMenuShareTimeline",
					   "onMenuShareAppMessage"
				    ] 
				});
			},
			error:function(e){
				//alert(e.responseText);
			}
		});
		wx.checkJsApi({
		    jsApiList: [        
		        "getLocation",    
			    "scanQRCode",     
			    "onMenuShareTimeline",
			    "onMenuShareAppMessage"
		    ] , 
		    success: function(res) {
		        if(errMsg!="checkJsApi:ok"){
		        	alert("不支持微信js");
		        }
		    }
		});
	},
	scanQRCode : function(obj){
		//调用微信扫一扫接口
		wx.scanQRCode({
		    needResult: 1, 
		    scanType: ["qrCode","barCode"], 
		    success: function (res) {
    			var result = res.resultStr;
		    	obj.fun(result);
		    }
		});
	},
	getLocation : function(obj){
		//调用微信获取地理位置接口
		wx.getLocation({
		    success: function (res) {
	        	obj.fun(res);
		    },
		    cancel: function (res) {
		        alert('用户拒绝接受地理位置');
		    }
		});
	},
	share : function(obj){
		wx.ready(function () {
	        wx.onMenuShareTimeline({
	            title: obj.title,
	            link: obj.link,
	            imgUrl: obj.img,
	            trigger: function (res) {
	                console.log(JSON.stringify(res));
	            },
	            success: function (res) {
	            	console.log(JSON.stringify(res));
	            },
	            cancel: function (res) {
	            	console.log(JSON.stringify(res));
	            },
	            fail: function (res) {
	            	console.log(JSON.stringify(res));
	            }
	        });
	        wx.onMenuShareAppMessage({
	            title: obj.title, 
	            desc: obj.desc, 
	            link: obj.link, 
	            imgUrl: obj.img, 
	            type: 'link', 
	            success: function () {
	                console.log(1);
	            },
	            cancel: function () {
	                console.log(2)
	            }
	        });
	        wx.error(function (res) {
	            alert(res.errMsg);
	        });
	    });
	}
};
//Date对象扩展方法格式化日期字符串
Date.prototype.format = function(format){
	var formatDate = "";
	var date = {};
	date.year = this.getFullYear();
	date.year = (date.year < 10 ? "0"+date.year:date.year);
	date.month = this.getMonth()+1;
	date.month = (date.month < 10 ? "0"+date.month:date.month);
	date.day = this.getDate();
	date.day = (date.day < 10 ? "0"+date.day:date.day);
	if(this.getHours() == 8){
		if(this.getUTCHours() == 0){
			date.hours = this.getUTCHours();
		}else{
			date.hours = this.getHours();
		}
	}else{
		date.hours = this.getHours();
	}
	date.hours = (date.hours < 10 ? "0"+date.hours:date.hours);
	date.minutes = this.getMinutes();
	date.minutes = (date.minutes < 10 ? "0"+date.minutes:date.minutes);
	date.seconds = this.getSeconds();
	date.seconds = (date.seconds < 10 ? "0"+date.seconds:date.seconds);
	switch(format){
		case "yyyy-MM-dd":
			formatDate = date.year + '-' + date.month + '-' + date.day;
			break;
		case "yyyy-MM-dd HH:mm:ss":
			formatDate = date.year + '-' + date.month + '-' + date.day + " " +
				date.hours + ":" + date.minutes + ":" + date.seconds
			break;
		default : 
			formatDate = date.year + '-' + date.month + '-' + date.day;
			break;
	}
	return formatDate;
}

//计算天数差的函数，通用  
//sDate1和sDate2是2002-12-18格式  
function  DateDiff(sDate1,  sDate2){    
   var  aDate, oDate1, oDate2, iDays;
   aDate  =  sDate1.split("-");
   //转换为12-18-2002格式  
   oDate1  =  new  Date(aDate[1]  +  '-'  +  aDate[2]  +  '-'  +  aDate[0]);   
   aDate  =  sDate2.split("-")  
   oDate2  =  new  Date(aDate[1]  +  '-'  +  aDate[2]  +  '-'  +  aDate[0]);  
	 //把相差的毫秒数转换为天数  
   iDays  =  parseInt(Math.abs(oDate1  -  oDate2)  /  1000  /  60  /  60  /24);    
   return  iDays; 
}