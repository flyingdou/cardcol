<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>我的评价</title>
<s:include value="/share/meta.jsp" />
<link href="css/pulic-2.css" type="text/css" rel="stylesheet" />
<link href="css/smoothness/template.css" type="text/css"
	rel="stylesheet" />
<link href="css/club-order.css" type="text/css" rel="stylesheet" />
<link href="css/comment.css" type="text/css" rel="stylesheet" />
<link href="css/pulicstyle.css" type="text/css" rel="stylesheet" />
<link type="text/css" rel="stylesheet" href="script/jRating/jRating.jquery.css" />
<script type="text/javascript" src="script/jRating/jRating.jquery.js"></script>
<script src="script/condition/Plugins/jquery.form.js" type="text/javascript"></script>
<script type="text/javascript" src="script/jquery.form.js"></script>
<script type="text/javascript" src="script/jquery.parser.js"></script>
<script src="script/comment.js" type="text/javascript"></script>
<script type="text/javascript" src="script/uploadPreview.js"></script>

<script type="text/javascript">
$(function (){ 
	var _size = '<s:property value="pageInfo.items.size"/>';
	for(var i = 0 ; i < _size; i++){
		if($('#txtImage1_'+i)&&$('#txtImage1_'+i).attr('id')){
				new uploadPreview({
					UpBtn : "txtImage1_"+i,
					ImgShow : "preview1_"+i
				});
				new uploadPreview({
					UpBtn : "txtImage2_"+i,
					ImgShow : "preview2_"+i
				});
				new uploadPreview({
					UpBtn : "txtImage3_"+i,
					ImgShow : "preview3_"+i
				});
		}
	}
	
    $(".basic").jRating({
		rateMax : 100,
		sendRequest: false,
		onClick : function(element, rate) {
			var _index = $(element).attr('index');
			$('#grade').val(rate);
			var aDiv=document.getElementById("commc"+_index);
			var aDivd=document.getElementById("comm"+_index);
			aDiv.style.display="none";
			aDivd.style.display="block";
		}
	});
    $(".basic1").jRating({
    	isDisabled : true,
		rateMax : 100
	});
    $(".basic1").each(function(){
    	var id = $(this).attr('id');
    	$('#'+id).children('div:eq(1)').css('width',$('#'+id).attr('rate')*1.16+'px');
      });
});

function deleteImage(o) {
	$('#preview' + o).attr('src', 'images/img-defaul.jpg');
	$('#txtImage' + o).val('');
}
	
function onQuery(){
	queryByPage(1);
}	
   
function changePage(type){
	if(type=="0"){
		$('#flag').val(0);
	}else if(type=="1"){
		$('#flag').val(1);
	}else if(type=="2"){
		$('#flag').val(2);
	}
	onQuery();
}  
function deleteAppraise(appraiseId){
	if (confirm('是否确认删除评价?')) {
		$('#appraiseId').val(appraiseId);
		$.ajax({type:'post',url:'myappraise!deleteAppraise.asp',data:$('#queryForm').serialize(),
			success:function(msg){
				if(msg == "OK"){
					alert("删除成功!");
					$.ajax({
						type : "post",
						url : "myappraise!queryAppraise.asp",
						data : $('#queryForm').serialize(),
						success : function(msg) {
							$("#right-2").html(msg);
						}
					});
				}else{
					alert("删除失败!");
				}
			}
		});
  	}
}

