<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!doctype html >
<html>
<head>
<s:include value="/share/meta.jsp" />
<meta name="keywords" content="瘦身减肥课程,肌肉健美课程,增强体质" />
<meta name="description"
	content="健身教练的商务中心是健身教练制定的健身套餐,健身课程，如：瘦身减肥课程,肌肉健身课程,增强体质课程等。" />
<title>我发起的砍价活动</title>
<link href="css/club-order.css" rel="stylesheet" type="text/css" />
<link type="text/css" rel="stylesheet" href="css/pulicstyle.css" />
<link rel="stylesheet" type="text/css" href="css/activ_new.css" />
<style type="text/css">
.pagenumber1 ul{ padding-right:7px;}

.btn_create{
    margin-top: 10px;
    margin-left: 10px;
	width: 77px;
    height: 25px;
    line-height: 25px;
    text-align: center;
    border: none;
    cursor: pointer;
    background: url(../images/anjiuall.jpg);
    background-position: 0px -167px;
}
</style>

</head>
<body>
	<s:include value="/share/header.jsp" />
	<div id="content">
	<s:include value="/order/nav.jsp" />
		<div id="right-2" >
			<s:form name="queryForm" id="queryForm" method="post" action="active.asp" theme="simple">
<div id="container">
	<h1>我发起的砍价活动</h1>
	  <input type="button" class="btn_create" value="发砍价活动" @click="release()"/>
      <div class="t_lists" style="padding-left:0px; padding-top:0px;"  >
        <table width="100%" border="0" cellpadding="0" cellspacing="0"  class="t_lists_table">
          <tr class="head_table"  style="text-align:center;">
            <td width="120" ><strong>名称</strong></td>
            <td width="50" ><strong>摘要</strong></td>
            <td width="70" ><strong>操作</strong></td>
          </tr>
          
          <!-- vue遍历 -->
          <tr class="top_right" v-for="(price,i) in priceData">
          	<td style="height:25px;text-align: center;" hidden="hidden">{{price.id}}</td>
          	<td style="text-align: center;">{{price.activeName}}</td>
           	<td style="text-align: center;">原价{{price.money}}元 砍价至{{price.lowPrice}}元</td>   
          	<td style="border-right:0px;text-align: center;" class="pj" >
          		 <a @click="changeStatus(i)" class="status" ><span v-if="price.status == '0'">关闭</span><span v-if="price.status == '1'">开启</span></a>
          		 <a @click="editPriceCutdown(i)" v-if="price.status == '1'  ">编辑</a>
          		 <a @click="priceActiveDetail(i)" class="lookDetail">查看详情</a>
          	</td>
          </tr>
          
        </table>
      </div>
	  <div class="page_rigddd"><s:include value="/share/pageAjax.jsp"/></div>
</div>
</s:form>
		</div>
	</div>
	<s:include value="/share/footer.jsp" />
	
	<!-- 引入js文件 -->
	<script src="js/vue.min.js"></script>
	
	<script>
			/* 页面载入函数 */
		  	var priceData = {};
	        var ret = ${ret};
	        priceData = ret.priceActiveList == undefined ? [] : ret.priceActiveList;
	        var appData = new Vue({
		  	    	   el:"#right-2",
		  	    	   data: {
		  	    		   priceData: priceData
		  	    	   },
		  	    	   methods:{
		  	    		   // 发布砍价活动
		  	    		   release: function () {
		  	    			 var param = {
		  	    					 source:"release"
		  	    			 }; 
		  	    			 window.location.href="clubmp!launchPriceActive.asp?json=" + encodeURI(JSON.stringify(param));
		  	    		   },
		  	    		   //活动 的关闭/开启 
		  	    		   changeStatus: function (i) {
		  	    			   var param = {
		  	    				   priceId: priceData[i].id
		  	    			   };
		  	    			   $.ajax({
		  	    				   url:"clubmp!changePriceStatus.asp",
		  	    				   dataType: "json",
		  	    				   data: {
		  	    					   json: encodeURI(JSON.stringify(param))
		  	    				   },
		  	    				   success: function (res) {
		  	    					   if (res.success) {
		  	    						   appData.priceData[i].status = res.status;
		  	    					   } else {
		  	    						   alert(res.message);
		  	    					   }
		  	    				   },
		  	    				   error: function (e) {
		  	    					   alert("网络异常");
		  	    				   }
		  	    			   })
		  	    		   },
		  	    		   
		  	    		   // 编辑、修改砍价活动
		  	    		   editPriceCutdown: function (i) {
		  	    			   var param = {
		  	    				 priceId: priceData[i].id,
		  	    				 source:"edit"
		  	    			   };
		  	    			   window.location.href="clubmp!launchPriceActive.asp?json=" + encodeURI(JSON.stringify(param));
		  	    		   },
		  	    		   
		  	    		   // 查看活动详情
		  	    		   priceActiveDetail: function (i) {
		  	    			   var param = {
		  	    				   priceId: priceData[i].id,
		  	    				   source:"display"
		  	    			   };
		  	    			   window.location.href="clubmp!launchPriceActive.asp?json=" + encodeURI(JSON.stringify(param));
		  	    		   }
		  	    	   
		  	    	   
		  	    	   }
		  	       })
	        
	        
	        
	</script>
	
	
</body>
</html>
