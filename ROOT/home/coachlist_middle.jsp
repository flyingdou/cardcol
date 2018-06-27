<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
	$(
			function() {
				$('#ul_order li')
						.click(
								function() {
									var id = $(this).attr('id');
									var oldCls = $(this).attr('class');
									$('#ul_order li').attr('class', 'none');
									$(this)
											.attr(
													'class',
													(oldCls === 'none' ? 'desc'
															: (oldCls === 'asc' ? 'none'
																	: (oldCls === 'desc' ? 'asc'
																			: 'desc'))));
									var newCls = $(this).attr('class');
									$('#orderFlag').val(
											newCls == 'none' ? "" : newCls);
									if (id === 'timeOrder') {
										$('#order').val("birthday");
									} else if (id === 'salesNumOrder') {
										$('#order').val("salesNum");
									} else {
										$('#order').val("coach_grade");
									}
									queryByPage(1);
								});
			})
</script>
<div class="row">
	<div class="col-md-12">
		<div class="chose clearfix">
			<div class="list_span">
				<p>
					已选条件：
					<s:if test="county!=null && county!=''">
						<a href="javascript:onQuery('county','','');"><b> <s:property
									value="county" /></b><img src="images/plan_lde.png" /></a>
					</s:if>
					<s:if test="specialityName!=null && specialityName!=''">
						<a href="javascript:onQuery('speciality','','');"><b> <s:property
									value="specialityName" /></b><img src="images/plan_lde.png" /></a>
					</s:if>
					<s:if test="courseName!=null && courseName!=''">
						<a href="javascript:onQuery('course','','');"><b> <s:property
									value="courseName" /></b><img src="images/plan_lde.png" /></a>
					</s:if>
					<s:if test="modeName!=null && modeName!=''">
						<a href="javascript:onQuery('mode','','');"><b> <s:property
									value="modeName" /></b><img src="images/plan_lde.png" /></a>
					</s:if>
					<s:if test="styleName!=null && styleName!=''">
						<a href="javascript:onQuery('style','','');"><b> <s:property
									value="styleName" /></b><img src="images/plan_lde.png" /></a>
					</s:if>
					<s:if test="sexName!=null && sexName!=''">
						<a href="javascript:onQuery('sex','','');"><b> <s:property
									value="sexName" /></b><img src="images/plan_lde.png" /></a>
					</s:if>

				</p>
			</div>
		</div>
	</div>
</div>
<div class="row">
	<div class="col-md-6">
		<div class="show_style">
			<ul id="ul_order">
				<li
					class="<s:if test="pageInfo.order =='birthday'"><s:if test="pageInfo.orderFlag ==''">none</s:if><s:elseif test="pageInfo.orderFlag =='asc'">asc</s:elseif><s:else>desc</s:else></s:if>"
					id="timeOrder">默认：<a href="javascript:void(0);">时间</a></li>
				<li
					class="<s:if test="pageInfo.order =='salesNum'"><s:if test="pageInfo.orderFlag ==''">none</s:if><s:elseif test="pageInfo.orderFlag =='asc'">asc</s:elseif><s:else>desc</s:else></s:if>"
					id="salesNumOrder"><a href="javascript:void(0);">销量<img
						src="images/sanjiawxz_31.png" class="down_up"></a></li>
				<li
					class="<s:if test="pageInfo.order =='coach_grade'"><s:if test="pageInfo.orderFlag ==''">none</s:if><s:elseif test="pageInfo.orderFlag =='asc'">asc</s:elseif><s:else>desc</s:else></s:if>"
					id="gradeOrder"><a href="javascript:void(0);">评分<img
						src="images/sanjiawxz_31.png" class="down_up"></a></li>
			</ul>
		</div>
	</div>
	<div class="col-md-6">
		<div class="show_list">
			<span>到</span>&nbsp;&nbsp;&nbsp;&nbsp; <input type="text"
				name="goPage" id="goPage">页
			<button type="button" class="btn btn-primary btn-xs"
				onclick="javascript:queryByGoPage();">跳转</button>
			<span>&nbsp;<s:property value="pageInfo.currentPage" />&nbsp;
			</span>/<span>&nbsp;<s:property value="pageInfo.totalPage" />&nbsp;
			</span>&nbsp;&nbsp;&nbsp;&nbsp; <img src="images/next-icon_03.png">
		</div>
	</div>
	<script type="text/javascript">
		function queryByGoPage() {
			if (jQuery("#goPage").val()) {
				var nowPage = jQuery("#currentPage").val(
						jQuery("#goPage").val()).val();
				queryByPage(nowPage);
			}
		}
	</script>
