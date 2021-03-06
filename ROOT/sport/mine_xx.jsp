<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    
<%  
String path = request.getContextPath();  
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";  
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

	<head>
		<meta >
		<title>个人信息</title>
		<base href="<%=basePath %>" >
		<link href="sport/css/style.css" rel="stylesheet" type="text/css">
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
		<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no, minimal-ui" />
		<meta name="apple-mobile-web-app-capable" content="yes" />
		<meta name="format-detection" content="telephone=no, email=no" />
		<link rel="stylesheet" type="text/css" href="sport/css/mui.min.css" />
		<style>
			.logo-license {
				padding-top: 10px;
				padding-bottom: 10px;
				margin: 0;
			}
			
			.info {
				margin-top: 10px;
			}
			
			<style type="text/css">.mui-navigate-right:after,
			.mui-push-right:after {
				right: 10px;
				content: '\e583';
			}
			
			.mui-pull-right {
				margin-right: 10px;
				color: #999999;
				font-size: 15px;
			}
			
			.input1 {
				line-height: 21px!important;
				;
				margin: 0!important;
				padding: 0!important;
				height: 21px!important;
				text-align: right;
				border: none!important
			}
			
			select {
				font-size: 14px;
				height: 21px;
				margin-top: 1px;
				border: 0!important;
				background-color: #fff;
				padding: 0px;
				margin: 0;
			}
			
			.f16 {
				font-size: 18px!important;
			}
			
			.button {
				width: 100%;
				height: 100%;
				line-height: none;
			}
			
			#clipBtn {
				background: #ff4401;
				;
			}
			
			.mui-table-view-cell>a:not(.mui-btn) {
				font-size: 16px;
			}
			
			#file {
				float: left;
				height: 100%;
				width: 100%;
			}
			
			.clipBtn {
				width: 50%;
			}
			input[type='date']::-webkit-inner-spin-button {
				display: none;
			}
			input[type='date']{
				position:relative;
			text-align: right!important;
			width: 115px;float: right;
			
			
			}
			input[type='date']::-webkit-calendar-picker-indicator {
				position:absolute;
				width: 100%;
				left: 0;
				top:0;
				height: 100%;
				opacity: 0;
			}
			input[type='date']::-webkit-clear-button {
				display: none;
			}
			input[type='date']::-webkit-datetime-edit-fields-wrapper{
				float: right!important;
			 
			    display: block;
			    
				text-align: right!important;
			}

		</style>

	</head>

	<body>
