<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>        
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="keywords" content="健身,健身计划,健身计划表,哑铃健身计划,减肥计划表,力量训练,有氧训练,柔韧训练,增肌" />
<meta name="description" content="健身计划列表中有健身者分享好的健身计划,有针对性的健身课程，比如：瘦身计划,减肥计划,健美课程,塑身课程等。" />
<title>挑战目标</title>
<script type="text/javascript">
$(function() {
	$('#ul_order li').click(
				function() {
					var id = $(this).attr('id');
					var oldCls = $(this).attr('class');
					$('#ul_order li').attr('class', 'none');
					$(this).attr(
							'class',
							(oldCls === 'none' ? 'desc'
									: (oldCls === 'asc' ? 'none'
											: (oldCls === 'desc' ? 'asc'
													: 'desc'))));
					var newCls = $(this).attr('class');
					$('#orderFlag').val(newCls == 'none' ? "" : newCls);
					if (id === 'timeOrder') {
						$('#order').val("createTime");
					} else {
						$('#order').val("partake_num");
					}
					queryByPage(1);
				});
	})
	
	
	
</script>
<script type="text/javascript">
//增加部分
//修改图片的样式
$(function() {
	var width_zhi;
	$('.weight  img').css({
		width : function(index,value){
			width_zhi=value;
		},
		height : function(index,value){
			var img_height=width_zhi;
			var b=img_height.replace(/[^0-9]+/ig,'');
			var value=b*0.75+"px";
			return  value ;
		}
	});
});
</script>
                <div class="row">
                    <div class="col-md-12">
                        <div class="challenge_cause">
                            <p><i class="glyphicon glyphicon-play"></i>挑战目标：</p>
                            <span><a href="javascript:onQuery('target','','');" style="color: white">全部</a></span>
                            <s:iterator value="#request.activeTargetList">
                            <span><a href="javascript:onQuery('target','<s:property value="code"/>','<s:property value="name"/>');" style="color: white"><s:property value="name" /></a></span></s:iterator>
                        </div>
                    </div>
                </div>
                <div class=" row">
                    <div class="col-md-6">
                        <div class="show_style">
		               <ul id="ul_order">
			            <li class="<s:if test="pageInfo.order =='createTime'"><s:if test="pageInfo.orderFlag ==''">none</s:if><s:elseif test="pageInfo.orderFlag =='asc'">asc</s:elseif><s:else>desc</s:else></s:if>" id = "timeOrder">默认：<a href="javascript:void(0);" style="color: black">发布时间</a></li>
			            <li class="<s:if test="pageInfo.order =='partake_num'"><s:if test="pageInfo.orderFlag ==''">none</s:if><s:elseif test="pageInfo.orderFlag =='asc'">asc</s:elseif><s:else>desc</s:else></s:if>" id = "partakeNumOrder"><a href="javascript:void(0);" style="color: black"><img src="images/sanjiawxz_31.png" class="down_up">参加人数</a></li>
 	                   </ul>
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div class="show_list">
                                <span>到</span>&nbsp;&nbsp;&nbsp;&nbsp;
                                <input type="text" value="" name="goPage" id="goPage" >页
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
                <s:iterator value="pageInfo.items">
                <div class="row">
                            <div class="col-md-12">
                                <div class="weight clearfix">
                                    <div class="col-md-4 col-lg-3">
<%--                                         <img src="picture/<s:property value="active_image"/>"> --%>
                                         <img src='picture/<s:property value="active_image"/>'  />
                                    </div>
                                    <div class="middle col-md-5 col-lg-5">
                                        <h3><s:property value="name"/></h3>
                                        <p>已有<s:if test="partake_num!=null"><s:property value="partake_num"/></s:if><s:else>0</s:else>人参加&nbsp;&nbsp;&nbsp;
                                      <span>发起人：</span><a  class="fqr_name"><s:property value="creatorName"/></a>
                                       </p>
                                        <h6><span>挑战目标：</span><s:property value="days"/>天
                                        <s:if test="category==\"A\"">体重增加<s:property value="value"/>KG</s:if>
											<s:elseif test="category==\"B\"">体重减少<s:property value="value"/>KG</s:elseif>
											<s:elseif test="category==\"C\"">体重保持在<s:property value="value"/>%左右</s:elseif>
											<s:elseif test="category==\"D\"">运动<s:property value="value"/>次</s:elseif>
											<s:elseif test="category==\"E\"">运动<s:property value="value"/>小时</s:elseif>
											<s:elseif test="category==\"F\"">每周运动<s:property value="value"/>次</s:elseif>
											<s:elseif test="category==\"G\""><s:if test="action == null"></s:if><s:else><s:property value="action"/></s:else><s:property value="value"/>千米</s:elseif>
											<s:elseif test="category==\"H\"">力量运动负荷<s:property value="value"/>KG</s:elseif></h6>
                                        <h6 class="success"><span class="jiangcheng">成功奖励：</span><s:property value="award"/></h6>
                                        <h6><span class="jiangcheng">失败惩罚：</span>向<s:property value="institutionName"/>捐款<s:property value="amerce_Money"/>元。</h6>
                                    </div>
                                    <div class="right col-md-2 col-lg-2 col-lg-offset-1">
                                        <span><a href="activewindow.asp?id=<s:property value="id"/>" style="color: white">参加挑战</a></span>
                                    </div>
                                </div>
                            </div>
                        </div>
                </s:iterator>
                 <div class="row" >
                                <div class="col-md-12">
                                    <s:include value="/share/pageUtil.jsp" />
                                </div>
                            </div>
