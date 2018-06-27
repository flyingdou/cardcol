<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>专家系统</title>
		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
		<link href="${pageContext.request.contextPath}/eg/css/mui.min.css" rel="stylesheet" />
		<style>
			.fleft{
				float: left;
			}
			.fright{
				float: right;
			}
			.tright{
				text-align: right;
			}
			.tleft{
				text-align: left;
			}
			.tcenter{
				text-align: center;
			}
			.fix{
				clear: both;
			}
			.boxcss{
				padding: 10px;
			}
			.body{
				background: white!important;
			}
			.mui-content{
				background: white;
			}
			.jsxt{
				width: 16px;
				height: auto;
				padding-left: 15px;
				padding-top: 15px;
				font-weight: 700;
			}
			.money{
				color:red;
				font-size: 18px;
				padding-right: 10px;
				margin-bottom: 5px;
			}
			.dz{
				margin: 0 auto;
				font-size: 14px;
				font-weight: 600;
				background: red;;
				padding: 2px;
				width: 100px;
				border-radius: 6px;text-align: center;
				color:white
			}
			.tbbottom{
				border-top:1px solid #eaeaea ;
				border-bottom: 1px solid #eaeaea;
			}
			.grey{
				color:grey
			}
		</style>
	</head>

	<body>
		<script src="${pageContext.request.contextPath}/eg/js/mui.min.js"></script>
		<script type="text/javascript">
			mui.init()
		</script>
		<div class="mui-content">
		    <div class=" boxcss">
		    	<div class="fleft jsxt">
		    		健身专家系统
		    	</div>
		    	<div class="fright">
		    		<img src="${pageContext.request.contextPath}/WX/picture/<s:property value='goods.image1'/>" alt="" width="144px"height="144px"/>
		    		<p>
		    			<div class="tright money">￥<s:property value="goods.price" /></div>
		    			<div class="tcenter dz"id="dz">我要定制</div>
		    		</p>
		    	</div>
		    	<div class="fix"></div>
		    </div>
		       <div class="boxcss tbbottom">
		    	<h4>专家介绍</h4>
		    	<p style="text-indent: 2em;">王严老师从1981年开始进行健美锻炼并从事健身健美训练的研究、教学及健美比赛裁判工作。现任北京市健美协会副主席，国际级健美裁判员，中国健美协会裁判员、裁判委员会委员、培训委员会委员、教练委员会委员。著有《健身运动指导全书》、《青年健美ABC》和《想对健身者说》等专著。</p>
		    </div>
		       <div class="boxcss">
		    	<h4>系统简介</h4>
		    	<p style="text-indent: 2em;">
		    		<span class="grey">计划类型：</span><s:property value="goods.planType" />
		    	</p>
		    	<p style="text-indent: 2em;">
		    		<span class="grey">适用对象：</span><s:property value="goods.applyObject" />
		    	</p>
		    	<p style="text-indent: 2em;">
		    		<span class="grey">计划周期：</span><s:property value="goods.plancircle" />
		    	</p>
		    	<p style="text-indent: 2em;">
		    		<span class="grey">使用场景：</span><s:property value="goods.scene" />
		    	</p>
		    	<p style="text-indent: 2em;">
		    		<span class="grey">所需器材：</span><s:property value="goods.apparatuses" />
		    	</p>
		    </div>
		</div>
	</body>
	<script src="${pageContext.request.contextPath}/eg/bodytest/js/jquery-2.2.0.min.js"></script>	
      <script>
      	var dz=document.getElementById('dz');
    	dz.addEventListener('click',function(){
    		location.href='goodswx!movementtype.asp';
    	});
      </script>	
</html>