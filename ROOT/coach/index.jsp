<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<s:include value="/share/meta.jsp"/>
<meta name="keywords" content="健身教练,私人教练,健身课程,肌肉健美,瘦身减肥,强身健体" />
<meta name="description" content="健身教练有教练的资格证书,教练的专业健身课程,根据不同的健身目的提供不同的健身计划。" />
<title><s:property value="member.name"/>-教练首页</title>
<link href="css/coach-homepage.css" rel="stylesheet" type="text/css" />
<link type="text/css" rel="stylesheet" href="css/coachstyle.css"/>
<link type="text/css" rel="stylesheet" href="css/pulicstyle.css"/>
<link type="text/css" rel="stylesheet" href="script/jRating/jRating.jquery.css"/>
<script type="text/javascript" src="script/jRating/jRating.jquery.js"></script>
<script type="text/javascript" src="http://api.map.baidu.com/api?v=1.3"></script>
<style type="text/css">
#allmap {width: 200px;height:140px;overflow: hidden;margin:0;}
</style>
<script type="text/javascript">
$(document).ready(function(){ 
	$('#signDate').datepicker({changeYear: true});
	$('#orderStartTime').datepicker({changeYear: true});
	$('#doneDate').datepicker({changeYear: true});
    $(".avgGrade").jRating({
    	type:'small',
  	  	length : 10,
  	  	decimalLength : 1,
  	  	rateMax:100,
  		isDisabled:true
    });
    $(".grade").jRating({
    	type:'small',
  	  	length : 10,
  	  	decimalLength : 1,
  	  	rateMax:100
    });
    if("<s:property value="member.id"/>" != "<s:property value="#session.loginMember.id"/>"){
		$('#recordForm2').find('input,textarea,select').each(function(){
			$(this).attr('disabled', true);
		});
	}
    var marker, circle, radius;
    var nick = '<s:property value="member.name" escape="false"/>';
    var city = '<s:property value="member.city" escape="false"/>';
    if (city == '') city = '武汉';
    var lng = <s:if test="member.longitude == null">0</s:if><s:else><s:property value="member.longitude"/></s:else>,
    	lat = <s:if test="member.latitude == null">0</s:if><s:else><s:property value="member.latitude"/></s:else>;
    var map = new BMap.Map("allmap");
    if (lng > 0 && lat > 0) {
    	var point = new BMap.Point(lng, lat);
    	map.centerAndZoom(point, 15);
    	marker = new BMap.Marker(point);
        map.addOverlay(marker);
        marker.setAnimation(BMAP_ANIMATION_BOUNCE);
        var label = new BMap.Label(nick, {offset:new BMap.Size(20,-10)});
        marker.setLabel(label);
    	var radius = '<s:property value="member.radius" escape="false"/>';
    	circle = new BMap.Circle(point, radius);
    	map.addOverlay(circle);
    } else {
    	map.centerAndZoom(city, 15);	
    }
    map.enableScrollWheelZoom();
});

function saveShop(){
	if($("#orderStartTime").val()){
		$.ajax({type:'post',url:'shop!checkShop.asp',data:$('#productForm').serialize(),
			success:function(msg){
				if(msg == "isShop"){
					alert("购物车中已存在该教练的课程收费，且时间与本课程收费有冲突，请修改课程收费开始时间或先到购物车中处理其他课程收费！");
					return;
				}else if(msg == "isOrder"){
					alert("订单中已存在该教练的课程收费，且时间与本课程收费有冲突，请修改课程收费开始时间或先到订单中处理其他课程收费！");
					return;
				}else if(msg == "ok"){
					$('#productForm').submit();
				}else{
					alert(msg);
					return;
				}
			}
		});
	}else{
		alert("请选择课程收费开始时间！");
		$("#orderStartTime").focus();
		$("#orderStartTime").select();
	}
}
function changeOrderEndTime(){
	var orderStartTime = $('#orderStartTime').val();
	var	ost = new Date(orderStartTime);
	var wellNum = $('#wellNum').val();
	ost.setMonth(ost.getMonth()+parseInt(wellNum));
	var yearStr = ost.getFullYear();
	var monthStr = (ost.getMonth()+1)>9?(ost.getMonth()+1):"0"+(ost.getMonth()+1);
	var dateStr = ost.getDate()>9?ost.getDate():"0"+ost.getDate();
	$("#endTime").html(yearStr+"年"+monthStr+"月"+dateStr+"日");
	$("#orderEndTime").val(yearStr+"-"+monthStr+"-"+dateStr); 
	$("#shopReportDay").val(dateStr);
	$("#shopReportDaySpan").html(dateStr);
}

