<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
var mode = <s:property value="#request.workout.action.part.project.mode"/>;
var workId = <s:property value="#request.workout.id"/>;

function onPlay(){
	_audio.audio_play();
}
</script>
	<s:form theme="simple" name="editform" id="editform">
	<div class="altleft" style="margin-left:7px;margin-right:7px; margin-top:5px;height:260px;">
<!-- 	<div class="altleft1"> -->
<%-- 			<div id="divimage"><img src="<s:property value="#request.workout.action.image"/>"/></div> --%>
<!-- 		</div> -->
<!-- 		<div class="altleft2" id="divdh" style="background-color: #000000;"></div> -->
		<div class="action_anm" id = "playercontainer1"></div>
		<script type="text/javascript">
			var player = cyberplayer("playercontainer1").setup({ 
				width : "96%", height : 270, 
				backcolor : "#FFFFFF", 
				stretching : "uniform", 
				file : "<s:property value="#request.videoSignPath"/>",							
				autoStart : true, 
				repeat : "always", 
				volume : 100,
				controlbar : "top", 
				ak : "vtpI8z2Q4G1KvSKunvQaRDKK", 
				sk : "uuB5rlXoAWxdcxUsRn3sN8PmWooKdeh1"
			}); 
		</script>
		<a style="float:left; display:none; margin-top:-7px;margin-left:5px;font-size:13px; font-weight:bold; cursor:pointer; color:#333;" onclick="cloalt()" >X</a>
	</div>
	<div>
<%-- 	<audio style="width:100%;" autoplay="autoplay" loop="loop" controls="controls" src="http://bcs.duapp.com<s:property value="#request.audioSignPath"/>"> --%>
<!-- 		您的浏览器不支持 audio 标签。 -->
<!-- 	</audio> -->
	</div>
	<div class="altright">
		<div class="altright">
			<div class="altright1">
				<h2>
					<b id="sxg">运动负荷数据表</b><!--<span><a href="#" id="colotoa">查看动作描述</a></span>-->
				</h2>
				<div style="width:455px;">
				<table cellspacing="1" cellpadding="0" border="0" class="table1" id="tabgroup" style="margin-top:5px;">
					<tr>
						<td id="tdtop" >次数</td>
						<td id="tdtop" >时间min</td>
						<td id="tdtop" >重量kg</td>
						<td id="tdtop" >距离km</td>
						<td id="tdtop">间隔sec</td>
						<!-- <td id="tdtop">开始铃</td>
						<td id="tdtop">间隔铃</td> -->
						<td id="tdtop">删除</td>
					</tr>
					<s:iterator value="details" status="st">
					<tr>
						<td><s:textfield name="%{'details['+#st.index+'].planTimes'}"/></td>
						<td><s:textfield name="%{'details['+#st.index+'].planDuration'}"/></td>
						<td><s:textfield name="%{'details['+#st.index+'].planWeight'}"/>
							<s:hidden name="%{'details['+#st.index+'].id'}"/>
							<s:hidden name="%{'details['+#st.index+'].workout.id'}"/>
						</td>
						<td><s:textfield name="%{'details['+#st.index+'].planDistance'}" /></td>
						<td><s:textfield name="%{'details['+#st.index+'].planIntervalSeconds'}" /></td>
						<%-- <td><s:select list="#{1:'铃声1',2:'铃声2',3:'铃声3',4:'铃声4',5:'铃声5',6:'铃声6',7:'铃声7',8:'铃声8',9:'铃声9'}" listKey="key" listValue="value"></s:select> </td>
						<td><s:select list="#{1:'铃声1',2:'铃声2',3:'铃声3',4:'铃声4',5:'铃声5',6:'铃声6',7:'铃声7',8:'铃声8',9:'铃声9'}" listKey="key" listValue="value"></s:select></td> --%>
						<td><a href="#" onclick="onDelGroup(this)"id="delect"></a></td>
					</tr>
					</s:iterator>
					<tr>
						<td colspan="6"><a href="#" id="coloa" title="增加组" class="addgroup" onclick="onAddGroup(this);">增加组</a></td>
					</tr>
				</table>
				</div>				
				<div class="altdiv" id="divdescr" style="text-overflow: ellipsis; overflow: hidden;"><s:property value="#request.workout.action.descr"/></div>
				<div class="altin" id="divoper">
					<input type="button" name="remove" class="frist" value="上个动作" onclick="onPrev(<s:property value="#request.workout.id"/>)"/>
			        <input type="button" name="pause" id="btnPause" class="second" value="音乐暂停" onclick="onPlay()"/>
					<input type="button" name="share" class="serv" value="保存" onclick="saveGroup()" />
					<input type="button" name="print" class="last" value="下个动作" onclick="onNext(<s:property value="#request.workout.id"/>)"/>
				</div>
			</div>
		</div>
	</div>
	</s:form>
