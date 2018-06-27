<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<s:include value="/share/meta.jsp" />
<meta name="keywords" content="瘦身减肥课程,肌肉健美课程,增强体质" />
<meta name="description"
	content="健身教练的商务中心是健身教练制定的健身套餐,健身课程，如：瘦身减肥课程,肌肉健身课程,增强体质课程等。" />
<title>我发起的优惠券</title>
<link href="css/club-order.css" rel="stylesheet" type="text/css" />
<link type="text/css" rel="stylesheet" href="css/pulicstyle.css" />
<link rel="stylesheet" type="text/css" href="css/activ_new.css">
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
		<s:include value="/order/nav.jsp" />
		<div id="right-2">
			<div id="container">
				<h1>我发起的优惠券</h1>
				<input type="button" class="btn_create" value="发起优惠券" onclick="location.href='clubmp!editTicket.asp'"/>
				<!--发起记录list-->
				<div class="t_lists" style="padding-left: 0px; padding-top: 0px;">
					<table width="100%" border="0" cellpadding="0" cellspacing="0"
						class="t_lists_table">
						<tr class="head_table" style="text-align: center;">
							<td width="120"><strong>名称</strong></td>
							<td width="50"><strong>有效期</strong></td>
							<td width="90"><strong>减免金额</strong></td>
							<td width="70"><strong>操作</strong></td>
						</tr>
						<tr class="top_right" v-for="(ticket,i) in ticketList">
							<td style="height: 25px; text-align: center;">{{ticket.name}}</td>
							<td style="text-align: center;">{{ticket.period}}天</td>
							<td style="text-align: center;">{{ticket.limit_price}}</td>
							<td style="border-right: 0px; text-align: center;" class="pj">
								<span style="color:blue;cursor:pointer;" @click="changeStatus(i)">
									{{ticket.effective == 1 ? "关闭" : "开启"}}
								</span>
								<span style="color:blue;cursor:pointer;" @click="editTicket(i)" v-if="ticket.effective == 0">编辑</span>
								<span style="color:blue;cursor:pointer;" @click="getTicketById(i)">查看详情</span>
							</td>
						</tr>
					</table>
				</div>
				<div class="page_rigddd">
					<s:include value="/share/pageAjax.jsp" />
				</div>
			</div>
		</div>
	</div>
	<s:include value="/share/footer.jsp" />
	<!-- js -->
	<script src="ecartoon-weixin/js/vue.min.js"></script>
	<script>
		var vue = new Vue({
			el : "#content",
			data : {
				ticketList : []
			},
			created : function() {
				this.ticketList = ${ticketList}
			},
			methods: {
			 changeStatus: function (i) {
					var ticketId = this.ticketList[i].id;
					$.ajax({
						url: "clubmp!changeTicketStatus.asp",
						type: "post",
						data: {
							ticketId: ticketId
						},
						dataType: "json",
						success: function (res) {
							vue.ticketList[i].effective = res.effective;
						}
					});
				},
				
				editTicket: function(i){
					location.href = "clubmp!editTicket.asp?ticketId=" + this.ticketList[i].id;
				},
				
				getTicketById: function (i){
					location.href = "clubmp!editTicket.asp?look=1&ticketId=" + this.ticketList[i].id;
				}
			}
		});
	</script>
</body>
</html>