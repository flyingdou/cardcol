<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>	
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8">
<title>发起挑战首页</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no, minimal-ui" />
<link href="css/styles.css" rel="stylesheet" type="text/css">
<link type="text/css" href="css/bootstrap.css" rel="stylesheet">
<link type="text/css" href="css/base.css" rel="stylesheet">
<link type="text/css" href="css/style.css" rel="stylesheet">
<link type="text/css" href="css/index.css" rel="stylesheet">
</head>
<body>
<form action="">
<div class="stage_challenge container">
 		<div class="challenge_name">
			<div class="list">
				<span>挑战名称</span> <input name="name" type="text" value="" placeholder="请输入挑战名称"
					class="fr" id="cname">
			</div>
			<div class="list">
				<span>完成时间</span>
				<input name="days" type="text" value="" placeholder="请输入完成时间" class="fr" id="ctime">天
			</div>
			<div class="list">
				<a href="JavaScript:tzmb();"><span>挑战目标</span>
					<i class="fr">
					<input type="hidden" name="target" value=""/>
					<input type="text" value=""/>
					<img src="images/right_icon_03.png" ></i>
				</a>
			</div>
		</div>
		<div class="challenge_message">
			<h3>裁判员</h3>
			<label class="label_three"><span class="first_span"></span>发起人为裁判<input
				type="radio" value="<s:property value="member.id"/>" name="checked" checked class="check_three"></label>
			<label class="label_three"  id="agentCp"><span></span>报名者指定裁判<input type="radio" value="" name="checked" class="check_three"></label>
		</div>
		<div class="challenge_message">
			<h3>缩略图</h3>
			<label class="fr">
				<div class="camera">
				 <a href="javascript:void(0);" class="logoBox" id="logoBox">
   						 <img id="bgl" src="images/camera_03.png" >
				</a> 
				</div>
			</label>
		</div>
		<div class="challenge_message TheLast">
			<h3>注意事项：</h3>
			<div align="center">
			<textarea name="memo" rows="" cols="" id="zysx"></textarea>
			</div>
			<p>请将运动成绩记录到训练日志中，成绩将自定发送给裁判评判真实性，如挑战成功，保证金将全额退还。</p>
		</div>
		<div class="assign">
			<a href="JavaScript:goon();">继续</a>
		</div>
	</div>
    <div class="assign">
        <a href="success_lose.jsp">继续</a>
    </div>
</form>        
<div class="htmleaf-container">
<div id="clipArea"></div>
<div id="view"></div>
</div>
<div id="dpage">
 <a href="javascript:void(0);">
     <input type="button" name="file" class="button" value="上传照片">
	   <input id="file" type="file" onchange="javascript:setImagePreview();" accept="image/*" multiple  />
      </a>
 <a href="javascript:void(0);" class="qx"><button id="clipBtn">确定</button></a>
</div> 

