<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>					
<base href="<%=basePath%>">
<meta charset="UTF-8">
<title>门店分布</title>
<meta name="viewport"
	content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
<link href="ecartoon-weixin/css/mui.min.css" rel="stylesheet" />
<style type="text/css">
.font12 {
	font-size: 12px;
}

.font13 {
	font-size: 13px;
}

.font15 {
	font-size: 15px;
}

.font18 {
	font-size: 18px;
}

.font100 {
	font-weight: 100;
}

.font700 {
	font-weight: 700;
}

.mR10 {
	margin-right: 10px;
}

.mL10 {
	margin-left: 10px;
}

.pL10 {
	padding-left: 10px;
}

.pR10 {
	padding-right: 10px;
}

.fleft {
	float: left;
}

.fright {
	float: right;
}

.clear {
	zoom: 1;
}
/*==for IE6/7 Maxthon2==*/
.clear :after {
	clear: both;
	content: '.';
	display: block;
	width: 0;
	height: 0;
	visibility: hidden;
}

.tright {
	text-align: right;
}

.tleft {
	text-align: left;
}

.border-none {
	border: none
}

.cB {
	color: black
}

.cG {
	color: #999999;
}

.cW {
	color: white
}

.cR {
	color: #ff4401;
}

.bgW {
	background: white;
}

.bgR {
	background: #ff4401;
}

.w50 {
	width: 50%;
}

.mui-content {
	width: 100%;
	overflow: hidden;
	box-sizing: border-box;
}

.address_box {
	height: 100px;
	width: 100%;
	position: relative;
	padding: 20px 10px;
}

.address_box:after {
	content: "";
	position: absolute;
	width: 100%;
	border-bottom: 1px solid #f2f2f2;
	height: 100px;
	left: 10px;
	top: -1px;
}

.address_next {
	height: 60px;
	display: -webkit-box;
	-webkit-box-orient: horizontal;
}

.img_box {
	height: 60px;
	padding: 8px 0;
}

.js {
	-webkit-box-flex: 1;
	padding-left: 10px;
}

.js_next {
	width: 100%;
	height: 60px;
	display: -webkit-box;
	-webkit-box-orient: vertical;
	-webkit-box-pack: center;
}

.name_lone, .star, .people {
	-webkit-box-flex: 1;
	box-sizing: border-box;
	height: 20px;
	overflow: hidden;
}

.mui-icon-star-filled {
	color: #FF4401
}


  /* 
   * 下拉样式 Pull down styles
   */
  .pullUp,.pullDown{
  	text-align: center;
  	color:#888;
  	font-weight:bold;
    font-size:14px;
    height:40px;
    line-height: 40px;
  }
  
  /*#item1{
	   position: relative;
	   width: 100%;
   } */
  
  
  #body{
	  position: relative;
	  width: 100%;
  }
 

 #dy {
	position: fixed;
	height: 44px;
	text-align: center;
	line-height: 44px;
	padding: 0 10px;
	bottom: 0;
	width: 100%;
	z-index:1;
}

.footer{
    width: 100%;
	height: 100%;
	background: #FF4401;
	border: none;
	color:white;
}



</style>
</head>

