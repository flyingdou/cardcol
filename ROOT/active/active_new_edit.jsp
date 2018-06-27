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
<link type="text/css" rel="stylesheet" href="css/validator.css" />
<script type="text/javascript" src="script/jquery.form.js"></script>
<script type="text/javascript" src="script/jquery.parser.js"></script>

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
/*text  */
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
  
</style>

</head>
<body>
	<s:include value="/share/header.jsp" />
	<div id="content">
	<s:include value="/order/nav.jsp" />
	<div id="right-2">
	<s:form name="activeform" id="activeform" theme="simple" method="post" action="active!save.asp" enctype="multipart/form-data">
	<s:hidden name="active.id" id="keyId" />
	<s:hidden name="active.creator.id" id="creatorId" />
	<s:hidden name="active.status" id="status" />
	<s:hidden id="imgflag" value="0"/>
	<input type="hidden" name="active.createTime" value="<s:date name="active.createTime" format="yyyy-MM-dd HH:mm:ss"/>" />
	<div id="container" style="color: #000;">
		<h1>{{editActiveTitle}}</h1>
		<div style="overflow: hidden; padding-left: 20px; padding-top: 10px" class="fap">
			<ul style="margin:0;padding:0;list-style: none;">
				<li style="height:78px;line-height: 78px;">
					<div style="width:30%;float: left;">
						<b>挑战海报</b>
					</div>
					<div style="width:70%;float: left;">
						<img id="preview1" width="100px" height="75px" 
									<s:if test="active==null || active.image == null || active.image == ''">src="images/img-defaul.jpg"</s:if>
									<s:else> src="picture/<s:property value='active.image' />"</s:else>
									onclick="openUploadWindow()" />
							 <s:file id="txtImage1" name="image" style="display: none;" />
					</div>
					
					
				</li>
				
				<li style="height: 30px;line-height: 30px;" >
				     <div style="width: 30%;float: left;">
						<b>挑战名称</b>
				     </div>
				     
				     <div style="width: 70%; float:left; ">
					     <label v-if="showInput"> 
					     	<s:textfield name="active.name" cssClass="input_w" id="activeName" v-model="model.activeName" cssStyle="width: 300px;line-height:22px;" />
						 </label>
						 <label v-if="showSpan">
						 	<span>${active.name}</span>
						 </label>
				     </div>
				</li>
				
				<li style="height: 30px; line-height: 30px; " >
				    <div style="width: 30%; float: left;">
				         <b>请输入挑战天数</b>
				    </div>
				    
				    <div style="width: 70%; float: left;" >
				        <input type="number"  v-model="model.activeDays"  onchange="validateNumber(this)"  class = "activeDays"  style="border: 1px solid #A5A3A5;"  v-if="showInput"/>
				        <span>${active.days}</span>	
				          天
				    </div>
				</li>
				
				<li style="height: 30px; line-height: 30px;">
				    <div style="width: 30%; float: left;">
				         <b>挑战目标</b>
				    </div>
				    
				    <div style="width: 70%;float: left;" v-if="showInput">
				       <select id = "targetType" v-model="model.targetType" onchange="changeUnit()">
				            <option value = "A"  >体重减少</option>
				            <option value = "B"  >体重增加</option>
				            <option value = "C"  >运动次数</option>
				        </select>
				        <input type="number" min = "1"  v-model = "model.targetValue" onchange="validateNumber(this)" id = "targetValue"  class = "value" style="border: 1px solid #A5A3A5; width: 40px;" />
				        <span id = "targetUnit">KG</span>
				     </div>
				     
				     <span v-if="showSpan">
				     	{{target}}
				     </span>
				    
				</li>
				
				<li style="height: 100px;">
				    <div style="width: 30%; float: left;"> 
				       <b>成功奖励</b>
				    </div>
				   
				    <div style="width: 70%; float: left;">
				          <textarea rows="5" cols="30" placeholder="请输入挑战成功后的奖励内容" v-model = "model.parise"  v-if="showInput">
				          
				          </textarea>
				          <span v-if="showSpan">
				          	${active.award}
				          </span>
				    </div>
				</li>
				
				<li style="height: 30px;" >
				     <div style="width: 30%; float: left;">
				        <b>失败惩罚</b>
				     </div>
				     
				     <div style="width: 70%; float: left;" v-if="showInput">
					           向
						<s:select list="#request.csjgs" name="active.institution.id" listKey="id" listValue="name" v-model = "model.institution" />
						捐款
						<input type="number"  min = "0.01" v-model = "model.amerceMoney" id = "amerceMoney" style="width:60px; height: 20px; line-height: 20px;" />
						元
				     </div>
				     <div style="width: 70%; float: left;" v-if="showSpan">
				     	<span>向${active.institution.name}捐款${active.amerceMoney}元</span>
				     </div>
				</li>
				
				<li style="height: 150px; line-height: 30px;">
				    <div style="width: 30%; float: left;">
				       <b>注意事项</b>
				    </div>
				    
				    <div style="width: 70%; float: left;">
				        <textarea rows="8" cols="30" placeholder="请输入挑战注意事项" v-model = "model.memo"  v-if="showInput">
				        </textarea>
				        <span v-if="showSpan">${active.memo}</span>
				    </div>
				</li>
				
				
				<li style="height: 100px; line-height: 100px;" v-if="showInput">
				     <div style="height:100%; width: 100%; padding:10px 0; float: left; text-align: center;">
					     <img src="images/order.jpg" id="submit1" onclick="saveActive()" style="margin-left: -100px;" />
					 </div>
				</li>
				
			</ul>
		
			<input type="hidden" id="jsons" name="jsons" />
		</div>
