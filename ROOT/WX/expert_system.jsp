<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!doctype html>
<html>

<head>
<meta charset="UTF-8">
<title></title>
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
<link href="${pageContext.request.contextPath}/eg/css/mui.min.css" rel="stylesheet" />
<!--App自定义的css-->
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/eg/css/app.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/eg/css/mui.picker.min.css" />
<style>
.fleft {
	float: left;
}

.fright {
	float: right;
}

.tright {
	text-align: right;
}

.tleft {
	text-align: left;
}

.tcenter {
	text-align: center;
}

.fix {
	clear: both;
}

.boxcss {
	padding: 10px;
}

.body {
	background: white !important;
}
/*.mui-content{
				background: white;
			}*/
.jsxt {
	width: 16px;
	height: auto;
	padding-left: 15px;
	padding-top: 15px;
	font-weight: 700;
}

.money {
	color: red;
	font-size: 18px;
	padding-right: 10px;
	margin-bottom: 5px;
}

.dz {
	margin: 0 auto;
	font-size: 14px;
	font-weight: 600;
	background: red;;
	padding: 2px;
	width: 100px;
	border-radius: 6px;
	text-align: center;
	color: white
}

.tbbottom {
	border-top: 1px solid #eaeaea;
	border-bottom: 1px solid #eaeaea;
}

.grey {
	color: grey
}

.w100 {
	width: 100%;
	border-bottom: 1px solid #B6B6B6;
}

.w33 {
	width: 33.3%;
	float: left;
	height: 30px;
	text-align: center;
	background: white;
	padding-top: 5px;
	font-size: 13px;
}

.redspan {
	height: 25px;
	line-height: 20px;
	display: inline-block;
	color: red;
	border-bottom: 2px solid red;
}

.blackspan {
	color: black;
	border: none;
}

.raioBox {
	padding: 10px 20px;
	border-bottom: 1px solid #eaeaea;
	background: white;
}

.labelradio {
	margin-left: 40px;
	font-size: 14px;
}

/*2*/
.fleft {
	float: left;
}

.fright {
	float: right;
}

.tright {
	text-align: right;
}

.tleft {
	text-align: left;
}

.tcenter {
	text-align: center;
}

.boxcss {
	padding: 10px;
}

.body {
	background: white !important;
}
/*.mui-content{
				background: white;
			}*/
.jsxt {
	width: 16px;
	height: auto;
	padding-left: 15px;
	padding-top: 15px;
	font-weight: 700;
}

.money {
	color: red;
	font-size: 18px;
	padding-right: 10px;
	margin-bottom: 5px;
}

.dz {
	margin: 0 auto;
	font-size: 14px;
	font-weight: 600;
	background: red;;
	padding: 2px;
	width: 100px;
	border-radius: 6px;
	text-align: center;
	color: white
}

.tbbottom {
	border-top: 1px solid #eaeaea;
	border-bottom: 1px solid #eaeaea;
}

.grey {
	color: grey
}

.w100 {
	width: 100%;
	border-bottom: 1px solid #B6B6B6;
}

.w33 {
	width: 33.3%;
	float: left;
	height: 30px;
	text-align: center;
	background: white;
	padding-top: 5px;
	font-size: 13px;
}

.redspan {
	height: 25px;
	line-height: 20px;
	display: inline-block;
	color: red;
	border-bottom: 2px solid red;
}

.blackspan {
	color: black;
	border: none;
}

.raioBox {
	padding: 10px 20px;
	border-bottom: 1px solid #eaeaea;
	background: white;
	height: 43px;
	box-sizing: border-box;
	font-size: 12px;
}

.labelradio {
	margin-left: 40px;
	font-size: 14px;
}

.sex {
	float: left;
	text-indent: 1.5em;
	background: url(../images/xb.png) no-repeat scroll 0 4px/16px auto;
}

.selectcss {
	padding: 0;
	font-size: 12px;
}

.sgimg {
	float: left;
	text-indent: 1.5em;
	background: url(../images/man.png) no-repeat scroll 0 4px/16px auto;
}

.ywimg {
	float: left;
	text-indent: 1.5em;
	background: url(../images/men.png) no-repeat scroll 0 4px/16px auto;
}

.sgbox {
	float: right;
	width: 50%;
}

.srbox {
	float: right;
	width: 50%;
}

.tzimg {
	float: left;
	text-indent: 1.5em;
	background: url(../images/weight.png) no-repeat scroll 0 4px/16px auto;
}

