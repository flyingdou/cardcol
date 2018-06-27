<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<style type="text/css">
.b {
	margin-left: 5px;
	color: black;
}

#ul_order a:link {
	color: black;
}

.address_title a:link {
	color: white;
}

.middle_T a:link {
	color: #707070;
}
</style>
<script type="text/javascript" src="${pageContext.request.contextPath}/eg/js/jquery-1.9.1.js"></script>
<script type="text/javascript">
	$(function() {
		$('#ul_order li').click(function() {
			var id = $(this).attr('id');
			var oldCls = $(this).attr('class');
			$('#ul_order li').attr('class', 'none');
			$(this).attr('class', (oldCls === 'none' ? 'desc' : (oldCls === 'asc' ? 'none' : (oldCls === 'desc' ? 'asc' : 'desc'))));
			var newCls = $(this).attr('class');
			$('#orderFlag').val(newCls == 'none' ? "" : newCls);
			if (id === 'timeOrder') {
				$('#order').val("endPublishTime");
			} else if (id === 'countOrder') {
				$('#order').val("orderCount");
			} else
				$('#order').val("countSocure");
			queryByPage(1);
		});

		$(".card").find("span").on("mouseover", function() {
			$(this).find("a").css("backgroundColor", "#f69346");
			$(this).find("a").css("color", "#fff");
		}).on("mouseout", function() {
			$(this).find("a").css("backgroundColor", "");
			$(this).find("a").css("color", "");
		});
		$(".middle_T").find("span").on("mouseover", function() {
			$(this).find("a").css("color", "#f47e17");
		}).on("mouseout", function() {
			$(this).find("a").css("color", "");
		})
	})
</script>

<div class="col-md-12" style="border: 1px solid #bfbfbf; margin: 20px 0">
	<div class="col-md-12">
		<div class="show_style">
			<ul id="ul_order">
				<li style="margin-right: 40px">默认：</li>
				<li
					class="thisLi   <s:if test="pageInfo.order =='endPublishTime'"><s:if test="pageInfo.orderFlag 

==''">none</s:if><s:elseif test="pageInfo.orderFlag =='asc'">asc</s:elseif><s:else>desc</s:else></s:if>"
					id="timeOrder">
					<a href="javascript:void(0);">
						时间
						<img src="images/sanjiawxz_31.png" class="down_up" style="margin-right: 40px">
					</a>
				</li>
				<li
					class="thisLi   <s:if test="pageInfo.order =='orderCount'"><s:if test="pageInfo.orderFlag 

==''">none</s:if><s:elseif test="pageInfo.orderFlag =='asc'">asc</s:elseif><s:else>desc</s:else></s:if>"
					id="countOrder">
					<a href="javascript:void(0);">
						销量
						<img src="images/sanjiawxz_31.png" class="down_up" style="margin-right: 40px">
					</a>
				</li>
				<li
					class="thisLi  <s:if test="pageInfo.order =='countSocure'"><s:if test="pageInfo.orderFlag 

==''">none</s:if><s:elseif test="pageInfo.orderFlag =='asc'">asc</s:elseif><s:else>desc</s:else></s:if>"
					id="socureOrder">
					<a href="javascript:void(0);">
						评分
						<img src="images/sanjiawxz_31.png" class="down_up" style="margin-right: 40px">
					</a>
				</li>
				<div style="clear: both"></div>
			</ul>
		</div>

	</div>
</div>
<script type="text/javascript">
	function queryByGoPage() {
		if (jQuery("#goPage").val()) {
			var nowPage = jQuery("#currentPage").val(jQuery("#goPage").val()).val();
			queryByPage(nowPage);
		}
	}
</script>
<div class="row">
	<div class="col-md-9">
		<s:iterator value="pageInfo.items" var="club">


			<div class="course clearfix">
				<div class="col-md-4 col-lg-3">
					<div class="left">
						<a href="javascript:goMemberHome('<s:property value="id"/>','<s:property value="role"/>');">
							<s:if test="image == '' || image == null">
								<img src="images/userpho.jpg" />
							</s:if>
							<s:else>
								<img src="picture/<s:property value="image"/>"/>
							</s:else>
						</a>
					</div>
				</div>
				<div class="col-md-8 col-lg-9" style="padding-top: 20px">

					<h4 class="address_title">
						<a href="javascript:goMemberHome('<s:property value="id"/>','<s:property value="role"/>');">
							<s:property value="name" />
						</a>
					</h4>
					<div class="middle_M">
						<span class="score">
							评分：<i><s:property value="member_grade" /></i>
						</span>
						<span class="rated">
							已有<i><s:property value="countEmp" /></i>人评价
						</span>
					</div>
					<div class="middle_B">

						<p class="address">
							<span>地址: </span>
							<s:property value="address" />
						</p>

						<p class="address">
							<span>项目: </span>
							<s:iterator value="#request.courseInfos.get(#club.id + \"\")" status="stat" var="c">
								<s:if test="#stat.index<7">
									<s:property value="#c.name" />
								</s:if>
								<s:elseif test="#stat.index == 7">
							                             ...
							                        </s:elseif>
							</s:iterator>
						</p>
					</div>
				</div>
			</div>
		</s:iterator>
	</div>
	<div class="col-md-3">
		<div class="charitable">
			<span style="display: inline-block; width: 40%; padding: 15px 0">
				<a class="download"> Android下载 </a>
				<a class="download"> IOS下载 </a>
			</span>
			<img src="images/Qrcode.png" width="40%" style="display: inlne-block; margin-top: -50px">

		</div>
		<!-- 推荐俱乐部列表开始 -->
		<s:iterator value="#request.factoryRecommends">
			<div class="left_two">
				<img src="${pageContext.request.contextPath}/picture/<s:property value="image"/>" style="max-width:180px;">
				<h5><s:property value="rName"/></h5>
				<p><s:property value="title"/></p>
			</div>
		</s:iterator>
		<!-- 推荐俱乐部列表结束 -->
	</div>
</div>
<div class="row">
	<div class="col-md-12">
		<s:include value="/share/pageUtil.jsp" />
	</div>
</div>