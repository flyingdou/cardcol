<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!doctype html>
<head>
<s:include value="/share/meta.jsp" />
<meta name="keywords" content="瘦身减肥课程,肌肉健美课程,增强体质" />
<meta name="description" content="健身教练的商务中心是健身教练制定的健身套餐,健身课程，如：瘦身减肥课程,肌肉健身课程,增强体质课程等。" />
<title>我发起的挑战</title>
<link href="css/club-order.css" rel="stylesheet" type="text/css" />
<link type="text/css" rel="stylesheet" href="css/pulicstyle.css" />
<link rel="stylesheet" type="text/css" href="css/activ_new.css">

<style type="text/css">
.pagenumber1 ul {
	padding-right: 7px;
}

.kuangdd {
	display: block;
	float: left;
}

.zhixia {
	padding-left: 5px;
	padding-top: 6px;
	display: block;
	float: left;
}

.fap p {
	line-height: 38px;
}



.container1234{  
	 height:250px; 
    width: 300px;  
}  
.resizer{  
    overflow: hidden;  
}  
.resizer.have-img button.ok{  
    display: inline-block;  
}  
.resizer.have-img .inner {  
    display: block;  
}

.inner{  
    width: 100%;  
    position: relative;  
    font-size: 0;  
    overflow: hidden;  
    display: none;  
}  
 #tmpimg{  
    width: 100%;  
}  
  
.frames{  
    position: absolute;  
    top: 0;  
    left: 0;  
    border: 1px solid black;  
    cursor: move;  
    outline: rgba(0, 0, 0, 0.6) solid 10000px;  
}  
button.ok{  
    float:right;  
    margin-left: 5px;  
    display: none;  
}  
canvas{  
    max-width: 100%;  
    margin:auto;  
    display: block;  
} 

li {
    margin:0;
    padding:0;
    list-style: none;
    overflow: hidden;
    margin-top: 10px;
}


 .img-con {
      width: 800px;
      height: 400px;
      border: 1px solid #ccc;
      float: left;
  }

  .img-pre {
      width: 400px;
      height: 400px;
      float: right;
      border: 1px solid #eee;
  }

  .img-pre img {
      width: 180px;
      height: 180px;
      padding: 10px;
  }

  .img-con img {
      width: 100%;
      height: 100%;
  }
  
  .richRemark{
     width: 100%;
     height: 510px;
  }
  
  
  .remarkDou{
   margin-top: 20px;
   width:100%;
   height:480px;
  }
  
  .dou_ul{
   postion: relative;
   margin:0;
   padding:0;
   list-style: none;
  }
  
  
  .sure{
   position: absolute;
   left:50%;
   top:95%;
   width: 240px;
   height: 100px;
   line-height: 100px;
  }
  
</style>

<script src="script/ckeditor/ckeditor.js"></script>
<script src="script/uploadPreview.js"></script>


