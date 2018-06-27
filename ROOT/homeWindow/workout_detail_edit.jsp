<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript" src="script/jquery.flash.js" ></script>
<script type="text/javascript">
var mode = <s:property value="#request.workout.action.part.project.mode"/>;
var workId = <s:property value="#request.workout.id"/>;
$(function(){
	$('#divdh').flash({src: '<s:property value="#request.workout.action.flash"/>'});
});
function onPlay(){
	_audio.audio_play();
}
</script>
	<s:form theme="simple" name="editform" id="editform">
	<div class="altleft">
		<div class="altleft1">
			<h1><s:property value="#request.workout.action.name"/></h1>
			<div id="divimage"><img src="<s:property value="#request.workout.action.image"/>"/></div>
		</div>
		<div class="altleft2" id="divdh" style="background-color: #000000;"></div>
		<a onclick="cloalt()" >X</a>
	</div>
	<div class="altright">
		<div class="altright">
			<div class="altright1">
				<h2>
					<b>运动负荷数据表</b><!--<span><a href="#" id="colotoa">查看动作描述</a></span>-->
				</h2>
				
				<s:if test="#request.workout.action.part.project.mode == '0'">
				<div>
				<table cellspacing="1" cellpadding="0" border="0" class="table1" id="tabgroup">
					<tr>
						<td colspan="2" id="tdtop" class="tda">计划</td>
						<td id="tdtop" rowspan="2" class="tdc">运动强度</td>
						<td colspan="2" id="tdtop" class="tda">实际</td>
						<td rowspan="2" id="tdtop"  class="tdb">删除</td>
					</tr>
					<tr>
						<td id="tdtop">距离m</td>
						<td id="tdtop">时间minx</td>
						<td id="tdtop">距离m</td>
						<td id="tdtop">时间minx</td>
					</tr>
					<s:iterator value="details" status="st">
					<tr>
						<td> <div style="word-break:break-all;padding-left:10px;"><s:textfield name="%{'details['+#st.index+'].planWeight'}"/>
							<s:hidden name="%{'details['+#st.index+'].id'}"/>
							<s:hidden name="%{'details['+#st.index+'].workout.id'}"/></div>
						</td>
						<td> <div style="word-break:break-all;padding-left:10px;"><s:textfield name="%{'details['+#st.index+'].planTimes'}" /></div></td>
						<td> <div style="word-break:break-all;padding-left:10px;"><s:textfield name="%{'details['+#st.index+'].intensity'}"/></div></td>
						<td> <div style="word-break:break-all;padding-left:10px;"><s:textfield name="%{'details['+#st.index+'].actualWeight'}"/></div></td>
						<td> <div style="word-break:break-all;padding-left:10px;"><s:textfield name="%{'details['+#st.index+'].actualTimes'}" /></div></td>
						<td> <div style="word-break:break-all;padding-left:10px;"><a href="#" onclick="onDelGroup(this)"id="delect"></a></div></td>
					</tr>
					</s:iterator>
				</table>
				</div>
				</s:if>
				<s:elseif test="#request.workout.action.part.project.mode == '1'">
				<div>
				<table  cellspacing="1" cellpadding="0" border="0" class="table1" id="tabgroup">
					<tr>
						<td colspan="3" id="tdtop">计划</td>
						<td colspan="3" id="tdtop" >实际</td>
						<td rowspan="2" width="50" id="tdtop">删除</td>
					</tr>
					<tr>
						<td id="tdtop" width="67">重量kg</td>
						<td id="tdtop" width="66">次数</td>
						<td id="tdtop" width="66">间隔sec</td>
						<td id="tdtop" width="66">重量kg</td>
						<td id="tdtop" width="66">次数</td>
						<td id="tdtop" width="66">间隔sec</td>
					</tr>
					<s:iterator value="details" status="st">
					<tr>
						<td><s:textfield name="%{'details['+#st.index+'].planWeight'}"/>
							<s:hidden name="%{'details['+#st.index+'].id'}"/>
							<s:hidden name="%{'details['+#st.index+'].workout.id'}"/>
						</td>
						<td><s:textfield name="%{'details['+#st.index+'].planTimes'}" /></td>
						<td><s:textfield name="%{'details['+#st.index+'].planIntervalSeconds'}"/></td>
						<td><s:textfield name="%{'details['+#st.index+'].actualWeight'}"/></td>
						<td><s:textfield name="%{'details['+#st.index+'].actualTimes'}" /></td>
						<td><s:textfield name="%{'details['+#st.index+'].actualIntervalSeconds'}" /></td>
						<td><a href="#" onclick="onDelGroup(this)" id="delect"></a></td>
					</tr>
					</s:iterator>
				</table>
				</div>
				</s:elseif>
				<s:elseif test="#request.workout.action.part.project.mode == '2'">
				<div>
				<table  cellspacing="1" cellpadding="0" border="0" class="table1" id="tabgroup">
					<tr>
						<td colspan="2" id="tdtop" width="265">计划</td>
						<td colspan="2" id="tdtop" width="265">实际</td>
						<td rowspan="2" width="50" id="tdtop">删除</td>
					</tr>
					<tr>
						<td id="tdtop">时间sec</td>
						<td id="tdtop">重复次数</td>
						<td id="tdtop">时间sec</td>
						<td id="tdtop">重复次数</td>
					</tr>
					<s:iterator value="details" status="st">
					<tr>
						<td><s:textfield name="%{'details['+#st.index+'].planWeight'}"/>
							<s:hidden name="%{'details['+#st.index+'].id'}"/>
							<s:hidden name="%{'details['+#st.index+'].workout.id'}"/>
						</td>
						<td><s:textfield name="%{'details['+#st.index+'].planTimes'}" /></td>
						<td><s:textfield name="%{'details['+#st.index+'].actualWeight'}"/></td>
						<td><s:textfield name="%{'details['+#st.index+'].actualTimes'}"/></td>
						<td><a href="#" onclick="onDelGroup(this)" id="delect"></a></td>
					</tr>
					</s:iterator>
				</table>
				</div>
				</s:elseif>
				
				<div class="altdiv" id="divdescr">动作描述：在本页面左侧“添加健身项目”面板中选择并添加健身项目（动作），形成健身计划表在本页面左侧“添加健身项目”面板中选择并添加健身项目（动作），形成健身计划表</div>
				<div class="altin" id="divoper">
					<input type="button" name="remove" class="frist" value="上一个动作" onclick="onPrev(<s:property value="#request.workout.id"/>)"/>
					<input type="button" name="print" class="last" value="下一个动作" onclick="onNext(<s:property value="#request.workout.id"/>)"/>
				</div>
			</div>
		</div>
	</div>
	</s:form>
