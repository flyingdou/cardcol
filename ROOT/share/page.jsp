﻿<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript" src="script/jquery-1.7.1.min.js"></script>
<script type="text/javascript">

function queryByPage(page){
	if(page){
		jQuery('#currentPage').val(page);
		jQuery('#queryForm').submit();
	}
}
function queryByGoPage(){
	if(jQuery("#goPage").val()){
		jQuery('#currentPage').val(jQuery("#goPage").val());
		jQuery('#queryForm').submit();
	}
	
}
</script>

<s:hidden name="pageInfo.start" id="pageStart"/>
<s:hidden name="pageInfo.end" id="pageStart"/>
<s:hidden name="pageInfo.pageSize" id="pageSize"/>
<s:hidden name="pageInfo.currentPage" id="currentPage"/>
<s:hidden name="pageInfo.totalPage" id="totalPage"/>
<s:hidden name="pageInfo.totalCount" id="totalCount"/>
<s:hidden name="pageInfo.splitFlag" id="splitFlag"/>
<s:hidden name="pageInfo.order" id="order"/>
<s:hidden name="pageInfo.orderFlag" id="orderFlag"/>
<div class="fenye_club">
	<div class="fenye_btn">
		<s:if test="pageInfo.currentPage > 1">
			<a href="javascript:queryByPage('<s:property value="pageInfo.currentPage-1"/>');" class="btnback"></a>
		</s:if>
		<span>
			<s:iterator value="pageInfo.displayPage" var="displayPage">
				<s:if test="pageInfo.currentPage == #displayPage">
					<s:property value="#displayPage"/>
				</s:if>
				<s:else>
					<a href="javascript:queryByPage('<s:property value="#displayPage"/>');"><s:property value="#displayPage"/></a>
				</s:else>
			</s:iterator>
		</span>
		<s:if test="pageInfo.currentPage < pageInfo.totalPage">
			<a href="javascript:queryByPage('<s:property value="pageInfo.currentPage+1"/>');" class="btnlast"></a>
		</s:if>
	</div> 
	<div class="fenye_btn_gong"><div class="fen_wer">共<span><s:property value="pageInfo.totalPage"/></span>页  到第</div>  <input type="text" name="goPage" id="goPage"  class="textbtn" /> 页  </div>      
	<input type="button" value="" class="textque" onclick="javascript:queryByGoPage();" />
</div>