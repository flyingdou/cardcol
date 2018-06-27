<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String basePath = request.getScheme() + "://" + request.getServerName() +  request.getContextPath() + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="UTF-8">
<title>挑战列表</title>
<meta name="viewport"
	content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
<link href="ecartoon-weixin/css/mui.min.css" rel="stylesheet" />
<style>
.cg {
	color: #999;
}

.cb {
	color: #1e1e1e;
}

.bw {
	background: white;
}

.cw {
	color: white;
}

.f15 {
	font-size: 15px;
}

.tcenter {
	text-align: center;
}

.f12 {
	font-size: 12px;
}

.mr10 {
	margin-right: 10px;
}

.mt10 {
	margin-bottom: 10px;
}

.mt10 {
	margin-top: 10px;
}

.h44 {
	height: 44px;
	line-height: 44px!important;
}

.mui-segmented-control.mui-segmented-control-inverted~.mui-slider-progress-bar {
	background-color: rgba(0, 0, 0, 0);
	position: relative;
}

.mui-segmented-control.mui-segmented-control-inverted~.mui-slider-progress-bar:after {
	position: absolute;
	content: "";
	width: 30%;
	left: 35%;
	bottom: 2px;
	height: 2px;
	background: #FF4401;
}

.mui-segmented-control.mui-segmented-control-inverted .mui-control-item {
	color: #999999;
	font-size: 13px;
}

.mui-segmented-control.mui-segmented-control-inverted .mui-control-item.mui-active {
	color: #1e1e1e;
}

.mui-slider-progress-bar {
	z-index: 1;
	height: 0px;
}

.fun {
	height: 32px;
	line-height: 32px;
	font-size: 12px;
	color: #999999;
	background: white;
	padding-left: 10px;
}

.mui-segmented-control.mui-scroll-wrapper {
	height: 85px;
	background: white;
}

.mui-segmented-control.mui-scroll-wrapper .mui-scroll {
	width: auto;
	height: 75px;
	white-space: nowrap;
	overflow: hidden;
}

.mui-segmented-control .mui-control-item {
	line-height: 75px;
}

.imgbox1 {
	width: 106px!important;
	height: 75px!important;
	overflow: hidden;
	display: block;
	box-sizing: border-box;
	padding: 0!important;
	margin-left: 10px;
	position: relative
}

.mb {
	position: absolute;
	width: 100%;
	height: 100%;
	background: rgba(1, 1, 1, .3);
	top: 0;
	left: 0;
	text-align: center;
}

.lh35 {
	line-height: 55px;
}

.mp {
	margin-top: -35px;
	color: rgba(255, 255, 255, .5)
}

.mp1 {
	margin-top: -55px;
	color: rgba(255, 255, 255, .5)
}

.posiR {
	position: relative;
}

.mb1 {
	position: absolute;
	width: 100%;
	background: rgba(1, 1, 1, .3);
	height: 100%!important;
	color: red;
	top: -5px;
	left: 0px;
}

.lh45 {
	line-height: 95px;
}

.mui-table-view.mui-grid-view .mui-table-view-cell>a:not(.mui-btn) {
	margin: 0;
	padding: 0;
	overflow: hidden;
}

 /**
   *
   * 下拉样式 Pull down styles
   *
   */
  .pullUp,.pullDown{
  	text-align: center;
  	color:#888;
  	font-weight:bold;
    font-size:14px;
    height:40px;
    line-height: 40px;
  }
</style>
</head>
<body>
	<div class="mui-slider" id="app">
		<div class="mui-slider-indicator mui-segmented-control mui-segmented-control-inverted bw h44">
			<a class="mui-control-item h44 mui-active item" id="link1" href="#item1">体重管理</a>
			<a class="mui-control-item h44 item" href="#item2" id="link2">健身次数</a>
		</div>
		<div id="sliderProgressBar"
			class="mui-slider-progress-bar mui-col-xs-6"></div>

<!--  
		<div class="fun">你可能感兴趣的挑战</div>
		<div
			class="mui-scroll-wrapper mui-slider-indicator mui-segmented-control mui-segmented-control-inverted">
			<div class="mui-scroll">
				<a class="mui-control-item  imgbox1"
					:class="{'mui-active':index==0}" v-for="(x,index) in data1"  v-if="index<=3">
					<a :href="'ecoursewx!getActive.asp?id='+x.id">
						<img src="http://placehold.it/40x30" class="img-scroll" />
					</a>
					<div class="mb lh35">
						<span class="cw f15">{{x.name}}</span>
						<p class="mp f12">已参加{{x.applyCount}}</p>
					</div>
				</a>
			</div>
		</div>
