<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!doctype html>
<head>
<s:include value="/share/meta.jsp" />
<meta name="keywords" content="瘦身减肥课程,肌肉健美课程,增强体质" />
<meta name="description"
	content="健身教练的商务中心是健身教练制定的健身套餐,健身课程，如：瘦身减肥课程,肌肉健身课程,增强体质课程等。" />
<title>我发起的挑战</title>
<link href="css/club-order.css" rel="stylesheet" type="text/css" />
<link type="text/css" rel="stylesheet" href="css/pulicstyle.css" />
<link rel="stylesheet" type="text/css" href="css/activ_new.css" />
<link type="text/css" rel="stylesheet" href="css/validator.css" />
<script type="text/javascript" src="script/jquery.form.js"></script>
<script type="text/javascript" src="script/jquery.parser.js"></script>
<script type="text/javascript" src="script/uploadPreview.js"></script>
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
</style>
<!-- 图片预览的js -->
<script type="text/javascript">
$(document).ready(function(){
	new uploadPreview({
		UpBtn : "txtImage1",
		ImgShow : "preview1"
	});
	new uploadPreview({
		UpBtn : "txtImage2",
		ImgShow : "preview2"
	});
	new uploadPreview({
		UpBtn : "txtImage3",
		ImgShow : "preview3"
	});
	$("[name='proType']").each(function(index){	
		$(this).click(function(){	
			loadMask();
			var liNode = $(this);			
			$("#proType").val($(this).val());
			$.ajax({type:"post",url:"product!editProduct.asp",data:$('#editForm').serialize(),
				success:function(msg){
					removeMask();
					$("#right-2").html(msg);
				}
			});
		});			
	});
	selectCheckbox("product.freeProjects","<s:property value="product.freeProjects"/>");
	selectCheckbox("product.costProjects","<s:property value="product.costProjects"/>");
	selectCheckbox("product.talkFunc","<s:property value="product.talkFunc"/>");
	$.formValidator.initConfig({theme:'Default',formID:"editForm",alertMessage:true,onError:function(msg){alert(msg)}});
	$("input[name='product.name']").formValidator().inputValidator({min:1,max:50,onError:"健身套餐名称不能为空且长度必须为50字以内,请确认"});
	$("input[name='product.wellNum']").formValidator().inputValidator({min:1,max:20,onError:"有效期限不能为空,请确认"}).regexValidator({regExp:"^[1-9][0-9]*$",onError:"有效期限必须为正整数,请确认"});
});
function deleteImage() {
	$('#preview1').attr('src', 'images/img-defaul.jpg');
	$('#txtImage1').val('');
}
</script>

<script type="text/javascript">
var tmp=$('<div class="resizer">'+
        '<div class="inner">'+
        '<img id="tmpimg">'+
        '<div class="frames"></div>'+
        '</div>'+
        //'<button>&#10007;</button>'+
        '<button class="ok">完成</button>'+
        '</div>');
