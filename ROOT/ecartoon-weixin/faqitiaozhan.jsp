<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8">
<title>创建应用</title>
<link href="css/style.css" rel="stylesheet" type="text/css">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no, minimal-ui" />
<meta name="apple-mobile-web-app-capable" content="yes" />
<meta name="format-detection" content="telephone=no, email=no" />
<link rel="stylesheet" type="text/css"
	href="ecartoon-weixin/css/mui.min.css" />
<script src="ecartoon-weixin/js/vue.min.js" type="text/javascript"></script>
<!--[if IE]>
    <script src="js/html5shiv.min.js"></script>
    <![endif]-->
<style>
.logo-license {
	padding-top: 10px;
	padding-bottom: 10px;
	margin: 0;
}

.info {
	margin-top: 10px;
}

.mui-navigate-right:after, .mui-push-right:after {
	right: 10px;
	content: '\e583';
}

.mui-pull-right {
	margin-right: 10px;
	color: #999999;
	font-size: 15px;
}

.input1 {
	line-height: 21px !important;;
	margin: 0 !important;
	padding: 0 !important;
	height: 21px !important;
	text-align: right;
	border: none !important
}

select {
	font-size: 14px;
	height: 21px;
	margin-top: 1px;
	border: 0 !important;
	background-color: #fff;
	padding: 0px;
	margin: 0;
}

