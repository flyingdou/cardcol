<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<script type="text/javascript">
	$(function() {
		$("#ul_order li").click(
				function() {
					var id = $(this).attr("id");
					var oldCls = $(this).attr("class");
					var flag=$.trim(oldCls);
					$(this).attr(
							"class",
							(flag === "none" ? "desc"
									: (flag === "asc" ? "none"
											: (flag === "desc" ? "asc"
													: "desc"))));
					var newCls = $(this).attr("class");
					$("#orderFlag").val(newCls == "none" ? "" : newCls);
					if (id === "timeOrder") {
						$("#order").val("publishTime");
					} else if (id === "countOrder") {
						$("#order").val("saleout");
					} else if (id === "socureOrder") {
						$("#order").val("avgCount");
					} else {
						$("#order").val("unitPrice");
					}
					queryByPage(1);
				});
	})
</script>
     <div class="row">
                                <div class="col-md-12" style="margin-top: 5px">
                                    <div class="chose clearfix">
                                        <div class="list_span">
                                           <span> 已选条件：
			              <s:if test="targetName!=null && targetName!=''">
									<a href="javascript:onPlanQuery('taget','','');"><b><s:property
												value="targetName" /></b><img src="images/plan_lde.png" /></a>
								</s:if>&nbsp;&nbsp;
								<s:if test="applyObjectName!=null && applyObjectName!=''">
									<a href="javascript:onPlanQuery('applyObject','','');"><b><s:property
												value="applyObjectName" /></b><img src="images/plan_lde.png" /></a>
								</s:if>&nbsp;&nbsp;
								<s:if test="sceneName!=null && sceneName!=''">
									<a href="javascript:onPlanQuery('scene','','');"><b><s:property
												value="sceneName" /></b><img src="images/plan_lde.png" /></a>
								</s:if>&nbsp;&nbsp;
								<s:if test="plancircleName!=null && plancircleName!=''">
									<a href="javascript:onPlanQuery('plancircle','','');"><b><s:property
												value="plancircleName" /></b><img src="images/plan_lde.png" /></a>
								</s:if>&nbsp;&nbsp;
                                            </span>
                                        </div>
                                    </div>
                                </div>
                            </div>
					
                <div class="row">
                                <div class="col-md-6">
                                    <div class="show_style">
                                        <ul id="ul_order">
											<li
												class="<s:if test="pageInfo.order =='publishTime'">
														<s:if test="pageInfo.orderFlag ==''">none</s:if>
														<s:elseif test="pageInfo.orderFlag =='asc'">asc</s:elseif>
														<s:else>desc</s:else>
												</s:if>"
												id="timeOrder">默认：<a href="javascript:void(0);">时间</a></li>
											<li
												class="<s:if test="pageInfo.order =='saleout'"><s:if test="pageInfo.orderFlag ==''">none</s:if><s:elseif test="pageInfo.orderFlag =='asc'">asc</s:elseif><s:else>desc</s:else></s:if>"
												id="countOrder"><a href="javascript:void(0);">销量<img src="images/sanjiawxz_31.png" class="down_up"></a></li>
											<li
												class="<s:if test="pageInfo.order =='avgCount'"><s:if test="pageInfo.orderFlag ==''">none</s:if><s:elseif test="pageInfo.orderFlag =='asc'">asc</s:elseif><s:else>desc</s:else></s:if>"
												id="socureOrder"><a href="javascript:void(0);">评分<img src="images/sanjiawxz_31.png" class="down_up"></a></li>
										    <li
												class="<s:if test="pageInfo.order =='unitPrice'"><s:if test="pageInfo.orderFlag ==''">none</s:if><s:elseif test="pageInfo.orderFlag =='asc'">asc</s:elseif><s:else>desc</s:else></s:if>"
												id="priceOrder"><a href="javascript:void(0);">价格<img src="images/sanjiawxz_31.png" class="down_up"></a></li>		
										</ul>
                                    </div>
                                </div>
                                <div class="col-md-6">
                                    <div class="show_list">
                                <span>到</span>&nbsp;&nbsp;&nbsp;&nbsp;
                                <input type="text" name="goPage" id="goPage">页
                                <button type="button" class="btn btn-primary btn-xs" onclick="javascript:queryByGoPage();">跳转</button>
                                <span>&nbsp;<s:property value="pageInfo.currentPage" />&nbsp;</span>/<span>&nbsp;<s:property value="pageInfo.totalPage" />&nbsp;</span>&nbsp;&nbsp;&nbsp;&nbsp;
                                <img src="images/next-icon_03.png">
                            </div>
                                </div>
                                  <script type="text/javascript">
				                    function queryByGoPage(){
				                    	if(jQuery("#goPage").val()){
				                    		var nowPage=jQuery("#currentPage").val(jQuery("#goPage").val()).val();
				                    		queryByPage(nowPage);
				                    	}
				                    	}
                                 </script>
                            </div>

                <div id="container">
                                <div class="row">
                                <s:iterator value="pageInfo.items" status="st" var="plan">
                                <s:if test="(#st.index/3)==0">
                                    <div class="col-md-4">
                                       <s:if test="unit_price==0||unit_price==null"> <span class="free" >
                                    免费</span></s:if>
                                    <s:else>
                                        <span class="price">
                                        ￥<s:property value="unit_price" /></span></s:else>
                                        <a
											href="javascript:goMemberHome('<s:property value="id"/>','<s:property value="role"/>');"
											class="plansk"> <s:if test="image1 == '' || image1 == null">
												<a href="plan.asp?pid=<s:property value="id"/>"><img src="images/userpho.jpg" class="plan_img" /></a>
											</s:if> <s:else>
												<a href="plan.asp?pid=<s:property value="id"/>"><img src="picture/<s:property value="image1"/>" /></a>
											</s:else>
										</a> 
                                        <h5><a	href="plan.asp?pid=<s:property value="id"/>"><s:property value="plan_name" /></a></h5>
                                        <p class="book_content">
                                        <s:if test="briefing.length()==0"><br></s:if>
                                        <s:elseif test="briefing.length()>36"><s:property value="briefing.substring(0,35)" />...</s:elseif>
                                        <s:else><s:property value="briefing" /></s:else></p>
                                        <p class="down">下载量<span><s:if test="saleNum==0\\saleNum==null">0
                                        </s:if>
                                        <s:else><s:property value="saleNum"/></s:else>
                                        </span></p>
                                        <p class="down">评价<span><s:if test="#request.appraiseNum.get(#plan.id+'')>0">
									    <s:property value="#request.appraiseNum.get(#plan.id+'')" />
								       </s:if>
								       <s:else>0</s:else></span></p>
                                    </div>
                                </s:if>    
                                </s:iterator>
                                </div>
                                <div class="row">
                                <s:iterator value="pageInfo.items" status="st" var="plan">
                                <s:if test="(#st.index/3)==1">
                                    <div class="col-md-4">
                                       <s:if test="unit_price==0||unit_price==null"> <span class="free" >
                                    免费</span></s:if>
                                    <s:else>
                                        <span class="price">
                                        ￥<s:property value="unit_price" /></span></s:else>
                                        <a
											href="javascript:goMemberHome('<s:property value="id"/>','<s:property value="role"/>');"
											class="plansk"> <s:if test="image1 == '' || image1 == null">
												<a href="plan.asp?pid=<s:property value="id"/>"><img src="images/userpho.jpg" class="plan_img" /></a>
											</s:if> <s:else>
												<a href="plan.asp?pid=<s:property value="id"/>"><img src="picture/<s:property value="image1"/>" /></a>
											</s:else>
										</a> 
                                        <h5><a	href="plan.asp?pid=<s:property value="id"/>"><s:property value="plan_name" /></a></h5>
                                        <p class="book_content"><s:if test="briefing.length()==0"><br></s:if>
                                        <s:elseif test="briefing.length()>36"><s:property value="briefing.substring(0,35)" />...</s:elseif>
                                        <s:else><s:property value="briefing" /></s:else></p>
                                        <p class="down">下载量<span><s:if test="saleNum==0\\saleNum==null">0
                                        </s:if>
                                        <s:else><s:property value="saleNum"/></s:else>
                                        </span></p>
                                        <p class="down">评价<span><s:if test="#request.appraiseNum.get(#plan.id+'')>0">
									    <s:property value="#request.appraiseNum.get(#plan.id+'')" />
								       </s:if>
								       <s:else>0</s:else></span></p>
                                    </div>
                                </s:if>    
                                </s:iterator>
                                </div>
                                <div class="row">
                                <s:iterator value="pageInfo.items" status="st" var="plan">
                                <s:if test="(#st.index/3)==2">
                                    <div class="col-md-4">
                                       <s:if test="unit_price==0||unit_price==null"> <span class="free" >
                                    免费</span></s:if>
                                    <s:else>
                                        <span class="price">
                                        ￥<s:property value="unit_price" /></span></s:else>
                                        <a
											href="javascript:goMemberHome('<s:property value="id"/>','<s:property value="role"/>');"
											class="plansk"> <s:if test="image1 == '' || image1 == null">
												<a href="plan.asp?pid=<s:property value="id"/>"><img src="images/userpho.jpg" class="plan_img" /></a>
											</s:if> <s:else>
												<a href="plan.asp?pid=<s:property value="id"/>"><img src="picture/<s:property value="image1"/>" /></a>
											</s:else>
										</a> 
                                        <h5><a	href="plan.asp?pid=<s:property value="id"/>"><s:property value="plan_name" /></a></h5>
                                        <p class="book_content"><s:if test="briefing.length()==0"><br></s:if>
                                        <s:elseif test="briefing.length()>36"><s:property value="briefing.substring(0,35)" />...</s:elseif>
                                        <s:else><s:property value="briefing" /></s:else></p>
                                        <p class="down">下载量<span><s:if test="saleNum==0\\saleNum==null">0
                                        </s:if>
                                        <s:else><s:property value="saleNum"/></s:else>
                                        </span></p>
                                        <p class="down">评价<span><s:if test="#request.appraiseNum.get(#plan.id+'')>0">
									    <s:property value="#request.appraiseNum.get(#plan.id+'')" />
								       </s:if>
								       <s:else>0</s:else></span></p>
                                    </div>
                                </s:if>    
                                </s:iterator>
                                </div>
                            </div>
              		   <div class="row" >
                       <div class="col-md-12">
                                    <s:include value="/share/pageUtil.jsp" />
                            </div>
                     </div>
                     </div>  
               