<body id = "body">
		<div class="mui-scroll" id="app">
		
			<div id="item1" style="overflow: hidden;" class = "buttondd">
				<div v-for="(x,i) in clubList" id = "dataDou" >
					<a class="link" :href="'eproductwx!findClubById.asp?id='+x.id">
						<div class="address_box bgW">
							<div class="address_next">
								<div class="img_box">
									<img :src="'picture/'+x.image" width="44px" height="44px" />
								</div>
								<div class="js">
									<div class="js_next">
										<div class="name_lone">
											<span id="" class="font15 tleft w50 cB"> {{x.name}} </span> 
											<span id="" class="font12 cG fright clear">
											{{distance[i]}}<span v-if="x.distance<1000">m</span><span v-if="x.distance>=1000">km</span>
											</span>
										</div>
										<div class="star">
											<div class="icons mui-inline" style="margin-left: 6px;">
												<i :data-index="index+1"
													class="mui-icon mui-icon-star font15 cG"
													v-for="(y,index) in 5"
													:class="x.star>index?'mui-icon-star-filled':''"></i>
											</div>
										</div>
										<div class="people font12 cG ">
											<span id="" class="">
												设备{{x.deviceScore}}；环境{{x.evenScore}}；服务{{x.serviceScore}} </span> 
												<span id="" class="fright clear">
												 {{x.evaluateNum}}人评价 
												</span>
										</div>
									</div>
								</div>
							</div>
						</div>
					</a>
				</div>
				<div style="height:44px;"></div>
			</div>
	
		</div>
		
		<div id = "dy"> 
				<button  onclick="goToMap()" class="footer">
					去&nbsp;地&nbsp;图&nbsp;中&nbsp;看
				</button>
	    </div>
	<script src="ecartoon-weixin/js/jquery.min.js" charset="utf-8"></script>
	<script src="ecartoon-weixin/js/mui.min.js"></script>
	<script src="ecartoon-weixin/js/vue.min.js"></script>
	<script src="ecartoon-weixin/js/dropload.js"></script>
	<script type="text/javascript">
	
	$(function (){
	    var upUrl = location.href.split("#")[0];
		pushHistory();
		window.addEventListener("popstate", function(e) {
	        //根据自己的需求实现自己的功能
			if (upUrl.length > 0){
				if ("=" != upUrl.substring(upUrl.length-1,upUrl.length)){
				   if (document.cookie.indexOf("id>1") != -1){
					   document.cookie = "id>2";
					   history.go(-3);
				   } else {
				   	   history.go(-2);
				   }
				} else {
						//调用微信的closeWindow方法，关闭当前页面
						history.go(-2);
					    pushHistory();
				}
				
			}
			
			}, false);
		function pushHistory() {
			var state = {
			title: "title",
			url: upUrl
			};
			window.history.pushState(state, "title", upUrl);
			}
		
	});
	
	
	
		var data = ${club.clubList == null ? 0 : club.clubList};
		var kk = new Vue({
			el : '#app',
			data : {
				clubList : data,
				distance : []
			}
		});
		
		//Vue渲染完数据以后，立即判断俱乐部距离是否大于1000，大于1000就将值替换
		function changeUnite (data) {
			if (!kk.distance){
			    kk.distance = [];	
			 }
		    data.forEach(function (item){
			   if (item.distance >= 1000) {
				   kk.distance.push((item.distance/1000).toFixed(2));
			   } else {
				   kk.distance.push(item.distance);
			   }
		   });	
		}
		changeUnite(data);
		
		
		function ref(elem) {
			var index = $(".address_box").index(elem);
			location.href = $(".link").eq(index).attr("href");
		}
		
		function goToMap () {
			location.href="eproductwx!findProduct45Member.asp?source=mdfb&latitude=${club.latitude}&longitude=${club.longitude}&id=${club.eid}";
		}
		
		
		
		/*上拉加载 开始*/
		var page = 1;
		(function(){
			//获取屏幕高度
			var height = window.screen.height;
			$("#body").css({"height":(height-44)+"px"});
			$("#body").attr({"overflow":"scroll"});
			var eid = ${club.eid == null || club.eid == "" ? 0 : club.eid};
			
			function loading(drop,type){
				$.ajax({
					url : "eproductwx!findProduct45Member.asp",
					type : "post",
					data : {
						"longitude" : "${club.longitude}",
						"latitude" : "${club.latitude}",
						"ajax" : "1",
						"id" : eid,
						"pageInfo.currentPage" : (++page)
					},
					dataType : "json",
					success : function(res) {
						changeUnite(res.clubList);
						//下拉刷新数据时将已加载的数据全部置空
						if(type == "reload"){
							kk.clubList = [];
						}
						//将查询出来的数据加载到页面中
						res.clubList.forEach(function(item){
							kk.clubList.push(item);
						});
						if(res.clubList.length < 20 || res.clubList.length == 0){
							drop.resetload();
				            // 显示无数据
				            drop.noData();
				            drop.resetload();
						}else{
							drop.resetload();
						}					
					},
					error : function(e) {
						drop.resetload();
					}
				});
			}
			
	    // dropload
	    var droploadItem1 = $('#app').dropload({
	        scrollArea: window,
	        distance : 50,
	        domUp : {
	            domClass   : 'dropload-up',
	            domRefresh : '<div class="pullUp">下拉刷新</div>',
	            domUpdate  : '<div class="pullUp">释放更新</div>',
	            domLoad    : '<div class="pullUp"><span class="loading"></span>加载中...</div>'
	        },
	        domDown: {
	            domClass: 'dropload-down',
	            domRefresh: '<div class="pullDown">上拉加载更多</div>',
	            domLoad: '<div class="pullDown"><span class="loading"></span>加载中...</div>',
	            domNoData: '<div class="pullDown">已加载完全部数据</div>'
	        },
	       loadUpFn : function(drop){
	        	//重置页码
	        	page = 0;
				var type = "reload";
	        	loading(drop,type);
	        },
	        /*loadDownFn: function (drop){
				 var type = "loadMore";
	        	 loading(drop,type);
				
	        }*/
	    });
	    
	    
	})();
	/*上拉加载 结束*/
		
		
	</script>
</body>

</html>