.srinput {
	width: 100% !important;
	padding: 0 !important;
	line-height: 20px !important;
	font-size: 14px;
	text-align: right !important;
	height: 20px !important;
	border: none !important
}

.wtimg {
	float: left;
	text-indent: 1.5em;
	background: url(../images/act.png) no-repeat scroll 0 4px/16px auto;
}
/*3*/
.fleft {
	float: left;
}

.fright {
	float: right;
}

.tright {
	text-align: right;
}

.tleft {
	text-align: left;
}

.tcenter {
	text-align: center;
}

.fix {
	clear: both;
}

.boxcss {
	padding: 10px;
}

.body {
	background: white !important;
}
/*.mui-content{
				background: white;
			}*/
.jsxt {
	width: 16px;
	height: auto;
	padding-left: 15px;
	padding-top: 15px;
	font-weight: 700;
}

.money {
	color: red;
	font-size: 18px;
	padding-right: 10px;
	margin-bottom: 5px;
}

.dz {
	margin: 0 auto;
	font-size: 14px;
	font-weight: 600;
	background: red;;
	padding: 2px;
	width: 100px;
	border-radius: 6px;
	text-align: center;
	color: white
}

.tbbottom {
	border-top: 1px solid #eaeaea;
	border-bottom: 1px solid #eaeaea;
}

.grey {
	color: grey
}

.w100 {
	width: 100%;
	border-bottom: 1px solid #B6B6B6;
}

.w33 {
	width: 33.3%;
	float: left;
	height: 30px;
	text-align: center;
	background: white;
	padding-top: 5px;
	font-size: 13px;
}

.redspan {
	height: 25px;
	line-height: 20px;
	display: inline-block;
	color: red;
	border-bottom: 2px solid red;
	box-sizing: border-box;
}

.blackspan {
	box-sizing: border-box;
	color: black;
	border: none;
}

.mui-bar {
	padding: 0 !important;
}

.footercss {
	border: none;
	background: white;
	padding: 0 !important;
}

.footerbotton {
	background: orange;
	color: white;
	width: 100%;
	height: 100%;
	box-sizing: border-box;
	border: none;
	border-radius: 0;
	display: block;
	float: left;
}

.form_nextbox {
	padding: 10px;
	background: white;
	border-bottom: 1px solid #eaeaea;
}

.paobu {
	height: 60px;
	line-height: 60px;
	float: left;
	text-indent: 1em;
	font-size: 14px
}

.chickb {
	height: 60px;
	line-height: 60px;
}

.chickboxcss {
	float: right;
	margin-top: 26px;
}
</style>

</head>

