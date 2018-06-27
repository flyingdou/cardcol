<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<s:hidden name="channel" theme="simple" id="channel"/>
<div class="select-1">
	<h2><b><s:property value="article.title"/></b></h2>
</div>
<div class="select-2">
	<p>发表时间： <s:property value="article.issueTime"/></p>
</div>
<div class="select-3">
	<p>
		<s:property value="article.content" escape="false"/>
	</p>
</div>
<iframe width="710" height="450" frameborder="0" scrolling="yes"
	src="http://widget.weibo.com/distribution/comments.php?language=zh_cn&width=710&height=245&skin=9&dpc=1&url=http://open.weibo.com/widget/comments.php&titlebar=1&border=1&appkey=1800789311&colordiy=0&dpc=1&url=<s:property value="#request.link"/>&ralateuid=1800789311"></iframe>
