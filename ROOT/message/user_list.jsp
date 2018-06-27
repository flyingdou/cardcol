<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<s:include value="/share/meta.jsp" />
<meta name="keywords" content="瘦身减肥课程,肌肉健美课程,增强体质" />
<meta name="description"
	content="健身教练的商务中心是健身教练制定的健身套餐,健身课程，如：瘦身减肥课程,肌肉健身课程,增强体质课程等。" />
<title>用户数据</title>
<link href="css/club-order.css" rel="stylesheet" type="text/css" />
<link type="text/css" rel="stylesheet" href="css/pulicstyle.css" />
<link rel="stylesheet" type="text/css" href="css/activ_new.css">
<link rel="stylesheet" type="text/css" href="css/information-center.css" />
<style type="text/css">
.pagenumber1 ul {
	padding-right: 7px;
}

.btn_create{
    margin-top: 10px;
    margin-left: 10px;
	width: 77px;
    height: 25px;
    line-height: 25px;
    text-align: center;
    border: none;
    cursor: pointer;
    background: url(../images/anjiuall.jpg);
    background-position: 0px -167px;
}
</style>
</head>
<body>
	<s:include value="/share/header.jsp" />
	<div id="content">
		<div id="left-1">
			<h1 class="inform"style="background-image:none;background:#ff4401;color:white;font-weight:600">信息中心</h1>
			<div>
				<ul class="inform">
					<li>
						<h1><b>我的消息</b></h1>
						<ul class="bowen">
							<s:if test="#session.loginMember.role != \"M\"">
							<li><a href="message.asp?flag=0" id="coloa">审批 <span
									id="tishi1">(0)</span>
							</a></li>
							</s:if>
							<li><a href="message.asp?flag=1" id="coloa">消息 <span
									id="tishi2">(0)</span>
							</a></li>
							<li><a href="message.asp?flag=2" id="coloa">提醒 <span
									id="tishi3">(0)</span>
							</a></li>
						</ul>
					</li>
					<li>
						<h1><b>成员管理</b></h1>
						<ul class="bowen">
							<s:if test="#session.loginMember.role == \"M\"">
							<li><a href="javascript:goFriend('1');" id="coloa">我的私人教练</a></li>
							</s:if>
							<li><a href="message.asp?flag=3" id="coloa">我的俱乐部</a></li>
							<s:if test="#session.loginMember.role == \"E\"">
							<li><a href="message.asp?flag=4" id="coloa">我的教练</a></li>
							</s:if>
							<s:if test="#session.loginMember.role != \"M\"">
							<li><a href="message.asp?flag=5" id="coloa">我的会员</a></li>
							</s:if>
							<s:if test="#session.loginMember.role == \"E\"">
							<li><a href="info!getMemberData.asp" id="coloa">用户数据</a></li>
							</s:if>
						</ul>
					</li>
				</ul>
			</div>
		</div>
		<div id="right-2">
		   <form action="info!getMemberData.asp" method="post" id="queryForm">
			<div id="container">
				<h1>用户数据</h1>
				<!-- <input type="button" class="btn_create" value="发起优惠券" onclick="location.href='clubmp!editTicket.asp'"/> -->
				<div style="padding: 10px; overflow: hidden;">
					<div style="float: left; margin-right: 10px; overflow: hidden;">
						<div style="float: left; margin-right: 10px;height: 19px; line-height: 19px;">类型</div>
						<select style="float: left;" class="select1" @change="changeType" v-model="model.type">
							<option value="0">全部</option>
							<option value="1">订单</option>
							<option value="2">优惠券</option>
						</select>
					</div>
					<div style="float: left; overflow: hidden;" v-if="model.type == 1">
						<div style="float: left; margin-right: 10px;height: 19px; line-height: 19px;">状态</div>
						<select style="float: left;" class="select2" @change="changeStatus" v-model="model.status">
							<option value="2">全部</option>
							<option value="1">已付款</option>
							<option value="0">未付款</option>
						</select>
					</div>
				</div>
				<div class="t_lists" style="padding-left: 0px; padding-top: 0px;">
					<table width="100%" border="0" cellpadding="0" cellspacing="0"
						class="t_lists_table">
						<tr class="head_table" style="text-align: center;">
							<td width="100"><strong>购买日期</strong></td>
							<td width="90"><strong>昵称</strong></td>
							<td width="90"><strong>真实姓名</strong></td>
							<td width="70"><strong>性别</strong></td>
							<td width="80"><strong>手机号</strong></td>
							<td width="70"><strong>信息来源</strong></td>
							<td width="90"><strong>商品</strong></td>
							<td width="70"><strong>金额</strong></td>
						</tr>
						<tr class="top_right" v-for="(item,i) in memberList">
							<td style="height: 25px; text-align: center;">{{item.time}}</td>
							<td style="text-align: center;">{{item.name}}</td>
							<td style="text-align: center;">{{item.realName}}</td>
							<td style="height: 25px; text-align: center;">{{item.gender == 'M' ? '男' : '女'}}</td>
							<td style="text-align: center;">{{item.mobilephone}}</td>
							<td style="text-align: center;">{{item.origin}}</td>
							<td style="height: 25px; text-align: center;">{{item.productName}}</td>
							<td style="text-align: center;">{{item.price}}</td>
						</tr>
					</table>
				</div>
				<div class="page_rigddd">
					<input type="hidden" name="type" :value="model.type" />
					<input type="hidden" name="status" :value="model.status" />
					<s:include value="/share/pageAjax.jsp" />
				</div>
			</div>
		  </form>
		</div>
	</div>
	<s:include value="/share/footer.jsp" />
	<!-- js -->
	<script src="ecartoon-weixin/js/vue.min.js"></script>
	<script>
		var vue = new Vue({
			el : "#content",
			data : {
				memberList: [],
				model: {}
			},
			created : function() {
				this.memberList = ${memberData};
				this.model.type = ${type};
				this.model.status = ${status};
			},
			methods: {
				changeType: function () {
					location.href = "info!getMemberData.asp?type=" + this.model.type;
				},
				
				changeStatus: function () {
					location.href = "info!getMemberData.asp?type=" + this.model.type + "&status=" + this.model.status;
				}
			}
		});
	</script>
</body>
</html>