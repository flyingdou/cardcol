<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + path + "/";
%>
<%@ page isELIgnored="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<base href="<%=basePath%>">
<meta charset="UTF-8">
<title>淘课</title>
<meta name="viewport"
	content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
<link href="ecartoon-weixin/css/mui.min.css" rel="stylesheet" />
<script src="ecartoon-weixin/js/jquery.min.js" type="text/javascript"
	charset="utf-8"></script>
<script src="ecartoon-weixin/js/common.js" type="text/javascript"
	charset="utf-8"></script>
<script src="ecartoon-weixin/js/vue.min.js" type="text/javascript"></script>
<style type="text/css">
.colorR {
	color: #ff4401;
}

.cW {
	color: white
}

.bgR {
	background: #FF4401;
}

.font7 {
	font-weight: 700;
}

.font12 {
	font-size: 12px;
}

.font13 {
	font-size: 13px;
	line-height: 19px;
}

.font15 {
	font-size: 15px;
}

.font16 {
	font-size: 16px;
}

.f18 {
	font-size: 18px;
}

.mui-control-item {
	height: 45px !important;
	line-height: 45px !important;
	background: white;
	position: relative;
}

.mui-segmented-control.mui-segmented-control-inverted .mui-control-item
	{
	color: #999999
}

.control-item:after {
	content: "";
	position: absolute;
	width: 100%;
	height: 33%;
	top: 33%;
	left: -1px;
	border-right: 1px solid #f2f2f2;
}

.mui-slider-progress-bar {
	background: rgba(0, 0, 0, 0) !important;
	position: relative;
}

.mui-slider-progress-bar:after {
	content: "";
	position: absolute;
	width: 30%;
	height: 100%;
	background: #FF4401;
	left: 35%;
}

.mui-slider-indicator {
	outline: 2px solid #f2f2f2 !important;
}

.p10 {
	padding: 10px 10px 5px 10px !important;
}

.p0 {
	padding-left: 0 !important;
	padding-right: 0 !important;
}

.m0 {
	margin: -10px 0 !important;
}

.mui-table-view .mui-media-object {
	line-height: 80px;
	max-width: 106px;
	height: 80px;
}

.pd5 {
	padding-top: 5px;
}

.mui-table-view-cell:after {
	position: absolute;
	right: 0;
	bottom: 0;
	left: 10px;
	content: '';
	transform: scaleY(.5);
	background-color: #f2f2f2;
}

.bg0 {
	background: rgba(0, 0, 0, 0);
}

.mt10 {
	margin-bottom: 10px;
}

.bgW {
	background: white;
}

.cG {
	color: #999999
}

.cB {
	color: #1e1e1e
}

.mui-segmented-control.mui-segmented-control-inverted .mui-control-item.mui-active
	{
	color: #1e1e1e;
	background: white;
}

.mui-slider .mui-segmented-control.mui-segmented-control-inverted ~.mui-slider-group .mui-slider-item
	{
	border: none;
}

