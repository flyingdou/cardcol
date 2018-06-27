<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
states = <s:property value="#request.monthStatus" escape="false"/>;
$(function() {
	$('#details').tabs();
	$('#details').removeClass('ui-widget-content ui-corner-all');
	$('#details .ui-tabs-nav').removeClass('ui-widget-header ui-corner-all');
	$('#addCourse').click(function(){
		$('#divcourses').children('div').each(function(){
			$(this).children('select').attr('disabled', true);
		});
		var len = $('#divcourses').children('div').length;
		var html = $('#sampleCourse').html().replace(new RegExp("XXXX", "gm"), len + "");
		$('#divcourses').append(html);
		$('#detail').css('display', 'block');
		$('#emptyMsg').css('display', 'none');
		$('#courseId').val('');
		$('#coachId').val('');
		$('#doneDate').val('');
		$('#costs').val('');
		$('#memo').val('');
		$('#place').val('');
		$('#color').val('');
		$('#count').val('');
		$('#joinNum').val('');
		$('#mode').val('');
		$('#modeType').val('');
		$('#modeValue').val('');
		$('#weekOf').val('');
		$('#repeatWhere').val('');
		$('#repeatStart').val('');
		$('#repeatNum').val('');
		$('#repeatEnd').val('');
		$('#hasReminder').val('');
		$('#reminder').val('');
		$('#groupBy').val('');
		$('#actionTable').children('tbody').empty();
	});
	$("#aa").dialog({autoOpen: false,modal:true,show: "blind",hide: "blind",resizable: false,width:720});
	$( "#colotoa" ).click(function() {
		$( "#aa" ).dialog( "open" );
		return false;
	});
	$('.inpsave1').click(function(){
		loadMask();
		var params = $('#planform').serialize();
		$.ajax({type: 'post', url: 'workoutwindow!save.asp', data: params, 
			success: function(html){
				removeMask();
				$('#divdetail').html(html);	
			}
		});
	});
	$("#startDate").datepicker();
	$("#endDate").datepicker();
	$("#startDateb").datepicker();
	$("#endDateb").datepicker();
	$("#dialog").dialog({autoOpen: false, show: "blind", hide: "explode", resizable: false, width:450,modal:true});
	$("#opener").click(function() {$("#dialog").dialog("open");});
   	$("#clea").dialog({autoOpen: false, show: "blind", hide: "explode", resizable: false, width:450, modal: true});
	$("#clear").click(function() {$("#clea").dialog("open");});
   	$("#shar").dialog({autoOpen: false, show: "blind", hide: "explode", resizable: false, width:450,modal: true});
	$("#share").click(function() {$("#shar").dialog("open");});
	setStatus();
});
function onclose(){
	$('#aa').dialog('close');
}
function preShop(productId,memberId){
	if("<s:property value="#session.loginMember.id"/>" == ""){
		//alert("未登录用户不能购买商品！");
		openLogin();
		return;
	}
	if (confirm("确认要成为高级会员？")) {
		$('#productId').val(productId);
		$('#queryForm').attr('action','shop!save.asp');
		$('#queryForm').submit();
	}
}
</script>
<style type="text/css">
 select {
  width:80px;
  height:20px;
  margin-bottom:5px;
  border:1px solid #ccc;
 }
