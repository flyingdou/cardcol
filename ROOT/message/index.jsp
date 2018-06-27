<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<s:include value="/share/meta.jsp" />
<meta name="keywords" content="健身E卡通—信息中心" />
<meta name="description" content="健身E卡通—信息中心" />
<title>信息中心</title>
<link rel="stylesheet" type="text/css" href="css/information-center.css" />
<link type="text/css" rel="stylesheet" href="css/pulicstyle.css"/>
<script type="text/javascript"> 
$(document).ready(function(){
	var countJson = $.parseJSON($("input[name='countList']").val());
	if(!countJson){
		return;
	}
	for(var i=0;i<countJson.length;i++){
		$("#tishi"+countJson[i][1]).html("("+countJson[i][0]+")");
	}
	
	if(${flag} == 0){
		goMessage('1', 1);
	} else if (${flag} == 1) {
		goMessage('2', 2);
	} else if (${flag} == 2) {
		goMessage('3', 3);
	} else if (${flag} == 3) {
		goFriend('2');
	} else if (${flag} == 4) {
		goFriend('3');
	} else if (${flag} == 5) {
		goFriend('4');
	}
});
function goFriend(type){
	$.ajax({type:'post',url:'info!findFriend.asp',data:"type="+type,
		success:function(msg){
			$("#right-2").html(msg);
		}
	});
}
function goMessage(type, status){
	$.ajax({type:'post',url:'message!query.asp',data:"queryType="+type+"&status="+status,
		success:function(msg){
			$("#right-2").html(msg);
			var msgbVal = $("#tishi"+type).html();
			msgbVal = msgbVal.substring(1,msgbVal.length-1);
			if(type != '1'){
				$('.msgb').html($('.msgb').html()-msgbVal);
				$("#tishi"+type).html("(0)");
			}
		}
	});
}
$.fx.speeds._default = 1000;
$(function() {
	$( "#dialog" ).dialog({
		autoOpen: false,
		show: "blind",
		hide: "explode",
		resizable: false
	});
});
	 
// Dialog
function onClosec(){
	$('#dialog').dialog('close');
}
function sendMessage(id, name, pid){
	if(id && name){
		$("select[name='msg.memberTo.id']").empty();
		if (pid) $('#msgparent').val(pid);
		$("<option value='"+id+"'>"+name+"</option>").appendTo("select[name='msg.memberTo.id']");
		$("textarea[name='msg.content']").val("");
		$( "#dialog" ).dialog( "open" );
	}else{
		$.ajax({type:'post',url:'message!findMember.asp',data:'',
			success:function(msg){
				$("select[name='msg.memberTo.id']").empty();//清空下拉框
				if(msg){
					var memberArr,member;
					memberArr = msg.split(":");
					for(var i=0;i<memberArr.length;i++){
						member = memberArr[i].split(",");
						$("<option value='"+member[0]+"'>"+member[1]+"</option>").appendTo("select[name='msg.memberTo.id']");//添加下拉框的option
					}
				}
				$("textarea[name='msg.content']").val("");
				$( "#dialog" ).dialog( "open" );
			}
		});
	}
}
function onSave(){
	if(!$("select[name='msg.memberTo.id']").val()){
		alert("收件人不能为空，请确认！");
		$("select[name='msg.memberTo.id']").focus();
		$("select[name='msg.memberTo.id']").selet();
		return;
	}
	if(!$("textarea[name='msg.content']").val()){
		alert("消息内容不能为空，请确认！");
		$("textarea[name='msg.content']").focus();
		$("textarea[name='msg.content']").selet();
		return;
	}
	$.ajax({type:'post',url:'message!save.asp',data:$('#msgForm').serialize(),
		success:function(msg){
			if(msg == "ok"){
				alert("当前信息已成功发送！");
				onClosec();
			}else{
				alert(msg);
			}
		}
	});
}
</script>
</head>
<body>
<input type="hidden" name="countList" value="<s:property value="#request.countList"/>"/>
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
							<li><a href="javascript:goMessage('1', 1);" id="coloa">审批 <span
									id="tishi1">(0)</span>
							</a></li>
							</s:if>
							<li><a href="javascript:goMessage('2', 2);" id="coloa">消息 <span
									id="tishi2">(0)</span>
							</a></li>
							<li><a href="javascript:goMessage('3', 3);" id="coloa">提醒 <span
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
							<li><a href="javascript:goFriend('2');" id="coloa">我的俱乐部</a></li>
							<s:if test="#session.loginMember.role == \"E\"">
							<li><a href="javascript:goFriend('3');" id="coloa">我的教练</a></li>
							</s:if>
							<s:if test="#session.loginMember.role != \"M\"">
							<li><a href="javascript:goFriend('4');" id="coloa">我的会员</a></li>
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
			<s:if test="queryType == 1">
			<s:include value="/message/message_list1.jsp"/>
			</s:if>
			<s:if test="queryType == 2">
			<s:include value="/message/message_list2.jsp"/>
			</s:if>
			<s:if test="queryType == 3">
			<s:include value="/message/message_list3.jsp"/>
			</s:if>
		</div>
	</div>
	<s:include value="/share/footer.jsp" />
	<%-- <div id="dialog" title="发送消息">
	<form id="msgForm" name="msgForm" method="post" action="message!save.asp">
		<input type="hidden" name="msg.type" value="2"/>
		<input type="hidden" name="msg.parent" id="msgparent"/>
		<div id="divId"></div>
		<p>
			<span>收件人：<select name="msg.memberTo.id" class="uidpwd"/></span>
		</p>
		<p class="message_content">
			<span>消息内容：</span>
			<span><textarea name="msg.content"  class="send-message" onkeyup="this.value = this.value.substring(0, 140)"></textarea></span>
		</p>
		<p class="pa">
		   <input type="button" value="确定" onclick="onSave();"  class="butpa"/>
		   <input type="button" value="取消" onclick="onClosec()"  class="butpa"/>
		</p>
	</form> --%>
	</div>
</body>
</html>
