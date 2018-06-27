<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + path + "/";
%>
<!doctype html>
<html>
<head>
<meta charset="UTF-8">
<base href="<%=basePath%>">
<title>智能健身计划引擎</title>
<meta name="viewport"
	content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
<link href="expert/css/mui.min.css" rel="stylesheet" />

<!--App自定义的css-->
<link rel="stylesheet" type="text/css" href="expert/css/app.css" />
<!--<link href="../css/mui.picker.css" rel="stylesheet" />
		<link href="../css/mui.dtpicker.css" rel="stylesheet" />-->
<link rel="stylesheet" type="text/css" href="expert/css/mui.picker.min.css" />
<script src="expert/js/jquery.js" type="text/javascript"></script>
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
	border-top: 1px solid #f2f2f2;
	border-bottom: 1px solid #f2f2f2;
}

.grey {
	color: grey
}

.w100 {
	width: 100%;
	border-bottom: 1px solid #f2f2f2;
}

.w33 {
	width: 33.3%;
	float: left;
	height: 44px;
	text-align: center;
	background: white;
	line-height: 44px;
	font-size: 13px;
	color: #999999;
}

.w33_next {
	width: 100%;
	height: 100%;
	overflow: hidden;
}

.redspan {
	height: 44px;
	line-height: 44px;
	display: inline-block;
	color: #1e1e1e;
	position: relative;
}

.redspan:after {
	content: "";
	position: absolute;
	width: 50%;
	height: 44px;
	left: 25%;
	border-bottom: 2px solid #FF4401;
	box-sizing: border-box;
}

.blackspan {
	color: black;
	border: none;
}

.raioBox {
	padding: 14px 10px;
	background: white;
	height: 70px;
	box-sizing: border-box;
	font-size: 12px;
	display: -webkit-box;
	-webkit-box-orient: horizontal;
	position: relative
}

.raioBox:after {
	content: "";
	width: 100%;
	bottom: 0;
	border-bottom: 1px solid #f2f2f2;
	left: 10px;
	position: absolute;
}

.label_box1 {
	-webkit-box-flex: 9;
}

.input_box1 {
	-webkit-box-flex: 1;
	line-height: 40px;
	text-align: right;
}

.labelradio {
	margin-left: 10px;
	display: block !important;
	font-size: 15px !important;
	line-height: 20px !important;
}

.mui-radio input[type=radio] {
	position: absolute;
	top: 23px !important;
	right: 10px !important;
	display: inline-block;
	width: 16px;
	height: 16px;
	border: 0;
	outline: 0 !important;
	background-color: inherit;
	-webkit-appearance: none;
}

.mui-radio input[type=radio]:checked:before {
	content: '' !important;
	border: 4px solid #FF4401;
	box-sizing: border-box;
	border-radius: 50%;
	width: 16px;
	height: 16px;
	top: 3px;
	position: absolute
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

.raioBox1 {
	background: white;
	height: 45px;
	box-sizing: border-box;
	font-size: 12px;
	display: -webkit-box;
	-webkit-box-orient: horizontal;
	position: relative
}

.raioBox1:after {
	content: "";
	width: 100%;
	bottom: 0;
	border-bottom: 1px solid #f2f2f2;
	left: 10px;
	position: absolute;
}

.sex, .sgimg, .ywimg, .tzimg, .wtimg {
	-webkit-box-flex: 1;
	line-height: 45px;
	padding-left: 10px;
	color: #1e1e1e;
	font-size: 15px
}

.sex1 {
	-webkit-box-flex: 1;
	line-height: 45px;
	font-size: 13px;
	color: #999999;
}

.sex2 {
	width: 100%;
	height: 100%;
	padding-right: 5px;
	display: -webkit-box;
	-webkit-box-orient: horizontal;
	-webkit-box-pack: end;
}

#st2, #st1 {
	width: 0px;
	height: 0px;
}

.st2, .st1 {
	right: 0;
	width: 40px;
	height: 20px;
	border: 1px solid #f2f2f2;
	box-sizing: border-box;
	display: inline-block;
	margin: 10px 5px;
	line-height: 20px;
	text-align: center;
	border-radius: 3px;
	color:#000;
}

.chickedit {
	background-color:#ff4401;
	border: none;
	color: white;
}

