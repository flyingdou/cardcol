<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div id="left-1">
			<h1 style="background:#ff4401;color:white;font-weight:600">健身预约</h1>
			<div>
				<ul class="order">
					<li>
						<h1>
							<b>课程管理</b>
						</h1>
						<ul class="ordermanagement">
							<li><a id="coloa" href="course.asp">课程表</a></li>
						</ul>
						<s:if test="#session.loginMember.role==\"E\"">
							<ul class="ordermanagement">
							<li><a id="coloa" href="factoryorder.asp">场地预定</a></li>
						</ul>
						</s:if>
					</li>
					<li>
						<h1>
							<b>预约管理</b>
						</h1>
					</li>
					<ul class="ordermanagement">
						<li><a id="coloa" href="apply!query.asp?query.status=1">待审的预约<span id="tishi1">(0)</span></a></li>
						<li><a id="coloa" href="apply!query.asp?query.status=2">成功的预约<span id="tishi2">(0)</span></a></li>
						<li><a id="coloa" href="apply!query.asp?query.status=3">拒绝的预约<span id="tishi3">(0)</span></a></li>
					</ul>
					<li>
						<h1><b>考勤管理</b>	</h1>
						<ul class="ordermanagement">
							<li><a id="coloa" href="signin!query.asp">签到管理</a></li>
							<!-- <li><a id="coloa" href="signin!querySign.asp">履约记录</a></li> -->
						</ul>
					</li>
				</ul>
			</div>
		</div>