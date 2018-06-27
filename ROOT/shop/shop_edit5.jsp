<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
$(document).ready(function(){
	var today = new Date().format('yyyy-MM-dd');
	if(!$("#orderStartTime").val()){
		  $('#orderStartTime').val(today);
	 } 
	$('#orderStartTime').datepicker({changeYear: true});
	changeOrderEndTime();
});
function changeOrderEndTime(){
	var orderStartTime = $('#orderStartTime').val();
	var ostArr = orderStartTime.split("-");
	var	ost = new Date(ostArr[0],ostArr[1],ostArr[2]);
	var wellNum = "<s:property value="product.wellNum"/>";
	ost.setDate(ost.getDate()+(wellNum*7));
	var yearStr = ost.getFullYear();
	var monthStr = ost.getMonth() > 9 ? ost.getMonth() : "0" + ost.getMonth();
	var dateStr = ost.getDate() > 9 ? ost.getDate() : "0" + ost.getDate();
	$("#endTime").html(yearStr+"年"+monthStr+"月"+dateStr+"日");
	$("#orderEndTime").val(yearStr+"-"+monthStr+"-"+dateStr); 
}

</script>
	<s:hidden name="shop.orderEndTime" id="orderEndTime"/>
	<s:hidden name="shop.reportDay" id="shopReportDay"/>
	<div class="proedit">
	<!-- <h1>对赌(频率)</h1> -->
	<p align="center" style="font-weight:bold">
		<s:property value="product.name"/>
	</p>
	<div class="proedithi">
		<ul>
			<li>甲方：<s:property value="#session.loginMember.nick"/></li>
			<li>乙方：<s:property value="product.member.name"/></li>
			<li>丙方：健身E卡通</li>
		</ul>
	</div>
	<div class="proedifour">
		<p>
			根据《中华人民共和国合同法》等有关法律法规的规定，甲乙丙三方在平等、自愿、公平和诚实信用的基础上，就健身服务的有关事宜协商订立合同如下：
		</p>
		<p>
			1.甲方承诺从<input type="text" name="shop.orderStartTime" id="orderStartTime" class="text1" value="<s:date name="shop.orderStartTime" format="yyyy-MM-dd"/>" onchange="changeOrderEndTime();"/>起，每个星期到乙方俱乐部健身 <span><s:property value="product.num"/></span> 次，共持续 <span><s:property value="product.wellNum"/></span> 周时间。截止日期为：<span id="endTime"><s:date name="shop.orderEndTime" format="yyyy-MM-dd"/></span>。
		</p>
		<p>
			2.如果甲方在合同期内按承诺的频率完成了健身，乙方向甲方收取 <span><s:property value="product.cost"/></span> 元费用。
		</p>
		<p>
			3.甲方的健身频率不得低于承诺次数；每缺勤一次，甲方将向乙方缴纳缺勤费用 <span><s:property value="product.dutyCost"/></span> 元。
		</p>
		<p>
			4.如果甲方健身超过承诺频率次数，每次应向乙方缴纳 <span><s:property value="product.overCost"/></span> 元费用。但不支付违约金。此款当即从会员的保证金中扣除，会员应该于一周内补足保证金，否则视为违约。
		</p>
		<p>
			5.甲方存在缺勤行为或因自身原因提前终止合同，应向乙方支付违约金 <span><s:property value="product.breachCost"/></span> 元。
		</p>
		<p>
			6.甲方同意交纳本合同保证金  <span><s:property value="product.promiseCost"/></span> 元。乙方收取的缺勤费和违约金总额不得超过甲方交纳的保证金上限。
		</p>
		<p>
			7.保证金由甲方交付给丙方。合同期满<span>一周内</span>，丙方根据合同履约记录与甲、乙双方进行合同结算。
		</p>
		<p>
			8.如乙方因自身原因提前终止合同或服务，丙方确认该事实后的一周内进行结算。将保证金退还给甲方。
		</p>
		<p>
			 9.本合同仅限甲方本人使用，不得转让他人。
		</p>
		<p>
			10.本合同 <span><s:if test="product.promotionType == 1">可以</s:if><s:if test="product.promotionType == 2">不能</s:if></span> 与乙方其他优惠促销活动同时使用。	 
		</p>
		<p>
			11.乙方在非法定节假日暂停营业超过24小时或甲方按照约定办理暂停服务手续的，有效期限相应顺延。
		</p>
		<p>
			12.乙方承诺提供的服务内容：
			<p>
			（1）合同内免费服务项目：
			<span><s:if test="product.freeProjects==null || product.freeProjects == \"\"">无</s:if><s:else><s:property value="#request.freeProjects"/></s:else>
			<s:if test="product.freeProject != null && product.freeProject != \"\"">其它:<s:property value="product.freeProject"/></s:if>
			</span>
			</p>
			<p>
			（2）另收费项目：
			<span><s:if test="product.costProjects==null || product.costProjects == \"\"">无</s:if><s:else><s:property value="#request.costProjects"/></s:else>
			</span>
			其它：<span><s:if test="product.costProject==null || product.costProject == \"\"">无</s:if><s:else><s:property value="product.costProject"/></s:else></span>           
			（以店内公示项目和价格为准）。
			</p>
		</p>
		<p>
			13.乙方向甲方告知调整营业时间、暂停营业等重要事项，应当采用店堂告示及<span><s:if test="(', '+product.talkFunc+', ').indexOf(', 1, ') != -1">交易平台消息</s:if><s:if test="(', '+product.talkFunc+', ').indexOf(', 2, ') != -1">电子邮件</s:if><s:if test="(', '+product.talkFunc+', ').indexOf(', 3, ') != -1">短信</s:if><s:if test="(', '+product.talkFunc+', ').indexOf(', 4, ') != -1">传真</s:if></span>方式。
		</p>
		<p>
			14.其它约定：<span><s:property value="product.remark"/></span>
		</p>
		<p>
			15.双方均同意上述条款及《<a href="product!clause.asp" target="_blank">健身合同通用条款</a>》，三方通过本交易平台完成签约手续，甲方支付合同保证金后本合同正式生效。
		</p>
		<p>
			16.丙方的服务系统仅对甲方的考勤及乙方是否正常营业进行监控，协助双方办理合同款项结算。甲乙双方在合同履行过程中产生的服务质量、安全责任事故、人身、财产损害事故或任何其它合同履行过程中产生的纠纷，丙方不承担任何包括但不限于补偿、赔偿、担保等法律责任。
		</p>
		<p>
			17.丙方完成本合同的结算并向乙方支付甲方的应付款时，按应付款金额的<s:text name="pay.service.cost.e"/>%向乙方收取交易服务费。（交易服务费下限为1元/笔）		
		</p>
		<p>
			18.丙方完成本合同的结算并向甲方办理退款手续时，按应退款金额的<s:text name="pay.service.cost.m"/>%向甲方收取交易手续费。（交易手续费下限为1元/笔）
		</p>
		<p>
			19.甲乙双方如产生争议，应向丙方提供证据，由丙方主持双方调解。调解达成协议的，丙方协助办理结算付/退款事宜；调解无法达成协议，丙方对托管资金进行冻结，双方均有权提起诉讼解决，冻结资金由司法机关处理。
		</p>
		<!-- <p class="end1">
			<input  type="button" name="button" class="button" value="购买" onclick="saveShop();"/>
		</p> -->
	</div>
	</div>

