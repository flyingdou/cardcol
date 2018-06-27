<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div class="altleft" id="videodiv"
	style="margin-left: 7px; margin-right: 7px; margin-top: 5px; height: 260px;"
	title="<s:property value="#request.actionName"/>">
	<div class="action_anm" id="playercontainer2"></div>
	<script type="text/javascript">
		var player = cyberplayer("playercontainer2").setup({
			width : "100%",
			height : "100%",
			backcolor : "#FFFFFF",
			stretching : "uniform",
			file : "<s:property value="#request.videoSignPath"/>",
			autoStart : true,
			repeat : "always",
			volume : 100,
			controlbar : "top",
			ak : "vtpI8z2Q4G1KvSKunvQaRDKK",
			sk : "uuB5rlXoAWxdcxUsRn3sN8PmWooKdeh1"
		});
	</script>
</div>
