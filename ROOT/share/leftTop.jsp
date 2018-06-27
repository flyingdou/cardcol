<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<script type="text/javascript">
	function onTurnToPage(typesort, code, name) {
		var url = '';
		if (typesort == "plantype") { //健身计划	
			url = "planlist.asp";
			jQuery("#ltarget").val(code);
			jQuery("#ltargetName").val(name);
		} else if (typesort == "cardtype") { //健身卡	
			url = "clublist.asp";
			jQuery("#lproType").val(code);
			jQuery("#lproTypeName").val(name);
		} else if (typesort == "coursetype") { //场馆预订
			url = "clublist.asp";
			jQuery("#ltypeId").val(code);
			jQuery("#ltypeName").val(name);
		} else if (typesort == "coachtype") { //健身教练
			url = "coachlist.asp";
			jQuery("#ltstyle").val(code);
			jQuery("#ltstyleName").val(name);
		} else {
			url = "index.asp";
		}
		$('#leftTopForm').attr('action', url);
		$('#leftTopForm').submit();
	}
</script>

<div>
	<s:form id="leftTopForm" name="leftTopForm" method="post"
		action="index.asp" theme="simple">
		<input type="hidden" name="ltarget" id="ltarget" />
		<input type="hidden" name="ltargetName" id="ltargetName" />
		<input type="hidden" name="lproType" id="lproType" />
		<input type="hidden" name="lproTypeName" id="lproTypeName" />
		<input type="hidden" name="ltypeId" id="ltypeId" />
		<input type="hidden" name="ltypeName" id="ltypeName" />
		<input type="hidden" name="ltstyle" id="ltstyle" />
		<input type="hidden" name="ltstyleName" id="ltstyleName" />


		<div class="item pcios nav_dang">
			<a href="planlist.asp"><span>健身计划</span></a>
		</div>
		<div class="item nav_item">
			<s:iterator value="#request.lt_planTypeList">
				<a
					href="javascript:onTurnToPage('plantype','<s:property value="code"/>','<s:property value="name"/>');"><s:property
						value="name" /></a>
			</s:iterator>
		</div>
		<div class="item pcios nav_cord">
			<a href="clublist.asp"><span>健身卡</span> </a>
		</div>
		<div class="item">
			<s:iterator value="#request.lt_cardtypeList">
				<a
					href="javascript:onTurnToPage('cardtype','<s:property value="code"/>','<s:property value="name"/>');"><s:property
						value="name" /></a>
			</s:iterator>
		</div>

		<div class="item pcios nav_yuding">
			<a href="clublist.asp"><span>场馆预订</span></a>
		</div>
		<div class="item nav_item">
			<s:iterator value="#request.lt_coursetypeList">
				<a
					href="javascript:onTurnToPage('coursetype','<s:property value="id"/>','<s:property value="name"/>');"><s:property
						value="name" /></a>
			</s:iterator>
		</div>

		<div class="item pcios nav_jiaolian">
			<a href="coachlist.asp"><span>健身教练</span></a>
		</div>
		<div class="item nav_item">
			<s:iterator value="#request.lt_coachtypeList">
				<a
					href="javascript:onTurnToPage('coachtype','<s:property value="code"/>','<s:property value="name"/>');"><s:property
						value="name" /></a>
			</s:iterator>
		</div>
	</s:form>
</div>

