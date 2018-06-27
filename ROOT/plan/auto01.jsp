<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div id="righttop">
	<div class="rightop_update" id="emptyMsg" style="display: block;">
		<h1 id="sprotjh">健身E卡通智能健身计划引擎</h1>
		<div class="baiot"></div>
		<div class="my-setting">
			<ul id="nextstep">
				<li><a href="javascript:onGuide(0, 0)">健身目的</a></li>
				<li id="firsts"><a href="javascript:onGuide(0, 1)">健身历史</a></li>
				<li><a href="javascript:onGuide(0, 2)">健身频率</a></li>
				<li><a href="javascript:onGuide(0, 3)">喜欢的有氧训练</a></li>
				<li><a href="javascript:onGuide(0, 4)">开始日期</a></li>
				<li><a href="javascript:onGuide(0, 5)">完成</a></li>
			</ul>
			<div>
				<s:form id="frmsetting" name="frmsetting" theme="simple">
				<div class="quest">
					<div class="quetion">
						<div class="que_li">
							<div class="quest-1">
								<input type="radio" name="params.currGymStatus" <s:if test="setting.currGymStatus == \"A\"">checked="checked"</s:if> value="A" id="radioA" />
								<label for="radioA">从来没有进行健身运动</label>
							</div>
							<div class="quest-1">
								<input type="radio" name="params.currGymStatus" <s:if test="setting.currGymStatus == \"B\"">checked="checked"</s:if> value="B" id="radioB" />
								<label for="radioB">参加健身运动少于6个月</label>
							</div>
							<div class="quest-1">
								<input type="radio" name="params.currGymStatus" <s:if test="setting.currGymStatus == \"C\"">checked="checked"</s:if> value="C" id="radioC"/>
								<label for="radioC">参加健身运动少于12个月</label>
							</div>
							<div class="quest-1">
								<input type="radio" name="params.currGymStatus" <s:if test="setting.currGymStatus == \"D\"">checked="checked"</s:if> value="D" id="radioD"/>
								<label for="radioD">已经很规律地进行了12个月以上的健身</label>
							</div>
						</div>
						<div class=""></div>
					</div>
					<div class="stepo">
						<a href="javascript:onGuide(0, 0)" title="上一步" class="butlost">上一步</a>
						<a href="javascript:onGuide(0, 2)" title="下一步" class="btnnext">下一步</a>
					</div>
				</div>
				</s:form>
			</div>
		</div>
	</div>
</div>
