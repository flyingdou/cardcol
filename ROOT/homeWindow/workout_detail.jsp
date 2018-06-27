<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script src="script/audio.js" type="text/javascript"></script>
<script type="text/javascript">
$(function(){
	$('input[name="course.doneDate"]').datepicker();
	_audio._init("btnPause");
	$('#video_dialog').dialog({autoOpen: false,width: 540, show: "blind", hide: "blind",modal:true,resizable: false});
});
function showVideo(_id, _name) {
	$(document).mask('请稍候，正在加载页面......');
	$.ajax({url: 'index!showVideo.asp', type: 'post', data: {id: _id}, 
		success: function(msg){
			$('#video_dialog').html(msg);
			$('#video_dialog').dialog('open');
			$('#video_dialog').dialog('option', 'title', _name);
			$(document).unmask();
		}
	});
}
</script>
<div>
    <table width="98%" cellspacing="1" cellpadding="0" border="0" class="table1" id="actionTable">
    	<thead>
	    <tr>
		   <th class="td1-1" width="50">顺序</th>
		   <th class="td1-1" width="130">训练动作</th>
		   <th class="td1-1" width="80">类型</th>
		   <th class="td1-1" width="380">运动负荷</th>
		   <th class="td1-1" width="80">操作</th>
		</tr>
		</thead>
		<tbody>
		<s:iterator value="#request.workouts" status="st">
		<tr>
			 <td>
			 	<s:hidden name="%{'workouts['+#st.index+'].action.id'}" />
			 	<s:hidden name="%{'workouts['+#st.index+'].sort'}" />
			 	<s:hidden name="%{'workouts['+#st.index+'].id'}" />
			 	<s:hidden name="%{'workouts['+#st.index+'].action.part.project.mode'}" />
				<div><s:if test="#st.first"><img src='images/allow5.gif' onclick='javascript:onDown(this);'/></s:if>
				<s:elseif test="#st.last"><img src='images/allow4.gif' onclick='javascript:onUp(this);'/></s:elseif>
				<s:else><img src='images/allow5.gif' onclick='javascript:onDown(this);'/><img src='images/allow4.gif' onclick='javascript:onUp(this);'/></s:else>
				</div>
			</td>
			<td><a
				href="javascript:showVideo(<s:property value="action.id"/>, '<s:property value="action.name"/>')"><s:property
						value="action.name" /></a></td>
			<td><s:property value="action.part.project.name"/></td>
			<td >
			   <div class="sports_plan">
			   <s:iterator value="details" status="sd">
				<s:if test="action.part.project.mode == '0'">
				第<s:property value="#sd.index + 1"/>组　距离：<s:property value="planDistance"/> KM　时间：<s:property value="planDuration"/> Minx　强度：<s:property value="intensity"/><br/>　
				</s:if><s:if test="action.part.project.mode == '1'">
				第<s:property value="#sd.index + 1"/>组　重量：<s:property value="planWeight"/> KG　重复次数：<s:property value="planTimes"/>　间隔：<s:property value="planIntervalSeconds"/>　secs<br/>　
				</s:if>
				<s:if test="action.part.project.mode == '2'">
				第<s:property value="#sd.index + 1"/>组　时间：<s:property value="planDuration"/>Minx　重复次数：<s:property value="planTimes"/><br/>　
				</s:if>
				 </s:iterator> 
				 </div>
			</td>
			<td>
			   <a class="ondelete" onclick="onDelete(this)" id="colotoa" >删除</a>
			   <a class="onedit" onclick="onEdit(this)" id="colotoa" >查看</a>
			</td>
		</tr>
		</s:iterator>
		</tbody>
	</table>
</div>
<div class="footdiv">
	<h2>
		<b>注意事项</b>
	</h2>
	<div>
		<s:textarea cssClass="text" name="course.memo" />
	</div>
</div>
<section class='u-audio' src='http://www.cardcol.com/<s:property value="course.music.addr"/>'></section>
<div id="video_dialog" title="" style="display: none;"></div>