<body>
	<script src="${pageContext.request.contextPath}/eg/js/mui.min.js"></script>
	<script type="text/javascript">
		mui.init()
	</script>
	<form action="goodswx!wyTraningPlan.asp" id="form1">
		<div class="mui-content">
			<div class="w100">
				<div class="w33">
					<span class="black redspan">健身目的</span>
				</div>
				<div class="w33 ">
					<span class="black">身体数据</span>
				</div>
				<div class="w33 ">
					<span class="black">喜欢有氧运动</span>
				</div>
				<div class="fix"></div>
			</div>

			<div class="w33_next">
				<div class="raioBox" style="">
					<input type="radio" name="radio" id="radio" checked="checked" value="减脂塑形" />
					<label for="radio" class="labelradio">减脂塑形</label>
				</div>
				<div class="raioBox" style="">
					<input type="radio" name="radio" id="radio" value="健美增肌" />
					<label for="radio" class="labelradio">健美增肌</label>
				</div>
				<div class="raioBox" style="">
					<input type="radio" name="radio" id="radio" value="增加力量" />
					<label for="radio" class="labelradio">增加力量</label>
				</div>
				<div
					style="position: relative; margin-top: 8px; padding: 10px; border-top: 1px solid #eaeaea; background: white; font-size: 14px">
					<div>
						选择健身日期:
						<span id='result' class="ui-alert" style="width: 50%; float: right; text-align: right;"></span>
						<a id='demo4' data-options='{"type":"date"}' class="btn mui-btn mui-btn-block"
							style="position: absolute; width: 50%; padding: 0; top: .5em; right: 0; opacity: 0;">选择日期 </a>
					</div>
				</div>
			</div>

			<!--2-->
			<div class="w33_next">
				<div class="raioBox" style="">
					<div class="sex">性别:</div>
					<div class="fright">
						<select name="" class="selectcss">
							<option value="男">男</option>
							<option value="女">女</option>
						</select>
					</div>
					<div class="fix"></div>
				</div>
				<div class="raioBox">
					<div class="sgimg">身高:</div>
					<div class="sgbox">
						<input type="text" name="" id="sg" value="" placeholder="输入身高(cm)" class="srinput" />
					</div>
					<div class="fix"></div>
				</div>
				<div class="raioBox">
					<div class="ywimg">腰围:</div>
					<div class="srbox">
						<input type="text" name="" id="yw" value="" placeholder="输入腰围(cm)" class="srinput" />
					</div>
					<div class="fix"></div>
				</div>
				<div class="raioBox" style="">
					<div class="tzimg">体重:</div>
					<div class="srbox">
						<input type="text" name="" id="tz" value="" placeholder="输入体重(kg)" class="srinput" />
					</div>
					<div class="fix"></div>
				</div>
				<div class="raioBox">
					<div class="wtimg">最大卧推重量:</div>
					<div class="srbox">
						<input type="text" name="" id="zl" value="" placeholder="输入重量(kg)" class="srinput" />
					</div>
					<div class="fix"></div>
				</div>
			</div>
			<!--3-->
			<div class="w33_next">
				<!-- 动作列表开始 -->
			 	<s:iterator value="#request.actions">
					<div style="" class="form_nextbox">
						<img src="../<s:property value="image"/>" width="90px" height="60px" style="float: left;" />
						<div style="" class="paobu"><s:property value="name"/></div>
						<div style="" class="chickb">
							<input type="checkbox" name="check" id="" value="跑步" value="<s:property value="id"/>" class="chickboxcss" />
						</div>
						<div class="fix"></div>
					</div>
				</s:iterator>
				<!-- 动作列表结束 -->
			</div>
		</div>
		<footer class="mui-bar mui-bar-footer font-white" style="border: none; background: white; padding: 0;">
			<input type="button" class="next"
				style="background: orange; color: white; width: 100%; height: 100%; box-sizing: border-box; border: none; border-radius: 0; display: block; float: left;"
				value="下一步">
		</footer>
	</form>
</body>
<script src="${pageContext.request.contextPath}/eg/js/mui.min.js"></script>
<script src="${pageContext.request.contextPath}/eg/js/mui.picker.min.js"></script>
<script src="${pageContext.request.contextPath}/eg/js/jquery-3.1.1.min.js"></script>
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
<script>
	function time() {
		var today = new Date();
		var y = today.getFullYear();
		var M = today.getMonth() + 1;
		var d = today.getDate();
		M = jia(M);
		d = jia(d);
		function jia(i) {
			if (i < 10) {
				i = "0" + i;
			}
			return i;
		}
		$(".ui-alert").html(y + "年" + M + "月" + d + "日");
		setTimeout("time()", 1000)
	}

	function checkedTest() {
		var count = 0;
		var checkArry = document.getElementsByName("check");
		for (var i = 0; i < checkArry.length; i++) {
			if (checkArry[i].checked == true) {
				//选中的操作
				count++;
				$("#form1").submit();
			}
		}
		if (count == 0) {
			alert("请选择！")
		}
	}

	$(document).ready(function() {

		time();

		var index = 0;
		$(".w33 span").removeClass("redspan").eq(index).addClass("redspan");
		$(".w33_next").css("display", "none").eq(index).css("display", "block");
		$(".next").click(function() {
			index++;
			var sg = $('#sg').val();
			var tz = $("#tz").val();
			var yw = $("#yw").val();
			var zl = $("#zl").val();

			//	第二页判断值是否为空，是否是数字，不是让index还是1
			if (validateBodyData(sg,zl,yw,tz)) {
				index = index;
			} else {
				index = 1
			}
			if (index > 2) {
				checkedTest();
				index = 2;
			}

			$(".w33_next").css("display", "none").eq(index).css("display", "block");
			$(".w33 span").removeClass("redspan").eq(index).addClass("redspan");

		});

	})
	
	function validateBodyData(sg,zl,yw,tz) {
		if (!(!isNaN(sg) && sg != "" && !isNaN(zl) && zl != "" && !isNaN(yw) && yw != "" && !isNaN(tz) && tz != "")) {
			return false;
		}
		
		if (sg <= 0 || sg >= 300) {
			alert("请输入合理的身高区间 1 - 299");
			return false;
		}
		
		if (tz <= 0 || tz > 500) {
			alert("请输入合理的体重区间 0 - 500");
			return false;
		}
		
		return true;
	}
</script>
</html>