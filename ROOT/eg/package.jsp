<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title><s:property value="#request.pr.name"/></title>
		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
		<meta name="apple-mobile-web-app-capable" content="yes">
		<link  rel="stylesheet" href="../eg/css/mui.min.css" />
		<link rel="stylesheet" type="text/css" href="../eg/css/mui.picker.min.css" />
		<script type="text/javascript" src="script/jquery-1.7.2.min.js"></script>
		<style type="text/css">
			#packageName{
				text-align: center;
				color:black;
				font-size:15px;
			}
			.mui-table-view .mui-table-view-cell p>span:first-child{
				color:black;
				font-size:13px;
			}
			.mui-table-view .mui-table-view-cell p>span:nth-child(2){
				margin-left:50px;
				font-size:13px;
			}
			.fagui{
				font-size:12px;
				margin-bottom:10px; 
			}
			.select_dateImg{
				
			}
			/*日期显示框*/
			.date{
				line-height:26px;
				height:26px;
				border:1px solid #2AC845;
				border-radius:3px;
				color:black;
				font-size:13px;
				margin:0 5px;
			}
			#footer,footer{
				border:none;
				color:#fff;
				/*background-color:#F87D04;*/
			}
			.mui-bar-footer {
				
			    bottom: 0;
			    background-color: #FE9E3A;
			}
		</style>
		
	</head>
	<body>
		<footer class="mui-bar mui-bar-footer font-white">
			<h3 class="mui-title font-white" id="footer"><a href="javascript:gosubmit()">购买</a></h3>
		</footer>
		<div class="mui-content">
			<div class="mui-scroll-wrapper" style="bottom:45px;">
			    <div class="mui-scroll">
			    <s:hidden value="#request.customer.id" id="customerid"></s:hidden>
			    <s:hidden value="#request.pr.id" id="productid"></s:hidden>
			    	<ul class="mui-table-view">
			    		<li class="mui-table-view-cell">
			    			<p id="packageName"><s:property value="#request.pr.name"/></p>
			    		</li>
			    	</ul>
			    	<ul class="mui-table-view">
			    		<li class="mui-table-view-cell">
			    			<p><span>甲方:</span><span id="jia_name"><s:property value="#request.customer.name"/></span></p>
			    		</li>
			    	</ul>
			    	<ul class="mui-table-view">
			    		<li class="mui-table-view-cell">
			    			<p><span>乙方:</span><span id="yi_club"><s:property value="#request.author"/></span></p>
			    		</li>
			    	</ul>
			    	<ul class="mui-table-view">
			    		<li class="mui-table-view-cell">
			    			<p><span>丙方:</span><span id="bing_platform">卡库健身电商平台</span></p>
			    		</li>
			    	</ul>
			    	<ul class="mui-table-view">
			    		<li class="mui-table-view-cell">
			    			<p class="fagui">根据《合同法》法律法规的规定，甲乙丙三方在平等、自愿、公平的诚实信用的基础上，就健身服务的有关事宜订立如下合同：</p>
			    			<p ><span class="mui-col-sm-2"> <b style="margin-left:2%; color:#484848">1.甲方通过购买乙方的<s:if test="#request.pr.cardType.equals('1')"><s:property value="#request.pr.wellNum"/>月时效卡</s:if>
        <s:else><s:property value="#request.pr.num"/>次计次卡，有效期限<s:property value="#request.pr.wellNum"/>月</s:else>			
			<br></b>
		<b style="margin-left:2%; color:#484848">2.<s:if test="#request.pr.isReg==1">该会员资格记名，仅限甲方本人使用；甲方将会员资格转让他人时，<s:if test="#request.pr.assignType==1">需要支付转让手续费<s:property value="#request.pr.assignCost"/> 元。</s:if> <s:else>不需要支付转让手续费</s:else></s:if>
			
			<s:else>该会员资格不记名，可由任何符合条件的持卡人使用。</s:else><br></b>
		<b style="margin-left:2%; color:#484848">3.甲方向丙方支付购卡款计<s:property value="#request.pr.cost"/> 元。丙方于系统自动计算将该合同款全部转付给乙方。<br></b>
		<b style="margin-left:2%; color:#484848">4.甲方因自身原因提前终止合同，应向乙方支付违约金 <s:property value="#request.pr.breachCost"/>元,乙方收到违约金后向甲方退还甲方未消费的金额。<br></b>
		<b style="margin-left:2%; color:#484848">5.乙方因自身原因提前终止合同或服务，应将未履行的合同款退还甲方。<br></b>
			
		<b style="margin-left:2%; color:#484848">6.计次卡的有效期限届满后，卡内仍有剩余次数的，乙方应当提供一次展期，展期时间计算方式：剩余次数乘以 3
         天（3-5天的合理健身频率）=展期天数。<br></b>
		<b style="margin-left:2%; color:#484848">7.本合同<s:if test="#request.pr.promotionType.equals('1')">可以</s:if><s:else>不能 </s:else>与乙方其他优惠促销活动同时使用。<br></b>
		<b style="margin-left:2%; color:#484848">8.本合同使用范围：<s:if test="#request.pr.useType.equals('1')">  仅限在本单店有效。</s:if>
			<s:else>在连锁门店通用，通用门店包括：
			<s:iterator value="#request.friends" id="friend"><s:property value="name"/>&nbsp;&nbsp;&nbsp;</s:iterator></s:else><br></b>
		<b style="margin-left:2%; color:#484848">9.乙方在非法定节假日暂停营业超过24小时或甲方按照约定办理暂停服务手续的，有效期限相应顺延。<br></b>	
		<b style="margin-left:2%; color:#484848">10.乙方承诺提供的服务内容：<br>
			
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;（1）合同内免费服务项目：<s:if test="#request.pr.freeProjects==null">无</s:if><s:else><s:property value="freeProjects"/></s:else> <br> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;其它：<s:if test="#request.pr.freeProject==null" >无</s:if> <s:else><s:property value="freeProject"/></s:else><br>
			
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;（2）另收费项目：<s:if test="#request.pr.costProjects==null">无</s:if><s:else><s:property value="costProjects"/></s:else><br>  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;其它：<s:if test="#request.pr.costProject">无</s:if> <s:else ><s:property value="costProject"/></s:else>
			 （以店内公示项目和价格为准）。<br></b>
			
		<b style="margin-left:2%; color:#484848">11.乙方向甲方告知调整营业时间、暂停营业等重要事项，应当采用店堂告示及<s:if test="#request.pr.talkFunc.equals('1')">交易平台消息</s:if> <s:elseif test="#request.pr.talkFunc.equals('2')">电子邮件 </s:elseif> <s:elseif test="#request.pr.talkFunc.equals('3')">短信</s:elseif> <s:else>传真</s:else> 方式。<br></b>
			
		<b style="margin-left:2%; color:#484848">12.其它约定：
			<s:if test="#request.pr.remark==null">无</s:if><s:else><s:property value="#request.pr.remark"/></s:else><br></b>
			
		<b style="margin-left:2%; color:#484848">13.双方均同意上述条款及《健身合同通用条款》，三方通过本交易平台完成签约手续，甲方支付合同款后本合同正式生效。<br></b>
			
		<b style="margin-left:2%; color:#484848">14.丙方的服务系统仅对甲方的考勤及乙方是否正常营业进行监控，协助双方办理合同款项结算。甲乙双方在合同履行过程中产生的服务质量、安全责任事故、人身、财产损害事故或任何其它合同履行过程中产生的纠纷，丙方不承担任何包括但不限于补偿、赔偿、担保等法律责任。<br></b>
			
		<b style="margin-left:2%; color:#484848">15.丙方完成本合同的结算并向乙方支付甲方的应付款时，按应付款金额的8%向乙方收取交易服务费。（交易服务费下限为1元/笔）<br></b></span>
			    			   <input type="input"  value="2016-12-31" id="resule"  style="line-height:26px;height:26px;border:1px solid #2AC845;border-radius:3px;color:black;font-size:13px;margin:0 5px;width:65%;"/>
			    			   <img  class="mui-col-sm-1" data-options='{"type":"date","beginYear":"2010","endYear":"2050"}' src="images/logo1.png" class="select_dateImg" id="rili_img"   style="width:15px;height:25px;padding-top:13px;"/>
			    			</p>
			    			<p id="details"></p>
			    		</li>
			    		
			    	</ul>
			    	
			    </div>
			</div>
			
		</div>
	</body>
    <script src="../eg/js/mui.min.js"></script>
	<script type="text/javascript">
		mui('.mui-scroll-wrapper').scroll({
			deceleration:0.0005
		});
		