<script>
	$(function(){
		new uploadPreview({
			UpBtn : "txtImage1",
			ImgShow : "preview1"
		});
	})

	window.onload = function () {
		var ret = ${ret};
		var productList = ret.productList == undefined ? [] : ret.productList;
		var priceActive = ret.priceActive == undefined ? {} : ret.priceActive;
		var source = ret.source == undefined ? "" : ret.source;
		
		
		var editActveTitle = "发砍价活动";
		var showInput = true;
		var showSpan = false;
		var poster = "images/img-default.jpg";
		
		
		// 发布活动
		if (source == "release") {
			editActiveTitle = "发砍价活动";
			showInput = true;
			showSpan = false;
			poster = "http://www.ecartoon.com.cn/images/img-defaul.jpg";
			// 海报
			$("#preview1").attr("src", poster);
			var options4dou = "";
			for(var y = 0; y < productList.length; y++ ){
				if (y == 0) {
					options4dou += "<option value = " + productList[y].id  + " selected='selected' >" + productList[y].name + "</option>";
				    continue;
				}
				options4dou += "<option value = " + productList[y].id  + " >" + productList[y].name + "</option>";
				
			}
			$("#productSelect").html(options4dou);
			
			// 注意事项
			$("#editor1").val("");
		}
		
		// 修改活动
		if (source == "edit") {
			editActiveTitle = "修改活动";
			var options4dou = "";
			for (var x=0; x < productList.length; x++) {
				 productList[x].id = parseInt(productList[x].id);
				 if (productList[x].id == priceActive.product) {
					 options4dou += "<option value = " + productList[x].id  + " selected='selected' >" + productList[x].name + "</option>";
					 continue;
				 }
				 options4dou += "<option value = " + productList[x].id  + " >" + productList[x].name + "</option>";
			}
			$("#productSelect").html(options4dou);
			
			showInput = true;
			showSpan = false;
			poster = "http://www.ecartoon.com.cn/picture/" + priceActive.poster;
			
			// 海报
			$("#preview1").attr("src", poster);
			
			// 活动名称
			$("#activeName").val(priceActive.activeName);
			
			// 活动有效期
			$("#period").val(priceActive.period);
			
			// 商品低价
			$("#lowPrice").val(priceActive.lowPrice);
			
			// 砍价次数
			$("#totalTimes").val(priceActive.totalTimes);
			
			// 注意事项
			$("#editor1").val(priceActive.remark);
			
		}
		
		// 查看活动详情
		if (source == "display") {
			var productName = "";
			for (var x=0; x < productList.length; x++) {
				if ( parseInt(productList[x].id) == parseInt(priceActive.product) ) {
					productName = productList[x].name;
				}
			}
			editActiveTitle = "活动详情";
			showInput = false;
			showSpan = true;
			poster = "http://www.ecartoon.com.cn/picture/" + priceActive.poster;
			productName = productName;
			
			// 海报
			$("#preview1").attr({"src":poster});
			
			// 活动名称
			$("#activeNameS").html(priceActive.activeName);
			
			// 活动有效期
			$("#periodS").html(priceActive.period);
			
			// 商品名称
			$("#productNameS").html(productName);
			
			// 商品低价
			$("#lowPriceS").html(priceActive.lowPrice);
			
			// 砍价次数
			$("#totalTimesS").html(priceActive.totalTimes);
			
			// 注意事项
			$("#remarkS").html(priceActive.remark);
			
		}
		
		// 设置title
		$("#editActiveTitle").html(editActiveTitle);
		
		// 设置showInput
		if (!showInput) {
			$(".showInput").hide();
		} else {
			$(".showInput").show();
		}
		
		// 设置showSpan
		if (!showSpan) {
			$(".showSpan").hide();
		} else {
			$(".showSpan").show();
		}
		
		if (productName) {
			$("#productName").html(productName);
		}
		
		
	}
	
	
	 // 打开上传窗口
    function openUploadWindow () {
    	var source = "${ret.source}";
    	if(source == "display"){
    		return;
    	}
    	$('#txtImage1').click();
    }

    
   // 当用户点击保存按钮时，收集、校验参数，提交后台保存
   function saveActive(){
		   // 数据非空校验
		   var dou = {};
		   
		   var activeName = $("#activeName").val();
		   if (!activeName || activeName == ""){
			   alert("请填写砍价活动名称！");
			   return;
		   }
		   dou.activeName = activeName;
		   
		   var period = $("#period").val();
		   if (!period || period == ""){
			   alert("请填写有效期！");
			   return;
		   }
		   dou.period = period;
		   
		   // 砍价商品
		   var product = $("#productSelect option:selected").val();
		   if (!product || product == ""){
			   alert("请选择砍价商品！");
			   return;
		   }
		   dou.product = product;
		   
		   var lowPrice = $("#lowPrice").val();
		   if (!lowPrice || lowPrice == ""){
			   alert("请填写商品底价！");
			   return;
		   }
		   dou.lowPrice = lowPrice;
		   
		   var totalTimes = $("#totalTimes").val();
		   if (!totalTimes || totalTimes == ""){
			   alert("请填写砍价次数！");
			   return;
		   }
		   dou.totalTimes = totalTimes;
		   
		   
		   // 设置source
		   var source = "${ret.source}";
		   var ret = ${ret};
		   var priceActive = ret.priceActive == undefined ? {} : ret.priceActive;
		   dou.source = source;
		   if ("edit" == source) {
			   dou.id = priceActive.id;
		   }
		   
		   $("#jsons").val(encodeURI(JSON.stringify(dou)));
		   $("#activeform").attr("action", "clubmp!releasePriceCutdown.asp").submit();
	   
   }
   
   // 正整数校验
   function validateNumber(obj){
       var reg = new RegExp("^[1-9]*$");
	    if(!reg.test(obj.value)){
	        alert("请输入大于0的正整数!");
	    }
   }
	
	
	
	