</style>
<s:form name="planform" id="planform" theme="simple">
	<s:hidden name="toMember.id" id="toMemberId" />
	<s:hidden name="toMember.role" id="toMemberRole" />
	<s:hidden name="member" id="member" />
	<s:hidden name="course.planDate" id="planDate" value="%{planDate}"/>
	<s:hidden name="course.id" id="courseId" />
	<s:hidden name="course.coach.id" id="coachId" />
	<s:hidden name="course.member" id="memberId" value="%{member}"/>
	<s:hidden name="course.costs" id="costs" />
	<s:hidden name="course.memo" id="memo" />
	<s:hidden name="course.place" id="place" />
	<s:hidden name="course.color" id="color" />
	<s:hidden name="course.count" id="count" />
	<s:hidden name="course.joinNum" id="joinNum" />
	<s:hidden name="course.mode" id="mode" />
	<s:hidden name="course.type" id="modeType" />
	<s:hidden name="course.value" id="modeValue" />
	<s:hidden name="course.weekOf" id="weekOf" />
	<s:hidden name="course.repeatWhere" id="repeatWhere" />
	<s:hidden name="course.repeatStart" id="repeatStart" />
	<s:hidden name="course.repeatNum" id="repeatNum" />
	<s:hidden name="course.repeatEnd" id="repeatEnd" />
	<s:hidden name="course.hasReminder" id="hasReminder" />
	<s:hidden name="course.reminder" id="reminder" />
	<s:hidden name="course.groupBy" id="groupBy" />
	<div id="righttop">
		<div>
			<div id="divcourses">
				<s:iterator value="#request.courses" status="st">
				<s:param name="disabled" value="disabled"/>
				<div id="<s:property value="id"/>" coachId="<s:property value="coach.id"/>" planDate="<s:property value="planDate"/>" 
					memberId="<s:property value="member"/>" doneDate="<s:property value="doneDate"/>" costs="<s:property value="costs"/>"
					memo="<s:property value="memo"/>" place="<s:property value="place"/>" color="<s:property value="color"/>"
					count="<s:property value="count"/>" joinNum="<s:property value="joinNum"/>" mode="<s:property value="mode"/>" 
					modeType="<s:property value="type"/>" modeValue="<s:property value="value"/>" weekOf="<s:property value="weekOf"/>" 
					repeatWhere="<s:property value="repeatWhere"/>" repeatStart="<s:property value="repeatStart"/>" repeatNum="<s:property value="repeatNum"/>" 
					repeatEnd="<s:property value="repeatEnd"/>" hasReminder="<s:property value="hasReminder"/>" reminder="<s:property value="reminder"/>" 
					groupBy="<s:property value="groupBy"/>">
					课程名称：<s:select name="course.courseInfo.id" list="#request.courseinfos" listKey="id" listValue="name" value="%{courseInfo.id}" id="courseInfoId" cssStyle="disabled:%{disabled}; width:120px;" />
					<span>开始时间:</span><s:select name="course.startTime" list="#request.times" listKey="key" listValue="value" id="startTime" value="%{startTime}"/>
					<span>结束时间:</span><s:select name="course.endTime" list="#request.times" listKey="key" listValue="value" id="endTime" cssStyle="" value="%{endTime}" />
					<s:if test="isChange ==  \"Y\"">
					<a class="inpa1" onclick="delCourse(this)">删除</a> 
					</s:if>
					<a href="#" class="inpa1" onclick="editCourse(this)">编辑健身计划</a>
				</div>
				</s:iterator>
			</div>
			<s:if test="isChange ==  \"Y\"">
			<div>
				<a id="addCourse" class="inpa2">[+新增课程]</a>
				<a id="colotoa">查看帮助</a>
			</div>
			</s:if>
		</div>
		<div class="div1" id="emptyMsg" style="<s:if test="course == null">display:block;</s:if><s:else>display:none;</s:else>">
	         <div class="div1-1">
				  <h3>健身E卡通为您提供了三种制订健身计划的方式</h3>
				  <h4>1、系统自动生成健身计划</h4>
				  <p>在本页左侧点击“王严健身专家系统”或“健身E卡通智能健身计划引擎”，在商品详情页填写您的身体数据并完成支付后，可以得到系统自动生成的个性化健身方案。</p>
				  <h4>2、聘请私人教练制订健身计划</h4>
				  <p>你可以在健身E卡通中选择一位适合自己的健身教练，在支付了该教练的课时费以后，教练将为您提供一对一的健身指导服务。【<a href="coachlist.asp">我要聘请教练</a>】</p>
				  <h4>3、自己制订健身计划</h4>
				  <p>如果您是一位健身专家，掌握了正确的训练方法，可以使用健身E卡通自己制订计划、记录训练过程。自己制订计划的方法如下:</p>
				  <p class="pdiv">(1)点击本页面上面的“新增课程”，输入课程名称、时间；</p>
				  <p class="pdiv">(2)在本页面左侧“添加健身项目”面板中选择并添加健身项目（动作），形成健身计划表；</p>
				  <p class="pdiv">(3)在健身计划表中点击训练动作后面的“编辑”</p>
				  <p class="pdiv">(4)在弹出的训练动作详情窗口中填写计划的训练动作负荷数据并保存；</p>
				  <p class="pdiv"> (5)在健身计划表页面点击“保存计划”按钮。</p>
				  <p class="pdiv">(6)训练结束后，在训练动作详情窗口中填写实际训练动作负荷数据并保存。</p>
			 </div>
			 <div class="div1-3">
			      <h3>温馨提示：</h3>
				  <p>(1)如果一天中有2个以上的课程，必须点击“编辑健身计划”，才能选择正确的计划表；</p>
				  <p>(2)可以使用“复制计划”的功能，将某天的计划复制到其它日期；</p>
				  <p>(3)点击“分享计划”后，可以将当天的计划分享给其他用户浏览。</p>
				  <p>(4)点击“清除计划”，可以将设定起止时间的计划内容清除掉。</p>
			 </div>
		</div>
	</div>
	
	<div id="detail" style="<s:if test="course == null">display:none;</s:if><s:else>display:block;</s:else>">
		<p class="copy">
			<s:if test="isChange ==  \"Y\"">
			<input type="button" name="save" class="inpsave" value="保存计划" onclick="javascript:onSave()"/>
			<input type="button" name="remove" class="inpremove" value="复制计划" id="opener"/>
			<input type="button" name="remove" class="inpclear" value="清除计划"  id="clear" />
			<input type="button" name="share" class="inpshare" value="分享计划"  id="share"/>
			</s:if>
			<input type="button" name="print" class="inpprint" value="打印计划" />
		</p>
		<div id="details">
			<ul class="health">
				<li><a href="#right-2" class="active">健身计划</a></li>
			</ul>
			<div id="right-2">
				<s:include value="/homeWindow/workout_detail.jsp"></s:include>
			</div>
		</div>
	</div>