//		点击购买时跳到提交订单页面
// 		$(function(){
// 			$('#footer').click(function(){
// 				var customerid=<s:property value="#request.customer.id"/>;
//				var productid=<s:property value="#request.pr.id"/>;
// 				var date=$("#resule").val();
// 				$.ajax({
// 					url:"cardselling!submitOrder.asp",
// 					type:"post",
// 					dataType:"text",
// 					data:{
// 						memberid:customerid,
//						productid:productid,
// 						date:date
// 					},
// 					success:function(data){
// 						location.href="eg/submit_order.jsp"
// 					},
// 					error:function(data){debugger;
// 						alert("数据有误");
// 					}
// 				});
// 			});
// 		});
		function gosubmit(){
			var customerid=<s:property value="#request.customer.id"/>;
			var productid=<s:property value="#request.pr.id"/>;
			var date=$("#resule").val();
			location.href="cardselling!submitOrder.asp?mid="+customerid+"&pid="+productid+"&orderdate="+date;
		}
		
			
			
			
			//mui方法    点击购买时进入订单提交页面
//			var footer=document.getElementById('footer');
//			footer.addEventListener('tap',function(){
//				mui.openWindow({
//					url:'submit_order.html',		
//					id:'submit_order.html'
//				});
//			});
//			
			
			//获取上一页面传过来的值
