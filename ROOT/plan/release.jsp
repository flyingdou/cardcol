<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<style type="text/css">

/*text  */
.container123{  
	height:330px;
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
<script type="text/javascript" src="script/jquery.form.js"></script>
<script type="text/javascript" src="script/jquery.parser.js"></script>
<script type="text/javascript" src="script/uploadPreview.js"></script>
<script type="text/javascript">
 	$(function() {
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
		CKEDITOR.replace('txtDetails', {
			extraPlugins : 'uicolor'
		});
	})

	function isPrice(str) {
		var RegExpPtn = /^\d+(\.\d{0,2})?$/;
		if (!RegExpPtn.test(str)) {
			return false;
		}
		return true;
	}

	function compareDate(d1, d2) {
		var date1 = new Date(d1.replace("-", "/"));
		var date2 = new Date(d2.replace("-", "/"));
		if (date1 > date2) {
			return false;
		} else {
			return true;
		}
	}

	function validate() {
		if($("#imgflag").val()=='0'){
			return false;
		}
		$('#imgflag').val('0');
		if (isEmpty($("#planName").val())) {
			alert("请输入计划名称！");
			return false;
		}
		if ($('input[name="release.planType"]:checked').length <= 0) {
			alert("请选择计划类型！");
			return false;
		}

		if ($('input[name="release.scene"]:checked').length <= 0) {
			alert("请选择适用场景！");
			return false;
		}
		if ($('input[name="release.applyObject"]:checked').length <= 0) {
			alert("请选择适用对象！");
			return false;
		}

		var startDate = $("#startDate").val();
		if (isEmpty(startDate)) {
			alert("请输入保存范围的开始日期！");
			return false;
		}
		var endDate = $("#endDate").val();
		if (isEmpty(endDate)) {
			alert("请输入保存范围的结束日期！");
			return false;
		}
		if (compareDate(startDate, endDate) == false) {
			alert("保存范围的结束日期应大于开始日期！");
			return false;
		}

		var unitPrice = $("#unitPrice").val();
		if (isEmpty(unitPrice)) {
			alert("请输入销售价格！");
			return false;
		} else {
			if (isPrice(unitPrice) == false) {
				alert("请输入正确的销售价格！");
				return false;
			}
		}
		var briefing = $("#briefing").val();
		if (isEmpty(briefing)) {
			alert("请输入计划简介！");
			return false;
		}
		return true;
	}
 	function onSave() {
		// 验证
		$('#imgflag').val('1');
		if (validate()) {
			$(document).mask('请稍候，正在保存数据......');
			$('#formrelease').form('submit', {
				url : 'release!executeSave.asp',
				onSubmit : function(param) {
					return true;
				},
				success : function(msg) {
					$(document).unmask();
					alert('您的计划已经成功发布！');
					var $json = $.parseJSON(msg);
					$('#keyId').val($json.key);
				}
			});
		}
	} 
	function deleteImage(o) {
		$('#preview' + o).attr('src', 'images/img-defaul.jpg');
		$('#txtImage' + o).val('');
	}
	
	//裁剪
	 $('#ssimg').change(function(){
		var file=this.files[0];
		
		var reader=new FileReader();
		reader.onload=function(){
			var url=reader.resule;
			setImageURL(url);
		};
		reader.readAsDataURL(file);
	});
	var image=new Image();
	function setImageURL(url){
		image.src=url;
	} 
	var tmp=$('<div class="resizer">'+  
	          '<div class="inner">'+  
	          '<img id="tmpimg">'+  
	          '<div class="frames"></div>'+  
	          '</div>'+  
	          //'<button>✗</button>'+  
	          '<button class="ok">完成</button>'+  
	          '</div>');  
	$.imageResizer=function(){  
	    if(Uint8Array&&HTMLCanvasElement&&atob&&Blob){  
	          
	    }else{  
	        return false;  
	    }  
	    var resizer=tmp.clone();  
	      
	    resizer.image=resizer.find('img')[0];  
	    //resizer.image=$('#preview1'); 
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
	        if(!src)return this.doneCallback(null);  //callback与return类似
	        src=window.atob(src);  //用来解码一个已经被base-64编码过的数据
	          
	        var ia = new Uint8Array(src.length);  
	        for (var i = 0; i < src.length; i++) {  
	            ia[i] = src.charCodeAt(i);  
	        };  
	          
	        this.doneCallback(new Blob([ia], {type:"image/png"}));  
	    };  
	 //   通过 FileReader 我们可以将图片文件转化成 DataURL，就是以 data:image/png;base64, 开头的一种URL,然后可以直接放在 image.src 里，这样本地图片就显示出来了 
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
	        this.find('canvas').detach();  //与remove类似
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
	      
	    resizer.moveFrames=function(offset){  //拖动的裁剪框大小
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
	      
	    (function(){  				//拖动裁剪框
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
	$('.container123').append(resizer);  
	  
	 $('#txtImage1').change(function(event){  
	    var file=this.files[0];  
	    resizer.resize(file,function(file){  
	        resizedImage=file;  
	    });  
	}); 
	  
	 /* $('button.submit').click(function(){  
	    var url=$('input.url').val();  
	    if(!url || !resizedFile)return;  
	    var fd=new FormData();  
	    fd.append('image2',resizedFile);  
	    $.ajax({  
	        type:'POST',  
	        url:url,  
	        data:fd  
	    });  
	});  */
</script>
<s:form id="formrelease" name="formrelease" method="post" onsubmit="return validate()"
	action="release!executeSave.asp" theme="simple" enctype="multipart/form-data">
	<s:hidden name="release.id" id="keyId" />
	<s:hidden name="release.image1" id='image1' />
	<s:hidden name="release.image2" id='image2' />
	<s:hidden name="release.image3" id='image3' />
	<div id="right-2">
		<h1>发布计划</h1>
		<table style="margin-left: 15px;">
			<tr height="38px">
				<td><b>计划名称：</b></td>
				<td><s:textfield name="release.planName" id="planName"
						class="input_w"
						style="height: 26px; width: 300px; line-height: 22px; border: 1px solid #ccc;" /></td>
			</tr>
			<tr height="38px">
				<td><b>上传缩略图：</b></td>
				<td>
					<div id="divimage">
						<div class="container123">
							<!-- <img id="preview1" width="60px" height="60px"
								<s:if test="release==null || release.image1 == null || release.image1 == ''">src="images/img-defaul.jpg"</s:if>
								<s:else> src="picture/<s:property value='release.image1'/>"</s:else>
								onclick="javascript: $('#txtImage1').click();" /> 
							<img id="delImg1" width="15px" height="15px" src="images/del_img.png" onClick="deleteImage('1')">  -->
							<!-- <img id="preview2" width="60px" height="60px"
								<s:if test="release==null || release.image2 == null || release.image2 == ''">src="images/img-defaul.jpg"</s:if>
								<s:else> src="picture/<s:property value='release.image2'/>"</s:else>
								onclick="javascript: $('#txtImage2').click();" />
							<img id="delImg1" width="15px" height="15px" src="images/del_img.png" onClick="deleteImage('2')">
							<img id="preview3" width="60px" height="60px"
								<s:if test="release==null || release.image3 == null || release.image3 == ''">src="images/img-defaul.jpg"</s:if>
								<s:else> src="picture/<s:property value='release.image3'/>"</s:else>
								onclick="javascript: $('#txtImage3').click();" />
							<img id="delImg1" width="15px" height="15px" src="images/del_img.png" onClick="deleteImage('3')"> -->
							<s:if test="release==null || release.image1 == null || release.image1 == ''"></s:if>
							<s:else> <img src="picture/<s:property value='release.image1'/>"/></s:else>
							<s:file id="txtImage1" name="image1" accept="images/*"/><s:hidden id="imgflag" value="0"/> 
							 <s:file id="txtImage2" name="image2" style="display: none;" />
							<s:file id="txtImage3" name="image3" style="display: none;" />
						<s:file id="txtImage1" name="image1" style="display: none;" />
							<s:file id="txtImage2" name="image2" style="display: none;" />
							<s:file id="txtImage3" name="image3" style="display: none;" />
						</div>
					</div>
				</td>
			</tr>
			<tr height="38px">
				<td><b>计划类型：</b></td>
				<td><s:radio name="release.planType" id="planType"
						list="#{'A':'瘦身减重','B':'健美增肌','C':'运动康复','D':'提高运动表现'}" /></td>
			</tr>
			<tr height="38px">
				<td><b>适用对象：</b></td>
				<td><s:radio name="release.applyObject" id="applyObject"
						list="#{'A':'初级','B':'中级','C':'高级'}" /></td>
			</tr>
			<tr height="38px">
				<td><b>选择会员：</b></td>
				<td><s:if test="#session.loginMember.role == \"S\"">
					<s:select
						list="#request.members" value="member" listKey="id"
						listValue="nick" cssClass="selectmember" />
				</s:if></td>
			</tr>
			<tr height="38px">
				<td><b>保存范围：</b></td>
				<td>从<span><s:textfield name="release.startDate"
							id="startDate" readonly="readonly" style="width:100px;" /> <img
						id="img_startDate" onclick="WdatePicker({el:'startDate'})"
						src="script/DatePicker/skin/datePicker.gif" align="absmiddle"
						width="16" height="22" style="cursor: pointer;"></span> 到<span><s:textfield
							name="release.endDate" id="endDate" readonly="readonly"
							style="width:100px;" /> <img id="img_endDate"
						onclick="WdatePicker({el:'endDate'})"
						src="script/DatePicker/skin/datePicker.gif" align="absmiddle"
						width="16" height="22" style="cursor: pointer;"></span>
				</td>
			</tr>
			<tr height="38px">
				<td><b>适用场景：</b></td>
				<td><s:checkboxlist id="scene" value="#request.scenes"
						list="#{'A':'办公室','B':'健身房','C':'家庭','D':'户外'}" listKey="key"
						listValue="value" name="release.scene" /></td>
			</tr>
			
			<tr height="38px">
				<td><b>所需器材：</b></td>
				<td><s:textfield name="release.apparatuses" id="releaseName"
						class="input_w"
						style="height: 26px; width: 300px; line-height: 22px; border: 1px solid #ccc;" /></td>
			</tr>
			<tr height="38px">
				<td><b>销售价格：</b></td>
				<td><s:textfield name="release.unitPrice" id="unitPrice"
						style="height: 26px; width: 100px; border: 1px solid #ccc;" /><label
					for="unitPrice"> 元</label></td>
			</tr>
			<tr>
				<td valign="top"><b>计划简介：</b></td>
				<td><s:textarea id="briefing" name="release.briefing"
						style="overflow: hidden; border: 1px solid #ccc;" cols="50"
						rows="5" /></td>
			</tr>
			<tr>
				<td valign="top"><b>计划详情：</b></td>
				<td><s:textarea id="txtDetails" name="release.details"
						cols="20" style="vertical-align:top; width:400px;height:200px;" /></td>
			</tr>
			<tr>
				<td colspan="2" align="center"><div>
						<input type="button" value="保存" onclick="javascript: onSave()" />
					</div></td>
			</tr>
		</table>
	</div>
</s:form></html>