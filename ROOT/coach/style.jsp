<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="keywords" content="健身E卡通-我的设定" />
<meta name="description" content="健身E卡通-我的设定" />
<title>健身E卡通-我的设定</title>
<s:include value="/share/meta.jsp"/>
<link href="css/user-config.css" rel="stylesheet" type="text/css" />
<link type="text/css" rel="stylesheet" href="css/pulicstyle.css"/>
<script language="javascript">
function nextStep(){
	$('#form').attr('action', 'style!next.asp');
	$('#form').submit();
}
function onAdd(){
	var $ul = $('.tiemcoa');
	var size = $ul.find('li').length;
	var html = $('#samples').html().replace(new RegExp('XXXX', "gm"), size + '');
	$ul.append(html);
}

function onDelete(the){
	var $li = $(the).parents('li');
	var key = $li.children('input').val();
	if(confirm('是否确认删除当前的工作时间？')){
		$.ajax({type:'post',url:'worktime!delete.asp',data: 'ids='+ key,
			success: function(){
				$li.remove();
			}
		});
	}
}
</script>

</head>
<body>
	<s:include value="/share/header.jsp"/>
	<div id="content">
		<div class="my-setting">
			<ul id="nextstep">
			  <li class="first1">一.设定风格</li> 
			  <li>二.设定课程</li>
			  <li>三.设定健身项目</li>
			  <li>四.设定身体部位</li>
			  <li>五.设定训练动作</li>
			  <li>六.完成</li>
			</ul>
		</div>

		<s:form id="form" name="form" action="" method="post" theme="simple">
			<s:hidden name="member.id"/>
			<div id="center-1">
				<div class="configall">
						<h4>1、我的专长：</h4>
						<div>
						<s:checkboxlist id="speciality" value="#request.list" list="#request.targets" listKey="code" listValue="name" name="member.speciality"/></div>
						<h4>2、我的私教形式：</h4>
						<div class="educoa">
							<s:checkboxlist id="mode" value="#request.list1" list="#{'A':'网络私人健身教练，通过卡库网为会员提供健身指导','B':'传统私人健身教练，一对一提供面对面的指导'}" listKey="key" listValue="value" name="member.mode" theme="ethan"/>
						</div>
						<h4>3、我的教练类型：</h4>
						<div class="fengge">
							<s:checkboxlist id="style" value="#request.list2" list="#{'A':'私人教练','B':'团体教练'}" listKey="key" listValue="value" name="member.style"/>
						</div>
						<h4>4、设定工作日期</h4>
						<div>
							<s:checkboxlist id="workDate" value="#request.workDates" list="#{'1':'星期日','2':'星期一','3':'星期二','4':'星期三','5':'星期四','6':'星期五','7':'星期六'}" listKey="key" listValue="value" name="member.workDate"/>
						</div>
						<h4>5、设定工作日内的关闭时段</h4>
						<div>
						<ul class="tiemcoa">
							<s:iterator value="worktimes" status="st">
							<li>从<s:select list="#request.times" listKey="key" listValue="value" name="%{'worktimes['+#st.index+'].startTime'}"/>
								到<s:select list="#request.times" listKey="key" listValue="value" name="%{'worktimes['+#st.index+'].endTime'}"/>
								<a onclick="javascript: onDelete(this);">删除</a>
								<s:hidden name="%{'worktimes['+#st.index+'].id'}"/>
							</li>
							</s:iterator>
						</ul>
						</div>
						<div class="saveoperate">
							<span><a href="javascript:onAdd();" class="adat">新增</a></span>
						</div>
				</div>
			</div>
		</s:form>
		<div class="stepoperate">
			<a href="javascript:nextStep()" title="下一步" class="butnext">下一步</a>
		</div>
	</div>
	<s:include value="/share/footer.jsp"/>
	<ul id="samples" style="display: none;">
		<li>从<select name="worktimes[XXXX].startTime">
				<option value="00:00">00:00</option>
				<option value="00:30">00:30</option>
				<option value="01:00">01:00</option>
				<option value="01:30">01:30</option>
				<option value="02:00">02:00</option>
				<option value="02:30">02:30</option>
				<option value="03:00">03:00</option>
				<option value="03:30">03:30</option>
				<option value="04:00">04:00</option>
				<option value="04:30">04:30</option>
				<option value="05:00">05:00</option>
				<option value="05:30">05:30</option>
				<option value="06:00">06:00</option>
				<option value="06:30">06:30</option>
				<option value="07:00">07:00</option>
				<option value="07:30">07:30</option>
				<option value="08:00" selected="selected">08:00</option>
				<option value="08:30">08:30</option>
				<option value="09:00">09:00</option>
				<option value="09:30">09:30</option>
				<option value="10:00">10:00</option>
				<option value="10:30">10:30</option>
				<option value="11:00">11:00</option>
				<option value="11:30">11:30</option>
				<option value="12:00">12:00</option>
				<option value="12:30">12:30</option>
				<option value="13:00">13:00</option>
				<option value="13:30">13:30</option>
				<option value="14:00">14:00</option>
				<option value="14:30">14:30</option>
				<option value="15:00">15:00</option>
				<option value="15:30">15:30</option>
				<option value="16:00">16:00</option>
				<option value="16:30">16:30</option>
				<option value="17:00">17:00</option>
				<option value="17:30">17:30</option>
				<option value="18:00">18:00</option>
				<option value="18:30">18:30</option>
				<option value="19:00">19:00</option>
				<option value="19:30">19:30</option>
				<option value="20:00">20:00</option>
				<option value="20:30">20:30</option>
				<option value="21:00">21:00</option>
				<option value="21:30">21:30</option>
				<option value="22:00">22:00</option>
				<option value="22:30">22:30</option>
				<option value="23:00">23:00</option>
				<option value="23:30">23:30</option>
		</select> 到<select name="worktimes[XXXX].endTime">
				<option value="00:00">00:00</option>
				<option value="00:30">00:30</option>
				<option value="01:00">01:00</option>
				<option value="01:30">01:30</option>
				<option value="02:00">02:00</option>
				<option value="02:30">02:30</option>
				<option value="03:00">03:00</option>
				<option value="03:30">03:30</option>
				<option value="04:00">04:00</option>
				<option value="04:30">04:30</option>
				<option value="05:00">05:00</option>
				<option value="05:30">05:30</option>
				<option value="06:00">06:00</option>
				<option value="06:30">06:30</option>
				<option value="07:00">07:00</option>
				<option value="07:30">07:30</option>
				<option value="08:00">08:00</option>
				<option value="08:30">08:30</option>
				<option value="09:00">09:00</option>
				<option value="09:30">09:30</option>
				<option value="10:00">10:00</option>
				<option value="10:30">10:30</option>
				<option value="11:00">11:00</option>
				<option value="11:30">11:30</option>
				<option value="12:00" selected="selected">12:00</option>
				<option value="12:30">12:30</option>
				<option value="13:00">13:00</option>
				<option value="13:30">13:30</option>
				<option value="14:00">14:00</option>
				<option value="14:30">14:30</option>
				<option value="15:00">15:00</option>
				<option value="15:30">15:30</option>
				<option value="16:00">16:00</option>
				<option value="16:30">16:30</option>
				<option value="17:00">17:00</option>
				<option value="17:30">17:30</option>
				<option value="18:00">18:00</option>
				<option value="18:30">18:30</option>
				<option value="19:00">19:00</option>
				<option value="19:30">19:30</option>
				<option value="20:00">20:00</option>
				<option value="20:30">20:30</option>
				<option value="21:00">21:00</option>
				<option value="21:30">21:30</option>
				<option value="22:00">22:00</option>
				<option value="22:30">22:30</option>
				<option value="23:00">23:00</option>
				<option value="23:30">23:30</option>


		</select> <a onclick="javascript:onDelete(this);">删除</a>
		</li>
	</ul>
</body>
</html>