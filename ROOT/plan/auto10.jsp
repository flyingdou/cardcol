<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
	<div id="righttop">
		<div class="rightop_update" id="emptyMsg" style="display: block;">
			<h1 id="sprotjh">王严健身专家系统</h1>
			<div class="baiot"></div>
			<div class="my-setting">
				<div class="wangyu">
					<ul id="nextstep">
						<li id="first1"><a href="javascript:onGuide(1, 0)">健身目的</a></li>
						<li><a href="javascript:onGuide(1, 1)">身体状况</a></li>
						<li><a href="javascript:onGuide(1, 2)">喜欢的有氧训练</a></li>
						<li><a href="javascript:onGuide(1, 3)">开始日期</a></li>
						<li><a href="javascript:onGuide(1, 4)">完成</a></li>
					</ul>
				</div>
				<div class="div1-1-1">
				<s:form name="frmsetting" id="frmsetting" theme="simple">
					<div class="pcili">
						<ul>
							<li class="pickdd"><img src="images/shap.jpg" width="146" height="138" />								
								<input type="radio" name="params.target" <s:if test="setting.target == \"1\"">checked="checked"</s:if> value="1" id="radio1"/> <label for="radio1">减脂塑形</label></li>
							<li class="pickdd"><img src="images/body.jpg" width="146" height="138" />
								<input type="radio" name="params.target" <s:if test="setting.target == \"2\"">checked="checked"</s:if> value="2" id="radio2" /> <label for="radio2">健美增肌</label></li>
							<li><img src="images/power.jpg" width="146" height="138" />
								<input type="radio" name="params.target" <s:if test="setting.target == \"3\"">checked="checked"</s:if> value="3" id="radio3" /> <label for="radio3">增加力量</label></li></ul>
					</div>
					</s:form>
					<div class="stepo">
						<a href="javascript:onGuide(1, 1)" title="下一步" class="btnnext">下一步</a>
					</div>
				</div>
			</div>
		</div>
	</div>