function onSignIn(){
	if(!$("input[name='username']").val()){
		alert("会员账号不能为空，请确认！");
		$("input[name='username']").select();
		$("input[name='username']").focus();
		return;
	}
	if(!$("input[name='password']").val()){
		alert("会员密码不能为空，请确认！");
		$("input[name='password']").select();
		$("input[name='password']").focus();
		return;
	}
	if(!$("input[name='signIn.signDate']").val()){
		alert("签到日期不能为空，请确认！");
		$("input[name='signIn.signDate']").select();
		$("input[name='signIn.signDate']").focus();
		return;
	}
	if($("input[name='username']").val() == "<s:property value="member.email"/>"){
		alert("本功能仅供您的俱乐部、教练或者会员使用！");
		return;
	}
	$("#signDateDo").val($("input[name='signIn.signDate']").val());
	$('input[name="password"]').val(md5($('input[name="password"]').val()));
	$.ajax({type:'post',url: 'signin!checkUserAndLoadOrder.asp',data: $('#signInForm').serialize(), 
		success: function(msg){
			if(msg == "errorUserPassword") {
				alert("会员帐号或会员密码错误！");
				$("input[name='username']").select();
				$("input[name='username']").focus();
			}else if(msg == "noOrder") {
				alert("您没有购买该商户的健身卡，不能进行签单操作！");
				$("input[name='username']").select();
				$("input[name='username']").focus();
			}else{
				$('#dialog').dialog('close');
				$("select[name='signIn.orderId']").empty();
				//$("<option value=''></option>").appendTo($("select[name='signIn.orderId']"));
				var orderArr = $.parseJSON(msg);
				var orderNickNick = "";
				for(var i = 0;i < orderArr.length;i++){
					$("#memberSignId").val(orderArr[i].fromId);
					if(!orderArr[i].id){
						$("<option value=''></option>").appendTo($("select[name='signIn.orderId']"));
					}else{
						$("<option value='"+orderArr[i].id+"'>"+orderArr[i].no+"【"+orderArr[i].orderType+"】</option>").appendTo($("select[name='signIn.orderId']"));
					}
					orderNickNick += orderArr[i].payNo + ":";
				}
				$("#orderNickNick").val(orderNickNick.substring(0,orderNickNick.length-1));
				initOrderNo();
				$('#dialogSignIn').dialog('open');
			}
		}
	});
}
function initOrderNo(){
	var oStr = $("select[name='signIn.orderId'] option:selected").text();
	$("input[name='signIn.orderNo']").val(oStr.substring(0,oStr.indexOf("【")));
	var orderNickNick = $("#orderNickNick").val();
	var orderNickNickArr = orderNickNick.split(":");
	$("input[name='signIn.orderNick']").val(orderNickNickArr[$("select[name='signIn.orderId'] option:selected").index()]);
}
function saveRecord(){
	if(!$("input[name='record.doneDate']").val() ){
		alert("完成日期必须填写后才能保存！");
		return;
	}
	var parms = $('#recordForm2').serialize() + '&syncWeibo=' + ($('#syncWeibo').attr('checked') === 'checked' ? 1 : 0);
	$.ajax({type:'post',url:'member!saveRecord.asp',data: parms,
		success: function(msg){
			var _json = $.parseJSON(msg);
			var result = _json.msg;
			if(result == "ok"){
				alert("当前最新身体数据已成功保存,并已同步微博！");
			}else if(result == "okRecord"){
				alert("当前最新身体数据已成功保存！");
			}else if(result == "noSinaId"){
				alert("当前最新身体数据已成功保存,同步微博失败：您还未关联微博账户！");
			}else{
				alert(msg);
			}
		}
	});
}
function onSignInDo(){
	$.ajax({type:'post',url: 'signin!save.asp',data: $('#signInFormDo').serialize(), 
		success: function(msg){
			if(msg == "ok"){
				alert('签到成功！');
				onClosecDo();
			}else{
				alert(msg);
			}
		}
	});
}
$(function() {
		$( "#dialog" ).dialog({
			autoOpen: false,
			show: "blind",
			hide: "explode",
			resizable: false
		});
		$( "#dialogProduct" ).dialog({
			width: 460,	
			autoOpen: false,
			show: "blind",
			hide: "explode",
			modal: true,
			resizable: false
		});
		$( "#opener" ).click(function() {
			var loginMember = "<s:property value="#session.loginMember.id"/>";
			var member = "<s:property value="member.id"/>"
			if(member == loginMember){
				var	date = new Date();
				var yearStr = date.getFullYear();
				var monthStr = (date.getMonth()+1)>9?(date.getMonth()+1):"0"+(date.getMonth()+1);
				var dateStr = date.getDate()>9?date.getDate():"0"+date.getDate();
				$("input[name='signIn.signDate']").val(yearStr+"-"+monthStr+"-"+dateStr);
				$("input[name='username']").val("");
				$("input[name='password']").val("");
				$( "#dialog" ).dialog( "open" );
				return false;
			}else{
				alert("该教练登录后才能签到！");
				return;
			}
		});
		$( "#dialogMsg" ).dialog({
			autoOpen: false,
			show: "blind",
			hide: "explode",
			resizable: false,
			
		});
		$( "#dialogCourseInfo" ).dialog({
			autoOpen: false,
			show: "blind",
			hide: "explode",
			resizable: false
		});
		$( "#dialogSignIn" ).dialog({
			autoOpen: false,
			show: "blind",
			hide: "explode",
			modal: true,
			resizable: false
		});
	});
	 
