<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<s:form id="form" name="form" action="" method="post" theme="simple">
	<s:hidden name="member.id"/>
	<div class="time1">
		<p class="time1-1">1、设定工作日期</p>
		<ul class="time1-2">
			<s:checkboxlist id="workDate" value="#request.list" list="#{'1':'星期日','2':'星期一','3':'星期二','4':'星期三','5':'星期四','6':'星期五','7':'星期六'}" listKey="key" listValue="value" name="member.workDate"/>
		</ul>
	</div>
	<div class="time2">
		<p class="time2-1">2、设定工作日内的关闭时段</p>
		<ul class="time2-2">
			<s:iterator value="worktimes" status="st">
			<li>从<s:select list="#request.times" listKey="key" listValue="value" name="%{'worktimes['+#st.index+'].startTime'}"/>
				到<s:select list="#request.times" listKey="key" listValue="value" name="%{'worktimes['+#st.index+'].endTime'}"/>
				<a onclick="javascript: onDelete(this);">删除</a>
				<s:hidden name="%{'worktimes['+#st.index+'].id'}"/>
			</li>
			</s:iterator>
		</ul>
	</div>
</s:form>
