<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>团课预约</title>
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<link rel="stylesheet" href="${pageContext.request.contextPath}/eg/css/mui.min.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/eg/css/swiper.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/eg/css/mui.picker.min.css" />
<!--门店弹出选择-->
<script src="${pageContext.request.contextPath}/eg/js/rili.js"></script>
<style type="text/css">
.mui-table-view {
	margin-top: 15px;
}
/*修改原图片的样式*/
.mui-table-view .mui-media-object {
	line-height: 42px;
	max-width: none;
	height: 60px;
}

.user_img {
	width: 80px;
	height: 60px;
	position: relative;
}

.free {
	width: 20px;
	height: 20px;
	position: absolute;
	top: 11px;
	left: 15px;
}

.bt {
	font-size: 12px;
}

.time {
	
}

.minge {
	font-size: 11px;
	color: #fe9e3a;
}

.btn_y {
	width: 60px;
	text-align: center;
	background-color: #FE9E3A;
	color: white;
	position: absolute;
	top: 30px;
	right: 10px;
	padding-top: 3px;
	padding-bottom: 3px;
	font-size: 12px;
	border: none;
}

.btn_no {
	width: 60px;
	text-align: center;
	background-color: #999999;
	color: white;
	position: absolute;
	top: 30px;
	right: 10px;
	/*padding: 3px 20px;*/
	padding-top: 3px;
	padding-bottom: 3px;
	font-size: 12px;
	border: none;
}
/*周历样式*/
.fanye {
	color: #8A8A8A;
}

#mytable  tr td {
	width: 11%;
	text-align: center;
	height: 50px;
	padding-top: 0px;
	border: 1px solid #999999;
	/*background:-mon-linear-gradient(left,#FFFFFF,#F3EBEB);
			    background: -webkit-linear-gradient(left,#FFFFFF,#F3EBEB);
			    background: -moz-linear-gradient(left,#FFFFFF,#F3EBEB);
			   background: linear-gradient(left,#FFFFFF,#F3EBEB);*/
}

/*周历样式结束*/
/*点击当前周历中的日期时，其背景色发生变化*/
.focused {
	/*background: #abcdef;*/
	background: #FE9E3A;
}

/*门店显示文字的设计*/
#mendian_sel input {
	color: #999999;
}
</style>
</head>
<body onload="initDate()">
	<div class="mui-content">
		<div class="mui-scroll-wrapper">
			<div class="mui-scroll">
				<!--周历部分-->
				<!--<div class="week"></div>-->
				<!--周历开始-->
				<div align="center" style="font-size: 18px; padding-top: 15px; padding-bottom: 5px;">
					<span onclick="previousWeek();" class="fanye "> << </span>
					<span id="showdate">
						<s:property value="planDate" />
					</span>
					<span onclick="nextWeek();" class="fanye "> >> </span>
				</div>
				<div align="center" style="font-size: 80%">
					<table id="mytable1" width="100%">
						<tr align="center">
							<td style="color: #FE9E3A;"></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td style="color: #FE9E3A;"></td>
						</tr>
					</table>
				</div>
				<div align="center" style="font-size: 80%;">
					<!--显示日期-->
					<table id="mytable" align="center" width="100%">
						<tr align="center" id="content">
							<td tabIndex="1"></td>
							<td tabIndex="2"></td>
							<td tabIndex="3"></td>
							<td tabIndex="4"></td>
							<td tabIndex="5"></td>
							<td tabIndex="6"></td>
							<td tabIndex="7"></td>
						</tr>
					</table>
				</div>
				<!--周历结束-->

				<!--门店开始-->
				<ul class="mui-table-view" id="mendian_sel">
					<li class="mui-table-view-cell">
						<a class="mui-navigate-right">
							<span class="mui-pull-left">门店</span>
							<input class="mui-pull-right" placeholder="模拟健身房（虚拟店）"
								style="text-align: right; margin-right: 15px; border: none;" id="mendian" />
						</a>
					</li>
				</ul>
				<!--门店结束-->

				<!--周历对应的详情部分-->
				<ul class="mui-table-view ul2">
					<s:iterator value="pageInfo.items">
						<li class="mui-table-view-cell mui-media">
							<img class="mui-media-object mui-pull-left user_img"
								src="${pageContext.request.contextPath}/eg/images/shuijiao.jpg" />
							<img class="free" src="${pageContext.request.contextPath}/eg/images/free.png" />
							<div class="mui-media-body">
								<div class="bt">
									<s:property value="name" />
								</div>
								<p class="time">
									<s:property value="startTime" />
									-
									<s:property value="endTime" />
								</p>
								<div class="minge">
									还剩
									<span>
										<s:property value="num" />
									</span>
									个名额
								</div>
							</div>
							<button type="button" class="mui-pull-right btn_y">
								<s:if test="<s:property value='num'/>==0">人已满</s:if>
								<s:else>可预约</s:else>
							</button>
						</li>
					</s:iterator>
				</ul>
			</div>
		</div>
	</div>