//Dialog
function onClosec(){
	$('#dialog').dialog('close');
}
function onClosecDo(){
	$('#dialogSignIn').dialog('close');
}
function sendMsg(){
	if(!"<s:property value="#session.loginMember.id"/>"){
		//alert("未登录用户不能发送消息！");
		openLogin();
		return;
	}
	if("<s:property value="member.id"/>" == "<s:property value="#session.loginMember.id"/>"){
		alert("自己不能向自己发送信息！");
	}else{
		$("textarea[name='msg.content']").val("");
		$( "#dialogMsg" ).dialog( "open" );
	}
}
function onSendMsg(id,memberId){
	if(id){
		if(!"<s:property value="#session.loginMember.id"/>"){
			//alert("未登录用户不能申请加入俱乐部！");
			openLogin();
			return;
		}
		if(memberId == "<s:property value="#session.loginMember.id"/>"){
			alert("自己不能申请加入自己！");
			return;
		}
		$("textarea[name='msg.content']").val("请求加入俱乐部");
		$("input[name='msg.type']").val("1");
	}else{
		if(!$("textarea[name='msg.content']").val()){
			alert("消息内容不能为空，请确认！");
			$("textarea[name='msg.content']").focus();
			$("textarea[name='msg.content']").selet();
			return;
		}
		$("input[name='msg.type']").val("2");
	}
	$.ajax({type:'post',url:'message!save.asp',data:$('#msgForm').serialize(),
		success:function(msg){
			if(msg == "ok"){
				if(id){
					alert("当前申请已成功发送,请耐心等待俱乐部审核！");
				}else{
					alert("当前信息已成功发送！");
					onCloseMsg();
				}
			}else{
				alert(msg);
			}
		}
	});
}
function onCloseMsg(){
	$('#dialogMsg').dialog('close');
}	
function showCourseInfo(i) {
	$('div[id^="courseInfo"]').hide();
	$('#courseInfo' + i).show();
}
function requestJoin() {
	var fId = "<s:property value="#session.loginMember.id"/>";
	if (fId == "") {
		openLogin();
		return;
	}
	var tId = "<s:property value="member.id"/>";
	if (fId == tId) {
		alert('您不能加入到自己的教练中！');
		return;
	}
	$("textarea[name='msg.content']").val("请求加入教练");
	$("input[name='msg.type']").val("1");
	$.ajax({type:'post',url:'message!save.asp',data:$('#msgForm').serialize(),
		success:function(msg){
			if(msg == "ok"){
				alert("当前申请已成功发送,请耐心等待教练审核！");
			}else if(msg == "isMsg"){
				alert("您已申请加入过该教练,请耐心等待教练审核！");
				return;
			}else if(msg == "isFriend"){
				alert("您已加入该教练,请勿重复执行该操作！");
				return;
			}else if(msg == "joined"){
				alert("您已加入其他教练,请先解除再申请！");
				return;
			}else{
				alert(msg);
			}
		}
	});
}
function preShop(productId) {
	url = "clublist!shoGo.asp?productId="+productId;
	$('#queryForm').attr('action', url);
	$('#queryForm').submit();
}

