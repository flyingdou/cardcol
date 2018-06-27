<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<s:include value="/share/meta.jsp"/>
<title><s:property value="member.name"/>首页</title>
<meta name="keywords" content="健身俱乐部,健身房,私人教练,肌肉健美,瘦身减肥,强身健体,健身项目" />
<meta name="description" content="健身俱乐部也称为健身房，有各种健身器材供健身者健身，提供各种健身课程和项目。" />
<link href="css/club-homepage.css" rel="stylesheet" type="text/css" />
<link href="css/club-center.css" rel="stylesheet" type="text/css" />
<link href="css/club-member.css" rel="stylesheet" type="text/css" />
<link href="css/pulicstyle.css" rel="stylesheet" type="text/css" />
<link type="text/css" rel="stylesheet" href="script/jRating/jRating.jquery.css"/>
<script type="text/javascript" src="script/jRating/jRating.jquery.js"></script>
<script charset="utf-8" src="http://map.qq.com/api/js?v=2.exp"></script>
<script type="text/javascript" src="script/course.js"></script>
<script type="text/javascript">
$(document).ready(function(){ 
	var wenzi=document.getElementById('rightmember');
	var divp=document.getElementById('coloa');
  if(wenzi != null){
	  wenzi.onmouseover=function(){
	        $(this).show();
	   }
  }
	var $tab_lit = $('.centernav li');
	$tab_lit.click(function(){
		var index = $tab_lit.index(this);
		if(index == 0){
			$('#courseUl').attr('class', 'centernav');
		}else if(index == 1){
			$('#courseUl').attr('class', 'centernav2');
		}else if(index == 2){
			$('#courseUl').attr('class', 'centernav3');
		}
		$('.navbotd').eq(index).show().siblings().hide();
	});	
	$('#signDate').datepicker({changeYear: true});
    $(".avgGrade").jRating({type:'small',length : 10,decimalLength : 1,rateMax:100,isDisabled:true});
    $(".grade").jRating({type:'small',length : 10,decimalLength : 1,rateMax:100});
	<s:if test="#session.loginMember.id == member.id">
	var city = '<s:property value="#session.loginMember.city"/>';
    if (city == '') city = '武汉';
    var nick = '<s:property value="#session.loginMember.name"/>';
    var lng = '<s:property value="#session.loginMember.longitude"/>';
    var lat = '<s:property value="#session.loginMember.latitude"/>';
	</s:if>
	<s:else>
	var city = '<s:property value="member.city"/>';
    if (city == '') city = '武汉';
    var nick = '<s:property value="member.name"/>';
    var lng = '<s:property value="member.longitude"/>';
    var lat = '<s:property value="member.latitude"/>';
	</s:else>
   /*  var map = new BMap.Map("allmap");
    if (lng > 0 && lat > 0) {
	    var point = new BMap.Point(lng, lat);
	    var marker = new BMap.Marker(point);
	    map.addOverlay(marker);
	    marker.setAnimation(BMAP_ANIMATION_BOUNCE); 
	    var label = new BMap.Label(nick, {offset:new BMap.Size(20,-10)});
	    marker.setLabel(label);
	    map.centerAndZoom(point, 15);
    } else {
    	map.centerAndZoom(city, 15);
    }
    map.enableScrollWheelZoom(); */
    if (lng == 0 || lat == 0) {
    	lat = 30.53069759743673;
    	lng = 114.31637048721313;
    } 
    var center = new qq.maps.LatLng(lat, lng);
    var map = new qq.maps.Map("allmap", {
    	center: center,
    	zoom: 20
    });
    var marker = new qq.maps.Marker({
        position: center,
        map: map,
        animation:qq.maps.MarkerAnimation.BOUNCE
    });
	$("#dialog").dialog({autoOpen: false, show: "blind", hide: "explode", modal: true, resizable: false});
	$("#opener").click(function() {
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
			alert("不允许签到,请确保该俱乐部已登录，且双方同时在场！");
			return;
		}
	});
	$( "#dialogMsg" ).dialog({autoOpen: false, show: "blind", hide: "explode",resizable: false,modal: true});
	$( "#dialogCourseInfo" ).dialog({autoOpen: false, show: "blind", hide: "explode", resizable: false,modal: true});
	$( "#dialogSignIn" ).dialog({autoOpen: false, show: "blind", hide: "explode", modal: true, resizable: false});
});
function preShop(productId) {
	url = "clublist!shoGo.asp?productId="+productId;
	$('#queryForm').attr('action', url);
	$('#queryForm').submit();
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
						$("<option value='"+orderArr[i].id+"' proType='" + orderArr[i].proType + "'>"+orderArr[i].no+"【"+orderArr[i].orderType+"】</option>").appendTo($("select[name='signIn.orderId']"));
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
	var proType = $("select[name='signIn.orderId'] option:selected").attr('proType');
	if (proType === '3') {
		$('#signmoney').css('display', 'block');
	} else {
		$('#signmoney').css('display', 'none');
	}
}
function onSignInDo(){
	if (!$('#orderId').val()) {
		alert('请先选择需要签到的订单！');
		return;
	}
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
$.fx.speeds._default = 1000;
$(function() {
	});
	 
// Dialog
function onClosec(){
	$('#dialog').dialog('close');
}

function onClosecDo(){
	$('#dialogSignIn').dialog('close');
}
function sendMsg(){
	if(!"<s:property value="#session.loginMember.id"/>"){
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
			}else if(msg == "isMsg"){
				alert("您已申请加入过该俱乐部,请耐心等待俱乐部审核！");
				return;
			}else if(msg == "isFriend"){
				alert("您已加入该俱乐部,请勿重复执行该操作！");
				return;
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
</script>

</head>
<body style="background:white">
<input type="hidden" name="orderNickNick" id="orderNickNick"/>
<s:form id ="queryForm" name="queryForm" method="post" action="club.asp" theme="simple">
</s:form>
	<s:if test="#session.loginMember.id == member.id">
	<s:include value="/share/header.jsp"/>
	</s:if>
	  <s:else>
	<s:include value="/share/window-header.jsp"/>
	</s:else>
	<div id="content"style="margin-top:30px">
		<div id="left" style="width:320px;margin-top:0;padding-top:5px;">
			<div id="left-1"style="width:320px;height:auto;border:none">
				<!--用户资料--> 
				<!-- <h1>用户资料</h1> -->
				<div>
					<dl style="border:none">
						<dt>
							<s:if test="#session.loginMember.id == member.id">
							<a href="picture.asp"><s:if test="member.image == '' || member.image == null"><img src="images/userpho.jpg" width="150px" style="border-radius:50%"alt="健身俱乐部"/></s:if><s:else><img src="picture/<s:property value="member.image"/>"width="150px" alt="健身俱乐部"style="border-radius:50%"/></s:else></a>
							</s:if>
							<s:else>
							<s:if test="member.image == '' || member.image == null"><img src="images/userpho.jpg" alt="健身俱乐部"width="150px" style="border-radius:50%"/></s:if><s:else><img src="picture/<s:property value="member.image"/>" width="150px"alt="健身俱乐部"style="border-radius:50%"/></s:else>
							</s:else>
						</dt>
						<dd style="color:#8c8c8c"><s:property value="member.name"/></dd>
					</dl>
               <%--      <p class="havep">已有<span class="numred"><s:property value="member.countEmp"/></span>人进行了评价<span class="red3"><s:property value="member.avgGrade"/></span><span>分</span></p> --%>
					<s:if test="#session.loginMember.id != member.id">
					<input type="button"style="margin-top:0;background:#ff4401;color:white;line-height:23px" name="Submit" value="申请加入" class="inpbut" onclick="onSendMsg('1','<s:property value="member.id"/>');" />
					<input type="button"style="margin-top:0;background:#ff4401;color:white;line-height:23px" name="Submit" value="发消息" class="inpbut" onclick="sendMsg();"/>
					</s:if>
						<div>
					<p style="text-align:left;padding-left:10px;padding-top:10px;color:#545454">
					<span style="font-weight:600">营业时间</span>:<s:property value="#request.workTime.startTime"/>-<s:property value="#request.workTime.endTime"/><span></span>
						<s:if test="member.workDate == '1, 2, 3, 4, 5, 6, 7'">
						全天营业
						</s:if>
						<s:else>
							<s:if test="(', '+member.workDate).indexOf(', 1') == -1">星期日</s:if>
							<s:if test="(', '+member.workDate).indexOf(', 2') == -1">星期一</s:if>
							<s:if test="(', '+member.workDate).indexOf(', 3') == -1">星期二</s:if>
							<s:if test="(', '+member.workDate).indexOf(', 4') == -1">星期三</s:if>
							<s:if test="(', '+member.workDate).indexOf(', 5') == -1">星期四</s:if>
							<s:if test="(', '+member.workDate).indexOf(', 6') == -1">星期五</s:if>
							<s:if test="(', '+member.workDate).indexOf(', 7') == -1">星期六</s:if>
							不营业
						</s:else>
					</p>
					<p style="text-align:left;padding-left:10px;color:#545454"><span style="font-weight:600">电话号码</span>:<s:property value="member.tell"/></p>
					<p style="text-align:left;padding-left:10px;color:#545454"><span style="font-weight:600">坐标地址</span>:<s:property value="member.address"/></p>
					<div id="allmap" class="ditu_map"style="margin:10px auto;width:300px"></div>
				</div>
					
					
						<div id="right-3"style="width:100%;border:none">
				<h1 style="width:100%;background-color:#ff4401!important;background-image:none;color:white;border:none;text-align:left">服务项目</h1>
				<s:if test="member.courses.size > 0">
				<s:iterator value="#request.courses" status="cstat" end="13">
				<div id="right-3-1" class="color1"style="width:33.3%;padding-top:0;border:none!important">
				<div id="rightmember" style="height:100%">
                    <a>
					<dl style='border:none'>
						<dt style="margin:0;width:100%;height:100%;padding:0">
							<img src="<s:if test="image == null">images/fdy1_24.jpg</s:if><s:else>picture/<s:property value="image"/></s:else>" name="img1" style="width:100px;border:none!important;margin:0 auto;height:75px!important;box-sizing:border-box" id="img1" />
						
						</dt>
						
						
					<%-- 	<dd>
							
                              <span class="spansevera">已有<s:property value="appraiseNum"/>人评价</span>
							  <span class="spanseverb"><s:property value="avg_grade"/>分</span>
							
						</dd> --%>
					</dl>
					</a>
					<h3 style="width:100px;margin-top:-10px"><s:property value="name"/></h3>
					<div id="courseInfo<s:property value="#cstat.index"/>" style="display: none;"><s:property value="memo" escape="false"/></div>
				</div>
                                        
				
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
<!-- 			
          <div id="right-1">
				<a id="opener">
				   <img src="images/memberd.png" width="212" height="51"/>
				</a>
			</div>  -->
<!-- 			<div id="left-2">
				<h1>俱乐部信息</h1>
			
			
			
			
			</div> -->
		<%-- 	<div id="left-3">
				<!--最近访客-->
				<h1>最近访客</h1>
				<div>
					<s:iterator value="member.visitors">
						<dl>
							<dt>
								<a title="<s:property value="friend.name"/>" href="javascript:goMemberHome('<s:property value="friend.id"/>','<s:property value="friend.role"/>');"><s:if test="friend.image == '' || friend.image == null"><img src="images/userpho.jpg"/></s:if><s:else><img src="picture/<s:property value="friend.image"/>"/></s:else></a>
							</dt>
							<dd><s:property value="friend.name"/></dd>
						</dl>
					</s:iterator>
				</div>
			</div> --%>
		</div>
		<div id="center" style="width:625px;border:none;">
			<div id="center-2"style="width:625px;border:none">
			<div id="right-2"style="width:625px;border:none">
				<h1 style="width:625px;background-image:none;background-color:#ff4401;color:white">健身套餐<span class="clustanda"style="float:right;padding-right:2.5em"><a id="coloa" style="color:white;"href="javascript:goProductHome('<s:property value="member.id"/>');">更多</a> </span> <div style="clear:both"></div> </h1>
				<div>
				<div style="float:left;margin-left:10px">
				<div style="width:300px;height:180px;margin-top:10px;position:relative;background-color:rgba(1,1,1,.1)">
				<img id="productImage" src="picture/opacity.png" alt="" style="width:300px;height:180px"/>
				<div style="position:absolute;bottom:0px;width:300px;height:30px;background-color:rgba(1,1,1,.2);color:white;line-height:30px;text-indent:2em" id="productName">暂未发布健身套餐</div>
				</div>
				<div  style="width:300px;height:50px;">
				<a href="javascript:void(0)" id="productLink" style="background:#ff4401;display:block;width:200px;height:30px;font-size:15px;border-radius:4px;margin:10px 50px;line-height:30px;text-align:center;color:white">购买</a>		
				</div>
				
				</div>
				<s:if test="member.products.size > 0">
				<dl style="width:250px;float:right;margin-right:10px">
				  <s:iterator value="member.products" status="pstat">
					<s:if test="#pstat.index > 0 && #pstat.index<6">
						<dt  style="width:180px;height:30px;margin-top:10px;background:#ececec" <s:if test="#pstat.index%2==0">id="colorjla"</s:if>><s:property value="name" /></dt>
						<dd style="background:#ececec;height:30px;margin-top:10px;"<s:if test="#pstat.index%2==0">id="colorjla"</s:if>>
							<a style="background-color:#ff4401;color:white;padding:2px 10px;border-radius:3px " href="javascript:preShop('<s:property value="id"/>');" id="colotoa">购买</a>
						</dd>
					</s:if>
					</s:iterator><!--有健身套餐时使用-->
				</dl>
				<div style="clear:both"></div>
				</s:if>
				<s:else>
                <div class="clustandb">
               		<s:if test="#session.loginMember.id == member.id">
					 还没有添加你的特色健身套餐哦，<a id="colotoa" href="product.asp">点击填写属于你的套餐计划</a>，健身套餐不仅是让会员对俱乐部的特色健身有一定的了解，而却还是获取更多利益的最佳渠道，不要错过！！
					</s:if>
					<s:else>
					该用户还没有添加自己的特色健身套餐
					</s:else>
                </div><!--没有健身套餐时使用-->
				</s:else>
				</div>
			</div>
			</div>
			
			
			<div id='center-2'style="width:625px;min-height:auto;">
					<div id="right"style="margin:0;padding:0;width:625px">
			
			

                          <div id="right-3"style="margin:0;width:625px;border:none">
                             <h1 style="width:625px;color:white;background-image:none;background-color:#ff4401;">健身挑战<span class="clustanda"style="float:right;padding-right:2.5em"><a id="coloa" style="color:white" href="javascript:goProductHome('<s:property value="member.id"/>');">更多</a></span><div style="clear:both"></div></h1>
					<s:if test="(#request.actives.size()>0)">
					<s:iterator value="#request.actives">
						<a href="activewindow.asp?id=<s:property value="id"/>"style="float:left;width:50%;padding-top:10px;padding-left:10px;padding-bottom:10px;box-sizing:border-box">
						<div class="chanllge" style="width:100%;;margin:0;background:#555555;height:110px;box-sizing:border-box">
                                 <div class="chanpic"style="float:left;"><img style="width:110px;height:110px;" src="picture/<s:property value="active_image"/>"/></div>
                                 <div class="chancont"style="float:left;width:150px">
                                     <p class="chantitle"style="margin:5px 0;color:white"><s:property value="name"/></p>
                                     <p style="margin:5px 0;color:white"><s:if test="category==\"A\""><s:property value="days"/>天内体重增加<s:property value="value"/>KG</s:if>
										<s:elseif test="category==\"B\""><s:property value="days"/>天内体重减少<s:property value="value"/>KG</s:elseif>
										<s:elseif test="category==\"C\""><s:property value="days"/>天内体重保持在<s:property value="value"/>%左右</s:elseif>
										<s:elseif test="category==\"D\""><s:property value="days"/>天内运动<s:property value="value"/>次</s:elseif>
										<s:elseif test="category==\"E\""><s:property value="days"/>天内运动<s:property value="value"/>小时</s:elseif>
										<s:elseif test="category==\"F\""><s:property value="days"/>天内每周运动<s:property value="value"/>次</s:elseif>
										<s:elseif test="category==\"G\""><s:property value="days"/>天内<s:if test="action == null"></s:if><s:else><s:property value="action"/></s:else><s:property value="value"/>千米</s:elseif>
										<s:elseif test="category==\"H\""><s:property value="days"/>天内力量运动负荷<s:property value="value"/>KG</s:elseif></p>
                                     <p style="margin:5px 0;color:white"><font color="white">奖</font>：<s:property value="award"/></p>
                                     <p style="margin:5px 0;color:white"><font color="white">罚</font>：公益捐款<s:property value="amerce_Money"/>元</p>
                                  </div>
                                  <div style="clear:both"></div>	
                                 	
                              </div>
                              </a>
					</s:iterator>
					</s:if><s:else>你可以发起健身挑战活动，激发会员的健身热情</s:else>
                          </div>
		
	</div>
			</div>
	
			
			
			
			
			<div id="center-3"style="width:625px">
			
			
			
			
			
			
				<div class="centertab"style="width:625px">
				<h1 style="width:625px;background-image:none;background:#ff4401;color:white">淘课吧</h1>
                                   <ul id ="courseUl" class="centernav">
                                   		<li style="width:68px;"><s:property value="day1" /></li>
                                   		<li style="width:68px"><s:property value="day2" /></li>
                                   		<li style="width:68px"><s:property value="day3" /></li>
                                   	</ul>
                                </div>
				<div>
                                     <div class="navbotd"style="display:block;padding:10px">
                                     <s:if test="(#request.courseList1.size()>0)">
                                     <ul style="width:100%;margin:0">
                                     <s:iterator value="#request.courseList1" status="start">
          								<s:hidden name="courseInfo.type.id"></s:hidden>
          								<a href="javascript:coursePurchase(<s:property value="id" />)">
               								<li class="centershioo"style="width:50%;border:none">
                								<div class="taokepic"><img src="picture/<s:property value="courseInfo.image"/>" style="width:150px!important;height:100px!important"/></div>
               	 								<div class="taokeinfo" >
                  									<p class="infoss"style="margin:5px 0"><s:property value="courseInfo.name" /></p>
                  									<p class="infotime"style="margin:5px 0">时间:<span><s:property value="startTime" />-<s:property value="endTime" /></span></p>
                  									<p class="infoshop"style="margin:5px 0"><strong>￥<s:property value="hourPrice"/>元</strong><span style="margin-left:10px;background:#ff4401;font-weight:100">抢购</span></p>
                								</div>
              								</li>
              							</a>
           								</s:iterator>
                                     </ul> 
                                     </s:if>
                                     <s:else>
                                     	请到健身预约-团体课表中发布团体课程
                                     </s:else>
                                     
                                      </div>
                                     <div class="navbotd" style="">  <s:if test="(#request.courseList2.size()>0)">
                                     <ul style="width:100%;margin:0">
                                     <s:iterator value="#request.courseList2" status="start">
          								<s:hidden name="courseInfo.type.id"></s:hidden>
          								<a href="javascript:coursePurchase(<s:property value="id" />)">
               								<li class="centershioo"style="width:50%;border:none">
                								<div class="taokepic" style=""><img src="picture/<s:property value="courseInfo.image"/>" style="width:150px!important;height:100px!important"/></div>
               	 								<div class="taokeinfo">
                  									<p class="infoss"style="margin:5px 0"><s:property value="courseInfo.name" /></p>
                  									<p class="infotime"style="margin:5px 0">时间:<span><s:property value="startTime" />-<s:property value="endTime" /></span></p>
                  									<p class="infoshop"style="margin:5px 0"><strong>￥<s:property value="hourPrice"/>元</strong><span style="margin-left:10px;background:#ff4401;font-weight:100">抢购</span></p>
                								</div>
              								</li>
              							</a>
           								</s:iterator>
                                     </ul> 
                                     </s:if>
                                     <s:else>
                                     	请到健身预约-团体课表中发布团体课程
                                     </s:else></div>
                                     <div class="navbotd">  <s:if test="(#request.courseList3.size()>0)">
                                     <ul style="width:100%;margin:0">
                                     <s:iterator value="#request.courseList3" status="start">
          								<s:hidden name="courseInfo.type.id"></s:hidden>
          								<a href="javascript:coursePurchase(<s:property value="id" />)">
               								<li class="centershioo"style="width:50%;border:none">
                								<div class="taokepic"><img src="picture/<s:property value="courseInfo.image"/>" style="width:150px!important;height:100px!important"/></div>
               	 								<div class="taokeinfo">
                  									<p class="infoss"style="margin:5px 0"><s:property value="courseInfo.name" /></p>
                  									<p class="infotime"style="margin:5px 0">时间:<span><s:property value="startTime" />-<s:property value="endTime" /></span></p>
                  									<p class="infoshop"style="margin:5px 0"><strong>￥<s:property value="hourPrice"/>元</strong><span style="margin-left:10px;background:#ff4401;font-weight:100">抢购</span></p>
                								</div>
              								</li>
              							</a>
           								</s:iterator>
                                     </ul> 
                                     </s:if>
                                     <s:else>
                                     	请到健身预约-团体课表中发布团体课程
                                     </s:else></div>
				</div>
                             
			</div>
		</div>

	<s:include value="/share/footer.jsp"/>
	<div id="dialog" title="会员签到" style="display:none;">
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
	
	<div>
		<div id="signmoney" style="display: none;">消费金额：<input type="text" name="signIn.money" /></div>
		<p class="select-order">
			签到订单：<select name="signIn.orderId" onchange="initOrderNo();" id="orderId"/><br />
		</p>
		<p class="pa">
		   <input type="button" onclick="onSignInDo();" value="签到" class="butok"/>
		   <input type="button" value="取消" onclick="onClosecDo()" class="butok"/>
		</p>
	</div>
	</form>
	</div>
	<div id="dialogMsg" title="发送消息" style="display:none;">
	<form id="msgForm" name="msgForm" method="post" action="message!save.asp">
		<input type="hidden" name="msg.type" value="2"/>
		<input type="hidden" name="msg.memberTo.id" value="<s:property value="member.id"/>"/>
		<input type="hidden" name="msg.memberTo.nick" value="<s:property value="member.nick"/>"/>
		<p>
			收件人：<s:property value="member.nick"/>
		</p>
		<p class="message_content">
			<span>消息内容：</span>
			<span><textarea name="msg.content" class="send-message" onkeyup="this.value = this.value.substring(0, 140)"></textarea><span>
		</p>
		<p class="pa">
		   <input type="button" value="确定" onclick="onSendMsg();" class="butok"/>
		   <input type="button" value="取消" onclick="onCloseMsg();" class="butok"/>
		</p>
	</form>
</body>
<script>
	var products = ${products}
	if(products.length != 0){
		$("#productImage").attr("src", "picture/" + products[0].image);
		$("#productName").html(products[0].name);
		$("#productLink").attr("href", "javascript:preShop('"+ products[0].id +"')");
	} 
</script>
</html>