function save(index,type,orderType,orderId){	
	var title = $('#title2'+index).val();
	var content = $('#content2'+index).val();
// 	var starId = 'star'+ index;
// 	$('#grade').val($('#'+starId).attr('rate'));
	
 	if(title =="标题或摘要(必填)"){
		alert("请先输入标题或摘要！");
		$('#title2'+index).focus();
		$('#title2'+index).select();
		return;
	}
 	if(content=="在此撰写评价内容"){
		alert("请先输入评价内容！");
		$('#content2'+index).focus();
		$('#content2'+index).select();
		return;
	}
 	$('#type').val(type);
 	$('#orderType').val(orderType);
 	$('#orderId').val(orderId);
 	$('#title').val(title);
 	$('#appraisecontent').val(content);
 	$('#queryForm').form('submit', {
		url: 'myappraise!saveAppraise.asp',
		onSubmit: function(param) {
			return true;
		},
		success: function(msg) {
			if(msg == "OK"){
				alert("保存成功!");
				$.ajax({
					type : "post",
					url : "myappraise!queryAppraise.asp",
					data : $('#queryForm').serialize(),
					success : function(msg) {
						$("#right-2").html(msg);
					}
				});
			}else{
				alert("保存失败!");
			}
		}
	});
}

function saveCallBack(msg){
	if(msg == "ok"){
		alert('评价成功！');
	}else{
		alert(msg);
	}
}

function productDetail(productId, productType){
	if(productType == 1){
		$('#queryForm').attr('action','clublist!shoGo.asp?productId='+productId);
		$('#queryForm').submit();
	}else if(productType == 2){
		$('#queryForm').attr("action",'activewindow.asp?id='+productId);
		$('#queryForm').submit();	
	}else if(productType == 3){
		$('#queryForm').attr("action",'plan.asp?pid='+productId);
		$('#queryForm').submit();	
	}else if(productType == 6){
		$('#queryForm').attr("action",'goods.asp?goodsId='+productId);
		$('#queryForm').submit();	
	}
}
</script>
<style type="text/css">
.plan_nav{ padding-left:300px;}
</style>


</head>


<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<s:include value="/share/meta.jsp" />
<meta name="keywords" content="瘦身减肥课程,肌肉健美课程,增强体质" />
<meta name="description"
	content="健身教练的商务中心是健身教练制定的健身套餐,健身课程，如：瘦身减肥课程,肌肉健身课程,增强体质课程等。" />
<title>商务中心</title>
<link href="css/club-order.css" rel="stylesheet" type="text/css" />
<link type="text/css" rel="stylesheet" href="css/pulicstyle.css" />

