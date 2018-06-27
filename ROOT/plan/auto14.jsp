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
						<li><a href="javascript:onGuide(1, 1)">身体状况</a></li>
						<li><a href="javascript:onGuide(1, 2)">喜欢的有氧训练</a></li>
						<li><a href="javascript:onGuide(1, 3)">开始日期</a></li>
						<li id="firsts"><a href="javascript:onGuide(1, 4)">完成</a></li>
					</ul>
				</div>
				<div>
					<div class="quest">
						<div class="quetion">
							<div class="youxi">
								<div class="finsh">
									<font color="#0000CC">您已经完成了智能计划的设定。请点击确定按钮，继续完成支付后，系统将自动为您生成健身计划。
									</font>
								</div>
								<div class="finsh">
									<div class="jingao">脱臼一年以上，骨折、肌肉、肌腱拉伤愈后三个月以上可以使用本系统。<br/>
										有心、肺、肝、肾、神经疾病者不建议使用本系统。</div>
									<img src="images/jinggaos.png" class="finspic" />
								</div>
							</div>
						</div>
					</div>
					<div class="stepo">
						<a href="javascript:onGuide(1, 2)" title="上一步" class="butlost">上一步</a> <a 　　　
							href="javascript:goodsCardcol(2)" title="确定" class="btnniii">确定</a>
					</div>
				</div>
			</div>
		</div>
	</div>
<s:form id="dateForm" name="dateForm" method="post" action="" theme="simple">
	<s:hidden id="startDate" name="startDate"></s:hidden>
</s:form>