</body>
<script src="${pageContext.request.contextPath}/eg/js/mui.min.js"></script>
<script src="${pageContext.request.contextPath}/eg/js/jquery-1.11.1.min.js"></script>
<script src="${pageContext.request.contextPath}/eg/js/swiper.js"></script>
<script src="${pageContext.request.contextPath}/eg/js/mui.picker.min.js"></script>
<script type="text/javascript">
		mui('.mui-scroll-wrapper').scroll({
			deceleration:0.0005
		});
		//点击当前元素时，其背景色改变
		//焦点元素
		$("#content").delegate( "*", "focus blur", function( event ) {
		    var elem = $(this);
		    setTimeout(function() {
		       elem.toggleClass("focused", elem.is(":focus"));
		    }, 0);
		});
		
		//当点击预约按钮时，跳转到团课预约页面(此点击事件不能放在门店选择点击事件的后面运行，否则会有冲突发生)
//		$("p").not( $("#selected")[0] )
		$(".ul2").each(function(){
			$("button").click(function(){
				location.href="groupschedulewx!booking.asp";
			});
		});
		
		//关于轮播滑动的设计
//		 var swiper = new Swiper('.swiper-container', {
//		 	slidesPerView: 1,
//          spaceBetween: 0,
//	        paginationClickable: true,
//	        nextButton: '.swiper-button-next',
//	        prevButton: '.swiper-button-prev',
//	        hashnav: true,
//	        hashnavWatchState: true,
//	    });
		
		
		
		//点击门店跳转到门店选择的页面
//		var  mendian=document.getElementById('mendian');
//		mendian.addEventListener('click',function(){
//			
//			location.href="mendian.html";
//		});
		//将门店选择改为弹框
		var userPicker = new mui.PopPicker();
					userPicker.setData([
					<s:iterator value="pageInfo.items"></s:iterator>
							{value:'nan',
							text:"<s:property value='mname'/>"}
					]);
				var mendian_sel = document.getElementById('mendian_sel');
				var mendian = document.getElementById('mendian');
				mendian_sel.addEventListener('tap', function(event) {
					userPicker.show(function(items) {
//						userResult.innerHTML = JSON.stringify(items[0].text);
						mendian.value=items[0].text;
						//返回 false 可以阻止选择框的关闭
//						return false; 
					});
				}, false);			
	</script>


<!--<script>
		$(function(){
			function getWeek(num){
				switch(num){
					case 0:
						return '星期天';
						break;
					case 1:
						return '星期一';
						break;
					case 2:
						return '星期二';
						break;
					case 3:
						return '星期三';
						break;
					case 4:
						return '星期四';
						break;
					case 5:
						return '星期五';
						break;
					case 6:
						return '星期六';
						break;
				}
			}
			var date = new Date();
			for(var i=0;i<7;i++){
				var month = (date.getMonth()+1)>9?(date.getMonth()+1):'0'+(date.getMonth()+1);
				$('.week').append('<span style="margin-right:10px;">'+getWeek(date.getDay())+' '+date.getFullYear()+'-'+month+'-'+date.getDate()+'</span>');
				date.setDate(date.getDate()+1);
			}
		})
	</script>-->

</html>
