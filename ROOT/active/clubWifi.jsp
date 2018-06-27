<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!doctype html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<s:include value="/share/meta.jsp" />
<meta name="keywords" content="健身E卡通-我的账户" />
<meta name="description" content="健身E卡通-我的账户" />
<title>设置WIFI</title>
<link rel="stylesheet" type="text/css" href="css/user-account.css" />
<link rel="stylesheet" type="text/css" href="css/pulicstyle.css" />
<script src="ecartoon-weixin/js/vue.min.js"></script>
</head>
<body>
	<s:include value="/share/header.jsp" />
	<div id="content">
		<s:include value="/basic/nav.jsp" />
		<div id="right-2">
			<div id="container">
			     <h1>设置WIFI</h1>
				 <div id = 'app' class='settingWifi'>
					 <!-- ssid -->
                      <div class='ssidDiv'>
						  <span class='title'>wifi名称:</span>
						  <input type="text" placeholder="请输入wifi名称" v-model='model.ssid' />
					  </div>

					  <!-- password -->
					  <div class='passwordDiv'>
						   <span class='title'>wifi密码:</span>
						   <input type="text" placeholder="请输入wifi密码" v-model='model.password' />

					  </div>

					  <!-- 保存按钮 -->
					  <input type="button" name="botton" value="保存" class="save-to" @click="saveWifi()" />
					  
				 </div>
			</div>
			
			
		</div>
	</div>
	<s:include value="/share/footer.jsp" />


	<script>
	
	  var wifi = new Vue({
		 el:'#app',
		 data: {
			 model: {
				 
			 }
		 },
		 // vue初始化函数
		 created: function () {
			 // 查询当前俱乐部的wifi信息
			  $.ajax({
				  url: 'clubmp!getClubWifiById.asp',
				  dataType: 'json',
				  success: function (res) {
					  if (res.success) {
						  // 数据请求正常
						  wifi.model = res.clubWifi;
					  } else {
						  alert('程序异常, 原因: ' + res.message);
					  }
				  },
				  error: function (e) {
					  console.log('网络异常');
				  }
			  })
		 },
		 methods: {
			 saveWifi: function () {
				 $.ajax({
					 url:'clubmp!saveWifi.asp',
					 dataType: 'json',
					 data: {
						 json: encodeURI(JSON.stringify(wifi.model))
					 },
					 success: function (res) {
						 if (res.success) {
							 alert('保存或修改wifi成功');
							 // 刷新当前页面
							 location.reload();
						 } else {
							 console.log('程序异常,原因: ' + res.message);
						 }
					 },
					 error: function (e) {
						 alert('网络异常');
					 }
				 })
			 }
		 }
	  });
	
	</script>

</body>
</html>
