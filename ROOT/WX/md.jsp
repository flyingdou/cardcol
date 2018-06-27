<!doctype html>
<html>

	<head>
		<meta charset="UTF-8">
		<title></title>
		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
		<link href="../css/mui.min.css" rel="stylesheet" />
	
		<!--App自定义的css-->
		<link rel="stylesheet" type="text/css" href="../css/app.css" />
		<!--<link href="../css/mui.picker.css" rel="stylesheet" />
		<link href="../css/mui.dtpicker.css" rel="stylesheet" />-->
		<link rel="stylesheet" type="text/css" href="../css/mui.picker.min.css" />
		<style>
			.fleft{
				
				float: left;
			}
			.fright{
				float: right;
			}
			.tright{
				text-align: right;
			}
			.tleft{
				text-align: left;
			}
			.tcenter{
				text-align: center;
			}
			.fix{
				clear: both;
			}
			.boxcss{
				padding: 10px;
			}
			.body{
				background: white!important;
			}
			/*.mui-content{
				background: white;
			}*/
			.jsxt{
				width: 16px;
				height: auto;
				padding-left: 15px;
				padding-top: 15px;
				font-weight: 700;
			}
			.money{
				color:red;
				font-size: 18px;
				padding-right: 10px;
				margin-bottom: 5px;
			}
			.dz{
				margin: 0 auto;
				font-size: 14px;
				font-weight: 600;
				background: red;;
				padding: 2px;
				width: 100px;
				border-radius: 6px;text-align: center;
				color:white
			}
			.tbbottom{
				border-top:1px solid #eaeaea ;
				border-bottom: 1px solid #eaeaea;
			}
			.grey{
				color:grey
			}
			.w100{
				width: 100%;
				border-bottom: 1px solid #B6B6B6;
			}
			.w33{
				width: 33.3%;
				float: left;
				height: 30px;
				text-align: center;
				background: white;
				padding-top: 5px;
				font-size: 13px;
			}
			.redspan{
				height: 25px;
				line-height: 20px;
				display: inline-block;
				color:red;
				border-bottom: 2px solid red;
			}
			.blackspan{
				color:black;
				border: none;
			}
			.raioBox{
				padding:10px 20px;border-bottom: 1px solid #eaeaea;
				background: white;
			}
			.labelradio{
				margin-left: 40px;font-size: 14px;
			}
		</style>

	</head>

	<body>
		<script src="../js/mui.min.js"></script>
		<script type="text/javascript">
			mui.init()
		</script>
		<div class="mui-content">
		    <div class="w100">
		    	<div class="w33"><span class="redspan">健身目的</span></div>
		    	<div class="w33 "><span class="black">身体数据</span></div>
		    	<div class="w33 "><span class="black">喜欢有氧运动</span></div>
		    	<div class="fix"></div>
		    </div>
		    <form action="">
		    	 <div class="raioBox"style="">
		             <input type="radio" name="radio" id="radio" checked="checked" value="减脂塑形"/><label for="radio" class="labelradio">减脂塑形</label>
		         </div>
		          <div class="raioBox"style="">
		             <input type="radio" name="radio" id="radio" value="健美增肌"/><label for="radio" class="labelradio">健美增肌</label>
		         </div>
		          <div class="raioBox"style="">
		             <input type="radio" name="radio" id="radio" value="增加力量"/><label for="radio" class="labelradio">增加力量</label>
		         </div>
		         <div style="position:relative;margin-top:8px;padding: 10px;border-top:1px solid #eaeaea;background: white;font-size:14px">
		         	<div>选择健身日期:<span id='result' class="ui-alert"style="width: 50%;float: right;text-align: right;"></span>
		         		<button id='demo4' data-options='{"type":"date"}' class="btn mui-btn mui-btn-block"style="position:absolute;width: 50%;padding: 0;top:.5em;right: 0;opacity: 0;">选择日期 </button>
		         	</div>
		         </div>
		    </form>
		   
		    
		    
		</div>
					<footer class="mui-bar mui-bar-footer font-white" style="border:none;background: white;padding: 0;">
							<button type="button" style="background: orange;color:white;width: 100%;height: 100%;box-sizing: border-box;border:none;border-radius: 0;display:block;float: left;">下一步</button>
 
		                 </footer>
	</body>
	<script src="../js/mui.min.js"></script>
		<!--<script src="../js/mui.picker.js"></script>
		<script src="../js/mui.dtpicker.js"></script>-->
		<script src="../js/mui.picker.min.js"></script>
		<script>
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
							result.innerText = rs.text;
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