.p25 {
	padding: 0px 8px;
	border-radius: 8px;
	display: inline-block;
}

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
	<div class="mui-content" id="app">
		<div class="mui-slider">
			<div
				class="mui-slider-indicator mui-segmented-control mui-segmented-control-inverted" style="position: fixed;top:0;z-index:1;">
				<a class="mui-control-item control-item cG mui-active" href="#item1" id="link1">{{date1}}</a>
				<a class="mui-control-item control-item" href="#item2" id="link2">{{date2}}</a>
				<a class="mui-control-item control-item" href="#item3" id="link3">{{date3}}</a>
			</div>
			<div id="sliderProgressBar" class="mui-slider-progress-bar mui-col-xs-4" style="position: fixed;top:45px;z-index:1;"></div>
			<div class="mui-slider-group" style="margin-top: 46px;">
				<div id="item1"
					class="mui-slider-item mui-control-content mui-active">
					<div class="pullDown init_pullDown" v-if="course1.length == 0">加载中...</div>
					<ul class="mui-table-view bg0">
						<li class="mui-table-view-cell p10  bgW" v-for="(d,i) in course1">
							<div class="bgw cG">
								<ul class="mui-table-view">
									<li class="mui-table-view-cell mui-media p0">
									<a :href="'ecoursewx!findCourseDetail.asp?id='+d.id" class="m0">
									 <img class="mui-media-object mui-pull-left"
											v-bind:src="'picture/'+d.image" width="106px" height="80px">
											<div class="mui-media-body font15 cB">
												{{d.courseName}} <span class="mui-pull-right cG font12">{{distance1[i]}}<span v-if="d.distance<1000">m</span><span v-if="d.distance>=1000">km</span></span>
												<p class="mui-ellipsis font13 colorR">
													￥<span class="font16">{{d.member_price==null?0:d.member_price}}</span>
												</p>
												<p class="mui-ellipsis font13 cG">{{d.clubName}}</p>
												<p class="mui-ellipsis font13 cG">
													课程时间：{{d.startTime}}-{{d.endTime}} <span
														class="mui-pull-right bgR cW p25">淘</span>
														<input type="hidden" class="courseId" v-bind:value="d.id" />
												</p>
											</div>
										</a>
									</li>
								</ul>
							</div>
						</li>
					</ul>
				</div>
				<div id="item2" class="mui-slider-item mui-control-content">
				 <div class="pullDown init_pullDown" v-if="course2.length == 0">加载中...</div>
					<ul class="mui-table-view  bg0">
						<li class="mui-table-view-cell p10  bgW" v-for="(d,i) in course2">
							<div class="bgw cG">
								<ul class="mui-table-view">
									<li class="mui-table-view-cell mui-media p0">
									<a :href="'ecoursewx!findCourseDetail.asp?id='+d.id" class="m0"> 
									<img class="mui-media-object mui-pull-left"
											v-bind:src="'picture/'+d.image" width="106px" height="80px">
											<div class="mui-media-body font15 cB">
												{{d.courseName}} <span class="mui-pull-right cG font12">{{distance2[i]}}<span v-if="d.distance<1000">m</span><span v-if="d.distance>=1000">km</span></span>
												<p class="mui-ellipsis font13 colorR">
													￥<span class="font16">{{d.member_price}}</span>
												</p>
												<p class="mui-ellipsis font13 cG">{{d.clubName}}</p>
												<p class="mui-ellipsis font13 cG">
													课程时间：{{d.startTime}}-{{d.endTime}} <span
														class="mui-pull-right bgR cW p25">淘</span>
														<input type="hidden" class="courseId" v-bind:value="d.id" />
												</p>
											</div>
										</a>
									</li>
								</ul>
							</div>
						</li>
					</ul>
				</div>
				<div id="item3" class="mui-slider-item mui-control-content">
				  <div class="pullDown init_pullDown" v-if="course3.length == 0">加载中...</div>
					<ul class="mui-table-view  bg0">
						<li class="mui-table-view-cell p10  bgW" v-for="(d,i) in course3">
							<div class="bgw cG">
								<ul class="mui-table-view">
									<li class="mui-table-view-cell mui-media p0">
									<a :href="'ecoursewx!findCourseDetail.asp?id='+d.id" class="m0"> 
									<img class="mui-media-object mui-pull-left"
											v-bind:src="'picture/'+d.image" width="106px" height="80px">
											<div class="mui-media-body font15 cB">
												{{d.courseName}} <span class="mui-pull-right cG font12">{{distance3[i]}}<span v-if="d.distance<1000">m</span><span v-if="d.distance>=1000">km</span></span>
												<p class="mui-ellipsis font13 colorR">
													￥<span class="font16">{{d.member_price}}</span>
												</p>
												<p class="mui-ellipsis font13 cG">{{d.clubName}}</p>
												<p class="mui-ellipsis font13 cG">
													课程时间：{{d.startTime}}-{{d.endTime}} 
													<span class="mui-pull-right bgR cW p25">
														淘
													</span>
													<input type="hidden" class="courseId" v-bind:value="d.id" />
												</p>
											</div>
										</a>
									</li>
								</ul>
							</div>
						</li>
					</ul>
				</div>
			</div>
		</div>
	</div>
