<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<title>挑战目标</title>
<link type="text/css" href="${pageContext.request.contextPath}/WX/css/bootstrap.css" rel="stylesheet">
<link type="text/css" href="${pageContext.request.contextPath}/WX/css/base.css" rel="stylesheet">
<link type="text/css" href="${pageContext.request.contextPath}/WX/css/style.css" rel="stylesheet">
<link type="text/css" href="${pageContext.request.contextPath}/WX/css/index.css" rel="stylesheet">
<script type="text/javascript" src="${pageContext.request.contextPath}/WX/js/jquery-2.2.0.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/WX/js/style.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/WX/js/index.js"></script>
</head>
<body>
	<div class="challengeMb container">
		<div class="chose">
			<div class="challengeMb_list">
				<div class="list_one">
					<label class="first_label label_one"> <input type="radio"
						value="B" name="checked1" class="check_one"></label> <span>体重减少</span>
					<span class="fr">公斤</span> <input type="number" value=""
						class="fr text" id="tzjs" onclick="tzjs()" style="text-align: right;">
				</div>
				<div class="list_one">
					<label class="label_one"> <input type="radio" value="A"
						name="checked1" class="check_one"></label> <span>体重增加</span> <span
						class="fr">公斤</span> <input type="number" value=" "
						class="fr text" id="tzzj" onclick="tzzj()" style="text-align: right;"> 
				</div>
				<div class="list_one">
					<label class="label_one"><input type="radio" value="E"
						name="checked1" class="check_one"></label> <span>健身时间</span> <span
						class="fr">小时</span> <input type="number" value="" class="fr text"
						id="jssj" onclick="jssj()" style="text-align: right;">
				</div>
				<div class="list_one">
					<label class="label_one">
					<input type="radio" value="D" name="checked1" class="check_one" style="text-align: right;">
					</label> 
					<span>健身次数</span> 
					<span class="fr">&nbsp;&nbsp;次</span> 
					<input type="number" value="" class="fr text" id="jscs" onclick="jscs()" style="text-align: right;">
				</div>
				<div class="list_one">
				<div class="custom_click ">
					<label class="label_one">
					<input type="radio" value="G" name="checked1" class="check_one">
					</label> 
					</div>
					<span class="custom_tx	t">自定义</span> 
					<img src="images/right_icon_03.png" class="fr right">
				</div>
			</div>
		</div>
		<div class="custom" style="display: none;">
			<div class="challengeMb_list ">
				<div class="list_one">
					<img src="images/challenge_icon_03.png"> <span>挑战内容</span> 
					<input type="text" value="" placeholder="请输入运动名称" class="fr" id="name" style="text-align: right;">
				</div>
				<div class="list_one">
					<img src="images/challenge_icon_06.png"> <span>挑战目标</span> 
					<input type="number" value="" placeholder="请输入目标数量" class="fr" id="number" style="text-align: right;">
				</div>
				<div class="list_one">
					<img src="images/challenge_icon_09.png"> <span>目测计量单位</span>
					<input type="text" value="" placeholder="分钟、公斤、米..." class="fr" id="unit" style="text-align: right;">
				</div>
			</div>
			<div class="challengeMb_list last">
				<div class="list_one">
					<img src="images/challenge_icon_12.png"> <span>挑战成绩判定方式</span>
				</div>
				<div class="list_one">
					<label class="first_label label_two"><input type="radio"
						value="" name="checked2" checked class="check_two"></label> <span>单次最好成绩</span>
					<div class="range fr">	
						<span></span>
					</div>
				</div>
				<div class="list_one">
					<label class="label_two"><input type="radio" value=""
						name="checked2" class="check_two"></label> <span>累计成绩</span>
					<div class="range fr">
						<span></span>
					</div>
				</div>
				<div class="list_one">
					<label class="label_two"><input type="radio" value=""
						name="checked2" class="check_two"></label> <span>最后一次成绩</span>
					<div class="range fr">
						<span></span>
					</div>
				</div>
				<p style="display: none;">您还没有选择该判定方式</p>
			</div>
		</div>
		<div class="assign">
			<!-- <a href="stage_challenge.html">完成</a> -->
			<a href="JavaScript:accomplish()">完成</a>
		</div>
	</div>
	<script type="text/javascript">
	 function GetQueryString(name) {
			var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
			var r = window.location.search.substr(1).match(reg);
			if (r != null)
				return decodeURI(r[2]);
			return null;
		}
	 //var time = GetQueryString("time");
	 //var name =GetQueryString("name");
	 //alert(time+name);
	 
	
		function accomplish() {
			var type = $(".list_one").find(':radio:checked').val();
			if (type == "B" || type == "") {
				var val = $("#tzjs").val();
				if (val == "") {
					alert("请输入减少体重");
				} else if (type == "") {
					var time =GetQueryString("time");
					var name =GetQueryString("name");
					window.location.href = "stage_challenge.jsp?type=" + "B"
							+ "&val=" + val +"&time="+time+"&name="+name;
				} else {
					window.location.href = "stage_challenge.jsp?type=" + type
							+ "&val=" + val+"&time="+time+"&name="+name;
				}
			} else if (type == "A") {
				var val = $("#jszj").val();
				var time =GetQueryString("time");
				var name =GetQueryString("name");
				if (val == "") {
					alert("请输入增加体重");
				} else {
					window.location.href = "stage_challenge.jsp?type=" + type
							+ "&val=" + val+"&time="+time+"&name="+name;
				}
			} else if (type == "E") {
				var val = $("#jssj").val();
				var time =GetQueryString("time");
				var name =GetQueryString("name");
				if (val == "") {
					alert("请输入健身时间");
				} else {
					window.location.href = "stage_challenge.jsp?type=" + type
							+ "&val=" + val+"&time="+time+"&name="+name;
				}
			} else if (type == "D") {
				var val = $("#jscs").val();
				var time =GetQueryString("time");
				var name =GetQueryString("name");
				if (val == "") {
					alert("请输入健身次数");
				} else {
					window.location.href = "stage_challenge.jsp?type=" + type
							+ "&val=" + val+"&time="+time+"&name="+name;
				}
			}else if(type=="G"){
				var time =GetQueryString("time");
				var name =GetQueryString("name");
				var name = $("#name").val();
				var number = $("#number").val();
				var unit = $("#unit").val();
				if (name == "") {
					alert("请输入运动名称");
				} else if(number==""){
					alert("请输入目标数量");
				}else if(unit==""){
					alert("请输入：分钟、公斤、米...");
				}else{
					window.location.href = "stage_challenge.jsp?type=" + type
					+"&time="+time+"&name="+name;
				}
			}
		}
	</script>
</body>
</html>