// 			var url=location.search;
// 			var mei=new Object();
			
// 			if(url.indexOf("?")!=-1){
				
// 				var str=url.substr(1);
// 				var strs=str.split("&");
// 				for(var i=0;i<strs.length;i++){
// 					mei[strs[i].split("=")[0]]=unescape(strs[i].split("=")[1]);
// 				}
// 			}
//			alert(mei["card_name"]);
//			alert(mei["card_mendian"]);
			
// 			var packageName=document.getElementById('packageName');
// 			var yi_club=document.getElementById('yi_club');
// 			packageName.innerText=mei["card_name"];
// 			yi_club.innerText=mei["card_mendian"];
			
			
			
			
	</script>
	<script src="../eg/js/mui.picker.min.js"></script>
	<script>
				var rili_img=document.getElementById('rili_img');
				rili_img.addEventListener('tap',function(){
					var resule=document.getElementById('resule');
					var optionsJson = this.getAttribute('data-options') || '{}';
					var options = JSON.parse(optionsJson);
					var picker = new mui.DtPicker(options);
					picker.show(function(rs) {
							/*
							 * rs.value 拼合后的 value
							 * rs.text 拼合后的 text
							 * rs.y 年，可以通过 rs.y.vaue 和 rs.y.text 获取值和文本
							 * rs.m 月，用法同年
							 * rs.d 日，用法同年
							 * rs.h 时，用法同年
							 * rs.i 分（minutes 的第二个字母），用法同年
							 */
							resule.value=rs.text;
							picker.dispose();
						});
					
					
				});
				
			
		</script>
</html>