$.imageResizer=function(){
  if(Uint8Array&&HTMLCanvasElement&&atob&&Blob){
      
  }else{
      return false;
  }
  var resizer=tmp.clone();
  
  resizer.image=resizer.find('img')[0];
  resizer.frames=resizer.find('.frames');
  resizer.okButton=resizer.find('button.ok');
  resizer.frames.offset={
      top:0,
      left:0
  };
  
  resizer.okButton.click(function(){
      resizer.clipImage();
  });
  resizer.clipImage=function(){
      var nh=this.image.naturalHeight,
          nw=this.image.naturalWidth,
          size=nw>nh?nh:nw;
      
      size=size>1000?1000:size;
      
      var canvas=$('<canvas width="'+size+'" height="'+size+'"></canvas>')[0],
          ctx=canvas.getContext('2d'),
          scale=nw/this.offset.width,
          x=this.frames.offset.left*scale,
          y=this.frames.offset.top*scale,
          w=this.frames.offset.size*scale,
          h=this.frames.offset.size*scale;
      
      ctx.drawImage(this.image,x,y,w,h,0,0,size,size*0.75);
      var src=canvas.toDataURL();
      this.canvas=canvas;
      this.append(canvas);
      this.addClass('uploading');
      this.removeClass('have-img');
      
      src=src.split(',')[1];
      if(!src)return this.doneCallback(null);
      src=window.atob(src);
      
      var ia = new Uint8Array(src.length);
      for (var i = 0; i < src.length; i++) {
          ia[i] = src.charCodeAt(i);
      };
      
      this.doneCallback(new Blob([ia], {type:"image/png"}));
  };
  
  resizer.resize=function(file,done){
      this.reset();
      this.doneCallback=done;
      this.setFrameSize(0);
      this.frames.css({
          top:0,
          left:0
      });
      var reader=new FileReader();
      reader.onload=function(){
          resizer.image.src=reader.result;
          reader=null;
          resizer.addClass('have-img');
          resizer.setFrames();
      };
      reader.readAsDataURL(file);
  };
  
  resizer.reset=function(){
      this.image.src='';
      this.removeClass('have-img');
      this.removeClass('uploading');
      this.find('canvas').detach();
  };
  
  resizer.setFrameSize=function(size){
      this.frames.offset.size=size;
      return this.frames.css({
          width:size+'px',
          height:size+'px'
      });
  };
  
  resizer.getDefaultSize=function(){
      var width=this.find(".inner").width(),
          height=this.find(".inner").height();
      this.offset={
          width:width,
          height:height
      };
      console.log(this.offset)
      return width>height?height:width;
  };
  
  resizer.moveFrames=function(offset){
      var x=offset.x,
          y=offset.y,
          top=this.frames.offset.top,
          left=this.frames.offset.left,
          size=this.frames.offset.size,
          width=this.offset.width,
          height=this.offset.height;
      
      if(x+size+left>width){
          x=width-size;
      }else{
          x=x+left;
      };
      
      if(y+size+top>height){
          y=height-size;
      }else{
          y=y+top;
      };
      x=x<0?0:x;
      y=y<0?0:y;
      this.frames.css({
          top:y+'px',
          left:x+'px'
      });
      
      this.frames.offset.top=y;
      this.frames.offset.left=x;
  };
  (function(){
      var time;
      function setFrames(){
          var size=resizer.getDefaultSize();
          resizer.setFrameSize(size);
      };
      
      window.onresize=function(){
          clearTimeout(time)
          time=setTimeout(function(){
              setFrames();
          },1000);
      };
      
      resizer.setFrames=setFrames;
  })();
  
  (function(){
      var lastPoint=null;
      function getOffset(event){
          event=event.originalEvent;
          var x,y;
          if(event.touches){
              var touch=event.touches[0];
              x=touch.clientX;
              y=touch.clientY;
          }else{
              x=event.clientX;
              y=event.clientY;
          }
          
          if(!lastPoint){
              lastPoint={
                  x:x,
                  y:y
              };
          };
          
          var offset={
              x:x-lastPoint.x,
              y:y-lastPoint.y
          }
          lastPoint={
              x:x,
              y:y
          };
          return offset;
      };
      resizer.frames.on('touchstart mousedown',function(event){
          getOffset(event);
      });
      resizer.frames.on('touchmove mousemove',function(event){
          if(!lastPoint)return;
          var offset=getOffset(event);
          resizer.moveFrames(offset);
      });
      resizer.frames.on('touchend mouseup',function(event){
          lastPoint=null;
      });
  })();
  return resizer;
};
var resizer=$.imageResizer(),
  resizedImage;

if(!resizer){
  resizer=$("<p>Your browser doesn't support these feature:</p><ul><li>canvas</li><li>Blob</li><li>Uint8Array</li><li>FormData</li><li>atob</li></ul>")   
};

$('.container1234').append(resizer);

