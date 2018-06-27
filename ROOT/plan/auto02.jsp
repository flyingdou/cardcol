<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div id="righttop">
	<div class="rightop_update" id="emptyMsg" style="display: block;">
		<h1 id="sprotjh">健身E卡通智能健身计划引擎</h1>
		<div class="baiot"></div>
		<div class="my-setting">
			<ul id="nextstep">
				<li><a href="javascript:onGuide(0, 0)">健身目的</a></li>
				<li><a href="javascript:onGuide(0, 1)">健身历史</a></li>
				<li id="firsts"><a href="javascript:onGuide(0, 2)">健身频率</a></li>
				<li><a href="javascript:onGuide(0, 3)">喜欢的有氧训练</a></li>
				<li><a href="javascript:onGuide(0, 4)">开始日期</a></li>
				<li><a href="javascript:onGuide(0, 5)">完成</a></li>
			</ul>
			<div>
				<s:form name="frmsetting" id="frmsetting" theme="simple">
					<div class="quest">
						<div class="quetion border_que">
							<div class="questjin">
								<div class="quelist">
									1.每星期哪几天进行力量训练？ <span id="strenghTips"><s:if
											test="(#request.strengthDates.size()==7)">连续7天运动不利于身体健康，一周请至少安排1天休息。</s:if>
										<s:else>
											<s:if test="setting.currGymStatus == \"C\"">根据您的个人情况，建议您每周进行3-4次力量训练。</s:if>
											<s:elseif test="setting.currGymStatus == \"D\"">根据您的个人情况，建议您每周进行4-6次力量训练。</s:elseif>
											<s:else>根据您的个人情况，建议您每周进行2-3次力量训练。</s:else>
										</s:else></span>
								</div>
							</div>
							<div class="quexuan">
								<s:checkboxlist value="%{#request.strengthDates}"
									list="#{'1':'星期一','2':'星期二','3':'星期三','4':'星期四','5':'星期五','6':'星期六','7':'星期日'}"
									listKey="key" listValue="value" id="chkDate"
									name="params.strengthDate" onclick="validateSelectAll()" />
							</div>
							<div class="questjin">
								<div class="quelist">2.每次力量训练用多长时间？</div>
							</div>

							<div class="quexuan">
								<s:radio value="%{setting.strengthDuration}"
									list="#{'30':'&nbsp;30分钟','45':'&nbsp;45分钟','60':'&nbsp;60分钟','90':'&nbsp;90分钟','120':'&nbsp;120分钟'}"
									listKey="key" listValue="value" id="radio"
									name="params.strengthDuration" />
							</div>
							<div class="questjin">
								<div class="quelist">
									3.每星期哪几天进行有氧训练？ <span><s:if
											test="setting.target == \"1\"">根据您的个人情况，建议您每周进行5-6天有氧训练。</s:if>
										<s:elseif test="setting.target == \"2\"">
											<s:if test="setting.currGymStatus == \"A\"">根据您的个人情况，建议您每周进行5-6天有氧训练。</s:if>
											<s:elseif test="setting.currGymStatus == \"D\"">根据您的个人情况，建议您每周进行4天有氧训练。</s:elseif>
											<s:else>根据您的个人情况，建议您每周进行3天有氧训练。</s:else>
										</s:elseif> <s:elseif test="setting.target == \"3\"">
											<s:if test="setting.currGymStatus == \"A\"">根据您的个人情况，建议您每周进行5-6天有氧训练。</s:if>
											<s:else>根据您的个人情况，建议您每周进行3天有氧训练。</s:else>
										</s:elseif> <s:else>根据您的个人情况，建议您每周进行5天有氧训练。</s:else></span>
								</div>
							</div>
							<div class="quexuan">
								<s:checkboxlist value="%{#request.cardioDates}"
									list="#{'1':'星期一','2':'星期二','3':'星期三','4':'星期四','5':'星期五','6':'星期六','7':'星期日'}"
									listKey="key" listValue="value" id="chkDate"
									name="params.cardioDate" />
							</div>
							<div class="questjin">
								<div class="quelist">4.每次有氧训练用多长时间？</div>
							</div>
							<div class="quexuan">
								<s:radio value="%{setting.cardioDuration}"
									list="#{'30':'30分钟','45':'45分钟','60':'60分钟','90':'90分钟','120':'120分钟'}"
									listKey="key" listValue="value" id="radio"
									name="params.cardioDuration" />
							</div>
						</div>
					</div>
				</s:form>
				<div class="stepo">
					<a href="javascript:onGuide(0, 1)" title="上一步" class="butlost">上一步</a>
					<a href="javascript:onGuide(0, 3)" title="下一步" class="btnnext">下一步</a>
				</div>
			</div>
		</div>
	</div>
</div>
