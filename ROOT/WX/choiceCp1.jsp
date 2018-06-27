<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <title>裁判候选人</title>
    <link type="text/css" href="${pageContext.request.contextPath}/WX/css/bootstrap.css" rel="stylesheet">
    <link type="text/css" href="${pageContext.request.contextPath}/WX/css/base.css" rel="stylesheet">
    <link type="text/css" href="${pageContext.request.contextPath}/WX/css/style.css" rel="stylesheet">
    <link type="text/css" href="${pageContext.request.contextPath}/WX/css/index.css" rel="stylesheet">
    <script type="text/javascript" src="${pageContext.request.contextPath}/WX/js/jquery-2.2.0.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/WX/js/style.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/WX/js/choiceCps.js"></script>
    <script type="text/javascript">
   function search(){
   	var keyword = $("#search").val();
   	if(keyword !="null"){
   		$.ajax({
   	   		type : "post",
   	   		url : "activesavewx!tmember.asp",
   	   		data:"keyword="+keyword,
   	   		success:function(msg){
   	   			$("#part").html($(msg).find("#part").html());
   	   	}
   	   	});
   	}else{
   		alert("请您输入候选裁判人");
   	}
   }
   function GetQueryString(name) {
		var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
		var r = window.location.search.substr(1).match(reg);
		if (r != null)
			return decodeURI(r[2]);
		return null;
	} 
	var type= GetQueryString("type");
	var val= GetQueryString("val");
	function over(){ 
		var val =[];  
	    $('input[name="test"]:checked').each(function(){
	    val.push($(this).val());  
	      });
	    var vals =   
	window.location.href="stage_challenge.jsp?jmid="+val;
	}
	</script>
    </script>
</head>
<body>
<div class="choiceCp container">
   <div class="choiceTj clearfix">
        <div class="search fl" style="width: 100%">
            <input type="search" placeholder="搜索">
            <i class="glyphicon glyphicon-search search_icon"></i>
        </div>
    </div>
   <div class="table_list" id="part">
	    	<s:iterator value="pageInfo.items">
				<div class="list">
					<div class="pic fl">
						<img src="images/<s:property value="image"/>">
					</div>
					<span id=""><s:property value="name"/></span>
					<label class="fr label_two">
					<input type="checkbox" name="test" value="<s:property value="id"/>" class="check_two"></label>
				</div>
			</s:iterator>
	</div>
    <div class="assign">
        <a href="JavaScript:over();">完成</a>
    </div>
</div>
</body>
</html>