function queryPlanInfo(planId,planType) {
	if(planType == '3'){
		$('#queryForm').attr("action", 'plan.asp?pid='+planId);
	}else{
		$('#queryForm').attr("action", 'goods.asp?goodsId='+planId);
	}
	$('#queryForm').submit();
}
</script>
</head>
<body>
<input type="hidden" name="orderNickNick" id="orderNickNick"/>
<s:form id ="queryForm" name="queryForm" method="post" action="coach.asp" theme="simple">
<s:hidden name="member.id" id="memberId"/>
<s:hidden name="shop.id" id="shopId"/>
<s:hidden name="product.id" id="productId"/>
</s:form>
	<s:if test="#session.loginMember.id == member.id">
	<s:include value="/share/header.jsp"/>
	</s:if>
	<s:else>
	<s:include value="/share/window-header.jsp"/>
	</s:else>
		<style>
	
	.butt {
	cursor: pointer;
	width: 64px;
	height: 28px;
	text-align: center;
	line-height: 29px;
	text-indent: 10px;
	margin-right: 10px;
	letter-spacing: 8px;
	background: url(images/anjiuall.jpg);
	background-position: 0px -48px;
}
	
			</style>
	<div id="content">
	
		<div id="left">
			<div id="left-1">
				<h1>用户资料</h1>
				<div>
					<dl>
						<dt styel="height:180px;">
						   	<s:if test="#session.loginMember.id == member.id">
							<a href="picture.asp"><s:if test="member.image == '' || member.image == null"><img src="images/userpho.jpg" alt="健身教练"/></s:if><s:else><img src="picture/<s:property value="member.image"/>" alt="健身教练"/></s:else></a>
							</s:if>
							<s:else>
							<s:if test="member.image == '' || member.image == null"><img src="images/userpho.jpg" alt="健身教练"/></s:if><s:else><img src="picture/<s:property value="member.image"/>" alt="健身教练"/></s:else>
							</s:else>
						</dt>
						<dd><s:property value="member.name"/></dd>
					</dl>
                                        <p class="havep">已有<span class="numred"><s:property value="member.countEmp"/></span>人进行了评价<span class="red3"><s:property value="member.avgGrade"/></span><span>分</span></p>
					<s:if test="#session.loginMember.id != member.id">
					<s:if test="#session.loginMember.role==\"M\"">
						<input type="button" name="Submit" value="申请加入" class="inpbut" onclick="requestJoin()"/>
					</s:if>
					<input type="button" name="Submit" value="发消息" class="inpbut" onclick="sendMsg();"/>
					</s:if>
				</div>
			</div>
                        
			
                         <div id="left-3">
                            <h1>教练信息</h1>
		            <div class="coachleft">
                                 <p><b>教练类型:</b></p>
                                 <p class="siren"><s:if test="member.style.indexOf('A')>=0">私人教练</s:if><s:if test="member.style.indexOf('B')>=0">团体教练</s:if></p>
                                 <p><b>服务方式:</b></p>
                                 <p class="siren"><s:if test="member.mode.indexOf('A')>=0">网络线上指导 </s:if><s:if test="member.mode.indexOf('B')>=0">传统线下授课</s:if></p>
                                 <p><b>联系电话:</b><s:property value="member.tell"/></p>
                                 <p><b>线下服务范围</b></p>
                                 <div id="allmap"></div>
                            </div>
                        </div>
                        <%-- <div id="left-5">
                            <h1>专业证书</h1>
                           <div class="leftzhengshu">
                           <s:iterator value="#request.certs">
                           		<div class="leftpic1">
                                	<img style="width:90px;height:80px;" src="picture/<s:property value="fileName"/>"/>
                            		<s:if test="isValid==\"1\""><img src="images/authentic.png"/></s:if>
                            	</div>
                            </s:iterator>
                          </div>
                        </div> --%>
                        
			<div id="left-2">
				<h1>最近访客</h1>
				<div>
					<s:iterator value="member.visitors">
						<dl>
							<dt>
								<a title="<s:property value="friend.nick"/>" href="javascript:goMemberHome('<s:property value="friend.id"/>','<s:property value="friend.role"/>');"><s:if test="friend.image == '' || friend.image == null"><img src="images/userpho.jpg"/></s:if><s:else><img src="picture/<s:property value="friend.image"/>"/></s:else></a>
							</dt>
							<dd><s:property value="friend.name"/></dd>
						</dl>
					</s:iterator>
				</div>
			</div>
		</div>
		<div id="center">
			<div id="center-2">
				<h1>教练介绍</h1>
	
				<s:if test="member.description == null || member.description == ''">
					<div class="introd">
						<s:if test="#session.loginMember.id == member.id">
						还没有对你自己进行介绍哦，<a href="account.asp" id="colotoa">点击赶紧去完善教练你的资料吧！</a>更多的介绍自己，可以给自己留下更多的会员，完善的信息可以让更多的人了解你的教学风格
						</s:if>
						<s:else>
						该用户还没有完善自己的资料
						</s:else>
					</div>
				</s:if>
				<s:else>
					<div class="introdb">
						<s:property value="member.description" escape="false"/>
					</div>
				</s:else>
		
			</div>
			
			<div id="left-5">
                            <h1>专业证书</h1>
                           <div class="leftzhengshu">
                           <s:iterator value="#request.certs">
                           		<div class="leftpic1">
                                	<img style="width:90px;height:80px;" src="picture/<s:property value="fileName"/>"/>
                            		<s:if test="isValid==\"1\""><img src="images/authentic.png"/></s:if>
                            	</div>
                            </s:iterator>
                          </div>
                        </div>
			
			<div id="center-3">
				<h1>我发布的健身计划</h1>
				<div class="mywangyan">
				<s:if test="#request.plans.size > 0">
				<s:iterator value="#request.plans"> 
					<div class="myautor">
						<div class="pingzuo">
							<div class="pzpic">
								<img src="picture/<s:property value="image1" />"/>
							</div>
							<div class="planpic">
								<p class="planday"><s:property value="planName" /></p>
								<p class="planpric">￥<s:property value="price" /></p>
								<p class="shopping">
									<a href="javascript:queryPlanInfo('<s:property value="planId"/>','<s:property value="planType"/>');"><span>购买</span></a>
								</p>
							</div>
						</div>
					</div>
					</s:iterator>
					</s:if><s:else>该用户还没有发布健身计划</s:else>
				</div>
			</div>
			
			<%-- <s:form name="recordForm2" id="recordForm2" action="member!saveRecord.asp" theme="simple">
				<div id="center-3" style="height: auto">
					<h1>健身记录</h1>
					<div class="memdiv" style="padding-top: 10px; height: 220px;">
						<div style="padding-top: 10px;">
				           <div>
							<p style="padding-left:15px; padding-top: 0; float:left;width:250px;">
								运动日期：<s:textfield id="doneDate" name="record.doneDate"  style=" border:1px solid #ccc;width:100px;height:23px;line-height:23px;" />
										<img src="images/sar-1_03.jpg" align="absbottom" />
							</p>
							<p style="text-align: left;float:left;width:220px;">
								运动时长：<s:textfield name="record.times" maxlength="10" id="recordForm2_record_weight" style=" border:1px solid #ccc;width:100px;height:23px;line-height:23px;"/> 分钟
							</p>
				           <div  style="clear:both;"></div>
					</div>
								<div style="padding-top:10px;">
									<p  style="padding-left: 15px;float:left;width:250px;">
								有氧运动：<s:select name="record.action" list="#request.yyxms" listKey="name" listValue="name"  style=" border:1px solid #ccc;width:100px;height:23px;line-height:23px;"/>
							</p>
								
							<p style="float:left;width:210px;">
								运动量：<s:textfield name="record.actionQuan" maxlength="10" id="recordForm2_record_weight" style=" border:1px solid #ccc;width:100px;height:23px;line-height:23px;"/> 千米
							</p>      <div  style="clear:both;"></div>
					</div>							
							<div style="padding-top: 20px;">
									<span class="p1" style="padding-top:18px;padding-left: 15px;">
								<b style="font-size:14px;">填写训练体会:</b>
							</span>
								
							<div  style=" padding-top:10px;  padding-left:360px;">
								可以输入<span>200</span>个字
							</div>
								</div>
							<p  style="padding-left: 15px;padding-top:10px;"><s:textarea name="record.memo" cols="" rows="" id="recordMemo"  style="border:1px solid #ccc;width:470px; height:88px;"/></p>
						</div>
						<div style=" padding-top:15px; height: 80px;">
							<p  style="padding-left:15px;font-size:14px;">
								<b>最新身体数据:</b>
							</p>
								
							<Div style=" padding-top:15px;">
								
								
							<p style="padding-left:20px;float:left; width:250px;">
								<a title="除去衣服后的重量">体重：</a><s:textfield name="record.weight" maxlength="10" id="recordForm2_record_weight"  style="border:1px solid #ccc;width:100px; height:23px;"/> Kg
							</p>
							<p  style="text-align: left;float:left;">
								<a title="从头顶点至地面的垂距。">身高：</a><s:textfield name="record.height" maxlength="10" id="recordForm2_record_height" style="border:1px solid #ccc;width:100px;; height:23px;"/> cm
							</p>
								
							<p  style="padding-left: 20px;float:left;padding-top:10px; width:250px;">
								<a title="直立，放松，皮尺放在腰最细的部分水平绕体一周测量。">腰围：</a><s:textfield name="record.waist" maxlength="10" id="recordForm2_record_waist" style="border:1px solid #ccc;width:100px; height:23px;"/> cm
							</p>
							<p style="text-align: center;float:left;padding-top:10px; ">
								<a title="两腿直立、双臂下垂，皮尺水平放在髋部左右大转子骨的尖端水平绕体一周测量.">臀围：</a><s:textfield name="record.hip" maxlength="10" id="recordForm2_record_hip" style="border:1px solid #ccc;width:100px; height:23px;"/> cm
							</p>
								
								
								
								<div  style="clear:both;"></div>
								</div>	
						</div>
					</div>
					<div style="clear: both; padding: 0; height: 1px;"></div>
					<p class="p1" style="padding-left:15px; padding-top:25px;">
						<b>记录保存到以下健身挑战:</b>
					</p>
					<p class="c_check" style="line-height: 30px; float: left; padding: 10px 0 0 40px;">
						<s:checkboxlist list="#request.buyActives" listKey="id" listValue="name" name="ids"/>
					</p>
					<div style="clear: both; height: 1px; padding: 0;"></div>
					<dt class="p5" style="padding-bottom: 10px; padding-left: 75px;">
						<p style="padding-top:25px; padding-left:80px;">
							<input type="button" value="保存" class="butt" onclick="saveRecord();" />
							<input type="checkbox" id="syncWeibo" />
							同时发布到微博
						</p>
					</dt>
					<div id="divemotion" class="emtion" style="display: none;">
						<div class="xiaosj"></div>
						<div class="emtion-s"></div>
					</div>
				</div>
			</s:form> --%>
		</div>
		<div id="right">
			<div id="right-1">
				<a href="#" id="opener">
				  <img src="images/memberd.png" width="210" height="51" />
				</a>
			</div>
			<div id="right-2">
				<h1>收费标准<span class="standa"><a id="coloa" href="javascript:goProductHome('<s:property value="member.id"/>');">更多</a></span></h1>
				<s:if test="member.products.size > 0">
				<dl>
					<s:iterator value="member.products" status="pstat">
					<s:if test="#pstat.index<5">
						<dt <s:if test="#pstat.index%2==0">id="colorjla"</s:if>><s:property value="name"/></dt>
						<dd <s:if test="#pstat.index%2==0">id="colorjla"</s:if>>
							<a href="javascript:preShop('<s:property value="id"/>');" id="colotoa">购买</a>
						</dd>
					</s:if>
					</s:iterator>
				</dl>
				</s:if>
				<s:else>
				<div class="standb">
					<s:if test="#session.loginMember.id == member.id">
					还没有添加你的特色健身套餐哦，<a id="colotoa" href="product.asp">点击填写属于你的套餐计划</a>，健身套餐不仅是让会员对俱乐部的特色健身有一定的了解，而却还是获取更多利益的最佳渠道，不要错过！！
					</s:if>
					<s:else>
					该用户还没有添加自己的特色健身套餐
					</s:else> 
				</div>
				</s:else>
			</div>
                        <div id="right-4">
                             <h1>健身挑战<a href="">more</a></h1>
                             <p  style="margin: 10px 5px  10px 5px;line-height: 18px;text-indent: 2em;color: #454142;">
                             <s:if test="(#request.actives.size()>0)">
									<s:iterator value="#request.actives">
										<a href="activewindow.asp?id=<s:property value="id"/>">
										<div class="chanllge">
										<div class="chancont">
										<p class="chantitle"><s:property value="name"/></p>
										</div>
										 <div class="chancont" style="margin-left:70px">
		                                     <p style="width:120px;overflow:hidden;margin-top:10px;"><s:if test="category==\"A\""><s:property value="days"/>天内体重增加<s:property value="value"/>KG</s:if>
												<s:elseif test="category==\"B\""><s:property value="days"/>天内体重减少<s:property value="value"/>KG</s:elseif>
												<s:elseif test="category==\"C\""><s:property value="days"/>天内体重保持在<s:property value="value"/>%左右</s:elseif>
												<s:elseif test="category==\"D\""><s:property value="days"/>天内运动<s:property value="value"/>次</s:elseif>
												<s:elseif test="category==\"E\""><s:property value="days"/>天内运动<s:property value="value"/>小时</s:elseif>
												<s:elseif test="category==\"F\""><s:property value="days"/>天内每周运动<s:property value="value"/>次</s:elseif>
												<s:elseif test="category==\"G\""><s:property value="days"/>天内<s:if test="action == null"></s:if><s:else><s:property value="action"/></s:else><s:property value="value"/>千米</s:elseif>
												<s:elseif test="category==\"H\""><s:property value="days"/>天内力量运动负荷<s:property value="value"/>KG</s:elseif></p>
		                                     <p style="width:120px;overflow:hidden;text-overflow:ellipsis;white-space:nowrap;"><font color="#fd0202">奖</font>：<s:property value="award"/></p>
		                                     <p style="width:120px;overflow:hidden;text-overflow:ellipsis;white-space:nowrap;"><font color="#10fa04">罚</font>：公益捐款<s:property value="amerce_Money"/>元</p>
		                                  </div>	
                                 <div class="chanpic"><img style="width:60px;height:60px;margin-top:-60px;margin-left:5px;" src="picture/<s:property value="active_image"/>"/></div>
                                 
                              </div>
                              </a>
					</s:iterator>
					</s:if><s:else>你可以发起健身挑战活动，激发会员的健身热情</s:else>
                      	</p>
                     </div>
			<div id="right-3">
				<h1>服务项目</h1>
				<s:if test="member.courses.size > 0">
				<s:iterator value="#request.courses" status="cstat" end="13">
				<div id="right-3-1" class="color1">
                                      <div id="rightmember">
                    <a onmouseover="javascript:showCourseInfo('<s:property value="#cstat.index"/>');" href="javascript:void(null);">
					<dl>
						<dt>
							<img src="<s:if test="image == null">images/fdy1_24.jpg</s:if><s:else>picture/<s:property value="image"/></s:else>" name="img1" width="50" height="50" id="img1" />
						</dt>
						<dd>
							<h3><b><s:property value="name"/></b></h3>
                              <span class="spansevera">已有<s:property value="appraiseNum"/>人评价</span>
							  <span class="spanseverb"><s:property value="avg_grade"/>分</span>
							
						</dd>
					</dl>
					</a>
					<div id="courseInfo<s:property value="#cstat.index"/>" style="display: none;"><s:property value="memo" escape="false"/></div>
                                       </div>
                                        
					<s:if test="#cstat.last"></s:if><s:else><div class="dldiv"></div></s:else><!--设定服务项目后使用-->
				</div>
				</s:iterator>
				</s:if>
				<s:else>
				<div class="spanseverc">
				    请到我的设定中添加服务项目
				</div>
				</s:else>
			</div>
	</div>
	</div>
	<s:include value="/share/footer.jsp"/>
	<div id="dialog" title="会员签到">
	<form id="signInForm" name="signInForm" method="post" action="signin!save.asp">
	<input type="hidden" name="signIn.memberAudit.id" value="<s:property value="member.id"/>"/>
		<p>
			会员帐号：<input type="text" name="username" class="uidpwd" />
		</p>
		<p>
			会员密码：<input type="password" name="password" class="uidpwd" />
		</p>
		<p>
			签到日期：<input type="text" name="signIn.signDate" id="signDate" class="date-1"/>
		</p>
		<p class="pa">
		   <input type="button" onclick="onSignIn();" value="下一步" class="butok"/>
		   <!--  
		   <input type="button" value="取消" onclick="onClosec()" class="butok"/>
		   -->
		</p>
	</form>
	</div>
	<div id="dialogSignIn" title="会员签到">
	<form id="signInFormDo" name="signInFormDo" method="post" action="signin!save.asp">
	<input type="hidden" name="signIn.memberAudit.id" value="<s:property value="member.id"/>"/>
	<input type="hidden" name="signIn.memberSign.id" id="memberSignId"/>
	<input type="hidden" name="signIn.signDate" id="signDateDo"/>
	<input type="hidden" name="signIn.orderNo" id="orderNo"/>
	<input type="hidden" name="signIn.orderNick" id="orderNick"/>
		<p class="select-order">
			签到订单:<select name="signIn.orderId" onchange="initOrderNo();"/><br>
		</p>
		<p class="pa">
		   <input type="button" onclick="onSignInDo();" value="签到" class="butok"/>
		   <input type="button" value="取消" onclick="onClosecDo()" class="butok"/>
		</p>
	</form>
	</div>
	<div id="dialogMsg" title="发送消息">
	<form id="msgForm" name="msgForm" method="post" action="message!save.asp">
		<input type="hidden" name="msg.type" value="2"/>
		<input type="hidden" name="msg.memberTo.id" value="<s:property value="member.id"/>"/>
		<p>
			收件人：<s:property value="member.nick"/>
		</p>
		<p  class="message_content">
			<span>消息内容：</span>
			<span><textarea name="msg.content"  class="send-message" onkeyup="this.value = this.value.substring(0, 140)"></textarea><span>
		</p>
		<p class="pa">
		   <input type="button" value="确定" onclick="onSendMsg();"  class="butok"/>
		   <input type="button" value="取消" onclick="onCloseMsg();"  class="butok"/>
		</p>
	</form>
	</div>
	<div id="dialogCourseInfo" title="课程信息">
		<p>
			课程名称：<span id="courseInfoName"></span>
		</p>
		<p>
			课程类型：<span id="courseInfoType"></span>
		</p>
		<p>
			运动强度：<span id="courseInfoIntensity"></span>
		</p>
		<p>
			课程简介：<span id="courseInfoMemo"></span>
		</p>
		<p class="pa">
		   <input type="button" value="取消" onclick="onCloseCourseInfo()" class="butok"/>
		</p>
	</div>	
	<div id="dialogProduct" title="购买课程收费">
		<s:form name="productForm" id="productForm" action="shop!save.asp" method="post" theme="simple">
		<input type="hidden" name="product.id"/>
		<input type="hidden" name="wellNum" id="wellNum"/>
		<s:hidden name="shop.orderEndTime" id="orderEndTime"/>
		<s:hidden name="shop.reportDay" id="shopReportDay"/>
		<div style="line-height:32px;padding-top:5px; font-size:1.1em">
			服务商：<span id="productMember" style="color:#515151;"></span>
		</div>
		<div style="line-height:32px;font-size:1.1em">
			名称：<span id="productName" style="color:#515151;"></span>
		</div>
		<div style="line-height:32px;font-size:1.1em">
			类型：<span id="productCourseType" style="color:#515151;"></span>
		</div>
		<div style="line-height:32px;font-size:1.1em">
			数量：<span id="productNum" style="color:#515151;"></span>
		</div>
		<div style="line-height:32px;font-size:1.1em"> 
			金额：<span id="productCost" style="color:red;"></span>
		</div>
		<div style="line-height:32px;font-size:1.1em">
			描述：<span id="productRemark" style="color:#515151;line-height:25px;"></span>
		</div>
		<div style="line-height:32px;font-size:1.1em">
			开始日期：<input style="border:1px solid #cccccc;line-height:23px; height:23px;" type="text" name="shop.orderStartTime" id="orderStartTime" class="text1" value="<s:date name="shop.orderStartTime" format="yyyy-MM-dd"/>" onchange="changeOrderEndTime();"/>
		</div>
		<div style="line-height:32px;font-size:1.1em">
			结束日期：<span id="endTime"><s:date name="shop.orderEndTime" format="yyyy-MM-dd"/></span>
		</div>
		<div style="line-height:32px;" style="color:#515151;font-size:1.1em">
			每月： <span id="shopReportDaySpan"><s:property value="shop.reportDay"/></span>号结算
		</div>
		<div class="pa">
			<input type="button" name="button" class="butok" value="购买" onclick="saveShop();"/>
			<input type="button" name="button" class="butok" value="取消" onclick="onCloseProduct();"/>
		</div>
		</s:form>
	</div>	 
</body>
</html>