.f16 {
	font-size: 18px !important;
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

.mui-table-view-cell
>
a
:not
 
(
.mui-btn
 
)
{
font-size
:
 
16
px
;


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

input[type='date'] {
	position: relative;
	text-align: right !important;
	width: 115px;
	float: right;
}

input[type='date']::-webkit-calendar-picker-indicator {
	position: absolute;
	width: 100%;
	left: 0;
	top: 0;
	height: 100%;
	opacity: 0;
}

input[type='date']::-webkit-clear-button {
	display: none;
}

input[type='date']::-webkit-datetime-edit-fields-wrapper {
	float: right !important;
	display: block;
	text-align: right !important;
}
</style>
</style>
</head>

<body>
  <form id="form1" action="ecoursewx!activeSave.asp" method="post">
	<section class="logo-license" style="background: white;">
	<div class="half" style="width: 100%; padding: 0; margin: 0;">
		<a class="logo" id="logox"
			style="margin-left: 0; margin-right: 10px; width: 45px; height: 45px; display: block; overflow: hidden; float: right;">
			<img id="bgl" src="images/logo_n.png"
			style="margin: 0; padding: 0; width: 44px; height: 44px;">
		</a>
		<div
			style="float: left; font-size: 16px; line-height: 44px; margin-left: 15px;">挑战海报</div>
	</div>
	<div class="half">
		<div class="yulan">
			<img src="" id="img0">
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
			<input type="button" name="image" class="button" value="打开" /> <input
				id="file" type="file" onchange="javascript:setImagePreview();"
				accept="image/*" multiple />
		</div>
		<button id="clipBtn">截取</button>
	</div>
	<div id="view"></div>
	</article>
	<div class="mui-content" id="app">
		<ul class="mui-table-view">

			<li class="mui-table-view-cell"><a class=""> 挑战名称 <span
					class="mui-pull-right"><input type="text" class="input1"
						name="activeName" id="activeName"
						:value="data1.name==null?'':data1.name" placeholder="输入挑战名称" /></span>
			</a></li>
			<li class="mui-table-view-cell"><a class="mui-navigate-right" href="javascript:to()" id="aaa" > 
				挑战目标
				<script type="text/javascript">
					function to(){
						location.href="ecoursewx!faqitiaozhan.asp?id=1&type=1&name="+$("#activeName").val();
					}
				</script>
			 </a>
			</li>
			<li class="mui-table-view-cell"><a class="mui-navigate-right"
				href="ecoursewx!faqitiaozhan.asp?id=1&type=2"> 成功奖励 </a></li>
			<li class="mui-table-view-cell"><a class="mui-navigate-right"
				href="ecoursewx!faqitiaozhan.asp?id=1&type=3"> 失败惩罚 </a></li>
			<li class="mui-table-view-cell"><a> 注意事项 </a>
			<textarea name="memo" id="" cols="30" rows=""
					style="margin-top: 10px; border: none; padding: 0; text-indent: 0;"
					placeholder="请输入挑战的注意事项"></textarea></li>
		</ul>
	</div>
	<article class="btn-1">
	<button
		style="background: #FF4401; height: 50px; line-height: 40px !important; font-size: 16px;"
		onclick="$('#form1').submit()"
		>
		确认提交
	</button>
	</article>
	</form>
	<script src="ecartoon-weixin/js/jquery.min.js" type="text/javascript"></script>
	<script>
		window.jQuery
				|| document
						.write('<script src="js/jquery-2.1.1.min.js"><\/script>')
	</script>
	<script src="ecartoon-weixin/js/iscroll-zoom.js"></script>
	<script src="ecartoon-weixin/js/hammer.js"></script>
	<script src="ecartoon-weixin/js/jquery.photoClip.js"></script>
	<script src="ecartoon-weixin/js/mui.min.js" type="text/javascript">
	</script>
	<script>
		var obUrl = ''
		//document.addEventListener('touchmove', function (e) { e.preventDefault(); }, false);
		$("#clipArea").photoClip({
			width : 200,
			height : 200,
			file : "#file",
			view : "#view",
			ok : "#clipBtn",
			loadStart : function() {
				console.log("照片读取中");
			},
			loadComplete : function() {
				console.log("照片读取完成");
			},
			clipFinish : function(dataURL) {
				console.log(dataURL);
			}
		});
	</script>
	<script>
		$(function() {
			$("#logox").click(function() {
				$(".htmleaf-container").show();
			})
			$("#clipBtn")
					.click(
							function() {
								$("#logox").empty();
								$('#logox')
										.append(
												'<img src="' + imgsource + '" align="absmiddle" style=" width:44px;height: 44px;margin:0">');
								$(".htmleaf-container").hide();
							})
		});
	</script>
	<!--<script type="text/javascript">
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
</script>-->
	<script type="text/javascript">
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
	<!--<script type="text/javascript">
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
</script>-->
	<!--<script type="text/javascript">
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
</script>-->
	<!--<script>
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
</script>-->
	<!--<script type="text/javascript">
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
</script>-->
	<script type="text/javascript">
		function setImagePreview() {
			var preview, img_txt, localImag, file_head = document
					.getElementById("file_head"), picture = file_head.value;
			if (!picture.match(/.jpg|.gif|.png|.bmp/i))
				return alert("您上传的图片格式不正确，请重新选择！"), !1;
			if (preview = document.getElementById("preview"), file_head.files
					&& file_head.files[0])
						preview.style.display = "block",
						preview.style.width = "44px",
						preview.style.height = "44px",
						preview.src = window.navigator.userAgent
								.indexOf("Chrome") >= 1
								|| window.navigator.userAgent.indexOf("Safari") >= 1 ? window.webkitURL
								.createObjectURL(file_head.files[0])
								: window.URL
										.createObjectURL(file_head.files[0]);
			else {
				file_head.select(), file_head.blur(),
						img_txt = document.selection.createRange().text,
						localImag = document.getElementById("localImag"),
						localImag.style.width = "44px",
						localImag.style.height = "44px";
				try {
							localImag.style.filter = "progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale)",
							localImag.filters
									.item("DXImageTransform.Microsoft.AlphaImageLoader").src = img_txt
				} catch (f) {
					return alert("您上传的图片格式不正确，请重新选择！"), !1
				}
				preview.style.display = "none", document.selection.empty()
			}
			return document.getElementById("DivUp").style.display = "block", !0
		}
	</script>
	<script type="text/javascript">
		new Vue({
			el : "#app",
			data : {
				data1 : ${Active}
			}
		});
	</script>
</body>
</html>