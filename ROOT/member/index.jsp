<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<s:include value="/share/meta.jsp" />
<meta name="keywords" content="健身会员，健身爱好者，瘦身者，减肥者，运动" />
<meta name="description" content="健身会员的首页" />
<title><s:property value="member.name"/>首页</title>
<link rel="stylesheet" type="text/css" href="css/member-homepage.css" />
<link type="text/css" rel="stylesheet" href="css/validator.css" />
<link type="text/css" rel="stylesheet" href="css/pulicstyle.css" />
<script type="text/javascript" src="script/FusionCharts/FusionCharts.js"></script>
<script type="text/javascript" src="script/jquery.insertContent.js"></script>
<script language="javascript">
//禁用Enter键表单自动提交  
document.onkeydown = function(event) {  
    var target, code, tag;  
    if (!event) {
        event = window.event; //针对ie浏览器  
        target = event.srcElement;  
        code = event.keyCode;  
        if (code == 13) {  
            tag = target.tagName;  
            if (tag == "TEXTAREA") { return true; }  
            else { return false; }  
        }  
    }  
    else {  
        target = event.target; //针对遵循w3c标准的浏览器，如Firefox  
        code = event.keyCode;  
        if (code == 13) {  
            tag = target.tagName;  
            if (tag == "INPUT") { return false; }  
            else { return true; }   
        }  
    }  
}; 
$(function(){
	$('#doneDate').datepicker({onSelect: loadRecord});
	$( "#startDate" ).datepicker();
	$( "#endDate" ).datepicker();
	$( "#recordDate" ).datepicker({maxDate:new Date()});
	$( "#dialogMsg" ).dialog({
		autoOpen: false,
		show: "blind",
		hide: "explode",
		resizable: false
	});
	var	date = new Date();
	var yearStr = date.getFullYear();
	var monthStr = (date.getMonth()+1)>9?(date.getMonth()+1):"0"+(date.getMonth()+1);
	var dayStr = date.getDate()>9?date.getDate():"0"+date.getDate();
	var dateStr = yearStr+"-"+monthStr+"-"+dayStr;
	queryRecord();
	if("<s:property value="member.id"/>" != "<s:property value="#session.loginMember.id"/>"){
		$('#recordForm2').find('input,textarea,select').each(function(){
			$(this).attr('disabled', true);
		});
	}
});
function loadRecord(dateText, inst){
	$.post('member!getRecordByDate.asp', {'record.doneDate': dateText}, function(data, textStatus, jqXHR){
		var json = data[0];
		$('#spanfh').html(json.strength);
		$('#recordMemo').val(json.memo);
		$('#recordHeight').val(json.height);
		$('#recordHip').val(json.hip);
		$('#recordWeight').val(json.weight);
		$('#recordWaist').val(json.waist);
	}, 'json');
}
Array.prototype.unique5 = function() {
	var res = [], hash = {};
	for ( var i = 0, elem; (elem = this[i]) != null; i++) {
		if (!hash[elem]) {
			res.push(elem);
			hash[elem] = true;
		}
	}
	return res;
}
function showEmotion(the) {
	var left = 0, top = 0, o = the;
	while(o.offsetParent) {
        left += o.offsetLeft;
        top += o.offsetTop;
        o = o.offsetParent;
	}
	$('#divemotion').css('top', top + 20);
	$('#divemotion').css('left', left);
	$('#divemotion').show();
}
function getEmotion(the) {
	var val = $(the).attr('value');
	$("#recordMemo").insertContent(val,-1);
	$('#divemotion').hide();
}
function queryRecord(){
	$.ajax({type:'post',url: 'member!queryRecord.asp',data: $('#recordForm1').serialize(), 
		success: function(msg){
			var recordType = $("#recordType").val();
			var recordName = $("#recordType").find("option:selected").text();  
			var dataString="<chart canvasPadding='10' caption='"+recordName+"情况分析' yAxisName='"+recordName+"' bgColor='F7F7F7, E9E9E9' numVDivLines='10' divLineAlpha='30'  labelPadding ='10' yAxisValuesPadding ='10' showValues='1' rotateValues='1' valuePosition='auto' connectNullData='1'>";
			var categories="<categories>";
			var datasetsR="<dataset seriesName='"+recordName+"' color='A66EDD'>";
			var datasetsT="<dataset seriesName='目标"+recordName+"' color='F6BD0F'>";
			var recordDate = "";
			if(msg == "no_data"){
				//alert("没有最近身体数据！");
			}else{
				var recordArr = $.parseJSON(msg).recordList;
				if(recordArr){
					for(var i=0;i<recordArr.length;i++){
						var rec = recordArr[i];
						categories += "<category label='" + rec.doneDate.substr(0,10) + "'/>";
						datasetsR += "<set value='" + rec[recordType] + "'/>";
					}
				}
			}
			dataString = dataString+categories+"</categories>"+datasetsR+"</dataset>"+"</chart>";
			var chart = new FusionCharts("script/FusionCharts/MSLine.swf", "ChartId", "480", "220", "0", "0");
			chart.setXMLData(dataString);
			chart.render("chartdiv");
		}
	});
}
function changeModeSpan(){
	for(var i=1;i<=3;i++){
		if($("#modeType").val() == i){
			$("#modeSpan"+i).css("display","block");
		}else{
			$("#modeSpan"+i).css("display","none");
		}
	}
}
function changeTS(){
	var modeType = $("#modeType").val();
	var modeValue = parseInt($("#modeValue"+modeType).val());
	var msgArr = ["您的运动强度较小，可以加大。","您的运动强度合适，请继续坚持。","您的运动强度太大，应减慢速度或减小强度。"];
	var msgIndex;
	if(modeType == "1"){
		modeValue = $("#modeValue"+modeType).val();
		if(!modeValue){
			alert("请先输入你运动后的心率，再进行运动强度评估！");
			$("#modeValue1").focus();
			$("#modeValue1").select();
			return;
		}
		if(isNaN(modeValue)){
			alert("请先输入正确的心率数据，谢谢！");
			$("#modeValue1").focus();
			$("#modeValue1").select();
			return;
		}
		modeValue = parseInt(modeValue);
		var birthday = "<s:property value="member.birthday"/>"
		if(!birthday){
			alert("请先在我的账户中维护生日数据，再进行运动强度评估！");
			return;
		}
		var age = new Date(Date.parse(birthday.replace(/-/g,"/"))); 
		age = parseInt((new Date()-age)/(1000*60*60*24*365));

		var bmiHigh = "<s:property value="setting.bmiHigh"/>";
		if(!bmiHigh){
			alert("请先在我的设定【四.设定靶心率】中维护【靶心率阈值上限】数据，再进行运动强度评估！");
			return;
		}
		var bmiLow = "<s:property value="setting.bmiLow"/>";
		if(!bmiLow){
			alert("请先在我的设定【四.设定靶心率】中维护【靶心率阈值下限】数据，再进行运动强度评估！");
			return;
		}
		var heartH = 220-age;
		var heartHigh = heartH*bmiHigh/100;
		var heartLow = heartH*bmiLow/100;

		if(modeValue < heartLow){
			msgIndex = 0;
		}else if(modeValue >=heartLow && modeValue <= heartHigh){
			msgIndex = 1;
		}else{
			msgIndex = 2;
		}
	}else if(modeType == "2"){
		if(modeValue == 1){
			msgIndex = 0;
		}else if(modeValue >=2 && modeValue <= 3){
			msgIndex = 1;
		}else{
			msgIndex = 2;
		}
	}else if(modeType == "3"){
		if(modeValue >= 1 && modeValue <= 4){
			msgIndex = 0;
		}else if(modeValue >= 5 && modeValue <= 7){
			msgIndex = 1;
		}else{
			msgIndex = 2;
		}
	}
	$("#ts").html(msgArr[msgIndex]);
}
function saveRecord(){
	if(!$("input[name='record.doneDate']").val() ){
		alert("完成日期必须填写后才能保存！");
		return;
	}
	//var parms = $('#recordForm2').serialize() + '&syncWeibo=' + ($('#syncWeibo').attr('checked') === 'checked' ? 1 : 0);
	
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
function changeInfo(){
	var _weight = $("input[name='record.weight']").val(), _height = <s:property value="setting.height"/>/100;
	if (_weight) {
		if (_weight != '' && _weight != '0' && _height != '0') {
			$("#weightSpan").html(_weight);
			var _bmi = toDecimal(_weight / (_height * _height), 2);
			$("#bmiSpan").html(_bmi);
			var tishiBmiHtml;
			if(_bmi){
				if(_bmi < 18.5){
					tishiBmiHtml = "您的体重过轻，请增强营养，加强锻炼。";
				}else if(_bmi >= 18.5 && _bmi < 24){
					tishiBmiHtml = "您的体重正常，健康风险最低，身体健康。";
				}else if(_bmi >= 24 && _bmi < 28){
					tishiBmiHtml = "您的体重超重，患病风险正在加大。";
				}else if(_bmi > 28){
					tishiBmiHtml = "您属于肥胖体型，属健康高危人群。";
				}
			}
			$("#tishiBmi").html(tishiBmiHtml);
		}
	}
	var _waist = $("input[name='record.waist']").val(), _hip = $("input[name='record.hip']").val(),waistHipVal = <s:if test="member.sex == \"M\"">0.94</s:if><s:else>0.82</s:else>, tishiWaistHipHtml;
	if(_waist && _hip){
		var _waistHip = toDecimal(_waist / _hip, 2);
		$("#waistHipSpan").html(_waistHip);
		if(_waistHip >= waistHipVal){
			tishiWaistHipHtml = "您的腰臀比偏高，患病的概率比较大。";
		}else{
			tishiWaistHipHtml = "您的腰臀比正常，身体健康。";
		}
		$("#tishiWaistHip").html(tishiWaistHipHtml);
	}
}
//保留n位小数，如：2，会在2后面补上n个0   
function toDecimal(x,n) {   
    var f = parseFloat(x);   
    if (isNaN(f)) {   
        return false;   
    }
    var nm = 1;
    for(var i = 0;i<n;i++){
    	nm = nm*10;
    }
    var f = Math.round(x*nm)/nm;
    var s = f.toString();   
    var rs = s.indexOf('.');   
    if (rs < 0) {   
        rs = s.length;   
        s += '.';   
    }   
    while (s.length <= rs + n) {   
        s += '0';   
    }   
    return s;   
} 
 
function sendMsg(type){
	if(!"<s:property value="#session.loginMember.id"/>"){
		alert("登录后才能发送消息！");
		return;
	}
	// type为0则是其他用户向自己发送消息。为1则是自己向私教发送消息
	if(type==0){
		if("<s:property value="member.id"/>" == "<s:property value="#session.loginMember.id"/>"){
			alert("自己不能向自己发送信息！");
			return;
		}
	}else{
		// 会员向私教发送消息，接收消息的对象设置为私教
		$("#memberTo").val('<s:property value="member.coach.id"/>');
		$("#memberToName").html('<s:property value="member.coach.nick"/>');
	}
	$("textarea[name='msg.content']").val("");
	$( "#dialogMsg" ).dialog( "open" );
}
function onSendMsg(id){
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
//解除私教关系
function relieve(){
	if(window.confirm("确认解除私教关系？")){
		$.ajax({type:"post",url:"member!saveRelieve.asp",data:'',
			success:function(msg){
				if(msg == 'ok'){
					alert("当前关系已成功解除！");
					var divHtml = '<dl>'
									+'<dt>'
									+'<img src="images/userpho.jpg"/>'
									+'</dt>'
								  +'</dl>'
								  +'<p style="line-height:18px;">还没有私人教练<BR/><a href="coachlist.asp" id="colotoa">点击这里申请教练吧</a></p>';
					$('#divCoach').html(divHtml);
				}else{
					alert(msg);
				}
			}
		});
	}
}
</script>
</head>
<body>
	   <s:if test="#session.loginMember.id == member.id">
	<s:include value="/share/header.jsp"/>
	</s:if>
        <s:else>
	<s:include value="/share/window-header.jsp"/>
	</s:else>


	
    <div id="content">
		<div id="left">
			<div id="left-1">
				<!--用户资料-->
				<h1>用户资料</h1>
				<div>
					<dl>
						<dt>
							<s:if test="#session.loginMember.id == member.id">
							<a href="picture.asp"><s:if test="member.image == '' || member.image == null"><img src="images/userpho.jpg" alt="健身会员" style="height:178px;width:178px;"/></s:if><s:else><img style="height:178px;width:178px;" src="picture/<s:property value="member.image"/>" alt="健身会员"/></s:else></a>
							</s:if>
							<s:else>
							<s:if test="member.image == '' || member.image == null"><img style="height:178px;width:178px;" src="images/userpho.jpg" alt="健身会员"/></s:if><s:else><img style="height:178px;width:178px;" src="picture/<s:property value="member.image"/>" alt="健身会员"/></s:else>
							</s:else>
						</dt>
						<dd><s:property value="member.name"/></dd>
					</dl>
					<s:if test="#session.loginMember.id != member.id">
					<input type="button" name="Submit" value="加关注" class="button" />
					<input type="button" name="Submit" value="发消息" class="button" onclick="sendMsg(0);"/>
					</s:if>
					 <div class="imdivx">
					    <p class="imgp">用户等级:
					    <span>
					    	<s:if test="member.integralCount>0 && member.integralCount<=100">
					    		<s:if test="member.role==\"M\"">三级运动员</s:if>
					    		<s:if test="member.role==\"S\"">一星级教练</s:if>
					    		<s:if test="member.role==\"E\"">一星级俱乐部</s:if>
					    	</s:if>
					    	<s:if test="member.integralCount>100 && member.integralCount<=200">
					    		<s:if test="member.role==\"M\"">二级运动员</s:if>
					    		<s:if test="member.role==\"S\"">二星级教练</s:if>
					    		<s:if test="member.role==\"E\"">二星级俱乐部</s:if>
					    	</s:if>
					    	<s:if test="member.integralCount>200 && member.integralCount<=300">
					    		<s:if test="member.role==\"M\"">一级运动员</s:if>
					    		<s:if test="member.role==\"S\"">三星级教练</s:if>
					    		<s:if test="member.role==\"E\"">三星级俱乐部</s:if>
					    	</s:if>
					    	<s:if test="member.integralCount>300 && member.integralCount<=400">
					    		<s:if test="member.role==\"M\"">运动健将</s:if>
					    		<s:if test="member.role==\"S\"">四星级教练</s:if>
					    		<s:if test="member.role==\"E\"">四星级俱乐部</s:if>
					    	</s:if>
					    	<s:if test="member.integralCount>400">
					    		<s:if test="member.role==\"M\"">国际级运动健将</s:if>
					    		<s:if test="member.role==\"S\"">五星级教练</s:if>
					    		<s:if test="member.role==\"E\"">五星级俱乐部</s:if>
					    	</s:if>
					    </span>
					    </p>
						<p class="imgp2">用户积分:<span><s:property value="member.integralCount"/></span></p>
					</div>
				</div>
			</div>

			<%-- <div id="left-2">
				<!--运动强度评估-->
				<h1>运动强度评估</h1>
				<div>
					<p>
						选择评估模式：<br /> <select name="modeType" id="modeType" size="1" class="assess-2" onchange="changeModeSpan();">
							<option value="1">心率模式</option>
							<option value="2">呼吸指数模式</option>
							<option value="3">自我感受模式</option>
						</select>
					</p>
					<p>
						<span id="modeSpan1">输入你运动后的心率：<br/><input type="text" class="assess-2" name="modeValue1" id="modeValue1"/></span>
						<span id="modeSpan2">选择你运动后的呼吸指数：<br/>
						<select name="modeValue2" id="modeValue2" size="1" class="assess-2" onchange="che">
							<option value="1">正常呼吸，没有不适</option>
							<option value="2">呼吸加快，可以正常交谈</option>
							<option value="3">呼吸急促，交谈有困难</option>
							<option value="4">气喘，伴有胸闷等不适感</option>
						</select>
						</span>
						<span id="modeSpan3">选择你运动后的自我感受：<br/>
						<select name="modeValue3" id="modeValue3" size="1" class="assess-2" onchange="che">
							<option value="1">无运动感觉，静坐</option>
							<option value="2">非常容易，站立</option>
							<option value="3">容易，慢步走</option>
							<option value="4">容易/中等 </option>
							<option value="5">中等强度，中速步行 </option>
							<option value="6">困难，慢跑 </option>
							<option value="7">非常困难，长跑</option>
							<option value="8">极度困难，陡坡冲刺</option>
						</select>
						</span>
						<br />
						<input type="button" name="Submit" value="确定" class="buttom" onclick="changeTS();"/>
					</p>
					<p id="tishi" class="tishi">
						<span id="ts"></span>
					</p>
				</div>
			</div> --%>

			<div id="left-3">
				<!--最近访客-->
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
			<div id="right-2">
				<!--我的体质分析-->
				<h1>身体指标</h1>
				<div>
					<ul>
						<li>目前体重：<span id="weightSpan"><s:property value="record.weight"/></span>(kg)</li>
						<li>目前体质指数：<span id="bmiSpan"><s:property value="#request.bmi"/></span></li>
						<li>建议：
							<span id="tishiBmi">
							<s:if test="#request.bmi !=null ">
							<s:if test="#request.bmi <= 0"><span id="tishi">请先到我的设定中维护您的身高体重。</span></s:if>
							<s:elseif test="#request.bmi < 18.5 && #request.bmi > 0"><span id="tishi">您的体重过轻，请增强营养，加强锻炼。</span></s:elseif>
							<s:elseif test="#request.bmi >= 18.5 && #request.bmi < 24"><span id="tishi">您的体重正常，健康风险最低，身体健康。</span></s:elseif>
							<s:elseif test="#request.bmi >= 24 && #request.bmi < 28"><span id="tishi">您的体重超重，患病风险正在加大。</span></s:elseif>
							<s:elseif test="#request.bmi > 28"><span id="tishi">您属于肥胖体型，属健康高危人群。</span></s:elseif>
							</s:if>
							</span>
						</li>
						<li>目前腰臀比：<span id="waistHipSpan"><s:property value="record.waistHip"/></span></li>
						<li>建议：
							<span id="tishiWaistHip">
							<s:if test="record.waistHip !=null ">
							<s:if test="member.sex == \"M\"">
								<s:if test="record.waistHip >= 0.94"><span id="tishi">您的腰臀比偏高，患病的概率比较大。</span></s:if><s:else><span id="tishi">您的腰臀比正常，身体健康。</span></s:else>
							</s:if>
							<s:if test="member.sex == \"F\"">
								<s:if test="record.waistHip >=0.82"><span id="tishi">您的腰臀比偏高，患病的概率比较大。</span></s:if><s:else><span id="tishi">您的腰臀比正常，身体健康。</span></s:else>								
							</s:if>
							</s:if>							
							</span>
						</li>
					</ul>
				</div>
			</div>
			
			<s:form name="recordForm1" id="recordForm1" action="member!queryRecord.asp" theme="simple">
			<div id="center-2">
				<!--身体数据曲线图-->
				<h1>历史数据</h1>
				<div>
					<p>
						开始时间 <input type="text" id="startDate" name="startDate" class="Data-Figure2" />
						<img src="images/sar-1_03.jpg" align="absbottom" />
					</p>
					<p>
						结束时间 <input type="text" id="endDate" name="endDate" class="Data-Figure2" />
						<img src="images/sar-1_03.jpg" align="absbottom" />
					</p>
					<p >
						选择指标 <select name="recordType" id="recordType">
							<option value="weight">体重(kg)</option>
							<option value="waist">腰围(cm)</option>
							<option value="hip">臀围(cm)</option>
						</select> <input type="button" name="Submit2" value="查询" class="fond" onclick="queryRecord();"/>
					</p>
					<div id="chartdiv" align="center">Chart will load here</div>
				</div>
			</div>
			</s:form>
			
			<%--  <s:form name="recordForm2" id="recordForm2" action="member!saveRecord.asp" theme="simple">
				<div id="center-3" style="height: auto">
					<h1>健身记录</h1>
					<div class="memdiv" style="padding-top: 10px; height: 250px;">
						<div style="padding-top: 10px;">
							<p class="divp" style="padding-left:15px; padding-top: 0;">
								运动日期：<input id="doneDate" name="record.doneDate" class="Data-Figure2" value="<s:date name="record.doneDate" format="yyyy-MM-dd"/>" />
										<img src="images/sar-1_03.jpg" align="absbottom" />
							</p>
						
							<p class="divp" style="text-align: left;">
								运动时长：<s:textfield name="record.times" maxlength="10" id="recordTimes" /> 分钟
							</p>
								
									<p class="divp"  style="padding-left: 15px;">
								有氧运动：<s:select name="record.action" id="recordAction" list="#request.yyxms" listKey="name" listValue="name"/>
							</p>
								
							<p class="divp">
								运动量：<s:textfield name="record.actionQuan" maxlength="10" id="recordActionQuan" /> 千米
							</p>
							<p class="p1" style="padding-top:18px;">
								<b>填写训练体会:</b>
							</p>
								
							<p class="p2">
								可以输入<span>200</span>个字
							</p>
							<s:textarea name="record.memo" cols="" rows="" id="recordMemo" />
						</div>
						<div style="margin-top: 50px; padding-top: 20px; height: 80px;">
							<p class="p1" style="padding-bottom: 20px;">
								<b>最新身体数据:</b>
							</p>
							<p class="divp" style="padding-left: 15px;">
								<a title="除去衣服后的重量。">体重：</a><s:textfield name="record.weight" maxlength="10" id="recordWeight" /> Kg
							</p>
							<p class="divp" style="text-align: left">
								<a title="从头顶点至地面的垂距。">身高：</a><s:textfield name="record.height" maxlength="10" id="recordHeight" /> cm
							</p>
							<p class="divp" style="padding-left: 15px;">
								<a title="直立，放松，皮尺放在腰最细的部分水平绕体一周测量。">腰围：</a><s:textfield name="record.waist" maxlength="10" id="recordWaist" /> cm
							</p>
							<p class="divp" style="text-align: left">
								<a title="两腿直立、双臂下垂，皮尺水平放在髋部左右大转子骨的尖端水平绕体一周测量。">臀围：</a><s:textfield name="record.hip" maxlength="10" id="recordHip" /> cm
							</p>
						</div>
					</div>
					<div style="clear: both; padding: 0; height: 1px;"></div>
					<p class="p1">
						<b>记录保存到以下健身挑战:</b>
					</p>
					<p class="c_check" style="line-height: 30px; float: left; padding: 10px 0 0 40px;">
						<s:checkboxlist list="#request.actives" listKey="id" listValue="name" name="ids"/>
					</p>
					<div style="clear: both; height: 1px; padding: 0;"></div>
					<dt class="p5" style="padding-bottom: 10px; padding-left: 75px;">
						<p>
							<input type="button" value="保存" class="butt" onclick="saveRecord();" />
							<input type="checkbox" id="syncWeibo" style="width: 14px; height: 14px; padding-left: 10px;" />
							同时发布到微博
						</p>
					</dt>
					<div id="divemotion" class="emtion" style="display: none;">
						<div class="xiaosj"></div>
						<div class="emtion-s"></div>
					</div>
				</div>
			</s:form>  --%>
		</div>
		<div id="right">
			<div id="right-1">
				<!--我已经进行了35次健身训练-->
				<div class="test">
					 <h1  style="padding-left:10px;">我的历程</h1> 
					 <span >健身次数:<span><s:property value="#request.workoutTimes"/>次</span></span><br />
					 <span >健身时间:<span><s:property value="#request.trainTimes"/>分钟</span></span><br />
					<!-- <span >健身次数:<strong><s:property value="#request.workoutTimes"/>次</strong></span><br />
					 <span >健身时间:<strong><s:property value="#request.trainTimes"/>分钟</strong></span><br />   --> 
					<!--  <span >课程完成率:<strong><s:property value="#request.trainRate"/></strong></span>   -->
				</div>

				<h2 class="teacher">我的私人教练</h2>
				<div id = "divCoach">
					<s:if test="member.coach.id != null && member.coach.id != ''">
					<dl>
						<dt>
							<a href="javascript:goMemberHome('<s:property value="member.coach.id"/>','S');">
							<s:if test="member.coach.image == ''"><img src="images/userpho.jpg"/></s:if><s:else><img src="picture/<s:property value="member.coach.image"/>" /></s:else>
							</a>
						</dt>
						<dd><s:property value="member.coach.name"/></dd>
					</dl>
					<s:if test="#session.loginMember.id == member.id">
					<input type="button" name="Submit" value="解除关系" class="button" onclick="relieve();"/>
					<input type="button" name="Submit" value="发消息" class="button" onclick="sendMsg(1);"/><!--如果有教练显示这两个按钮-->
					</s:if>
					</s:if>
					<s:else>
					<dl>
						<dt>
							<img src="images/userpho.jpg"/>
						</dt>
					</dl>
					<p class="private_teach">
						<s:if test="#session.loginMember.id == member.id">
						还没有私人教练<BR/><a href="coachlist.asp" id="colotoa">点击这里申请教练吧</a>
						</s:if>
						<s:else>
						该用户还没有申请私人教练
						</s:else> 
					
					</p><!--如果没有教练显示这句话-->
					</s:else>
				</div>
			</div>

			<%-- <div id="right-2">
				<!--我的体质分析-->
				<h1>我的体质分析</h1>
				<div>
					<ul>
						<li>目前体重：<span id="weightSpan"><s:property value="record.weight"/></span>(kg)</li>
						<li>目前体质指数：<span id="bmiSpan"><s:property value="#request.bmi"/></span></li>
						<li>建议：
							<span id="tishiBmi">
							<s:if test="#request.bmi !=null ">
							<s:if test="#request.bmi <= 0"><span id="tishi">请先到我的设定中维护您的身高体重。</span></s:if>
							<s:elseif test="#request.bmi < 18.5 && #request.bmi > 0"><span id="tishi">您的体重过轻，请增强营养，加强锻炼。</span></s:elseif>
							<s:elseif test="#request.bmi >= 18.5 && #request.bmi < 24"><span id="tishi">您的体重正常，健康风险最低，身体健康。</span></s:elseif>
							<s:elseif test="#request.bmi >= 24 && #request.bmi < 28"><span id="tishi">您的体重超重，患病风险正在加大。</span></s:elseif>
							<s:elseif test="#request.bmi > 28"><span id="tishi">您属于肥胖体型，属健康高危人群。</span></s:elseif>
							</s:if>
							</span>
						</li>
						<li>目前腰臀比：<span id="waistHipSpan"><s:property value="record.waistHip"/></span></li>
						<li>建议：
							<span id="tishiWaistHip">
							<s:if test="record.waistHip !=null ">
							<s:if test="member.sex == \"M\"">
								<s:if test="record.waistHip >= 0.94"><span id="tishi">您的腰臀比偏高，患病的概率比较大。</span></s:if><s:else><span id="tishi">您的腰臀比正常，身体健康。</span></s:else>
							</s:if>
							<s:if test="member.sex == \"F\"">
								<s:if test="record.waistHip >=0.82"><span id="tishi">您的腰臀比偏高，患病的概率比较大。</span></s:if><s:else><span id="tishi">您的腰臀比正常，身体健康。</span></s:else>								
							</s:if>
							</s:if>							
							</span>
						</li>
					</ul>
				</div>
			</div> --%>
		</div>
	</div>
	<s:include value="/share/footer.jsp"/>
	<div id="dialogMsg" title="发送消息" style="display: none;">
	<form id="msgForm" name="msgForm" method="post" action="message!save.asp">
		<input type="hidden" name="msg.type" value="2"/>
		<input type="hidden" name="msg.memberTo.id" value="<s:property value="member.id"/>" id="memberTo"/>
		<p>
			收件人：<span id="memberToName"><s:property value="member.nick"/></span>
		</p>
		<p class="message_content">
		<span style="float:left;" >消息内容：</span>
			<span><textarea name="msg.content"  class="send-message" onkeyup="this.value = this.value.substring(0, 140)"></textarea><span>
		</p>
		<p class="pa">
		   <input type="button" value="确定" onclick="onSendMsg();" class="butok"/>
		   <input type="button" value="取消" onclick="onCloseMsg();" class="butok"/>
		</p>
	</form>
	</div>
</body>
</html>
