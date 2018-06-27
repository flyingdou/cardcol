<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
	<div id="righttop">
		<div class="rightop_update" id="emptyMsg" style="display: block;">
			<h1 id="sprotjh">王严健身专家系统</h1>
			<div class="baiot"></div>
			<div class="my-setting">
				<div class="wangyu">
					<ul id="nextstep">
						<li><a href="javascript:onGuide(1, 0)">健身目的</a></li>
						<li id="firsts"><a href="javascript:onGuide(1, 1)">身体状况</a></li>
						<li><a href="javascript:onGuide(1, 2)">喜欢的有氧训练</a></li>
						<li><a href="javascript:onGuide(1, 3)">开始日期</a></li>
						<li><a href="javascript:onGuide(1, 4)">完成</a></li>
					</ul>
				</div>
				<div>
				<s:form name="frmsetting" id="frmsetting" theme="simple">
					<div class="quest">
						<div class="quetion">
							<div class="que_li">
								<div class="quest-1">
									<div class="shejg">
										性别:<span><s:radio list="#{'M':'男性','F':'女性'}" name="member.sex"></s:radio></span>
									</div>
								</div>
								<div class="quest-1">
									<div class="shejg">
										<span class="pro_shgao" style="padding-left:0px;">身高:<s:textfield name="params.height" ></s:textfield> cm  </span>
									</div>
								</div>

								<div class="quest-1">
									<div class="shejg">
										<span class="pro_shgao" style="padding-left:0px;">体重:<s:textfield name="params.weight" ></s:textfield> kg</span></p>
									</div>
								</div>
								<div class="quest-1">
									<div class="shejg">
										<p class="prokk"><span class="body_shuji">腰围:<s:textfield name="params.waistline"></s:textfield> cm </span> </p>
									</div>
								</div>
								<div class="quest-1">
									<div class="shejg">
										 最大卧推重量   <s:textfield name="params.maxwm"></s:textfield> kg
									</div>
								</div>
							</div>
							<div class=""></div>
						</div>
						<div class="stepo">
							<a href="javascript:onGuide(1, 0)" title="上一步" class="butlost">上一步</a> <a
								href="javascript:onGuide(1,2)" title="下一步" class="btnnext">下一步</a>
						</div>
					</div>
					</s:form>
				</div>
			</div>
		</div>
	</div>