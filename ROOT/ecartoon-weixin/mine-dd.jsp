<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

	<head>
		<meta charset="UTF-8">
		<title>我的订单</title>
		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
		<link href="ecartoon-weixin/css/mui.min.css" rel="stylesheet" />
		<script src="ecartoon-weixin/js/jquery.min.js" type="text/javascript" charset="utf-8"></script>
		<script src="ecartoon-weixin/js/vue.min.js" type="text/javascript"></script>
		<style type="text/css">
			.colorR {
				color: #ff4401;
			}
			
			.font7 {
				font-weight: 700;
			}
			
			.font12 {
				font-size: 12px;
			}
			
			.font13 {
				font-size: 13px;
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
				height: 45px!important;
				line-height: 45px!important;
				background: white;
				position: relative;
			}
			
			.mui-segmented-control.mui-segmented-control-inverted .mui-control-item {
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
				background: rgba(0, 0, 0, 0)!important;
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
				outline: 2px solid #f2f2f2!important;
			}
			
			.mui-slider-progress-bar {
				margin-bottom: 10px;
			}
			
			.p10 {
				padding: 10px 10px 5px 10px!important;
			}
			
			.p0 {
				padding-left: 0!important;
			}
			
			.m0 {
				margin: -10px 0!important;
			}
			
			.mui-table-view .mui-media-object {
				line-height: 72px;
				max-width: 72px;
				height: 72px;
			}
			
			.pd5 {
				padding-top: 5px;
			}
			
			.mui-table-view-cell:after {
				position: absolute;
				right: 0;
				bottom: 0;
				left: 0px;
				content: '';
				transform: scaleY(.5);
				background-color: rgba(0, 0, 0, 0);
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
			
			.mui-segmented-control.mui-segmented-control-inverted .mui-control-item.mui-active {
				color: #1e1e1e;
				background: white;
			}
			
			.mui-slider .mui-segmented-control.mui-segmented-control-inverted~.mui-slider-group .mui-slider-item {
				border: none;
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
	


			
		</style>
	</head>

	<body ng-app="myApp">

		<div class="mui-content" ng-controller="myCtrl" id="app">
			<!--滑动组件-->
			<div id="pullrefresh" class="mui-content mui-scroll-wrapper">
				<div class="mui-scroll">
               <!--滑动组件结束-->
					<div class="mui-slider">
						<div class="mui-slider-indicator mui-segmented-control mui-segmented-control-inverted">
							<a class="mui-control-item control-item cG mui-active" href="#item1" class="item1" id="item1s" >有效订单</a>
							<a class="mui-control-item control-item" href="#item2" class="item2" id="item2s">未付款订单</a>
							<a class="mui-control-item" href="#item3" class="item3" id="item3s">已完成订单</a>
						</div>
						<div id="sliderProgressBar" class="mui-slider-progress-bar mui-col-xs-4"></div>
						<div class="mui-slider-group">
							<div id="item1" class="mui-slider-item mui-control-content mui-active dou-slider">
									<ul class="mui-table-view bg0"> 
										<li class="mui-table-view-cell p10 mt10 bgW" v-for="v in b" >
											<div class="bgw cG">
												订单编号：<span>{{v.no}}</span>
												<ul class="mui-table-view">
													<li class="mui-table-view-cell mui-media p0">
														<a :href="'ecartoon-weixin/orderDetail.jsp?orderId='+v.id+'&orderType='+v.orderType" class="m0 orderDetail">
															<img class="mui-media-object mui-pull-left" :src="'picture/'+v.image" width="72px" height="72px">
															<div class="mui-media-body font15 pd5 cB">
																<span>{{v.name}}</span>
																<p class="mui-ellipsis font13 cG" >开始时间：{{v.orderStartTime}}</p>
																<p class="mui-ellipsis colorR font12">￥ <span class="f18" >{{v.orderMoney | NAAN}}</span></p>
															</div>
														</a>
													</li>
												</ul>
											</div>
										</li>
									</ul>
							</div>
							<div id="item2" class="mui-slider-item mui-control-content dou-slider">
									<ul class="mui-table-view  bg0">
										<li class="mui-table-view-cell p10 mt10 bgW order_delete_li" v-for="v in a">
											<div class="bgw cG">
												订单编号：<span>{{v.no}}</span>
												<ul class="mui-table-view">
													<li class="mui-table-view-cell mui-media p0" style="position: relative;">
														<a :href="'eorderwx!payMent.asp?id='+v.id+'&orderType='+v.orderType" class="m0 payMent">
															<img class="mui-media-object mui-pull-left" :src="'picture/'+v.image" width="72px" height="72px" />
															<div class="mui-media-body font15 pd5 cB">
																<span>{{v.name}}</span>
																<p class="mui-ellipsis font13 cG">开始时间：{{v.orderStartTime}}</p>
																<p class="mui-ellipsis colorR font12">￥ <span class="f18">{{v.orderMoney | NAAN}}</span></p>
															</div>
															<img src="ecartoon-weixin/img/delete@2x.png" class="delete" style="height:20px;width:20px;position: absolute;top:30px;right:0;"/>
														</a>
													</li>
												</ul>
											</div>
										</li>
									</ul>
							</div>
							<div id="item3" class="mui-slider-item mui-control-content dou-slider">
									<ul class="mui-table-view bg0">
										<li class="mui-table-view-cell p10 mt10 bgW" v-for="v in c">
											<div class="bgw cG">
												订单编号：<span>{{v.no}}</span>
												<ul class="mui-table-view">
													<li class="mui-table-view-cell mui-media p0">
														<a href="javascript:void(0);" class="m0">
															<img class="mui-media-object mui-pull-left" :src="'picture/'+v.image" width="72px" height="72px">
															<div class="mui-media-body font15 pd5 cB">
																<span>{{v.name}}</span>
																<p class="mui-ellipsis font13 cG">开始时间：{{v.orderStartTime}}</p>
																<p class="mui-ellipsis colorR font12">￥ <span class="f18">{{v.orderMoney | NAAN}}</span></p>
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
			</div>
		</div>
		
		<script src="ecartoon-weixin/js/mui.min.js"></script>
		<script src="ecartoon-weixin/js/dropload.js"></script>
		<script type="text/javascript">
		  $(function(){
			  var height = window.screen.height;
			  var roll = $(".dou-slider");
			  roll.each(function(i,item){
				  $(item).css({"height":(height-80),"overflow":"scroll"});
			  });
			  
		  })
		</script>
		<script type="text/javascript">
		mui.init();
		mui('body').on('tap','.payMent',function(){
			window.top.location.href = this.href;
			
			/* 每月只能购买一次淘课的逻辑
			var href = this.href;
			var orderType = href.toString().split("?")[1].split("&")[1].split("=")[1];
			if(orderType == 5){
				$.ajax({
					url:"ecoursewx!checkBuyCourse.asp",
					type:"post",
					data:{},
					dataType:"json",
					success:function(res){
					  if(res.success){
						  window.top.location.href = href;
					  }else{
						 alert("抱歉!您这个月不能再购买课程"); 
					  } 
					}
				});
			}else{
				window.top.location.href = href;
			} */
		});
		mui('body').on('tap','.orderDetail',function(){
		    window.top.location.href=this.href;
		});
		mui('body').on('tap','.delete',function(event){
		    event.stopPropagation();
		    var index = $(".delete").index($(this));
		    var no = orderList.a[index].no;		    
		    var type = orderList.a[index].orderType;
		    if(confirm("确认删除此订单?")){
		    	$.ajax({
		    		url:"eproductorderwx!removeOrder.asp",
		    		type:"post",
		    		data:{no:no,type:type},
		    		dataType:"json",
		    		success:function(res){
		    			if(res.success){
		    				$(".order_delete_li").eq(index).remove();
		    				alert("删除成功!");
		    			}else{
		    				alert("网络异常,请稍后再试!");
		    			}
		    		}
		    	});
		    }
		});
		
		var dd = ${orderLists};
		var page = {item1:1,item2:1,item3:1,tap:1,status:1};
        var orderList = new Vue({
            el:"#app",
            data:{
                   b:dd.b,
            	   a:dd.a,
            	   c:dd.c
            	 },
            filters:{
            	NAAN: function(value){
            		if (value == null || value == undefined || value == "null" || value == "0"){
            			value = "0.00";
            			return value;
            		} else {
            			return value.toFixed(2);
            		}
            		
            	 }
            }
           });
        
        
        var dropItems = {dropItem1:createDrop("#item1"),dropItem2:createDrop("#item2"),dropItem3:createDrop("#item3")};
      //绑定页面上拉、下拉事件
		if(orderList.b.length == 0){
			dropItems.dropItem1.lock('down');
		}
		
		if(orderList.a.length == 0){
			dropItems.dropItem2.lock('down');
		}
		
		if(orderList.c.length == 0){
			dropItems.dropItem3.lock('down');
		}
        
        
        
       /*上拉加载开始*/

    	   
    	  	document.getElementById("item1s").addEventListener("tap",function(){
    	  		page.tap = 1;
				page.status=1;
			},false);
    	  	document.getElementById("item2s").addEventListener("tap",function(){
    	  		page.tap = 2;
				page.status = 0;
			},false);
    	  	document.getElementById("item3s").addEventListener("tap",function(){
    	  		page.tap = 3;
				page.status = 2;
				console.log(page.status);
			},false);
    	  	
			
			function loading(drop,type){
				$.ajax({
					url:"eproductorderwx!findOrderByType.asp",
					type:"post",
					data:{
					      "pageInfo.currentPage":(++page['item'+page.tap]),
					      "ajax":"1",
					      "status":page.status
						 },
					dataType:"json",
					success:function(res){
						if(page.status == 1){
							if(type == "reload"){
								orderList.b = [];
							}
							res.orders.forEach(function(item){
								orderList.b.push(item);
							});
						}else if (page.status == 0){
							if(type == "reload"){
								orderList.a = [];
							}
							res.orders.forEach(function(item){
								orderList.a.push(item);
							});
						}else {
							if(type == "reload"){
								orderList.c = [];
							}
							res.orders.forEach(function(item){
								orderList.c.push(item);
							});
						}
						
					
						if(res.orders.length < 20 || res.orders.length == 0){
							drop.resetload();
				            // 显示无数据
				            drop.noData();
				            drop.resetload();
						}else{
							drop.resetload();
						}
					},
					error:function(e){
						drop.resetload();
					}
				});
				
			}
		
			
			
			
			/*下拉刷新，上拉加载*/
    	   function createDrop (selector){
    		  return $(selector).dropload({
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
       		            domNoData: '<div class="pullDown">暂无更多数据</div>'
       		        },
       		        /* loadUpFn : function(drop){
       		        	//重置页码
       		        	var type = "reload";
       		        	page['item'+page.tap] = 0;
       		        	loading(drop,type);
       		        }, */
       		        loadDownFn: function (drop){
       		        	var type = "loadMore";
       		        	loading(drop,type);
       		        }
       		    });
    	   }
			
       
       
        
            	
			
		</script>
	</body>
</html>