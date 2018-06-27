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
<style>
.input1 {
	line-height: 21px !important;;
	margin: 0 !important;
	padding: 0 !important;
	height: 21px !important;
	text-align: right;
	border: none !important
}

input[type="radio"] {
	position: relative;
	height: 1px;
}

input[type="radio"]:after {
	position: absolute;
	width: 16px;
	height: 16px;
	content: "";
	border: 1px solid #999;
	border-radius: 50%;
	top: -14px;
	left: -4px;
}

input[type="radio"]:checked::after {
	position: absolute;
	content: "";
	width: 8px;
	height: 8px;
	border: 4px solid #FF4401;
	border-radius: 50%;
	top: -14px;
	left: -4px;
}

.mui-table-view-cell:after {
	position: absolute;
	right: 0;
	bottom: 0;
	left: 15px;
	height: 1px;
	content: '';
	-webkit-transform: scaleY(.5);
	transform: scaleY(.5);
	background-color: rgba(0, 0, 0, 0);
}

.liafter:after {
	position: absolute;
	right: 0;
	bottom: 0;
	left: 15px;
	height: 1px;
	content: '';
	-webkit-transform: scaleY(.5);
	transform: scaleY(.5);
	background-color: #f2f2f2;
}

.liafter1:after {
	position: absolute;
	right: 0;
	bottom: 0;
	left: 35px;
	height: 1px;
	content: '';
	-webkit-transform: scaleY(.5);
	transform: scaleY(.5);
	background-color: #f2f2f2;
}

.cg {
	color: #999 !important;
	font-size: 13px;
	text-indent: 32px;
	height: 35px !important;
	line-height: 14px !important;
}

.footer {
	width: 100%;
	height: 64px;
	overflow: hidden;
	box-sizing: border-box;
	padding: 10px;
	position: fixed;
	bottom: 0;
	left: 0;
}

.footer input {
	width: 100%;
	background: #FF4401;
	height: 100%;
	margin: 0px;
	border: none;
}
</style>
</head>
<body>
	<form action="ecoursewx!faqitiaozhan.asp?id=4" method="post">
		<ul class="mui-table-view">
			<li class="mui-table-view-cell liafter"><a class="">
					挑战失败向以下用户支付<span class="mui-pull-right" style="width: 20%;"><input
						type="text" class="input1" name="money" id="" value="" placeholder="元" /></span>
			</a></li>
		</ul>
		<ul class="mui-table-view" style="margin-top: 10px;">
			<li class="mui-table-view-cell liafter1"><a class=""> <label
					for=""> <span class="mui-pull-left"
						style="margin-right: 12px;"> <img
							src="img/mine/Look-cool.png" width="16px"
							style="border-radius: 50%;" /></span> 宋庆龄基金会 <span
						class="mui-pull-right"> <input type="radio" name="radio"
							id="" class="radiocss" value="宋庆龄基金会" />
					</span>
				</label>
			</a></li>
			<li class="mui-table-view-cell"><a class="mui-navigate-right cg">
					基金会简介 </a></li>
		</ul>
		<ul class="mui-table-view" style="margin-top: 10px;">
			<li class="mui-table-view-cell liafter1"><a class=""> <label
					for=""> <span class="mui-pull-left"
						style="margin-right: 12px;"><img
							src="img/mine/Look-cool.png" width="16px"
							style="border-radius: 50%;" /></span>中国福利基金会 <span
						class="mui-pull-right"> <input type="radio" name="radio"
							id="" class="radiocss" value="中国福利基金会" />
					</span>
				</label>
			</a></li>
			<li class="mui-table-view-cell"><a class="mui-navigate-right cg">
					基金会简介 </a></li>
		</ul>
		<ul class="mui-table-view" style="margin-top: 10px;">
			<li class="mui-table-view-cell liafter1"><a class=""> <label
					for=""> <span class="mui-pull-left"
						style="margin-right: 12px;"><img
							src="imgup/examples/crop-avatar/img/picture.jpg" width="16px"
							style="border-radius: 50%;" /></span>活动发起人 <span class="mui-pull-right">
							<input type="radio" name="radio" id="" class="radiocss"
							value="中国福利基金会" />
					</span>
				</label>
			</a></li>
		</ul>
		<div class="footer">
			<input type="submit" value="确认" />
		</div>
	</form>
	<script src="ecartoon-weixin/js/mui.min.js"></script>
	<script type="text/javascript">
		mui.init()
	</script>
</body>
</html>