</script>
</head>
<body>
	<s:include value="/share/header.jsp" />
	<div id="content">
	<s:include value="/order/nav.jsp" />
	<div id="right-2">
	<form name="activeform" id="activeform" theme="simple" method="post" action="active!save.asp" enctype="multipart/form-data">
	<div id="container" style="color: #000;">
		<h1 id = "editActiveTitle"></h1>
		<div style="overflow: hidden; padding-left: 20px; padding-top: 10px" class="fap">
			<ul class="dou_ul">
				<li style="height:78px;line-height: 78px;">
					<div style="width:30%;float: left;">
						<b>砍价活动海报</b>
					</div>
					<div style="width:70%;float: left;">
						<img id="preview1" width="100px" height="75px" onclick="openUploadWindow()" />
							 <input type="file" id="txtImage1" name = "image" style="display: none;">
					</div>
					
					
				</li>
				
				<li style="height: 30px;line-height: 30px;" >
				     <div style="width: 30%;float: left;">
						<b>活动名称</b>
				     </div>
				     
				     <div style="width: 70%; float:left; ">
					     <label class="showInput"> 
					     	<input id = "activeName" />
						 </label>
						 <label>
						 	<span class="showSpan" id = "activeNameS"></span>
						 </label>
				     </div>
				</li>
				
				<li style="height: 30px; line-height: 30px; " >
				    <div style="width: 30%; float: left;">
				         <b>活动有效期</b>
				    </div>
				    
				    <div style="width: 70%; float: left;" >
				        <input type="number" onchange="validateNumber(this)"  class = "showInput" id = "period"  style="border: 1px solid #A5A3A5;" />
				        <span class = "showSpan" id = "periodS"></span>
				          天
				    </div>
				</li>
				
				<li style="height: 30px; line-height: 30px;">
				    <div style="width: 30%; float: left;">
				         <b>选择砍价商品</b>
				    </div>
				    
				    <div style="width: 70%;float: left;" class="showInput product">
				        <select id = "productSelect"  >
				        
				        </select>
				     </div>
				     
				     <span class="showSpan" id = "productNameS">
				     </span>
				    
				</li>
				
				<li style="height: 100px;">
				    <div style="width: 30%; float: left;"> 
				       <b>商品底价</b>
				    </div>
				   
				    <div style="width: 70%; float: left;">
				          <input type="number" min="1" placeholder="请输入商品底价" class="showInput" id = "lowPrice" />
				          <span class="showSpan" id = "lowPriceS">
				          </span>
				    </div>
				</li>
				
				<li style="height: 30px;" >
				     <div style="width: 30%; float: left;">
				        <b>砍价次数</b>
				     </div>
				     
				     <div style="width: 70%; float: left;" class="showInput">
						<input type="number"  min = "1" id = "totalTimes" style="width:60px; height: 20px; line-height: 20px;" />
				     </div>
				     <div style="width: 70%; float: left;" class="showSpan">
				     	<span id = "totalTimesS"></span>
				     </div>
				</li>
				
				<li style="height: 150px; line-height: 30px;" class="showSpan">
				    <div style="width: 30%; float: left;">
				       <b>注意事项</b>
				    </div>
				    
				    <div style="width: 70%; float: left;">
				       <span class="showSpan" id = "remarkS"></span>
				    </div>
				</li>
				
				
				<!-- 确定按钮 -->
				<li style="" class="showInput sure">
				     <div style="height:100%; width: 100%; padding:10px 0; float: left; text-align: center;">
					     <img src="images/order.jpg" id="submit1" onclick="saveActive()" style="margin-left: -100px;" />
					 </div>
				</li>
				
				
				<input type="hidden" id="jsons" name="jsons" />
				
				
				<li class="showInput richRemark" >
				     <div class="remarkTitle">
				          <b>注意事项</b>
				     </div>
				    
				    <div class="showInput remarkDou" style="text-indent:1em">
				       	  <textarea id="editor1" name="remark" cols="60" rows="4" class="ckeditor" maxlength="200" />
				    </div>
				</li>
				
			</ul>
		
		</div>
</form>
		</div>
	</div>
	<s:include value="/share/footer.jsp" />
	

</body>
</html>