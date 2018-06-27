<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %> 
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
		<meta name="apple-mobile-web-app-capable" content="yes">
		<title>健身日志页面</title>
		<link rel="stylesheet"  href="${pageContext.request.contextPath}/eg/css/mui.min.css"  />
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/eg/css/app.css" />
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/eg/css/mui.picker.min.css" />
		<style type="text/css">
			.title{
				text-align: center;
			}
			#date_show{
				margin:5px 5px;
				border:1px solid green;
				border-radius:3px;
			}
			.date_sel{
				width:15px;
				height:15px;
			}
			.tb{
				width:20px;
				height:20px;
			}
			/*编辑小图标的样式*/
			.mui-table-view .mui-media-object {
			    line-height: 20px;
			    /*max-width: 42px;*/
			   height:20px;
			}
			.yd_shuju{
				line-height:20px;
			}
			/*身高输入一栏样式*/
			.shg{
				float:right;
				text-align:right;
				margin-right:15px;
				width:120px;
				height:23px;
				line-height:23px;
				padding:0px;
				
			}
			.shg input{
				text-align:right;
				height:23px;
				line-height:23px;
				margin-top:-2px;
				margin-bottom:0px;
				padding:0px;
				padding-top:5px;
				width:80px;
				font-size:14px;
				border:none;
			}
			.shengao{
				line-height:20px;
				padding-top:-12px;
				padding-right:2px;
				text-align:right;
				
			}
			.shg1{
				margin-right:12px;
				float:right;
				width:70%;
				height:23px;
				line-height:23px;
				padding:0px;
				text-align: right;
			}
			.shg .shengao1{
				width:100%;
				text-align:right;
				color:gray;
				height:23px;
				line-height:23px;
				margin-top:0px;
				margin-bottom:0px;
				padding:0px;
				padding-top:3px;
				font-size:14px;
				border:none;
			}
			.shg2{
				width:70%;
				margin-right:-10px;
				float:right;
				height:23px;
				padding:0px;
				text-align: right;
			}
			.shg2 .shengao2{
				width:100%;
				text-align:right;
				/*color:gray;*/
				font-size:14px;
			}
			.dw{
			color:#999;
				font-size: 14px;
				position: relative;
				top:0px;
			
			}
			/*身高输入一栏样式结束*/
			#ul1_1{
				margin-left:1em;
			}
			/*li前的图标样式*/
			.tb_img{
				width:15px;
				height:15px;
				padding-top:0px;
				margin-top:3px;
				margin-right:5px;
				line-height:23px;
			}
			.btn{
				height:38px;
				line-height:8px;
				margin-top:18px;
				color:white;
				
			}
			/*p (这里为详细列表图标旁的样式)*/
			p {
			    color: #000000;
			}
		</style>		
	</head>
	<body>
		
		
		<form id="sporForm" action="bodydatawx!bodys.asp" method="post">
	       	<footer class="mui-bar mui-bar-footer font-white"style="border:none;background: white;padding: 0;">
	       		<button type="button" onclick="saveRecord()" style="background: orange;color:white;width: 100%;height: 100%;" >保存</button>
			</footer>
			<input type="hidden" id="height" name="trainRecord.height" value="" />
		<div class="mui-content">
			<div class="mui-scroll-wrapper" style="">
			    <div class="mui-scroll">
			        <!--这里放置真实显示的DOM内容-->
			        <div style="width: 100%;padding: 10px 0;background: white;margin: 10px 0;border-bottom: 1px solid #c8c7cc;;border-top: 1px solid #c8c7cc;">
			       <div style="float: left;text-indent: 1em;">选择日期</div>
			       <div style="position: relative;right:0;width:100%;position:absolute;box-sizing: border-box;overflow: hidden;">
			       <div id="result" class="ui-alert cgreen"style="display: inline-block;float: right;margin-right:1em ;">2017-06-26</div>
			       <input id="doneDate" type="hidden" name="trainRecord.doneDate" value="2017-06-26" />
				    <button id="demo4" data-options="{&quot;type&quot;:&quot;date&quot;}" class="btn mui-btn mui-btn-block"style="width: 80px;position:absolute;top:-20px;right:1em;height: 100%;opacity: 0;"></button>
			       </div>
			       <div style="clear: both;"></div>
			       </div>
			        <ul class="mui-table-view" id="ul1">
			        	<li class="mui-table-view-cell mui-media">
			        		<div class="mui-media-body yd_shuju">身体数据</div>
			        	</li>
			        	<ul class="mui-table-view" id="ul1_1"> 
			        		<li class="mui-table-view-cell">
			        			<a >
									<p class='mui-ellipsis nav_sel mui-pull-left' style="">体重</p>
									<span class="mui-pull-right shg shg1 dw" >
										<input type="number" name="trainRecord.weight" id="weight"  class="shengao1" placeholder="" style="text-align:right;" value="" /> kg
									</span>
								</a>
			        		</li>
			        		<li class="mui-table-view-cell ">
								<a >
									<p class='mui-ellipsis nav_sel mui-pull-left' style="">腰围</p>
									<span class="mui-pull-right shg shg1 dw">
										<input type="number" id="waist" name="trainRecord.waist"  class="shengao1" placeholder="" value="" /> cm
									</span>
								</a>
							</li>
						<li class="mui-table-view-cell ">
								<a >
									<p class='mui-ellipsis nav_sel mui-pull-left' style="">臀围</p>
									<span class="mui-pull-right shg shg1 dw">
										<input type="number" id="hip" name="trainRecord.hip"  class="shengao1" placeholder="" value="" /> cm
									</span>
								</a>
							</li>
							<li class="mui-table-view-cell ">
								<a >
									<p class='mui-ellipsis nav_sel mui-pull-left' style="">运动时间</p>
									<span class="mui-pull-right shg shg2"style="padding: 0!important;">
										<input type="number" id="times" name="trainRecord.times" class="shengao" placeholder="" value="" /><span class="dw"style=''> 分钟</span>
									</span>
								</a>
							</li>
			        	</ul>
			        </ul>
			         <ul class="mui-table-view" id="ul1"style="margin-top: 1em;">
			        	<li class="mui-table-view-cell mui-media">
			        		<div class="mui-media-body yd_shuju">我的健康评估 </div>
			        	</li>
			        	<li class="mui-table-view-cell mui-media">
                         <div style="width: 80px;height: 80px;background: url(${pageContext.request.contextPath}/WX/images/iconfont-ren.png)no-repeat  scroll center center/64px auto;"></div>
			        		<div style="width: 100%;height: 80px;position:absolute;top:11px;padding-left: 80px;padding-right: 20px;">
			        			<div style="width: 100%;height: 40px;border-bottom: 1px solid #eaeaea;">
			        				<div style="float:left;font-size:13px;line-height: 20px;">
			        					<span>体质指数</span>
			        					<div style="color:#999">相关疾病发病率的危险系数高</div>
			        				</div>
			        				<div id="physique" style="float:right;font-size：20px;line-height: 40px;">
			        					
			        				</div>
			        			</div>
			        			
			        			<div style="width: 100%;height: 40px;padding-top:5px ;">
			        				<div style="float:left;font-size:13px;line-height: 20px;">
			        					<span>腰臀比 </span>
			        					<div style="color:#999">相关疾病发病率的危险系数高</div>
			        				</div>
			        				<div id="waistAndHipRatio" style="float:right;font-size：20px;line-height: 40px;">
			        					
			        				</div>
			        			</div>
			        			
			        		</div>
			        	</li>
			         </ul>
			</div>
		</div>
		</div>
		</form>
	</body>
	<script src="${pageContext.request.contextPath}/eg/js/mui.min.js"></script>
	<script src="${pageContext.request.contextPath}/eg/js/mui.picker.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/WX/js/jquery-2.2.0.min.js"></script>
	<script type="text/javascript">
	    var today = new Date()
        var y = today.getFullYear()
        var M = today.getMonth() + 1
        var d = today.getDate()
        M = jia(M)
        d = jia(d)
            function jia(i) {
            if (i < 10) {
                i = "0" + i;
            }
            return i;
        }
	    $("#result").html(y+"-"+M+"-"+d);
	</script>
	<script type="text/javascript">
	// 如果数据不为空 则填充数据
	<s:iterator value="pageInfo.items">
		$("#height").val('<s:property value="height"/>')
		$("#weight").val('<s:property value="weight"/>')
		$("#waist").val('<s:property value="waist"/>');
		$("#hip").val('<s:property value="hip"/>');
		var times = '<s:property value="times"/>';
		$("#times").val(times.substring(0,times.lastIndexOf(".")));
		$("#result").html('<s:date name="done_date" format="yyyy-MM-dd" />');
	</s:iterator>
		// 计算体质指数 和 臀腰比 
		// 体质指数 = 体重/身高的平方 体重的单位是：千克；身高的单位是米 
		// 腰臀比 = 腰围/臀围
		var height = $("#height").val()/100;
		var weight = $("#weight").val();
		var waist = $("#waist").val();
		var hip = $("#hip").val();
		
		if (height != "" && height != 0 && weight != "" && weight != 0) {
			var physique = (weight/(height * height)).toFixed(1);	
			// 设置体质指数
			$("#physique").html(physique);			
		}
		
		if (waist != "" && waist != 0 && hip != "" && hip != 0) {
			var waistAndHipRatio = (waist/hip).toFixed(1);
			// 设置腰臀比
			$("#waistAndHipRatio").html(waistAndHipRatio);		
		}
		
		// 保存身体数据
		function saveRecord(){
	    	// 获取当前完成时间
	    	var doneDate =  $("#result").html();
	    	$("#doneDate").val(doneDate);
	    	
	    	var parms = $('#sporForm').serialize();
	    	$.ajax({
	    		type:'post',
	    		url:'bodydatawx!saveRecord.asp',
	    		data: parms,
				success:function(msg){
					var _json = $.parseJSON(msg);
					var result = _json.msg;
					if(result == "okRecord"){
						alert("当前训练日志已成功保存！");
					}
				}
			});
	    }
	</script>
	
	<script>
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
					var picker = new $.DtPicker(options);
					picker.show(function(rs) {
						/*
						 * rs.value 拼合后的 value
						 * rs.text 拼合后的 text
						 * rs.y 年，可以通过 rs.y.vaue 和 rs.y.text 获取值和文本
						 * rs.m 月，用法同年
						 * rs.d 日，用法同年
						 * rs.h 时，用法同年
						 * rs.i 分（minutes 的第二个字母），用法同年
						 */
						result.innerText =rs.text;
				     
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
		
		
		
		mui('.mui-scroll-wrapper').scroll({
			deceleration:0.0005
		});
		mui.plusReady(function(){
			//跳转到日历页面
			mui('.title').on('tap','img',function(){
				var url=this.getAttribute('id');
				if(url){
					mui.openWindow({
						url:url,
						id:url
					});
				}
			});
		});
		
		
	</script>
		
</html>