<script src="ecartoon-weixin/js/mui.min.js"></script>
<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js "></script>
<script type="text/javascript" src="js/utils.elisa.js"></script>
<script src="ecartoon-weixin/js/dropload.js" ></script>
<script type="text/javascript">
	/*页面初始化*/
	mui.init();
	
	wxUtils.sign("ewechatwx!sign.asp");
	wx.ready(function(){
		wxUtils.share({
			title : "淘课就上健身E卡通",
			link : "<%=basePath%>ecoursewx!findCourse.asp"+ location.search,
			img : "<%=basePath%>img/shareLogo.png",
			desc : "一卡在手，全城都有。健身E卡通为您打造健康生命第三空间……"
		});
	});
 
	// 定义全局page对象
	var page = {
		init_data:${courseData == null ? 0 : courseData},
		date1:${courseData.date1 == null ? 0 : courseData.date1},
		date2:${courseData.date2 == null ? 0 : courseData.date2},
		date3:${courseData.date3 == null ? 0 : courseData.date3},
		item1:1,
		item2:1,
		item3:1,
		status:1
	};
	
	// 如果距离大于1000距离单位转换为km
	function changeDistance(result){
		if(result.course1){
			if(!page.distance1){
				page.distance1 = [];
			}
			result.course1.forEach(function(item){
				 if(item.distance >= 1000){
					 page.distance1.push((item.distance / 1000).toFixed(2));
				 } else {
					 page.distance1.push(item.distance);
				 }
			});
		}
		if(result.course2){
			if(!page.distance2){
				page.distance2 = [];
			}
			result.course2.forEach(function(item){
				 if(item.distance >= 1000){
					 page.distance2.push((item.distance / 1000).toFixed(2));
				 } else {
					 page.distance2.push(item.distance);
				 }
			});
		}
		if(result.course3){
			if(!page.distance3){
				page.distance3 = [];
			}
			result.course3.forEach(function(item){
				 if(item.distance >= 1000){
					 page.distance3.push((item.distance / 1000).toFixed(2));
				 } else {
					 page.distance3.push(item.distance);
				 }
			});
		}
		
		// 更改Vue数据源,重新渲染页面
		courseList.distance1 = page.distance1;
		courseList.distance2 = page.distance2;
		courseList.distance3 = page.distance3;
	}
	
	// Vue绑定渲染页面
	var courseList = new Vue({
		el : "#app",
		data : {
			date1 : page.init_data.date1,
			date2 : page.init_data.date2,
			date3 : page.init_data.date3,
			course1 : page.init_data.course1,
			course2 : page.init_data.course2,
			course3 : page.init_data.course3,
			distance1 : page.distance1,
			distance2 : page.distance2,
			distance3 : page.distance3
		},
		created:function(){
			// 如果服务端返回状态为true,存储经纬度,并判断是否没有下一页数据,
			// 否则向微信服务端请求经纬度,并调用loading方法
			if(page.init_data.success){
				page.lon = page.init_data.longitude;
				page.lat = page.init_data.latitude;
			
				if(page.init_data.course1.length == 0){
					$(".init_pullDown").eq(0).html("已无更多数据");
				}
				if(page.init_data.course2.length == 0){
					$(".init_pullDown").eq(1).html("已无更多数据");
				}
				if(page.init_data.course3.length == 0){
					$(".init_pullDown").eq(2).html("已无更多数据");
				}
			}else{
				// 请求地理位置
				wx.ready(function(){
					wx.getLocation({
						success: function (res) {
							page.lon = res.longitude;
							page.lat = res.latitude;
							// 重新加载数据
							loading(null,{type:'reload',status:'only_once',ajax:'2'});
						},
						cancel: function (res) {
							// 用户拒绝请求地理位置 , 经纬度给0
							page.lon = 0;
							page.lat = 0;
							// 重新加载数据
							loading(null,{type:'reload',status:'only_once',ajax:'2'});
						}
					});
				});
			}
		}
	});	
	
	// Vue渲染页面后立即调用,判断距离是否超过千米,并转换
	changeDistance(page.init_data);
	
	// 切换内容显示状态
	$(function(){
		$("#link1").click(function(){
			page.status = 1;
		});
		$("#link2").click(function(){
			page.status = 2;
		});
		$("#link3").click(function(){
			page.status = 3;
		});
	});
	
	// 向服务端请求数据，请用Vue重新渲染页面
	function loading(drop,pull){
		$.ajax({
			url : "ecoursewx!findCourse.asp",
			type : "post",
			data : {
				"date" : page['date'+page.status],
				"longitude" : page.lon,
				"latitude" : page.lat,
				"ajax" : pull.ajax,
				"pageInfo.currentPage" : pull.status == 'only_once' ? '1' : (++page['item'+page.status])
			},
			dataType : "json",
			success : function(res) {
				// 如果距离大于1000距离单位转换为km
				changeDistance(res);
				if(pull.status == 'only_once'){
					courseList.course1 = [];
					courseList.course2 = [];
					courseList.course3 = [];
					res.course1.forEach(function(item){
						courseList.course1.push(item);
					});
					res.course2.forEach(function(item){
						courseList.course2.push(item);
					});
					res.course3.forEach(function(item){
						courseList.course3.push(item);
					});
					
					if(res.course1.length == 0){
						$(".init_pullDown").eq(0).html("已无更多数据");
					}else{
						createDrop('#item1');
					}
					if(res.course3.length == 0){
						$(".init_pullDown").eq(1).html("已无更多数据");
					}else{
						createDrop('#item2');
					}
					if(res.course3.length == 0){
						$(".init_pullDown").eq(2).html("已无更多数据");
					}else{
						createDrop('#item3');
					}
				}else{
					if(page.status == 1){
						if(pull.type == 'reload'){
							courseList.course1 = [];
						}
						res.items.forEach(function(item){
							courseList.course1.push(item);
						});
					}else if(page.status == 2){
						if(pull.type == 'reload'){
							courseList.course2 = [];
						}
						res.items.forEach(function(item){
							courseList.course2.push(item);
						});
					}else if(page.status == 3){
						if(pull.type == 'reload'){
							courseList.course3 = [];
						}
						res.items.forEach(function(item){
							courseList.course3.push(item);
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
				}
			},
			error : function(e) {
				if(drop){
					drop.resetload();
				}
			}
		});
	}
	
	// 创建上拉加载/下拉刷新组件
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
	
	// 创建下拉刷新组件
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