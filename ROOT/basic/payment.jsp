<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<s:include value="/share/meta.jsp" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="keywords" content="健身E卡通_我的账户" />
<meta name="description" content="健身E卡通_我的账户" />
<title>健身E卡通_我的账户</title>
<link href="css/payment.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
var money = 90;
$(function(){
	$('input[name="radiobutton"]').click(function(){
		if($(this).attr('checked')) {
			var qx = $('#cmbqx').val();
			var val = $(this).val();
			money = qx * val;
			$('.money').html(money);
		}
	});
	$('#cmbqx').change(function(){
		var qx = $(this).val();
		var val = $('input[name="radiobutton"]:checked').val();
		money = qx * val;
		$('.money').html(money);
	});
})
</script>
</head>
<body>
	<s:include value="/share/header.jsp"/>
	<div id="content">
		<h1>会员支付</h1>
		<h2>用户级别说明</h2>
		<table cellpadding="1" cellspacing="1" border="0">
			<tr>
				<th width=15%;>用户级别</th>
				<th width=25%;>权限介绍</th>
				<th width=10%;>游客</th>
				<th width=10%;>普通用户</th>
				<th width=10%;>高级用户</th>
				<th width=30%;>备注</th>
			</tr>
			<tr>
				<td></td>
				<td>浏览</td>
				<td class="textaba">√</td>
				<td class="textaba">√</td>
				<td class="textaba">√</td>
				<td></td>
			</tr>
			<tr>
				<td></td>
				<td>查询</td>
				<td class="textaba">√</td>
				<td class="textaba">√</td>
				<td class="textaba">√</td>
				<td></td>
			</tr>
			<tr>
				<td></td>
				<td>自动生成计划</td>
				<td class="textaba">—</td>
				<td class="textaba">—</td>
				<td class="textaba">√</td>
				<td></td>
			</tr>
			<tr>
				<td></td>
				<td>加入俱乐部</td>
				<td class="textaba">—</td>
				<td class="textaba">√</td>
				<td class="textaba">√</td>
				<td></td>
			</tr>
			<tr>
				<td></td>
				<td>聘请私教</td>
				<td class="textaba">—</td>
				<td class="textaba">√</td>
				<td class="textaba">√</td>
				<td></td>
			</tr>
			<tr>
				<td></td>
				<td>DIY健身计划</td>
				<td class="textaba">—</td>
				<td class="textaba">√</td>
				<td class="textaba">√</td>
				<td></td>
			</tr>
			<tr>
				<td></td>
				<td>预约课程</td>
				<td class="textaba">—</td>
				<td class="textaba">√</td>
				<td class="textaba">√</td>
				<td>成为俱乐部或教练的会员才能预约</td>
			</tr>
			<tr>
				<td></td>
				<td>评价俱乐部</td>
				<td class="textaba">—</td>
				<td class="textaba">√</td>
				<td class="textaba">√</td>
				<td>所有用户都可在俱乐部首页上评价</td>
			</tr>
			<tr>
				<td></td>
				<td>评价课程（团体、私教）</td>
				<td class="textaba">—</td>
				<td class="textaba">√</td>
				<td class="textaba">√</td>
				<td>所有用户都可在课程表上对过去的课程（教练）进行评价。</td>
			</tr>
			<tr>
				<td></td>
				<td>评价私教</td>
				<td class="textaba">—</td>
				<td class="textaba">√</td>
				<td class="textaba">√</td>
				<td>所有用户在私教首页上评价</td>
			</tr>
			<tr>
				<td>费用</td>
				<td></td>
				<td>免费</td>
				<td>60元/每季度</td>
				<td>90元/每季度</td>
				<td></td>
			</tr>
			<tr>
				<td>选择会员级别</td>
				<td></td>
				<form method="post" action="">
					<td></td>
					<td><label> <input name="radiobutton" type="radio" value="60" /></label></td>
					<td><label> <input name="radiobutton" type="radio" value="90" checked="checked" /></label></td>
				</form>
				<td></td>
			</tr>
			<tr>
				<td colspan="6" class="textabc">注：<br />1. 每个训练周期为3个月。 <br />
					2. 以上均为本健身E卡通网络健身平台使用费，不含私教费。私教费按教练制订的标准支付。
				</td>
			</tr>
		</table>
		<h2>付款方式</h2>
		<table class="tableb" cellpadding="1" cellspacing="1" border="0">
			<tr>
				<td>会员期限：</td>
				<td colspan="2">
					<p class="tabpa">
						<select style="width: 100px; height: 20px; border: 1px solid #ccc;" id="cmbqx">
							<option value="1">一个季度</option>
							<option value="2">两个季度</option>
							<option value="3">三个季度</option>
							<option value="4">四个季度</option>
						</select>
					</p>
				</td>
			</tr>
			<tr>
				<td>所需费用：</td>
				<td colspan="2">
					<p class="tabpb">
						<label>￥<span class="money">90</span>元</label>
						<!-- 选不同的套餐和季度价格会变-->
					</p>
				</td>
			</tr>
			<tr>
				<td rowspan="3">支付方式</td>
				<td>
					<form method="post" action="">
						<input type="radio" name="" value="">网银通
					</form>
				</td>
				<td rowspan="3">注：目前只支持淘宝支付方式。</td>
			</tr>
			<tr>
				<td><form method="post" action="">
						<input type="radio" name="" value="">淘宝支付
					</form></td>
			</tr>
			<tr>

				<td>
					<form method="post" action="">
						<input type="radio" name="" value="">支付宝支付
					</form>
				</td>
			</tr>
			<tr>
				<td colspan="3"><input type="button" value="付款" onclick=""
					class="buttom"></td>
			</tr>
		</table>
		<div id="tabsev">
			<h1>客户服务</h1>
			<div class="content">
				<div class="condiv">
					<li><span class="red"><s:property value="#session.systemConfig.serviceTell" /></span></li>
					<li><span class="red"><s:property value="#session.systemConfig.qq" /></span></li>
					<li><span class="red2">健身E卡通官方微博</span><a href="http://www.dyjlb.net:82/wb"></a></li>
				</div>
				<p>工作时间：周一到周日 8:00 - 22:00</p>
			</div>
		</div>
	</div>
	<s:include value="/share/footer.jsp" />
</body>
</html>
