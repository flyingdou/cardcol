<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
		<meta charset="UTF-8">
		<title>参加挑战</title>
		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
		<link href="ecartoon-weixin/css/mui.min.css" rel="stylesheet" />
		<link rel="stylesheet" type="text/css" href="ecartoon-weixin/css/app.css" />
		<link rel="stylesheet" type="text/css" href="ecartoon-weixin/css/mui.picker.min.css" />
		<style>
			.mui-table-view .mui-media-object {
				line-height: 80px;
				max-width: 106px;
				height: 80px;
			}
			
			.mui-media-body {
				padding-top: 12px;
			}
			
			.mui-table-view:before {
				position: absolute;
				right: 0;
				left: 10px;
				height: 1px;
				content: '';
				-webkit-transform: scaleY(.5);
				transform: scaleY(.5);
				background-color: #c8c7cc;
				top: -1px;
			}
			
			.mui-table-view-cell {
				position: relative;
				overflow: hidden;
				padding: 10px 10px;
				-webkit-touch-callout: none;
				font-size:14px
			}
			
			.mui-table-view-cell>a:not(.mui-btn) {
				position: relative;
				display: block;
				overflow: hidden;
				margin: -10px -10px;
				padding: inherit;
				white-space: nowrap;
				text-overflow: ellipsis;
				color: inherit;
			}
			
			.mui-table-view-cell:after {
				position: absolute;
				right: 0;
				bottom: 0;
				left: 10px;
				height: 1px;
				content: '';
				-webkit-transform: scaleY(.5);
				transform: scaleY(.5);
				background-color: #c8c7cc;
			}
			.input_css{
				height: 21px!important;
				line-height: 21px!important;
				color:#999!important;
				margin: 0!important;
				padding: 0!important;
				text-align: right!important;
				border:none!important;
				outline:none!important;
			}
			.monkey{
				display: -webkit-box;-webkit-box-orient: horizontal;overflow: hidden;margin: 10px 10px 50px 10px;
			}
			.imgbox44{
				width: 33px;height: 33px;
			}
			.say{
				height: 33px;line-height: 16px;font-size: 12px;color:#999; -webkit-box-flex: 1;margin-left:10px 
			}
			.footer{
				position: fixed;width: 100%;height: 64px;left: 0;bottom: 0;padding: 10px;
			}
			.footer input{
				width: 100%;height: 100%;margin: 0;padding: 0;background: #ff4401;border:none
			}
		</style>
	</head>

	<body>
		<form action="eorderwx!saveProductType.asp" id="form1" method="post">
		<input type="hidden" name="jsons" id="jsons" />
		<div class="mui-content">
			<ul class="mui-table-view" style="margin-top: 0px;" id="app">
				<li class="mui-table-view-cell mui-media">
					<a href="javascript:void(0);">
						<img class="mui-media-object mui-pull-left" :src="'picture/'+data1.image">
						<div class="mui-media-body">
							{{data1.name}}
							<p class="mui-ellipsis" style="margin-top:10px;">目标:&nbsp;&nbsp;{{data1.target == 'A' ? data1.days+'天减少体重'+data1.value+'KG' : (data1.target == 'B' ? data1.days+'天增加'+data1.value+'KG' : data1.days+'天运动'+data1.value+'次')}}</p>
							<p class="mui-ellipsis">发起人:&nbsp;&nbsp;{{data1.member.name}}</p>
						</div>
					</a>
				</li>
			</ul>
			<div style="position:relative;margin-top:8px;padding: 10px;border-top:1px solid #eaeaea;background: white;font-size:14px">
				<div>挑战开始日期<span style=""></span><input type="text" id='result' class="ui-alert input_css" style="width: 50%;float: right;text-align: right;font-size:14px;"></input>
					<a id='demo4' data-options='{"type":"date"}' class="btn mui-btn mui-btn-block" style="position:absolute;width: 50%;padding: 0;top:.5em;right: 0;opacity: 0;">选择日期 </a>
				</div>
			</div>
			<ul class="mui-table-view" id="target">
				<li class="mui-table-view-cell" v-if="target == 'A' || target == 'B'">
					<a class="">
						当前体重 <span class="mui-pull-right"><input type="number" id="weight" style="font-size:14px;" class="input_css" placeholder="KG" /></span>
						<div style="color:#999;font-size:14px;">挑战结束后,请进入 "我的挑战" 输入结束时的体重。</div>
					</a>
				</li>
			</ul>
			<ul class="mui-table-view"style="margin-top: 10px;">
			        <li class="mui-table-view-cell">
			            <a class="">
			                                                挑战保证金
			              <span class="mui-pull-right" style="color:#ff4401">
			                <!-- <input type="number" name="" id="price" :value="data1.amerceMoney" class="input_css" /> -->
			                ${data.amerceMoney}元
			              </span>
			            </a>
			        </li>
			    </ul>
			    <div class="monkey">
			    	<div class="imgbox44"><img src="ecartoon-weixin/img/Look-happy.png"width="100%"/></div>
			    	<div class="say">
			    		我自愿参加本挑战活动，无条件遵守活动规则，同意运营方根据挑战规则处置保证金。
			    	</div>
			    </div>
				</div>
          <div class="footer">
          	<input type="button" onclick="submitActive()" style="color:#FFF;" value="支付保证金"/>
          </div>    
        </form>
		<script src="ecartoon-weixin/js/jquery.min.js"></script>
		<script src="ecartoon-weixin/js/vue.min.js"></script>
		<script src="ecartoon-weixin/js/mui.min.js"></script>
		<script src="ecartoon-weixin/js/mui.picker.min.js"></script>
		<script type="text/javascript">
		mui.init()

		function time() {
			var today = new Date();
			var y = today.getFullYear();
			var M = today.getMonth() + 1;
			var d = today.getDate();
			M = jia(M);
			d = jia(d);

			function jia(i) {
				if(i < 10) {
					i = "0" + i;
				}
				return i;
			}
			$(".ui-alert").html(y + "-" + M + "-" + d);
			setTimeout("time()", 1000)
		}
		
		(function(){
			var today = new Date();
			var y = today.getFullYear();
			var M = today.getMonth() + 1;
			var d = today.getDate();
			 $('#result')[0].value = y + "-" + (M<10?'0'+M:M) + "-" + (d<10?'0'+d:d)+" >";
		})();

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
					var today = new Date();
					var y = today.getFullYear();
					var M = today.getMonth() + 1;
					var d = today.getDate();
					
					var picker = new $.DtPicker({
						type: "date", //设置日历初始视图模式
						//真正的月份比写的多一个月。  type的类型你还是可以选择date, datetime month time  hour 

						beginDate: new Date(), //设置开始日期   

						endDate: new Date(y, M, d) //设置结束日期    //真正的是10.21

					});
					picker.show(function(e) {
						var datetext = e.y.text + "-" + e.m.text + "-" + e.d.text;

						result.value = datetext+" >";

						/*
						 * rs.value 拼合后的 value
						 * rs.text 拼合后的 text
						 * rs.y 年，可以通过 rs.y.vaue 和 rs.y.text 获取值和文本
						 * rs.m 月，用法同年
						 * rs.d 日，用法同年
						 * rs.h 时，用法同年
						 * rs.i 分（minutes 的第二个字母），用法同年
						 */
						/* result.innerText = rs.text; */
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
		<script type="text/javascript">
			var id = ${id};
			var data = ${data};
			new Vue({
				el:"#app",
				data:{
					data1:data
				}
			});
			
			new Vue({
				el:"#target",
				data:{
					target:"${data.target}"
				}
			});
			function submitActive(){
				if($("#weight").val() == ""){
					alert("请填写当前体重");
					return;
				}
				var date = new Date();
				var dateFormat = "20"+(date.getYear()+"").substring(1,3)+"-"+((date.getMonth()+1)<10?"0"+(date.getMonth()+1):(date.getMonth()+1))+"-"+(date.getDate()<10?"0"+date.getDate():date.getDate());
				if($("#result").val()!=null && $("#result").val()!=""){
					dateFormat = $("#result").val().replace(">","");
				}
				var price = $("#price").val();
				if(price=="" || price==null){
					price = 1;
				}
				var weight = $("#weight").val()
				if(weight=="" || weight==null){
					weight = 60;
				}
				var jsons = '[{"product":'+id+',"productType":2,"quantity":1,"unitPrice":'+price+',"startTime":'+dateFormat+',"weight":'+weight+'}]';
				$("#jsons").val(jsons);
				$("#form1").submit();	
			}
		</script>
	</body>
</html>