</s:form>

<div id="aa">
	<div class="div1-1">
		<h3>健身E卡通为您提供了三种制订健身计划的方式<span onclick="onclose()"><a>X</a></span></h3>
		<h4>1、系统自动生成健身计划</h4>
		<p>在本页左侧点击“王严健身专家系统”或“健身E卡通智能健身计划引擎”，在商品详情页填写您的身体数据并完成支付后，可以得到系统自动生成的个性化健身方案。</p>
		<h4>2、聘请私人教练制订健身计划</h4>
		<p>你可以在健身E卡通网中选择一位适合自己的健身教练，在支付了该教练的课时费以后，教练将为您提供一对一的健身指导服务。【<a href="#">我要聘请教练</a>】</p>
		<h4>3、自己制订健身计划</h4>
		<p>如果您是一位健身专家，掌握了正确的训练方法，可以使用健身E卡通自己制订计划、记录训练过程。自己制订计划的方法如下:</p>
		<p class="pdiv">(1)点击本页面上面的“新增课程”，输入课程名称、时间；</p>
		<p class="pdiv">(2)在本页面左侧“添加健身项目”面板中选择并添加健身项目（动作），形成健身计划表；</p>
		<p class="pdiv">(3)在健身计划表中点击训练动作后面的“编辑”</p>
		<p class="pdiv">(4)在弹出的训练动作详情窗口中填写计划的训练动作负荷数据并保存；</p>
		<p class="pdiv">(5)在健身计划表页面点击“保存计划”按钮。</p>
		<p class="pdiv">(6)训练结束后，在训练动作详情窗口中填写实际训练动作负荷数据并保存。</p>
	</div>
	<div class="div1-3">
		<h3>温馨提示：</h3>
		<p>(1)如果一天中有2个以上的课程，必须点击“编辑健身计划”，才能选择正确的计划表；</p>
		<p>(2)可以使用“复制计划”的功能，将某天的计划复制到其它日期；</p>
		<p>(3)点击“分享计划”后，可以将当天的计划分享给其他用户浏览。</p>
		<p>(4)点击“清除计划”，可以将设定起止时间的计划内容清除掉。</p>
	</div>
</div>