-->
		<div class="mui-slider-group" id="group">
				<div id="item1"
				class="mui-slider-item mui-control-content mui-active" style="height:600px;overflow: scroll;">
				<ul class="mui-table-view mui-grid-view" id="tab-1">
					<li class="mui-table-view-cell mui-media mui-col-xs-6 " v-for="x in arr1">
							<a :href="'ecoursewx!getActive.asp?type=2&id='+x.id"
							style="padding: 0px; overflow: hidden; box-sizing: border-box;"
							class="posiR"> 
								<img :src="'picture/'+x.image" class="mui-media-object" style="width:181px;height:135px;"/>
								<div class="mb1 lh45">
									<span class="cw f15">{{x.name}}</span>
									<p class="mp1 f12">已参加{{x.applyCount}}</p>
									<p class="mp f12">目标 : {{x.memo}}</p>
								</div>
						</a>
					</li>
					</ul>
			<!-- <div class="pullUp" onclick="loading()">
   			上拉加载更多...
   		</div> -->
		</div>
			<div id="item2" class="mui-slider-item mui-control-content" style="height:600px;overflow: scroll;">
				<ul class="mui-table-view mui-grid-view">
					<li class="mui-table-view-cell mui-media mui-col-xs-6 "
						v-for="x in arr2"><a :href="'ecoursewx!getActive.asp?type=2&id='+x.id"
						style="padding: 0px; overflow: hidden; box-sizing: border-box;"
						class="posiR"> 
							<img :src="'picture/'+x.image" class="mui-media-object" style="width:181px;height:135px;"/>
							<div class="mb1 lh45">
								<span class="cw f15">{{x.name}}</span>
								<p class="mp1 f12">已参加{{x.applyCount}}</p>
								<p class="mp f12">目标 : {{x.memo}}</p>
							</div>
					</a></li>
				</ul>
				<!-- <div class="pullUp" onclick = "loading()">
   			 	上拉加载更多...
   		  </div> -->
			</div>
		</div>
		<!-- <div class="footer" style="width: 100%;position:fixed;bottom:0;height:40px;">
			<input type="button" id="load" value="加载更多" style="color:white;background: #FF4401;width:100%;height:40px;" />
			<div style="width:100%;height:100%;position:absolute;top:0;left:0;z-index:1;cursor:pointer;"
			  	 onclick="loading()">
			</div>
		</div> -->
	</div>
	<script src="ecartoon-weixin/js/mui.min.js"></script>
	<script src="ecartoon-weixin/js/jquery.min.js" ></script>
	<script src="ecartoon-weixin/js/vue.min.js" ></script>
	<script src="ecartoon-weixin/js/dropload.js" ></script>
	<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js "></script>
	<script type="text/javascript" src="http://www.ecartoon.com.cn/js/utils.elisa.js"></script>
	<script type="text/javascript">
		mui.init();
		var sw = $(".img-scroll").width();
		var sh = $(".img-scroll").height();
		if (sw > sh) {
			$(".img-scroll").css('height', "75px");
			var imt = -($(".img-scroll").width() - 106) / 2 + "px";
			$('.img-scroll').css("margin-left", imt)
		} else {
			$(".img-scroll").css('width', "106px");
			var iml = -($(".img-scroll").height() - 75) / 2 + "px";
			$('.img-scroll').css("margin-top", imt)
		};
		
		wxUtils.sign("ewechatwx!sign.asp");
		wx.ready(function(){
			wxUtils.share({
				title : "健身E卡通—挑战列表",
				link : "<%=basePath%>ecoursewx!findActiveAndDetail.asp"+ location.search,
				img : "<%=basePath%>img/shareLogo.png",
				desc : "一卡在手，全城都有。健身E卡通为您打造健康生命第三空间……"
			});
		});

		var data = ${data == null ? 0 : data};
		var page = {item1:1,item2:1,type1:"A",type2:"D",status:1};
	
		var active = new Vue({
			el : "#app",
			data : {
				arr1 : data.items,
				arr2 : []
			}
		});
		
		if(active.arr1.length == 0){
			createDropUp('#item1');
		}else{
			createDrop('#item1');
		}

		$(function() {
			$.ajax({
				url : "ecoursewx!findActiveAndDetail.asp",
				type : "post",
				data : {
					"target" : "D",
					"ajax" : "1"
				},
				dataType : "json",
				success : function(res) {
					active.arr2 = res.items;
					if(res.items.length == 0){
						createDropUp('#item2');
					}else{
						createDrop('#item2');
					}
				}
			});
		});
		
		document.getElementById("link1").addEventListener("tap",function(){
			page.status = 1;
		},false);
		document.getElementById("link2").addEventListener("tap",function(){
			page.status = 2;
		},false);
		
		/*上拉加载 /下拉刷新*/
		function loading(drop,pull){
			$.ajax({
				url : "ecoursewx!findActiveAndDetail.asp",
				type : "post",
				data : {
					"target" : page['type'+page.status],
					"ajax" : "1",
					"pageInfo.currentPage" : (++page['item'+page.status])
				},
				dataType : "json",
				success : function(res) {
					if(page.status == 1){
						if(pull.type == 'reload'){
							active.arr1 = [];	
						}
						res.items.forEach(function(item){
							active.arr1.push(item);
						});
					}else{
						if(pull.type == 'reload'){
							active.arr2 = [];
						}
						res.items.forEach(function(item){
							active.arr2.push(item);
						});
					}
					if(res.items.length < 20 || res.items.length == 0){
						drop.resetload();
						drop.lock('down');
            // 显示无数据
            drop.noData();
            drop.resetload();
					}else{
						drop.resetload();
					}					
				},
				error : function(e) {
					drop.resetload();
					$(".pullUp").eq(page.status).html("加载出错,请稍后再试");
				}
			});
		}
			
		function createDrop(selector){
			$(selector).dropload({
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
			    	page['item'+page.status] = 0;
			    	loading(drop,{type:'reload'});
			    },
			    loadDownFn: function (drop){
			    	 loading(drop,{ajax:'1'});
			    }
			});
		}
		
		function createDropUp(selector){
			$(selector).dropload({
			    scrollArea: window,
			    distance : 50,
			    domUp : {
			        domClass   : 'dropload-up',
			        domRefresh : '<div class="pullUp">下拉刷新</div>',
			        domUpdate  : '<div class="pullUp">释放更新</div>',
			        domLoad    : '<div class="pullUp"><span class="loading"></span>加载中...</div>'
			    },
			    loadUpFn : function(drop){
			    	//重置页码
			    	page['item'+page.status] = 0;
			    	loading(drop,{type:'reload'});
			    }
			});
		}
	</script>
</body>
</html>