</s:form>
		</div>
	</div>
	<s:include value="/share/footer.jsp" />
	
	
	<script src="ecartoon-weixin/js/vue.min.js"></script>
	<script src="ecartoon-weixin/js/ajaxfileupload.js" ></script>


	<script type="text/javascript" src="script/uploadPreview.js"></script>
	<script type="text/javascript">
	    $(function(){
	    	/* targetData.model.poster = JSON.parse(sessionStorage.poster);
	    	$("#preview1").attr({"src":"picture/" + JSON.parse(sessionStorage.poster)}); */
	    	
	    	new uploadPreview({
	    		UpBtn : "txtImage1",
	    		ImgShow : "preview1"
	    	});
	    })
	
	
	    var targetData = new Vue({
	    	el: "#content",
	    	data: {
	    		editActiveTitle: "发起活动",
	    		showInput: true,
	    		showSpan: false,
	    		model: {},
	    		target: "",
	    		jsons: ""
	    	},
	    	created: function(){
	    		if("${active.id}" != ""){
	    			this.editActiveTitle = "修改活动";
	    			this.model.activeName = "${active.name}";
		    		this.model.activeDays = "${active.days}";
		    		this.model.targetType = "${active.target}";
		    		this.model.targetValue = "${active.value}";
		    		this.model.parise = "${active.award}";
		    		this.model.institution = "${active.institution.id}";
		    		this.model.amerceMoney = "${active.amerceMoney}";
		    		this.model.memo = "${active.memo}";
	    		} 
	    		
	    		if("${look}" == "1") {
	    			this.showInput = false;
	    			this.showSpan = true;
	    			this.editActiveTitle = "活动详情";
	    			var days = "${active.days}";
	    			var target = "${active.target}";
	    			var value = "${active.value}" == "" ? 0 : parseInt("${active.value}");
	    			var danwei = target == "A" || target == "B" ? "公斤" : "次";
	    			var obj = {"A": "体重减少", "B": "体重增加", "C": "运动", "D": "运动"};
	    			this.target = days + "天" + obj[target] + value + danwei;
	    		}
	    		
	    	}
	    });
	    
	    // 打开上传窗口
	    function openUploadWindow () {
	    	if("${look}" == "1"){
	    		return;
	    	}
	    	$('#txtImage1').click();
	    }
	
	    //挑战目标切换时，后面的单位实时切换
	    function changeUnit(){
	    	setTimeout(function(){
	    		var selectedType = targetData.model.targetType;
		    	if (selectedType == "A") {
		    		$("#targetUnit").html("KG");
		    	} else if (selectedType == "B"){
		    		$("#targetUnit").html("KG");
		    	} else {
	                $("#targetUnit").html("次");	    	
		    	}
	    	}, 200);
	    }
	    
	   /*  
	    //上传用户图片
	    function savePoster(val) {
	    	// 读取文件
			var file = {
					path:$("#poster")[0].files[0],
					size:$("#poster")[0].files[0].size,
					suffix:$("#poster")[0].value.split(".")[1].toUpperCase()
			}
	    	//上传图片
	    	if (file.size > 0){
	    		if (file.size >= (10 * 1024 * 1024)){
	    			alert("请选择小于10M的图片");
	    		} else if (file.suffix != "JPEG" && file.suffix != "PNG" && file.suffix != "JPG" && file.suffix != "GIF") {
	    			alert("图片类型必须是.gif,jpeg,jpg,png中的一种");
	    		} else {
	    		  var reader = new FileReader();
	  			  reader.readAsDataURL(file.path);
	  			  reader.onloadend = function () {
	  				 $("#preview1").attr("src", this.result);
	  			      saveImage(this.result);
	  			  }
	    		}
	    	}
	    	
	    } */
	    
	   /* 
	    //保存图片
	    function saveImage(dataurl) {
	       var arr = dataurl.split(','),mine = arr[0].match(/:(.*?);/)[1],
	       bstr = atob(arr[1]),n = bstr.length,u8arr = new Uint8Array(n);
	       while(n--){
	    	   u8arr[n] = bstr.charCodeAt(n);
	       }
	       var obj = new Blob([u8arr],{type:mine});
	       var fd = new FormData();
	       fd.append("image",obj,"image.png");
	       $.ajax({
	    	   url:"active!uploadImage.asp",
	    	   type:"POST",
	    	   processData:false,
	    	   contentType:false,
	    	   data:fd,
	    	   success: function (data){
	    		   targetData.model.poster = JSON.parse(data).imageName;
	    		   alert("图片上传成功");
	    	   }
	       });
	    
	       
	    } */
	   
	   // 当用户点击保存按钮时，收集、校验参数，提交后台保存
	   function saveActive(){
			   // 数据非空校验
			   var dou = targetData.model;
			   /* if (dou.poster == undefined || dou.poster == ""){
				   alert("请上传挑战海报！");
				   return;
			   } */
			   if (dou.activeName == undefined || dou.activeName == ""){
				   alert("请填写挑战名称！");
				   return;
			   }
			   if (dou.activeDays == undefined || dou.activeDays == ""){
				   alert("请填写挑战天数！");
				   return;
			   }
			   if (dou.targetType == undefined || dou.targetType == "" || dou.targetValue == undefined || dou.targetValue == ""){
				   alert("请选择挑战目标，并填写目标值！");
				   return;
			   }
			   if (dou.institution == undefined || dou.institution == "" || dou.amerceMoney == undefined || dou.amerceMoney == ""){
				   alert("请选择捐款机构，并填写金额！");
				   return;
			   }
			   //验证通过，提交数据到后台
			   /* $.ajax({
				   url:"active!activeSave.asp",
				   type:"post",
				   dataType:"json",
				   data: {
					   jsons: encodeURI(JSON.stringify(targetData.model))
				   },
				   success:function (res){
					   if (res.success){
						   location.href = "active.asp";
					   } else {
						   alert(JSON.stringify(res.message));
					   }
				   },
				   error:function (e){
					   alert(JSON.stringify(e));
				   }
				   
			   }); */
			   
			   
			   $("#jsons").val(encodeURI(JSON.stringify(targetData.model)));
			   $("#activeform").attr("action", "active!activeSave.asp").submit();
		   
	   }
	   
	   // 正整数校验
	   function validateNumber(obj){
	       var reg = new RegExp("^[1-9]*$");
		    if(!reg.test(obj.value)){
		        alert("请输入大于0的正整数!");
		    }
	   }
	    
	</script>
	
	
	
</body>
</html>