$('#txtImage').change(function(event){
	$('#upimg').remove();
	
	//$('.fileimg').attr('name','image');
  var file=this.files[0];
  resizer.resize(file,function(file){
      resizedImage=file;
  });
});

 $('button.submit').click(function(){
  var url=$('input.url').val();
  if(!url||!resizedFile){alert(11);return;}
  var fd=new FormData();
  fd.append('file',resizedFile);
  $.ajax({
      type:'POST',
      url:url,
      data:fd
  });
}); 
 
	var valObj = null, val = '<s:property value="active.value"/>', target = '<s:property value="active.target"/>', category = '<s:property value="active.category"/>';
	$(function() {
		$('.addjudge').css('cursor', 'pointer');
		$('.deljudge').css('cursor', 'pointer');
		/* new uploadPreview({
			UpBtn : "file",
			ImgShow : "preview"
		}); */
		$('input[name="active.judgeMode"]').change(function(){
			var val = $('input[name="active.judgeMode"]:checked').val();
			if (val === 'C') {
				$('#judgedivs').show();
			} else {
				$('#judgedivs').hide();
			}
		});
		$('.addjudge').on('click', function(item){
			$('#judgelist').append('<div><input type="hidden" name="judges[0].judge.id" /><input type="text" name="judges[0].judge.name" class="member_name"/><span class="deljudge">删除</span></div>');
			onResort();
			$('.member_name').off('blur');
			$('.member_name').on('blur', function(){
				var _the = $(this);
				$.ajax({
					url: 'active!findMemberInfo.asp',
					data: {code: _the.val()},
					type: 'post',
					success: function(resp){
						var _json = $.parseJSON(resp);
						if (_json.success === true) {
							_the.val(_json.name);
							_the.prev('input').val(_json.id);
						} else {
							alert('当前输入的会员或教练不存在，请重新输入！');
							_the.select();
							_the.focus();
						}
					}
				});
			});
		});
		$('.deljudge').on('click', function(){
			onDeleteJudge(this);
		});
		if (target != '') $('#value' + target + category).val(val);
		if ('<s:property value="active.mode"/>' == 'A') {
			$('#spanTeam').css('display', 'none');
			$('#spanEmp').css('display', 'none');
			$('#teamNum').css('display', 'none');
		}
		$('.trTarget').css('display', 'none');
		$('input[name="active.mode"]').click(function(item) {
			var val = $('input[name="active.mode"]:checked').val();
			if (val === 'A') {
				$('#spanTeam').css('display', 'none');
				$('#spanEmp').css('display', 'none');
				$('#teamNum').css('display', 'none');
			} else {
				$('#spanTeam').css('display', '');
				$('#spanEmp').css('display', '');
				$('#teamNum').css('display', '');
			}
		});
		$('input[name="active.target"]').click(function(item) {
			$('.trTarget').css('display', 'none');
			var _val = $('input[name="active.target"]:checked').val();
			$("#trTarget" + _val).css('display', '');
			$('#trTarget' + _val).children('input:eq(0)').attr('checked', true);
		});
		if (target === '') {
			$('#trTargetA').css('display', '');
		} else {
			$('#trTarget' + target).css('display', '');
		}
		$('#submit1').css('cursor', 'hand');
		$('input[name="active.value1"]').focus(function() {
			if (valObj) valObj.attr('name', 'active.value1');
			valObj = $(this);
			valObj.attr('name', 'active.value');
		});
	});
	function onResort(){
		$('.addjudge').css('cursor', 'pointer');
		$('.deljudge').css('cursor', 'pointer');
		var i = 0;
		$('#judgelist').children('div').each(function(){
			$(this).children('span').off('click');
			$(this).children('span').on('click', function(){
				onDeleteJudge(this);
			});
			$(this).children('input').each(function(){
				var _name = $(this).attr('name'), _index = _name.indexOf('.');
				 $(this).attr('name', 'judges[' + i + ']' + _name.substring(_index));
			});
			i++;
		});
	}
	function onDeleteJudge(item){
		var inputs = $(item).parent('div').children('input');
		if (inputs.length == 3) {
			if (confirm('是否删除当前候选裁判？')) {
				var _id = $($(item).parent('div').children('input:eq(0)')).val();
				$(document).mask('请稍候，正在删除数据......');
				$.ajax({
					url : 'active!deleteJudge.asp',
					type : 'post',
					data: {id: _id},
					success : function(resp) {
						$(document).unmask();
						$(item).parent('div').remove();
					}
				});
			}
		} else {
			$(item).parent('div').remove();
		}
	}
	function validate() {
		if($("#imgflag").val()=='0'){
			return false;
		}
		$('#imgflag').val('0');
		var a =$("#activeName").val();
		if (a==null||a=='') {
			alert("请输入活动名称！");
			return false;
		}
		var b=$("#textfield2").val();
		if (b==null||b=='') {
			alert("请输入完成天数！");
			return false;
		}
		if ($('input[name="active.target"]:checked').length <= 0) {
			alert("请填写活动目标！");
			return false;
		} else {
			var _val = $('input[name="active.target"]:checked').val();
				var notNull = true;
				$("#trTarget" + _val).find('input:text').each(function() {
					var radioId = $(this).attr("id").substring(5, 7);
						if ($(this).val() == '' || $(this).val() == null) {
							notNull = false;
							return false;
						}
				});
				if (notNull == false) {
					alert("请填写活动目标！");
					return false;
				}
		}
		return true;
	}
	function onSave() {
		if($('#upimg').src=="picture/<s:property value='active.image'/>"){
			alert(1111)
			$('#txtImage').remove();
		}
	$('#imgflag').val('1');
	if(validate()){
		$(document).mask('请稍候，正在保存数据......');
		$('#activeform').form('submit', {
			onSubmit : function(param) {
				return true;
			},
			success : function(msg) {
				$(document).unmask();
				/* $.ajax({
					url : 'active.asp',
					type : 'post',
					success : function(resp) {
						$('#right-2').html(resp);
					}
				}); */
				alert('当前活动已经成功保存！');
			}
		});
	}
	}

	 //实时显示上传的图片
	function setImagePreview() {
		var docObj = document.getElementById("file");
		var imgObjPreview = document.getElementById("preview");
		if (docObj.files && docObj.files[0]) {
			//火狐下，直接设img属性 
			imgObjPreview.style.display = 'block';
			imgObjPreview.style.width = '120px';
			imgObjPreview.style.height = '100px';
			//imgObjPreview.src = docObj.files[0].getAsDataURL(); 
			//火狐7以上版本不能用上面的getAsDataURL()方式获取，需要一下方式 
			imgObjPreview.src = window.URL.createObjectURL(docObj.files[0]);
		} else {
			//IE下，使用滤镜 
			docObj.select();
			var imgSrc = document.selection.createRange().text;
			var localImagId = document.getElementById("localImag");
			//必须设置初始大小 
			localImagId.style.width = "50px";
			localImagId.style.height = "50px";
			//图片异常的捕捉，防止用户修改后缀来伪造图片 
			try {
// 				localImagId.style.filter = "progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale)";
				localImagId.filters
						.item("DXImageTransform.Microsoft.AlphaImageLoader").src = imgSrc;
			} catch (e) {
				alert("您上传的图片格式不正确，请重新选择!");
				return false;
			}
			imgObjPreview.style.display = 'none';
			document.selection.empty();
		}
		return true;
	} 
	function changeselectValue(){
		var a=$('#valueE4 option:selected') .val();
		var b=$('#valueE5 option:selected') .val();
		var c=a+b;
		$('#selectValue').val(c);
	}
	
