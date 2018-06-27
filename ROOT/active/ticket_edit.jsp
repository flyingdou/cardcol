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
<link rel="stylesheet" type="text/css" href="css/activ_new.css">
<style type="text/css">
.pagenumber1 ul {
	padding-right: 7px;
}

.uploadClass1: {
	margin-left: -40px;
	width: 240.8px;
	height:78.4px;
	overflow: hidden;
}

.uploadClass2: {
	
}
</style>
</head>
<body>
	<s:include value="/share/header.jsp" />
	<div id="content">
		<s:include value="/order/nav.jsp" />
		<div id="right-2">
			<div id="container">
			  <form id="ticketForm" method="post" action="clubmp!saveTicket.asp" enctype="multipart/form-data">
				<h1>{{editTicketTitle}}</h1>
				<div style="overflow: hidden; padding-left: 20px; padding-top: 10px"
					class="fap">
					<ul style="margin: 0; padding: 0; list-style: none;">
						<li style="height: 78px; line-height: 78px;margin-bottom:10px;">
							<div style="width: 30%; float: left;">
								<b>优惠券图片</b>
							</div>
							<div style="width: 70%; float: left;position: relative;">
								<div @click="openUploadWindow()" style="margin-left: -40px;width: 240.8px;height:78.4px;overflow: hidden;border: 1px solid #F0F0F2;">
									<img :src="model.image" id="preview1" style="width: 100%;height: auto;" />
								</div>
								<input type="file" id="txtImage1" name="image" style="display: none;" />
							</div>
						</li>
						
						<li style="height: 30px; line-height: 30px;">
							<div style="width: 30%; float: left;">
								<b>优惠券名称</b>
							</div>

							<div style="width: 70%; float: left;">
								<label v-if="showInput"> <s:textfield name="active.name"
										cssClass="input_w" id="activeName" v-model="model.name"
										cssStyle="width: 300px;line-height:22px;" />
								</label> <label v-if="showSpan"> <span>{{model.name}}</span>
								</label>
							</div>
						</li>
						
						<li style="height: 30px; line-height: 30px;">
							<div style="width: 30%; float: left;">
								<b>优惠券减免金额</b>
							</div>
							<div style="width: 70%; float: left;">
								<input type="number" v-model="model.price" class="activeDays"
									style="border: 1px solid #A5A3A5;" v-if="showInput" /> <span v-if="showSpan">{{model.price}}</span>
								元
							</div>
						</li>
						
						<li style="height: 30px; line-height: 30px;">
							<div style="width: 30%; float: left;">
								<b>优惠券起用金额</b>
							</div>
							<div style="width: 70%; float: left;">
								<input type="number" v-model="model.limit_price" class="activeDays"
									style="border: 1px solid #A5A3A5;" v-if="showInput" /> <span v-if="showSpan">{{model.limit_price}}</span>
								元
							</div>
						</li>
						
						<li style="height: 30px; line-height: 30px;">
							<div style="width: 30%; float: left;">
								<b>优惠券有效期</b>
							</div>
							<div style="width: 70%; float: left;">
								<input type="number" v-model="model.period" class="activeDays"
									style="border: 1px solid #A5A3A5;" v-if="showInput" /> <span v-if="showSpan">{{model.period}}</span>
								天
							</div>
						</li>
						
						<li style="height: 30px; line-height: 30px;">
							<div style="width: 30%; float: left;">
								<b>优惠券适用俱乐部</b>
							</div>
							<div style="width: 70%; float: left;">
								<div class="club-item" style="float:left;margin-right:10px;" v-for="(club,i) in clubList" v-if="showInput || club.id == model.limit_club[i]">
									<input type="checkbox" class="check-club-list" :value="club.id" v-model="model.limit_club" :true-value="club.id" disabled v-if="i == 0 || !showInput"/> 
									<input type="checkbox" class="check-club-list" :value="club.id" v-model="model.limit_club" :true-value="club.id" v-if="i != 0 && showInput"/>
									<span>{{club.name}}</span>
								</div>
							</div>
						</li>
						
						<li style="height: 100px; line-height: 100px;" v-if="showInput">
							<input type="hidden" name="json" :value="param" />
							<div
								style="height: 100%; width: 100%; padding: 10px 0; float: left; text-align: center;">
								<img src="images/order.jpg" id="submit1" @click="saveTicket()"
									style="margin-left: -100px;" />
							</div>
						</li>
						
					</ul>
				</div>
			   </form>
			</div>
		</div>
		<s:include value="/share/footer.jsp" />
		<!-- js -->
		<script src="ecartoon-weixin/js/vue.min.js"></script>
		<script type="text/javascript" src="script/uploadPreview.js"></script>
		<script>
			$(function(){
				// 图片处理
				new uploadPreview({
		    		UpBtn : "txtImage1",
		    		ImgShow : "preview1"
		    	});
			});
		
			
			var vue = new Vue({
				el : "#content",
				data : {
					editTicketTitle : "发起优惠券",
					model : {
						image: ""
					},
					clubList: [],
					param: "",
					showInput: true,
					showSpan: false
				},
				created : function() {
					this.clubList = ${clubList};
					this.model.limit_club = [];
					this.model.limit_club.push(this.clubList[0].id);
					if ('${ticket}' != '') {
						this.model = JSON.parse('${ticket}');
						this.model.image = "picture/" + this.model.image;
						this.model.limit_club = this.model.limit_club.split(",");
						this.editTicketTitle = "修改优惠券";
					}
					if('${look}' != ''){
						this.editTicketTitle = "优惠券详情";
						this.showInput = false;
						this.showSpan = true;
					}
				},
				methods : {
					// 打开上传窗口
					openUploadWindow: function(){
						if('${look}' != ''){
							return;
						}
						$("#txtImage1").click();
					},
					
					// 保存优惠券
					saveTicket: function(){
						// 检查参数
						if(!this.model.name || this.model.name == ""){
							alert("请填写优惠券名称");
							return;
						}
						if(!this.model.price || this.model.price == ""){
							alert("请填写优惠券减免金额");
							return;
						}
						if(!this.model.limit_price || this.model.limit_price == ""){
							alert("请填写优惠券起用金额");
							return;
						}
						if(!this.model.period || this.model.period == ""){
							alert("请填写优惠券有效期");
							return;
						}
						this.model.limit_club = this.model.limit_club.join(",");
						this.param = encodeURI(JSON.stringify(this.model));
						setTimeout(function(){
							document.querySelector("#ticketForm").submit();
						}, 200);
					}
				}
			});
		</script>
</body>
</html>