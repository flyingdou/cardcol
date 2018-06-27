<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
$(function() {
	$('#details').tabs();
	$('#details').removeClass('ui-widget-content ui-corner-all');
	$('#details .ui-tabs-nav').removeClass('ui-widget-header ui-corner-all');
// 	$("#aa").dialog({autoOpen: false,modal:true,show: "blind",hide: "blind",resizable: false,width:720});
// 	$( "#colotoa" ).click(function() {
// 		var divs = $('#aa').children('div');
// 		$( "#aa" ).dialog( "open" );
// 		$('.ui-dialog-titlebar').css('display', 'none');
// 		return false;
// 	});
	$("#startDate").datepicker();
	$("#endDate").datepicker();
	$("#startDateb").datepicker();
	$("#endDateb").datepicker();
	$("#dialog").dialog({autoOpen: false, show: "blind", hide: "explode", resizable: false, width:450,modal:true});
	$("#opener").click(function() {
		$('#dialog').find(':text').val('');
		$('#dialog').find(':checkbox').attr("checked",false);
		$("#dialog").dialog("open");
	});
   	$("#clea").dialog({autoOpen: false, show: "blind", hide: "explode", resizable: false, width:450, modal: true});
	$("#clear").click(function() {$("#clea").dialog("open");});
   	$("#shar").dialog({autoOpen: false, show: "blind", hide: "explode", resizable: false, width:450, modal: true});
	$('#release').click(function(){
		var hasValid ='<s:property value="#session.loginMember.hasValid"/>';
		if(hasValid != null && hasValid == '1'){
			$('#divdetail').hide();
			$('#divplanset').show();
			$.ajax({type: 'post', url: 'release.asp', data: {type: 1}, 
				success: function(msg){
					$('#divplanset').html(msg);
				}
			});
		}else{
			alert('请您上传教练证书并申请验证，您的资格通过验证后才能发布计划');
		}
		
	});
	states = <s:property value="#request.monthStatus" escape="false"/>;
	setStatus();
	<s:if test="#request.courses.size() == 0">
		addCourse();
	</s:if>
});
// function onclose(){
// 	$('#aa').dialog('close');
// 	$('.ui-dialog-titlebar').css('display', 'block');
// }
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
/* function ondisplay(the,thee){
	if($('#scoursename'+the).css('display')!="none"){
		$('#scoursename'+the).css('display','none');$('#scoursetime'+the).css('display','none');
		$('#stime'+the).css('display','none');$('#saction'+the).css('display','none');$('#detail'+the).css('display','none');
	}else{
		editCourse(thee);
		$('#scoursename'+the).css('display','');$('#scoursetime'+the).css('display','');
		$('#stime'+the).css('display','');$('#saction'+the).css('display','');$('#detail'+the).css('display','');
	}
	
} */
$('.selectmember').change(function(){
	$(document).mask('请稍候，正在加载页面......');
	var member = $(this).val();
	$('#member').val(member);
	$('#memberId').val(member);
	var date = $('#planDate').val();
	$.ajax({type: 'post', url: 'workout!switchMember.asp', data: 'member=' + member + '&planDate=' + date, 
		success: function(msg){				
			$(document).unmask();
			$('#divdetail').html(msg);
		}
	});
});
</script>
<style type="text/css">
select {
	width: 80px;
	height: 20px;
	margin-bottom: 5px;
	border: 1px solid #ccc;
}
</style>
<s:form id ="queryForm" name="queryForm" method="post" action="shop!save.asp" theme="simple">
<s:hidden name="member.id" id="memberId"/>
<s:hidden name="shop.id" id="shopId"/>
<s:hidden name="product.id" id="productId"/>
</s:form>
<s:form name="planform" id="planform" theme="simple">
	<s:hidden name="member" id="member" />
	<s:hidden name="course.planDate" id="planDate" value="%{planDate}"/>
	<s:hidden name="course.id" id="courseId" />
	<s:hidden name="course.coach.id" id="coachId" />
	<s:hidden name="course.member.id" id="memberId" value="%{member}"/>
	<s:hidden name="course.costs" id="costs" />
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
	<s:hidden name="course.share" id="share1" />
	<s:hidden name="course.planSource" id="planSource" />
	<s:hidden name="course.music.id" id="musicId" />
	<div id="righttop">
		<div>
				 <div  style="float: left; margin-left: 0px; margin-top: 0px;
			  margin-bottom: -20px;">
						<s:if test="#session.loginMember.role ==\"S\"">
						<span >选择会员</span>：   <s:select
							list="#request.members" value="member" listKey="id"
							listValue="name" cssClass="selectmember" />
					</s:if>
					<s:if test="#session.loginMember.role ==\"M\"">
						<s:select list="#session.loginMember" value="member" listKey="id" listValue="nick" cssClass="selectmember"/>
				    </s:if>
			</div>
			<div id="divcourses" style="margin-top: 30PX" >
				<s:iterator value="#request.courses" status="st" var="c">
				<s:set name="disabled" value="false" />
				<s:if test="course.id != #c.id"><s:set name="disabled" value="true"/></s:if>
				<div id="<s:property value="id"/>" coachId="<s:property value="coach.id"/>" planDate="<s:property value="planDate"/>" 
					memberId="<s:property value="member.id"/>" doneDate="<s:property value="doneDate"/>" costs="<s:property value="costs"/>"
					memo="<s:property value="memo"/>" place="<s:property value="place"/>" color="<s:property value="color"/>"
					count="<s:property value="count"/>" joinNum="<s:property value="joinNum"/>" mode="<s:property value="mode"/>" 
					modeType="<s:property value="type"/>" modeValue="<s:property value="value"/>" weekOf="<s:property value="weekOf"/>" 
					repeatWhere="<s:property value="repeatWhere"/>" repeatStart="<s:property value="repeatStart"/>" repeatNum="<s:property value="repeatNum"/>" 
					repeatEnd="<s:property value="repeatEnd"/>" hasReminder="<s:property value="hasReminder"/>" reminder="<s:property value="reminder"/>" 
					groupBy="<s:property value="groupBy"/>" musicId="<s:property value="music.id"/>">
					<span id='scoursename<s:property value="id"/>'  >课程名称:</span><s:select name="course.courseInfo.id" list="#request.courseinfos" listKey="id" listValue="name" value="%{courseInfo.id}" id="courseInfoId" disabled="%{disabled}" cssStyle="min-width:100px;margin-left:13px;" onchange="changeTitle(this)" />
					<span id="scoursetime<s:property value="id"/>">课程时间:</span><s:select name="course.startTime" list="#request.times" listKey="key" listValue="value" id="startTime" value="%{startTime}" disabled="%{disabled}"/>
					-  <s:select name="course.endTime" list="#request.times" listKey="key" listValue="value" id="endTime" value="%{endTime}" disabled="%{disabled}" />
					<a href="#" class="inpa1" onclick="delCourse(this)">删除</a> 
					<%-- <s:if test="music == null || music == ''"><a href="#" class="inpa1" onclick="javascript:onMusic(this);" >配乐</a></s:if>
					<s:else><a href="#" class="inpa1" onclick="javascript:delMusic(this);" >删除配乐</a></s:else> --%>
					<a href="#" class="inpa1" onclick="editCourse(this)">编辑健身计划</a> 
					<!-- <input type="button" value="开始训练"> -->
				<%-- <p>
					<input type="button" value="转换图片" onclick="javascript:ondisplay(<s:property value="id"/>,this);">
				</p> --%>
				<br>
					循环次数：
					<s:select list="#{1:'1',2:'2',3:'3',4:'4',5:'5',6:'6',7:'7',8:'8',9:'9',10:'10'}" name="course.cycleCount" listKey="key" 
					listValue="value"  theme="simple" id="cycleCount" cssStyle="border:1px solid #ccc; width:100px; height:20px;" disabled="%{disabled}" value="%{cycleCount}"/>
					<span>播放倒计时:</span>
					<s:select list="#{1:'1',2:'2',3:'3',4:'4',5:'5',6:'6',7:'7',8:'8',9:'9',10:'10'}" name="course.countdown" listKey="key" 
					listValue="value"  theme="simple" id="countdown" cssStyle="border:1px solid #ccc; width:80px; height:20px;" disabled="%{disabled}" value="%{countdown}"/>
					<s:if test="music == null || music == ''">	<input type="button" value="配乐" onclick="javascript:onMusic(this);"></s:if>
					<s:else><input type="button" value="删除配乐" onclick="javascript:delMusic(this);"></s:else>
				
				<%-- <p class="copy" id="saction<s:property value="id"/>">
					<input type="button" name="save" class="inpsave" value="保存" onclick="javascript:onSave()"/>
					<input type="button" name="remove" class="inpremove" value="复制" id="opener"/>
					<input type="button" name="" class="inpclear" value="删除"   onclick="delCourse(this)"/>
					<a href="#" class="inpa1" onclick="editCourse(this)">编辑健身计划</a>
				</p> --%>
				
					<%-- <div id="detail<s:property value="id"/>" style="<s:if test="course == null">display:none;</s:if><s:else>display:block;</s:else>">
						<div id="right-2">
							<s:include value="/plan/workout_detail.jsp"></s:include>
						</div>
						<div>
							<a class="inpa2" onclick="addCourse()">[+新增课程]</a>
						</div>
					</div> --%>
				</div>
				</s:iterator>
			</div>
			<!-- <div>
				<a class="inpa2" onclick="addCourse()">[+新增课程]</a>
			</div> -->
		</div>
	</div>
	
 	<div id="detail" style="<s:if test="course == null">display:none;</s:if><s:else>display:block;</s:else>">
		 <div id="details">
			<ul class="health">
				<li> <a href="#right-2" class="active" id="courseTitle"><s:if test="course == null">健身计划</s:if><s:else><s:property value="course.courseInfo.name"/></s:else></a></li>
			</ul>
						
			<div id="right-2">
				<s:include value="/plan/workout_detail.jsp"></s:include>
			</div>
		</div> 
		<%-- <div id="right-2">
				<s:include value="/plan/workout_detail.jsp"></s:include>
			</div>--%>
		<div> 
				<a class="inpa2" onclick="addCourse()">[+新增课程]</a>
		</div>
		<p class="copy">
			<input type="button" name="save" class="inpsave" value="保存计划" onclick="javascript:onSave()"/>
			<input type="button" name="remove" class="inpremove" value="复制计划" id="opener"/>
			<input type="button" name="remove" class="inpclear" value="清除计划"  id="clear" />
			 <s:if test="#session.loginMember.role == \"S\"">
			<input type="button" name="share" class="inpshare" value="发布计划"  id="release"/>
			</s:if>
			<input type="button" name="print" class="inpprint" value="打印计划" />
		</p>
	</div>
</s:form>
