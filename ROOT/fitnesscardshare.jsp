<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	     <meta name="keywords" content="健身E卡通" />
      <meta name="description" content="健身E卡通 " />
<title>[健身卡]-健身E卡通</title>
<link href="css/bootstrap.min.css" rel="stylesheet">
 	<style>
    	#div{
			width:100%;
			float:left;
			position:fixed; 
			bottom:0;	
		}
		#div2{
			width:100%;
			_position:absolute; 
			position:fixed !important; 
 	 		overflow:hidden;
			bottom:0px; 
			right:0px;
		}
		
    </style>
</head>
<body>
	<div class="text-center" style="margin-top:2%; font-size:16px;"><b>${pr.name}</b></div>
    <div style="margin-top:5%;">
    <div style="margin-left:5%;">
    	<span style="color:#3D3D3D"><b>甲方</b>：</span>
        <span style=" float:right; padding-right:20px; color:#999;">${username}</span>
        <hr style=" margin-top:10px;"/>
    </div>
    <div style="margin-left:5%;">
    	<span style="color:#555;"><b>乙方</b>：</span>
        <span style=" float:right; padding-right:20px; color:#999;">${pr.member.name}</span>
        <hr style=" margin-top:10px;"/>
    </div>
    <div style="margin-left:5%;">
    	<span style="color:#757575"><b>丙方</b>：</span>
        <span style=" float:right; padding-right:20px; color:#999;">健身E卡通电商平台</span>
        <hr style=" margin-top:10px;"/>
    </div>
    <div style="margin-left:5%; line-height:40px; margin-bottom:20%;">
    	<p style="color:#999;">根据《合同法》法律法规的规定，甲乙丙三方在平等、自愿、公平、诚实信用的基础上，就健身服务的有关事宜订立合同如下：</p>
    
    <!-- 圈存（计时）合约 -->
    <s:if test="#request.pr.proType==1">
    	<b style="margin-left:2%; color:#484848">1.甲方自
			<s:property value="#request.pr.startTime"/>
			起成为乙方会员，会员资格的有效期限为 <s:property value="#request.pr.wellNum"/>
			 月。截止日期为：
			<s:property value="#request.pr.endTime"/>
			。甲方拥有在有效期限内按照乙方承诺不限次享受约定服务的权利，有效期限届满后会员资格自动终止。<br></b>
			
		<b style="margin-left:2%; color:#484848">2.<s:if test="#request.pr.isReg==1">该会员资格记名，仅限甲方本人使用；甲方将会员资格转让他人时，<s:if test="#request.pr.assignType==1">需要支付转让手续费<s:property value="#request.pr.assignCost"/> 元。</s:if> <s:else>不需要支付转让手续费</s:else></s:if>
			
			<s:else>该会员资格不记名，可由任何符合条件的持卡人使用。</s:else><br></b>
			
		<b style="margin-left:2%; color:#484848">3.甲方应向乙方支付合同款共计<s:property value="#request.pr.cost"/> 
			 元。双方同意将该合同款交付给丙方托管，并由丙方每月 
			<s:property value="#request.pr.reportDay"/>
			 日进行上一个月甲乙双方应付费用的结算。<br></b>
			
		<b style="margin-left:2%; color:#484848">4.甲方因自身原因提前终止合同，应向乙方支付违约金 <s:property value="#request.pr.breachCost"/>
			元。乙方收到违约金后向甲方退还甲方未消费的金额。<br></b>
			
		<b style="margin-left:2%; color:#484848">5.乙方因自身原因提前终止合同或服务，丙方一周内组织双方确认该事实后，协助办理结算，将未履行的合同款退还甲方。<br></b>
			
		<b style="margin-left:2%; color:#484848">6.本合同<s:if test="#request.pr.promotionType.equals('1')">可以</s:if><s:else>不能 </s:else>与乙方其他优惠促销活动同时使用。<br></b>
			
		<b style="margin-left:2%; color:#484848">7.本合同使用范围：<s:if test="#request.pr.useType.equals('1')">  仅限在本单店有效。</s:if>
			<s:else>在连锁门店通用，通用门店包括：</s:else><br></b>
			
		<b style="margin-left:2%; color:#484848">8.乙方在非法定节假日暂停营业超过24小时或甲方按照约定办理暂停服务手续的，有效期限相应顺延。<br></b>
			
		<b style="margin-left:2%; color:#484848">9.乙方承诺提供的服务内容：<br>
			
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;（1）合同内免费服务项目：<s:if test="#request.pr.freeProjects==null">无</s:if><s:else><s:property value="freeProjects"/></s:else> <br> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;其它：<s:if test="#request.pr.freeProject==null" >无</s:if> <s:else><s:property value="freeProject"/></s:else><br>
			
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;（2）另收费项目：<s:if test="#request.pr.costProjects==null">无</s:if><s:else><s:property value="costProjects"/></s:else><br>  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;其它：<s:if test="#request.pr.costProject">无</s:if> <s:else ><s:property value="costProject"/></s:else>
			 （以店内公示项目和价格为准）。<br></b>
			
		<b style="margin-left:2%; color:#484848">10.乙方向甲方告知调整营业时间、暂停营业等重要事项，应当采用店堂告示及<s:if test="#request.pr.talkFunc.equals('1')">交易平台消息</s:if> <s:elseif test="#request.pr.talkFunc.equals('2')">电子邮件 </s:elseif> <s:elseif test="#request.pr.talkFunc.equals('3')">短信</s:elseif> <s:else>传真</s:else> 方式。<br></b>
			
		<b style="margin-left:2%; color:#484848">11.其它约定：
			<s:property value="#request.pr.remark"/><br></b>
			
		<b style="margin-left:2%; color:#484848">12.双方均同意上述条款及《健身合同通用条款》，三方通过本交易平台完成签约手续，甲方支付合同款后本合同正式生效。<br></b>
			
		<b style="margin-left:2%; color:#484848">13.丙方的服务系统仅对甲方的考勤及乙方是否正常营业进行监控，协助双方办理合同款项结算。甲乙双方在合同履行过程中产生的服务质量、安全责任事故、人身、财产损害事故或任何其它合同履行过程中产生的纠纷，丙方不承担任何包括但不限于补偿、赔偿、担保等法律责任。<br></b>
			
		<b style="margin-left:2%; color:#484848">14.丙方完成本合同的结算并向乙方支付甲方的应付款时，按应付款金额的8%向乙方收取交易服务费。（交易服务费下限为1元/笔）<br></b>
			
		<b style="margin-left:2%; color:#484848">15.丙方完成本合同的结算并向甲方办理退款手续时，按应退款金额的3%向甲方收取交易手续费。（交易手续费下限为1元/笔）<br></b>
			
		<b style="margin-left:2%; color:#484848">16.甲乙双方如产生争议，应向丙方提供证据，由丙方主持双方调解。调解达成协议的，丙方协助办理结算付/退款事宜；调解无法达成协议，丙方对托管资金进行冻结，双方均有权提起诉讼解决，冻结资金由司法机关处理。<br /></b>
       </s:if>
       
     <!-- 圈存（计次）合约 -->  
       <s:elseif test="#request.pr.proType==2">
       <b style="margin-left:2%; color:#484848">1.甲方自
			<s:property value="#request.pr.startTime"/>
			购买乙方的<s:property value="#request.pr.num"/>次计次卡，成为乙方的会员。
			<br></b>
			
		<b style="margin-left:2%; color:#484848">2.<s:if test="#request.pr.isReg==1">该会员资格记名，仅限甲方本人使用；甲方将会员资格转让他人时，<s:if test="#request.pr.assignType==1">需要支付转让手续费<s:property value="#request.pr.assignCost"/> 元。</s:if> <s:else>不需要支付转让手续费</s:else></s:if>
			
			<s:else>该会员资格不记名，可由任何符合条件的持卡人使用。</s:else><br></b>
		<b style="margin-left:2%; color:#484848">3.该会员资格的有效期限为 <s:property value="#request.pr.wellNum"/>
             月；有效期限届满后，卡内仍有剩余次数的，乙方应当提供一次展期，展期时间计算方式：剩余次数乘以 3
             天（3-5天的合理健身频率）=展期天数。<br></b>
		<b style="margin-left:2%; color:#484848">4.甲方应向乙方支付合同款共计<s:property value="#request.pr.cost"/> 
			 元。双方同意将该合同款交付给丙方托管，并由丙方每月 
			<s:property value="#request.pr.reportDay"/>
			 日进行上一个月甲乙双方应付费用的结算。<br></b>
			
		<b style="margin-left:2%; color:#484848">5.甲方因自身原因提前终止合同，应向乙方支付违约金 <s:property value="#request.pr.breachCost"/>
			元。乙方收到违约金后向甲方退还甲方未消费的金额。<br></b>
			
		<b style="margin-left:2%; color:#484848">6.乙方因自身原因提前终止合同或服务，丙方一周内组织双方确认该事实后，协助办理结算，将未履行的合同款退还甲方。<br></b>
			
		<b style="margin-left:2%; color:#484848">7.本合同<s:if test="#request.pr.promotionType.equals('1')">可以</s:if><s:else>不能 </s:else>与乙方其他优惠促销活动同时使用。<br></b>
			
		<b style="margin-left:2%; color:#484848">8.本合同使用范围：<s:if test="#request.pr.useType.equals('1')">  仅限在本单店有效。</s:if>
			<s:else>在连锁门店通用，通用门店包括：</s:else><br></b>
			
		<b style="margin-left:2%; color:#484848">9.乙方在非法定节假日暂停营业超过24小时或甲方按照约定办理暂停服务手续的，有效期限相应顺延。<br></b>
			
		<b style="margin-left:2%; color:#484848">10.乙方承诺提供的服务内容：<br>
			
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;（1）合同内免费服务项目：<s:if test="#request.pr.freeProjects==null">无</s:if><s:else><s:property value="freeProjects"/></s:else> <br> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;其它：<s:if test="#request.pr.freeProject==null" >无</s:if> <s:else><s:property value="freeProject"/></s:else><br>
			
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;（2）另收费项目：<s:if test="#request.pr.costProjects==null">无</s:if><s:else><s:property value="costProjects"/></s:else><br>  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;其它：<s:if test="#request.pr.costProject">无</s:if> <s:else ><s:property value="costProject"/></s:else>
			 （以店内公示项目和价格为准）。<br></b>
			
		<b style="margin-left:2%; color:#484848">11.乙方向甲方告知调整营业时间、暂停营业等重要事项，应当采用店堂告示及<s:if test="#request.pr.talkFunc.equals('1')">交易平台消息</s:if> <s:elseif test="#request.pr.talkFunc.equals('2')">电子邮件 </s:elseif> <s:elseif test="#request.pr.talkFunc.equals('3')">短信</s:elseif> <s:else>传真</s:else> 方式。<br></b>
			
		<b style="margin-left:2%; color:#484848">12.其它约定：
			<s:property value="#request.pr.remark"/><br></b>
			
		<b style="margin-left:2%; color:#484848">13.双方均同意上述条款及《健身合同通用条款》，三方通过本交易平台完成签约手续，甲方支付合同款后本合同正式生效。<br></b>
			
		<b style="margin-left:2%; color:#484848">14.丙方的服务系统仅对甲方的考勤及乙方是否正常营业进行监控，协助双方办理合同款项结算。甲乙双方在合同履行过程中产生的服务质量、安全责任事故、人身、财产损害事故或任何其它合同履行过程中产生的纠纷，丙方不承担任何包括但不限于补偿、赔偿、担保等法律责任。<br></b>
			
		<b style="margin-left:2%; color:#484848">15.丙方完成本合同的结算并向乙方支付甲方的应付款时，按应付款金额的8%向乙方收取交易服务费。（交易服务费下限为1元/笔）<br></b>
			
		<b style="margin-left:2%; color:#484848">16.丙方完成本合同的结算并向甲方办理退款手续时，按应退款金额的3%向甲方收取交易手续费。（交易手续费下限为1元/笔）<br></b>
			
		<b style="margin-left:2%; color:#484848">17.甲乙双方如产生争议，应向丙方提供证据，由丙方主持双方调解。调解达成协议的，丙方协助办理结算付/退款事宜；调解无法达成协议，丙方对托管资金进行冻结，双方均有权提起诉讼解决，冻结资金由司法机关处理。<br /></b>
       </s:elseif> 
       
       <!-- 圈存（储值）合约 -->
       <s:elseif test="#request.pr.proType==3">
        <b style="margin-left:2%; color:#484848">1.甲方自
			<s:property value="#request.pr.startTime"/>
			购买乙方的健身储值卡，成为乙方的会员。
			<br></b>
			
		<b style="margin-left:2%; color:#484848">2.<s:if test="#request.pr.isReg==1">该会员资格记名，仅限甲方本人使用；甲方将会员资格转让他人时，<s:if test="#request.pr.assignType==1">需要支付转让手续费<s:property value="#request.pr.assignCost"/> 元。</s:if> <s:else>不需要支付转让手续费</s:else></s:if>
			
			<s:else>该会员资格不记名，可由任何符合条件的持卡人使用。</s:else><br></b>
		<b style="margin-left:2%; color:#484848">3.该会员资格的有效期限为 <s:property value="#request.pr.wellNum"/>
             月；有效期限届满后，卡内仍有剩余金额的，乙方应当提供展期，展期时间由甲乙双方协定。<br></b>
		<b style="margin-left:2%; color:#484848">4.甲方应向乙方支付合同款共计<s:property value="#request.pr.cost"/> 
			 元。双方同意将该合同款交付给丙方托管，并由丙方每月 
			<s:property value="#request.pr.reportDay"/>
			 日进行上一个月甲乙双方应付费用的结算。<br></b>
			
		<b style="margin-left:2%; color:#484848">5.甲方因自身原因提前终止合同，应向乙方支付违约金 <s:property value="#request.pr.breachCost"/>
			元。乙方收到违约金后向甲方退还甲方未消费的金额。<br></b>
			
		<b style="margin-left:2%; color:#484848">6.乙方因自身原因提前终止合同或服务，丙方一周内组织双方确认该事实后，协助办理结算，将未履行的合同款退还甲方。<br></b>
		<b style="margin-left:2%; color:#484848">7.使用程序：甲方在乙方进行各项消费活动，结帐时由甲、乙双方通过丙方平台进行消费金额确认。<br></b>
			
		<b style="margin-left:2%; color:#484848">8.本合同<s:if test="#request.pr.promotionType.equals('1')">可以</s:if><s:else>不能 </s:else>与乙方其他优惠促销活动同时使用。<br></b>
			
		<b style="margin-left:2%; color:#484848">9.本合同使用范围：<s:if test="#request.pr.useType.equals('1')">  仅限在本单店有效。</s:if>
			<s:else>在连锁门店通用，通用门店包括：</s:else><br></b>
			
		<b style="margin-left:2%; color:#484848">10.乙方在非法定节假日暂停营业超过24小时或甲方按照约定办理暂停服务手续的，有效期限相应顺延。<br></b>
			
		<b style="margin-left:2%; color:#484848">11.乙方承诺提供的服务内容：<br>
			
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;（1）合同内免费服务项目：<s:if test="#request.pr.freeProjects==null">无</s:if><s:else><s:property value="freeProjects"/></s:else> <br> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;其它：<s:if test="#request.pr.freeProject==null" >无</s:if> <s:else><s:property value="freeProject"/></s:else><br>
			
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;（2）另收费项目：<s:if test="#request.pr.costProjects==null">无</s:if><s:else><s:property value="costProjects"/></s:else><br>  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;其它：<s:if test="#request.pr.costProject">无</s:if> <s:else ><s:property value="costProject"/></s:else>
			 （以店内公示项目和价格为准）。<br></b>
			
		<b style="margin-left:2%; color:#484848">12.乙方向甲方告知调整营业时间、暂停营业等重要事项，应当采用店堂告示及<s:if test="#request.pr.talkFunc.equals('1')">交易平台消息</s:if> <s:elseif test="#request.pr.talkFunc.equals('2')">电子邮件 </s:elseif> <s:elseif test="#request.pr.talkFunc.equals('3')">短信</s:elseif> <s:else>传真</s:else> 方式。<br></b>
			
		<b style="margin-left:2%; color:#484848">13.其它约定：
			<s:property value="#request.pr.remark"/><br></b>
			
		<b style="margin-left:2%; color:#484848">14.双方均同意上述条款及《健身合同通用条款》，三方通过本交易平台完成签约手续，甲方支付合同款后本合同正式生效。<br></b>
			
		<b style="margin-left:2%; color:#484848">15.丙方的服务系统仅对甲方的考勤及乙方是否正常营业进行监控，协助双方办理合同款项结算。甲乙双方在合同履行过程中产生的服务质量、安全责任事故、人身、财产损害事故或任何其它合同履行过程中产生的纠纷，丙方不承担任何包括但不限于补偿、赔偿、担保等法律责任。<br></b>
			
		<b style="margin-left:2%; color:#484848">16.丙方完成本合同的结算并向乙方支付甲方的应付款时，按应付款金额的8%向乙方收取交易服务费。（交易服务费下限为1元/笔）<br></b>
			
		<b style="margin-left:2%; color:#484848">17.丙方完成本合同的结算并向甲方办理退款手续时，按应退款金额的3%向甲方收取交易手续费。（交易手续费下限为1元/笔）<br></b>
			
		<b style="margin-left:2%; color:#484848">18.甲乙双方如产生争议，应向丙方提供证据，由丙方主持双方调解。调解达成协议的，丙方协助办理结算付/退款事宜；调解无法达成协议，丙方对托管资金进行冻结，双方均有权提起诉讼解决，冻结资金由司法机关处理。<br /></b>
       </s:elseif> 
       
       <!-- 对赌（次数）合约 -->
       <s:elseif test="#request.pr.proType==4">
        <b style="margin-left:2%; color:#484848">1.甲方自
			<s:property value="#request.pr.startTime"/>
			起成为乙方的会员，会员资格的有效期限为 <s:property value="#request.pr.wellNum"/>
			 月。截止日期为：
			<s:property value="#request.pr.endTime"/>
			<br></b>
			
		<b style="margin-left:2%; color:#484848">2.双方同意根据甲方在合同有效期内实际到乙方俱乐部健身的总次数收取费用。健身次数小于等于6次，收费为0.3元；健身次数大于6次小于12次，收费为0.2元； 健身次数大于等于12次，收费为0.1元。<br></b>
		<b style="margin-left:2%; color:#484848">3.甲方因自身原因提前终止合同，应向乙方支付违约金 <s:property value="#request.pr.breachCost"/>
			元。同时根据按本协议第二条最低健身次数约定的单价向乙方支付健身费用。<br></b>
		<b style="margin-left:2%; color:#484848">4.甲方同意交纳本合同保证金 <s:property value="#request.pr.promiseCost"/> 元 。保证金由甲方交付给丙方。合同期满一周内，丙方根据合同履约记录与甲、乙双方进行合同结算。<br></b>
		<b style="margin-left:2%; color:#484848">5.如乙方因自身原因提前终止合同或服务，丙方确认该事实后的一周内进行结算。将保证金退还给甲方。<br></b>
			
		<b style="margin-left:2%; color:#484848">6.本合同仅限甲方本人使用，不得转让他人。<br></b>
		<b style="margin-left:2%; color:#484848">7.本合同<s:if test="#request.pr.promotionType.equals('1')">可以</s:if><s:else>不能 </s:else>与乙方其他优惠促销活动同时使用。<br></b>
		<b style="margin-left:2%; color:#484848">8.乙方在非法定节假日暂停营业超过24小时或甲方按照约定办理暂停服务手续的，有效期限相应顺延。<br></b>
			
		<b style="margin-left:2%; color:#484848">9.乙方承诺提供的服务内容：<br>
			
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;（1）合同内免费服务项目：<s:if test="#request.pr.freeProjects==null">无</s:if><s:else><s:property value="freeProjects"/></s:else> <br> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;其它：<s:if test="#request.pr.freeProject==null" >无</s:if> <s:else><s:property value="freeProject"/></s:else><br>
			
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;（2）另收费项目：<s:if test="#request.pr.costProjects==null">无</s:if><s:else><s:property value="costProjects"/></s:else><br>  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;其它：<s:if test="#request.pr.costProject">无</s:if> <s:else ><s:property value="costProject"/></s:else>
			 （以店内公示项目和价格为准）。<br></b>
			
		<b style="margin-left:2%; color:#484848">10.乙方向甲方告知调整营业时间、暂停营业等重要事项，应当采用店堂告示及<s:if test="#request.pr.talkFunc.equals('1')">交易平台消息</s:if> <s:elseif test="#request.pr.talkFunc.equals('2')">电子邮件 </s:elseif> <s:elseif test="#request.pr.talkFunc.equals('3')">短信</s:elseif> <s:else>传真</s:else> 方式。<br></b>
			
		<b style="margin-left:2%; color:#484848">11.其它约定：
			<s:if test="#request.pr.remark==null">无</s:if><s:else><s:property value="#request.pr.remark"/></s:else><br></b>
			
		<b style="margin-left:2%; color:#484848">12.双方均同意上述条款及《健身合同通用条款》，三方通过本交易平台完成签约手续，甲方支付合同款后本合同正式生效。<br></b>
			
		<b style="margin-left:2%; color:#484848">13.丙方的服务系统仅对甲方的考勤及乙方是否正常营业进行监控，协助双方办理合同款项结算。甲乙双方在合同履行过程中产生的服务质量、安全责任事故、人身、财产损害事故或任何其它合同履行过程中产生的纠纷，丙方不承担任何包括但不限于补偿、赔偿、担保等法律责任。<br></b>
			
		<b style="margin-left:2%; color:#484848">14.丙方完成本合同的结算并向乙方支付甲方的应付款时，按应付款金额的8%向乙方收取交易服务费。（交易服务费下限为1元/笔）<br></b>
			
		<b style="margin-left:2%; color:#484848">15.丙方完成本合同的结算并向甲方办理退款手续时，按应退款金额的3%向甲方收取交易手续费。（交易手续费下限为1元/笔）<br></b>
			
		<b style="margin-left:2%; color:#484848">16.甲乙双方如产生争议，应向丙方提供证据，由丙方主持双方调解。调解达成协议的，丙方协助办理结算付/退款事宜；调解无法达成协议，丙方对托管资金进行冻结，双方均有权提起诉讼解决，冻结资金由司法机关处理。<br /></b>
       </s:elseif> 
       
       
       <!-- 预付卡合约 -->
       <s:elseif test="#request.pr.proType==6">
        <b style="margin-left:2%; color:#484848">1.甲方通过购买乙方的<s:if test="#request.pr.cardType.equals('1')"><s:property value="#request.pr.wellNum"/>月时效卡</s:if>
        <s:else><s:property value="#request.pr.num"/>次计次卡，有效期限<s:property value="#request.pr.wellNum"/>月</s:else>			
			</b>
		<b style="margin-left:2%; color:#484848">2.<s:if test="#request.pr.isReg==1">该会员资格记名，仅限甲方本人使用；甲方将会员资格转让他人时，<s:if test="#request.pr.assignType==1">需要支付转让手续费<s:property value="#request.pr.assignCost"/> 元。</s:if> <s:else>不需要支付转让手续费</s:else></s:if>
			
			<s:else>该会员资格不记名，可由任何符合条件的持卡人使用。</s:else><br></b>
		<b style="margin-left:2%; color:#484848">3.甲方向丙方支付购卡款计<s:property value="#request.pr.cost"/> 元。丙方于系统自动计算将该合同款全部转付给乙方。<br></b>
		<b style="margin-left:2%; color:#484848">4.甲方因自身原因提前终止合同，应向乙方支付违约金 <s:property value="#request.pr.breachCost"/>元,乙方收到违约金后向甲方退还甲方未消费的金额。<br></b>
		<b style="margin-left:2%; color:#484848">5.乙方因自身原因提前终止合同或服务，应将未履行的合同款退还甲方。<br></b>
			
		<b style="margin-left:2%; color:#484848">6.计次卡的有效期限届满后，卡内仍有剩余次数的，乙方应当提供一次展期，展期时间计算方式：剩余次数乘以 3
         天（3-5天的合理健身频率）=展期天数。<br></b>
		<b style="margin-left:2%; color:#484848">7.本合同<s:if test="#request.pr.promotionType.equals('1')">可以</s:if><s:else>不能 </s:else>与乙方其他优惠促销活动同时使用。<br></b>
		<b style="margin-left:2%; color:#484848">8.8.本合同使用范围：<s:if test="#request.pr.useType.equals('1')">  仅限在本单店有效。</s:if>
			<s:else>在连锁门店通用，通用门店包括：</s:else><br></b><br></b>
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
			
		<b style="margin-left:2%; color:#484848">15.丙方完成本合同的结算并向乙方支付甲方的应付款时，按应付款金额的8%向乙方收取交易服务费。（交易服务费下限为1元/笔）<br></b>
       </s:elseif> 
       
    </div>
    </div>
 	<div id="div" style="float: left:;">
    	<a href="https://itunes.apple.com/cn/app/ka-ku-jian-shen/id848860632?mt=8" target="_blank">
    		<img src="images/iphone86.png" class="img-responsive"  style="width: 50%; float: left;">
    	</a>
    	<a href="app/cardcol.apk" target="_blank">
    		<img src="images/android86.png" class="img-responsive" style="width:50%;float: right; ">
        </a>
    </div>
    <script src="js/jquery.min.js"></script>
    <script src="js/bootstrap.min.js"></script>  
</body>
</html>