<div id = "app">
<form :action="'smemberwx!saveMe.asp?jsons='+jsons" method="post" enctype="multipart/form-data">
		<section class="logo-license" style="background: white;">
			<div class="half" style="width: 100%;padding: 0;margin: 0;">
				<a class="logo" id="logox" style="margin-left:0;margin-right: 10px;width: 45px;height: 45px;display: block;overflow: hidden;float: right;">
					<img id="bgl" src="sport/images/logo_n.png" style="margin: 0;padding: 0;width: 44px;height: 44px;">
				</a>
				<div style="float: left;font-size: 16px;line-height: 44px;margin-left: 15px;">头像</div>
			</div>
			 <div class="half">
				<div class="yulan">
					<img src="sport/${member.image}" id="img0">
					<div class="enter">
						<button class="btn-2 fl">取消</button>
						<button class="btn-3 fr">确定</button>
					</div>
				</div>
			</div> 
			<div class="clear"></div>
		</section>
		<article class="htmleaf-container">
			<div id="clipArea"></div>
			<div class="foot-use">
				<div class="uploader1 blue">
					<input type="button" name="file" class="button" value="打开" /> 
					<input id="file" name = "image" type="file" onchange="javascript:setImagePreview();" accept="image/*" multiple />
				</div>
				<button id="clipBtn">截取</button>
			</div>
			<div id="view"></div>
		</article>
		<div class="mui-content">
			<ul class="mui-table-view">

				<li class="mui-table-view-cell">
					<a class="">
						昵称 <span class="mui-pull-right"><input type="text"class="input1" name="" id="nick" value="${member.nick}" /></span>
						<input type="hidden"  id="planId" value="${member.planId}" />
						<input type="hidden"  id="setId" value="${member.setId}" />
					</a>
				</li>
				<li class="mui-table-view-cell">
					<a href="sport/saveMobile.jsp">
						手机 <span class="mui-pull-right"><input type="number" class="input1" name="" id="mobilephone"  value="${member.mobilephone}" disabled="disabled" /></span>
					</a>
				</li>

			</ul>
			<ul class="mui-table-view" style="margin-top: 10px;">
				<li class="mui-table-view-cell">
					<a class="">
						性别 <span class="mui-pull-right" style="margin-right: 10px;">
			                 	<select name="sex" style="width: 20px;" id = "sex">
			                 	<option value="M" :selected="data0">男</option>
			                 	<option value="F" :selected="data1">女</option>
			                 </select></span>
					</a>
				</li>
				<li class="mui-table-view-cell">
					<a class="">
						出生年月 <span class="mui-pull-right" style="width: 50%;"><input type="date" class="input1"name="" id="birthday" value="${member.birthday}" /></span>
					</a>
				</li>
				<li class="mui-table-view-cell">
					<a class="">
						身高 <span class="mui-pull-right"><input type="text" class="input1" name="" id="height" value="${member.height}" placeholder="cm"/></span>
					</a>
				</li>
				<li class="mui-table-view-cell">
					<a class="">
						静心率 <span class="mui-pull-right"><input type="text" class="input1" name="" id="heartRate" value="${member.heartRate}" placeholder="次/分" /></span>
					</a>
				</li>
				<li class="mui-table-view-cell">
					<a class="">
						靶心率阈值 <span class="mui-pull-right" style="width: 50%;"><input class="input1" type="text" name="" id="bmi" value="${member.bmiLow}%-${member.bmiHigh}%" /></span>
					</a>
				</li>
			</ul>

		</div>

		<article class="btn-1">
			<input type="submit" value = "确认提交" />
		</article>

		
		</form>
		</div>
		
		<script src="sport/js/jquery.min.js" type="text/javascript"></script>
		<script type="text/javascript">
		
		</script>
		
		<script>
			window.jQuery || document.write('<script src="sport/js/jquery-2.1.1.min.js"><\/script>')
		</script>
		<script src="sport/js/iscroll-zoom.js"></script>
		<script src="sport/js/hammer.js"></script>
		<script src="sport/js/jquery.photoClip.js"></script>
		<script src="sport/js/mui.min.js" type="text/javascript"></script>
		<script src="sport/js/vue.min.js" type="text/javascript"></script>
		<script>
			var obUrl = ''
			$("#clipArea").photoClip({
				width: 200,
				height: 200,
				file: "#file",
				view: "#view",
				ok: "#clipBtn",
				loadStart: function() {
					console.log("照片读取中");
				},
				loadComplete: function() {
					console.log("照片读取完成");
				},
				clipFinish: function(dataURL) {
					console.log(dataURL);
				}
			});
		</script>
		<script>
			$(function() {
				$("#logox").click(function() {
					$(".htmleaf-container").show();
				})
				$("#clipBtn").click(function() {
					$("#logox").empty();
					$('#logox').append('<img src="' + imgsource + '" align="absmiddle" style=" width:44px;height: 44px;margin:0">');
					$(".htmleaf-container").hide();
				})
			});
		</script>
		<script type="text/javascript">
		var sex = ${member == null ? 0 : member};
		var sel0 = sex.sex=='M'?'selected':'';
		var sel1 = sex.sex=='F'?'selected':'';
		
		
		var nick = $("#nick").val() != ""?$("#nick").val():"0";
    	
    	var planId = $("#planId").val() != ""?$("#planId").val():"0";
    	
    	var setId = $("#setId").val() != ""?$("#setId").val():"0";
    	
    	var mobilephone = $("#mobilephone").val() != ""?$("#mobilephone").val():"0";
    	
    	var sex = $("#sex").val() != "" ? $("#sex").val() : "M";
    	
    	var birthday = $("#birthday").val() != ""?$("#birthday").val():"0";
    	
    	var height = $("#height").val() != ""?$("#height").val():"0";
    	
    	var heartRate = $("#heartRate").val() != ""?$("#heartRate").val():"0";
    	
    	var bmi = $("#bmi").val() != ""?$("#bmi").val():"0";
    	
    	
    	
    	var jsons = '[{"nick":'+nick+',"mobilephone":"'+mobilephone+'","sex":'+sex+',"birthday":"'+birthday+'","height":'+height+',"heartRate":'+heartRate+',"bmi":"'+bmi+'","planId":'+planId+',"setId":'+setId+'}]';
		
		
		new Vue({
			el:"#app",
			data:{
				member:sex,
				data0:sel0,
				data1:sel1,
				jsons:jsons
			}
		});
		
		
		
		
			$(function() {
				jQuery.divselectx = function(divselectxid, inputselectxid) {
					var inputselectx = $(inputselectxid);
					$(divselectxid + " small").click(function() {
						$("#divselectx ul").toggle();
						$(".mask").show();
					});
					$(divselectxid + " ul li a").click(function() {
						var txt = $(this).text();
						$(divselectxid + " small").html(txt);
						var value = $(this).attr("selectidx");
						inputselectx.val(value);
						$(divselectxid + " ul").hide();
						$(".mask").hide();
						$("#divselectx small").css("color", "#333")
					});
				};
				$.divselectx("#divselectx", "#inputselectx");
			});
		</script>
		<script type="text/javascript">
			function setImagePreview() {
				var preview, img_txt, localImag, file_head = document.getElementById("file"),
				    picture = file_head.value;
				if(!picture.match(/.jpg|.gif|.png|.bmp/i)){
					return alert("您上传的图片格式不正确，请重新选择！"), !1;
				}else{
					$(".photo-clip-mask").css("background-image","url("+picture+")");
				}
				if(preview = document.getElementById("preview"), file_head.files && file_head.files[0]){
					preview.style.display = "block",
					preview.style.width = "44px",
					preview.style.height = "44px",
					preview.src = window.navigator.userAgent.indexOf("Chrome") >= 1 || window.navigator.userAgent.indexOf("Safari") >= 1 ? window.webkitURL.createObjectURL(file_head.files[0]) : window.URL.createObjectURL(file_head.files[0]);
				}
				else {
					file_head.select(),
						file_head.blur(),
						img_txt = document.selection.createRange().text,
						localImag = document.getElementById("localImag"),
						localImag.style.width = "44px",
						localImag.style.height = "44px";
					try {
						localImag.style.filter = "progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale)",
							localImag.filters.item("DXImageTransform.Microsoft.AlphaImageLoader").src = img_txt
					} catch(f) {
						return alert("您上传的图片格式不正确，请重新选择！"), !1
					}
					preview.style.display = "none",
						document.selection.empty()
				}
				return document.getElementById("clipBtn").style.display = "block", !0
			}
		</script>
		
	</body>

</html>