</div>
<div class="row">
	<s:iterator value="pageInfo.items" status="st">
		<s:if test="(#st.index/2)==0">
			<div class="col-md-6">
				<div class="teach_name">
					<h4>
						<span>教练昵称：<s:property value="name" /></span>
					</h4>
				</div>
				<div class="teach_message">
					<div class="row">
						<div class="col-md-4">
							<a
								href="javascript:goMemberHome('<s:property value="id"/>','<s:property value="role"/>');"><s:if
									test="image == '' || image == null">
									<img src="images/userpho.jpg" class="img1" />
								</s:if> <s:else>
									<img src="picture/<s:property value="image"/>" class="img1" />
								</s:else></a>
						</div>
						<div class="col-md-8">
							<p>
								已有<span><s:property value="countEmp" /></span>人评价<i><s:if
										test="member_grade>0">
										<s:property value="member_grade" />
									</s:if> <s:else>0</s:else></i>分
							</p>
						</div>
					</div>
					<div class="row">
						<div class="col-md-6">
							<h6>服务项目：</h6>
							<span><s:property value="courses" /></span>
						</div>
						<div class="col-md-6">
							<h6>专业资质：</h6>
							<span><s:property value="certificates" /></span>
						</div>
					</div>
				</div>
			</div>
		</s:if>
	</s:iterator>
</div>
<div class="row">
	<s:iterator value="pageInfo.items" status="st">
		<s:if test="(#st.index/2)==1">
			<div class="col-md-6">
				<div class="teach_name">
					<h4>
						<span>教练昵称：<s:property value="name" /></span>
					</h4>
				</div>
				<div class="teach_message">
					<div class="row">
						<div class="col-md-4">
							<a
								href="javascript:goMemberHome('<s:property value="id"/>','<s:property value="role"/>');"><s:if
									test="image == '' || image == null">
									<img src="images/userpho.jpg" class="img1" />
								</s:if> <s:else>
									<img src="picture/<s:property value="image"/>" class="img1" />
								</s:else></a>
						</div>
						<div class="col-md-8">
							<p>
								已有<span><s:property value="countEmp" /></span>人评价<i><s:if
										test="member_grade>0">
										<s:property value="member_grade" />
									</s:if> <s:else>0</s:else></i>分
							</p>
						</div>
					</div>
					<div class="row">
						<div class="col-md-6">
							<h6>服务项目：</h6>
							<span><s:property value="courses" /></span>
						</div>
						<div class="col-md-6">
							<h6>专业资质：</h6>
							<span><s:property value="certificates" /></span>
						</div>
					</div>
				</div>
			</div>
		</s:if>
	</s:iterator>
</div>
<div class="row">
	<s:iterator value="pageInfo.items" status="st">
		<s:if test="(#st.index/2)==2">
			<div class="col-md-6">
				<div class="teach_name">
					<h4>
						<span>教练昵称：<s:property value="name" /></span>
					</h4>
				</div>
				<div class="teach_message">
					<div class="row">
						<div class="col-md-4">
							<a
								href="javascript:goMemberHome('<s:property value="id"/>','<s:property value="role"/>');"><s:if
									test="image == '' || image == null">
									<img src="images/userpho.jpg" class="img1" />
								</s:if> <s:else>
									<img src="picture/<s:property value="image"/>" class="img1" />
								</s:else></a>
						</div>
						<div class="col-md-8">
							<p>
								已有<span><s:property value="countEmp" /></span>人评价<i><s:if
										test="member_grade>0">
										<s:property value="member_grade" />
									</s:if> <s:else>0</s:else></i>分
							</p>
						</div>
					</div>
					<div class="row">
						<div class="col-md-6">
							<h6>服务项目：</h6>
							<span><s:property value="courses" /></span>
						</div>
						<div class="col-md-6">
							<h6>专业资质：</h6>
							<span><s:property value="certificates" /></span>
						</div>
					</div>
				</div>
			</div>
		</s:if>
	</s:iterator>
</div>
<div class="row">
	<s:iterator value="pageInfo.items" status="st">
		<s:if test="(#st.index/2)==3">
			<div class="col-md-6">
				<div class="teach_name">
					<h4>
						<span>教练昵称：<s:property value="name" /></span>
					</h4>
				</div>
				<div class="teach_message">
					<div class="row">
						<div class="col-md-4">
							<a
								href="javascript:goMemberHome('<s:property value="id"/>','<s:property value="role"/>');"><s:if
									test="image == '' || image == null">
									<img src="images/userpho.jpg" class="img1" />
								</s:if> <s:else>
									<img src="picture/<s:property value="image"/>" class="img1" />
								</s:else></a>
						</div>
						<div class="col-md-8">
							<p>
								已有<span><s:property value="countEmp" /></span>人评价<i><s:if
										test="member_grade>0">
										<s:property value="member_grade" />
									</s:if> <s:else>0</s:else></i>分
							</p>
						</div>
					</div>
					<div class="row">
						<div class="col-md-6">
							<h6>服务项目：</h6>
							<span><s:property value="courses" /></span>
						</div>
						<div class="col-md-6">
							<h6>专业资质：</h6>
							<span><s:property value="certificates" /></span>
						</div>
					</div>
				</div>
			</div>
		</s:if>
	</s:iterator>
</div>
<div class="row">
	<div class="col-md-12">
		<s:include value="/share/pageUtil.jsp" />
	</div>
</div>
