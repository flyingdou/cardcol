<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<link rel="stylesheet" type="text/css" href="css/fitnessplan.css"/>	
<div class="altleft" style="margin-left:3px; margin-top:-5px;height:300px;">
	<div class="altleft1">
<%-- 		<div class="action_img"><img src="<s:property value="#request.action.image"/>"/></div> --%>
		<div class="action_anm" id = "playercontainer"></div>
		<script type="text/javascript">
			var player = cyberplayer("playercontainer").setup({ 
				width : "100%", height : 300, 
				backcolor : "#FFFFFF", 
				stretching : "uniform", 
				file : "<s:property value="#request.signPath"/>",							
				autoStart : true, 
				repeat : "always", 
				volume : 100, 
				controlbar : "top", 
				ak : "vtpI8z2Q4G1KvSKunvQaRDKK", 
				sk : "uuB5rlXoAWxdcxUsRn3sN8PmWooKdeh1"
			}); 
		</script>
	</div>
</div>
<div class="action_desc" style="text-overflow: ellipsis; overflow: hidden;">动作描述：<s:property value="#request.action.descr"/></div>
