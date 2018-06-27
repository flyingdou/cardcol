<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div id="righttop">
	<div class="rightop_update" id="emptyMsg" style="display: block;">
		<h1 id="sprotjh">健身E卡通智能健身计划引擎</h1>
		<div class="baiot"></div>
		<div class="my-setting">
			<ul id="nextstep">
				<li id="first1"><a href="javascript:onGuide(0, 0)">健身目的</a></li>
				<li><a href="javascript:onGuide(0, 1)">健身历史</a></li>
				<li><a href="javascript:onGuide(0, 2)">健身频率</a></li>
				<li><a href="javascript:onGuide(0, 3)">喜欢的有氧训练</a></li>
				<li><a href="javascript:onGuide(0, 4)">开始日期</a></li>
				<li><a href="javascript:onGuide(0, 5)">完成</a></li>
			</ul>
			<div class="div1-1">
				<s:form name="frmsetting" id="frmsetting" theme="simple">
					<div class="pcili">
						<ul>
							<li><img src="images/weight.jpg" width="130" height="135" />
								<input type="radio" name="params.target" <s:if test="setting.target == \"1\"">checked="checked"</s:if> value="1" id="radio1"/> <label for="radio1">减脂塑形</label>
							</li>
							<li><img src="images/Muscle.jpg" width="130" height="135" />
								<input type="radio" name="params.target" <s:if test="setting.target == \"2\"">checked="checked"</s:if> value="2" id="radio2" /> <label for="radio2">健美增肌</label>
							</li>
							<li><img src="images/fitness.jpg" width="130" height="135" />
								<input type="radio" name="params.target" <s:if test="setting.target == \"3\"">checked="checked"</s:if> value="3" id="radio3" /> <label for="radio3">促进健康</label>
							</li>
							<li><img src="images/sport.jpg" width="130" height="135" />
								<input type="radio" name="params.target" <s:if test="setting.target == \"4\"">checked="checked"</s:if> value="4" id="radio4" /> <label for="radio4">提高运动表现</label>
							</li>
						</ul>
					</div>
					<div class="stepo">
						<div class="stepnext">
							<a href="javascript:onGuide(0, 1)" title="下一步" class="btnnext">下一步</a>
						</div>
					</div>
				</s:form>
			</div>
		</div>
	</div>
</div>