</head>
<body>
	<s:include value="/share/header.jsp" />
	<div id="content">
	<s:include value="/order/nav.jsp" />
		<div id="right-2">
			
			<body>
	<s:form id="queryForm" name="queryForm"
		action="myappraise!queryAppraise.asp" method="post" theme="simple"
		enctype="multipart/form-data">
		<s:hidden name="appraiseId" id="appraiseId" />
		<s:hidden name="type" id="type" />
		<s:hidden name="orderId" id="orderId" />
		<s:hidden name="orderType" id="orderType" />
		<s:hidden name="title" id="title" />
		<s:hidden name="content" id="appraisecontent" />
		<s:hidden name="grade" id="grade" />
		<s:hidden name="flag" id="flag" />
		<s:hidden name="image1" id="image1" />
		<h1>我的评价</h1>
		<div class="right">
			<div style="margin-top: 10px; overflow: hidden;">
				<p class="conradio">

					<span><input type="radio"
						<s:if test="flag==null ||flag==0 ">checked="checked"</s:if>
						class="radoo" name="pingjia" onclick="changePage(0)" />全部 </span> <span><input
						type="radio" <s:if test="flag==1 ">checked="checked"</s:if>
						class="radoo" name="pingjia" onclick="changePage(1)" />待评价</span> <span><input
						type="radio" <s:if test="flag==2 ">checked="checked"</s:if>
						class="radoo" name="pingjia" onclick="changePage(2)" />已评价</span>
					<div class="plan_nav" style="float: right;">
						<div class="plan_fenye">
							<span class="plan_yema"> <s:property
									value="pageInfo.currentPage" /></span>/<span><s:property
									value="pageInfo.totalPage" /> </span>
						</div>
						<s:if test="pageInfo.currentPage < pageInfo.totalPage">
							<a
								href="javascript:queryByPage('<s:property value="pageInfo.currentPage+1"/>');"><img
								src="images/plan_next.gif" /></a>
						</s:if>
						<s:if test="pageInfo.currentPage > 1">
							<a
								href="javascript:queryByPage('<s:property value="pageInfo.currentPage-1"/>');"><img
								src="images/plan_pre.gif" /></a>
						</s:if>
					</div>

				</p>
			</div>
			<div class="right_con">
				<div class="com_tt">
					<s:iterator value="pageInfo.items" status="start">
						<!-- 待评价 -->
						<s:if test="isappraise==0">
							<div class="coment_pic">
								<a
									href="javascript:productDetail(<s:property value="productId"/>,<s:property value="orderType"/>);">
									<img src="picture/<s:property value="image"/>" width="160px"
									height="140px" />
								</a>
								<p class="right_wen">
									该商品已有
									<s:if test="cnt==null || cnt==''">0</s:if>
									<s:else>
										<s:property value="cnt" />
									</s:else>
									人评价
								</p>
							</div>
							<div class="coment_com">
								<div class="comping2">
									<div class="conment_title">
										<div class="comment_sj">
											商家： <a
												href="javascript:goMemberHome('<s:property value="sellerid"/>','<s:property value="role"/>');"
												id="coloa"> <s:property value="sellerName" />
											</a>
										</div>
										<p>
											<a
												href="javascript:productDetail(<s:property value="productId"/>,<s:property value="orderType"/>);"><s:property
													value="productName" /></a>
										</p>
									</div>

									<div id="xzw_starSys">
										<div class="basic" id="star${start.index}" index="${start.index}" data-average="10"></div>
										<div class="description_${start.index}"
											style="color: #ff8000;" name="description1"></div>
									</div>
								</div>

								<div class="comm_picjie" id="commc${start.index}">
									<p>&nbsp;</p>
									<p>&nbsp;</p>
									<p>&nbsp;</p>
								</div>

								<div class="comm_picjie" id="comm${start.index}" style="display: none;">
									<p>
										<input type="hidden" id="rate2${start.index}"/>
										<input type="text" id="title2${start.index}"
											value="<s:if test="#request.title != null && #request.index ==#start.index"><s:property value="#request.title"/></s:if><s:else>标题或摘要(必填)</s:else>"
											class="comm_text" onmouseover=this.focus();this.select();
											onclick="if(value==defaultValue){value='';this.style.color='#000'}"
											onBlur="if(!value){value=defaultValue;this.style.color='#999'}"
											style="color: #999" />
									</p>
									<p>
										<textarea id="content2${start.index}"
											onmouseover=this.focus();this.select();
											onclick="if(value==defaultValue){value='';this.style.color='#000'}"
											onBlur="if(!value){value=defaultValue;this.style.color='#999'}"
											style="color: #999"> <s:if
												test="#request.content != null && #request.index ==#start.index">
												<s:property value="#request.content" />
											</s:if><s:else>在此撰写评价内容</s:else></textarea>
									</p>
									<div id="uploadFile">
										上传图片：
										<div>
											<img id="preview1_<s:property value="#start.index"/>" width="60px" height="60px"
												src="images/img-defaul.jpg"
												onclick="javascript: $('#txtImage1_<s:property value="#start.index"/>').click();" /> <img
												id="delImg1" width="15px" height="15px"
												src="images/del_img.png" onClick="deleteImage('1_<s:property value="#start.index"/>')"/> <img
												id="preview2_<s:property value="#start.index"/>" width="60px" height="60px"
												src="images/img-defaul.jpg"
												onclick="javascript: $('#txtImage2_<s:property value="#start.index"/>').click();" /><img
												id="delImg1" width="15px" height="15px"
												src="images/del_img.png" onClick="deleteImage('2_<s:property value="#start.index"/>')"/><img
												id="preview3_<s:property value="#start.index"/>" width="60px" height="60px"
												src="images/img-defaul.jpg"
												onclick="javascript: $('#txtImage3_<s:property value="#start.index"/>').click();" /><img
												id="delImg1" width="15px" height="15px"
												src="images/del_img.png" onClick="deleteImage('3_<s:property value="#start.index"/>')"/>
												<s:file	id="txtImage1_%{#start.index}" name="image1" style="display: none;" /> <s:file
													id="txtImage2_%{#start.index}" name="image2" style="display: none;" /> <s:file
													id="txtImage3_%{#start.index}" name="image3" style="display: none;" />
										</div>
									</div>
									<div class="coomert">
										<input type="button" value="提交"
											onclick="javascript:save(${start.index},'<s:property value="type"/>','<s:property value="orderType"/>','<s:property value="orderId"/>')" />
									</div>
								</div>
							</div>
						</s:if>


						<!-- 已评价 -->
						<s:if test="isappraise==1">
							<div class="coment_pic">
								<a
									href="javascript:productDetail(<s:property value="productId"/>,<s:property value="orderType"/>);">
									<img src="picture/<s:property value="image"/>" width="160px"
									height="140px" />
								</a>
								<p class="right_wen">
									该商品已有
									<s:if test="cnt==null || cnt==''">0</s:if>
									<s:else>
										<s:property value="cnt" />
									</s:else>
									人评价
								</p>
							</div>
							<div class="coment_com">
								<div class="comping2">
									<div class="conment_title">
										<div class="comment_sj">
											商家： <a
												href="javascript:goMemberHome('<s:property value="sellerid"/>','<s:property value="role"/>');"
												id="coloa"> <s:property value="sellerName" />
											</a>
										</div>
										<p>
											<a
												href="javascript:productDetail(<s:property value="productId"/>,<s:property value="orderType"/>);"><s:property
													value="productName" /></a>
										</p>
									</div>
									<div id="xzw_starSys">
										<div class="basic1" id="star${start.index}" data-average="10"
											rate="<s:property value='grade' />"></div>
									</div>
									<div>
										<s:if test="grade<=20">很不喜欢</s:if>
										<s:elseif test="grade>20 && grade<=40">不喜欢</s:elseif>
										<s:elseif test="grade>40 && grade<=60">还可以</s:elseif>
										<s:elseif test="grade>60 && grade<=80">喜欢</s:elseif>
										<s:else>很喜欢</s:else>
										(
										<s:property value='grade' />
										)

									</div>

								</div>
								<div class="comment_skk">
									<p>
										<span class="comment_ss">时间:</span><span><s:property
												value="appDate" /></span>
									</p>
									<p>
										<span class="comment_ss">标题:</span><span><s:property
												value="title" /></span>
									</p>
									<p>
										<span class="comment_ss">评价:</span><span><s:property
												value="content" /></span>
									</p>
									<p>
										<span class="comment_ss">晒图:</span>
										<div>
											<s:if test="image1 != null && image1 != ''">
												<img width="60px" height="60px" src="picture/<s:property value='image1'/>" />
											</s:if>
											<s:if test="image2 != null && image2 != ''">
												<img width="60px" height="60px" src="picture/<s:property value='image2'/>" />
											</s:if>
											<s:if test="image3 != null && image3 != ''">
												<img width="60px" height="60px" src="picture/<s:property value='image3'/>" />
											</s:if>
										</div>
									</p>

								</div>
								<div class="detebtn">
									<input type="button" value="删除" class="detebtn_btn"
										onclick="javascript:deleteAppraise(<s:property value="id"/>)" />
								</div>
							</div>
						</s:if>
					</s:iterator>
				</div>
			</div>
			<s:include value="/share/pageAjax.jsp" />
		</div>
	</s:form>
</body>
		</div>
	</div>
	<s:include value="/share/footer.jsp" />
</body>
</html>
