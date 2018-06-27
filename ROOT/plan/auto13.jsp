<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script language="javascript" src="script/DatePicker/WdatePicker.js"></script>
	<div id="righttop">
		<div class="rightop_update" id="emptyMsg" style="display: block;">
			<h1 id="sprotjh">健身E卡通智能健身计划引擎</h1>
			<div class="baiot"></div>
			<div class="my-setting">
				<div class="wangyu">
					<ul id="nextstep">
						<li><a href="javascript:onGuide(1, 0)">健身目的</a></li>
						<li><a href="javascript:onGuide(1, 1)">身体状况</a></li>
						<li><a href="javascript:onGuide(1, 2)">喜欢的有氧训练</a></li>
						<li id="firsts"><a href="javascript:onGuide(1, 3)">开始日期</a></li>
						<li><a href="javascript:onGuide(1, 4)">完成</a></li>
					</ul>
				</div>
				<div>
					<s:form name="frmsetting" id="frmsetting" theme="simple">
					<div class="quest">
						<div class="quetion">
							<div class="youxi">
								<input type="text" name="startDate" id="startDate" value="" readonly="readonly" style="width:100px;" /> <img id="img_repeatStart" onclick="WdatePicker({el:'startDate'})" src="script/DatePicker/skin/datePicker.gif" align="absmiddle" width="16" height="22" style="cursor:pointer;">
							</div>
						</div>
					</div>
					</s:form>
					<div class="stepo">
						<a href="javascript:onGuide(1, 2)" title="上一步" class="butlost">上一步</a> <a
							href="javascript:onGuide(1, 4)" title="下一步" class="btnnext">下一步</a>
					</div>
				</div>
			</div>
		</div>
	</div>