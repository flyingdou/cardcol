<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
	$(function() {
		$('input[name="course.doneDate"]').datepicker({
			minDate : '<s:property value="planDate"/>'
		});
		if ($('#audio1').attr('id') === 'audio1') {
			var audio = $('#audio1');
			$('#source1').attr('src', 'http://www.cardcol.com/<s:property value="course.music.addr"/>');
			audio[0].pause();
			audio[0].load();
			audio[0].play();
		} else {
			var strVideo = '<audio id="audio1" loop="loop" preload="auto" autoplay="true">';
			strVideo += '<source id="source1"/></audio>';
			$(strVideo).insertAfter($(document.body));
		}
// 		if (_audio._inited === false) {
// 			_audio._init('btnPause', 'http://www.cardcol.com/<s:property value="course.music.addr"/>');
// 		}
// 		_audio.audio_load('http://www.cardcol.com/<s:property value="course.music.addr"/>');
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
		<s:hidden name="course.courseInfo" />
		
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
			<s:iterator value="workouts" status="st">
				<tr>
					<td><s:hidden name="%{'workouts['+#st.index+'].action.id'}" />
						<s:hidden name="%{'workouts['+#st.index+'].sort'}" /> <s:hidden
							name="%{'workouts['+#st.index+'].id'}" /> <s:hidden
							name="%{'workouts['+#st.index+'].action.part.project.mode'}" />
						<div>
							<s:if test="#st.first">
								<img src='images/allow5.gif' onclick='javascript:onDown(this);' />
							</s:if>
							<s:elseif test="#st.last">
								<img src='images/allow4.gif' onclick='javascript:onUp(this);' />
							</s:elseif>
							<s:else>
								<img src='images/allow5.gif' onclick='javascript:onDown(this);' />
								<img src='images/allow4.gif' onclick='javascript:onUp(this);' />
							</s:else>
						</div></td>
					<td><a
						href="javascript:showVideo(<s:property value="action.id"/>, '<s:property value="action.name"/>')"><s:property
								value="action.name" /></a></td>
					<td><s:property value="action.part.project.name" /></td>
					<td><s:if test="(details.size()>0)">
							<div
								style="text-align: left; text-indent: 16px; margin-top: 15px;">
								<s:iterator value="details" status="sd">
							第<s:property value="#sd.index + 1" />组　重量：<s:property
										value="planWeight" />　距离：<s:property value="planDistance" />　
							次数：<s:property value="planTimes" /> 时间：<s:property
										value="planDuration" /> 间隔：<s:property
										value="planIntervalSeconds" />
									<br />
								</s:iterator>
							</div>
						</s:if> <s:else>请点击右侧编辑按钮编制计划内容</s:else></td>
					<td><a onclick="javascript:onEdit(this);" id="colotoa">编辑</a>
						<a onclick="javascript:onDelete(this);" id="colotoa">删除</a></td>
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
		<s:textarea cssClass="text" name="course.memo" id="memo" />
	</div>
</div>
<div id="video_dialog" title="" style="display: none;"></div>