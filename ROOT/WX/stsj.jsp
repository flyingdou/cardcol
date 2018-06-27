<!doctype html>
<html>

	<head>
		<meta charset="UTF-8">
		<title></title>
		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
		<link href="../css/mui.min.css" rel="stylesheet" />
	

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
			/*.mui-content{
				background: white;
			}*/
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
			.w100{
				width: 100%;
				border-bottom: 1px solid #B6B6B6;
			}
			.w33{
				width: 33.3%;
				float: left;
				height: 30px;
				text-align: center;
				background: white;
				padding-top: 5px;
				font-size: 13px;
			}
			.redspan{
				height: 25px;
				line-height: 20px;
				display: inline-block;
				color:red;
				border-bottom: 2px solid red;
			}
			.blackspan{
				color:black;
				border: none;
			}
			.raioBox{
				padding:10px 20px;border-bottom: 1px solid #eaeaea;
				background: white;
				height: 43px;box-sizing: border-box;font-size: 12px;
			}
			.labelradio{
				margin-left: 40px;font-size: 14px;
			}
			.sex{
				float:left;text-indent: 1.5em;background: url(../images/xb.png)no-repeat scroll 0 4px/16px auto;
			}
			.selectcss{
				padding: 0;font-size: 12px;
			}
			.sgimg{
				float:left;text-indent: 1.5em;background: url(../images/man.png)no-repeat scroll 0 4px/16px auto;
			}
			.ywimg{
				float:left;text-indent: 1.5em;background: url(../images/men.png)no-repeat scroll 0 4px/16px auto;
			}
			.sgbox{
				float:right;width: 50%;
			}
			.srbox{
				float:right;width: 50%;
			}
			.tzimg{
				float:left;text-indent: 1.5em;background: url(../images/weight.png)no-repeat scroll 0 4px/16px auto;
			}
			.srinput{
				width: 100%!important;padding: 0!important;line-height: 20px!important;font-size: 14px;text-align:right!important;height: 20px!important;border:none!important
			}
			.wtimg{
				float:left;text-indent: 1.5em;background: url(../images/act.png)no-repeat scroll 0 4px/16px auto;
			}
			.footercss{
				border:none;background: white;padding: 0;
			}
			.footerbotton{
				background: orange;color:white;width: 100%;height: 100%;box-sizing: border-box;border:none;border-radius: 0;display:block;float: left;
			}
		</style>

	</head>

	<body>
		<script src="../js/mui.min.js"></script>
		<script type="text/javascript">
			mui.init()
		</script>
			<footer class="mui-bar mui-bar-footer font-white footercss" style="">
							<button type="button"class="footerbotton" style="">下一步</button>
		                 </footer>
		<div class="mui-content">
		    <div class="w100">
		    	<div class="w33"><span class="black">健身目的</span></div>
		    	<div class="w33 "><span class="redspan">身体数据</span></div>
		    	<div class="w33 "><span class="black">喜欢有氧运动</span></div>
		    	<div class="fix"></div>
		    </div>
		    <form action="">
		    	 <div class="raioBox"style="">
		           <div class="sex">
		           	性别:
		           </div>
		           <div class="fright">
		           <select name=""class="selectcss">
		           	<option value="男">男</option>
		           		<option value="女">女</option>
		           </select>
		           </div>
		           <div class="fix"></div>
		         </div>
		          <div class="raioBox">
		                      <div class="sgimg">
		           	身高:
		           </div>
		           <div class="sgbox"><input type="text" name="" id="" value="" placeholder="输入身高"class="srinput"/></div>
		           <div class="fix"></div>
		         </div>
		  	          <div class="raioBox">
		                      <div class="ywimg">
		           	腰围:
		           </div>
		           <div class="srbox"><input type="text" name="" id="" value="" placeholder="输入腰围"class="srinput"/></div>
		           <div class="fix"></div>
		         </div>
		                   <div class="raioBox"style="">
		                      <div class="tzimg">
		           	体重:
		           </div>
		           <div class="srbox"><input type="text" name="" id="" value="" placeholder="输入体重"class="srinput"/></div>
		           <div class="fix"></div>
		         </div>
		                       <div class="raioBox">
		                      <div class="wtimg">
		           	最大卧推重量:
		           </div>
		           <div class="srbox"><input type="text" name="" id="" value="" placeholder="输入重量"class="srinput"/></div>
		           <div class="fix"></div>
		         </div>

		    </form>
		   
		    
		    
		</div>
				
	</body>
	<script src="../js/mui.min.js"></script>
	
</html>