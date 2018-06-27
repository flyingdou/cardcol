﻿<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
/* function queryByPage(page, div){
	var divId = div || 'right-2';
	$('#currentPage').val(page);
	if(page){
		loadMask();
		$.ajax({type:"post",url: $('#queryForm').attr("action"),data: $('#queryForm').serialize(),
			success:function(msg){
				removeMask();
				$("#" + divId).html(msg);
			}
		});
	}
} */
function queryByPage(page){
	//var divId = div || 'right-2';
	$('#currentPage').val(page);
	if(page){
		loadMask();
		/* $.ajax({type:"post",url: $('#queryForm').attr("action"),data: $('#queryForm').serialize(),
			success:function(msg){
				removeMask();
				$("#" + divId).html(msg);
			}
		}); */
		$('#queryForm').submit();
	}
}
</script>
<div class="plan_nav" style="margin-top: 0px; margin-right:auto;margin-left:auto;">
	<div class="plan_fenye">
		<span class="plan_yema"> <s:property value="pageInfo.currentPage" />
			</span>/<span> <s:property value="pageInfo.totalPage" /></span>
	</div>
	<s:if test="pageInfo.currentPage > 1">
		<a href="javascript:queryByPage('<s:property value="pageInfo.currentPage-1"/>');">
			<img src="images/plan_pre.gif" /></a>
	</s:if>
	<s:if test="pageInfo.currentPage < pageInfo.totalPage">
		<a href="javascript:queryByPage('<s:property value="pageInfo.currentPage+1"/>');">
			<img src="images/plan_next.gif" /></a>
	</s:if>
</div>
<s:hidden name="pageInfo.pageSize" id="pageSize"/>
<s:hidden name="pageInfo.currentPage" id="currentPage"/>
<s:hidden name="pageInfo.totalPage" id="totalPage"/>
<s:hidden name="pageInfo.totalCount" id="totalCount"/>
<s:hidden name="pageInfo.splitFlag" id="splitFlag"/>
<s:hidden name="pageInfo.order" id="order"/>
<s:hidden name="pageInfo.orderFlag" id="orderFlag"/>