.srinput {
	height: 45px !important;
	margin: 0px !important;
	border: none;
	color: #999999;
	font-size: 13px;
	text-align: right;
	border: none !important;
	padding:0 !important;
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
/*.redspan{
				height: 25px;
				line-height: 20px;
				display: inline-block;
				color:red;
				border-bottom: 2px solid red;
				box-sizing: border-box;
			}*/
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
/*.form_nextbox{
				padding: 10px;background: white;border-bottom: 1px solid #eaeaea;
			}*/
.form_nextbox {
	background: white;
	height: 45px;
	box-sizing: border-box;
	font-size: 12px;
	display: -webkit-box;
	-webkit-box-orient: horizontal;
	position: relative
}

.form_nextbox:after {
	content: "";
	width: 100%;
	bottom: 0;
	border-bottom: 1px solid #f2f2f2;
	left: 10px;
	position: absolute;
}

.one {
	width: 50%;
	height: 100%;
	box-sizing: border-box;
	position: relative
}

.two {
	width: 100%;
	height: 45px;
	box-sizing: border-box;
	position: relative;
	display: -webkit-box;
	-webkit-box-orient: horizontal;
	padding: 10px;
	position: relative;
}

.one:before {
	content: "";
	position: absolute;
	width: 100%;
	height: 16px;
	top: 15px;
	border-right: 1px solid #f2f2f2;
	display: block;
}

.checkboxImg {
	width: 25px;
	height: 25px;
}

.checkboxlabel {
	-webkit-box-flex: 1;
	height: 25px;
	line-height: 25px;
	display: block;
	text-align: left;
	margin-left: 10px;
}

.chick_css {
	margin-top: 6px;
	margin-right: 10px;
}

.mui-checkbox label {
	display: block;
	float: none;
	width: auto padding-right: 0;
}

.mui-input-row label {
	font-family: 'Helvetica Neue', Helvetica, sans-serif;
	/* line-height: 1.1;*/
	float: none;
	width: auto;
	padding: 0;
}

.checkboxlabel {
	-webkit-box-flex: 1;
	height: 25px;
	line-height: 25px !important;
	display: block;
	text-align: left;
	margin-left: 10px;
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

.colorG {
	color: #999999 !important;
}

.font12 {
	font-size: 12px;
}

.ui-alert {
	color: #999999;
}

.mui-checkbox input[type=checkbox], .mui-radio input[type=radio] {
	position: absolute;
	top: 7px;
	right: 0px;
	display: inline-block;
	width: 16px;
	height: 16px;
	border: 0;
	outline: 0 !important;
	background-color: inherit;
	-webkit-appearance: none;
}

.mui-checkbox input[type=checkbox]:checked:before, .mui-radio input[type=radio]:checked:before
	{
	color: #FF4401;
}

.mui-checkbox input[type=checkbox]:before, .mui-radio input[type=radio]:before
	{
	font-family: Muiicons;
	font-size: 16px;
	font-weight: 400;
	line-height: 1;
	text-decoration: none;
	color: #aaa;
	border-radius: 0;
	background: 0 0;
	-webkit-font-smoothing: antialiased;
	right: 0;
}
</style>

</head>

<body>
	<form action="ecoursewx!saveMemberSetting.asp?id=1" method="post" id="form1" style="overflow:scroll;">
		<div class="mui-content" id="memberSetting">
			<div class="w100">
				<div class="w33">
					<span class="black redspan">健身目标</span>
				</div>
				<div class="w33 ">
					<span class="black">身体数据</span>
				</div>
				<div class="w33 ">
					<span class="black">喜欢的有氧运动</span>
				</div>
				<div class="fix"></div>
			</div>
			<div class="w33_next">
				<div class="raioBox mui-input-row mui-radio" style="">
					<img src="expert/img/buy/Reduced-fat-shape.png" width="42px" height="42px" />
					<div class="label_box1">
						<label for="radio" class="labelradio">减脂塑形
							<div class="font12 colorG">推荐体型肥胖者选择</div>
						</label>
					</div>
					<div class="input_box1">
						<input type="radio" class="" name="target" id="radio" value="A" v-model="model.target"/>
					</div>
				</div>
				<div class="raioBox mui-input-row mui-radio" style="">
					<img src="expert/img/buy/Didnt-add-muscle.png" width="42px" height="42px" />
					<div class="label_box1">
						<label for="radio1" class="labelradio ">健美增肌
							<div class="font12 colorG">推荐健美爱好者选择</div>
						</label>
					</div>
					<div class="input_box1">
						<input type="radio" class="" name="target" id="radio1" value="B" v-model="model.target"/>
					</div>
				</div>
				<div class="raioBox mui-input-row mui-radio" style="">
					<img src="expert/img/buy/strength.png" width="42px" height="42px" />
					<div class="label_box1">
						<label for="radio2" class="labelradio ">增加力量
							<div class="font12 colorG">推荐需要变得强壮者选择</div>
						</label>
					</div>
					<div class="input_box1">
						<input type="radio" class="" name="target" id="radio2"
							checked="checked" value="C" v-model="model.target"/>
					</div>
				</div>
				<div
					style="position: relative; margin-top: 8px; padding: 10px; border-top: 1px solid #eaeaea; background: white; font-size: 14px">
					<div>
						选择训练开始日期<span id='result' class="ui-alert"
							style="width: 50%; float: right; text-align: right;"></span> 
							<input type="hidden" id="strengthDate" name="strengthDate" v-model="model.strengthDate"/>
							<a id='demo4' data-options='{"type":"date"}'
							class="btn mui-btn mui-btn-block"
							style="position: absolute; width: 50%; padding: 0; top: .5em; right: 0; opacity: 0;">选择日期
						</a>
					</div>
				</div>
			</div>
			<!--2-->
			<div class="w33_next">
				<div class="raioBox1" style="">
					<div class="sex">性别</div>
					<div class="sex1">
						<div class="sex2 ">
							<a class="st1 sta checkedSex chickedit">男 
								<input type="radio" name="st" id="st1" value="M" checked="checked" v-model="model.st" style="display:none;"/>
							</a> 
							<a class="st2 sta checkedSex">女 
								<input type="radio" name="st" id="st2" value="F" v-model="model.st" style="display:none;"/>
							</a>
						</div>
					</div>
					<div class="fix"></div>
				</div>
				<div class="raioBox1">
					<div class="sgimg">身高</div>
					<div class="sgbox" style="position: relative;">
						<input type="number" name="height" id="sg" value=""
							placeholder="输入身高" class="srinput" v-model="model.height"/>
					</div>
					<div class="fix" style="font-size:13px;color:#999;line-height:45px;margin-right:10px;">cm</div>
				</div>
				<div class="raioBox1">
					<div class="ywimg">腰围</div>
					<div class="srbox">
						<input type="number" name="waistline" id="yw" value=""
							placeholder="输入腰围" class="srinput" v-model="model.waistline"/>
					</div>
					<div class="fix" style="font-size:13px;color:#999;line-height:45px;margin-right:10px;">cm</div>
				</div>
				<div class="raioBox1" style="">
					<div class="tzimg">体重</div>
					<div class="srbox">
						<input type="number" name="weight" id="tz" value=""
							placeholder="输入体重" class="srinput" v-model="model.weight"/>
					</div>
					<div class="fix" style="font-size:13px;color:#999;line-height:45px;margin-right:10px;">kg</div>
				</div>
				<div class="raioBox1">
					<div class="wtimg">最大卧推重量</div>
					<div class="srbox">
						<input type="number" name="maxwm" id="zl" value=""
							placeholder="输入重量" class="srinput" v-model="model.maxwm"/>
					</div>
					<div class="fix" style="font-size:13px;color:#999;line-height:45px;margin-right:10px;">kg</div>
				</div>
			</div>
			<!--3-->
			<div class="w33_next">
				<div style="" class="form_nextbox">
					<div class="one">
						<div class="two mui-input-row mui-checkbox">
							<img src="expert/img/buy/spinning.png" class="checkboxImg" /> <label
								for="" class="checkboxlabel">动感单车</label> <input type="checkbox"
								name="check" id="" value="动感单车" class="chick_css" />
						</div>
					</div>
					<div class="one">
						<div class="two mui-input-row mui-checkbox">
							<img src="expert/img/buy/basketball.png" class="checkboxImg" /> <label
								for="" class="checkboxlabel">篮球</label> <input type="checkbox"
								name="check" id="" value="篮球" class="chick_css" />
						</div>
					</div>
				</div>
				<div style="" class="form_nextbox">
					<div class="one">
						<div class="two mui-input-row mui-checkbox">
							<img src="expert/img/buy/badminton.png" class="checkboxImg" /> <label
								for="" class="checkboxlabel">羽毛球</label> <input type="checkbox"
								name="check" id="" value="羽毛球" class="chick_css" />
						</div>
					</div>
					<div class="one">
						<div class="two mui-input-row mui-checkbox">
							<img src="expert/img/buy/football.png" class="checkboxImg" /> <label
								for="" class="checkboxlabel">足球</label> <input type="checkbox"
								name="check" id="" value="足球" class="chick_css" />
						</div>
					</div>
				</div>
				<div style="" class="form_nextbox">
					<div class="one">
						<div class="two mui-input-row mui-checkbox">
							<img src="expert/img/buy/run.png" class="checkboxImg" /> <label for=""
								class="checkboxlabel">跑步</label> <input type="checkbox"
								name="check" id="" value="跑步" class="chick_css" />
						</div>
					</div>
					<div class="one">
						<div class="two mui-input-row mui-checkbox">
							<img src="expert/img/buy/volleyball.png" class="checkboxImg" /> <label
								for="" class="checkboxlabel">排球</label> <input type="checkbox"
								name="check" id="" value="排球" class="chick_css" />
						</div>
					</div>
				</div>
				<div style="" class="form_nextbox">
					<div class="one">
						<div class="two mui-input-row mui-checkbox">
							<img src="expert/img/buy/swimming_1.png" class="checkboxImg" /> <label
								for="" class="checkboxlabel">游泳</label> <input type="checkbox"
								name="check" id="" value="游泳" class="chick_css" />
						</div>
					</div>
					<div class="one">
						<div class="two mui-input-row mui-checkbox">
							<img src="expert/img/buy/Setting-up-exercise.png" class="checkboxImg" />
							<label for="" class="checkboxlabel">健身操</label> <input
								type="checkbox" name="check" id="健身操" value="" class="chick_css" />
						</div>
					</div>
				</div>
				<div style="" class="form_nextbox">
					<div class="one">
						<div class="two mui-input-row mui-checkbox">
							<img src="expert/img/buy/swimming.png" class="checkboxImg" /> <label
								for="" class="checkboxlabel">跳绳</label> <input type="checkbox"
								name="check" id="" value="跳绳" class="chick_css"/>
						</div>
					</div>
					<div class="one">
						<div class="two mui-input-row mui-checkbox">
							<img src="expert/img/buy/dancing.png" class="checkboxImg" /> <label
								for="" class="checkboxlabel">舞蹈</label> <input type="checkbox"
								name="check" id="" value="舞蹈" class="chick_css" />
						</div>
					</div>
				</div>
				<div style="" class="form_nextbox">
					<div class="one">
						<div class="two mui-input-row mui-checkbox">
							<img src="expert/img/buy/tennis.png" class="checkboxImg" /> <label
								for="" class="checkboxlabel">网球</label> <input type="checkbox"
								name="check" id="" value="网球" class="chick_css"/>
						</div>
					</div>
					<div class="one">
						<div class="two mui-input-row mui-checkbox">
							<img src="expert/img/buy/Mountain-climbing.png" class="checkboxImg" />
							<label for="" class="checkboxlabel">登山</label> <input
								type="checkbox" name="check" id="" value="登山" class="chick_css"/>
						</div>
					</div>
				</div>
				<div style="" class="form_nextbox">
					<div class="one">
						<div class="two mui-input-row mui-checkbox">
							<img src="expert/img/buy/Table-tennis.png" class="checkboxImg" /> <label
								for="" class="checkboxlabel">乒乓球</label> <input type="checkbox"
								name="check" id="" value="乒乓球" class="chick_css" />
						</div>
					</div>
					<div class="one">
						<div class="two mui-input-row mui-checkbox">
							<img src="expert/img/buy/boxing.png" class="checkboxImg" /> <label
								for="" class="checkboxlabel">搏击</label> <input type="checkbox"
								name="check" id="" value="搏击" class="chick_css"/>
						</div>
					</div>
				</div>
			</div>
		</div>
		<footer style="width:100%;height:44px;position:absolute;bottom:0;"
			style="border:none;background: white;padding: 0;"> <input
			type="button" class="next"
			style="background: #ff4401; color: white; width: 100%; height: 100%; box-sizing: border-box; border: none; border-radius: 0; display: block; float: left;"
			value="下一步"> 
		</footer>
	</form>
</body>
<script src="expert/js/vue.min.js"></script>
<script src="expert/js/jquery.min.js"></script>
<script src="expert/js/mui.min.js"></script>
<script src="expert/js/mui.picker.min.js"></script>
<script src="https://res.wx.qq.com/open/js/jweixin-1.3.2.js"></script>
<script>
	var vue = new Vue({
		el:"#memberSetting",
		data:{
			model:{
				target:"A",
				st:"M",
				strengthDate:"",
				favoriateCardio:""
			}
		},
		created:function(){
			document.getElementById("radio").checked = true;
		}
	});

	$(function(){
		setTimeout(function(){
			$("#strengthDate").val($("#result").html());
		},1000);
		$("#result").change(function(){
			if($(this).html() != ""){
				$("#strengthDate").val($("#result").html());
			}
		});
	});
	
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
		$(".ui-alert").html(y + "-" + M + "-" + d);
		vue.model.strengthDate = (y + "-" + M + "-" + d);
		//setTimeout("time()", 1000)
	}
	time();

	function checkedTest() {
		var count = 0;
		var checkArry = document.getElementsByName("check");
		for (var i = 0; i < checkArry.length; i++) {
			if (checkArry[i].checked == true) {
				//选中的操作
				count++;
			}
		}
		if (count == 0) {
			alert("请选择！")
		}else{
			var fav = "";
			$(".chick_css:checked").each(function(){
				fav += $(this).val() + ",";
			});
			vue.model.favoriateCardio = fav.substring(0,fav.length-1); 
			// 请求服务端,保存用户身体数据
			var memberId = location.search.split("=")[1];
			$.ajax({
				url: "expertex!saveMemberSetting.asp",
				type: "post",
				data: {
					jsons: encodeURI(JSON.stringify(vue.model)),
					id: 1,
					memberId: memberId
				},
				dataType: "json",
				success: function(res){
				  wx.miniProgram.navigateTo({url: '../order/order?strengthDate='+vue.model.strengthDate});
				}
			});
		}
	}

	$(document).ready(
			function() {

				var index = 0;
				$(".w33 span").removeClass("redspan").eq(index).addClass(
						"redspan");
				$(".w33_next").css("display", "none").eq(index).css("display",
						"block");
				$(".next").click(
						function() {
							index++;
							var sg = $('#sg').val();
							var tz = $("#tz").val();
							var yw = $("#yw").val();
							var zl = $("#zl").val();

							//				第二页判断值是否为空，是否是数字，不是让index还是1
							if (!isNaN(sg) && sg != "" && !isNaN(zl)
									&& zl != "" && !isNaN(yw) && yw != ""
									&& !isNaN(tz) && tz != "") {
								index = index;
							} else {
								index = 1
							}
							if (index > 2) {
								checkedTest();
								index = 2;
							}

							$(".w33_next").css("display", "none").eq(index)
									.css("display", "block");
							$(".w33 span").removeClass("redspan").eq(index)
									.addClass("redspan");

						});
			})
			
		
			$(function(){
				$(".st1").click(function(){
					$(".st2").removeClass('chickedit');
					$(".st1").addClass('chickedit');
					vue.model.st = "M";
				});
				
				$(".st2").click(function(){
					$(".st1").removeClass('chickedit');
					$(".st2").addClass('chickedit');
					vue.model.st = "M";
				});
				
				var h = document.body.scrollHeight;
				window.onresize = function(){
					if (document.body.scrollHeight < h) {
						document.getElementsByTagName("footer")[0].style.display = "none";
					}else{
						document.getElementsByTagName("footer")[0].style.display = "block";
					}
				}
			});
			
	
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
					type : "date", //设置日历初始视图模式
					//真正的月份比写的多一个月。  type的类型你还是可以选择date, datetime month time  hour 

					beginDate : new Date(), //设置开始日期   

					endDate : new Date(y, M, d)
				//设置结束日期    //真正的是10.21

				});
				picker.show(function(e) {
					var datetext = e.y.text + "-" + e.m.text + "-" + e.d.text;

					result.innerText = datetext;
					vue.model.strengthDate = datetext;

					/*
					 * rs.value 拼合后的 value
					 * rs.text 拼合后的 text
					 * rs.y 年，可以通过 rs.y.vaue 和 rs.y.text 获取值和文本
					 * rs.m 月，用法同年
					 * rs.d 日，用法同年
					 * rs.h 时，用法同年
					 * rs.i 分（minutes 的第二个字母），用法同年
					 */
					//result.innerText = rs.text;
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