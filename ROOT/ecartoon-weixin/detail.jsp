<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

	<head>
	<base href="<%=basePath%>">
		<meta charset="UTF-8">
		<title>${ecartoonDetail.prod_name}</title>
		<meta name="description" content='${ecartoonDetail.prod_summary == null || ecartoonDetail.prod_summary == "" ? "暂无简介" : ecartoonDetail.prod_summary}'/>
		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />

		<link rel="stylesheet" type="text/css" href="ecartoon-weixin/css/app.css" />
		<link rel="stylesheet" type="text/css" href="ecartoon-weixin/css/mui.min.css" />
		<link rel="stylesheet" type="text/css" href="ecartoon-weixin/css/mui.picker.min.css" />

		<style>
			.fright {
				float: right;
			}
			
			.fleft {
				float: left;
			}
			
			.red {
				color: red
			}
			.cb{
				color:#1e1e1e;
			}
			
			.cgrey {
				color: #999999
			}
			
			.corange {
				color: #ff4401
			}
			
			.cgreen {
				color: green;
			}
			
			.font7 {
				font-weight: 700;
			}
			
			.font12 {
				font-size: 12px;
			}
			.font13{
				font-size: 13px;
			}
			.font15{
				font-size: 15px;
			}
			.colorR{
				color:#ff4401;
			}
			.border-radiu8{
				border:1px solid #ff4401;
				border-radius:8px;
				padding: 0px 5px;
				color:#FF4401;
			}
			
			.header-a {
				width: 100%;
				overflow: hidden;
		
			}
			.header-div{
				width: 120%;
				height: 100%;
				position: relative;
				box-sizing: border-box;
				overflow: hidden;
			}
			.header-img {
				 /*-webkit-filter: grayscale(100%);
             -moz-filter: grayscale(100%);
         -ms-filter: grayscale(100%);
            -o-filter: grayscale(100%);
    
            filter: grayscale(100%);
	
               filter: black;*/
              
				/*高斯模糊*/
				filter: url(blur.svg#blur);
				/* FireFox, Chrome, Opera */
				-webkit-filter: blur(13px) brightness(50%);;
				-moz-filter: blur(3px) brightness(50%);;
				-o-filter: blur(3px) brightness(50%);;
				-ms-filter: blur(3px) brightness(50%);;
				filter: blur(3px) brightness(50%);;
				filter: progid:DXImageTransform.Microsoft.Blur(PixelRadius=3, MakeShadow=false);
				/* IE6~IE9 */
			}
	
			.header-say {
				position: absolute;
				top:0;
				z-index: 2;
				width: 100%;
				height: 100%;
				padding: 10px;
				display: -webkit-box;
				-webkit-box-orient: vertical;
				box-sizing: border-box;
				overflow: hidden;
                   
			}
			.ml-10{
				height: 100%;
				margin-left:10px ;
			}
			.h33{
				height: 33%;
				padding: 5px;
				box-sizing: border-box;
                overflow: hidden;
                color:white
			}
			.header-smallimg {
				width: 100%;
				-webkit-box-align: center;
				height: 76%;
				padding: 5px 0 15px;
				box-sizing: border-box;
				overflow: hidden;
			   
			}
			
			.header-where {
				width: 100%;
				box-sizing: border-box;
				border-top: 1px solid #f2f2f2;
				height: 24%;
				display: -webkit-box;
				-webkit-box-orient: vertical;
				 -webkit-box-pack: center
			}
			.cishu{
				color:white;
				font-size: 13px;
			}
	
			.content-say {
				width: 100%;
				box-sizing: border-box;
			}
			.srdm{
				background: url(ecartoon-weixin/img/shouye/taoke/store@2x.png)no-repeat scroll 10px 15px/15px 15px white;
			}
			.jg{
				background: url(ecartoon-weixin/img/shouye/taoke/price@2x.png)no-repeat scroll 10px 15px/15px 15px white;
			}
			
			.content-bottombox {
				box-sizing: border-box;
				width: 100%;
				height: 45px;
				line-height: 45px;
				font-size: 14px;
				padding: 0 10px;
				border-bottom: 1px solid #eaeaea;
				
				text-indent: 20px;
			}
			.img-card{
				display: block;
                float:left ;
                }
			
			.jtnr {
				width: 100%;
				padding: 5px 10px;
				margin-top: 10px;
				background: white;
				font-size: 13px;
			}
			
			#result {
				text-indent: 2em;
				display: inline-block;
			}
			
			#demo4 {
				width: 100px;
				position: absolute;
				left: 5em;
				top: 0em;
				opacity: 0;
				height: 100%;
				z-index: 33
			}
			
			#buy {
				font-size: 15px;
				width: 120px;
				color: white;
				background: #FF4401;
				display: inline-block;
				height: 44px;
				line-height: 44px;
				float: right;
				text-align: center;
				
			}
			
			a{
			  text-decoration:none;
			  color:#000;
			}
		</style>
	</head>

	<body>
		<form action="" method="post" id="app">
			<footer class="mui-bar mui-bar-footer font-white" style="border:none;background: white;padding-right: 0;">
				<div class="font12" style="line-height: 44px;">
					请选择开卡日期:<span id="result" class="ui-alert corange"></span>
					
					<button id="demo4" data-options="{&quot;type&quot;:&quot;date&quot;}" class="btn mui-btn mui-btn-block"></button>
		             <a href="javascript:goBuy()" id='buy'>购买</a>
				</div>
			</footer>
			<div class="mui-content" >
				<div class="header-a">
					<div class="header-div">
						<img src="picture/${ecartoonDetail.prod_image}"class="header-img"/>
						<div class="header-say ">
				 	   <div class="header-smallimg ">
                           <img src="picture/${ecartoonDetail.prod_image}"height="100%"class="img-card"/>
                           <div class="fleft ml-10">
                           	<div class="h33 "><span class="border-radiu8 font12">已售${ecartoonDetail.count}</span></div>
                           	<div class="h33 font15 font7">${ecartoonDetail.prod_name}</div>
                           	<div class="h33 font13">有效期${ecartoonDetail.prod_period}天</div>
                           </div>
				 	        </div>
				 	         <div class="header-where">
				 		                    <div class="cishu">
				 		                    	${ecartoonDetail.prod_summary == null || ecartoonDetail.prod_summary == "" ? "暂无简介" : ecartoonDetail.prod_summary}
				 		                    </div> 
				 	          </div>
				 </div>
				    </div>
				    	 
				    
				</div>
				

				<div style="margin-top: 10px;">
				<div class="content-bottombox jg" >价格<span class="fright corange font7 " id = "moneyDou">￥${ecartoonDetail.prod_price}</span></div>
					<a href="eproductwx!mdfbLogin.asp?id=${ecartoonDetail.id}"><div class="content-bottombox font15 srdm">适用店面<span id ="fitStore" class="fright cgrey font13">${ecartoonDetail.clubCount}</span></div></a>
				</div>

				<div class="jtnr ">
					<div>
						<p class="font12 ">${ecartoonDetail.prod_content}</p>
					</div>
					

				</div>

			</div>
		</form>
		
		
	</body>
		<script src="ecartoon-weixin/js/mui.min.js"></script>
		<script src="ecartoon-weixin/js/jquery.min.js" type="text/javascript"></script>
		<script src="ecartoon-weixin/js/jquery.js" type="text/javascript"></script>
		<script src="ecartoon-weixin/js/mui.picker.min.js" type="text/javascript " ></script>
		<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
		<script type="text/javascript" src="js/utils.elisa.js"></script>
 
 <script>
 			// 分享
 			(function(){
 				//替换适用门店
 				var clubCount = ${ecartoonDetail.clubCount == null ? 0 : ecartoonDetail.clubCount };
 				if ( clubCount == 0 ){
 					$("#fitStore").html("暂无试用门店");
 				   } else {
 					$("#fitStore").html(${ecartoonDetail.clubCount}+"家 >");
 				   }
 				var moneyDou = ${ecartoonDetail.prod_price};
 				moneyDou = moneyDou.toFixed(2);
 				$("#moneyDou").html("￥" + moneyDou);
 				wxUtils.sign("ewechatwx!sign.asp");
 				wx.ready(function(){
 					wxUtils.share({
 						title : '健身E卡通-${ecartoonDetail.prod_name}',
 						link :  "<%=basePath%>eproductwx!oneCardDetail.asp" + location.search,
 						img : '<%=basePath%>picture/${ecartoonDetail.prod_image}',
 						desc : '${ecartoonDetail.prod_summary == null || ecartoonDetail.prod_summary == "" ? "暂无简介" : ecartoonDetail.prod_summary}'
 					});
 				});
 			})();
 			

			var data = ${ecartoonDetail};
	
			//当用户点击购买按钮时
			function goBuy(){
				// 判断是否是通过分享链接进入
				var page = {
					shareMember : "${shareMember == null ? 0 : shareMember}",
					shareMemberName : "${shareMemberName == null ? 0 : shareMemberName}"
				}
				if(page.shareMember != 0 && page.shareMemberName != 0){
					alert('感谢您通过['+page.shareMemberName+']的分享进入我们健身E卡通微信公众号,购买此健身卡享有减免5元的优惠,关注我们的微信公众号有更多优惠等着您!');
					page.json = {
						product : ${ecartoonDetail.id},
						productType : 8,
						quantity : 1,
						unitPrice : ${ecartoonDetail.prod_price},
						shareMember : page.shareMember,
						startTime : document.getElementById("result").innerHTML
					}
					location.href = "egamewx!pleaseSweatOrder.asp?json="+encodeURI(JSON.stringify(page.json));
					return;
				}
							
				var startTime =  $("#result").html();
				var url = "eorderwx!saveProductType.asp?jsons=[{'product':${ecartoonDetail.id},'productType':8,'quantity':1,'unitPrice':${ecartoonDetail.prod_price},'addressId':6,'startTime':'"+startTime+"','payType':'1'}]";
				location.href = url;
			}
			
			
			window.onload = function() {
			//处理头部的图片高度
			var headeraheight=$(".header-a").width()/2;
			$('.header-a').css("height",headeraheight);
			var imgwidth=$('.header-div').width();
			var imgheight=imgwidth/2*1;
			$('.header-img').css('height', (imgheight + 60));
		    var imgcardheight=$(".img-card").height();
		    var imgcardwidth=imgcardheight/3*4;
		    $('.img-card').css("width",(imgcardwidth))
			
			
			//处理默认开卡时间
			var today = new Date()
			var y = today.getFullYear()
			var M = today.getMonth() + 1
			var d = today.getDate()
			M = add(M)
			d = add(d)

			function add(i) {
				if(i < 10) {
					i = "0"+ i;
				}
				return i;
			}
		    var time = y + "-" + M + "-" + d;
		    var rst = document.getElementById("result");
		    rst.innerHTML = time;
		}
	
	
		mui.init();

	(function($) {
		$.init();
		var result = $('#result')[0];
		var btns = $('.btn');
		btns.each(function(i, btn) {
			btn.addEventListener('tap', function() {
				var optionsJson = this.getAttribute('data-options') || '{}';
				var options = JSON.parse(optionsJson);
				var id = this.getAttribute('id');
				/*
				 * 首次显示时实例化组件
				 * 示例为了简洁，将 options 放在了按钮的 dom 上
				 * 也可以直接通过代码声明 optinos 用于实例化 DtPicker
				 */
				//var picker = new $.DtPicker(options);
				    var today=new Date();
               	   	var y = today.getFullYear();
			       	var M = today.getMonth() + 1;
			        var d = today.getDate();
					var picker = new $.DtPicker({//设置日历初始视图模式
						type: "date ",//真正的月份比写的多一个月。  type的类型你还是可以选择date, datetime month time  hour 
               			beginDate:new Date(),//设置开始日期   
               			endDate: new Date(y, M, d)//设置结束日期    //真正的是10.21
					});
				
				
				picker.show(function(e) {
					/*
					 * rs.value 拼合后的 value
					 * rs.text 拼合后的 text
					 * rs.y 年，可以通过 rs.y.vaue 和 rs.y.text 获取值和文本
					 * rs.m 月，用法同年
					 * rs.d 日，用法同年
					 * rs.h 时，用法同年
					 * rs.i 分（minutes 的第二个字母），用法同年
					 */
	                var datetext=e.y.text+"-"+e.m.text+"-"+e.d.text;
					result.innerText=datetext;
			     
					/* 
					 * 返回 false 可以阻止选择框的关闭
					 * return false;
					 */
					/*
					 * 释放组件资源，释放后将将不能再操作组件
					 * 通常情况下，不需要示放组件，new DtPicker(options) 后，可以一直使用。
					 * 当前示例，因为内容较多，如不进行资原释放，在某些设备上会较慢。
					 * 所以每次用完便立即调用 dispose 进行释放，下次用时再创建新实例。
					 */
					 picker.dispose();
				});
			}, false);
		}); 
	 })(mui);  
	
	</script>

</html>