</script>
</head>
<body>
	<s:include value="/share/header.jsp" />
	<div id="content">
	<s:include value="/order/nav.jsp" />
		<div id="right-2">
			<s:form name="activeform" id="activeform" theme="simple" method="post" onsubmit="return validate()"
	action="active!save.asp" enctype="multipart/form-data">
	<s:hidden name="active.id" id="keyId" />
	<s:hidden name="active.creator.id" id="creatorId" />
	<s:hidden name="active.status" id="status" />
	<%-- <s:hidden name="active.image" id="image123" />  --%>
	<s:hidden id="imgflag" value="0"/>
	<input type="hidden" name="active.createTime" value="<s:date name="active.createTime" format="yyyy-MM-dd HH:mm:ss"/>" />
	<div id="container" style="color: #000;">
		<h1>发起挑战</h1>
		<div style="overflow: hidden; padding-left: 20px; padding-top: 10px"
			class="fap">
			<div style="float: left; width: 100px; line-height: 38px;">
				<p style="line-height: 38px; height: 38px;">
					<b>活动名称：</b>
				</p>
				<p style="line-height: 50px; height: 55px;">
					<b>缩略图：</b>
				</p>
				<!-- <p style="line-height: 38px; height: 38px;">
					<b>活动模式：</b>
				</p> -->
			<!-- 	 <p style="line-height: 38px; height: 124px;">&nbsp;
				<p> -->
				<p style="line-height: 38px; height: 38px;margin-top:55px">
					<b>完成时间：</b>
				</p>
				<p style="line-height: 38px; height: 38px;">
					<b>活动目标：</b>
				</p>
				<p style="line-height: 38px; height: 38px;">&nbsp;
				<p>
				<p style="line-height: 38px; height: 38px;">&nbsp;
				<p>
				<p style="line-height: 38px; height: 38px;">
					<b>成功奖励：</b>
				</p>
				<p style="line-height: 38px; height: 38px;">
					<b>失败惩罚：</b>
				</p>
			<!-- 	 <p style="line-height: 38px; height: 38px;">
					<b>裁判方式：</b>
				</p> -->
				<!-- <p style="line-height: 38px; height: 38px;">&nbsp; -->
				<p style="line-height: 38px; height: 38px;">
					<b>注意事项：</b>
				</p> 
			</div>
			<div style="float: left; line-height: 38px;">
				<p style="line-height: 33px; height: 33px; padding-top: 5px;">
					<label> <s:textfield name="active.name" cssClass="input_w"
							id="activeName" cssStyle="width: 300px;line-height:22px;" />
					</label>
				</p>
				<div class="container1234" style="width:150px;height:113px">
						<!-- 缩略图 -->
						<img id="preview1" width="100px" height="75px"
								<s:if test="active==null || active.image == null || active.image == ''">src="images/img-defaul.jpg"</s:if>
								<s:else> src="picture/<s:property value='active.image' />"</s:else>
								onclick="javascript: $('#txtImage1').click();" /> 
						<img id="delImg1" width="15px" height="15px" src="images/del_img.png"
						onClick="deleteImage()"> 
								
						<%-- <s:if test="active==null || active.image == null || active.image == ''">  --%>
							<!-- <img id="upimg"  width="100px" height="75px" src="images/img-defaul.jpg"onclick="javascript: $('#txtImage').click();" /> -->
							 
						<%-- </s:if> --%>
						<%-- <s:else> --%>
							<%-- <img id="upimg"  width="100px" height="75px" src="picture/<s:property value='active.image'/>" onclick="javascript: $('#txtImage').click();" /> --%> 
							<%-- <s:hidden name="active.image" id="hidimg" /> --%>
					    <%-- </s:else> --%>
						
						<s:file id="txtImage1" name="image" accept="images/*" style="display:none"/>
				</div>
			<%-- 	<p style="line-height: 50px; height: 50px; padding-top: 5px;">
					
					<img id="preview" width="60px" height="60px"
						<s:if test="active==null || active.image == null || active.image == ''">src="images/img-defaul.jpg"</s:if>
						<s:else> src="picture/<s:property value='active.image'/>"</s:else>
						onclick="javascript: $('#file').click();" />
					<s:file name="image" id="file" style="display: none;" />
				<p style="padding-top: 3px; line-height: 38px; height: 38px;">
					<s:radio name="active.mode" id="activeMode" list="#{'A':'个人挑战','B':'团体挑战'}" />
				</p>
				<p>
					<span id="spanTeam" style="clear: both; display: none;">每个团休人数：<s:textfield
							cssStyle="line-height:22px;" name="active.teamNum"
							cssClass="input_w" id="teamNum" />人
					</span> &nbsp;
				</p>  --%>
				<p style="clear: both; line-height: 38px; height: 38px;">
					<span class="kuangdd" style="padding-top: 5px;"><s:textfield
							name="active.days" cssStyle="line-height:22px;"
							cssClass="input_w" id="textfield2" style="line-height:22px;" /></span><span
						class="zhixia" style="padding-top: 0px;">天</span><span
						style="clear: both;"></span>
				</p>
				<p style="clear: both; line-height: 38px; height: 38px;">
					<s:radio name="active.target" id="target"
						list="#{'A':'体重减少','B':'体重增加','C':'运动次数'}" />
				</p>
				<p>
				<p id="trTargetA" class="trTarget" style="height:76px">
					<%-- <s:if test="active.target=='A'"><s:hidden name="active.category" value="A"></s:hidden></s:if> --%> 
					<input type="radio" name="active.category" id="A1" value="A"
						<s:if test="active.category=='A'">checked="checked"</s:if>  hidden="hidden"/>
					<s:textfield name="active.value" cssClass="input_w" id="valueA1"
						style="height:20px;line-height:22px;" />
					&nbsp;公斤 
					
				</p>
				<p id="trTargetB" class="trTarget" style="height:76px">
					<%-- <s:if test="active.target=='B'"><s:hidden name="active.category" value="B"></s:hidden></s:if> --%>
					<input type="radio" name="active.category" id="B1" value="B"
						<s:if test="active.category=='B'">checked="checked"</s:if>  hidden="hidden"/>
					<s:textfield name="active.value" cssClass="input_w" id="valueB1"
						style="height:20px;line-height:22px;" />
					&nbsp;公斤
					
				</p>

				<p id="trTargetC" class="trTarget" style="height:76px">
					<%-- <s:if test="active.target=='C'"><s:hidden name="active.category" value="C"></s:hidden></s:if> --%> 
					<input type="radio" name="active.category" id="C1" value="C"
						<s:if test="active.category=='C'">checked="checked"</s:if>  hidden="hidden"/>
					<s:textfield name="active.value" cssClass="input_w" id="valueC1"
						style="height:20px;line-height:22px;" />
					次数
					
				</p>
				<p id="trTargetD" class="trTarget" style="height:76px">
					<%-- <s:if test="active.target=='D'"><s:hidden name="active.category" value="D"></s:hidden></s:if> --%>
					<input type="radio" name="active.category" id="D1" value="D"
						<s:if test="active.category=='D'">checked="checked"</s:if>  hidden="hidden"/>
					<s:textfield name="active.value" cssClass="input_w" id="valueD1"
						style="height:20px;line-height:22px;" />
					次数 
					
				</p>
				<p id="trTargetE" class="trTarget" style="height:76px">
					<%-- <s:if test="active.target=='E'"><s:hidden name="active.category" value="E"></s:hidden></s:if> --%>
					<input type="radio" name="active.category" id="E1" value="E"
						<s:if test="active.category=='E'">checked="checked"</s:if>  hidden="hidden"/>
					挑战内容：<s:textfield cssClass="input_w" name="active.content" id="valueE1" style="height:20px;line-height:22px;" placeholder="运动名称"/>
					挑战目标：<s:textfield cssClass="input_w" name="active.customTareget" id="valueE2" style="height:20px;line-height:22px;" placeholder="目标数量"/>
					目标计量单位：<s:textfield cssClass="input_w" name="active.unit" id="valueE3" style="height:20px;line-height:22px;" placeholder="计量单位"/>
					<br/> 挑战成绩评定方式：<s:select id="valueE4"  list="#{'0':'单次最好成绩','1':'累计成绩','2':'最后一次成绩'}" label="成绩评定" onchange="changeselectValue()"  style="width:50px"></s:select>
					<s:select id="valueE5" list="#{'0':'大于或等于','1':'小于或等于'}" label="关系" onchange="changeselectValue()" style="width:50px"></s:select>目标
					<input type="text" id="selectValue" name="active.evaluationMethod" value="00" hidden="hidden"/>
				</p>
				<p
					style="clear: both; line-height: 33px; height: 33px; padding-top: 5px;">
					<s:textfield name="active.award" cssClass="input_log"
						id="textfield12" style="height:20px;line-height:22px;" />
				</p>
				<p style="clear: both; line-height: 35px; height: 35px;">
					向
					<s:select list="#request.csjgs" name="active.institution.id"
						listKey="id" listValue="name" />
					捐款
					<s:textfield name="active.amerceMoney" cssClass="input_w"
						id="amerceMoney" style="height:20px;line-height:22px;" />
					元
				</p>
	<%-- 			<p style="height: 38px; padding-top: 7px;">
					<s:radio name="active.judgeMode" id="judgeMode"
						list="#{'A':'参加者选择裁判','B':'本活动的发起人为裁判'}"
						cssStyle="height:20px;line-height:22px;" />
				</p> --%>
				<%-- <p style="line-height: 35px; height: 35px;">
					<span class="tishi" style="padding-right: 20px; color: #945F50;">
						裁判将收到挑战者的健身报告，由裁判确认报告中健身数据的真实性。</span>
					<div id="judgedivs">
						<span>裁判列表：</span><span class="addjudge">增加裁判</span>
						<div id="judgelist">
						<s:iterator value="judges" status="st">
							<div>
								<s:hidden name="%{'judges['+#st.index+'].id'}" />
								<s:hidden name="%{'judges['+#st.index+'].judge.id'}" />
								<s:textfield name="%{'judges['+#st.index+'].judge.name'}" class="member_name"/>
								<span class="deljudge">删除</span>
							</div>
						</s:iterator>
						</div>
					</div>
				</p> --%>
				<p>
					<s:textfield name="active.memo" cssClass="input_log"
						id="textfield13" style="height:20px;line-height:22px;" />
					<p><b>若挑战成功，保证金全额退回！</b> </p>
				</p>
				 
			</div>
			<div style="clear: both;"></div>
			<DIV style="clear: both; padding:10px 0;height:30px">
				<p class="p_auto"style="">
					<span class="btn_flf"
						style=" display: block; padding-top: 7px;"><img
						src="images/order.jpg" id="submit1" onclick="onSave()" />
					
					</span> 
					
				</p>
		
			</DIV>
		</div>
		</div>
</s:form>
		</div>
	</div>
	<s:include value="/share/footer.jsp" />
</body>
</html>





