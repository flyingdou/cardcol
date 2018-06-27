<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<h1 class="community">我的社区</h1>
<div>
	<ul class="community">
		<li>
			<h1>博文管理</h1>
			<ul class="bowen">
				<li><a href="sns.asp?query.status=1">全部博文</a></li>
				<li><a href="sns.asp?query.status=0">草稿箱</a></li>
			</ul>
		</li>
		<li>
			<h1>留言管理</h1>
			<ul class="bowen">
				<li><a href="leave.asp">留言</a></li>
			</ul>
		</li>
		<li><a href="#"><h1>评价管理</h1> </a>
			<ul class="bowen">
				<li><a href="appraise.asp">评价</a></li>
				<li><a href="critique.asp">评论</a></li>
			</ul></li>
		<li>
			<h1>消息管理</h1>
			<ul class="bowen">
				<li><a href="approve.asp">审批</a></li>
				<li><a href="message.asp">消息</a></li>
				<li><a href="awoke.asp">提醒</a></li>
			</ul>
		</li>
		<li>
			<h1>好友管理</h1>
			<ul class="bowen">
				<li><a href="friend.asp">我的好友</a></li>
				<li><a href="mycoach.asp">我的教练</a></li>
				<li><a href="mymember.asp">我的会员</a></li>
			</ul>
		</li>
		<li>
			<h1>圈子管理</h1>
			<ul class="bowen">
				<li><a href="sns5-chuangjian.html">我创建的圈子</a></li>
				<li><a href="sns5-canyu.html">我参与的圈子</a></li>
			</ul>
		</li>
	</ul>
</div>