<script src="js/jquery.min.js" type="text/javascript"></script>
<script src="js/iscroll-zoom.js"></script>
<script src="js/hammer.js"></script>
<script src="js/jquery.photoClip.js"></script>
<script>
var obUrl = ''
$("#clipArea").photoClip({
	width: 300,
	height: 225,
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
		alert(dataURL);
	}
});
</script>
<script>
$(function(){
$("#logoBox").click(function(){
$(".htmleaf-container").fadeIn(300);
$("#dpage").addClass("show");
})
	$("#clipBtn").click(function(){
		$("#logoBox").empty();
		$('#logoBox').append('<img src="' + imgsource + '" align="absmiddle" style=" width:100%;">');
		$(".htmleaf-container").hide();
		$("#dpage").removeClass("show");
	})
});
</script>
<script type="text/javascript">
$(function(){
	jQuery.divselect = function(divselectid,inputselectid) {
		var inputselect = $(inputselectid);
		$(divselectid+" small").click(function(){
			$("#divselect ul").toggle();
			$(".mask").show();
		});
		$(divselectid+" ul li a").click(function(){
			var txt = $(this).text();
			$(divselectid+" small").html(txt);
			var value = $(this).attr("selectid");
			inputselect.val(value);
			$(divselectid+" ul").hide();
			$(".mask").hide();
			$("#divselect small").css("color","#333")
		});
	};
	$.divselect("#divselect","#inputselect");
});
</script>
<script type="text/javascript">
$(function(){
	jQuery.divselectx = function(divselectxid,inputselectxid) {
		var inputselectx = $(inputselectxid);
		$(divselectxid+" small").click(function(){
			$("#divselectx ul").toggle();
			$(".mask").show();
		});
		$(divselectxid+" ul li a").click(function(){
			var txt = $(this).text();
			$(divselectxid+" small").html(txt);
			var value = $(this).attr("selectidx");
			inputselectx.val(value);
			$(divselectxid+" ul").hide();
			$(".mask").hide();
			$("#divselectx small").css("color","#333")
		});
	};
	$.divselectx("#divselectx","#inputselectx");
});
</script>
<script type="text/javascript">
$(function(){
	jQuery.divselecty = function(divselectyid,inputselectyid) {
		var inputselecty = $(inputselectyid);
		$(divselectyid+" small").click(function(){
			$("#divselecty ul").toggle();
			$(".mask").show();
		});
		$(divselectyid+" ul li a").click(function(){
			var txt = $(this).text();
			$(divselectyid+" small").html(txt);
			var value = $(this).attr("selectyid");
			inputselecty.val(value);
			$(divselectyid+" ul").hide();
			$(".mask").hide();
			$("#divselecty small").css("color","#333")
		});
	};
	$.divselecty("#divselecty","#inputselecty");
});
</script>
<script type="text/javascript">
$(function(){
   $(".mask").click(function(){
	   $(".mask").hide();
	   $(".all").hide();
   })
	$(".right input").blur(function () {
		if ($.trim($(this).val()) == '') {
			$(this).addClass("place").html();
		}
		else {
			$(this).removeClass("place");
		}
	})
});
</script>
<script>
$("#file0").change(function(){
	var objUrl = getObjectURL(this.files[0]) ;
	 obUrl = objUrl;
	console.log("objUrl = "+objUrl) ;
	if (objUrl) {
		$("#img0").attr("src", objUrl).show();
	}
	else{
		$("#img0").hide();
	}
}) ;
function qd(){
   var objUrl = getObjectURL(this.files[0]) ;
   obUrl = objUrl;
   console.log("objUrl = "+objUrl) ;
   if (objUrl) {
	   $("#img0").attr("src", objUrl).show();
   }
   else{
	   $("#img0").hide();
   }
}
function getObjectURL(file) {
	var url = null ;
	if (window.createObjectURL!=undefined) { // basic
		url = window.createObjectURL(file) ;
	} else if (window.URL!=undefined) { // mozilla(firefox)
		url = window.URL.createObjectURL(file) ;
	} else if (window.webkitURL!=undefined) { // webkit or chrome
		url = window.webkitURL.createObjectURL(file) ;
	}
	return url ;
}
</script>
<script type="text/javascript">
var subUrl = "";
$(function (){
	$(".file-3").bind('change',function(){
		subUrl = $(this).val()
		$(".yulan").show();
		$(".file-3").val("");
	});

	$(".file-3").each(function(){
		if($(this).val()==""){
			$(this).parents(".uploader").find(".filename").val("营业执照");
		}
	});
$(".btn-3").click(function(){
$("#img-1").attr("src", obUrl);
$(".yulan").hide();
$(".file-3").parents(".uploader").find(".filename").val(subUrl);
})
	$(".btn-2").click(function(){
		$(".yulan").hide();
	})

});
</script>
<script type="text/javascript">
function setImagePreview() {
	var preview, img_txt, localImag, file_head = document.getElementById("file_head"),
			picture = file_head.value;
	if (!picture.match(/.jpg|.gif|.png|.bmp/i)) return alert("您上传的图片格式不正确，请重新选择！"),
			!1;
	if (preview = document.getElementById("preview"), file_head.files && file_head.files[0]) preview.style.display = "block",
			preview.style.width = "200px",
			preview.style.height = "150px",
			preview.src = window.navigator.userAgent.indexOf("Chrome") >= 1 || window.navigator.userAgent.indexOf("Safari") >= 1 ? window.webkitURL.createObjectURL(file_head.files[0]) : window.URL.createObjectURL(file_head.files[0]);
	else {
		file_head.select(),
				file_head.blur(),
				img_txt = document.selection.createRange().text,
				localImag = document.getElementById("localImag"),
				localImag.style.width = "100px",
				localImag.style.height = "100px";
		try {
			localImag.style.filter = "progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale)",
					localImag.filters.item("DXImageTransform.Microsoft.AlphaImageLoader").src = img_txt
		} catch(f) {
			return alert("您上传的图片格式不正确，请重新选择！"),
					!1
		}
		preview.style.display = "none",
				document.selection.empty()
	}
	return document.getElementById("DivUp").style.display = "block",
			!0
}
</script>
</body>
</html>
