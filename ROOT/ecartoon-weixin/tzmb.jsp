<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="UTF-8">
<title></title>
<meta name="viewport"
	content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
<link href="ecartoon-weixin/css/mui.min.css" rel="stylesheet" />
<style type="text/css">
.inputcss {
	height: 21px !important;
	margin: 0 !important;
	padding: 0 !important;
	line-height: 21px !important;
	border: none;
	text-align: right;
	border: none !important;
	color: #999;
}

input:-webkit-input-placeholder {
	color: #999;
	font-size: 13px !important;
}

.footer {
	height: 44px;
	text-align: center;
	line-height: 44px;
	position: fixed;
	padding: 0 10px;
	bottom: 10px;
	width: 100%;
}

input[type="submit"] {
	width: 100%;
	height: 100%;
	background: #FF4401;
	border: none;
}

.radio_img {
	width: 16px;
	height: 16px;
	margin-right: 5px;
	border-radius: 50%;
	border: 1px solid #999;
}
</style>
</head>

<body>
	<form action="ecoursewx!faqitiaozhan.asp?id=2" method="post">
	  <div class="mui-content">
		<ul class="mui-table-view">
			<li class="mui-table-view-cell"><a class=""> 请输入挑战的天数 <span
					class="mui-pull-right"><input type="text" name="days" placeholder="天"
						class="inputcss" /></span>
			</a></li>
		</ul>
		<ul class="mui-table-view">
			<li class="mui-table-view-cell"><a
				style="font-size: 14px; color: #999"> 请选择挑战目标 </a></li>
			<li class="mui-table-view-cell radio_im"><a class=""> <span
					class="mui-pull-left radio_img"></span> 健身次数<span
					class="mui-pull-right"><input type="number"
						placeholder="输入次数" class="inputcss" /></span>
			</a></li>
			<li class="mui-table-view-cell radio_im"><a class=""> <span
					class="mui-pull-left radio_img"></span> 体重减少<span
					class="mui-pull-right"><input type="number"
						placeholder="输入减少重量" class="inputcss" /></span>
			</a></li>
			<li class="mui-table-view-cell radio_im"><a class=""> <span
					class="mui-pull-left radio_img"></span> 体重增加<span
					class="mui-pull-right"><input type="number"
						placeholder="输入体重增加" class="inputcss" /></span>
			</a></li>
		</ul>
	  </div>
	<div class="footer">
		<input type="submit" id="" name="" value="确定" />
	</div>
	</form>
	<script src="ecartoon-weixin/js/mui.min.js"></script>
	<script src="ecartoon-weixin/js/jquery.min.js" type="text/javascript" charset="utf-8"></script>
	<script type="text/javascript">
		mui.init();
		$(".radio_im").click(function() {
			$(".radio_img").css({
				'width' : "16px",
				"height" : "16px",
				"border-radius" : "50%",
				"border" : "1px solid #999"
			})
			$(".radio_img").eq($(this).index() - 1).css({
				'width' : "16px",
				"height" : "16px",
				"border-radius" : "50%",
				"border" : "4px solid red"
			})
		})
	</script>
</body>
</html>