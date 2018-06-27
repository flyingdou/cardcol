<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>可参加的挑战-挑战报名</title>
		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
		<meta name="apple-mobile-web-app-capable" content="yes">
		<link  rel="stylesheet" href="${pageContext.request.contextPath}/eg/css/mui.min.css" />
		<link rel="stylesheet" href="${pageContext.request.contextPath}/eg/css/mui.picker.min.css" />
		<script type="text/javascript" src="${pageContext.request.contextPath}/WX/js/jquery-2.2.0.min.js"></script>
		<style type="text/css">
			.title{
				height:150px;
				background-image:url(../images/shuijiao.jpg);
				background-repeat: no-repeat;
				background-position:100% auto;
				background-size:100% 100%;
			}
			.zhezhao{
				background-color:rgba(0,0,0,.7);
				position:absolute;
				top:0;
				width:100%;
				height:150px;
				z-index: 1111;
			}
			.yh_img{
				float:left;
				width:100px;
				height:75px;
				margin-left: 20px;
    			margin-top: 35px;
			}
			.xx{
				float:left;
				line-height:12px;
				margin:45px 20px;
				/*border:1px solid #111;*/
			}
			.xx .bt{
				color:white;
			}
			.mui-table-view{
				margin-bottom:20px;
			}
			/*选择日历的图标的大小*/
			#sel_data{
				width:20px;
				height:20px;
			}
			.mui-table-view-cell a{
				position: relative;
			}
			.center_show{
				width: 130px;
				height:23px;
				border:none;
			    position: absolute;
			    left: 130px;
			    top: 10px;
			    /*background: red;*/
			    padding: 0;
			    color:#999999;
			}
					.center_show1{
				width: 130px;
				height:23px;
				border:none;
			    position: absolute;
			    right: 1px;
			    top: 10px;
			    /*background: red;*/
			    padding: 0;
			    color:#999999;
			}
			.bzj{
				height:140px;
				text-align: center;
				padding-top: 20px;
			}
			.pl_bg{
				background: -o-linear-gradient(#fe9e3a,#FF8B9E);
				background: -webkit-linear-gradient(#fe9e3a,#FF8B9E);
				background: -moz-linear-gradient(#fe9e3a,#FF8B9E);
				background:linear-gradient(#fe9e3a,#FF8B9E);
				width:65px;
				height:65px;
				border-radius: 100%;
				text-align: center;
				margin:0 auto;
				line-height: 65px;
				color:#ffffff;
				font-size: 15px;
				
			}
			.this_bzj{
				margin-top: 10px;
			}
			/*fe9e3a*/
			.tz_shm{
				color:#fe9e3a;
				margin-top: 20px;
				
			}
			.shm1{
				margin-top:10px;
				color:#999999;
				font-size: 14px;
			}
			.shm2{
				margin-top:5px;
				color:#999999;
				font-size: 14px;
			}
			/*底部样式设计  开始*/
			.footer_zhifu{
				text-align:center;
				background-color:#FF6A00;
				padding:15px 0;
				color:white;
				font-size:15px;
			}
			.mui-bar{
				padding:0px;
			}
			/*底部样式设计结束*/
		</style>
	</head>
	<script type="text/javascript">
		function pay() {
			window.location.href = "activelistwx!orderInfo.asp";
		}
	</script>
	<body>
		<s:iterator value="active">
		<footer class="mui-bar mui-bar-footer">
			<div class="footer_zhifu" id="to_pay" onclick="pay()">支付保证金</div>
		</footer>
		<div class="mui-content">
			<div class="mui-scroll-wrapper">
			    <div class="mui-scroll">
			    		<div class="title">
			    		<div class="zhezhao">
			    			<img src="../images/shuijiao.jpg" class="yh_img"/>
			    			<div class="xx">
			    				<p class="bt">发布人：<span id="name"><s:property value="creator.name" /></span></p>
			    				<p class="bt">裁判人：<span id="caipan"></span></p>
			    				<p class="bt">参加人数：<span id="number"><s:property value="#request.count" /></span>人</p>
			    			</div>
			    		</div>
			    	</div>
			    	<ul class="mui-table-view">
			    		<li class="mui-table-view-cell" id="">
			    			<a >
			    				<span class="mui-pull-left">开始挑战时间</span>
			    				<input class="center_show"style="text-align: right;right: 1px;" readonly="readonly" id="star_time"  />
			    				<img src="${pageContext.request.contextPath}/WX/images/date_sel.jpg" data-options='{"type":"date","beginYear":"2017","endYear":"2100"}' class="mui-pull-right" id="sel_data"/>
			    			</a>
			    		</li>
			    
			    		<li class="mui-table-view-cell">
			    			<a class="mui-navigate-right">
			    				<span class="mui-pull-left">当前体重</span>
			    				<input class="center_show1"style="text-align: right;right: 28px;" placeholder="输入体重"  />
			    			</a>
			    		</li>
			    		
			    		<li class="mui-table-view-cell">
			    				<span class="mui-pull-left">挑战保证金</span>
			    				<span class="mui-pull-right"><s:property value="amerce_money"/>元</span>
			    				<div style="clear: both;"></div>
			    				<p style="margin-top: 10px;">
			    					<span style="color:orangered">声明：</span>
			    					<s:property value="memo"/>
			    				</p>
			    		</li>
			    	</ul>
			    </div>
			</div>
		</div>
		</s:iterator>
	</body>
	<script src="${pageContext.request.contextPath}/eg/js/mui.min.js"></script>
	<script src="${pageContext.request.contextPath}/eg/js/mui.picker.min.js"></script>
	<script type="text/javascript">
		mui('.mui-scroll-wrapper').scroll({
			deceleration:0.0005
		});
		
		//选择日期
		var sel_data=document.getElementById('sel_data');
		sel_data.addEventListener('click',function(){
			var star_time=document.getElementById('star_time');
			var optionsJson=this.getAttribute('data-options')||'{}';
			var options=JSON.parse(optionsJson);
			var picker=new mui.DtPicker(options);
			picker.show(function(rs){
				star_time.value=rs.text;
				picker.dispose();
			});
		});
		
		//点击支付保证金时跳转到submit_order.html
		/* var to_pay=document.getElementById('to_pay');
		to_pay.addEventListener('click',function(){ */
			/* var startDate = $(".center_show").val();
			var weight = $("#center_show1").val();
			if (startDate == '') {
				alert("请选择开始时间");
				return;
			} else if (weight == '') {
				alert("请输入体重");
				return;
			} else {
				$.ajax({
					url : "activepaywx!savePartake.asp",
					type : "post",
					data : {
						"startDate" : startDate,
						"weight" : weight,
						"id" : id
					},
					success : function(msg) {
						var obj = eval("(" + msg + ")");
						if (obj.success == false) {
							alert(obj.message);
						} else if (obj.success == true) {
							var id = obj.order.id;
							window.location.href = "activepaywx!OrderInfo.asp?id=" + id;
						}

					},
				}); */
			/* } */
		});
	</script>
</html>
