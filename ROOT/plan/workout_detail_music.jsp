<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<link rel="stylesheet" type="text/css" href="css/pulic-1.css"/>
<link rel="stylesheet" type="text/css" href="css/product.css"/>
<script type="text/javascript" src="script/jquery.form.js"></script>
<script type="text/javascript" src="script/jquery.parser.js"></script>
<script type="text/javascript">
$(function(){
   	var aBtn=$('#container_pro .cont_pro').find('li'); 

	var aDiv=$('#container_pro').find('.tab_container');
	aBtn.click(function(){
		$(this).addClass('tab').siblings().removeClass("tab");
		aDiv.css('display','none');				  
		aDiv.eq($(this).index()).css('display', 'block');			  
	});
});
		
function onPictu(a){
	var pos='';
	if(a=='1'){
		var path = document.getElementById("musicAddr").value;
		var pos1 = path.lastIndexOf("\\");
		 var pos2 = path.lastIndexOf("");
		 pos = path.substring(pos1 + 1, pos2);
		 var pos3 = path.lastIndexOf(".");
		 var yz = path.substring(pos3+1);

		 if(pos==null|pos==''){
			alert("配音不能为空，请上传配音");		 
			 return;
		 }
		 if(yz!='mp3'&&yz!='wva'&&yz!='wma'){
			 alert("请选择正确的配音格式");
			 return;
		 }
		 $(document).mask('请稍候，正在保存数据......');
		$('#frmimage').form('submit', {
			url: "workout!uploadMusic.asp?fileName="+pos+"&exe="+a+"&id="+$('#courseId').val(),
			onSubmit: function(param) {
				return true;
			},
			success: function(msg) {
				$(document).unmask();
				var $msg = $.parseJSON(msg);
				if ($msg.success === true) {
					$('#musicId').val($msg.key);
					$('#altdiv2').dialog('close');
					alert('上传成功！');
				} else {
					alert('上传失败！可能的原因为：' + $msg.message);
				}
			}
		});

	}else if(a=='2'){
		$('#musicId').val($('#musickey').val());
		$('#altdiv2').dialog('close');
	}
}
function musDelete(the){
	var $tr = $(the).parents('td');
	if (confirm('您是否删除当前配音？')) {
		$.ajax({url: 'workout!deleteMusic.asp',type:'post',data: 'id=' + the, 
			success: function(msg){
				if(msg === "error"){
					alert("音乐被引用，不能删除！");
				}else{
					$('#altdiv2').html(msg);
					var aBtn=$('#container_pro .cont_pro').find('li').eq(1); 
					var aDiv=$('#container_pro').find('.tab_container').eq(1); 
					aBtn.addClass('tab').siblings().removeClass("tab");
					$('#container_pro').find('.tab_container').eq(0).css('display', 'none');
					aDiv.css('display', 'block');			  
				}
			}
		})
	}
}

function musSet(a, id){
	document.getElementById("name").value=a;
	$('#musicId').val(id);
	$('#musickey').val(id);
}	  
</script>
<div id="container_pro" style="width:400px;">
<s:form name="frmimage" theme="simple" id="frmimage" action="workout.asp" enctype="multipart/form-data">
	 <input type="hidden" id="musicId" name="course.music.id" value="<s:property value="#request.course.music.id"/>"/>
	 <input type="hidden" id="courseId" name="course.id" value="<s:property value="#request.course.id"/>"/>
	<ul class="cont_pro">
		<li class="tab"  style="border-left:1px solid #e7e7e7;"><span>本地音乐</span></li>
		<li class="tab"  style="border-left:1px solid #e7e7e7;"><span>网络音乐</span></li>
	</ul> 
	 <div id="news" class="tab_container" style="display:block; margin-top:10px;">
	 		
	 		<table width="40%" border="0" cellpadding="0" cellspacing="0"style="padding-left:10px;">
	 		<tr>
				<td><div style="float:left;width:70px">音乐地址：</td>
				<td><s:file name="file.file" id="musicAddr"></s:file></td>
			</tr>
			<tr>
				<td>音乐描述：</td>
				<td>
					<s:textarea cssClass="text" name="music.desciption" id="description" cols="40" rows="2"/>
				</td>
			</tr>
			<tr>
				<td style="float:left;margin-left:80px;clear:both;"><input type="button" name="musicAddr" class="button" value="确定" onclick="onPictu('1');"/></td>
				<td><input type="button" name="musicAddr" class="button" value="取消" onclick="rePic();"/></div></td>
			</tr>
		</table>
		
	 </div>
	 <div id="news" class="tab_container">
	 <table width="500" border="0" cellpadding="0" cellspacing="0"  style="margin-top:10px;">
	 	<tr>
			<td><div style="float:right;width:80px;">音乐地址：</div></td>
			<td><input type="name" name ="music.addr" id="name">
			<input type="hidden" id="musickey"/>
			</td>
		</tr>
                <tr><td style="float:right;width:80px;" colspan="2">我的音乐：</td></tr>
		<tr>
			
			<td colspan="2">
			<div style="overflow: auto; height: 70px;">
			<s:iterator value="musics" status="p" var = "mu">
				<div style="margin-top:10px;margin-left:50px; width: 200px;">
				<input type="radio" name="musicRadio" onclick="javascript:musSet('<s:property value="addr"/>', <s:property value="#mu.id"/>);" title="<s:property value="desciption"/>">
<%-- 				<a onclick="javascript:musSet('<s:property value="addr"/>', <s:property value="#mu.id"/>);"><img src="images/01.png" title="<s:property value="desciption"/>" /></a> --%>
				<a onclick="javascript:musSet('<s:property value="addr"/>');"><s:property value="name"/></a>
                                <a onclick="javascript:musDelete('<s:property value="#mu.id"/>');"><img src="images/03.png" /></a>
				
				</div>
			</s:iterator>
			</div>
			</td>
		</tr>
		<tr>
		  <td colspan="2"><div  style="padding-left:150px;margin-top:10px;"><input type="button" name="musicAddr" class="button" value="确定" onclick="onPictu('2');"/><input type="button" name="musicAddr" class="button" value="取消" onclick="rePic();"style="margin-left:20px;"/></div></td>	
		</tr>
	 </table>
	 </div>
</s:form>
</div>
